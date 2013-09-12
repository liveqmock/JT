package windows.costPanes;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.Box;

import org.jdesktop.swingx.JXDatePicker;

import com.mao.jf.beans.SessionData;

import windows.customComponet.RsTablePane;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WorkCostPnl extends JPanel {
	private static String sql="select a.name 工序名称,a.工作时间,a.费用,b.调机时间,b.调机费用  from ("+
					"select c.name ,sum(a.worktime) 工作时间,cast(sum(a.worktime*b.wage) as decimal(18,2)) 费用 from operationwork a left join employee b on a.employee=b.id join operationplan c on a.operationplan=c.id where c.name like ?  and finishdate between ? and ?  group by c.name) a join ("+
					"select c.name ,sum(a.preparetime) 调机时间,cast(sum(a.preparetime*b.wage) as decimal(18,2)) 调机费用 from operationwork a left join employee b on a.prepareemployee=b.id join operationplan c on a.operationplan=c.id where c.name like ?  and finishdate between ? and ? group by c.name) b on a.name=b.name";

	private JXDatePicker sDate;
	private JTable table;
	private RsTablePane tablePane;
	private JTextField name;

	private JXDatePicker eDate;


	/**
	 * Create the panel.
	 */
	public WorkCostPnl() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label = new JLabel("工序名称：");
		panel.add(label);

		JLabel label_1 = new JLabel("日期：");
		panel.add(label_1);

		name = new JTextField();
		panel.add(name);
		name.setMinimumSize(new Dimension(60, 20));
		name.setColumns(30);
		name.setMaximumSize(new Dimension(100, 40));
		JLabel label_2 = new JLabel("\u65E5\u671F\uFF1A");
		panel.add(label_2);

		sDate = new JXDatePicker();
		panel.add(sDate);

		JLabel label_3 = new JLabel("\u81F3");
		panel.add(label_3);

		eDate = new JXDatePicker(new Date());
		panel.add(eDate);

		Component horizontalStrut = Box.createHorizontalStrut(30);
		panel.add(horizontalStrut);
		sDate.setFormats("yyyy年MM月dd日");
		eDate.setFormats("yyyy年MM月dd日");
		JButton btnNewButton_2 = new JButton("\u641C\u7D22(S)");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sDate.getDate()==null&&eDate.getDate()==null) JOptionPane.showMessageDialog(WorkCostPnl.this, "必须输入日期");
				try(PreparedStatement pst=SessionData.getConnection().prepareStatement(sql)){
					pst.setString(1, "%"+name.getText()+"%");
					pst.setDate(2, new java.sql.Date(sDate.getDate().getTime()));
					pst.setDate(3, new java.sql.Date(eDate.getDate().getTime()));
					pst.setString(4, "%"+name.getText()+"%");
					pst.setDate(5, new java.sql.Date(sDate.getDate().getTime()));
					pst.setDate(6, new java.sql.Date(eDate.getDate().getTime()));
					RowSetFactory rowSetFactory = RowSetProvider.newFactory();
					CachedRowSet crs = rowSetFactory.createCachedRowSet();
					crs.populate(pst.executeQuery());
					tablePane.setRs(crs);
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setMnemonic('s');
		panel.add(btnNewButton_2);

		tablePane= new RsTablePane(null,"员工产出统计");
		add(tablePane, BorderLayout.CENTER);

	}

}
