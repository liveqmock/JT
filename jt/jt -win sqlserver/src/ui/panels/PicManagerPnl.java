package ui.panels;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.menu.PicPopmenu;
import ui.tables.PicTable;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.PicBean;

public class PicManagerPnl extends PicShowPnl implements ListSelectionListener{

	private PicTable table;
	private MyImageView imageView;
	
	private JSplitPane splitPane;

	
	public PicManagerPnl() {

		
		table = new PicTable(null);
		table.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setPopupMenu(new PicPopmenu(table));
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
	
	


	public PicTable getTable() {
		return table;
	}


	@Override
	public void searchAction(String search) {	
		try{
		List<PicBean> beans = BillBean.SearchPics(search);
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