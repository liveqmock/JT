package ui.customComponet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;

public abstract class BeansPanel<T> extends BeanPanel<Vector<T>> {
	private BeanTablePane<T> tablePane;
	private BeanPanel<T> beanPanel;
	private String[] filterColumns;
	private boolean vertical=false;
	private Class<T> class1;
	public BeansPanel(Vector<T> beans, BeanPanel<T> beanPanel,Class<T> class1) {
		this(beans, beanPanel,class1, null,false);
	}
	public BeansPanel(Vector<T> beans, BeanPanel<T> beanPanel,Class<T> class1,boolean vertical) {
		this(beans, beanPanel,class1, null,vertical);
	}
	public BeansPanel(Vector<T> beans, BeanPanel<T> beanPanel,Class<T> class1,
			String filterColumns[]) {
		this(beans, beanPanel,class1, filterColumns,false);
	}
	public BeansPanel(Vector<T> beans, BeanPanel<T> beanPanel,Class<T> class1,
			String filterColumns[],boolean vertical) {
		super(beans,1);
		this.beanPanel=beanPanel;
		this.filterColumns=filterColumns;
		this.vertical=vertical;
		this.class1=class1;
		createContents();
	}
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
		JButton resetBt=new JButton("重置(R)");
		resetBt.setMnemonic('r');
		JPanel submitPnl = new JPanel();
		submitPnl.setLayout(new BoxLayout(submitPnl, BoxLayout.LINE_AXIS));
		submitPnl.add(resetBt);
		submitPnl.add(Box.createHorizontalStrut(40));
		submitPnl.add(submitBt);
		panel_1.add(submitPnl,BorderLayout.NORTH);
		submitBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNew();

			}
		});
		resetBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BeansPanel.this.beanPanel.setBean((T) beanPanel.getBean().getClass().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
		});
		panel.add(this.beanPanel.getvPanel(),vertical? BorderLayout.NORTH:BorderLayout.CENTER);
		
		panel.add(panel_1, vertical?BorderLayout.CENTER:BorderLayout.SOUTH);

		tablePane.setPreferredSize(new Dimension(tablePane.getWidth(), 200));
		add(panel, vertical?BorderLayout.WEST:BorderLayout.NORTH);
		add(tablePane, BorderLayout.CENTER);
	}
	protected void dataBinding() {
		
	}
	public void reEditRow() {
		try {
			beanPanel.setBean((T) BeanUtils.cloneBean( tablePane.getSelectBean()));
		} catch (IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public void addNew() {
		if (!beanPanel.isValide()) {
			return;
		}
		T newbean = saveBean();
		if (newbean != null) {
			tablePane.addNew(newbean);
			try {
				setPanelBean((T) newbean.getClass().newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
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
