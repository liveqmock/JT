package ui.customComponet;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;

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

public class RsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CachedRowSet rs;
	private int rows;
	private int cols;

	public CachedRowSet getRs() {
		return rs;
	}

	public void setRs(CachedRowSet rs) {
		this.rs = rs;
		try {
			rs.last();
			rows=rs.getRow();
			cols=rs.getMetaData().getColumnCount();
		} catch (SQLException e) {

		}
		fireTableDataChanged();
		fireTableStructureChanged();
	}

	public RsTableModel(CachedRowSet rs) {
		this.rs = rs;
		if(rs==null)return;
		try {
			rs.last();
			rows=rs.getRow();
			cols=rs.getMetaData().getColumnCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Class<?> getColumnClass(int c) {
		try {
			switch (rs.getMetaData().getColumnType(c + 1)) {
			case java.sql.Types.SMALLINT:
			case java.sql.Types.TINYINT:
			case java.sql.Types.INTEGER:
				return int.class;
			case java.sql.Types.BIGINT:
			case java.sql.Types.ROWID:
				return long.class;
			case java.sql.Types.DECIMAL:
			case java.sql.Types.DOUBLE:
				return double.class;
			case java.sql.Types.FLOAT:
				return float.class;
			case java.sql.Types.NUMERIC:
			case java.sql.Types.REAL:
				return Number.class;
			case java.sql.Types.DATE:
				return java.util.Date.class;
			case java.sql.Types.BOOLEAN:
				return boolean.class;
			case java.sql.Types.LONGNVARCHAR:
			case java.sql.Types.LONGVARCHAR:
			case java.sql.Types.VARCHAR:
			case java.sql.Types.CHAR:
			case java.sql.Types.NCHAR:
			case java.sql.Types.NVARCHAR:
				return String.class;
			default:
				return Object.class;
			}
		} catch (SQLException e) {
			return Object.class;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		try {
			return rs.getMetaData().getColumnLabel(column + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(column);
	}

	@Override
	public int getColumnCount() {
		return cols;
	}

	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public Object getValueAt(int r, int c) {
		try {
			if(getRowCount()<=r)return null;
			if(getColumnCount()<=c)return null;
			rs.absolute(r + 1);

			if(getColumnClass(c)==String.class)
				System.err.println("S-"+rs.getString(c+1));
			else
				System.err.println(rs.getObject(c+1));
			return rs.getObject(c + 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}

	public void exportToExl(String title) throws IOException,
	SQLException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("输入要导出的Excel文件名称");
		fileChooser.setFileView(new MyFileView());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new MyFileFilter());
		fileChooser.setApproveButtonText("导出");
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filename.toLowerCase().endsWith(".xls"))
				filename += ".xls";

			FileOutputStream os=new FileOutputStream(new File(filename));
			createXls(rs, os);
			Desktop.getDesktop().open(new File(filename));
			
		}
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
						cell.setCellStyle(numericStyle);
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
		os.close();


	}
}
