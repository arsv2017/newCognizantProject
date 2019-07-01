package hello.Controllers;

import java.util.ArrayList;

import hello.BusinessLogic.BusinessLogic;
import hello.Domain.Quote;
import hello.Domain.User;
import hello.Statements.ActionStatement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class QuoteController {


    Quote commonQuote = new Quote();
    BusinessLogic bs = new BusinessLogic();
    ActionStatement st = new ActionStatement();
    ArrayList<Quote> resultSet = new ArrayList<>();
    User commonUser = new User();


    @PostMapping(params = "getBack")
    public String enterNumber(Model model) {
        model.addAttribute("greeting", commonQuote);

        //  bs.retrievePicture(1);
        return "index";
    }





    @GetMapping("/intro")
    public String logIn(Model model) {
        st.setStatement("");
        model.addAttribute("st", st);
        model.addAttribute("user", new User());

        return "intro";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }


    @RequestMapping(params = "findNew", method = RequestMethod.POST)

    public String getNewQuote(Model model, @ModelAttribute Quote quote) throws Exception {

        st.setStatement("HERE ARE YOUR SEARCH RESULTS!!!");
        quote.setText(bs.retrieveQuote(quote.getNumber()));

        commonQuote.setNumber(quote.getNumber());
        commonQuote.setFound(quote.getFound());
        commonQuote.setText(quote.getText());
        resultSet.clear();
        resultSet.add(quote);


        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);


        return "saveView";
    }


    @RequestMapping(params = "save", method = RequestMethod.POST)
    public String saveQuote(Model model) throws Exception {

        resultSet.clear();
        int IDNumber = bs.saveQuote(commonQuote, commonUser.getUserName());
        if (IDNumber != -1) {

            st.setStatement("QUOTE SUCCESSFULLY SAVED TO QUATEBASE");
        } else st.setStatement("QUOTE IS EITHER ALREADY PRESENT IN QUOTEBASE OR NOT SAVED DUE TO AN ERROR");


        commonQuote.setIdNumber(IDNumber);
        resultSet.add(commonQuote);

        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);
        return "showView";

    }

    @RequestMapping(params = "show", method = RequestMethod.POST)
    public String showQuote(@ModelAttribute Quote quote, Model model) {

        resultSet = bs.showQuote(-1);

        if (!resultSet.isEmpty()) st.setStatement("HERE IS A LIST OF ALL QUOTES IN QUOTEBASE");
        else st.setStatement("ERROR WHILE TRYING TO SHOW QUOTELIST");


        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);

        return "showView";

    }


    @RequestMapping(params = "find", method = RequestMethod.POST)
    public String findQuoteInDataBase(@ModelAttribute Quote quote, Model model) {

        resultSet = bs.showQuote(quote.getNumber());

        if (!resultSet.isEmpty()) st.setStatement("THE LIST OF QUOTES FOUND IN QUOTE DATABASE");
        else st.setStatement("NO QUOTES FOUND IN LOCAL QUOTE DATABASE!!!");

        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);
        model.addAttribute("greeting", new Quote());

        return "findDeleteView";
    }


    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public String findQuoteByID(@ModelAttribute Quote quote, Model model) {
        resultSet.clear();
        int ID = quote.getIdNumber();

        commonQuote = bs.findByID(ID);

        if (commonQuote.getIdNumber() == -1)
            st.setStatement("NO QUOTES FOUND IN LOCAL QUOTE DATABASE OR THERE WAS AN ERROR WHILE EXECUTING SEARCH OPERATION!!!");
        else {

            st.setStatement("ARE YOU SURE YOU WANT TO PERMANENTLY DELETE THIS QUOTE FROM QUOTEBASE? PRESS DELETE TO CONFIRM");
            resultSet.add(commonQuote);
        }

        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);


        return "deleteView";

    }

    @RequestMapping(params = "deleteFinal", method = RequestMethod.POST)
    public String delete(Model model) {

        boolean deleted = bs.delete(commonQuote.getIdNumber());

        if (deleted) {

            st.setStatement("QUOTE WITH ID " + commonQuote.getIdNumber() + " HAS BEEN SUCCESSFULLY DELETED!!!");
        } else st.setStatement("AN ERROR OCCURRED WHILE DELETING QUOTE WITH ID " + commonQuote.getIdNumber());

        model.addAttribute("st", st);

        return "showView";
    }

    @RequestMapping(params = "newUser", method = RequestMethod.POST)
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "registration";

    }

    @RequestMapping(params = "registerNew", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model) {

        if (bs.saveUser(user)) st.setStatement("NEW USER SUCCESSFULLY REGISTERED!!!");
        else st.setStatement("ERROR!!! THE USER WITH SELECTED USERNAME ALREADY EXISTS. TRY AGAIN!!!");
        model.addAttribute("st", st);
        model.addAttribute("user", new User());
        return "intro";

    }

    @RequestMapping(params = "change", method = RequestMethod.POST)
    public String changeQuote(@ModelAttribute Quote quote, Model model) {
        resultSet.clear();

       quote = bs.updateQuote(quote.getIdNumber(), quote.getText(), commonUser.getUserName());

        if (quote.getIdNumber() != -1) {

            st.setStatement("QUOTE WITH ID " + quote.getIdNumber() + " HAS BEEN SUCCESSFULLY UPDATED!!!");

            resultSet.add(quote);
        } else {

            st.setStatement("AN ERROR OCCURRED WHILE UPDATING QUOTE WITH ID " + quote.getIdNumber());
        }


        model.addAttribute("st", st);
        model.addAttribute("resultSet", resultSet);

        return "showView";

    }


    @RequestMapping(params = "findUser", method = RequestMethod.POST)
    public String findUser(@ModelAttribute User user, Model model) {

        if (!bs.findUser(user)) {

            st.setStatement("LOGGING-IN UNSUCCESSFUL. TRY AGAIN!!!");
            model.addAttribute("st", st);
            return "intro";
        } else {
            model.addAttribute("greeting", commonQuote);
            commonUser.setUserName(user.getUserName());
            //  bs.retrievePicture(1);
            return "index";


        }

    }
}