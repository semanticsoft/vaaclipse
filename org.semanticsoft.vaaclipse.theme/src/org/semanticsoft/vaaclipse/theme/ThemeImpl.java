package org.semanticsoft.vaaclipse.theme;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticsoft.vaaclipse.publicapi.theme.Theme;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeContribution;

public class ThemeImpl extends ThemeEntryImpl implements Theme {
	
	private String label;
	private String description;
	private List<ThemeContribution> contributions = new ArrayList<>();
	private List<Theme> inheritedThemes = new ArrayList<>();
	
	private List<String> cssCashList;
	private List<String> resourcesCashList;
	
	private byte[] cssBytes;
	
	public ThemeImpl(String id, String label) 
	{
		super(id);
		this.label = label;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	public List<Theme> getInheritedThemes()
	{
		return Collections.unmodifiableList(this.inheritedThemes);
	}
	
	@Override
	public List<ThemeContribution> getContributions()
	{
		return Collections.unmodifiableList(this.contributions);
	}
	
	public void addContribution(ThemeContribution contibution)
	{
		this.resourcesCashList = null;
		this.cssCashList = null;
		this.contributions.add(contibution);
	}
	
	public void addInheritedTheme(Theme theme)
	{
		this.resourcesCashList = null;
		this.cssCashList = null;
		this.inheritedThemes.add(theme);
	}

	@Override
	public String toString() {
		return "Theme [id=" + id + ", label='" + label;
	}
	
	@Override
	public List<String> getAllCssURIs()
	{
		if (cssCashList == null)
		{
			cssCashList = new ArrayList<>();
			cssCashList.add(getCssUri());
			
			for (Theme theme : this.getInheritedThemes())
			{
				cssCashList.addAll(theme.getAllCssURIs());
			}
			
			for (ThemeContribution c : this.getContributions())
			{
				if (c.getCssUri() != null)
					cssCashList.add(c.getCssUri());
			}
		}
		return cssCashList;
	}
	
	@Override
	public List<String> getAllResourceLocationURIs()
	{
		if (resourcesCashList == null)
		{
			resourcesCashList = new ArrayList<>();
			resourcesCashList.addAll(getResourceLocationURIs());
			
			for (Theme theme : this.getInheritedThemes())
			{
				resourcesCashList.addAll(theme.getAllResourceLocationURIs());
			}
			
			for (ThemeContribution c : this.getContributions())
			{
				resourcesCashList.addAll(c.getResourceLocationURIs());
			}
		}
		return Collections.unmodifiableList(resourcesCashList);
	}
	
	@Override
	public InputStream getCssAsStream()
	{
		return new ByteArrayInputStream(cssBytes);
	}

	@Override
	public InputStream getThemeResourceAsStream(String themeUri)
	{
		InputStream in = null;
		for (String resLocUri : getAllResourceLocationURIs())
		{
			String bundlePath = resLocUri + themeUri;
			try {
				URL u = new URL(bundlePath);
				in = u.openStream();
				break;
			}
			catch (Exception ex) {}
			
		}
		return in;
	}
	
	void prepareTheme() throws Exception
	{
		StringBuffer inheritedBuffer = new StringBuffer();
		for (Theme inherited : inheritedThemes)
		{
			inheritedBuffer.append(String.format("@import \"../%s/styles.css\";", inherited.getWebId()));
		}
		
		List<ThemeContributionImpl> afterMainCss = new ArrayList<>();
		List<ThemeContributionImpl> afterInheritedImports = new ArrayList<>();
		List<ThemeContributionImpl> beforeInheritedImports = new ArrayList<>();
		Map<String, List<ThemeContributionImpl>> bindedContributionsBefore = new HashMap<>();
		Map<String, List<ThemeContributionImpl>> bindedContributionsAfter = new HashMap<>();
		
		for (ThemeContribution c : this.contributions)
		{
			if (c.getCssUri() == null)
				continue;
			
			ThemeContributionImpl contribution = (ThemeContributionImpl) c;
			String pos = contribution.getInsertPosition();
			String[] split = pos.split("=");
			if (split.length == 2)
			{
				String mod = split[0].trim();
				
				if ("after".equals(mod) || "before".equals(mod))
				{
					String value = split[1].trim();
					
					if (mod.equals("after") && ThemeContribution.MAIN_CSS.equals(value))
					{
						afterMainCss.add(contribution);
					}
					else if (mod.equals("after") && ThemeContribution.INHERITED_IMPORTS.equals(value) ||
							mod.equals("before") && ThemeContribution.MAIN_CSS.equals(value))
					{
						afterInheritedImports.add(contribution);
					}
					else if (mod.equals("before") && ThemeContribution.INHERITED_IMPORTS.equals(value))
					{
						beforeInheritedImports.add(contribution);
					}
					else
					{//value - is contribution id?
						if (mod.equals("before"))
						{
							if (!bindedContributionsBefore.containsKey(value))
								bindedContributionsBefore.put(value, new ArrayList<ThemeContributionImpl>());
							bindedContributionsBefore.get(value).add(contribution);	
						}
						else if (mod.equals("after"))
						{
							if (!bindedContributionsAfter.containsKey(value))
								bindedContributionsAfter.put(value, new ArrayList<ThemeContributionImpl>());
							bindedContributionsAfter.get(value).add(contribution);	
						}
					}
				}
			}
		}
		
		StringBuffer afterMainCssContributionsBuffer = buildStringBuffer(afterMainCss, bindedContributionsBefore, bindedContributionsAfter);
		StringBuffer afterInheritedImportsContributionsBuffer = buildStringBuffer(afterInheritedImports, bindedContributionsBefore, bindedContributionsAfter);
		StringBuffer beforeInheritedImportsContributionsBuffer = buildStringBuffer(beforeInheritedImports, bindedContributionsBefore, bindedContributionsAfter);
		
		String ls = System.lineSeparator();
		String total = beforeInheritedImportsContributionsBuffer.toString() + ls + inheritedBuffer.toString() + ls + 
				afterInheritedImportsContributionsBuffer.toString() + ls + "@import \"original_styles.css\";" + ls + afterMainCssContributionsBuffer.toString() + ls;
		
		cssBytes = total.getBytes("UTF-8");
		
		for (Theme inherited : inheritedThemes)
		{
			if (inherited instanceof ThemeImpl)
			{
				((ThemeImpl)inherited).prepareTheme();
			}
		}
	}

	private StringBuffer buildStringBuffer(List<ThemeContributionImpl> list,
			Map<String, List<ThemeContributionImpl>> bindedContributionsBefore, Map<String, List<ThemeContributionImpl>> bindedContributionsAfter)
	{
		StringBuffer buffer = new StringBuffer();
		List<ThemeContributionImpl> result = buildList(list, bindedContributionsBefore, bindedContributionsAfter);
		for (ThemeContributionImpl c : result)
		{
			buffer.append(String.format("@import \"%s.css\";", c.getWebId()));
		}
		return buffer;
	}
	
	private List<ThemeContributionImpl> buildList(List<ThemeContributionImpl> list,
			Map<String, List<ThemeContributionImpl>> bindedContributionsBefore, Map<String, List<ThemeContributionImpl>> bindedContributionsAfter)
	{
		bindedContributionsBefore = new HashMap<>(bindedContributionsBefore);
		bindedContributionsAfter = new HashMap<>(bindedContributionsAfter);
		List<ThemeContributionImpl> accumulator = new ArrayList<>();
		
		int restBefore, restAfter;
		do
		{
			restBefore = bindedContributionsBefore.size() + bindedContributionsAfter.size();
			for (ThemeContributionImpl c : list)
			{

				List<ThemeContributionImpl> bindedList = bindedContributionsBefore.remove(c.getId());
				if (bindedList != null)
					accumulator.addAll(bindedList);
				
				accumulator.add(c);
				
				bindedList = bindedContributionsAfter.remove(c.getId());
				if (bindedList != null)
					accumulator.addAll(bindedList);
			}
			restAfter = bindedContributionsBefore.size() + bindedContributionsAfter.size();
			list = accumulator;
			accumulator = new ArrayList<>();
		}
		while (restBefore != restAfter);
		
		return list;
	}
}
