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
		
		setCaption("�޸��û�"+user.getEmployee().getName()+"������");
		
		FormLayout layout=new FormLayout();
		oldPasswd=new PasswordField("ԭ���룺");
		newPasswd=new PasswordField("�����룺");
		newRePasswd=new PasswordField("ȷ�������룺");

		oldPasswd.setInputPrompt("ԭ����");
		oldPasswd.setRequired(true);
		oldPasswd.setRequiredError("���벻��Ϊ��");
		newPasswd.setInputPrompt("������");
		newPasswd.setRequired(true);
		newPasswd.setRequiredError("���벻��Ϊ��");
		newRePasswd.setInputPrompt("ȷ��������");
		newRePasswd.setRequired(true);
		newRePasswd.setRequiredError("���벻��Ϊ��");


		Button okButton=new Button("�ύ(S)");
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
				Notification.show("ԭ���벻��ȷ��������ȷ��ԭ���룡", Notification.Type.ERROR_MESSAGE);
				oldPasswd.focus();
				return;
			}
			if(!newPasswd.getValue().equals(newRePasswd.getValue())){
				Notification.show("������������벻һ����������ȷ�������룡", Notification.Type.ERROR_MESSAGE);
				newPasswd.focus();
				return;
			}
			user.setPassword(newPasswd.getValue());
			BeanManager.BM.saveBean(user);
			Notification.show("�޸�����ɹ���", Notification.Type.ERROR_MESSAGE);
			
			close();
		} catch (Exception e) {
			MaoLogger.error("�����û�",e);
		} 

	}

}
