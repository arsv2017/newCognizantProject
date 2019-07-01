package hello.DataBase;

import hello.Domain.User;

import java.sql.*;

public class UserDao implements Intf_UserDao{

    char ch = '"';
    public boolean saveUser(User user) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");

            con.setAutoCommit(false);


            Statement stmt = con.createStatement();
            PreparedStatement enterNewUser = null;

            String update;

            ResultSet rs = stmt.executeQuery("select username from Users where username="
                    + ch + user.getUserName()+ ch +";");


            while (rs.next()) {


                if (rs.getString(1).equals(user.getUserName()))
                    return false;


                       }



            update = "insert into Users (Username, Hash) Values(" + ch + user.getUserName() + ch + "," + user.hashCode() + ");";



            enterNewUser = con.prepareStatement(update);
            enterNewUser.executeUpdate();
            con.commit();



            con.close();

        } catch (Exception e) {

            System.out.println(e);
            return false;


        }
        return true;
    }



    public boolean findUser(User user) {


        try {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/QuoteBase?useSSL=false", "root", "root");

        con.setAutoCommit(false);


        Statement stmt = con.createStatement();
        PreparedStatement enterNewUser = null;

        String update;
        System.out.println("this comes from here!!!!");
        ResultSet rs = stmt.executeQuery("select hash from Users where username="
                + ch + user.getUserName()+ ch +";");


        while (rs.next()) {


            if (rs.getInt(1) == user.hashCode())  return true;
                else return false;
            }



        } catch (Exception e) {

            System.out.println(e);

        }

        return false;
    }
}
