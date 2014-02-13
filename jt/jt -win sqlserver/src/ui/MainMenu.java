package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import ui.tables.BillTable;

import com.mao.jf.beans.Userman;

public class MainMenu extends JMenuBar {
	@SuppressWarnings("unused")
	private BillTable table;

	public MainMenu(BillTable table) {
		super();
		this.table = table;
		setBillMenus();





	}

	public   void setBillMenus() {

		MenuAction action = new MenuAction(table);
		JMenu menu;
		if (Userman.loginUser.isManager()){

			menu = new JMenu("��������");
			add(menu);
			menu.add("�½�����").addActionListener(action);
			menu.add("�޸Ķ���").addActionListener(action);
			menu.add("ɾ������").addActionListener(action);		
			menu.add("��������").addActionListener(action);	
			menu.add("�鿴������").addActionListener(action);
			menu.addSeparator();
			menu.add("������ʾ�Ķ���").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("�����ɫ");
			menu.add(colorMenu);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("ȥɫ").addActionListener(action);

			menu = new JMenu("ͳ��");
			add(menu);
			menu.add("�¶�ͳ��").addActionListener(action);
			menu.add("���ͳ��").addActionListener(action);
			menu.add("Ա������ͳ��").addActionListener(action);

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

			menu = new JMenu("��������");
			add(menu);
			menu.add("�޸Ķ���").addActionListener(action);	
			menu.add("��������").addActionListener(action);	
			menu.add("�鿴������").addActionListener(action);

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

	public static void setPopMenus(JPopupMenu menu, MenuAction action) {
		if (Userman.loginUser.isManager())
			menu.add("�½�����").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("ͳ��Ա")) {
			menu.add("�鿴������").addActionListener(action);
			menu.add("�޸Ķ���").addActionListener(action);
			menu.add("��������").addActionListener(action);
			
			menu.addSeparator();		
			menu.add("�Ų��ƻ�").addActionListener(action);
			menu.add("�����ɹ�").addActionListener(action);
			menu.add("�������Ϲ���").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
			menu.addSeparator();
			menu.add("ɾ������").addActionListener(action);
			menu.addSeparator();
			menu.add("��������").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("�����ɫ");
			menu.add(colorMenu);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("ȥɫ").addActionListener(action);
		}else if(Userman.loginUser.getLevelStr().equals("�ֿ����Ա")) {
			menu.add("�������Ϲ���").addActionListener(action);
			
		}

	}

}
