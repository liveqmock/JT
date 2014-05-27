package ui.costPanes;


import ui.customComponet.BeansPanel;

import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.PicPlan;

public class OperarionPlansPnl extends BeansPanel<OperationPlan> {
	private static final String[] HEADER=new String[]{"排产序号","设备","工艺","工艺描述",
		"使用设备数","单位用时","调机用时","计划时间","开始时间","结束时间","计划耗时"};
	private PicPlan plan;
	public OperarionPlansPnl(PicPlan plan) {
		super(null,HEADER, new OperationPlanPnl(null), OperationPlan.class);
		this.plan=plan;
		getTablePane().getTable().setSortable(false);
		setPlan(plan);
	}
	@Override
	public OperationPlan saveBean() {
		OperationPlan bean = getPanelBean();
		if(bean.getSequence()==0)
			bean.setSequence(getTablePane().getTable().getRowCount()+1);
		bean.save();
		return bean;


	}
	@Override
	public void removeSelectRow() {
		super.removeSelectRow();
		for(int p=0;p< getTablePane().getTable().getRowCount();p++)
			getTablePane().getBean(p).setSequence(p);

	}
	@Override
	public OperationPlan createNewBean() {
		return new OperationPlan(plan);
	}
	public void setPlan(PicPlan plan) {
		this.plan=plan;
		setBean(plan==null?null:plan.getOperationPlans());


	}
}
