<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Person Object</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>

<% PersonObjectView pov = (PersonObjectView) request.getSession().getAttribute("person");
      String profile = pov.getProfile();
      String name = pov.getName();
      
%>
<%if(!profile.equals("null")){ %>
<td><%=name%> </td> &nbsp; <img src=<%=profile%> alt="Poster" style="width:80px;height:80px;">
<%}else{ %>
<td><%=name%> </td> &nbsp; <img src="/images/not-found.png" alt="Profile" style="width:80px;height:80px;">
<%} %>
</body>
</html>