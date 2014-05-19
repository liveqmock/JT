package ui.panels;

import javax.swing.JLabel;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import ui.customComponet.JTextField;
import validation.builtin.NumberMaxE;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.OutFpBean;

public class FpOutPanel extends BeanPanel<OutFpBean> {
private JTextField fbNoFld;
private JTextField money;
private JXDatePicker createDate;
private JTextField billNoFld;
private NumberMaxE moneyMaxE;
private JTextField content;
	public FpOutPanel(OutFpBean bean) {
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
		
		JLabel lblNewLabel = new JLabel("�����ţ�");
		add(lblNewLabel, "2, 2, right, default");
		
		billNoFld = new JTextField();
		add(billNoFld, "4, 2, fill, default");
		billNoFld.setEditable(false);

		fbNoFld = new JTextField();
		add(fbNoFld, "4, 4, fill, default");

		createDate = new JXDatePicker();
		createDate.setFormats(new String[] {"yyyy-MM-dd"});
		add(createDate, "4, 6, left, default");

		money = new JTextField();
		add(money, "4, 8, fill, default");
		JLabel numLabel = new JLabel("��Ʊ���룺");
		add(numLabel, "2, 4");
		
		content=new JTextField();
		add(content, "4, 10, fill, default");
		
		JLabel label = new JLabel("��Ʊ���ڣ�");
		add(label, "2, 6");
		
		JLabel label_1 = new JLabel("��  �");
		add(label_1, "2, 8, right, default");
		
		add( new JLabel("��Ʊ���ݣ�"), "2, 10");
		
		
		fbNoFld.setName("��Ʊ����");
		createDate.getEditor().setName("��Ʊʱ��");
		money.setName("��Ʊ���");
		moneyMaxE=new NumberMaxE(this.bean.getPic().getRemainOutNotFbMoney());
		getValidationGroup().add(fbNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(fbNoFld,Validators.notNull());
		getValidationGroup().add(money,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		getValidationGroup().add(money,moneyMaxE);
		getValidationGroup().add(createDate.getEditor(),Validators.REQUIRE_NON_EMPTY_STRING);
	}
	@Override
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("pic.picid"), billNoFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("fpNo"), fbNoFld, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("money"), money, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("createDate"), createDate, BeanProperty.create("date")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("content"), content, jTextFieldBeanProperty));

	}
	@Override
	public void setBean(OutFpBean bean) {
		// TODO �Զ����ɵķ������
		super.setBean(bean);
		if(moneyMaxE==null)
			moneyMaxE=new NumberMaxE(this.bean.getPic().getRemainOutNotFbMoney());
		else 
			moneyMaxE.setMax(bean.getPic().getRemainOutNotFbMoney());
	}
	
	
}