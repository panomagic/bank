package bean;

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

    enum AccType {DEBIT, CREDIT}

    private AccType accountType;

    public String getAccountType() {
        return this.accountType.toString();
    }

    public void setAccountType(String accountType) {
        this.accountType = AccType.valueOf(accountType);
    }
}
