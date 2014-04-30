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
			if(Userman.loginUser.getMenus().contains("�½�����"))
				add("�½�����").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("�鿴������"))
				add("�鿴������").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("�޸Ķ���"))
				add("�޸Ķ���").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("���ͼֽ"))
				add("���ͼֽ").addActionListener(action);
			addSeparator();
			if(Userman.loginUser.getMenus().contains("ɾ������"))
				add("ɾ������").addActionListener(action);	
			if(Userman.loginUser.getMenus().contains("ȡ������"))	
				add("ȡ������").addActionListener(action);
			if(Userman.loginUser.getMenus().contains("�����ɫ")){
				JMenu colorMenu=new JMenu("�����ɫ");
				addSeparator();
				add(colorMenu);
				colorMenu.add("��ɫ").addActionListener(action);
				colorMenu.add("��ɫ").addActionListener(action);
				colorMenu.add("��ɫ").addActionListener(action);
				colorMenu.add("ȥɫ").addActionListener(action);	
			}
			addSeparator();
			if(Userman.loginUser.getMenus().contains("��ӷ�Ʊ��Ϣ"))	
				add("��ӷ�Ʊ��Ϣ").addActionListener(action);

		}
	

}

