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
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import ui.customComponet.BeanDialog;
import ui.frames.About;
import ui.menu.MainMenu;
import ui.panels.LoginPanel;
import ui.panels.MainPanel;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class Main extends JFrame {


	/**
	 * Launch the application.
	 */

	public  static int ver=68;


	public Main() {
		setBounds(100, 100, 700, 400);
		setTitle("津田精密机构订单管理系统"); 
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/ui/logo.PNG")));
		final MainPanel mainPanel=new MainPanel();
		setContentPane(mainPanel);
		setJMenuBar(new  MainMenu());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);

		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				super.windowClosing(arg0);
				mainPanel.saveTableStatus();
				BeanMao.beanManager.flush();
				BeanMao.close();
			}

		});
	}

	public static void main(String[] args) {
		final Spash splash=new Spash();
		splash.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Runnable() {
						@Override
						public void run() {
							jcifs.Config.setProperty("jcifs.smb.client.domain",
									"192.168.1.103");
							jcifs.Config.setProperty("jcifs.smb.client.username", "cw");
							jcifs.Config.setProperty("jcifs.smb.client.password",
									"mao564864");
							jcifs.Config.setProperty(
									"jcifs.smb.client.responseTimeout", "5000");
							jcifs.Config.setProperty("jcifs.smb.client.soTimeout",
									"5000");
							try {
								fileCheck();
							} catch (IOException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
						}
					}.run();

					int dbVer= (int) BeanMao.beanManager.queryNativeSingle("select ver from version",null);
					if(ver<dbVer){
						try{
							update();
						}catch(Exception exception){

						}
					}
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					Userman user = (Userman) SerialiObject.loadFile(new File("user.dat"));
					if (user == null)
						user = new Userman();
					Dialog dialog=new BeanDialog<Userman>(new LoginPanel(user), "登录") {

						@Override
						public boolean okButtonAction() {
							try{

								Userman loginUser = BeanMao.getBean(Userman.class, " a.name='"+getBean().getName()+"' and password='"+getBean().getPassword()+"'");
								Userman.loginUser=loginUser;
								try {
									SerialiObject.save(getBean(), new File("user.dat"));
								} catch (Exception e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
								new Main();
								return true;
							} catch(Exception e){
								e.printStackTrace();
								return false;
							}
						}

						@Override
						public void cancel() {
							try {
								BeanMao.close();
								SessionData.getConnection().close();
							} catch (SQLException e) {
							}
							System.exit(0);
						}


					};
					splash.dispose();
					dialog.setVisible(true);
					dialog.setAlwaysOnTop(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});
	}
	private static void update() throws IOException {

		fileUpdateCheck();
		JOptionPane.showMessageDialog(null, "更新完成，请重新打开程序");
		BeanMao.close();
		System.exit(0);
	}
	private static void fileUpdateCheck() throws IOException {
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

			if((!localFile.exists())||file.length()!=localFile.length()) {
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

	}
	private static void fileCheck() throws IOException {

		int byteread = 0;
		byte[] buffer = new byte[2048];

		for(SmbFile file: new SmbFile("smb://192.168.1.103/pics/main_lib/").listFiles()) {
			File localFile= new File("main_lib/"+file.getName());

			if((!localFile.exists())||file.length()!=localFile.length()) {
				SmbFileInputStream smbFile = new SmbFileInputStream(file);

				byteread = 0;
				FileOutputStream fs = new FileOutputStream(localFile);
				while ((byteread = smbFile.read(buffer)) != -1) {

					fs.write(buffer, 0, byteread);
				}
				smbFile.close();
				fs.close();
			}
		}

	}
}