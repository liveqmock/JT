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
			add("�½�����").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("ͳ��Ա")) {
			add("�鿴������").addActionListener(action);
			add("�޸Ķ���").addActionListener(action);
			add("��������").addActionListener(action);
			
			addSeparator();		
			add("�Ų��ƻ�").addActionListener(action);
			add("�����ɹ�").addActionListener(action);
			add("�������Ϲ���").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
			addSeparator();
			add("ɾ��ͼֽ").addActionListener(action);
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
		}else if(Userman.loginUser.getLevelStr().equals("�ֿ����Ա")) {
			add("�������Ϲ���").addActionListener(action);
			
		}

		addSeparator();
		add("��ӷ�����Ϣ").addActionListener(action);
		add("��ӷ�Ʊ��Ϣ").addActionListener(action);
	}
	

}
