package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    public Account() {
        //no-args constructor
    }
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "clients_clientID")
    private int clientID;

    @Column(name = "currencies_currencyID")
    private int currencyID;

    @Column(name = "accountTypes_accTypeID")
    private int accTypeID;

    @Column(name = "balance")
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
