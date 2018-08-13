// JavaScript Document
var app = {};
app.areaCode  ='72';//test
app.staField  ='value';//test
app.legendDiv = "#legendinfo";
app.staChartDiv = "#stainfo";
app.userID = '0';
app.staExcelInit = false;
//app.Thai_shp_url =  hostIP+":6080/arcgis/rest/services/Thailand_shp/MapServer/0"
app.Thai_shp_url =  hostIP+":6080/arcgis/rest/services/Thailand_shp/MapServer/"
$(function(){
//	alert(hostIP);
	initapp();
	
	$('.list-group-item-text').mouseover(function(e) {
        //$(this).addClass('active').siblings().removeClass('active');
    });
	$('.list-group-item-text').mouseout(function(e) {
        //$(this).removeClass('active');
    });
	var productKind_code="";
	$('.list-group-item-text').click(function(e) {
//		console.log(app.province_code);
//		console.log(app.city_code);
//		console.log(app.town_code);
		if(app.town_code!=0)
		{
			app.areaCode = app.town_code;
			AddAreaLayer(1,app.areaCode);
			
		}
		else if(app.city_code!=0)
		{
			app.areaCode = app.city_code;
			AddAreaLayer(3,app.areaCode);
			
		}
		else if(app.province_code!=0)
		{
			app.areaCode = app.province_code;
			AddAreaLayer(2,app.areaCode);
			
		}
		else{
			alert("area null");
		}
//		console.log(app.areaCode);
		//whichSelected = $(this).text();
		productKind_code = $(this).attr("value");
		app.productKind_code = productKind_code;
		if(app.areaCode&&app.productDate)
		{
			console.log(app.areaCode+","+app.productDate);
			AssembleProductLayerInfo(app.areaCode,app.productDate,app.productKind_code);
		}
		
		/*
		if(whichSelected!=$(this).text())
		{
			whichSelected = $(this).text();
			$('.StaButton').show();
		}
		else{
			
			$('.StaButton').hide();
		}*/
		//console.log($(this).text());
//        $('.BbProductMenu').hide();

		$(this).addClass('active').siblings().removeClass('active');
		//$('.StaButton').toggle('normal');
		$('.StaButton').show();
    });
	
	$('.staChartButton').click(function(e) {//生成统计图
		if(app.featureLayer)
		{
			$('.StaButton').toggle('normal');
			$('#staChartModal').modal();
	        //$('.BbReportcons .BbReportRight').fadeIn();
//			getCountry("72",app.featureLayer,"value");//统计
			getCountry(app.areaCode,app.featureLayer,app.staField);//统计
			printMap();//获取mapPicUrl 生成map缩略图
		}
		
		
    });
	$('#staChartModal .close').click(function(e) {
		
        $('#staChartModal').modal('hide');
		
    });
	$('#staChartModal .cancel_btn').click(function(e) {
        $('#staChartModal').modal('hide');
		
    });
	
	$('#staChartModal .GenerateReportbtnDOC').click(function(e) {//生成报告 doc
		if(app.productKind_code=='01')//area
		{
			generateReport("doc",app.legendDiv,app.staChartDiv);
		}
		if(app.productKind_code=='02')//growth
		{
			generateReport("doc",app.legendDiv,app.staChartDiv);
		}	
		if(app.productKind_code=='03')//yield
		{
			//generateReport("doc","#legendinfo","#stainfo");
			generateReport("doc",app.legendDiv,app.staChartDiv);
		}
		if(app.productKind_code=='04')//drought
		{
			
			generateReport("doc",app.legendDiv,app.staChartDiv);
		}
        //$('#staChartModal').modal('hide');
		
    });
	$('#staChartModal .GenerateReportbtnPDF').click(function(e) {//生成报告 pdf
		if(app.productKind_code=='01')//area
		{
			generateReport("pdf",app.legendDiv,app.staChartDiv);
		}
		if(app.productKind_code=='02')//growth
		{
			generateReport("pdf",app.legendDiv,app.staChartDiv);
		}	
		if(app.productKind_code=='03')//yield
		{
			//generateReport("pdf","#legendinfo","#stainfo");
			generateReport("pdf",app.legendDiv,app.staChartDiv);
		}
		if(app.productKind_code=='04')//drought
		{
			
			generateReport("pdf",app.legendDiv,app.staChartDiv);
		}
		
		
        //$('#staChartModal').modal('hide');
		
    });
	/**********************************************************************************/
	/*
	$('.BbProductabs').click(function(e) {
        $(this).hide().siblings().show();
    });
	$('.generate_cons button.close').click(function(e) {
    	$('.BbReportGenerate').hide();
		$('.BbReportcons').show();
		$('.BbReportcons .BbReportLeft').show();
		$('.BbReportcons .BbReportRight').show();
    });
	$('.modal-footer .btn').click(function(e) {
        $('.modal').hide();
		$('.BbReportGenerate').hide();
		$('.BbReportcons').hide();
		$('.BbReportRight').hide();
		$('.progress').hide();
		$('.BbReportWrap').hide();
    });
	$('.download_btn').click(function(e) {
        $('.BbReportWrap').hide();
    });
	$('.report_downbtn').click(function(e) {
        $('#reportModal').modal();
    });
	*/
})

