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

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nlm");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String MovieComment = nov.getText();
	String movieid = nov.getMovieId();
%>
      
    <h3><%=userName%> </h3> &nbsd; <h4><%=note%></h4> <h4><%=movieid%></h4><br> 
    <td>MovieComment</td><br>  
    <h4><%=time %></h4><br><br>
    <br>



</body>
</html>