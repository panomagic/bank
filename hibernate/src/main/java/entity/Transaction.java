package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    public Transaction() {
        //no-args constructor
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "currencies_currencyID")
    private int currencyID;

    @Column(name = "clients_payerID")
    private int payerID;

    @Column(name = "accounts_payerAccID")
    private int payerAccID;

    @Column(name = "clients_recipientID")
    private int recipientID;

    @Column(name = "accounts_recipientAccID")
    private int recipientAccID;

    @Column(name = "transactionTypes_transTypeID")
    private int transTypeID;

    @Column(name = "trans_datetime")
    private Date transDateTime;

    @Column(name = "sum")
    private BigDecimal sum;

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    public int getPayerID() {
        return payerID;
    }

    public void setPayerID(int payerID) {
        this.payerID = payerID;
    }

    public int getPayerAccID() {
        return payerAccID;
    }

    public void setPayerAccID(int payerAccID) {
        this.payerAccID = payerAccID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
    }

    public int getRecipientAccID() {
        return recipientAccID;
    }

    public void setRecipientAccID(int recipientAccID) {
        this.recipientAccID = recipientAccID;
    }

    public int getTransTypeID() {
        return transTypeID;
    }

    public void setTransTypeID(int transTypeID) {
        this.transTypeID = transTypeID;
    }

    public Date getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(Date transDateTime) {
        this.transDateTime = transDateTime;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}