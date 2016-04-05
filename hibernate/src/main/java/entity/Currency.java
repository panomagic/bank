package entity;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {
    public Currency() {
        //no-args constructor
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "currency")
    private String currencyName;

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
