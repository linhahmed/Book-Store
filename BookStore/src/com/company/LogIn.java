package com.company;
import java.sql.*;

public class LogIn {

	

	// return 0 if wrong username or password
	// return 1 if customer
	// 2 if manager
	public int check(String username , String pass) {
		int res=0;
		ResultSet myRs = null ;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");

			 String query = "select * from users where userName = " + "'" +username+  "' and pass = "  +"'"+pass+"'" +";";
			 Statement sta = connection.createStatement();
			 myRs= sta.executeQuery(query) ;
			 while (myRs.next()) {

				 res=(Integer.parseInt(myRs.getString("ismanager")))+1;
			 }

			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		return res;
	}



}
