package ui.panels;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Material;

public class MaterialsPanel extends BeansPanel<Material> {

	

	public MaterialsPanel(Bill bill) {
		super(bill.getMaterials(), new MaterialPanel(null),Material.class);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public Material saveBean() {
		this.getBeanPanel().getBean().save();
		
		return this.getBeanPanel().getBean();
	}

	

}
