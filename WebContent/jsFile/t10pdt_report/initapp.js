/**
 * author:lyf
 */
//one global for persistent app variables
var app = {};
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
	      	/*
	      	 * doc word
	      	 * #legendinfo legend
	      	 * #stainfo sta
	      	 */
	    	generateReport("doc","#legendinfo","#stainfo");
	    	
	      	
	      });
	    on(dom.byId("btn_report_pdf"),"click",function(e){
	      	 
	    	generateReport("pdf","#legendinfo","#stainfo");
	    	
	      	
	      });
	});
}
