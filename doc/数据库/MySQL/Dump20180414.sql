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
  `id` bigint(20) unsigned NOT NULL COMMENT '系统消息编号',
  `receive_userid` bigint(20) unsigned DEFAULT NULL COMMENT '接收人ID',
  `status_` enum('01','02') NOT NULL COMMENT '消息状态代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r4message_send`
--

LOCK TABLES `r4message_send` WRITE;
/*!40000 ALTER TABLE `r4message_send` DISABLE KEYS */;
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
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '参数明细备注',
  `status_` tinyint(1) DEFAULT NULL COMMENT '删除标志',
  `version` int(9) DEFAULT NULL COMMENT '版本号',
  `datetime_` datetime DEFAULT NULL COMMENT '时间戳',
  `parm_type_id` varchar(8) NOT NULL COMMENT '参数类型编号',
  `name_` varchar(256) DEFAULT NULL COMMENT '参数明细名称',
  `value_` varchar(256) NOT NULL COMMENT '参数值',
  `parm__id` varchar(3) NOT NULL COMMENT '参数明细编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t1parameter`
--

LOCK TABLES `t1parameter` WRITE;
/*!40000 ALTER TABLE `t1parameter` DISABLE KEYS */;
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
INSERT INTO `t2syslog` VALUES (1,'1',1,'test','action1','测试内容详情','2018-03-02 00:00:00'),(2,'2',2,'test','action2','测试内容详情','2018-03-04 00:00:00'),(4,'0',1,'test2','action1','测试内容详情','2018-03-06 00:00:00'),(5,'2',1,'test','actions','测试内容详情','2018-03-06 00:00:00'),(6,'2',1,'test','actions','测试内容详情','2018-03-06 00:00:00'),(7,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(8,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(9,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(10,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(11,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(12,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(13,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(14,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(15,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(16,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00'),(17,'2',2,'test','actions','测试内容详情','2018-03-06 00:00:00');
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
  `type_` enum('01普通用户','02系统操作员','03系统管理员') NOT NULL COMMENT '用户类型代码',
  `account` varchar(180) NOT NULL COMMENT '用户名',
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
  `status_` enum('01未审核','02审核通过','03审核未通过','04用户过期') NOT NULL COMMENT '用户审核状态代码',
  `identiCode` varchar(6) DEFAULT NULL COMMENT '忘记密码校验码',
  `expirTime` datetime DEFAULT NULL COMMENT '校验码到期时间',
  `Prdt_EfDt` datetime DEFAULT NULL COMMENT '产品生效日期',
  `PD_ExDat` datetime DEFAULT NULL COMMENT '产品到期日期',
  `activation` int(1) DEFAULT '0' COMMENT '账号是否激活',
  `PD_TpCd` varchar(50) DEFAULT NULL COMMENT '产品类型代码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t3user`
--

LOCK TABLES `t3user` WRITE;
/*!40000 ALTER TABLE `t3user` DISABLE KEYS */;
INSERT INTO `t3user` VALUES (1,'01普通用户','111111','111111','111111','15800001111','111@163.com','北京','1','1','北京','农业','2018-02-26 07:01:28','02审核通过',NULL,NULL,'2018-04-02 23:36:12','2018-04-02 23:36:15',NULL,NULL),(3,'03系统管理员','zhuce2','e10adc3949ba59abbe56e057f20f883e','bijioj','15000001111','yangtaobeijing@126.com',NULL,'000',NULL,'bfofjaofj','fafaf','2018-04-02 23:34:25','01未审核',NULL,NULL,'2018-04-02 23:36:01','2018-04-02 23:36:04',NULL,NULL),(11,'03系统管理员','admin','b9d11b3be25f5a1a7dc8ca04cd310b28','admin','15811112223','bluesky1986yang@163.com','zhongguobeijingshixichengqu','100055','/upload/head/4766105322444d71851785d3c4a48fcf.jpg','beijing','agricultural','2018-04-02 22:53:23','01未审核',NULL,NULL,'2018-04-02 00:00:00','2018-05-31 00:00:00',1,'01,02,03,04,'),(14,'02系统操作员','caozuoyuan02','e10adc3949ba59abbe56e057f20f883e','caozuoyuan02','15801019999','caozuoyuan02@126.com','泰国某区','000',NULL,'农业公司','agricultural','2018-04-08 15:51:06','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(15,'02系统操作员','caozuoyuan03','e10adc3949ba59abbe56e057f20f883e','caozuoyuan','13800002222','ewew@126.com','ewew','000',NULL,'wewew','agricultural','2018-04-08 15:52:29','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(16,'02系统操作员','caozuoyuan04','e10adc3949ba59abbe56e057f20f883e','caozuoyuan04','15801019999','caozuoyuan04@hotmail.com','beijingshi','000',NULL,'lianxiang','agricultural','2018-04-08 15:53:02','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(24,'02系统操作员','guanliyuan01','e10adc3949ba59abbe56e057f20f883e','guanliyuan01','15801012222','15801012222@hotmail.com','北京市西城区','000',NULL,'建设银行','agricultural','2018-04-13 21:17:08','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(25,'02系统操作员','caozuoyuan01','e10adc3949ba59abbe56e057f20f883e','yangtao','15811112222','yangtao@126.com','xichengqu','000',NULL,'beii','agricultural','2018-04-13 22:19:04','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(27,'01普通用户','yangtao7979','9bfb60950a3b7fb1d3d0edf9a1905c00','yangtao','15899998877','522015966@qq.com','bejing','100055','/upload/head/e0e04578633a44499ad11d0ca44b10b3.jpg','zhongguojiansheyinhang','it','2018-04-13 23:11:21','02审核通过',NULL,NULL,'2018-04-13 00:00:00','2018-04-15 00:00:00',1,'01,02,'),(28,'02系统操作员','caozuoyuan22','e10adc3949ba59abbe56e057f20f883e','caozuoyuan22','15801012222','12322@126.com','beijingshixicheng','000',NULL,'baidu','agricultural','2018-04-13 23:50:41','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(29,'02系统操作员','caozuoyuan12','e10adc3949ba59abbe56e057f20f883e','caozuoyuan12','15811112345','caozuoyuan12@126.com','beiingshi','000',NULL,'baidu','agricultural','2018-04-14 09:58:47','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(30,'02系统操作员','caozuoyuan05','e10adc3949ba59abbe56e057f20f883e','caozuoyuan05','15988887676','caozuoyuan05@163.com','nanjinshi','000',NULL,'gongshang','agricultural','2018-04-14 09:59:45','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(31,'02系统操作员','caozuoyuan06','e10adc3949ba59abbe56e057f20f883e','caozuoyuan06','17899876543','caozuoyuan06@qq.com','beijingshi','000',NULL,'kejikaifagongsi','agricultural','2018-04-14 10:00:33','02审核通过',NULL,NULL,NULL,NULL,0,NULL),(32,'01普通用户','zhuchaobin1','905989566041dc2f1d3190bc0397e174','zhuchaobin','13056789876','nanjing2008@163.com','beijingshixichengqu','1000555',NULL,'zhongguojiansheyinhang','IT','2018-04-14 19:15:45','03审核未通过',NULL,NULL,'2018-04-14 00:00:00','2018-04-30 00:00:00',0,'01,02,03,04,');
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
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '源文件编号',
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
  `dload_end_time` datetime DEFAULT NULL COMMENT '文件下载结束时间',
  PRIMARY KEY (`id`),
  KEY `t6org_data_index1` (`name_`) USING HASH,
  KEY `t6org_data_index2` (`status_`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=488361 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t6org_data`
--

LOCK TABLES `t6org_data` WRITE;
/*!40000 ALTER TABLE `t6org_data` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t8message`
--

LOCK TABLES `t8message` WRITE;
/*!40000 ALTER TABLE `t8message` DISABLE KEYS */;
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

-- Dump completed on 2018-04-14 23:21:19
