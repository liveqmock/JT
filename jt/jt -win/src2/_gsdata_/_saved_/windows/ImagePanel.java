package windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXImageView;

public class ImagePanel extends JPanel {
	private JXImageView imageView;
	private JSlider slider;
	private MyImageView myImageView;
	public ImagePanel(MyImageView myImageView) {
		super();
		this.myImageView=myImageView;
		try {
			createContents();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	public void  createContents() throws IOException {
		setLayout(new BorderLayout());
		imageView = new JXImageView();
		imageView.setImage(myImageView.getImage());
		imageView.setExportFormat(".jpg");
		imageView.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				imageView.setScale(imageView.getScale()
						+ e.getPreciseWheelRotation() / 2);

			}
		});
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		add(imageView, BorderLayout.CENTER);

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
		slider.setToolTipText("拖动以缩放图片");
		panel.add(new JLabel("缩放图片:"));
		panel.add(slider);

		JButton button = new JButton("保存图纸");
		panel.add(button);
		horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		JButton button2 = new JButton("打印图纸");
		panel.add(button2);
		button2.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				myImageView.printImage();
			}

		});
		button.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				myImageView.saveImage();
			}
		});
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel.add(horizontalGlue_1);

	}
}
