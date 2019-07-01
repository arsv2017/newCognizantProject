package hello.Domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements Comparable {

    public Quote(int idNumber, int number, String text, String CR, String CH) {

        this.number = number;
        this.text = text;
        this.idNumber = idNumber;
        this.CR=CR;
        this.CH=CH;

    }


    public Quote() {
    }

    private int number;
    private String text;

    public String getCR() {
        return CR;
    }

    public String getCH() {
        return CH;
    }

    private boolean found;
    private int idNumber;
    private String CR;
    private String CH;

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setCR(String CR) {
        this.CR = CR;
    }

    public void setCH(String CH) {
        this.CH = CH;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean getFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    @Override
    public int compareTo(Object o) {
        return number - ((Quote) o).number;
    }
}