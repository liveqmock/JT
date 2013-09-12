package windows.costPanes;

import java.util.Date;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import validation.builtin.Validators;
import windows.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.OperationWork;

public class OperationWorkPnl extends BeanPanel<OperationWork> {
	private JTextField getNum;
	private JTextField productNum;
	private JTextField scrapNum;
	private JTextField scrapReason;
	private JTextField prepareTime;
	private JTextField useTime;
	private JTextField note;
	private JComboBox<OperationPlan> operationPlan;
	private JComboBox<Employee> employee;
	private JComboBox<Employee> checker;
	private JXDatePicker finishDate;
	private JComboBox<Employee> prepareEmployee;

	/**
	 * Create the panel.
	 */
	public OperationWorkPnl(OperationWork bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel label_7 = new JLabel("\u5DE5\u5E8F\uFF1A");
		add(label_7, "2, 2, right, default");
		
		operationPlan = new JComboBox();
		add(operationPlan, "4, 2, fill, default");
		operationPlan.setName("工序");
		
		
		JLabel label_8 = new JLabel("\u64CD\u4F5C\u5458\uFF1A");
		add(label_8, "6, 2, right, default");
		
		employee = new JComboBox(Employee.loadOperaters());
		add(employee, "8, 2, fill, default");
		employee.setName("操作员");
		
		JLabel lblNewLabel = new JLabel("\u5B9E\u53D1\u6570\u91CF\uFF1A");
		add(lblNewLabel, "2, 4, right, default");
		
		getNum = new JTextField();
		add(getNum, "4, 4, fill, default");
		getNum.setColumns(10);
		getNum.setName("实发数");
		
		
		JLabel label = new JLabel("\u6210\u54C1\u6570\u91CF\uFF1A");
		add(label, "6, 4, right, default");
		
		productNum = new JTextField();
		productNum.setColumns(10);
		add(productNum, "8, 4, fill, default");
		productNum.setName("成品数");
		JLabel label_1 = new JLabel("\u62A5\u5E9F\u6570\u91CF\uFF1A");
		add(label_1, "2, 6, right, default");
		
		scrapNum = new JTextField();
		scrapNum.setColumns(10);
		add(scrapNum, "4, 6, fill, default");
		scrapNum.setName("报废数");


		JLabel label_2 = new JLabel("\u62A5\u5E9F\u539F\u56E0\uFF1A");
		add(label_2, "6, 6, right, default");
		
		scrapReason = new JTextField();
		scrapReason.setColumns(10);
		add(scrapReason, "8, 6, fill, default");
		
		JLabel label_3 = new JLabel("\u52A0\u5DE5\u7528\u65F6\uFF1A");
		add(label_3, "2, 8, right, default");
		
		useTime = new JTextField();
		useTime.setColumns(10);
		add(useTime, "4, 8, fill, default");
		useTime.setName("用时");

		JLabel label_11 = new JLabel("\u52A0\u5DE5\u65F6\u95F4\uFF1A");
		add(label_11, "6, 8");
		
		finishDate = new JXDatePicker(new Date());
		finishDate.setFormats(new String[] {"yyyy\u5E74MM\u6708dd\u65E5"});
		add(finishDate, "8, 8");
		
		JLabel label_4 = new JLabel("\u8C03\u673A\u65F6\u95F4\uFF1A");
		add(label_4, "2, 10, right, default");
		
		prepareTime = new JTextField();
		prepareTime.setColumns(10);
		add(prepareTime, "4, 10, fill, default");
		prepareTime.setName("调机时间");

		JLabel label_10 = new JLabel("\u8C03\u673A\u4EBA\uFF1A");
		add(label_10, "6, 10, right, default");
		
		prepareEmployee = new JComboBox(Employee.loadOperaters());
		add(prepareEmployee, "8, 10, fill, default");
		
		JLabel label_5 = new JLabel("\u68C0\u9A8C\u5458\uFF1A");
		add(label_5, "2, 12, right, default");
		
		checker = new JComboBox(Employee.loadCheckers());
		add(checker, "4, 12, fill, default");
		
		JLabel label_6 = new JLabel("\u5907\u6CE8\uFF1A");
		add(label_6, "6, 12, right, default");
		
		note = new JTextField();
		note.setColumns(10);
		add(note, "8, 12, fill, default");
		addValidators();
	}
	public void addValidators() {

		
		getValidationGroup().add(getNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(getNum,Validators.numberMin(0));
		getValidationGroup().add(getNum,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(operationPlan,Validators.notNull());
		getValidationGroup().add(productNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(productNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(useTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(useTime,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		getValidationGroup().add(employee,Validators.notNull());
	}
	
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("getNum"), getNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("productNum"), productNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapNum"), scrapNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapReason"), scrapReason, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("workTime"), useTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("finishDate"), finishDate,  BeanProperty.create("date")));
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("employee"), employee, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("checker"), checker, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareEmployee"), prepareEmployee, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operationPlan"), operationPlan, comboBoxBeanProperty));

		
		
	}
	public void setOperationPlans(TreeMap<String, OperationPlan> operationPlans) {
		operationPlan.removeAllItems();
		for(OperationPlan operation:operationPlans.values()){
			if(operation.getUseTime()>0){
				operationPlan.addItem(operation);
			}
			
		}
		
	}

}
