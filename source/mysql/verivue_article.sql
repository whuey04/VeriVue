-- MySQL dump 10.13  Distrib 8.4.3, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: verivue_article
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
-- Table structure for table `ap_article`
--

DROP TABLE IF EXISTS `ap_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_article` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'Article Id',
  `title` varchar(100) NOT NULL COMMENT 'Article Title',
  `author_id` bigint unsigned NOT NULL COMMENT 'Author Id',
  `author_name` varchar(30) DEFAULT NULL COMMENT 'Author name',
  `channel_id` bigint unsigned DEFAULT NULL COMMENT 'Channel Id',
  `channel_name` varchar(10) DEFAULT NULL COMMENT 'Channel name',
  `layout` tinyint unsigned DEFAULT NULL COMMENT 'Article layout: 0 No_Pic 1 One_Pic 2 More_Pic',
  `flag` tinyint unsigned DEFAULT NULL COMMENT 'Article flag\r\n        0 = Normal article\r\n        1 = Hot article\r\n        2 = Pinned article\r\n        3 = Featured article\r\n        4 = Influencer article',
  `images` varchar(3000) DEFAULT NULL COMMENT 'Article images: Multiple images separated by commas',
  `labels` varchar(500) DEFAULT NULL COMMENT 'Article tags (max = 3, separated by commas)',
  `likes` int unsigned DEFAULT NULL,
  `collection` int unsigned DEFAULT NULL,
  `comment` int unsigned DEFAULT NULL,
  `views` int unsigned DEFAULT NULL,
  `created_time` datetime DEFAULT NULL COMMENT 'Create time',
  `publish_time` datetime DEFAULT NULL COMMENT 'Publish time',
  `sync_status` tinyint(1) DEFAULT '0' COMMENT 'Sync status',
  `origin` tinyint unsigned DEFAULT '0' COMMENT 'Origin',
  `static_url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1959772927910354947 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ap_article`
--

