package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public abstract class BeansOPanel<T> extends BeanOPanel<Collection<T>> {
	private BeanTablePane<T> tablePane;
	private BeanOPanel<T> beanPanel;
	private String[] filterColumns;
	private boolean vertical=false;
	private Class<T> class1;
	public BeansOPanel() {
		super(null);
	}
	public BeansOPanel(Collection<T> beans, BeanOPanel<T> beanPanel,Class<T> class1) {
		this(beans, beanPanel,class1, null,false);
	}
	public BeansOPanel(Collection<T> beans, BeanOPanel<T> beanPanel,Class<T> class1,boolean vertical) {
		this(beans, beanPanel,class1, null,vertical);
	}
	public BeansOPanel(Collection<T> beans, BeanOPanel<T> beanPanel,Class<T> class1,
			String filterColumns[]) {
		this(beans, beanPanel,class1, filterColumns,false);
	}
	public BeansOPanel(Collection<T> beans, BeanOPanel<T> beanPanel,Class<T> class1,
			String filterColumns[],boolean vertical) {
		super(beans);
		this.beanPanel=beanPanel;
		this.filterColumns=filterColumns;
		this.vertical=vertical;
		this.class1=class1;
		createContents();
		setBean(beans);
	}
	@Override
	protected void createContents() {

		if(beanPanel==null)return;
		
		beanPanel.setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout(0, 0));
		tablePane = new BeanTablePane<T>(bean,class1, filterColumns);
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
		JButton submitBt= new JButton("提交(S)");
		submitBt.setMnemonic('s');
		JButton resetBt=new JButton("新建(N)");
		resetBt.setMnemonic('n');
		JPanel submitPnl = new JPanel();
		submitPnl.setLayout(new BoxLayout(submitPnl, BoxLayout.LINE_AXIS));
		submitPnl.add(resetBt);
		submitPnl.add(Box.createHorizontalStrut(40));
		submitPnl.add(submitBt);
		panel_1.add(submitPnl,BorderLayout.NORTH);
		submitBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addNew();

			}
		});
		resetBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reset();

			}

			
		});
		panel.add(this.beanPanel.getvPanel(),vertical? BorderLayout.NORTH:BorderLayout.CENTER);
		
		panel.add(panel_1, vertical?BorderLayout.CENTER:BorderLayout.SOUTH);

		tablePane.setPreferredSize(new Dimension(tablePane.getWidth(), 200));
		add(panel, vertical?BorderLayout.WEST:BorderLayout.NORTH);
		add(tablePane, BorderLayout.CENTER);
	}
	@Override
	protected void dataBinding() {
		
	}
	public void reEditRow() {
		beanPanel.setBean( tablePane.getSelectBean());
		
		
	}
	protected void addNew() {
		if (!beanPanel.isValide()) {
			return;
		}
		
		T newbean = saveBean();
		if (newbean != null) {
			tablePane.addNew(newbean);
			T newBean = createNewBean();
			setPanelBean(newBean);
			
		}
	}

	public void removeSelectRow() {
		tablePane.removeSelectRow();

	}

	/**
	 * 
	 * @return 生成新的bean加入到列表中
	 */
	public abstract T saveBean();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mao.beanAdapter.BeanPanel#setBean(java.lang.Object)
	 */
	@Override
	public void setBean(Collection<T> bean) {
		// TODO Auto-generated method stub
		super.setBean(bean);
		tablePane.setBeans(bean);
		T newBean = createNewBean();
		beanPanel.setBean(newBean);
		
	}
	public T createNewBean() {
		try {
			return class1.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
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
	public BeanOPanel<T> getBeanPanel() {
		return this.beanPanel;
	}

	/**
	 * @param beanPanel
	 *            the beanPanel to set
	 */
	public void setBeanOPanel(BeanOPanel<T> beanPanel) {
		this.beanPanel = beanPanel;
	}

	public BeanTablePane<T> getTablePane() {
		return tablePane;
	}

	public void setTablePane(BeanTablePane<T> tablePane) {
		this.tablePane = tablePane;
	}

	public void reset() {
		BeansOPanel.this.beanPanel.setBean(createNewBean());
		
	}
}
