package com.mao.bean;

import java.util.TreeSet;

public class MenuBean  implements Comparable<MenuBean>{
	private Resource resource;
	private TreeSet<MenuBean> childs=new TreeSet<>();
	private boolean parentFlag;
	
	public MenuBean(Resource resource) {
		this.resource=resource;
		
	}

//	public static TreeMap<String, MenuBean> load() {
//		Statement st=null;
//		TreeMap<String, MenuBean> menuMap=new TreeMap<String, MenuBean>();
//				
//		try {
//			st = Database.getConnection().createStatement();
//			ResultSet rs = st.executeQuery("Select * from DWMM.U_RESOURCE order by PARENT_ID,iorder");
//			while(rs.next()){
//				MenuBean menu=new MenuBean();
//				menu.setId(rs.getString("RESOURCE_ID"));
//				menu.setParent_id(rs.getString("PARENT_ID"));
//				menu.setCaption(rs.getString("RESOURCE_NAME"));
//				menu.setActionDes(rs.getString("RESOURCE_URL"));
//				menuMap.put(menu.getId(), menu);
//			}
//			rs.close();
//			st.close();
//			for(MenuBean menu:menuMap.values()){
//				if(menu.getParent_id()!=null){
//					MenuBean parentMenu = menuMap.get(menu.getParent_id());
//					if(menuMap!=null){
//						menu.setParent(parentMenu);
//						parentMenu.setParentFlag(true);
//					}
//				}
//
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} catch (NamingException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}finally{
//			try{
//				st.close();
//			}catch(Exception e){
//
//			}
//		}
//		return menuMap;
//	}

//	public static Accordion createMenuWithAccordion(Vector<Menu> menus) {
//		Accordion accordion=new Accordion();
//		accordion.setWidth("300px");
//		for(Menu menu:menus){
//			VerticalLayout layout=new VerticalLayout();
//			if(menu.getMenus()!=null&&menu.getMenus().size()>0){
//				layout.addComponent(createMenuWithAccordion(menu.getMenus()));
//			}
//			if(menu.getItems()!=null&&menu.getItems().size()>0){
//				for(MenuItem item:menu.getItems()){
//
//					Button button=new Button(item.getCaption());
//					button.setData(item.getClass());
//					button.setWidth("100%");
//					layout.addComponent(button);
//				}
//			}
//
//			accordion.addTab(layout, menu.getCaption());
//		}
//		return accordion;
//	}

	
//	this.actionDes=resource.getResourceUrl();
//	this.caption=resource.getResourceName();
//	this.id=resource.getResourceId();
//	this.parentResource=resource.getParent();

	public String getCaption() {
		return resource.getResourceName();
	}
	
	public int getId() {
		return resource.getId();
	}
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public boolean isParentFlag() {
		return parentFlag;
	}
	public boolean setParentFlag(boolean parentFlag) {
		return this.parentFlag=parentFlag;
	}
	public Resource getParentResource() {
		return resource.getParentRe();
	}

	
	@Override
	public String toString() {
		return resource.getResourceName();
	}


	public TreeSet<MenuBean> getChilds() {
		return childs;
	}

	public void setChilds(TreeSet<MenuBean> childs) {
		this.childs = childs;
	}

	@Override
	public int compareTo(MenuBean o) {
		// TODO 自动生成的方法存根
		return resource.compareTo(o.getResource());
	}



}
