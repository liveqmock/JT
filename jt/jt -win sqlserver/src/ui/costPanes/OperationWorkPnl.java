package ui.costPanes;

import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.OperationWork;

public class OperationWorkPnl extends BeanPanel<OperationWork> {
	private JTextField getNum     ;
	private JTextField productNum ;
	private JTextField scrapNum   ;
	private JTextField scrapReason;
	private JTextField prepareTime;
	private JTextField useTime    ;
	private JTextField note       ;
	private JComboBox<OperationPlan> operationPlan;
	private JComboBox<Employee> employee          ;
	private JComboBox<Employee> checker           ;
	private JXDatePicker finishDate               ;
	private JComboBox<Employee> prepareEmployee   ;

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
		getNum=new JTextField();      
		productNum=new JTextField();  
		scrapNum=new JTextField();    
		scrapReason=new JTextField(); 
		prepareTime=new JTextField(); 
		useTime=new JTextField();     
		note=new JTextField();        
		operationPlan= new JComboBox<OperationPlan>();;
		employee= new JComboBox<Employee>(new Vector<Employee>(Employee.loadOperaters()));
		checker= new JComboBox<Employee>();
		finishDate=new JXDatePicker();
		prepareEmployee=new JComboBox<Employee>(new Vector<Employee>(Employee.loadOperaters()));


		add( new JLabel("工序:"), "2, 2, right, default");		
		add(operationPlan, "4, 2, fill, default");


		add( new JLabel("操作员:"), "6, 2, right, default");		
		add(employee, "8, 2, fill, default");
		employee.setName("操作员");

		add(new JLabel("实发数:"), "2, 4, right, default");
		add(getNum, "4, 4, fill, default");
		getNum.setName("实发数");


		add(new JLabel("成品数："), "6, 4, right, default");
		add(productNum, "8, 4, fill, default");
		productNum.setName("成品数");

		add(new JLabel("报废数:"), "2, 6, right, default");
		add(scrapNum, "4, 6, fill, default");
		scrapNum.setName("报废数");


		add(new JLabel("报废原因:"), "6, 6, right, default");		
		add(scrapReason, "8, 6, fill, default");

		add(new JLabel("用时:"), "2, 8, right, default");		
		add(useTime, "4, 8, fill, default");
		useTime.setName("用时");

		JLabel label_11 = new JLabel("完成时间:");
		add(label_11, "6, 8");
		finishDate.setFormats(new String[] {"yyyy\u5E74MM\u6708dd\u65E5"});
		add(finishDate, "8, 8");


		add( new JLabel("调机用时:"), "2, 10, right, default");	
		add(prepareTime, "4, 10, fill, default");
		prepareTime.setName("调机用时");


		add(new JLabel("调机员:"), "6, 10, right, default");		
		add(prepareEmployee, "8, 10, fill, default");

		add(new JLabel("检验员:"), "2, 12, right, default");		
		add(checker, "4, 12, fill, default");


		add(new JLabel("备注:"), "6, 12, right, default");		
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
	@Override
	public void setBean(OperationWork bean) {
		unBind();
		try{
		operationPlan.setModel(new DefaultComboBoxModel<>(new Vector<>(bean.getPlan().getOperationPlans())));
		}catch(Exception e){
			operationPlan.removeAllItems();
		}
		super.setBean(bean);
	}

}
