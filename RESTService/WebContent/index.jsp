<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="isd.database.DBQuery"%>
<%@page import="isd.model.Message"%>
<% response.setIntHeader("Refresh", 60);%>    <!-- Refresh page every 60 seconds-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ultima stare a senzorilor</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" /> 
</head>
<body>
<jsp:useBean id="sensors" class="isd.model.Message"/>

<div class="alignCenter"><h3>Ultima stare a senzorilor</h3></div>

	<table align="center" cellpadding="5" class="tableStyle">
		<tr bgcolor="#f1f1f1" >
			<td><strong>Heartbeat</strong></td>
			<td><strong>TimeReceived</strong></td>
			<td><strong>LightSensorVal</strong></td>
			<td><strong>PirSensorVal</strong></td>
		</tr>
		<%
		Message message = new DBQuery().getLastEntry(); //Get the latest state of sensors
		%>
		 <!-- Set the properties in bean -->
		<jsp:setProperty name="sensors" property="heartbeat"  value="<%=message.isHeartbeat()%>"/>
		<jsp:setProperty name="sensors" property="timeReceived"  value="<%=message.getTimeReceived()%>"/>
		<jsp:setProperty name="sensors" property="lightSensorVal"  value="<%=message.getLightSensorVal()%>"/>
		<jsp:setProperty name="sensors" property="pirSensorVal"  value="<%=message.getPirSensorVal()%>"/>
		<tr>
			<td>
			 <!-- Get value from bean -->
			<jsp:getProperty name="sensors" property="heartbeat"/>
			</td>
			<td>
			<jsp:getProperty name="sensors" property="timeReceived"/>
			</td>
			<td>
			<jsp:getProperty name="sensors" property="lightSensorVal"/>
			</td>
			<td>
			<jsp:getProperty name="sensors" property="pirSensorVal"/>
			</td>
		</tr>
	</table>
</body>
</html>
