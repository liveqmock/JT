package com.mao.customLayout;

import java.io.File;
import java.util.Collection;
import java.util.List;

import com.mao.customLayout.bean.DbSearch;
import com.mao.tool.BeanManager;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class DbSearchJpaTable extends VerticalLayout {
	private BeansTable<?> table;
	private Button exportBt=new Button("导出");
	private String caption="查询结果";
	private DbSearch dbSearch;

	public DbSearchJpaTable(DbSearch dbSearch) {
		super();
		this.dbSearch=dbSearch;
		
		table=new BeansTable(dbSearch.getBeanClass(), null);
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



		exportBt.setIcon(new ThemeResource("img/exp.png"));
		addComponent(header);
		addComponent(table);
		setExpandRatio(table, 1.0f);

		FileDownloader downloader=new FileDownloader(new FileResource(new File(""))){

			@Override
			public boolean handleConnectorRequest(VaadinRequest request,
					VaadinResponse response, String path)  {
				try{
					setFileDownloadResource(TableExcelExport.getResouce(table,caption));
					return super.handleConnectorRequest(request, response, path);
				}catch (Exception e){
					Notification.show("请先执行【查询】，得到查询结果");
					return false;
				}
			}

		};

		downloader .extend(exportBt);




	}
	public void executeQuery() throws Exception{
		this.dbSearch=dbSearch;
		List beans = BeanManager.BM.getBeans(dbSearch.getBeanClass(), dbSearch.getWhereHqlString(),dbSearch.getJpaParms().toArray());
		table.setBeans((Collection) beans);


	}



	public Table getTable() {
		return table;
	}


}
