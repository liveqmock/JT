package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;

import ui.customComponet.BeanTableModel;
import ui.customComponet.BeanTablePane;
import ui.panels.PicShowPnl;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.PicPlan;

public class PlanCreatePanel extends PicShowPnl {

	private BeanTablePane<PicBean> picTable;
	private PlanPnl planPnl;
	private JSplitPane splitPane;
	private BeanTablePane<PicPlan> plansTablePane;

	public PlanCreatePanel() {
		super();

		splitPane=new JSplitPane();

		picTable=new BeanTablePane<PicBean>(null, PicBean.class);

		splitPane.setLeftComponent(picTable);
		planPnl=new PlanPnl(new PicPlan(new PicBean()));
		plansTablePane=new BeanTablePane<PicPlan>(null, PicPlan.class);
		plansTablePane.setPreferredSize(new Dimension(400, 150));
		
		JButton newPlanBt = new JButton("�����Ų��ƻ�");
		JPanel plansPanel=new JPanel();
		plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));
		plansTablePane.setBorder(new TitledBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED), "�Ų��ƻ���", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		plansPanel.add(plansTablePane);
		plansPanel.add(newPlanBt);
		plansPanel.add(planPnl);

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
		newPlanBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewPlan();
			}

			
		});
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				switch (item.getText()) {
				case "ɾ���Ų��ƻ�":

					removeSelectRow();

					break;
				case "�����Ų��ƻ�":

					addNewPlan();

					break;

				default:
					break;
				}
			}

			

		};
		plansTablePane.getPopupMenu().add("ɾ���Ų��ƻ�").addActionListener(listener);
		plansTablePane.getPopupMenu().add("�½��Ų��ƻ�").addActionListener(listener);
	}

	@Override
	public void searchAction(String search) {
		picTable.setBeans(BillBean.SearchPics(search));
	}

	public void billItemSelectAction() {
		PicBean bean = picTable.getSelectBean();
		Collection<PicPlan> plans = bean.getPlans();
		plansTablePane.setBeans( plans);	
		if(plans!=null&&plans.size()>0){
			plansTablePane.getTable().setRowSelectionInterval(0,0);
			planItemSelectAction();
		}else
			planPnl.setBean(null);
		
	}
	public void planItemSelectAction() {
		PicPlan plan = plansTablePane.getSelectBean();
		planPnl.setBean(plan);

	}
	private void addNewPlan() {
		try{
			PicBean pic = picTable.getSelectBean();
			if(pic==null)return;
			PicPlan plan = new PicPlan(pic);
			plan.save();
			 ((BeanTableModel<PicPlan>) plansTablePane.getTable().getModel()).insertRow(plan);
			
			planPnl.setBean(plan);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
	}
	private void removeSelectRow() {
		PicPlan rmBean = plansTablePane.getSelectBean();
		rmBean.getPic().getPlans().remove(rmBean);
		rmBean.remove();
		plansTablePane.setBeans(rmBean.getPic().getPlans());
		
	}
}
