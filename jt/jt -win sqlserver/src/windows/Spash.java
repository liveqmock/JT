package windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.frames.About;

public class Spash extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			Spash dialog = new Spash();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Spash() {
		setUndecorated(true);
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/ui/logo.PNG")));
		setBounds(100, 100, 417, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5609\u5174\u6D25\u7530\u7CBE\u5BC6\u673A\u68B0\u5236\u9020\u6709\u9650\u516C\u53F8");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 25));
		lblNewLabel.setBounds(20, 107, 370, 35);
		contentPanel.add(lblNewLabel);
		
		JLabel lblVer = new JLabel("\u8BA2\u5355\u7BA1\u7406\u7CFB\u7EDF Ver " + Main.ver);
		lblVer.setFont(new Font("����", Font.PLAIN, 16));
		lblVer.setBounds(118, 163, 205, 15);
		contentPanel.add(lblVer);
		
		JLabel lblNewLabel_2 = new JLabel("\u7248\u6743:\u6BDB\u5FE0\u65B9 \r\n@2013 \u672C\u8F6F\u4EF6\u53D7\u4E2D\u534E\u4EBA\u6C11\u5171\u548C\u56FD\u53CA\u56FD\u9645\u6CD5\u7248\u6743\u4FDD\u62A4\u3002");
		lblNewLabel_2.setBounds(20, 188, 387, 28);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(About.class.getResource("/ui/logo.PNG")));
		lblNewLabel_1.setBounds(152, 10, 171, 82);
		contentPanel.add(lblNewLabel_1);
		setLocationRelativeTo(null);
	}

}
