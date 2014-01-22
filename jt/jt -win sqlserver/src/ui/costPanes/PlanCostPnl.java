package ui.costPanes;

import java.util.Vector;

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
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.Plan;

public class PlanCostPnl extends BeanPanel<Plan> {

	private JTextField operationNum;
	private JTextField note;
	private JLabel billId;
	private JTextField useTime;
	private JLabel itemId;
	private JXDatePicker planDate;
	private JComboBox<Operation> operation;
	private JLabel unit;
	private JTextField unitCost;
	public PlanCostPnl(Plan bean) {
		super(bean);
	}
	@Override
	protected void dataBinding() {
		BeanProperty<Object, Object> textProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("bill.billid"), billId, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("bill.item"), itemId, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("operationNum"), operationNum, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, bean,
				BeanProperty.create("operationUnit"), unit, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("unitCost"), unitCost, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("note"), note, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("planDate"), planDate, BeanProperty.create("date")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("operation"), operation, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("useTime"), useTime, textProperty));
		
		
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
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel billitemL = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		add(billitemL, "2, 2");
		
		billId = new JLabel(""); 
		add(billId, "4, 2");
		
		JLabel label = new JLabel("\u9879\u76EE\u53F7\uFF1A");
		add(label, "6, 2");
		
		itemId = new JLabel("");
		add(itemId, "8, 2");
		
		JLabel lblNewLabel = new JLabel("\u6392\u4EA7\u65E5\u671F\uFF1A");
		add(lblNewLabel, "2, 4");
		
		planDate = new JXDatePicker();
		add(planDate, "4, 4, 5, 1");
		
		
		JLabel label_3 = new JLabel("\u751F\u4EA7\u5DE5\u5E8F\uFF1A");
		add(label_3, "2, 6, right, default");
		operation = new JComboBox<Operation>(new Vector<Operation>(Operation.loadAll(Operation.class)));
		
		add(operation, "4, 6, 5, 1, fill, default");
		
		JLabel label_14 = new JLabel("计量单位：");
		add(label_14, "2, 8, right, default");
		
		unit = new JLabel();
		add(unit, "4, 8, 5, 1, fill, default");
		unitCost=new JTextField();
		add(new JLabel("单位费用"), "2, 10, right, default");
		add(unitCost, "4, 10,  5, 1, fill, default");
		unitCost.setColumns(10);
		
		JLabel label_4 = new JLabel("\u5206\u914D\u6570\u91CF\uFF1A");
		add(label_4, "2, 12, right, default");
		
		operationNum = new JTextField();
		add(operationNum, "4, 12, 5, 1, fill, default");
		operationNum.setColumns(10);
		add(new JLabel("计划用时："), "2, 14, right, default");
		useTime=new JTextField();
		add(useTime, "4, 14, 5, 1, fill, default");
		JLabel label_6 = new JLabel("\u5907\u6CE8\uFF1A");
		add(label_6, "2, 16, right, default");
		
		note = new JTextField();
		note.setColumns(10);
		add(note, "4, 16, 5, 1, fill, default");
		
		
		planDate.setName("排产时间");
		operation.setName("工序");
		operationNum.setName("计划数量");
		note.setName("备注");
		getValidationGroup().add(planDate.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(operation,Validators.notNull());
		getValidationGroup().add(operationNum,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(operationNum,Validators.numberMin(0));
		getValidationGroup().add(useTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(useTime,Validators.numberMin(0));
		
		
	}
}
