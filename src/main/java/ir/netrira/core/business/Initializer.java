package ir.netrira.core.business;

import ir.netrira.core.application.filter.access.AccessFilter;
import ir.netrira.core.application.filter.access.GroupSystemAccessRepository;
import ir.netrira.core.application.filter.access.SystemAccessRepository;
import ir.netrira.core.application.filter.auth.repository.UserRepository;
import ir.netrira.core.business.personnel.PersonnelCode;
import ir.netrira.core.business.utils.calendar.CalendarRepo;
import ir.netrira.core.business.utils.calendar.DateUtil;
import ir.netrira.core.business.utils.element.ElementRepo;
import ir.netrira.core.models.application.personnel.User;
import ir.netrira.core.models.application.systemaccess.GroupSystemAccess;
import ir.netrira.core.models.application.systemaccess.SystemAccess;
import ir.netrira.core.models.application.utils.Calendar;
import ir.netrira.core.models.application.utils.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    ElementRepo elementRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SystemAccessRepository systemAccessRepository;

    @Autowired
    GroupSystemAccessRepository groupSystemAccessRepository;

    @Autowired
    CalendarRepo calendarRepo;

    private List<Element> elements = new LinkedList<>();
    private List<SystemAccess> systemAccesses = new LinkedList<>();
    private List<GroupSystemAccess> groupSystemAccesses = new LinkedList<>();


    @PostConstruct
    private void init() {

//        initElement();
//        initSystemAccess();
//        initGroupSystemAccess();
//        initGroupSystemAccessAssign();
//        initUsers();
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
        elements.add(new Element("ماکرو سیستم", PersonnelCode.MACRO_USER, PersonnelCode.USER_ROLE));
        elements.add(new Element("مدیر سیستم", PersonnelCode.SUPER_ADMIN_USER, PersonnelCode.USER_ROLE));
        elements.add(new Element("مدیر ارشد", PersonnelCode.ADMIN_USER, PersonnelCode.USER_ROLE));
        elements.add(new Element("مدیر شعبه", PersonnelCode.MANAGER_USER, PersonnelCode.USER_ROLE));
        elements.add(new Element("تحلیلگر", PersonnelCode.ANALYST_USER, PersonnelCode.USER_ROLE));
        elements.add(new Element("مدیر مالی", PersonnelCode.FINANCIAL_MANAGER_USER, PersonnelCode.USER_ROLE));
    }

    private void addGroupSystemAccess() {
        groupSystemAccesses.add(new GroupSystemAccess("ماکرو سیستم", PersonnelCode.MACRO_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("مدیر سیستم", PersonnelCode.SUPER_ADMIN_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("ناشناس", PersonnelCode.ANONYMOUS_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("مدیر ارشد", PersonnelCode.ADMIN_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("مدیر شعبه", PersonnelCode.MANAGER_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("تحلیلگر", PersonnelCode.ANALYST_USER, Arrays.asList()));
        groupSystemAccesses.add(new GroupSystemAccess("مدیر مالی", PersonnelCode.FINANCIAL_MANAGER_USER, Arrays.asList()));
    }

    private void addSystemAccess() {
        systemAccesses.add(new SystemAccess("POST", "/api/test", "test"));
    }

    private void initUsers() {
        List<User> users = Arrays.asList(
                new User(
                        "superuser",
                        "",
                        encoder.encode("1234567890"),
                        "مدیر سیستم",
                        elementRepo.findByCode(PersonnelCode.SUPER_ADMIN_USER).get()
                ),
                new User(
                        "macro",
                        "",
                        encoder.encode("1234567890"),
                        "ماکرو سیستم",
                        elementRepo.findByCode(PersonnelCode.MACRO_USER).get()
                )
        );
        for (User user : users) {
            try {
                userRepository.save(user);
                logger.info("ELEMENT PERSISTS: " + user.getUsername());
            } catch (Exception exception) {
                logger.error("ELEMENT NOT PERSISTS: " + user.getUsername());
            }
        }
    }

        private void initElement () {
            addElement();
            for (Element e : elements) {
                try {
                    Optional<Element> elementOptional = elementRepo.findByCode(e.getCode());
                    if (!elementOptional.isPresent()) {
                        Element element = new Element();
                        element.setRootCode(e.getRootCode());
                        element.setName(e.getName());
                        element.setCode(e.getCode());
                        elementRepo.save(element);
                        logger.info("ELEMENT PERSISTS: " + element.getCode());
                    }
                } catch (Exception exception) {
                    logger.error("ELEMENT NOT PERSISTS: " + e.getCode());
                }
            }
            logger.info("ALL ELEMENTS PERSIST.");
        }

        private void initSystemAccess () {
            addSystemAccess();
            for (SystemAccess access : systemAccesses) {
                try {
                    systemAccessRepository.save(access);
                    logger.info("SystemAccess PERSISTS: " + access.getMethod() + "-" + access.getRequestUri());
                } catch (Exception exception) {
                    logger.error("SystemAccess NOT PERSISTS: " + access.getMethod() + "-" + access.getRequestUri());
                }
            }
            logger.info("ALL SystemAccess PERSIST.");
        }
        private void initGroupSystemAccess () {
            addGroupSystemAccess();
            for (GroupSystemAccess groupAccess : groupSystemAccesses) {
                try {
                    groupSystemAccessRepository.save(groupAccess);
                    logger.info("GroupSystemAccess PERSISTS: " + groupAccess.getName());
                } catch (Exception exception) {
                    logger.error("GroupSystemAccess NOT PERSISTS: " + groupAccess.getName());
                }
            }
            logger.info("ALL GroupSystemAccess PERSIST.");
        }

        private void initGroupSystemAccessAssign () {

        }

        public static interface StaticValues {
            Map<Integer, List<String>> offOfficialDays = new HashMap<>() {{
                put(1401, Arrays.asList("1401/01/01", "1401/01/02", "1401/01/03", "1401/01/04", "1401/01/13", "1401/02/03", "1401/02/13", "1401/02/14", "1401/03/14", "1401/03/15", "1401/04/19", "1401/04/27", "1401/05/16", "1401/05/17", "1401/06/26", "1401/07/03", "1401/07/05", "1401/07/13", "1401/10/06", "1401/11/15", "1401/11/22", "1401/11/29", "1401/12/17", "1401/12/29"));
            }};

        }
    }
