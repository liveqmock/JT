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
		if(Userman.loginUser.getMenus().contains("�½�����"))
			add("�½�����").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�޸Ķ���"))
			add("�޸Ķ���").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�޸�ͼֽ"))
			add("�޸�ͼֽ").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��������"))
			add("��������").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�鿴ͼֽ"))
			add("ɾ��ͼֽ").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("����"))
			add("����").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��־��ͼֽ�����"))
			add("��־��ͼֽ�����").addActionListener(action);
		
		addSeparator();		
		if(Userman.loginUser.getMenus().contains("�������Ϲ���"))
			add("�������Ϲ���").addActionListener(action);
		addSeparator();
		if(Userman.loginUser.getMenus().contains("�����ɫ")){
			JMenu colorMenu=new JMenu("�����ɫ");
			add(colorMenu);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("ȥɫ").addActionListener(action);		
		}

		addSeparator();
		if(Userman.loginUser.getMenus().contains("�ز��趨"))
			add("�ز��趨").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��ӷ�����Ϣ"))
			add("��ӷ�����Ϣ").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��ӷ�Ʊ��Ϣ"))
			add("��ӷ�Ʊ��Ϣ").addActionListener(action);
	}


}
