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

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.mao.jf.beans.BillPlan;

public class PlanPnl extends BeanPanel<BillPlan>{

	private JTextField numField;
	private OperarionPlansPnl operationPlansPnl;

	public PlanPnl(BillPlan bean) {
		super(bean);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,  BeanProperty.create("num"), numField, BeanProperty.create("text")));
	}

	@Override
	protected void createContents() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "排产计划细节", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		JPanel numPanel = new JPanel();
		numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.X_AXIS));
		numPanel.add(new JLabel("计划排产数量："));
		numField=new JTextField();
		numPanel.add(numField);
		

		operationPlansPnl=new OperarionPlansPnl(editBean);

		operationPlansPnl.getTablePane().getTable().setUI(new DragDropRowTableUI<>());
		JButton saveBt = new JButton("保存排产计划");
		
		setLayout(new BorderLayout());
		add(numPanel,BorderLayout.NORTH);
		add(operationPlansPnl,BorderLayout.CENTER);
		add(saveBt,BorderLayout.SOUTH);
		saveBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlanPnl.this.getBean().save();
				
			}
		});
		
		try{
			getValidationGroup().add(numField,Validators.numberMaxE(editBean.getBill().getNum()));
			getValidationGroup().add(numField,Validators.notNull());
			getValidationGroup().add(numField,Validators.REQUIRE_VALID_INTEGER);
			getValidationGroup().add(numField,Validators.numberMin(0));
		}catch(Exception e){
			
		}
		
	}
	public void setBean(BillPlan plan) {
		super.setBean(plan);
		operationPlansPnl.setPlan(plan);
	}

}
