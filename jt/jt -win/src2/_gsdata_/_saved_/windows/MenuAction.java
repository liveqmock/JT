package windows;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.basic.BasicTableUI.MouseInputHandler;
import javax.swing.text.html.CSS;

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
			case "��ɫ":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ��ɫ�Ķ�����Ŀ!");
					break;
				}
				table.getSelectBean().setColor("hi-blue");
				table.getSelectBean().save();
				break;
			case "��ɫ":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ��ɫ�Ķ�����Ŀ!");
					break;
				}
				table.getSelectBean().setColor("hi-green");
				table.getSelectBean().save();
				break;
			case "��ɫ":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ��ɫ�Ķ�����Ŀ!");
					break;
				}
				table.getSelectBean().setColor("hi-orange");
				table.getSelectBean().save();
				break;
			case "ȥɫ":

				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫȥɫ�Ķ�����Ŀ!");
					break;
				}
				table.getSelectBean().setColor(null);
				table.getSelectBean().save();
				break;
			case "�½�����":
				new BillFrame(new Bill());
				break;
			case "��������":
				editBackRepair();
				break;

			case "�޸Ķ���":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
					break;
				}
				if (table.getSelectBean().isItemComplete()
						&& User.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
					break;
				}
				new BillFrame(table.getSelectBean());
				break;
			case "ɾ������":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫɾ���Ķ�����Ŀ!");
					break;
				}
				if (JOptionPane.showConfirmDialog(table, "ȷ��Ҫɾ��������"
						+ table.getSelectBean().getBillid() + "����", "ɾ��ȷ��",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					table.removeSelectRow();
				break;

			case "�����ͻ�����":
				Vector<AbstractCustom> customs =new Custom().LoadAll();
				if(customs.size()==0) customs.add(new Custom());
				adminCustom(customs);
				break;
			case "��Э�ͻ�����":
				Vector<AbstractCustom> outCustoms =new OutCustom().LoadAll();
				if(outCustoms.size()==0) outCustoms.add(new OutCustom());
				adminCustom(outCustoms);
				break;
			case "��������":
				try {
					((BeanTableModel<Bill>)table.getTable().getModel()).exportToExl("������Ϣ");
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				break;
			case "�޸�����":
				final ChangePasswdPanel userPanel = new ChangePasswdPanel(User.loginUser);
				BeanDialog<User> userDialog=new BeanDialog<User>(userPanel,"�޸��û�����") {

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

			case "����":
				new About().setVisible(true);
				break;
			case "������ù���":
				try {
					operationManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				break;
			case "�Ų��ƻ�":
				try {
					planCostManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				break;
			case "�����ɹ�":
				try {
					try {
						operCostManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				break;
			case "������Ա����":
				try {
					try {
						employeeManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				break;
			case "�¶�ͳ��":
				monStatistic();
				break;
			case "���ͳ��":
				yearStatistic();
				break;
			default:
				break;
			}

	}

	private void operCostManager() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
			return;
		}	
		if (table.getSelectBean().getStatus().equals("δ�Ų�")) {
			JOptionPane.showMessageDialog(table, "��δ���������ƻ������ܰ�����������!");
			return;
		}
		OperCostPnl beanPanel = new OperCostPnl(new OperCost(table.getSelectBean()));
		BeansPanel<OperCost> panel = new BeansPanel<OperCost>(OperCost.loadAllByBill(table.getSelectBean()),beanPanel,true) {
			@Override
			public void reEditRow() {
				OperCost operCost = getTablePane().getSelectBean();
				if(operCost.isComplete()) {
					JOptionPane.showMessageDialog(this, "����"+operCost.getOperationName()+"���Ѿ���ɣ������ٸ��ģ�","����",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.reEditRow();
			}

			@Override
			public void removeSelectRow() {
				OperCost operCost = getTablePane().getSelectBean();
				if(operCost.isComplete()) {
					JOptionPane.showMessageDialog(this, "����"+operCost.getOperationName()+"���Ѿ�����ɣ�����ɾ����","����",JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(getBeanPanel(), "��Ʒ+����Ʒ>�������� ����ȷ�ϳ�Ʒ������Ʒ�����Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<OperCost> dialog=new BeanDialog<OperCost>(panel,"�����ɹ�") {

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
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
			return;
		}
		PlanCostPnl beanPanel = new PlanCostPnl(new PlanCost(table.getSelectBean()));

		final BeansPanel<PlanCost> panel = new BeansPanel<PlanCost>(PlanCost.loadAllByBill(table.getSelectBean()),beanPanel,true) {

			@Override
			public void reEditRow() {
				PlanCost planCost = getTablePane().getSelectBean();
				if(planCost.hasOper()) {
					JOptionPane.showMessageDialog(this, "����"+planCost.getOperationName()+"���Ѿ��������У������ٸ��ģ�","����",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				super.reEditRow();
			}

			@Override
			public void removeSelectRow() {
				PlanCost planCost = getTablePane().getSelectBean();
				if(planCost.hasOper()) {
					JOptionPane.showMessageDialog(this, "����"+planCost.getOperationName()+"���Ѿ��������У�����ɾ����","����",JOptionPane.ERROR_MESSAGE);
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
								JOptionPane.showMessageDialog(getBeanPanel(), "����"+newbean.getOperationName()+"���Ѿ��ڼƻ��б��У���������ӣ�","����",JOptionPane.ERROR_MESSAGE);
								return ;
							}
							if(newbean.equals(bean))
								try {
									BeanUtils.copyProperties(bean, newbean);
								} catch (IllegalAccessException
										| InvocationTargetException e) {
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								}
						}
						if(newbean.getSequence()==0)
							newbean.setSequence(getTablePane().getTable().getRowCount()+1);
						getTablePane().addNew(newbean);
						setPanelBean(new PlanCost(newbean.getBill()));
					} catch (IllegalArgumentException
							| SecurityException e) {
						// TODO �Զ����ɵ� catch ��
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

		JMenuItem addOper = new JMenuItem("�����ɹ�");
		addOper.addActionListener(this);
		panel.getTablePane().getPopupMenu().add(addOper, 0);

		BeanDialog<PlanCost> dialog=new BeanDialog<PlanCost>(panel,"�����ɹ�") {

			@Override
			public boolean okButtonAction() {
				for(int i=0;i<getBeans().size();i++){
					PlanCost ps= getBeans().get(i);
					if(ps.hasOper())continue;
					for(PlanCost bean: getBeans()){
						if(ps!=bean&& bean.getOperationName().equals(ps.getOperationName())){
							JOptionPane.showMessageDialog(this, "����"+ps.getOperationName()+"���ƻ��б������ظ������ύ�޸��ٰ��˰�ť��","����",JOptionPane.ERROR_MESSAGE);
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
						// TODO �Զ����ɵ� catch ��
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<Employee> dialog=new BeanDialog<Employee>(panel,"������Ա����") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<Operation> dialog=new BeanDialog<Operation>(panel,"�����������") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
				return true;
			}

		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	private void yearStatistic() {

		SqlFrame("select   concat(year(itemcompletedate) ,'���') ���,custom ��λ,sum(num) ����,sum(reportprice*num) �ܼ� from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'���'),custom union select   concat(year(itemcompletedate) ,'��� �ܼ�') ���,null ��λ,sum(num) ����,sum(reportprice*num) �ܼ� from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'��� �ܼ�') order by ��� desc", "���ͳ��");

	}

	private void monStatistic() {
		SqlFrame("select   concat(year(itemcompletedate) ,'��',month(itemcompletedate),'��') �·�,custom ��λ,sum(num) ����,sum(reportprice*num) �ܼ� from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'��',month(itemcompletedate),'��'),custom union select   concat(year(itemcompletedate) ,'��',month(itemcompletedate),'�� �ܼ�') �·�,null ��λ,sum(num) ����,sum(reportprice*num) �ܼ� from bill where itemcompletedate is not null group by concat(year(itemcompletedate),'��',month(itemcompletedate),'�� �ܼ�') order by �·� desc", "�¶�ͳ��");
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
			// TODO �Զ����ɵ� catch ��
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
				// TODO �Զ����ɵķ������
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

		BeanDialog<BackRepair> dialog=new BeanDialog<BackRepair>(beansPanel,"���޼�¼") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
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

		BeanDialog<AbstractCustom> dialog=new BeanDialog<AbstractCustom>(beansPanel2,"�����ͻ�����") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
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
