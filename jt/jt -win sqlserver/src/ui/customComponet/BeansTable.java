package ui.customComponet;

import java.util.Collection;
import java.util.List;

import javax.swing.JTable;

public class BeansTable<T>  extends JTable{
	private Collection<T> beans;
	private BeanTableModel<T> model;

	public BeansTable(Collection<T> beans,Class<T> class1) {
		super();
		this.setBeans(beans);
		model=new BeanTableModel<>(beans, class1);
		setModel(model);
		
	}
	public T getSelectBean() {
		return model.getSelectBean(this.convertRowIndexToModel(getSelectedRow()));
	}

	public void addBean(T t) {
		 model.insertRow(t);
	}
	public Collection<T> getBeans() {
		return beans;
	}

	public void setBeans(Collection<T> beans) {
		this.beans = beans;
	}
	@Override
	public BeanTableModel<T> getModel() {
		return model;
	}
	public void setModel(BeanTableModel<T> model) {
		this.model = model;
	}
	
	
	
}
