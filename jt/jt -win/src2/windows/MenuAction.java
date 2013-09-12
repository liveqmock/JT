package windows;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableUI;

import org.apache.commons.beanutils.BeanUtils;

import windows.costPanes.EmployeePnl;
import windows.costPanes.OperCostPnl;
import windows.costPanes.OperationPnl;
import windows.costPanes.PlanCostPnl;
import windows.customComponet.BeanDialog;
import windows.customComponet.BeanTableModel;
import windows.customComponet.BeansPanel;
import windows.customComponet.RsTablePane;
import windows.panels.BackRepairPanel;
import windows.panels.ChangePasswdPanel;
import windows.panels.CustomPanel;

import com.mao.jf.beans.AbstractCustom;
import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.OperCost;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OutCustom;
import com.mao.jf.beans.PlanCost;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.User;

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
						&& User.loginUser.getLevel() > 0) {
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
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				break;
			case "修改密码":
				final ChangePasswdPanel userPanel = new ChangePasswdPanel(User.loginUser);
				BeanDialog<User> userDialog=new BeanDialog<User>(userPanel,"修改用户密码") {

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
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				break;
			case "排产计划":
				try {
					planCostManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				break;
			case "查看订单组":
					showBillGroup();
				break;
			case "订单派工":
				try {
					try {
						operCostManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				break;
			case "操作人员管理":
				try {
					try {
						employeeManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				break;
			case "月度统计":
				monStatistic();
				break;
			case "年度统计":
				yearStatistic();
				break;
			default:
				break;
			}

	}

	private void showBillGroup() {
		
		table.setBeans(Bill.loadByGrp(table.getSelectBean().getBillgroup()));
	}

	private void operCostManager() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
			return;
		}	
		if (table.getSelectBean().getStatus().equals("未排产")) {
			JOptionPane.showMessageDialog(table, "尚未安排生产计划，不能安排生产工序!");
			return;
		}
		OperCostPnl beanPanel = new OperCostPnl(new OperCost(table.getSelectBean()));
		BeansPanel<OperCost> panel = new BeansPanel<OperCost>(OperCost.loadAllByBill(table.getSelectBean()),beanPanel,true) {
			@Override
			public void reEditRow() {
				OperCost operCost = getTablePane().getSelectBean();
				if(operCost.isComplete()) {
					JOptionPane.showMessageDialog(this, "工序【"+operCost.getOperationName()+"】已经完成，不能再更改！","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.reEditRow();
			}

			@Override
			public void removeSelectRow() {
				OperCost operCost = getTablePane().getSelectBean();
				if(operCost.isComplete()) {
					JOptionPane.showMessageDialog(this, "工序【"+operCost.getOperationName()+"】已经在完成，不能删除！","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.removeSelectRow();
			}
			public void addNew() {
				if (!getBeanPanel().isValide()) {
					return;
				}
				OperCost newbean = getNewBean();
				if (newbean != null) {

					if(newbean.getAssignNum()<newbean.getProductNum()+newbean.getScrapnum()) {
						JOptionPane.showMessageDialog(getBeanPanel(), "成品+报废品>分派数量 ，请确认成品及报废品数量是否正确","错误",JOptionPane.ERROR_MESSAGE);
						return;

					}

					getTablePane().addNew(newbean);
					setPanelBean(new OperCost(newbean.getBill()));


				}
			}
			@Override
			public OperCost getNewBean() {
				try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalArgumentException
						| SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | IntrospectionException | SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<OperCost> dialog=new BeanDialog<OperCost>(panel,"订单派工") {

			@Override
			public boolean okButtonAction() {

				return true;
			}

		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

	}

	private void planCostManager() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {

		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
			return;
		}
		PlanCostPnl beanPanel = new PlanCostPnl(new PlanCost(table.getSelectBean()));

		final BeansPanel<PlanCost> panel = new BeansPanel<PlanCost>(PlanCost.loadAllByBill(table.getSelectBean()),beanPanel,true) {

			@Override
			public void reEditRow() {
				PlanCost planCost = getTablePane().getSelectBean();
				if(planCost.hasOper()) {
					JOptionPane.showMessageDialog(this, "工序【"+planCost.getOperationName()+"】已经在生成中，不能再更改！","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.reEditRow();
			}

			@Override
			public void removeSelectRow() {
				PlanCost planCost = getTablePane().getSelectBean();
				if(planCost.hasOper()) {
					JOptionPane.showMessageDialog(this, "工序【"+planCost.getOperationName()+"】已经在生成中，不能删除！","错误",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.removeSelectRow();
			}

			@Override
			public void addNew() {
				
				if (!getBeanPanel().isValide()) {
					return;
				}
				PlanCost newbean = getNewBean();
				if (newbean != null) {
					try {
						BeanTableModel<PlanCost> mode=	(BeanTableModel<PlanCost>) getTablePane().getTable().getModel();
						for(PlanCost bean: mode.getBeans()){
							
							if(!newbean.equals(bean)&& bean.getOperationName().equals(newbean.getOperationName())){
								JOptionPane.showMessageDialog(getBeanPanel(), "工序【"+newbean.getOperationName()+"】已经在计划列表中，不能再添加！","错误",JOptionPane.ERROR_MESSAGE);
								return ;
							}
							if(newbean.equals(bean))
								try {
									BeanUtils.copyProperties(bean, newbean);
								} catch (IllegalAccessException
										| InvocationTargetException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
						}
						if(newbean.getSequence()==0)
							newbean.setSequence(getTablePane().getTable().getRowCount()+1);
						getTablePane().addNew(newbean);
						setPanelBean(new PlanCost(newbean.getBill()));
					} catch (IllegalArgumentException
							| SecurityException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}

				}
			}

			@Override
			public PlanCost getNewBean() {

				return getPanelBean();
			}
		};
		panel.getTablePane().getTable().setUI(new PlanHandler());

		JMenuItem addOper = new JMenuItem("订单派工");
		addOper.addActionListener(this);
		panel.getTablePane().getPopupMenu().add(addOper, 0);

		BeanDialog<PlanCost> dialog=new BeanDialog<PlanCost>(panel,"订单派工") {

			@Override
			public boolean okButtonAction() {
				for(int i=0;i<getBeans().size();i++){
					PlanCost ps= getBeans().get(i);
					if(ps.hasOper())continue;
					for(PlanCost bean: getBeans()){
						if(ps!=bean&& bean.getOperationName().equals(ps.getOperationName())){
							JOptionPane.showMessageDialog(this, "工序【"+ps.getOperationName()+"】计划列表中有重复，请提交修改再按此按钮！","错误",JOptionPane.ERROR_MESSAGE);
							return  false;
						}
					}
					ps.setSequence(i+1);
					try {
						ps.save();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException
							| NoSuchMethodException | SecurityException
							| IntrospectionException | SQLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				return true;
			}

		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<Employee> dialog=new BeanDialog<Employee>(panel,"操作人员管理") {

			@Override
			public boolean okButtonAction() {
				// TODO 自动生成的方法存根
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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<Operation> dialog=new BeanDialog<Operation>(panel,"操作工序管理") {

			@Override
			public boolean okButtonAction() {
				// TODO 自动生成的方法存根
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
			JDialog frame=new JDialog();

			frame.getContentPane().add(tablePane,BorderLayout.CENTER);
			frame.setSize(700, 500);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
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
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
				return true;
			}
		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);
	}

	public class PlanHandler extends BasicTableUI {
		private boolean draggingRow = false;
		private int startDragPoint;
		private int dyOffset;

		protected MouseInputListener createMouseInputListener() {
			return new DragDropRowMouseInputHandler();
		}

		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);

			if (draggingRow) {
				g.setColor(table.getParent().getBackground());
				Rectangle cellRect = table.getCellRect(table.getSelectedRow(), 0, false);
				g.copyArea(cellRect.x, cellRect.y, table.getWidth(), table.getRowHeight(), cellRect.x, dyOffset);

				if (dyOffset < 0) {
					g.fillRect(cellRect.x, cellRect.y + (table.getRowHeight() + dyOffset), table.getWidth(), (dyOffset * -1));
				} else {
					g.fillRect(cellRect.x, cellRect.y, table.getWidth(), dyOffset);
				}
			}
		}

		class DragDropRowMouseInputHandler extends MouseInputHandler {

			private int sRow=-1;

			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				sRow=table.getSelectedRow();
				startDragPoint = (int)e.getPoint().getY();
			}

			public void mouseDragged(MouseEvent e) {
				int fromRow = table.getSelectedRow();
				BeanTableModel<PlanCost> model = (BeanTableModel<PlanCost>) table.getModel();
				if( model.getSelectBean(fromRow).hasOper()) return;

				if (fromRow >= 0) {
					draggingRow = true;

					int rowHeight = table.getRowHeight();
					int middleOfSelectedRow = (rowHeight * fromRow) + (rowHeight / 2);

					int toRow = -1;
					int yMousePoint = (int)e.getPoint().getY();

					if (yMousePoint < (middleOfSelectedRow - rowHeight)) {
						// Move row up
						toRow = fromRow - 1;
					} else if (yMousePoint > (middleOfSelectedRow + rowHeight)) {
						// Move row down
						toRow = fromRow + 1;
					}
					if (toRow >= 0 && toRow < table.getRowCount()) {
						if( ((BeanTableModel<PlanCost>) table.getModel()).getSelectBean(toRow).hasOper()) return;

						model.getSelectBean(fromRow).setSequence(toRow+1);
						model.getSelectBean(toRow).setSequence(fromRow+1);
						model.move(fromRow, toRow);
						table.setRowSelectionInterval(toRow, toRow);
						sRow=toRow;
						startDragPoint = yMousePoint;
					}

					dyOffset = (startDragPoint - yMousePoint) * -1;
					table.repaint();
				}
			}

			public void mouseReleased(MouseEvent e){
				super.mouseReleased(e);
				if (sRow >= 0 && sRow < table.getRowCount() && table.getSelectedRow()!=sRow) 
					table.setRowSelectionInterval(sRow, sRow);
				draggingRow = false;
				table.repaint();
			}
		}
	}
}
