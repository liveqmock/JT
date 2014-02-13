package ui.costPanes;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.BillPlan;
import com.mao.jf.beans.OperationWork;

public class OperarionWorksPnl extends BeansPanel<OperationWork> {
	private BillPlan plan;
	public OperarionWorksPnl(BillPlan plan) {
		
		super((plan==null?null:plan.getOperationWorks()), new OperationWorkPnl(new OperationWork(plan)), OperationWork.class);
	}
	@Override
	public OperationWork saveBean() {
		getPanelBean().save();
		return getPanelBean();
	}
	@Override
	public OperationWork createNewBean() {
		// TODO 自动生成的方法存根
		return new OperationWork(plan);
	}
	public BillPlan getPlan() {
		return plan;
	}
	public void setPlan(BillPlan plan) {
		this.plan = plan;
		setBean(plan==null?null:plan.getOperationWorks());
	}
	
}
