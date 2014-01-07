package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import ui.customComponet.RsTablePane;

import com.mao.jf.beans.SessionData;

public class EmployeeCostPnl extends JPanel {
	private static String sql="select a.name ����,����ʱ��,preparetime ����ʱ�� ,cast(wage as decimal(18,2)) \"����/��\",cast(isnull(wages,0)+isnull(����,0) as decimal(18,2)) ����,cast(��ֵ as decimal(18,2)) ��ֵ,��������, ���ϳɱ� from "+
								"(select b.wage,b.name,a.employee,sum(a.worktime) ����ʱ��,sum(a.worktime)*wage ����,sum(a.worktime*c.cost) ��ֵ,sum(a.scrapnum) ��������,SUM(e.reportprice*a.scrapnum) ���ϳɱ� "+
										"from employee b left join operationwork  a on a.employee=b.id  join operationplan c on a.operationplan = c.id "+
								"join \"plan\" d on c.\"plan\"=d.id join bill e on d.bill =e.id  where b.name like ? and finishdate between ? and ? group by a.employee,wage,b.name "+
								") a  full  join (select a.id employee, sum(preparetime) preparetime,sum(preparetime)*wage wages from employee a  left join operationwork b on b.prepareemployee =a.id  where a.name like ? and finishdate between ? and ?  group by a.id,wage"
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
		sDate.setFormats("yyyy��MM��dd��");
		eDate.setFormats("yyyy��MM��dd��");
		JButton btnNewButton_2 = new JButton("\u641C\u7D22(S)");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sDate.getDate()==null&&eDate.getDate()==null) JOptionPane.showMessageDialog(EmployeeCostPnl.this, "������������");
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
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setMnemonic('s');
		panel.add(btnNewButton_2);

		tablePane= new RsTablePane(null,"Ա������ͳ��");
		add(tablePane, BorderLayout.CENTER);

	}

}
