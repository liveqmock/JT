package ui.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.Userman;

import org.jdesktop.swingx.JXList;

import javax.swing.AbstractListModel;

public class UsermanPnl extends BeanPanel<Userman> {
	public UsermanPnl(Userman bean) {
		super(bean);
		// TODO �Զ����ɵĹ��캯�����
	}

	private JTextField name;
	private JCheckBoxPanel list;
	private JLabel label_1;

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
		
		label_1 = new JLabel("\u6743\u9650\uFF1A");
		add(label_1, "2, 4");
		
		list = new JCheckBoxPanel(new String[]{"�½�����","�鿴������","�޸Ķ���","���ͼֽ",
					"ɾ������","ȡ������","�����ɫ","��ӷ�Ʊ��Ϣ","��ӷ�Ʊ��Ϣ",
					"�½�����","�¶�ͳ��","���ͳ��","Ա������ͳ��","�鿴�豸ʹ�����","�����ͻ�����","��Э�ͻ�����",
					"�鿴�Ų��ƻ�","Ա������ͳ��","�������ͳ��","�����ɱ�ͳ��","�����ƻ���ʵ�ʳɱ�����","�豸����",
					"������Ա����","�û�����","�½�����","�޸Ķ���","�޸�ͼֽ","�鿴ͼֽ","ɾ��ͼֽ","����","��־��ͼֽ�����","�������Ϲ���",
					"�����ɫ","��ӷ�����Ϣ","�Ų��ƻ�¼��","��������¼��","��ӷ�Ʊ��Ϣ"}
			);
		add(list, "4, 4, fill, fill");

		setPreferredSize(new Dimension(100, 300));
	}

	@Override
	protected void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("name"), name, jTextFieldBeanProperty));
		bindingGroup.addBinding( Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("menus"), list,  BeanProperty.create("value")));
		
	}

	@Override
	public void setBean(Userman bean) {
		
		super.setBean(bean);
	}

}
