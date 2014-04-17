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

public class SearchDlg extends JDialog {
	private JTextField customName;
	private JTextField billNo;
	private JTextField billgroup;
	private JTextField itemNo;
	private JTextField picNo;
	private JTextField note;
	private JXDatePicker requestDateS;
	private JXDatePicker requestDateE;
	private JXDatePicker outGetDateS;
	private JXDatePicker outGetDateE;
	private JCheckBox isLk;
	private JXDatePicker billDateS;
	private JXDatePicker billDateE;
	private String sqlString=" true ";
	/**
	 * Create the panel.
	 */
	public SearchDlg() {
		createContents();

		JButton searBt = new JButton("��ѯ(S)");
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(customName.getText()!=null && customName.getText().length()>0)
			sqlString+=" and bill.custom like '%"+customName.getText()+"%' ";
		if(billNo.getText()!=null && billNo.getText().length()>0)
			sqlString+=" and bill.billid like '%"+billNo.getText()+"%' ";
		if(billgroup.getText()!=null && billgroup.getText().length()>0)
			sqlString+=" and bill.billgroup like '%"+billgroup.getText()+"%' ";
		if(itemNo.getText()!=null && itemNo.getText().length()>0)
			sqlString+=" and item like '%"+itemNo.getText()+"%' ";
		if(picNo.getText()!=null && picNo.getText().length()>0)
			sqlString+=" and picid like '%"+picNo.getText()+"%' ";
			if(note.getText()!=null && note.getText().length()>0)
			sqlString+=" and note like '%"+note.getText()+"%' ";
		
		if(requestDateS.getDate()!=null||requestDateE.getDate()!=null){
			if(requestDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				requestDateS.requestFocus();
				return;
			}  
			if(requestDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				requestDateE.requestFocus();
				return;
			}

			sqlString+=" and bill.requestDate between '"+df.format(requestDateS.getDate())+"' and '"+df.format(requestDateS.getDate())+"' ";
		}
		if(billDateS.getDate()!=null||billDateE.getDate()!=null){
			if(billDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				billDateS.requestFocus();
				return;
			} 
			if(billDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				billDateE.requestFocus();
				return;
			}

			sqlString+=" and bill.billdate between '"+df.format(billDateS.getDate())+"' and '"+df.format(billDateE.getDate())+"' ";
		}
		if(outGetDateS.getDate()!=null||outGetDateE.getDate()!=null){
			if(outGetDateS.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				outGetDateS.requestFocus();
				return;
			} 
			if(outGetDateE.getDate()==null){
				JOptionPane.showMessageDialog(SearchDlg.this, "��ͬʱָ����ʼ�ͽ�������","����",JOptionPane.ERROR_MESSAGE);
				outGetDateE.requestFocus();
				return;
			}

			sqlString+=" and outGetDate between '"+df.format(outGetDateS.getDate())+"' and '"+df.format(outGetDateS.getDate())+"' ";
		}
		if(isLk.isSelected())
			sqlString+=" and itemCompleteDate is not null ";
		this.dispose();

	}
	private void createContents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SearchDlg.class.getResource("/ui/logo.PNG")));
		setTitle("�߼���ѯ");
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
		JLabel lblNewLabel = new JLabel("�ͻ����ƣ�");
		mainPanel.add(lblNewLabel, "2, 2, right, default");

		customName = new JTextField();
		mainPanel.add(customName, "4, 2, fill, default");
		customName.setColumns(10);

		JLabel label = new JLabel("�����ţ�");
		mainPanel.add(label, "2, 4, right, default");

		billNo = new JTextField();
		billNo.setColumns(10);
		mainPanel.add(billNo, "4, 4, fill, default");

		JLabel label_4 = new JLabel("�����飺");
		mainPanel.add(label_4, "2, 6");

		billgroup = new JTextField();
		billgroup.setColumns(10);
		mainPanel.add(billgroup, "4, 6, fill, default");

		JLabel label_1 = new JLabel("��Ŀ�ţ�");
		mainPanel.add(label_1, "2, 8, right, default");

		itemNo = new JTextField();
		itemNo.setColumns(10);
		mainPanel.add(itemNo, "4, 8, fill, default");

		JLabel label_2 = new JLabel("ͼ�ţ�");
		mainPanel.add(label_2, "2, 10, right, default");

		picNo = new JTextField();
		picNo.setColumns(10);
		mainPanel.add(picNo, "4, 10, fill, default");

		JLabel label_6 = new JLabel("��ע��");
		mainPanel.add(label_6, "2, 12, right, default");

		note = new JTextField();
		note.setColumns(10);
		mainPanel.add(note, "4, 12, fill, default");

		JLabel label_7 = new JLabel("�������ڣ�");
		mainPanel.add(label_7, "2, 14");

		JPanel panel_2 = new JPanel();
		mainPanel.add(panel_2, "4, 14, fill, fill");
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel label_15 = new JLabel("��");
		panel_2.add(label_15);

		billDateS = new JXDatePicker();
		billDateS.setFormats(new String[] {"yyyy��MM��dd��"});
		panel_2.add(billDateS);

		JLabel label_16 = new JLabel("��");
		panel_2.add(label_16);

		billDateE = new JXDatePicker();
		billDateE.setFormats(new String[] {"yyyy��MM��dd��"});
		panel_2.add(billDateE);

		JLabel label_8 = new JLabel("Ҫ�󽻻����ڣ�");
		mainPanel.add(label_8, "2, 16");

		JPanel panel = new JPanel();
		mainPanel.add(panel, "4, 16, fill, default");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label_12 = new JLabel("��");
		panel.add(label_12);

		requestDateS = new JXDatePicker();
		requestDateS.setFormats(new String[] {"yyyy��MM��dd��"});
		panel.add(requestDateS);

		JLabel label_3 = new JLabel("��");
		panel.add(label_3);

		requestDateE = new JXDatePicker();
		requestDateE.setFormats(new String[] {"yyyy��MM��dd��"});
		panel.add(requestDateE);

		JLabel label_9 = new JLabel("��Э�������ڣ�");
		mainPanel.add(label_9, "2, 18");

		JPanel panel_1 = new JPanel();
		mainPanel.add(panel_1, "4, 18, fill, fill");
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel label_13 = new JLabel("��");
		panel_1.add(label_13);

		outGetDateS = new JXDatePicker();
		outGetDateS.setFormats(new String[] {"yyyy��MM��dd��"});
		panel_1.add(outGetDateS);

		JLabel label_14 = new JLabel("��");
		panel_1.add(label_14);

		outGetDateE = new JXDatePicker();
		outGetDateE.setFormats(new String[] {"yyyy��MM��dd��"});
		panel_1.add(outGetDateE);


		JLabel label_11 = new JLabel("���״̬��");
		mainPanel.add(label_11, "2, 20");

		isLk = new JCheckBox("�����");
		mainPanel.add(isLk, "4, 20");
		getContentPane().add(mainPanel);

	}
	public String getSqlString() {
		return sqlString;
	}
	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

}
