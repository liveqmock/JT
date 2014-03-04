package ui.costPanes;


import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

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
	private JTextField num;
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
		
		JLabel label = new JLabel("\u5de5\u5e8f\u540d\u79f0\uff1a");
		add(label, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(20);
		
		JLabel label_1 = new JLabel("\u8bbe\u5907\u6570\u91cf\uff1a");
		add(label_1, "2, 4, right, default");
		
		num = new JTextField();
		num.setColumns(10);
		num.setToolTipText("\u53ef\u540c\u65f6\u64cd\u4f5c\u7684\u8bbe\u5907\u6570\u91cf\u3002");
		add(num, "4, 4, fill, default");
		
		JLabel label_2 = new JLabel("\u8d39\u7528\uff1a");
		add(label_2, "2, 6, right, default");
		
		cost = new JTextField();
		cost.setColumns(10);
		add(cost, "4, 6, fill, default");
		
		JLabel label_3 = new JLabel("\u662f\u5426\u5916\u53d1\uff1a");
		add(label_3, "2, 8");
		
		out = new JCheckBox("\u5916\u53d1");
		add(out, "4, 8");
		
		JLabel label_4 = new JLabel("\u5907\u6ce8\uff1a");
		add(label_4, "2, 10, right, default");
		
		note = new JTextField();
		note.setColumns(10);
		add(note, "4, 10, fill, default");

		name.setName("\u540d\u79f0");
		num.setName("\u8ba1\u91cf\u5355\u4f4d");
		cost.setName("\u8d39\u7528");
		getValidationGroup().add(name,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(num,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(cost,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(cost,Validators.numberMin(0));
		getValidationGroup().add(cost,Validators.REQUIRE_NON_EMPTY_STRING);
	
	}
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("num"), num, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("cost"), cost, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("out"), out, BeanProperty.create("selected")));
		
		
	}
}
