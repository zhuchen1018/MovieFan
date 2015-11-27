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

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nt");
	String userName = nov.getUsername();
	String tweetContent = nov.getText();
	String tweetTime = nov.getReleaseTime();
	String note = nov.getNote();

%>
      
    <h3><%=userName%> </h3> &nbsd; <h4><%=note%></h4><br>   
    <h4><%=tweetTime %></h4><br><br>
    <td><%=tweetContent %></td>
    <br>

</body>
</html>