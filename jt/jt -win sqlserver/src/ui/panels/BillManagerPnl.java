package ui.panels;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.tables.BillTable;

import com.mao.jf.beans.Bill;

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
		try{
		List<Bill> beans = Bill.loadBySearch(search);
		table.setBeans(beans);
		}catch(Exception e){
			e.printStackTrace();
		}
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