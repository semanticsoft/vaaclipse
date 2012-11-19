/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.processors;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

/**
 * @author rushan
 *
 */
public class MediaLibrarySetupProcessor
{
	@Execute
	public void setup(IEclipseContext context)
	{
		MediaLibrary library = new MediaLibrary();
		
		MediaCategory vaadin = new MediaCategory();
		vaadin.setName("Vaadin");
		
		Media vaadinIntro = new Media();
		vaadinIntro.setName("Vaadin - Rich web applications in plain Java without plugins or JavaScript");
		vaadinIntro.setUri("http://www.youtube.com/v/W-mp5E-T88o");
		vaadin.addMedia(vaadinIntro);
		
		MediaCategory sport = new MediaCategory();
		sport.setName("Sport");
		
		Media sport1 = new Media();
		sport1.setName("Lionel Messi Goal vs Getafe - English version");
		sport1.setUri("http://www.youtube.com/v/5vmm-xCq4To");
		sport.addMedia(sport1);
		
		Media sport2 = new Media();
		sport2.setName("FC Barcelona Tiki Taka vs Real Madrid");
		sport2.setUri("http://www.youtube.com/v/M7INnQGoBkE");
		sport.addMedia(sport2);
		
		MediaCategory history = new MediaCategory();
		history.setName("History");
		
		Media sputnik = new Media();
		sputnik.setName("The Soviet Sputnik");
		sputnik.setUri("http://www.youtube.com/v/cLWQFN3iqME");
		history.addMedia(sputnik);
			
		Media gagarin = new Media();
		gagarin.setName("First journey into outer space");
		gagarin.setUri("http://www.youtube.com/v/EjDvMWlsSJc");
		history.addMedia(gagarin);
		
		MediaCategory nature = new MediaCategory();
		nature.setName("Nature");
		
		Media animalWorld = new Media();
		animalWorld.setName("In the Animal World");
		animalWorld.setUri("http://www.youtube.com/v/ygNNKUu2sWQ");
		nature.addMedia(animalWorld);
		
		library.addCategory(vaadin);
		library.addCategory(history);
		library.addCategory(sport);
		library.addCategory(nature);
		
		context.set(MediaLibrary.class, library);
	}
}
