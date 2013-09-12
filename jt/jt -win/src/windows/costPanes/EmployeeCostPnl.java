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

public class EmployeeCostPnl extends JPanel {
	private static String sql="select a.name 姓名,操作时间,preparetime 调机时间 ,cast(wage as decimal(18,2)) \"工资/分\",cast(wages+工资 as decimal(18,2)) 工资,cast(产值 as decimal(18,2)) 产值,报废数量, 报废成本 from "+
								"(select b.wage,b.name,a.employee,sum(a.worktime) 操作时间,sum(a.worktime)*wage 工资,sum(a.worktime*c.cost) 产值,sum(a.scrapnum) 报废数量,SUM(e.reportprice*a.scrapnum) 报废成本 "+
										"from employee b left join operationwork  a on a.employee=b.id  join operationplan c on a.operationplan = c.id "+
								"join plan d on c.plan=d.id join bill e on d.bill =e.id  where b.name like ? and finishdate between ? and ? group by a.employee "+
								") a   join (select a.id employee,a.name, ifnull(sum(preparetime),0) preparetime,ifnull(sum(preparetime),0)*wage wages from employee a  left join operationwork b on b.prepareemployee =a.id and finishdate between ? and ?  group by a.id"
								+") as b on b.employee=a.employee  ";
	private JXDatePicker sDate;
	private RsTablePane tablePane;
	private JTextField name;

	private JXDatePicker eDate;


	/**
	 * Create the panel.
	 */
	public EmployeeCostPnl() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label = new JLabel("\u641C\u7D22\u6761\u4EF6\uFF1A");
		panel.add(label);

		JLabel label_1 = new JLabel("\u5458\u5DE5\uFF1A");
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
				if(sDate.getDate()==null&&eDate.getDate()==null) JOptionPane.showMessageDialog(EmployeeCostPnl.this, "必须输入日期");
				try(PreparedStatement pst=SessionData.getConnection().prepareStatement(sql)){
					pst.setString(1, "%"+name.getText()+"%");
					pst.setDate(2, new java.sql.Date(sDate.getDate().getTime()));
					pst.setDate(3, new java.sql.Date(eDate.getDate().getTime()));
					pst.setDate(4, new java.sql.Date(sDate.getDate().getTime()));
					pst.setDate(5, new java.sql.Date(eDate.getDate().getTime()));
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
