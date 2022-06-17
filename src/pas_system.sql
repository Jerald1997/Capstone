-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: pas_system
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `accountID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`accountID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Jerald','Regidor','Mabuhay'),(2,'Camille','Dayao','Phase 2'),(3,'Je','Regi','Mabuhay'),(4,'Jp','Regidor','Mabuhay'),(5,'Siena','Regidor','San Pedro'),(6,'Ren','Romero','Test'),(7,'Kevin','Aranda','Tanza');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claim`
--

DROP TABLE IF EXISTS `claim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `claim` (
  `claimID` int NOT NULL AUTO_INCREMENT,
  `policyID` int NOT NULL,
  `accountID` int NOT NULL,
  `dateOfAcc` date NOT NULL,
  `addAccHappen` varchar(255) NOT NULL,
  `descOfAcc` varchar(255) NOT NULL,
  `descOfDamage` varchar(255) NOT NULL,
  `estimatedCostOfRep` varchar(255) NOT NULL,
  PRIMARY KEY (`claimID`),
  KEY `policyID` (`policyID`),
  KEY `accountID` (`accountID`),
  CONSTRAINT `claim_ibfk_1` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`),
  CONSTRAINT `claim_ibfk_2` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claim`
--

LOCK TABLES `claim` WRITE;
/*!40000 ALTER TABLE `claim` DISABLE KEYS */;
INSERT INTO `claim` VALUES (1,1,1,'2022-04-05','Laguna','Minor','Minor','20000.0');
/*!40000 ALTER TABLE `claim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `policy`
--

DROP TABLE IF EXISTS `policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `policy` (
  `policyID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  `effectiveDate` date NOT NULL,
  `expirationDate` date NOT NULL,
  `policyPremium` double DEFAULT NULL,
  `vehicleNum` int NOT NULL,
  PRIMARY KEY (`policyID`),
  KEY `accountID` (`accountID`),
  CONSTRAINT `policy_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `policy`
--

LOCK TABLES `policy` WRITE;
/*!40000 ALTER TABLE `policy` DISABLE KEYS */;
INSERT INTO `policy` VALUES (1,1,'2022-06-07','2022-06-30',600.8333571751913,1),(2,2,'2022-08-09','2023-02-09',2407.500035762787,2),(3,2,'2022-07-08','2023-01-08',87550.0010728836,2),(4,4,'2022-06-20','2022-12-20',1402.672394098683,2);
/*!40000 ALTER TABLE `policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `policyholder`
--

DROP TABLE IF EXISTS `policyholder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `policyholder` (
  `policyHolderID` int NOT NULL AUTO_INCREMENT,
  `policyID` int NOT NULL,
  `accountID` int NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `drivLicNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `drivLic1stIssDate` date NOT NULL,
  PRIMARY KEY (`policyHolderID`),
  KEY `policyID` (`policyID`),
  KEY `accountID` (`accountID`),
  CONSTRAINT `policyholder_ibfk_1` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`),
  CONSTRAINT `policyholder_ibfk_2` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `policyholder`
--

LOCK TABLES `policyholder` WRITE;
/*!40000 ALTER TABLE `policyholder` DISABLE KEYS */;
INSERT INTO `policyholder` VALUES (1,1,1,'Jerald','Regidor','Mabuhay','1997-09-05','123123','2010-02-03'),(2,2,2,'Jas','Dayao','Phase 2','2010-06-07','678678','2018-06-04'),(3,3,2,'Camille','Dayao','Phase 2','1997-06-12','890890','2020-06-07'),(4,4,4,'Jp','Regidor','Mabuhay','1997-08-08','123324','2010-06-07');
/*!40000 ALTER TABLE `policyholder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicleID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  `policyID` int NOT NULL,
  `make` varchar(25) DEFAULT NULL,
  `model` varchar(25) DEFAULT NULL,
  `year` int NOT NULL,
  `type` varchar(25) DEFAULT NULL,
  `fuelType` varchar(25) DEFAULT NULL,
  `purchasePrice` double NOT NULL,
  `color` varchar(20) DEFAULT NULL,
  `premium` double NOT NULL,
  PRIMARY KEY (`vehicleID`),
  KEY `accountID` (`accountID`),
  KEY `policyID` (`policyID`),
  CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`),
  CONSTRAINT `vehicles_ibfk_2` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,1,1,'Honda','City',2014,'jhk','nbm',1000,'Red',600.8333571751913),(2,2,2,'Toyota','Wigo',2019,'qwe','asd',2000,'Black',1605.000023841858),(3,2,2,'Mitsu','Mirrage',2019,'rty','fgh',1000,'Blue',802.500011920929),(4,2,3,'Mitsu','Monterro',2018,'qwe','ert',10000,'Blue',7049.99988079071),(5,2,3,'Honda','Civic',2019,'tyu','gh',100000,'Black',80500.0011920929),(6,4,4,'Honda','City',2012,'qwe','asd',1000,'Red',600.8333571751913),(7,4,4,'Toyota','Corolla',2010,'eqw','asd',2000.43,'Black',801.8390369234919);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-15 19:09:32
