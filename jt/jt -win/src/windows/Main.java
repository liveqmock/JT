package windows;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import windows.customComponet.BeanDialog;
import windows.panels.LoginPanel;

import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class Main extends JFrame {


	/**
	 * Launch the application.
	 */

	public  static int ver=33;


	public Main() {
		setBounds(100, 100, 700, 400);
		setTitle("���ﾫ�ܻ�����������ϵͳ");
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/windows/logo.PNG")));
		final BillManagerPnl billManagerPnl=new BillManagerPnl();
		setJMenuBar(new MainMenu(billManagerPnl.getTable()));
		setContentPane(billManagerPnl);

		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO �Զ����ɵķ������
				super.windowClosing(arg0);
				billManagerPnl.saveTableStatus();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO �Զ����ɵķ������
				super.windowClosed(e);
				billManagerPnl.saveTableStatus();
			}


		});
		//			if(Userman.loginUser.isManager()) {
		//			}else if(Userman.loginUser.getLevelStr().equals("ͳ��Ա")){
		//			final BillManagerPnl billManagerPnl=new BillManagerPnl();
		//			setJMenuBar(new MainMenu(billManagerPnl.getTable()));
		//			setContentPane(billManagerPnl);
		//
		//
		//			addWindowListener(new WindowAdapter(){
		//
		//				@Override
		//				public void windowClosing(WindowEvent arg0) {
		//					// TODO �Զ����ɵķ������
		//					super.windowClosing(arg0);
		//					billManagerPnl.saveTableStatus();
		//				}
		//
		//				@Override
		//				public void windowClosed(WindowEvent e) {
		//					// TODO �Զ����ɵķ������
		//					super.windowClosed(e);
		//					billManagerPnl.saveTableStatus();
		//				}
		//
		//
		//			});
		//		}else if(Userman.loginUser.getLevelStr().equals("�ƻ��ɹ�")){
		//			setJMenuBar(new MainMenu(null));
		//			final PlanTable table = new PlanTable();
		//			JScrollPane scroll = new JScrollPane( table );
		//			JPanel btPanel=new JPanel();
		//			btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
		//
		//			JButton Okbt = new JButton(new AbstractAction("ȷ��") {
		//
		//				@Override
		//				public void actionPerformed(ActionEvent e) {
		//					table.save();
		//				}
		//			});
		//			JButton addBt=new JButton("�����Ų��ƻ�");
		//			addBt.addActionListener(new ActionListener() {
		//
		//				@Override
		//				public void actionPerformed(ActionEvent e) {
		//					table.createPlan();
		//
		//				}
		//			});
		//
		//			btPanel.add(addBt);
		//			btPanel.add(Okbt);
		//			getContentPane().add(scroll,BorderLayout.CENTER);
		//			getContentPane().add(btPanel,BorderLayout.SOUTH);
		//		}else if(Userman.loginUser.getLevelStr().equals("�ֿ����Ա")){
		//			final MaterialTable table = new MaterialTable();
		//			JScrollPane scroll = new JScrollPane( table );
		//			JPanel btPanel=new JPanel();
		//			btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
		//
		//			JButton Okbt = new JButton(new AbstractAction("ȷ��") {
		//
		//				@Override
		//				public void actionPerformed(ActionEvent e) {
		//					table.save();
		//				}
		//			});
		//
		//
		//			btPanel.add(Okbt);
		//
		//			getContentPane().add(scroll,BorderLayout.CENTER);
		//			getContentPane().add(btPanel,BorderLayout.SOUTH);
		//		}


		setVisible(true);
		toFront();
		setAutoRequestFocus(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		
	}

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

					try(Statement st=SessionData.getConnection().createStatement();){
						ResultSet rs=st.executeQuery("select * from version");
						if(rs.next()){
							if(ver<rs.getInt(1)){

								update();
							}

						}
					}
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					Userman user = (Userman) SerialiObject.loadFile(new File(
							"user.dat"));
					if (user == null)
						user = new Userman();
					Dialog dialog=new BeanDialog<Userman>(new LoginPanel(user), "��¼") {

						@Override
						public boolean okButtonAction() {

							if (getBean().load()) {
								try {
									SerialiObject.save(getBean(), new File(
											"user.dat"));
								} catch (Exception e) {
									// TODO �Զ����ɵ� catch ��
									e.printStackTrace();
								}
								new Main();
								return true;
							} else
								return false;
						}
					};
					dialog.setVisible(true);
					dialog.toFront();
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});
	}
	private static void update() throws IOException {
		SmbFileInputStream smbFile=new SmbFileInputStream("smb://192.168.1.103/pics/main.jar");

		int byteread = 0;
		FileOutputStream fs = new FileOutputStream("main.jar");
		byte[] buffer = new byte[2048];
		while ((byteread = smbFile.read(buffer)) != -1) {

			fs.write(buffer, 0, byteread);
		}
		smbFile.close();
		fs.close();
		for(SmbFile file: new SmbFile("smb://192.168.1.103/pics/main_lib/").listFiles()) {
			File localFile= new File("main_lib/"+file.getName());
			if(!localFile.exists()) {
				smbFile=new SmbFileInputStream(file);

				byteread = 0;
				fs = new FileOutputStream(localFile);
				while ((byteread = smbFile.read(buffer)) != -1) {

					fs.write(buffer, 0, byteread);
				}
				smbFile.close();
				fs.close();
			}
		}
		JOptionPane.showMessageDialog(null, "������ɣ������´򿪳���");
	}
}