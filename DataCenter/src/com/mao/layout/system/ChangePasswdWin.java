package com.mao.layout.system;

import com.mao.bean.User;
import com.mao.tool.BeanManager;
import com.mao.tool.MaoLogger;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ChangePasswdWin extends Window implements ClickListener {
	private User user;
	private PasswordField oldPasswd;
	private PasswordField newPasswd;
	private PasswordField newRePasswd;

	public ChangePasswdWin(User user) {
		super();
		this.user = user;
		setResizable(true);
		setResizeLazy(true);
		
		setCaption("修改用户"+user.getEmployee().getName()+"的密码");
		
		FormLayout layout=new FormLayout();
		oldPasswd=new PasswordField("原密码：");
		newPasswd=new PasswordField("新密码：");
		newRePasswd=new PasswordField("确认新密码：");

		oldPasswd.setInputPrompt("原密码");
		oldPasswd.setRequired(true);
		oldPasswd.setRequiredError("密码不能为空");
		newPasswd.setInputPrompt("新密码");
		newPasswd.setRequired(true);
		newPasswd.setRequiredError("密码不能为空");
		newRePasswd.setInputPrompt("确认新密码");
		newRePasswd.setRequired(true);
		newRePasswd.setRequiredError("密码不能为空");


		Button okButton=new Button("提交(S)");
		okButton.setClickShortcut('s', ModifierKey.ALT);
		okButton.addClickListener(this);
		
		layout.addComponent(oldPasswd);
		layout.addComponent(newPasswd);
		layout.addComponent(newRePasswd);
		
		VerticalLayout mainLayout=new VerticalLayout();
		mainLayout.setMargin(true);
		mainLayout.addComponent(layout);
		mainLayout.addComponent(okButton);
		mainLayout.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(layout, 1.0f);
		setContent(mainLayout);
		setVisible(true);
		center();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			if(!(oldPasswd.isValid()&&oldPasswd.isValid()&&oldPasswd.isValid()) )
					return;
			if(!user.getPassword().equals(oldPasswd.getValue())){
				Notification.show("原密码不正确，请重新确认原密码！", Notification.Type.ERROR_MESSAGE);
				oldPasswd.focus();
				return;
			}
			if(!newPasswd.getValue().equals(newRePasswd.getValue())){
				Notification.show("新密码二次输入不一样，请重新确认新密码！", Notification.Type.ERROR_MESSAGE);
				newPasswd.focus();
				return;
			}
			user.setPassword(newPasswd.getValue());
			BeanManager.BM.saveBean(user);
			Notification.show("修改密码成功！", Notification.Type.ERROR_MESSAGE);
			
			close();
		} catch (Exception e) {
			MaoLogger.error("保存用户",e);
		} 

	}

}
