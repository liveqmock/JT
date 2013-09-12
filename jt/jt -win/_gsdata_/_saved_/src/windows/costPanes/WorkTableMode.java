package windows.costPanes;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Plan;

public class WorkTableMode extends AbstractTableModel {

	Vector<Plan> plans;
	Vector<String> columnNames=new Vector<>();
	
	public WorkTableMode(Vector<Plan> plans) {
		super();
		this.plans = plans;
		columnNames.add("���");
		columnNames.add("�ͻ�����");
		columnNames.add("ͼ��");
		columnNames.add("�Ų�����");
		columnNames.add("�Ų�����");
	}

	public Vector<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Vector<Plan> plans) {
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
		Plan plan=plans.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return plan.getBill().getBillid();

		case 1:
			return plan.getBill().getCustom();

		case 2:
			return plan.getBill().getPicid();
		case 3:
			return plan.getProduceDate();
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

	
	public Plan getSelectPlan(int row) {
		return plans.get(row);


	}

}
