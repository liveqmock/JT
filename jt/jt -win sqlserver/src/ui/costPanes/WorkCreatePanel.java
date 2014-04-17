package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;

import ui.customComponet.BeanTablePane;
import ui.panels.PicShowPnl;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.PicPlan;

public class WorkCreatePanel extends PicShowPnl {

	private BeanTablePane<PicBean> picTable;
	private OperarionWorksPnl operarionWorksPnl;
	private JSplitPane splitPane;
	private BeanTablePane<PicPlan> plansTablePane;

	public WorkCreatePanel() {
		super();

		splitPane=new JSplitPane();
		picTable=new BeanTablePane(null, PicBean.class);

		splitPane.setLeftComponent(picTable);
		operarionWorksPnl=new OperarionWorksPnl(null);
		operarionWorksPnl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "工序管理明细", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		plansTablePane=new BeanTablePane<PicPlan>(null, PicPlan.class);
		plansTablePane.setPreferredSize(new Dimension(400, 150));
		
		JPanel plansPanel=new JPanel();
		plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));
		plansTablePane.setBorder(new TitledBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED), "排产计划表", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		plansPanel.add(plansTablePane);
		plansPanel.add(operarionWorksPnl);

		splitPane.setRightComponent(plansPanel);
		splitPane.setDividerLocation(400);
		add(splitPane,BorderLayout.CENTER);
		picTable.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					billItemSelectAction();
				}

			}

		});
		plansTablePane.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					planItemSelectAction();
				}

			}

		});
		
	}

	@Override
	public void searchAction(String search) {
		try{
			if(StringUtils.isNotBlank(search)){
				search+=" and plans.size>0 ";
			}else {

				search+=" plans.size>0";
			}
			picTable.setBeans(BillBean.SearchPics(search));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void billItemSelectAction() {
		PicBean picBean = picTable.getSelectBean();
		if(picBean==null) return;
		Collection<PicPlan> plans = picBean.getPlans();
		plansTablePane.setBeans( plans);	
		if(plans!=null&&plans.size()>0)
			operarionWorksPnl.setPlan(plans.iterator().next());
		
	}
	public void planItemSelectAction() {
		PicPlan plan = plansTablePane.getSelectBean();

		operarionWorksPnl.setPlan(plan);

	}
	
}
