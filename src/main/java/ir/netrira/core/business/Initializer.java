package ir.netrira.core.business;

import ir.netrira.core.application.filter.access.AccessFilter;
import ir.netrira.core.application.utils.calendar.Calendar;
import ir.netrira.core.application.utils.calendar.CalendarRepo;
import ir.netrira.core.application.utils.calendar.DateUtil;
import ir.netrira.core.application.utils.element.Element;
import ir.netrira.core.application.utils.element.ElementRepo;
import ir.netrira.core.application.utils.element.dto.ElementDto;
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

//        Element action_types = elementRepo.save(new Element().setCode("ACTION_TYPES"));
//        Element firstType = elementRepo.save(new Element().setCode("FIRST_TYPES").setName("اولی").setRoot(action_types));
//        Element secondType = elementRepo.save(new Element().setCode("SECOND_TYPES").setName("اولی").setRoot(action_types));
        List<Element> action_types = elementRepo.findByRoot_Id("6d1e2bf4-0a39-46e4-a858-88dbe9b08ae6");
        System.out.println();
//        initElement();
//        initCalendar();
    }

    private void initCalendar() {
        initCalendar(1395,1);
        initCalendar(1396,3);
        initCalendar(1397,4);
        initCalendar(1398,5);
        initCalendar(1399,6);

        initCalendar(1400,1);
        initCalendar(1401,2);
        initCalendar(1402,3);
        initCalendar(1403,4);
        initCalendar(1404,6);
        initCalendar(1405,0);
    }


    private void initCalendar(Integer year, Integer firstDayOfYearWeekNumber) {
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
                                    .setId(date)
                                    .setDate(date)
                                    .setDayOfWeek(dayOfWeek)
                                    .setWeek(week)
                                    .setOff(dayOfWeek==6)
                    );
                    firstDayOfYearWeekNumber++;
                }
            }
            calendarRepo.deleteById(calendarRepo.findById(DateUtil.getDate(year, 12, 30)).get().getId());
            initOffOfficialDays(StaticValues.offOfficialDays.get(year));
        }
    }

    private void initOffOfficialDays(List<String> days) {
        if (Objects.nonNull(days))
            days.parallelStream().forEach(day ->
                    calendarRepo.findById(day).ifPresent(date -> {
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
