package windows.panels;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import windows.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Userman;

public class UsermanPnl extends BeanPanel<Userman> {
	public UsermanPnl(Userman bean) {
		super(bean);
		// TODO 自动生成的构造函数存根
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
		
		JLabel lblNewLabel = new JLabel("姓名：");
		add(lblNewLabel, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(10);
		
		JLabel label = new JLabel("级别：");
		add(label, "2, 4, right, default");
		
		levelStr = new JComboBox();
		levelStr.setModel(new DefaultComboBoxModel(new String[] {"管理员", "计划派工", "统计员", "仓库管理员"}));
		add(levelStr, "4, 4, fill, default");
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("levelStr"), levelStr,  BeanProperty.create("selectedItem")));
		
	}

}
