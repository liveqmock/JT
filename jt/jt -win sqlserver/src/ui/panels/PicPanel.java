package ui.panels;



import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
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
import javax.swing.filechooser.FileFilter;

import jcifs.smb.SmbFileOutputStream;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.BeanPanel;
import ui.menu.MenuAction;
import validation.Problem;
import validation.Severity;
import validation.builtin.Validators;
import validation.ui.ValidationPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class PicPanel extends BeanPanel<PicBean> {
	

	
	private JTextField noteFld;
	private JTextField itemNoFld;
	private JTextField reportPriceFld;
	private JTextField reportTaxPriceFld;
	private JTextField outPriceFld;
	private JTextField outNumFld;
	private JTextField picNoFld;
	private JTextField numFld;
	private JComboBox<String> outCustomCombox;
	private JXDatePicker outgetDate;
	private JCheckBox warehousCheckBox;
	private JTextField cost;
	private JTextField gjh;
	private JTextField partName;
	private JTextField techCondition;
	private JTextField meterialz;
	private JTextField meterialType;
	private JTextField meterialCode;
	private JComboBox<String> meterial;
	private JXDatePicker itemCompleteDate;
	
	private JTextField imgFld;
	private PicView imageView;
	private ValidationPanel vPanel;
	private JFileChooser fileChooser;
	private File imgFile;
	private JButton addOutCustomBt;
	
	public PicPanel(PicBean bean) {
		super(bean);		
		createContents();
		setFieldName();
		initDataBindings();
		addEnterKeyAction();
		outAdminEnable();
		addValitors();
	}

	@Override
	protected void createContents() {

		setLayout(new BorderLayout(0, 0));
		JSplitPane splitPane=new JSplitPane();
		add(splitPane,BorderLayout.CENTER);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);

		JPanel panel = new JPanel();
		vPanel = new ValidationPanel();
		vPanel.setInnerComponent(panel);
		splitPane.setLeftComponent(vPanel);

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
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));

		JPanel panel_3 = new JPanel();
		JLabel label_3 = new JLabel("\u5916\u534F\u5BA2\u6237\uFF1A");
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		//
		outCustomCombox = new JComboBox<String>(Custom.getCustomerNames(1));
		panel_3.add(outCustomCombox);

		addOutCustomBt = new JButton("\u65B0\u589E\u5BA2\u6237");
		addOutCustomBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MenuAction.adminCustom(1);

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


		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
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
		splitPane.setDividerLocation(500);
		imageView = new PicView();

		if(bean.getImageUrl()!=null){
			imageView.showFile(bean.getImageUrl());
		}
		panel_2.add(imageView, BorderLayout.CENTER);
		meterial=new JComboBox<>();
		try(Statement st=SessionData.getConnection().createStatement()){
			 List<String> list = (List<String>) BeanMao.beanManager.queryNativeList("Select distinct meterial from PicBean");
			for(String s:list) {
				meterial.addItem(s);
			}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}



		JLabel label_1 = new JLabel("\u56FE\u53F7\uFF1A");
		panel.add(label_1, "2, 2, right, default");

		picNoFld = new JTextField();
		picNoFld.setColumns(10);
		panel.add(picNoFld, "4, 2, fill, default");


		JLabel lblNewLabel_4 = new JLabel("\u9879\u76EE\u53F7\uFF1A");
		panel.add(lblNewLabel_4, "6, 2, right, default");


		itemNoFld = new JTextField();
		itemNoFld.setColumns(10);
		panel.add(itemNoFld, "8, 2, fill, default");

		JLabel lblNewLabel_3 = new JLabel("\u8BA2\u5355\u4EF7\u683C\uFF1A");
		panel.add(lblNewLabel_3, "2, 4, right, default");

		reportPriceFld = new JTextField();
		reportPriceFld.setColumns(10);
		panel.add(reportPriceFld, "4, 4, fill, default");
		reportTaxPriceFld = new JTextField();
		
		Label label_2 = new Label("含税价:");
		panel.add(label_2, "6, 4, right, default");
		panel.add(reportTaxPriceFld, "8, 4, fill, default");		
		JLabel lblNewLabel_5 = new JLabel("\u6570\u91CF\uFF1A");
		panel.add(lblNewLabel_5, "2, 6, right, default");

		numFld = new JTextField();
		numFld.setColumns(10);
		panel.add(numFld, "4, 6, fill, default");
		JLabel label = new JLabel("单件生产费用");
		panel.add(label, "6, 6, right, default");

		cost=new JTextField();
		panel.add(cost, "8, 6, fill, fill");




		JLabel label_9 = new JLabel("\u5165\u5E93\uFF1A");
		panel.add(label_9, "2, 8, right, default");
		warehousCheckBox = new JCheckBox("\u5DF2\u5165\u5E93");
		panel.add(warehousCheckBox, "4, 8");

		JLabel label_19 = new JLabel("\u5B8C\u5DE5\u65E5\u671F\uFF1A");
		panel.add(label_19, "6, 8");

		itemCompleteDate = new JXDatePicker();
		itemCompleteDate.setFormats(new String[] {"yyyy-MM-dd"});
		panel.add(itemCompleteDate, "8, 8");
		JLabel label_10 = new JLabel("材料类型");
		panel.add(label_10, "2, 12");
		//		
		meterial=new JComboBox<>(new String[] {"包工包料","来料加工"});
		meterial.setEditable(true);
		panel.add(meterial, "4, 12, fill, default");
		JLabel label_12 = new JLabel("材料编号");
		panel.add(label_12, "6, 12, right, default");
		meterialCode=new JTextField();
		panel.add(meterialCode, "8, 12");
		JLabel label_13 = new JLabel("工件号");
		panel.add(label_13, "2, 14, right, default");
		gjh=new JTextField();
		panel.add(gjh, "4, 14");
		JLabel label_14 = new JLabel("零件名称");
		panel.add(label_14, "6, 14, right, default");
		partName=new JTextField();
		panel.add(partName, "8, 14");
		JLabel label_15 = new JLabel("材质");
		panel.add(label_15, "2, 16, right, default");
		meterialz=new JTextField();
		panel.add(meterialz, "4, 16");
		JLabel label_16 = new JLabel("材料规格");
		panel.add(label_16, "6, 16, right, default");
		meterialType=new JTextField();
		panel.add(meterialType, "8, 16");
		JLabel label_17 = new JLabel("技术条件");
		panel.add(label_17, "2, 18, right, default");
		techCondition=new JTextField();
		panel.add(techCondition, "4, 18");
		panel.add(label_3, "2, 22");
		panel.add(panel_3, "4, 22, 5, 1, fill, fill");
		panel.add(label_6, "2, 24, right, default");
		panel.add(outPriceFld, "4, 24, fill, default");
		panel.add(label_7, "6, 24");
		panel.add(outNumFld, "8, 24, fill, default");
		panel.add(label_4, "2, 26");
		panel.add(outgetDate, "4, 26");

		JLabel label_5 = new JLabel("\u5907\u6CE8\uFF1A");
		panel.add(label_5, "2, 30, right, default");

		noteFld = new JTextField();
		noteFld.setColumns(10);
		panel.add(noteFld, "4, 30, 5, 1, fill, default");



	}

	private void outAdminEnable() {
		itemNoFld.setEnabled(Userman.loginUser.isManager());
		reportPriceFld.setEnabled(Userman.loginUser.isManager());
		reportTaxPriceFld.setEnabled(Userman.loginUser.isManager());
		picNoFld.setEnabled(Userman.loginUser.isManager());
		numFld.setEnabled(Userman.loginUser.isManager());
		outPriceFld.setEnabled(Userman.loginUser.isManager());
		itemCompleteDate.setEnabled(Userman.loginUser.isManager());
	}

	private void outEnable(boolean b) {
		outgetDate.setEnabled(b);
		outNumFld.setEnabled(b);
		outPriceFld.setEnabled(b && Userman.loginUser.isManager());

		if (!b) {
			outgetDate.setDate(null);
			outNumFld.setText(null);
			outPriceFld.setText(null);
		}

	}

	private void addValitors() {

		outEnable(outCustomCombox.getSelectedItem() != null);
		outCustomCombox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				outEnable(outCustomCombox.getSelectedItem() != null);
			}

		});


		picNoFld.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				PicBean pic = bean.getSamePicBill();
				if(pic!=null)
					imageView.showFile(pic.getImageUrl());

			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		//		 vPanel.getValidationGroup().add(billidFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(itemNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(picNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(contactManCombox,Validators.REQUIRE_NON_EMPTY_STRING);
		//		
		//		 vPanel.getValidationGroup().add(outBillNoFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(outCustomCombox,Validators.REQUIRE_NON_EMPTY_STRING);
		//		
		//		
		//		
		//		 vPanel.getValidationGroup().add(priceFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(outPriceFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(outNumFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(numFld,Validators.REQUIRE_NON_EMPTY_STRING);
		//		
		//		 vPanel.getValidationGroup().add(billcreateDate,Validators.notNull());
		//		 vPanel.getValidationGroup().add(requestDate,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(billgetDate,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(outgetDate,Validators.REQUIRE_NON_EMPTY_STRING);
		//		 vPanel.getValidationGroup().add(outBilledDate,Validators.REQUIRE_NON_EMPTY_STRING);
		//
		vPanel.getValidationGroup().add(reportPriceFld,
				Validators.REQUIRE_VALID_NUMBER);
		vPanel.getValidationGroup().add(reportTaxPriceFld,
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
		itemNoFld.addKeyListener(enterKeyListener);
		reportPriceFld.addKeyListener(enterKeyListener);
		outPriceFld.addKeyListener(enterKeyListener);
		outNumFld.addKeyListener(enterKeyListener);
		picNoFld.addKeyListener(enterKeyListener);
		numFld.addKeyListener(enterKeyListener);
		outCustomCombox.addKeyListener(enterKeyListener);
		outgetDate.getEditor().addKeyListener(enterKeyListener);
		itemCompleteDate.getEditor().addKeyListener(enterKeyListener);
		warehousCheckBox.addKeyListener(enterKeyListener);

	}

	private void setFieldName() {

		itemNoFld.setName("项目号");
		reportPriceFld.setName("订单价格");
		outPriceFld.setName("外协价格");
		outNumFld.setName("外协数量");
		picNoFld.setName("图号");
		numFld.setName("数量");
		outCustomCombox.setName("外协客户");
		outgetDate.setName("外协交货日期");
		itemCompleteDate.setName("项目完成日期");

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
							|| f.getName().toLowerCase().endsWith(".pdf")
							|| f.getName().toLowerCase().endsWith(".bmp") || f
							.isDirectory());
				}
			});
			fileChooser.setApproveButtonText("确定");
		}
		if (fileChooser.showOpenDialog(PicPanel.this) == JFileChooser.APPROVE_OPTION) {
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
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty
				.create("text");

		BeanProperty<JCheckBox, Boolean> checkBoxBeanProperty = BeanProperty
				.create("selected");
		BeanProperty<JComboBox<String>, String> jComboBoxBeanProperty = BeanProperty
				.create("selectedItem");
		BeanProperty<JXDatePicker, Date> dateBeanProperty = BeanProperty
				.create("date");
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("warehoused"), warehousCheckBox,checkBoxBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("note"), noteFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("item"), itemNoFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("reportPrice"), reportPriceFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("reportTaxPrice"), reportTaxPriceFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("outPrice"), outPriceFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("outNum"), outNumFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("picid"), picNoFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("num"), numFld,jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("outCustom"), outCustomCombox,jComboBoxBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("itemCompleteDate"), itemCompleteDate, dateBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("outGetDate"), outgetDate, dateBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("planCost"), cost, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("meterial"), (JTextField)meterial.getEditor().getEditorComponent(), jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("meterialz"), meterialz, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("meterialType"), meterialType, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("techCondition"), techCondition, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("partName"), partName, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("gjh"), gjh, jTextFieldBeanProperty).bind();
		Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean,BeanProperty.create("meterialCode"), meterialCode, jTextFieldBeanProperty).bind();
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
		BeanMao.saveBean(bean);
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

	@Override
	protected void dataBinding() {
		// TODO 自动生成的方法存根
		
	}

}
