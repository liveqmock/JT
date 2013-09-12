package windows;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.NamingException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import windows.costPanes.EmployeeCostPnl;
import windows.costPanes.EmployeePnl;
import windows.costPanes.MaterialTable;
import windows.costPanes.OperationPnl;
import windows.costPanes.PlanTable;
import windows.costPanes.PlanWorkPnl;
import windows.costPanes.WorkCostPnl;
import windows.costPanes.WorkCreatePanel;
import windows.customComponet.BeanDialog;
import windows.customComponet.BeanTableModel;
import windows.customComponet.BeansPanel;
import windows.customComponet.RsTablePane;
import windows.panels.BackRepairPanel;
import windows.panels.ChangePasswdPanel;
import windows.panels.CustomPanel;
import windows.panels.UsermanPnl;

import com.mao.jf.beans.AbstractCustom;
import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OutCustom;
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
				Vector<AbstractCustom> customs =new Custom().LoadAll();
				if(customs.size()==0) customs.add(new Custom());
				adminCustom(customs);
				break;
			case "外协客户管理":
				Vector<AbstractCustom> outCustoms =new OutCustom().LoadAll();
				if(outCustoms.size()==0) outCustoms.add(new OutCustom());
				adminCustom(outCustoms);
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
				try {
					try {
						employeeManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO 自动生产的 catch 块
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO 自动生产的 catch 块
					e1.printStackTrace();
				}
				break;
			case "月度统计":
				monStatistic();
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
		 BeansPanel<Userman> beansPanel=new BeansPanel<Userman>(Userman.loadAll(Userman.class),new UsermanPnl(new Userman())) {

			@Override
			public Userman getNewBean() {
				 try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException | IntrospectionException
						| SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				 return null;
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

	private void materialManager() {
		final JDialog  dialog=new JDialog();
		final MaterialTable table = new MaterialTable();
		JScrollPane scroll = new JScrollPane( table );
		JPanel btPanel=new JPanel();
		btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
		
		JButton Okbt = new JButton(new AbstractAction("确定") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				table.save();
				dialog.dispose();
			}
		});
		
		
		btPanel.add(Okbt);
		
		dialog.setTitle("生产材料管理");
		dialog.getContentPane().add( scroll,BorderLayout.CENTER );
		dialog.getContentPane().add(btPanel,BorderLayout.SOUTH);

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
		
	}

	private void planProduct() {
		final PlanTable table = new PlanTable();
		JScrollPane scroll = new JScrollPane( table );
		final JDialog  dialog=new JDialog();
		dialog.getContentPane().add( scroll,BorderLayout.CENTER );
		dialog.setTitle("生产计划管理");
		JPanel btPanel=new JPanel();
		btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
		
		JButton Okbt = new JButton(new AbstractAction("确定") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				table.save();
				dialog.dispose();
			}
		});
		JButton addBt=new JButton("新增排产计划");
		addBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				   table.createPlan();
				
			}
		});
		
		btPanel.add(addBt);
		btPanel.add(Okbt);
		dialog.getContentPane().add(btPanel,BorderLayout.SOUTH);

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
		
	}

	private void showBillGroup() {
		
		table.setBeans(Bill.loadByGrp(table.getSelectBean().getBillgroup()));
	}


	

	private void employeeManager() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		EmployeePnl beanPanel = new EmployeePnl(new Employee());
		BeansPanel<Employee> panel = new BeansPanel<Employee>(Employee.loadAll(Employee.class),beanPanel,true) {

			@Override
			public Employee getNewBean() {
				try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException | SQLException e) {
					e.printStackTrace();
				}
				return null;
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
		BeansPanel<Operation> panel = new BeansPanel<Operation>(Operation.loadAll(Operation.class),beanPanel,true) {


			@Override
			public Operation getNewBean() {
				try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException | SQLException e) {
					// TODO 自动生产的 catch 块
					e.printStackTrace();
				}
				return null;
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

		SqlFrame("select   concat(year(itemcompletedate) ,'年度') 年度,custom 单位,sum(num) 数量,sum(reportprice*num) 总价 from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'年度'),custom union select   concat(year(itemcompletedate) ,'年度 总计') 年度,null 单位,sum(num) 数量,sum(reportprice*num) 总价 from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'年度 总计') order by 年度 desc", "年度统计");

	}

	private void monStatistic() {
		SqlFrame("select   concat(year(itemcompletedate) ,'年',month(itemcompletedate),'月') 月份,custom 单位,sum(num) 数量,sum(reportprice*num) 总价 from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'年',month(itemcompletedate),'月'),custom union select   concat(year(itemcompletedate) ,'年',month(itemcompletedate),'月 总计') 月份,null 单位,sum(num) 数量,sum(reportprice*num) 总价 from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'年',month(itemcompletedate),'月 总计') order by 月份 desc", "月度统计");
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
	private void editBackRepair() {
		final Bill billItem=table.getSelectBean();
		Vector<BackRepair> backRepairs=null;
		BackRepair backRepair1=new BackRepair();
		backRepair1.setBillItem(billItem);
		if(billItem.getBackRepairNum()==0){
			backRepairs=new Vector<>();
		}else{
			backRepairs=BackRepair.Load(billItem);

		}
		BeansPanel<BackRepair> beansPanel=new BeansPanel<BackRepair>(backRepairs,new BackRepairPanel(backRepair1),true) {

			@Override
			public BackRepair getNewBean() {
				BackRepair backRepair;
				try {
					backRepair=getPanelBean();
					backRepair.save();
					billItem.setBackRepairNum(billItem.getBackRepairNum()+1);
					BackRepair newBackRepair =new BackRepair();
					newBackRepair.setBillItem(billItem);
					getBeanPanel().setBean(newBackRepair);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
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

	public static void adminCustom(Vector<AbstractCustom>customs) {
		BeansPanel<AbstractCustom> beansPanel2=new BeansPanel<AbstractCustom>(customs.firstElement().LoadAll(),new CustomPanel(customs.firstElement()),true) {

			@Override
			public AbstractCustom getNewBean() {
				try {
					AbstractCustom custom = getPanelBean();

					custom.save();
					if(custom instanceof Custom){
						setPanelBean(new Custom());
					}else if(custom instanceof OutCustom){
						setPanelBean(new OutCustom());
					}
					return custom;
				} catch (ClassNotFoundException | SQLException
						| NamingException e) {
					e.printStackTrace();
					return null;
				}
			}
		};

		BeanDialog<AbstractCustom> dialog=new BeanDialog<AbstractCustom>(beansPanel2,"订单客户管理") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);
	}

	

}
