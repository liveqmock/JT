package windows.tableModes;

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
		columnNames.add("�ͻ�����");
		columnNames.add("������");
		columnNames.add("ͼ��");
		columnNames.add("����ʱ��");
		columnNames.add("Ҫ�󽻻�ʱ��");
		columnNames.add("��������");
		columnNames.add("���״̬");
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
			return plan.getBill().getCustom();
		case 1:
			return plan.getBill().getBillid();

		case 2:
			return plan.getBill().getPicid();

		case 3:
			return plan.getBill().getBillDate();

		case 4:
			return plan.getBill().getRequestDate();

		case 5:
			return plan.getBill().getNum();

		case 6:
			return plan.getBill().getWarehousedStr();

		case 7:
			return plan.getProduceDate();

		case 8:

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
		case 6:
			return  String.class;

		case 3:
		case 4:
		case 7:
			return Date.class;
		case 5:
		case 8:
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
