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
		String[] planHeader = new String[]{"�ͻ�","ͼ��","Ҫ�󽻻�ʱ��","����","�ƻ���ʱ","ʵ���û�","��ʼʱ��","�ƻ�����ʱ��"};
		String[] operationHeader = new String[]{"�豸","����","��������","ʹ���豸��","��λ��ʱ","������ʱ","��ʼʱ��","����ʱ��","���������","ʣ������","�ƻ���ʱ","ʵ�ʺ�ʱ"};

		List<PicPlan> list = BeanMao.getBeans(PicPlan.class,"completed=0");
		planTable=new BeanTablePane<PicPlan>(list,PicPlan.class,planHeader,new String[]{"�ͻ�","ͼ��"});
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

		JMenuItem item=new JMenuItem(new AbstractAction("����Ϊ���") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(planTable.getSelectBean()==null){
					JOptionPane.showMessageDialog(PlansDetail.this, "��ѡ����Ҫ�����ļƻ���");
					return ;
				}if(JOptionPane.showConfirmDialog(PlansDetail.this, "ȷ�����Ų������?","���ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					PicPlan plan = planTable.getSelectBean();
					plan.setCompleted(true);
					plan.save();
				}
				
			}
		});

		planTable.getPopupMenu().add(item,0);



	}

}
