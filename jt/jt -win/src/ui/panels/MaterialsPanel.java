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
		// TODO 自动生成的构造函数存根
	}

	@Override
	public Material saveBean() {
		try {
			this.getBeanPanel().getBean().save();
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | IntrospectionException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return this.getBeanPanel().getBean();
	}

	

}
