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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Component;

public class WorkCreatePanel extends JPanel {
	private WorkTable table;
	private BeanTablePane<OperationWork> workTablePane;
	private OperationWorkPnl workPnl=new OperationWorkPnl(new OperationWork());
	private JTextField textField;

	public WorkCreatePanel() {
		createContents();
	}
	protected void createContents() {
		setLayout(new BorderLayout(0, 0));

		workTablePane = new BeanTablePane<>(null);


		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout(0, 0));

		JButton okBt= new JButton("\u786E\u5B9A\uFF08O\uFF09");
		okBt.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		okBt.setAlignmentX(Component.RIGHT_ALIGNMENT);
		okBt.setMnemonic('o');
		panel_3.add(okBt, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();

		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(panel_3, BorderLayout.NORTH);
		ValidationPanel validationPanel=new ValidationPanel();
		panel_3.add(validationPanel);
		validationPanel.setInnerComponent(workPnl);
		panel_1.add(workTablePane);

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(100,0));
		splitPane.setLeftComponent(panel);		
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane plan = new JScrollPane();
		panel.add(plan, BorderLayout.CENTER);
		table=new WorkTable(Plan.loadUnCompleted());
		plan.setViewportView(table);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel label = new JLabel("\u56FE\u53F7\uFF1A");
		panel_2.add(label);

		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);

		JButton searchBt = new JButton("\u67E5\u627E");
		panel_2.add(searchBt);
		searchBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.changeData(Plan.loadUnCompletedBySearch(textField.getText()));
				
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				workPnl.setOperationPlans(table.getSelectPlan().getOperationPlans());
				workTablePane.setBeans(table.getSelectPlan().getOperationWorks());

			}
		});
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

		splitPane.setDividerLocation(0.6);
		splitPane.setDividerLocation(500);
	}



}
