-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: dbs_ventas
-- ------------------------------------------------------
-- Server version	5.7.33-log

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
-- Table structure for table `tbl_categoria`
--

DROP TABLE IF EXISTS `tbl_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_categoria` (
  `cat_categoriaid` int(11) NOT NULL AUTO_INCREMENT,
  `cat_nombre` varchar(45) DEFAULT NULL,
  `cat_descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cat_categoriaid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_categoria`
--

LOCK TABLES `tbl_categoria` WRITE;
/*!40000 ALTER TABLE `tbl_categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_foto`
--

DROP TABLE IF EXISTS `tbl_foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_foto` (
  `fot_fotoid` int(11) NOT NULL AUTO_INCREMENT,
  `fot_ruta` varchar(255) DEFAULT NULL,
  `fot_descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fot_fotoid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_foto`
--

LOCK TABLES `tbl_foto` WRITE;
/*!40000 ALTER TABLE `tbl_foto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_producto`
--

DROP TABLE IF EXISTS `tbl_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_producto` (
  `pro_productoid` int(11) NOT NULL AUTO_INCREMENT,
  `pro_nombre` varchar(255) DEFAULT NULL,
  `pro_descripcion` longtext,
  `pro_valor` double DEFAULT NULL,
  `pro_cantidaddisponiblel` int(11) DEFAULT NULL,
  `fk_categoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`pro_productoid`),
  KEY `fk_catepro_idx` (`fk_categoria`),
  CONSTRAINT `fk_catepro` FOREIGN KEY (`fk_categoria`) REFERENCES `tbl_categoria` (`cat_categoriaid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_producto`
--

LOCK TABLES `tbl_producto` WRITE;
/*!40000 ALTER TABLE `tbl_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_producto_has_tbl_foto`
--

DROP TABLE IF EXISTS `tbl_producto_has_tbl_foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_producto_has_tbl_foto` (
  `fk_productoid` int(11) DEFAULT NULL,
  `fk_fotoid` int(11) DEFAULT NULL,
  KEY `fk_producto_idx` (`fk_productoid`),
  KEY `fk_foto_idx` (`fk_fotoid`),
  CONSTRAINT `fk_foto` FOREIGN KEY (`fk_fotoid`) REFERENCES `tbl_foto` (`fot_fotoid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto` FOREIGN KEY (`fk_productoid`) REFERENCES `tbl_producto` (`pro_productoid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_producto_has_tbl_foto`
--

LOCK TABLES `tbl_producto_has_tbl_foto` WRITE;
/*!40000 ALTER TABLE `tbl_producto_has_tbl_foto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_producto_has_tbl_foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_proveedor`
--

DROP TABLE IF EXISTS `tbl_proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_proveedor` (
  `pro_prevedorid` int(11) NOT NULL AUTO_INCREMENT,
  `pro_direccion` varchar(255) DEFAULT NULL,
  `pro_telefono` varchar(30) DEFAULT NULL,
  `pro_ext` varchar(45) DEFAULT NULL,
  `pro_movil` varchar(255) DEFAULT NULL,
  `pro_contacto` varchar(45) DEFAULT NULL,
  `fk_usuarioid` int(11) DEFAULT NULL,
  PRIMARY KEY (`pro_prevedorid`),
  KEY `fk_prousu_idx` (`fk_usuarioid`),
  CONSTRAINT `fk_prousu` FOREIGN KEY (`fk_usuarioid`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_proveedor`
--

LOCK TABLES `tbl_proveedor` WRITE;
/*!40000 ALTER TABLE `tbl_proveedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_rol`
--

DROP TABLE IF EXISTS `tbl_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_rol` (
  `rol_rolid` int(11) NOT NULL AUTO_INCREMENT,
  `rol_nombre` varchar(45) DEFAULT NULL,
  `rol_descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`rol_rolid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_rol`
--

LOCK TABLES `tbl_rol` WRITE;
/*!40000 ALTER TABLE `tbl_rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_usuario`
--

DROP TABLE IF EXISTS `tbl_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_usuario` (
  `usu_usuarioid` int(11) NOT NULL AUTO_INCREMENT,
  `usu_tipodocumento` varchar(45) DEFAULT NULL,
  `usu_numeroducumento` bigint(12) DEFAULT NULL,
  `usu_nombres` varchar(45) DEFAULT NULL,
  `usu_apellidos` varchar(45) DEFAULT NULL,
  `usu_correo` varchar(60) DEFAULT NULL,
  `usu_clave` varchar(8) DEFAULT NULL,
  `usu_estado` tinyint(4) DEFAULT NULL,
  `usu_foto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`usu_usuarioid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_usuario`
--

LOCK TABLES `tbl_usuario` WRITE;
/*!40000 ALTER TABLE `tbl_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_usuario_has_tbl_rol`
--

DROP TABLE IF EXISTS `tbl_usuario_has_tbl_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_usuario_has_tbl_rol` (
  `fk_usuarioid` int(11) DEFAULT NULL,
  `fk_rolid` int(11) DEFAULT NULL,
  KEY `fkusus_idx` (`fk_usuarioid`),
  KEY `fkrol_idx` (`fk_rolid`),
  CONSTRAINT `fkrol` FOREIGN KEY (`fk_rolid`) REFERENCES `tbl_rol` (`rol_rolid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fkusus` FOREIGN KEY (`fk_usuarioid`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_usuario_has_tbl_rol`
--

LOCK TABLES `tbl_usuario_has_tbl_rol` WRITE;
/*!40000 ALTER TABLE `tbl_usuario_has_tbl_rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_usuario_has_tbl_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_venta`
--

DROP TABLE IF EXISTS `tbl_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_venta` (
  `ven_ventaid` int(11) NOT NULL AUTO_INCREMENT,
  `fk_usuvendedor` int(11) DEFAULT NULL,
  `fk_usucomprador` int(11) DEFAULT NULL,
  `ven_direccioncomprador` varchar(255) DEFAULT NULL,
  `ven_telefonocomprador` int(11) DEFAULT NULL,
  `ven_ciudadcomprador` varchar(45) DEFAULT NULL,
  `ven_fecha` date DEFAULT NULL,
  `ven_hora` time DEFAULT NULL,
  PRIMARY KEY (`ven_ventaid`),
  KEY `fk_comprador_idx` (`fk_usucomprador`),
  KEY `fk_vendedor_idx` (`fk_usuvendedor`),
  CONSTRAINT `fk_comprador` FOREIGN KEY (`fk_usucomprador`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendedor` FOREIGN KEY (`fk_usuvendedor`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_venta`
--

LOCK TABLES `tbl_venta` WRITE;
/*!40000 ALTER TABLE `tbl_venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_venta_has_tbl_producto`
--

DROP TABLE IF EXISTS `tbl_venta_has_tbl_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_venta_has_tbl_producto` (
  `fk_ventaid` int(11) DEFAULT NULL,
  `fk_producto` int(11) DEFAULT NULL,
  `vep_cantidad` int(11) DEFAULT NULL,
  `vep_valorunidad` float DEFAULT NULL,
  `vep_ventaproductoid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`vep_ventaproductoid`),
  KEY `fk_ventas_idx` (`fk_ventaid`),
  KEY `fk_productos_idx` (`fk_producto`),
  CONSTRAINT `fk_productos` FOREIGN KEY (`fk_producto`) REFERENCES `tbl_producto` (`pro_productoid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ventas` FOREIGN KEY (`fk_ventaid`) REFERENCES `tbl_venta` (`ven_ventaid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_venta_has_tbl_producto`
--

LOCK TABLES `tbl_venta_has_tbl_producto` WRITE;
/*!40000 ALTER TABLE `tbl_venta_has_tbl_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_venta_has_tbl_producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-29 20:37:06
