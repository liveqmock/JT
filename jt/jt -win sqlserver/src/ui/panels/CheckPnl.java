package ui.panels;

import java.util.Vector;

import com.mao.jf.beans.Employee;
import com.mao.jf.beans.PicBean;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class CheckPnl extends BeanPanel<PicBean> {

	private JComboBox<Employee> checker;
	private JTextField nondefective;
	private JTextField defective;

	public CheckPnl(PicBean bean) {
		super(bean);
		
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("checker"), checker, BeanProperty.create("selectedItem")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("nondefective"), nondefective, BeanProperty.create("text")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("defective"), defective, BeanProperty.create("text")));
		
		
	}

	@Override
	protected void createContents() {
		setLayout(new MigLayout("", "[][]", "[][][]"));
		
		JLabel lblNewLabel = new JLabel("检验员:");
		add(lblNewLabel, "cell 0 0,alignx right");
		
		checker = new JComboBox<>(new Vector<Employee>(Employee.loadCheckers()));
		add(checker, "cell 1 0");
		
		add(new JLabel("良品数:"), "cell 0 1");
		nondefective=new JTextField();
		add(nondefective, "cell 1 1,growx");
		defective=new JTextField();
		add(new JLabel("不良数:"), "cell 0 2");
		add(defective, "cell 1 2,growx");
		defective.setName("不良数");
		nondefective.setName("良品数");
		validationGroup.add(nondefective,Validators.REQUIRE_VALID_INTEGER);
		validationGroup.add(nondefective,Validators.REQUIRE_NON_EMPTY_STRING);
		validationGroup.add(nondefective,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		validationGroup.add(defective,Validators.REQUIRE_VALID_INTEGER);
		validationGroup.add(defective,Validators.REQUIRE_NON_EMPTY_STRING);
		validationGroup.add(defective,Validators.REQUIRE_NON_NEGATIVE_NUMBER);
		
	}

}
