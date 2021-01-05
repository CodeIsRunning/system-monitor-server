package liuxf.live.monitor.dao.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import liuxf.live.monitor.dao.model.ServerMemory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuxf
 * @version 1.0
 * @date 2021/1/5 15:26
 */
@Mapper
public interface ServerMemoryMapper {
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
    int insert(ServerMemory record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ServerMemory record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ServerMemory selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ServerMemory record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ServerMemory record);

    /**
     * 根据服务器id查询内存信息
     * @param monitorServerId
     * @return
     */
    ServerMemory selectByMonitorServerId(@Param("monitorServerId")Integer monitorServerId);



}