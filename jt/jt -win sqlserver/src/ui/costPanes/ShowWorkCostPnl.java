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

	private static String sqlString="select billNo �������,picid ͼ��,billdate ��������,planwork �ƻ���ʱ,operation �ƻ��ӹ�����,"
+"prepare �ƻ���������,worktime ʵ�ʼӹ���ʱ,workcost ʵ�ʼӹ�����,"
+"preparecost ʵ�ʵ������� , material ���Ϸ���,"
+"ifnull(workcost,0)+ifnull(preparecost,0)+ifnull(material,0) �ܷ��� from ("
+"select bill,sum(unitusetime*b.num) planwork, sum(cost*unitusetime*b.num) operation,sum(preparetime*.2) prepare "
+"from operationplan a join plan b on a.plan=b.id group by bill"
+") a left join("
+"select bill,sum(workcost) workcost,sum(worktime) worktime,sum(preparecost) preparecost "
+"from operationwork a join plan b on a.plan=b.id group by bill"
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}

	@Override
	public void itemSelectAction() {
		
		
	}
	


	
}
