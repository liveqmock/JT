package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXDatePicker;

import com.mao.jf.beans.Custom;

public abstract class PicShowPnl extends JPanel{

	private JXComboBox cstlist;
	private JTextField textField;
	private JComboBox<String> dateBox;
	private JComboBox<String> txtBox;
	private JXDatePicker sDatePicker;
	private JXDatePicker eDatePicker;
	private JButton searchButton;


	private SearchPicDlg searchDlg;
	private JDialog dialog;

	public abstract void searchAction(String search);


	public PicShowPnl() {

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		cstlist = new JXComboBox(Custom.getCustomerNames(0));

		

		JPanel panel = new JPanel(new HorizontalLayout(1));

		JLabel label = new JLabel(
				"<html><B><font color=red>快捷搜索:</font></B></html>");
		label.setMaximumSize(new Dimension(50, 40));
		panel.add(Box.createHorizontalGlue());
		panel.add(label);

		txtBox = new JComboBox<String>();
		txtBox.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"客户名称", "订单号",
				"项目号", "图号","订单组", "备注"}));
		panel.add(txtBox);
		panel.add(new JLabel(":"));

		textField = new JTextField(20);
		textField.setToolTipText("输入查询的内容");
		panel.add(textField);
		dateBox = new JComboBox<String>();
		dateBox.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"订单日期",
				"要求交货日期", "外协交货日期",
		"订单交货日期" }));
		panel.add(dateBox);

		panel.add(new JLabel(":"));
		sDatePicker = new JXDatePicker();
		eDatePicker = new JXDatePicker(new java.util.Date());
		sDatePicker.setFormats(new String[] { "yyyy年MM月dd日" });
		eDatePicker.setFormats(new String[] { "yyyy年MM月dd日" });
		panel.add(sDatePicker);
		panel.add(new JLabel("至"));
		panel.add(eDatePicker);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		panel.add(horizontalStrut);
		searchButton = new JButton("搜索");

		panel.add(searchButton);
		
		JButton searchAdButton = new JButton("高级搜索");

		horizontalStrut = Box.createHorizontalStrut(50);
		horizontalStrut.setPreferredSize(new Dimension(50, 0));
		panel.add(horizontalStrut);
		panel.add(searchAdButton);
		searchAdButton.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchDlg==null)searchDlg=new SearchPicDlg();
				searchDlg.setVisible(true);
				loadData( searchDlg.getSqlString());

			}

			
		});

		JPanel panel2 = new JPanel(new HorizontalLayout(1));
		panel2.add(new JLabel("按客户查找:"));
		panel2.add(cstlist);

		JPanel panel3= new JPanel() ;
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel3.add(panel);
		panel3.add(panel2);
		add(panel3, BorderLayout.NORTH);
		actions();
		requestFocus();

	}
	private void actions() {
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchString = " 0=0 ";
				if (txtBox.getSelectedItem() != null
						&& !txtBox.getSelectedItem().equals("")) {
					if (textField.getText() == null
							|| textField.getText().equals("")) {
						JOptionPane.showMessageDialog(PicShowPnl.this, "请输入要搜索的内容！",
								"错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) txtBox.getSelectedItem()) {
						case "客户名称":
							field = "bill.custom";
							break;
						case "订单号":
							field = "custBillNo";
							break;
						case "项目号":
							field = "item";
							break;
						case "图号":
							field = "picid";
							break;
						case "订单组":
							field = "bill.billgroup";
							break;
						case "备注":
							field = "a.note";
							break;

						default:
							break;
						}
						searchString += " and " + field + " like '%"
								+ textField.getText() + "%'";
					}
				}
				if (dateBox.getSelectedItem() != null
						&& !dateBox.getSelectedItem().equals("")) {
					if (sDatePicker.getDate() == null
							|| eDatePicker.getDate() == null) {
						JOptionPane
						.showMessageDialog(PicShowPnl.this, "请输入要搜索的时间区间！",
								"错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) dateBox.getSelectedItem()) {
						case "订单日期":
							field = "bill.billDate";
							break;
						case "求交货日期":
							field = "bill.requestDate";
							break;
						case "订单交货日期":
							field = " bill.billGetDate";
							break;
						case "外协交货日期":
							field = " outGetDate";
							break;
						default:
							break;
						}
						SimpleDateFormat dfDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						searchString += " and " + field + " between '"
								+ dfDateFormat.format(sDatePicker.getDate())
								+ "' and '"
								+ dfDateFormat.format(eDatePicker.getDate())
								+ "'";

					}
				}
				loadData(searchString);

			}
		});
		
		cstlist.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				loadData("a.bill.custom='"+e.getItem()	+ "'");
				
			}
		});
	}
	private void loadData(final String search) {
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				if (dialog == null) {
					dialog = new JDialog();
					JLabel label1 = new JLabel("正在载入数据,请稍候。。。");
					label1.setBackground(new Color(0.5f, 0.5f, 0.5f));
					label1.setForeground(Color.red);
					label1.setBorder(new LineBorder(Color.black));
					dialog.getContentPane().add(label1);
					dialog.setUndecorated(true);
					dialog.setAlwaysOnTop(true);
					dialog.setBounds(1,1, 130, 30);

				}
				try {
					dialog.setLocationRelativeTo(PicShowPnl.this);
					dialog.setVisible(true);
					searchAction(search);
					
					dialog.setVisible(false);
				} catch (Exception e) {
					dialog.setVisible(false);
				}finally{
					dialog.setVisible(false);
				}
				return null;
			}
		}.execute();
	}
}