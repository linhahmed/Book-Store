package com.company;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class Main {
	public static void main(String []args) throws SQLException {
		Customer ob = new Customer();
		LogIn ob2 = new LogIn();
		Manager manager =new Manager();
		//int log = ob2.check("zozo","11");
		List<String> aaa = new ArrayList<>();
		long res=0;
		int price =0;

			 //aaa =ob.search_for_book("Title", "proceed");
			// ob.add_item("984",2);
			 //ob.add_item("33",1);
			// res= ob .total_price();
			// price = ob.individual_price("33");
			 //ob.checkout();
		List<ConnectionUrlParser.Pair<String,String>>customers= manager.top_customers();
		List<List<String>>books= manager.top_selling_books();
		for (int i=0;i<books.size();i++)
			System.out.println(books.get(i).get(0)+ " >>"+books.get(i).get(1) + " >> "+ books.get(i).get(2) );

		for (int i=0;i<customers.size();i++)
			System.out.println(customers.get(i).left+ " >>"+customers.get(i).right);
		System.out.println(manager.total_sales());

		/*for(int i=0;i<aaa.size() ; i++) {
			System.out.println(aaa.get(i));
		}*/
		//System.out.println(log);

	}
}
