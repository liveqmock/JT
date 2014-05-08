package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class BeanDialog<T> extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BeanPanel<T> contentPanel;
	private BeansPanel<T> contentPanel2;
	private BeanTablePane<T> contentPanel3;
	private T bean;
	private Collection<T> beans;

	/**
	 * @return the contentPanel
	 */
	public BeanPanel<T> getContentPanel() {
		return this.contentPanel;
	}

	public BeanDialog(BeanTablePane<T> panel, String title) {
		this.contentPanel3 = panel;
		this.beans = panel.getBeans();
		setTitle(title);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/ui/logo.PNG")));
		contentPanel3.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel3, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (getBeans() == null || getBeans().size() == 0) {
							JOptionPane.showMessageDialog(BeanDialog.this,
									"没有添加任何数据，无法提交！", "错误",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						if (okButtonAction()){
							 ok();
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		pack();
		// Dimension aaa = new Dimension(getWidth(), getHeight());
		// if (aaa.width < 500) aaa.width = 500;
		// if (aaa.height < 500) aaa.height = 500;
		// setBounds(0, 0, aaa.width, aaa.height);
		setLocationRelativeTo(null);
		// setResizable(false);
	}

	public BeanDialog(BeanPanel<T> panel, String title) {
		this.contentPanel = panel;
		this.bean = contentPanel.getBean();
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/ui/logo.PNG")));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel.getvPanel(), BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!BeanDialog.this.contentPanel.isValide()) {
							return;
						}
						
						if (okButtonAction()){
							 ok();
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
//				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancel();
					}

					
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		pack();
		setLocationRelativeTo(null);
		// setResizable(false);
	}

	public BeanDialog(BeansPanel<T> panel, String title) {
		this.contentPanel2 = panel;
		this.beans = panel.getBeans();
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/ui/logo.PNG")));
		getContentPane().setLayout(new BorderLayout());
		contentPanel2.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel2, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (getBeans() == null || getBeans().size() == 0) {
							JOptionPane.showMessageDialog(BeanDialog.this,
									"没有添加任何数据，无法提交！", "错误",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						if (okButtonAction()){
							ok();
						}

					}

					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancel();
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		pack();
		// Dimension aaa = new Dimension(getWidth(), getHeight());
		// if (aaa.width < 500) aaa.width = 500;
		// if (aaa.height < 500) aaa.height = 500;
		// setBounds(0, 0, aaa.width, aaa.height);
		setLocationRelativeTo(null);
		// setResizable(false);
	}

	public abstract boolean okButtonAction();
	public void cancel() {
		BeanDialog.this.bean = null;
		BeanDialog.this.dispose();
		
	}
	private void ok() {
		BeanDialog.this.dispose();
		
	}
	public T getBean() {
		return bean;
	}

	public Collection<T> getBeans() {
		return beans;
	}
}
