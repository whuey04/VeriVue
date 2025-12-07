-- MySQL dump 10.13  Distrib 8.4.3, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: verivue_wemedia
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
-- Table structure for table `wm_channel`
--

DROP TABLE IF EXISTS `wm_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_channel` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_default` tinyint unsigned DEFAULT NULL,
  `status` tinyint unsigned DEFAULT NULL,
  `ord` tinyint unsigned DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1959762002033668098 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_channel`
--

LOCK TABLES `wm_channel` WRITE;
/*!40000 ALTER TABLE `wm_channel` DISABLE KEYS */;
INSERT INTO `wm_channel` VALUES (1958756356219887618,'原神','一款由米哈游开发的游戏',1,1,0,'2025-08-22 05:01:22'),(1958756426189266946,'ZZZ','绝区零',1,1,0,'2025-08-22 05:01:39'),(1958756564865540097,'崩铁','崩坏星穹铁道',1,1,0,'2025-08-22 05:02:12'),(1959496186935541761,'Postman','Tester for postman',1,0,1,'2025-08-24 06:01:11');
/*!40000 ALTER TABLE `wm_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_fans_statistics`
--

DROP TABLE IF EXISTS `wm_fans_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_fans_statistics` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `article` bigint unsigned DEFAULT NULL,
  `read_count` int unsigned DEFAULT NULL,
  `comment` int unsigned DEFAULT NULL,
  `follow` int unsigned DEFAULT NULL,
  `collection` int unsigned DEFAULT NULL,
  `forward` int unsigned DEFAULT NULL,
  `likes` int unsigned DEFAULT NULL,
  `unlikes` int unsigned DEFAULT NULL,
  `unfollow` int unsigned DEFAULT NULL,
  `burst` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_time` (`user_id`,`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_fans_statistics`
--

LOCK TABLES `wm_fans_statistics` WRITE;
/*!40000 ALTER TABLE `wm_fans_statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `wm_fans_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_material`
--

DROP TABLE IF EXISTS `wm_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_material` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint unsigned DEFAULT NULL,
  `is_collection` tinyint(1) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_material`
--

LOCK TABLES `wm_material` WRITE;
/*!40000 ALTER TABLE `wm_material` DISABLE KEYS */;
INSERT INTO `wm_material` VALUES (5,1102,'http://192.168.200.130:9000/verivue/2025/08/22/396291ff3d6f4b97995bdc44d4d6d2de.png',0,1,'2025-08-22 05:14:45'),(6,1102,'http://192.168.200.130:9000/verivue/2025/08/22/27092b71aa284b2aa462c1b613441c18.png',0,1,'2025-08-22 05:17:21'),(7,1102,'http://192.168.200.130:9000/verivue/2025/08/22/96ca46a11ac44cd9a2acfbd8ddcf7641.png',0,1,'2025-08-22 05:17:27'),(8,1102,'http://192.168.200.130:9000/verivue/2025/08/22/d6746545d323420489edbf33f0f4118e.png',0,1,'2025-08-22 05:17:46'),(9,1102,'http://192.168.200.130:9000/verivue/2025/08/22/61ccdc44cf4146ac97becb9afb955461.png',0,1,'2025-08-22 05:17:56'),(10,1102,'http://192.168.200.130:9000/verivue/2025/08/22/b9d6149f5f0b49e4a71876a472537039.png',0,1,'2025-08-22 05:18:05'),(11,1102,'http://192.168.200.130:9000/verivue/2025/08/22/675e427999c74b888f8e50c8bf4980a6.png',0,0,'2025-08-22 05:18:20'),(12,1102,'http://192.168.200.130:9000/verivue/2025/08/22/2966a3c246974f6f9e53bbd1cda08480.png',0,0,'2025-08-22 05:18:35'),(13,1102,'http://192.168.200.130:9000/verivue/2025/08/22/24e1a6e9ba694d618d6435a2528a7d1a.png',0,0,'2025-08-22 05:19:11'),(14,1102,'http://192.168.200.130:9000/verivue/2025/08/22/887a0dd4184f476090ee17223e0ae302.png',0,0,'2025-08-22 05:19:34'),(15,1102,'http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png',0,0,'2025-08-22 05:36:53');
/*!40000 ALTER TABLE `wm_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_news`
--

DROP TABLE IF EXISTS `wm_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_news` (
  `id` bigint NOT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `title` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `type` tinyint unsigned DEFAULT NULL COMMENT 'Article Layout             0 No images             1 Single image             3 Multiple images',
  `channel_id` bigint unsigned DEFAULT NULL,
  `labels` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `submited_time` datetime DEFAULT NULL,
  `status` tinyint unsigned DEFAULT NULL COMMENT 'Current Status             0 Draft             1 Submitted (Pending Review)             2 Review Failed             3 Manual Review             4 Manual Review Approved             8 Review Approved (Pending Publication)             9 Published',
  `publish_time` datetime DEFAULT NULL,
  `reason` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `article_id` bigint unsigned DEFAULT NULL,
  `images` longtext COLLATE utf8mb4_unicode_ci,
  `enable` tinyint unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_news`
--

LOCK TABLES `wm_news` WRITE;
/*!40000 ALTER TABLE `wm_news` DISABLE KEYS */;
INSERT INTO `wm_news` VALUES (1958761963270324225,1102,'原来你也玩原神！！——纳塔火山风景篇','[{\"type\":\"text\",\"value\":\"来看看纳塔火山的风景吧\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/675e427999c74b888f8e50c8bf4980a6.png\"},{\"type\":\"text\",\"value\":\"除此之外还有一些古时代龙族生活的遗迹！！！\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/b9d6149f5f0b49e4a71876a472537039.png\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/2966a3c246974f6f9e53bbd1cda08480.png\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/24e1a6e9ba694d618d6435a2528a7d1a.png\"}]',1,1958756356219887618,'纳塔','2025-08-22 05:23:39','2025-08-22 05:23:39',9,'2025-08-22 05:23:34','审核成功',1958761974842392577,'http://192.168.200.130:9000/verivue/2025/08/22/675e427999c74b888f8e50c8bf4980a6.png',1),(1958763587678187521,1102,'Testttttt','[{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"},{\"type\":\"text\",\"value\":\"请在这里输入正文\"}]',0,1958756426189266946,'test','2025-08-22 05:38:33','2025-08-22 05:38:33',9,'2025-08-22 05:30:00','Reject Reason',1958765718497787906,NULL,1),(1959731388383371265,1102,'NewTest','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',0,1959496186935541800,'PostMann','2025-08-24 23:31:57','2025-08-24 23:31:57',9,'2025-08-24 23:31:57','审核成功',1959752817795575809,NULL,1),(1959735840997888001,1102,'Post Man Test','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',1,1959496186935541800,'PostMan','2025-08-24 21:53:13','2025-08-24 21:53:14',9,'2025-08-24 21:53:17','审核成功',1959736210528534529,NULL,1),(1959736421237903361,1102,'Post ManA ','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',1,1959496186935541800,'PostMan','2025-08-24 21:55:48','2025-08-24 21:55:48',9,'2025-08-24 21:55:48','审核成功',1959736434185601025,NULL,1),(1959738951493734401,1102,'Post MannA ','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',0,1959496186935541800,'PostMann','2025-08-24 22:05:46','2025-08-24 22:05:47',9,'2025-08-24 22:05:49','审核成功',1959739149993246722,NULL,0),(1959747368077639682,1102,'Post MannAafuigg ','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',0,1959496186935541800,'PostMann','2025-08-24 22:39:18','2025-08-24 22:39:18',1,'2025-08-24 22:39:18',NULL,NULL,NULL,1),(1959772923107897345,1102,'MyTest','[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]',1,1959496186935541800,'PostMan','2025-08-25 00:20:50','2025-08-25 00:20:50',9,'2025-08-25 00:20:50','审核成功',1959772927910354946,NULL,1);
/*!40000 ALTER TABLE `wm_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_news_material`
--

DROP TABLE IF EXISTS `wm_news_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_news_material` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `material_id` int unsigned DEFAULT NULL,
  `news_id` bigint unsigned DEFAULT NULL,
  `type` tinyint unsigned DEFAULT NULL COMMENT 'Reference Type             0 Content Reference             1 Main Image Reference',
  `ord` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_news_material`
--

LOCK TABLES `wm_news_material` WRITE;
/*!40000 ALTER TABLE `wm_news_material` DISABLE KEYS */;
INSERT INTO `wm_news_material` VALUES (1,10,1958761963270324225,0,0),(2,11,1958761963270324225,0,1),(3,12,1958761963270324225,0,2),(4,13,1958761963270324225,0,3),(5,11,1958761963270324225,1,0),(6,15,1958763587678187521,0,0),(9,15,1959733146853798000,0,0),(10,15,1959735840997888001,0,0),(11,15,1959736421237903361,0,0),(16,15,1959731388383371300,0,0),(18,15,1959738951493734401,0,0),(19,15,1959736421237903400,0,0),(21,15,1959733146853797889,0,0),(26,15,1959747368077639682,0,0),(31,15,1959731388383371265,0,0),(32,15,1959772923107897345,0,0);
/*!40000 ALTER TABLE `wm_news_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_news_statistics`
--

DROP TABLE IF EXISTS `wm_news_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_news_statistics` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `article` bigint unsigned DEFAULT NULL,
  `read_count` int unsigned DEFAULT NULL,
  `comment` int unsigned DEFAULT NULL,
  `follow` int unsigned DEFAULT NULL,
  `collection` int unsigned DEFAULT NULL,
  `forward` int unsigned DEFAULT NULL,
  `likes` int unsigned DEFAULT NULL,
  `unlikes` int unsigned DEFAULT NULL,
  `unfollow` int unsigned DEFAULT NULL,
  `burst` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id_time` (`user_id`,`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_news_statistics`
--

LOCK TABLES `wm_news_statistics` WRITE;
/*!40000 ALTER TABLE `wm_news_statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `wm_news_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_sensitive`
--

DROP TABLE IF EXISTS `wm_sensitive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_sensitive` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `sensitives` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_sensitive`
--

LOCK TABLES `wm_sensitive` WRITE;
/*!40000 ALTER TABLE `wm_sensitive` DISABLE KEYS */;
/*!40000 ALTER TABLE `wm_sensitive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wm_user`
--

DROP TABLE IF EXISTS `wm_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wm_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ap_user_id` bigint DEFAULT NULL,
  `ap_author_id` bigint DEFAULT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `salt` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nickname` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint unsigned DEFAULT NULL COMMENT 'Status             0 Temporarily unavailable             1 Permanently unavailable             9 Normal operation',
  `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint unsigned DEFAULT NULL COMMENT 'Account Type             0 Personal             1 Business             2 Sub-account',
  `score` tinyint unsigned DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1960595052812554243 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wm_user`
--

LOCK TABLES `wm_user` WRITE;
/*!40000 ALTER TABLE `wm_user` DISABLE KEYS */;
INSERT INTO `wm_user` VALUES (1102,4,NULL,'admin','b9d11b3be25f5a1a7dc8ca04cd310b28','123456','管理',NULL,NULL,'13234567657',9,NULL,NULL,NULL,'2025-08-27 23:29:18','2020-03-14 09:35:13'),(1960595052812554242,1960525930107908098,NULL,'wen','7d8121e227c0ee74ba7c597934811807','HBc0kal4','鱼子酱','http://192.168.200.130:9000/verivue/2025/08/28/69e99c56b38d48b7a0fb5854adf116ca.png',NULL,'1234567890',9,'wh123@gmail.com',1,NULL,'2025-08-27 21:29:02','2025-08-27 06:47:41');
/*!40000 ALTER TABLE `wm_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-03  6:40:59
