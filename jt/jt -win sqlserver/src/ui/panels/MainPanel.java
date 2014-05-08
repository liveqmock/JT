package ui.panels;

import javax.swing.JTabbedPane;

public class MainPanel extends JTabbedPane{

	private BillManagerPnl billManagerPnl;
	private PicManagerPnl picManagerPnl;

	public MainPanel() {
		super();
		 billManagerPnl= new BillManagerPnl();
		 picManagerPnl=new PicManagerPnl();
		add("按图纸查找", picManagerPnl);
		add("按订单查找",billManagerPnl);
	}

	public void saveTableStatus() {
		billManagerPnl.saveTableStatus();
		picManagerPnl.saveTableStatus();
		
	}
	
}
