package ui.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.Main;

public class About extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public About() {
		createContents();
	}
	private void createContents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/ui/logo.PNG")));
		setBounds(100, 100, 411, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5609\u5174\u6D25\u7530\u7CBE\u5BC6\u673A\u68B0\u5236\u9020\u6709\u9650\u516C\u53F8");
		lblNewLabel.setFont(new Font("ºÚÌå", Font.PLAIN, 25));
		lblNewLabel.setBounds(20, 91, 360, 35);
		contentPanel.add(lblNewLabel);
		
		JLabel lblVer = new JLabel("\u8BA2\u5355\u7BA1\u7406\u7CFB\u7EDF Ver " + Main.ver);
		lblVer.setFont(new Font("ËÎÌå", Font.PLAIN, 16));
		lblVer.setBounds(104, 136, 160, 15);
		contentPanel.add(lblVer);
		
		JLabel lblNewLabel_2 = new JLabel("\u7248\u6743:\u6BDB\u5FE0\u65B9 \r\n@2013 \u672C\u8F6F\u4EF6\u53D7\u4E2D\u534E\u4EBA\u6C11\u5171\u548C\u56FD\u53CA\u56FD\u9645\u6CD5\u7248\u6743\u4FDD\u62A4\u3002");
		lblNewLabel_2.setBounds(10, 177, 385, 28);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(About.class.getResource("/ui/logo.PNG")));
		lblNewLabel_1.setBounds(152, 10, 171, 82);
		contentPanel.add(lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A(O)");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						About.this.dispose();
					}
				});
				okButton.setMnemonic('O');
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
