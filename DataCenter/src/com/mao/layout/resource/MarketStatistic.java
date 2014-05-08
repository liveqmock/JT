package com.mao.layout.resource;

import com.mao.bean.BusinessMarket;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class MarketStatistic extends VerticalLayout {
	private Label label;
	private DateField startDateField;
	private DateField endDateField;
	public MarketStatistic() {
		super();
		setSizeFull();
		setCaption("营销业绩");
		FormLayout formLayout=new FormLayout();
		startDateField=new DateField("起始时间");
		endDateField=new DateField("终止时间");
		startDateField.setRequired(true);
		endDateField.setRequired(true);
		Button button=new Button("查询");
		button.setClickShortcut(KeyCode.ENTER);
		button.addStyleName(Reindeer.BUTTON_DEFAULT);
		
		label=new Label("查询结果：",ContentMode.HTML);
		formLayout.addComponent(startDateField);
		formLayout.addComponent(endDateField);

		formLayout.addComponent(button);
		Panel panel=new Panel();
		panel.setContent(label);
		panel.setSizeFull();
		addComponent(formLayout);
		addComponent(panel);
		setExpandRatio(panel, 1.0f);
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				label.setValue(BusinessMarket.getStaticHtml(startDateField.getValue(),endDateField.getValue()));
				
			}
		});
		
		

	}

}
