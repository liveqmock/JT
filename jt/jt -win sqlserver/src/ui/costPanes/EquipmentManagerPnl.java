package ui.costPanes;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mao.jf.beans.BeanManager;
import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Equipment;
import com.mao.jf.beans.Operation;

import ui.customComponet.BeanDialog;
import ui.customComponet.BeanTablePane;

public class EquipmentManagerPnl extends JPanel {

	 private OperationPnl operationPnl;
	 private BeanTablePane<Operation> operationTablePane;
	 private BeanTablePane<Equipment> equipmentTablePane;
	public EquipmentManagerPnl() {
		setLayout(new BorderLayout(0, 0));
		operationPnl=new OperationPnl(new Operation());
		operationTablePane=new BeanTablePane<>(BeanMao.getBeans(Operation.class),Operation.class);
		equipmentTablePane=new BeanTablePane<>(null,Equipment.class);
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		splitPane.setTopComponent(operationTablePane);
		splitPane.setBottomComponent(equipmentTablePane);
		splitPane.setDividerLocation(400);
		panel.add(splitPane, BorderLayout.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton OKBt = new JButton("确定");

		operationPnl.add(OKBt, "4, 10, right, default");
		panel_1.add(operationPnl, BorderLayout.CENTER);

		OKBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!operationPnl.isValide()) {
					return;
				}
				
				operationPnl.getBean().save();
				operationTablePane.insertBean(operationPnl.getBean());
				operationPnl.setBean(new Operation());
			}
		});
		JPopupMenu menu=new JPopupMenu();
		menu.add(new AbstractAction("修改") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				operationPnl.setBean(operationTablePane.getSelectBean());
				
			}
		});
		menu.add(new AbstractAction("添加设备") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Equipment equipment=new Equipment();
				equipment.setOperation(operationTablePane.getSelectBean());
				editEquipment(equipment);
				
			}

			
		});
		operationTablePane.setPopupMenu(menu);
		operationTablePane.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					equipmentTablePane.setBeans(operationTablePane.getSelectBean().getEquipments());
				}

			}

		});
		
		menu=new JPopupMenu();
		menu.add(new AbstractAction("修改设备") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Equipment equipment= equipmentTablePane.getSelectBean();
				if(equipment==null)JOptionPane.showMessageDialog(null, "请先选中你要修改的设备");
				editEquipment(equipment);
			}

			
		});
		equipmentTablePane.setPopupMenu(menu);
		
	}
	private void editEquipment(Equipment equipment) {
		EquipmentPnl equipmentPnl=new EquipmentPnl(equipment);
		BeanDialog<Equipment> dialog=new BeanDialog<Equipment>(equipmentPnl,"添加设备") {

			@Override
			public boolean okButtonAction() {
				BeanMao.saveBean(getBean());
				equipmentTablePane.insertBean(getBean());
				return true;
			}
		};
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
