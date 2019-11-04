package com.nky.community.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther:nky
 * @Date:2019/11/4
 * @Description:com.nky.community.scheduled
 * @version:1.0
 */
@Component
@Slf4j
public class HotTagTasks {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", new Date());
    }
}
