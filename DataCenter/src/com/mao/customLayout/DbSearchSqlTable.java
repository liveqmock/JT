package com.mao.customLayout;

import java.io.File;

import com.mao.customLayout.bean.DbSearch;
import com.mao.tool.Datasource;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
public class DbSearchSqlTable extends VerticalLayout implements ClickListener {
	private Table table=new Table();
	private Button exportBt=new Button("����");
	private String caption="��ѯ���";
	private TextField pageField;
	private int pageSize=100;
	private TextField pageNoField;

	private DbSearch dbSearch;
	
	public DbSearchSqlTable(DbSearch dbSearch) {
		super();
		this.dbSearch=dbSearch;

		table.setSizeFull();
		table.setColumnCollapsingAllowed(true);
		table.setSelectable(true);
		table.addStyleName("small striped");
		table.setImmediate(true);
		HorizontalLayout header=new HorizontalLayout();

		Label captionLabel = new  Label(caption+":");
		header.addComponent(captionLabel);
		header.addComponent(exportBt);
		header.setComponentAlignment(exportBt, Alignment.BOTTOM_RIGHT);
		header.setComponentAlignment(captionLabel, Alignment.BOTTOM_LEFT);
		header.setWidth("100%");


		HorizontalLayout pageBar=new HorizontalLayout();
		final Button firstBt=new Button("��ҳ");

		final Button preBt=new Button("��һҳ");

		final Button nextBt=new Button("��һҳ");

		final Button lastBt=new Button("ĩҳ");

		Button goBt=new Button("go");

		firstBt.addClickListener(this);
		preBt.addClickListener(this);
		nextBt.addClickListener(this);
		lastBt.addClickListener(this);
		goBt.addClickListener(this);

		pageNoField=new TextField();
		pageNoField.setImmediate(true);
		pageNoField.addValueChangeListener(new ValueChangeListener() {			
			@Override
			public void valueChange(ValueChangeEvent event) {	
				int pageNo =Integer.valueOf(pageNoField.getValue());
				int pages = table.getContainerDataSource().size()/table.getPageLength()+1;
				
				if(pageNo==1){
					firstBt.setEnabled(false)	;	
					preBt.setEnabled(false)	;	
				}else{

					firstBt.setEnabled(true)	;	
					preBt.setEnabled(true)	;	
				}
				if(pageNo==pages){
					nextBt.setEnabled(false)	;	
					lastBt.setEnabled(false)	;

				}else{
					nextBt.setEnabled(true)	;	
					lastBt.setEnabled(true)	;
				}
			}			
		});

		pageField=new TextField();
		pageField.setWidth("50px");
		pageField.setHeight("100%");
		pageField.setImmediate(true);

		pageField.setPropertyDataSource(new ObjectProperty<Integer>(0,Integer.class));

		pageField.addValueChangeListener(new ValueChangeListener() {


			@Override
			public void valueChange(ValueChangeEvent event) {
				try{
					pageField.validate();

				}catch(Exception exception){
				}
			}
		});

		pageBar.addComponent(firstBt);
		pageBar.addComponent(preBt);
		pageBar.addComponent(pageNoField);
		pageBar.setComponentAlignment(pageNoField,Alignment.MIDDLE_RIGHT);
		pageBar.addComponent(nextBt);
		pageBar.addComponent(lastBt);
		Label goLabel = new Label("��ת����");
		pageBar.addComponent(goLabel);
		pageBar.setComponentAlignment(goLabel,Alignment.MIDDLE_RIGHT);
		pageBar.addComponent(pageField);
		pageBar.addComponent(goBt);
		pageField.setInputPrompt("ָ��ҳ��");
		exportBt.setIcon(new ThemeResource("img/exp.png"));
		addComponent(header);
		addComponent(table);
		addComponent(pageBar);
		setExpandRatio(table, 1.0f);
		
		FileDownloader downloader=new FileDownloader(new FileResource(new File("export.xls"))){

			@Override
			public boolean handleConnectorRequest(VaadinRequest request,
					VaadinResponse response, String path)  {
				try{
				setFileDownloadResource(TableExcelExport.getResouce(table,caption));
				return super.handleConnectorRequest(request, response, path);
				}catch (Exception e){
					e.printStackTrace();
					Notification.show("����ִ�С���ѯ�����õ���ѯ���");
					return false;
				}
			}
			
		};

		downloader .extend(exportBt);
	



	}
	public void executeQuery() throws Exception{
		this.dbSearch=dbSearch;
		DbSearchSqlContainer container=new DbSearchSqlContainer(Datasource.getDataSource(dbSearch.getDatabase()).getConnection(),dbSearch,pageSize);
		table.setContainerDataSource(container);
		table.setColumnCollapsed("ROWNUM",true);
		table.setColumnCollapsible("ROWNUM",true);
		pageField.removeAllValidators();
		pageField.addValidator(new IntegerRangeValidator("�������0��������",1,Integer.MAX_VALUE));
		pageNoField.setReadOnly(false);
		pageNoField.setValue("1");
		pageNoField.setReadOnly(true);
		container.addPageChangeListener(new PageChangeListener() {

			@Override
			public void pageChange(PageEvent e) {
				int pageNo = (table.getCurrentPageFirstItemIndex()+1)/table.getPageLength()+1;
				
				if(table.getCurrentPageFirstItemIndex()+table.getPageLength()==table.getContainerDataSource().size())
					pageNo=table.getContainerDataSource().size()/table.getPageLength()+1;
				pageNoField.setReadOnly(false);
				pageNoField.setValue(String.valueOf(pageNo));
				pageNoField.setReadOnly(true);

			}
		});

	}
		
		
		
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		int pages = table.getContainerDataSource().size()/table.getPageLength()+1;
		if(event.getButton().getCaption().equals("��ҳ")){
			table.setCurrentPageFirstItemIndex(0);
		}else if(event.getButton().getCaption().equals("��һҳ")){
			Integer pageNo = Integer.valueOf(pageNoField.getValue());
			if(pageNo>1)
				table.setCurrentPageFirstItemIndex((pageNo-2)*table.getPageLength()-1);
		}else if(event.getButton().getCaption().equals("��һҳ")){
			Integer pageNo = Integer.valueOf(pageNoField.getValue());
			if(pageNo<pages)
				table.setCurrentPageFirstItemIndex(pageNo*table.getPageLength()-1);
		}else if(event.getButton().getCaption().equals("ĩҳ")){
			table.setCurrentPageFirstItemIndex((pages-1)*table.getPageLength()-1);
		}else if(event.getButton().getCaption().equals("go")){
			int pageNo = Integer.valueOf(pageField.getValue());
			if(pageNo>=pages){
				pageField.setValue(String.valueOf(pages));
				pageNo=pages;
			}
			table.setCurrentPageFirstItemIndex((pageNo-1)*table.getPageLength()-1);

		}

	}
	
	


}
