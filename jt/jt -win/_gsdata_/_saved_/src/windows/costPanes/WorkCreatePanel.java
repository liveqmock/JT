package windows.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import validation.builtin.Validators;
import validation.ui.ValidationPanel;
import windows.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.OperationWork;
import com.mao.jf.beans.Plan;

public class WorkCreatePanel extends BeanPanel<OperationWork> {
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
	private WorkTable table;

	public WorkTable getTable() {
		return table;
	}
	/**
	 * Create the panel.
	 */
	public WorkCreatePanel(OperationWork bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1);
		panel.add(splitPane);
		
		JScrollPane plan = new JScrollPane();
		table=new WorkTable(Plan.loadUnCompleted());
		plan.setViewportView(table);
		splitPane.setLeftComponent(plan);
		
		JPanel panel_2 = new JPanel();

		ValidationPanel validationPanel=new ValidationPanel();
		validationPanel.setInnerComponent(panel_2);
		splitPane.setRightComponent(validationPanel);
		setValidationGroup(validationPanel.getValidationGroup());

		panel_2.setMinimumSize(new Dimension(200, 10));
		panel_2.setPreferredSize(new Dimension(400, 10));
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
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
		panel_2.add(label_7, "2, 2, right, default");
		
		operationPlan = new JComboBox();
		panel_2.add(operationPlan, "4, 2, fill, default");
		
		JLabel label_8 = new JLabel("\u64CD\u4F5C\u5458\uFF1A");
		panel_2.add(label_8, "2, 4, right, default");
		
		employee = new JComboBox(Employee.loadOperaters());
		panel_2.add(employee, "4, 4, fill, default");
		
		JLabel lblNewLabel = new JLabel("\u5B9E\u53D1\u6570\u91CF\uFF1A");
		panel_2.add(lblNewLabel, "2, 6, right, default");
		
		getNum = new JTextField();
		panel_2.add(getNum, "4, 6, fill, default");
		getNum.setColumns(10);
		
		JLabel label = new JLabel("\u6210\u54C1\u6570\u91CF\uFF1A");
		panel_2.add(label, "2, 8, right, default");
		
		productNum = new JTextField();
		productNum.setColumns(10);
		panel_2.add(productNum, "4, 8, fill, default");
		
		JLabel label_1 = new JLabel("\u62A5\u5E9F\u6570\u91CF\uFF1A");
		panel_2.add(label_1, "2, 10, right, default");
		
		scrapNum = new JTextField();
		scrapNum.setColumns(10);
		panel_2.add(scrapNum, "4, 10, fill, default");
		
		JLabel label_2 = new JLabel("\u62A5\u5E9F\u539F\u56E0\uFF1A");
		panel_2.add(label_2, "2, 12, right, default");
		
		scrapReason = new JTextField();
		scrapReason.setColumns(10);
		panel_2.add(scrapReason, "4, 12, fill, default");
		
		JLabel label_3 = new JLabel("\u52A0\u5DE5\u7528\u65F6\uFF1A");
		panel_2.add(label_3, "2, 14, right, default");
		
		useTime = new JTextField();
		useTime.setColumns(10);
		panel_2.add(useTime, "4, 14, fill, default");
		
		JLabel label_4 = new JLabel("\u8C03\u673A\u6570\u91CF\uFF1A");
		panel_2.add(label_4, "2, 16, right, default");
		
		prepareTime = new JTextField();
		prepareTime.setColumns(10);
		panel_2.add(prepareTime, "4, 16, fill, default");
		
		JLabel label_5 = new JLabel("\u68C0\u9A8C\u5458\uFF1A");
		panel_2.add(label_5, "2, 18, right, default");
		
		checker = new JComboBox(Employee.loadCheckers());
		panel_2.add(checker, "4, 18, fill, default");
		
		JLabel label_9 = new JLabel("\u5907\u6CE8\uFF1A");
		panel_2.add(label_9, "2, 20");
		
		finishDate = new JXDatePicker();
		finishDate.setFormats(new String[] {"yyyy\u5E74MM\u6708dd\u65E5"});
		panel_2.add(finishDate, "4, 20");
		
		JLabel label_6 = new JLabel("\u5907\u6CE8\uFF1A");
		panel_2.add(label_6, "2, 22, right, default");
		
		note = new JTextField();
		note.setColumns(10);
		panel_2.add(note, "4, 22, fill, default");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				bean.setPlan(table.getSelectPlan());
				operationPlan.removeAllItems();
				for(OperationPlan operation:bean.getPlan().getOperationPlans().values()){
					if(operation.getUseTime()>0){
						operationPlan.addItem(operation);
					}
					
				}
				
			}
		});

		getNum.setName("实发数");
		productNum.setName("成品数");
		scrapNum.setName("报废数");
		useTime.setName("用时");
		prepareTime.setName("调机时间");
		operationPlan.setName("工序");
		employee.setName("操作员");

		
		getValidationGroup().add(getNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(getNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(productNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(productNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(useTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(useTime,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		
		getValidationGroup().add(operationPlan,Validators.notNull());
		getValidationGroup().add(employee,Validators.notNull());
	}
	
	
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("getNum"), getNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("productNum"), productNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapNum"), scrapNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapReason"), scrapReason, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("useTime"), useTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operationPlan"), operationPlan, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("employee"), employee, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("checker"), checker, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("finishDate"), finishDate,  BeanProperty.create("date")));

		
		
	}

}
