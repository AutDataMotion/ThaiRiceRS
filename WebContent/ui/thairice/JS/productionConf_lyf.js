/**
 * 
 */
var app = {};
function init_productionConf_Monitoring()
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
	    app.map = new Map("mapDiv_productionConf", {
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

	});
	
}
