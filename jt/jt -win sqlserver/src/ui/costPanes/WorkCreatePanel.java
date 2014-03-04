package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.customComponet.BeanTableModel;
import ui.customComponet.BeanTablePane;
import ui.panels.BillShowPnl;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.BillPlan;

public class WorkCreatePanel extends BillShowPnl {

	private BeanTablePane<Bill> billTable;
	private OperarionWorksPnl operarionWorksPnl;
	private JSplitPane splitPane;
	private BeanTablePane<BillPlan> plansTablePane;

	public WorkCreatePanel() {
		super();

		splitPane=new JSplitPane();
		billTable=new BeanTablePane(null, Bill.class);

		splitPane.setLeftComponent(billTable);
		operarionWorksPnl=new OperarionWorksPnl(null);
		operarionWorksPnl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "���������ϸ", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		plansTablePane=new BeanTablePane<BillPlan>(null, BillPlan.class);
		plansTablePane.setPreferredSize(new Dimension(400, 150));
		
		JPanel plansPanel=new JPanel();
		plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));
		plansTablePane.setBorder(new TitledBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED), "�Ų��ƻ���", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		plansPanel.add(plansTablePane);
		plansPanel.add(operarionWorksPnl);

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
		
	}

	@Override
	public void searchAction(String search) {
		try{
			List<Bill> beans = Bill.getBeans(Bill.class,search+" and id in (select bill from BillPlan where completed=0)");
			billTable.setBeans(beans);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void billItemSelectAction() {
		Bill bill = billTable.getSelectBean();
		Collection<BillPlan> plans = bill.getPlans();
		plansTablePane.setBeans( plans);	
		if(plans!=null&&plans.size()>0)
			operarionWorksPnl.setPlan(plans.iterator().next());
		
	}
	public void planItemSelectAction() {
		BillPlan plan = plansTablePane.getSelectBean();

		operarionWorksPnl.setPlan(plan);

	}
	private void addNewPlan() {
		try{
			Bill bill = billTable.getSelectBean();
			if(bill==null)return;
			BillPlan plan = new BillPlan(bill);
			plan.save();
			 ((BeanTableModel<BillPlan>) plansTablePane.getTable().getModel()).insertRow(plan);
			
			operarionWorksPnl.setBean(plan.getOperationWorks());
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
	}
	private void removeSelectRow() {
		((BeanTableModel<BillPlan>) plansTablePane.getTable().getModel()).removeRow(
				plansTablePane.getTable()
				.convertRowIndexToModel(plansTablePane.getTable().getSelectedRow()));
		
	}
}
