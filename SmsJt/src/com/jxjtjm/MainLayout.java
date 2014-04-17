package com.jxjtjm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout implements View {


	
	public MainLayout() {
		super();
		setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Object object=getSession().getAttribute("loginUser");
		if(object==null){
			((Root)UI.getCurrent()).getNavigator().navigateTo("login");
		}else{	
			
			TabSheet tabSheet=new TabSheet(new SingleSms(),new MuliteSms(),new GzSms()
				,new ManagerContact(),new SmsHistory());
			tabSheet.setSizeFull();
			addComponent(tabSheet);
		}


	}
}
