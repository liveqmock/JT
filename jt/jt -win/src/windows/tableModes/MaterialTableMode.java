package windows.tableModes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationOut;
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
		columnNames.add("客户名称");
		columnNames.add("订单号");
		columnNames.add("图号");
		columnNames.add("订单时间");
		columnNames.add("要求交货时间");
		columnNames.add("订单数量");
		columnNames.add("入库状态");
		columnNames.add("排产日期");
		columnNames.add("排产数量");
		createOperationNames();

		

	}
	public void createOperationNames() {
		Vector<Operation> operations;

		try {
			operations = Operation.loadOutAll();
			operationNames=new Vector<>();
			for (Operation oper:operations){
				columnNames.add("单价");
				columnNames.add("数量");
				operationNames.add(oper.getName());
			}
		} catch (IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException | InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
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
		case 9:
			return plan.getMaterialPrice();
		case 10:

			return plan.getMaterialNum();

		default:
				if(columnIndex%2==1) {
					return plan.getOperationOuts().get(operationNames.get((columnIndex-11)/2)).getMaterialCost();
				}else {
					return plan.getOperationOuts().get(operationNames.get((columnIndex-11)/2)).getMaterialNum();
				}
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
				return float.class;
			}
	}

	@Override
	public String getColumnName(int column) {
		// TODO 自动生成的方法存根
		return columnNames.get(column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex<9) return false;
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Plan plan=plans.get(rowIndex);
		if(columnIndex==10){
			plan.setMaterialNum((float) aValue);
		}else if(columnIndex==9){
			plan.setMaterialPrice((float) aValue);
		} else if(columnIndex>10&&columnIndex%2==1&&aValue!=null){					
					OperationOut operationOut=	plan.getOperationOuts().get(operationNames.get((columnIndex-11)/2));

					operationOut.setMaterialCost((float) aValue);

				}else if (aValue!=null){
					OperationOut operationOut =	plan.getOperationOuts().get(operationNames.get((columnIndex-12)/2));

					operationOut.setMaterialNum((float) aValue);

				}
		}

	
	public Plan getSelectPlan(int row) {
		return plans.get(row);


	}

}
