package com.mao.layout.system;

import java.util.HashMap;
import java.util.TreeSet;

import com.mao.bean.MenuBean;
import com.mao.bean.Resource;
import com.mao.bean.Role;
import com.mao.bean.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;

public class MyMenuBar extends MenuBar implements  Command{
	private HashMap<MenuItem, MenuBean> itemMap=new HashMap<>();
	ContentTab contentTab;
	public MyMenuBar(ContentTab contentTab) {
		super();
		this.contentTab=contentTab;
		setWidth("100%");
//		setAutoOpen(true);
		setImmediate(true);
		User loginUser = VaadinSession.getCurrent().getAttribute(User.class);
		TreeSet<MenuBean> topMenuBeans=new TreeSet<>();
		for(Role role:loginUser.getRoles()){
			for(Resource resource:role.getResources())
				topMenuBeans.add(resource.getTopMenu());
		}
		for(MenuBean menuBean:topMenuBeans){
			MenuItem item=addItem(menuBean.getCaption(), menuBean.getChilds().isEmpty()?this:null);
			itemMap.put(item, menuBean);
			for(MenuBean child:menuBean.getChilds()){
				addMenuItem(item, child);
			}
		}
	}
	public void addMenuItem(MenuItem topItem,MenuBean menuBean) {
		MenuItem item= topItem.addItem(menuBean.getCaption(), menuBean.getChilds().isEmpty()?this:null);
		itemMap.put(item, menuBean);
		
		for(MenuBean child:menuBean.getChilds()){
			addMenuItem(item, child);
		}
	}
	@Override
	public void menuSelected(MenuItem selectedItem) {
		contentTab.selectTab( itemMap.get(selectedItem));
		
	}
}
