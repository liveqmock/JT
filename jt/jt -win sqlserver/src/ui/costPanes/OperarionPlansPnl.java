package ui.costPanes;

import java.util.List;

import javax.swing.JOptionPane;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.BillPlan;
import com.mao.jf.beans.OperationPlan;

public class OperarionPlansPnl extends BeansPanel<OperationPlan> {
	private BillPlan plan;
	public OperarionPlansPnl(BillPlan plan) {
		super(null, new OperationPlanPnl(null), OperationPlan.class);
		this.plan=plan;
		setPlan(plan);
	}
	@Override
	public OperationPlan saveBean() {
		if (!getBeanPanel().isValide()) {
			return null;
		}
			List<OperationPlan> beans = (List<OperationPlan>) (getTablePane().getBeans());
			OperationPlan bean = getPanelBean();
			int index = beans.indexOf(bean);
			if(index>-1&&beans.get(index)!=bean){
				JOptionPane.showMessageDialog(this, "此工序已经存在，不能再添加！","错误",JOptionPane.ERROR_MESSAGE);
				return null;
			}
			bean.save();
			return bean;
		
			
	}
	@Override
	public void removeSelectRow() {
		OperationPlan removeBean = getTablePane().getSelectBean();
		plan.getOperationPlans().remove(removeBean);
		removeBean.remove();
		setBean(plan.getOperationPlans());

	}
	@Override
	public OperationPlan createNewBean() {
		return new OperationPlan(plan);
	}
	public void setPlan(BillPlan plan) {
		this.plan=plan;
		setBean(plan==null?null:plan.getOperationPlans());
		
		
	}
}
