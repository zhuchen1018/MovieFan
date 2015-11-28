<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Google Search Result</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<body>
<h1>Search Result</h1><br>
<%! int i=1; %>
<%@ page import="com.myapp.view.*" %>
<%GoogleListView glv = (GoogleListView)request.getAttribute("GoogleListView"); %>
<%

for(int i=0;i<glv.getCount();++i){%>
 <% GoogleObjectView gov = glv.getItem(i);
	if(gov != null) 
      request.getSession().setAttribute("item", gov);
    %>
    <jsp:include page="GoogleObj.jsp"/>	
<%
} 
%>
 
</body>
</html>
