package ui.panels;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import ui.customComponet.BeanPanel;
import validation.builtin.Validators;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Material;

public class MaterialPanel extends BeanPanel<Material> {

	private JTextField bill; 
	private JComboBox<String>  name ;
	private JTextField unitName;
	private JTextField unitCost;
	private JTextField num ;
	public MaterialPanel(Material bean) {
		super(bean);
	}
	@Override
	protected void createContents() {
		Layout();

		name.setEditable(true);
		
		name.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED)
					getMaterial((String)arg0.getItem());
				
			}
		});
		


		name.setName("��������");
		unitName.setName("�Ƽ۵�λ");
		unitCost.setName("����");
		num.setName("����");
		getValidationGroup().add(name,Validators.notNull());
		getValidationGroup().add(unitName,Validators.notNull());
		getValidationGroup().add(unitCost,Validators.numberMin(0));
		getValidationGroup().add(num,Validators.numberMin(0));
	}


	private void Layout() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		List names = BeanMao.beanManager.getEm().createQuery( "select distinct name from Material ").getResultList();
		
		bill=new JTextField(); 
		name=new JComboBox<String> (new Vector<String>(names)) ;
		unitName=new JTextField() ;
		unitCost =new JTextField();
		num=new JTextField() ;
		
		add( new JLabel("ͼ��"), "2, 2, right, default");		
		add(bill, "4, 2, fill, default");

		add(new JLabel("��������"), "2, 4, right, default");		
		add(name, "4, 4, fill, default");
		
		add(new JLabel("�Ƽ۵�λ"), "2, 6, right, default");		
		add(unitName, "4, 6, fill, default");

		add(new JLabel("����"), "2, 8, right, default");
		add(unitCost, "4, 8, fill, default");

		add(new JLabel("����"), "2, 10, right, default");
		add(num, "4, 10, fill, default");
		
	}
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("name"), name,  BeanProperty.create("selectedItem")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("unitCost"), unitCost, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("unitName"), unitName, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, editBean, BeanProperty.create("num"), num, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, editBean, BeanProperty.create("bill.picid"), bill, jTextFieldBeanProperty));
		
		
	}
	private void getMaterial(String nameStr) {
		try {
			 Object[] object = (Object[]) BeanMao.beanManager.getEm().createNativeQuery("select top 1 unitName,unitCost from material where name='"+nameStr+"' order by  createDate desc").getSingleResult();
			
			 unitName.setText((String) object[0]);
			unitCost.setText(String.valueOf( String.valueOf(object[1])));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
}
