package ui.panels;



import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanDialog;
import ui.customComponet.BeansPanel;
import ui.customComponet.JTextField;
import ui.menu.MenuAction;
import ui.tables.PicTable;
import validation.Problem;
import validation.Severity;
import validation.builtin.Validators;
import validation.ui.ValidationPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.FpBean;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.Userman;

public class BillPanel extends JPanel {
	private JTextField noteFld;
	private JTextField billidFld;
	private JTextField billgroup;
	private JComboBox<String> customCombox;
	private JComboBox<String> contactManCombox;
	private ValidationPanel vPanel;
	private JXDatePicker billcreateDate;
	private JXDatePicker requestDate;
	private JXDatePicker billgetDate;
	private BillBean bean;
	private JButton addCustomButton;
	private JCheckBox warehousCheckBox;
	private JCheckBox complete;
	private PicTable picTable;
	public BillPanel(BillBean bean) {
		this.bean = bean;
		createContents();
		setFieldName();
		initDataBindings();
		addEnterKeyAction();
		addValitors();


	}

	private void createContents() {
		setLayout(new BorderLayout(0, 0));
		vPanel = new ValidationPanel();
		JPanel panel=new JPanel();
		vPanel.setInnerComponent(panel);
		picTable = new PicTable(bean.getPicBeans());
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,vPanel,picTable);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(500);

