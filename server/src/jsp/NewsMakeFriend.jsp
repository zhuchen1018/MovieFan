<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/News.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ page import="com.myapp.view.*" %>

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nmf");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String friend = nov.getToList().get(0);
	String friendurl = "userpage?user="+friend;
%>
      
	<p>
    	<img src="/images/not-found.png" class="user"></img>
    	<b class="name"><%=userName%></b>
    	&nbsp;
    	<b class="note"><%=note%></b>
    	<a class="link" href=<%=friendurl %>><%=friend %></a><br>
    </p>
    <p>
    	<sub><%=time %></sub>
    </p>


</body>
</html>