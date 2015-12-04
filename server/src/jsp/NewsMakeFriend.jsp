<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
      
    <h3><%=userName%> </h3> &nbsp; <h4><%=note%></h4> <a href=<%=friendurl %>><%=friend %></a><br>   
    <h4><%=time %></h4><br><br>
    <br>


</body>
</html>