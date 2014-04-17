package com.mao.customLayout;


import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.sql.rowset.CachedRowSet;

import com.mao.customLayout.bean.DbSearch;
import com.mao.tool.MaoLogger;
import com.sun.rowset.CachedRowSetImpl;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class DbSearchContainer  implements Container {


	private CachedRowSet crs;
	private final HashMap<String, Class<?>> propertyTypes = new HashMap<String, Class<?>>();

	private Collection<PageChangeListener> pageChangeListeners=new HashSet<PageChangeListener>();
	private int size;
	private int pageSize;
	private Connection connection;
	private int startRow=1;
	private DbSearch dbSearch;
	private ArrayList<String> propertyIds=new ArrayList<String>();
	private ArrayList<Integer> c;
	private int columns;
	public DbSearchContainer(Connection connection, DbSearch dbSearch) {
		this(connection,dbSearch,100);
	}
	public DbSearchContainer(Connection connection, DbSearch dbSearch,int pageSize) {
		this.connection=connection;
		this.dbSearch=dbSearch;
		this.pageSize=pageSize;
		try{

			init();
		}catch(Exception e){
			System.out.println(dbSearch.getSql());
			MaoLogger.error("容器初始化失败",e);
		}
	}
	private void init() throws SQLException, ClassNotFoundException {
		crs = new CachedRowSetImpl();
		getsize();
		getProperty();
	}




	private void getsize() throws SQLException {
		crs.setCommand("select count(1) from "+ dbSearch.getFromString() +  dbSearch.getWhereSql());
		int i=1;
		for(Object object:dbSearch.getParms())
			crs.setObject(i++,object);
		crs.execute(connection);
		crs.first();
		this.size=crs.getInt(1);

	}
	public void getData() throws SQLException {
		crs.setCommand("select * from ( select ROW_NUMBER() OVER() AS ROWNUM, "+ dbSearch.getSelectSql() + " from "+ dbSearch.getFromString()  + dbSearch.getWhereSql()+") a where  ROWNUM>=? and ROWNUM<?");
		int i = 1;
		for(Object object:dbSearch.getParms())
			crs.setObject(i++,object);
		crs.setInt(dbSearch.getParms().size()+1,1);
		crs.setInt(dbSearch.getParms().size()+2,pageSize);
		crs.execute(connection);
	}
	private void getProperty() throws SQLException {
		getData();
		ResultSetMetaData metadata = crs.getMetaData();
		columns = metadata.getColumnCount();
		for (int i = 1; i <= columns; i++) {
			String columnName = metadata.getColumnLabel(i);
			Class<?> typeClass=null;
			switch (metadata.getColumnType(i)) {
			case Types.VARCHAR:
			case Types.CHAR:
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
				typeClass=String.class;
				break;

			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.TINYINT:
				typeClass=Integer.class;
				break;

			case Types.BIGINT:
				typeClass=Long.class;
				break;
			case Types.FLOAT:
				typeClass=Float.class;
				break;

			case Types.DOUBLE:
			case Types.DECIMAL:
				typeClass=double.class;
				break;

				//			case Types.DATE:
				//				typeClass=Date.class;
				//				break;
				//			case Types.TIME:
				//				typeClass=Time.class;
				//				break;
			case Types.TIMESTAMP:
				typeClass=Timestamp.class;
				break;
			case Types.NUMERIC:
			case Types.REAL:
				typeClass=Number.class;
				break;

			default:
				typeClass=String.class;
				break;
			}
			propertyTypes.put(columnName, typeClass);
			propertyIds.add(columnName);
		}
	}
	public Item getItem(Object id) {
		try {
			return new Row(id);
		} catch (SQLException e) {
			MaoLogger.error("获取数据项错误",e);
			return null;
		}
	}


	public Collection<String> getContainerPropertyIds() {
		return propertyIds;
	}

	public Collection<?> getItemIds() {
		if(c==null){
			c = new ArrayList<Integer>(size);
			for (int i = 1; i <= size; i++) {
				c.add(new Integer(i));
			}
		}
		return c;
	}

	public synchronized Property<?> getContainerProperty(Object itemId, Object propertyId) {
		if (!(itemId instanceof Integer)) {
			return null;
		}
		Object value;
		Integer id = (Integer)itemId;
		notifyListeners(new PageEvent(this,id));
		try {
			if(id>=startRow+pageSize||id<startRow){
				startRow=id/pageSize*pageSize+1;
				crs.setInt(dbSearch.getParms().size()+1,startRow);
				crs.setInt(dbSearch.getParms().size()+2,startRow+pageSize);
				crs.execute(connection);
			}
			int row = id % pageSize==0?pageSize:id % pageSize;			
			crs.absolute(row);

			value = crs.getObject(propertyIds.indexOf(propertyId)+1);

		} catch (final Exception e) {
			MaoLogger.error("获取数据失败"+propertyId,e);
			return null;
		}

		// Handle also null values from the database
		return new ObjectProperty(value,propertyTypes.get(propertyId));
	}

	public Class<?> getType(Object id) {
		return propertyTypes.get(id);
	}

	public int size() {
		return size;
	}


	public boolean containsId(Object id) {

		return c.contains(id);
	}


	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * The <code>Row</code> class implements methods of Item.
	 * 
	 * @author IT Mill Ltd.
	 * @version
	 * @since 4.0
	 */
	class Row  implements Item {

		private int id;
		private Row(Object rowId) throws SQLException {
			id=(Integer) rowId;

		}

		public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();

		}

		public Property<?> getItemProperty(Object propertyId) {
			return getContainerProperty(id, propertyId);

		}

		public Collection<String> getItemPropertyIds() {
			return propertyIds;
		}
		public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}

	public void export(ByteArrayOutputStream os) throws SQLException {

		CachedRowSet crs = new CachedRowSetImpl();
		crs.setCommand(dbSearch.getSql());
		int i=1;
		for(Object object:dbSearch.getParms())
			crs.setObject(i++,object);
		crs.execute(connection);
		ExcelExport.sqlExport(os,crs);
		crs.close();


	}


	public void addPageChangeListener(PageChangeListener listener) {
		pageChangeListeners.add(listener);
	}
	public void removePageChangeListener(PageChangeListener listener) {
		pageChangeListeners.remove(listener);
	}
	private void notifyListeners(PageEvent event) {
		for(PageChangeListener listener:pageChangeListeners)
			listener.pageChange(event);
	}



}