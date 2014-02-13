package ui.costPanes;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.BillPlan;
import com.mao.jf.beans.OperationPlan;

public class UnStartPlanPnl extends JPanel {

	/**
	 * Create the panel.
	 */
	public UnStartPlanPnl() {
		setLayout(new BorderLayout());
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final BeanTablePane<BillPlan> billplanTable=new BeanTablePane<BillPlan>(BillPlan.getUnstartPlan(), BillPlan.class);
		final BeanTablePane<OperationPlan> operationsTable=new BeanTablePane<OperationPlan>(null, OperationPlan.class);
		splitPane.add(billplanTable);
		splitPane.add(operationsTable);
		add(splitPane,BorderLayout.CENTER);
		
		billplanTable.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					operationsTable.setBeans(billplanTable.getSelectBean().getOperationPlans());
				}

			}

		});
	}

}
