package com.mao.layout.system;



import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
/**
 * Main UI class
 */
@Theme("datacenter")
public class Root extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Root.class)
	public static class Servlet extends VaadinServlet {
	}

	private Navigator navigator;
	public Navigator getNavigator() {
		return navigator;
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("平湖合行数据处理中心");   
		setSizeFull();
		navigator = new Navigator(this, this);
		navigator.addView("login", LoginLayout.class);
		navigator.addView("main",  MainLayout.class);
		if(getSession().getAttribute("loginUser")!=null)
			navigator.navigateTo("main");
		else
			navigator.navigateTo("login");

		VaadinService.getCurrent() .addSessionDestroyListener(new SessionDestroyListener() {

			@Override
			public void sessionDestroy(SessionDestroyEvent event) {
				navigator.navigateTo("login");				
			}
		});

		
	}

	
	
}