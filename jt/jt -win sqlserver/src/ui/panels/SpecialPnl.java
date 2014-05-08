package ui.panels;

import com.mao.jf.beans.PicBean;

import ui.customComponet.BeanPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class SpecialPnl extends BeanPanel<PicBean> {

	private JCheckBox checkBox;
	private JTextField man;

	public SpecialPnl(PicBean bean) {
		super(bean);
		
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void dataBinding() {
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("special"), checkBox, BeanProperty.create("selected")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("specialMan"), man, BeanProperty.create("text")));
		
		
	}

	@Override
	protected void createContents() {
		setLayout(new MigLayout("", "[][]", "[][]"));
		
		JLabel lblNewLabel = new JLabel("\u662F\u5426\u4E3A\u7279\u91C7:");
		add(lblNewLabel, "cell 0 0,alignx right");
		
		checkBox = new JCheckBox("特殊采用");
		add(checkBox, "cell 1 0");
		
		JLabel label = new JLabel("\u5BA2\u6237\u786E\u8BA4\u4EBA:");
		add(label, "cell 0 1");
		man=new JTextField();
		add(man, "cell 1 1");
		
	}

}
