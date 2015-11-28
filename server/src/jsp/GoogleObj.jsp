<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "Rating.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Google Result Object</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>

<% GoogleObjectView gov = (GoogleObjectView) request.getSession().getAttribute("item");

      String title = gov.getTitle();
      String url = gov.getUrl();
      String directURL = url; 
%>
      
	<a href = <%=directURL %>><td><%=title%></td></a> &nbsp; </td><br>	 

</body>
</html>
