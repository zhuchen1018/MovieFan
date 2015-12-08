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

<% UserInfoView uiv = (UserInfoView)request.getAttribute("UserInfoView");
	ArrayList<String> url=uiv.getPosterUrl();
	ArrayList<String> id=uiv.getMovieId();
%>

<%
	if(url.size()==0){%>
		<h2 id="note">No Like List</h2>
	<%}
	else{%>
		<h2 id="note">Movie Liked</h2>
		<div class="list">
		<%
			for(int i=0;i<url.size();++i){%>
				<div class="image">
					<a href=<%="moviepage?movie_id="+id.get(i)%>><img src=<%=url.get(i)%>></img></a>
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

