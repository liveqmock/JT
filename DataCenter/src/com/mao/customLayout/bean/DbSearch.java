package com.mao.customLayout.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.mozilla.universalchardet.UniversalDetector;





import com.mao.tool.Datasource;
import com.thoughtworks.xstream.XStream;

public class DbSearch {
	private static UniversalDetector detector=new UniversalDetector(null);

	private List<ColumnField<?>> columnFields;
	private String fromString;
	private String whereString;
	private String sql;
	private String selectSql="";
	private String whereSql="";
	private String groupSql="";
	private String orderSql="";
	private String database;
	private int type;
	private Class<?> beanClass;

	private ArrayList<Object> jpaParms;

	public String  getWhereSqlString() {
		StringBuffer selectSqlb = new StringBuffer();
		StringBuffer whereSqlb = new StringBuffer();

		for(ColumnField<?> columnField:columnFields){

			if(columnField.isSelectColumn()&&!columnField.getValueType().equals("password")){
				selectSqlb.append(columnField.getSelectColStr()+",");
			}
			if(columnField.isWhereColumn()&&columnField.getScopeType()!=null&&columnField.getValue()!=null&&!columnField.equals("")){
				whereSqlb.append(columnField.getWhereColStr()+" and ");
			}
		}
		if(whereString!=null&&!whereString.equals(""))
			whereSqlb.append(whereString+" and " );
		if(selectSqlb.length()>0)
			selectSql=selectSqlb.substring(0,selectSqlb.length()-1);

		if(whereSqlb.length()>0)
			whereSql=" where "+whereSqlb.substring(0,whereSqlb.length()-5);
		else
			whereSql="";
		return whereSql;
	}

	public String  getWhereHqlString() {
		StringBuffer selectSqlb = new StringBuffer();
		StringBuffer whereSqlb = new StringBuffer();
		int parmNo=1;
		jpaParms=new ArrayList<Object>();
		for(ColumnField<?> columnField:columnFields){

			if(columnField.isWhereColumn()&&columnField.getScopeType()!=null&&columnField.getValue()!=null&&!columnField.equals("")){
				whereSqlb.append(columnField.getWhereJpaColStr(parmNo)+" and ");
				if(columnField.getScopeType()==SCOPE.CONTAIN||columnField.getScopeType()==SCOPE.NCONTAIN)
					jpaParms.add("%"+ columnField.getValue()+"%");
				else
					jpaParms.add(columnField.getValue());

				parmNo++;
				if(columnField.getScopeType().equals(SCOPE.BETWEEN)){
					jpaParms.add( columnField.getValue2());
					parmNo++;
				}
			}
		}
		if(whereString!=null&&!whereString.equals(""))
			whereSqlb.append(whereString+" and " );
		if(selectSqlb.length()>0)
			selectSql=selectSqlb.substring(0,selectSqlb.length()-1);

		if(whereSqlb.length()>0)
			whereSql=" where "+whereSqlb.substring(0,whereSqlb.length()-5);
		else
			whereSql="";
		return whereSql;
	}
	public void createSql() {

		getWhereSqlString();
		sql= "select "+ selectSql + " from "+ fromString + whereSql+" "+groupSql+" "+orderSql ;
	}

	private List<ColumnField<?>> distinctCollection( Collection<ColumnField<?>> columnFields2) {
		Set<String> names=new HashSet<String>();
		List<ColumnField<?>> columnFieldsNew=new ArrayList<ColumnField<?>>();
		for(ColumnField<?> columnField:columnFields2){
			if(!names.contains(columnField.getName())){
				names.add(columnField.getName());
				columnFieldsNew.add(columnField);

			}
		}
		return columnFieldsNew;
	}



	public ArrayList<Object> getParms() {
		ArrayList<Object> parms=new ArrayList<>();
		for(ColumnField<?> columnField:columnFields){

			if(columnField.isWhereColumn()&&columnField.getScopeType()!=null&&columnField.getValue()!=null&&!columnField.equals("")){
				if(columnField.getValueType().equals("date"))
					parms.add( new java.sql.Date(((Date)columnField.getValue()).getTime()));
				else 
					if(columnField.getScopeType()==SCOPE.CONTAIN||columnField.getScopeType()==SCOPE.NCONTAIN)
						parms.add("%"+ columnField.getValue()+"%");
					else
						parms.add(columnField.getValue());

				if(columnField.getScopeType().equals(SCOPE.BETWEEN)){
					if(columnField.getValueType().equals("date"))
						parms.add( new java.sql.Date(((Date)columnField.getValue2()).getTime()));
					else
						parms.add( columnField.getValue2());
				}
			}
		}
		return parms;
	}


	public  String saveAsXml() throws FileNotFoundException {
		XStream xStream = new XStream();
		xStream.alias("root", DbSearch.class);
		xStream.alias("selectList", ArrayList.class);
		xStream.alias("columnField", ColumnField.class);
		xStream.alias("option", SelectBean.class);
		xStream.useAttributeFor(String.class);
		xStream.useAttributeFor(boolean.class);
		xStream.useAttributeFor(int.class);

		return xStream.toXML(this);
//		DbSearch dbSearch= (DbSearch) xStream.fromXML(xStream.toXML(this));
//		System.err.println(dbSearch.getBeanClass().getSimpleName());

	}
	public static DbSearch loadFromXml(String fileName) throws IOException {
		File file = new File(fileName);
		if(file.exists())
			return  loadFromXml(new FileInputStream(file));
		else 
			return null;
	}


