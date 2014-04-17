package com.mao.layout.system;

import com.mao.bean.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout implements View {

	private HorizontalLayout statusBar;
	private Label loginInfo=new Label("信息管理系统统一监控平台");
	private CenterPanel mainPanel;

	public MainLayout() {
		super();
		statusBar=new HorizontalLayout();
		statusBar.setWidth("100%");
		setSizeFull();
		setStyleName("border");
		statusBar.addComponent(loginInfo);
		mainPanel = new CenterPanel();
		addComponent(new headerLayout());
		addComponent(mainPanel);
		addComponent(statusBar);
		setExpandRatio(mainPanel, 1.0f);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		User user=VaadinSession.getCurrent().getAttribute(User.class);
		if(user==null){
			((Root)UI.getCurrent()).getNavigator().navigateTo("login");
		}else{	
			mainPanel.createMenu(user);
			loginInfo.setValue("登录用户名："+user+"    登录时间："+user.getLoginDate());
			
		}


	}

	
}
