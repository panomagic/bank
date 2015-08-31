package beans;

public class TransactionType {
    public TransactionType() {
        //no-args constructor
    }

    private int transTypeID;
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
