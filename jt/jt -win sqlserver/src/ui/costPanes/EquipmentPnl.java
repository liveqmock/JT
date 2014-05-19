package ui.costPanes;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Equipment;
import com.mao.jf.beans.Operation;

import ui.customComponet.BeanPanel;
import validation.Validator;
import validation.builtin.Validators;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class EquipmentPnl extends BeanPanel<Equipment> {
	private JTextField equipmentName;
	private JTextField code;
	private JCheckBox good;
	private JComboBox<Operation> operation;
	private JTextField workTime;

	/**
	 * Create the panel.
	 */
	public EquipmentPnl(Equipment bean) {
		super(bean);

	}

	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operation"),operation, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("name"),equipmentName, BeanProperty.create("text")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("code"),code, BeanProperty.create("text")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("workTime"),workTime, BeanProperty.create("text")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("good"),good, BeanProperty.create("selected")));
		

	}

	@Override
	protected void createContents() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel label = new JLabel("\u8BBE\u5907\u7C7B\u578B:");
		add(label, "cell 0 0,alignx trailing");

		List<Operation> list = BeanMao.getBeans(Operation.class);
		Operation[] operations=new Operation[list.size()];
		list.toArray(operations);
		operation = new JComboBox<>(operations);
		add(operation, "cell 1 0,growx");

		JLabel label_1 = new JLabel("\u8BBE\u5907\u540D:");
		add(label_1, "cell 0 1,alignx trailing");

		equipmentName = new JTextField();
		add(equipmentName, "cell 1 1,growx");
		equipmentName.setColumns(10);

		JLabel label_2 = new JLabel("\u7F16\u53F7:");
		add(label_2, "cell 0 2,alignx trailing");

		code = new JTextField();
		add(code, "cell 1 2,growx");
		code.setColumns(10);
		
		workTime=new JTextField();
		add(new JLabel("设备日工作时间"), "cell 0 3,alignx right");
		add(workTime, "cell 1 3,growx");
		
		JLabel label_3 = new JLabel("\u53EF\u7528\u6027:");
		add(label_3, "cell 0 4,alignx right");

		good = new JCheckBox("\u8BBE\u5907\u662F\u5426\u53EF\u7528");
		add(good, "cell 1 4");
		
		getValidationGroup().add(equipmentName,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(code,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(workTime,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(workTime,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		equipmentName.setName("设备名称");
		workTime.setName("日工作时间");
		code.setName("编号");
	}

}
