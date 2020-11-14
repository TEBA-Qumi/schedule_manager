package models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "accounts")
@NamedQueries({
    @NamedQuery(
            name = "getAllAccounts",
            query = "SELECT a FROM Account AS a ORDER BY a.id DESC"
            ),
    @NamedQuery(
            name = "getAccountsCount",
            query = "SELECT COUNT(a) FROM Account AS a"
            ),
    @NamedQuery(
            name = "checkRegisteredCode",
            query = "SELECT COUNT(a) FROM Account AS a WHERE a.code = :code"
            ),
    @NamedQuery(
            name = "checkLoginCodeAndPassword",
            query = "SELECT a FROM Account AS a WHERE a.delete_flag = 0 AND a.code = :code AND a.password = :pass"
            )
})
@Entity
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "account_Id")
    private List<Account_Team> account_ids;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    //getter / setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Account_Team> getAccount_ids() {
        return account_ids;
    }

    public void setAccount_ids(List<Account_Team> account_ids) {
        this.account_ids = account_ids;
    }


}
