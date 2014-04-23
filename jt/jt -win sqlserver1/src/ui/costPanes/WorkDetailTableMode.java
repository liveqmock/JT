package ui.costPanes;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.BillPlan;

public class WorkDetailTableMode extends AbstractTableModel {

	Vector<BillPlan> plans;
	Vector<String> columnNames=new Vector<>();
	
	public WorkDetailTableMode(Vector<BillPlan> plans) {
		super();
		this.plans = plans;
		columnNames.add("���");
		columnNames.add("�ͻ�����");
		columnNames.add("ͼ��");
		columnNames.add("�Ų�����");
		columnNames.add("ʵ������");
		columnNames.add("��Ʒ����");
		columnNames.add("��������");
		columnNames.add("δ�깤����");
		columnNames.add("�����ӹ�ʱ��");
		columnNames.add("�����ӹ�ʱ��");
		columnNames.add("�ƻ��ӹ�ʱ��");
		columnNames.add("����ʱ��");
		columnNames.add("ʱ��");
		columnNames.add("�������");
		columnNames.add("�������");
		columnNames.add("��ʱ��");
		columnNames.add("�����ӹ���");
		columnNames.add("�����ӹ���");
		columnNames.add("�������Ϸ�");
		columnNames.add("�������Ϸ�");
		columnNames.add("�����ɱ�");
		columnNames.add("�����ɱ�");
		columnNames.add("�����ɱ�ƽ���۸�");
		columnNames.add("������");
		columnNames.add("��ע");
	}

	public Vector<BillPlan> getPlans() {
		return plans;
	}

	public void setPlans(Vector<BillPlan> plans) {
		this.plans = plans;
	}

	@Override
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO �Զ����ɵķ������
		return plans.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BillPlan plan=plans.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return plan.getBill().getBillid();

		case 1:
			return plan.getBill().getCustom();

		case 2:
			return plan.getBill().getPicid();
		case 3:
			return plan.getPlanProcessTime();
		case 4:

			return plan.getNum();

		default:
				return  null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
			return  String.class;

		case 3:
			return Date.class;
		case 4:
			return int.class;

		default:
				return String.class;
			}
	}

	@Override
	public String getColumnName(int column) {
		// TODO �Զ����ɵķ������
		return columnNames.get(column);
	}

	
	public BillPlan getSelectPlan(int row) {
		return plans.get(row);


	}

}