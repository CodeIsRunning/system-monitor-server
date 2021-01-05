package liuxf.live.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuxf
 */
@EnableScheduling
@SpringBootApplication
public class SystemMonitorCollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemMonitorCollectApplication.class, args);
    }

}
