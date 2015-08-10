package beans;

public class Currency implements Identified<Integer> {
    public Currency() {
    }

    private Integer id;
    private String currency;

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
