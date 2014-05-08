package ui.panels;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.tables.BillTable;
import ui.tables.PicTable;

import com.mao.jf.beans.BillBean;

public class BillManagerPnl extends BillShowPnl {

	private BillTable table;
	private PicTable picTable;
	private PicView imageView;

	public BillManagerPnl() {


		table = new BillTable(null);
		picTable = new PicTable(null);


		table.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		picTable.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				table, picTable);
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
					picTable.setBeans(table.getSelectBean().getPicBeans());
					if(picTable.getTable().getRowCount()>0)
						picTable.getTable().setRowSelectionInterval(0,0);
				}

			}
		});
		picTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(picTable.getSelectBean()!=null)
					imageView.showFile(picTable.getSelectBean().getImageUrl());
			}
		});
	}


	public void saveTableStatus() {
		table.saveStatus();
//		picTable.saveStatus();
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
		return picTable;
	}


}