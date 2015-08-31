package beans;

import java.math.BigDecimal;

public class Account implements Identified<Integer> {

    public Account() {
        //no-args constructor
    }

    private Integer id;
    private int clientID;
    private int currencyID;
    private int accTypeID;
    private BigDecimal balance;

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    public int getAccTypeID() {
        return accTypeID;
    }

    public void setAccTypeID(int accTypeID) {
        this.accTypeID = accTypeID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
