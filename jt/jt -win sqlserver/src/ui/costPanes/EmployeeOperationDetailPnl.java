package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import ui.customComponet.RsTablePane;
import ui.panels.MyImageView;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Employee;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.SessionData;

public class EmployeeOperationDetailPnl extends JPanel {
	private static String sql="select b.name 员工,e.id,e.custom 客户,e.picid 图号,c.name 工序,finishdate 完成日期,a.worktime 操作时间, a.worktime*wage 工资,a.worktime*c.cost 产值,a.scrapnum 报废数量, e.reportprice*a.scrapnum 报废成本 	from employee b left join operationwork   a on a.employee=b.id  join operationplan c on a.operationplan = c.id 	join  billplan d on c.billplan=d.id join bill e on d.bill =e.id   where b.name like ?  and finishdate between ? and ?  union select  a.name,e.id,e.custom 客户,e.picid,c.name+'_调机' oper,finishdate,b.worktime 操作时间, b.worktime*wage 工资,b.worktime*c.cost 产值,null,null from  employee a  left join operationwork b on b.prepareemployee =a.id join operationplan  c on b.operationplan = c.id join  billplan d on c.billplan=d.id join bill e on d.bill =e.id  where a.name like ? and finishdate between ?  and ?";
	private JXDatePicker sDate;
	private RsTablePane tablePane;
	private JComboBox<String> name;
	private JPopupMenu popupMenu;
	private JXDatePicker eDate;


	/**
	 * Create the panel.
	 */
	public EmployeeOperationDetailPnl(String nameStr,Date startDate,Date endDate) {

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label = new JLabel("\u641C\u7D22\u6761\u4EF6\uFF1A");
		panel.add(label);

		JLabel label_1 = new JLabel("\u5458\u5DE5\uFF1A");
		panel.add(label_1);

		name = new JComboBox<>(Employee.getNames());
		panel.add(name);
//		name.setMinimumSize(new Dimension(60, 20));
//		name.setMaximumSize(new Dimension(100, 40));
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
		JButton searchBt = new JButton("搜索(S)");
		searchBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}


		});
		searchBt.setMnemonic('s');
		panel.add(searchBt);

		tablePane= new RsTablePane(null,"员工产出明细");
		add(tablePane, BorderLayout.CENTER);
		final JXTable table = tablePane.getTable();
		popupMenu=new JPopupMenu();
		popupMenu.add(new AbstractAction("查看图纸"){

			@Override
			public void actionPerformed(ActionEvent e) {
				dbClick();

			}

		});
		tablePane.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = table.rowAtPoint(e.getPoint());
					if (row >= 0) {
						table.setRowSelectionInterval(row, row);
						popupMenu.show(e.getComponent(),
								e.getX(), e.getY());
					}
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent
			 * )
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
					dbClick();
				}
			}


		});
		

		name.setSelectedItem(nameStr);
		sDate.setDate(startDate);
		eDate.setDate(endDate);
		search();
	}
	private void search() {
		if(sDate.getDate()==null||eDate.getDate()==null) JOptionPane.showMessageDialog(EmployeeOperationDetailPnl.this, "必须输入日期");
		try(PreparedStatement pst=SessionData.getConnection().prepareStatement(sql)){
			pst.setString(1, "%"+name.getSelectedItem()+"%");
			pst.setDate(2, new java.sql.Date(sDate.getDate().getTime()));
			pst.setDate(3, new java.sql.Date(eDate.getDate().getTime()));
			pst.setString(4, "%"+name.getSelectedItem()+"%");
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
	private void dbClick() {
		int picId=(int) tablePane.getTable().getValueAt(tablePane.getTable().getSelectedRow(), 1);
		PicBean pic=BeanMao.beanManager.find(PicBean.class, picId);
		if(StringUtils.isNoneBlank(pic.getImageUrl())){
			
			JDialog dialog=new JDialog();
			MyImageView imageView=new MyImageView();
			dialog.setContentPane(imageView);
			dialog.setLocationRelativeTo(null);
			dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

			imageView.showFile(pic.getImageUrl());
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setVisible(true);
			
		}
	}
}
