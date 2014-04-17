package ui.costPanes;

import java.util.Vector;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class WorkTable extends JXTable {

	private Vector<BillPlan> plans;
	private WorkTableMode mode;
	public WorkTable(Vector<BillPlan> plans) {
		this.plans=plans;
		setHighlighters(HighlighterFactory.
				createAlternateStriping());
		setHorizontalScrollEnabled(true);
		mode=new WorkTableMode(plans);
		setModel(mode);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setShowGrid(true);
		packAll();
	}
	public BillPlan getSelectPlan() {
		return mode.getSelectPlan(convertRowIndexToModel(getSelectedRow()));
	}
	public void setSelectBean(BillPlan plan) {
		 for(int i=0;i<getRowCount();i++){
			 if(mode.getSelectPlan(i).getId()==plan.getId()){
				 setRowSelectionInterval(i, i);
				 break;
			 }
		 }
		
	}
	
	public void changeData(Vector<BillPlan> plans) {
		mode.setPlans(plans);
	}
}
