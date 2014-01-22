package ui.panels;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Userman;

public class UsermanPnl extends BeanPanel<Userman> {
	public UsermanPnl(Userman editBean) {
		super(editBean);
		// TODO �Զ����ɵĹ��캯�����
	}

	private JTextField name;
	private JComboBox<String> levelStr;

	@Override
	protected void createContents() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("������");
		add(lblNewLabel, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(10);
		
		JLabel label = new JLabel("����");
		add(label, "2, 4, right, default");
		
		levelStr = new JComboBox();
		levelStr.setModel(new DefaultComboBoxModel(new String[] {"����Ա", "�ƻ��ɹ�", "ͳ��Ա", "�ֿ����Ա"}));
		add(levelStr, "4, 4, fill, default");
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("levelStr"), levelStr,  BeanProperty.create("selectedItem")));
		
	}

}
