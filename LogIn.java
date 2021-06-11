package book_store;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn {
	private String username;
	private String password;
	private String lname;
	private String fname;
	private String email;
	private String phonenum;
	private String address;
	private int isManager;
	
	
	// return 0 if wrong username or password
	// return 1 if customer
	// 2 if manager
	public int check(String username , String pass) {
		int res=0;
		ResultSet myRs = null ;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			
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
