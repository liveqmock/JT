package ui.panels;

import java.awt.Dimension;

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
	public UsermanPnl(Userman bean) {
		super(bean);
		// TODO 自动生成的构造函数存根
	}

	private JTextField name;
	private JCheckBoxPanel list;
	private JLabel label_1;
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblNewLabel = new JLabel("姓名：");
		add(lblNewLabel, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(10);
		JLabel label = new JLabel("级别：");
		add(label, "2, 4, right, default");
		
		levelStr = new JComboBox<String>(new String[] {"管理员", "工", "统计员", "仓库管理员"});
		add(levelStr, "4, 4, fill, default");

		label_1 = new JLabel("\u6743\u9650\uFF1A");
		add(label_1, "2, 6");
		
		list = new JCheckBoxPanel(new String[]{"新建订单","查看订单组","修改订单","添加图纸",
					"删除订单","取消订单","标记颜色","添加发货信息","添加发票信息","特采设定",
					"新建订单","月度统计","年度统计","员工产出统计","查看设备使用情况","订单客户管理","外协客户管理",
					"查看排产计划","员工产出统计","工序产出统计","生产成本统计","生产计划与实际成本对照","设备管理",
					"操作人员管理","用户管理","新建订单","修改订单","修改图纸","查看图纸","删除图纸","返修","标志此图纸已完结","生产材料管理",
					"标记颜色","排产计划录入","生产数据录入","生产情况表","检验数据"}
			);
		add(list, "4, 6, fill, fill");

		setPreferredSize(new Dimension(315, 322));
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("levelStr"), levelStr,  BeanProperty.create("selectedItem")));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("menus"), list,  BeanProperty.create("value")));
		
	}

	@Override
	public void setBean(Userman bean) {
		
		super.setBean(bean);
	}

}
