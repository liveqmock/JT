package com.mao.jf.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class SessionData {
	private static Connection connection;

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String connectionUrl = "jdbc:sqlserver://jxjtjm.com:5432;databaseName=bill;user=rdp;password=mao564864;";
//				String connectionUrl = "jdbc:sqlserver://192.168.1.103:1433;databaseName=bill;user=rdp;password=mao564864;";
				connection = DriverManager.getConnection(connectionUrl);
				

			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static CachedRowSet getRwoSet(String sql) throws SQLException {

		RowSetFactory rowSetFactory = RowSetProvider.newFactory();
		CachedRowSet rowset = rowSetFactory.createCachedRowSet();
	    try(Statement st=getConnection().createStatement();
	    		ResultSet rs=st.executeQuery(sql);
	    		){
	    
	    	rowset.populate(rs);
	    }
	    		
		return rowset;
	}
}