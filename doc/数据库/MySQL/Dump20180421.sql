-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: thairice
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `r4message_send`
--

DROP TABLE IF EXISTS `r4message_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r4message_send` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统消息编号',
  `message_id` bigint(20) DEFAULT NULL,
  `receive_userid` bigint(20) unsigned DEFAULT NULL COMMENT '接收人ID',
  `status_` enum('01','02') NOT NULL COMMENT '消息状态代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r4message_send`
--

LOCK TABLES `r4message_send` WRITE;
/*!40000 ALTER TABLE `r4message_send` DISABLE KEYS */;
INSERT INTO `r4message_send` VALUES (97,81,21,'01'),(99,81,38,'01'),(100,81,39,'01'),(101,81,43,'01'),(103,83,21,'01'),(105,83,38,'01'),(106,83,39,'01'),(107,83,43,'01'),(108,85,21,'01'),(110,85,38,'01'),(111,85,39,'01'),(112,85,43,'01'),(115,88,8,'01'),(116,89,1,'01'),(117,89,8,'01'),(119,90,1,'01'),(120,91,8,'01'),(125,96,27,'01'),(126,97,27,'02'),(127,98,27,'02');
/*!40000 ALTER TABLE `r4message_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t10pdt_report`
--

DROP TABLE IF EXISTS `t10pdt_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t10pdt_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品报表编号',
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `add_time` datetime DEFAULT NULL COMMENT '报告创建时间',
  `collect_time` datetime DEFAULT NULL COMMENT '采集日期',
  `start_time` datetime DEFAULT NULL COMMENT '查询产品开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '查询产品结束日期',
  `zone_code` int(16) DEFAULT NULL COMMENT '查询行政区域代码',
  `crop_type` enum('01') DEFAULT NULL COMMENT '查询作物种类代码',
  `pdt_type` enum('01','02','03','04') DEFAULT NULL COMMENT '产品类型代码',
  `suffix` enum('01','02') DEFAULT NULL COMMENT '报告格式代码',
  `download_path` varchar(256) DEFAULT NULL COMMENT '下载路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t10pdt_report`
--

LOCK TABLES `t10pdt_report` WRITE;
/*!40000 ALTER TABLE `t10pdt_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `t10pdt_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t11zone`
--

DROP TABLE IF EXISTS `t11zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t11zone` (
  `id` int(8) DEFAULT NULL COMMENT '行政区划id',
  `code_` int(16) NOT NULL COMMENT '行政区划代码',
  `type_` enum('01','02','03','04') DEFAULT NULL COMMENT '产品类型代码',
  `name_` varchar(30) DEFAULT NULL COMMENT '行政区划名称',
  PRIMARY KEY (`code_`),
  UNIQUE KEY `code_` (`code_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t11zone`
--

LOCK TABLES `t11zone` WRITE;
/*!40000 ALTER TABLE `t11zone` DISABLE KEYS */;
/*!40000 ALTER TABLE `t11zone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t1parameter`
--

DROP TABLE IF EXISTS `t1parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t1parameter` (
  `userid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '#参数明细备注',
  `status_` tinyint(1) DEFAULT NULL COMMENT '#删除标志',
  `version` int(9) DEFAULT NULL COMMENT '#版本号',
  `datetime_` datetime DEFAULT NULL COMMENT '#时间戳',
  `parm_type_id` varchar(8) NOT NULL COMMENT '参数类型编号',
  `name_` varchar(256) DEFAULT NULL COMMENT '参数明细名称',
  `value_` varchar(2048) NOT NULL COMMENT '参数值',
  `parm__id` varchar(3) NOT NULL COMMENT '参数明细编号',
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t1parameter`
--

LOCK TABLES `t1parameter` WRITE;
/*!40000 ALTER TABLE `t1parameter` DISABLE KEYS */;
INSERT INTO `t1parameter` VALUES (99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp地址','ladsweb.modaps.eosdis.nasa.gov','001',1),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp端口','21','002',2),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp用户名','anonymous','003',3),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp密码','','004',4),(99999999,'FTP自动下载',1,1,'2018-03-13 17:02:58','10000001','源ftp根目录','/allData/6/MOD13Q1/YYYY/XXX/','005',5),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp根目录','/allData/6/MOD13A2/YYYY/XXX/','005',6),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','源ftp根目录','/allData/6/MOD11A2/YYYY/XXX/','005',7),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','文件存放地址','D://test//','006',8),(99999999,'FTP自动下载',1,1,'2018-03-10 00:26:07','10000001','初始化开关','0','007',9),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','初始化开始日期','20010101','008',10),(99999999,'FTP自动下载',1,1,'2018-02-27 15:33:16','10000001','初始化结束日期','','009',11),(99999999,'邮件模板',1,1,'2018-02-27 15:33:16','10000002','找回密码邮件模板','  Dear user $1, \r\n  Thank you for using Thai agricultural remote sensing system! \r\n  You are using the system to retrieve the password function, this verification code is: $2, \r\n  valid for 30 minutes, verification code failure time is: $3. For your account security, \r\n  please enter the password interface and reset the password in time. \r\n  Thank you very much! ','001',12),(99999999,'邮件模板',1,1,'2018-02-27 15:33:16','10000002','用户到期提醒邮件模板','  Dear user $1, \r\n  Thank you for using Thai agricultural remote sensing system! \r\n  Your user will expire soon, and your data usage service will stop after $2. In order to avoid \r\n  affecting your use, please contact us in time for renewal. \r\n  For enquiries, please call 010-11223344. \r\n  Thank you very much!','002',13),(99999999,'FTP自动下载',1,1,'2018-03-13 17:09:36','10000001','源ftp根目录','','005',14),(99999999,'干旱监测算法输入参数',1,1,'2018-04-15 00:22:00','10000003','分级阈值1','0.337','001',15),(99999999,'干旱监测算法输入参数',1,1,'2018-04-15 00:22:00','10000003','分级阈值2','0.99','002',16),(99999999,'干旱监测算法输入参数',1,1,'2018-04-15 00:22:00','10000003','分级阈值3','0.13','003',17),(99999999,'干旱监测算法输入参数',1,1,'2018-04-15 00:22:00','10000003','分级阈值4','3.35','004',18),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','分级阈值1','0.39','001',19),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','分级阈值2','2.33','002',20),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','分级阈值3','0.98','003',21),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','分级阈值4','0.34','004',22),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','输入开始年份','2004','005',23),(99999999,'长势监测算法输入参数',1,1,'2018-04-15 03:45:08','10000004','输入结束年份','2019','006',24);
/*!40000 ALTER TABLE `t1parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t2syslog`
--

DROP TABLE IF EXISTS `t2syslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t2syslog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统日志编号',
  `type_` char(3) DEFAULT '0' COMMENT '系统日志类型代码',
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `username` varchar(60) DEFAULT '' COMMENT '用户名',
  `action_` varchar(256) DEFAULT '' COMMENT '操作',
  `content` text COMMENT '系统日志内容',
  `add_time` datetime DEFAULT NULL COMMENT '系统日志生成日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t2syslog`
--

LOCK TABLES `t2syslog` WRITE;
/*!40000 ALTER TABLE `t2syslog` DISABLE KEYS */;
INSERT INTO `t2syslog` VALUES (1,'1',1,'test','action1','action_detail_text','2018-03-02 00:00:00'),(2,'2',2,'test','action2','action_detail_text','2018-03-04 00:00:00'),(4,'0',1,'test2','action1','action_detail_text','2018-03-06 00:00:00'),(5,'2',1,'test','actions','action_detail_text','2018-03-06 00:00:00'),(6,'2',1,'test','actions','action_detail_text','2018-03-06 00:00:00'),(7,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(8,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(9,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(10,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(11,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(12,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(13,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(14,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(15,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(16,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00'),(17,'2',2,'test','actions','action_detail_text','2018-03-06 00:00:00');
/*!40000 ALTER TABLE `t2syslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t3user`
--

DROP TABLE IF EXISTS `t3user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t3user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `type_` enum('01','02','03') NOT NULL COMMENT '用户类型代码',
  `account` varchar(180) NOT NULL COMMENT 'username',
  `pwd` varchar(60) NOT NULL COMMENT '密码',
  `name_` varchar(180) NOT NULL COMMENT '姓名',
  `phone` varchar(30) NOT NULL COMMENT '手机号码',
  `email` varchar(240) NOT NULL COMMENT '电子邮箱地址',
  `address` varchar(1500) DEFAULT NULL COMMENT '通讯地址',
  `zip_encode` varchar(30) NOT NULL COMMENT '邮政编码',
  `heading` varchar(255) DEFAULT NULL COMMENT '头像',
  `company` varchar(240) DEFAULT NULL COMMENT '公司名称',
  `industry` varchar(80) DEFAULT NULL COMMENT '行业',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status_` enum('01','02','03','04') NOT NULL COMMENT '用户审核状态代码',
  `identiCode` varchar(6) DEFAULT NULL COMMENT '忘记密码校验码',
  `expirTime` datetime DEFAULT NULL COMMENT '校验码到期时间',
  `Prdt_EfDt` datetime DEFAULT NULL COMMENT '产品生效日期',
  `PD_ExDat` datetime DEFAULT NULL COMMENT '产品到期日期',
  `activation` int(1) DEFAULT '0' COMMENT '账号是否激活',
  `area` varchar(200) DEFAULT NULL,
  `PD_TpCd` varchar(50) DEFAULT NULL COMMENT '产品类型代码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t3user`
--

LOCK TABLES `t3user` WRITE;
/*!40000 ALTER TABLE `t3user` DISABLE KEYS */;
INSERT INTO `t3user` VALUES (3,'03','zhuce2','e10adc3949ba59abbe56e057f20f883e','bijioj','15000001111','yangtaobeijing@126.com',NULL,'000',NULL,'bfofjaofj','fafaf','2018-04-02 23:34:25','01',NULL,NULL,'2018-04-02 23:36:01','2018-04-02 23:36:04',NULL,NULL,NULL),(11,'03','admin','b9d11b3be25f5a1a7dc8ca04cd310b28','admin','15811112225','bluesky1986yang11@163.com','zhongguobeijingshixichengqu','100052','/upload/head/8742c338e9c7420db2b32d4c8f9434a7.jpg','beijing','agricultural','2018-04-02 22:53:23','01',NULL,NULL,'2018-04-02 00:00:00','2018-05-31 00:00:00',1,NULL,'01,02,03,04,'),(16,'02','caozuoyuan04','e10adc3949ba59abbe56e057f20f883e','caozuoyuan04','15801019999','caozuoyuan04@hotmail.com','beijingshi','000',NULL,'lianxiang','agricultural','2018-04-08 15:53:02','03',NULL,NULL,NULL,NULL,0,NULL,NULL),(24,'02','guanliyuan01','e10adc3949ba59abbe56e057f20f883e','guanliyuan01','15801012222','15801012222@hotmail.com','北京市西城区','000',NULL,'建设银行','agricultural','2018-04-13 21:17:08','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(25,'02','caozuoyuan01','e10adc3949ba59abbe56e057f20f883e','yangtao','15811112222','yangtao@125.com','xichengqu','000',NULL,'beii','agricultural','2018-04-13 22:19:04','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(27,'01','yangtao7979','9bfb60950a3b7fb1d3d0edf9a1905c00','yangtao','15899998866','522015966@qq.com','bejing','100056','/upload/head/414d14fe1cc64668bd2078aaff2be938.jpg','zhongguojiansheyinhang','IT','2018-04-13 23:11:21','02',NULL,NULL,'2018-04-13 00:00:00','2018-04-16 00:00:00',1,'13 1301 130102','01,02,'),(28,'02','caozuoyuan22','e10adc3949ba59abbe56e057f20f883e','caozuoyuan22','15801012222','12322@126.com','beijingshixicheng','000',NULL,'baidu','agricultural','2018-04-13 23:50:41','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(29,'02','caozuoyuan12','e10adc3949ba59abbe56e057f20f883e','caozuoyuan12','15811112345','caozuoyuan12@126.com','beiingshi','000',NULL,'baidu','agricultural','2018-04-14 09:58:47','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(30,'02','caozuoyuan05','e10adc3949ba59abbe56e057f20f883e','caozuoyuan05','15988887676','caozuoyuan05@163.com','nanjinshi','000',NULL,'gongshang','agricultural','2018-04-14 09:59:45','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(31,'02','caozuoyuan06','e10adc3949ba59abbe56e057f20f883e','caozuoyuan06','17899876543','caozuoyuan06@qq.com','beijingshi','000',NULL,'kejikaifagongsi','agricultural','2018-04-14 10:00:33','02',NULL,NULL,NULL,NULL,0,NULL,NULL),(32,'01','zhuchaobin1','905989566041dc2f1d3190bc0397e174','zhuchaobin','13056789876','nanjing2008@163.com','beijingshixichengqu','1000555',NULL,'zhongguojiansheyinhang','IT','2018-04-14 19:15:45','03',NULL,NULL,'2018-04-14 00:00:00','2018-04-30 00:00:00',0,NULL,'01,02,03,04,'),(34,'01','yangtao8989','9bfb60950a3b7fb1d3d0edf9a1905c00','yangtao8989','15801016666','lishaowen571635@qq.com','beijingshi ','100055',NULL,'beijingshi ','indust','2018-04-18 23:27:06','02',NULL,NULL,'2018-04-18 00:00:00','2018-05-31 00:00:00',1,'15 1501 150103','01,02,'),(35,'01','yangtao7878','9bfb60950a3b7fb1d3d0edf9a1905c00','yangtao','13501018888','13097850972@163.com','beijingshixichengqu','100069',NULL,'beijingshi','IT','2018-04-19 23:40:07','02',NULL,NULL,'2018-04-19 00:00:00','2018-06-14 00:00:00',0,'14 1401 140106','01,04,');
/*!40000 ALTER TABLE `t3user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t5parameter_type`
--

DROP TABLE IF EXISTS `t5parameter_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t5parameter_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `name_` varchar(256) DEFAULT NULL COMMENT '参数类型名称',
  `remark` varchar(256) DEFAULT NULL COMMENT '参数类型备注',
  `parm_type_id` varchar(8) NOT NULL COMMENT '参数类型编号',
  `version` int(9) DEFAULT NULL COMMENT '版本号',
  `datetime_` datetime DEFAULT NULL COMMENT '时间戳',
  `status_` tinyint(1) DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t5parameter_type`
--

LOCK TABLES `t5parameter_type` WRITE;
/*!40000 ALTER TABLE `t5parameter_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `t5parameter_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t6org_data`
--

DROP TABLE IF EXISTS `t6org_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t6org_data` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '源文件编号',
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `name_` varchar(128) DEFAULT NULL COMMENT '文件名称',
  `size_` float DEFAULT '0' COMMENT '文件大小',
  `download_path` varchar(256) DEFAULT NULL COMMENT '文件下载地址',
  `storage_path` varchar(256) DEFAULT NULL COMMENT '文件存放地址',
  `type_` enum('01','02','03','04','05') DEFAULT NULL COMMENT '文件类型代码',
  `row_column` varchar(256) DEFAULT NULL COMMENT '文件行列号',
  `band_number` varchar(256) DEFAULT NULL COMMENT '波段数编码',
  `status_` enum('01','02','03','04','05','06','07') DEFAULT NULL COMMENT '文件状态代码',
  `fail_code` enum('源文件不存在','网络不通') DEFAULT NULL COMMENT '文件下载失败原因代码',
  `collect_time` datetime DEFAULT NULL COMMENT '数据采集时间',
  `dload_start_time` datetime DEFAULT NULL COMMENT '文件下载开始时间',
  `dload_end_time` datetime DEFAULT NULL COMMENT '文件下载结束时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t6org_data`
--

LOCK TABLES `t6org_data` WRITE;
/*!40000 ALTER TABLE `t6org_data` DISABLE KEYS */;
INSERT INTO `t6org_data` VALUES (482300,NULL,'MOD13Q1.A2017337.h26v06.006.2017353223719.hdf',232236000,'/allData/6/MOD13Q1/2017/337',NULL,'05','h26v06','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482306,NULL,'MOD13Q1.A2017337.h27v06.006.2017353224002.hdf',229830000,'/allData/6/MOD13Q1/2017/337',NULL,'05','h27v06','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482307,NULL,'MOD13Q1.A2017337.h27v07.006.2017353223537.hdf',143788000,'/allData/6/MOD13Q1/2017/337',NULL,'05','h27v07','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482308,NULL,'MOD13Q1.A2017337.h27v08.006.2017353223321.hdf',44256300,'/allData/6/MOD13Q1/2017/337',NULL,'05','h27v08','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482318,NULL,'MOD13Q1.A2017337.h28v07.006.2017353223936.hdf',104148000,'/allData/6/MOD13Q1/2017/337',NULL,'05','h28v07','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482319,NULL,'MOD13Q1.A2017337.h28v08.006.2017353223744.hdf',61674500,'/allData/6/MOD13Q1/2017/337',NULL,'05','h28v08','1','07',NULL,'2017-12-03 01:36:06',NULL,'2018-03-24 13:32:28'),(482574,NULL,'MOD13A2.A2017337.h26v06.006.2017353223640.hdf',20057400,'/allData/6/MOD13A2/2017/337',NULL,'05','h26v06','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482580,NULL,'MOD13A2.A2017337.h27v06.006.2017353223921.hdf',21936800,'/allData/6/MOD13A2/2017/337',NULL,'05','h27v06','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482581,NULL,'MOD13A2.A2017337.h27v07.006.2017353223506.hdf',13471500,'/allData/6/MOD13A2/2017/337',NULL,'05','h27v07','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482582,NULL,'MOD13A2.A2017337.h27v08.006.2017353223300.hdf',5084400,'/allData/6/MOD13A2/2017/337',NULL,'05','h27v08','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482592,NULL,'MOD13A2.A2017337.h28v07.006.2017353223902.hdf',10113800,'/allData/6/MOD13A2/2017/337',NULL,'05','h28v07','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482593,NULL,'MOD13A2.A2017337.h28v08.006.2017353223720.hdf',6523000,'/allData/6/MOD13A2/2017/337',NULL,'05','h28v08','1','07',NULL,'2017-12-03 01:36:11',NULL,'2018-03-24 13:32:28'),(482891,NULL,'MOD11A2.A2017337.h26v06.006.2017346035118.hdf',7216950,'/allData/6/MOD11A2/2017/337',NULL,'05','h26v06','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(482897,NULL,'MOD11A2.A2017337.h27v06.006.2017346034932.hdf',7156970,'/allData/6/MOD11A2/2017/337',NULL,'05','h27v06','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(482898,NULL,'MOD11A2.A2017337.h27v07.006.2017346034844.hdf',5484110,'/allData/6/MOD11A2/2017/337',NULL,'05','h27v07','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(482899,NULL,'MOD11A2.A2017337.h27v08.006.2017346034838.hdf',1910280,'/allData/6/MOD11A2/2017/337',NULL,'05','h27v08','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(482909,NULL,'MOD11A2.A2017337.h28v07.006.2017346035710.hdf',4059940,'/allData/6/MOD11A2/2017/337',NULL,'05','h28v07','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(482910,NULL,'MOD11A2.A2017337.h28v08.006.2017346034927.hdf',1836860,'/allData/6/MOD11A2/2017/337',NULL,'05','h28v08','1','07',NULL,'2017-12-03 01:36:17',NULL,'2018-03-24 13:32:28'),(483208,NULL,'MOD11A2.A2017345.h26v06.006.2017354033436.hdf',7555180,'/allData/6/MOD11A2/2017/345',NULL,'05','h26v06','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483214,NULL,'MOD11A2.A2017345.h27v06.006.2017354033651.hdf',6591970,'/allData/6/MOD11A2/2017/345',NULL,'05','h27v06','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483215,NULL,'MOD11A2.A2017345.h27v07.006.2017354033349.hdf',5541830,'/allData/6/MOD11A2/2017/345',NULL,'05','h27v07','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483216,NULL,'MOD11A2.A2017345.h27v08.006.2017354032559.hdf',2380680,'/allData/6/MOD11A2/2017/345',NULL,'05','h27v08','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483226,NULL,'MOD11A2.A2017345.h28v07.006.2017354033707.hdf',4373090,'/allData/6/MOD11A2/2017/345',NULL,'05','h28v07','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483227,NULL,'MOD11A2.A2017345.h28v08.006.2017354033459.hdf',2564260,'/allData/6/MOD11A2/2017/345',NULL,'05','h28v08','1','07',NULL,'2017-12-11 01:36:30',NULL,'2018-03-24 13:32:28'),(483482,NULL,'MOD13Q1.A2017353.h26v06.006.2018004224922.hdf',237273000,'/allData/6/MOD13Q1/2017/353',NULL,'05','h26v06','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483488,NULL,'MOD13Q1.A2017353.h27v06.006.2018004225433.hdf',225601000,'/allData/6/MOD13Q1/2017/353',NULL,'05','h27v06','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483489,NULL,'MOD13Q1.A2017353.h27v07.006.2018004224944.hdf',144757000,'/allData/6/MOD13Q1/2017/353',NULL,'05','h27v07','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483490,NULL,'MOD13Q1.A2017353.h27v08.006.2018004224933.hdf',47378700,'/allData/6/MOD13Q1/2017/353',NULL,'05','h27v08','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483500,NULL,'MOD13Q1.A2017353.h28v07.006.2018004225128.hdf',108031000,'/allData/6/MOD13Q1/2017/353',NULL,'05','h28v07','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483501,NULL,'MOD13Q1.A2017353.h28v08.006.2018004225131.hdf',63741300,'/allData/6/MOD13Q1/2017/353',NULL,'05','h28v08','1','07',NULL,'2017-12-19 01:36:43',NULL,'2018-03-24 13:32:28'),(483756,NULL,'MOD13A2.A2017353.h26v06.006.2018004224841.hdf',20103800,'/allData/6/MOD13A2/2017/353',NULL,'05','h26v06','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(483762,NULL,'MOD13A2.A2017353.h27v06.006.2018004225353.hdf',21074200,'/allData/6/MOD13A2/2017/353',NULL,'05','h27v06','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(483763,NULL,'MOD13A2.A2017353.h27v07.006.2018004224914.hdf',13268200,'/allData/6/MOD13A2/2017/353',NULL,'05','h27v07','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(483764,NULL,'MOD13A2.A2017353.h27v08.006.2018004224912.hdf',5293050,'/allData/6/MOD13A2/2017/353',NULL,'05','h27v08','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(483774,NULL,'MOD13A2.A2017353.h28v07.006.2018004225100.hdf',10218800,'/allData/6/MOD13A2/2017/353',NULL,'05','h28v07','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(483775,NULL,'MOD13A2.A2017353.h28v08.006.2018004225104.hdf',6623720,'/allData/6/MOD13A2/2017/353',NULL,'05','h28v08','1','07',NULL,'2017-12-19 01:36:48',NULL,'2018-03-24 13:32:28'),(484073,NULL,'MOD11A2.A2017353.h26v06.006.2017362034929.hdf',7538590,'/allData/6/MOD11A2/2017/353',NULL,'05','h26v06','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484079,NULL,'MOD11A2.A2017353.h27v06.006.2017362034801.hdf',7427270,'/allData/6/MOD11A2/2017/353',NULL,'05','h27v06','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484080,NULL,'MOD11A2.A2017353.h27v07.006.2017362034910.hdf',4983540,'/allData/6/MOD11A2/2017/353',NULL,'05','h27v07','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484081,NULL,'MOD11A2.A2017353.h27v08.006.2017362034600.hdf',2019330,'/allData/6/MOD11A2/2017/353',NULL,'05','h27v08','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484091,NULL,'MOD11A2.A2017353.h28v07.006.2017362034751.hdf',3636300,'/allData/6/MOD11A2/2017/353',NULL,'05','h28v07','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484092,NULL,'MOD11A2.A2017353.h28v08.006.2017362034830.hdf',2022970,'/allData/6/MOD11A2/2017/353',NULL,'05','h28v08','1','07',NULL,'2017-12-19 01:36:53',NULL,'2018-03-24 13:32:28'),(484390,NULL,'MOD11A2.A2017361.h26v06.006.2018005034459.hdf',6914810,'/allData/6/MOD11A2/2017/361',NULL,'05','h26v06','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484396,NULL,'MOD11A2.A2017361.h27v06.006.2018005035001.hdf',5851980,'/allData/6/MOD11A2/2017/361',NULL,'05','h27v06','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484397,NULL,'MOD11A2.A2017361.h27v07.006.2018005034455.hdf',4384360,'/allData/6/MOD11A2/2017/361',NULL,'05','h27v07','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484398,NULL,'MOD11A2.A2017361.h27v08.006.2018005034711.hdf',1780860,'/allData/6/MOD11A2/2017/361',NULL,'05','h27v08','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484408,NULL,'MOD11A2.A2017361.h28v07.006.2018005034528.hdf',3377240,'/allData/6/MOD11A2/2017/361',NULL,'05','h28v07','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484409,NULL,'MOD11A2.A2017361.h28v08.006.2018005034512.hdf',1796940,'/allData/6/MOD11A2/2017/361',NULL,'05','h28v08','1','07',NULL,'2017-12-27 01:37:07',NULL,'2018-03-24 13:32:28'),(484676,99999999,'MOD13Q1.A2018001.h26v06.006.2018017223832.hdf',227210000,'/allData/6/MOD13Q1/2018/001','D://test//','05','h26v06','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:27:01','2018-03-24 13:32:28'),(484682,99999999,'MOD13Q1.A2018001.h27v06.006.2018017224135.hdf',230806000,'/allData/6/MOD13Q1/2018/001','D://test//','05','h27v06','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:28:01','2018-03-24 13:32:28'),(484683,99999999,'MOD13Q1.A2018001.h27v07.006.2018017224318.hdf',145419000,'/allData/6/MOD13Q1/2018/001','D://test//','05','h27v07','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:05:01','2018-03-24 13:32:28'),(484684,99999999,'MOD13Q1.A2018001.h27v08.006.2018017223817.hdf',45955200,'/allData/6/MOD13Q1/2018/001','D://test//','05','h27v08','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:27:42','2018-03-24 13:32:28'),(484694,99999999,'MOD13Q1.A2018001.h28v07.006.2018017224155.hdf',104436000,'/allData/6/MOD13Q1/2018/001','D://test//','05','h28v07','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:37:25','2018-03-24 13:32:28'),(484695,99999999,'MOD13Q1.A2018001.h28v08.006.2018017224201.hdf',61797400,'/allData/6/MOD13Q1/2018/001','D://test//','05','h28v08','1','07',NULL,'2018-01-01 01:37:17','2018-03-11 19:56:03','2018-03-24 13:32:28'),(484962,99999999,'MOD13A2.A2018001.h26v06.006.2018017223743.hdf',20265300,'/allData/6/MOD13A2/2018/001','D://test//','05','h26v06','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:01:31','2018-03-24 13:32:28'),(484968,99999999,'MOD13A2.A2018001.h27v06.006.2018017224041.hdf',22187900,'/allData/6/MOD13A2/2018/001','D://test//','05','h27v06','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:02:31','2018-03-24 13:32:28'),(484969,99999999,'MOD13A2.A2018001.h27v07.006.2018017224243.hdf',13605300,'/allData/6/MOD13A2/2018/001','D://test//','05','h27v07','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:03:09','2018-03-24 13:32:28'),(484970,99999999,'MOD13A2.A2018001.h27v08.006.2018017223746.hdf',5209930,'/allData/6/MOD13A2/2018/001','D://test//','05','h27v08','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:03:34','2018-03-24 13:32:28'),(484980,99999999,'MOD13A2.A2018001.h28v07.006.2018017224116.hdf',10179500,'/allData/6/MOD13A2/2018/001','D://test//','05','h28v07','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:03:45','2018-03-24 13:32:28'),(484981,99999999,'MOD13A2.A2018001.h28v08.006.2018017224127.hdf',6644850,'/allData/6/MOD13A2/2018/001','D://test//','05','h28v08','1','07',NULL,'2018-01-01 01:37:22','2018-03-11 20:04:03','2018-03-24 13:32:28'),(485279,99999999,'MOD11A2.A2018001.h26v06.006.2018011144413.hdf',7935470,'/allData/6/MOD11A2/2018/001','D://test//','05','h26v06','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 20:04:15','2018-03-24 13:32:28'),(485285,99999999,'MOD11A2.A2018001.h27v06.006.2018011144721.hdf',5892460,'/allData/6/MOD11A2/2018/001','D://test//','05','h27v06','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 20:04:30','2018-03-24 13:32:28'),(485286,99999999,'MOD11A2.A2018001.h27v07.006.2018011144529.hdf',5320390,'/allData/6/MOD11A2/2018/001','D://test//','05','h27v07','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 20:04:41','2018-03-24 13:32:28'),(485287,99999999,'MOD11A2.A2018001.h27v08.006.2018011144650.hdf',1810170,'/allData/6/MOD11A2/2018/001','D://test//','05','h27v08','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 20:04:51','2018-03-24 13:32:28'),(485297,99999999,'MOD11A2.A2018001.h28v07.006.2018011144314.hdf',3891660,'/allData/6/MOD11A2/2018/001','D://test//','05','h28v07','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 19:56:28','2018-03-24 13:32:28'),(485298,99999999,'MOD11A2.A2018001.h28v08.006.2018011144431.hdf',1664970,'/allData/6/MOD11A2/2018/001','D://test//','05','h28v08','1','07',NULL,'2018-01-01 01:37:30','2018-03-11 19:57:01','2018-03-24 13:32:28'),(485596,NULL,'MOD11A2.A2018009.h26v06.006.2018018033532.hdf',7560940,'/allData/6/MOD11A2/2018/009',NULL,'05','h26v06','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485602,NULL,'MOD11A2.A2018009.h27v06.006.2018018033814.hdf',7295810,'/allData/6/MOD11A2/2018/009',NULL,'05','h27v06','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485603,NULL,'MOD11A2.A2018009.h27v07.006.2018018033959.hdf',5356660,'/allData/6/MOD11A2/2018/009',NULL,'05','h27v07','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485604,NULL,'MOD11A2.A2018009.h27v08.006.2018018033455.hdf',1850190,'/allData/6/MOD11A2/2018/009',NULL,'05','h27v08','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485614,NULL,'MOD11A2.A2018009.h28v07.006.2018018033910.hdf',3873210,'/allData/6/MOD11A2/2018/009',NULL,'05','h28v07','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485615,NULL,'MOD11A2.A2018009.h28v08.006.2018018033912.hdf',1550580,'/allData/6/MOD11A2/2018/009',NULL,'05','h28v08','1','07',NULL,'2018-01-09 01:37:44',NULL,'2018-03-24 13:32:28'),(485882,NULL,'MOD13Q1.A2018017.h26v06.006.2018033223538.hdf',234800000,'/allData/6/MOD13Q1/2018/017',NULL,'05','h26v06','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(485888,NULL,'MOD13Q1.A2018017.h27v06.006.2018033223932.hdf',246301000,'/allData/6/MOD13Q1/2018/017',NULL,'05','h27v06','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(485889,NULL,'MOD13Q1.A2018017.h27v07.006.2018033223543.hdf',142134000,'/allData/6/MOD13Q1/2018/017',NULL,'05','h27v07','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(485890,NULL,'MOD13Q1.A2018017.h27v08.006.2018033223533.hdf',45027800,'/allData/6/MOD13Q1/2018/017',NULL,'05','h27v08','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(485900,NULL,'MOD13Q1.A2018017.h28v07.006.2018033223425.hdf',101272000,'/allData/6/MOD13Q1/2018/017',NULL,'05','h28v07','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(485901,NULL,'MOD13Q1.A2018017.h28v08.006.2018033223358.hdf',64193000,'/allData/6/MOD13Q1/2018/017',NULL,'05','h28v08','1','07',NULL,'2018-01-17 01:37:56',NULL,'2018-03-24 13:32:28'),(486168,NULL,'MOD13A2.A2018017.h26v06.006.2018033223444.hdf',20347700,'/allData/6/MOD13A2/2018/017',NULL,'05','h26v06','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486174,NULL,'MOD13A2.A2018017.h27v06.006.2018033223839.hdf',22437900,'/allData/6/MOD13A2/2018/017',NULL,'05','h27v06','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486175,NULL,'MOD13A2.A2018017.h27v07.006.2018033223502.hdf',13466900,'/allData/6/MOD13A2/2018/017',NULL,'05','h27v07','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486176,NULL,'MOD13A2.A2018017.h27v08.006.2018033223504.hdf',5169440,'/allData/6/MOD13A2/2018/017',NULL,'05','h27v08','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486186,NULL,'MOD13A2.A2018017.h28v07.006.2018033223350.hdf',10457300,'/allData/6/MOD13A2/2018/017',NULL,'05','h28v07','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486187,NULL,'MOD13A2.A2018017.h28v08.006.2018033223328.hdf',6663200,'/allData/6/MOD13A2/2018/017',NULL,'05','h28v08','1','07',NULL,'2018-01-17 01:38:01',NULL,'2018-03-24 13:32:28'),(486485,NULL,'MOD11A2.A2018017.h26v06.006.2018026031805.hdf',7351860,'/allData/6/MOD11A2/2018/017',NULL,'05','h26v06','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486491,NULL,'MOD11A2.A2018017.h27v06.006.2018026032105.hdf',6440720,'/allData/6/MOD11A2/2018/017',NULL,'05','h27v06','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486492,NULL,'MOD11A2.A2018017.h27v07.006.2018026031743.hdf',5137340,'/allData/6/MOD11A2/2018/017',NULL,'05','h27v07','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486493,NULL,'MOD11A2.A2018017.h27v08.006.2018026031943.hdf',1856530,'/allData/6/MOD11A2/2018/017',NULL,'05','h27v08','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486503,NULL,'MOD11A2.A2018017.h28v07.006.2018026032048.hdf',3662010,'/allData/6/MOD11A2/2018/017',NULL,'05','h28v07','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486504,NULL,'MOD11A2.A2018017.h28v08.006.2018026032023.hdf',1825890,'/allData/6/MOD11A2/2018/017',NULL,'05','h28v08','1','07',NULL,'2018-01-17 01:38:07',NULL,'2018-03-24 13:32:28'),(486802,NULL,'MOD11A2.A2018025.h26v06.006.2018034033222.hdf',7449370,'/allData/6/MOD11A2/2018/025',NULL,'05','h26v06','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28'),(486808,NULL,'MOD11A2.A2018025.h27v06.006.2018034033244.hdf',6544290,'/allData/6/MOD11A2/2018/025',NULL,'05','h27v06','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28'),(486809,NULL,'MOD11A2.A2018025.h27v07.006.2018034033131.hdf',5346410,'/allData/6/MOD11A2/2018/025',NULL,'05','h27v07','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28'),(486810,NULL,'MOD11A2.A2018025.h27v08.006.2018034033157.hdf',2221280,'/allData/6/MOD11A2/2018/025',NULL,'05','h27v08','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28'),(486820,NULL,'MOD11A2.A2018025.h28v07.006.2018034032936.hdf',3906130,'/allData/6/MOD11A2/2018/025',NULL,'05','h28v07','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28'),(486821,NULL,'MOD11A2.A2018025.h28v08.006.2018034033018.hdf',2242250,'/allData/6/MOD11A2/2018/025',NULL,'05','h28v08','1','07',NULL,'2018-01-25 01:38:20',NULL,'2018-03-24 13:32:28');
/*!40000 ALTER TABLE `t6org_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t7pdt_data`
--

DROP TABLE IF EXISTS `t7pdt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t7pdt_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据产品编号',
  `type_` enum('01','02','03','04') DEFAULT NULL COMMENT '产品类型代码',
  `source_file_list` varchar(256) DEFAULT NULL COMMENT '源文件列表',
  `sample_path` varchar(256) DEFAULT NULL COMMENT '样本路径',
  `product_path` varchar(256) DEFAULT NULL COMMENT '产品路径',
  `collect_time` datetime DEFAULT NULL COMMENT '数据采集时间',
  `generate_time` datetime DEFAULT NULL COMMENT '产品生成时间',
  `zone_code` char(4) DEFAULT NULL COMMENT '行政区域代码',
  `fail_code` enum('01','02') DEFAULT NULL COMMENT '文件下载失败原因代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t7pdt_data`
--

LOCK TABLES `t7pdt_data` WRITE;
/*!40000 ALTER TABLE `t7pdt_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `t7pdt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t8message`
--

DROP TABLE IF EXISTS `t8message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t8message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统消息编号',
  `send_userid` bigint(20) unsigned DEFAULT NULL COMMENT '发送人ID',
  `add_time` datetime DEFAULT NULL COMMENT '系统消息时间',
  `content` text COMMENT '系统消息内容',
  `type_` enum('01','02','03') DEFAULT NULL COMMENT '消息类型代码',
  `flag` tinyint(1) DEFAULT NULL COMMENT '群发标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t8message`
--

LOCK TABLES `t8message` WRITE;
/*!40000 ALTER TABLE `t8message` DISABLE KEYS */;
INSERT INTO `t8message` VALUES (80,29,'2018-04-14 11:45:00','你的产品服务准备到期了,到期时间:2018-03-30 00:00:00.0,为了不影响你的使用,请尽快续费。','01',3),(81,18,'2018-04-14 11:45:13','sdddddddddddddddddddddddddddddddf','01',1),(82,18,'2018-04-14 11:50:24','dffffffffffffffffffff','01',3),(83,18,'2018-04-14 11:50:46','dfffffffffffffffffff','01',1),(84,18,'2018-04-14 11:51:18','这是一个群发消息','01',2),(85,18,'2018-04-14 13:20:36','asssssssssssss','01',2),(86,18,'2018-04-14 13:21:18','请完善资料','01',1),(87,11,'2018-04-14 13:51:35','你好','01',1),(88,11,'2018-04-14 13:51:43','你好','01',1),(89,11,'2018-04-14 13:51:53','你们好','02',2),(90,1,'2018-04-14 13:52:00','你的产品服务准备到期了,到期时间:2018-04-02 23:36:15.0,为了不影响你的使用,请尽快续费。','01',3),(91,8,'2018-04-14 13:52:00','你的产品服务准备到期了,到期时间:2018-04-02 23:35:19.0,为了不影响你的使用,请尽快续费。','01',3),(93,27,'2018-04-14 15:48:00','你的产品服务准备到期了,到期时间:2018-04-15 00:00:00.0,为了不影响你的使用,请尽快续费。','01',3),(96,27,'2018-04-21 20:03:00','Your product service is ready to expire, due time:2018-04-16 00:00:00.0, In order to not affect your use, please continue to pay as soon as possible.','01',3),(97,27,'2018-04-21 20:04:00','Your product service is ready to expire, due time:2018-04-16 00:00:00.0, In order to not affect your use, please continue to pay as soon as possible.','01',3),(98,27,'2018-04-21 20:05:00','Your product service is ready to expire, due time:2018-04-16 00:00:00.0, In order to not affect your use, please continue to pay as soon as possible.','01',3);
/*!40000 ALTER TABLE `t8message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t9sample_info`
--

DROP TABLE IF EXISTS `t9sample_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t9sample_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '样本编号',
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `identifier` bigint(20) unsigned DEFAULT NULL COMMENT '源文件编号',
  `name_` varchar(128) DEFAULT NULL COMMENT '样本名称',
  `number_` varchar(30) DEFAULT NULL COMMENT '样本数量',
  `path_` varchar(30) DEFAULT NULL COMMENT '样本路径',
  `type_content` varchar(30) DEFAULT NULL COMMENT '样本类型内容',
  `datetime_` datetime DEFAULT NULL COMMENT '样本时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifier` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t9sample_info`
--

LOCK TABLES `t9sample_info` WRITE;
/*!40000 ALTER TABLE `t9sample_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t9sample_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tkvalue`
--

DROP TABLE IF EXISTS `tkvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tkvalue` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `key_` varchar(64) NOT NULL COMMENT 'key',
  `value_` varchar(256) NOT NULL COMMENT '名称',
  `info` varchar(128) DEFAULT '' COMMENT '备注',
  `status_` int(4) DEFAULT '0' COMMENT '状态 （0未/ 1已发布,2已结束）',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='kvalue表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tkvalue`
--

LOCK TABLES `tkvalue` WRITE;
/*!40000 ALTER TABLE `tkvalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `tkvalue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-21 20:48:45
