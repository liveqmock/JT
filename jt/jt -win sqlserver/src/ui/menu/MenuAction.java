package ui.menu;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ui.costPanes.EmployeeCostPnl;
import ui.costPanes.EmployeePnl;
import ui.costPanes.EquipmentManagerPnl;
import ui.costPanes.OperationPnl;
import ui.costPanes.OperationWorkPnl;
import ui.costPanes.PlanCreatePanel;
import ui.costPanes.PlansDetail;
import ui.costPanes.ShowWorkCostPnl;
import ui.costPanes.WorkCostPnl;
import ui.costPanes.WorkCreatePanel;
import ui.customComponet.BeanDialog;
import ui.customComponet.BeanTablePane;
import ui.customComponet.BeansPanel;
import ui.customComponet.RsTablePane;
import ui.frames.About;
import ui.frames.BillFrame;
import ui.panels.ChangePasswdPanel;
import ui.panels.CustomPanel;
import ui.panels.UsermanPnl;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationWork;
import com.mao.jf.beans.PicPlan;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class MenuAction extends AbstractAction {
	public MenuAction() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText() != null)
			switch (((JMenuItem) e.getSource()).getText()) {
			case "新建订单":
				new BillFrame(new BillBean());
				break;
			case "查看设备使用情况":
				showEquipmentUsing();
				break;

			case "修改订单":
				//				if (table.getSelectBean() == null) {
				//					JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
				//					break;
				//				}
				//				if (table.getSelectBean().isItemComplete()
				//						&& Userman.loginUser.getLevel() > 0) {
				//					JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
				//					break;
				//				}
				//				new BillFrame(table.getSelectBean());
				break;

			case "订单客户管理":
				adminCustom(0);
				break;
			case "外协客户管理":
				adminCustom(1);
				break;
			case "生产计划与实际成本对照":
				showWorkCostPanel();
				break;

			case "修改密码":
				changePasswd();
				break;
			case "清理内存":
				clearMemery();
				break;

			case "关于":
				new About().setVisible(true);
				break;
			case "工序费用管理":
				try {
					operationManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO 自动生产的 catch 块
					e1.printStackTrace();
				}
				break;

			case "查看订单组":
				showBillGroup();
				break;
			case "员工产出统计":
				showEmployeeCost();
				break;
			case "工序产出统计":
				showWorkCost();
				break;
			case "操作人员管理":

				employeeManager();

				break;
			case "月度统计":
				monStatistic();
				break;
			case "查看排产情况":
				showBillTime();
				break;
			case "年度统计":
				yearStatistic();
				break;
			case "生产成本统计":
				//planworkShow();
				break;
			case "排产计划录入":
				planProduct();
				break;
			case "生产数据录入":
				workManager();
				break;
			case "设备管理":
				try {
					operationManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e2) {
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
				break;
			case "生产情况表":
				SqlFrame("select * from planWorkDetail", "生产情况表");
				break;
			case "用户管理":
				try {
					manageUser();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			default:
				break;
			}

	}

	public void clearMemery() {
		
		System.gc();
	}
	private void showEquipmentUsing() {
		String sql = "select a.NAME 设备名称,a.code 设备编号, endtime 计划完工时间 from "
				+ "dbo.equipment a join operation b on a.operation=b.id left "
				+ "join (Select equipment,max(planEndTime) endtime from dbo.equipmentplan  "
				+ "group by equipment) c on a.id=c.equipment order by a.name,code";
		SqlFrame(sql, "设备使用情况");
	}

	private void changePasswd() {
		final ChangePasswdPanel userPanel = new ChangePasswdPanel(Userman.loginUser);
		BeanDialog<Userman> userDialog=new BeanDialog<Userman>(userPanel,"修改用户密码") {

			@Override
			public boolean okButtonAction() {

				return userPanel.isOk();
			}
		};
		userDialog.setBounds(100, 100, 300,250);
		userDialog.setLocationRelativeTo(null);
		userDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		userDialog.setVisible(true);



	}


	private void showBillTime() {
		JDialog dialog=new JDialog();
		dialog.setContentPane(new PlansDetail());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}

	private void showWorkCost() {
		JDialog dialog=new JDialog();
		dialog.setContentPane(new WorkCostPnl());
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}

	private void showEmployeeCost() {
		JDialog dialog=new JDialog();
		dialog.setContentPane(new EmployeeCostPnl());
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}



	private void manageUser() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		BeansPanel<Userman> beansPanel=new BeansPanel<Userman>(BeanMao.getBeans(Userman.class),new UsermanPnl(new Userman()),Userman.class) {

			@Override
			public Userman saveBean() {

				BeanMao.saveBean(getPanelBean());
				return getPanelBean();

			}

		};
		BeanDialog< Userman> dialog=new BeanDialog<Userman>(beansPanel,"用户管理") {

			@Override
			public boolean okButtonAction() {
				// TODO 自动生成的方法存根
				return true;
			}
		};
		dialog.setSize(400, 600);
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}


	private void showWorkCostPanel(){
		JDialog dialog=new JDialog();
		dialog.setTitle("工序管理");
		dialog.setContentPane(new ShowWorkCostPnl());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}


	private void planProduct() {
		JFrame dialog=new JFrame();
		dialog.setTitle("排产计划录入");
		dialog.add(new PlanCreatePanel());
		dialog.setExtendedState(Frame.MAXIMIZED_BOTH );
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}
	private void workManager() {

		
		
		OperationWorkPnl operationWorkPnl=new OperationWorkPnl(new OperationWork());
		BeansPanel<OperationWork> panel=new BeansPanel<OperationWork>(new ArrayList<OperationWork>(),operationWorkPnl,OperationWork.class) {

			@Override
			public OperationWork saveBean() {
				getBeanPanel().getBean().save();
				return getBeanPanel().getBean();
			}

			@Override
			protected OperationWork createNewBean() {
				OperationWork operationWork= new OperationWork();
				operationWork.setEmployee(getBeanPanel().getBean().getEmployee());
				return operationWork;
			}

			
			
		};

		JFrame dialog=new JFrame();
		dialog.setTitle("生产数据录入");
		dialog.add(panel);
		dialog.setExtendedState(Frame.MAXIMIZED_BOTH );
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	private void showBillGroup() {

		//		table.setBeans((Collection<BillBean>) BillBean.loadByGrp(table.getSelectBean().getBillgroup()));
	}




	private void employeeManager()  {
		EmployeePnl beanPanel = new EmployeePnl(new Employee());
		BeansPanel<Employee> panel = new BeansPanel<Employee>(BeanMao.getBeans(Employee.class),beanPanel,Employee.class,true) {

			@Override
			public Employee saveBean() {
				getPanelBean().save();
				return getPanelBean();

			}
		};
		BeanDialog<Employee> dialog=new BeanDialog<Employee>(panel,"操作人员管理") {

			@Override
			public boolean okButtonAction() {
				return true;
			}

		};

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setVisible(true);

	}

	private void operationManager() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {

		JDialog dialog=new JDialog();
		dialog.setTitle("设备管理");
		EquipmentManagerPnl equipmentManagerPnl=new EquipmentManagerPnl();
		dialog.setContentPane(equipmentManagerPnl);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	private void yearStatistic() {
		String sql = "select 年份,单位,sum(数量) ,sum(总价) from ("
				+"select   str(year(itemcompletedate)) +'年' 年份,custom 单位,num 数量,reportprice*num 总价 from bill where itemcompletedate is not null  union "
				+"select   str(year(itemcompletedate)) +'年' 年份,' 合计' 单位,num 数量,reportprice*num 总价 from bill where itemcompletedate is not null) a "
				+"group by 年份,单位 order by 年份 desc,单位 ";
		SqlFrame(sql, "年度统计");

	}

	private void monStatistic() {
		String sql = "select 月份,单位,sum(数量) ,sum(总价) from ("
				+"select   str(year(itemcompletedate)) +'年'+str(month(itemcompletedate))+'月' 月份,custom 单位,num 数量,reportprice*num 总价 from bill where itemcompletedate is not null  union "
				+"select   str(year(itemcompletedate)) +'年'+str(month(itemcompletedate))+'月' 月份,' 合计' 单位,num 数量,reportprice*num 总价 from bill where itemcompletedate is not null) a "
				+"group by 月份,单位 order by 月份 desc,单位 ";
		SqlFrame(sql,"月度统计");
	}

	private void SqlFrame(String sql,final String title){
		try(Statement st=SessionData.getConnection().createStatement();
				ResultSet rs=st.executeQuery(sql)){
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			final RsTablePane tablePane=new RsTablePane(crs, title);
			JDialog dialog=new JDialog();

			dialog.getContentPane().add(tablePane,BorderLayout.CENTER);
			dialog.setSize(700, 500);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void adminCustom(final int out) {
		List<Custom> customs = BeanMao.getBeans(Custom.class, " a.out="+out);
		if(customs==null)customs=new ArrayList<>();
		BeansPanel<Custom> beansPanel=new BeansPanel<Custom>(customs,new CustomPanel(null),Custom.class,true) {

			@Override
			public Custom saveBean() {

				Custom custom = getPanelBean();
				custom.setOut(out);
				custom.save();
				return custom;

			}

		};

		BeanDialog<Custom> dialog=new BeanDialog<Custom>(beansPanel,"订单客户管理") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);
	}



}
