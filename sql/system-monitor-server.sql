/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50728
Source Host           : localhost:3306
Source Database       : system-monitor-server

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2021-01-05 21:17:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dd_robot_config
-- ----------------------------
DROP TABLE IF EXISTS `dd_robot_config`;
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

-- ----------------------------
-- Table structure for monitor_server
-- ----------------------------
DROP TABLE IF EXISTS `monitor_server`;
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

-- ----------------------------
-- Table structure for server_hard_disk
-- ----------------------------
DROP TABLE IF EXISTS `server_hard_disk`;
CREATE TABLE `server_hard_disk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monitor_server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `disk_path` varchar(50) DEFAULT NULL COMMENT '盘符路径',
  `total_size` int(11) DEFAULT NULL COMMENT '总量 单位g',
  `used` int(11) DEFAULT NULL COMMENT '已使用',
  `avail` int(11) DEFAULT NULL COMMENT '剩余量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='硬盘信息';

-- ----------------------------
-- Table structure for server_memory
-- ----------------------------
DROP TABLE IF EXISTS `server_memory`;
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
