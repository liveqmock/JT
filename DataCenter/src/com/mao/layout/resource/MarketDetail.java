package com.mao.layout.resource;

import java.util.List;

import com.mao.bean.BusinessMarket;
import com.mao.bean.MarketInfo;
import com.mao.customLayout.BeansTable;
import com.mao.tool.BeanManager;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class MarketDetail extends VerticalLayout {
	private Label label;
	private DateField startDateField;
	private DateField endDateField;
	private BeansTable<MarketInfo> table;
	public MarketDetail() {
		super();
		setCaption("营销明细");
		setSizeFull();
		FormLayout formLayout=new FormLayout();
		startDateField=new DateField("起始时间");
		endDateField=new DateField("终止时间");
		startDateField.setRequired(true);
		endDateField.setRequired(true);
		Button button=new Button("查询");
		button.setClickShortcut(KeyCode.ENTER);
		button.addStyleName(Reindeer.BUTTON_DEFAULT);
		
		label=new Label("查询结果：",ContentMode.HTML);
		table=new BeansTable<MarketInfo>(MarketInfo.class, null);
		table.setSizeFull();
		formLayout.addComponent(startDateField);
		formLayout.addComponent(endDateField);

		formLayout.addComponent(button);
		addComponent(formLayout);
		addComponent(label);
		addComponent(table);
		
		setExpandRatio(table,1.0f);
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				List<MarketInfo> beans = BeanManager.BM.getBeans(MarketInfo.class, " where marketDate between ?1 and ?2",startDateField.getValue(),endDateField.getValue());
				table.setBeans(beans);
			}
		});
		
		

	}

}
