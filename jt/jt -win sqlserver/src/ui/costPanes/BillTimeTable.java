package ui.costPanes;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.DatePickerCellEditor;

import ui.mutiHeadTable.GroupableTableHeader;
import ui.tableModes.BillTimeTableMode;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.BillTime;

public class BillTimeTable extends JXTable {

	private Vector<BillTime> billTimes;
	private BillTimeTableMode mode;
	public BillTimeTable() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		super();
		billTimes=BeanMao.loadAll(BillTime.class,"select distinct bill,custom,billid,picid,producedate from operationtime order by custom,producedate");
		setHighlighters(HighlighterFactory.
				createAlternateStriping());
		setHorizontalScrollEnabled(true);
		mode=new BillTimeTableMode(billTimes);
		setModel(mode);
		setDefaultRenderer(float.class, new NumberCellRenderer() );
		
		setDefaultEditor(Date.class, new DatePickerCellEditor());
		setDefaultEditor(float.class, new  FloatCellEditor(new JTextField()));
		setDefaultEditor(int.class, new  IntCellEditor(new JTextField()));
		
//		popupMenu=new JPopupMenu();
//		popupMenu.add(new AbstractAction("新增排产计划") {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				createPlan();
//				
//			}
//		});


		setSortable(false);
//		addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				showPopup(e);
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				showPopup(e);
//			}
//
//			private void showPopup(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					int row =rowAtPoint(e.getPoint());
//					if (row > 0) {
//						setRowSelectionInterval(row, row);
//						popupMenu.show(e.getComponent(),
//								e.getX(), e.getY());
//					}
//				}
//			}
//		});

		setShowGrid(true);
		packAll();
	}


	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new GroupableTableHeader(columnModel);
	}
	class FloatCellEditor extends DefaultCellEditor {

		@Override
		public Object getCellEditorValue() {
			try{
				return Float.valueOf(super.getCellEditorValue().toString());
			}catch(Exception e){
				return null;
			}
		}



		public FloatCellEditor(final JTextField textField) {
			super(textField);
			textField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {

				}

				@Override
				public void focusGained(FocusEvent e) {
					textField.setSelectionStart(0);
					textField.setSelectionEnd(Integer.MAX_VALUE);

				}
			});
		}

	}
	class IntCellEditor extends DefaultCellEditor {

		@Override
		public Object getCellEditorValue() {
			try{
				return Integer.valueOf(super.getCellEditorValue().toString());
			}catch(Exception e){
				return null;
			}
		}



		public IntCellEditor(final JTextField textField) {
			super(textField);
			textField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {

				}

				@Override
				public void focusGained(FocusEvent e) {
					textField.setSelectionStart(0);
					textField.setSelectionEnd(Integer.MAX_VALUE);

				}
			});
		}

	}


}
