package ui;

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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ui.costPanes.BillTimeTable;
import ui.costPanes.EmployeeCostPnl;
import ui.costPanes.EmployeePnl;
import ui.costPanes.OperationPnl;
import ui.costPanes.PlanTable;
import ui.costPanes.PlanWorkPnl;
import ui.costPanes.ShowWorkCostPnl;
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

import com.mao.jf.beans.AbstractCustom;
import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OutCustom;
import com.mao.jf.beans.Plan;
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
						&& Userman.loginUser.getLevel() > 0) {
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
			case "�����ƻ���ʵ�ʳɱ�����":
				showWorkCostPanel();
				break;
			case "��������":
				try {
					((BeanTableModel<Bill>)table.getTable().getModel()).exportToExl("������Ϣ");
				} catch (IOException e1) {
					// TODO �Զ������� catch ��
					e1.printStackTrace();
				}
				break;
			case "�޸�����":
				final ChangePasswdPanel userPanel = new ChangePasswdPanel(Userman.loginUser);
				BeanDialog<Userman> userDialog=new BeanDialog<Userman>(userPanel,"�޸��û�����") {

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
					// TODO �Զ������� catch ��
					e1.printStackTrace();
				}
				break;

			case "�鿴������":
				showBillGroup();
				break;
			case "Ա������ͳ��":
				showEmployeeCost();
				break;
			case "�������ͳ��":
				showWorkCost();
				break;
			case "������Ա����":
				try {
					try {
						employeeManager();
					} catch (InstantiationException | IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| IntrospectionException e1) {
						// TODO �Զ������� catch ��
						e1.printStackTrace();
					}
				} catch (IllegalArgumentException | SecurityException e1) {
					// TODO �Զ������� catch ��
					e1.printStackTrace();
				}
				break;
			case "�¶�ͳ��":
				monStatistic();
				break;
			case "�Ų�ʱ��鿴":
				showBillTime();
				break;
			case "���ͳ��":
				yearStatistic();
				break;
			case "�����ɱ�ͳ��":
				planworkShow();
				break;
			case "�����ƻ�����":
				planProduct();
				break;
			case "�������Ϲ���":
				materialManager();
				break;
			case "�����������":
				workManager();
				break;
			case "�û�����":
				try {
					manageUser();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			default:
				break;
			}

	}

	private void showBillTime() {
		try {
			JDialog dialog=new JDialog();

			BillTimeTable table;
			table = new BillTimeTable();
			JPanel panel=new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(new JScrollPane(table),BorderLayout.CENTER);
			dialog.setContentPane(panel);
			dialog.setTitle("�Ų�ʱ��鿴");

			dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
			dialog.setLocationRelativeTo(null);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setVisible(true);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
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
		BeansPanel<Userman> beansPanel=new BeansPanel<Userman>(Userman.loadAll(Userman.class),new UsermanPnl(new Userman()),Userman.class) {

			@Override
			public Userman saveBean() {
				try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException | IntrospectionException
						| SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				return null;
			}

		};
		BeanDialog< Userman> dialog=new BeanDialog<Userman>(beansPanel,"�û�����") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
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
		dialog.setTitle("�������");
		dialog.setContentPane(new WorkCreatePanel());
		dialog.setBounds(100, 100, 500, 500);
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}
	private void showWorkCostPanel(){
		JDialog dialog=new JDialog();
		dialog.setTitle("�������");
		dialog.setContentPane(new ShowWorkCostPnl());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}
	private void materialManager() {
		JDialog dialog=new JDialog();
		MaterialsPanel materialsPanel=new MaterialsPanel(table.getSelectBean()) ;
		dialog.setContentPane(materialsPanel);
		dialog.setTitle("�������Ϲ���");

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
		//		JButton Okbt = new JButton(new AbstractAction("ȷ��") {
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
		//		dialog.setTitle("�������Ϲ���");
		//		dialog.getContentPane().add( scroll,BorderLayout.CENTER );
		//		dialog.getContentPane().add(btPanel,BorderLayout.SOUTH);
		//
		//		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		//		dialog.setLocationRelativeTo(null);
		//		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		//		dialog.setVisible(true);

	}

	private void planProduct() {
		final PlanTable table = new PlanTable();
		JScrollPane scroll = new JScrollPane( table );
		final JDialog  dialog=new JDialog();

		dialog.getContentPane().add( scroll,BorderLayout.CENTER );
		dialog.setTitle("�����ƻ�����");
		JPanel btPanel=new JPanel();
		btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));

		JButton Okbt = new JButton(new AbstractAction("ȷ��") {

			@Override
			public void actionPerformed(ActionEvent e) {
				table.save();
				dialog.dispose();
			}
		});
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(1, 1, 1, 1));
		dialog.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel label = new JLabel("\u56FE\u53F7\uFF1A");
		panel_2.add(label);

		final JTextField textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);

		JButton searchBt = new JButton("\u67E5\u627E");
		panel_2.add(searchBt);
		searchBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setPlans(Plan.loadUnCompletedByPicId(textField.getText()));

			}
		});
		JButton addBt=new JButton("�����Ų��ƻ�");
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
		BeansPanel<Employee> panel = new BeansPanel<Employee>(Employee.loadAll(Employee.class),beanPanel,Employee.class,true) {

			@Override
			public Employee saveBean() {
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
		BeanDialog<Employee> dialog=new BeanDialog<Employee>(panel,"������Ա����") {

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
				try {
					getPanelBean().save();
					return getPanelBean();
				} catch (IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException | SQLException e) {
					// TODO �Զ������� catch ��
					e.printStackTrace();
				}
				return null;
			}
		};
		BeanDialog<Operation> dialog=new BeanDialog<Operation>(panel,"�����������") {

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
		BeansPanel<BackRepair> beansPanel=new BeansPanel<BackRepair>(backRepairs,new BackRepairPanel(backRepair1),BackRepair.class,true) {

			@Override
			public BackRepair saveBean() {
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

		BeanDialog<BackRepair> dialog=new BeanDialog<BackRepair>(beansPanel,"���޼�¼") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

	}

	public static void adminCustom(Vector<AbstractCustom>customs) {
		BeansPanel<AbstractCustom> beansPanel2=new BeansPanel<AbstractCustom>(customs.firstElement().LoadAll(),new CustomPanel(customs.firstElement()),AbstractCustom.class,true) {

			@Override
			public AbstractCustom saveBean() {
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
				return true;
			}
		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);
	}



}