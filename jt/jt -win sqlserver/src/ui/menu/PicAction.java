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
			case "蓝色":

				addColor("hi-blue");
				break;
			case "绿色":
				addColor("hi-green");
				break;
			case "橙色":

				addColor("hi-orange");
				break;
			case "去色":
				addColor(null);
				break;
			case "新建订单":
				new BillFrame(new BillBean());
				break;
			case "返修":
				editBackRepair();
				break;
			case "添加发票信息":
				editFp();
				break;
			case "添加发货信息":
				editShiping();
				break;
			case "添加外协发票信息":
				editOutFp();
				break;
			case "查看图纸":
				showPic();
				break;
			case "特采设定":
				specialManager();
				break;
			case "检验数据":
				checkManager();
				break;

			case "修改订单":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
					break;
				}
				if (table.getSelectBean().getBill().isComplete()
						&& Userman.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
					break;
				}
				new BillFrame(table.getSelectBean().getBill());
				break;

			case "标志此图纸已完结":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
					break;
				}
				if (table.getSelectBean().isComplete()) {
					JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
					break;
				}
				if(JOptionPane.showConfirmDialog(null, "确定图纸["+table.getSelectBean().getPicid()+"]已经完成?","完成确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					table.getSelectBean().setComplete(true);
				}
				break;
			case "修改图纸":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
					break;
				}
				if (table.getSelectBean().isComplete()
						&& Userman.loginUser.getLevel() > 0) {
					JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
					break;
				}
				final PicPanel picPanel=new PicPanel(table.getSelectBean());
				BeanDialog<PicBean> dialog=new BeanDialog<PicBean>(picPanel,"修改图纸") {

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
			case "删除图纸":
				if (table.getSelectBean() == null) {
					JOptionPane.showMessageDialog(table, "未选择要删除的订单条目!");
					break;
				}
				if (JOptionPane.showConfirmDialog(table, "确定要删除图号【"
						+ table.getSelectBean().getPicid() + "】吗？", "删除确认",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					table.removeSelectRow();
					
				break;
			case "查看排产":
				showPlan();
				break;
			case "排产计划录入":
				planProduct();
				break;
			case "生产材料管理":
				materialManager();
				break;
			case "生产工序录入":
				workManager();
				break;
			
			default:
				JOptionPane.showMessageDialog(null, "没有此操作");
				break;
			}

	}

	private void checkManager() {
		if (table.getSelectBean() == null) {
			JOptionPane.showMessageDialog(table, "未选择要修改的订单项!");
			return;
		}
		 PicBean bean=table.getSelectBean();
		
		BeanDialog<PicBean> dialog =new BeanDialog<PicBean>(new CheckPnl(bean),"检验数据") {
			
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
			JOptionPane.showMessageDialog(table, "未选择要修改的订单项!");
			return;
		}
		 PicBean bean=table.getSelectBean();
		
		BeanDialog<PicBean> dialog =new BeanDialog<PicBean>(new SpecialPnl(bean),"特采设定") {
			
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
			JOptionPane.showMessageDialog(table, "未选择要修改的订单条目!");
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
			JOptionPane.showMessageDialog(table, "未选择要修改的订单项!");
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
		
		BeanDialog<FpBean> dialog =new BeanDialog<FpBean>(panel,"发票管理") {
			
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
			JOptionPane.showMessageDialog(table, "未选择要修改的订单项!");
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
		
		BeanDialog<OutFpBean> dialog =new BeanDialog<OutFpBean>(panel,"外协发票管理") {
			
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
			JOptionPane.showMessageDialog(table, "未选择要修改的图纸项!");
			return;
		}
		if (table.getSelectBean().isComplete()) {
			JOptionPane.showMessageDialog(table, "该订单已经完成，不能再修改!");
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
		
		BeanDialog<ShipingBean> dialog =new BeanDialog<ShipingBean>(panel,"发票管理") {
			
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
			JOptionPane.showMessageDialog(table, "未选择要加色的订单条目!");
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
			JOptionPane.showMessageDialog(table, "未选择要删除的订单条目!");
			return;
		}
		JDialog dialog=new JDialog();
		MaterialsPanel materialsPanel=new MaterialsPanel(table.getSelectBean()) ;
		dialog.setContentPane(materialsPanel);
		dialog.setTitle("生产材料管理");

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

		JFrame dialog=new JFrame();
		dialog.setTitle("生产工序录入");
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
			JOptionPane.showMessageDialog(table, "未选择要修改的图纸!");
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

		BeanDialog<BackRepair> dialog=new BeanDialog<BackRepair>(beansPanel,"返修记录") {

			@Override
			public boolean okButtonAction() {
			
				return true;
			}
		};

		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setVisible(true);

	}



}
