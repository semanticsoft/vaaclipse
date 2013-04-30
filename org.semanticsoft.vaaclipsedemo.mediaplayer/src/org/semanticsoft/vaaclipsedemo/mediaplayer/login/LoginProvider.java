/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipse.publicapi.authentication.AuthenticationConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.MediaplayerActivator;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class LoginProvider
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	UI ui;
	
	
	@PostConstruct
	public void postConstruct(VerticalLayout parent)
	{
		//Set the caption of login page (window)
		ui.getPage().setTitle("Login to Mediaplayer");
				
				
		Panel loginPanel = new Panel("Login");
		loginPanel.setWidth("250px");
		parent.addComponent(loginPanel);
		
		parent.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		
		LoginForm login = new LoginForm();
		loginPanel.setContent(login);
		
		login.addListener(new LoginListener() {
			
			@Override
			public void onLogin(LoginEvent event)
			{
				String username = event.getLoginParameter("username");
				String password = event.getLoginParameter("password");
                
				if (username.trim().isEmpty())
					username = null;
				
				if (username == null)
					username = "guest";
				
				//Here you check username and password and if user with given password exists 
				//send message AuthenticationConstants.Events.Authentication with User object:
				if (check(username, password))
				{
					MediaplayerActivator.getInstance().getUserCounter().increment();
					User user = new User();
					user.setName(username);
					
					eventBroker.send(AuthenticationConstants.Events.Authentication.name, user);
				}
				else
				{
					Notification.show("User does not exist", Notification.Type.WARNING_MESSAGE);
				}
			}
		});
	};
	
	private boolean check(String username, String password)
	{
		return true;
	}
}
