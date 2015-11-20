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
	<%String po = mlv.getMovies().get(i).getPoster();%>
	<br>
	<td><%=i+1+"." %><td><br>
	<td><%=mlv.getMovies().get(i).getName()+" "+mlv.getMovies().get(i).getRating()%></td><br>
	<td><%=mlv.getMovies().get(i).getOverview()%></td><br>
	<%if(!po.equals("null")){ %>
		<img src=<%=po%> alt="Poster" style="width:200px;height:160px;"><br>
	<% } else { %>
		<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"><br>
	<%} %>
<%
} 
%>





 
</body>
</html>
