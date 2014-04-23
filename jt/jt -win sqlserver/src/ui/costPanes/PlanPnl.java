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
import validation.builtin.NumberMaxE;
import validation.builtin.Validators;

import com.mao.jf.beans.PicPlan;

public class PlanPnl extends JPanel{

	private OperarionPlansPnl operationPlansPnl;
	private PicPlan bean;
	public PlanPnl(PicPlan bean) {
		super();
		this.bean=bean;
		createContents();
	}

	protected void createContents() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "排产计划细节", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		

		operationPlansPnl=new OperarionPlansPnl(bean);

		operationPlansPnl.getTablePane().getTable().setUI(new DragDropRowTableUI<>());
		JButton saveBt = new JButton("保存排产计划");
		
		setLayout(new BorderLayout());
		add(operationPlansPnl,BorderLayout.CENTER);
		add(saveBt,BorderLayout.SOUTH);
		saveBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				savePlan();
			}
		});
		
		
	}
	public void setBean(PicPlan plan) {
		this.bean=plan;
		operationPlansPnl.setPlan(plan);
	}
	private void savePlan() {
		try {
			bean.initOperationPlans();
			bean.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		
	}

}
