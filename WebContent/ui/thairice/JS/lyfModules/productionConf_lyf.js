/**
 * 
 */
var app = {};
var rasterLayerUrl = "http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer";
var featureLayerUrl = "http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer";
var rasterLayerWorkspace = "areatif";
var eraseUrl = "http://localhost:6080/arcgis/rest/services/erase/GPServer/erase";
var mergeUrl = "http://localhost:6080/arcgis/rest/services/merge/GPServer/merge";
//var merge_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/merge_gpserver/";
//var erase_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/erase_gpserver/";
function init_productionConf_Monitoring()
{

//    packages: [{
//      "name": "lyfModules",
//      "location": "/ui/thairice/JS/" + "lyfModules"
//    }]
	/************初始化加载文件列表**************/
	app.files_excel = $('#files_excel').DataTable({
		columns: [
        	{ title: "Name" },
            { title: "Date" },
            { title: "District" }
        ]
    });
	 $('#files_excel tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	app.files_excel.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    } );
	/******************************************/
	app.sample_excel = $('#sample_excel').DataTable( {
		"searching": false,
		"paging":   false,
        "ordering": false,
        "info":     false
    } );
	$('#addRow').on( 'click', function () {
		app.undoManager.clearRedo();
		app.undoManager.clearUndo();
		app.graphicsLayer.clear();
		app.sample_excel.row.add( [
			"<input type='text'  placeholder='sample name' style='width:100px; text-align:center;'>",
			"<button type='button' onclick='startDraw_sample($(this))'><i class='glyphicon glyphicon-edit'></i>Draw</button>"+
          	"<button type='button' onclick='saveDraw_sample($(this))'><i class='glyphicon glyphicon-ok-circle'></i>Save</button>"+
          	"<button type='button' onclick='deleteDraw_sample($(this))'><i class='glyphicon glyphicon-remove-sign'></i>Cancel</button>"
           
        ] ).draw( false );
 
    } );
	
	require(["esri/map",
		"esri/dijit/Scalebar",
		"esri/undoManager",
		"esri/toolbars/draw",
        "esri/graphic",
        "esri/symbols/SimpleMarkerSymbol",
        "esri/symbols/SimpleLineSymbol",
        "esri/symbols/SimpleFillSymbol",
        "esri/layers/GraphicsLayer",
        "esri/layers/DynamicLayerInfo", "esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","esri/layers/RasterDataSource",
//        "lyfModules/customoperation",
        "dojo/_base/connect", "esri/Color", "dojo/parser", "dijit/registry",
	    "dojo/dom",
	    "dojo/on",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,UndoManager,Draw, Graphic,
	        SimpleMarkerSymbol, SimpleLineSymbol,SimpleFillSymbol,GraphicsLayer,RasterDataSource,
	        DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,
	        connect, Color, parser, registry,
	        dom,on, ArcGISDynamicMapServiceLayer,Extent) {
	    //var map = new Map("mapDiv");
		 	parser.parse();

	        //specify the number of undo operations allowed using the maxOperations parameter
	        app.undoManager = new UndoManager();
	        // hook up undo/redo buttons
	        registry.byId("undo").on("click", function() {
	        	app.undoManager.undo();
	        });
	        registry.byId("redo").on("click", function() {
	        	app.undoManager.redo();
	        });
	        connect.connect(app.undoManager,"onChange",function(){
	            //enable or disable buttons depending on current state of application
	            if (app.undoManager.canUndo) {
	              registry.byId("undo").set("disabled", false);
	              registry.byId("undo").set("iconClass","undoIcon");
	            } else {
	              registry.byId("undo").set("disabled", true);
	              registry.byId("undo").set("iconClass","undoGrayIcon");
	            }

	            if (app.undoManager.canRedo) {
	              registry.byId("redo").set("disabled", false);
	              registry.byId("redo").set("iconClass","redoIcon");
	            } else {
	              registry.byId("redo").set("disabled", true);
	              registry.byId("redo").set("iconClass","redoGrayIcon");
	            }
	          });
//		var layer1 = new ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer");
	    //app.map.addLayer(layer1);
//	    var initExtent = layer1.fullExtent;
//	    console.log(layer1.fullExtent);
	    app.map = new Map("mapDiv_productionConf", {
	    	basemap: "streets",
	    	extent: new Extent({xmin:97.34370447600008,ymin:5.612765642000056,xmax:105.63700411400004,ymax:20.46490708700003,spatialReference:{wkid:4326}}),
	        logo:false
	      });
	    var scalebar = new Scalebar({
	          map: app.map,
	          // "dual" displays both miles and kilometers
	          // "english" is the default, which displays miles
	          // use "metric" for kilometers
	          scalebarUnit: "metric"
	       });
	    
//	    app.map.on("layers-add-result", initEditing);
	 	
	    app.graphicsLayer = new GraphicsLayer();
	    
	    app.map.on("load", createToolbar);
	    function createToolbar(themap) {
	    	  
	    	app.map.addLayer(app.graphicsLayer);
	    	
	    	app.toolbar = new Draw(app.map);
	    	app.toolbar.on("draw-end", addToMap);
	    }
	    function addToMap(evt) {
	          var symbol;
	          // toolbar.deactivate();
//	          map.showZoomSlider();
	          switch (evt.geometry.type) {
	            case "point":
	            case "multipoint":
	              symbol = new SimpleMarkerSymbol();
	              break;
	            case "polyline":
	              symbol = new SimpleLineSymbol();
	              break;
	            default:
	              symbol = new SimpleFillSymbol();
	              break;
	          }
	          var graphic = new Graphic(evt.geometry, symbol);
	          var customoperation = require('customoperation');
	          var operation = new customoperation.Add({
		          graphicsLayer: app.graphicsLayer,
		          addedGraphic: graphic
	          });

	          app.undoManager.add(operation);
	          
	          app.graphicsLayer.add(graphic);
	        }
	});
	
}
function getLayerSource(workspaceId,dataSourceName)
{
	var dataSource = new esri.layers.TableDataSource();
	dataSource.workspaceId = workspaceId;
	dataSource.dataSourceName = dataSourceName;
	
	var layerSource = new esri.layers.LayerDataSource();
	layerSource.dataSource = dataSource;
	
	return layerSource;
}
function startDraw_sample(tr)
{
	app.DrawSample = true;//draw sample;
	
	app.graphicsLayer.clear();
	app.toolbar.activate("polygon");
}
function saveDraw_sample(tr)
{
	var sampleName = tr.parents('tr').find("input").val();
	if(!sampleName)
	{
//		alert("sample name missing!");
		$("#hintContent").text("sample name missing!");
		
		$("#hintModal").modal('show');
//		$("#hintModal_okbtn").click(function(e){
//			return;
//		});
	}
	else{
		var fileName = sampleName+".shp";
		var filePath = "E:/arcgisserver_shp_workspace/AreaSample/2018-05-11_72/samle/"
//		var graphicArray = app.graphicsLayer.graphics;
		saveDraw(fileName,filePath);
		
	}
	
}
function deleteDraw_sample(tr)
{
	
	app.sample_excel.row( tr.parents('tr') ).remove().draw();
	
	app.undoManager.clearRedo();
	app.undoManager.clearUndo();
	app.graphicsLayer.clear();
	
}
function saveDraw(fileName,filePath)//save graphics to server
{
	console.log("saveDraw---begin");
	require(["esri/SpatialReference",
		"esri/geometry/projection",
		"esri/geometry/webMercatorUtils",
		"dojo/dom",
	    "dojo/on",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"
		], function(SpatialReference,projection,webMercatorUtils,
				dom,on, ArcGISDynamicMapServiceLayer,Extent) {
		
			app.toolbar.deactivate();
		
		  var geoJson = { "type": "FeatureCollection",
			  "features": []
			   };
		
		  var outSpatialReference = new SpatialReference(4326);
		  
		  app.graphicsLayer.graphics.forEach(function(graphic){
//			  graphic.geometry = projection.project(graphic.geometry, outSpatialReference);
			  if (webMercatorUtils.canProject(graphic.geometry, outSpatialReference)) {
				  var result = webMercatorUtils.project(graphic.geometry, outSpatialReference);
				  //console.log(result.toJson());
				  var feature = { "type": "Feature",
				       "geometry": {
					         "type": "Polygon",
					         "coordinates":result["rings"]
					       },
					       "properties": {
					         "prop0":"",
					         "prop1":""
					         }
					      }
				  geoJson["features"].push(feature);
				}
			  
			});
//		  console.log(geoJson);
//		  alert(JSON.stringify(geoJson));
		  $.ajax({
			    url:'/jf/thairice/t1parameter/generateShpfileByGeoJson',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    data:{
			    	fileName:fileName,
			    	filePath:filePath,
			    	geoJsonStr:JSON.stringify(geoJson)//统计数据
			    },
			    timeout:5000,    //超时时间
			    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			    beforeSend:function(xhr){
			        //console.log(xhr)
			        console.log('发送前')
			    },
			    success:function(data,textStatus,jqXHR){
			    	console.log(data);
			    	if(data["status"]=="success")
			    	{
//			    		alert("save success!")
			    		console.log("saveDraw---success");
			    		if(app.DrawSample)
			    		{
			    			$("#hintContent").text("save success!");
				    		$("#hintModal").modal('show');
				    		
				    		app.undoManager.clearRedo();
				    		app.undoManager.clearUndo();
				    		app.graphicsLayer.clear();
			    		}
			    		else{
			    			///////编辑productLayer add 添加
			    			//////merge_gpserver
			    			/////step 1 生成 sample 还需要执行
			    			////step 2 执行地理处理
			    			//////todo 
			    			doGeoprocessor();
			    		}
			    		
		    		
			    		
			    		
			    	}
			    	else
		    		{
			    		
		    		}
			    },
			    error:function(xhr,textStatus){
			        console.log('错误')
			        console.log(xhr)
			        console.log(textStatus)
			    },
			    complete:function(){
			        console.log('结束')
			    }
			})
	});
}
function initFilesTable()
{

	$.ajax({
		    url:'/jf/thairice/t1parameter/getFilesfromServerWorkspace',//获取面积相关的遥感影像 文件列表
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	
		    },
		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        //console.log(xhr)
		        console.log('发送前')
		    },
		    success:function(data,textStatus,jqXHR){
//		    	alert("ok");
//		    	console.log(data);
		    	if(data.flag) {  
		    		app.files_excel.clear().draw();
//		    		var dataSet = [];
		    		for(var i =0;i<data.files.length;i++)
		    		{
		    			var file = [];
		    			file.push(data.files[i].name);
		    			file.push(data.files[i].date);
		    			file.push(data.files[i].district);
//		    			dataSet.push(file);
		    			app.files_excel.row.add(file).draw();
		    		}
		    		$('#fileModal_addbtn').click( function () {
//		    			console.log(app.files_excel.row('.selected').data());
		    			var productfileName = "2018-05-05_72.shp";
		    			var workspaceId = "Area";
		    			addProductFeatureLayer(workspaceId,productfileName);
//		    			
		    			var fileName = app.files_excel.row('.selected').data()[0];
		    			addRasterLayer(fileName);
		    	    } );
		    		
	            }  
	            
		    },
		    error:function(xhr,textStatus){
		        console.log('错误')
		        console.log(xhr)
		        console.log(textStatus)
		    },
		    complete:function(){
		        console.log('结束')
		    }
		})

}
function addRasterLayer(fileName)
{
	/************************/
	if (app.hasOwnProperty("renderLayer") ) {//删除之前的图层
		 
		 app.map.removeLayer(app.renderLayer);
		 
	}
	
    var dynamicLayerInfos=[];
    var dataSource = new esri.layers.RasterDataSource();
	dataSource.workspaceId = rasterLayerWorkspace;
	dataSource.dataSourceName = fileName;
	
	var layerSource = new esri.layers.LayerDataSource();
	layerSource.dataSource = dataSource;
	//动态图层 供渲染使用
    app.renderLayer = new esri.layers.ArcGISDynamicMapServiceLayer(rasterLayerUrl, {
        //"id": "yield"
    	"id": "test"
     });
    //
 // create a new dynamic layer info object for the lakes layer
    var dynamicLayerInfo = new esri.layers.DynamicLayerInfo();
    dynamicLayerInfo.id = 0;
    //dynamicLayerInfo.name = "72_Yield_2017_193.shp";
    dynamicLayerInfo.name = fileName;
    
    dynamicLayerInfo.source = layerSource;
    dynamicLayerInfos.push(dynamicLayerInfo);
    
    app.renderLayer.setDynamicLayerInfos(dynamicLayerInfos, true);
    app.map.addLayer(app.renderLayer,1);
    
    app.renderLayer.on("load",function(res){
//    	alert("ooook");
    	require(["esri/geometry/Extent","esri/map"], function(Extent,Map) { 
    		var layerExt = app.renderLayer.fullExtent;
//        	console.log(layerExt.getCenter());
//         	app.map.setExtent(layerExt.expand(1));
//        	getHeight()getWidth()
    		app.map.centerAt(layerExt.getCenter());
    	});
    	
    });
   
    /**************************** */
}
function addProductFeatureLayer(workspaceId,fileName)
{
//	alert(fileName);
	if (app.hasOwnProperty("featureLayer") ) {//删除之前的图层
		 
		 app.map.removeLayer(app.featureLayer);
		 
	}
	var dynamicLayerInfos=[];
	
	require(["esri/layers/FeatureLayer","esri/layers/ArcGISDynamicMapServiceLayer",
		"esri/layers/DynamicLayerInfo","esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","esri/symbols/SimpleFillSymbol", 
	      "esri/renderers/SimpleRenderer","esri/Color"], function(FeatureLayer,ArcGISDynamicMapServiceLayer,
	    	DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,SimpleFillSymbol,SimpleRenderer,Color) { 
		//var layerSource = getLayerSource("yield","72_Yield_2017_193.shp");
		var layerSource = getLayerSource(workspaceId,fileName);
		/*
		app.featureLayer = new FeatureLayer("http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer/dynamicLayer", {
	        mode: FeatureLayer.MODE_ONDEMAND,
	        //outFields: ["value"],
	        outFields: ["*"],
	        source: layerSource
	    });
		*/
		//动态图层 供渲染使用
		
	    app.featureLayer = new ArcGISDynamicMapServiceLayer(featureLayerUrl, {
	        //"id": "yield"
	    	"id": "areaproduct"
	     });
	    var dynamicLayerInfo = new DynamicLayerInfo();
        dynamicLayerInfo.id = 0;
        //dynamicLayerInfo.name = "72_Yield_2017_193.shp";
        dynamicLayerInfo.name = fileName;
        
        dynamicLayerInfo.source = layerSource;
        dynamicLayerInfos.push(dynamicLayerInfo);
        
        app.featureLayer.setDynamicLayerInfos(dynamicLayerInfos, true);
        
        var drawingOptions = new LayerDrawingOptions();
		
		var renderer = new SimpleRenderer(
		          new SimpleFillSymbol("solid", null, new Color([255, 128, 0, 1]) // fuschia lakes!
		));
		drawingOptions.renderer = renderer;
	          
        var options = [];
        options[0] = drawingOptions;

        app.featureLayer.setLayerDrawingOptions(options);
        
//	    console.log(app.featureLayer);
	    app.map.addLayer(app.featureLayer,2);
	    app.featureLayer.on("load",function(evt){
//	    	add();
//	    	initEditing(evt);
	    });
	});
	
}
function EditFeatures(addOrdelete){
	console.log("begin--EditFeatures----"+addOrdelete);
	app.DrawSample = false;//编辑productLayer
	
	app.edit = addOrdelete;//add or delete features
	
	app.graphicsLayer.clear();
	app.toolbar.activate("polygon");
}
function Edit_Save()
{
	console.log("begin---Edit_Save");
	var fileName;//add.shp or erase.shp
	var filePath;
//	var geoprocessor_url;
	//////////////
	//执行add-》》》merge 
	//step1 先在服务器端生成add.shp step2 执行gp merge操作
	if(app.edit =='add')
	{
		app.geoprocessor_url = mergeUrl;
		fileName = "add.shp";
		filePath = "E:/arcgisData/models/";
		//在服务器端生成add.shp
		saveDraw(fileName,filePath);
	}
	//执行delete--》》erase 
	///直接在客户端生成Graphics 不用传递到服务器端生成shp
	if(app.edit =='delete')
	{
		app.geoprocessor_url = eraseUrl;
		doGeoprocessor();
//		fileName = "erase.shp";
//		filePath = "E:/arcgisserver_shp_workspace/AreaSample/";
	}
	///step 1 生成add.shp or erase.shp
	
	///step 2 执行地理处理 merge or erase 并将结果返回
//	saveDraw(fileName,filePath);
	
	
	
}
function doGeoprocessor(){
	console.log("begin---doGeoprocessor");
	require(["esri/tasks/Geoprocessor","esri/tasks/query","esri/symbols/SimpleFillSymbol","esri/tasks/FeatureSet","esri/layers/FeatureLayer", 
	      "esri/renderers/SimpleRenderer","esri/Color","esri/layers/ImageParameters","esri/layers/LayerDrawingOptions",
	      "esri/geometry/projection","esri/SpatialReference",
			"esri/geometry/webMercatorUtils","dojo/dom",
		    "dojo/domReady!"],function(Geoprocessor,Query,
	    		  SimpleFillSymbol,FeatureSet,FeatureLayer,SimpleRenderer,Color,
	    		  ImageParameters,LayerDrawingOptions,projection,SpatialReference,webMercatorUtils,dom){
				  var gp_params={};
				  if(app.edit =='add')
				  {
					  gp_params = null;
				  }
				  if(app.edit =='delete')
				  {
					var addOrerase_features = app.graphicsLayer.graphics;
//				        console.log(addOrerase_features);
					var outSpatialReference = new SpatialReference(4326);
					var features = [];  
			        addOrerase_features.forEach(function(graphic){
			//			  graphic.geometry = projection.project(graphic.geometry, outSpatialReference);
						  if (webMercatorUtils.canProject(graphic.geometry, outSpatialReference)) {
							  var result = webMercatorUtils.project(graphic.geometry, outSpatialReference);
							  var graphic = new esri.Graphic(result, null);
							  features.push(graphic);
							}
						  
						});
//				        console.log(features);
			          var addOrerasefeatureSet = new FeatureSet();
			          addOrerasefeatureSet.features = features;
			          gp_params = {
			            "eraseshp": addOrerasefeatureSet
			          };
				  }
				
		          ////do gp
		    	  var geoprocessor = new Geoprocessor(app.geoprocessor_url);
			      geoprocessor.setOutSpatialReference({
			        wkid: 4326
			      });
			      geoprocessor.submitJob(gp_params, completeCallback, statusCallback,errback);
			      function statusCallback(jobInfo){
			            console.log(jobInfo.jobStatus);
			      }
			      function completeCallback(jobInfo) {
			    	console.log(jobInfo.jobId);
			        var imageParams = new ImageParameters();
			        imageParams.imageSpatialReference = app.map.spatialReference;
			        geoprocessor.getResultImageLayer(jobInfo.jobId, "result",imageParams, function(gpLayer){
		//	        	console.log(gpLayer);
		//	        	alert("1111");
		//	          gpLayer.setOpacity(0.5);
		//	        	  app.gpLayer = gpLayer;
				          app.map.removeLayer(app.featureLayer);
				          app.featureLayer = gpLayer;
				          /*************
				          var drawingOptions = new LayerDrawingOptions();
				  		  var renderer = new SimpleRenderer(
				  		          new SimpleFillSymbol("solid", null, new Color([255, 128, 0, 1]) // fuschia lakes!
				  		  ));
				  		  drawingOptions.renderer = renderer;
				  	          
				          var options = [];
				          options[0] = drawingOptions;
				          app.featureLayer.setLayerDrawingOptions(options);
				          */
				          app.map.addLayer(app.featureLayer,2);
				          
				          app.undoManager.clearRedo();
			    		  app.undoManager.clearUndo();
			    		  app.graphicsLayer.clear();
				          //将gp执行的生成结果result.shp转存到models空间 滚动更新 备下次使用
				          copyResult2Modelspace(app.edit,jobInfo.jobId);
			        });
			       }
			      function errback(error){
			    	  console.log(error);
			      }
			 
	});
}
////将地理处理的结果数据copy到gp的工作空间中，删除原先的数据，进行更新
/*
 * edit:add or delete
 * jobID:gp return id
 */
