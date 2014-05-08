package ui.costPanes;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.mao.jf.beans.PicPlan;

public class PicPlanPnl extends BeanPanel<PicPlan> {
	private JTextField numFld;
	private JXDatePicker planDateFld;

	/**
	 * Create the panel.
	 */
	public PicPlanPnl(PicPlan bean) {
		super(bean);
		
	}
	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("num"), numFld, BeanProperty.create("text")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("planDate"), planDateFld, BeanProperty.create("date")));
		
		
	}
	@Override
	protected void createContents() {
setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		JLabel lblNewLabel = new JLabel("数量:");
		add(lblNewLabel, "cell 0 0,alignx right");
		
		numFld = new JTextField();
		add(numFld, "cell 1 0,growx");
		numFld.setColumns(10);
		
		JLabel label = new JLabel("计划时间:");
		add(label, "cell 0 1");
		
		planDateFld = new JXDatePicker();
		planDateFld.setToolTipText("如果为空,则系统自动分配");
		add(planDateFld, "cell 1 1");

		getValidationGroup().add(numFld,Validators.numberMaxE(bean.getPic().getNum()));
		getValidationGroup().add(numFld,Validators.notNull());
		getValidationGroup().add(numFld,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(numFld,Validators.numberMin(0));
	}

}
