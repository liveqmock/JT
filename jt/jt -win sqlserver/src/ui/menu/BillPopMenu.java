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
			add("�½�����").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("ͳ��Ա")) {
			add("�鿴������").addActionListener(action);
			add("�޸Ķ���").addActionListener(action);
			
			addSeparator();		
			add("�Ų��ƻ�").addActionListener(action);
			add("�����ɹ�").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
			addSeparator();
			add("ɾ������").addActionListener(action);
			addSeparator();
			add("��������").addActionListener(action);
			addSeparator();
			JMenu colorMenu=new JMenu("�����ɫ");
			add(colorMenu);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("ȥɫ").addActionListener(action);			
			add("ȡ������").addActionListener(action);
		}

		addSeparator();
		add("��ӷ�Ʊ��Ϣ").addActionListener(action);
	}

}
