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

public class BillManagerPnl extends BillShowPnl {

	private BillTable table;
	private PicTable picBeanTablePane;
	private PicView imageView;

	public BillManagerPnl() {


		table = new BillTable(null);
		table.setPopupMenu(new BillPopMenu(table));
		picBeanTablePane = new PicTable(null);

		picBeanTablePane.setPopupMenu(new PicPopmenu(picBeanTablePane));

		table.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		picBeanTablePane.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				table, picBeanTablePane);
		splitPaneV.setOneTouchExpandable(true);
		imageView=new PicView();
		JSplitPane splitPaneH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitPaneV,imageView);
		splitPaneV.setDividerLocation(500);
		splitPaneH.setDividerLocation(((Double) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()).intValue() - 500);	

		add(splitPaneH,BorderLayout.CENTER);	





		table.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectBean()!=null) {
					picBeanTablePane.setBeans(table.getSelectBean().getPicBeans());
					if(picBeanTablePane.getTable().getRowCount()>0)
						picBeanTablePane.getTable().setRowSelectionInterval(0,0);
				}

			}
		});
		picBeanTablePane.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(picBeanTablePane.getSelectBean()!=null)
					imageView.showFile(picBeanTablePane.getSelectBean().getImageUrl());
			}
		});
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



	public PicTable getPicBeanTablePane() {
		return picBeanTablePane;
	}


}