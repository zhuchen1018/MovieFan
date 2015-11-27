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
<%@ page import="java.util.*" %>
<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nsm");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String MovieComment = nov.getText();
	String movieid = nov.getMovieId();
	ArrayList<String> ToList = nov.getToList();
%>
      
    <h3><%=userName%> </h3> &nbsp; <h4><%=note%></h4> <h4><%=movieid%></h4> <td>to</td><br> 
    
    <%for(int i=0;i<ToList.size();i++) {%>
    <td><%=ToList.get(i)%></td><br>  
    <br>
<% 
}
%>
</body>
</html>