package com.mao.layout.resource;

import java.util.Collection;
import java.util.List;

import com.mao.bean.Resource;
import com.mao.bean.Role;
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


public class SystemRoleAuth extends VerticalLayout {

	public SystemRoleAuth() throws Exception {
		super();

		List<Role> roles = BeanManager.BM.getBeans( Role.class);
		final ComboBox roleBox=new ComboBox("角色选择：", roles);
		roleBox.setImmediate(true);
		roleBox.setRequired(true);;
		List<Resource> resouces =BeanManager.BM.getBeans(Resource.class,"where  parentRe<>null");
		final TwinColSelect l = new TwinColSelect(null,resouces);
		l.setRows(20);
		l.setStyleName("border");
		l.setNullSelectionAllowed(false);
		l.setMultiSelect(true);
		l.setImmediate(true);

		l.setLeftColumnCaption("可分配资源:");
		l.setRightColumnCaption("已分配资源:");
		l.setSizeFull();

		Button submitBt=new Button("提交(S)");
		submitBt.setClickShortcut(KeyCode.ENTER);
		addComponent(roleBox);
		addComponent(l);
		addComponent(submitBt);
		setExpandRatio(l, 1.0f);

		roleBox.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				l.setValue(((Role)roleBox.getValue()).getResources());

			}
		});
		submitBt.addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				Role role=(Role)roleBox.getValue();
				role.setResources((Collection<Resource>) l.getValue());
				BeanManager.BM.saveBean(role);
				Notification.show("保存成功！");

			}
		});

	}


}
