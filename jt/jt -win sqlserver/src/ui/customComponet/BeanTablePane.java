package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class BeanTablePane<T> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JXTable table;
	private JPopupMenu popupMenu;
	private String filterColoums[];


	public BeanTablePane(List<T> beans,Class<T> class1) {
		this(beans,class1, null);
	}
	public BeanTablePane(List<T> beans,Class<T> class1,String[] headers) {
		this(beans,class1, headers,null);
	}

	public BeanTablePane(List<T> beans,Class<T> class1,String[] headers, String filterColoums[]) {
		super();
		this.beans = beans;
		this.filterColoums = filterColoums;
		table = new JXTable(new BeanTableModel<>(beans,class1,headers));
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
				if (BeanTablePane.this.popupMenu == null)
					return;
				if (e.isPopupTrigger()) {
					int row = table.rowAtPoint(e.getPoint());
					if (row >= 0) {
						table.setRowSelectionInterval(row, row);
						BeanTablePane.this.popupMenu.show(e.getComponent(),
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
		Dimension aaa = new Dimension(300, 300);
		aaa.width = table.getPreferredScrollableViewportSize().width;
		aaa.height = table.getPreferredScrollableViewportSize().height;
		if (aaa.width < 500)
			aaa.width = 500;
		if (aaa.height < 500)
			aaa.height = 500;

		setPreferredSize(aaa);
		setLayout(new BorderLayout(0, 0));
		table.setHorizontalScrollEnabled(true);
		table.setHighlighters(HighlighterFactory.createAlternateStriping());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnControlVisible(true);
		table.setGridColor(Color.gray);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);		
		table.setHorizontalScrollEnabled(true);
		JScrollPane jScrollPane = new JScrollPane(table);
		add(jScrollPane, BorderLayout.CENTER);
		setFilter();
		
		table.packTable(10);
		table.packAll();
	}

	public void setFilter(String filterColoums[]) {
		this.filterColoums = filterColoums;
		setFilter();
	}

	public void setFilter() {
		if (filterColoums != null && filterColoums.length > 0
				&& beans.size() > 0) {

			BeanTableModelHeader[] heads = ((BeanTableModel<T>) table.getModel())
					.getHeads();

			if (heads.length > 0) {
				if (filterColoums != null || filterColoums.length > 0) {
					final JPanel panel = new JPanel();
					panel.setLayout(new FlowLayout(FlowLayout.LEADING));

					panel.add(new JLabel("过滤条件："));
					final List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(
							filterColoums.length);
					for (int i = 0; i < filterColoums.length; i++) {

						for (int c = 0; c < heads.length; c++) {
							if (heads[c].equals(filterColoums[i])) {
								TreeSet<String> colValues = new TreeSet<>();
								colValues.add("");
								for (T t : beans) {
									try {
										Object object =PropertyUtils.getSimpleProperty(t, heads[c].getField()); 
										if (object != null)
											colValues.add(object.toString());

									} catch (IllegalAccessException
											| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {

									}
								}
								final JComboBox<String> comboBox = new JComboBox<String>(
										new Vector<String>(colValues));
								final int p = i, cc = c;
								filters.add(RowFilter.regexFilter("", c));
								comboBox.addItemListener(new ItemListener() {
									@Override
									public void itemStateChanged(
											final ItemEvent e) {
										filters.set(p, RowFilter.regexFilter(e
												.getItem().toString(), cc));
										table.setRowFilter(RowFilter
												.andFilter(filters));

									}
								});
								panel.add(new JLabel(filterColoums[i] + ":"));
								panel.add(comboBox);
								panel.add(Box.createHorizontalStrut(20));
							}
						}
					}
					panel.add(new JButton(new AbstractAction("导出") {

						@SuppressWarnings("unchecked")
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								((BeanTableModel<T>) table.getModel())
										.exportToExl(null);
							} catch ( IllegalArgumentException
									| IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					}));
					JScrollPane scrollPane = new JScrollPane(panel);
					scrollPane.setPreferredSize(new Dimension(100, 60));
					add(scrollPane, BorderLayout.NORTH);
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public T getSelectBean() {
		try {
			return ((BeanTableModel<T>) table.getModel()).getSelectBean(table
					.convertRowIndexToModel(table.getSelectedRow()));
		} catch (Exception e) {
			return null;
		}
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

	/**
	 * @return the beans
	 */
	public List<T> getBeans() {
		return beans;
	}

	@SuppressWarnings("unchecked")
	public void removeSelectRow() {
		((BeanTableModel<T>) getTable().getModel()).removeRow(getTable()
				.convertRowIndexToModel(getTable().getSelectedRow()));
	}

	@SuppressWarnings("unchecked")
	public void addNew(T t) {
		((BeanTableModel<T>) getTable().getModel()).insertRow(t);
	}

	/**
	 * @param beans
	 *            the beans to set
	 */
	@SuppressWarnings("unchecked")
	public void setBeans(List<T> beans) {
		this.beans = beans;
		((BeanTableModel<T>) table.getModel()).setBeans(beans);
		table.packTable(10);
		
	}
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

	private List<T> beans;

}
