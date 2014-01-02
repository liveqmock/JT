package ui;

import static org.jdesktop.swingx.util.PaintUtils.blend;

import java.awt.Color;
import java.awt.Component;

import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import com.mao.jf.beans.Bill;

public class BillColorHighlighter extends AbstractHighlighter {

	private Color background;
	private Color foreground;
	private Color selectedBackground;
	private Color selectedForeground;
	private BillTable table;

	/**
	 * Instantiates a ColorHighlighter with null colors and uses the specified
	 * HighlightPredicate.
	 * 
	 * @param predicate
	 *            the HighlightPredicate to use.
	 */
	public BillColorHighlighter(BillTable table, HighlightPredicate predicate) {
		super(predicate);
		this.table = table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component doHighlight(Component renderer, ComponentAdapter adapter) {
		applyBackground(renderer, adapter);
		applyForeground(renderer, adapter);
		return renderer;
	}

	/**
	 * Applies a suitable background for the renderer component within the
	 * specified adapter.
	 * <p>
	 * 
	 * This implementation applies its background or selectedBackground color
	 * (depending on the adapter's selected state) if != null. Otherwise it does
	 * nothing.
	 * 
	 * @param renderer
	 *            the cell renderer component that is to be decorated
	 * @param adapter
	 *            the ComponentAdapter for this decorate operation
	 */
	protected void applyBackground(Component renderer, ComponentAdapter adapter) {

		Color color = adapter.isSelected() ? getSelectedBackground()
				: ((Bill) table.getBeans().elementAt(adapter.row))
						.getTableColor();

		renderer.setBackground(blend(renderer.getBackground(), color));
	}
	protected void applyForeground(Component renderer, ComponentAdapter adapter) {
		Color color = adapter.isSelected() ? getSelectedForeground()
				: getForeground();

		renderer.setForeground(blend(renderer.getForeground(), color));
	}

	// ---------------------- state

	/**
	 * Returns the background color of this <code>ColorHighlighter</code>.
	 * 
	 * @return the background color of this <code>ColorHighlighter</code>, or
	 *         null, if no background color has been set
	 */
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