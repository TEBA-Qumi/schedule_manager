package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="schedules")
@NamedQueries({
    @NamedQuery(
            name = "getAllSchedules",
            query = "SELECT s FROM Schedule AS s ORDER BY s.id DESC"
            ),
    @NamedQuery(
            name = "getSchedulesCount",
            query = "SELECT COUNT(s) FROM Schedule AS s"
            ),
    @NamedQuery(
            name = "getMyAllSchedules",
            query = "SELECT s FROM Schedule AS s WHERE s.account = :account ORDER BY s.id DESC"
            ),
    @NamedQuery(
            name = "getMySchedulesCount",
            query = "SELECT COUNT(s) FROM Schedule AS s WHERE s.account = :account"
            ),
    @NamedQuery(
            name = "getMyDailySchedules",
            query = "SELECT s FROM Schedule AS s WHERE s.account = :account AND s.schedule_date = :schedule_date"
            )
})
@Entity
public class Schedule {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false )
    private Account account;

    @Column(name="schedule_date", nullable=false)
    private Date schedule_date;

    @Column(name="title", nullable=false, length=255)
    private String title;

    @Lob
    @Column(name="content", nullable=false)
    private String content;

    @Column(name="created_at", nullable=false)
    private Timestamp created_at;

    @Column(name="updated_at", nullable=false)
    private Timestamp updated_at;

    @Column(name = "finish_flag", nullable = false)
    private Integer finish_flag;

    @Column(name = "share_flag", nullable = false)
    private Integer share_flag;

    // setter / getter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(Date schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getFinish_flag() {
        return finish_flag;
    }

    public void setFinish_flag(Integer finish_flag) {
        this.finish_flag = finish_flag;
    }

    public Integer getShare_flag() {
        return share_flag;
    }

    public void setShare_flag(Integer share_flag) {
        this.share_flag = share_flag;
    }


}
