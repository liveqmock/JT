package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import ui.costPanes.NumberCellRenderer;
import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;

public abstract class BillShowPnl extends JPanel{

	private JXList cstlist;
	protected JXTable table;
	private JTextField textField;
	private JComboBox<String> dateBox;
	private JComboBox<String> txtBox;
	private JXDatePicker sDatePicker;
	private JXDatePicker eDatePicker;
	private JButton searchButton;


	private SearchDlg searchDlg;
	private JDialog dialog;

	public abstract void searchAction(String search);

	public abstract void itemSelectAction();

	public BillShowPnl() {

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		cstlist = new JXList(Custom.LoadNames());
		cstlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(cstlist);

		

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label = new JLabel(
				"<html><B><font color=red>快捷搜索:</font></B></html>");
		label.setMaximumSize(new Dimension(50, 40));
		panel.add(Box.createHorizontalGlue());
		panel.add(label);

		txtBox = new JComboBox<String>();
		txtBox.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"客户名称", "订单号",
				"项目号", "图号","订单组","发票号码", "备注"}));
		panel.add(txtBox);
		panel.add(new JLabel(":"));

		textField = new JTextField();
		textField.setToolTipText("输入查询的内容");
		panel.add(textField);
		textField.setColumns(10);
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
		horizontalStrut.setPreferredSize(new Dimension(30, 0));
		panel.add(horizontalStrut);
		searchButton = new JButton("搜索");
		panel.add(searchButton);
		JButton searchAdButton = new JButton("高级搜索");
		panel.add(searchAdButton);
		searchAdButton.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchDlg==null)searchDlg=new SearchDlg();
				searchDlg.setVisible(true);
				loadData( searchDlg.getSqlString());

			}

			
		});
		panel.add(Box.createHorizontalGlue());
		add(panel, BorderLayout.NORTH);
		
		table = new JXTable();
		table.setDefaultRenderer(float.class, new NumberCellRenderer() );
		table.setDefaultRenderer(double.class, new NumberCellRenderer() );
		table.setDefaultRenderer(Number.class, new NumberCellRenderer() );
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setHorizontalScrollEnabled(true);
		table.setHighlighters(HighlighterFactory.createAlternateStriping());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnControlVisible(true);
		table.setGridColor(Color.gray);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.packTable(10);
		table.packAll();
		table.setHorizontalScrollEnabled(true);
		JScrollPane jScrollPane = new JScrollPane(table);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPane, jScrollPane);
		splitPane.setOneTouchExpandable(true);
		add(splitPane);
		splitPane.setDividerLocation(200);
		actions();
		setVisible(true);
		requestFocus();


	}
	private void actions() {
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchString = " true ";
				if (txtBox.getSelectedItem() != null
						&& !txtBox.getSelectedItem().equals("")) {
					if (textField.getText() == null
							|| textField.getText().equals("")) {
						JOptionPane.showMessageDialog(BillShowPnl.this, "请输入要搜索的内容！",
								"错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) txtBox.getSelectedItem()) {
						case "客户名称":
							field = "custom";
							break;
						case "订单号":
							field = "billid";
							break;
						case "项目号":
							field = "item";
							break;
						case "图号":
							field = "picid";
							break;
						case "发票号码":
							field = "billno";
							break;
						case "订单组":
							field = "billgroup";
							break;
						case "备注":
							field = "note";
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
						.showMessageDialog(BillShowPnl.this, "请输入要搜索的时间区间！",
								"错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) dateBox.getSelectedItem()) {
						case "订单日期":
							field = "billdate";
							break;
						case "请求交货日期":
							field = "requestdate";
							break;
						case "外协交货日期":
							field = "outgetdate";
							break;
						case "订单交货日期":
							field = "billeddate";
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
		table.getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					itemSelectAction();
				}

			}

		});
		cstlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				if (arg0.getValueIsAdjusting()) {

					loadData("custom='"+cstlist.getSelectedValue()	+ "'");

				}

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
					dialog.setLocationRelativeTo(table);
					dialog.setVisible(true);
					searchAction(search);
					dialog.setVisible(false);
				} catch (Exception e) {
					dialog.setVisible(false);
				}
				return null;
			}
		}.execute();
	}
}