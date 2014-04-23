package ui.panels;

import javax.swing.JTabbedPane;

public class MainPanel extends JTabbedPane{

	private BillManagerPnl billManagerPnl;
	private PicManagerPnl picManagerPnl;

	public MainPanel() {
		super();
		 billManagerPnl= new BillManagerPnl();
		 picManagerPnl=new PicManagerPnl();
		add("����������",billManagerPnl);
		add("��ͼֽ����", picManagerPnl);
	}

	public void saveTableStatus() {
		billManagerPnl.saveTableStatus();
		picManagerPnl.saveTableStatus();
		
	}
	
}
