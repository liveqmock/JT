package windows.panels;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import validation.builtin.Validators;
import windows.customComponet.BeanPanel;

import com.mao.jf.beans.User;

public class LoginPanel extends BeanPanel<User> {
	private JTextField userNameField;
	private JPasswordField passwordField;

	public LoginPanel(User bean) {
		super(bean);
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

		userNameField.setName("”√ªß√˚");
		passwordField.setName("√‹¬Î");
		getValidationGroup().add(userNameField, Validators.notNull());
		getValidationGroup().add(userNameField,
				Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(passwordField, Validators.notNull());
		getValidationGroup().add(passwordField,
				Validators.REQUIRE_NON_EMPTY_STRING);

		dataBinding();
	}

	protected void dataBinding() {
		BeanProperty<User, String> userBeanProperty = BeanProperty
				.create("name");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty
				.create("text");
		bindingGroup.addBinding(Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						userBeanProperty, userNameField, jTextFieldBeanProperty));
		//
		BeanProperty<User, String> userBeanProperty_1 = BeanProperty
				.create("password");
		BeanProperty<JPasswordField, String> jPasswordFieldBeanProperty = BeanProperty
				.create("text");
		bindingGroup.addBinding(Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						userBeanProperty_1, passwordField,
						jPasswordFieldBeanProperty));
	}
}
