package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

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

import ui.customComponet.BeanTableModel;
import ui.customComponet.BeanTablePane;
import ui.panels.BillShowPnl;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.BillPlan;

public class PlanCreatePanel extends BillShowPnl {

	private BeanTablePane<Bill> billTable;
	private PlanPnl planPnl;
	private JSplitPane splitPane;
	private BeanTablePane<BillPlan> plansTablePane;

	public PlanCreatePanel() {
		super();

		splitPane=new JSplitPane();

		billTable=new BeanTablePane(null, Bill.class);

		splitPane.setLeftComponent(billTable);
		planPnl=new PlanPnl(new BillPlan(new Bill()));
		plansTablePane=new BeanTablePane<BillPlan>(null, BillPlan.class);
		plansTablePane.setPreferredSize(new Dimension(400, 150));
		
		JButton newPlanBt = new JButton("新增排产计划");
		JPanel plansPanel=new JPanel();
		plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));
		plansTablePane.setBorder(new TitledBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED), "排产计划表", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		plansPanel.add(plansTablePane);
		plansPanel.add(newPlanBt);
		plansPanel.add(planPnl);

		splitPane.setRightComponent(plansPanel);
		splitPane.setDividerLocation(400);
		add(splitPane,BorderLayout.CENTER);
		billTable.getTable().getSelectionModel()
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

					removeSelectRow();

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
		billTable.setBeans(Bill.getBeans(Bill.class,search));
	}

	public void billItemSelectAction() {
		Bill bill = billTable.getSelectBean();
		Collection<BillPlan> plans = bill.getPlans();
		plansTablePane.setBeans( plans);	
		if(plans!=null&&plans.size()>0){
			plansTablePane.getTable().setRowSelectionInterval(0,0);
			planItemSelectAction();
		}else
			planPnl.setBean(null);
		
	}
	public void planItemSelectAction() {
		BillPlan plan = plansTablePane.getSelectBean();
		planPnl.setBean(plan);

	}
	private void addNewPlan() {
		try{
			Bill bill = billTable.getSelectBean();
			if(bill==null)return;
			BillPlan plan = new BillPlan(bill);
			plan.save();
			 ((BeanTableModel<BillPlan>) plansTablePane.getTable().getModel()).insertRow(plan);
			
			planPnl.setBean(plan);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
	}
	private void removeSelectRow() {
		BillPlan rmBean = plansTablePane.getSelectBean();
		rmBean.getBill().getPlans().remove(rmBean);
		rmBean.remove();
		plansTablePane.setBeans(rmBean.getBill().getPlans());
		
	}
}
