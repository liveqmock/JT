package com.mao.customLayout;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

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

public class ExcelExport {
	public static OutputStream beanExport(Collection<?> beans) {
		return null;
	}
	public static OutputStream sqlExport(OutputStream os,CachedRowSet crs) {

		try {
			createXls(crs, os);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}


		return os;
	}
	public static void createXls(RowSet crs,OutputStream os) throws IOException, SQLException {

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
		for(int c=0;c<crs.getMetaData().getColumnCount();c++){
			Cell cell=row.createCell(c);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(crs.getMetaData().getColumnLabel(c+1));
		}
		crs.beforeFirst();
		while (crs.next()) {
			if(rowNo>65000) break;
			row = sheet.createRow(rowNo++);
			for (int c = 0; c < crs.getMetaData().getColumnCount(); c++) {
				Cell cell=row.createCell(c);
				if(crs.getObject(c+1)!=null){
					switch (crs.getMetaData().getColumnType(c + 1)) {
					case java.sql.Types.SMALLINT:
					case java.sql.Types.TINYINT:
					case java.sql.Types.INTEGER:
					case java.sql.Types.BIGINT:
					case java.sql.Types.ROWID:
						cell.setCellValue(crs.getDouble(c+1));
						cell.setCellStyle(integerStyle);
						break;
					case java.sql.Types.DECIMAL:
					case java.sql.Types.DOUBLE:
					case java.sql.Types.FLOAT:
					case java.sql.Types.NUMERIC:
					case java.sql.Types.REAL:
						cell.setCellValue(crs.getDouble(c+1));
						cell.setCellStyle(numericStyle);
						break;
					case java.sql.Types.DATE:
						cell.setCellValue(crs.getDate(c+1));
						cell.setCellStyle(dateStyle);
						break;
					case java.sql.Types.BOOLEAN:
						cell.setCellValue(crs.getBoolean(c+1));
						cell.setCellStyle(style);
						break;
						//					case java.sql.Types.VARBINARY:
						//					case java.sql.Types.LONGNVARCHAR:
						//					case java.sql.Types.LONGVARCHAR:
						//					case java.sql.Types.VARCHAR:
						//					case java.sql.Types.CHAR:
						//					case java.sql.Types.NCHAR:
						//					case java.sql.Types.NVARCHAR:
					default:
						cell.setCellValue(crs.getString(c+1));
						cell.setCellStyle(style);
						break;
					}
				}else{
					cell.setCellStyle(style);
				}
			}
		}



		for(int i=0;i<crs.getMetaData().getColumnCount();i++)
			sheet.autoSizeColumn(i);
		wb.write(os);
		


	}
}
