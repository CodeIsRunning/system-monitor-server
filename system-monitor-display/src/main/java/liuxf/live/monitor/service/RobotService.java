package liuxf.live.monitor.service;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:14
 */
public interface RobotService {

    /**
     * 通过钉钉机器人推送消息
     */
    public void sendMessageByDdRobot()throws Exception;

}
