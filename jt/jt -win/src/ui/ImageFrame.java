package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXImageView;

public class ImageFrame extends JFrame {
	private JXImageView imageView;
	private JSlider slider;

	public ImageFrame(Image image) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		imageView = new JXImageView();
		imageView.setImage(image);
		imageView.setExportFormat(".jpg");
		imageView.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				imageView.setScale(imageView.getScale()
						+ e.getPreciseWheelRotation() / 2);

			}
		});
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		getContentPane().add(imageView, BorderLayout.CENTER);

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);

		slider = new JSlider();
		slider.setValue(0);
		slider.setMinimum(-30);
		slider.setMaximum(30);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				imageView.setScale(1 + slider.getValue() / 30.0f);

			}
		});
		slider.setToolTipText("Õœ∂Ø“‘Àı∑≈Õº∆¨");
		panel.add(new JLabel("Àı∑≈Õº∆¨:"));
		panel.add(slider);

		JButton button = new JButton("\u4FDD\u5B58\u56FE\u7247");
		panel.add(button);
		button.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Image img = imageView.getImage();
				BufferedImage dst = new BufferedImage(img.getWidth(null), img
						.getHeight(null), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) dst.getGraphics();

				try {
					// smooth scaling
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_BICUBIC);
					g.drawImage(img, 0, 0, null);
				} finally {
					g.dispose();
				}
				FileDialog fd = new FileDialog((Frame) SwingUtilities
						.windowForComponent(imageView));
				fd.setTitle("±£¥ÊÕº∆¨");
				fd.setFile("Õº÷Ω" + imageView.getExportFormat());
				fd.setMode(FileDialog.SAVE);
				fd.setVisible(true);

				if (fd.getFile() != null) {
					try {
						ImageIO.write(dst, "jpeg", new File(fd.getDirectory(),
								fd.getFile()));
					} catch (IOException ex) {

					}
				}
				/*
				 * JFileChooser chooser = new JFileChooser();
				 * chooser.showSaveDialog(JXImageView.this); File file =
				 * chooser.getSelectedFile(); if(file != null) { try {
				 * ImageIO.write(dst,"png",file); } catch (IOException ex) {
				 * log.fine(ex.getMessage()); ex.printStackTrace();
				 * fireError(ex); } }
				 */
			}
		});
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);

	}

}
