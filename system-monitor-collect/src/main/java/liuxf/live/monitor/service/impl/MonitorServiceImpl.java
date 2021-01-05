package liuxf.live.monitor.service.impl;

import liuxf.live.monitor.common.Constans;
import liuxf.live.monitor.dao.mapper.MonitorServerMapper;
import liuxf.live.monitor.dao.mapper.ServerHardDiskMapper;
import liuxf.live.monitor.dao.mapper.ServerMemoryMapper;
import liuxf.live.monitor.dao.model.MonitorServer;
import liuxf.live.monitor.dao.model.ServerHardDisk;
import liuxf.live.monitor.dao.model.ServerMemory;
import liuxf.live.monitor.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 14:13
 */
@Slf4j
@Service
public class MonitorServiceImpl implements MonitorService {

    private static Sigar sigar;

    private static String ip;

    static {
        sigar = new Sigar();

        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            log.info("ip:" + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private MonitorServerMapper monitorServerMapper;

    @Resource
    private ServerHardDiskMapper serverHardDiskMapper;

    @Resource
    private ServerMemoryMapper serverMemoryMapper;

    @Override
    public void collectHardDiskMessage() throws Exception {

        MonitorServer monitorServer = monitorServerMapper.selectByIpAddress(ip);

        if (monitorServer == null) {
            log.info("服务器未配置");
            return;
        }


        FileSystem fslist[] = sigar.getFileSystemList();

        for (int i = 0; i < fslist.length; i++) {
            FileSystem fs = fslist[i];
            FileSystemUsage usage = null;

            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
            } catch (Exception e) {
                log.info(ExceptionUtils.getStackTrace(e));
                continue;
            }

            switch (fs.getType()) {
                case 2:

                    ServerHardDisk serverHardDisk = new ServerHardDisk();

                    serverHardDisk.setMonitorServerId(monitorServer.getId());
                    serverHardDisk.setDiskPath(fs.getDirName());
                    serverHardDisk.setTotalSize((int) (usage.getTotal() / (1024L * 1024L)));
                    serverHardDisk.setUsed((int) (usage.getUsed() / (1024L * 1024L)));
                    serverHardDisk.setAvail((int) (usage.getAvail() / (1024L * 1024L)));
                    serverHardDisk.setUpdateTime(LocalDateTime.now());
                    serverHardDisk.setCreateTime(LocalDateTime.now());

                    serverHardDiskMapper.insertSelective(serverHardDisk);
                    break;
                default:
                    break;
            }
        }

    }


    @Override
    public void collectMemoryMessage() throws Exception {

        MonitorServer monitorServer = monitorServerMapper.selectByIpAddress(ip);

        if (monitorServer == null) {
            log.info("服务器未配置");
            return;
        }

        Mem mem = sigar.getMem();


        ServerMemory serverMemory = new ServerMemory();

        serverMemory.setMonitorServerId(monitorServer.getId());
        serverMemory.setCreateTime(LocalDateTime.now());
        serverMemory.setUpdateTime(LocalDateTime.now());

        if (Constans.osType.indexOf("WINDOWS") != -1) {

            serverMemory.setTotalSize((int) (mem.getTotal() / (1024L * 1024L)));
            serverMemory.setUsed((int) (mem.getUsed() / (1024L * 1024L)));
            serverMemory.setAvail((int) (mem.getFree() / (1024L * 1024L)));

        } else {

            int used = (int) (mem.getActualUsed() / (1024L * 1024L));

            int avail = (int) (mem.getActualFree() / (1024L * 1024L));

            int total = used + avail;

            serverMemory.setTotalSize(total);
            serverMemory.setUsed(used);
            serverMemory.setAvail(avail);
        }
        serverMemoryMapper.insertSelective(serverMemory);
    }

}
