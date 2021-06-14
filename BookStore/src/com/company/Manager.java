package com.company;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import java.sql.*;
import java.util.*;

public class Manager extends Customer {

    public void add_book(String ISBN, String Title, String Publisher, String Publication_Year,
                         int Quantity, int Min_Quantity, int Price, String Category, List<String> authors) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
            Statement stmt = connection.createStatement();
            ResultSet myRs = null;
            String query = "insert into book values( " + "'" + ISBN + "','" + Title + "','" + Publisher + "','" +
                    Publication_Year + "'," + Quantity + "," + Min_Quantity + "," + Price + ",'" + Category + "' );";
            stmt.executeUpdate(query);
            connection.close();

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
            stmt = connection.createStatement();

            for (int i = 0; i < authors.size(); i++) {
                String query2 = "insert into bookauthor values('" + authors.get(i) + "','" + ISBN + "');";
                System.out.println(query2);
                stmt = connection.createStatement();
                stmt.executeUpdate(query2);
            }

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public void add_publisher(String PName, String Address, String Phone) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
            Statement stmt = connection.createStatement();
            ResultSet myRs = null;
            String query = "insert into publisher values( " + "'" + PName + "','" + Address + "','" + Phone + "');" ;
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

    public List<Pair<String, Integer>> top_customers() {
        ResultSet myRs = null;
        List<Pair<String, Integer>> result = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");

            String query = "select sum(num_of_cpies),userName from sales " +
                    "group by userName " + " order by sum(num_of_cpies) desc limit 5;";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);

            while (myRs.next()) {
                result.add(new Pair<String, Integer>(myRs.getString("sum(num_of_cpies)"), Integer.parseInt(myRs.getString("userName"))));
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

    public List<String> top_selling_books() {
        ResultSet myRs = null;
        List<String> result = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Hosney4444!");
            String query = "select sum(num_of_cpies),Title,ISBN from sales natural join book " +
                    "group by ISBN order by sum(num_of_cpies) desc limit 10;";
            Statement stmt = connection.createStatement();
            myRs = stmt.executeQuery(query);
            while (myRs.next()) {
                result.add(myRs.getString("ISBN"));
            }
            connection.close();
            return result;

        } catch (Exception exception) {
            exception.printStackTrace();
            return result;

        }


    }

    public String individual_Userattribute(String userName, String attr) {
        String res = "";
        String query = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
            Statement sta = connection.createStatement();
            ResultSet myRs = null;

            query = "select * from users where userName = " + "'" + userName + "'" + ";";
            myRs = sta.executeQuery(query);
            while (myRs.next()) {
                res+=myRs.getString(attr);
            }

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public List<String> all_customers() {
        List<String> res = new ArrayList<>();
        String query = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
            Statement sta = connection.createStatement();
            ResultSet myRs = null;
            query = "select * from users where ismanager = 0;";

            myRs = sta.executeQuery(query);
            while (myRs.next()) {
                res.add(myRs.getString("userName"));

            }

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public void confirm(String ISBN) {
        List<String> res = new ArrayList<>();
        String query = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "741963654");
            Statement sta = connection.createStatement();
            ResultSet myRs = null;
            query = "delete from orders where ISBN = " + "'" + ISBN + "'" + ";";
            sta.executeUpdate(query);

            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
