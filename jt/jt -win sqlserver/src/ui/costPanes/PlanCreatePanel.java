package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.customComponet.BeanDialog;
import ui.customComponet.BeanTableModel;
import ui.customComponet.BeanTablePane;
import ui.panels.PicShowPnl;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.PicPlan;

public class PlanCreatePanel extends PicShowPnl {

	private BeanTablePane<PicBean> picTable;
	private PlanPnl planPnl;
	private JSplitPane splitPane;
	private BeanTablePane<PicPlan> plansTablePane;
	private JButton newPlanBt;

	public PlanCreatePanel() {
		super();

		splitPane=new JSplitPane();

		picTable=new BeanTablePane<PicBean>(null, PicBean.class,new String[]{"客户","订单号","项目号","图号","客户订单号","数量"});

		splitPane.setLeftComponent(picTable);
		planPnl=new PlanPnl(new PicPlan(new PicBean()));
		plansTablePane=new BeanTablePane<PicPlan>(null, PicPlan.class);
		plansTablePane.setPreferredSize(new Dimension(400, 80));
		
		newPlanBt = new JButton("新增排产计划");
		JPanel plansPanel=new JPanel();
		plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));
		plansTablePane.setBorder(new TitledBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED), "排产计划表", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		plansPanel.add(plansTablePane);
		plansPanel.add(newPlanBt);
		plansPanel.add(planPnl);

		splitPane.setRightComponent(plansPanel);
		splitPane.setDividerLocation(400);
		add(splitPane,BorderLayout.CENTER);
		picTable.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					billItemSelectAction();
				}

			}

		});
		plansTablePane.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					planItemSelectAction();
				}

			}

		});
		newPlanBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewPlan();
			}

			
		});
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				switch (item.getText()) {
				case "删除排产计划":

					plansTablePane.removeSelectRow();

					break;
				case "新增排产计划":

					addNewPlan();

					break;

				default:
					break;
				}
			}

			

		};
		plansTablePane.getPopupMenu().add("删除排产计划").addActionListener(listener);
		plansTablePane.getPopupMenu().add("新建排产计划").addActionListener(listener);
	}

	@Override
	public void searchAction(String search) {
		picTable.setBeans(BillBean.SearchPics(search));
	}

	public void billItemSelectAction() {
		PicBean bean = picTable.getSelectBean();
		Collection<PicPlan> plans = bean.getPlans();
		plansTablePane.setBeans( plans);	
		if(plans!=null&&plans.size()>0){
			plansTablePane.getTable().setRowSelectionInterval(0,0);
			planItemSelectAction();
		}else
			planPnl.setBean(null);
		
	}
	public void planItemSelectAction() {
		PicPlan plan = plansTablePane.getSelectBean();
		planPnl.setBean(plan);

	}
	private void addNewPlan() {
		try{
			final PicBean pic = picTable.getSelectBean();
			if(pic==null)return;
			PicPlan plan = new PicPlan(pic);
			plan.setOperationPlans(new TreeSet<OperationPlan>());
			BeanDialog<PicPlan> dialog=new BeanDialog<PicPlan>(new PicPlanPnl(plan),"新增排产计划") {

				@Override
				public boolean okButtonAction() {
					getBean().save();
					PicPlan picPlan=getBean();
					((BeanTableModel<PicPlan>) plansTablePane.getTable().getModel()).insertRow(picPlan);
					planPnl.setBean(picPlan);
					return true;
				}
			};
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(newPlanBt);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
	}
}
