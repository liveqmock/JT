package ui.panels;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Material;

public class MaterialsPanel extends BeansPanel<Material> {

	

	public MaterialsPanel(Bill bill) {
		super(bill.getmaterials(), new MaterialPanel(bill.getFirstMaterial()),Material.class);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public Material saveBean() {
		try {
			this.getBeanPanel().getBean().save();
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | IntrospectionException | SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return this.getBeanPanel().getBean();
	}

	

}
