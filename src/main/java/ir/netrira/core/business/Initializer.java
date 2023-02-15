package ir.netrira.core.business;

import ir.netrira.core.application.filter.access.AccessFilter;
import ir.netrira.core.models.application.utils.Calendar;
import ir.netrira.core.business.utils.calendar.CalendarRepo;
import ir.netrira.core.business.utils.calendar.DateUtil;
import ir.netrira.core.models.application.utils.Element;
import ir.netrira.core.business.utils.element.ElementRepo;
import ir.netrira.core.business.utils.element.dto.ElementDto;
import ir.netrira.core.business.tmp.StaticValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    ElementRepo elementRepo;
    @Autowired
    CalendarRepo calendarRepo;

    private List<ElementDto> elements = new LinkedList<>();


    @PostConstruct
    private void init() {

//        initElement();
//        initCalendar();
    }

    private void initCalendar() {
        initCalendar(1395, 1, true);
        initCalendar(1396, 3, false);
        initCalendar(1397, 4, false);
        initCalendar(1398, 5, false);
        initCalendar(1399, 6, true);

        initCalendar(1400, 1, false);
        initCalendar(1401, 2, false);
        initCalendar(1402, 3, false);
        initCalendar(1403, 4, true);
        initCalendar(1404, 6, false);
        initCalendar(1405, 0, false);
    }


    private void initCalendar(Integer year, Integer firstDayOfYearWeekNumber, boolean isLeapYear) {
        boolean notInitializeYet = calendarRepo.findByDate(DateUtil.getDate(year, 1, 1)).isEmpty();
        if (notInitializeYet) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 31; day++) {
                    if (month > 6 && day == 31) continue;
                    String date = DateUtil.getDate(year, month, day);
                    Integer dayOfWeek = firstDayOfYearWeekNumber % 7;
                    Integer week = (firstDayOfYearWeekNumber / 7) + 1;
                    calendarRepo.save(
                            new Calendar()
                                    .setYear(year)
                                    .setMonth(month)
                                    .setDay(day)
                                    .setDate(date)
                                    .setDayOfWeek(dayOfWeek)
                                    .setWeek(week)
                                    .setOff(dayOfWeek == 6)
                    );
                    firstDayOfYearWeekNumber++;
                }
            }
            if (!isLeapYear)
                calendarRepo.deleteById(calendarRepo.findByDate(DateUtil.getDate(year, 12, 30)).get().getId());
            initOffOfficialDays(StaticValues.offOfficialDays.get(year));
        }
    }

    private void initOffOfficialDays(List<String> days) {
        if (Objects.nonNull(days))
            days.parallelStream().forEach(day ->
                    calendarRepo.findByDate(day).ifPresent(date -> {
                        calendarRepo.save(date.setOff(Boolean.TRUE));
                    })
            );
    }


    private void addElement() {

    }

    private void initElement() {
        addElement();
        for (ElementDto elm : elements) {
            try {
                Optional<Element> elementOptional = elementRepo.findByCode(elm.getCode());
                if (!elementOptional.isPresent()) {
                    Element element = new Element();
                    element.setRoot(elementRepo.findByCode(elm.getRootCode()).get());
                    element.setName(elm.getName());
                    element.setCode(elm.getCode());
                    elementRepo.save(element);
                    logger.info("ELEMENT PERSISTS: " + element.getCode());
                }
            } catch (Exception e) {
                logger.error("ELEMENT NOT PERSISTS: " + elm.getCode());
            }
        }
    }

}
