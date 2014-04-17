package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import ui.customComponet.BeanTablePane;
import ui.panels.BillManagerPnl;
import ui.tables.BillTable;
import ui.tables.PicTable;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.Userman;

public class BillPopMenu extends JPopupMenu {
	public BillPopMenu(BeanTablePane<BillBean> table) {
		super();
		BillAction action = new BillAction(table);
		if (Userman.loginUser.isManager())
			add("新建订单").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("统计员")) {
			add("查看订单组").addActionListener(action);
			add("修改订单").addActionListener(action);
			
			addSeparator();		
			add("排产计划").addActionListener(action);
			add("订单派工").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
			addSeparator();
			add("删除订单").addActionListener(action);
			addSeparator();
			add("导出订单").addActionListener(action);
			addSeparator();
			JMenu colorMenu=new JMenu("标记颜色");
			add(colorMenu);
			colorMenu.add("蓝色").addActionListener(action);
			colorMenu.add("绿色").addActionListener(action);
			colorMenu.add("橙色").addActionListener(action);
			colorMenu.add("去色").addActionListener(action);			
			add("取消订单").addActionListener(action);
		}

		addSeparator();
		add("添加发票信息").addActionListener(action);
	}

}
