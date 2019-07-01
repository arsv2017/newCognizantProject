package hello.DataBase;

import hello.Domain.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class QuoteDao implements Intf_QuoteDAO {
    char ch = '"';

    public int saveQuote(Quote quote, String userName) {
        int IDNumber = 0;


        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");

            con.setAutoCommit(false);


            Statement stmt = con.createStatement();
            PreparedStatement enterNewQuote = null;

            String update;

            ResultSet rs = stmt.executeQuery("select text from QuoteList where number=" +
                    quote.getNumber());


            while (rs.next()) {


                if (rs.getString(1).equals(quote.getText())) {
                    return -1;
                }

                if (rs.getString(1).equals("boring number")) {

                    update = "delete from QuoteList where text=" + ch + "boring number" + ch + ";";

                    enterNewQuote = con.prepareStatement(update);
                    enterNewQuote.executeUpdate();
                    con.commit();
                    continue;

                }


            }


            update = "insert into QuoteList (Number, Text, CR) Values(" +
                    +quote.getNumber() + "," + ch + quote.getText() + ch + "," + ch + userName + ch + ");";

            enterNewQuote = con.prepareStatement(update);
            enterNewQuote.executeUpdate();
            con.commit();

            rs = stmt.executeQuery("select max(ID) from Quotelist;");
            rs.next();
            IDNumber = rs.getInt(1);

            con.close();

        } catch (Exception e) {

            System.out.println(e);
            return -1;


        }
        return IDNumber;
    }


    public ArrayList<Quote> showQuote(int number) {
        String requestStatement;
        ArrayList<Quote> resultSet = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");

            if (number == -1) requestStatement = "select * from QuoteList;";
            else requestStatement = "select ID, text, CR, CH from QuoteList where number=" + number + ";";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(requestStatement);

            while (rs.next()) {


               if (number==-1) resultSet.add(new Quote(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
                else resultSet.add(new Quote(
                       rs.getInt(1), number, rs.getString(2),
                                rs.getString(3), rs.getString(4)));

            }

        } catch (Exception e) {

            System.out.println(e);

        }
        Collections.sort(resultSet);
        return resultSet;
    }


    public Quote findByID(int ID) {

        Quote quote = new Quote();
        quote.setIdNumber(-1);

        String requestStatement;

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");


            requestStatement = "select number, text, CR, CH from QuoteList where ID=" + ID + ";";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(requestStatement);

            while (rs.next()) {


                quote.setIdNumber(ID);
                quote.setNumber(rs.getInt(1));
                quote.setText(rs.getString(2));
                quote.setCR(rs.getString(3));
                quote.setCH(rs.getString(4));

            }

        } catch (Exception e) {
            System.out.println(e);
            quote.setIdNumber(-1);
        }

        return quote;
    }

    public Quote updateQuote(int ID, String text, String userName) {


        Quote quote = new Quote();

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");

            con.setAutoCommit(false);


            Statement stmt = con.createStatement();
            PreparedStatement enterNewQuote = null;


            String requestStatement = "select number, text, CR, CH from QuoteList where ID=" + ID + ";";

            ResultSet rs = stmt.executeQuery(requestStatement);

            if (!rs.next()) {
                quote.setIdNumber(-1);
                return quote;
            }
            else {

                quote.setIdNumber(ID);
                quote.setNumber(rs.getInt(1));
                quote.setText(text);
                quote.setCR(rs.getString(3));
                quote.setCH(userName);

            }

            String updateStatement = "Update QuoteList Set text=" + ch + text + ch + ", CH=" + ch + userName + ch + "where ID=" + ID + ";";


            enterNewQuote = con.prepareStatement(updateStatement);
            enterNewQuote.executeUpdate();
            con.commit();

            con.close();

        } catch (Exception e) {

            System.out.println(e);
            quote.setIdNumber(-1);
            return quote;


        }
        return quote;
    }


    public boolean delete(int ID) {

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");
            con.setAutoCommit(false);
            PreparedStatement deleteQuote = null;
            String deleteStatement = "delete from quotelist where ID=" + ID + ";";

            deleteQuote = con.prepareStatement(deleteStatement);

            deleteQuote.executeUpdate();
            con.commit();
            con.close();


        } catch (Exception e) {
            System.out.println(e);
            return false;

        }

        return true;
    }


}