package ui.costPanes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.HorizontalLayout;

import com.itextpdf.text.DocumentException;
import com.mao.jf.beans.PicPlan;
import com.mao.jf.beans.PlanPdf;

public class PlanPnl extends JPanel{

	private OperarionPlansPnl operationPlansPnl;
	private PicPlan bean;
	public PlanPnl(PicPlan bean) {
		super();
		this.bean=bean;
		createContents();
	}

	protected void createContents() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "�Ų��ƻ�ϸ��", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		

		operationPlansPnl=new OperarionPlansPnl(bean);

		operationPlansPnl.getTablePane().getTable().setUI(new DragDropRowTableUI<>());
		JButton saveBt = new JButton("�����Ų��ƻ�");
		JButton viewPdfButton=new JButton("Ԥ����ӡ���ո��ٿ�");
		JPanel panel=new JPanel(new HorizontalLayout(30));
		panel.add(saveBt);
		panel.add(viewPdfButton);
		setLayout(new BorderLayout());
		add(operationPlansPnl,BorderLayout.CENTER);
		
		add(panel,BorderLayout.SOUTH);
		saveBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				savePlan();
			}
		});
		viewPdfButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(bean.getStartDate()==null){
						savePlan();
					}
					new PlanPdf(bean).buildPdfViewer();
				} catch (DocumentException | IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
	}
	public void setBean(PicPlan plan) {
		this.bean=plan;
		operationPlansPnl.setPlan(plan);
	}
	private void savePlan() {
		try {
			bean.initOperationPlans();
			bean.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			
		
	}

}
