CREATE DATABASE  IF NOT EXISTS `training` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `training`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: training
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_employe`
--

DROP TABLE IF EXISTS `t_employe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_employe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matricule` varchar(45) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `prenom` varchar(45) NOT NULL,
  `date_embauche` date NOT NULL,
  `fk_organisation_id` int NOT NULL,
  `creation_date` timestamp NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `tech_lock` int NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `matricule_UNIQUE` (`matricule`),
  KEY `fk_employe_organisation_idx` (`fk_organisation_id`),
  CONSTRAINT `fk_employe_organisation` FOREIGN KEY (`fk_organisation_id`) REFERENCES `t_organisation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employe`
--

LOCK TABLES `t_employe` WRITE;
/*!40000 ALTER TABLE `t_employe` DISABLE KEYS */;
INSERT INTO `t_employe` VALUES (1,'mat001','chris','mornal','2012-09-29',1,'2022-09-29 17:33:38','UNKNOW',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `t_employe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_organisation`
--

DROP TABLE IF EXISTS `t_organisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_organisation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org` char(3) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creation_date` timestamp NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `tech_lock` int NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_UNIQUE` (`org`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_organisation`
--

LOCK TABLES `t_organisation` WRITE;
/*!40000 ALTER TABLE `t_organisation` DISABLE KEYS */;
INSERT INTO `t_organisation` VALUES (1,'abc','first organisation','premiere organisation','2022-09-29 17:33:17','UNKNOW',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `t_organisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_reservation`
--

DROP TABLE IF EXISTS `t_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` timestamp NOT NULL,
  `fk_employe_id` int NOT NULL,
  `fk_salle_id` int NOT NULL,
  `creation_date` timestamp NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `tech_lock` int NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reservation_employe1_idx` (`fk_employe_id`),
  KEY `fk_reservation_salle1_idx` (`fk_salle_id`),
  CONSTRAINT `fk_reservation_employe1` FOREIGN KEY (`fk_employe_id`) REFERENCES `t_employe` (`id`),
  CONSTRAINT `fk_reservation_salle1` FOREIGN KEY (`fk_salle_id`) REFERENCES `t_salle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_reservation`
--

LOCK TABLES `t_reservation` WRITE;
/*!40000 ALTER TABLE `t_reservation` DISABLE KEYS */;
INSERT INTO `t_reservation` VALUES (1,'2012-10-02 08:45:30',1,1,'2022-09-29 17:39:02','UNKNOW',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `t_reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_salle`
--

DROP TABLE IF EXISTS `t_salle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_salle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  `capacite` int NOT NULL,
  `creation_date` timestamp NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `tech_lock` int NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_salle`
--

LOCK TABLES `t_salle` WRITE;
/*!40000 ALTER TABLE `t_salle` DISABLE KEYS */;
INSERT INTO `t_salle` VALUES (1,'salle001',10,'2022-09-29 17:33:09','UNKNOW',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `t_salle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-29 22:13:42
