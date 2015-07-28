package beans;

public class AccountType {
    public AccountType() {
    }

    private int accTypeID;

    public int getAccTypeID() {
        return accTypeID;
    }

    public void setAccTypeID(int accTypeID) {
        this.accTypeID = accTypeID;
    }

    private AccType accountType;

    public AccType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccType accountType) {
        this.accountType = accountType;
    }
}
