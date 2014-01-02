package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

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

			menu = new JMenu("订单管理");
			add(menu);
			menu.add("新建订单").addActionListener(action);
			menu.add("修改订单").addActionListener(action);
			menu.add("删除订单").addActionListener(action);		
			menu.add("订单返修").addActionListener(action);	
			menu.add("查看订单组").addActionListener(action);
			menu.addSeparator();
			menu.add("导出显示的订单").addActionListener(action);
			menu.addSeparator();
			JMenu colorMenu=new JMenu("标记颜色");
			menu.add(colorMenu);
			colorMenu.add("蓝色").addActionListener(action);
			colorMenu.add("绿色").addActionListener(action);
			colorMenu.add("橙色").addActionListener(action);
			colorMenu.add("去色").addActionListener(action);

			menu = new JMenu("统计");
			add(menu);
			menu.add("月度统计").addActionListener(action);
			menu.add("年度统计").addActionListener(action);
			menu.add("员工产出统计").addActionListener(action);

			menu = new JMenu("客户管理");
			add(menu);
			menu.add("订单客户管理").addActionListener(action);
			menu.add("外协客户管理").addActionListener(action);

			menu = new JMenu("成本管理");
			add(menu);
			menu.add("生产计划管理").addActionListener(action);
			menu.add("生产工序管理").addActionListener(action);
//			menu.add("生产材料管理").addActionListener(action);
			menu.addSeparator();
			menu.add("员工产出统计").addActionListener(action);
			menu.add("工序产出统计").addActionListener(action);
			menu.add("排产时间查看").addActionListener(action);
			menu.add("生产成本统计").addActionListener(action);
			menu.add("生产计划与实际成本对照").addActionListener(action);
			menu.addSeparator();
			menu.add("工序费用管理").addActionListener(action);
			menu.add("操作人员管理").addActionListener(action);

			menu = new JMenu("用户管理");
			add(menu);
			menu.add("用户管理").addActionListener(action);
			menu.add("修改密码").addActionListener(action);
		}else if(Userman.loginUser.getLevelStr().equals("统计员")){

			menu = new JMenu("订单管理");
			add(menu);
			menu.add("修改订单").addActionListener(action);	
			menu.add("订单返修").addActionListener(action);	
			menu.add("查看订单组").addActionListener(action);

			menu = new JMenu("客户管理");
			add(menu);
			menu.add("订单客户管理").addActionListener(action);
			menu.add("外协客户管理").addActionListener(action);

			menu = new JMenu("成本管理");
			add(menu);
			//			menu.add("生产计划管理").addActionListener(action);
			menu.add("生产工序管理").addActionListener(action);
			menu.add("员工产出统计").addActionListener(action);
			//			menu.add("生产材料管理").addActionListener(action);

			menu = new JMenu("用户管理");
			add(menu);
			menu.add("修改密码").addActionListener(action);
		}else if(Userman.loginUser.getLevelStr().equals("计划派工")){
			menu = new JMenu("成本管理");
			add(menu);
			menu.add("生产计划管理").addActionListener(action);
			menu.add("生产计划与实际成本对照").addActionListener(action);
			menu = new JMenu("用户管理");
			add(menu);
			menu.add("修改密码").addActionListener(action);
		}
		else if(Userman.loginUser.getLevelStr().equals("仓库管理员")){
			menu = new JMenu("成本管理");
			add(menu);
//			menu.add("生产材料管理").addActionListener(action);
			menu = new JMenu("用户管理");
			add(menu);
			menu.add("修改密码").addActionListener(action);
		}

		menu = new JMenu("帮助");
		add(menu);

		menu.add("关于").addActionListener(action);
	}

	public static void setPopMenus(JPopupMenu menu, MenuAction action) {
		if (Userman.loginUser.isManager())
			menu.add("新建订单").addActionListener(action);
		if(Userman.loginUser.isManager()||Userman.loginUser.getLevelStr().equals("统计员")) {
			menu.add("查看订单组").addActionListener(action);
			menu.add("修改订单").addActionListener(action);
			menu.add("订单返修").addActionListener(action);
			
			menu.addSeparator();		
			menu.add("排产计划").addActionListener(action);
			menu.add("订单派工").addActionListener(action);
			menu.add("生产材料管理").addActionListener(action);
		}
		if (Userman.loginUser.isManager()) {
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
		}else if(Userman.loginUser.getLevelStr().equals("仓库管理员")) {
			menu.add("生产材料管理").addActionListener(action);
			
		}

	}

}
