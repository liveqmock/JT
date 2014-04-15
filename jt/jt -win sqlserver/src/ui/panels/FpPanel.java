package ui.panels;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import validation.builtin.NumberMaxE;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BackRepair;
import com.mao.jf.beans.FpBean;
import com.mao.jf.beans.ShipingBean;

public class FpPanel extends BeanPanel<FpBean> {
private JTextField fbNoFld;
private JTextField money;
private JXDatePicker createDate;
private JTextField billNoFld;
private NumberMaxE moneyMaxE;
	public FpPanel(FpBean bean) {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("订单号：");
		add(lblNewLabel, "2, 2, right, default");
		
		billNoFld = new JTextField();
		add(billNoFld, "4, 2, fill, default");
		billNoFld.setEditable(false);

		fbNoFld = new JTextField();
		add(fbNoFld, "4, 4, fill, default");

		createDate = new JXDatePicker();
		createDate.setFormats(new String[] {"yyyy-MM-dd"});
		add(createDate, "4, 6, fill, default");

		money = new JTextField();
		add(money, "4, 8, fill, default");
		JLabel numLabel = new JLabel("发票号码：");
		add(numLabel, "2, 4");
		
		JLabel label = new JLabel("开票日期：");
		add(label, "2, 6");
		
		JLabel label_1 = new JLabel("金  额：");
		add(label_1, "2, 8, right, default");
		
		
		fbNoFld.setName("发票号码");
		createDate.getEditor().setName("开票时间");
		money.setName("发票金额");
		moneyMaxE=new NumberMaxE(this.bean.getBill().getRemainNotFbMoney());
		getValidationGroup().add(fbNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(fbNoFld,Validators.notNull());
		getValidationGroup().add(money,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(money,moneyMaxE);
		getValidationGroup().add(createDate.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
	}
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("bill.billid"), billNoFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("fpNo"), fbNoFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("money"), money, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("createDate"), createDate, BeanProperty.create("date")));

	}
	@Override
	public void setBean(FpBean bean) {
		// TODO 自动生成的方法存根
		super.setBean(bean);
		moneyMaxE.setMax(bean.getBill().getRemainNotFbMoney());
	}
	
	
}
