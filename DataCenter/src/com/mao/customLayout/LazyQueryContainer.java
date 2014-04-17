package com.mao.customLayout;


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class LazyQueryContainer implements Container {


	private Collection<String> propertyIds;

	private final HashMap<String, Class<?>> propertyTypes = new HashMap<String, Class<?>>();

	private CachedRowSet crs;
	private int size = 50;
	private int pageSize = 50;
	private String queryStatement;
	private Connection connection;
	private int resultSetType;
	private int resultSetConcurrency;
	/**
	 * Constructs new <code>QueryContainer</code> with the specified
	 * <code>queryStatement</code>.
	 * 
	 * @param queryStatement
	 *            Database query
	 * @param connection
	 *            Connection object
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @throws SQLException
	 *             when database operation fails
	 * @throws ClassNotFoundException
	 */
	public LazyQueryContainer(String queryStatement, Connection connection, int resultSetType, int resultSetConcurrency,int pageSize)
			throws SQLException, ClassNotFoundException {

		this.queryStatement=queryStatement;
		this.connection=connection;
		this.resultSetType=resultSetType;
		this.resultSetConcurrency=resultSetConcurrency;
		this.pageSize=pageSize;
		crs = new CachedRowSetImpl();
		crs.setPageSize(this.pageSize);
		
		crs.setCommand(queryStatement);
		crs.execute(this.connection);
		
		init();
	}

	/**
	 * Constructs new <code>QueryContainer</code> with the specified
	 * queryStatement using the default resultset type and default resultset
	 * concurrency.
	 * 
	 * @param queryStatement
	 *            Database query
	 * @param connection
	 *            Connection object
	 * @see LazyQueryContainer#DEFAULT_RESULTSET_TYPE
	 * @see LazyQueryContainer#DEFAULT_RESULTSET_CONCURRENCY
	 * @throws SQLException
	 *             when database operation fails
	 * @throws ClassNotFoundException
	 */
	public LazyQueryContainer(String queryStatement, Connection connection,int pageSize) throws SQLException, ClassNotFoundException {
		this(queryStatement, connection, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, pageSize);
	}
	public LazyQueryContainer(String queryStatement, Connection connection) throws SQLException, ClassNotFoundException {
		this(queryStatement, connection, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, 50);
	}
	public LazyQueryContainer(ResultSet rs,int pageSize) throws SQLException, ClassNotFoundException {
		this.pageSize=pageSize;
		crs = new CachedRowSetImpl();
		crs.setPageSize(this.pageSize);
		
		crs.populate(rs);
		init();
		
	}
	/**
	 * Fills the Container with the items and properties. Invoked by the
	 * constructor.
	 * 
	 * @throws SQLException
	 *             when parameter initialization fails.
	 * @throws ClassNotFoundException
	 * @see QueryContainer#QueryContainer(String, Connection, int, int).
	 */
	private void init() throws SQLException, ClassNotFoundException {
		
		this.size=crs.size();
		ResultSetMetaData metadata = crs.getMetaData();
		final int count = metadata.getColumnCount();
		final ArrayList<String> list = new ArrayList<String>(count);
		for (int i = 1; i <= count; i++) {
			String columnName = metadata.getColumnLabel(i);
			list.add(columnName);
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

			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				typeClass=Date.class;
				break;
			case Types.NUMERIC:
			case Types.REAL:
				typeClass=Number.class;
				break;

			default:
				typeClass=String.class;
				break;
			}

			Property<?> p = new ObjectProperty(crs.getObject(i),typeClass);
			propertyTypes.put(columnName, typeClass);
		}
		propertyIds = list;
	}


	/**
	 * Gets the Item with the given Item ID from the Container.
	 * 
	 * @param id
	 *            ID of the Item to retrieve
	 * @return Item Id.
	 */

	public Item getItem(Object id) {
		return new Row(id);
	}

	/**
	 * Gets the collection of propertyId from the Container.
	 * 
	 * @return Collection of Property ID.
	 */

	public Collection<String> getContainerPropertyIds() {
		return propertyIds;
	}

	/**
	 * Gets an collection of all the item IDs in the container.
	 * 
	 * @return collection of Item IDs
	 */
	
	public Collection<?> getItemIds() {
		final Collection<Integer> c = new ArrayList<Integer>(size);
		for (int i = 1; i <= size; i++) {
			c.add(new Integer(i));
		}
		return c;
	}


	public synchronized Property getContainerProperty(Object itemId, Object propertyId) {
		if (!(itemId instanceof Integer )) {
			return null;
		}
		Object value;
		try {
			crs.absolute(((Integer) itemId).intValue());
			int c = ((List)propertyIds).indexOf(propertyId)+1;
			value = crs.getObject(c);
			
		} catch (final Exception e) {
			return null;
		}

		// Handle also null values from the database
		return new ObjectProperty<Object>(value != null ? value : new String(""));
	}

	/**
	 * Gets the data type of all properties identified by the given type ID.
	 * 
	 * @param id
	 *            ID identifying the Properties
	 * 
	 * @return data type of the Properties
	 */

	public Class<?> getType(Object id) {
		return propertyTypes.get(id);
	}

	/**
	 * Gets the number of items in the container.
	 * 
	 * @return the number of items in the container.
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests if the list contains the specified Item.
	 * 
	 * @param id
	 *            ID the of Item to be tested.
	 * @return <code>true</code> if given id is in the container;
	 *         <code>false</code> otherwise.
	 */
	public boolean containsId(Object id) {
		if (!(id instanceof Integer)) {
			return false;
		}
		final int i = ((Integer) id).intValue();
		if (i < 1) {
			return false;
		}
		if (i > size) {
			return false;
		}
		return true;
	}

	/**
	 * Creates new Item with the given ID into the Container.
	 * 
	 * @param itemId
	 *            ID of the Item to be created.
	 * 
	 * @return Created new Item, or <code>null</code> if it fails.
	 * 
	 * @throws UnsupportedOperationException
	 *             if the addItem method is not supported.
	 */
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new Item into the Container, and assign it an ID.
	 * 
	 * @return ID of the newly created Item, or <code>null</code> if it fails.
	 * @throws UnsupportedOperationException
	 *             if the addItem method is not supported.
	 */
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the Item identified by ItemId from the Container.
	 * 
	 * @param itemId
	 *            ID of the Item to remove.
	 * @return <code>true</code> if the operation succeeded; <code>false</code>
	 *         otherwise.
	 * @throws UnsupportedOperationException
	 *             if the removeItem method is not supported.
	 */
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds new Property to all Items in the Container.
	 * 
	 * @param propertyId
	 *            ID of the Property
	 * @param type
	 *            Data type of the new Property
	 * @param defaultValue
	 *            The value all created Properties are initialized to.
	 * @return <code>true</code> if the operation succeeded; <code>false</code>
	 *         otherwise.
	 * @throws UnsupportedOperationException
	 *             if the addContainerProperty method is not supported.
	 */
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes a Property specified by the given Property ID from the Container.
	 * 
	 * @param propertyId
	 *            ID of the Property to remove
	 * @return <code>true</code> if the operation succeeded; <code>false</code>
	 *         otherwise.
	 * @throws UnsupportedOperationException
	 *             if the removeContainerProperty method is not supported.
	 */
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes all Items from the Container.
	 * 
	 * @return <code>true</code> if the operation succeeded; <code>false</code>
	 *         otherwise.
	 * @throws UnsupportedOperationException
	 *             if the removeAllItems method is not supported.
	 */
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
	class Row implements Item {

		Object id;

		private Row(Object rowId) {
			id = rowId;
		}

		/**
		 * Adds the item property.
		 * 
		 * @param id
		 *            ID of the new Property.
		 * @param property
		 *            Property to be added and associated with ID.
		 * @return <code>true</code> if the operation succeeded;
		 *         <code>false</code> otherwise.
		 * @throws UnsupportedOperationException
		 *             if the addItemProperty method is not supported.
		 */
		public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
			
		}

		/**
		 * Gets the property corresponding to the given property ID stored in
		 * the Item.
		 * 
		 * @param propertyId
		 *            identifier of the Property to get
		 * @return the Property with the given ID or <code>null</code>
		 */
		public Property getItemProperty(Object propertyId) {
			return getContainerProperty(id, propertyId);
			
		}

		/**
		 * Gets the collection of property IDs stored in the Item.
		 * 
		 * @return unmodifiable collection containing IDs of the Properties
		 *         stored the Item.
		 */
		public Collection<String> getItemPropertyIds() {
			return propertyIds;
		}

		/**
		 * Removes given item property.
		 * 
		 * @param id
		 *            ID of the Property to be removed.
		 * @return <code>true</code> if the item property is removed;
		 *         <code>false</code> otherwise.
		 * @throws UnsupportedOperationException
		 *             if the removeItemProperty is not supported.
		 */
		public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}







}