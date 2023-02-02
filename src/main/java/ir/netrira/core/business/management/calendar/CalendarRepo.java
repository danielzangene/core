package ir.netrira.core.business.management.calendar;

import ir.netrira.core.models.management.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByDate(String date);

    List<Calendar> findAllByYearAndWeek(Integer year, Integer week);

    List<Calendar> findAllByYearAndMonth(Integer year, Integer week);

    @Query("SELECT cal.date FROM Calendar cal " +
            "where cal.year = ?1 " +
            "and cal.week = ?2")
    List<String> findAllWeekDates(@Param("year") Integer year, @Param("week") Integer week);

    @Query("SELECT cal.date FROM Calendar cal " +
            "where cal.year = ?1 " +
            "and cal.month = ?2")
    List<String> findAllMonthDates(@Param("year") Integer year, @Param("week") Integer month);

    @Query("SELECT count(cal.id) FROM Calendar cal " +
            "where cal.year = ?1 " +
            "and cal.month = ?2 " +
            "and cal.off = false " +
            "and cal.dayOfWeek <> 5")
    Integer getMonthWorkDaysNumber(@Param("year") Integer year, @Param("week") Integer month);



}
