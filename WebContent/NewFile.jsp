<%@page import="java.text.*"%>
<%@page import="org.apache.commons.net.ntp.TimeInfo"%>
<%@page import="java.net.InetAddress"%>
<%@page import="org.apache.commons.net.ntp.NTPUDPClient"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
                   			<%  Date date=new Date();
			                   	String TIME_SERVER = "0.in.pool.ntp.org";   
			                   	NTPUDPClient timeClient = new NTPUDPClient();
			                   	InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
			                   	TimeInfo timeInfo = timeClient.getTime(inetAddress);
			                   	long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
			                   	Timestamp time=new Timestamp(returnTime);
			                   	System.out.println(time);%>

</body>
</html>