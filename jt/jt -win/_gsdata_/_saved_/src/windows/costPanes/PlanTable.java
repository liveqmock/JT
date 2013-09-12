package windows.costPanes;

import static org.jdesktop.swingx.util.PaintUtils.blend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
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

import windows.customComponet.BeanTablePane;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Plan;

public class PlanTable extends JXTable {

	private Vector<Plan> plans;
	private PlanTableMode mode;
	private JPopupMenu popupMenu;
	public PlanTable() {
		super();
		plans=Plan.loadAllUnCompleted();
		setHighlighters(HighlighterFactory.
				createAlternateStriping());
		setHorizontalScrollEnabled(true);
		mode=new PlanTableMode(plans);
		setModel(mode);
		int i=8;
		TableColumnModel cm = getColumnModel();
		setDefaultEditor(Date.class, new DatePickerCellEditor());
		setDefaultEditor(int.class, new  IntCellEditor(new JTextField()));
		GroupableTableHeader header = (GroupableTableHeader)getTableHeader();
		for(String oper:mode.getOperationNames()){
			ColumnGroup operColumnGroup=new ColumnGroup(oper);
			operColumnGroup.add(cm.getColumn(i+1));
			operColumnGroup.add(cm.getColumn(i+2));
			header.addColumnGroup(operColumnGroup);
			i+=2;
		}
		popupMenu=new JPopupMenu();
		popupMenu.add(new AbstractAction("�����Ų��ƻ�") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createPlan();
				
			}
		});
popupMenu.add(new AbstractAction("ɾ��") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode.getRemovePlan(convertRowIndexToModel(getSelectedRow()));
				
			}
		});
		setSortable(false);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row =rowAtPoint(e.getPoint());
					if (row > 0) {
						setRowSelectionInterval(row, row);
						popupMenu.show(e.getComponent(),
								e.getX(), e.getY());
					}
				}
			}
		});
		addHighlighter(new Highter());
		setShowGrid(true);
		packAll();
	}
	public void save() {
		for(Plan plan:plans) {
			plan.save();
		}
	}

	public void createPlan() {
		Vector<Bill> bills = Bill.loadBySearch("true", false);
		final Plan plan=new Plan();
		final BeanTablePane<Bill> panel= new BeanTablePane<>(bills);
		final JDialog dialog=new JDialog();
		dialog.setTitle("ѡ�񶩵�");
		dialog.getContentPane().add(panel,BorderLayout.CENTER);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		panel.getTable().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				super.mouseClicked(e);
				if(e.getClickCount()>1){
					plan.setBill(panel.getSelectBean());
					plan.initOperations();
					mode.addPlan(plan);
					setRowSelectionInterval(getRowCount()-1,getRowCount()-1);
					scrollRowToVisible(getRowCount()-1);
					dialog.dispose();
				}
			}


		}); 
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);

	}
	private  class Highter extends AbstractHighlighter{

		private Color foreground;
		private Color background;
		private Color selectedBackground;
		private Color selectedForeground;

		public Highter() {
			super();
			// TODO �Զ����ɵĹ��캯�����
		}

		public Highter(HighlightPredicate predicate) {
			super(predicate);
			// TODO �Զ����ɵĹ��캯�����
		}

		@Override
		protected Component doHighlight(Component renderer,
				ComponentAdapter adapter) {
			applyBackground(renderer, adapter);
			applyForeground(renderer, adapter);
			return renderer;
		}
		protected void applyBackground(Component renderer, ComponentAdapter adapter) {

			Color color = adapter.isSelected() ?  plans.get(adapter.row).isOut()?Color.MAGENTA:getSelectedBackground()
					: plans.get(adapter.row).isOut()?Color.red:getSelectedBackground();

			renderer.setBackground(blend(renderer.getBackground(), color));
		}
		protected void applyForeground(Component renderer, ComponentAdapter adapter) {
			Color color = adapter.isSelected() ? getSelectedForeground()
					: getForeground();

			renderer.setForeground(blend(renderer.getForeground(), color));
		}
		public Color getBackground() {
			return background;
		}

		public void setBackground(Color color) {
			if (areEqual(color, getBackground()))
				return;
			background = color;
			fireStateChanged();
		}

		public Color getForeground() {
			return foreground;
		}


		public void setForeground(Color color) {
			if (areEqual(color, getForeground()))
				return;
			foreground = color;
			fireStateChanged();
		}


		public Color getSelectedBackground() {
			return selectedBackground;
		}


		public void setSelectedBackground(Color color) {
			if (areEqual(color, getSelectedBackground()))
				return;
			selectedBackground = color;
			fireStateChanged();
		}

		public Color getSelectedForeground() {
			return selectedForeground;
		}

		public void setSelectedForeground(Color color) {
			if (areEqual(color, getSelectedForeground()))
				return;
			selectedForeground = color;
			fireStateChanged();
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
}
