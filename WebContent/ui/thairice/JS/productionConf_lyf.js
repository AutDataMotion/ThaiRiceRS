/**
 * 
 */
var app = {};
function init_productionConf_Monitoring()
{

//    packages: [{
//      "name": "lyfModules",
//      "location": "/ui/thairice/JS/" + "lyfModules"
//    }]
	
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
			"<button type='button' onclick='startDraw($(this))'><i class='glyphicon glyphicon-edit'></i></button>"+
          	"<button type='button' onclick='saveDraw($(this))'><i class='glyphicon glyphicon-ok-circle'></i></button>"+
          	"<button type='button' onclick='deleteDraw($(this))'><i class='glyphicon glyphicon-remove-sign'></i></button>"
           
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
//        "lyfModules/customoperation",
        "dojo/_base/connect", "esri/Color", "dojo/parser", "dijit/registry",
	    "dojo/dom",
	    "dojo/on",
	    "esri/layers/ArcGISDynamicMapServiceLayer",
	    "esri/geometry/Extent",
	    "dojo/domReady!"], function (
	        Map,Scalebar,UndoManager,Draw, Graphic,
	        SimpleMarkerSymbol, SimpleLineSymbol,SimpleFillSymbol,GraphicsLayer,
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
function startDraw(tr)
{
	app.graphicsLayer.clear();
	app.toolbar.activate("polygon");
}
function saveDraw(tr)
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
		
//		var graphicArray = app.graphicsLayer.graphics;
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
//				  graphic.geometry = projection.project(graphic.geometry, outSpatialReference);
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
//			  console.log(geoJson);
//			  alert(JSON.stringify(geoJson));
			  $.ajax({
				    url:'/jf/thairice/t1parameter/generateShpfileByGeoJson',
				    type:'POST', //GET
				    async:true,    //或false,是否异步
				    data:{
				    	sampleName:sampleName,
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
//				    		alert("save success!");
				    		$("#hintContent").text("save success!");
				    		$("#hintModal").modal('show');
				    		app.undoManager.clearRedo();
				    		app.undoManager.clearUndo();
				    		app.graphicsLayer.clear();
			    		
				    		
				    		
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
	
}
function deleteDraw(tr)
{
	
	app.sample_excel.row( tr.parents('tr') ).remove().draw();
	
	app.undoManager.clearRedo();
	app.undoManager.clearUndo();
	app.graphicsLayer.clear();
	
}