package ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import ui.panels.BillManagerPnl;
import ui.tables.BillTable;
import ui.tables.PicTable;

import com.mao.jf.beans.Userman;

public class MainMenu extends JMenuBar {

	public MainMenu() {
		super();
		setBillMenus();





	}

	public   void setBillMenus() {

		MenuAction action = new MenuAction();
		JMenu menu;
		if (Userman.loginUser.isManager()){

			menu = new JMenu("��������");
			add(menu);
			menu.add("�½�����").addActionListener(action);
				
			menu = new JMenu("ͳ��");
			add(menu);
			menu.add("�¶�ͳ��").addActionListener(action);
			menu.add("���ͳ��").addActionListener(action);
			menu.add("Ա������ͳ��").addActionListener(action);
			menu.add("�鿴�豸ʹ�����").addActionListener(action);
			menu = new JMenu("�ͻ�����");
			add(menu);
			menu.add("�����ͻ�����").addActionListener(action);
			menu.add("��Э�ͻ�����").addActionListener(action);

			menu = new JMenu("�ɱ�����");
			add(menu);
			menu.add("�Ų��ƻ�¼��").addActionListener(action);
			menu.add("��������¼��").addActionListener(action);
//			menu.add("�������Ϲ���").addActionListener(action);
			menu.addSeparator();
			menu.add("�鿴δ��ʼ���Ų��ƻ�").addActionListener(action);
			menu.add("Ա������ͳ��").addActionListener(action);
			menu.add("�������ͳ��").addActionListener(action);
			menu.add("�Ų�ʱ��鿴").addActionListener(action);
			menu.add("�����ɱ�ͳ��").addActionListener(action);
			menu.add("�����ƻ���ʵ�ʳɱ�����").addActionListener(action);
			menu.addSeparator();
			menu.add("������ù���").addActionListener(action);
			menu.add("������Ա����").addActionListener(action);
			menu = new JMenu("�û�����");
			add(menu);
			menu.add("�û�����").addActionListener(action);
			menu.add("�޸�����").addActionListener(action);
		}else if(Userman.loginUser.getLevelStr().equals("ͳ��Ա")){

			menu = new JMenu("�ͻ�����");
			add(menu);
			menu.add("�����ͻ�����").addActionListener(action);
			menu.add("��Э�ͻ�����").addActionListener(action);

			menu = new JMenu("�ɱ�����");
			add(menu);
			//			menu.add("�Ų��ƻ�¼��").addActionListener(action);
			menu.add("��������¼��").addActionListener(action);
			menu.add("Ա������ͳ��").addActionListener(action);
			//			menu.add("�������Ϲ���").addActionListener(action);

			menu = new JMenu("�û�����");
			add(menu);
			menu.add("�޸�����").addActionListener(action);
		}else if(Userman.loginUser.getLevelStr().equals("�ƻ��ɹ�")){
			menu = new JMenu("�ɱ�����");
			add(menu);
			menu.add("�Ų��ƻ�¼��").addActionListener(action);
			menu.add("�鿴δ��ʼ���Ų��ƻ�").addActionListener(action);
			menu.add("�����ƻ���ʵ�ʳɱ�����").addActionListener(action);
			menu = new JMenu("�û�����");
			add(menu);
			menu.add("�޸�����").addActionListener(action);
		}
		else if(Userman.loginUser.getLevelStr().equals("�ֿ����Ա")){
			menu = new JMenu("�ɱ�����");
			add(menu);
//			menu.add("�������Ϲ���").addActionListener(action);
			menu = new JMenu("�û�����");
			add(menu);
			menu.add("�޸�����").addActionListener(action);
		}

		menu = new JMenu("����");
		add(menu);

		menu.add("����").addActionListener(action);
	}


}
