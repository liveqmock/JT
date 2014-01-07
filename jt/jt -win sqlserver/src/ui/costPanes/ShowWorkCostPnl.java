package ui.costPanes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import ui.customComponet.RsTableModel;
import ui.panels.BillShowPnl;

import com.mao.jf.beans.SessionData;

public class ShowWorkCostPnl extends BillShowPnl {

	private static String sqlString="select billNo 订单编号,picid 图号,billdate 订单日期,planwork 计划用时,operation 计划加工费用,"
+"prepare 计划调机费用,worktime 实际加工用时,workcost 实际加工费用,"
+"preparecost 实际调机费用 , material 材料费用,"
+"isnull(workcost,0)+isnull(preparecost,0)+isnull(material,0) 总费用 from ("
+"select bill,sum(unitusetime*b.num) planwork, sum(cost*unitusetime*b.num) operation,sum(preparetime*.2) prepare "
+"from operationplan a join \"plan\" b on a.\"plan\"=b.id group by bill"
+") a left join("
+"select bill,sum(workcost) workcost,sum(worktime) worktime,sum(preparecost) preparecost "
+"from operationwork a join \"plan\" b on a.\"plan\"=b.id group by bill"
+") b on a.bill=b.bill left join ("
+"select  bill, sum(unitcost*num) material from material group by bill"
+") c on a.bill=c.bill join bill d on a.bill=d.id";
	private RsTableModel model;
	@Override
	public void searchAction(String search) {
		try(Statement st=SessionData.getConnection().createStatement()){
			ResultSet rs = st.executeQuery(sqlString+" and "+search);
			RowSetFactory rowSetFactory = RowSetProvider.newFactory();
			CachedRowSet crs = rowSetFactory.createCachedRowSet();
			crs.populate(rs);
		    if(model==null){
		    	model=new RsTableModel(crs);
		    	table.setModel(model);
		    }
		    else
		    	model.setRs(crs);
			
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	@Override
	public void itemSelectAction() {
		
		
	}
	


	
}
