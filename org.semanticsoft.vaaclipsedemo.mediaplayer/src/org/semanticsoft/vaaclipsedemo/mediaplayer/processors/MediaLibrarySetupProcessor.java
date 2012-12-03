/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.processors;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;

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
		
		//Eclipse
		MediaCategory eclipse = new MediaCategory();
		eclipse.setName("Eclipse");
		
		Media eclipseIntro = new Media();
		eclipseIntro.setName("Introduction into Eclipse E4");
		eclipseIntro.setUri("http://www.youtube.com/v/hAGhny7bcCs");
		eclipseIntro.setDescription("San Francisco Java User Group hosted an event on April 13th, 2010 with Lars Vogel, " +
				"a committer on the Eclipse e4 project, who gave a talk on the next generation of the Eclipse Platform. " +
				"We had two speakers that evening, this is the first of the two presentations.");
		eclipse.addMedia(eclipseIntro);
		
		//Vaadin
		MediaCategory vaadin = new MediaCategory();
		vaadin.setName("Vaadin");
		
		Media vaadinIntro = new Media();
		vaadinIntro.setName("Introduction into vaadin");
		vaadinIntro.setUri("http://www.youtube.com/v/W-mp5E-T88o");
		vaadinIntro.setDescription("The video describes vaadin idea: rich web applications in plain Java without plugins or JavaScript");
		vaadin.addMedia(vaadinIntro);
		
		//History
		MediaCategory history = new MediaCategory();
		history.setName("History");
		
		Media sputnik = new Media();
		sputnik.setName("The Soviet Sputnik");
		sputnik.setUri("http://www.youtube.com/v/TbAXkWPasYw");
		sputnik.setDescription("History changed on October 4, 1957, when the Soviet Union successfully launched Sputnik I. " +
				"The world's first artificial satellite was about the size of a beach ball (58 cm.or 22.8 inches in diameter), " +
				"weighed only 83.6 kg. or 183.9 pounds, and took about 98 minutes to orbit the Earth on its elliptical path. " +
				"That launch ushered in new technological and scientific developments. While the Sputnik launch was a single event, " +
				"it marked the start of the space age and the U.S.-U.S.S.R space race.");
		history.addMedia(sputnik);
			
		Media gagarin = new Media();
		gagarin.setName("First journey into outer space");
		gagarin.setUri("http://www.youtube.com/v/EjDvMWlsSJc");
		gagarin.setDescription("Yuri Alekseyevich Gagarin was a Soviet pilot, cosmonaut, communist. " +
				"He was the first human to journey into outer space, when his Vostok spacecraft completed an orbit of the Earth on 12 April 1961." +
				"Gagarin became an international celebrity, and was awarded many medals and titles, including Hero of the Soviet Union, " +
				"the highest soviet honour.");
		history.addMedia(gagarin);
		
		Media moon = new Media();
		moon.setName("Lunar ascent and return");
		moon.setUri("http://www.youtube.com/v/RMINSD7MmT4");
		moon.setDescription("Apollo 11 was the spaceflight that landed the first humans, Americans Neil Armstrong and Buzz Aldrin, " +
				"on the Moon on July 20, 1969, at 20:18 UTC. Armstrong became the first to step onto the lunar surface 6 hours later " +
				"on July 21 at 02:56 UTC.");
		history.addMedia(moon);
		
		//Sport
		MediaCategory sport = new MediaCategory();
		sport.setName("Sport");
		
		Media sport1 = new Media();
		sport1.setName("Lionel Messi - Maradona's successor");
		sport1.setUri("http://www.youtube.com/v/5vmm-xCq4To");
		sport1.setDescription("On 18th April 2007, Barcelona�s Lionel Messi scored 2 goals during a Copa del Rey semifinal against Getafe CF. " +
				"The first goal was very similar to Diego Maradona�s goal against England at the Quarterfinals of the 1986 World Cup, " +
				"also known as the Goal of the Century. Messi ran about the same distance (60 metres), beat the same number of players, " +
				"scored from a very similar position, and ran towards the corner flag just as Maradona did in Mexico 21 years before.");
		sport.addMedia(sport1);
		
		Media sport2 = new Media();
		sport2.setName("FC Barcelona Tiki Taka vs Real Madrid");
		sport2.setUri("http://www.youtube.com/v/M7INnQGoBkE");
		sport2.setDescription("Tiki-taka is a style of play in football that has evolved from Total Football and is characterised by short " +
				"passing and movement, working the ball through various channels, and maintaining possession. " +
				"The style is primarily associated with club FC Barcelona. Tiki-taka moves away from the traditional thinking of formations in football " +
				"to a concept derived from zonal play.");
		sport.addMedia(sport2);
		
		Media sport3 = new Media();
		sport3.setName("FC Barcelona against Liverpool (UCL 2001-02)");
		sport3.setUri("http://www.youtube.com/v/1C--gtzvDUk");
		sport3.setDescription("Barca and her style against Liverpool FC");
		sport.addMedia(sport3);
		
		Media sport4 = new Media();
		sport4.setName("Barca: 40 passes in succession and goal");
		sport4.setUri("http://www.youtube.com/v/dg9-_qiaiM8");
		sport4.setDescription("Yet another example of Barca style.");
		sport.addMedia(sport4);
		
		//Nature
		MediaCategory nature = new MediaCategory();
		nature.setName("Nature");
		
		Media animalWorld = new Media();
		animalWorld.setName("In the Animal World");
		animalWorld.setUri("http://www.youtube.com/v/ygNNKUu2sWQ");
		animalWorld.setDescription("Fauna is extremely diverse.");
		nature.addMedia(animalWorld);
		
		library.addCategory(eclipse);
		library.addCategory(vaadin);
		library.addCategory(history);
		library.addCategory(sport);
		library.addCategory(nature);
		
		context.set(MediaLibrary.class, library);
		context.set(Playlist.class, new Playlist());
		context.set(MediaService.class, ContextInjectionFactory.make(MediaService.class, context));
	}
}
