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
		JMenu menu = new JMenu("订单管理");
		add(menu);
		MenuAction action = new MenuAction(table);
		setBillMenus(menu,action);

		if(User.loginUser.getLevel()==0){
			menu = new JMenu("统计");
			add(menu);
			menu.add("月度统计").addActionListener(action);
			menu.add("年度统计").addActionListener(action);

			menu = new JMenu("客户管理");
			add(menu);
			menu.add("订单客户管理").addActionListener(action);
			menu.add("外协客户管理").addActionListener(action);

		}

		menu = new JMenu("车间管理");
		add(menu);
		menu.add("排产计划").addActionListener(action);
		menu.add("订单派工").addActionListener(action);
		if(User.loginUser.getLevel()==0){
			menu.add("工序费用管理").addActionListener(action);
			menu.add("操作人员管理").addActionListener(action);
		}

		menu = new JMenu("用户管理");
		add(menu);

		menu.add("修改密码").addActionListener(action);

		menu = new JMenu("帮助");
		add(menu);

		menu.add("关于").addActionListener(action);

	}

	public static  void setBillMenus(JMenu menu,MenuAction action) {
		if (User.loginUser.getLevel() == 0)
			menu.add("新建订单").addActionListener(action);

		menu.add("修改订单").addActionListener(action);
		menu.add("订单返修").addActionListener(action);
		if (User.loginUser.getLevel() == 0) {
			menu.addSeparator();
			menu.add("删除订单").addActionListener(action);
			menu.addSeparator();
			menu.add("导出显示的订单").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("标记颜色");
			menu.add(colorMenu);
			colorMenu.add("蓝色").addActionListener(action);
			colorMenu.add("绿色").addActionListener(action);
			colorMenu.add("橙色").addActionListener(action);
			colorMenu.add("去色").addActionListener(action);

		}

	}

	public static void setPopMenus(JPopupMenu menu, MenuAction action) {
		if (User.loginUser.getLevel() == 0)
			menu.add("新建订单").addActionListener(action);

		menu.add("修改订单").addActionListener(action);
		menu.add("订单返修").addActionListener(action);
		
		menu.addSeparator();		
		menu.add("排产计划").addActionListener(action);
		menu.add("订单派工").addActionListener(action);
		
		if (User.loginUser.getLevel() == 0) {
			menu.addSeparator();
			menu.add("删除订单").addActionListener(action);
			menu.addSeparator();
			menu.add("导出订单").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("标记颜色");
			menu.add(colorMenu);
			colorMenu.add("蓝色").addActionListener(action);
			colorMenu.add("绿色").addActionListener(action);
			colorMenu.add("橙色").addActionListener(action);
			colorMenu.add("去色").addActionListener(action);
		}

	}

}
