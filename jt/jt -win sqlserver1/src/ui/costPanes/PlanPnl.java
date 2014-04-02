package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanOPanel;
import validation.builtin.NumberMaxE;
import validation.builtin.Validators;

import com.mao.jf.beans.BillPlan;
import com.mao.jf.beans.OperationPlan;

public class PlanPnl extends BeanOPanel<BillPlan>{

	private JTextField numField;
	private OperarionPlansPnl operationPlansPnl;
	private NumberMaxE numberMaxE;
	public PlanPnl(BillPlan bean) {
		super(bean);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("num"), numField, BeanProperty.create("text")));
	}

	@Override
	protected void createContents() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "�Ų��ƻ�ϸ��", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		JPanel numPanel = new JPanel();
		numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.X_AXIS));
		numPanel.add(new JLabel("�ƻ��Ų�������"));
		numField=new JTextField();
		numPanel.add(numField);
		

		operationPlansPnl=new OperarionPlansPnl(bean);

		operationPlansPnl.getTablePane().getTable().setUI(new DragDropRowTableUI<>());
		JButton saveBt = new JButton("�����Ų��ƻ�");
		
		setLayout(new BorderLayout());
		add(numPanel,BorderLayout.NORTH);
		add(operationPlansPnl,BorderLayout.CENTER);
		add(saveBt,BorderLayout.SOUTH);
		saveBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				savePlan();
			}
		});
		
		try{
			numberMaxE = Validators.numberMaxE(1);
			getValidationGroup().add(numField,numberMaxE);
			getValidationGroup().add(numField,Validators.notNull());
			getValidationGroup().add(numField,Validators.REQUIRE_VALID_INTEGER);
			getValidationGroup().add(numField,Validators.numberMin(0));
		}catch(Exception e){
			
		}
		
	}
	public void setBean(BillPlan plan) {
		if(plan!=null){
			numberMaxE.setMax(plan.getBill().getNum());
		}
		super.setBean(plan);
		operationPlansPnl.setPlan(plan);
	}
	private void savePlan() {
		if(!isValide()) return;
		for(OperationPlan operationPlan: PlanPnl.this.getBean().getOperationPlans()){
			try {
				operationPlan.createPlan();
			} catch (Exception e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		PlanPnl.this.getBean().save();
		
		operationPlansPnl.setBean(PlanPnl.this.getBean().getOperationPlans());	
		
			
		
	}

}
