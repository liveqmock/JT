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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.icepdf.core.views.DocumentViewController;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

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
	private final DecimalFormat Iddf= new DecimalFormat("###,000,000");
	public PlanPdf(PicPlan plan) {
		super();
		this.plan=plan;
	}
	
	private ByteArrayOutputStream createPdf() throws DocumentException, IOException {
		underLine =new Font(BaseFont.createFont( "FZYTK.TTF" ,  BaseFont.IDENTITY_H,  false ),11f,Font.UNDERLINE, BaseColor.BLACK);  
		fontChinese =new  Font(BaseFont.createFont( "simhei.ttf" , BaseFont.IDENTITY_H,  false )  , 20.0f , Font.NORMAL, BaseColor.BLACK);  
		normal =new Font(BaseFont.createFont( "FZYTK.TTF" ,  BaseFont.IDENTITY_H,  false ),11f,Font.NORMAL, BaseColor.BLACK);  
		normalMin =new Font(BaseFont.createFont( "FZYTK.TTF" , BaseFont.IDENTITY_H,  false ),9f,Font.NORMAL, BaseColor.BLACK);  
		document = new Document(PageSize.A4.rotate());
		document.setMargins(30, 30, 30, 40);
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
		Phrase phrase=new Phrase("产品工艺及生产进度跟踪卡");
		Image img = Image.getInstance(PlanPdf.class.getResource("logo.PNG"));
		img.scalePercent(30f);
		paragraph.add(new Chunk(img,-10,-5,true));
		
		phrase.setFont(fontChinese);
		paragraph.add(phrase);
		img = Image.getInstance(PlanPdf.class.getResource("planTop.png"));
		paragraph.add(new Chunk(img,175,-20,true));
		img.scalePercent(80f);
		document.add(paragraph);
		paragraph = new Paragraph();


		String planIdString = "编号:"+Iddf.format(plan.getId()).replaceAll(",", "-");
		Chunk chunk=new Chunk(planIdString,underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);
		
		paragraph.add("    ");		
		chunk=new Chunk("客户："+plan.getPic().getBill().getCustom(),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);
		paragraph.add("    ");
		chunk=new Chunk(String.format("纳期时间：%1$tY年%1$tm月%1$td日", plan.getPic().getRequestDate()),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		paragraph.add("    ");
		chunk=new Chunk(String.format("排产日期：%1$tY年%1$tm月%1$td日", plan.getStartDate()),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		paragraph.add("    ");
		chunk=new Chunk(String.format("计划完成时间：%1$tY年%1$tm月%1$td日",plan.getEndDate()),underLine);
		chunk.setLineHeight(30);
		paragraph.add(chunk);

		
		document.add(paragraph);
		PdfPTable table = new PdfPTable(6);

		table.setWidthPercentage(100);
		table.setWidths(new int[]{2,6,2,6,3,6});
		
		PdfPCell cell= new PdfPCell(new Phrase("图 号",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		cell.setMinimumHeight(25);
		table.addCell(cell);

		cell= new PdfPCell(new Phrase(plan.getPic().getPicid(),normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase("材 质",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterialz(),normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase("牌 号",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterialCode(),normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		table.addCell(cell);

		cell= new PdfPCell(new Phrase("规 格",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		cell.setMinimumHeight(25);
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase(plan.getPic().getMeterial(),normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		table.addCell(cell);

		cell= new PdfPCell(new Phrase("数 量",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);   
		table.addCell(cell);
		
		cell= new PdfPCell(new Phrase(String.valueOf( plan.getNum()),normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);  
		table.addCell(cell);


		cell= new PdfPCell(new Phrase("更改材料牌号",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		table.addCell("");
		table.setSpacingBefore(10);

		return table;
	}

	private  PdfPTable createBodyTable() throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(15);

		table.setWidthPercentage(100);
		table.setExtendLastRow(true);
		table.setWidths(new int[]{35,35,30,70,30,20,20,20,30,20,20,30,20,30,30});
		createTableHeader(table);
		int row =1;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		PdfPCell cell=null;
		for(OperationPlan operationPlan:plan.getOperationPlanSet()){
			
				cell= new PdfPCell(new Phrase(df.format(operationPlan.getStartDate()),normal));
				cell.setMinimumHeight(30);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell= new PdfPCell(new Phrase( Iddf.format(operationPlan.getId()).replaceAll(",", "-"),normal));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);

				cell= new PdfPCell(new Phrase(operationPlan.getTechnics(),normalMin));
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
				for(int j=7;j<table.getNumberOfColumns();j++){
					cell= new PdfPCell(new Phrase(" ",normal));
					table.addCell(cell);
				}
				row++;
		}

		while(row<11){
			for(int j=0;j<table.getNumberOfColumns();j++){
				cell= new PdfPCell(new Phrase(" ",normal));
				cell.setMinimumHeight(25);
				table.addCell(cell);
			}
			row++;
		}
		cell= new PdfPCell(new Phrase("异常情况说明：",normal));
		cell.setColspan(table.getNumberOfColumns());
		cell.setMinimumHeight(25);
		table.addCell(cell);
		table.setSpacingBefore(5);

		return table;
	}

	private void createTableHeader(PdfPTable table) {
		PdfPCell cell= new PdfPCell(new Phrase("计划加\n工日期",normalMin));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 
		cell.setMinimumHeight(30);
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("工艺序号",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("工艺",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("工序内容",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);    
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("设备",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("额定单\n件工时",normalMin));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("额定调\n机时间",normalMin));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("数量",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("程序\n确认",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("自检\n确认",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("合格数",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("检验员",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 

		table.addCell(cell);
		cell= new PdfPCell(new Phrase("实际\n工时",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("操作员",normal));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 		 
		table.addCell(cell);
		cell= new PdfPCell(new Phrase("完成时间",normal));
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
		controller.openDocument(new ByteArrayInputStream(createPdf().toByteArray()),"图纸",null);
		JDialog dialog=new JDialog();
		dialog.setTitle("排产工序单预览打印"); 
		dialog.setContentPane(pdfViewPanel);
		dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		dialog.setLocationRelativeTo(null);
		controller.getDocumentViewController().setFitMode(DocumentViewController.PAGE_FIT_WINDOW_WIDTH);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
		
	}
}