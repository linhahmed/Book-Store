package book_store;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Manager extends Customer {

    public void add_book(String ISBN, String Title, String Publisher, String Publication_Year,
                         int Quantity, int Min_Quantity, int Price, String Category) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
            ResultSet myRs = null;
            String query = "insert into book values( " + "'" + ISBN + "','" + Title + "','" + Publisher + "','" +
                    Publication_Year + "'," + Quantity + "," + Min_Quantity + "," + Price + ",'" + Category + "' );";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public boolean modify_existing_book(String ISBN, int sold) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");

            ResultSet myRs = null;

            String query = "select * from book where ISBN = " + "'" + ISBN + "'" + ";";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);
            if (myRs.next()) {
                query = "Update book set Quantity=Quantity -" + "'" + sold + "'" + "where ISBN = " + "'" + ISBN + "'" + ";";
                return true;


            }
            connection.close();
            return false;


        } catch (Exception exception) {
            exception.printStackTrace();
            return false;

        }


    }

    public void promote(String userName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");

            ResultSet myRs = null;
            String query = "select * from users where userName = " + "'" + userName + "'" + ";";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);
            if (myRs.next()) {
                query = "Update users set ismanager " + "'true'" + "where userName = " + "'" + userName + "'" + ";";
            }
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();

        }


    }

    public List<Pair<String, String>> top_customers() {
        ResultSet myRs = null;
        List<Pair<String, String>> result = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime current_date = LocalDateTime.now();
            String query = "select sum(num_of_cpies),userName from sales " +
                    "group by userName " + " order by sum(num_of_cpies) desc limit 5;";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);

            while (myRs.next()) {
                result.add(new Pair<String, String>(myRs.getString("sum(num_of_cpies)"), myRs.getString("userName")));
            }
            connection.close();

            return result;

        } catch (Exception exception) {
            exception.printStackTrace();
            return result;


        }


    }

    public int total_sales() {
        ResultSet myRs = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");


            String query = "select sum(sales.num_of_cpies * book.Price) as total_sales from book natural join sales where orderDate >= date_add( curdate(),interval -3 month);";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);
            if (myRs.next())
                return Integer.parseInt(myRs.getString("total_sales"));


            connection.close();
            return 0;


        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;


        }


    }

    public List<List<String>> top_selling_books() {
        ResultSet myRs = null;
        List<List<String>> result = new ArrayList<>();


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime current_date = LocalDateTime.now();

            String query = "select sum(num_of_cpies),Title,ISBN from sales natural join book " +
                    "group by ISBN order by sum(num_of_cpies) desc limit 10;";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);
            while (myRs.next()) {
                List<String> res = new ArrayList<>();
                res.add(myRs.getString("sum(num_of_cpies)"));
                res.add(myRs.getString("Title"));
                res.add(myRs.getString("ISBN"));
                result.add(res);
            }
            connection.close();
            return result;

        } catch (Exception exception) {
            exception.printStackTrace();
            return result;


        }


    }
    public List<String> individual_Userattribute(String userName, String attr) {
		List<String> res = new ArrayList<>();
		String query="";
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			Statement sta = connection.createStatement();
			ResultSet myRs = null ;

				query = "select * from users where userName = "  + "'" + userName+ "'" + ";";
				
				 myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 res.add(myRs.getString(attr));
					 
				 
			}
			
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		return res;
	}
    
    public List<String> all_customers() {
		List<String> res = new ArrayList<>();
		String query="";
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			Statement sta = connection.createStatement();
			ResultSet myRs = null ;
				query = "select * from users where ismanager = 0;";
				
				 myRs= sta.executeQuery(query);
				 while (myRs.next()) {
					 res.add(myRs.getString("userName"));
					 
				 }
			
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
		return res;
	}
    public void confirm(String ISBN) {
    	List<String> res = new ArrayList<>();
		String query="";
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
			Statement sta = connection.createStatement();
			ResultSet myRs = null ;
				query = "delete from orders where ISBN = "+"'" + ISBN + "'" + ";";
				 sta.executeUpdate(query);
			
			 connection.close();
			}
			catch (Exception exception) {
	            exception.printStackTrace();
	        }
    }


}
