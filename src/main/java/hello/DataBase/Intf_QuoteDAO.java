package hello.DataBase;

import hello.Domain.Quote;

import java.util.ArrayList;

public interface Intf_QuoteDAO {

    int saveQuote(Quote quote, String userName);

    ArrayList<Quote> showQuote(int number);

    Quote findByID(int ID);

    boolean delete (int ID);

    Quote updateQuote(int ID, String text, String userName);
}
