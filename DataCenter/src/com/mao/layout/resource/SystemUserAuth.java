package com.mao.layout.resource;

import java.util.Collection;
import java.util.List;

import com.mao.bean.Role;
import com.mao.bean.User;
import com.mao.tool.BeanManager;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;


public class SystemUserAuth extends VerticalLayout {

	public SystemUserAuth() throws Exception {
		super();

		List<User> users = BeanManager.BM.getBeans(User.class);
		List<Role> roles = BeanManager.BM.getBeans(Role.class);
		final ComboBox userBox=new ComboBox("角色选择：", users);
		userBox.setImmediate(true);
		userBox.setRequired(true);
		final TwinColSelect l = new TwinColSelect(null,roles);
		l.setRows(20);
		l.setStyleName("border");
		l.setNullSelectionAllowed(true);
		l.setMultiSelect(true);
		l.setImmediate(true);

		l.setLeftColumnCaption("可分配角色:");
		l.setRightColumnCaption("已分配角色:");
		l.setSizeFull();

		Button submitBt=new Button("提交(S)");
		submitBt.setClickShortcut(KeyCode.ENTER);
		addComponent(userBox);
		addComponent(l);
		addComponent(submitBt);
		setExpandRatio(l, 1.0f);


		userBox.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				User user=(User)userBox.getValue();
				if(user!=null)
				l.setValue(user.getRoles());

			}
		});
		submitBt.addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				User user=(User)userBox.getValue();
				user.setRoles((Collection<Role>) l.getValue());

				BeanManager.BM.saveBean(user);
				Notification.show("保存成功！");

			}
		});

	}


}
