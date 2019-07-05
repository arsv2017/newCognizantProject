package hello.BusinessLogic;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import hello.DataBase.*;
import hello.Domain.Quote;
import hello.Domain.User;
import hello.DataBase.Intf_QuoteDAO;

public class BusinessLogic implements BusinessLogic_Intf {

    Intf_QuoteDAO dao = new QuoteDao();
    Intf_UserDao userDAO = new UserDao();


    public boolean retrievePicture(int number) throws Exception {

        HttpResponse response = Unirest.get("https://dog.ceo/api/breeds/image/random")
                .asJson();

        System.out.println(response.getBody().toString());

        response = Unirest.get(" https://api.whatdoestrumpthink.com/api/v1/quotes").asJson();

        System.out.println(response.getBody().toString());


        return true;
    }

    public String retrieveQuote(int number) throws Exception {

      /*  String url = "https://numbersapi.p.rapidapi.com/" + number + "/trivia?fragment=true&notfound=floor&json=true";

        HttpResponse response = Unirest.get(url)
                .header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                .header("X-RapidAPI-Key", "631f009423msh482132093aa83b4p1ff41bjsn468b481c8039")

                .asJson();

*/

        String url = "http://arturssvarinskis-eval-test.apigee.net/getstarted";

        HttpResponse response = Unirest.get(url)
                // .header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                //   .header("x-apikey", "qlUhcRaiSBV3yASlnd9L0VGRqbyE8D98")

                .asJson();


        System.out.println(response.getBody().toString());
        Gson gson = new Gson();
        Quote quote = gson.fromJson(response.getBody().toString(), Quote.class);

        quote.setText(quote.getText().replace('"', '.'));


        if (!quote.getFound()) {
            quote.setNumber(number);
            quote.setText("boring number");
        }

        return quote.getText();
    }


    public int saveQuote(Quote quote, String userName) {


        return dao.saveQuote(quote, userName);

    }

    public ArrayList<Quote> showQuote(int number) {


        return (dao.showQuote(number));

    }

    public Quote findByID(int ID) {


        return dao.findByID(ID);
    }


    public boolean delete(int ID) {

        return dao.delete(ID);
    }


    public Quote updateQuote(int ID, String text, String userName) {

        String newText = text.replace('"', '.');
        return dao.updateQuote(ID, newText, userName);
    }

    public boolean saveUser(User user) {

        return userDAO.saveUser(user);
    }

    public boolean findUser(User user) {

        return userDAO.findUser(user);


    }

}
