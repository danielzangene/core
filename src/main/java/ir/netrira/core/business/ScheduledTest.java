package ir.netrira.core.business;

import ir.netrira.core.application.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTest {
//    sec (0-59)
//    minute (0-59)
//    hour (0 - 23)
//    day of the month (1 - 31)
//    month (1 - 12)
//    day of the week (0 - 6)

//    sample-> */10 15-50 18 * * *
//    Every 10 seconds, minutes 15 through 50 past the hour, between 06:00 PM and 06:59 PM

//    @Scheduled(cron = "*/10 1-59 19 * * * ")
    public void job() {
        log.error(DateUtil.getCurrentDateTime());
    }
}
