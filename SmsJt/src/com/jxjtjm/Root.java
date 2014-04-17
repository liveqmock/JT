package com.jxjtjm;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UnsupportedBrowserHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@Theme("smsjt")
@Title("津田短信发送平台")
public class Root extends UI {

	private Navigator navigator;
	public Navigator getNavigator() {
		return navigator;
	}
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Root.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		VaadinService.getCurrent().addSessionInitListener(new MyUnsupportedBrowserHandler());
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

	
	private class MyUnsupportedBrowserHandler extends UnsupportedBrowserHandler
	implements SessionInitListener {

		@Override
		public void sessionInit(SessionInitEvent event) throws ServiceException {
			// Listen to requests so we can serve the custom "Browser too old" page
			event.getSession().addRequestHandler(this);
		}

		@Override
		protected void writeBrowserTooOldPage(com.vaadin.server.VaadinRequest request,
				com.vaadin.server.VaadinResponse response) throws IOException {

			 PrintWriter pageWriter = response.getWriter();

			 WebBrowser browser = Page.getCurrent().getWebBrowser();

			if (browser.isIE()) {
				pageWriter
				.write("Internet Explorer "
						+ browser.getBrowserMajorVersion()
						+ " is too old and is not supported. Please upgrade to a newer version");
			} else if (browser.isOpera()) {
				pageWriter
				.write("Opera "
						+ browser.getBrowserMajorVersion()
						+ " is too old and is not supported. Please upgrade to a newer version");
			} else if (browser.isFirefox()) {
				pageWriter
				.write("Firefox "
						+ browser.getBrowserMajorVersion()
						+ " is too old and is not supported. Please upgrade to a newer version");
			} else if (browser.isSafari()) {
				pageWriter
				.write("Safari "
						+ browser.getBrowserMajorVersion()
						+ " is too old and is not supported. Please upgrade to a newer version");
			}
		};
	}
}