package windows.customComponet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public abstract class BeansPanelV<T> extends BeanPanel<Vector<T>> {
	private BeanTablePane<T> tablePane;
	private BeanPanel<T> beanPanel;
	private String[] filterColumns;

	public BeansPanelV(Vector<T> beans, BeanPanel<T> beanPanel) {
		this(beans, beanPanel, null);
	}

	public BeansPanelV(Vector<T> beans, BeanPanel<T> beanPanel,
			String filterColumns[]) {
		super(beans,1);
		this.beanPanel=beanPanel;
		this.filterColumns=filterColumns;
		createContents();
	}
	protected void createContents() {

		if(beanPanel==null)return;
		
		beanPanel.setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout(0, 0));
		tablePane = new BeanTablePane<T>(bean, filterColumns);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				switch (item.getText()) {
				case "删除":

					removeSelectRow();

					break;
				case "修改":

						reEditRow();

						break;

				default:
					break;
				}
			}

		};
		tablePane.getPopupMenu().add("修改").addActionListener(listener);
		tablePane.getPopupMenu().add("删除").addActionListener(listener);
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(this.beanPanel.getvPanel(), BorderLayout.CENTER);
		panel.add(panel_1, BorderLayout.EAST);

		JButton btnNewButton = new JButton("\u6DFB\u52A0(A)");
		btnNewButton.setMnemonic('a');
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNew();

			}
		});
		tablePane.setPreferredSize(new Dimension(tablePane.getWidth(), 200));
		add(panel, BorderLayout.NORTH);
		add(tablePane, BorderLayout.CENTER);
	}
	public void reEditRow() {
		beanPanel.setBean(tablePane.getSelectBean());
		
	}
	public void addNew() {
		if (!beanPanel.isValide()) {
			return;
		}
		T newbean = getNewBean();
		if (newbean != null) {
			tablePane.addNew(newbean);
		}
	}

	public void removeSelectRow() {
		tablePane.removeSelectRow();

	}

	/**
	 * 
	 * @return 生成新的bean加入到列表中
	 */
	public abstract T getNewBean();
	protected abstract void dataBinding();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mao.beanAdapter.BeanPanel#setBean(java.lang.Object)
	 */
	@Override
	public void setBean(Vector<T> bean) {
		// TODO Auto-generated method stub
		super.setBean(bean);
		tablePane.setBeans(bean);
	}

	public void setPanelBean(T t) {
		this.beanPanel.setBean(t);
	}

	public T getPanelBean() {
		return this.beanPanel.getBean();
	}

	/**
	 * @return the beanPanel
	 */
	public BeanPanel<T> getBeanPanel() {
		return this.beanPanel;
	}

	/**
	 * @param beanPanel
	 *            the beanPanel to set
	 */
	public void setBeanPanel(BeanPanel<T> beanPanel) {
		this.beanPanel = beanPanel;
	}

	public BeanTablePane<T> getTablePane() {
		return tablePane;
	}

	public void setTablePane(BeanTablePane<T> tablePane) {
		this.tablePane = tablePane;
	}

	/**
	 * Create the panel.
	 */
}
