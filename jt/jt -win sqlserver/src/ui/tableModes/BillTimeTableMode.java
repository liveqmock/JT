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
		billTime.setBillid("�ϼ�");
		this.billTimes.add(0, billTime);
		columnNames.add("�ͻ�����");
		columnNames.add("������");
		columnNames.add("ͼ��");
		columnNames.add("�Ų�ʱ��");


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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		columnNames.add("�ϼƼƻ���ʱ");

	}

	@Override
	public int getColumnCount() {
		// TODO �Զ����ɵķ������
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		return columnNames.get(column);
	}




}
