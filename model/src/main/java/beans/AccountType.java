package beans;

public class AccountType {
    public AccountType() {
        //no-args constructor
    }

    private int accTypeID;

    public int getAccTypeID() {
        return accTypeID;
    }

    public void setAccTypeID(int accTypeID) {
        this.accTypeID = accTypeID;
    }

    private AccType accountTypeName;

    public AccType getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(AccType accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
