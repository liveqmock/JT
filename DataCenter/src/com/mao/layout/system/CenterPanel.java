package com.mao.layout.system;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.mao.bean.User;
import com.mao.layout.resource.MarketDetail;
import com.mao.layout.resource.MarketInfoLayout;
import com.mao.layout.resource.MarketStatistic;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class CenterPanel extends VerticalLayout{

	public void createMenu(User user) {
		setSizeFull();
		TabSheet sheet=new TabSheet();
		sheet.setSizeFull();
		
		try {
			sheet.addTab(new MarketInfoLayout());
			sheet.addTab(new MarketStatistic());
			sheet.addTab(new MarketDetail());
			addComponent(sheet);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
