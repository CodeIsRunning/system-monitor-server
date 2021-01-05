package liuxf.live.monitor.task;

import liuxf.live.monitor.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:10
 */
@Slf4j
@Component
public class MonitorTask {

    @Resource
    private MonitorService monitorService;

    @Scheduled(cron = "0 50 8 * * ?")
    public void monitor()throws Exception{

        log.info("加载硬盘信息");
        monitorService.collectHardDiskMessage();
        log.info("加载内存信息");
        monitorService.collectMemoryMessage();

    }

}
