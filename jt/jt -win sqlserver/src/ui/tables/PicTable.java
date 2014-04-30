package ui.tables;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

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

public class PicTable extends BeanTablePane<PicBean> {
	boolean columnInit=false;
	public PicTable(Collection<PicBean> beans) {
		super(beans,PicBean.class);

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