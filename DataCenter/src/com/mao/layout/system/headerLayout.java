package com.mao.layout.system;

import org.vaadin.dialogs.ConfirmDialog;

import com.mao.bean.User;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
public class headerLayout extends HorizontalLayout implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -27236499716634176L;
	
	
	private Button changePwdBt;
	private Button logoutBt;
	private Button exitBt;
	
	public headerLayout() {
		super();
		setWidth("100%");
		setStyleName("header");
		Image image=new Image();
		image.setSource( new ThemeResource("img/logo.png"));
		
		HorizontalLayout buttonLayout=new HorizontalLayout();
		changePwdBt=new Button("修改密码");
		logoutBt=new Button("注销用户");
		exitBt=new Button("退出系统");

		changePwdBt.setIcon(new ThemeResource("img/icon_m_ps.gif"));
		logoutBt.setIcon(new ThemeResource("img/icon_logout.gif"));
		exitBt.setIcon(new ThemeResource("img/icon_bye.gif"));

		changePwdBt.addClickListener(this);
		logoutBt.addClickListener(this);
		exitBt.addClickListener(this);
		
		buttonLayout.addComponent(changePwdBt);
		buttonLayout.addComponent(logoutBt);
		buttonLayout.addComponent(exitBt);
		buttonLayout.setComponentAlignment(changePwdBt, Alignment.MIDDLE_RIGHT);
		buttonLayout.setComponentAlignment(logoutBt, Alignment.MIDDLE_RIGHT);
		buttonLayout.setComponentAlignment(exitBt, Alignment.MIDDLE_RIGHT);
		buttonLayout.setWidth("350px");


		addComponent(image);
		addComponent(buttonLayout);
		

		setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
		setExpandRatio(buttonLayout, 1.0f);
		
		
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==changePwdBt){
			ChangePasswdWin win = new ChangePasswdWin((User) getSession().getAttribute(User.class));
			
			getUI().addWindow(win);
		}else if(event.getButton()==logoutBt){
			ConfirmDialog.show(UI.getCurrent(), "注销确认", "你确定要注销本系统？", "注销", "取消", new ConfirmDialog.Listener() {
				
				@Override
				public void onClose(ConfirmDialog dialog) {
					 if (dialog.isConfirmed()) {
						 getSession().close();
						 JavaScript.getCurrent().execute(" location.reload();");
							
		                } 
				}
			});
		}else if(event.getButton()==exitBt){
			ConfirmDialog.show(UI.getCurrent(), "退出确认", "你确定要退出本系统？", "退出", "取消", new ConfirmDialog.Listener() {
				
				@Override
				public void onClose(ConfirmDialog dialog) {
					 if (dialog.isConfirmed()) {
						 getSession().close();
						 JavaScript.getCurrent().execute("window.open('','_self','');window.close();");
							
		                } 
				}
			});
		}
		
	}
	
}
