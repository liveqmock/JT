package windows;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import com.mao.jf.beans.User;

public class MainMenu extends JMenuBar {
	@SuppressWarnings("unused")
	private BillTable table;

	public MainMenu(BillTable table) {
		super();
		this.table = table;
		JMenu menu = new JMenu("��������");
		add(menu);
		MenuAction action = new MenuAction(table);
		setBillMenus(menu,action);

		if(User.loginUser.getLevel()==0){
			menu = new JMenu("ͳ��");
			add(menu);
			menu.add("�¶�ͳ��").addActionListener(action);
			menu.add("���ͳ��").addActionListener(action);

			menu = new JMenu("�ͻ�����");
			add(menu);
			menu.add("�����ͻ�����").addActionListener(action);
			menu.add("��Э�ͻ�����").addActionListener(action);

		}

		menu = new JMenu("�������");
		add(menu);
		menu.add("�Ų��ƻ�").addActionListener(action);
		menu.add("�����ɹ�").addActionListener(action);
		if(User.loginUser.getLevel()==0){
			menu.add("������ù���").addActionListener(action);
			menu.add("������Ա����").addActionListener(action);
		}

		menu = new JMenu("�û�����");
		add(menu);

		menu.add("�޸�����").addActionListener(action);

		menu = new JMenu("����");
		add(menu);

		menu.add("����").addActionListener(action);

	}

	public static  void setBillMenus(JMenu menu,MenuAction action) {
		if (User.loginUser.getLevel() == 0)
			menu.add("�½�����").addActionListener(action);

		menu.add("�鿴������").addActionListener(action);
		menu.add("�޸Ķ���").addActionListener(action);
		menu.add("��������").addActionListener(action);
		if (User.loginUser.getLevel() == 0) {
			menu.addSeparator();
			menu.add("ɾ������").addActionListener(action);
			menu.addSeparator();
			menu.add("������ʾ�Ķ���").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("�����ɫ");
			menu.add(colorMenu);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("��ɫ").addActionListener(action);
			colorMenu.add("ȥɫ").addActionListener(action);

		}

	}

	public static void setPopMenus(JPopupMenu menu, MenuAction action) {
		if (User.loginUser.getLevel() == 0)
			menu.add("�½�����").addActionListener(action);

		menu.add("�鿴������").addActionListener(action);
		menu.add("�޸Ķ���").addActionListener(action);
		menu.add("��������").addActionListener(action);
		
		menu.addSeparator();		
		menu.add("�Ų��ƻ�").addActionListener(action);
		menu.add("�����ɹ�").addActionListener(action);
		
		if (User.loginUser.getLevel() == 0) {
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
		}

	}

}
