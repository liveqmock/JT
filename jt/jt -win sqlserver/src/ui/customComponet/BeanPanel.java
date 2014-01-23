package ui.customComponet;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;

import validation.Problem;
import validation.Severity;
import validation.ui.ValidationGroup;
import validation.ui.ValidationPanel;

public abstract class BeanPanel<T> extends JPanel {
	protected ValidationGroup validationGroup;
	protected ValidationPanel vPanel;
	/**
	 * 
	 */
	private T origBean;
	protected BindingGroup bindingGroup;
	protected T editBean;
	/**
	 * @return the origBean
	 */
	public T getBean() {
		 
		 return origBean;
	}

	public BeanPanel(T origBean) {
		super();
		try{
			vPanel = new ValidationPanel();
			vPanel.setInnerComponent(this);
			setValidationGroup(vPanel.getValidationGroup());
			bindingGroup=new BindingGroup();
			createContents();
			setBean(origBean);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public BeanPanel(T origBean,int beans) {
		super();
		this.origBean = origBean;
		vPanel = new ValidationPanel();
		vPanel.setInnerComponent(this);
		setValidationGroup(vPanel.getValidationGroup());
		bindingGroup=new BindingGroup();

	}
	public boolean isValide() {
		boolean validate = false;
		Problem p = vPanel.getProblem();
		Problem p2 = validationGroup.validateAll();

		validate = (p == null ? true : p.severity() != Severity.FATAL);
		if (!validate)
			JOptionPane.showMessageDialog(null, p.getMessage(), "����",
					JOptionPane.ERROR_MESSAGE);
		if (validate) {
			validate = (p2 == null ? true : p2.severity() != Severity.FATAL);

			if (!validate)
				JOptionPane.showMessageDialog(null, p2.getMessage(), "����",
						JOptionPane.ERROR_MESSAGE);
		}
		if(validate)
		try {
			BeanUtils.copyProperties(origBean, editBean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return validate;
	}
	protected abstract void  dataBinding();
	/**
	 * @param origBean
	 *            the origBean to set
	 */
	public void setBean(T origBean) {
		try{
			bindingGroup.unbind();
			for(Binding<?, ?, ?, ?> binding: bindingGroup.getBindings())
				bindingGroup.removeBinding(binding);
			this.origBean = origBean;
			if(origBean!=null)
				this.editBean=(T) BeanUtils.cloneBean(origBean);
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
