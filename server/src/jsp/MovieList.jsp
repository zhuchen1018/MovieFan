<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Moviepage</title>
</head>
<body>
<h1>Search Result</h1><br>
<%! int i=1; int j=3; %>
<%
while( j>0 ){
%>
<h4><%=i+"." %></h4>
<tr>
<td>movie_name</td><br>
<td>director</td><br>
<td>actor</td><br>
</tr>
<%
i++;
j--;
}
%>
 
</body>
</html>
