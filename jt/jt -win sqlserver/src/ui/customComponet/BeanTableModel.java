package ui.customComponet;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.beanutils.PropertyUtils;
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

import com.mao.jf.beans.BeanMao;
import com.mao.jf.beans.annotation.Caption;
import com.mao.jf.beans.annotation.Hidden;
public class BeanTableModel<T> extends AbstractTableModel  {
	private Collection<T> beans;
	private BeanTableModelHeader[] heads;
	public BeanTableModel(Collection<T> beans,Class<T> class1) {
		this(beans,class1,null);

	}
	public BeanTableModel(Collection<T> beans,Class<T> class1, String [] header) {
		super();
		if (beans != null)
			setBeans(beans);
		TreeSet<BeanTableModelHeader> headers = new TreeSet<BeanTableModelHeader>();
		for(Field fld:class1.getDeclaredFields()){
			if(fld.getAnnotation(Hidden.class)==null){
				Caption captionAn =fld.getAnnotation(Caption.class);
				if(captionAn!=null){
					if(header!=null){
						for(String headerName:header){
							if(headerName.equals(captionAn.value())){
								headers.add(new BeanTableModelHeader(captionAn,fld.getName(),fld.getType()));
								break;
							}
						}
					}
					else headers.add(new BeanTableModelHeader(captionAn,fld.getName(),fld.getType()));

				}


			}
		}

		for(Method method:class1.getDeclaredMethods()){
			Caption captionAn =method.getAnnotation(Caption.class);
			String name=method.getName();
			if(captionAn!=null&&name.startsWith("get")){
				name=name.substring(3,4).toLowerCase()+name.substring(4,name.length());
				if(header!=null){
					for(String headerName:header){
						if(headerName.equals(captionAn.value())){
							headers.add(new BeanTableModelHeader(captionAn,name,method.getReturnType()));
							break;
						}
					}
				}
				else headers.add(new BeanTableModelHeader(captionAn,name,method.getReturnType()));

			}
		}
		heads=new BeanTableModelHeader[headers.size()];
		headers.toArray(heads);
		

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return heads[column].getCaption().value();
	}

	public T getSelectBean(int r) {		
		return (T) beans.toArray()[r];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return heads.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return beans == null ? 0 : beans.size();
	}

	@Override
	public Object getValueAt(int r, int c) {

		try {			
			return PropertyUtils.getSimpleProperty(getSelectBean(r), heads[c].getField());
		} catch (Exception e) {			
			return null;
		}
	}

	/**
	 * @return the beans
	 */
	public Collection<T> getBeans() {
		return beans;
	}

	public BeanTableModelHeader[] getHeads() {
		return heads;
	}
	/**
	 * @param beans
	 *            the beans to set
	 */
	public void setBeans(Collection<T> beans) {
		
		this.beans=beans;
		fireTableDataChanged();
	}




	public void exportToExl(String title) throws IOException {
		if(heads.length==0)return;
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
			for(int c=0;c<heads.length;c++){
				Cell cell=row.createCell(c);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(heads[c].getCaption().value());
			}

			for (T bean : beans) {
				int c = 0;
				row= sheet.createRow(rowNo++);
				for (BeanTableModelHeader header : heads) {
					Cell cell = row.createCell(c);
					try {
						Object object =  PropertyUtils.getSimpleProperty(bean, heads[c].getField());
						if (object != null) {
							if (object instanceof Date){
								cell.setCellValue((Date)object);
								cell.setCellStyle(dateStyle);
							}else if (object instanceof Number) {
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
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
							e) {
						cell.setCellValue("");
						cell.setCellStyle(style);
					}
					c++;
				}
			}

			for(int i=0;i<heads.length;i++)
				sheet.autoSizeColumn(i);
			FileOutputStream os = new FileOutputStream(new File(filename));
			wb.write(os);
			os.close();

			Desktop.getDesktop().open(new File(filename));
		}
	}

	public void removeRow(int row) {
		T bean = getSelectBean(row);
		beans.remove(bean);
		BeanMao.removeBean( bean);
		fireTableRowsDeleted(row, row);
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		
		return heads[arg0].getFldClass();
	}
	public void insertRow( T t) {
		int row =0;
		
		if(beans.contains(t)){
			Iterator<T> it = beans.iterator();
			int p=0;
			while(it.hasNext()){
				if(it.next().equals(t)){
					row=p;
					break;
				}
				p++;
			}
		}else{
			row=beans.size();
			beans.add(t);
		}			
		fireTableRowsInserted(row, row);
	}

}
