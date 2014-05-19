package ui.tables;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import ui.customComponet.BeanTableModel;
import ui.customComponet.BeanTablePane;
import ui.menu.PicPopmenu;

import com.mao.jf.beans.PicBean;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.Userman;

public class PicTable extends BeanTablePane<PicBean> {
	boolean columnInit=false;
	private final static String[] admHeaders=new String[]{"客户","订单号","项目号","图号","客户订单号","数量","良品数","不良数","最终报价(未含税)","最终报价(含税)","额定单件成本","检验人"
			,"总价","订单时间","客户要求交货时间","订单交货时间","发货时间","发货数量","开票金额","发票张数","最后开票日期",
			"特殊采用","特采客户确认人","特采确认人","外协单位","外协价格","外协数量","外协交货日期","外协发票数量","外协发票金额","外协发票时间","构件号","材料名","材料编号","材质","材料型号","材料技术条件","材料部件名"
			,"完成时间","入库"};

	private final static String[] nomheaders=new String[]{"客户","订单号","项目号","图号","客户订单号","额定单件成本","数量","良品数","检验人"
			,"订单时间","客户要求交货时间","订单交货时间","发货时间","发货数量","外协单位","外协价格","外协数量","外协交货日期","外协发票数量","外协发票金额","外协发票时间","构件号","材料名","材料编号"
			,"材质","材料型号","材料技术条件","材料部件名","完成时间","入库"};
	public PicTable(Collection<PicBean> beans) {
		super(beans,PicBean.class,Userman.loginUser.isManager()?admHeaders:nomheaders);

		setPopupMenu(new PicPopmenu(this));
		getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ColorHighlighter highlighter= new ColorHighlighter( new HighlightPredicate() {

			@Override
			public boolean isHighlighted(Component renderer,
					ComponentAdapter adapter) {
				if (PicTable.this.getBeans() == null)
					return false;
				try{
					return PicTable.this.getBean(adapter.row).getColor() != null;
				}catch(Exception e){
					return false;
				}
			}

		}){

			@Override
			Color getColor(int row) {
				// TODO 自动生成的方法存根
				return PicTable.this.getBean(row).getTableColor();
			}
			
		};
		getTable().addHighlighter(highlighter);
		
		initTable();
		
	}
	private void initTable(){
		Object object=SerialiObject.loadFile(new File(
				"PictableColumn.dat"));
		if(object!=null){
			HashMap<String, Object> tableStatus=(HashMap<String, Object>)object;
			for(int i=0;i<getTable().getModel().getColumnCount();i++){
				Object identifier = getTable().getModel().getColumnName(i);
				TableColumnExt columnExt = ((TableColumnModelExt)getTable().getColumnModel()).getColumnExt(identifier);
				 Object iSvisible = tableStatus.get(identifier);
				if (columnExt != null && iSvisible!=null) {
					columnExt.setVisible((boolean) iSvisible);
				}
				Object width= tableStatus.get(identifier+"_width");
				if (width != null ) {
					columnExt.setPreferredWidth((int) width);
				}

			}
		}
	}
	@Override
	public void setBeans(Collection<PicBean> beans) {
//		this.beans = beans;
		((BeanTableModel<PicBean>) table.getModel()).setBeans(beans);
		


	}
	public void saveStatus() {
		HashMap<Object, Object> tableStatus=new HashMap<>();



		for(int i=0;i<getTable().getModel().getColumnCount();i++){
			String identifier = getTable().getModel().getColumnName(i);
			TableColumnExt columnExt = ((TableColumnModelExt)getTable().getColumnModel()).getColumnExt(identifier);
			if (columnExt != null) {
				tableStatus.put(identifier,columnExt.isVisible());
				tableStatus.put(identifier+"_width",columnExt.getWidth());
			}

		}
		try {
			SerialiObject.save(tableStatus,new File("PictableColumn.dat"));
//			System.err.println("pictable saved");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}