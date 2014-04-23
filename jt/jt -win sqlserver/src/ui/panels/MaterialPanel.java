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
		


		name.setName("材料名称");
		unitName.setName("计价单位");
		unitCost.setName("单价");
		num.setName("数量");
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
		
		List names = BeanMao.beanManager.queryList( "select distinct name from Material",String.class);
		
		bill=new JTextField(); 
		name=new JComboBox<String> (new Vector<String>(names)) ;
		unitName=new JTextField() ;
		unitCost =new JTextField();
		num=new JTextField() ;
		
		add( new JLabel("图号"), "2, 2, right, default");		
		add(bill, "4, 2, fill, default");

		add(new JLabel("材料名称"), "2, 4, right, default");		
		add(name, "4, 4, fill, default");
		
		add(new JLabel("计价单位"), "2, 6, right, default");		
		add(unitName, "4, 6, fill, default");

		add(new JLabel("单价"), "2, 8, right, default");
		add(unitCost, "4, 8, fill, default");

		add(new JLabel("数量"), "2, 10, right, default");
		add(num, "4, 10, fill, default");
		
	}
	@Override
	protected  void dataBinding() {
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("name"), name,  BeanProperty.create("selectedItem")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("unitCost"), unitCost, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("unitName"), unitName, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("num"), num, jTextFieldBeanProperty));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ, bean, BeanProperty.create("bill.picid"), bill, jTextFieldBeanProperty));
		
		
	}
	private void getMaterial(String nameStr) {
		try {
			 Object[] object = (Object[]) BeanMao.beanManager.querySingle("select top 1 unitName,unitCost from Material where name=?1 order by  createDate desc",Object[].class,nameStr);
			
			 unitName.setText((String) object[0]);
			unitCost.setText(String.valueOf( String.valueOf(object[1])));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
