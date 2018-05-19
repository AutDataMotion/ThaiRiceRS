// JavaScript Document
var app = {};
app.areaCode  ='72';//test
app.staField  ='value';//test
app.legendDiv = "#legendinfo";
app.staChartDiv = "#stainfo";
app.userID = '0';
app.staExcelInit = false;
$(function(){
	
	initapp();
	
	$('.list-group-item-text').mouseover(function(e) {
        //$(this).addClass('active').siblings().removeClass('active');
    });
	$('.list-group-item-text').mouseout(function(e) {
        //$(this).removeClass('active');
    });
	var productKind_code="";
	$('.list-group-item-text').click(function(e) {
		
		//whichSelected = $(this).text();
		productKind_code = $(this).attr("value");
		app.productKind_code = productKind_code;
		if(app.areaCode&&app.productDate)
		{
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
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,dom,on, ArcGISDynamicMapServiceLayer,Extent) {
	    //var map = new Map("mapDiv");
	    
//		var layer1 = new ArcGISDynamicMapServiceLayer("http://localhost:6080/arcgis/rest/services/tai_wgs84/MapServer");
	    //app.map.addLayer(layer1);
//	    var initExtent = layer1.fullExtent;
//	    console.log(layer1.fullExtent);
	    app.map = new Map("mapDiv", {
	    	basemap: "satellite",
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