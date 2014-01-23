package ui.customComponet;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;


public class SqlTableModel extends AbstractTableModel {
	private RowSet rowSet;

	public SqlTableModel(CachedRowSet rowSet) {
		this.rowSet = rowSet;
	}

	public RowSet getRowSet() {
		return rowSet;
	}

	public void setRowSet(RowSet rowSet) {
		this.rowSet = rowSet;
		fireTableStructureChanged();
	}

	public void exportToExl(String title) throws IOException, SQLException {
		if (rowSet == null)
			return;
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
			FileOutputStream os = new FileOutputStream(new File(filename));
			RsTableModel.createXls(rowSet,os);
			Desktop.getDesktop().open(new File(filename));
		}
	}

	@Override
	public int getColumnCount() {
		if (rowSet == null)
			return 0;
		try {
			return rowSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getRowCount() {
		// TODO 自动生成的方法存根
		if (rowSet == null)
			return 0;
		try {
			rowSet.last();
			return rowSet.getRow();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (rowSet == null)
			return null;
		try {
			rowSet.absolute(row + 1);
			return rowSet.getObject(col + 1);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (rowSet == null)
			return null;
		try {
			switch (rowSet.getMetaData().getColumnType(columnIndex + 1)) {

			case Types.LONGNVARCHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
			case Types.NCHAR:
			case Types.NVARCHAR:
			case Types.CHAR:
				return String.class;
			case Types.DOUBLE:
			case Types.DECIMAL:
				return Double.class;
			case Types.FLOAT:
				return Float.class;

			case Types.JAVA_OBJECT:
				return Object.class;
			case Types.BOOLEAN:
				return Boolean.class;

			case Types.BIT:
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				return Integer.class;
//			case Types.BIGINT:
//				return BigInt.class;

			case Types.NULL:
				return null;

			case Types.ROWID:
				return RowId.class;
			case Types.DATE:
			case Types.TIMESTAMP:
				return Date.class;

			case Types.NUMERIC:
				return Number.class;
			case Types.ARRAY:
				return Array.class;
			case Types.REAL:
				return Double.class;
			default:
				return Object.class;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		if (rowSet == null)
			return null;
		// TODO 自动生成的方法存根
		try {
			return rowSet.getMetaData().getColumnLabel(column + 1);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	public void removeRow(int row) throws SQLException {
		rowSet.absolute(row);
		rowSet.deleteRow();
		fireTableDataChanged();

	}


}
