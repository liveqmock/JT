package ui.panels;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SearchBillDlg extends JDialog {
	private JTextField customName;
	private JTextField billNo;
	private JTextField billgroup;
	private JTextField note;
	private JXDatePicker requestDateS;
	private JXDatePicker requestDateE;
	private JXDatePicker completeDateS;
	private JXDatePicker completeDateE;
	private JXDatePicker billDateS;
	private JXDatePicker billDateE;
	private String sqlString=null;
	/**
	 * Create the panel.
	 */
	public SearchBillDlg() {
		createContents();

		JButton searBt = new JButton("查询(S)");
		searBt.setMnemonic('s');
		getContentPane().add(searBt, BorderLayout.SOUTH);
		

		searBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				doSearch();


			}


		});
		pack();
		setLocationRelativeTo(null);
		setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		
	}
	private void doSearch() {
		sqlString=" 0=0 ";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(customName.getText()!=null && customName.getText().length()>0)
			sqlString+=" and custom like '%"+customName.getText()+"%' ";
		if(billNo.getText()!=null && billNo.getText().length()>0)
			sqlString+=" and billid like '%"+billNo.getText()+"%' ";
		if(billgroup.getText()!=null && billgroup.getText().length()>0)
			sqlString+=" and billgroup like '%"+billgroup.getText()+"%' ";
		if(note.getText()!=null && note.getText().length()>0)
			sqlString+=" and note like '%"+note.getText()+"%' ";
		
		if(requestDateS.getDate()!=null||requestDateE.getDate()!=null){
			if(requestDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				requestDateS.requestFocus();
				return;
			}  
			if(requestDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				requestDateE.requestFocus();
				return;
			}

			sqlString+=" and requestDate between '"+df.format(requestDateS.getDate())+"' and '"+df.format(requestDateS.getDate())+"' ";
		}
		if(billDateS.getDate()!=null||billDateE.getDate()!=null){
			if(billDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				billDateS.requestFocus();
				return;
			} 
			if(billDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				billDateE.requestFocus();
				return;
			}

			sqlString+=" and billdate between '"+df.format(billDateS.getDate())+"' and '"+df.format(billDateE.getDate())+"' ";
		}
		if(completeDateS.getDate()!=null||completeDateE.getDate()!=null){
			if(completeDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				completeDateS.requestFocus();
				return;
			} 
			if(completeDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchBillDlg.this, "请同时指定开始和结束日期","错误",JOptionPane.ERROR_MESSAGE);
				completeDateE.requestFocus();
				return;
			}

			sqlString+=" and billGetDate between '"+df.format(completeDateS.getDate())+"' and '"+df.format(completeDateE.getDate())+"' ";
		}
		this.dispose();

	}
	private void createContents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SearchBillDlg.class.getResource("/ui/logo.PNG")));
		setTitle("高级查询");
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		JLabel lblNewLabel = new JLabel("客户名称：");
		mainPanel.add(lblNewLabel, "2, 2, right, default");

		customName = new JTextField();
		mainPanel.add(customName, "4, 2, fill, default");
		customName.setColumns(10);

		JLabel label = new JLabel("订单号：");
		mainPanel.add(label, "2, 4, right, default");

		billNo = new JTextField();
		billNo.setColumns(10);
		mainPanel.add(billNo, "4, 4, fill, default");

		JLabel label_4 = new JLabel("订单组：");
		mainPanel.add(label_4, "2, 6");

		billgroup = new JTextField();
		billgroup.setColumns(10);
		mainPanel.add(billgroup, "4, 6, fill, default");

		JLabel label_6 = new JLabel("备注：");
		mainPanel.add(label_6, "2, 8, right, default");

		note = new JTextField();
		note.setColumns(10);
		mainPanel.add(note, "4, 8, fill, default");

		JLabel label_7 = new JLabel("订单日期：");
		mainPanel.add(label_7, "2, 10");

		JPanel panel_2 = new JPanel();
		mainPanel.add(panel_2, "4, 10, fill, fill");
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel label_15 = new JLabel("从");
		panel_2.add(label_15);

		billDateS = new JXDatePicker();
		billDateS.setFormats(new String[] {"yyyy年MM月dd日"});
		panel_2.add(billDateS);

		JLabel label_16 = new JLabel("至");
		panel_2.add(label_16);

		billDateE = new JXDatePicker();
		billDateE.setFormats(new String[] {"yyyy年MM月dd日"});
		panel_2.add(billDateE);

		JLabel label_8 = new JLabel("要求交货日期：");
		mainPanel.add(label_8, "2, 12");

		JPanel panel = new JPanel();
		mainPanel.add(panel, "4, 12, fill, default");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label_12 = new JLabel("从");
		panel.add(label_12);

		requestDateS = new JXDatePicker();
		requestDateS.setFormats(new String[] {"yyyy年MM月dd日"});
		panel.add(requestDateS);

		JLabel label_3 = new JLabel("至");
		panel.add(label_3);

		requestDateE = new JXDatePicker();
		requestDateE.setFormats(new String[] {"yyyy年MM月dd日"});
		panel.add(requestDateE);

		JLabel label_9 = new JLabel("订单交货日期：");
		mainPanel.add(label_9, "2, 14");

		JPanel panel_1 = new JPanel();
		mainPanel.add(panel_1, "4, 14, fill, fill");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel label_13 = new JLabel("从");
		panel_1.add(label_13);

		completeDateS = new JXDatePicker();
		completeDateS.setFormats(new String[] {"yyyy年MM月dd日"});
		panel_1.add(completeDateS);

		JLabel label_14 = new JLabel("至");
		panel_1.add(label_14);

		completeDateE = new JXDatePicker();
		completeDateE.setFormats(new String[] {"yyyy年MM月dd日"});
		panel_1.add(completeDateE);


		getContentPane().add(mainPanel);

	}
	public String getSqlString() {
		return sqlString;
	}
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

}
