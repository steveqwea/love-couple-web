CREATE DATABASE IF NOT EXISTS love_db DEFAULT CHARACTER SET utf8mb4;
USE love_db;

-- 情侣用户表
CREATE TABLE `couple_user` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '情侣配对码，两人通过这个绑定',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `password` varchar(64) NOT NULL,
  `avatar` varchar(255) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP
);

-- 纪念日表
CREATE TABLE `anniversary` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '名称',
  `event_date` date NOT NULL COMMENT '日期',
  `type` tinyint COMMENT '0在一起纪念日 1生日 2其他',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP
);

-- 留言板
CREATE TABLE `message` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `sender_name` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP
);

-- 共享相册
CREATE TABLE `photo` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `photo_url` varchar(255) NOT NULL COMMENT '图片地址',
  `remark` varchar(200) DEFAULT '',
  `upload_time` datetime DEFAULT CURRENT_TIMESTAMP
);

-- 上传文件内容表（照片/头像二进制存数据库，云端部署不丢失）
CREATE TABLE `photo_file` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `url` varchar(255) NOT NULL COMMENT '访问路径，如 /uploads/xxx.jpg',
  `content_type` varchar(100) DEFAULT 'image/jpeg',
  `data` mediumblob NOT NULL COMMENT '文件二进制内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_url` (`url`)
) COMMENT='上传文件内容';

-- 每日打卡（每人每天一次，双方都可打卡，提交后不可修改）
CREATE TABLE `check_in` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '情侣配对码',
  `user_id` bigint NOT NULL COMMENT '打卡人ID',
  `nickname` varchar(50) NOT NULL COMMENT '打卡人昵称',
  `mood` varchar(20) NOT NULL COMMENT '心情：happy兴奋 excited平淡 calm难过 sad',
  `love_degree` tinyint NOT NULL COMMENT '恩爱程度 1-5星',
  `check_date` date NOT NULL COMMENT '打卡日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user_date` (`user_id`, `check_date`)
) COMMENT='每日打卡';

-- 想你了（每人每天累计次数，只有对方能看到，当日首次到520/1314触发隐藏特效）
CREATE TABLE `miss_you` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '情侣配对码',
  `user_id` bigint NOT NULL COMMENT '想念人ID',
  `nickname` varchar(50) NOT NULL COMMENT '想念人昵称',
  `miss_date` date NOT NULL COMMENT '想念日期',
  `count` int NOT NULL DEFAULT 0 COMMENT '当日想念次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user_date` (`user_id`, `miss_date`)
) COMMENT='想你了';

-- 许愿池
CREATE TABLE `wish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `couple_code` varchar(64) NOT NULL COMMENT '情侣配对码',
  `wisher` varchar(50) NOT NULL COMMENT '许愿人昵称',
  `content` varchar(500) NOT NULL COMMENT '愿望内容',
  `status` tinyint DEFAULT '0' COMMENT '0未实现 1已实现',
  `create_time` datetime DEFAULT NULL COMMENT '许愿时间',
  `finish_time` datetime DEFAULT NULL COMMENT '实现时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='许愿池';
