package ui.costPanes;

import javax.swing.JOptionPane;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.PicPlan;

public class OperarionPlansPnl extends BeansPanel<OperationPlan> {
	private PicPlan plan;
	public OperarionPlansPnl(PicPlan plan) {
		super(null, new OperationPlanPnl(null), OperationPlan.class);
		this.plan=plan;
		getTablePane().getTable().setSortable(false);
		setPlan(plan);
	}
	@Override
	public OperationPlan saveBean() {
		if (!getBeanPanel().isValide()) {
			return null;
		}
		OperationPlan bean = getPanelBean();
		
		for(OperationPlan bean2:getBeans()){
			if(bean2.equals(bean)&&bean2!=bean){
				JOptionPane.showMessageDialog(this, "此工序已经存在，不能再添加！","错误",JOptionPane.ERROR_MESSAGE);
				return null;
			}

		}
		bean.setSequence(getTablePane().getTable().getRowCount());
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
