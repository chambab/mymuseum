<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<!-- script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script -->
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script> 
<style type="text/css">
        #map {
                width: 100%;
                height: 400px;
                border-bottom-color:#eeeeee;
                border-bottom-width:1px;
        }
        .st {
                font-family: verdana;
                font-size:9pt;
                border-bottom-style:solid;
                border-color: red;
        }
        .stf {
                font-family: verdana;
                font-size:9pt;
        }

</style>
<title>Register User</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript">
var markersArray = [];
var map;

 	window.onload = function(){
		
		initialize();
/* 		var mapE = document.getElementById("map");
		map = new google.maps.Map(mapE, {			
			//mapTypeId: google.maps.MapTypeId.ROADMAP,
			mapTypeId: google.maps.MapTypeId.TERRAIN,
			zoom: 12
		});	 */
				
		fnRequestMuseumList();
		
	}; 
	
	function addMarker(location) {
		  marker = new google.maps.Marker({
		    position: location,
		    map: map
		  });
		  markersArray.push(marker);
		}
	function initialize(){			
		  var haightAshbury = new google.maps.LatLng(37.7699298, -122.4469157);
		  var mapOptions = {
		    zoom: 16,
		    center: haightAshbury,
		    //mapTypeId: google.maps.MapTypeId.TERRAIN
		    mapTypeId: google.maps.MapTypeId.ROADMAP,
		  };
		  map =  new google.maps.Map(document.getElementById("map"), mapOptions);
		  
		  google.maps.event.addListener(map, 'click', function(event){
			  addMarker(event.latLng);
		  });			  
	};
	function success(position){
		//
		//	Google Map 연동
		var mapCenter = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		var mapE = document.getElementById("map");
		map = new google.maps.Map(mapE, {			
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			//mapTypeId: google.maps.MapTypeId.TERRAIN,
			center: mapCenter,
			zoom: 12
		});		
	};
	function fail(error){
		alert("fail");
	};	
	function fnRequestMuseumList(){
		
		var userId = window.localStorage.getItem("userId");		
		var uri = "<%=request.getContextPath()%>/mm/1/msg/" + userId + "/MyMuseumList.json";
		
 		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				var obj = JSON.parse(xhr.responseText); 
				//fnPositionMuseum(obj[1].latitude, obj[1].longitude);
				fnPositionMuseumAll(obj);
			};
		};
				
		xhr.open("GET", uri);	
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(null); 
		
				
	};
	
	function fnPositionMuseum(lati, longi){
			// 현재 위치로 맵을 중앙에 위치함
			var mapCenter = new google.maps.LatLng(lati, longi);
			map.setCenter(mapCenter);
			
			var marker = new google.maps.Marker({
				position: mapCenter,
				title: "현재위치",
				map: map
			});			
	};
	function fnPositionMuseumAll(obj){
		
/* 		for(var i=0; i < obj.length; i++) {
		  var haightAshbury = new google.maps.LatLng(obj[i].latitude, obj[i].longitude);
		  var mapOptions = {
		    zoom: 12,
		    center: haightAshbury,
		    //mapTypeId: google.maps.MapTypeId.TERRAIN
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  map =  new google.maps.Map(document.getElementById("map"), mapOptions);
		  
			var marker = new google.maps.Marker({
				position: mapCenter,
				title: obj[i].userNm,
				map: map
			});		  
		} */
		 
 		for(var i=0; i < obj.length; i++) {
			var mapCenter = new google.maps.LatLng(obj[i].latitude, obj[i].longitude);
/* 		    var mapOptions = {
				    zoom: 12,
				    center: mapCenter,
				    //mapTypeId: google.maps.MapTypeId.TERRAIN
				    mapTypeId: google.maps.MapTypeId.ROADMAP
				  }; */
		    map.setCenter(mapCenter);
		    //map =  new google.maps.Map(document.getElementById("map"), mapOptions);		    
			var marker = new google.maps.Marker({
				position: mapCenter,
				title: obj[i].userNm,
				map: map
			});
		} 
	};
	function fail(error){
		log("fail to find location ! " + error.code);
		log("Error : " + error.message);
	};
	function fnGetAddress(){
		
		jQuery.ajax({
			dataType: 'json',
			url: 'http://maps.google.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=true',
			cache: false,
			success: function(data) {
				alert(data);
			}
		});	
	};	
</script>
</head>
<body>
	<div id="map"></div>
</body>
</html>