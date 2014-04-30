package com.mao.jf.beans;


/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

import java.awt.Dialog.ModalityType;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PlanPdf {
	private Font underLine;
	private Font fontChinese;
	private Font normal;
	private Font normalMin;
	private PicPlan plan;
	private Document document;
	private SwingController controller;
	
	public PlanPdf(PicPlan plan) {
		super();
		this.plan=plan;
	}
	
	private ByteArrayOutputStream createPdf() throws DocumentException, IOException {
		underLine =new Font(BaseFont.createFont( "simfang.ttf" ,  "Identity-H",  false ),12f,Font.UNDERLINE, BaseColor.BLACK);  
		fontChinese =new  Font(BaseFont.createFont( "simhei.ttf" ,  "Identity-H",  false )  , 20.0f , Font.NORMAL, BaseColor.BLACK);  
		normal =new Font(BaseFont.createFont( "simfang.ttf" ,  "Identity-H",  false ),12f,Font.NORMAL, BaseColor.BLACK);  
		normalMin =new Font(BaseFont.createFont( "simfang.ttf" ,  "Identity-H",  false ),10f,Font.NORMAL, BaseColor.BLACK);  
		document = new Document(PageSize.A4.rotate());
		document.setMargins(30, 30, 40, 40);
		ByteArrayOutputStream pdfOs = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, pdfOs);

		document.open();


		document.add(createHeaderTable());
		document.add(createBodyTable());
		document.close();
		return pdfOs;
	}

	public  PdfPTable createHeaderTable() throws DocumentException, IOException {

		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		paragraph.setFont(fontChinese);
		Phrase phrase=new Phrase("��Ʒ���ռ��������ȸ��ٿ�");
		Image img = Image.getInstance(PlanPdf.class.getResource("logo.PNG"));
		img.scalePercent(30f);
		paragraph.add(new Chunk(img,-10,0,true));
		phrase.setFont(fontChinese);
		paragraph.add(phrase);
		document.add(paragraph);
		paragraph = new Paragraph();



		Chunk chunk=new Chunk("�ͻ���"+plan.getPic().getBill().getCustom(),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);
		paragraph.add("        ");
		chunk=new Chunk("����ʱ�䣺      ��   ��   ��",underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		paragraph.add("        ");
		chunk=new Chunk(String.format("�Ų����ڣ�%1$tY��%1$tm��%1$td��", plan.getStartDate()),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		paragraph.add("        ");
		chunk=new Chunk(String.format("��ʱ���ʱ�䣺%1$tY��%1$tm��%1$td��",plan.getEndDate()),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		document.add(paragraph);
		PdfPTable table = new PdfPTable(6);

		table.setWidthPercentage(100);
		table.setWidths(new int[]{2,6,2,6,3,6});
		PdfPCell cell= new PdfPCell(new Phrase("ͼ ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		cell.setMinimumHeight(25);
		table.addCell(cell);

		cell= new PdfPCell(new Phrase(plan.getPic().getPicid(),normal));
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase("�� ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterialz(),normal));
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase("�� ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterialCode(),normal));
		table.addCell(cell);

		cell= new PdfPCell(new Phrase("�� ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		cell.setMinimumHeight(25);
		table.addCell(cell);
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterialType(),normal));
		table.addCell(cell);

		cell= new PdfPCell(new Phrase("�� ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);   
		table.addCell(cell);
		cell= new PdfPCell(new Phrase(String.valueOf( plan.getNum()),normal));
		table.addCell(cell);


		cell= new PdfPCell(new Phrase("���Ĳ����ƺ�",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		table.addCell("");
		table.setSpacingBefore(10);

		return table;
	}

	private  PdfPTable createBodyTable() throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(14);

		table.setWidthPercentage(100);
		table.setWidths(new int[]{35,30,50,30,20,20,20,30,20,20,30,20,30,30});
		createTableHeader(table);
		int row =1;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		PdfPCell cell=null;
		for(OperationPlan operationPlan:plan.getOperationPlans()){
			
				cell= new PdfPCell(new Phrase(df.format(operationPlan.getStartDate()),normal));
				cell.setMinimumHeight(30);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell= new PdfPCell(new Phrase(operationPlan.getTechnics(),normal));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				cell= new PdfPCell(new Phrase(operationPlan.getTechnicsDes(),normal));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell= new PdfPCell(new Phrase(operationPlan.getOperation().getName(),normal));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell= new PdfPCell(new Phrase(String.valueOf(operationPlan.getUnitUseTime()),normal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				
				cell= new PdfPCell(new Phrase(String.valueOf(operationPlan.getPrepareTime()),normal));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				for(int j=6;j<14;j++){
					cell= new PdfPCell(new Phrase(" ",normal));
					cell.setMinimumHeight(30);
					table.addCell(cell);
				}
				row++;
//				if(row>10){
//					document.newPage();
//					document.add(createHeaderTable());
//				}
		}

		while(row<11){
			for(int j=0;j<14;j++){
				cell= new PdfPCell(new Phrase(" ",normal));
				cell.setMinimumHeight(25);
				table.addCell(cell);
			}
			row++;
		}
		cell= new PdfPCell(new Phrase("�쳣���˵����",normal));
		cell.setColspan(14);
		cell.setMinimumHeight(90);
		table.addCell(cell);
		table.setSpacingBefore(5);

		return table;
	}

	private void createTableHeader(PdfPTable table) {
		PdfPCell cell= new PdfPCell(new Phrase("�ƻ���\n������",normalMin));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 
		cell.setMinimumHeight(30);
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("����",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("��������",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("�豸",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("���\n����ʱ",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("���\n��ʱ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("����",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("����\nȷ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("�Լ�\nȷ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("�ϸ���",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("����Ա",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 

		table.addCell(cell);
		cell= new PdfPCell(new Phrase("ʵ��\n��ʱ",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("����Ա",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("���ʱ��",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		table.setHeaderRows(1);
		
	}

	public void buildPdfViewer() throws DocumentException, IOException {
		controller = new SwingController();
		SwingViewBuilder factory = new SwingViewBuilder(controller);
		controller.getDocumentViewController().setAnnotationCallback(
				new org.icepdf.ri.common.MyAnnotationCallback(
						controller.getDocumentViewController()));
		MyAnnotationCallback myAnnotationCallback = new MyAnnotationCallback(
				controller.getDocumentViewController());
		controller.getDocumentViewController().setAnnotationCallback(myAnnotationCallback);
			
		JPanel pdfViewPanel = factory.buildViewerPanel();
		controller.openDocument(new ByteArrayInputStream(createPdf().toByteArray()),"ͼֽ",null);
		JDialog dialog=new JDialog();
		dialog.setTitle("�Ų�����Ԥ����ӡ");
		dialog.setContentPane(pdfViewPanel);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		controller.getDocumentViewController().setFitMode(DocumentViewController.PAGE_FIT_WINDOW_WIDTH);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
		
	}


}