package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "account_team")
@NamedQueries({
    @NamedQuery(
            name = "getMyTeams",
            query = "SELECT a_t FROM Account_Team AS a_t WHERE a_t.account_Id = :account_Id"
            ),
    @NamedQuery(
            name = "getMyTeamsCount",
            query = "SELECT COUNT(a_t) FROM Account_Team AS a_t"
            ),
    @NamedQuery(
            name = "getTeam",
            query = "SELECT a_t FROM Account_Team AS a_t WHERE a_t.account_Id = :account_Id AND a_t.team_Id = :team_Id"
            ),
    @NamedQuery(
            name = "exitTeam",
            query = "SELECT a_t FROM Account_Team AS a_t WHERE a_t.team_Id = :team_Id"
            )
    })
@Entity
public class Account_Team {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_Id", nullable = false)
    private Account account_Id;

    @ManyToOne
    @JoinColumn(name = "team_Id", nullable = false)
    private Team team_Id;

    //getter / setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount_Id() {
        return account_Id;
    }

    public void setAccount_Id(Account account_Id) {
        this.account_Id = account_Id;
    }

    public Team getTeam_Id() {
        return team_Id;
    }

    public void setTeam_Id(Team team_Id) {
        this.team_Id = team_Id;
    }


}
