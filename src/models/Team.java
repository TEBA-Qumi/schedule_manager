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

@Table(name="teams")
@NamedQueries({
    @NamedQuery(
            name = "getAllTeams",
            query = "SELECT t FROM Team AS t ORDER BY t.id DESC"
            ),
    @NamedQuery(
            name = "getTeamsCount",
            query = "SELECT COUNT(t) FROM Team AS t"
            ),
    @NamedQuery(
            name = "checkRegisteredTeam_code",
            query = "SELECT COUNT(t) FROM Team AS t WHERE t.team_code = :team_code"
            ),
    @NamedQuery(
            name = "checkJoinCodeAndPassword",
            query = "SELECT t FROM Team AS t WHERE t.team_code = :team_code AND t.password = :pass"
            )
})
@Entity
public class Team {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "team_Id")
    private List<Account_Team> team_ids;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="team_code", nullable=false,unique=true)
    private String team_code;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

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

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
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

    public List<Account_Team> getTeam_ids() {
        return team_ids;
    }

    public void setTeam_ids(List<Account_Team> team_ids) {
        this.team_ids = team_ids;
    }


}
