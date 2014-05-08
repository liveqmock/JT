package com.mao.layout.system;

import java.io.IOException;
import java.util.Iterator;

import com.mao.bean.MenuBean;
import com.mao.customLayout.DbSearchPanel;
import com.mao.customLayout.bean.DbSearch;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class ContentTab extends TabSheet {



	public ContentTab() {
		super();
		setSizeFull();
	}

	public void selectTab(MenuBean menuBean) {
		Iterator<Component> iterator = iterator();
		Component componet=null;
		while(iterator.hasNext()){
			componet = iterator.next();
			if(((AbstractLayout)componet).getData()==menuBean){
				setSelectedTab(componet);
				return;
			}
		}
		componet = getLayout(menuBean);
		if(componet!=null){			
			((AbstractLayout)componet).setData(menuBean);
			addTab(componet,menuBean.getCaption()).setClosable(true);	
			setSelectedTab(componet);
		}
	}

	public  AbstractLayout getLayout(MenuBean menuBean) {
		AbstractLayout layout=null;

		if(menuBean.getResource().getResourcePnl().toLowerCase().endsWith(".xml")){
			try {
				String fileName = getSession().getService().getBaseDirectory().getCanonicalPath()+"/ds/"+menuBean.getResource().getResourcePnl();
				DbSearch dbSearch = DbSearch.loadFromXml(fileName);
				if(dbSearch!=null)
					layout=new DbSearchPanel(dbSearch);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else{
			try{
				@SuppressWarnings("unchecked")
				Class<AbstractLayout> layoutClass = (Class<AbstractLayout>) Class
					.forName(menuBean.getResource().getResourcePnl());
				if(layoutClass!=null)
					layout=layoutClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return layout;
	}
}
