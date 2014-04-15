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
import com.mao.jf.beans.ShipingBean;

public class ShipingPanel extends BeanPanel<ShipingBean> {
private JTextField numFld;
private JTextField noteFld;
private JXDatePicker shipingDateFld;
private JTextField picFld;
private NumberMaxE moneyMaxE;
	public ShipingPanel(ShipingBean bean) {
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
		
		JLabel lblNewLabel = new JLabel("\u56FE\u53F7\uFF1A");
		add(lblNewLabel, "2, 2, right, default");
		
		picFld = new JTextField();
		add(picFld, "4, 2, fill, default");
		picFld.setEditable(false);

		numFld = new JTextField();
		add(numFld, "4, 4, fill, default");

		shipingDateFld = new JXDatePicker();
		shipingDateFld.setFormats(new String[] {"yyyy-MM-dd"});
		add(shipingDateFld, "4, 6, fill, default");

		noteFld = new JTextField();
		add(noteFld, "4, 8, fill, default");
		JLabel numLabel = new JLabel("\u53D1\u8D27\u6570\u91CF\uFF1A");
		add(numLabel, "2, 4");
		
		JLabel label = new JLabel("\u53D1\u8D27\u65F6\u95F4\uFF1A");
		add(label, "2, 6");
		
		JLabel label_1 = new JLabel("\u5907  \u6CE8\uFF1A");
		add(label_1, "2, 8, right, default");
		
		
		numFld.setName("数量");
		shipingDateFld.getEditor().setName("发货时间");
		noteFld.setName("备注");
		 moneyMaxE=new NumberMaxE(this.bean.getPic().getRemainNotShipingNum());
		getValidationGroup().add(numFld,moneyMaxE);
		getValidationGroup().add(numFld,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(numFld,Validators.REQUIRE_VALID_INTEGER);
		getValidationGroup().add(shipingDateFld.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
	}
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("pic.picid"), picFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("num"), numFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("note"), noteFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("shipingDate"), shipingDateFld, BeanProperty.create("date")));

	}
	@Override
	public void setBean(ShipingBean bean) {
		// TODO 自动生成的方法存根
		super.setBean(bean);
		moneyMaxE.setMax(bean.getPic().getRemainNotShipingNum());
	}

	
}
