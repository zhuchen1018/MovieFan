<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Moviepage</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<body>
<h1>Search Result</h1><br>
<%! int i=1; int j=3; %>
<%@ page import="com.myapp.view.*" %>
<%MovieListView mlv = (MovieListView)request.getAttribute("MovieListView"); %>
<%

for(int i=0;i<mlv.getMovieNumber();++i){%>
	<td><%=mlv.getMovies().get(i).getName()%></td><br>
	<td><%=mlv.getMovies().get(i).getOverview()%></td><br>
<%
} 
%>





 
</body>
</html>
