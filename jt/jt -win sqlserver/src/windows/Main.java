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

import javax.persistence.EntityManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import ui.MainMenu;
import ui.customComponet.BeanDialog;
import ui.frames.About;
import ui.panels.BillManagerPnl;
import ui.panels.LoginPanel;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.SessionData;
import com.mao.jf.beans.Userman;

public class Main extends JFrame {


	/**
	 * Launch the application.
	 */

	public  static int ver=49;
 

	public Main() {
		setBounds(100, 100, 700, 400);
		setTitle("���ﾫ�ܻ�����������ϵͳ"); 
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/ui/logo.PNG")));
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


		setVisible(true);
		toFront();
		setAutoRequestFocus(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO �Զ����ɵķ������
				super.windowClosing(e);
				BeanMao.beanManager.flush();
			}
			
		});
	}

	public static void main(String[] args) {
		 EntityManager a = BeanMao.beanManager.getEm();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					 new Runnable() {
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
							BeanMao.beanManager.getEm();
						}
					}.run();
					
					
					try(Statement st=SessionData.getConnection().createStatement();){
						ResultSet rs=st.executeQuery("select * from version");
						if(rs.next()){
							if(ver<rs.getInt(1)){
								try{
								update();
								}catch(Exception exception){
									
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
					Userman user = (Userman) SerialiObject.loadFile(new File(
							"user.dat"));
					if (user == null)
						user = new Userman();
					Dialog dialog=new BeanDialog<Userman>(new LoginPanel(user), "��¼") {

						@Override
						public boolean okButtonAction() {
							try{
								
								Userman loginUser = BeanMao.getBean(Userman.class, " a.name='"+getBean().getName()+"' and password='"+getBean().getPassword()+"'");
								Userman.loginUser=loginUser;
								try {
									SerialiObject.save(getBean(), new File(
											"user.dat"));
								} catch (Exception e) {
									// TODO �Զ����ɵ� catch ��
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
							System.exit(0);
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
		System.exit(0);
	}
}