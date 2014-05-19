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

import ui.customComponet.BeanTablePane;
import ui.menu.BillPopMenu;

import com.mao.jf.beans.BillBean;
import com.mao.jf.beans.SerialiObject;
import com.mao.jf.beans.Userman;

public class BillTable extends BeanTablePane<BillBean> {
	boolean columnInit=false;
	private final static String[] admHeaders=new String[]{"�ͻ�","��ϵ��","������",
		"������","��������","Ҫ�󽻻�ʱ��","������������","�Ƿ����","���ձ���(δ��˰)","���ձ���(��˰)",
		"��Ʊ����","��Ʊ���","���Ʊʱ��","��ע"};

private final static String[] nomheaders=new String[]{"�ͻ�","��ϵ��","������",
	"������","��������","Ҫ�󽻻�ʱ��","������������","�Ƿ����","��ע"};
	public BillTable(Vector<BillBean> beans) {
		super(beans,BillBean.class,Userman.loginUser.isManager()?admHeaders:nomheaders);
		setPopupMenu(new BillPopMenu(this));
		getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ColorHighlighter highlighter= new ColorHighlighter( new HighlightPredicate() {

			@Override
			public boolean isHighlighted(Component renderer,
					ComponentAdapter adapter) {
				if (BillTable.this.getBeans() == null)
					return false;
				try{
					return BillTable.this.getBean(adapter.row).getColor() != null;
				}catch(Exception e){
					return false;
				}
			}

		}){

			@Override
			Color getColor(int row) {
				// TODO �Զ����ɵķ������
				return BillTable.this.getBean(row).getTableColor();
			}
			
		};
		
		getTable().addHighlighter(highlighter);

//		MainMenu.setPopMenus(getPopupMenu(), new MenuAction(this));
	}
	@Override
	public void setBeans(Collection<BillBean> beans) {
		// TODO �Զ����ɵķ������
		super.setBeans(beans);
		if(!columnInit){
			columnInit=true;
			Object object=SerialiObject.loadFile(new File(
					"BilltableColumn.dat"));
			if(object!=null){
				HashMap<Object, Boolean> tableStatus=(HashMap<Object, Boolean>)object;
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
			SerialiObject.save(tableStatus,new File("BilltableColumn.dat"));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
}