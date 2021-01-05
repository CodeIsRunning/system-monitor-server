package liuxf.live.monitor.dao.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import liuxf.live.monitor.dao.model.DdRobotConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:26
 */
@Mapper
public interface DdRobotConfigMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(DdRobotConfig record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(DdRobotConfig record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    DdRobotConfig selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(DdRobotConfig record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DdRobotConfig record);

    /**
     * 根据服务器id查询需要推送的dd群
     * @param monitorServerId
     * @return
     */
    List<DdRobotConfig> selectByMonitorServerId(@Param("monitorServerId")Integer monitorServerId);


}