package ui.panels;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Material;

public class MaterialsPanel extends BeansPanel<Material> {

	

	public MaterialsPanel(Bill bill) {
		super(bill.getMaterials(), new MaterialPanel(null),Material.class);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public Material saveBean() {
		this.getBeanPanel().getBean().save();
		
		return this.getBeanPanel().getBean();
	}

	

}
