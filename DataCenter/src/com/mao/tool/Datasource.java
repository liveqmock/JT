package com.mao.tool;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class Datasource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6656717894987429179L;
	public static String JOB="job";
	public static String REPORT="report";
	public static String BEA="bea";
	
	public static String DB2="db2";
	public static String ORACLE="oracle";

	private static HashMap<String ,DataSource> dataSources=new HashMap<String ,DataSource>();
	public static DataSource getDataSource(String dataSourceName) throws Exception {
		DataSource dataSource = dataSources.get(dataSourceName);
		if (dataSource == null) {
			try{
				dataSource=createDataSource("db2", "192.168.1.103", "50000","phdb","db2", ".");
				dataSource.getConnection();
				dataSources.put(dataSourceName,dataSource);
			}catch (Exception e) {
				throw new Exception("不能打开数据库连接");
				
			} 
		}
		return dataSource;
		

	} 
	public static DataSource createDataSource(String databaseType,String ip,String port,String database,String user,String passwd) throws PropertyVetoException  {


				ComboPooledDataSource cpds = new ComboPooledDataSource();	
		
				if(databaseType==null||databaseType.equals(DB2)){
					cpds.setDriverClass("com.ibm.db2.jcc.DB2Driver");          
					cpds.setJdbcUrl("jdbc:db2://"+ip+":"+port+"/"+database);
		
				}else if(databaseType.equals(ORACLE)){
					cpds.setDriverClass("oracle.jdbc.OracleDriver");          
					cpds.setJdbcUrl("jdbc:oracle:thin:@"+ip+":"+port+":"+database);
				}
				cpds.setUser(user); 
				cpds.setPassword(passwd); 
				cpds.setMaxIdleTime(600);
				return cpds;
//		DruidDataSource druidDataSource=new DruidDataSource();
//		if(databaseType==null||databaseType.equals(DB2)){
//			druidDataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
//			druidDataSource.setUrl("jdbc:db2://"+ip+":"+port+"/"+database);
//		}else if(databaseType.equals(ORACLE)){
//			druidDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//			druidDataSource.setUrl("jdbc:oracle:thin:@"+ip+":"+port+":"+database);
//		}
//		druidDataSource.setUsername(user);
//		druidDataSource.setPassword(passwd);
//		druidDataSource.setMaxWait(15000);
//		return druidDataSource;
	}
	public static Connection getConnection() throws Exception {
		try{ 
			return  getDataSource("coredb").getConnection();
		}catch(Exception e){
			dataSources.remove("coredb");
			throw e;
		}
	}


//	public static Connection getConnection(String databaseType,String ip,int port,String database,String user,String passwd) throws PropertyVetoException, SQLException {
//		String name=ip+"_"+port+"_"+database;
//		DataSource dataSource = dataSources.get(name);
//		if (dataSource == null) {				
//			dataSource=createDataSource(databaseType,ip, String.valueOf(port), database, user, passwd);
//			dataSources.put(name,dataSource);
//
//		}
//		return dataSource.getConnection();
//
//	}

}









