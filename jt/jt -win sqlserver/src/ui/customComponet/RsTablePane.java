package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class RsTablePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXTable table;

	private CachedRowSet rs;
	private RsTableModel model;

	public RsTablePane(CachedRowSet rs, final String title) {
		this.rs = rs;
		model=new RsTableModel(rs);
		table = new JXTable(model);
		table.setHorizontalScrollEnabled(true);
		table.setHighlighters(HighlighterFactory.createAlternateStriping());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnControlVisible(true);

		table.setGridColor(Color.gray);
		table.setShowGrid(true);
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_ALL_COLUMNS);
		table.packTable(10);
		Dimension aaa = new Dimension(300, 300);
		aaa.width = table.getPreferredScrollableViewportSize().width;
		aaa.height = table.getPreferredScrollableViewportSize().height;
		if (aaa.width < 500)
			aaa.width = 500;
		if (aaa.height < 500)
			aaa.height = 500;

		setPreferredSize(aaa);
		setLayout(new BorderLayout(0, 0));
		table.packAll();
		table.setHorizontalScrollEnabled(true);
		JScrollPane jScrollPane = new JScrollPane(table);
		add(jScrollPane, BorderLayout.CENTER);

		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		panel.add(new JButton(new AbstractAction("导出") {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					((RsTableModel) table.getModel()).exportToExl(title);
				} catch ( SQLException
						| IllegalArgumentException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}));
		add(panel, BorderLayout.SOUTH);
	}
	public void export(String title) {
		 try {
			model.exportToExl(title);
		} catch (IOException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public JXTable getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(JXTable table) {
		this.table = table;
	}

	public CachedRowSet getRs() {
		return rs;
	}

	public void setRs(CachedRowSet rs) {
		this.rs = rs;
		model.setRs(rs);
		
	}


}
