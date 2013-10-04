package windows.panels;



import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import jcifs.smb.SmbFileOutputStream;

import org.h2.command.dml.Select;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import validation.Problem;
import validation.Severity;
import validation.builtin.Validators;
import validation.ui.ValidationPanel;
import windows.MenuAction;
import windows.MyImageView;
import windows.customComponet.RsTablePane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.AbstractCustom;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.OutCustom;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class BillPanel extends JSplitPane {
	private JTextField noteFld;
	private JTextField billidFld;
	private JTextField billNoFld;
	private JTextField itemNoFld;
	private JTextField priceFld;
	private JTextField outPriceFld;
	private JTextField outBillNoFld;
	private JTextField billgroup;
	private JTextField outNumFld;
	private JTextField picNoFld;
	private JTextField numFld;
	private JTextField imgFld;
	private JComboBox<String> customCombox;
	private JComboBox<String> contactManCombox;
	private JComboBox<String> outCustomCombox;
	private MyImageView imageView;
	private ValidationPanel vPanel;
	private JXDatePicker billcreateDate;
	private JXDatePicker requestDate;
	private JXDatePicker billgetDate;
	private JXDatePicker outgetDate;
	private JXDatePicker outBilledDate;
	private JXDatePicker billedDate;
	private Bill bean;
	private JButton addCustomButton;
	private JButton addOutCustomBt;
	private JCheckBox warehousCheckBox;
	private JFileChooser fileChooser;
	private File imgFile;
	private JTextField cost;
	private JComboBox<String> meterial;
	private JCheckBox taxCheck;

	public BillPanel(Bill bean) {
		this.bean = bean;
		createContents();
		setFieldName();
		initDataBindings();
		addEnterKeyAction();
		outAdminEnable();
		addValitors();
		

	}

	private void outAdminEnable() {
		billidFld.setEnabled(Userman.loginUser.isManager());
		billNoFld.setEnabled(Userman.loginUser.isManager());
		itemNoFld.setEnabled(Userman.loginUser.isManager());
		billgroup.setEnabled(Userman.loginUser.isManager());
		priceFld.setEnabled(Userman.loginUser.isManager());
		picNoFld.setEnabled(Userman.loginUser.isManager());
		numFld.setEnabled(Userman.loginUser.isManager());
		customCombox.setEnabled(Userman.loginUser.isManager());
		contactManCombox.setEnabled(Userman.loginUser.isManager());
		billcreateDate.setEnabled(Userman.loginUser.isManager());
		requestDate.setEnabled(Userman.loginUser.isManager());
		billedDate.setEnabled(Userman.loginUser.isManager());
		outPriceFld.setEnabled(Userman.loginUser.isManager());
		outBillNoFld.setEnabled(Userman.loginUser.isManager());
		addCustomButton.setEnabled(Userman.loginUser.isManager());
		addOutCustomBt.setEnabled(Userman.loginUser.isManager());
		taxCheck.setEnabled(Userman.loginUser.isManager());

	}

	private void outEnable(boolean b) {
		outBilledDate.setEnabled(b && Userman.loginUser.isManager()
				&& outBillNoFld.getText() != null
				&& !outBillNoFld.getText().equals(""));
		outBillNoFld.setEnabled(b && Userman.loginUser.isManager());
		outgetDate.setEnabled(b);
		outNumFld.setEnabled(b);
		outPriceFld.setEnabled(b && Userman.loginUser.isManager());

		if (!b) {
			outBilledDate.setDate(null);
			outBillNoFld.setText(null);
			outgetDate.setDate(null);
			outNumFld.setText(null);
			outPriceFld.setText(null);
		}

	}

	private void addValitors() {
		outBilledDate.setEnabled(Userman.loginUser.isManager()
				&& outBillNoFld.getText() != null
				&& !outBillNoFld.getText().equals(""));
		billedDate.setEnabled(Userman.loginUser.isManager()
				&& billNoFld.getText() != null
				&& !billNoFld.getText().equals(""));
		outEnable(outCustomCombox.getSelectedItem() != null);
		outCustomCombox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				outEnable(outCustomCombox.getSelectedItem() != null);
			}

		});
		billNoFld.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (billNoFld.getText() != null
						&& !billNoFld.getText().equals("")) {
					billedDate.setEnabled(true);
				} else {
					billedDate.setDate(null);
					billedDate.setEnabled(false);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (billNoFld.getText() != null
						&& !billNoFld.getText().equals("")) {
					billedDate.setEnabled(true);
				} else {
					billedDate.setDate(null);
					billedDate.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (billNoFld.getText() != null
						&& !billNoFld.getText().equals("")) {
					billedDate.setEnabled(true);
				} else {
					billedDate.setDate(null);
					billedDate.setEnabled(false);
				}

			}
		});
		outBillNoFld.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (outBillNoFld.getText() != null
						&& !outBillNoFld.getText().equals("")) {
					outBilledDate.setEnabled(true);
				} else {
					outBilledDate.setDate(null);
					outBilledDate.setEnabled(false);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (outBillNoFld.getText() != null
						&& !outBillNoFld.getText().equals("")) {
					outBilledDate.setEnabled(true);
				} else {
					outBilledDate.setDate(null);
					outBilledDate.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (outBillNoFld.getText() != null
						&& !outBillNoFld.getText().equals("")) {
					outBilledDate.setEnabled(true);
				} else {
					outBilledDate.setDate(null);
					outBilledDate.setEnabled(false);
				}

			}
		});
		vPanel.getValidationGroup().add(customCombox, Validators.notNull());

		// vPanel.getValidationGroup().add(billidFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(itemNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(picNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(contactManCombox,Validators.REQUIRE_NON_EMPTY_STRING);
		//
		// vPanel.getValidationGroup().add(outBillNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(outCustomCombox,Validators.REQUIRE_NON_EMPTY_STRING);
		//
		//
		//
		// vPanel.getValidationGroup().add(priceFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(outPriceFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(outNumFld,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(numFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//
		// vPanel.getValidationGroup().add(billcreateDate,Validators.notNull());
		// vPanel.getValidationGroup().add(requestDate,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(billgetDate,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(outgetDate,Validators.REQUIRE_NON_EMPTY_STRING);
		// vPanel.getValidationGroup().add(outBilledDate,Validators.REQUIRE_NON_EMPTY_STRING);
		//
		vPanel.getValidationGroup().add(priceFld,
				Validators.REQUIRE_VALID_NUMBER);
		vPanel.getValidationGroup().add(outPriceFld,
				Validators.REQUIRE_VALID_NUMBER);
		vPanel.getValidationGroup().add(outNumFld,
				Validators.REQUIRE_VALID_INTEGER);
		vPanel.getValidationGroup().add(numFld,
				Validators.REQUIRE_VALID_INTEGER);
	}

	private void addEnterKeyAction() {
		KeyListener enterKeyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					((JComponent) e.getSource()).transferFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根

			}
		};
		billidFld.addKeyListener(enterKeyListener);
		billNoFld.addKeyListener(enterKeyListener);
		itemNoFld.addKeyListener(enterKeyListener);
		billgroup.addKeyListener(enterKeyListener);
		priceFld.addKeyListener(enterKeyListener);
		outPriceFld.addKeyListener(enterKeyListener);
		outBillNoFld.addKeyListener(enterKeyListener);
		outNumFld.addKeyListener(enterKeyListener);
		picNoFld.addKeyListener(enterKeyListener);
		numFld.addKeyListener(enterKeyListener);
		customCombox.addKeyListener(enterKeyListener);
		contactManCombox.addKeyListener(enterKeyListener);
		outCustomCombox.addKeyListener(enterKeyListener);
		billcreateDate.getEditor().addKeyListener(enterKeyListener);
		requestDate.getEditor().addKeyListener(enterKeyListener);
		billgetDate.getEditor().addKeyListener(enterKeyListener);
		outgetDate.getEditor().addKeyListener(enterKeyListener);
		billedDate.getEditor().addKeyListener(enterKeyListener);
		outBilledDate.getEditor().addKeyListener(enterKeyListener);
		warehousCheckBox.addKeyListener(enterKeyListener);
		addCustomButton.addKeyListener(enterKeyListener);
		addOutCustomBt.addKeyListener(enterKeyListener);

	}

	private void setFieldName() {

		billidFld.setName("订单号");
		billNoFld.setName("发票号码");
		itemNoFld.setName("项目号");
		billgetDate.setName("订单组");
		priceFld.setName("订单价格");
		outPriceFld.setName("外协价格");
		outBillNoFld.setName("外协发票号");
		outNumFld.setName("外协数量");
		picNoFld.setName("图号");
		numFld.setName("数量");
		customCombox.setName("订单客户");
		contactManCombox.setName("联系人");
		outCustomCombox.setName("外协客户");
		billcreateDate.setName("订单日期");
		requestDate.setName("要求交货日期");
		billgetDate.setName("订单交货日期");
		outgetDate.setName("外协交货日期");
		outBilledDate.setName("外协开票日期");

	}

	public boolean isValide() {
		boolean validate = false;
		Problem p = vPanel.getProblem();
		Problem p2 = vPanel.getValidationGroup().validateAll();

		validate = (p == null ? true : p.severity() != Severity.FATAL);
		if (!validate)
			JOptionPane.showMessageDialog(null, p.getMessage(), "错误",
					JOptionPane.ERROR_MESSAGE);
		if (validate) {
			validate = (p2 == null ? true : p2.severity() != Severity.FATAL);

			if (!validate)
				JOptionPane.showMessageDialog(null, p2.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
		}

		return validate;
	}

	private void createContents() {
		setDividerSize(10);
		setOneTouchExpandable(true);

		JPanel panel = new JPanel();
		vPanel = new ValidationPanel();
		vPanel.setInnerComponent(panel);
		setLeftComponent(vPanel);

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, 
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("\u8BA2\u5355\u5BA2\u6237\uFF1A");

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		customCombox = new JComboBox<>(Custom.LoadNames());
		customCombox.setName("\u8BA2\u5355\u5BA2\u6237");
		panel_1.add(customCombox);
		customCombox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				contactManCombox.setModel(new DefaultComboBoxModel<>(Custom
						.LoadContacts((String) e.getItem())));

			}
		});
		addCustomButton = new JButton("\u65B0\u589E\u5BA2\u6237");
		panel_1.add(addCustomButton);

		addCustomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				Vector<AbstractCustom> customs =new Custom().LoadAll();
				if(customs.size()==0) customs.add(new Custom());
				MenuAction.adminCustom(customs);

			}
		});
		JLabel lblNewLabel_1 = new JLabel(
				"\u7ECF\u529E\u4EBA(\u8BA2\u5355\u65B9)\uFF1A");

		contactManCombox = new JComboBox<>(
				Custom.LoadContacts((String) customCombox.getSelectedItem()));
		contactManCombox.setName("\u7ECF\u529E\u4EBA");


		JLabel lblNewLabel_2 = new JLabel("\u8BA2\u5355\u65E5\u671F\uFF1A");


		billcreateDate = new JXDatePicker();
		billcreateDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label = new JLabel("\u8BA2\u5355\u53F7\uFF1A");


		billidFld = new JTextField();
		billidFld.setColumns(10);

		JLabel label_1 = new JLabel("\u56FE\u53F7\uFF1A");

		picNoFld = new JTextField();
		picNoFld.setColumns(10);


		JLabel lblNewLabel_4 = new JLabel("\u9879\u76EE\u53F7\uFF1A");


		itemNoFld = new JTextField();
		itemNoFld.setColumns(10);

		numFld = new JTextField();
		numFld.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\u8BA2\u5355\u4EF7\u683C\uFF1A");

		priceFld = new JTextField();
		priceFld.setColumns(10);



		JLabel label_2 = new JLabel(
				"\u8981\u6C42\u4EA4\u8D27\u65E5\u671F\uFF1A");
		JLabel lblNewLabel_5 = new JLabel("\u6570\u91CF\uFF1A");


		requestDate = new JXDatePicker();
		requestDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label_10 = new JLabel(
				"\u8BA2\u5355\u4EA4\u8D27\u65E5\u671F\uFF1A");

		billgetDate = new JXDatePicker();
		billgetDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label_9 = new JLabel("\u5165\u5E93\uFF1A");
		warehousCheckBox = new JCheckBox("\u5DF2\u5165\u5E93");


		JLabel label_101 = new JLabel("开票日期：");

		billedDate = new JXDatePicker();
		billedDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label_91 = new JLabel("发票号码：");

		billNoFld = new JTextField();

		JPanel panel_3 = new JPanel();
		JLabel label_3 = new JLabel("\u5916\u534F\u5BA2\u6237\uFF1A");
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		outCustomCombox = new JComboBox<>(OutCustom.LoadNames());
		panel_3.add(outCustomCombox);

		addOutCustomBt = new JButton("\u65B0\u589E\u5BA2\u6237");
		addOutCustomBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Vector<AbstractCustom> customs =new OutCustom().LoadAll();
				if(customs.size()==0) customs.add(new Custom());
				MenuAction.adminCustom(customs);

			}
		});
		panel_3.add(addOutCustomBt);

		JLabel label_6 = new JLabel("\u5916\u534F\u4EF7\u683C\uFF1A");

		outPriceFld = new JTextField();
		outPriceFld.setColumns(10);



		JLabel label_7 = new JLabel("\u5916\u534F\u6570\u91CF\uFF1A");
		outNumFld = new JTextField();
		outNumFld.setColumns(10);

		JLabel label_4 = new JLabel(
				"\u5916\u534F\u4EA4\u8D27\u65E5\u671F\uFF1A");
		outgetDate = new JXDatePicker();
		outgetDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label_5 = new JLabel("\u5916\u534F\u53D1\u7968\u53F7\uFF1A");

		outBillNoFld = new JTextField();
		outBillNoFld.setColumns(10);

		JLabel label_8 = new JLabel(
				"\u5916\u534F\u5F00\u7968\u65E5\u671F\uFF1A");

		outBilledDate = new JXDatePicker();
		outBilledDate.setFormats(new String[] { "yyyy-MM-dd" });

		JLabel label_11 = new JLabel("\u5907\u6CE8\uFF1A");

		noteFld = new JTextField();
		noteFld.setColumns(10);

		JPanel panel_2 = new JPanel();
		setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));

		JLabel lblNewLabel_6 = new JLabel("\u56FE\u7EB8\u6587\u4EF6\uFF1A");
		panel_4.add(lblNewLabel_6);

		imgFld = new JTextField();
		imgFld.setText("");
		imgFld.setEditable(false);
		panel_4.add(imgFld);
		imgFld.setColumns(10);

		JButton btnNewButton = new JButton("\u6D4F\u89C8...");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				getFile();

			}

		});
		panel_4.add(btnNewButton);
		setDividerLocation(500);
		imageView = new MyImageView();

		if(bean.getImageUrl()!=null){
			imageView.showFile(bean.getImageUrl());
		}
		panel_2.add(imageView, BorderLayout.CENTER);

		JLabel billgroupLabel = new JLabel("订单组：");
		billgroup=new JTextField();
		cost=new JTextField();
		
		taxCheck=new JCheckBox("含税折算");
		taxCheck.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(taxCheck.isSelected())
					priceFld.setText((String.valueOf(Math.round(Float.valueOf(priceFld.getText())*83)/100.0f)));
				else 
					priceFld.setText((String.valueOf(Math.round(Float.valueOf(priceFld.getText())/.0083)/100.0f)));
					
			}
		});
		meterial=new JComboBox<>(new String[] {"包工包料","来料加工"});
		try(Statement st=SessionData.getConnection().createStatement()){
			ResultSet rs = st.executeQuery("Select distinct meterial from bill");
			while(rs.next()) {
				meterial.addItem(rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		meterial.setEditable(true);
		panel.add(lblNewLabel, "2, 2, right, default");
		panel.add(panel_1, "4, 2, 5, 1, fill, fill");
		panel.add(lblNewLabel_1, "2, 4, right, default");
		panel.add(contactManCombox, "4, 4, fill, default");
		panel.add(lblNewLabel_2, "6, 4, right, default");
		panel.add(billcreateDate, "8, 4, fill, fill");
		panel.add(billgroupLabel, "2, 6,right, default");
		panel.add(billgroup, "4, 6, fill, fill");
		panel.add(new JLabel("单件生产费用"), "6, 6,right, default");
		panel.add(cost, "8, 6, fill, fill");
		panel.add(label, "2, 8, right, default");
		panel.add(billidFld, "4, 8, fill, default");
		panel.add(label_1, "6, 8, right, default");
		panel.add(picNoFld, "8, 8, fill, default");
		panel.add(lblNewLabel_4, "2, 10, right, default");
		panel.add(itemNoFld, "4, 10, fill, default");
		panel.add(lblNewLabel_5, "6, 10, right, default");
		panel.add(numFld, "8, 10, fill, default");
		panel.add(lblNewLabel_3, "2, 12, right, default");
		panel.add(priceFld, "4, 12, fill, default");
		panel.add(new Label("含税折算"), "6, 12, right, default");
		panel.add(taxCheck, "8, 12, fill, default");		
		
		panel.add(label_2, "2, 14");
		panel.add(requestDate, "4, 14");
		panel.add(label_10, "6, 14");
		panel.add(billgetDate, "8, 14");
		panel.add(label_9, "2, 16, right, default");
		panel.add(warehousCheckBox, "4, 16");	
		panel.add(label_101, "6, 18");
		panel.add(billedDate, "8, 18");
		panel.add(label_91, "2, 18, right, default");
		panel.add(billNoFld, "4, 18");
		panel.add(label_3, "2, 20");
		panel.add(panel_3, "4, 20, 5, 1, fill, fill");
		panel.add(label_6, "2, 22, right, default");
		panel.add(outPriceFld, "4, 22, fill, default");
		panel.add(label_7, "6, 22");
		panel.add(outNumFld, "8, 22, fill, default");
		panel.add(label_4, "2, 24");
		panel.add(outgetDate, "4, 24");
		panel.add(label_5, "2, 26, right, default");
		panel.add(outBillNoFld, "4, 26, fill, default");
		panel.add(label_8, "6, 26");
		panel.add(outBilledDate, "8, 26");
		panel.add(new JLabel("材料类型"), "2, 28");
		panel.add(meterial, "4, 28,  fill, default");
		panel.add(label_11, "2, 30, right, default");
		panel.add(noteFld, "4, 30, 5, 1, fill, default");
		
//		billcreateDate.addPropertyChangeListener("date",new PropertyChangeListener() {
//			
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				try{
//				billgroup.setText( new SimpleDateFormat("yyyyMMdd").format(bean.getBillDate())+Custom.Load(bean.getCustom()).getSysId()+"001");
//				}catch(Exception e){
//					
//				}
//				
//			}
//		});
		
	}

	public void saveFile(File selectFile) throws IOException {
		try {
			SerialiObject.save(selectFile, new File("selectfile.dat"));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
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

	private void getFile() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser(loadFile());
			fileChooser.setDialogTitle("选择图片名称");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					return "图片或PDF文件";
				}

				@Override
				public boolean accept(File f) {
					// TODO 自动生成的方法存根
					return (f.getName().toLowerCase().endsWith(".gif")
							|| f.getName().toLowerCase().endsWith(".jpg")
							|| f.getName().toLowerCase().endsWith(".jpeg")
							|| f.getName().toLowerCase().endsWith(".png")
							|| f.getName().toLowerCase().endsWith(".xls")
							|| f.getName().toLowerCase().endsWith(".pdf")
							|| f.getName().toLowerCase().endsWith(".bmp") || f
							.isDirectory());
				}
			});
			fileChooser.setApproveButtonText("确定");
		}
		if (fileChooser.showOpenDialog(BillPanel.this) == JFileChooser.APPROVE_OPTION) {
			try {
				saveFile(fileChooser.getSelectedFile());
				imgFile = fileChooser.getSelectedFile();
				imgFld.setText(imgFile.getCanonicalPath());
				imageView.showFile(imgFile);
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}

	}

	protected void initDataBindings() {

		BeanProperty<Bill, Boolean> billItemBeanProperty_0 = BeanProperty
				.create("warehoused");
		BeanProperty<JCheckBox, Boolean> checkBoxBeanProperty = BeanProperty
				.create("selected");
		AutoBinding<Bill, Boolean, JCheckBox, Boolean> autoBinding0 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_0, warehousCheckBox,
						checkBoxBeanProperty);
		autoBinding0.bind();

		BeanProperty<Bill, String> billItemBeanProperty = BeanProperty
				.create("note");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty
				.create("text");
		AutoBinding<Bill, String, JTextField, String> autoBinding = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty, noteFld, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_1 = BeanProperty
				.create("billNo");
		AutoBinding<Bill, String, JTextField, String> autoBinding_1 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_1, billNoFld,
						jTextFieldBeanProperty);
		autoBinding_1.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_2 = BeanProperty
				.create("item");
		AutoBinding<Bill, String, JTextField, String> autoBinding_2 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_2, itemNoFld,
						jTextFieldBeanProperty);
		autoBinding_2.bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				BeanProperty.create("billgroup"), billgroup,
				jTextFieldBeanProperty).bind();
		//
		BeanProperty<Bill, Float> billItemBeanProperty_3 = BeanProperty
				.create("reportPrice");
		AutoBinding<Bill, Float, JTextField, String> autoBinding_3 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_3, priceFld,
						jTextFieldBeanProperty);
		autoBinding_3.bind();
		//
		BeanProperty<Bill, Float> billItemBeanProperty_4 = BeanProperty
				.create("outPrice");
		AutoBinding<Bill, Float, JTextField, String> autoBinding_4 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_4, outPriceFld,
						jTextFieldBeanProperty);
		autoBinding_4.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_5 = BeanProperty
				.create("outBillNo");
		AutoBinding<Bill, String, JTextField, String> autoBinding_5 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_5, outBillNoFld,
						jTextFieldBeanProperty);
		autoBinding_5.bind();
		//
		BeanProperty<Bill, Long> billItemBeanProperty_6 = BeanProperty
				.create("outNum");
		AutoBinding<Bill, Long, JTextField, String> autoBinding_6 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_6, outNumFld,
						jTextFieldBeanProperty);
		autoBinding_6.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_7 = BeanProperty
				.create("picid");
		AutoBinding<Bill, String, JTextField, String> autoBinding_7 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_7, picNoFld,
						jTextFieldBeanProperty);
		autoBinding_7.bind();
		//
		BeanProperty<Bill, Long> billItemBeanProperty_8 = BeanProperty
				.create("num");
		AutoBinding<Bill, Long, JTextField, String> autoBinding_8 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_8, numFld, jTextFieldBeanProperty);
		autoBinding_8.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_9 = BeanProperty
				.create("custom");
		BeanProperty<JComboBox<String>, String> jComboBoxBeanProperty = BeanProperty
				.create("selectedItem");
		AutoBinding<Bill, String, JComboBox<String>, String> autoBinding_9 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_9, customCombox,
						jComboBoxBeanProperty);
		autoBinding_9.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_10 = BeanProperty
				.create("customMan");
		AutoBinding<Bill, String, JComboBox<String>, String> autoBinding_10 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_10, contactManCombox,
						jComboBoxBeanProperty);
		autoBinding_10.bind();
		//
		BeanProperty<Bill, String> billItemBeanProperty_11 = BeanProperty
				.create("outCustom");
		AutoBinding<Bill, String, JComboBox<String>, String> autoBinding_11 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_11, outCustomCombox,
						jComboBoxBeanProperty);
		autoBinding_11.bind();

		//
		BeanProperty<Bill, String> billItemBeanProperty_12 = BeanProperty
				.create("billid");
		AutoBinding<Bill, String, JTextField, String> autoBinding_12 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_12, billidFld,
						jTextFieldBeanProperty);
		autoBinding_12.bind();
		//
		BeanProperty<Bill, Date> billItemBeanProperty_13 = BeanProperty
				.create("billedDate");
		BeanProperty<JXDatePicker, Date> dateBeanProperty = BeanProperty
				.create("date");

		BeanProperty<Bill, Date> billItemBeanProperty_14 = BeanProperty
				.create("billDate");
		BeanProperty<Bill, Date> billItemBeanProperty_15 = BeanProperty
				.create("outGetDate");
		BeanProperty<Bill, Date> billItemBeanProperty_16 = BeanProperty
				.create("outBillDate");
		BeanProperty<Bill, Date> billItemBeanProperty_17 = BeanProperty
				.create("itemCompleteDate");
		BeanProperty<Bill, Date> billItemBeanProperty_18 = BeanProperty
				.create("requestDate");

		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_13 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_13, billedDate, dateBeanProperty);
		autoBinding_13.bind();
		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_14 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_14, billcreateDate,
						dateBeanProperty);
		autoBinding_14.bind();
		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_15 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_15, outgetDate, dateBeanProperty);
		autoBinding_15.bind();
		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_16 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_16, outBilledDate,
						dateBeanProperty);
		autoBinding_16.bind();
		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_17 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_17, billgetDate, dateBeanProperty);
		autoBinding_17.bind();
		AutoBinding<Bill, Date, JXDatePicker, Date> autoBinding_18 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
						billItemBeanProperty_18, requestDate, dateBeanProperty);
		autoBinding_18.bind();
		 Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				 BeanProperty.create("planCost"), cost, jTextFieldBeanProperty).bind();
		 Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,
				 BeanProperty.create("meterial"), (JTextField)meterial.getEditor().getEditorComponent(), jTextFieldBeanProperty).bind();
	}

	public void saveBill() {
		if (imgFile != null) {
			String[] sub = imgFile.getName().split("\\.");
			String pifName = sub[sub.length - 1];
			String newName = new Date().getTime()
					+ "_"
					+ ((Integer) imgFile.getName().hashCode()).toString()
					.replaceAll("-", "0") + "." + pifName;
			try {
				smbCopy(imgFile, newName);
				bean.setImageUrl("D:\\webApp\\webappsInner\\ROOT\\pics\\"
						+ newName);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
		bean.save();
	}

	public static void smbCopy(File localFile, String smbFile)
			throws IOException {

		if (localFile.exists()) {
			int byteread = 0;
			InputStream inStream = new FileInputStream(localFile);
			SmbFileOutputStream fs = new SmbFileOutputStream(
					"smb://192.168.1.103/pics/" + smbFile);
			byte[] buffer = new byte[2048];
			while ((byteread = inStream.read(buffer)) != -1) {

				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
		}
	}

	public Bill getBean() {
		// TODO 自动生成的方法存根
		return bean;
	}



}
