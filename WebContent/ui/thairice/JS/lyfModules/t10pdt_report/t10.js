/*
 * author:lyf
 */
/**********************************************************************************************************
 * 添加产品数据
 */
var featureLayerUrl = hostIP+":6080/arcgis/rest/services/tai_wgs84/MapServer/dynamicLayer";
var renderLayerUrl = hostIP+":6080/arcgis/rest/services/tai_wgs84/MapServer";
//var featureQueryUrl = "http://localhost:6080/arcgis/rest/services/taicode/MapServer/0";
var printMapUrl = hostIP+":6080/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task";
productKind_code_2_des = {
		'01':'Area',
		'02':'Growth',
		'03':'Yield',
		'04':'Drought'
}
var renderLabel_Drought_Growth = {
		"Drought":["Moist","Normal","Light Drought","Moderate","Heavy"],
		"Growth":["Very Bad","Bad","Average","Good","Excellent"],
};
var render_color = {
		"Drought": [[56, 168, 0, 1],[139, 209, 0, 1],[255, 255, 0, 1],[255, 128, 0, 1],[255, 0, 0, 1]],
		"Growth":[[255, 0, 0, 1],[255, 128, 0, 1],[255, 255, 0, 1],[139, 209, 0, 1],[56, 168, 0, 1]],
};
var render_color_rgb = {
		"Drought": ['#38A800','#8BD100','#FFFF00','#FF8000','#FF0000'],
		"Growth":['#FF0000','#FF8000','#FFFF00','#8BD100','#38A800'],
};
/*
 * areaCode:选择的行政区域
 * productDate:选择的产品日期
 * productKind：选择的产品种类 估产、面积、长势、干旱
 */
