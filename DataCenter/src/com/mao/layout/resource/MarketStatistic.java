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
		setCaption("Ӫ��ҵ��");
		FormLayout formLayout=new FormLayout();
		startDateField=new DateField("��ʼʱ��");
		endDateField=new DateField("��ֹʱ��");
		startDateField.setRequired(true);
		endDateField.setRequired(true);
		Button button=new Button("��ѯ");
		button.setClickShortcut(KeyCode.ENTER);
		button.addStyleName(Reindeer.BUTTON_DEFAULT);
		
		label=new Label("��ѯ�����",ContentMode.HTML);
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