function copyResult2Modelspace(edit,jobId){
	$.ajax({
	    url:'/jf/thairice/t1parameter/copyResult2Modelspace',//
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	gpEdit:edit,
	    	gpJobID:jobId
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('copyResult2Modelspace---发送前')
	    },
	    success:function(data,textStatus,jqXHR){

	    	if(data.flag) {  
	    		
	    		console.log('copyResult2Modelspace---success')
            }  
            
	    },
	    error:function(xhr,textStatus){
	        console.log('copyResult2Modelspace---错误')
	        console.log(xhr)
	        console.log(textStatus)
	    },
	    complete:function(){
	        console.log('copyResult2Modelspace---结束')
	    }
	})
}
function Edit_Cancel()
{
	app.undoManager.clearRedo();
	app.undoManager.clearUndo();
	app.graphicsLayer.clear();
}
/*
function initEditing(evt) {
	alert("okkkkkk");
	require(["esri/toolbars/edit","dojo/_base/event", "esri/tasks/query","dojo/domReady!"],
			function(Edit,event,Query){
		var editToolbar = new Edit(app.map);
	    editToolbar.on("deactivate", function(evt) {
	      if (evt.info.isModified) {
	    	  app.featureLayer.applyEdits(null, [evt.graphic], null);
	      }
	    });

	    var editingEnabled = false;
	    app.featureLayer.on("dbl-click", function(evt) {
	    	console.log(evt.graphic);
	      event.stop(evt);
	      if (editingEnabled) {
	        editingEnabled = false;
	        editToolbar.deactivate();
	        app.featureLayer.clearSelection();
	      }
	      else {
	        editingEnabled = true;
	        editToolbar.activate(Edit.EDIT_VERTICES, evt.graphic);
	        // select the feature to prevent it from being updated by map navigation
	        var query = new Query();
	        query.objectIds = [evt.graphic.attributes[app.featureLayer.objectIdField]];
	        app.featureLayer.selectFeatures(query);
	      }
	    });
	});
    
  }
*/
/*
function test(shpFileName)
{
	$.ajax({
	    url:'/jf/thairice/t1parameter/getAreaGeoJsonFromShpFile',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	shpFileName:"E:/codingWorkspace/ThaiRiceRS/rice.shp"
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
	    	console.log(data);
	    	if(data["status"]=="success") {  
	    		
	    		console.log(data["message"]);
            }  
            
	    },
	    error:function(xhr,textStatus){
	        console.log('错误')
	        console.log(xhr)
	        console.log(textStatus)
	    },
	    complete:function(){
	        console.log('结束')
	    }
	})
}*/