package com.mao.jf.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class SessionData {
	private static Connection connection;

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				String user = "mao";
				String passwd = "780205";
				// String url = "jdbc:h2:file:" +
				// SessionData.class.getResource("").toURI().getPath() + "jt";
				 String url = "jdbc:h2:tcp://183.249.185.2:9092/jt";
//				 String url = "jdbc:h2:tcp://192.168.1.103:9092/jt";
//				String url = "jdbc:h2:/c:/开发工程/jt";
//				String url = "jdbc:h2:/e:/jt";
				Class.forName("org.h2.Driver");
				DriverManager.setLoginTimeout(100);
				connection = DriverManager.getConnection(url, user, passwd);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static CachedRowSet getRwoSet(String cmd) throws SQLException {

		RowSetFactory rowSetFactory = RowSetProvider.newFactory();
		CachedRowSet rowset = rowSetFactory.createCachedRowSet();
		rowset.setUrl("jdbc:h2:/c:/开发工程/jt");
		rowset.setUsername("mao");
		rowset.setPassword("780205");
		rowset.setCommand(cmd);
		return rowset;
	}
}