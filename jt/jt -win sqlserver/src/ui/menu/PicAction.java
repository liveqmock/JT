package ui.menu;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import ui.costPanes.PlanCreatePanel;
import ui.costPanes.WorkCreatePanel;
import ui.customComponet.BeanDialog;
import ui.customComponet.BeanTablePane;
import ui.customComponet.BeansPanel;
import ui.customComponet.RsTablePane;
import ui.frames.BillFrame;
import ui.panels.BackRepairPanel;
import ui.panels.CheckPnl;
import ui.panels.FpOutPanel;
import ui.panels.FpPanel;
import ui.panels.MaterialsPanel;
import ui.panels.PicPanel;
import ui.panels.PicView;
import ui.panels.ShipingPanel;
import ui.panels.SpecialPnl;

import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.FpBean;
import com.mao.jf.beans.OutFpBean;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.ShipingBean;
import com.mao.jf.beans.Userman;

public class PicAction extends AbstractAction {
	private BeanTablePane<PicBean> table;
	public PicAction(BeanTablePane<PicBean> table) {
		super();
		this.table=table;
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
				new BillFrame(new BillBean());
				break;
			case "����":
				editBackRepair();
				break;
			case "��ӷ�Ʊ��Ϣ":
				editFp();
				break;
			case "��ӷ�����Ϣ":
				editShiping();
				break;
			case "�����Э��Ʊ��Ϣ":
				editOutFp();
				break;
			case "�鿴ͼֽ":
				showPic();
				break;
			case "�ز��趨":
				specialManager();
				break;
			case "��������":
				checkManager();
				break;

			case "�޸Ķ���":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
					break;
				}
				if (table.getSelectBean().getBill().isComplete()
						&& Userman.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
					break;
				}
				new BillFrame(table.getSelectBean().getBill());
				break;

