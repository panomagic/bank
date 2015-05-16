package bean;

import java.math.BigDecimal;

public class Transaction {

    public Transaction() {
    }

    private int transID;
    private int currencyID;
    private int payerID;
    private int payerAccID;
    private int recipientID;
    private int recipientAccID;
    private int transTypeID;
    private BigDecimal sum;

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
