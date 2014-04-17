package com.mao.customLayout.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.thoughtworks.xstream.XStream;

public class DbSearch {
	private static UniversalDetector detector=new UniversalDetector(null);

	private List<ColumnField<?>> columnFields;
	private String fromString;
	private String whereString;
	private String sql;
	private String selectSql="*";
	private String whereSql="";
	private String database;

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
		sql= "select "+ selectSql + " from "+ fromString + whereSql ;
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
		ArrayList<Object> parms=new ArrayList();
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


	public  void saveAsXml() throws FileNotFoundException {
		XStream xStream = new XStream();
		xStream.alias("root", DbSearch.class);
		xStream.alias("selectList", ArrayList.class);
		xStream.alias("columnField", ColumnField.class);
		xStream.alias("option", SelectBean.class);
		xStream.useAttributeFor(String.class);
		xStream.useAttributeFor(boolean.class);
		xStream.useAttributeFor(int.class);

		//		System.out.println(xStream.toXML(this));;
		//		DbSearch dbSearch= (DbSearch) xStream.fromXML(xStream.toXML(this));
		//		for(ColumnField columnField:columns.getColumnFields())
		//			 if(columnField.getSelectList()!=null){
		//				 for(SelectBean selectBean:columnField.getSelectList())
		//					 System.out.println(selectBean.getCaption()+"\t"+selectBean.getValue());
		//			 }

	}
	public static DbSearch loadFromXml(String fileName) throws IOException {
		if(new File(fileName).exists())
			return  loadFromXml(new FileInputStream(fileName));
		else 
			return null;
	}


	public static DbSearch loadFromXml(InputStream is) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		IOUtils.copy(is, os);

		ByteArrayInputStream inputStream=new ByteArrayInputStream(os.toByteArray());
		ByteArrayInputStream inputStream2=new ByteArrayInputStream(os.toByteArray());

		String codeString = getCodeString(inputStream);
		XStream xStream = new XStream();
		xStream.alias("root", DbSearch.class);
		xStream.alias("selectList", ArrayList.class);
		xStream.alias("columnField", ColumnField.class);
		xStream.alias("option", SelectBean.class);
		xStream.useAttributeFor(String.class);
		xStream.useAttributeFor(boolean.class);
		xStream.useAttributeFor(int.class);
		DbSearch dbSearch= (DbSearch)xStream.fromXML(new InputStreamReader(inputStream2,codeString));

		is.close();
		os.close();
		inputStream.close();
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

	public  static void main(String[] a) throws FileNotFoundException {
		DbSearch dbSearch=new DbSearch();
		dbSearch.setDatabase("database");;
		dbSearch.saveAsXml();
	}
}
