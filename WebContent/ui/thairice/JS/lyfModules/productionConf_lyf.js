/**
 * 
 */
var DEBUG = true;

var app = {};
var rasterLayerUrl = hostIP+":6080/arcgis/rest/services/tai_wgs84/MapServer";
var featureLayerUrl = hostIP+":6080/arcgis/rest/services/tai_wgs84/MapServer";
var rasterLayerWorkspace = "areatif";
//var tempProductfileName = "tempProduct.shp";
var areaworkspaceId = "Area";
var eraseUrl = hostIP+":6080/arcgis/rest/services/erase/GPServer/erase";
var mergeUrl = hostIP+":6080/arcgis/rest/services/merge/GPServer/merge";
var arearasterbandsextractUrl = hostIP+":6080/arcgis/rest/services/arearasterbandsextract/GPServer/arearasterbandsextract";
var areatifsBaseUrl = hostIP+":8080/areatifs/";
//var merge_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/merge_gpserver/";
//var erase_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/erase_gpserver/";
//
//var m_GraphicsLayers = new Array();
var m_GraphicsLayerIndex = 0;
var m_GraphicsLayers = {};
var m_GraphicsLayers_selectedIndex;//当前选中的
function init_productionConf_Monitoring()
{
	
//    packages: [{
//      "name": "lyfModules",
//      "location": "/ui/thairice/JS/" + "lyfModules"
//    }]
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
        "dojo/_base/connect", "esri/Color", "dojo/parser", 
        "esri/urlUtils","esri/config","dijit/registry",
	    "dojo/dom",
	    "dojo/on",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,UndoManager,Draw, Graphic,
	        SimpleMarkerSymbol, SimpleLineSymbol,SimpleFillSymbol,GraphicsLayer,RasterDataSource,
	        DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,
	        connect, Color, parser, urlUtils,esriConfig,registry,
	        dom,on, ArcGISDynamicMapServiceLayer,Extent) {
	    //var map = new Map("mapDiv");
		 	parser.parse();
//		 	urlUtils.addProxyRule({
//		          urlPrefix: hostIP+":6080",
//		          proxyUrl: hostIP+":8080/Java/proxy.jsp"
//		        });
		 	esriConfig.defaults.io.proxyUrl = hostIP+":8080/Java/proxy.jsp";
			esriConfig.defaults.io.alwaysUseProxy = false;
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
	 	
//	    app.graphicsLayer = new GraphicsLayer();
	    //作为编辑面积产品数据用的图层  添加、删除
	    var graphicsLayerForEdit = new esri.layers.GraphicsLayer();

		m_GraphicsLayers['graphicsLayerForEdit'] = graphicsLayerForEdit;
		
		
	    
	    app.map.on("load", createToolbar);
	    function createToolbar(themap) {
	    	  
//	    	app.map.addLayer(app.graphicsLayer);
	    	app.map.addLayer(graphicsLayerForEdit);
	    	
	    	
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
//	              symbol = new SimpleFillSymbol();
//	            	symbol = new SimpleFillSymbol("solid",  new SimpleLineSymbol(SimpleLineSymbol.STYLE_DASHDOT,
//	            		    new Color([255,0,0]), 2), new Color([255, 136, 0, 1]));
	            	symbol = new SimpleFillSymbol("solid",  new SimpleLineSymbol(SimpleLineSymbol.STYLE_DASHDOT,
	            		    new Color([255,0,0]), 2), new Color([app.color_R, app.color_G, app.color_B, 0.5]));
	              break;
	          }
	          var graphic = new Graphic(evt.geometry, symbol);
	          var customoperation = require('customoperation');
	          var operation = new customoperation.Add({
//		          graphicsLayer: app.graphicsLayer,
	        	  graphicsLayer:m_GraphicsLayers[m_GraphicsLayers_selectedIndex],
		          addedGraphic: graphic
	          });

	          app.undoManager.add(operation);
	          m_GraphicsLayers[m_GraphicsLayers_selectedIndex].add(graphic);
//	          app.graphicsLayer.add(graphic);
	        }
	   
	});
	/************初始化加载文件列表**************/
	
	app.files_excel = $('#files_excel').DataTable({
		columns: [
        	{ title: "Name" },
            { title: "Date" },
            { title: "District" }
        ]
    });
	//高亮显示选中的文件列表的行
	 $('#files_excel tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	app.files_excel.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    } );
	/************设置样本表格参数***************/
	app.sample_excel = $('#sample_excel').DataTable( {
		"searching": false,
		"paging":   false,
        "ordering": false,
        "info":     false
    } );
	$('.editing_list').on( 'click', function () {
		$('.editing').toggle();
		$('.samples').toggle();
    } );
	$('#addRow').on( 'click', function () {
//		GraphicsLayerIndex=GraphicsLayerIndex+1;
//		m_GraphicsLayerIndex++;
//		alert(m_GraphicsLayerIndex++);
		var GraphicsLayerIndex='GraphicsLayerIndex'+m_GraphicsLayerIndex++;
		
		var graphicsLayer = new esri.layers.GraphicsLayer();
		  
//		  var graphicsLayerObj  = {
//					'index':GraphicsLayerIndex,
//					'graphicsLayer':graphicsLayer
//		  } 
//		
//		m_GraphicsLayers.push(graphicsLayerObj);
		  
		m_GraphicsLayers[GraphicsLayerIndex] = graphicsLayer;
		app.map.addLayer(graphicsLayer);
//		app.undoManager.clearRedo();
//		app.undoManager.clearUndo();
//		app.graphicsLayer.clear();
		app.sample_excel.row.add( [
			"<input type='text'  id='"+GraphicsLayerIndex+"' placeholder='sample name' style='width:100px; text-align:center;'>",
			"<button type='button' onclick='startDraw_sample($(this))'><i class='glyphicon glyphicon-edit'></i>Draw</button>"+
			"<button type='button' class='color-box' style='width:15px;height:15px;background-color:#ff8800;'></button>"+
          	"<button type='button' onclick='saveDraw_sample($(this))'><i class='glyphicon glyphicon-ok-circle'></i>Save</button>"+
          	"<button type='button' onclick='deleteDraw_sample($(this))'><i class='glyphicon glyphicon-remove-sign'></i>Cancel</button>"
           
        ] ).draw( false );
		$('.color-box').colpick({

			colorScheme:'dark',

			layout:'rgbhex',

			color:'ff8800',

			onSubmit:function(hsb,hex,rgb,el) {

				m_GraphicsLayers_selectedIndex = $(el).parents('tr').find("input")[0].id;
				
				$(el).css('background-color', '#'+hex);
//				console.log(rgb);
				$(el).colpickHide();
				require([
				     
				      "esri/symbols/SimpleFillSymbol", "esri/renderers/SimpleRenderer",
				      "esri/config",
				      "esri/Color", "dojo/domReady!"
				    ], function(
				      SimpleFillSymbol, SimpleRenderer,
				      esriConfig,
				      Color
				    ) {
//					var renderer = new SimpleRenderer(
//					          new SimpleFillSymbol("solid", null, new Color([255, 0, 255, 0.75]) // fuschia lakes!
//					        ));

//					app.graphicsLayer.setRenderer(renderer);
//					app.graphicsLayer.redraw();
					/*
					for(var i =0;i<m_GraphicsLayers.length;i++)
					{
						if(selectedGraphicsLayerIndex == m_GraphicsLayers[i]["index"]){
							var graphicSys =  new SimpleFillSymbol("solid", null, new Color([rgb.r, rgb.g, rgb.b, 0.5])); // fuschia lakes!
//							console.log(app.graphicsLayer.graphics);
							var selectedGraphicsLayer = m_GraphicsLayers[i]["graphicsLayer"];
							if(selectedGraphicsLayer.graphics.length>0)
							{
								for(var i=0;i<selectedGraphicsLayer.graphics.length;i++){
							           
									selectedGraphicsLayer.graphics[i].setSymbol(graphicSys);
						           
						          }
								
							}
							break;
						}
						
					}
					*/
			
					var graphicSys =  new SimpleFillSymbol("solid", null, new Color([rgb.r, rgb.g, rgb.b, 0.5])); // fuschia lakes!
//							console.log(app.graphicsLayer.graphics);
//							var selectedGraphicsLayer = m_GraphicsLayers[i]["graphicsLayer"];
					if(m_GraphicsLayers[m_GraphicsLayers_selectedIndex].graphics.length>0)
					{
						for(var i=0;i<m_GraphicsLayers[m_GraphicsLayers_selectedIndex].graphics.length;i++){
					           
							m_GraphicsLayers[m_GraphicsLayers_selectedIndex].graphics[i].setSymbol(graphicSys);
				           
				          }
						
					}
					
					
				});
				
			}

		});
    } );
	
	$('#rgbChange').on( 'click', function () {
		areaRasterbandsExtract();
    } );
	
	
}
//根据index 获取当前选中的GraphicsLayer
function getGraphicsLayer(selectedGraphicsLayerIndex)
{
	for(var i =0;i<m_GraphicsLayers.length;i++)
	{
		if(selectedGraphicsLayerIndex == m_GraphicsLayers[i]["index"]){
			
			var selectedGraphicsLayer = m_GraphicsLayers[i]["graphicsLayer"];
			return selectedGraphicsLayer;
			
		}
		
	}
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
/*
function generateFeatureLayer(layerSource,fileName)
{
	require(["esri/layers/FeatureLayer","esri/layers/ArcGISDynamicMapServiceLayer",
		"esri/layers/DynamicLayerInfo","esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","esri/symbols/SimpleFillSymbol", 
	      "esri/renderers/SimpleRenderer","esri/Color"], function(FeatureLayer,ArcGISDynamicMapServiceLayer,
	    	DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,SimpleFillSymbol,SimpleRenderer,Color) { 
		
		var featurelayer = new ArcGISDynamicMapServiceLayer(featureLayerUrl, {
	        //"id": "yield"
	    	"id": "areaproduct"
	     });
	    var dynamicLayerInfo = new DynamicLayerInfo();
        dynamicLayerInfo.id = 0;
        //dynamicLayerInfo.name = "72_Yield_2017_193.shp";
        dynamicLayerInfo.name = fileName;
        
        dynamicLayerInfo.source = layerSource;
        dynamicLayerInfos.push(dynamicLayerInfo);
        
        featurelayer.setDynamicLayerInfos(dynamicLayerInfos, true);
        
        var drawingOptions = new LayerDrawingOptions();
		
		var renderer = new SimpleRenderer(
		          new SimpleFillSymbol("solid", null, new Color([255, 128, 0, 1]) // fuschia lakes!
		));
		drawingOptions.renderer = renderer;
	          
        var options = [];
        options[0] = drawingOptions;

        featurelayer.setLayerDrawingOptions(options);
	});
}
*/
function startDraw_sample(tr)
{

	var color_box = tr.parents('td').children("button.color-box")[0].style.backgroundColor;
	
	var parts = color_box.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
//			console.log(parts);parts now should be ["rgb(0, 70, 255", "0", "70", "255"]
	app.color_R = parseInt(parts[1]);
	app.color_G = parseInt(parts[2]);
	app.color_B = parseInt(parts[3]);
	
	app.DrawSample = true;//draw sample;
	/*************************
	 * 
	 * @param tr
	 * @returns
	 */
	m_GraphicsLayers_selectedIndex = tr.parents('tr').find("input")[0].id;

	
	app.toolbar.activate("polygon");

//	console.log(m_GraphicsLayers);

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
		m_GraphicsLayers_selectedIndex = tr.parents('tr').find("input")[0].id;
		saveDraw(fileName,m_GraphicsLayers_selectedIndex);
//		var filePath = "E:/arcgisserver_shp_workspace/AreaSample/2018-05-11_72/samle/";
//		var graphicArray = app.graphicsLayer.graphics;
//		saveDraw(fileName,filePath);
		
	}
	
}
//生成中间的产品数据
function generateTempProduct()
{
//	alert(app.tifFileName);
	console.log("generateTempProduct---begin");
	if(app.tifFileName)
	{
		$.ajax({
		    url:'/jf/thairice/t1parameter/generateTempProduct',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
//		    data:{
//		    	fileName:fileName,
//		    	filePath:filePath,
//		    	geoJsonStr:JSON.stringify(geoJson)//统计数据
//		    },
		    data:{
		    	tifFileName:app.tifFileName,
		    },
//		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        //console.log(xhr)
		        console.log('发送前');
		        $("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		    		textPosition: "top"
		    	});
		    },
		    success:function(data,textStatus,jqXHR){
		    	console.log(data);
		    	if(data["status"]=="success")
		    	{
//		    		alert("save success!")
		    		console.log("generateTempProduct---success");
		    		var productfileName = data["filename"];
	    			var workspaceId = areaworkspaceId;
	    			addProductFeatureLayer(workspaceId,productfileName);
		    	}
		    	else
	    		{
		    		console.log("generateTempProduct---faliure");
	    		}
		    },
		    error:function(xhr,textStatus){
		        console.log('错误')
		        console.log(xhr)
		        console.log(textStatus)
		    },
		    complete:function(){
		        console.log('结束');
		        $("#mapDiv_productionConf").busyLoad("hide");
		    }
		})
	}
	else{
		$("#hintContent").text("file missing!");
		
		$("#hintModal").modal('show');
	}
	
}
function deleteDraw_sample(tr)
{
//	console.log(m_GraphicsLayers);
	app.sample_excel.row( tr.parents('tr') ).remove().draw();
	
	m_GraphicsLayers_selectedIndex = tr.parents('tr').find("input")[0].id;
//	var selectedGraphicsLayer = getGraphicsLayer(selectedGraphicsLayerIndex);
	
	app.map.removeLayer(m_GraphicsLayers[m_GraphicsLayers_selectedIndex]);
//	app.undoManager.clearRedo();
//	app.undoManager.clearUndo();
//	app.graphicsLayer.clear();
	delete m_GraphicsLayers[m_GraphicsLayers_selectedIndex];
//	console.log(m_GraphicsLayers);
	app.toolbar.deactivate();
	
}
function saveDraw(fileName,which2save)//save graphics to server
{
	$("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		textPosition: "top"
	});
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
//		  m_GraphicsLayers_selectedIndex = tr.parents('tr').find("input")[0].id;
		  m_GraphicsLayers[which2save].graphics.forEach(function(graphic){
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
		  	$.ajax({
			    url:'/jf/thairice/t1parameter/generateShpfileByGeoJson',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
//			    data:{
//			    	fileName:fileName,
//			    	filePath:filePath,
//			    	geoJsonStr:JSON.stringify(geoJson)//统计数据
//			    },
			    data:{
			    	tifFileName:app.tifFileName,
			    	drawSample:app.DrawSample,
			    	fileName:fileName,
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
			    			$("#hintContent").text("save successful!");
				    		$("#hintModal").modal('show');
				    		
//				    		app.undoManager.clearRedo();
//				    		app.undoManager.clearUndo();
//				    		app.graphicsLayer.clear();
			    		}
			    		else{
			    			///////编辑productLayer add 添加
			    			//////merge_gpserver
			    			/////step 1 生成 add.shp 还需要执行
			    			////step 2 执行地理处理
			    			//////todo 
//			    			doGeoprocessor();
			    		}
			    		
		    		
			    		
			    		
			    	}
			    	else
		    		{
			    		$("#hintContent").text("save failed!");
			    		$("#hintModal").modal('show');
			    		
//			    		app.undoManager.clearRedo();
//			    		app.undoManager.clearUndo();
//			    		app.graphicsLayer.clear();
		    		}
			    },
			    error:function(xhr,textStatus){
			    	$("#mapDiv_productionConf").busyLoad("hide");
			        console.log('错误')
			        console.log(xhr)
			        console.log(textStatus)
			    },
			    complete:function(){
			    	$("#mapDiv_productionConf").busyLoad("hide");
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
		        $("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		    		textPosition: "top"
		    	});
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
		    		$('#fileModal_addbtn').unbind('click').click( function () {
		    			$("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
				    		textPosition: "top"
				    	});
//		    			console.log(app.files_excel.row('.selected').data());
		    			//加载遥感影像的同时加载与影像关联的临时中间产品数据
		    			app.tifFileName = app.files_excel.row('.selected').data()[0];
		    			/********恢复相关参数设置***************/
		    			app.undoManager.clearRedo();
			    		app.undoManager.clearUndo();
//			    		
			    		m_GraphicsLayers_selectedIndex = 'graphicsLayerForEdit';
				      	m_GraphicsLayers[m_GraphicsLayers_selectedIndex].clear();
				      	
				      	for(var key in m_GraphicsLayers)
				      	{
				      		if(key != m_GraphicsLayers_selectedIndex)
				      		{
				      			app.map.removeLayer(m_GraphicsLayers[key]);
				      			delete m_GraphicsLayers[key];
				      		}
				      	}
				      	console.log(m_GraphicsLayers);
				      	app.sample_excel.rows().remove().draw();
				      	
				      	/****************************************************/
				      	  
		    			//将选中的tif文件关联的temp shp文件copy to gpmodels ，备editing
		    			copyTempShpFile2gpWorkspace(app.tifFileName);
		    			//将选中的tif文件copy to gpmodels ，备改变波段显示
//		    			copyAreaTifFile2gpWorkspace(app.tifFileName);
		    			
		    			var tifFileNamePrefix = app.tifFileName.lastIndexOf('.');
		    			var tempproductfileName = app.tifFileName.substring(0,tifFileNamePrefix)+"_temp.shp";
		    			
		    			
		    			addRasterLayer(app.tifFileName);
		    			console.log("addProductFeatureLayer---"+tempproductfileName);
		    			//在workspace空间中，加载对应日期的中间产品数据，
		    			//如果空间中有对应的数据，即加载，若无，不会加载成功
		    			addProductFeatureLayer(areaworkspaceId,tempproductfileName);
		    			
		    			
		    			
		    	    } );
		    		
	            }  
	            
		    },
		    error:function(xhr,textStatus){
		        console.log('错误')
		        console.log(xhr)
		        console.log(textStatus)
		        $("#mapDiv_productionConf").busyLoad("hide");
		    },
		    complete:function(){
		        console.log('结束')
		        $("#mapDiv_productionConf").busyLoad("hide");
		    }
		})

}
function copyAreaTifFile2gpWorkspace(tifFileName){
	console.log("copyAreaTifFile2gpWorkspace---begin--"+tifFileName);
	$.ajax({
	    url:'/jf/thairice/t1parameter/copyAreaTifFile2gpWorkspace',//获取面积相关的遥感影像 文件列表
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	tifFileName:tifFileName
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
	    	if(data.flag) {  
	    		console.log("copyAreaTifFile2gpWorkspace---success");
	    		
	    		var tifFileNamePrefix = app.tifFileName.lastIndexOf('.');
    			var tempproductfileName = app.tifFileName.substring(0,tifFileNamePrefix)+"_temp.shp";
    			
    			addRasterLayer(app.tifFileName);
    			addProductFeatureLayer(areaworkspaceId,tempproductfileName);
            }  
            
	    },
	    error:function(xhr,textStatus){
	    	console.log("copyAreaTifFile2gpWorkspace---错误");
	        console.log(xhr)
	        console.log(textStatus)
	    },
	    complete:function(){
	    	console.log("copyAreaTifFile2gpWorkspace---结束");
	    }
	})
}
function copyTempShpFile2gpWorkspace(tifFileName){
	console.log("copyTempShpFile2gpWorkspace---begin--"+tifFileName);
	$.ajax({
	    url:'/jf/thairice/t1parameter/copyTempShpFile2gpWorkspace',//获取面积相关的遥感影像 文件列表
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	tifFileName:tifFileName
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
	    	if(data.flag) {  
	    		console.log("copyTempShpFile2gpWorkspace---success");
	    	}  
            
	    },
	    error:function(xhr,textStatus){
	    	console.log("copyTempShpFile2gpWorkspace---错误");
	        console.log(xhr)
	        console.log(textStatus)
	    },
	    complete:function(){
	    	console.log("copyTempShpFile2gpWorkspace---结束");
	    }
	})
}
function addRasterLayer(fileName)
{
	require(["esri/layers/FeatureLayer","esri/layers/ArcGISDynamicMapServiceLayer",
		"esri/layers/DynamicLayerInfo","esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","esri/symbols/SimpleFillSymbol", 
	      "esri/renderers/SimpleRenderer","esri/Color",
	      "esri/geometry/Extent",
	      "esri/SpatialReference",
	      	"dojo/parser",
		    "esri/urlUtils",
		    "esri/config",
		    "dojo/domReady!"], function(FeatureLayer,ArcGISDynamicMapServiceLayer,
	    	DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,SimpleFillSymbol,SimpleRenderer,Color,Extent,SpatialReference,
	    	parser,urlUtils,esriConfig) { 
		
		parser.parse();

		esriConfig.defaults.io.proxyUrl = hostIP+":8080/Java/proxy.jsp";
		esriConfig.defaults.io.alwaysUseProxy = false;
		
		console.log("addRasterLayer---"+fileName);
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
//	    	alert("ooook");
	    	require(["esri/geometry/Extent","esri/map"], function(Extent,Map) { 
	    		//alert("00000");
	    		var layerExt = app.renderLayer.fullExtent;
	    		var startExtent = new Extent(layerExt.xmin, layerExt.ymin, layerExt.xmax, layerExt.ymax,
	    		          new SpatialReference({ wkid:4326 }) );

	    		app.map.setExtent(startExtent);
	    		console.log(app.renderLayer);
//	        	console.log(layerExt.getCenter());
//	         	app.map.setExtent(layerExt);
//	        	getHeight()getWidth()
//	    		app.map.centerAndZoom(layerExt.getCenter(),10);
//	    		$("#mapDiv_productionConf").busyLoad("hide");
	    	});
	    	
	    });
	});
	
	
   
    /**************************** */
}
function addProductFeatureLayer(workspaceId,fileName)
{
//	alert(fileName);
	console.log("addProductFeatureLayer---"+fileName);
	if (app.hasOwnProperty("featureLayer") ) {//删除之前的图层
		 
		 app.map.removeLayer(app.featureLayer);
		 
	}
	var dynamicLayerInfos=[];
	
	require(["esri/layers/FeatureLayer","esri/layers/ArcGISDynamicMapServiceLayer",
		"esri/layers/DynamicLayerInfo","esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","esri/symbols/SimpleFillSymbol", 
	      "esri/renderers/SimpleRenderer","esri/Color",
	      "dojo/parser",
		    "esri/urlUtils",
		    "esri/config",
		    "dojo/domReady!"], function(FeatureLayer,ArcGISDynamicMapServiceLayer,
	    	DynamicLayerInfo,LayerDataSource,LayerDrawingOptions,SimpleFillSymbol,SimpleRenderer,Color,
	    	parser,urlUtils,esriConfig) {
		
		parser.parse();

		esriConfig.defaults.io.proxyUrl = hostIP+":8080/Java/proxy.jsp";
		esriConfig.defaults.io.alwaysUseProxy = false;
		
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
	    	$("#mapDiv_productionConf").busyLoad("hide");
	    });
	});
	
}
function EditFeatures(addOrdelete){
	console.log("begin--EditFeatures----"+addOrdelete);
	app.DrawSample = false;//编辑productLayer
	
	app.edit = addOrdelete;//add or delete features
	
	m_GraphicsLayers_selectedIndex = 'graphicsLayerForEdit';
	
//	m_GraphicsLayers[m_GraphicsLayers_selectedIndex].clear();
	
//	app.graphicsLayer.clear();
	app.toolbar.activate("polygon");
}
function Edit_Preview()
{
	console.log("begin---Edit_Preview");
	var fileName;//add.shp or erase.shp
	var filePath;
//	var geoprocessor_url;
	//////////////
	//执行add-》》》merge 
	//step1 先在服务器端生成add.shp step2 执行gp merge操作
	if(app.edit =='add')
	{
		app.geoprocessor_url = mergeUrl;
//		fileName = "add.shp";
//		filePath = "E:/arcgisData/models/";
//		//在服务器端生成add.shp
//		saveDraw(fileName,filePath);
		doGeoprocessor();
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
//保存编辑之后的结果数据，即最终数据
function Edit_Save()
{
	//SaveAreaEditedProduct
	if(app.tifFileName)
	{
		var tifFileNamePrefix = app.tifFileName.lastIndexOf('.');
		var fileinfo = app.tifFileName.substring(0,tifFileNamePrefix);
		$.ajax({
		    url:'/jf/thairice/t1parameter/SaveAreaEditedProduct',//
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	fileinfo:fileinfo
		    },
//		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        //console.log(xhr)
		        console.log('SaveAreaEditedProduct---发送前');
		        $("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		    		textPosition: "top"
		    	});
		    },
		    success:function(data,textStatus,jqXHR){

		    	if(data.flag) {  
		    		
		    		console.log('SaveAreaEditedProduct---success')
	            }  
	            
		    },
		    error:function(xhr,textStatus){
		    	$("#mapDiv_productionConf").busyLoad("hide");
		        console.log('SaveAreaEditedProduct---错误')
		        console.log(xhr)
		        console.log(textStatus)
		    },
		    complete:function(){
		    	$("#mapDiv_productionConf").busyLoad("hide");
		        console.log('SaveAreaEditedProduct---结束')
		    }
		})
	}
	else{
		$("#hintContent").text("file missing!");
		
		$("#hintModal").modal('show');
	}
	
}
function doGeoprocessor(){
	console.log("begin---doGeoprocessor");
	$("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		textPosition: "top"
	});
	require(["esri/tasks/Geoprocessor","esri/tasks/query","esri/symbols/SimpleFillSymbol","esri/tasks/FeatureSet","esri/layers/FeatureLayer", 
	      "esri/renderers/SimpleRenderer","esri/Color","esri/layers/ImageParameters","esri/layers/LayerDrawingOptions",
	      "esri/geometry/projection","esri/SpatialReference",
			"esri/geometry/webMercatorUtils","dojo/dom",
		    "dojo/domReady!"],function(Geoprocessor,Query,
	    		  SimpleFillSymbol,FeatureSet,FeatureLayer,SimpleRenderer,Color,
	    		  ImageParameters,LayerDrawingOptions,projection,SpatialReference,webMercatorUtils,dom){
				  var gp_params={};
				  
				  m_GraphicsLayers_selectedIndex = 'graphicsLayerForEdit';
					
//				  m_GraphicsLayers[m_GraphicsLayers_selectedIndex].clear();
					
				  var addOrerase_features = m_GraphicsLayers[m_GraphicsLayers_selectedIndex].graphics;
//			        console.log(addOrerase_features);
				  var outSpatialReference = new SpatialReference(4326);
				  var features = [];  
		          addOrerase_features.forEach(function(graphic){
		//			  graphic.geometry = projection.project(graphic.geometry, outSpatialReference);
					  if (webMercatorUtils.canProject(graphic.geometry, outSpatialReference)) {
						  var result = webMercatorUtils.project(graphic.geometry, outSpatialReference);
						  var graphic = new esri.Graphic(result, null);
						  graphic.setAttributes({"value":5});
						  features.push(graphic);
						}
					  
					});
//			        console.log(features);
		          var addOrerasefeatureSet = new FeatureSet();
		          addOrerasefeatureSet.features = features;
		          
				  if(app.edit =='add')
				  {
//					  gp_params = null;
					  gp_params = {
					            "add": addOrerasefeatureSet
					          };
				  }
				  if(app.edit =='delete')
				  {
					
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
			        	$("#mapDiv_productionConf").busyLoad("hide");
		//	        	gpLayer.setOpacity(0.5);
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
				          
//				          app.undoManager.clearRedo();
//			    		  app.undoManager.clearUndo();
//			    		  app.graphicsLayer.clear();
				          m_GraphicsLayers_selectedIndex = 'graphicsLayerForEdit';
				      	
				      	  m_GraphicsLayers[m_GraphicsLayers_selectedIndex].clear();
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
//	app.undoManager.clearRedo();
//	app.undoManager.clearUndo();
	
	m_GraphicsLayers_selectedIndex = 'graphicsLayerForEdit';
	
	m_GraphicsLayers[m_GraphicsLayers_selectedIndex].clear();
	app.toolbar.deactivate();
//	app.graphicsLayer.clear();
	
}
function areaRasterbandsExtract(){
	console.log("areaRasterbandsExtract---begin");
	$("#mapDiv_productionConf").busyLoad("show", { text: "LOADING ...",
		textPosition: "top"
	});
	var bands = [];
	var rgb_red_band = $('#rgb_red option:selected').val();//red选中的值
	bands.push(rgb_red_band);
	var rgb_green_band = $('#rgb_green option:selected').val();//green选中的值
	bands.push(rgb_green_band);
	var rgb_blue_band = $('#rgb_blue option:selected').val();//blue选中的值
	bands.push(rgb_blue_band);
	console.log(bands);
	require(["esri/tasks/Geoprocessor","esri/tasks/query","esri/tasks/RasterData","esri/layers/ImageParameters",
		"esri/layers/LayerDrawingOptions","dojo/dom",
		    "dojo/domReady!"],function(Geoprocessor,Query,RasterData,ImageParameters,LayerDrawingOptions,dom){
		var gp_params={};
		
		var rasterData = new RasterData();
		
		rasterData.url=areatifsBaseUrl+app.tifFileName;
		rasterData.format="tif";
		
		gp_params = {
	            "bands":bands,
	            "input":rasterData
	    };
		console.log(gp_params);
		var geoprocessor = new Geoprocessor(arearasterbandsextractUrl);
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
	        geoprocessor.getResultImageLayer(jobInfo.jobId, "output",imageParams, function(gpLayer){
	        	
	        	$("#mapDiv_productionConf").busyLoad("hide");
	        	
//	        	if (app.hasOwnProperty("featureLayer") ) {//删除之前的图层
//		     		 
//		     		 app.map.removeLayer(app.featureLayer);
//		     		 
//		          }
//	        	
		          app.map.removeLayer(app.renderLayer);
		          app.renderLayer = gpLayer;
		     
		          app.map.addLayer(app.renderLayer);
		          app.map.reorderLayer(app.renderLayer,1);
//		          app.map.addLayer(app.featureLayer);
//		          app.renderLayer.on("load",function(res){
////		  	    	alert("ooook");
//		  	    	require(["esri/geometry/Extent","esri/map"], function(Extent,Map) {
//		  	    		alert("oooo");
//		  	    		var layerExt = app.renderLayer.fullExtent;
//		  	        	app.map.centerAt(layerExt.getCenter());
//		  	        	
//		  	        	app.map.addLayer(app.featureLayer);
//	  	    		
//		  	    	});
//		  	    	
//		          });
		          
	        });
	       }
	      function errback(error){
	    	  console.log(error);
	      }
	});
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