package hello.BusinessLogic;

import hello.Domain.Quote;
import hello.Domain.User;

import java.util.ArrayList;

public interface BusinessLogic_Intf {


    boolean retrievePicture (int number)throws Exception;

    String retrieveQuote(int number)throws Exception;

    int saveQuote(Quote quote, String userName);

    ArrayList<Quote> showQuote(int number);

    Quote findByID(int ID);

    boolean delete(int ID);

    Quote updateQuote(int ID, String text, String userName);

    boolean saveUser(User user);

    boolean findUser(User user);




}