	public static DbSearch loadFromXml(InputStream is) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		IOUtils.copy(is, os);

//		ByteArrayInputStream inputStream=new ByteArrayInputStream(os.toByteArray());
		ByteArrayInputStream inputStream2=new ByteArrayInputStream(os.toByteArray());

//		String codeString = getCodeString(inputStream);
		XStream xStream = new XStream();
		xStream.alias("root", DbSearch.class);
		xStream.alias("selectList", ArrayList.class);
		xStream.alias("columnField", ColumnField.class);
		xStream.alias("option", SelectBean.class);
		xStream.useAttributeFor(String.class);
		xStream.useAttributeFor(boolean.class);
		xStream.useAttributeFor(int.class);
		DbSearch dbSearch= (DbSearch)xStream.fromXML(new InputStreamReader(inputStream2,"GBK"));

		is.close();
		os.close();
//		inputStream.close();
		inputStream2.close();

		return dbSearch;

	}
	public static String getCodeString(InputStream is) throws IOException{  
		byte[] buf = new byte[4096];

		int nread;
		while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();

		String encoding = detector.getDetectedCharset();
		detector.reset();
		return encoding;  
	} 

	public List<ColumnField<?>> getColumnFields() {
		if(columnFields==null)columnFields=new ArrayList<>();
		return columnFields;
	}

	public void setColumnFields(List<ColumnField<?>> columnFields) {
		this.columnFields =distinctCollection( columnFields);
	}

	public String getFromString() {
		return fromString;
	}

	public void setFromString(String fromString) {
		this.fromString = fromString;
	}

	public String getWhereString() {
		return whereString;
	}

	public void setWhereString(String whereString) {
		this.whereString = whereString;
	}

	public String getSql() {
		return sql;
	}


	public String getSelectSql() {
		return selectSql;
	}

	public String getWhereSql() {
		return whereSql;
	}


	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public ArrayList<Object> getJpaParms() {
		return jpaParms;
	}


	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public String getGroupSql() {
		return StringUtils.isBlank(groupSql)?"":" group by "+groupSql;
	}

	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	public void getColumns(ResultSetMetaData meta) throws SQLException {

		for(int c=1;c<=meta.getColumnCount();c++){
			ColumnField<?> columnField=null;
			switch (meta.getColumnType(c)) {
			case Types.CHAR:
			case Types.NCHAR:
			case Types.VARCHAR:
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
				columnField=new ColumnField<String>();	
				columnField.setValueType("string");
				break;

			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.TINYINT:
			case Types.BIT:
				columnField=new ColumnField<Integer>();		
				columnField.setValueType("int");				
				break;		
			case Types.BIGINT:	
				columnField=new ColumnField<Long>();		
				columnField.setValueType("long");				
				break;		

			case Types.DECIMAL:
			case Types.DOUBLE:
				columnField=new ColumnField<Double>();	
				columnField.setValueType("double");
				break;

			case Types.FLOAT:
				columnField=new ColumnField<Float>();	
				columnField.setValueType("float");				
				break;
			case Types.REAL:
			case Types.NUMERIC:
				columnField=new ColumnField<BigDecimal>();	
				columnField.setValueType("number");	
			case Types.DATE:
				columnField=new ColumnField<java.sql.Date>();
				columnField.setValueType("date");						
				break;

			case Types.TIME:
				columnField=new ColumnField<java.sql.Time>();
				columnField.setValueType("time");					
				break;

			case Types.TIMESTAMP:
				columnField=new ColumnField<java.sql.Time>();
				columnField.setValueType("timestamp");					
				break;


			default:
				break;
			}
			columnField.setLabel(meta.getColumnLabel(c));
			columnField.setName(meta.getColumnName(c));
			getColumnFields().add(columnField);
		}



	}
	public String getOrderSql() {
		return StringUtils.isBlank(orderSql)?"":" order by "+orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public  static void main(String[] a) throws FileNotFoundException {
		DbSearch dbSearch=new DbSearch();
		dbSearch.setFromString("datacenter.role");
		try (Connection conn = Datasource.getDataSource("").getConnection();
				Statement	st=conn.createStatement();
				ResultSet rs = st.executeQuery("select * from core.BBFMSTLR");
				){
			dbSearch.getColumns(rs.getMetaData());
		} catch ( Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		dbSearch.saveAsXml();
		
		
		
//		ZqlParser zqlParser=new ZqlParser(new ByteArrayInputStream("select STATUS from datacenter.role bb join sss aa where ass='' and ss between 12 and 122;".getBytes()));
//		
//		System.err.println(((ZQuery)zqlParser.readStatement()).getFrom());
	}

	public String getRightSqlString() {
		// TODO 自动生成的方法存根
		return " from "+ getFromString() +  getWhereSqlString()+ getGroupSql();
	}
}
