package ui.panels;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BackRepair;

public class BackRepairPanel extends BeanPanel<BackRepair> {
	private JTextField backNumFld;
private JXDatePicker backDatePicker;
private JLabel billNoLabel;
private JLabel cstLabel;
private JXDatePicker getDatePicker;
private JTextField backNote;
	public BackRepairPanel(BackRepair bean) {
		super(bean);
	}
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel label_2 = new JLabel("\u5BA2\u6237\uFF1A");
		add(label_2, "2, 2");
		
		cstLabel = new JLabel();
		add(cstLabel, "4, 2, left, default");

		JLabel label_3 = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		add(label_3, "2, 4");
		
		billNoLabel = new JLabel();
		add(billNoLabel, "4, 4");

		JLabel label_6 = new JLabel("返修原因");
		add(label_6, "2, 6");
		
		backNote = new JTextField();
		backNote.setColumns(20);
		add(backNote, "4, 6");
		
		JLabel label = new JLabel("\u8FD4\u4FEE\u6570\u636E\uFF1A");
		add(label, "2, 8, right, default");
		
		backNumFld = new JTextField();
		add(backNumFld, "4, 8, fill, default");
		backNumFld.setColumns(10);
		
		JLabel label_1 = new JLabel("\u8FD4\u4FEE\u65F6\u95F4\uFF1A");
		add(label_1, "2,10");

		backDatePicker = new JXDatePicker();
		backDatePicker.setFormats(new String[] {"yyyy-MM-dd"});

		add(backDatePicker, "4,10");
		
		JLabel label_4 = new JLabel("返修交货时间");
		add(label_4, "2, 12");
		getDatePicker = new JXDatePicker();
		getDatePicker.setFormats(new String[] {"yyyy-MM-dd"});
		add(getDatePicker, "4,12");
		
		backNumFld.setName("返修数量");
		getDatePicker.getEditor().setName("返修交货时间");
		backDatePicker.getEditor().setName("返修日期");
		backNote.setName("返修原因");
		getValidationGroup().add(backNumFld,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(backNumFld,Validators.numberMin(0));
		getValidationGroup().add(backDatePicker.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
	}
	protected  void dataBinding() {
		BeanProperty<BackRepair, Long> backRepairBeanProperty = BeanProperty.create("backNum");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, backRepairBeanProperty, backNumFld, jTextFieldBeanProperty));
		
		BeanProperty<BackRepair, String> noteProperty = BeanProperty.create("note");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, noteProperty, backNote, jTextFieldBeanProperty));

		BeanProperty<BackRepair, Date> backRepairBeanProperty_1 = BeanProperty.create("backDate");
		BeanProperty<JXDatePicker, Date> jXDatePickerBeanProperty = BeanProperty.create("date");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, backRepairBeanProperty_1, backDatePicker, jXDatePickerBeanProperty));
		
		BeanProperty<BackRepair, Date> backRepairBeanProperty_11 = BeanProperty.create("getDate");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, backRepairBeanProperty_11, getDatePicker, jXDatePickerBeanProperty));
		
		BeanProperty<BackRepair, String> backRepairBeanProperty_2 = BeanProperty.create("billItem.billNo");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, bean, backRepairBeanProperty_2, billNoLabel, jLabelBeanProperty));
		//
		BeanProperty<BackRepair, String> backRepairBeanProperty_3 = BeanProperty.create("billItem.custom");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, bean, backRepairBeanProperty_3, cstLabel, jLabelBeanProperty));
	}
}
