package windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import windows.panels.BillPanel;

import com.mao.jf.beans.Bill;

public class BillFrame extends JDialog {

	private JPanel contentPane;
	private BillPanel billPanel;

	public BillFrame(Bill billItem) {
		billPanel = new BillPanel(billItem);
		createContents();
	}

	private void createContents() {
		setTitle("¶©µ¥±à¼­");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton btnNewButton = new JButton("\u53D6\u6D88(C)");
		btnNewButton.setMnemonic('C');
		panel.add(btnNewButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);

		JButton btnNewButton_1 = new JButton("\u786E\u5B9A(O)");
		btnNewButton_1.setMnemonic('O');
		panel.add(btnNewButton_1);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_2);

		contentPane.add(billPanel, BorderLayout.CENTER);
		contentPane.add(panel, BorderLayout.SOUTH);
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		setLocationRelativeTo(null);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillFrame.this.dispose();
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(billPanel.isValide()){
					billPanel.saveBill();
					billPanel.getBean().save();
					BillFrame.this.dispose();
				}
			}
		});
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}

}
