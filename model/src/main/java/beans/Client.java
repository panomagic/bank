package beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Client implements Identified<Integer> {

    public Client() {
        //no-args constructor
    }

    private Integer id;
    private String fullName;
    private Gender gender;
    private Date dateOfBirth;
    private Date dateOfReg;

    private Set clientsAccounts = new HashSet();    //temp.


    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfReg() {
        return dateOfReg;
    }

    public void setDateOfReg(Date dateOfReg) {
        this.dateOfReg = dateOfReg;
    }


    public Set getClientsAccounts() {
        return clientsAccounts;
    }

    public void setClientsAccounts(Set clientsAccounts) {
        this.clientsAccounts = clientsAccounts;
    }
}