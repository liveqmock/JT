package ui.panels;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import net.miginfocom.swing.MigLayout;
import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.mao.jf.beans.Userman;

public class ChangePasswdPanel extends BeanPanel<Userman> {
	private JLabel userNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;

	public ChangePasswdPanel(Userman bean) {
		super(bean);
	}
	@Override
	protected void createContents(){
		setLayout(new MigLayout("", "[right][grow]", "[][][][]"));
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		add(label, "cell 0 0,alignx trailing");

		userNameField = new JLabel(bean.getName());
		add(userNameField, "cell 1 0,growx");

		add(new JLabel("ԭ���룺"), "cell 0 1,alignx trailing");
		add(new JLabel("�����룺"), "cell 0 2,alignx trailing");
		add(new JLabel("��������飺"), "cell 0 3,alignx trailing");

		passwordField = new JPasswordField();
		passwordField1 = new JPasswordField();
		passwordField2 = new JPasswordField();
		add(passwordField, "cell 1 1,growx");
		add(passwordField1, "cell 1 2,growx");
		add(passwordField2, "cell 1 3,growx");

		userNameField.setName("�û���");
		passwordField.setName("����");
		passwordField1.setName("������");
		passwordField2.setName("���������");
		getValidationGroup().add(passwordField, Validators.notNull());
		getValidationGroup().add(passwordField,
				Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(passwordField1, Validators.notNull());
		getValidationGroup().add(passwordField1,
				Validators.REQUIRE_NON_EMPTY_STRING);
		getValidationGroup().add(passwordField2, Validators.notNull());
		getValidationGroup().add(passwordField2,
				Validators.REQUIRE_NON_EMPTY_STRING);
	}

	protected void dataBinding() {
		//
	}
	public boolean isOk() {
		if(bean.getPassword().equals(passwordField.getText())&&passwordField1.getText().equals(passwordField2.getText())){
			bean.setPassword(passwordField1.getText());
			bean.save();
				
			return true;
		}else if(!bean.getPassword().equals(passwordField.getText())){
			JOptionPane.showMessageDialog(this, "ԭ���벻��ȷ��");
		}else if(!passwordField1.getText().equals(passwordField2.getText())){
			JOptionPane.showMessageDialog(this, "�������ٴ����벻һ����");
		}
		return false;
	}
}
