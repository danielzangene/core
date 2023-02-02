package ir.netrira.core.models.management;

import ir.netrira.core.models.SimpleEntity;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Accessors(chain = true)
@Setter
@Entity
@Table(name = "t_Calendar",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "c_date")
        })
public class Calendar extends SimpleEntity {
    private Integer year;
    private Integer month;
    private Integer day;

    private String date;
    private Boolean off;
    private Integer dayOfWeek;
    private Integer week;

    @Column(name = "c_date")
    public String getDate() {
        return date;
    }

    @Column(name = "c_off")
    public Boolean getOff() {
        return off;
    }

    @Column(name = "c_dayOfWeek")
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    @Column(name = "c_week")
    public Integer getWeek() {
        return week;
    }
    @Column(name = "c_year")
    public Integer getYear() {
        return year;
    }
    @Column(name = "c_month")
    public Integer getMonth() {
        return month;
    }
    @Column(name = "c_day")
    public Integer getDay() {
        return day;
    }
}
