package liuxf.live.monitor.service;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 14:11
 */
public interface MonitorService {

    /**
     * 采集硬盘信息
     */
    public void collectHardDiskMessage()throws Exception;

    /**
     * 采集内存信息
     */
    public void collectMemoryMessage()throws Exception;

}
