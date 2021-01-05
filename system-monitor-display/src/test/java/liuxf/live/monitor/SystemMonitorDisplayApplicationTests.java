package liuxf.live.monitor;

import liuxf.live.monitor.service.RobotService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SystemMonitorDisplayApplicationTests {

	@Resource
	private RobotService robotService;

	@Test
	void contextLoads()throws Exception {
	}

}
