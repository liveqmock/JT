package windows.costPanes;

import java.util.Vector;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import windows.tableModes.WorkTableMode;

import com.mao.jf.beans.Plan;

public class WorkTable extends JXTable {

	private Vector<Plan> plans;
	private WorkTableMode mode;
	public WorkTable(Vector<Plan> plans) {
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
	public Plan getSelectPlan() {
		return mode.getSelectPlan(convertRowIndexToModel(getSelectedRow()));
	}
	public void setSelectBean(Plan plan) {
		 for(int i=0;i<getRowCount();i++){
			 if(mode.getSelectPlan(i).getId()==plan.getId()){
				 setRowSelectionInterval(i, i);
				 break;
			 }
		 }
		
	}
}