function initapp()
{
	require(["esri/map",
		"esri/dijit/Scalebar",
	    "dojo/dom",
	    "dojo/on",
	    "dojo/parser",
	    "esri/urlUtils",
	    "esri/config",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/layers/FeatureLayer",
	    "esri/symbols/SimpleFillSymbol", "esri/symbols/SimpleLineSymbol","esri/Color", "esri/renderers/SimpleRenderer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,dom,on, parser,urlUtils,esriConfig,ArcGISDynamicMapServiceLayer,FeatureLayer,SimpleFillSymbol, SimpleLineSymbol,Color, SimpleRenderer,Extent) {
	    //var map = new Map("mapDiv");
	    
//		var layer1 = new ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer");
	    //app.map.addLayer(layer1);
//	    var initExtent = layer1.fullExtent;
//	    console.log(layer1.fullExtent);
		parser.parse();
//		urlUtils.addProxyRule({
//	          urlPrefix: hostIP+":6080",
//	          proxyUrl: hostIP+":8080/Java/proxy.jsp"
//	        });
		esriConfig.defaults.io.proxyUrl = hostIP+":8080/Java/proxy.jsp";
		esriConfig.defaults.io.alwaysUseProxy = false;
		
	    app.map = new Map("mapDiv", {
	    	basemap: "satellite",
	    	extent: new Extent({xmin:97.34370447600008,ymin:5.612765642000056,xmax:105.63700411400004,ymax:20.46490708700003,spatialReference:{wkid:4326}}),
	        logo:false
	      });
	    
	    // Carbon storage of trees in Warren Wilson College.
	    var featureLayer = new FeatureLayer(app.Thai_shp_url+"0");//泰国的国界图层
		
	    var symbol = new SimpleFillSymbol()
	    .setColor(new Color([255,0,0,0]))
	    .setOutline(new SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new Color([255,215,0]), 2)); ;
	    var renderer = new SimpleRenderer(symbol);

	    featureLayer.setRenderer(renderer);
	    
	    app.map.addLayer(featureLayer);
	    
	    var scalebar = new Scalebar({
	          map: app.map,
	          // "dual" displays both miles and kilometers
	          // "english" is the default, which displays miles
	          // use "metric" for kilometers
	          scalebarUnit: "metric"
	       });
//	    app.map.on("load", function(theMap){
//	    	app.map.setExtent(layer1.fullExtent);
//	    });
	    /*
	    on(dom.byId("btn_addLayer"),"click",function(e){
	    	
	    	addProductLayer();
	    	
	    });
	    on(dom.byId("btn_staData"),"click",function(e){
	   	 //test
	   		getCountry("72",app.featureLayer,"value");
	   });
	    
	    on(dom.byId("btn_preview"),"click",function(e){
	      	 
	    	preview();
	    	
	      	
	      });
	    on(dom.byId("btn_report_doc"),"click",function(e){
	      	
	    	generateReport("doc","#legendinfo","#stainfo");
	    	
	      	
	      });
	    on(dom.byId("btn_report_pdf"),"click",function(e){
	      	 
	    	generateReport("pdf","#legendinfo","#stainfo");
	    	
	      	
	      });
	    */
	});
	//初始化统计表格
	 
	 app.staChart = echarts.init(document.getElementById('staDiv'));
}
function AddAreaLayer(index,code)//添加行政区域边界图层
{
	require(["esri/map",
		"esri/dijit/Scalebar",
	    "dojo/dom",
	    "dojo/on",
	    "dojo/parser",
	    "esri/urlUtils",
	    "esri/config",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/layers/FeatureLayer",
	    "esri/tasks/query",
	    "esri/symbols/SimpleFillSymbol", "esri/symbols/SimpleLineSymbol","esri/Color", "esri/renderers/SimpleRenderer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,dom,on, parser,urlUtils,esriConfig,ArcGISDynamicMapServiceLayer,FeatureLayer,Query,SimpleFillSymbol, SimpleLineSymbol,Color, SimpleRenderer,Extent) {
	    
		parser.parse();

		esriConfig.defaults.io.proxyUrl = hostIP+":8080/Java/proxy.jsp";
		esriConfig.defaults.io.alwaysUseProxy = false;
		
		if (app.hasOwnProperty("AreaBoundLayer") ) {//删除之前的图层
			 
			 app.map.removeLayer(app.AreaBoundLayer);
			 
		}
	    // Carbon storage of trees in Warren Wilson College.
		app.AreaBoundLayer = new FeatureLayer(app.Thai_shp_url+index, {
	        mode: FeatureLayer.MODE_ONDEMAND,
	        //outFields: ["value"],
	        outFields: ["CODE"],
	       
	        definitionExpression:"CODE="+code
	    });
		
		var symbol = new SimpleFillSymbol()
	    .setColor(new Color([255,0,0,0]))
	    .setOutline(new SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new Color([255,215,0]), 2)); ;
	    var renderer = new SimpleRenderer(symbol);

	    app.map.addLayer(app.AreaBoundLayer);
	    
		app.AreaBoundLayer.setRenderer(renderer);
		
//	  	console.log(app.AreaBoundLayer);
	   
		var count = 5;//如果发生错误，尝试执行5次
		//缩放到指定 区域
	    var queryParams = new Query();
	    queryParams.outFields = ['CODE'];
	    //queryParams.outStatistics = [ minStatDef, maxStatDef];
	    queryParams.where = "CODE="+code;
	    queryParams.returnGeometry = true;
	    app.AreaBoundLayer.queryFeatures(queryParams, getStats, geterror);
	    
	    function getStats(results){
//	    	 console.log("12");
	    	 var feature = results.features[0];
	    	 console.log(feature);
	    	 app.map.setExtent(feature.geometry.getExtent());
	    }
	    
	    function geterror(error)
	    {
//	    	console.log(error);
	    	count--;
	    	if(count>0)
	    	{
//	    		console.log(count);
	    		app.AreaBoundLayer.queryFeatures(queryParams, getStats, geterror);
	    	}
	    	else{
	    		console.log(error);
	    	}
	    	
	    }

	});
	
}
