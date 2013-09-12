package windows.tableModes;

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

public class PlanTableMode extends AbstractTableModel {

	Vector<Plan> plans;
	Vector<String> columnNames=new Vector<>();
	private Vector<String> operationNames;
	public Vector<String> getOperationNames() {
		return operationNames;
	}

	public void setOperationNames(Vector<String> operationNames) {
		this.operationNames = operationNames;
	}

	public PlanTableMode(Vector<Plan> plans) {
		super();
		this.plans = plans;
		Plan tPlan=new Plan();
		Bill bill=new Bill();
		bill.setBillid("�ϼ�");
		tPlan.setBill(bill);
		tPlan.initOperationsByNotOut();
		this.plans.add(0, tPlan);
		columnNames.add("�ͻ�����");
		columnNames.add("������");
		columnNames.add("ͼ��");
		columnNames.add("����ʱ��");
		columnNames.add("Ҫ�󽻻�ʱ��");
		columnNames.add("��������");
		columnNames.add("���״̬");
		columnNames.add("�Ų�����");
		columnNames.add("�Ų�����");


		Vector<Operation> operations;

		try {
			operations = Operation.loadAll(Operation.class,"select * from operation where not out");
			operationNames=new Vector<>();
			for (Operation oper:operations){
				columnNames.add("������ʱ");
				columnNames.add("��ʱ");
				columnNames.add("����");
				operationNames.add(oper.getName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		columnNames.add("�ϼ�");
		columnNames.add("�����");

	}

	public Vector<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Vector<Plan> plans) {
		this.plans = plans;
		fireTableDataChanged();
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
					try{
					total+=Float.valueOf( getValueAt(i, columnIndex).toString());
					}catch (Exception e){
						
					}
				}
				return total;
			}
		}

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
			if(columnIndex<columnNames.size()-2){
				try{
				if(columnIndex%3==0) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-9)/3)).getUnitUseTime();
				}else  if(columnIndex%3==1) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-10)/3)).getUseTime();
				}else  if(columnIndex%3==2) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-11)/3)).getPlanCost();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			}else{
				if(columnIndex==columnNames.size()-2)
					return plan.getPlanCost();
				else if(columnIndex==columnNames.size()-1)
					return plan.getBill().getPlanCost();
			}
		}
		return "unknow";
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
				return float.class;
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO �Զ����ɵķ������
		return columnNames.get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(rowIndex==0)return false;
		if(!Userman.loginUser.isManager()&& plans.get(rowIndex).getId()>0)return false;
		if(columnIndex<7 ||columnIndex>columnNames.size()-2) 
			return false ;
		else if(columnIndex>=7 && columnIndex<=8) 
			return true;
		else
			return columnIndex%3==0;

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Plan plan=plans.get(rowIndex);
		if(columnIndex==7) plan.setProduceDate((Date) aValue);
		else if(columnIndex==8&&aValue!=null){
			plan.setNum((int) aValue);
		}
		else {
			if(columnIndex>8 ||columnIndex<columnNames.size()-2) {
				if(columnIndex%3==0&&aValue!=null){					
					OperationPlan operationPlan=	plan.getOperationPlans().get(operationNames.get((columnIndex-8)/3));
					operationPlan.setUnitUseTime( (float) aValue);

				}
			}
		}
	}

	public void addPlan(Plan plan) {
		plans.add(plan);
		this.fireTableDataChanged();

	}

	public Plan getSelectPlan(int row) {
		return plans.get(row);


	}

	public void getRemovePlan(int row) {
		plans.remove(row);

		this.fireTableDataChanged();
	}

}
