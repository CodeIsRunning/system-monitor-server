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
 * 服务器信息表
 */
@Data
public class MonitorServer implements Serializable {
    private Integer id;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 服务器描述
     */
    private String note;

    /**
     * 状态 1 正常 2 警告 3 未知（无数据）
     */
    private Integer status;

    /**
     * 通知群里的某个人
     */
    private String notifyerPhone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}