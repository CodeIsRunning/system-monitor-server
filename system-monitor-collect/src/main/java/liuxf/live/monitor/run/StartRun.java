package liuxf.live.monitor.run;

import liuxf.live.monitor.service.MonitorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:08
 */
@Component
public class StartRun implements CommandLineRunner {

    @Resource
    private MonitorService monitorService;

    @Override
    public void run(String... args) throws Exception {

        monitorService.collectHardDiskMessage();
        monitorService.collectMemoryMessage();
    }
}
