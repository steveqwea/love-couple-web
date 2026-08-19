-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: love_db
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `love_db`
--

/*!40000 DROP DATABASE IF EXISTS `love_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `love_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `love_db`;

--
-- Table structure for table `anniversary`
--

DROP TABLE IF EXISTS `anniversary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anniversary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '鍚嶇О',
  `event_date` date NOT NULL COMMENT '鏃ユ湡',
  `type` tinyint DEFAULT NULL COMMENT '0鍦ㄤ竴璧风邯蹇垫棩 1鐢熸棩 2鍏朵粬',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anniversary`
--

LOCK TABLES `anniversary` WRITE;
/*!40000 ALTER TABLE `anniversary` DISABLE KEYS */;
INSERT INTO `anniversary` VALUES (1,'AB12CD','一周年纪念日','2026-05-20',0,'2026-08-18 10:56:51'),(2,'12F25E','在一起一百天','2026-08-01',0,'2026-08-18 23:55:27'),(3,'12F25E','第一个七夕','2026-08-19',2,'2026-08-19 00:03:21'),(4,'12F25E','在一起','2026-04-19',0,'2026-08-19 00:05:26'),(5,'12F25E','第一次见面','2026-06-12',2,'2026-08-19 00:06:02'),(6,'12F25E','第二次见面','2026-08-07',2,'2026-08-19 00:06:20'),(7,'21D493','在一起','2026-04-19',2,'2026-08-19 13:20:57');
/*!40000 ALTER TABLE `anniversary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_in`
--

DROP TABLE IF EXISTS `check_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_in` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '情侣配对码',
  `user_id` bigint NOT NULL COMMENT '打卡人ID',
  `nickname` varchar(50) NOT NULL COMMENT '打卡人昵称',
  `mood` varchar(20) NOT NULL COMMENT '心情',
  `love_degree` tinyint NOT NULL COMMENT '恩爱程度 1-5星',
  `check_date` date NOT NULL COMMENT '打卡日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`,`check_date`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='每日打卡';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_in`
--

LOCK TABLES `check_in` WRITE;
/*!40000 ALTER TABLE `check_in` DISABLE KEYS */;
INSERT INTO `check_in` VALUES (4,'12F25E',2,'马君豪','excited',5,'2026-08-19','2026-08-19 15:29:13'),(8,'12F25E',3,'张涵','happy',3,'2026-08-19','2026-08-19 16:31:22');
/*!40000 ALTER TABLE `check_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `couple_user`
--

DROP TABLE IF EXISTS `couple_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `couple_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '鎯呬荆閰嶅?鐮侊紝涓や汉閫氳繃杩欎釜缁戝畾',
  `nickname` varchar(50) NOT NULL COMMENT '鏄电О',
  `password` varchar(64) NOT NULL,
  `avatar` varchar(255) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(50) DEFAULT NULL COMMENT '登录用户名',
  `partner_id` bigint DEFAULT NULL COMMENT '伴侣用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `couple_user`
--

LOCK TABLES `couple_user` WRITE;
/*!40000 ALTER TABLE `couple_user` DISABLE KEYS */;
INSERT INTO `couple_user` VALUES (2,'12F25E','马君豪','majunhao233','/uploads/avatar/12d83ed5c6704581bf17180612315d6c.png','2026-08-18 23:39:52','马君豪',3),(3,'12F25E','张涵','ASD200509','/uploads/avatar/1df7516b325043779d3ef016f406ab17.jpg','2026-08-18 23:54:03','张涵',2),(4,'CC3CC3','马大豪','ASD200509','','2026-08-19 13:10:18','马大豪',NULL),(5,'438120','测试','123','','2026-08-19 15:24:59','测试',NULL);
/*!40000 ALTER TABLE `couple_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `sender_name` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'AB12CD','小可爱','永远喜欢你！','2026-08-18 10:56:51'),(3,'12F25E','马大豪','嘿嘿 这是我们过的第一个七夕 七夕快乐宝贝 我最爱的宝贝 我真的很爱你 我会改正我的不足的 ','2026-08-19 00:00:15'),(4,'12F25E','马小豪','我爱你张涵宝宝','2026-08-19 00:00:30'),(5,'12F25E','马大豪','我会主动关心你 谢谢宝贝包容我的脾气 我肯定会好好对你的 我会对你负责的','2026-08-19 00:01:07'),(6,'12F25E','马大豪 ','你不能叫马君豪','2026-08-19 00:04:29'),(7,'12F25E','马君豪','七夕节快乐宝宝','2026-08-19 16:25:32');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miss_you`
--

DROP TABLE IF EXISTS `miss_you`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `miss_you` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL COMMENT '情侣配对码',
  `user_id` bigint NOT NULL COMMENT '想念人ID',
  `nickname` varchar(50) NOT NULL COMMENT '想念人昵称',
  `miss_date` date NOT NULL COMMENT '想念日期',
  `count` int NOT NULL DEFAULT '0' COMMENT '当日想念次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`,`miss_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='想你了';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miss_you`
--

LOCK TABLES `miss_you` WRITE;
/*!40000 ALTER TABLE `miss_you` DISABLE KEYS */;
INSERT INTO `miss_you` VALUES (1,'12F25E',2,'马君豪','2026-08-19',520,'2026-08-19 16:56:51','2026-08-19 17:09:38'),(2,'12F25E',3,'张涵','2026-08-19',4,'2026-08-19 17:07:59','2026-08-19 17:08:05');
/*!40000 ALTER TABLE `miss_you` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `photo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `couple_code` varchar(32) NOT NULL,
  `photo_url` varchar(255) NOT NULL COMMENT '鍥剧墖鍦板潃',
  `remark` varchar(200) DEFAULT '',
  `upload_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo`
--

LOCK TABLES `photo` WRITE;
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` VALUES (5,'12F25E','/uploads/a236720a26ce4ca6b5886ad9426d329d.jpg','','2026-08-18 23:59:26'),(7,'12F25E','/uploads/ecf0d513ae554d829da2fc3a9665c818.jpg','不开心的宝贝不好看以后多笑笑','2026-08-19 00:01:55'),(8,'12F25E','/uploads/58429659a5d14a4b9d0498c4d03eecf2.png','','2026-08-19 13:58:40');
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `couple_code` varchar(64) NOT NULL COMMENT '情侣配对码',
  `wisher` varchar(50) NOT NULL COMMENT '许愿人昵称',
  `content` varchar(500) NOT NULL COMMENT '愿望内容',
  `status` tinyint DEFAULT '0' COMMENT '0未实现 1已实现',
  `create_time` datetime DEFAULT NULL COMMENT '许愿时间',
  `finish_time` datetime DEFAULT NULL COMMENT '实现时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='许愿池';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
INSERT INTO `wish` VALUES (3,'12F25E','马君豪','可以少吵架',0,'2026-08-19 00:42:11',NULL),(4,'12F25E','马大豪','希望我们都开心',0,'2026-08-19 13:46:58',NULL),(5,'12F25E','张涵','马豪豪待我如初',0,'2026-08-19 16:44:29',NULL);
/*!40000 ALTER TABLE `wish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-19 17:12:20
