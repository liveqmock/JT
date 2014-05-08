package com.mao.customLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mortbay.util.IO;

import sun.nio.ch.IOUtil;

import com.vaadin.data.Property;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

public class TableExcelExport {
	public static ByteArrayOutputStream exportToExl(Table table) throws IOException {



		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("sheet1"); 

		CellStyle style = wb.createCellStyle();
		CellStyle headerStyle = wb.createCellStyle();
		CellStyle dateStyle = wb.createCellStyle();
		CellStyle numericStyle = wb.createCellStyle();
		CellStyle integerStyle = wb.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		Font font = wb.createFont();
		font.setBoldweight((short) 2);
		style.setFont(font);
		headerStyle.cloneStyleFrom(style);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		DataFormat format = wb.createDataFormat();
		dateStyle.cloneStyleFrom(style);
		dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
		numericStyle.cloneStyleFrom(style);
		numericStyle.setDataFormat(format.getFormat("#,##0.00"));
		integerStyle.cloneStyleFrom(style);
		integerStyle.setDataFormat(format.getFormat("###0"));
		// Create a cell and put a date value in it.  The first cell is not styled
		// as a date.

		int rowNo=0;
		Row row = sheet.createRow(rowNo++);
		int c=0;
		for(String header: table.getColumnHeaders()){
			Cell cell=row.createCell(c++);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
		}
		for (Object bean: table.getContainerDataSource().getItemIds()) {
			row= sheet.createRow(rowNo++);
			c=0;
			for (Object propertyId:  table.getContainerPropertyIds()) {
				Cell cell = row.createCell(c);
				try {
					Property property=   table.getContainerProperty(bean, propertyId);
					if (property != null) {
						Object object = property.getValue();
						if (object instanceof Date){
							cell.setCellValue((Date)property.getValue());
							cell.setCellStyle(dateStyle);
						}else if (property.getValue() instanceof Number) {
							// BigDecimal、BigInteger、Byte、Double、Float、Integer、Long 和 Short 
							if (object instanceof BigDecimal||object instanceof Double||object instanceof Float||object instanceof Short){
								cell.setCellValue( Double.valueOf(object.toString()));
								cell.setCellStyle(numericStyle);

							}else{
								cell.setCellValue( Double.valueOf(object.toString()));
								cell.setCellStyle(integerStyle);
							}

						} else {

							cell.setCellValue(object.toString());
							cell.setCellStyle(style);
						}
					}else {

						cell.setCellType(Cell.CELL_TYPE_BLANK);
						cell.setCellStyle(style);
					}
				} catch (Exception e) {
					cell.setCellValue("error");
					cell.setCellStyle(style);
				}
				c++;
			}
		}

		for(int i=0;i< table.getColumnHeaders().length;i++)
			sheet.autoSizeColumn(i);
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		wb.write(os);
		return os;

	}
	public static StreamResource getResouce(final Table table,String fileName) throws Exception {
		
		return new StreamResource(new StreamResource.StreamSource() {


			@Override
			public InputStream getStream() {

				try {					
					ByteArrayOutputStream os=exportToExl(table);
					ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
					return in;

				}  catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
					return null;					
				}
				
				

			}
		},fileName+".xls");
	}
}
