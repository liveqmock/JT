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

		menu = new JMenu("订单管理");
		add(menu);
		if(Userman.loginUser.getMenus().contains("新建订单"))
			menu.add("新建订单").addActionListener(action);

		menu = new JMenu("统计");
		add(menu);
		if(Userman.loginUser.getMenus().contains("月度统计"))
			menu.add("月度统计").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("生产数据管理"))
			menu.add("生产数据管理").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("年度统计"))
			menu.add("年度统计").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("员工产出统计"))
			menu.add("员工产出统计").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("工序产出统计"))
			menu.add("工序产出统计").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("生产情况表"))
			menu.add("生产情况表").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("查看设备使用情况"))
			menu.add("查看设备使用情况").addActionListener(action);
		menu = new JMenu("客户管理");
		add(menu);
		if(Userman.loginUser.getMenus().contains("订单客户管理"))
			menu.add("订单客户管理").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("外协客户管理"))
			menu.add("外协客户管理").addActionListener(action);

		menu = new JMenu("成本管理");
		add(menu);
		if(Userman.loginUser.getMenus().contains("排产计划录入"))
			menu.add("排产计划录入").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("生产数据录入"))
			menu.add("生产数据录入").addActionListener(action);
		//			menu.add("生产材料管理").addActionListener(action);
		menu.addSeparator();
		
		if(Userman.loginUser.getMenus().contains("查看排产计划"))
			menu.add("查看未开始的排产计划").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("查看排产情况"))
			menu.add("查看排产情况").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("生产成本统计"))
			menu.add("生产成本统计").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("生产计划与实际成本对照"))
			menu.add("生产计划与实际成本对照").addActionListener(action);
		menu.addSeparator();
		if(Userman.loginUser.getMenus().contains("设备管理"))
			menu.add("设备管理").addActionListener(action);
		if(Userman.loginUser.getMenus().contains("操作人员管理"))
			menu.add("操作人员管理").addActionListener(action);
		menu = new JMenu("用户管理");
		add(menu);
		if(Userman.loginUser.getMenus().contains("用户管理"))
			menu.add("用户管理").addActionListener(action);
		menu.add("修改密码").addActionListener(action);


		menu = new JMenu("帮助");
		add(menu);

		menu.add("清理内存").addActionListener(action);
		menu.add("关于").addActionListener(action);
	}


	//	public   void setBillMenus() {
	//
	//		MenuAction action = new MenuAction();
	//		JMenu menu;
	//		menu = new JMenu("订单管理");
	//		add(menu);
	//		menu.add("新建订单").addActionListener(action);
	//
	//		menu = new JMenu("统计");
	//		add(menu);
	//		menu.add("月度统计").addActionListener(action);
	//		menu.add("年度统计").addActionListener(action);
	//		menu.add("员工产出统计").addActionListener(action);
	//		menu.add("查看设备使用情况").addActionListener(action);
	//		menu = new JMenu("客户管理");
	//		add(menu);
	//		menu.add("订单客户管理").addActionListener(action);
	//		menu.add("外协客户管理").addActionListener(action);
	//
	//		menu = new JMenu("成本管理");
	//		add(menu);
	//		menu.add("排产计划录入").addActionListener(action);
	//		menu.add("生产数据录入").addActionListener(action);
	//		menu.addSeparator();
	//		menu.add("查看未开始的排产计划").addActionListener(action);
	//		menu.add("员工产出统计").addActionListener(action);
	//		menu.add("工序产出统计").addActionListener(action);
	//		menu.add("排产时间查看").addActionListener(action);
	//		menu.add("生产成本统计").addActionListener(action);
	//		menu.add("生产计划与实际成本对照").addActionListener(action);
	//		menu.addSeparator();
	//		menu.add("工序费用管理").addActionListener(action);
	//		menu.add("操作人员管理").addActionListener(action);
	//		
	//		menu = new JMenu("用户管理");
	//		add(menu);
	//		menu.add("用户管理").addActionListener(action);
	//		menu.add("修改密码").addActionListener(action);
	//
	//
	//		menu = new JMenu("帮助");
	//		add(menu);
	//
	//		menu.add("关于").addActionListener(action);
	//	}

}
