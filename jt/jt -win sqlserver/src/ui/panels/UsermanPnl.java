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
		// TODO �Զ����ɵĹ��캯�����
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
		
		JLabel lblNewLabel = new JLabel("������");
		add(lblNewLabel, "2, 2, right, default");
		
		name = new JTextField();
		add(name, "4, 2, fill, default");
		name.setColumns(10);
		JLabel label = new JLabel("����");
		add(label, "2, 4, right, default");
		
		levelStr = new JComboBox<String>(new String[] {"����Ա", "��", "ͳ��Ա", "�ֿ����Ա"});
		add(levelStr, "4, 4, fill, default");

		label_1 = new JLabel("\u6743\u9650\uFF1A");
		add(label_1, "2, 6");
		
		list = new JCheckBoxPanel(new String[]{"�½�����","�鿴������","�޸Ķ���","���ͼֽ",
					"ɾ������","ȡ������","�����ɫ","��ӷ�����Ϣ","��ӷ�Ʊ��Ϣ","�ز��趨",
					"�½�����","�¶�ͳ��","���ͳ��","Ա������ͳ��","�鿴�豸ʹ�����","�����ͻ�����","��Э�ͻ�����",
					"�鿴�Ų��ƻ�","Ա������ͳ��","�������ͳ��","�����ɱ�ͳ��","�����ƻ���ʵ�ʳɱ�����","�豸����",
					"������Ա����","�û�����","�½�����","�޸Ķ���","�޸�ͼֽ","�鿴ͼֽ","ɾ��ͼֽ","����","��־��ͼֽ�����","�������Ϲ���",
					"�����ɫ","�Ų��ƻ�¼��","��������¼��","���������","��������"}
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
