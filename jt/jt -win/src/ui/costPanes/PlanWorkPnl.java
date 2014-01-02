package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.customComponet.BeanTablePane;
import ui.customComponet.RsTablePane;
import ui.panels.SearchDlg;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Plan;
import com.mao.jf.beans.SessionData;

public class PlanWorkPnl extends JPanel {

	/**
	 * Create the panel.
	 */
	private BeanTablePane<Bill> billTable=new BeanTablePane<>(null,Bill.class);
	private BeanTablePane<Plan> planTable=new BeanTablePane<>(null,Plan.class);
	private RsTablePane operationPlanPane=new RsTablePane(null, "");
	private final JPanel panel = new JPanel();
	private final JButton button = new JButton("订单查询");
	public PlanWorkPnl() {

		createContents();
	}
	private void createContents() {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JSplitPane splitPane_1 = new JSplitPane();

		splitPane.setLeftComponent(billTable);
		splitPane.setRightComponent(splitPane_1);

		splitPane_1.setLeftComponent(planTable);		
		splitPane_1.setRightComponent(operationPlanPane);

		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchDlg searchDlg=	new SearchDlg();
				searchDlg.setVisible(true);
				String sql="select a.* from bill a join plan b on a.id=b.bill where "+searchDlg.getSqlString();
//				System.err.println(sql);
				try {
					billTable.setBeans(Bill.loadAll(Bill.class, sql));
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException
						| IntrospectionException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}


			}
		});	

		billTable.setPreferredSize(new Dimension(200, 0));
		planTable.setPreferredSize(new Dimension(100, 0));

		billTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				planTable.setBeans(billTable.getSelectBean().getPlans());

			}
		});
		planTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String sql="select * from operationplan where plan="+planTable.getSelectBean().getId();
				try {
					operationPlanPane.setRs(SessionData.getRwoSet(sql));
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		});
	}

}
