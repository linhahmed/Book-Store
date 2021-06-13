package book_store;
import java.util.*;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {
	private String username;
	private String password;
	private String lname;
	private String fname;
	private String email;
	private String phonenum;
	private String address;
	private int isManager;
	List<Pair<String,Integer>> cart=new ArrayList<>();  // list of ISBNs
	
	public void set_UserName(String name) {
		username = name;
	}
	public String get_UserName() {
		return username;
	}
	
	public void set_isManager(int bit) {
		isManager = bit;
	}
	public int get_isManager() {
		return isManager;
	}
	
	public List<String> search_for_book(String atrr , String val) throws SQLException{
		List<String> books = new ArrayList<>();
		try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
		
		ResultSet myRs = null ;
		String query;
		if(val.contentEquals("")) {
			query = "select * from book ;";
		}
		else if(atrr.equals("author")) {
			query = "select * from book natural join bookauthor where " +"AuthorName"+ " = " + "'" + val+ "'" + ";";
		}
		else {
			query = "select * from book where " +atrr+ " = " + "'" + val+ "'" + ";";
		} 
		 Statement sta = connection.createStatement();
		 myRs= sta.executeQuery(query);
		 
		 while (myRs.next()) {
			 books.add(myRs.getString("ISBN"));
			 
		 }
		 connection.close();
		}
		catch (Exception exception) {
            exception.printStackTrace();
        }
		return books;
	}
	
	public List<Pair<String, Integer>> view_items() {
		return cart;
	}
	public void add_item(String ISBN,Integer quantity) {
		cart.add(new Pair<String,Integer> (ISBN, quantity));
	}
	public void remove_item(String ISBN) {
		for(int i=0;i<cart.size();i++) {
			if(cart.get(i).left.equals(ISBN)) {
				cart.remove(i);
				break;
			}
		}
	}
	public int individual_price(String ISBN) {
		int price =0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			
			ResultSet myRs = null ;
			
				String query = "select Price from book where ISBN = "  + "'" + ISBN+ "'" + ";";
				
				Statement sta = connection.createStatement();
				 myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 price+= Integer.parseInt(myRs.getString("Price"));
					 
				 }
			
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		return price;
	}
	
	public List<String> individual_attribute(String ISBN, String attr) {
		List<String> res = new ArrayList<>();
		String query="";
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			Statement sta = connection.createStatement();
			ResultSet myRs = null ;
			if(attr.equals("author")) {
				query = "select * from bookauthor natural join book where ISBN ="+"'"+ ISBN +"'"+";";
				myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 res.add(myRs.getString("AuthorName"));
					 
				 }
			}
			else {
				query = "select "+attr +" from book where ISBN = "  + "'" + ISBN+ "'" + ";";
				
				 myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 res.add(myRs.getString(attr));
					 
				 }
			}
			
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		return res;
	}
	
	
	public long total_price () {
		long res= 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			
			ResultSet myRs = null ;
			for(int i=0;i<cart.size(); i++) {
				String query = "select Price from book where ISBN = "  + "'" + cart.get(i).left+ "'" + ";";
				System.out.println(cart.get(i));
				Statement sta = connection.createStatement();
				 myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 res+= (Integer.parseInt(myRs.getString("Price"))*cart.get(i).right);
					 
				 }
			}
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		
		return res;
	}
	public void checkout() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			
			while(cart.size()>0) {
				int cartquantity=cart.get(0).right;
				String query1 = "Update book set Quantity=Quantity -"+"'"+cartquantity+"'"+ "where ISBN = "  + "'" + cart.get(0).left+ "'" + ";";
				String query2 = "insert into sales values( " + "'" + username + "','" + cart.get(0).left +
						"','" + cartquantity +  "',' " + LocalDateTime.now() +" ') ;";
	                   
				Statement sta = connection.createStatement();
				sta.executeUpdate(query1);
				sta.executeUpdate(query2);
				cart.remove(0);
				
			}
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
	}
	
	public void log_out() {
		cart.clear();
	}
	
}
