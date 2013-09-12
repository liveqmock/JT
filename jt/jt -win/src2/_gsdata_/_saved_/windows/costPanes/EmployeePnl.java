package windows.costPanes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComboBox;
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
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.Operation;

public class EmployeePnl extends BeanPanel<Employee> {
	private JTextField name;
	private JTextField wage;
	private JComboBox<Operation> comboBox;
	public EmployeePnl(Employee bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("\u59D3\u540D\uFF1A");
		add(lblNewLabel, "2, 2, right, top");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(10);
		
		JLabel label = new JLabel("\u5C97\u4F4D\uFF1A");
		add(label, "2, 4, right, default");
		
		try {
			comboBox = new JComboBox<Operation>(Operation.loadAll(Operation.class));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		add(comboBox, "4, 4, fill, default");
		if(bean.getOperation()==null) bean.setOperation((Operation) comboBox.getSelectedItem());
		JLabel label_1 = new JLabel("\u5DE5\u8D44\uFF1A");
		add(label_1, "2, 6, right, default");
		
		wage = new JTextField();
		wage.setColumns(10);
		add(wage, "4, 6, fill, default");
		name.setName("����");
		comboBox.setName("��λ");
		wage.setName("����");
		getValidationGroup().add(comboBox,Validators.notNull());
		getValidationGroup().add(name,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(name,Validators.maxLength(20));
		getValidationGroup().add(wage,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(wage,Validators.numberMinE(0));
		
	}
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operation"), comboBox, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("wage"), wage, jTextFieldBeanProperty));
		
		
	}
}
