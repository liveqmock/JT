package ui.panels;

import ui.customComponet.BeansPanel;

import com.mao.jf.beans.Material;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.Userman;

public class MaterialsPanel extends BeansPanel<Material> {

	public MaterialsPanel(PicBean pic) {
		super(pic.getMaterials(), new MaterialPanel(new Material(pic)),Material.class,true);
		
	}

	@Override
	public Material saveBean() {
		this.getPanelBean().setEnterEmployee(Userman.loginUser);
		this.getPanelBean().save();
		
		return this.getPanelBean();
	}
	@Override
	public Material createNewBean() {
		return new Material(getPanelBean().getPic());
		
	}
	

}
