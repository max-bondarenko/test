package org.max.boot.shedule;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Maksym_Bondarenko on 6/15/2016.
 */
@Component
public class CronTask {
    private static final Logger log = getLogger(CronTask.class);

    @Scheduled(cron = "5 * * * * ?")
    public void cronExampleTask(){
        log.info("Task ran:" + LocalDate.now());
    }

}
