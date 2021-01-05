package liuxf.live.monitor.dao.mapper;

import liuxf.live.monitor.dao.model.MonitorServer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:26
 */
@Mapper
public interface MonitorServerMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("ipAddress") String ipAddress);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(MonitorServer record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(MonitorServer record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    MonitorServer selectByPrimaryKey(@Param("id") Integer id, @Param("ipAddress") String ipAddress);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(MonitorServer record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(MonitorServer record);

    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    MonitorServer selectByPrimaryKey(Integer id);

    /**
     * 根据IP查询
     *
     * @param ipAddress
     * @return
     */
    MonitorServer selectByIpAddress(@Param("ipAddress") String ipAddress);

    /**
     * 查询所有需要推送的服务器
     *
     * @param status
     * @return
     */
    List<MonitorServer> selectByStatus(@Param("status") Integer status);
}