package com.mao.layout.resource;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.mao.bean.Resource;
import com.mao.customLayout.BeansEditPanel;
import com.mao.layout.system.Root;
import com.mao.tool.BeanManager;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Layout;

public class SystemResource  extends BeansEditPanel<Resource>{
	private ComboBox layoutSelect;
	public SystemResource() throws ClassNotFoundException, SQLException, NamingException, Exception {
		super(Resource.class,BeanManager.BM.getBeans( Resource.class),new Resource());

		layoutSelect = new ComboBox();
		layoutInit();
		ComboBox statusSelect = new ComboBox();
		statusSelect.addItem(0);
		statusSelect.addItem(1);
		statusSelect.setItemCaption(0, "启用");
		statusSelect.setItemCaption(1, "停用");
		ComboBox parentSelect = new ComboBox();
		for(Resource resource:getBeans()){
			if(resource.getResourcePnl()==null||resource.getResourcePnl().equals("")){
				parentSelect.addItem(resource);
			}
		}

		getForm().binder(statusSelect, "status");
		getForm().binder(parentSelect, "parentRe");
		getForm().binder(layoutSelect, "resourcePnl");
		getForm().autoBinder();
	}

	public static void getClassFile(File file,String path,ArrayList<String> classes) {
		if(file.isDirectory()){
			for(File newFile:file.listFiles())
				getClassFile(newFile,path+file.getName()+".",classes);
		}else{
			if(file.getName().endsWith(".class")){
				String name = path+file.getName().substring(0,file.getName().length()-6);
				classes.add(name);
			}
		}
	}
	public void layoutInit() {
		ArrayList<String> classNames = new ArrayList<String>();

		for(File file:new File(Root.class.getResource("").getFile())
		.getParentFile()
		.getParentFile()
		.getParentFile()
		.getParentFile()
		.listFiles()){

			getClassFile(file, "", classNames );
		}
		for(String className:classNames)
			try{
				Class.forName(className).asSubclass(Layout.class);

				layoutSelect.addItem(className);

			}catch (Exception e) {
			}
		try {
			for(File file:new File( VaadinSession.getCurrent().getService().getBaseDirectory().getCanonicalPath()+"/ds").listFiles())
				layoutSelect.addItem(file.getName());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
