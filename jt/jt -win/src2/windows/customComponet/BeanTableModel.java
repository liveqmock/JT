package windows.customComponet;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Vector;

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

import com.mao.jf.beans.ChinaAno;

public class BeanTableModel<T> extends AbstractTableModel  {
	private Vector<T> beans;
	private LinkedList<String> heads = new LinkedList<String>();
	private LinkedList<Method> methods = new LinkedList<Method>();

	public BeanTableModel(Vector<T> beans) {
		super();
		if (beans != null)
			setBeans(beans);
		
	}
	public void move(int from,int to) {
		T bean = beans.remove(from);
		beans.add(to,bean);
		this.fireTableDataChanged();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return heads.get(column);
	}

	public T getSelectBean(int i) {
		return (T) beans.elementAt(i);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return heads.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return beans == null ? 0 : beans.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Object object = null;
		try {
			object = methods.get(arg1).invoke(beans.elementAt(arg0));
			if (object instanceof Date) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				object = sf.format(object);
			}
		} catch (IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {

		}
		return object;
	}

	/**
	 * @return the beans
	 */
	public Vector<T> getBeans() {
		return beans;
	}

	/**
	 * @param beans
	 *            the beans to set
	 */
	public void setBeans(Vector<T> beans) {
		if(this.beans==null){

			this.beans = beans;
			if (this.beans != null && beans.size() > 0) {
				Method[] method = beans.iterator().next().getClass().getMethods();
				TreeMap<Integer, String> headTreeMap = new TreeMap<Integer, String>();
				TreeMap<Integer, Method> methodTreeMap = new TreeMap<Integer, Method>();
				for (Method m : method) {
					if (m.isAnnotationPresent(ChinaAno.class)) {
						headTreeMap.put(m.getAnnotation(ChinaAno.class).order(), m
								.getAnnotation(ChinaAno.class).str());
						methodTreeMap.put(m.getAnnotation(ChinaAno.class).order(),
								m);

					}
				}
				heads=null;
				methods=null;
				heads = new LinkedList<String>(headTreeMap.values());
				methods = new LinkedList<Method>(methodTreeMap.values());
			}
			fireTableStructureChanged();
		}else{
			this.beans=beans;
		}
		fireTableDataChanged();
	}

	/**
	 * @return the heads
	 */
	public LinkedList<String> getHeads() {
		return this.heads;
	}

	/**
	 * @return the methods
	 */
	public LinkedList<Method> getMethods() {
		return this.methods;
	}

	public void exportToExl(String title) throws IOException {
		if(heads.size()==0)return;
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
			for(int c=0;c<heads.size();c++){
				Cell cell=row.createCell(c);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(heads.get(c));
			}
			
			for (T bean : beans) {
				int c = 0;
				row= sheet.createRow(rowNo++);
				for (Method method : methods) {
					Cell cell = row.createCell(c++);
						try {
						Object object = method.invoke(bean);
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
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e1) {
						cell.setCellValue("");
						cell.setCellStyle(style);
					}

				}
			}

			for(int i=0;i<methods.size();i++)
				sheet.autoSizeColumn(i);
			FileOutputStream os = new FileOutputStream(new File(filename));
		    wb.write(os);
		    os.close();
			
			Desktop.getDesktop().open(new File(filename));
		}
	}

	public void removeRow(int row) {
		try {
			Method removeMethod = getSelectBean(row).getClass().getMethod(
					"remove");
			System.out.println(removeMethod.getName());
			removeMethod.invoke(getSelectBean(row));

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		beans.remove(getSelectBean(row));
		fireTableRowsDeleted(row, row);
	}

	public void insertRow(int row, T t) {
		if (heads.size() == 0) {
			Method[] method = t.getClass().getMethods();
			TreeMap<Integer, String> headTreeMap = new TreeMap<Integer, String>();
			TreeMap<Integer, Method> methodTreeMap = new TreeMap<Integer, Method>();
			for (Method m : method) {
				if (m.isAnnotationPresent(ChinaAno.class)) {
					headTreeMap.put(m.getAnnotation(ChinaAno.class).order(), m
							.getAnnotation(ChinaAno.class).str());
					methodTreeMap.put(m.getAnnotation(ChinaAno.class).order(),
							m);

				}
			}
			heads = new LinkedList<String>(headTreeMap.values());
			methods = new LinkedList<Method>(methodTreeMap.values());
			this.fireTableDataChanged();
			this.fireTableStructureChanged();
		}
		if(beans.contains(t)) return;
		beans.add(t);
		fireTableRowsInserted(row, row);
	}

	// public static void main(String s[]) {
	// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	// System.err.println(sf.format(Calendar.getInstance().getTime()));
	//
	// }
}
