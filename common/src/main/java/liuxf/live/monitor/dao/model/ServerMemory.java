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
 * 内存信息
 */
@Data
public class ServerMemory implements Serializable {
    private Integer id;

    /**
     * 服务器id
     */
    private Integer monitorServerId;

    /**
     * 总量M
     */
    private Integer totalSize;

    /**
     * 已用
     */
    private Integer used;

    /**
     * 剩余量
     */
    private Integer avail;

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