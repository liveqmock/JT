package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.mao.jf.beans.Userman;

public class MainMenu extends JMenuBar {

	public MainMenu() {
		super();
		setBillMenus();





	}

	public   void setBillMenus() {
		MenuAction action = new MenuAction();
		JMenu menu;

		menu = new JMenu("��������");
		add(menu);
		if(Userman.loginUser.getMenus().contains("�½�����"))
			menu.add("�½�����").addActionListener(action);

		menu = new JMenu("ͳ��");
		add(menu);
		if(Userman.loginUser.getMenus().contains("�¶�ͳ��"))
			menu.add("�¶�ͳ��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("���ͳ��"))
			menu.add("���ͳ��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("Ա������ͳ��"))
			menu.add("Ա������ͳ��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�������ͳ��"))
			menu.add("�������ͳ��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("���������"))
			menu.add("���������").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�鿴�豸ʹ�����"))
			menu.add("�鿴�豸ʹ�����").addActionListener(action);
		menu = new JMenu("�ͻ�����");
		add(menu);
		if(Userman.loginUser.getMenus().contains("�����ͻ�����"))
			menu.add("�����ͻ�����").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��Э�ͻ�����"))
			menu.add("��Э�ͻ�����").addActionListener(action);

		menu = new JMenu("�ɱ�����");
		add(menu);
		if(Userman.loginUser.getMenus().contains("�Ų��ƻ�¼��"))
			menu.add("�Ų��ƻ�¼��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("��������¼��"))
			menu.add("��������¼��").addActionListener(action);
		//			menu.add("�������Ϲ���").addActionListener(action);
		menu.addSeparator();
		
		if(Userman.loginUser.getMenus().contains("�鿴�Ų��ƻ�"))
			menu.add("�鿴δ��ʼ���Ų��ƻ�").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�Ų�ʱ��鿴"))
			menu.add("�Ų�ʱ��鿴").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�����ɱ�ͳ��"))
			menu.add("�����ɱ�ͳ��").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("�����ƻ���ʵ�ʳɱ�����"))
			menu.add("�����ƻ���ʵ�ʳɱ�����").addActionListener(action);
		menu.addSeparator();
		if(Userman.loginUser.getMenus().contains("�豸����"))
			menu.add("�豸����").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("������Ա����"))
			menu.add("������Ա����").addActionListener(action);
		menu = new JMenu("�û�����");
		add(menu);
		if(Userman.loginUser.getMenus().contains("�û�����"))
			menu.add("�û�����").addActionListener(action);
		menu.add("�޸�����").addActionListener(action);


		menu = new JMenu("����");
		add(menu);

		menu.add("����").addActionListener(action);
	}


	//	public   void setBillMenus() {
	//
	//		MenuAction action = new MenuAction();
	//		JMenu menu;
	//		menu = new JMenu("��������");
	//		add(menu);
	//		menu.add("�½�����").addActionListener(action);
	//
	//		menu = new JMenu("ͳ��");
	//		add(menu);
	//		menu.add("�¶�ͳ��").addActionListener(action);
	//		menu.add("���ͳ��").addActionListener(action);
	//		menu.add("Ա������ͳ��").addActionListener(action);
	//		menu.add("�鿴�豸ʹ�����").addActionListener(action);
	//		menu = new JMenu("�ͻ�����");
	//		add(menu);
	//		menu.add("�����ͻ�����").addActionListener(action);
	//		menu.add("��Э�ͻ�����").addActionListener(action);
	//
	//		menu = new JMenu("�ɱ�����");
	//		add(menu);
	//		menu.add("�Ų��ƻ�¼��").addActionListener(action);
	//		menu.add("��������¼��").addActionListener(action);
	//		menu.addSeparator();
	//		menu.add("�鿴δ��ʼ���Ų��ƻ�").addActionListener(action);
	//		menu.add("Ա������ͳ��").addActionListener(action);
	//		menu.add("�������ͳ��").addActionListener(action);
	//		menu.add("�Ų�ʱ��鿴").addActionListener(action);
	//		menu.add("�����ɱ�ͳ��").addActionListener(action);
	//		menu.add("�����ƻ���ʵ�ʳɱ�����").addActionListener(action);
	//		menu.addSeparator();
	//		menu.add("������ù���").addActionListener(action);
	//		menu.add("������Ա����").addActionListener(action);
	//		
	//		menu = new JMenu("�û�����");
	//		add(menu);
	//		menu.add("�û�����").addActionListener(action);
	//		menu.add("�޸�����").addActionListener(action);
	//
	//
	//		menu = new JMenu("����");
	//		add(menu);
	//
	//		menu.add("����").addActionListener(action);
	//	}

}