function AssembleProductLayerInfo(areaCode,productDate,productKind_code)
{
	app.productKind_des = productKind_code_2_des[productKind_code];
	if(areaCode&&productDate&&app.productKind_des)
	{
		$("#mapDiv").busyLoad("show", { text: "LOADING ...",
		textPosition: "top"
		});
		if (app.hasOwnProperty("renderLayer") ) {//删除之前的图层
			 
			 app.map.removeLayer(app.renderLayer);
			 app.featureLayer = null;
			 //app.map.removeAllLayers();
		}
//		var prov_code = areaCode.substring(0,2);
//		getProductDataAndCopy2Workspace(prov_code,productDate,productKind_des);
		getProductDataAndCopy2Workspace(areaCode,productDate,app.productKind_des);

//		
//		addProductLayer(areaCode,productDate,productKind_des);
	}

}
function getProductDataAndCopy2Workspace(areaCode,productDate,productKind_des)
{
	
	$.ajax({
	    url:'/jf/thairice/t10pdt_report/CopyProductData2Workspace',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	
	    	productKind:productKind_des,//产品种类 yield等
			productDate:productDate,//选择的产品日期
			areaCode:areaCode,//选择的产品行政区域
	    	
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
	    	
//	    	alert("success");
	    	if(data.result)
	    	{
	    		//alert("ok");
	    		//$('.StaButton').show();// if data exist show StaButton
	    		var prov_code = areaCode.substring(0,2);
	    		
	    		addProductLayer(prov_code,productDate,productKind_des);
	    	}
	    	else{
//	    		alert("no");
	    		// 隐藏loading
	    		//$('.StaButton').hide();// if data exist show StaButton
	    		$("#mapDiv").busyLoad("hide");
	    		
	    		$('#systemTipsModal').modal();
		    	
		    	$('#systemTipsModalBtn').click(function(e) {
		    		
		    		//window.open(data.reportUrl,"_blank");
		    		$('#systemTipsModal').modal('hide');
		    		
		        });
	    	}
	    },
	    error:function(xhr,textStatus){
	    	// 隐藏loading
			$("#mapDiv").busyLoad("hide");
			
			$('#systemTipsModal').modal();
	    	
	    	$('#systemTipsModalBtn').click(function(e) {
	    		
	    		//window.open(data.reportUrl,"_blank");
	    		$('#systemTipsModal').modal('hide');
	    		
	        });
	    	
	        console.log('错误')
	        console.log(xhr)
	        console.log(textStatus)
	    },
	    complete:function(){
	        console.log('结束')
	    }
	})
}
function addProductLayer(prov_code,productDate,productKind_des)
{
//	alert("addProductLayer");
	var featureLayer_outFields = ["value","PROV_NAME","AMP_NAME","TAM_NAME","prov_code","amp_code","tam_code"];
	var renderLayer_id = productKind_des;
	var Render_field = 'value';
	//var workspaceId = 'yield';
	var workspaceId = productKind_des;
	//var dataSourceName = '72_Yield_2017_193.shp';
	var dataSourceName = '';
	if(productKind_des=="Yield")//估产
	{
//		dataSourceName = '72_Yield_2017_193.shp';
		dataSourceName = productDate+'_'+prov_code+'.shp';
	}
	if(productKind_des=="Drought")//干旱
	{
		dataSourceName = productDate+'_'+prov_code+'.shp';
	}
	if(productKind_des=="Area")//面积
	{
		dataSourceName = productDate+'_'+prov_code+'.shp';
	}
	if(productKind_des=="Growth")//面积
	{
		dataSourceName = productDate+'_'+prov_code+'.shp';
	}
	
	require(["esri/layers/ArcGISDynamicMapServiceLayer","esri/layers/FeatureLayer",
	      "esri/layers/DynamicLayerInfo", "esri/layers/LayerDataSource",
	      "esri/layers/LayerDrawingOptions","dojo/domReady!"],function(
	    		  ArcGISDynamicMapServiceLayer,FeatureLayer,DynamicLayerInfo,
	    		  LayerDataSource,LayerDrawingOptions){
		
		var dynamicLayerInfos=[];
		
		//var layerSource = getLayerSource("yield","72_Yield_2017_193.shp");
		var layerSource = getLayerSource(workspaceId,dataSourceName);
		///根据行政区划代码 确定需要过滤的字段
		var definitionExpression_str;
//		alert(app.areaCode);
		if(app.areaCode.length == 2)//选择的是省
		{
			definitionExpression_str = "prov_code = '"+app.areaCode+"'";
		}
		else if(app.areaCode.length == 4)//选择的是市
		{
			definitionExpression_str = "amp_code = '"+app.areaCode+"'";
		}
		else{//选择的是县
			definitionExpression_str = "tam_code = '"+app.areaCode+"'";
		}
		//定义一个要素图层:注意链接为动态图层的地址dynamicLayer  可以在点击连接看到 
		//供查询分级使用
		app.featureLayer = new FeatureLayer(featureLayerUrl, {
	        mode: FeatureLayer.MODE_ONDEMAND,
	        //outFields: ["value"],
	        outFields: featureLayer_outFields,
	        source: layerSource,
	        definitionExpression:definitionExpression_str
	    });
//	    console.log(app.featureLayer);
//	    console.log(app.featureLayer.fullExtent);
	    app.featureLayer.on("load",function(res){
	    	queryFeatureNum(app.featureLayer);
//	    	console.log(app.featureLayer.fullExtent);
//       	 	preview(app.featureLayer);//缩放到指定范围
//       	 createLegend(app.map, featureLayer,legendTitle);
        });
	    //动态图层 供渲染使用
	    app.renderLayer = new ArcGISDynamicMapServiceLayer(renderLayerUrl, {
	        //"id": "yield"
	    	"id": renderLayer_id
	     });
	    //
	 // create a new dynamic layer info object for the lakes layer
        var dynamicLayerInfo = new DynamicLayerInfo();
        dynamicLayerInfo.id = 0;
        //dynamicLayerInfo.name = "72_Yield_2017_193.shp";
        dynamicLayerInfo.name = dataSourceName;
        
        dynamicLayerInfo.source = layerSource;
        dynamicLayerInfos.push(dynamicLayerInfo);
        
        app.renderLayer.setDynamicLayerInfos(dynamicLayerInfos, true);
        //根据选择的行政区划代码 过滤要素
        var layerDefinitions = [];
        layerDefinitions[0] = definitionExpression_str;
//        layerDefinitions[0] = "value > 28";
        app.renderLayer.setLayerDefinitions(layerDefinitions);
        
	    //渲染图层
	    //createRender(app.featureLayer,app.renderLayer,"value",5);
        if(productKind_des=="Yield")
    	{
        	
        	createClassBreakRenderLayer(app.featureLayer,app.renderLayer,Render_field,5,"Yield(/ton)");
    	}
    	if(productKind_des=="Drought")
    	{
    		createUniqueValueRenderLayer(app.featureLayer,app.renderLayer,Render_field,5,"Drought");
    	}
    	if(productKind_des=="Growth")
    	{
    		createUniqueValueRenderLayer(app.featureLayer,app.renderLayer,Render_field,5,"Growth");
    	}
    	if(productKind_des=="Area")
    	{
    		createRenderLayer(app.featureLayer,app.renderLayer,Render_field,"Acreage");
    	}
        //createClassBreakRender(app.featureLayer,app.renderLayer,Render_field,5);
	});
	
}
function queryFeatureNum(featureLayer)//查看app.featureLayer 是否有数据
{
//	alert("queryFeatureNum");
	require([
		"esri/symbols/SimpleFillSymbol", 
	    "esri/symbols/SimpleLineSymbol",
	    "esri/renderers/ClassBreaksRenderer",
	    "esri/layers/LayerDrawingOptions",
	    "esri/Color",
	    "esri/tasks/query",
	    "dojo/dom",
	    "dojo/domReady!"],
	    function(SimpleFillSymbol,SimpleLineSymbol,ClassBreaksRenderer,LayerDrawingOptions,Color,Query,dom){
		 	
			var count = 5;//如果发生错误，尝试执行5次
			var queryParams = new Query();
//		    queryParams.outFields = ['value'];
		    //queryParams.outStatistics = [ minStatDef, maxStatDef];
		    queryParams.where = "1=1";
		    
		    featureLayer.queryFeatures(queryParams, getStats, geterror);
//		    featureLayer.queryFeatures(queryParams);
//		    console.log(featureLayer.url);
//		    featureLayer.on("query-features-complete",getStats);
//		    console.log("13");
		 // Executes on each query
		    function getStats(results){
//		    	 console.log("12");
		    	 var features = results.features;
		    	 if(features.length == 0)
			    	{
		    		 	$('.StaButton').hide();
		    		 	$('#legendinfo').hide();
			    		$('#systemTipsModal').modal();
				    	
				    	$('#systemTipsModalBtn').click(function(e) {
				    		
				    		//window.open(data.reportUrl,"_blank");
				    		$('#systemTipsModal').modal('hide');
				    		
				        });
			    	}
		    	 else{
		    		 $('.StaButton').show();
		    		 $('#legendinfo').show();
		    	 }
		    }
		    function geterror(error)
		    {
//		    	console.log(error);
		    	count--;
		    	if(count>0)
		    	{
//		    		console.log(count);
		    		featureLayer.queryFeatures(queryParams, getStats, geterror);
		    	}
		    	else{
		    		console.log(error);
		    	}
		    	
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
function createClassBreakRenderLayer(featureLayer,renderLayer,field,numbreaks,legendTitle)
{
	require([
		"esri/symbols/SimpleFillSymbol", 
	    "esri/symbols/SimpleLineSymbol",
	    "esri/renderers/ClassBreaksRenderer",
	    "esri/layers/LayerDrawingOptions",
	    "esri/Color",
	    "esri/tasks/query",
	    "dojo/dom",
	    "dojo/domReady!"],
	    function(SimpleFillSymbol,SimpleLineSymbol,ClassBreaksRenderer,LayerDrawingOptions,Color,Query,dom){
		
//		var outline = new SimpleLineSymbol().setColor(new Color([0, 0, 0, 0.0])).setWidth(0.0);
//	    var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_NULL,
//	    		outline,new Color([0,0,0,0.0])
//	    	  );
		var count = 5;//如果发生错误，尝试执行5次
		var drawingOptions = new LayerDrawingOptions();
		
	    var renderer = new ClassBreaksRenderer(null, field);
//	    var renderColor = [[56, 168, 0, 1],[139, 209, 0, 1],[255, 255, 0, 1],[255, 128, 0, 1],[255, 0, 0, 1]];
	    var renderColor = [[255, 0, 0, 1],[255, 128, 0, 1],[255, 255, 0, 1],[139, 209, 0, 1],[56, 168, 0, 1]];
	    
	    var queryParams = new Query();
	    queryParams.outFields = [field];
	    //queryParams.outStatistics = [ minStatDef, maxStatDef];
	    queryParams.where = "1=1";
	    
	    featureLayer.queryFeatures(queryParams, getStats, geterror);
//	    featureLayer.queryFeatures(queryParams);
//	    console.log(featureLayer.url);
//	    featureLayer.on("query-features-complete",getStats);
//	    console.log("13");
	 // Executes on each query
	    function getStats(results){
//	    	 console.log("12");
	    	 var features = results.features;
//	    	 console.log(features);        
	    	 var min = max = parseFloat(features[0].attributes[field]);
	    	           
	    	 //找到Florida 城市中的最少人数和最多人数
	    	           
	    	 dojo.forEach(features,function(feature) {
	    	 min = Math.min(min,feature.attributes[field]);
	    	 max = Math.max(max,feature.attributes[field]);
	    	 });
	    	 
	    	 //console.log(min,max);
	    	 
	    	 var breaks = ((max-min)/numbreaks).toFixed(2);
	    	 //console.log(breaks);
	    	 for (var i=0;i<numbreaks;i++) {
	    		if(i==numbreaks-1)
	   			{
	   			 renderer.addBreak(
	       				 (min + (i*breaks)).toFixed(2), 
	       				 max.toFixed(2),
	           			new SimpleFillSymbol("solid", null, new Color(renderColor[i]))
	           	        );
	   			}
	       		 else{
	       			renderer.addBreak(
	        				 (min + (i*breaks)).toFixed(2), 
	        				 (min + ((i+1)*breaks)).toFixed(2),
	            			new SimpleFillSymbol("solid", null, new Color(renderColor[i]))
	            	        );
	       		 }
	    		 
	    	 }
	    	 
	    	 drawingOptions.renderer = renderer;
	            
             var options = [];
             options[0] = drawingOptions;

             renderLayer.setLayerDrawingOptions(options);
            
             featureLayer.setRenderer(renderer);          
             featureLayer.refresh();
	       	//地图添加动态图层
	         
//	         featureLayer.on("load",function(res){
//	        	 preview(featureLayer);//缩放到指定范围
//	        	 createLegend(app.map, featureLayer,legendTitle);
//	         });
//             console.log("11");
	         app.map.addLayer(renderLayer);

//	         preview(app.featureLayer);//缩放到指定范围

	         createLegend(app.map, featureLayer,legendTitle);

	      
	    }
	    function geterror(error)
	    {
//	    	console.log(error);
	    	count--;
	    	if(count>0)
	    	{
//	    		console.log(count);
	    		featureLayer.queryFeatures(queryParams, getStats, geterror);
	    	}
	    	else{
	    		console.log(error);
	    	}
	    	
	    }
	    
	    
	});
	
}
function createUniqueValueRenderLayer(featureLayer,renderLayer,field,uniqueKinds,legendTitle){
	
	require([
		"esri/symbols/SimpleFillSymbol", 
	    "esri/symbols/SimpleLineSymbol",
	    "esri/renderers/UniqueValueRenderer",
	    "esri/layers/LayerDrawingOptions",
	    "esri/Color",
	    "esri/tasks/query",
	    "dojo/dom",
	    "dojo/domReady!"],
	    function(SimpleFillSymbol,SimpleLineSymbol,UniqueValueRenderer,LayerDrawingOptions,Color,Query,dom){
		
//		var outline = new SimpleLineSymbol().setColor(new Color([0, 0, 0, 0.0])).setWidth(0.0);
//	    var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_NULL,
//	    		outline,new Color([0,0,0,0.0])
//	    	  );
		var drawingOptions = new LayerDrawingOptions();
		
	    var renderer = new UniqueValueRenderer(null, field);
//	    var renderColor = [[0, 255, 127, 1],[152, 251, 152, 1],[255, 255, 0, 1],[255, 128, 0, 1],[255, 0, 0, 1]];
//	    var renderLabel = {
//	    		"Drought":["Moist","Normal","Light Drought","Middling","Heavy"],
//	    		"Growth":["Very Bad","Bad","Average","Good","Excellent"],
//	    };
	      renderer.addValue({
	        value: "1",
	        symbol: new SimpleFillSymbol("solid", null, new Color(render_color[legendTitle][0])),
	        label: renderLabel_Drought_Growth[legendTitle][0],
	        description: renderLabel_Drought_Growth[legendTitle][0]
	      });
	      renderer.addValue({
	        value: "2",
	        symbol: new SimpleFillSymbol("solid", null, new Color(render_color[legendTitle][1])),
	        label: renderLabel_Drought_Growth[legendTitle][1],
	        description: renderLabel_Drought_Growth[legendTitle][1]
	      });
	      renderer.addValue({
	        value: "3",
	        symbol: new SimpleFillSymbol("solid", null, new Color(render_color[legendTitle][2])),
	        label: renderLabel_Drought_Growth[legendTitle][2],
	        description: renderLabel_Drought_Growth[legendTitle][2]
	      });
	      renderer.addValue({
	        value: "4",
	        symbol: new SimpleFillSymbol("solid", null, new Color(render_color[legendTitle][3])),
	        label: renderLabel_Drought_Growth[legendTitle][3],
	        description: renderLabel_Drought_Growth[legendTitle][3]
	      });
	      renderer.addValue({
	        value: "5",
	        symbol: new SimpleFillSymbol("solid", null, new Color(render_color[legendTitle][4])),
	        label: renderLabel_Drought_Growth[legendTitle][4],
	        description: renderLabel_Drought_Growth[legendTitle][4]
	      });
	      
	      drawingOptions.renderer = renderer;
          
          var options = [];
          options[0] = drawingOptions;

          renderLayer.setLayerDrawingOptions(options);
         
          featureLayer.setRenderer(renderer);          
          featureLayer.refresh();
       	//地图添加动态图层
//          console.log("21");
          app.map.addLayer(renderLayer);
          createLegend(app.map, featureLayer,legendTitle);
	    /*var queryParams = new Query();
	    queryParams.outFields = [field];
	    //queryParams.outStatistics = [ minStatDef, maxStatDef];
	    queryParams.where = "1=1";
	    
//	    featureLayer.queryFeatures(queryParams, renderFeatures, null);
	    featureLayer.queryFeatures(queryParams);
	    console.log("20");
	    featureLayer.on("query-features-complete",renderFeatures);
	    function renderFeatures(results)
	    {
	    	  console.log("20");
	    	  renderer.addValue({
		        value: "1",
		        symbol: new SimpleFillSymbol("solid", null, new Color(renderColor[0])),
		        label: "Moist",
		        description: "Moist"
		      });
		      renderer.addValue({
		        value: "2",
		        symbol: new SimpleFillSymbol("solid", null, new Color(renderColor[1])),
		        label: "Normal",
		        description: "Normal"  
		      });
		      renderer.addValue({
		        value: "3",
		        symbol: new SimpleFillSymbol("solid", null, new Color(renderColor[2])),
		        label: "Light Drought",
		        description: "Light Drought"  
		      });
		      renderer.addValue({
		        value: "4",
		        symbol: new SimpleFillSymbol("solid", null, new Color(renderColor[3])),
		        label: "Middling",
		        description: "Middling"  
		      });
		      renderer.addValue({
		        value: "5",
		        symbol: new SimpleFillSymbol("solid", null, new Color(renderColor[4])),
		        label: "Heavy",
		        description: "Heavy"  
		      });
		      
		      drawingOptions.renderer = renderer;
	          
	          var options = [];
	          options[0] = drawingOptions;

	          renderLayer.setLayerDrawingOptions(options);
	         
	          featureLayer.setRenderer(renderer);          
	          featureLayer.refresh();
	       	//地图添加动态图层
	          console.log("21");
	          app.map.addLayer(renderLayer);
	         
//	         var layerExt = app.featureLayer.fullExtent;
	         
	         //console.log(layerExt);
//	         preview(app.featureLayer);//缩放到指定范围
	      	// create the legend if it doesn't exist
	         //if (!app.hasOwnProperty("legend") ) {
	         //createLegend(app.map, featureLayer,"Drought");
	         //if (!app.hasOwnProperty("legend") ) {
//	         createLegend(app.map, featureLayer,legendTitle);
	         //}
//	         else{
//	        	 app.legend.destroy();
//	        	 createLegend(app.map, featureLayer,"Drought");
//	         }
	    }*/
	    
	    
	});
}
function createRenderLayer(featureLayer,renderLayer,field,legendTitle){
	
	require([
		"esri/symbols/SimpleFillSymbol", 
	    "esri/symbols/SimpleLineSymbol",
	    "esri/renderers/SimpleRenderer",
	    "esri/layers/LayerDrawingOptions",
	    "esri/Color",
	    "esri/tasks/query",
	    "dojo/dom",
	    "dojo/domReady!"],
	    function(SimpleFillSymbol,SimpleLineSymbol,SimpleRenderer,LayerDrawingOptions,Color,Query,dom){
		

		var drawingOptions = new LayerDrawingOptions();
		
		var renderer = new SimpleRenderer(
		          new SimpleFillSymbol("solid", null, new Color([255, 128, 0, 1]) // fuschia lakes!
		));
		drawingOptions.renderer = renderer;
	          
	      var options = [];
	      options[0] = drawingOptions;
	
	      renderLayer.setLayerDrawingOptions(options);
	     
	      featureLayer.setRenderer(renderer);          
	      featureLayer.refresh();
	   	//地图添加动态图层
//	      console.log("3");
	      app.map.addLayer(renderLayer);
	      createLegend(app.map, featureLayer,legendTitle);
//	     var layerExt = app.featureLayer.fullExtent;
//	     console.log(app.featureLayer);
//	     console.log(app.featureLayer.fields);
//	     preview(app.featureLayer);//缩放到指定范围
//	  	
//	     createLegend(app.map, featureLayer,legendTitle);
	     //}

	    
	});
}
function createLegend(map, fl,legendTitle) {
	
	require([
		"esri/dijit/Legend",
		"dojo/dom",
	    "dojo/domReady!"],
	    function(Legend,dom){
		
		if (app.hasOwnProperty("legend") ) {
			//app.legend.destroy();
			app.legend.refresh([{layer:fl, title:legendTitle}]);
		}
		else{
			app.legend = new Legend({
			      map : map,
			      layerInfos : [ {
			        layer : fl,
			        //title : "Yield(/ton)"
			        title : legendTitle
			      } ]
			    }, dom.byId("legendDiv"));
			app.legend.startup();
		}
		// 隐藏loading
		$("#mapDiv").busyLoad("hide");
	});
    
}
/*******************************************************************************************************************/ 
/**
 * 统计
 */
var sta = {};

	//通过行政区区域名称 得到行政代码
	function getCodeByName()
	{
		
	}
	//通过行政区域代码 得到行政区域名称
	function getNameByCode()
	{
		
	}
	//根据行政区域代码 获取相应的geometry
	function getGeometryByCode(code,url)
	{
		
	}
	function getChildAreaCodeAndNameByParentAreaCode(areaCode)
	{
//		var ChildAreaCodeAndName_Array = [{'NAME':'MUEANG SUPHAN BURI','CODE':'7201'},{'NAME':'DOEM BANG NANG BUAT','CODE':'7202'},
//			{'NAME':'DAN CHANG','CODE':'7203'},{'NAME':'BANG PLA MA','CODE':'7204'},
//			{'NAME':'SI PRACHAN','CODE':'7205'},{'NAME':'DON CHEDI','CODE':'7206'},{'NAME':'SONG PHI NONG','CODE':'7207'},
//			{'NAME':'SAM CHUK','CODE':'7208'},{'NAME':'U THONG','CODE':'7209'},{'NAME':'NONG YA SAI','CODE':'7210'}];
//		
		var ChildAreaCodeAndName_Array;
		$.ajax({
		    url:'/jf/thairice/t13region/getChildAreaCodeAndNameByParentAreaCode',
		    type:'POST', //GET
		    async:false,    //或false,是否异步
		    data:{
		    	parentareaCode:areaCode
		    },
		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        
		        console.log('发送前')
		    },
		    success:function(data,textStatus,jqXHR){
		    	
//		    	alert("success");
		    	if(data.flag)
		    	{
//		    		console.log(data);
		    		ChildAreaCodeAndName_Array = data.childRegions;
		    	}
		    	else{
		    		
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
//		console.log(ChildAreaCodeAndName_Array);
		return ChildAreaCodeAndName_Array;
	}
	//得到Province-Country 根据省---》市
	function getCountry(areaCode,featureLayer,field)
	{
		
		$("#staChartModal").busyLoad("show", { text: "LOADING ...",
			textPosition: "top"
		});
		/*
		var queryTask = new esri.tasks.QueryTask(featureQueryUrl);
        var query = new esri.tasks.Query();
        query.outFields = ["CODE","NAME"];//
        query.returnGeometry = true;
        query.where = "PROV_CODE = '"+Province+"'";
        queryTask.execute(query, handleQueryResult, errorHandler);
        
        function handleQueryResult(results) {
            if ( ! results.hasOwnProperty("features") ||
                 results.features.length === 0 ) {
            	alert("no");
              return; // no features, something went wrong
            }
            
            app.staData = [];
            //console.log("sta",results);
            sta(results.features,featureLayer,field,app.staData);
            //ChartStaData(outdatas);
          }
        
        function errorHandler(err) {
            console.log('Oops, error: ', err);
          }
        */
		var ChildAreaCodeAndName_Array = getChildAreaCodeAndNameByParentAreaCode(areaCode);
//		console.log(ChildAreaCodeAndName_Array);
		app.staData = [];
        //console.log("sta",results);
        sta(ChildAreaCodeAndName_Array,featureLayer,field,app.staData);
      //按行政区域进行统计
      /*
       / features---》
        * {
        * feature:{code,name,geometry}
        * }
       */
      function sta(features,featureLayer,field,staData)
      {
      	//var datas = [];
      	//var value = 0;
    	console.log("sta---->");
//    	console.log(features);
    	var len = features.length;//控制位
      	dojo.forEach(features,function(feature){
      		
      		staDataByUnitGeometry(len,feature,featureLayer,field,staData);
      		
      	});
      	//console.log(datas);
      	//根据行政单元的地理范围查询地理范围内的features
      	function staDataByUnitGeometry(len,feature,featureLayer,field,staData)
      	{
      		  var data = {};
//      		data.name = feature.attributes["NAME"];//
//      		data.code = feature.attributes["CODE"];//
      		  data.name = feature["NAME"];//
      		  data.code = feature["CODE"];//
//      		console.log(data.code);
      		  var queryParams = new esri.tasks.Query();
              queryParams.outFields = [field];
              
              if(app.areaCode.length == 2)//选择的是省--->市
	      		{
            	  	queryParams.where = "amp_code = '"+feature["CODE"]+"'";//行政代码相同
	      		}
	      		else{//选择的是//选择的是市--->县
	      			queryParams.where = "tam_code = '"+feature["CODE"]+"'";
	      		}
	          /*
              if(app.productKind_code=="01"){//Area
            	  
            	  queryParams.where = "code = '"+feature.attributes["CODE"]+"'";//行政代码相同
              }
              else{
            	  queryParams.geometry = feature.geometry;
              }
              */
              var countTry = 5;//queryFeatures如果出错，尝试执行5次
              featureLayer.queryFeatures(queryParams, getStats, errback);
              
              function getStats(results){
             	 
             	 var features = results.features;
//             	 console.log("staDataByUnitGeometry",features.length);
             	 processData(features,field,data,staData);
             	 //console.log(staData);
             	 /*
             	 var sum = 0;        
             	 dojo.forEach(features,function(feature) {
             		 sum += feature.attributes[field];
             	 });
             	 data.value = sum.toFixed(2);
             	 staData.push(data);
             	 */
             	 
             	 if(len == staData.length)//循环结束
             	 {
             		ChartStaData(staData);//生成统计图
             		//console.log(staData);
             	 }
             	//console.log(datas);
             	 //console.log("staDataByUnitGeometry",data); 
             }
             
             function errback(err){
            	 countTry--;
            	 if(countTry>0)
            	 {
            		 featureLayer.queryFeatures(queryParams, getStats, errback);
            	 }
            	 else{
//                	 value = 0;
//                	 data.value = value;
//                	 staData.push(data);
                	 console.log("Couldn't retrieve summary statistics. ", err);
            	 }

             }
      	}
      }
	}
	function processData(features,field,data,staData)
	{
		if(app.productKind_code=="01"){//Area
//			console.log(data);
			var sum = 0;     
			if(features.length>0)
			{
				dojo.forEach(features,function(feature) {
		    		 var feature_area = calculatorArea(feature);
		    		
//		    		 sum += feature.attributes[field];
		    		 sum += feature_area;
		    	 });
			}
	    	
	    	 data.value = sum>0?(sum.toFixed(2)):0;
//	    	 data.value = sum.toFixed(2);
	    	 staData.push(data);
		} 
		if(app.productKind_code=="03"){//Yield
			
			 var sum = 0;  
			 var count = 0;
			 if(features.length>0)
			 {
				 dojo.forEach(features,function(feature) {
		    		 
		    		 sum += feature.attributes[field];
		    		
		    		 
		    		 count +=1; 
		    	 });
			 }
	    	 
//	    	 data.value = sum.toFixed(2);
	    	 data.value = sum>0?([((sum*1600)/(count*250*250)).toFixed(2),sum.toFixed(2)]):([0,0]);
	    	 staData.push(data);
		}
		if(app.productKind_code=="04"||app.productKind_code=="02"){//Drought or Growth
			
			 var sum = 0; 
			 var moistNum = 0;
			 var normalNum = 0;
			 var lightDroughtNum = 0;
			 var middlingNum = 0;
			 var heavyNum = 0;
			 
			 if(features.length>0)
			 {
				 dojo.forEach(features,function(feature) {
		    		 sum += 1;
		    		 
		    		 if(feature.attributes[field]==1)
	    			 {
	    			 	moistNum+=1;
	    			 }
		    		 if(feature.attributes[field]==2)
	    			 {
		    			normalNum+=1;
	    			 }
		    		 if(feature.attributes[field]==3)
	    			 {
		    			lightDroughtNum+=1;
	    			 }
		    		 if(feature.attributes[field]==4)
	    			 {
		    			middlingNum+=1;
	    			 }
		    		 if(feature.attributes[field]==5)
	    			 {
		    			heavyNum+=1;
	    			 }
		    	 });
		    	 data.value = [toHundred(moistNum/sum),
		    		 toHundred(normalNum/sum),
		    		 toHundred(lightDroughtNum/sum),
		    		 toHundred(middlingNum/sum),
		    		 toHundred(heavyNum/sum)];
			 }
			 else{
				 data.value = [0,
		    		 0,
		    		 0,
		    		 0,
		    		 0];
			 }
	    	 
	    	 staData.push(data);
		}
		
    	 
    	return staData;
	}
	function calculatorArea(feature)
	{
		var area_RAI;
		require(["esri/geometry/webMercatorUtils","esri/geometry/geodesicUtils", "esri/units"], 
				function(webMercatorUtils,geodesicUtils, Units) {
			//console.log(feature);
			var area_SQUARE_METERS = geodesicUtils.geodesicAreas([feature.geometry], esri.Units.SQUARE_METERS);
			area_RAI = area_SQUARE_METERS[0]/1600;//泰国常用面积单位
//			console.log("calculatorArea-----"+area_SQUARE_METERS);
//			console.log("calculatorArea-----"+area_RAI);
			
		});
//		console.log("calculatorArea-----"+area_RAI);
		return area_RAI;
	}
	//功能：归100     
	function toHundred(x) {   
		var f = parseFloat(x);    
		if (isNaN(f)) {   
		  return;    
		}          
		f = Math.round(x*100);  
		return f;        
	} 
	//保留两位小数    
	//功能：将浮点数四舍五入，取小数点后2位     
	function toDecimal(x) {   
		var f = parseFloat(x);    
		if (isNaN(f)) {   
		  return;    
		}          
		f = Math.round(x*100)/100;  
		return f;        
	}   
	function Num2EnFormat(iValue)
	{
//		var iValue = 20002365879.12; //要转换的数字
		var sValue = iValue+'';
		var needProcessSvalue = (sValue.indexOf('.')>0)?sValue.substring(0,sValue.indexOf('.')):sValue;//'20002365879'
		var floatNumStr = (sValue.indexOf('.')>0)?sValue.substring(sValue.indexOf('.')):'';//'.12'
		var aValue = new Array();
		var iNum = needProcessSvalue.length%3;
		var aResult; //转换结果
		var index = 0;
		if(needProcessSvalue.length<=3){
		    return sValue;
		}else{
		    if(iNum == 0){
			    for(var i=0; i<needProcessSvalue.length; i=i+3){
			        aValue[index] = needProcessSvalue[i]+''+needProcessSvalue[i+1]+''+needProcessSvalue[i+2];
			        index++;
			    }
			}else if(iNum == 1){
			    aValue[0] = needProcessSvalue[0];
			    index = 1;
			    for(var i=1; i<needProcessSvalue.length; i=i+3){
			        aValue[index] = needProcessSvalue[i]+''+needProcessSvalue[i+1]+''+needProcessSvalue[i+2];
			        index++;
			    }
			}
			else if(iNum == 2){
			    aValue[0] = needProcessSvalue[0]+''+needProcessSvalue[1];
			    index = 1;
			    for(var i=2; i<needProcessSvalue.length; i=i+3){
			        aValue[index] = needProcessSvalue[i]+''+needProcessSvalue[i+1]+''+needProcessSvalue[i+2];
			        index++;
			    }
			}
		aResult = aValue.join(',');
		return aResult.toString()+floatNumStr;//输出20,002,365,879
		}   
	}
	function ChartStaData(staData)
	{
		//console.log(staData);
		
		//console.log("ChartStaData",stadatas);
		// 基于准备好的dom，初始化echarts实例
        //var myChart = echarts.init(document.getElementById('staDiv'));
		if(app.staExcelInit)//统计表格已进行初始化
		{
			$('#sta_excel').DataTable().destroy();
			$('#sta_excel').empty();
			
		}
		
		app.staExcelInit = true;
		if(app.productKind_code=="01"){//Area
        	
        	var names = [];
    		var datas = [];
    		var dataSet = [];
    		dojo.forEach(staData,function(stadata) {
    			//console.log(stadata,i);
        		 names.push(stadata["name"]);
        		 datas.push(stadata["value"]);
        		 var data = [];
        		 data.push(stadata["name"]);
        		 data.push(Num2EnFormat(stadata["value"]));
        		 dataSet.push(data);
        	 });
    		//统计表
    		
   		 	
    		$('#sta_excel').DataTable({
    		        data: dataSet,
//    		        "filter": false,
//    		        "destroy": true,
    		        columns: [
    		            { title: "Name" },
    		            { title: "Value/rai" }
    		        ]
    		    });
    		// 指定图表的配置项和数据
            var option = {
            	color: ['#3398DB'],
                title: {
                    text: 'Rice Acreage',
                    textStyle:{
                    	color:'#fff'
                    }
                },
                tooltip: {},
                toolbox: {
                    show: true,
                    orient: 'horizontal',
                    left: 'right',
                    //top: 'center',
                    feature: {
//                        mark: {show: true},
//                        dataView: {show: true, readOnly: false},
                        magicType: {
                        	show: true,
                        	type: ['line', 'bar'],
                        	title:{
                        		line:'line',
                        		bar:'bar'
                        	}
                        },
//                        restore: {show: true},
//                        saveAsImage: {show: true}
                    }
                },
                legend: {
                    data:['Acreage'],
                    textStyle:{
                    	color:'#fff'
                    }
                },
                xAxis: {
                	name:"Region",
                	nameLocation:'end',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                    data: names
                },
                yAxis: {
                	name:"Acreage/rai",
                	//nameLocation:'middle',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                },
                series: [{
                    name: 'Acreage',
                    type: 'bar',
                    data: datas,
                    markPoint : {
                        data : [
                            {type : 'max', name: 'MAX'},
                            {type : 'min', name: 'MIN'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: 'AVG'}
                        ]
                    }
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            app.staChart.clear();
            app.staChart.setOption(option);
		}
        if(app.productKind_code=="03"){//Yield
        	
        	var names = [];
//    		var datas = [];
        	var datas_sum_yield = [];
        	var datas_unit_yield = [];
    		var dataSet = [];
    		dojo.forEach(staData,function(stadata) {
    			//console.log(stadata,i);
        		 names.push(stadata["name"]);
//        		 datas.push(stadata["value"]);
        		 datas_unit_yield.push(stadata["value"][0]);
        		 datas_sum_yield.push(stadata["value"][1]);
        		 var data = [];
//        		 data.push(stadata["name"]);
//        		 data.push(Num2EnFormat(stadata["value"]));
        		 var temp_data = stadata["value"].slice(0);
        		 data = [temp_data[0],Num2EnFormat(temp_data[1])];
        		 data.unshift(stadata["name"]);
        		 dataSet.push(data);
        	 });
    		//统计表
    		
   		 	
    		$('#sta_excel').DataTable({
    		        data: dataSet,
//    		        "filter": false,
//    		        "destroy": true,
    		        columns: [
    		            { title: "Name" },
    		            { title: "Yield(ton)" },
    		            { title: "Gross(ton)" }
    		        ]
    		    });
    		// 指定图表的配置项和数据
            var option = {
            	color: ['#3398DB'],
                title: {
                    text: 'Rice Yield',
                    textStyle:{
                    	color:'#fff'
                    }
                },
                tooltip: {},
                toolbox: {
                    show: true,
                    orient: 'horizontal',
                    left: 'right',
                    //top: 'center',
                    feature: {
//                        mark: {show: true},
//                        dataView: {show: true, readOnly: false},
                        magicType: {show: true,
                        	type: ['line', 'bar'],
                        	title:{
                        		line:'line',
                        		bar:'bar'
                        	}
                        },
//                        restore: {show: true},
//                        saveAsImage: {show: true}
                    }
                },
                legend: {
                    data:['Yield'],
                    textStyle:{
                    	color:'#fff'
                    }
                },
                xAxis: {
                	name:"Region",
                	nameLocation:'end',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                    data: names
                },
                yAxis: {
                	name:"Yield/ton",
                	//nameLocation:'middle',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                },
                series: [{
                    name: 'Yield',
                    type: 'bar',
                    data: datas_sum_yield,
                    markPoint : {
                        data : [
                            {type : 'max', name: 'MAX'},
                            {type : 'min', name: 'MIN'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: 'AVG'}
                        ]
                    }
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            app.staChart.clear();
            app.staChart.setOption(option);
		}
        if(app.productKind_code=="04"||app.productKind_code=="02"){//Drought or Growth
        	var names = [];
    		var datas_Moist = [];
    		var datas_Normal = [];
    		var datas_LightDrought = [];
    		var datas_Middling = [];
    		var datas_Heavy = [];
    		var dataSet = [];
    		dojo.forEach(staData,function(stadata) {
    			 //console.log(stadata);
        		 names.push(stadata["name"]);
        		 datas_Moist.push(stadata["value"][0]);
        		 datas_Normal.push(stadata["value"][1]);
        		 datas_LightDrought.push(stadata["value"][2]);
        		 datas_Middling.push(stadata["value"][3]);
        		 datas_Heavy.push(stadata["value"][4]);
//        		 datas.push(stadata["value"]);
        		 var data = [];
        		 //data.push(stadata["name"]);
        		 data = stadata["value"].slice(0);
        		 data.unshift(stadata["name"]);
        		 dataSet.push(data);
        	 });
    		//console.log(staData);
    		//统计表
//    		$('#sta_excel').DataTable().clear().draw();
//    		 $('#sta_excel').DataTable().destroy();
//    		 $('#sta_excel').empty();
    		$('#sta_excel').DataTable({
    		        data: dataSet,
//    		        "filter": false,
//    		        "destroy": true,
    		        columns: [
    		            { title: "Name" },
//    		            { title: "Moist" },
//    		            { title: "Normal" },
//    		            { title: "Light Drought" },
//    		            { title: "Middling" },
//    		            { title: "Heavy" }
    		            { title: renderLabel_Drought_Growth[app.productKind_des][0]+' Ratio(%)'},
    		            { title: renderLabel_Drought_Growth[app.productKind_des][1]+' Ratio(%)'},
    		            { title: renderLabel_Drought_Growth[app.productKind_des][2]+' Ratio(%)'},
    		            { title: renderLabel_Drought_Growth[app.productKind_des][3]+' Ratio(%)'},
    		            { title: renderLabel_Drought_Growth[app.productKind_des][4]+' Ratio(%)'}
    		        ]
    		    });
    		
    		// 指定图表的配置项和数据
            var option = {
//            	color: ['#00FF7F','#98FB98','#FFFF00','#FF8000','#FF0000'],
            	color: render_color_rgb[app.productKind_des],
                title: {
//                    text: 'TaiLand Drought',
                	text: 'ThaiLand '+app.productKind_des,
                    textStyle:{
                    	color:'#fff'
                    }
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
//                    data:['Moist','Normal','LightDrought','Middling','Heavy'],
                	data:renderLabel_Drought_Growth[app.productKind_des],
                    textStyle:{
                    	color:'#fff'
                    }
                },
                xAxis: {
                	name:"Region",
                	nameLocation:'end',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                    data: names
                },
                yAxis: {
//                	name:"Drought",
                	name:app.productKind_des+' Ratio(%)',
                	//nameLocation:'middle',
                	nameTextStyle:{
                		color:'#fff'
                	},
                	axisLabel:{
                		color:'#fff'
                	},
                	max:100,
                },
                series: [
                	{
	                    name: renderLabel_Drought_Growth[app.productKind_des][0],
	                    type: 'bar',
	                    stack: 'drought',
	                    data: datas_Moist
                    },
                    {
                        name: renderLabel_Drought_Growth[app.productKind_des][1],
                        type: 'bar',
                        stack: 'drought',
                        data: datas_Normal
                    },
                    {
                        name: renderLabel_Drought_Growth[app.productKind_des][2],
                        type: 'bar',
                        stack: 'drought',
                        data: datas_LightDrought
                    },
                    {
                        name: renderLabel_Drought_Growth[app.productKind_des][3],
                        type: 'bar',
                        stack: 'drought',
                        data: datas_Middling
                    },
                    {
                        name: renderLabel_Drought_Growth[app.productKind_des][4],
                        type: 'bar',
                        stack: 'drought',
                        data: datas_Heavy
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            app.staChart.clear();
            app.staChart.setOption(option);
        }
     // 隐藏loading
		$("#staChartModal").busyLoad("hide");
     
	}
	//得到Country-Town
	function getTown(Country)
	{
		
	}
//Preview
/* ************************************************************************************************************/
function preview(featureLayer)
{
	var layerExt = featureLayer.fullExtent;
 	app.map.setExtent(layerExt.expand(1));
//	alert(layerExt);
	
}
 /****************************************************************************************************************/
/****************************************************************************/
/*
 * 生成报告
 * reprotType  报告格式 doc\pdf
 * legendDivID 图例divID
 * staDivID 统计图divID
 */
function generateReport(reprotType,legendDivID,staDivID)
{
	//console.log(app.dynamicLayer);
//	var layerExt = app.featureLayer.fullExtent;
// 	app.map.setExtent(layerExt.expand(1));
// 	
 	//printMap(reprotType,legendDivID,staDivID);
	//printStaChart();
	printChart(app.mapPicUrl,reprotType,legendDivID,staDivID);
}
function printMap()
{
	require([
		"esri/tasks/PrintTask", 
		"esri/tasks/PrintParameters",
		"esri/tasks/PrintTemplate",
	    "dojo/dom",
	    "dojo/domReady!"],
	    function(PrintTask,PrintParameters,PrintTemplate,dom){
		
		
		var url = printMapUrl;
		//var url = "https://sampleserver6.arcgisonline.com/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task";
		var printTask = new PrintTask(url);
		
		var template = new PrintTemplate();
		template.format = "PNG32";
		template.layout = "MAP_ONLY";
//		template.layout = "A4 Portrait";
		template.preserveScale = false;  
		template.exportOptions = {
			    width: 580,
			    height: 400,
			    dpi: 96
			  };
//		template.layoutOptions = { 
//	              "authorText": "Made by:  lyf",
//	              "copyrightText": "<lyf>",
//	              "legendLayers": [app.featureLayer], 
//	              "titleText": "Yield", 
//	              "scalebarUnit": "Miles" 
//	            };
		
		var params = new PrintParameters();
		params.map = app.map;
		params.template = template;
		
//		printTask.execute(params, function (evt) {
//			 console.log(evt);
//	           
//	       }); 
		var tryCount = 5;
		printTask.execute(params, printResult,errback);
		
		function printResult(Result)
		{
			//alert("2222");
			//console.log(Result.url);
			//var mapPicUrl = Result.url;
			app.mapPicUrl = Result.url;
	        //printChart(mapPicUrl,reprotType,legendDivID,staDivID);
			$("#mapPic").attr('src',app.mapPicUrl); 
			
			//console.log(mapPic_str);
//			var legendPicUrl = printChart("#info");//lengend
//			var staPicUrl = printChart("#stainfo");//sta
			//printChart(mapPicUrl,"#info","#stainfo");
			//getReport();
		}
		function errback(err){
			tryCount--;
			if(tryCount>0)
			{
				printTask.execute(params, printResult,errback);
			}
			else{
				console.log("Couldn't print mapPic. ", err);
			}
       	}
	});
}
//传入图片路径url，返回base64
function getBase64(img){
    function getBase64Image(img,width,height) {//width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
      var canvas = document.createElement("canvas");
      canvas.width = width ? width : img.width;
      canvas.height = height ? height : img.height;

      var ctx = canvas.getContext("2d");
      ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
      var dataURL = canvas.toDataURL();
      return dataURL;
    }
    var image = new Image();
    image.crossOrigin = '';
    image.src = img;
    var deferred=$.Deferred();
    if(img){
      image.onload =function (){
        deferred.resolve(getBase64Image(image));//将base64传给done上传处理
      }
      return deferred.promise();//问题要让onload完成后再return sessionStorage['imgTest']
    }
  }
//抓取图例和统计图
function printChart(mapPicUrl,reprotType,legendDivID,staDivID)
{
//	html2canvas(document.getElementById("staDiv"), {    
//        allowTaint: true,    
//        taintTest: false,    
//        onrendered: function(canvas) {    
//            canvas.id = "mycanvas";    
//            //生成base64图片数据    
//            var dataUrl = canvas.toDataURL();
//            console.log(dataUrl);
//            var newImg = document.createElement("img");    
//            newImg.src =  dataUrl;    
//            document.body.appendChild(newImg);    
//        }    
//	}); 
	var opts = {
	        //width: 200, 
	        //height: 200 
	        scale: 1 // 添加的scale 参数
	    };
	
//	var optsSta = {
//	        width: 400, 
//	        height: 100,
//	        scale: 2, // 添加的scale 参数
//	    };
	html2canvas(document.querySelector(legendDivID),opts).then(canvas => {
	    //document.body.appendChild(canvas)
		//if(reprotType=="doc")
		//{
			var legendPicUrl_base64 = canvas.toDataURL();
			//console.log(legendPicUrl_base64);
		    var legendPic_str = encodeURIComponent(legendPicUrl_base64.substring(legendPicUrl_base64.indexOf(",")+1));
		    //console.log(legendPicUrl);
		    

			html2canvas(document.querySelector(staDivID),opts).then(canvas => {
			    //document.body.appendChild(canvas)
				var staPicUrl_base64 = canvas.toDataURL();
				//console.log(staPicUrl_base64);
			    var staPic_str = encodeURIComponent(staPicUrl_base64.substring(staPicUrl_base64.indexOf(",")+1));
				
				//console.log(staPicUrl);
				
				getReport(reprotType,mapPicUrl,legendPic_str,staPic_str);
				
			});
		//}
//		if(reprotType=="pdf")
//		{
//			var legendPicUrl_base64 = canvas.toDataURL();
//		    var legendPic_str = legendPicUrl_base64
//		    console.log(legendPic_str);
//
//			html2canvas(document.querySelector(staDivID),opts).then(canvas => {
//			    //document.body.appendChild(canvas)
//				var staPicUrl_base64 = canvas.toDataURL();
//			    var staPic_str = staPicUrl_base64
//				getReport(reprotType,mapPicUrl,legendPic_str,staPic_str);
//				
//			});
//		}
		
	});
	
	
	
}
function getReport(reprotType,mapPicUrl,legendPic_str,staPic_str)
{
//	console.log(mapPicUrl);
//	console.log(legendPic_str);
//	console.log(staPic_str);
	//console.log(app.staData);
	$.ajax({
	    url:'/jf/thairice/t10pdt_report/generateRoprt',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	userID:app.userID,//用户ID
	    	ProductKind:productKind_code_2_des[app.productKind_code],//产品种类 yield等
			productDate:app.productDate,//选择的产品日期
			areaCode:app.areaCode,//选择的产品行政区域
			areaName:app.areaName,//选择的产品行政区域
	    	reprotType:reprotType,//报告格式 doc,pdf
	    	mapPicUrl:mapPicUrl,//地图url
	    	legendPic_str:legendPic_str,//图例url
	    	staPic_str:staPic_str,//统计图url
	    	staData:JSON.stringify(app.staData)//统计数据
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
	    	//window.location.href=data.reportUrl;
	    	//alert(data.reportUrl);
	    	window.open(data.reportUrl,"_blank");
	    	//$('#staChartModal').modal('hide');
	    	$('#reportModal').modal();
	    	
	    	$('#downLoadReportBtn').click(function(e) {
	    		
	    		//window.open(data.reportUrl,"_blank");
	    		$('#reportModal').modal('hide');
	    		
	        });
//	        console.log(data)
//	        console.log(textStatus)
//	        console.log(jqXHR)
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
function initTimeSelect_indexHtml(provinceCode)
{
//	alert(provinceCode);
//	$(".BbAreaSelect").style.display="display";
	var yearSelect = document.getElementById("year");
//	yearSelect.options.length=0;
//	var monthSelect = document.getElementById("month");
//	monthSelect.options.length=0;  
//	var daySelect = document.getElementById("day");
//	daySelect.options.length=0;  
	 $("#year").html("<option value='year' >Year</option>");
	 $("#month").html("<option value='month' >Month</option>");
	 $("#day").html("<option value='day' >Day</option>");
	 if(!provinceCode||!app.productKind_code)
	 {
		 return false;
	 }
	$.ajax({
	    url:'/jf/thairice/t7pdt_data/get_pdt_data_year',
	    type:'POST', //GET
	    async:true,    //或false,是否异步
	    data:{
	    	provinceCode:provinceCode,//行政代码
	    	productKind_code:app.productKind_code//产品类别
	    },
	    timeout:5000,    //超时时间
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    beforeSend:function(xhr){
	        //console.log(xhr)
	        console.log('发送前')
	    },
	    success:function(data,textStatus,jqXHR){
//	    	console.log(data);
	    	if(data.length>0){
	    		$('#legendinfo').show();
//	    		var yearSelect = document.getElementById("year");
	    		if(yearSelect)
    			{
//	    			var op = new Option("Year","Year");  
//					yearSelect.options.add(op);  
		    		for(var i =0;i<data.length;i++)
		    		{
		    			//循环将数组中的数据写入<option>标记中
		    			var op = new Option(data[i]["year"],data[i]["year"]);  
    					yearSelect.options.add(op);  
	    			}
    			}
	    	}
	    	else{
//	    		alert("no data00");
	    		$('#legendinfo').hide();
	    		$('.StaButton').hide();
	    		$('#systemTipsModal').modal();
		    	
		    	$('#systemTipsModalBtn').click(function(e) {
		    		
		    		//window.open(data.reportUrl,"_blank");
		    		$('#systemTipsModal').modal('hide');
		    		
		        });
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
function inityearIndex(type)
{
	if(type == "year")
	{
//		var monthSelect = document.getElementById("month");
//		monthSelect.options.length=0;  
//		var daySelect = document.getElementById("day");
//		daySelect.options.length=0;  
		
		var year = $('#year option:selected').val();//选中的year
		 $("#month").html("<option value='month' >Month</option>");
		 $("#day").html("<option value='day' >Day</option>");
//		alert(year);
		if(year=="year")
		{
			return false;
		}
		$.ajax({
		    url:'/jf/thairice/t7pdt_data/get_pdt_data_year_month',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	provinceCode:app.province_code,
		    	productKind_code:app.productKind_code,//产品类别
		    	year:year//用户ID
		    	
		    },
		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        //console.log(xhr)
		        console.log('发送前')
		    },
		    success:function(data,textStatus,jqXHR){
//		    	console.log(data);
		    	if(data.length>0){
		    		var monthSelect = document.getElementById("month");
		    		if(monthSelect)
	    			{
//		    			var op = new Option("Year","Year");  
//						yearSelect.options.add(op);  
			    		for(var i =0;i<data.length;i++)
			    		{
			    			//循环将数组中的数据写入<option>标记中
			    			var month = data[i]["month"]<10?'0'+data[i]["month"]:data[i]["month"];
			    			var op = new Option(month,month);  
			    			monthSelect.options.add(op);  
		    			}
	    			}
		    	}
		    	else{
		    		$('#systemTipsModal').modal();
			    	
			    	$('#systemTipsModalBtn').click(function(e) {
			    		
			    		//window.open(data.reportUrl,"_blank");
			    		$('#systemTipsModal').modal('hide');
			    		
			        });
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
	else if(type == "month")//选中的month
	{
//		var daySelect = document.getElementById("day");
//		daySelect.options.length=0;  
		
		var year = $('#year option:selected').val();//选中的year
		var month = $('#month option:selected').val();//选中的year
		 $("#day").html("<option value='day' >Day</option>");
		if(month=="month")
		{
			return false;
		}	
//		alert(year);
		$.ajax({
		    url:'/jf/thairice/t7pdt_data/get_pdt_data_year_month_day',
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	provinceCode:app.province_code,
		    	productKind_code:app.productKind_code,//产品类别
		    	year:year,//year
		    	month:month//month
		    	
		    },
		    timeout:5000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    beforeSend:function(xhr){
		        //console.log(xhr)
		        console.log('发送前')
		    },
		    success:function(data,textStatus,jqXHR){
//		    	console.log(data);
		    	if(data.length>0){
		    		var daySelect = document.getElementById("day");
		    		if(daySelect)
	    			{
//		    			var op = new Option("Year","Year");  
//						yearSelect.options.add(op);  
			    		for(var i =0;i<data.length;i++)
			    		{
			    			//循环将数组中的数据写入<option>标记中
			    			var day = data[i]["day"]<10?'0'+data[i]["day"]:data[i]["day"];
			    			var op = new Option(day,day);  
			    			daySelect.options.add(op);  
		    			}
	    			}
		    	}
		    	else{
		    		$('#systemTipsModal').modal();
			    	
			    	$('#systemTipsModalBtn').click(function(e) {
			    		
			    		//window.open(data.reportUrl,"_blank");
			    		$('#systemTipsModal').modal('hide');
			    		
			        });
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
	else{//选中的day
		var day = $('#day option:selected').val();//选中的year
		if(day=="day")
		{
			return false;
		}
		else{
			var year = $('#year option:selected').val();//选中的year
			var month = $('#month option:selected').val();//选中的year
			var day = $('#day option:selected').val();//选中的year
			app.productDate = year+'-'+month+'-'+day+'';
			
			if(app.areaCode&&app.productDate&&app.productKind_code)
			{
				console.log(app.areaCode+","+app.areaName+","+app.productDate+","+app.productKind_code);
				AssembleProductLayerInfo(app.areaCode,app.productDate,app.productKind_code);
			}
//			alert(app.productDate);
			$('.StaButton').show();
		}
	}
}
//function print
/**********************************************************************************/
