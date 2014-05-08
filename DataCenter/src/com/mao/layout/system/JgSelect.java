package com.mao.layout.system;

import com.mao.bean.Department;
import com.mao.bean.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;

public class JgSelect extends ComboBox{

	public JgSelect() {
		super();
		User loginUser = VaadinSession.getCurrent().getAttribute(User.class);
		if(loginUser.getLevel()<=1){
			addItem("%");
			setItemCaption("%", "ȫϽ");
		}else if(loginUser.getLevel()==2){
			addItem(loginUser.getEmployee().getDepartment().getId().substring(0, 5)+ "%");
			setItemCaption("%", "ȫϽ");
		}
		for(Department department:loginUser.getContralDepartments()){
			addItem(department.getId());
			setItemCaption(department.getId(),department.getSimpleName());
		}
	}

}
