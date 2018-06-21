# ThaiRiceRS

## use git
关于服务器端产品数据的组织形式说明：
=========================
#1.生产的产品数据的组织路径如下：
--------------------------
```
##"E:/thairiceproduct/"+productKind+"/"+productDate+"/"+areacode.shp;demo：E:/thairiceproduct/Area/2018-05-05/72.shp</br>
##其中productKind代表产品类型：{Area（面积），Yield（估产）,Growth（长势），Drought（干旱）}</br>
##productDate代表日期：YYYY-MM-DD（2018-05-05）</br>
##areacode代表行政区划代码</br>
```
#2.发布产品的工作空间路径如下：
-----------------------
##流程如下：系统会将服务器端的产品数据拷贝到工作空间</br>
##“E:/thairiceproduct/Area/2018-05-05/72.shp”---》“E:/arcgisserver_shp_workspace/Area/2018-05-05_72.shp”</br>
##E:/arcgisserver_shp_workspace/productKind</br>
##其中productKind代表产品类型：{Area（面积），Yield（估产）,Growth（长势），Drought（干旱）}</br>
##demo：E:/arcgisserver_shp_workspace/Area</br>

#3.面积产品创建相关说明
------------------
##1）前端initFilesTable通过ajax执行后端getFilesfromServerWorkspace请求遥感影像数据列表，前端以表格的形式呈现影像存放空间中已有的数据。同时在请求过程中，将数据列表存入数据库saveTifFilesInfo2Database</br>
##2）用户点击相应的遥感影像文件后，（通过tomcat建立文件服务，E:/areatif，文件夹里的栅格文件可以通过url进行访问，通过GP服务的参数形式，进行波段改变显示！）（！----通过copyAreaTifFile2gpWorkspace将选中的影像文件copy到gpmodels中，以备改变波段显示，---）同时前端通过addRasterLayer加载对应的影像到地图，同时加载影像文件对应的产品数据addProductFeatureLayer（文件名对应：如2018-05-11_72.tif对应2018-05-11_72_temp.shp）如有，即加载，如无则地图无反应。</br>
##3）用户基于选择加载的遥感影像勾绘样本数据，如rice、building等，前端通过saveDraw将绘制的样本的Graphics转成Geojson传回后端generateShpfileByGeoJson，生成对应的shp文件，并将文件信息存入数据库</br>
##4）所需样本绘制完成后，前端执行generateTempProduct，进而在服务端执行中间产品的数据生成任务，通过数据库查询之前所有相关样本数据信息，执行生成任务，生成的中间产品数据保存在E:/areaSampleAndTempProduct/2018-05-11_72/tempProduct/2018-05-11_72_temp.shp，</br>
##同时转存到E:/arcgisserver_shp_workspace/Area/2018-05-11_72_temp.shp 进行地图显示。</br>
##重命名并转存到E:/gpmodels/waitingformodify.shp,以备后期编辑修改</br>
##5）前端用户对于生成的中间产品数据可以进行修改（add、delete feature）绘制需要添加或删除的feature执行EditFeatures，执行Edit_Preview--》doGeoprocessor---》copyResult2Modelspace（根据jobId将编辑之后生成的结果数据copy到E:/gpmodels/waitingformodify.shp），生成修改后的结果。
##6）用户对于编辑之后的结果数据，进行最终的保存操作，Edit_Save--》SaveAreaEditedProduct，将最终的数据保存到E:/thairiceproduct/Area/2018-05-11/72.shp。</br>

#栅格图像存放工作空间：E:/areatif</br>
#以栅格文件2018-05-11_72.tif为例：</br>
#样本保存的目录为：E:/areaSampleAndTempProduct/2018-05-11_72/sample/样本名称（rice、building etc）</br>
#生成的中间产品数据存放路径为：E:/areaSampleAndTempProduct/2018-05-11_72/tempProduct/2018-05-11_72_temp.shp</br>
#生成的中间产品文件会转存到E:/arcgisserver_shp_workspace/Area/2018-05-11_72_temp.shp 进行地图显示</br>
#生成的中间产品数据的编辑工作空间为：E:/gpmodels/waitingformodify.shp</br>
