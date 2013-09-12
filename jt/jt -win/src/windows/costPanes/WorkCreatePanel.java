package windows.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.swingx.VerticalLayout;

import validation.ui.ValidationPanel;
import windows.customComponet.BeanTablePane;

import com.mao.jf.beans.OperationWork;
import com.mao.jf.beans.Plan;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WorkCreatePanel extends JPanel {
	private WorkTable table;
	private BeanTablePane<OperationWork> workTablePane;
	private OperationWorkPnl workPnl=new OperationWorkPnl(new OperationWork());

	public WorkCreatePanel() {
		createContents();
	}
	protected void createContents() {
		setLayout(new BorderLayout(0, 0));

		workTablePane = new BeanTablePane<>(null);
		
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.6);
		add(splitPane, BorderLayout.CENTER);
		
		JScrollPane plan = new JScrollPane();
		table=new WorkTable(Plan.loadUnCompleted());
		plan.setViewportView(table);
		
		splitPane.setLeftComponent(plan);
		ValidationPanel validationPanel=new ValidationPanel();
		validationPanel.setInnerComponent(workPnl);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JButton okBt= new JButton("\u786E\u5B9A\uFF08O\uFF09");
		okBt.setMnemonic('o');
		panel_3.add(okBt);
		
		JPanel panel_1 = new JPanel();

		panel_1.setPreferredSize(new Dimension(500, 480));
		splitPane.setRightComponent(panel_1);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(validationPanel, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(103)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(workTablePane, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(validationPanel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(workTablePane, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		okBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!workPnl.isValide()) {
					return;
				}
				try {
					workPnl.getBean().save();
					workTablePane.addNew(workPnl.getBean());
					workPnl.setBean(new OperationWork());
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException
						| SecurityException | IntrospectionException
						| SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}


			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				workPnl.setOperationPlans(table.getSelectPlan().getOperationPlans());
				workTablePane.setBeans(table.getSelectPlan().getOperationWorks());

			}
		});
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				switch (item.getText()) {
				case "删除":

					workTablePane.removeSelectRow();

					break;
				case "修改":

						try {
							workPnl.setBean((OperationWork) BeanUtils.cloneBean( workTablePane.getSelectBean()));
						} catch (IllegalAccessException | InstantiationException
								| InvocationTargetException | NoSuchMethodException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}

						break;

				default:
					break;
				}
			}

		};
		workTablePane.getPopupMenu().add("修改").addActionListener(listener);
		workTablePane.getPopupMenu().add("删除").addActionListener(listener);
	}



}
