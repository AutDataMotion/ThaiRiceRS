
##算法远程调用
1 java调用端代码已做，在DUCRPC工程中generated/RPCRice中
2 被调用端（算法）还未真实调用
3 未跑调用流程

##系统监控
从Linux改为Windows：
1 将工程下 build-lib/hyperic-sigar-1.6.4/sigar-bin/lib/sigar-amd64-winnt.dll 和sigar-x86-winnt.dll拷贝到 安装jdk目录下的bin文件夹中
2 磁盘还未改过来，不可用，稍后加上

##系统日志接口
T2syslogService.addLog(EnumT2sysLog type, BigInteger userid, String userName, String action_, String content);

1 会存入log日志文件
2 会存入log数据库表
3 定时（2秒）存储一次