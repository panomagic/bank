package entity;

import javax.persistence.*;

@Entity
@Table(name = "accounttypes")
public class AccountType {
    public AccountType() {
        //no-args constructor
    }

    @Id
    @GeneratedValue
    @Column(name = "accTypeID")
    private int accTypeID;

    public int getAccTypeID() {
        return accTypeID;
    }

    public void setAccTypeID(int accTypeID) {
        this.accTypeID = accTypeID;
    }

    @Column(name = "accountType")
    @Enumerated(EnumType.STRING)
    private AccType accountTypeName;

    public AccType getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(AccType accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
