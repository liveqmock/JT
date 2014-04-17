package ui.panels;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Custom;

public class CustomPanel extends BeanPanel<Custom> {
	private JTextField nameFld;
	private JTextField telFld;
	private JTextField addressFld;
	private JTextField faxFld;
	private JTextField contactFld;
	private JTextField emailFld;
	public CustomPanel(Custom bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default:grow"),},
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
		
		JLabel label = new JLabel("\u5BA2\u6237\u540D\u79F0\uFF1A");
		add(label, "2, 2, right, default");
		
		nameFld = new JTextField();
		add(nameFld, "4, 2, fill, default");
		nameFld.setColumns(20);
		
		JLabel lblNewLabel = new JLabel("\u7535\u8BDD\uFF1A");
		add(lblNewLabel, "2, 4, right, default");
		
		telFld = new JTextField();
		telFld.setColumns(10);
		add(telFld, "4, 4, fill, default");
		
		JLabel label_1 = new JLabel("\u5730\u5740\uFF1A");
		add(label_1, "2, 6, right, default");
		
		addressFld = new JTextField();
		addressFld.setColumns(10);
		add(addressFld, "4, 6, fill, default");
		
		JLabel label_2 = new JLabel("\u4F20\u771F\uFF1A");
		add(label_2, "2, 8, right, default");
		
		faxFld = new JTextField();
		faxFld.setColumns(10);
		add(faxFld, "4, 8, fill, default");
		
		JLabel label_3 = new JLabel("\u8054\u7CFB\u4EBA\uFF1A");
		add(label_3, "2, 10, right, default");
		
		contactFld = new JTextField();
		contactFld.setColumns(10);
		add(contactFld, "4, 10, fill, default");
		
		JLabel lblEmail = new JLabel("Email\uFF1A");
		add(lblEmail, "2, 12, right, default");
		
		emailFld = new JTextField();
		emailFld.setColumns(10);
		add(emailFld, "4, 12, fill, default");
		nameFld.setName("¿Í»§Ãû³Æ");
		getValidationGroup().add(nameFld,Validators.notNull());
	}


	@Override
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("name"), nameFld, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("tel"), telFld, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("address"), addressFld, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("fax"), faxFld, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("contact"), contactFld, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("email"), emailFld, jTextFieldBeanProperty));
		
		
	}
}
