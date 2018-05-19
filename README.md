# ThaiRiceRS

## use git
关于服务器端产品数据的组织形式说明：
1.生产的产品数据的组织路径如下：
"E:/thairiceproduct/"+productKind+"/"+productDate+"/"+areacode.shp;
demo：E:/thairiceproduct/Area/2018-05-05/72.shp
其中productKind代表产品类型：{Area（面积），Yield（估产）,Growth（长势），Drought（干旱）}
productDate代表日期：YYYY-MM-DD（2018-05-05）
areacode代表行政区划代码

2.发布产品的工作空间路径如下：
流程如下：系统会将服务器端的产品数据拷贝到工作空间
“E:/thairiceproduct/Area/2018-05-05/72.shp”---》“E:/arcgisserver_shp_workspace/Area/2018-05-05_72.shp”
E:/arcgisserver_shp_workspace/productKind
其中productKind代表产品类型：{Area（面积），Yield（估产）,Growth（长势），Drought（干旱）}
demo：E:/arcgisserver_shp_workspace/Area

3.面积样本创建相关说明
栅格图像存放工作空间：E:/areatif

