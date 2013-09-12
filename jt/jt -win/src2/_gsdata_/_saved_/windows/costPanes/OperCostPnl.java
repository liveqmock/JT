package windows.costPanes;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import validation.builtin.NumberMaxE;
import validation.builtin.Validators;
import validation.ui.ValidationGroup;
import windows.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.OperCost;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.PlanCost;

public class OperCostPnl extends BeanPanel<OperCost> {

	private JTextField assignNum;
	private JTextField scrapReason;
	private JTextField note;
	private JLabel billId;
	private JLabel itemId;
	private JXDatePicker createDate;
	private JComboBox<PlanCost> planCost;
	private JTextField productNum;
	private JTextField scrapnum;
	private JTextField operationNum;
	private JComboBox<Employee> operEmployee;
	private NumberMaxE assignMaxE;
	private JLabel operationUnit;
	public OperCostPnl(OperCost bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		assignMaxE=new NumberMaxE(0);
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel billitemL = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		add(billitemL, "2, 2");

		billId = new JLabel(""); 
		add(billId, "4, 2");

		JLabel label = new JLabel("\u9879\u76EE\u53F7\uFF1A");
		add(label, "6, 2");

		itemId = new JLabel("");
		add(itemId, "8, 2");

		JLabel lblNewLabel = new JLabel("\u6392\u4EA7\u65E5\u671F\uFF1A");
		add(lblNewLabel, "2, 6");

		createDate = new JXDatePicker();
		add(createDate, "4, 6, 5, 1");

		JLabel label_1 = new JLabel("\u8BA1\u5212\u5DE5\u5E8F\uFF1A");
		add(label_1, "2, 4, right, default");

		planCost = new JComboBox<PlanCost>(PlanCost.loadAllByBillHasAssing(bean.getBill()));

		planCost.setSelectedItem(null);

		add(planCost, "4, 4, 5, 1, fill, default");


		JLabel label_3 = new JLabel("\u64CD\u4F5C\u4EBA\u5458\uFF1A");
		add(label_3, "2, 8, right, default");
		try {
			operEmployee = new JComboBox<Employee>(Employee.loadAll(Employee.class));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		operEmployee.setSelectedItem(null);
		add(operEmployee, "4, 8, 5, 1, fill, default");

		JLabel label_4 = new JLabel("\u5206\u914D\u6570\u91CF\uFF1A");
		add(label_4, "2, 10, right, default");

		assignNum = new JTextField();
		add(assignNum, "4, 10, 5, 1, fill, default");
		assignNum.setColumns(10);

		JLabel label_2 = new JLabel("\u6210\u54C1\u6570\u91CF\uFF1A");
		add(label_2, "2, 12, right, default");

		productNum = new JTextField();
		productNum.setColumns(10);
		add(productNum, "4, 12, 5, 1, fill, default");

		JLabel label_7 = new JLabel("\u62A5\u5E9F\u6570\u91CF\uFF1A");
		add(label_7, "2, 14, right, default");

		scrapnum = new JTextField();
		scrapnum.setColumns(10);
		add(scrapnum, "4, 14, 5, 1, fill, default");

		JLabel label_5 = new JLabel("\u62A5\u5E9F\u539F\u56E0\uFF1A");
		add(label_5, "2, 16, right, default");

		scrapReason = new JTextField();
		scrapReason.setColumns(10);
		add(scrapReason, "4, 16, 5, 1, fill, default");

		JLabel label_18 = new JLabel("工序单位：");
		add(label_18, "2, 18, right, default");

		operationUnit = new JLabel();
		add(operationUnit, "4, 18, 5, 1, fill, default");

		JLabel label_8 = new JLabel("工序数量：");
		add(label_8, "2, 20, right, default");

		operationNum = new JTextField();
		operationNum.setColumns(10);
		add(operationNum, "4, 20, 5, 1, fill, default");

		JLabel label_6 = new JLabel("\u5907\u6CE8\uFF1A");
		add(label_6, "2, 22, right, default");
		note = new JTextField();
		note.setColumns(10);
		add(note, "4, 22, 5, 1, fill, default");


		planCost.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					if(bean.getId()>0) return;
					PlanCost sPlanCost = ((PlanCost)e.getItem());
					operEmployee.setEnabled(!sPlanCost.getIsOut());
					int max = sPlanCost.getMaxAsignNum();
					assignMaxE.setMax(max);
					if(max!=Integer.MAX_VALUE)
						assignNum.setText(String.valueOf(max));
					createDate.setDate(sPlanCost.getPlanDate());
					operationUnit.setText(((PlanCost)e.getItem()).getOperationUnit());
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		})	;	

		assignNum.setName("分派数量");
		operEmployee.setName("操作人");
		createDate.setName("排产时间");
		planCost.setName("计划工序");
		scrapReason.setName("报废原因");
		productNum.setName("成品数量");
		scrapnum.setName("报废数量");
		operationNum.setName("用时");
		

		getValidationGroup().add(assignNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(assignNum,Validators.numberMin(0));	
		getValidationGroup().add(assignNum,assignMaxE);
		getValidationGroup().add(planCost,Validators.notNull());
		getValidationGroup().add(createDate.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(operEmployee,Validators.notNull());
		getValidationGroup().add(scrapnum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(scrapnum,Validators.numberMinE(0));


		getValidationGroup().add(productNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(productNum,Validators.numberMinE(0));

		getValidationGroup().add(operationNum,Validators.numberMin(0));
		getValidationGroup().add(operationNum,Validators.REQUIRE_VALID_NUMBER);

	}

	@Override
	protected void dataBinding() {
		BeanProperty<Object, Object> textProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("bill.billid"), billId, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("bill.item"), itemId, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("createDate"), createDate, BeanProperty.create("date")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("productNum"), productNum, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("assignNum"), assignNum, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("scrapnum"), scrapnum, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("operationNum"), operationNum, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("scrapReason"), scrapReason, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("note"), note, textProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("operEmployee"), operEmployee, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("planCost"), planCost, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("operationUnit"), operationUnit, textProperty));


	}
	@Override
	public void setBean(OperCost bean) {
		
		super.setBean(bean);
		if(bean.getId()>0) {
			assignNum.setEnabled(false);
			createDate.setEnabled(false);
			planCost.setEnabled(false);
			operEmployee.setEnabled(false);

			scrapReason.setEnabled(true);
			productNum.setEnabled(true);
			productNum.setText(String.valueOf(bean.getAssignNum()));
			scrapnum.setEnabled(true);
			operationNum.setEnabled(true);
		}else {

			scrapReason.setEnabled(false);
			productNum.setEnabled(false);
			scrapnum.setEnabled(false);
			operationNum.setEnabled(false);
			
			assignNum.setEnabled(true);
			createDate.setEnabled(true);
			planCost.setEnabled(true);
			operEmployee.setEnabled(true);
		}
		
	}
	
}
