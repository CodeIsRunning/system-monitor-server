package liuxf.live.monitor.task;

import liuxf.live.monitor.service.RobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuxf
 * @version 1.0
 * @date 2020/10/28 15:39
 */
@Slf4j
@Component
public class Monitor {


    @Resource
    RobotService robotService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void monitor() throws Exception {

        robotService.sendMessageByDdRobot();

    }


}
