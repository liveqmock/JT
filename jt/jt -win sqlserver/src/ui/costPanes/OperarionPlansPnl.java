package ui.costPanes;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.beanutils.BeanUtils;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.BillPlan;

public class OperarionPlansPnl extends BeansPanel<OperationPlan> {
	private BillPlan plan;
	public OperarionPlansPnl(BillPlan plan) {
		super(null, new OperationPlanPnl(null), OperationPlan.class);
		this.plan=plan;
		setPlan(plan);
	}
	@Override
	public OperationPlan saveBean() {
		return null;
	}

	

	@Override
	public void addNew() {
		if (!getBeanPanel().isValide()) {
			return;
		}
			List<OperationPlan> beans = (List<OperationPlan>) (getTablePane().getBeans());
			OperationPlan bean = getPanelBean();
			int index = beans.indexOf(bean);
			if(index>-1&&beans.get(index)!=bean){
				JOptionPane.showMessageDialog(this, "此工序已经存在，不能再添加！","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}else if(index>0){
				try {
					 BeanUtils.copyProperties(beans.get(index), bean);
				} catch (IllegalAccessException |  InvocationTargetException  e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			bean.save();
			getTablePane().addNew(bean);
			setPanelBean(createNewBean());
		
			
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
