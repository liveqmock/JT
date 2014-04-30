package ui.menu;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.Userman;

public class BillPopMenu extends JPopupMenu {
		public BillPopMenu(BeanTablePane<BillBean> table) {
			super();
			BillAction action = new BillAction(table);
			if(Userman.loginUser.getMenus().contains("新建订单"))
				add("新建订单").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("查看订单组"))
				add("查看订单组").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("修改订单"))
				add("修改订单").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("添加图纸"))
				add("添加图纸").addActionListener(action);
			addSeparator();
			if(Userman.loginUser.getMenus().contains("删除订单"))
				add("删除订单").addActionListener(action);	
			if(Userman.loginUser.getMenus().contains("取消订单"))	
				add("取消订单").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("标记颜色")){
				JMenu colorMenu=new JMenu("标记颜色");
				addSeparator();
				add(colorMenu);
				colorMenu.add("蓝色").addActionListener(action);
				colorMenu.add("绿色").addActionListener(action);
				colorMenu.add("橙色").addActionListener(action);
				colorMenu.add("去色").addActionListener(action);	
			}
			addSeparator();
			if(Userman.loginUser.getMenus().contains("添加发票信息"))	
				add("添加发票信息").addActionListener(action);

		}
	

}

