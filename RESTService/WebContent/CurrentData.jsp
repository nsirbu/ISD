<%@page import="java.util.*" %>
<%@page import="isd.database.DBQuery"%>
<%@page import="isd.model.Message"%>
<jsp:useBean id="sensors" class="isd.model.Message"/>
<table class="tableStyle">
	<tr bgcolor="#f1f1f1" >
		<td><strong>Heartbeat</strong></td>
		<td><strong>TimeReceived</strong></td>
		<td><strong>LightSensorVal</strong></td>
		<td><strong>PirSensorVal</strong></td>
	</tr>
	<%
		Message message = DBQuery.getLastEntry(); //Get the latest state of sensors
	%>
	 <!-- Set the properties in bean -->
	<jsp:setProperty name="sensors" property="heartbeat"  value="<%=message.isHeartbeat()%>"/>
	<jsp:setProperty name="sensors" property="timeReceived"  value="<%=message.getTimeReceived().substring(0,19)%>"/>
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