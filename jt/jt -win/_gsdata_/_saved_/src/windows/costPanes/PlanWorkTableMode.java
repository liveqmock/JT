package windows.costPanes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.Plan;
import com.mao.jf.beans.Userman;

public class PlanWorkTableMode extends AbstractTableModel {

	Vector<Plan> plans;
	Vector<String> columnNames=new Vector<>();
	private Vector<String> operationNames;
	public Vector<String> getOperationNames() {
		return operationNames;
	}

	public void setOperationNames(Vector<String> operationNames) {
		this.operationNames = operationNames;
	}

	public PlanWorkTableMode(Vector<Plan> plans) {
		super();
		this.plans = plans;
		Plan tPlan=new Plan();
		Bill bill=new Bill();
		bill.setBillid("�ϼ�");
		tPlan.setBill(bill);
		tPlan.initOperations();
		this.plans.add(0, tPlan);
		columnNames.add("���");
		columnNames.add("�ͻ�����");
		columnNames.add("ͼ��");
		columnNames.add("����ʱ��");
		columnNames.add("Ҫ�󽻻�ʱ��");
		columnNames.add("��������");
		columnNames.add("���״̬");
		columnNames.add("�Ų�����");
		columnNames.add("�Ų�����");


		Vector<Operation> operations;

		try {
			operations = Operation.loadAll(Operation.class);
			operationNames=new Vector<>();
			for (Operation oper:operations){
				columnNames.add("�ƻ���ʱ");
				columnNames.add("�ƻ�����");
				columnNames.add("ʵ����ʱ");
				columnNames.add("ʵ�ʷ���");
				operationNames.add(oper.getName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		columnNames.add("�ƻ��ϼƷ���");
		columnNames.add("�����");
		columnNames.add("ʵ�ʷ���");

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
		if(rowIndex==0){
			if(columnIndex>7){
				float total=0;
				for(int i=1;i<getRowCount();i++){
					total+=Float.valueOf( getValueAt(i, columnIndex).toString());
				}
				return total;
			}
		}

		Plan plan=plans.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return plan.getBill().getBillid();

		case 1:
			return plan.getBill().getCustom();

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
			if(columnIndex<columnNames.size()-3){
				
				if(columnIndex%4==1) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-9)/2)).getUseTime();
				}else if(columnIndex%4==1) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-10)/2)).getCostTotal();
				}else if(columnIndex%4==1) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-10)/2)).getWorkTime();
				}else if(columnIndex%4==0) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-10)/2)).getWorkCostTotal();
				}
			}else{
				if(columnIndex==columnNames.size()-2)
					return plan.getCost();
				else if(columnIndex==columnNames.size()-1)
					return plan.getBill().getPlanCost()*plan.getNum();
			}
		}
		return "unknow";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;

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
			if(columnIndex<columnNames.size()-3){if(columnIndex%4==1) {
				return int.class;
			}else if(columnIndex%4==1) {
				return double.class;
			}else if(columnIndex%4==1) {
				return int.class;
			}else if(columnIndex%4==0) {
				return double.class;
			}
			}else{
				return double.class;
			}
		}
		return String.class;
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