		add(splitPane);

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("\u8BA2\u5355\u5BA2\u6237\uFF1A");

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		customCombox = new JComboBox<String>(Custom.getCustomerNames(0));
		//		customCombox = new JComboBox<>();
		customCombox.setName("\u8BA2\u5355\u5BA2\u6237");
		panel_1.add(customCombox);
		customCombox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				contactManCombox.setModel(
						new DefaultComboBoxModel<String>(Custom.getStrings((String) e.getItem(),0)));

			}
		});
		addCustomButton = new JButton("\u65B0\u589E\u5BA2\u6237");
		panel_1.add(addCustomButton);

		addCustomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MenuAction.adminCustom(0);
				customCombox.setModel(new DefaultComboBoxModel<String>(Custom.getCustomerNames(0)));
			}
		});
		JLabel lblNewLabel_1 = new JLabel(
				"\u7ECF\u529E\u4EBA(\u8BA2\u5355\u65B9)\uFF1A");

		contactManCombox = new JComboBox<>();
		contactManCombox.setName("\u7ECF\u529E\u4EBA");


		JLabel lblNewLabel_2 = new JLabel("\u8BA2\u5355\u65E5\u671F\uFF1A");


		billcreateDate = new JXDatePicker();
		billcreateDate.setFormats(new String[] { "yyyy-MM-dd" });


		JLabel billgroupLabel = new JLabel("�����飺");
		billgroup=new JTextField();

		panel.add(lblNewLabel, "2, 2, right, default");
		panel.add(panel_1, "4, 2, 5, 1, fill, fill");
		panel.add(lblNewLabel_1, "2, 4, right, default");
		panel.add(contactManCombox, "4, 4, fill, default");
		panel.add(lblNewLabel_2, "6, 4, right, default");
		panel.add(billcreateDate, "8, 4, fill, fill");
		panel.add(billgroupLabel, "2, 6,right, default");
		panel.add(billgroup, "4, 6, fill, fill");

		JLabel label = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		panel.add(label, "6, 6, right, default");


		billidFld = new JTextField();
		billidFld.setColumns(10);
		panel.add(billidFld, "8, 6, fill, default");



		JLabel label_2 = new JLabel(
				"\u8981\u6C42\u4EA4\u8D27\u65E5\u671F\uFF1A");
		panel.add(label_2, "2, 8");


		requestDate = new JXDatePicker();
		requestDate.setFormats(new String[] { "yyyy-MM-dd" });
		panel.add(requestDate, "4, 8");

		JLabel label_10 = new JLabel(
				"\u8BA2\u5355\u4EA4\u8D27\u65E5\u671F\uFF1A");
		panel.add(label_10, "6, 8");

		billgetDate = new JXDatePicker();
		billgetDate.setFormats(new String[] { "yyyy-MM-dd" });
		panel.add(billgetDate, "8, 8");

		JLabel label_9 = new JLabel("\u5165\u5E93\uFF1A");
		panel.add(label_9, "2, 10, right, default");
		warehousCheckBox = new JCheckBox("\u5DF2\u5165\u5E93");
		panel.add(warehousCheckBox, "4, 10");	

		JLabel label_1 = new JLabel("\u5B8C\u6210\u6807\u5FD7\uFF1A");
		panel.add(label_1, "6, 10, right, default");
		complete=new JCheckBox("����־");
		panel.add(complete, "8, 10");

		JButton btnNewButton = new JButton("\u65B0\u589E\u56FE\u7EB8");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPic();
			}


		});
		panel.add(btnNewButton, "2, 12");

		JButton button = new JButton("\u7BA1\u7406\u53D1\u7968");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editFp();
			}



		});
		panel.add(button, "6, 12");
		JLabel label_3 = new JLabel("��ע");
		panel.add(label_3, "2, 14, right, default");



		noteFld = new JTextField();
		noteFld.setColumns(10);
		panel.add(noteFld, "4, 14, 5, 1, fill, default");
		JLabel lblNewLabel_5 = new JLabel("\u6570\u91CF\uFF1A");

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JLabel lblNewLabel_6 = new JLabel("\u56FE\u7EB8\u6587\u4EF6\uFF1A");
		panel_4.add(lblNewLabel_6);


	}

	private void addValitors() {



		vPanel.getValidationGroup().add(customCombox, Validators.notNull());

	}

	private void addEnterKeyAction() {
		
		customCombox.addKeyListener(JTextField.enterKeyListener);
		contactManCombox.addKeyListener(JTextField.enterKeyListener);
		billcreateDate.getEditor().addKeyListener(JTextField.enterKeyListener);
		requestDate.getEditor().addKeyListener(JTextField.enterKeyListener);
		billgetDate.getEditor().addKeyListener(JTextField.enterKeyListener);
		warehousCheckBox.addKeyListener(JTextField.enterKeyListener);
		addCustomButton.addKeyListener(JTextField.enterKeyListener);

	}

	private void setFieldName() {

		billidFld.setName("������");
		customCombox.setName("�����ͻ�");
		contactManCombox.setName("��ϵ��");
		billcreateDate.setName("��������");
		requestDate.setName("Ҫ�󽻻�����");
		billgetDate.setName("������������");

	}

	public boolean isValide() {
		boolean validate = false;
		Problem p = vPanel.getProblem();
		Problem p2 = vPanel.getValidationGroup().validateAll();

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

		return validate;
	}



	public void saveFile(File selectFile) throws IOException {
		try {
			SerialiObject.save(selectFile, new File("selectfile.dat"));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public static File loadFile() {
		File selectFile = (File) SerialiObject.loadFile(new File(
				"selectfile.dat"));

		if (selectFile == null) {
			selectFile = new File("c:/");
		}
		return selectFile;
	}



	protected void initDataBindings() {

		BeanProperty<JCheckBox, Boolean> checkBoxBeanProperty = BeanProperty.create("selected");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		BeanProperty<JComboBox<String>, String> jComboBoxBeanProperty = BeanProperty.create("selectedItem");
		BeanProperty<JXDatePicker, Date> dateBeanProperty = BeanProperty.create("date");
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("custom"), customCombox,jComboBoxBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("customMan"), contactManCombox,jComboBoxBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("billDate"), billcreateDate, dateBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("requestDate"), requestDate, dateBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("billGetDate"), billgetDate, dateBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("billid"), billidFld, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("note"), noteFld, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("billgroup"), billgroup, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("complete"), complete, checkBoxBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("warehoused"), warehousCheckBox, checkBoxBeanProperty).bind();
	}



	public BillBean getBean() {
		// TODO �Զ����ɵķ������
		return bean;
	}
	private void createPic() {
		saveBill();
		PicBean picBean=new PicBean();
		picBean.setBill(bean);
		BeanDialog<PicBean> dialog=new BeanDialog<PicBean>(new PicPanel(picBean),"����ͼֽ") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
				((PicPanel)getContentPanel()).saveBill();
				picTable.insertBean(getBean());
				return true;
			}
		};
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}
	private void editFp() {
		FpBean fpBean=new FpBean();
		fpBean.setBill(bean);
		BeansPanel<FpBean> panel=new BeansPanel<FpBean>(bean.getFpBeans(),new FpPanel(fpBean),FpBean.class) {

			@Override
			public FpBean saveBean() {
				getPanelBean().setInputUser(Userman.loginUser);
				BeanMao.saveBean(getPanelBean());
				return getPanelBean();
			}

			@Override
			protected FpBean createNewBean() {
				FpBean fpBean=new FpBean();
				fpBean.setBill(bean);
				return fpBean;
			}

		};

		BeanDialog<FpBean> dialog =new BeanDialog<FpBean>(panel,"��Ʊ����") {

			@Override
			public boolean okButtonAction() {
				// TODO �Զ����ɵķ������
				return true;
			}
		};
		dialog.setBounds(100, 100, 500,500);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

	public void saveBill() {
		bean.save();

	}
}
