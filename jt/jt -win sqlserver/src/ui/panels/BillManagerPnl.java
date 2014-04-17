package ui.panels;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.customComponet.BeanTablePane;
import ui.menu.BillPopMenu;
import ui.menu.PicPopmenu;
import ui.tables.BillTable;
import ui.tables.PicTable;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.PicBean;

public class BillManagerPnl extends BillShowPnl implements ListSelectionListener{

	private BillTable table;
	private BeanTablePane<PicBean> picBeanTablePane;
	
	private JSplitPane splitPane;

	
	public BillManagerPnl() {

		
		table = new BillTable(null);
		table.setPopupMenu(new BillPopMenu(table));
		table.getTable().getSelectionModel().addListSelectionListener(this);
		picBeanTablePane = new BeanTablePane<PicBean>(null,PicBean.class);
		picBeanTablePane.setPopupMenu(new PicPopmenu(picBeanTablePane));

		table.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		picBeanTablePane.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				table, picBeanTablePane);
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
		List<BillBean> beans = BillBean.SearchBeans(search);
		table.setBeans(beans);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void valueChanged(final ListSelectionEvent arg0) {
		if (arg0.getValueIsAdjusting()) {
					
						if (table.getSelectBean()!=null) {
							picBeanTablePane.setBeans(table.getSelectBean().getPicBeans());
						}
		}

	}


	public BeanTablePane<PicBean> getPicBeanTablePane() {
		return picBeanTablePane;
	}


	public void setPicBeanTablePane(BeanTablePane<PicBean> picBeanTablePane) {
		this.picBeanTablePane = picBeanTablePane;
	}
}