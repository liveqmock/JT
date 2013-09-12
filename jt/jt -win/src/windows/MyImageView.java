package windows;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.print.PrintException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import org.icepdf.core.views.DocumentViewController;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jdesktop.swingx.JXImageView;

import windows.panels.ImagePanel;

public class MyImageView extends JScrollPane {

	private int type=1;
	private String piff;
	private JXImageView imageView;
	//	private boolean isSmbConnect;
	private String fileName;
	private SwingController controller;
	private JPanel pdfViewPanel;
	private File imgFile;
	private JDialog dialog;
	private SwingWorker swingWorker;
	public MyImageView() {
		super();

	}

	private void showImage() throws IOException {
		if(imageView==null)buildImageView();	
		URL url = new URL("http://192.168.1.103/pics/"
				+ fileName);
		imageView.setImage(url);
		setViewportView(imageView);	
	}

	private void buildImageView() {

		imageView=new JXImageView();
		imageView.setAutoscrolls(true);
		imageView.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame frame = new JFrame("图片浏览");
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
				frame.setContentPane(new ImagePanel(MyImageView.this));
				frame.setVisible(true);

			}

		});
		imageView.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				imageView.setScale(imageView.getScale()
						+ e.getPreciseWheelRotation() / 2);

			}
		});

	}

	private void showPdf() throws IOException {
		if(pdfViewPanel==null)buildPdfViewer();			
		URL url = new URL("http://192.168.1.103/pics/"
				+ fileName);
		controller.openDocument(url.openStream(),"图纸",null);
		pdfViewPanel.setPreferredSize(new Dimension(getVisibleRect().width-50, getVisibleRect().height-50));
		setViewportView(pdfViewPanel);


	}
	public void showFile(String path) {
		//		this.isSmbConnect=isSmbConnect;
		type=1;
		String a[] =path.split("\\\\");
		fileName=a[a.length - 1];
		String sss[]=fileName.split("\\.");
		String s=null;
		if(sss.length>1) s=sss[sss.length-1];
		setPiff(s);
		final String ass = s;
		if(swingWorker!=null)swingWorker.cancel(true);
		swingWorker=new SwingWorker<Void, Void>() {

			@Override
			public Void doInBackground() throws IOException {
				if (dialog == null) {
					dialog = new JDialog();
					JLabel label1 = new JLabel("正在载入图纸,请稍候。。。");
					label1.setBackground(new Color(0.5f, 0.5f, 0.5f));
					label1.setForeground(Color.red);
					label1.setBorder(new LineBorder(Color.black));
					dialog.getContentPane().add(label1);
					dialog.setUndecorated(true);
					dialog.setAlwaysOnTop(true);
					dialog.setLocationRelativeTo(MyImageView.this);

					dialog.setBounds(1,1, 130, 30);

				}
				dialog.setLocationRelativeTo(MyImageView.this);
				dialog.setVisible(true);
				try {
					if(ass!=null&&ass.equals("pdf"))
						showPdf();
					else
						showImage();
				} catch ( IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

				dialog.setVisible(false);
				return null;	
			}
		};
		swingWorker.execute();



	}
	private void buildPdfViewer() {
		controller = new SwingController();
		SwingViewBuilder factory = new SwingViewBuilder(controller);
		controller.getDocumentViewController().setFitMode(DocumentViewController.PAGE_FIT_WINDOW_WIDTH);
		controller.getDocumentViewController().setAnnotationCallback(
				new org.icepdf.ri.common.MyAnnotationCallback(
						controller.getDocumentViewController()));
		MyAnnotationCallback myAnnotationCallback = new MyAnnotationCallback(
				controller.getDocumentViewController());
		controller.getDocumentViewController().setAnnotationCallback(myAnnotationCallback);

		pdfViewPanel = factory.buildViewerPanel();

	}
	public String getPiff() {
		return piff;
	}
	public void setPiff(String piff) {
		this.piff = piff;
	}

	public void saveImage() {
		InputStream is = getIs();
		if(is!=null){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(new File("图纸"));
			fileChooser.setDialogTitle("图片文件名");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setApproveButtonText("保存");
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

				try {
					String fileString=fileChooser.getSelectedFile().getCanonicalPath();
					String[] ss=fileChooser.getSelectedFile().getName().split("\\.");
					if(ss.length<=1){
						fileString+="."+getPiff();
					}



					OutputStream os = new FileOutputStream(fileString);
					byte[] buffer = new byte[4096];  
					int bytesRead;  
					while ((bytesRead = is.read(buffer)) != -1) {  
						os.write(buffer, 0, bytesRead);  
					}   
					os.close();
					is.close();
				} catch ( IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}  

			}
		}
	}
	private InputStream getIs() {
		try {
			//			if(type==0){
			//				return new SmbFileInputStream(
			//						"smb://192.168.1.103/pics/"+fileName);
			//
			//			}else 
			//			
			if(type==1){
				return new URL("http://192.168.1.103/pics/"
					+ fileName).openStream();
			}else if(type==2){
				return new FileInputStream(imgFile);
			}
		} catch ( IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	public void printImage() {
		try {
			PrintPic.print(getIs(), getPiff());
		} catch (FileNotFoundException | PrintException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void showFile(final File imgFile) throws IOException {
		type=2;
		this.imgFile=imgFile;
		String a[] =imgFile.getCanonicalPath().split("\\\\");
		fileName=a[a.length - 1];
		String sss[]=fileName.split("\\.");
		String s=null;
		if(sss.length>1) s=sss[sss.length-1];
		setPiff(s);
		final String ss=s;
		if(swingWorker!=null)swingWorker.cancel(true);
		swingWorker=new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() throws IOException {

				if(ss!=null&&ss.equals("pdf")){
					if(pdfViewPanel==null)buildPdfViewer();
					controller.openDocument(imgFile.getAbsolutePath());
					pdfViewPanel.setPreferredSize(new Dimension(getVisibleRect().width-50, getVisibleRect().height-50));
					setViewportView(pdfViewPanel);
				}else{
					if(imageView==null)buildImageView();

					imageView.setImage(imgFile);
					setViewportView(imageView);
				}
				return null;
			}
		};
		swingWorker.execute();


	}

	public Image getImage() {
		// TODO 自动生成的方法存根
		return imageView.getImage();
	}

}