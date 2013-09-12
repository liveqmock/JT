/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package validation.ui;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.border.Border;

import validation.Severity;

/**
 * Class which can decorate a component when it has errors. Decoration is done
 * through providing a border that can be applied to a component.
 * 
 * @author Tim Boudreau
 */
public class ComponentDecorator {
	/**
	 * Create a border to apply to the component which shows an error. A useful
	 * way to create custom borders is to wrap the original border and paint it,
	 * then paint over it. If the insets of the border returned by this method
	 * are different than the insets of the original border, then the UI layout
	 * will "jump".
	 * <p>
	 * Severity.color() and Severity.image() are handy here.
	 * 
	 * @param c
	 *            The component
	 * @param originalBorder
	 *            The original border of the component
	 * @param severity
	 *            The severity of the problem
	 * @return A border. May not be null. To have no effect, simply return the
	 *         original border.
	 */
	public Border createProblemBorder(Component c, Border originalBorder,
			Severity severity) {
		return new ColorizingBorder(originalBorder, severity);
	}

	private static class ColorizingBorder implements Border {
		private final Border real;
		private final Severity severity;

		public ColorizingBorder(Border real, Severity severity) {
			this.real = real;
			this.severity = severity;
		}

		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			real.paintBorder(c, g, x, y, width, height);
			g.setColor(severity.color());
			Graphics2D gg = (Graphics2D) g;
			Composite composite = gg.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.05F);
			Insets ins = getBorderInsets(c);
			try {
				gg.setComposite(alpha);
				gg.fillRect(x, y, width, height);
			} finally {
				gg.setComposite(composite);
			}
			BufferedImage badge = severity.image();
			int by = (c.getHeight() / 2) - (badge.getHeight() / 2);
			int w = Math.max(2, ins.left);
			int bx = x + width - (badge.getHeight() + (w * 2));
			gg.drawRenderedImage(badge,
					AffineTransform.getTranslateInstance(bx, by));
		}

		public Insets getBorderInsets(Component c) {
			return real.getBorderInsets(c);
		}

		public boolean isBorderOpaque() {
			return false;
		}
	}

	public static final ComponentDecorator noOpComponentDecorator() {
		return new ComponentDecorator() {
			@Override
			public Border createProblemBorder(Component c,
					Border originalBorder, Severity severity) {
				return originalBorder;
			}
		};
	}

}
