package ui.costPanes;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableUI;

import ui.customComponet.BeanTableModel;

import com.mao.jf.beans.OperationPlan;

public class DragDropRowTableUI<T> extends BasicTableUI {
	private boolean draggingRow = false;
	private int startDragPoint;
	private int dyOffset;

	@Override
	protected MouseInputListener createMouseInputListener() {
		return new DragDropRowMouseInputHandler();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);

		if (draggingRow) {
			g.setColor(table.getParent().getBackground());
			Rectangle cellRect = table.getCellRect(table.getSelectedRow(), 0, false);
			g.copyArea(cellRect.x, cellRect.y, table.getWidth(), table.getRowHeight(), cellRect.x, dyOffset);

			if (dyOffset < 0) {
				g.fillRect(cellRect.x, cellRect.y + (table.getRowHeight() + dyOffset), table.getWidth(), (dyOffset * -1));
			} else {
				g.fillRect(cellRect.x, cellRect.y, table.getWidth(), dyOffset);
			}
		}
	}

	class DragDropRowMouseInputHandler extends MouseInputHandler {

		private int sRow=-1;

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			sRow=table.getSelectedRow();
			startDragPoint = (int)e.getPoint().getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int fromRow = table.getSelectedRow();

			if (fromRow >= 0) {
				draggingRow = true;

				int rowHeight = table.getRowHeight();
				int middleOfSelectedRow = (rowHeight * fromRow) + (rowHeight / 2);

				int toRow = -1;
				int yMousePoint = (int)e.getPoint().getY();

				if (yMousePoint < (middleOfSelectedRow - rowHeight)) {
					// Move row up
					toRow = fromRow - 1;
				} else if (yMousePoint > (middleOfSelectedRow + rowHeight)) {
					// Move row down
					toRow = fromRow + 1;
				}
				if (toRow >= 0 && toRow < table.getRowCount()&&toRow!=fromRow) {
					
					((BeanTableModel<OperationPlan>)table.getModel()).swapBean(fromRow, toRow);

					table.setRowSelectionInterval(toRow, toRow);
					startDragPoint = yMousePoint;
				}

				dyOffset = (startDragPoint - yMousePoint) * -1;
				table.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e){
			super.mouseReleased(e);
//			if (sRow >= 0 && sRow < table.getRowCount() && table.getSelectedRow()!=sRow) 
//				table.setRowSelectionInterval(sRow, sRow);        	
			draggingRow = false;

			for(int p=0;p< table.getRowCount();p++)
				((OperationPlan)((BeanTableModel<T>) table.getModel()).getSelectBean(table
							.convertRowIndexToModel(p))).setSequence(p);;
				
			table.repaint();
		}
	}
}