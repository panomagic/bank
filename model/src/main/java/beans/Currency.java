package beans;

public class Currency implements Identified<Integer> {
    public Currency() {
        //no-args constructor
    }

    private Integer id;
    private String currencyName;

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
