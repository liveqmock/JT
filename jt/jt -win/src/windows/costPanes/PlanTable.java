package windows.costPanes;

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

import windows.customComponet.BeanTablePane;
import windows.tableModes.PlanTableMode;

import com.mao.jf.beans.Bill;
import com.mao.jf.beans.Plan;

public class PlanTable extends JXTable {

	private Vector<Plan> plans;
	private PlanTableMode mode;
	private JPopupMenu popupMenu;
	public PlanTable() {
		super();
		plans=Plan.loadAllUnCompletedByNotOut(null);
		setHighlighters(HighlighterFactory.
				createAlternateStriping());
		setHorizontalScrollEnabled(true);
		mode=new PlanTableMode(plans);
		setModel(mode);
		int i=8;
		
		setDefaultRenderer(float.class, new NumberCellRenderer() );
		TableColumnModel cm = getColumnModel();
		
		setDefaultEditor(Date.class, new DatePickerCellEditor());
		setDefaultEditor(float.class, new  FloatCellEditor(new JTextField()));
		setDefaultEditor(int.class, new  IntCellEditor(new JTextField()));
		GroupableTableHeader header = (GroupableTableHeader)getTableHeader();
		for(String oper:mode.getOperationNames()){
			ColumnGroup operColumnGroup=new ColumnGroup(oper);
			operColumnGroup.add(cm.getColumn(i+1));
			operColumnGroup.add(cm.getColumn(i+2));
			operColumnGroup.add(cm.getColumn(i+3));
			header.addColumnGroup(operColumnGroup);
			i+=3;
		}
		popupMenu=new JPopupMenu();
		popupMenu.add(new AbstractAction("新增排产计划") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createPlan();
				
			}
		});
		popupMenu.add(new AbstractAction("删除") {
			
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
		dialog.setTitle("选择订单");
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(1, 1, 1, 1));
		dialog.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JLabel label = new JLabel("\u56FE\u53F7\uFF1A");
		panel_2.add(label);

		final JTextField textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);

		JButton searchBt = new JButton("\u67E5\u627E");
		panel_2.add(searchBt);
		searchBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText()!=null)
				panel.setBeans( Bill.loadBySearch("picid like '%"+textField.getText()+"'",false));
				
			}
		});
		
		panel.getTable().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseClicked(e);
				if(e.getClickCount()>1){
					plan.setBill(panel.getSelectBean());
					plan.initOperationsByNotOut();
					mode.addPlan(plan);
					setRowSelectionInterval(getRowCount()-1,getRowCount()-1);
					scrollRowToVisible(getRowCount()-1);
					dialog.dispose();
				}
			}


		}); 

		dialog.getContentPane().add(panel,BorderLayout.CENTER);
		dialog.getContentPane().add(panel_2,BorderLayout.NORTH);
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
			// TODO 自动生成的构造函数存根
		}

		public Highter(HighlightPredicate predicate) {
			super(predicate);
			// TODO 自动生成的构造函数存根
		}

		@Override
		protected Component doHighlight(Component renderer,
				ComponentAdapter adapter) {
			applyBackground(renderer, adapter);
			applyForeground(renderer, adapter);
			return renderer;
		}
		protected void applyBackground(Component renderer, ComponentAdapter adapter) {
			
			Color color = adapter.isSelected() ?  plans.get(adapter.row).isBig()?Color.MAGENTA:getSelectedBackground()
					: plans.get(adapter.row).isBig()?Color.red:adapter.isEditable()?getSelectedBackground():Color.LIGHT_GRAY;
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
	public void setPlans(Vector<Plan> plans) {
		this.plans = plans;
		mode.setPlans(plans);
	}

}
