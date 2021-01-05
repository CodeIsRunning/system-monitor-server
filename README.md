# system-monitor-server
服务器监控服务
### 服务分为两个部分

1、信息采集

2、信息展示和推送以及监控的管理

#### 信息采集

1、信息采集采用sigar采集需要的服务器信息

2、信息存储，通过sigar采集的信息存储到mysql数据库

3、需要监控的服务器每个服务器只需要部署采集服务即可

#### 信息推送以及配置

1、信息推送通过dd机器人的方式推送到dd群

2、采集的策略通过修改mysql表里的配置进行调整

### 最终效果

在需要采集的服务器上部署一个采集服务，支持windows和linux  就会定时把想要的数据采集到数据库

信息展示服务能够配合前端展示监控到的数据，也能通过各种推送通知，通

知到需要知道的人的手里，通过管理数据库的配置来管理采集服务



### 项目

#### 项目整体结构

![image-20210105205645970](http://cdn.liuxf.live/20210105205646.png)

一、common里放了一些数据库的model和mapper以及mapping

二、collect就是采集服务是单独的boot服务



![image-20210105210210286](http://cdn.liuxf.live/20210105210210.png)

1、采集信息之前会根据服务器本身IP查询下该服务器是否需要采集。

```
 MonitorServer monitorServer = monitorServerMapper.selectByIpAddress(ip);

        if (monitorServer == null) {
            log.info("服务器未配置");
            return;
        }
```

2、如果需要采集则开始采集需要采集的信息，我这里只采集了内存和硬盘信息

内存部分

```java
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
```

3、采集完成会存储到相应的表中

![image-20210105210714391](http://cdn.liuxf.live/20210105210714.png)

三、display就是展示和管理服务

这里推送用的是钉钉机器人[地址](https://ding-doc.dingtalk.com/document#/org-dev-guide/krgddi)

1、管理和展示可以配合前端进行对需要监控的服务的表进行修改调整，以及钉钉推送表进行添加和修改

![image-20210105211124035](http://cdn.liuxf.live/20210105211124.png)

2、对采集的信息进行推送

![image-20210105211227011](http://cdn.liuxf.live/20210105211227.png)



3、数据库sql语句

钉钉配置表

```
CREATE TABLE `dd_robot_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monitor_server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `note` varchar(50) DEFAULT NULL COMMENT '推送群描述',
  `secret` varchar(200) DEFAULT NULL COMMENT '推送秘钥',
  `web_hook` varchar(255) DEFAULT NULL COMMENT '推送连接',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='钉钉机器人配置';

```

监控服务器配置表

```
CREATE TABLE `monitor_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note` varchar(50) DEFAULT NULL COMMENT '服务器描述',
  `ip_address` varchar(20) NOT NULL COMMENT 'IP地址',
  `status` smallint(11) DEFAULT '1' COMMENT '状态 1 需要推送监控  2 不需要',
  `notifyer_phone` varchar(15) DEFAULT NULL COMMENT '通知群里的某个人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`ip_address`),
  KEY `ip` (`ip_address`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='服务器信息表';


```

内存监控表

```
CREATE TABLE `server_memory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monitor_server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `total_size` int(11) DEFAULT NULL COMMENT '总量M',
  `used` int(11) DEFAULT NULL COMMENT '已用',
  `avail` int(11) DEFAULT NULL COMMENT '剩余量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='内存信息';

```



### 项目地址

[GitHub](https://github.com/CodeIsRunning/system-monitor-server)