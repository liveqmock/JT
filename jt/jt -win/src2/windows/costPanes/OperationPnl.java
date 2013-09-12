package windows.costPanes;


import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import validation.builtin.Validators;
import windows.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Operation;

public class OperationPnl extends BeanPanel<Operation> {
	public OperationPnl(Operation bean) {
		super(bean);
	}
	private JTextField name;
	private JTextField unit;
	private JTextField cost;
	private JTextField note;
	private JCheckBox out;
	@Override
	protected void createContents() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel label = new JLabel("\u5DE5\u5E8F\u540D\u79F0\uFF1A");
		add(label, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(20);
		
		JLabel label_1 = new JLabel("\u8BA1\u91CF\u5355\u4F4D\uFF1A");
		add(label_1, "2, 4, right, default");
		
		unit = new JTextField();
		unit.setColumns(10);
		add(unit, "4, 4, fill, default");
		
		JLabel label_2 = new JLabel("\u8D39\u7528\uFF1A");
		add(label_2, "2, 6, right, default");
		
		cost = new JTextField();
		cost.setColumns(10);
		add(cost, "4, 6, fill, default");
		
		JLabel label_3 = new JLabel("\u662F\u5426\u5916\u53D1\uFF1A");
		add(label_3, "2, 8");
		
		out = new JCheckBox("\u5916\u53D1");
		add(out, "4, 8");
		
		JLabel label_4 = new JLabel("\u5907\u6CE8\uFF1A");
		add(label_4, "2, 10, right, default");
		
		note = new JTextField();
		note.setColumns(10);
		add(note, "4, 10, fill, default");

		name.setName("名称");
		unit.setName("计量单位");
		cost.setName("费用");
		getValidationGroup().add(name,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(unit,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(cost,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(cost,Validators.numberMin(0));
		getValidationGroup().add(cost,Validators.REQUIRE_NON_EMPTY_STRING);
	}
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("unit"), unit, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("cost"), cost, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("out"), out, BeanProperty.create("selected")));
		
		
	}
}
