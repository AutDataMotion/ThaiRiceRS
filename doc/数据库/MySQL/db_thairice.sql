use thairice;
drop table if exists t1parameter;  
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


drop table if exists t2syslog;  
Create table t2syslog(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'系统日志编号',
type_ CHAR(3) default '0' COMMENT'系统日志类型代码',
userid bigint(20) unsigned COMMENT'用户ID',
action_ VARCHAR(256) COMMENT'操作',
content TEXT COMMENT'系统日志内容',
add_time datetime   COMMENT'系统日志生成日期'

)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists t3user;  

Create table t3user(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'用户ID',
type_ ENUM ('01','02','03') NOT NULL COMMENT'用户类型代码',
account VARCHAR(180) UNIQUE NOT NULL COMMENT'用户名',
pwd VARCHAR(60) NOT NULL COMMENT'密码',
name_ VARCHAR(180) NOT NULL COMMENT'姓名',
phone VARCHAR(30) NOT NULL COMMENT'手机号码',
email VARCHAR(240) NOT NULL COMMENT'电子邮箱地址',
address VARCHAR(1500) COMMENT'通讯地址',
zip_encode VARCHAR(30) NOT NULL COMMENT'邮政编码',
heading VARCHAR (255) COMMENT'头像',
company VARCHAR(240) COMMENT'公司名称',
industry VARCHAR(80) COMMENT'行业',
create_time datetime   COMMENT'创建时间',
status_ ENUM ('01','02','03','04') NOT NULL COMMENT'用户审核状态代码'
)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists r4message_send;  
Create table r4message_send(
`id` bigint(20) unsigned NOT NULL PRIMARY KEY  COMMENT'系统消息编号',
receive_userid bigint(20) unsigned COMMENT'接收人ID',
status_ ENUM ('01','02') NOT NULL COMMENT'消息状态代码'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;


drop table if exists t5parameter_type;  

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




drop table if exists t6org_data;  
Create table t6org_data(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'源文件编号',
userid bigint(20) unsigned COMMENT'用户ID',
name_ VARCHAR(128) COMMENT'文件名称',
size_ FLOAT DEFAULT 0 COMMENT'文件大小',
download_path VARCHAR(256) COMMENT'文件下载地址',
storage_path VARCHAR(256) COMMENT'文件存放地址',
type_ ENUM('01','02','03','04','05') COMMENT'文件类型代码',
row_column VARCHAR(256) COMMENT'文件行列号',
band_number VARCHAR(256) COMMENT'波段数编码',
status_ ENUM ('01','02','03','04','05','06','07') COMMENT'文件状态代码',
fail_code ENUM ('01','02') COMMENT'文件下载失败原因代码',
collect_time datetime   COMMENT'数据采集时间',
dload_start_time datetime   COMMENT'文件下载开始时间',
dload_end_time datetime   COMMENT'文件下载结束时间'

)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists t7pdt_data;  
Create table t7pdt_data(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'数据产品编号',
type_ ENUM('01','02','03','04') COMMENT'产品类型代码',
source_file_list VARCHAR(256) COMMENT'源文件列表',
sample_path VARCHAR(256) COMMENT'样本路径',
product_path VARCHAR(256) COMMENT'产品路径',
collect_time datetime   COMMENT'数据采集时间',
generate_time datetime   COMMENT'产品生成时间',
zone_code CHAR(4) COMMENT'行政区域代码',
fail_code ENUM('01','02') COMMENT'文件下载失败原因代码'
)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists t8message;  
Create table t8message(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'系统消息编号',
send_userid bigint(20) unsigned COMMENT'发送人ID',
add_time datetime   COMMENT'系统消息时间',
content TEXT COMMENT'系统消息内容',
type_ ENUM('01','02','03') COMMENT'消息类型代码',
flag TINYINT(1) COMMENT'群发标志'
)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

drop table if exists t9sample_info;  
Create table t9sample_info(
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT'样本编号',
userid bigint(20) unsigned COMMENT'用户ID',
identifier bigint(20) unsigned UNIQUE COMMENT'源文件编号',
name_ VARCHAR(128) COMMENT '样本名称',
number_ VARCHAR(30) COMMENT'样本数量',
path_ VARCHAR(30) COMMENT'样本路径',
type_content VARCHAR(30) COMMENT'样本类型内容',
datetime_ datetime   COMMENT'样本时间'

)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists t10pdt_report;  
Create table t10pdt_report(
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT'产品报表编号',
userid bigint(20) unsigned COMMENT'用户ID',
add_time datetime   COMMENT'报告创建时间',
collect_time datetime   COMMENT'采集日期',
start_time datetime   COMMENT'查询产品开始日期',
end_time datetime   COMMENT'查询产品结束日期',
zone_code INT(16) COMMENT'查询行政区域代码',
crop_type ENUM ('01') COMMENT'查询作物种类代码',
pdt_type ENUM ('01','02','03','04') COMMENT'产品类型代码',
suffix ENUM ('01','02') COMMENT'报告格式代码'

)ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists t11zone;  
Create table t11zone(
id INT(8) COMMENT'行政区划id',
code_ INT(16) PRIMARY KEY UNIQUE NOT NULL COMMENT'行政区划代码',
type_  ENUM ('01','02','03','04') COMMENT'产品类型代码',
name_ VARCHAR(30)COMMENT'行政区划名称'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


drop table if exists tkvalue;  
CREATE TABLE `tkvalue` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `key_` varchar(64) NOT NULL COMMENT 'key',
  `value_` varchar(256) NOT NULL COMMENT '名称',
  `info` varchar(128) DEFAULT '' COMMENT '备注',
  `status_` int(4) DEFAULT '0' COMMENT '状态 （0未/ 1已发布,2已结束）',
  `add_time` datetime  COMMENT'添加时间'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='kvalue表'
