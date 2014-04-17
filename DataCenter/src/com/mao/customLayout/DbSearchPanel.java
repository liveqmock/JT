package com.mao.customLayout;


import com.mao.customLayout.bean.DbSearch;
import com.mao.tool.MaoLogger;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class DbSearchPanel extends VerticalLayout {

	protected DbSearchTable tableLayout;
	private HorizontalLayout btBar;
	protected DbSearchForm form;
	public DbSearchPanel(DbSearch dbSearch) {
		super();
		form=new DbSearchForm(dbSearch,2);
		createContent();
	}
	public DbSearchPanel(DbSearch dbSearch,int columnNum) {
		super();
		form=new DbSearchForm(dbSearch,columnNum);
		createContent();
	}
	public Table getTable() {
		return tableLayout.getTable();
	}
	private void createContent(){
		initBar();
		initTable();


		
		VerticalLayout formLayout=new VerticalLayout();
		formLayout.setStyleName("form");
		formLayout.addComponent(form);
		formLayout.addComponent(btBar);
		formLayout.setSizeUndefined();
		formLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		formLayout.setComponentAlignment(btBar, Alignment.MIDDLE_CENTER);
		
		addComponent(formLayout);
		addComponent(tableLayout);
		setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		setExpandRatio(tableLayout, 1.0f);
		setSizeFull();
	}
	private void initTable() {
		tableLayout=new DbSearchTable("历史记录查询结果");
		tableLayout.setSizeFull();

	}
	private void initBar() {
		btBar=new HorizontalLayout();
		btBar.setMargin(true);
		btBar.setWidth("400px");
		Button searchBt=new Button("查询(S)");
		Button cancelBt=new Button("重置(C)");
		searchBt.setClickShortcut('s',ModifierKey.ALT);
		cancelBt.setClickShortcut('c',ModifierKey.ALT);
		
		searchBt.setIcon(new ThemeResource("img/search.png"));
		cancelBt.setIcon(new ThemeResource("img/reset.png"));
		btBar.addComponent(searchBt);
		btBar.addComponent(cancelBt);
		btBar.setComponentAlignment(searchBt, Alignment.MIDDLE_CENTER);
		btBar.setComponentAlignment(cancelBt, Alignment.MIDDLE_CENTER);
		
		searchBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					form.commit();
					tableLayout.executeQuery(form.getDbSearch());
					//						tableLayout.setSqlString("select * from dwmm.JOB_LOG");
				} catch (CommitException e) {
					e.printStackTrace();
					Notification.show("表单中有不符要求的值",Notification.Type.ERROR_MESSAGE);
				} catch (Exception e) {
					Notification.show("提交失败",Notification.Type.ERROR_MESSAGE);
					MaoLogger.error("提交失败",e);
				}


			}
		});
		cancelBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				form.cancel();
			}
		});
	}

}
