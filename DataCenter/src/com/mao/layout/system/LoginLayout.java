package com.mao.layout.system;

import java.util.Date;

import javax.persistence.NoResultException;

import com.mao.bean.User;
import com.mao.tool.BeanManager;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public  class LoginLayout extends HorizontalLayout implements ClickListener,View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -37230621037287303L;

	private TextField userName=new TextField();;
	private PasswordField password=new PasswordField();

	public LoginLayout() {
		setStyleName("login");


		userName.setInputPrompt("请输入登录用户名！");
		password.setInputPrompt("请输入登录密码");
		userName.setInvalidAllowed(false);
		
				userName.setValue("8730001");
				password.setValue("1");
				
		userName.setWidth("100%");
		password.setWidth("100%");
		setSizeFull();

		Button button=new Button();
		button.setStyleName("v-button-link");
		button.setIcon(new ThemeResource("img/login_bt.png"));
		button.setClickShortcut(KeyCode.ENTER);
		button.addClickListener(this);
		CustomLayout customLayout=new CustomLayout("login");
		customLayout.setWidth("500px");
		customLayout.addComponent(userName,"username");
		customLayout.addComponent(password,"password");
		customLayout.addComponent(button,"button");
		addComponent(customLayout);
		setComponentAlignment(customLayout, Alignment.MIDDLE_CENTER);
		//		setWidth("453px");
		//		setHeight("262px");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		login();

	}
	public void login(){
		if(!userName.isValid()) {
			Notification.show("登录名不能为空！",Type.HUMANIZED_MESSAGE);
			userName.focus();
			return;
		}
		if(!password.isValid()) {
			Notification.show("密码不能为空！",Type.HUMANIZED_MESSAGE);
			password.focus();
			return;
		}
		try{
			User user = BeanManager.BM.getBean(User.class,"where  employee.id=?1",userName.getValue());
			if(user!=null&&user.getPassword().trim().equals(password.getValue())){
				getSession().setAttribute(User.class, user);	
				user.setLoginDate(new Date());
				((Root)UI.getCurrent()).getNavigator().navigateTo("main");
			}else{

				Notification.show("登录名或密码有误！",Type.ERROR_MESSAGE);
			}
		}catch(NoResultException  e){

			Notification.show("登录不成功！",Type.ERROR_MESSAGE);
		}
	}
	@Override
	public void enter(ViewChangeEvent event) {
		User user=VaadinSession.getCurrent().getAttribute(User.class);
		if(user!=null){
			event.getNavigator().navigateTo("main");
			//			System.err.println("\n\n goto main ......................................................................\n\n");
		}


	}

}
