package liuxf.live.monitor.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import liuxf.live.monitor.dao.mapper.DdRobotConfigMapper;
import liuxf.live.monitor.dao.mapper.MonitorServerMapper;
import liuxf.live.monitor.dao.mapper.ServerHardDiskMapper;
import liuxf.live.monitor.dao.mapper.ServerMemoryMapper;
import liuxf.live.monitor.dao.model.DdRobotConfig;
import liuxf.live.monitor.dao.model.MonitorServer;
import liuxf.live.monitor.dao.model.ServerHardDisk;
import liuxf.live.monitor.dao.model.ServerMemory;
import liuxf.live.monitor.service.RobotService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:15
 */
@Service
public class RobotServiceImpl implements RobotService {

    @Resource
    private MonitorServerMapper monitorServerMapper;

    @Resource
    private ServerMemoryMapper serverMemoryMapper;

    @Resource
    private ServerHardDiskMapper serverHardDiskMapper;

    @Resource
    private DdRobotConfigMapper ddRobotConfigMapper;

    @Override
    public void sendMessageByDdRobot() throws Exception{

        List<MonitorServer> list = monitorServerMapper.selectByStatus(1);

        for (MonitorServer m : list
        ) {


            dealRobotMessage(appendMessage(m.getNote(), m.getId()), m.getId(), m.getNotifyerPhone());

        }

    }


    private String appendMessage(String head, int serverId) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(head).append(System.lineSeparator());

        stringBuilder.append("内存信息：").append(System.lineSeparator());

        ServerMemory serverMemory = serverMemoryMapper.selectByMonitorServerId(serverId);

        if (serverMemory != null) {
            stringBuilder.append("总量:    " + serverMemory.getTotalSize() + "M").append(System.lineSeparator());
            stringBuilder.append("已使用:    " + serverMemory.getUsed() + "M").append(System.lineSeparator());
            stringBuilder.append("剩余:    " + serverMemory.getAvail() + "M").append(System.lineSeparator());
        }

        stringBuilder.append("硬盘信息：").append(System.lineSeparator());

        List<ServerHardDisk> hardDisks = serverHardDiskMapper.selectByMonitorServerId(1);

        for (ServerHardDisk s : hardDisks
        ) {

            stringBuilder.append(s.getDiskPath() + "    总量:    " + s.getTotalSize() + "G").append(System.lineSeparator());
            stringBuilder.append(s.getDiskPath() + "    已用:    " + s.getUsed() + "G").append(System.lineSeparator());
            stringBuilder.append(s.getDiskPath() + "    剩余:    " + s.getAvail() + "G").append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());

        }

        return stringBuilder.toString();

    }

    private void dealRobotMessage(String msg, int serverId, String phone) throws Exception{

       List<DdRobotConfig> ddRobotConfigs =  ddRobotConfigMapper.selectByMonitorServerId(serverId);

        for (DdRobotConfig d:ddRobotConfigs
             ) {
            sendDdRobot(msg,d.getSecret(),d.getWebHook(),phone);
        }

    }

    private void sendDdRobot(String msg, String secret, String webHook, String phone) throws Exception {

        Long timestamp = System.currentTimeMillis();


        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");


        String addSign = "&timestamp=" + timestamp + "&sign=" + sign;

        DingTalkClient dingTalkClient = new DefaultDingTalkClient(webHook + addSign);

        OapiRobotSendRequest robotSendRequest = new OapiRobotSendRequest();

        robotSendRequest.setMsgtype("text");


        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(msg);
        robotSendRequest.setText(text);


        if (StringUtils.isNotBlank(phone)) {
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Arrays.asList(phone));
            at.setIsAtAll(false);
            robotSendRequest.setAt(at);
        }

        OapiRobotSendResponse response = dingTalkClient.execute(robotSendRequest);

    }
}
