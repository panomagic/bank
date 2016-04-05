package entity;

import javax.persistence.*;

@Entity
@Table(name = "transactiontypes")
public class TransactionType {
    public TransactionType() {
        //no-args constructor
    }

    @Id
    @GeneratedValue
    @Column(name = "transTypeID")
    private int transTypeID;

    @Column(name = "transType")
    private String transType;

    public int getTransTypeID() {
        return transTypeID;
    }

    public void setTransTypeID(int transTypeID) {
        this.transTypeID = transTypeID;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
