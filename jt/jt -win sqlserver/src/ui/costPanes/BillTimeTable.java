package ui.costPanes;

import static org.jdesktop.swingx.util.PaintUtils.blend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import mutiHeadTable.ColumnGroup;
import mutiHeadTable.GroupableTableHeader;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.DatePickerCellEditor;

import ui.customComponet.BeanTablePane;
import ui.tableModes.BillTimeTableMode;
import ui.tableModes.PlanTableMode;

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.Bill;
import com.mao.jf.beans.BillTime;
import com.mao.jf.beans.Plan;

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
