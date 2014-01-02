package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
	private Vector<T> beans;

	/**
	 * @return the contentPanel
	 */
	public BeanPanel<T> getContentPanel() {
		return this.contentPanel;
	}

	public BeanDialog(BeanTablePane<T> panel, String title) {
		this.contentPanel3 = panel;
		this.bean = null;
		setTitle(title);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/windows/logo.PNG")));
		contentPanel3.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel3, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (getBeans() == null || getBeans().size() == 0) {
							JOptionPane.showMessageDialog(BeanDialog.this,
									"û������κ����ݣ��޷��ύ��", "����",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						if (okButtonAction())
							BeanDialog.this.dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("ȡ��");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BeanDialog.this.bean = null;
						BeanDialog.this.dispose();
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
		this.bean = (T) contentPanel.getBean();
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/windows/logo.PNG")));
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
					public void actionPerformed(ActionEvent e) {
						if (!BeanDialog.this.contentPanel.isValide()) {
							return;
						}
						if (okButtonAction())
							BeanDialog.this.dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("ȡ��");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BeanDialog.this.bean = null;
						BeanDialog.this.dispose();
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
		this.beans = panel.getBean();
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BeanDialog.class.getResource("/windows/logo.PNG")));
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
					public void actionPerformed(ActionEvent e) {
						if (getBeans() == null || getBeans().size() == 0) {
							JOptionPane.showMessageDialog(BeanDialog.this,
									"û������κ����ݣ��޷��ύ��", "����",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						if (okButtonAction())
							BeanDialog.this.dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("ȡ��");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BeanDialog.this.beans = null;
						BeanDialog.this.dispose();
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

	public T getBean() {
		return bean;
	}

	public Vector<T> getBeans() {
		return beans;
	}
}
