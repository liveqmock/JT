package ui.costPanes;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Validator;

import ui.customComponet.BeanOPanel;
import validation.builtin.NumberRangeER;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationPlan;

public class OperationPlanPnl extends BeanOPanel<OperationPlan> implements FocusListener{
	private JTextField unitUseTime     ;
	private JTextField prepareTime   ;
	private JTextField equipmentNum;
	private JTextField note    ;
	private NumberRangeER numberRangeER = Validators.numberRangeER(0,0);
	private JComboBox<Operation> operations;

	/**
	 * Create the panel.
	 */
	public OperationPlanPnl(OperationPlan bean) {
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
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		operations= new JComboBox<Operation>(new Vector<Operation>( BeanMao.loadAll(Operation.class)));



		add( new JLabel("\u52A0\u5DE5\u8BBE\u5907:"), "2, 2, right, default");		
		add(operations, "4, 2, fill, default");


		JLabel label = new JLabel("\u4F7F\u7528\u8BBE\u5907\u6570\u91CF:");
		add( label, "6, 2, right, default");	
		equipmentNum=new JTextField();
		add(equipmentNum, "8, 2, fill, default");

		JLabel label_1 = new JLabel("\u5355\u4EF6\u52A0\u5DE5\u65F6\u95F4:");
		add(label_1, "2, 4, right, default");
		unitUseTime=new JTextField();
		add(unitUseTime, "4, 4, fill, default");
		unitUseTime.setName("实发数");

		unitUseTime.addFocusListener(this);

		JLabel label_2 = new JLabel("\u8C03\u673A\u65F6\u95F4:");
		add(label_2, "6, 4, right, default");
		prepareTime=new JTextField();
		add(prepareTime, "8, 4, fill, default");
		prepareTime.setName("报废数");
		prepareTime.addFocusListener(this);

		JLabel label_3 = new JLabel("\u5907\u6CE8:");
		add(label_3, "2, 6, right, default");		
		note=new JTextField();
		add(note, "4, 6, 5, 1, fill, default");
		note.setName("用时");
		operations.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					numberRangeER.setMax(((Operation)e.getItem()).getEquipments().size());
				}

			}
		});
		addValidators();
	}
	public void addValidators() {		
		getValidationGroup().add(operations,Validators.notNull());
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(unitUseTime,Validators.numberMin(0));
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(equipmentNum,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(equipmentNum,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(equipmentNum,numberRangeER);
		getValidationGroup().add(prepareTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		getValidationGroup().add(prepareTime,Validators.REQUIRE_NON_EMPTY_STRING);
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("unitUseTime"), unitUseTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operation"), operations, comboBoxBeanProperty));

	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO 自动生成的方法存根

	}
	@Override
	public void focusLost(FocusEvent e) {
		try{
			equipmentNum.setText(String.valueOf(bean.getPlanCost()));
		}catch(Exception exception){

		}
	}



}
