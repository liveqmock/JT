package ui.tables;

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

import ui.MainMenu;
import ui.MenuAction;
import ui.costPanes.NumberCellRenderer;
import ui.customComponet.BeanTablePane;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.SerialiObject;

public class BillTable extends BeanTablePane<Bill> {
	boolean columnInit=false;
	public BillTable(Vector<Bill> beans) {
		super(beans,Bill.class);
		
		getTable().setDefaultRenderer(float.class, new NumberCellRenderer() );
		getTable().setDefaultRenderer(double.class, new NumberCellRenderer() );
		
		getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		getTable().addHighlighter(
				new BillColorHighlighter(this, new HighlightPredicate() {

					@Override
					public boolean isHighlighted(Component renderer,
							ComponentAdapter adapter) {
						if (BillTable.this.getBeans() == null)
							return false;
						try{
							return ((Bill)BillTable.this.getBeans().toArray()[
									adapter.row]).getTableColor() != null;
						}catch(Exception e){
							return false;
						}
					}

				}));

		MainMenu.setPopMenus(getPopupMenu(), new MenuAction(this));
	}
	@Override
	public void setBeans(Collection<Bill> beans) {
		// TODO �Զ����ɵķ������
		super.setBeans(beans);
		if(!columnInit){
			columnInit=true;
			Object object=SerialiObject.loadFile(new File(
					"tableColumn.dat"));
			if(object!=null){
				HashMap<Object, Boolean> tableStatus=(HashMap<Object, Boolean>)object;
				for(int i=0;i<getTable().getModel().getColumnCount();i++){
					Object identifier = getTable().getModel().getColumnName(i);
					TableColumnExt columnExt = ((TableColumnModelExt)getTable().getColumnModel()).getColumnExt(identifier);
					Boolean iSvisible=tableStatus.get(identifier);
					if (columnExt != null && iSvisible!=null) {
						columnExt.setVisible(iSvisible);
					}

				}
			}
		}


	}
	public void saveStatus() {

		HashMap<Object, Boolean> tableStatus=new HashMap<>();



		for(int i=0;i<getTable().getModel().getColumnCount();i++){
			Object identifier = getTable().getModel().getColumnName(i);
			TableColumnExt columnExt = ((TableColumnModelExt)getTable().getColumnModel()).getColumnExt(identifier);
			if (columnExt != null) {
				tableStatus.put(identifier,columnExt.isVisible());

			}

		}
		try {
			SerialiObject.save(tableStatus,new File("tableColumn.dat"));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
}