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
			case "�½�����":
				new BillFrame(new BillBean());
				break;
			case "�鿴�豸ʹ�����":
				showEquipmentUsing();
				break;

			case "�޸Ķ���":
				//				if (table.getSelectBean() == null) {
				//					JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
				//					break;
				//				}
				//				if (table.getSelectBean().isItemComplete()
				//						&& Userman.loginUser.getLevel() > 0) {
				//					JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
				//					break;
				//				}
				//				new BillFrame(table.getSelectBean());
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

			case "�޸�����":
				changePasswd();
				break;
			case "�����ڴ�":
				clearMemery();
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
			case "�¶�ͳ��":
				monStatistic();
				break;
			case "�鿴�Ų����":
				showBillTime();
				break;
			case "���ͳ��":
				yearStatistic();
				break;
			case "�����ɱ�ͳ��":
				//planworkShow();
				break;
			case "�Ų��ƻ�¼��":
				planProduct();
				break;
			case "��������¼��":
				workManager();
				break;
			case "�豸����":
				try {
					operationManager();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e2) {
					// TODO �Զ����ɵ� catch ��
					e2.printStackTrace();
				}
				break;
			case "���������":
				SqlFrame("select * from planWorkDetail", "���������");
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

	public void clearMemery() {
		
		System.gc();
	}
	private void showEquipmentUsing() {
		String sql = "select a.NAME �豸����,a.code �豸���, endtime �ƻ��깤ʱ�� from "
				+ "dbo.equipment a join operation b on a.operation=b.id left "
				+ "join (Select equipment,max(planEndTime) endtime from dbo.equipmentplan  "
				+ "group by equipment) c on a.id=c.equipment order by a.name,code";
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
		BeanDialog< Userman> dialog=new BeanDialog<Userman>(beansPanel,"�û�����") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
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
		dialog.setTitle("�������");
		dialog.setContentPane(new ShowWorkCostPnl());
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}


	private void planProduct() {
		JFrame dialog=new JFrame();
		dialog.setTitle("�Ų��ƻ�¼��");
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
		dialog.setTitle("��������¼��");
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

		JDialog dialog=new JDialog();
		dialog.setTitle("�豸����");
		EquipmentManagerPnl equipmentManagerPnl=new EquipmentManagerPnl();
		dialog.setContentPane(equipmentManagerPnl);
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
