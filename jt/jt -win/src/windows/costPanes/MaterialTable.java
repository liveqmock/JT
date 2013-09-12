package windows.costPanes;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import mutiHeadTable.ColumnGroup;
import mutiHeadTable.GroupableTableHeader;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import windows.tableModes.MaterialTableMode;

import com.mao.jf.beans.Plan;

public class MaterialTable extends JXTable {

	private Vector<Plan> plans;
	private MaterialTableMode mode;
	public MaterialTable() {
		super();
		plans=Plan.loadUnCompletedByOut();
		setHighlighters(HighlighterFactory.
				createAlternateStriping());
		setHorizontalScrollEnabled(true);
		mode=new MaterialTableMode(plans);
		setModel(mode);
		int i=8;
		TableColumnModel cm = getColumnModel();
		setDefaultRenderer(float.class, new NumberCellRenderer() );
		setDefaultEditor(float.class, new FloatCellEditor(new JTextField()));
		setDefaultEditor(int.class, new  IntCellEditor(new JTextField()));
		GroupableTableHeader header = (GroupableTableHeader)getTableHeader();

		ColumnGroup operColumnGroup=new ColumnGroup("Ö÷²Ä");
		operColumnGroup.add(cm.getColumn(i+1));
		operColumnGroup.add(cm.getColumn(i+2));
		header.addColumnGroup(operColumnGroup);
		for(String oper:mode.getOperationNames()){
			operColumnGroup=new ColumnGroup(oper);
			operColumnGroup.add(cm.getColumn(i+1));
			operColumnGroup.add(cm.getColumn(i+2));
			header.addColumnGroup(operColumnGroup);
			i+=2;
		}
		
	
		setShowGrid(true);
		packAll();
	}
	public void save() {
		for(Plan plan:plans) {
			plan.save();
		}
	}

	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new GroupableTableHeader(columnModel);
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
}
