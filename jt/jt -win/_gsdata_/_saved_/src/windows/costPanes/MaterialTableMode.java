package windows.costPanes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationPlan;
import com.mao.jf.beans.Plan;

public class MaterialTableMode extends AbstractTableModel {

	Vector<Plan> plans;
	Vector<String> columnNames=new Vector<>();
	private Vector<String> operationNames;
	public Vector<String> getOperationNames() {
		return operationNames;
	}

	public void setOperationNames(Vector<String> operationNames) {
		this.operationNames = operationNames;
	}

	public MaterialTableMode(Vector<Plan> plans) {
		super();
		this.plans = plans;
		columnNames.add("序号");
		columnNames.add("客户名称");
		columnNames.add("图号");
		columnNames.add("排产日期");
		columnNames.add("排产数量");


		Vector<Operation> operations;

		try {
			operations = Operation.loadAll(Operation.class);
			operationNames=new Vector<>();
			for (Operation oper:operations){
				columnNames.add("单价");
				columnNames.add("数量");
				operationNames.add(oper.getName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

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
				if(columnIndex%2==1) {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-5)/2)).getMaterialCost();
				}else {
					return plan.getOperationPlans().get(operationNames.get((columnIndex-5)/2)).getMaterialNum();
				}
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
				return columnIndex%2==1?float.class:int.class;
			}
	}

	@Override
	public String getColumnName(int column) {
		// TODO 自动生成的方法存根
		return columnNames.get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex<5) return false;
		Plan plan=plans.get(rowIndex);
		OperationPlan operationPlan=	plan.getOperationPlans().get(operationNames.get((columnIndex-5)/2));
		if(operationPlan.getOut())return false;
		return operationPlan.getNum()!=0;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Plan plan=plans.get(rowIndex);
				if(columnIndex%2==1&&aValue!=null){					
					OperationPlan operationPlan=	plan.getOperationPlans().get(operationNames.get((columnIndex-5)/2));

					operationPlan.setMaterialCost((float) aValue);

				}else if (aValue!=null){
					OperationPlan operationPlan=	plan.getOperationPlans().get(operationNames.get((columnIndex-5)/2));
					operationPlan.setMaterialNum((int) aValue);

				}
	}

	
	public Plan getSelectPlan(int row) {
		return plans.get(row);


	}

}
