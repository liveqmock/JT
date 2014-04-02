package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class SqlTablePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXTable table;
	private JPopupMenu popupMenu;

	/**
	 * @return the popupMenu
	 */
	public JPopupMenu getPopupMenu() {
		if (this.popupMenu == null)
			this.popupMenu = new JPopupMenu();
		return this.popupMenu;
	}

	/**
	 * @param popupMenu
	 *            the popupMenu to set
	 */
	public void setPopupMenu(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public SqlTablePane() {
		super();
		table = new JXTable(new SqlTableModel(null));
		table.setHorizontalScrollEnabled(true);
		table.setHighlighters(HighlighterFactory.createAlternateStriping());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnControlVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (SqlTablePane.this.popupMenu == null)
					return;
				if (e.isPopupTrigger()) {
					int row = table.rowAtPoint(e.getPoint());
					if (row >= 0) {
						table.setRowSelectionInterval(row, row);
						SqlTablePane.this.popupMenu.show(e.getComponent(),
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
		table.setGridColor(Color.gray);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
	}

	public void dbClick() {

	}

	// /* (non-Javadoc)
	// * @see
	// java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	// */
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// if (e.getClickCount() == 2 && table.getSelectedRow()>=0){
	//
	// }
	// }
	// });

	/**
	 * @return the table
	 */
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

	public void removeSelectRow() throws SQLException {
		((SqlTableModel) getTable().getModel()).removeRow(getTable()
				.convertRowIndexToModel(getTable().getSelectedRow()));
	}

	public void setRowset(RowSet rowSet) {
		((SqlTableModel) getTable().getModel()).setRowSet(rowSet);
	}

}
