package ui.costPanes;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mao.jf.beans.Plan;

public class WorkDetailTableMode extends AbstractTableModel {

	Vector<Plan> plans;
	Vector<String> columnNames=new Vector<>();
	
	public WorkDetailTableMode(Vector<Plan> plans) {
		super();
		this.plans = plans;
		columnNames.add("序号");
		columnNames.add("客户名称");
		columnNames.add("图号");
		columnNames.add("排产日期");
		columnNames.add("实发数量");
		columnNames.add("成品数量");
		columnNames.add("报废数量");
		columnNames.add("未完工数量");
		columnNames.add("单件加工时间");
		columnNames.add("本批加工时间");
		columnNames.add("计划加工时间");
		columnNames.add("超产时间");
		columnNames.add("时间");
		columnNames.add("超产金额");
		columnNames.add("负产金额");
		columnNames.add("工时价");
		columnNames.add("单件加工费");
		columnNames.add("本批加工费");
		columnNames.add("单件材料费");
		columnNames.add("本批材料费");
		columnNames.add("单件成本");
		columnNames.add("本批成本");
		columnNames.add("本批成本平均价格");
		columnNames.add("订单价");
		columnNames.add("备注");
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
		// TODO 自动生成的方法存根
		return columnNames.get(column);
	}

	
	public Plan getSelectPlan(int row) {
		return plans.get(row);


	}

}
