package windows;

import static org.jdesktop.swingx.util.PaintUtils.blend;

import java.awt.Color;
import java.awt.Component;

import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

import com.mao.jf.beans.BillItem;

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
				: ((BillItem) table.getBeans().elementAt(adapter.row))
						.getTableColor();

		renderer.setBackground(blend(renderer.getBackground(), color));
	}

	/**
	 * Applies a suitable foreground for the renderer component within the
	 * specified adapter.
	 * <p>
	 * 
	 * This implementation applies its foreground or selectedfForeground color
	 * (depending on the adapter's selected state) if != null. Otherwise it does
	 * nothing.
	 * 
	 * @param renderer
	 *            the cell renderer component that is to be decorated
	 * @param adapter
	 *            the ComponentAdapter for this decorate operation
	 */
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

	/**
	 * Sets the background color of this <code>ColorHighlighter</code> and
	 * notifies registered ChangeListeners.
	 * 
	 * @param color
	 *            the background color of this <code>Highlighter</code>, or
	 *            null, to clear any existing background color
	 */
	public void setBackground(Color color) {
		if (areEqual(color, getBackground()))
			return;
		background = color;
		fireStateChanged();
	}

	/**
	 * Returns the foreground color of this <code>ColorHighlighter</code>.
	 * 
	 * @return the foreground color of this <code>ColorHighlighter</code>, or
	 *         null, if no foreground color has been set
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * Sets the foreground color of this <code>ColorHighlighter</code> and
	 * notifies registered ChangeListeners.
	 * 
	 * @param color
	 *            the foreground color of this <code>ColorHighlighter</code>, or
	 *            null, to clear any existing foreground color
	 */
	public void setForeground(Color color) {
		if (areEqual(color, getForeground()))
			return;
		foreground = color;
		fireStateChanged();
	}

	/**
	 * Returns the selected background color of this
	 * <code>ColorHighlighter</code>.
	 * 
	 * @return the selected background color of this
	 *         <code>ColorHighlighter</code>, or null, if no selected background
	 *         color has been set
	 */
	public Color getSelectedBackground() {
		return selectedBackground;
	}

	/**
	 * Sets the selected background color of this <code>ColorHighlighter</code>
	 * and notifies registered ChangeListeners.
	 * 
	 * @param color
	 *            the selected background color of this
	 *            <code>ColorHighlighter</code>, or null, to clear any existing
	 *            selected background color
	 */
	public void setSelectedBackground(Color color) {
		if (areEqual(color, getSelectedBackground()))
			return;
		selectedBackground = color;
		fireStateChanged();
	}

	/**
	 * Returns the selected foreground color of this
	 * <code>ColorHighlighter</code>.
	 * 
	 * @return the selected foreground color of this
	 *         <code>ColorHighlighter</code>, or null, if no selected foreground
	 *         color has been set
	 */
	public Color getSelectedForeground() {
		return selectedForeground;
	}

	/**
	 * Sets the selected foreground color of this <code>ColorHighlighter</code>
	 * and notifies registered ChangeListeners.
	 * 
	 * @param color
	 *            the selected foreground color of this
	 *            <code>ColorHighlighter</code>, or null, to clear any existing
	 *            selected foreground color
	 */
	public void setSelectedForeground(Color color) {
		if (areEqual(color, getSelectedForeground()))
			return;
		selectedForeground = color;
		fireStateChanged();
	}
}