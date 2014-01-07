package ui.tableModes;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.BillTime;
import com.mao.jf.beans.Operation;
import com.mao.jf.beans.OperationTime;

public class BillTimeTableMode extends AbstractTableModel {

	Vector<BillTime> billTimes;
	Vector<String> columnNames=new Vector<>();
	

	public BillTimeTableMode(Vector<BillTime> billTimes) {
		super();
		this.billTimes = billTimes;
		BillTime billTime=new BillTime();
		billTime.setBillid("合计");
		this.billTimes.add(0, billTime);
		columnNames.add("客户名称");
		columnNames.add("订单号");
		columnNames.add("图号");
		columnNames.add("排产时间");


		Vector<Operation> operations;

		try {
			operations = Operation.loadAll(Operation.class,"select * from operation where not out");
			for (Operation oper:operations){
				columnNames.add(oper.getName());
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		columnNames.add("合计计划用时");

	}

	@Override
	public int getColumnCount() {
		// TODO 自动生成的方法存根
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO 自动生成的方法存根
		return billTimes.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==0){
			if(columnIndex>2){
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

		BillTime billTime=billTimes.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return billTime.getCustom();

		case 1:
			return billTime.getBillid();

		case 2:
			return billTime.getPicid();

		case 3:
			return billTime.getProduceDate();


		default:
			if(columnIndex<columnNames.size()-1){
				OperationTime operationTime=billTime.getOperationTimeByName( columnNames.get(columnIndex));
				if(operationTime!=null){
					return operationTime.getPlantime();

				}else{
					return 0;
				}
			}else{
				return billTime.getTotalPlanTime();
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

		default:
			return float.class;
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO 自动生成的方法存根
		return columnNames.get(column);
	}




}
