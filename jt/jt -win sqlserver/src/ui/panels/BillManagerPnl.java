package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXList;

import ui.tables.BillTable;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Custom;

public class BillManagerPnl extends BillShowPnl implements ListSelectionListener{

	private BillTable table;
	private MyImageView imageView;
	
	private JSplitPane splitPane;

	
	public BillManagerPnl() {

		
		table = new BillTable(null);
		table.getTable().getSelectionModel().addListSelectionListener(this);
		imageView = new MyImageView();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				table, imageView);
		splitPane.setOneTouchExpandable(true);
		add(splitPane,BorderLayout.CENTER);
		splitPane.setDividerLocation(((Double) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()).intValue() - 500);		
	}

	
	public void saveTableStatus() {
		table.saveStatus();
	}
	
	


	public BillTable getTable() {
		return table;
	}


	@Override
	public void searchAction(String search) {
		
		List bills = Bill.loadBySearch(search);
		table.setBeans((Collection<Bill>) bills);
	}
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
}