package windows;

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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXList;

import windows.panels.SearchDlg;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;

public class BillManagerPnl extends JPanel{

	private JXList cstlist;
	private BillTable table;
	private MyImageView imageView;
	private JTextField textField;
	private JComboBox<String> dateBox;
	private JComboBox<String> txtBox;
	private JXDatePicker sDatePicker;
	private JXDatePicker eDatePicker;
	private JButton searchButton;


	private SearchDlg searchDlg;
	private JDialog dialog;
	private JSplitPane splitPaneRight;

	
	public BillManagerPnl() {

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		cstlist = new JXList(Custom.LoadNames());
		cstlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(cstlist);

		table = new BillTable(null);
		imageView = new MyImageView();
		splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				table, imageView);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPane, splitPaneRight);
		splitPaneRight.setOneTouchExpandable(true);
		splitPane.setOneTouchExpandable(true);
		add(splitPane);

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel label = new JLabel(
				"<html><B><font color=red>�������:</font></B></html>");
		label.setMaximumSize(new Dimension(50, 40));
		panel.add(Box.createHorizontalGlue());
		panel.add(label);

		txtBox = new JComboBox<String>();
		txtBox.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"�ͻ�����", "������",
				"��Ŀ��", "ͼ��","������","��Ʊ����", "��ע"}));
		panel.add(txtBox);
		panel.add(new JLabel(":"));

		textField = new JTextField();
		textField.setToolTipText("�����ѯ������");
		panel.add(textField);
		textField.setColumns(10);
 
		dateBox = new JComboBox<String>();
		dateBox.setModel(new DefaultComboBoxModel<String>(new String[] { "",
				"��������",
				"Ҫ�󽻻�����", "��Э��������",
		"������������" }));
		panel.add(dateBox);
        
		panel.add(new JLabel(":"));
		sDatePicker = new JXDatePicker();
		eDatePicker = new JXDatePicker(new java.util.Date());
		sDatePicker.setFormats(new String[] { "yyyy��MM��dd��" });
		eDatePicker.setFormats(new String[] { "yyyy��MM��dd��" });
		panel.add(sDatePicker);
		panel.add(new JLabel("��"));
		panel.add(eDatePicker);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(30, 0));
		panel.add(horizontalStrut);
		searchButton = new JButton("����");
		panel.add(searchButton);
		JButton searchAdButton = new JButton("�߼�����");
		panel.add(searchAdButton);
		searchAdButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchDlg==null)searchDlg=new SearchDlg(table);
				searchDlg.setVisible(true);
			}
		});
		panel.add(Box.createHorizontalGlue());
		add(panel, BorderLayout.NORTH);
		splitPane.setDividerLocation(200);
		splitPaneRight.setDividerLocation(((Double) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()).intValue() - 500);
		actions();
		setVisible(true);
		requestFocus();
		
		
	}

	
	public void saveTableStatus() {
		table.saveStatus();
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
						JOptionPane.showMessageDialog(BillManagerPnl.this, "������Ҫ���������ݣ�",
								"����", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) txtBox.getSelectedItem()) {
						case "�ͻ�����":
							field = "custom";
							break;
						case "������":
							field = "billid";
							break;
						case "��Ŀ��":
							field = "item";
							break;
						case "ͼ��":
							field = "picid";
							break;
						case "��Ʊ����":
							field = "billno";
							break;
						case "������":
							field = "billgroup";
							break;
						case "��ע":
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
						.showMessageDialog(BillManagerPnl.this, "������Ҫ������ʱ�����䣡",
								"����", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						String field = "";
						switch ((String) dateBox.getSelectedItem()) {
						case "��������":
							field = "billdate";
							break;
						case "���󽻻�����":
							field = "requestdate";
							break;
						case "��Э��������":
							field = "outgetdate";
							break;
						case "������������":
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
				table.setBeans(Bill.loadBySearch(searchString,true));

			}
		});
		table.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
							
								if (table.getSelectBean()!=null&&table.getSelectBean().getImageUrl() != null
										&& !table.getSelectBean()
										.getImageUrl()
										.equals("")) {
									imageView.showFile(table.getSelectBean()
											.getImageUrl());
								}else{
									imageView.setViewportView(null);
								}

				}

			}
		});
		cstlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				if (arg0.getValueIsAdjusting()) {

					new SwingWorker<Void, Void>() {
						@Override
						public Void doInBackground() {
							if (dialog == null) {
								dialog = new JDialog();
								JLabel label1 = new JLabel("������������,���Ժ򡣡���");
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
								Vector<Bill> bills = Bill
										.loadBySearch("custom='"
												+ cstlist.getSelectedValue()
												+ "'",false);
								table.setBeans(bills);
								dialog.setVisible(false);
							} catch (Exception e) {
								dialog.setVisible(false);
							}
							return null;
						}
					}.execute();

				}

			}
		});
	}


	public BillTable getTable() {
		// TODO �Զ����ɵķ������
		return table;
	}
}