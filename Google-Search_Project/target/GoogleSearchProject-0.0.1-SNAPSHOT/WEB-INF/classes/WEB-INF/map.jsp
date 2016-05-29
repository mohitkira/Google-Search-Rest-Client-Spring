<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<title>Map Details</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
body {
	width: 100%;
}

#map-canvas {
	height: 600px;
	width: 900px;
	margin: 0px;
	padding: 0px
}

#list {
	height: 100%;
	overflow: scroll;
}
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?"></script>

<script type="text/javascript">
        var latitude, longitude;
       
            latitude = [
            	<c:forEach var="s" items="${locationbo}" varStatus="status">
                	<c:out value="${s.lat}"/>,
            	</c:forEach>];
           longitude = [
               <c:forEach var="s" items="${locationbo}" varStatus="status">
             	 <c:out value="${s.lng}"/>,
               </c:forEach>];

        function initialize() {
            var map;
            var initlatlng = new google.maps.LatLng(latitude[0],longitude[0]);
            var mapOptions = {
                zoom: 4,
                center: new google.maps.LatLng(37.09024, -95.712891),
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
            var infowindow = new google.maps.InfoWindow(); 
            var marker, i;

            for (i = 0; i < latitude.length; i++) {
                marker = new google.maps.Marker({
                    position: new google.maps.LatLng(latitude[i], longitude[i]),
                    map: map
                });

                google.maps.event.addListener(marker, 'click', (function(marker, i) {
                    return function() {
                        infowindow.setContent(markers[i]);
                        infowindow.open(map, marker);
                    }
                })(marker, i));
            }
        }

        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>

<body>

	<div class="table-responsive">
		<table border="1px solid" style="color: black;" class="table">
			<tr>
				<th><center>Map</center></th>
				<th><center>Address</center></th>
			</tr>
			<tr>
				<td>
					<div id="map-canvas"></div>
				</td>
				<td valign="top">
					<div id="list">
						<c:if test="${ locationbo == null || locationbo.size() == 0 }">
							<br />
							<b><font color=red> No records found with Search
									Criteria</font></b>
						</c:if>
						<c:if test="${ locationbo != null && locationbo.size() > 0 }">

							<table border="1px solid black" width="100%" cellspacing="0"
								cellpadding="0">

								<tbody>
									</c:if>
									<c:forEach items="${locationbo}" var="adress">
										<tr>
											<td><center>${adress.address}</center></td>

										</tr>
									</c:forEach>
									<c:if test="${ locationbo != null && locationbo.size() > 0 }">
								</tbody>
							</table>
						</c:if>
					</div>
				</td>
			</tr>
		</table>

	</div>
</body>
</html>
