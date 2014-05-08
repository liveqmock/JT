package com.mao.layout.system;

import com.mao.bean.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout implements View {

	private HorizontalLayout statusBar;
	private Label loginInfo=new Label("��Ϣ����ϵͳͳһ���ƽ̨");
	private ContentTab mainPanel;

	public MainLayout() {
		super();
	}
	private void initPanel(){

		statusBar=new HorizontalLayout();
		statusBar.setWidth("100%");
		setSizeFull();
		setStyleName("border");
		statusBar.addComponent(loginInfo);
		mainPanel=new ContentTab();
		
		addComponent(new headerLayout());
		addComponent(new MyMenuBar(mainPanel));
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
			initPanel();
			loginInfo.setValue("��¼�û�����"+user+"    ��¼ʱ�䣺"+user.getLoginDate());
			
		}


	}

	
}
