package ui.costPanes;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXComboBox;

import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.PicPlan;

public class PlansDetail extends JSplitPane{

	private BeanTablePane<PicPlan> planTable;
	private BeanTablePane<OperationPlan> operationTable;

	public PlansDetail() {
		super();
		String[] planHeader = new String[]{"客户","图号","要求交货时间","数量","计划用时","实际用户","开始时间","计划结束时间"};
		String[] operationHeader = new String[]{"设备","工艺","工艺描述","使用设备数","单位用时","调机用时","开始时间","结束时间","已完成数量","剩余数量","计划耗时","实际耗时"};

		List<PicPlan> list = BeanMao.getBeans(PicPlan.class,"completed=0");
		planTable=new BeanTablePane<PicPlan>(list,PicPlan.class,planHeader,new String[]{"客户","图号"});
		operationTable=new BeanTablePane<OperationPlan>(null,OperationPlan.class,operationHeader);
		setLeftComponent(planTable);
		setRightComponent(operationTable);
		setDividerLocation(500);
		planTable.getTable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(final ListSelectionEvent arg0) {
						if (arg0.getValueIsAdjusting()) {
							operationTable.setBeans(planTable.getSelectBean().getOperationPlans());
						}

					}

				});

		JMenuItem item=new JMenuItem(new AbstractAction("设置为完成") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(planTable.getSelectBean()==null){
					JOptionPane.showMessageDialog(PlansDetail.this, "请选中你要操作的计划表");
					return ;
				}if(JOptionPane.showConfirmDialog(PlansDetail.this, "确定此排产已完成?","完成确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					PicPlan plan = planTable.getSelectBean();
					plan.setCompleted(true);
					plan.save();
				}
				
			}
		});

		planTable.getPopupMenu().add(item,0);



	}

}
