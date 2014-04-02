package ui.customComponet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;

import validation.Problem;
import validation.Severity;
import validation.ui.ValidationGroup;
import validation.ui.ValidationPanel;

public abstract class BeanOPanel<T> extends JPanel {
	protected ValidationGroup validationGroup;
	protected ValidationPanel vPanel;
	/**
	 * 
	 */
	protected T bean;
	protected BindingGroup bindingGroup;
	/**
	 * @return the origBean
	 */
	public T getBean() {
		 
		 return bean;
	}

	public BeanOPanel(T bean) {
		super();
		try{
			vPanel = new ValidationPanel();
			vPanel.setInnerComponent(this);
			setValidationGroup(vPanel.getValidationGroup());
			bindingGroup=new BindingGroup();
			createContents();
			setBean(bean);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public boolean isValide() {
		boolean validate = false;
		Problem p = vPanel.getProblem();
		Problem p2 = validationGroup.validateAll();

		validate = (p == null ? true : p.severity() != Severity.FATAL);
		if (!validate)
			JOptionPane.showMessageDialog(null, p.getMessage(), "´íÎó",
					JOptionPane.ERROR_MESSAGE);
		if (validate) {
			validate = (p2 == null ? true : p2.severity() != Severity.FATAL);

			if (!validate)
				JOptionPane.showMessageDialog(null, p2.getMessage(), "´íÎó",
						JOptionPane.ERROR_MESSAGE);
		}
		return validate;
	}
	protected abstract void  dataBinding();
	/**
	 * @param origBean
	 *            the origBean to set
	 */
	public void setBean(T bean) {
		try{
			if(bean==null) return;
			bindingGroup.unbind();
			for(Binding<?, ?, ?, ?> binding: bindingGroup.getBindings())
				bindingGroup.removeBinding(binding);
			this.bean = bean;
			dataBinding();
			bindingGroup.bind();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setValidationGroup(ValidationGroup validationGroup) {
		this.validationGroup = validationGroup;
	}

	public ValidationGroup getValidationGroup() {
		return validationGroup;
	}

	/**
	 * @return the vPanel
	 */
	public ValidationPanel getvPanel() {
		return this.vPanel;
	}

	/**
	 * @param vPanel
	 *            the vPanel to set
	 */
	public void setvPanel(ValidationPanel vPanel) {
		this.vPanel = vPanel;
	}

	
	protected abstract void createContents();

}
