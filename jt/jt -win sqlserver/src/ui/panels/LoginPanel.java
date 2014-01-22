package ui.panels;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.mao.jf.beans.Userman;

public class LoginPanel extends BeanPanel<Userman> {
	private JTextField userNameField;
	private JPasswordField passwordField;

	public LoginPanel(Userman editBean) {
		super(editBean);
	}
	@Override
	protected void createContents(){
		setLayout(new MigLayout("", "[][grow]", "[][]"));
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		add(label, "cell 0 0,alignx trailing");

		userNameField = new JTextField();
		add(userNameField, "cell 1 0,growx");
		userNameField.setColumns(10);

		JLabel label_1 = new JLabel("\u5BC6  \u7801\uFF1A");
		add(label_1, "cell 0 1,alignx trailing");

		passwordField = new JPasswordField();
		add(passwordField, "cell 1 1,growx");

		userNameField.setName("�û���");
		passwordField.setName("����");
		getValidationGroup().add(userNameField, Validators.notNull());
		getValidationGroup().add(userNameField,
				Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(passwordField, Validators.notNull());
		getValidationGroup().add(passwordField,
				Validators.REQUIRE_NON_EMPTY_STRING);

		dataBinding();
	}

	protected void dataBinding() {
		BeanProperty<Userman, String> userBeanProperty = BeanProperty
				.create("name");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty
				.create("text");
		bindingGroup.addBinding(Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,
						userBeanProperty, userNameField, jTextFieldBeanProperty));
		//
		BeanProperty<Userman, String> userBeanProperty_1 = BeanProperty
				.create("password");
		BeanProperty<JPasswordField, String> jPasswordFieldBeanProperty = BeanProperty
				.create("text");
		bindingGroup.addBinding(Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, editBean,
						userBeanProperty_1, passwordField,
						jPasswordFieldBeanProperty));
	}
}
