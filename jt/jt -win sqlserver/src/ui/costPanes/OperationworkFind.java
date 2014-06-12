package ui.costPanes;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.OperationWork;

import ui.customComponet.BeanTablePane;

public class OperationworkFind extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public OperationworkFind() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("\u56FE\u53F7:");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(40);
		
		JButton findBt = new JButton("\u67E5\u627E(F)");
		findBt.setMnemonic('f');
		panel.add(findBt);
		final BeanTablePane<OperationWork> tablePane=new BeanTablePane<>(null, OperationWork.class);
		add(tablePane,BorderLayout.CENTER);
		findBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(StringUtils.isNotBlank( textField.getText())){
					tablePane.setBeans(BeanMao.getBeans(OperationWork.class, " plan.pic.picid like ?1", "%"+textField.getText()+"%"));
				}
				
			}
		});
		tablePane.getPopupMenu().add(new AbstractAction("删除") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OperationWork bean = tablePane.getSelectBean();
				if(bean!=null
						&& JOptionPane.showConfirmDialog(tablePane, "确定要删除选择的条目?","删除确认!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
						tablePane.removeSelectRow();
					
				}
				
			}
		});
	}

}
