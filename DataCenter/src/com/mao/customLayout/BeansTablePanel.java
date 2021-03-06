package com.mao.customLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class BeansTablePanel<T> extends VerticalLayout {
	private BeansTable<T> table;
	private Button exportBt=new Button("����");
	private Collection<T> beans;
	private String caption;
	public BeansTablePanel(Class<T> class1,Collection<T> beans) {
		this(class1,beans,true);
	}
	public BeansTablePanel(Class<T> class1,Collection<T> beans,boolean showExport) {
		super();
		this.beans=beans;
		table=new BeansTable<T>(class1,beans);
		table.setSizeFull();
		table.setImmediate(true);
		table.addStyleName("small striped");
		table.setColumnCollapsingAllowed(true);
		table.setSelectable(true);
		table.setMultiSelect(false);
		if(showExport){
		HorizontalLayout header=new HorizontalLayout();
		if(caption!=null){
			Label captionLabel = new  Label(caption+":");
			header.addComponent(captionLabel);
			header.setComponentAlignment(captionLabel, Alignment.BOTTOM_LEFT);
		}
		
		header.addComponent(exportBt);
		header.setComponentAlignment(exportBt, Alignment.BOTTOM_RIGHT);
		header.setWidth("100%");

		exportBt.setIcon(new ThemeResource("img/exp.png"));

		addComponent(header);
		}else{
			setCaption(caption);
		}
		addComponent(table);
		setExpandRatio(table, 1.0f);
		
		
	}
	
	
	
	public void dowloadExl() {

		final StreamResource resource = new StreamResource(new StreamResource.StreamSource() {

			@Override
			public InputStream getStream() {

				ByteArrayOutputStream os=new ByteArrayOutputStream();
				ExcelExport.beanExport(beans);



				ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
				return in;

			}
		},"search.xls");
		resource.setCacheTime(0);
		FileDownloader downloader=new FileDownloader(resource);

		downloader.extend(exportBt);
	}
	public Table getTable() {
		return table;
	}
	public Collection<T> getBeans() {
		return beans;
	}
	public void setBeans(Collection<T> beans) {
		this.beans = beans;
		table.setBeans(beans);
	}
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
		getTable().setCaption(caption);
	}
	

}
