package ui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
			case "��ɫ":
				
				addColor("hi-blue");
				break;
			case "��ɫ":
				addColor("hi-green");
				break;
			case "��ɫ":

				addColor("hi-orange");
				break;
			case "ȥɫ":
				addColor(null);
				break;
			case "�½�����":
				new BillFrame(new Bill());
				break;
			case "��������":
				editBackRepair();
				break;
			case "�鿴�豸ʹ�����":
				showEquipmentUsing();
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
				adminCustom(0);
				break;
			case "��Э�ͻ�����":
				adminCustom(1);
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
				changePasswd();
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

				employeeManager();

				break;
			case "�鿴δ��ʼ���Ų��ƻ�":
				showUnStartPlan();
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
			case "�Ų��ƻ�¼��":
				planProduct();
				break;
			case "�������Ϲ���":
				materialManager();
				break;
			case "��������¼��":
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

	private void showEquipmentUsing() {
		String sql = "select NAME �豸���,a.code �豸���, endtime �ƻ��깤ʱ�� from "
				+ "dbo.equipment a join operation b on a.operation=b.id left "
				+ "join (Select equipment,max(planEndTime) endtime from dbo.equipmentplan  "
				+ "group by equipment) c on a.id=c.equipment order by name,code";
		SqlFrame(sql, "�豸ʹ�����");
	}

	private void changePasswd() {
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


		
	}

	private void addColor(String color) {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ��ɫ�Ķ�����Ŀ!");
			return;
		}
		table.getSelectBean().setColor(color);
		table.getSelectBean().save();
		
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
		BeansPanel<Userman> beansPanel=new BeansPanel<Userman>(BeanMao.getBeans(Userman.class),new UsermanPnl(new Userman()),Userman.class) {

			@Override
			public Userman saveBean() {

				BeanMao.saveBean(getPanelBean());
				return getPanelBean();

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

	
	private void showWorkCostPanel(){
		JDialog dialog=new JDialog();
		dialog.setTitle("�������");
		dialog.setContentPane(new ShowWorkCostPnl());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}
	private void showUnStartPlan(){
		JDialog dialog=new JDialog();
		dialog.setTitle("δ��ʼ���Ų��ƻ�");
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
		JFrame dialog=new JFrame();
		dialog.setTitle("�Ų��ƻ�¼��");
		dialog.add(new PlanCreatePanel());
		dialog.setExtendedState(JFrame.MAXIMIZED_BOTH );
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}
private void workManager() {
		
		JFrame dialog=new JFrame();
		dialog.setTitle("��������¼��");
		dialog.setContentPane(new WorkCreatePanel());
		dialog.setExtendedState(JFrame.MAXIMIZED_BOTH );
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}
	private void showBillGroup() {

		table.setBeans((Collection<Bill>) Bill.loadByGrp(table.getSelectBean().getBillgroup()));
	}




	private void employeeManager()  {
		EmployeePnl beanPanel = new EmployeePnl(new Employee());
		BeansPanel<Employee> panel = new BeansPanel<Employee>(Employee.getBeans(Employee.class),beanPanel,Employee.class,true) {

			@Override
			public Employee saveBean() {
				getPanelBean().save();
				return getPanelBean();

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
		BeansPanel<Operation> panel = new BeansPanel<Operation>(Operation.getBeans(Operation.class),beanPanel,Operation.class,true) {


			@Override
			public Operation saveBean() {
				getPanelBean().save();
				return getPanelBean();

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
		String sql = "select ���,��λ,sum(����) ,sum(�ܼ�) from ("
				+"select   str(year(itemcompletedate)) +'��' ���,custom ��λ,num ����,reportprice*num �ܼ� from bill where itemcompletedate is not null  union "
				+"select   str(year(itemcompletedate)) +'��' ���,' �ϼ�' ��λ,num ����,reportprice*num �ܼ� from bill where itemcompletedate is not null) a "
				+"group by ���,��λ order by ��� desc,��λ ";
		SqlFrame(sql, "���ͳ��");

	}

	private void monStatistic() {
		String sql = "select �·�,��λ,sum(����) ,sum(�ܼ�) from ("
				+"select   str(year(itemcompletedate)) +'��'+str(month(itemcompletedate))+'��' �·�,custom ��λ,num ����,reportprice*num �ܼ� from bill where itemcompletedate is not null  union "
				+"select   str(year(itemcompletedate)) +'��'+str(month(itemcompletedate))+'��' �·�,' �ϼ�' ��λ,num ����,reportprice*num �ܼ� from bill where itemcompletedate is not null) a "
				+"group by �·�,��λ order by �·� desc,��λ ";
		SqlFrame(sql,"�¶�ͳ��");
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

		BeanDialog<BackRepair> dialog=new BeanDialog<BackRepair>(beansPanel,"���޼�¼") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

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

		BeanDialog<Custom> dialog=new BeanDialog<Custom>(beansPanel,"�����ͻ�����") {

			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);
	}



}
