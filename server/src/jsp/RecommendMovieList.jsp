<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/SimpleMovieList.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">

</head>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="com.myapp.servlet.*" %>
<%@ page import="com.myapp.view.*" %>

<body>

<% 
	ArrayList<String> url=(ArrayList<String>)request.getAttribute("RecommendPosters");
	ArrayList<String> id=(ArrayList<String>)request.getAttribute("RecommendMovieIds");
%>

<%
	if(url==null||url.size()==0){%>
		<h2 id="note">No Recommend</h2>
	<%}
	else{%>
		<h2 id="note">You May Like</h2>
		<div class="list">
		<%
			for(int i=0;i<url.size();++i){%>
				<div class="image">
					<a href=<%="moviepage?movie_id="+id.get(i)%>><img class="simpleImage" src=<%=url.get(i)%>></img></a>
				</div>
				<br>
			<%
			}
		%>
		</div>
	<%
	}
%>



</body>
</html>

