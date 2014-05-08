package ui.menu;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.Userman;

public class PicPopmenu extends JPopupMenu {
	public PicPopmenu(BeanTablePane<PicBean> table ) {
		super();
		PicAction action=new PicAction(table);
		if(Userman.loginUser.getMenus().contains("新建订单"))
			add("新建订单").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("修改订单"))
			add("修改订单").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("修改图纸"))
			add("修改图纸").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("检验数据"))
			add("检验数据").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("查看图纸"))
			add("删除图纸").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("返修"))
			add("返修").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("标志此图纸已完结"))
			add("标志此图纸已完结").addActionListener(action);
		
		addSeparator();		
		if(Userman.loginUser.getMenus().contains("生产材料管理"))
			add("生产材料管理").addActionListener(action);
		addSeparator();
		if(Userman.loginUser.getMenus().contains("标记颜色")){
			JMenu colorMenu=new JMenu("标记颜色");
			add(colorMenu);
			colorMenu.add("蓝色").addActionListener(action);
			colorMenu.add("绿色").addActionListener(action);
			colorMenu.add("橙色").addActionListener(action);
			colorMenu.add("去色").addActionListener(action);		
		}

		addSeparator();
		if(Userman.loginUser.getMenus().contains("特采设定"))
			add("特采设定").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("添加发货信息"))
			add("添加发货信息").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("添加发票信息"))
			add("添加发票信息").addActionListener(action);
	}


}