			case "��־��ͼֽ�����":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
					break;
				}
				if (table.getSelectBean().isComplete()) {
					JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
					break;
				}
				if(JOptionPane.showConfirmDialog(null, "ȷ��ͼֽ["+table.getSelectBean().getPicid()+"]�Ѿ����?","���ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					table.getSelectBean().setComplete(true);
				}
				break;
			case "�޸�ͼֽ":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
					break;
				}
				if (table.getSelectBean().isComplete()
						&& Userman.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
					break;
				}
				final PicPanel picPanel=new PicPanel(table.getSelectBean());
				BeanDialog<PicBean> dialog=new BeanDialog<PicBean>(picPanel,"�޸�ͼֽ") {

					@Override
					public boolean okButtonAction() {
						picPanel.saveBill();
						return true;
					}
				};
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				break;
			case "ɾ��ͼֽ":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "δѡ��Ҫɾ���Ķ�����Ŀ!");
					break;
				}
				if (JOptionPane.showConfirmDialog(table, "ȷ��Ҫɾ��ͼ�š�"
						+ table.getSelectBean().getPicid() + "����", "ɾ��ȷ��",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					table.removeSelectRow();
					
				break;
			case "�鿴�Ų�":
				showPlan();
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
			
			default:
				JOptionPane.showMessageDialog(null, "û�д˲���");
				break;
			}

	}

	private void checkManager() {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����!");
			return;
		}
		 PicBean bean=table.getSelectBean();
		
		BeanDialog<PicBean> dialog =new BeanDialog<PicBean>(new CheckPnl(bean),"��������") {
			
			@Override
			public boolean okButtonAction() {
				BeanMao.saveBean(getBean());
				return true;
			}
		};
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
	}

	private void specialManager() {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����!");
			return;
		}
		 PicBean bean=table.getSelectBean();
		
		BeanDialog<PicBean> dialog =new BeanDialog<PicBean>(new SpecialPnl(bean),"�ز��趨") {
			
			@Override
			public boolean okButtonAction() {
				getBean().setSpecialUser(Userman.loginUser.getName());
				BeanMao.saveBean(getBean());
				return true;
			}
		};
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
	}

	private void showPic() {
		
		PicBean pic=table.getSelectBean();
		if (pic == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����Ŀ!");
			return;
		}
		if(StringUtils.isNoneBlank(pic.getImageUrl())){
			
			JDialog dialog=new JDialog();
			PicView imageView=new PicView();
			try{
				imageView.showFile(pic.getImageUrl());
			}catch(Exception e){
				e.printStackTrace();
			}
			dialog.setContentPane(imageView);
			dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
			
			dialog.setLocationRelativeTo(null);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setVisible(true);
			
		}
		
	}

	private void editFp() {
		FpBean fpBean=new FpBean();
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����!");
			return;
		}
		final BillBean bean=table.getSelectBean().getBill();
		
		fpBean.setBill(bean);
		BeansPanel<FpBean> panel=new BeansPanel<FpBean>(bean.getFpBeans(),new FpPanel(fpBean),FpBean.class) {

			@Override
			public FpBean saveBean() {
				getPanelBean().setInputUser(Userman.loginUser);
				BeanMao.saveBean(getPanelBean());
				return getPanelBean();
			}

			@Override
			protected FpBean createNewBean() {
				FpBean fpBean=new FpBean();
				fpBean.setBill(bean);
				return fpBean;
			}
			
		};
		
		BeanDialog<FpBean> dialog =new BeanDialog<FpBean>(panel,"��Ʊ����") {
			
			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(100, 100, 500,500);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		
		
	}
	private void editOutFp() {
		OutFpBean fpBean=new OutFpBean();
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵĶ�����!");
			return;
		}
		final PicBean bean=table.getSelectBean();
		
		fpBean.setPic(bean);
		BeansPanel<OutFpBean> panel=new BeansPanel<OutFpBean>(bean.getFpOutBeans(),new FpOutPanel(fpBean),OutFpBean.class) {

			@Override
			public OutFpBean saveBean() {
				getPanelBean().setInputUser(Userman.loginUser);
				BeanMao.saveBean(getPanelBean());
				return getPanelBean();
			}

			@Override
			protected OutFpBean createNewBean() {
				OutFpBean fpBean=new OutFpBean();
				fpBean.setPic(bean);
				return fpBean;
			}
			
		};
		
		BeanDialog<OutFpBean> dialog =new BeanDialog<OutFpBean>(panel,"��Э��Ʊ����") {
			
			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(100, 100, 500,500);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		
		
	}

	private void editShiping() {
		
		
		final PicBean picBean=table.getSelectBean();
		if (picBean == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵ�ͼֽ��!");
			return;
		}
		if (table.getSelectBean().isComplete()) {
			JOptionPane.showMessageDialog(table, "�ö����Ѿ���ɣ��������޸�!");
			return;
		}
		ShipingBean shipingBean=new ShipingBean();
		shipingBean.setPic(picBean);
		BeansPanel<ShipingBean> panel=new BeansPanel<ShipingBean>(picBean.getShipingBeans(),new ShipingPanel(shipingBean),ShipingBean.class) {

			@Override
			public ShipingBean saveBean() {
				getPanelBean().setCreateUser(Userman.loginUser);
				BeanMao.saveBean(getPanelBean());
				return getPanelBean();
			}

			@Override
			protected ShipingBean createNewBean() {
				ShipingBean shipingBean=new ShipingBean();
				shipingBean.setPic(picBean);
				return shipingBean;
			}
			
		};
		
		BeanDialog<ShipingBean> dialog =new BeanDialog<ShipingBean>(panel,"��Ʊ����") {
			
			@Override
			public boolean okButtonAction() {
				return true;
			}
		};
		dialog.setBounds(100, 100, 500,500);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		
		
	}



	private void addColor(String color) {
		if (table.getTable().getSelectedRows().length==0) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ��ɫ�Ķ�����Ŀ!");
			return;
		}
		for(int row:table.getTable().getSelectedRows()){
			table.getSelectBean(row).setColor(color);
			BeanMao.saveBean( table.getSelectBean());
		}

	}

	private void showPlan() {

	}

	
	private void materialManager() {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫɾ���Ķ�����Ŀ!");
			return;
		}
		JDialog dialog=new JDialog();
		MaterialsPanel materialsPanel=new MaterialsPanel(table.getSelectBean()) ;
		dialog.setContentPane(materialsPanel);
		dialog.setTitle("�������Ϲ���");

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

		JFrame dialog=new JFrame();
		dialog.setTitle("��������¼��");
		dialog.setContentPane(new WorkCreatePanel());
		dialog.setExtendedState(Frame.MAXIMIZED_BOTH );
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

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
		final PicBean pic=table.getSelectBean();
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "δѡ��Ҫ�޸ĵ�ͼֽ!");
			return ;
		}
		BeansPanel<BackRepair> beansPanel=new BeansPanel<BackRepair>(pic.getBackRepairs(),new BackRepairPanel(null),BackRepair.class,true) {

			@Override
			public BackRepair saveBean() {
				BackRepair backRepair=getPanelBean();

				backRepair.save();

				return backRepair;
			}
			@Override
			public BackRepair createNewBean() {
				BackRepair backRepair=new BackRepair();
				backRepair.setPic(pic);
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



}
