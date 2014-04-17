package ui.costPanes;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.OperationWork;
import com.mao.jf.beans.PicPlan;

public class OperarionWorksPnl extends BeansPanel<OperationWork> {
	private PicPlan plan;
	public OperarionWorksPnl(PicPlan plan) {
		
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
	public PicPlan getPlan() {
		return plan;
	}
	public void setPlan(PicPlan plan) {
		this.plan = plan;
		
		setBean(plan==null?null:plan.getOperationWorks());
	}
	
}
