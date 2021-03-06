package ui.tableModes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.Plan;

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
		bill.setBillid("合计");
		tPlan.setBill(bill);
		tPlan.initOperationsByNotOut();
		this.plans.add(0, tPlan);
		columnNames.add("客户名称");
		columnNames.add("订单号");
		columnNames.add("图号");
		columnNames.add("订单时间");
		columnNames.add("要求交货时间");
		columnNames.add("订单数量");
		columnNames.add("入库状态");
		columnNames.add("排产日期");
		columnNames.add("排产数量");


		Vector<Operation> operations;

		try {
			operations = Operation.loadAll(Operation.class,"select * from operation where not out");
			operationNames=new Vector<>();
			for (Operation oper:operations){
				columnNames.add("单件用时");
				columnNames.add("用时");
				columnNames.add("费用");
				operationNames.add(oper.getName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		columnNames.add("合计");
		columnNames.add("额定费用");

	}

	public Vector<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Vector<Plan> plans) {
		this.plans = plans;
	}

	@Override
	public int getColumnCount() {
		// TODO 自动生成的方法存根
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO 自动生成的方法存根
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
		// TODO 自动生成的方法存根
		return columnNames.get(column);
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
