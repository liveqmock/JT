package ui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.Userman;

public class PicPopmenu extends JPopupMenu {
	public PicPopmenu(BeanTablePane<PicBean> table ) {
		super();
	    PicAction action=new PicAction(table);
		if (Userman.loginUser.isManager())
			add("新建订单").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("统计员")) {
			add("查看订单组").addActionListener(action);
			add("修改订单").addActionListener(action);
			add("订单返修").addActionListener(action);
			
			addSeparator();		
			add("排产计划").addActionListener(action);
			add("订单派工").addActionListener(action);
			add("生产材料管理").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
			addSeparator();
			add("删除图纸").addActionListener(action);
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
		}else if(Userman.loginUser.getLevelStr().equals("仓库管理员")) {
			add("生产材料管理").addActionListener(action);
			
		}

		addSeparator();
		add("添加发货信息").addActionListener(action);
		add("添加发票信息").addActionListener(action);
	}
	

}
