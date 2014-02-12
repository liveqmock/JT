package ui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ui.costPanes.EmployeeCostPnl;
import ui.costPanes.EmployeePnl;
import ui.costPanes.OperationPnl;
import ui.costPanes.PlanCreatePanel;
import ui.costPanes.PlanWorkPnl;
import ui.costPanes.ShowWorkCostPnl;
import ui.costPanes.UnStartPlanPnl;
import ui.costPanes.WorkCostPnl;
import ui.costPanes.WorkCreatePanel;
import ui.customComponet.BeanDialog;
import ui.customComponet.BeanTableModel;
import ui.customComponet.BeansPanel;
import ui.customComponet.RsTablePane;
import ui.frames.About;
import ui.frames.BillFrame;
import ui.panels.BackRepairPanel;
import ui.panels.ChangePasswdPanel;
import ui.panels.CustomPanel;
import ui.panels.MaterialsPanel;
import ui.panels.UsermanPnl;
import ui.tables.BillTable;

import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class MenuAction extends AbstractAction {
	private BillTable table;

	public MenuAction(BillTable table) {
		super();
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText() != null)
			switch (((JMenuItem) e.getSource()).getText()) {
			case "蓝色":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要加色的订单条目!");
					break;
				}
				table.getSelectBean().setColor("hi-blue");
				table.getSelectBean().save();
				break;
			case "绿色":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要加色的订单条目!");
					break;
				}
				table.getSelectBean().setColor("hi-green");
				table.getSelectBean().save();
				break;
			case "橙色":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要加色的订单条目!");
					break;
				}
				table.getSelectBean().setColor("hi-orange");
				table.getSelectBean().save();
				break;
			case "去色":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要去色的订单条目!");
					break;
				}
				table.getSelectBean().setColor(null);
				table.getSelectBean().save();
				break;
			case "新建订单":
				new BillFrame(new Bill());
				break;
			case "订单返修":
				editBackRepair();
				break;

			case "修改订单":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
					break;
				}
				if (table.getSelectBean().isItemComplete()
						&& Userman.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
					break;
				}
				new BillFrame(table.getSelectBean());
				break;
			case "删除订单":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要删除的订单条目!");
					break;
				}
				if (JOptionPane.showConfirmDialog(table, "确定要删除订单【"
						+ table.getSelectBean().getBillid() + "】吗？", "删除确认",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					table.removeSelectRow();
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
			case "导出订单":
				try {
					((BeanTableModel<Bill>)table.getTable().getModel()).exportToExl("订单信息");
				} catch (IOException e1) {
					// TODO 自动生产的 catch 块
					e1.printStackTrace();
				}
				break;
			case "修改密码":
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
			case "查看未开始的排产计划":
				showUnStartPlan();
				break;
			case "月度统计":
				monStatistic();
				break;
			case "排产时间查看":
				showBillTime();
				break;
			case "年度统计":
				yearStatistic();
				break;
			case "生产成本统计":
				planworkShow();
				break;
			case "生产计划管理":
				planProduct();
				break;
			case "生产材料管理":
				materialManager();
				break;
			case "生产工序管理":
				workManager();
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

	private void showBillTime() {

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

	private void planworkShow() {
		JDialog dialog=new JDialog();
		dialog.setContentPane(new PlanWorkPnl());
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}

	private void manageUser() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		BeansPanel<Userman> beansPanel=new BeansPanel<Userman>(BeanMao.loadAll(Userman.class),new UsermanPnl(new Userman()),Userman.class) {

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
				return false;
			}
		};
		dialog.setSize(400, 500);
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}

	private void workManager() {
		JDialog dialog=new JDialog();
		dialog.setTitle("工序管理");
		dialog.setContentPane(new WorkCreatePanel());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
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
	private void showUnStartPlan(){
		JDialog dialog=new JDialog();
		dialog.setTitle("未开始的排产计划");
		dialog.setContentPane(new UnStartPlanPnl());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}
	private void materialManager() {
		JDialog dialog=new JDialog();
		MaterialsPanel materialsPanel=new MaterialsPanel(table.getSelectBean()) ;
		dialog.setContentPane(materialsPanel);
		dialog.setTitle("生产材料管理");

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);


		//		final JDialog  dialog=new JDialog();
		//		final MaterialTable table = new MaterialTable();
		//		JScrollPane scroll = new JScrollPane( table );
		//		JPanel btPanel=new JPanel();
		//		btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
		//		
		//		JButton Okbt = new JButton(new AbstractAction("确定") {
		//			
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				table.save();
		//				dialog.dispose();
		//			}
		//		});
		//		JPanel panel_2 = new JPanel();
		//		panel_2.setBorder(new EmptyBorder(1, 1, 1, 1));
		//		dialog.add(panel_2, BorderLayout.NORTH);
		//		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		//
		//		JLabel label = new JLabel("\u56FE\u53F7\uFF1A");
		//		panel_2.add(label);
		//
		//		final JTextField textField = new JTextField();
		//		panel_2.add(textField);
		//		textField.setColumns(10);
		//
		//		JButton searchBt = new JButton("\u67E5\u627E");
		//		panel_2.add(searchBt);
		//		searchBt.addActionListener(new ActionListener() {
		//			
		//			@Override
		//			public void actionPerformed(ActionEvent arg0) {
		//				table.setPlans(Plan.loadUnCompletedByOut(textField.getText()));
		//				
		//			}
		//		});
		//		
		//		btPanel.add(Okbt);
		//		
		//		dialog.setTitle("生产材料管理");
		//		dialog.getContentPane().add( scroll,BorderLayout.CENTER );
		//		dialog.getContentPane().add(btPanel,BorderLayout.SOUTH);
		//
		//		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		//		dialog.setLocationRelativeTo(null);
		//		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		//		dialog.setVisible(true);

	}

	private void planProduct() {
		JDialog dialog=new JDialog();
		dialog.add(new PlanCreatePanel());
		dialog.setModal(true);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	private void showBillGroup() {

		table.setBeans((Collection<Bill>) Bill.loadByGrp(table.getSelectBean().getBillgroup()));
	}




	private void employeeManager()  {
		EmployeePnl beanPanel = new EmployeePnl(new Employee());
		BeansPanel<Employee> panel = new BeansPanel<Employee>(Employee.loadAll(Employee.class),beanPanel,Employee.class,true) {

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

		OperationPnl beanPanel = new OperationPnl(new Operation());
		BeansPanel<Operation> panel = new BeansPanel<Operation>(Operation.loadAll(Operation.class),beanPanel,Operation.class,true) {


			@Override
			public Operation saveBean() {
				getPanelBean().save();
				return getPanelBean();

			}
		};
		BeanDialog<Operation> dialog=new BeanDialog<Operation>(panel,"操作工序管理") {

			@Override
			public boolean okButtonAction() {
				return true;
			}

		};
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
	@SuppressWarnings("serial")
	private void editBackRepair() {
		final Bill billItem=table.getSelectBean();
		BeansPanel<BackRepair> beansPanel=new BeansPanel<BackRepair>(billItem.getBackRepairs(),new BackRepairPanel(null),BackRepair.class,true) {

			@Override
			public BackRepair saveBean() {
				BackRepair backRepair=getPanelBean();
				
				backRepair.save();
				
				return backRepair;
			}
			@Override
			public BackRepair createNewBean() {
				BackRepair backRepair=new BackRepair();
				backRepair.setBillItem(billItem);
				return backRepair;
			}
		};

		BeanDialog<BackRepair> dialog=new BeanDialog<BackRepair>(beansPanel,"返修记录") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

	}

	public static void adminCustom(final int out) {
		List<Custom> customs = BeanMao.loadAll(Custom.class, " a.out="+out);
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
