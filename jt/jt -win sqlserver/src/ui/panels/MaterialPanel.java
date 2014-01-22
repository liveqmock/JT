package ui.panels;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import com.mao.jf.beans.Material;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

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
		try(Statement st=SessionData.getConnection().createStatement()){
			ResultSet rs = st.executeQuery("select distinct name from (select name from material order by createDate desc) a");
			while(rs.next()){
				name.addItem(rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		name.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				getMaterial((String)arg0.getItem());
				
			}
		});
		
		editBean.setEnterEmployee(Userman.loginUser);

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
		
		bill=new JTextField(); 
		name=new JComboBox<String> () ;
		unitName=new JTextField() ;
		unitCost =new JTextField();
		num=new JTextField() ;
		
		add( new JLabel("������"), "2, 2, right, default");		
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
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, editBean, BeanProperty.create("billNo"), bill, jTextFieldBeanProperty));
		
		
	}
	private void getMaterial(String nameStr) {
		try {
			Material material= Material.load(Material.class,"select * from material where  name='"+nameStr+"' order by createdate desc");
			unitName.setText(material.getUnitName());
			unitCost.setText(String.valueOf( material.getUnitCost()));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
}
