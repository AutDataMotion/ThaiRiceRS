<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
<title></title>  
<style type="text/css">  
body {  
     
    font-family: Arial Unicode MS;  
    font-size: 10px; 
    text-align: center;	
}  
   
.testTable {  
    margin: auto;  
    width: 100%;  
    border-collapse: collapse;  
    border: 1px solid #444444;  
} 

 
.reportDesTable{
	margin: auto;  
    width: 680px;
	font-family: Arial Unicode MS;  
    font-size: 20px;  
	text-align: center;
	valign:middle;
	border:1;
	border-collapse: collapse;  
}
/*
th,td {  
    border: 1px solid #444444;  
    font-size: 10px;  
    margin-left: 5px;  
}  
*/
.mcContent {  
    line-height: 180%;  
    padding: 20px;  
}  
   
.logo {  
    text-align: center;  
}  
   
.title {  
    text-align: center;  
    font-weight: bold;  
    font-size: 20px;  
}  
   
.notes {  
    font-weight: normal;  
    margin-left: 5px;  
    margin-right: 5px;  
    line-height: 18px;  
}  
   
.text_content {  
    margin-left: 5px;  
    margin-right: 5px;  
    line-height: 18px;  
}  
   
.sum_insured_first_row {  
    width: 20%;  
}  
   
.sum_insured_span {  
    font-size: 10px;  
}  
   
.special_agreements_div {  
    page-break-before: always;  
    font-size: 14px;  
    margin-top: 20px;  
}  
   
.special_agreements_div .special_agreements {  
    font-size: 18px;  
    font-weight: bold;  
}  
   
.title_right {  
    width: 100%;  
    margin: 0 auto;  
}  
   
.title_right p {  
    text-align: left;  
    margin: 0;  
    margin-left: 50%;  
    padding: 0;  
}  
   
@page {  
    size: 8.5in 11in;  
    @  
    bottom-center  
    {  
    content  
    :  
    "page "  
    counter(  
    page  
    )  
    " of  "  
    counter(  
    pages  
    );  
}  
   
.signature {  
    margin: 0 auto;  
    clear: both;  
    font-size: 16px;  
    font-weight: bold;  
}  
   
.signature_table {  
/*     font-size: 16px; */  
    font-weight: bold;  
}  
   
</style>  
</head>  
<body>  
    
    <div style="margin-left:auto;margin-right:auto;text-align: center;">  
        <h1 align="center">${titleKind} Report</h1>  
        <table class="reportDesTable" border="1">
			<tr>
				<td colspan="1">District</td>  
				<td colspan="2">${District}</td>
				<td colspan="1">Time</td>
				<td colspan="2">${Time}</td>				
            </tr> 
			<tr style="background:gray;">
				<td colspan="2">ID</td>  
				<td colspan="2">ChildAreaName</td>
				<td colspan="2">Acreage(m2)</td>			
            </tr>
			
			<#list Arealist as area>
			<tr>
				<td colspan="2">${area.code}</td>  
				<td colspan="2">${area.name}</td>
				<td colspan="2">${area.value}</td>			
            </tr>
			</#list> 
			
			<tr>
				<td colspan="6">
					<div class="logo"><!--这里的图片使用相对与ITextRenderer.getSharedContext().setBaseURL("file:/"+imageDiskPath);的路径-->  
						<img src="${staPic}" height="245" width="690" />  
					</div> 
				</td>		
            </tr>
		</table>
		<hr/>
		<h1 align="center">Spatial Distribution Map</h1>  
		<table class="reportDesTable" border="1">
			<tr>
				<td colspan="6">
					<div class="logo"><!--这里的图片使用相对与ITextRenderer.getSharedContext().setBaseURL("file:/"+imageDiskPath);的路径-->  
						<img src="${mapPic}" height="400" width="580" />  
					</div> 
				</td>		
            </tr>
			<tr>
				<td colspan="2">Legend</td>  
				<td colspan="4">
					<div class="logo"><!--这里的图片使用相对与ITextRenderer.getSharedContext().setBaseURL("file:/"+imageDiskPath);的路径-->  
						<img src="${legendPic}" height="173" width="150" />  
					</div>
				</td>	
            </tr>
		</table>		
        
    </div>  
</body>  
</html>  