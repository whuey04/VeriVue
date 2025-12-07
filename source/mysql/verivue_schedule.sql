-- MySQL dump 10.13  Distrib 8.4.3, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: verivue_schedule
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
-- Table structure for table `taskinfo`
--

DROP TABLE IF EXISTS `taskinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taskinfo` (
  `task_id` bigint NOT NULL COMMENT 'Task Id',
  `execute_time` datetime(3) NOT NULL COMMENT 'Execute time',
  `parameters` longblob COMMENT 'Parameters',
  `priority` int NOT NULL COMMENT 'Priority',
  `task_type` int NOT NULL COMMENT 'Type of task',
  PRIMARY KEY (`task_id`),
  KEY `index_taskinfo_time` (`task_type`,`priority`,`execute_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskinfo`
--

LOCK TABLES `taskinfo` WRITE;
/*!40000 ALTER TABLE `taskinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskinfo_logs`
--

DROP TABLE IF EXISTS `taskinfo_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taskinfo_logs` (
  `task_id` bigint unsigned NOT NULL COMMENT 'Task Id',
  `execute_time` datetime(3) NOT NULL COMMENT 'Execute time',
  `parameters` longblob COMMENT 'Parameters',
  `priority` int NOT NULL COMMENT 'Priority',
  `task_type` int NOT NULL COMMENT 'Type of task',
  `version` int NOT NULL COMMENT 'Version number, used for optimistic locking',
  `status` int DEFAULT '0' COMMENT 'Status: 0 = Initialized, 1 = Executed, 2 = Cancelled',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskinfo_logs`
--

LOCK TABLES `taskinfo_logs` WRITE;
/*!40000 ALTER TABLE `taskinfo_logs` DISABLE KEYS */;
INSERT INTO `taskinfo_logs` VALUES (1958761965434638338,'2025-08-22 05:23:34.000',_binary 'ÅÄô\‚ä\Ù∫ó',1,1001,2,1),(1958763589238460417,'2025-08-22 05:30:00.000',_binary 'Å\‡úîÆ£ªó',1,1001,2,1),(1958765716979519490,'2025-08-22 05:30:00.000',_binary 'Å\‡úîÆ£ªó',1,1001,2,1),(1959735094411714561,'2025-08-24 21:50:30.088',_binary '\\‡ù∆ü›óô',1,1001,2,1),(1959736018442051585,'2025-08-24 21:53:16.956',_binary 'Å†üÇ‘´òô',1,1001,2,1),(1959736421367865346,'2025-08-24 21:55:47.622',_binary 'Å†ü\ ≈ºòô',1,1001,2,1),(1959736740671840257,'2025-08-24 21:57:03.747',_binary '§¿òﬁà™óô',1,1001,2,1),(1959736934381576194,'2025-08-24 21:57:49.953',_binary '§¿òﬁà™óô',1,1001,2,1),(1959737114791174146,'2025-08-24 21:58:29.374',_binary '§¿òﬁà™óô',1,1001,2,1),(1959737473106370561,'2025-08-24 21:59:44.017',_binary '§¿òﬁà™óô',1,1001,2,1),(1959738074095607810,'2025-08-24 22:02:10.932',_binary '§¿òﬁà™óô',1,1001,2,1),(1959738453810143234,'2025-08-24 22:03:52.226',_binary '®†ü\ ≈ºòô',1,1001,2,1),(1959739020401893378,'2025-08-24 22:05:49.200',_binary 'Å†ü¬óÜôô',1,1001,2,1),(1959739585756323841,'2025-08-24 22:07:29.852',_binary '®†ü\ ≈ºòô',1,1001,2,1),(1959742837767929857,'2025-08-24 22:21:17.225',_binary 'Å\‡ù∆ü›óô',1,1001,2,1),(1959743355709947905,'2025-08-24 22:23:12.288',_binary 'Å\‡ù∆ü›óô',1,1001,2,1),(1959744132528603138,'2025-08-24 22:26:26.136',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959746022502957058,'2025-08-24 22:33:56.714',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959746181639045122,'2025-08-24 22:34:34.669',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959746337889452033,'2025-08-24 22:35:11.935',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959747368971120641,'2025-08-24 22:39:17.501',_binary 'ÇÄôﬁë˚öô',1,1001,2,1),(1959751143739752450,'2025-08-24 22:54:17.546',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959752804608753666,'2025-08-24 23:00:53.151',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959756069635239937,'2025-08-24 23:13:51.925',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959757344674295810,'2025-08-24 23:18:55.964',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959760622845919234,'2025-08-24 23:31:56.812',_binary 'Å¿òﬁà™óô',1,1001,2,1),(1959772923422539777,'2025-08-25 00:20:50.348',_binary 'Å\‡ô\“\Ò‚†ô',1,1001,2,1);
/*!40000 ALTER TABLE `taskinfo_logs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-03  6:24:42
