package ui.costPanes;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import ui.customComponet.JTextField;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
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
	private JTextField operationId;
	private JComboBox<Employee> employee          ;
	private JComboBox<Employee> superintendent          ;
	private JComboBox<Employee> firstChecker          ;
	private JComboBox<Employee> checker           ;
	private JXDatePicker finishDate               ;
	private JComboBox<Employee> prepareEmployee   ;
	private JTextField checkData;
	private JTextField planId;
	private JTextField operationName;
	private JLabel label;
	private JTextField billId;
	private JLabel label_1;
	private JTextField picId;

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
		operationId= new JTextField();
		employee= new JComboBox<Employee>(new Vector<Employee>(Employee.loadOperaters()));
		checker= new JComboBox<Employee>();
		finishDate=new JXDatePicker();
		prepareEmployee=new JComboBox<Employee>(new Vector<Employee>(Employee.loadOperaters()));
		superintendent=new JComboBox<Employee>(new Vector<Employee>(Employee.loadSuperintendents()));
		firstChecker=new JComboBox<Employee>(new Vector<Employee>(Employee.loadAll()));
		checkData=new JTextField();

		add( new JLabel("操作员:"), "2, 2, right, default");		
		add(employee, "4, 2, fill, default");
		employee.setName("操作员");


		add( new JLabel("工艺编号:"), "6, 2, right, default");		
		add(operationId, "8, 2, fill, default");
		operationId.setName("工艺编号");

		add(new JLabel("工艺卡号:"), "2, 4, right, default");
		planId=new JTextField();
		planId.setEditable(false);
		add(planId, "4, 4, fill, default");

		operationName=new JTextField();
		operationName.setEditable(false);
		add(new JLabel("工艺名:"), "6, 4, right, default");
		add(operationName, "8, 4, fill, default");
		
		label = new JLabel("\u8BA2\u5355\u53F7:");
		add(label, "2, 6, right, default");
		
		billId = new JTextField();
		billId.setEditable(false);
		add(billId, "4, 6, fill, default");
		
		label_1 = new JLabel("\u56FE\u53F7:");
		add(label_1, "6, 6, right, default");
		
		picId = new JTextField();
		picId.setEditable(false);
		add(picId, "8, 6, fill, default");


		add(new JLabel("投入数:"), "2, 8, right, default");
		add(getNum, "4, 8, fill, default");
		getNum.setName("实发数");


		add(new JLabel("良品数:"), "6, 8, right, default");
		add(productNum, "8, 8, fill, default");
		productNum.setName("良品数");

		add(new JLabel("不良数:"), "2, 10, right, default");
		add(scrapNum, "4, 10, fill, default");
		scrapNum.setName("不良数");


		add(new JLabel("不良描述:"), "6, 10, right, default");		
		add(scrapReason, "8, 10, fill, default");

		add(new JLabel("用时:"), "2, 12, right, default");		
		add(useTime, "4, 12, fill, default");
		useTime.setName("用时");
		JLabel label_11 = new JLabel("生产时间:");
		add(label_11, "6, 12, right, default");

        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        finishDate.setDate(calendar.getTime());
        calendar=null;
		finishDate.setFormats(new String[] {"yyyy\u5E74MM\u6708dd\u65E5"});
		add(finishDate, "8, 12, left, default");
		finishDate.getEditor().setName("生产时间");

		add( new JLabel("调机用时:"), "2, 14, right, default");	
		add(prepareTime, "4, 14, fill, default");
		prepareTime.setName("调机用时");


		add(new JLabel("调机员:"), "6, 14, right, default");		
		add(prepareEmployee, "8, 14, fill, default");

		add(new JLabel("首件检验人:"), "2, 16, right, default");		
		add(firstChecker, "4, 16, fill, default");
		add(new JLabel("首件检验数据:"), "6, 16, right, default");		
		add(checkData, "8, 16, fill, default");

		add(new JLabel("检验员:"), "2, 18, right, default");		
		add(checker, "4, 18, fill, default");

		add(new JLabel("主管:"), "6, 18, right, default");		
		add(superintendent, "8, 18, fill, default");



		add(new JLabel("备注:"), "2, 20, right, default");		
		add(note, "4, 20, fill, default");
		operationId.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				showOperationPlan();

			}

			

			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根

			}
		});
		addValidators();
		addEnterKeyAction();
	}
	private void showOperationPlan() {
		if(StringUtils.isNoneBlank(operationId.getText())){
			try{
				OperationPlan operationPlan= BeanMao.beanManager.find(OperationPlan.class, Integer.valueOf(operationId.getText()));
				if(operationPlan==null) {
					JOptionPane.showMessageDialog(this,"没有找到对应的工艺,请确认有没有输入正确");
					operationId.setText(null);
					return;					
				}
				billId.setText(operationPlan.getPicPlan().getPic().getBillId());
				picId.setText(operationPlan.getPicPlan().getPic().getPicid());
				operationName.setText(operationPlan.getName());
				planId.setText(new DecimalFormat("000,000").format(operationPlan.getPicPlan().getId()).replaceAll(",", "-"));
				getBean().setOperationPlan(operationPlan);
			}catch(Exception e1){
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,"没有找到对应的工艺,请确认有没有输入正确");
				operationId.setText(null);
			}
		}
		
	}
	public void addValidators() {


		getValidationGroup().add(getNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(getNum,Validators.numberMin(0));
		getValidationGroup().add(getNum,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(operationId,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(operationId,Validators.numberMin(0));
		getValidationGroup().add(operationId,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(productNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(productNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(scrapNum,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(useTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(useTime,Validators.numberMin(0));
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		getValidationGroup().add(employee,Validators.notNull());
		getValidationGroup().add(finishDate.getEditor(),Validators.notNull());
		getValidationGroup().add(finishDate.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("getNum"), getNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("productNum"), productNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapNum"), scrapNum, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("scrapReason"), scrapReason, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("workTime"), useTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("checkData"), checkData, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("finishDate"), finishDate,  BeanProperty.create("date")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("employee"), employee, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("checker"), checker, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("prepareEmployee"), prepareEmployee, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("firstChecker"), firstChecker, comboBoxBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,  BeanProperty.create("superintendent"), superintendent, comboBoxBeanProperty));



	}
	private void addEnterKeyAction() {
		KeyListener enterKeyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					((JComponent) e.getSource()).transferFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根

			}
		};
		
		getNum.addKeyListener(enterKeyListener);
		productNum.addKeyListener(enterKeyListener);
		scrapNum.addKeyListener(enterKeyListener);
		scrapReason.addKeyListener(enterKeyListener);
		prepareTime.addKeyListener(enterKeyListener);
		useTime.addKeyListener(enterKeyListener);
		note.addKeyListener(enterKeyListener);
		operationId.addKeyListener(enterKeyListener);
		employee.addKeyListener(enterKeyListener);
		superintendent.addKeyListener(enterKeyListener);
		firstChecker.addKeyListener(enterKeyListener);
		checker.addKeyListener(enterKeyListener);
		finishDate.addKeyListener(enterKeyListener);
		prepareEmployee.addKeyListener(enterKeyListener);
		checkData.addKeyListener(enterKeyListener);
		operationName.addKeyListener(enterKeyListener);
		planId.addKeyListener(enterKeyListener);
	}
}
