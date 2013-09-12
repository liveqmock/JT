package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXList;

import windows.customComponet.BeanDialog;
import windows.panels.LoginPanel;
import windows.panels.SearchDlg;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.User;

public class Main extends JFrame {

	private JPanel contentPane;
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

	/**
	 * Launch the application.
	 */
	
	public  static int ver=4;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					
					jcifs.Config.setProperty("jcifs.smb.client.domain",
							"192.168.1.103");
					jcifs.Config.setProperty("jcifs.smb.client.username", "cw");
					jcifs.Config.setProperty("jcifs.smb.client.password",
							"mao564864");
					jcifs.Config.setProperty(
							"jcifs.smb.client.responseTimeout", "5000");
					jcifs.Config.setProperty("jcifs.smb.client.soTimeout",
							"5000");
					
					isSmb=testSmb();
					if(isSmb){
						try(Statement st=SessionData.getConnection().createStatement();){
							ResultSet rs=st.executeQuery("select * from version");
							if(rs.next()){
								if(ver<rs.getInt(1)){
									SmbFileInputStream smbFile=new SmbFileInputStream("smb://192.168.1.103/pics/main.jar");
									
											int byteread = 0;
											FileOutputStream fs = new FileOutputStream("main.jar");
											byte[] buffer = new byte[2048];
											while ((byteread = smbFile.read(buffer)) != -1) {

												fs.write(buffer, 0, byteread);
											}
											smbFile.close();
											fs.close();
								}
								
							}
						}
					}
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					User user = (User) SerialiObject.loadFile(new File(
							"user.dat"));
					if (user == null)
						user = new User();
					Dialog dialog=new BeanDialog<User>(new LoginPanel(user), "登录") {

						@Override
						public boolean okButtonAction() {

							if (getBean().load()) {
								try {
									SerialiObject.save(getBean(), new File(
											"user.dat"));
								} catch (Exception e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
								new Main();
								return true;
							} else
								return false;
						}
					};
					dialog.setVisible(true);
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static boolean testSmb() {
		try {
			new SmbFile("smb://192.168.1.103/pics/").canRead();
			System.err.println("smb is OK");
			 return true;
		} catch (SmbException | MalformedURLException e1) {
			System.err.println("smb is not\n"+e1.getMessage());
			return false;
		}
	}
	public static boolean isSmb;
	public Main() {
		setBounds(100, 100, 700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("津田精密机构订单管理系统");
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/windows/logo.PNG")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		cstlist = new JXList(Custom.LoadNames());
		cstlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(cstlist);

		table = new BillTable(null);
		setJMenuBar(new MainMenu(table));
		imageView = new MyImageView();
		splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				table, imageView);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollPane, splitPaneRight);
		splitPaneRight.setOneTouchExpandable(true);
		splitPane.setOneTouchExpandable(true);
		contentPane.add(splitPane);

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
				if(searchDlg==null)searchDlg=new SearchDlg(table);
				searchDlg.setVisible(true);
			}
		});
		panel.add(Box.createHorizontalGlue());
		contentPane.add(panel, BorderLayout.NORTH);
		splitPane.setDividerLocation(200);
		splitPaneRight.setDividerLocation(((Double) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()).intValue() - 500);
		actions();
		setVisible(true);
		requestFocus();
		
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				super.windowClosing(arg0);
				Main.this.table.saveStatus();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO 自动生成的方法存根
				super.windowClosed(e);
				Main.this.table.saveStatus();
			}

			
		});
		
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
						JOptionPane.showMessageDialog(Main.this, "请输入要搜索的内容！",
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
						.showMessageDialog(Main.this, "请输入要搜索的时间区间！",
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
				table.setBeans(Bill.loadBySearch(searchString));

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
								Vector<Bill> bills = Bill
										.loadBySearch("custom='"
												+ cstlist.getSelectedValue()
												+ "'");
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
}