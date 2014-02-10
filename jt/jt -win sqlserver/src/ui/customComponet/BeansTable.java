package ui.customComponet;

import java.util.List;

import javax.swing.JTable;

public class BeansTable<T>  extends JTable{
	private List<T> beans;
	private BeanTableModel<T> model;

	public BeansTable(List<T> beans,Class<T> class1) {
		super();
		this.setBeans(beans);
		model=new BeanTableModel<>(beans, class1);
		setModel(model);
		
	}
	public T getSelectBean() {
		return model.getSelectBean(this.convertRowIndexToModel(getSelectedRow()));
	}
	public List<T> getBeans() {
		return beans;
	}

	public void setBeans(List<T> beans) {
		this.beans = beans;
	}
	public BeanTableModel<T> getModel() {
		return model;
	}
	public void setModel(BeanTableModel<T> model) {
		this.model = model;
	}
	
	
	
}
