package beans;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Identified<Integer> {

    public Transaction() {
    }

    private Integer id;
    private int currencyID;
    private int payerID;
    private int payerAccID;
    private int recipientID;
    private int recipientAccID;
    private int transTypeID;
    private Date transDateTime;
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