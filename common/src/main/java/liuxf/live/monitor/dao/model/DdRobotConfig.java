package liuxf.live.monitor.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author  liuxf
 * @date  2021/1/5 15:26
 * @version 1.0
 */

/**
 * 钉钉机器人配置
 */
@Data
public class DdRobotConfig implements Serializable {
    private Integer id;

    /**
     * 服务器id
     */
    private Integer monitorServerId;

    /**
     * 推送群描述
     */
    private String note;

    /**
     * 推送秘钥
     */
    private String secret;

    /**
     * 推送连接
     */
    private String webHook;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}