LOCK TABLES `ap_article` WRITE;
/*!40000 ALTER TABLE `ap_article` DISABLE KEYS */;
INSERT INTO `ap_article` VALUES (1958761974842392577,'原来你也玩原神！！——纳塔火山风景篇',1102,'admin',1958756356219887618,'原神',1,NULL,'http://192.168.200.130:9000/verivue/2025/08/22/675e427999c74b888f8e50c8bf4980a6.png','纳塔',3,0,3,6,'2025-08-22 05:23:41','2025-08-22 05:23:34',0,0,'http://192.168.200.130:9000/verivue/2025/08/22/1958761974842392577.html'),(1958765718497787906,'Testttttt',1102,'admin',1958756426189266946,'ZZZ',0,NULL,NULL,'test',0,0,0,1,'2025-08-24 23:42:15','2025-08-22 05:30:00',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1958765718497787906.html'),(1959736210528534529,'Post Man Test',1102,'admin',1959496186935541800,NULL,1,NULL,NULL,'PostMan',NULL,NULL,NULL,NULL,'2025-08-24 21:54:53','2025-08-24 21:53:17',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959736210528534529.html'),(1959736434185601025,'Post ManA ',1102,'admin',1959496186935541800,NULL,1,NULL,NULL,'PostMan',NULL,NULL,NULL,NULL,'2025-08-24 21:55:51','2025-08-24 21:55:48',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959736434185601025.html'),(1959739149993246722,'Post MannA ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:06:38','2025-08-24 22:05:49',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959739149993246722.html'),(1959742860991885314,'Post MannA ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:21:23','2025-08-24 22:21:17',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959742860991885314.html'),(1959743535108812801,'Post MannAa ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:24:01','2025-08-24 22:23:12',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959743535108812801.html'),(1959744198203109377,'Post MannAafuig ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:26:41','2025-08-24 22:26:26',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959744198203109377.html'),(1959746056904732673,'Post MannAafuig ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:34:05','2025-08-24 22:33:57',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959746056904732673.html'),(1959746219438206977,'Post MannAafuig ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:34:44','2025-08-24 22:34:35',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959746219438206977.html'),(1959746406046986242,'Post MannAafuig ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:35:26','2025-08-24 22:35:12',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959746406046986242.html'),(1959747392983478274,'Post MannAafuigg ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:39:23','2025-08-24 22:39:18',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959747392983478274.html'),(1959751184466538497,'Post MannAafuigg ',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 22:54:26','2025-08-24 22:54:18',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959751184466538497.html'),(1959752817795575809,'NewTest',1102,'admin',1959496186935541800,NULL,0,NULL,NULL,'PostMann',NULL,NULL,NULL,NULL,'2025-08-24 23:31:59','2025-08-24 23:31:57',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959752817795575809.html'),(1959772927910354946,'MyTest',1102,'admin',1959496186935541800,NULL,1,NULL,NULL,'PostMan',NULL,NULL,NULL,NULL,'2025-08-25 00:20:51','2025-08-25 00:20:50',0,0,'http://192.168.200.130:9000/verivue/2025/08/25/1959772927910354946.html');
/*!40000 ALTER TABLE `ap_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ap_article_config`
--

DROP TABLE IF EXISTS `ap_article_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_article_config` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint unsigned DEFAULT NULL COMMENT 'Article Id',
  `is_comment` tinyint unsigned DEFAULT NULL,
  `is_forward` tinyint unsigned DEFAULT NULL,
  `is_down` tinyint unsigned DEFAULT NULL,
  `is_delete` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1959772927956492290 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ap_article_config`
--

LOCK TABLES `ap_article_config` WRITE;
/*!40000 ALTER TABLE `ap_article_config` DISABLE KEYS */;
INSERT INTO `ap_article_config` VALUES (1958761975257628674,1958761974842392577,1,1,0,0),(1958765718564896770,1958765718497787906,1,1,0,0),(1959736210608226305,1959736210528534529,1,1,0,0),(1959736434185601026,1959736434185601025,1,1,0,0),(1959739149993246723,1959739149993246722,1,1,1,0),(1959742861067382785,1959742860991885314,1,1,0,0),(1959743535108812802,1959743535108812801,1,1,0,0),(1959744198203109378,1959744198203109377,1,1,0,0),(1959746056904732674,1959746056904732673,1,1,0,0),(1959746219438206978,1959746219438206977,1,1,0,0),(1959746406046986243,1959746406046986242,1,1,0,0),(1959747393012838402,1959747392983478274,1,1,0,0),(1959751184512675841,1959751184466538497,1,1,0,0),(1959752817862684674,1959752817795575809,1,1,0,0),(1959772927956492289,1959772927910354946,1,1,0,0);
/*!40000 ALTER TABLE `ap_article_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ap_article_content`
--

DROP TABLE IF EXISTS `ap_article_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ap_article_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `article_id` bigint unsigned DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Article Content Table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ap_article_content`
--

LOCK TABLES `ap_article_content` WRITE;
/*!40000 ALTER TABLE `ap_article_content` DISABLE KEYS */;
INSERT INTO `ap_article_content` VALUES (1,1958761974842392577,'[{\"type\":\"text\",\"value\":\"来看看纳塔火山的风景吧\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/675e427999c74b888f8e50c8bf4980a6.png\"},{\"type\":\"text\",\"value\":\"除此之外还有一些古时代龙族生活的遗迹！！！\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/b9d6149f5f0b49e4a71876a472537039.png\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/2966a3c246974f6f9e53bbd1cda08480.png\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/24e1a6e9ba694d618d6435a2528a7d1a.png\"}]'),(2,1958765718497787906,'[{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"},{\"type\":\"text\",\"value\":\"请在这里输入正文\"}]'),(3,1959736210528534529,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(4,1959736434185601025,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(5,1959739149993246722,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(6,1959742860991885314,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(7,1959743535108812801,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(8,1959744198203109377,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(9,1959746056904732673,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(10,1959746219438206977,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(11,1959746406046986242,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(12,1959747392983478274,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(13,1959751184466538497,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(14,1959752817795575809,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]'),(15,1959772927910354946,'[{\"type\":\"text\",\"value\":\"This is PostMan Test\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/verivue/2025/08/22/660b95e9c51644e29592371d81511175.png\"}]');
/*!40000 ALTER TABLE `ap_article_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-03  6:18:00
