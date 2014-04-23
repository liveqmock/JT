package ui.costPanes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.NumberMaxE;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationPlan;

public class OperationPlanPnl extends BeanPanel<OperationPlan> {
	private JTextField unitUseTime     ;
	private JTextField prepareTime   ;
	private JTextField equipmentNum;
	private JTextField note    ;
	private NumberMaxE numberMaxE ;
	private JComboBox<Operation> operations;
	private JComboBox<String> technicsFld;
	private JTextField technicsDesFld;

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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		List<Operation> operationsList = BeanMao.getBeans(Operation.class);
		Operation[] operationArray=new Operation[operationsList.size()];
		operationsList.toArray(operationArray);
		operations= new JComboBox<Operation>( operationArray);

		add( new JLabel("\u52A0\u5DE5\u8BBE\u5907:"), "2, 2, right, default");		
		add(operations, "4, 2, fill, default");


		JLabel label = new JLabel("\u4F7F\u7528\u8BBE\u5907\u6570\u91CF:");
		add( label, "6, 2, right, default");	
		equipmentNum=new JTextField();
		equipmentNum.setName("加工设备数");
		add(equipmentNum, "8, 2, fill, default");
		List list = BeanMao.beanManager.queryList("select distinct technics from OperationPlan");
		String[] technicses=null;
		if(list!=null){
			
			technicses = new String[list.size()];
			list.toArray(technicses);
			
		}
		technicsFld=new JComboBox<String>(technicses);
		technicsFld.setEditable(true);
		technicsDesFld=new JTextField();
		add(new JLabel("工艺:"), "2, 4, right, default");
		add(technicsFld, "4, 4, fill, default");
		add(new JLabel("工艺描述:"), "6, 4, right, default");
		add(technicsDesFld, "8, 4, fill, default");

		JLabel label_1 = new JLabel("\u5355\u4EF6\u52A0\u5DE5\u65F6\u95F4:");
		add(label_1, "2, 6, right, default");
		unitUseTime=new JTextField();
		add(unitUseTime, "4, 6, fill, default");
		unitUseTime.setName("单件用时");

		
		JLabel label_2 = new JLabel("调机时间:");
		add(label_2, "6, 6, right, default");
		prepareTime=new JTextField();
		add(prepareTime, "8, 6, fill, default");
		prepareTime.setName("调机时间");
		
		JLabel label_3 = new JLabel("\u5907\u6CE8:");
		add(label_3, "2, 8, right, default");		
		note=new JTextField();
		add(note, "4, 8, 5, 1, fill, default");
		note.setName("备注");
		operations.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					numberMaxE.setMax(((Operation)e.getItem()).getEquipments().size());
				}

			}
		});
		addValidators();
	}
	public void addValidators() {		
		getValidationGroup().add(operations,Validators.notNull());
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(unitUseTime,Validators.numberMin(0));
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(equipmentNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(equipmentNum,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(equipmentNum,Validators.numberMin(0));
		numberMaxE=Validators.numberMaxE(1);
		getValidationGroup().add(equipmentNum,numberMaxE);
		getValidationGroup().add(prepareTime,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		getValidationGroup().add(prepareTime,Validators.REQUIRE_NON_EMPTY_STRING);
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("unitUseTime"), unitUseTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("equipmentNum"), equipmentNum, jTextFieldBeanProperty));
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("operation"), operations, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("technics"), technicsFld, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("technicsDes"), technicsDesFld, jTextFieldBeanProperty));

	}
	

}
