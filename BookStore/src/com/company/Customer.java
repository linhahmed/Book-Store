package com.company;//package book_store;
import java.util.*;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void set_isManager(int bit) {
		isManager = bit;
	}
	public int get_isManager() {
		return isManager;
	}
	
	public List<String> search_for_book(String atrr , String val) throws SQLException{
		List<String> books = new ArrayList<>();
		try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
		
		ResultSet myRs = null ;
		 String query = "select * from book where " +atrr+ " = " + "'" + val+ "'" + ";";
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
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
			
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
	public long total_price () {
		long res= 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
			
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
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
			
			while(cart.size()>0) {
				int cartquantity=cart.get(0).right;
				String query = "Update book set Quantity=Quantity -"+"'"+cartquantity+"'"+ "where ISBN = "  + "'" + cart.get(0).left+ "'" + ";";
				Statement sta = connection.createStatement();
				sta.executeUpdate(query);
				cart.remove(0);
			}
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
	}
}