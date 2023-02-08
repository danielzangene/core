package ir.netrira.core.business;

import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.utils.DateUtil;
import ir.netrira.core.business.management.calendar.CalendarRepo;
import ir.netrira.core.business.management.calendar.CalendarUtil;
import ir.netrira.core.business.management.element.TypeRepo;
import ir.netrira.core.business.management.element.dto.ElementDto;
import ir.netrira.core.business.management.element.dto.TypeDto;
import ir.netrira.core.business.tmp.StaticValues;
import ir.netrira.core.exception.BusinessException;
import ir.netrira.core.filter.filter.AccessFilter;
import ir.netrira.core.models.management.Calendar;
import ir.netrira.core.models.management.Type;
import ir.netrira.core.business.management.element.ElementRepo;
import ir.netrira.core.models.management.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    ElementRepo elementRepo;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    CalendarRepo calendarRepo;

    private List<ElementDto> elements = new LinkedList<>();
    private List<TypeDto> types = new LinkedList<>();


    @PostConstruct
    private void init() {
//        addElement();
//        addTypes();
//
//        persistType();
//        persistElement();
//
//        initCalendar(1400,1);
//        initCalendar(1401,2);
//        initCalendar(1402,3);

//        initShaHrivarDiningItems();
//        initMehrDiningItems();

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


    private void addTypes() {

    }

    private void addElement() {


    }

    private void persistType() {
        for (TypeDto tp : types) {
            try {
                Optional<Type> typeOptional = typeRepo.findByCode(tp.getCode());
                if (!typeOptional.isPresent()) {
                    Type type = new Type();
                    type.setCode(tp.getCode());
                    typeRepo.save(type);
                    logger.info("TYPE PERSISTS: " + type.getCode());
                }
            } catch (Exception e) {
                logger.error("TYPE NOT PERSISTS: " + tp.getCode());
            }
        }

    }

    private void persistElement() {
        for (ElementDto elm : elements) {
            try {
                Optional<Element> elementOptional = elementRepo.findByCode(elm.getCode());
                if (!elementOptional.isPresent()) {
                    Type type = typeRepo.findByCode(elm.getTypeCode()).orElseThrow(() -> {
                        throw new BusinessException(
                                ResponseConstant.INVALID_FOOT_WORK_LOG_ID,
                                ResponseConstantMessage.INVALID_FOOT_WORK_LOG_ID);
                    });
                    Element element = new Element();
                    element.setType(type);
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
