-- MySQL dump 10.13  Distrib 8.4.3, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: verivue_admin
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `ad_channel_label`
--

DROP TABLE IF EXISTS `ad_channel_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad_channel_label` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `channel_id` bigint unsigned DEFAULT NULL,
  `label_id` bigint unsigned DEFAULT NULL COMMENT 'Label ID',
  `ord` int unsigned DEFAULT NULL COMMENT 'Sorting',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Channel label information table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_channel_label`
--

LOCK TABLES `ad_channel_label` WRITE;
/*!40000 ALTER TABLE `ad_channel_label` DISABLE KEYS */;
/*!40000 ALTER TABLE `ad_channel_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_label`
--

DROP TABLE IF EXISTS `ad_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad_label` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Channel name',
  `created_time` datetime DEFAULT NULL COMMENT 'Created time',
  `created_admin` bigint unsigned DEFAULT NULL COMMENT 'Creator id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Label information table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_label`
--

LOCK TABLES `ad_label` WRITE;
/*!40000 ALTER TABLE `ad_label` DISABLE KEYS */;
/*!40000 ALTER TABLE `ad_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ad_user`
--

DROP TABLE IF EXISTS `ad_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ad_user` (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Username',
  `password` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Password',
  `salt` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Salt used for encryption',
  `nickname` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Nickname',
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Avatar',
  `phone` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Phone number',
  `status` tinyint unsigned NOT NULL DEFAULT '9' COMMENT 'Status: 0: Temporarily unavailable  1: Permanently unavailable  9: Normal',
  `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Email',
  `latest_login_time` datetime DEFAULT NULL COMMENT 'Latest login time',
  `created_time` datetime DEFAULT NULL COMMENT 'Account created time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='VeriVue administrator user information table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ad_user`
--

LOCK TABLES `ad_user` WRITE;
/*!40000 ALTER TABLE `ad_user` DISABLE KEYS */;
INSERT INTO `ad_user` VALUES (2,'admin','81e158e10201b6d7aee6e35eaf744796','123abc','ad',NULL,'13320325528',9,'admin@qq.com','2025-08-27 23:00:34','2020-03-04 17:07:40'),(3,'guest','34e20b52f5bd120db806e57e27f47ed0','123456','gu',NULL,'13412345676',1,'guest@qq.com','2020-07-30 15:00:03','2020-07-30 15:00:06'),(1960841924177178626,'mika','f0e4569e3ddbf13ce58d50294529e304','6NiGfaSA','MikkA','http://192.168.200.130:9000/verivue/2025/08/28/724782d25b89407f9e9a907c51250a87.png','1234567890',9,'mikadesu@gmail.com','2025-08-27 23:29:41','2025-08-27 23:08:40');
/*!40000 ALTER TABLE `ad_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-03  6:12:18
