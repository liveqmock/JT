package ui.costPanes;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.Bidi;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationPlan;

public class OperationPlanPnl extends BeanPanel<OperationPlan> implements FocusListener{
	private JTextField unitUseTime     ;
	private JTextField prepareTime   ;
	private JTextField planCost;
	private JTextField note    ;
	private JComboBox<String> operations;

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
				FormFactory.DEFAULT_ROWSPEC,}));
		unitUseTime=new JTextField();
		prepareTime=new JTextField();
		planCost=new JTextField();
		planCost.setBackground(Color.WHITE);
		planCost.setForeground(Color.RED);
		planCost.setEditable(false);
		note=new JTextField();
		
		operations= new JComboBox<String>(new Vector<String>( BeanMao.beanManager.getEm().createQuery("select name from Operation").getResultList()));
		

		
		add( new JLabel("工序:"), "2, 2, right, default");		
		add(operations, "4, 2, fill, default");

		add(new JLabel("\u5355\u4EF6\u52A0\u5DE5\u65F6\u95F4:"), "2, 4, right, default");
		add(unitUseTime, "4, 4, fill, default");
		unitUseTime.setName("实发数");
		
		add(new JLabel("\u8C03\u673A\u65F6\u95F4:"), "2, 6, right, default");
		add(prepareTime, "4, 6, fill, default");
		prepareTime.setName("报废数");
		
		add(new JLabel("\u5907\u6CE8:"), "2, 8, right, default");		
		add(note, "4, 8, fill, default");
		note.setName("用时");


		add( new JLabel("\u672C\u5DE5\u5E8F\u8D39\u7528:"), "2, 10, right, default");	
		add(planCost, "4, 10, fill, default");
		
		unitUseTime.addFocusListener(this);
		prepareTime.addFocusListener(this);
		
		addValidators();
	}
	public void addValidators() {		
		getValidationGroup().add(operations,Validators.notNull());
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(unitUseTime,Validators.numberMin(0));
		getValidationGroup().add(unitUseTime,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(prepareTime,Validators.REQUIRE_VALID_NUMBER);
		getValidationGroup().add(prepareTime,Validators.numberMinE(0));
		getValidationGroup().add(prepareTime,Validators.REQUIRE_NON_EMPTY_STRING);
	}
	
	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,  BeanProperty.create("unitUseTime"), unitUseTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,  BeanProperty.create("prepareTime"), prepareTime, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,  BeanProperty.create("note"), note, jTextFieldBeanProperty));
		BeanProperty<Object, Object> comboBoxBeanProperty = BeanProperty.create("selectedItem");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,  BeanProperty.create("name"), operations, comboBoxBeanProperty));

	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		try{
		planCost.setText(String.valueOf(editBean.getPlanCost()));
		}catch(Exception exception){
			
		}
	}
	
	

}
