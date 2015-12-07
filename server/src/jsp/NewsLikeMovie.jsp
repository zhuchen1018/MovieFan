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

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nlm");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String movieid = nov.getMovieId();
	String movieName = nov.getMovieName();
	String movieUrl = nov.getUrl();
	String moviePage="moviepage?movie_id="+movieid;
	String userUrl = nov.getUserUrl();
	if(userUrl==null) userUrl="/images/not-found.png";
%>
      
      
    <p>
    	<img src=<%=userUrl%> class="user"></img>
    	<b class="name"><%=userName%></b>
    	&nbsp;
    	<b class="note"><%=note%></b>
    </p>
    <br>
    <p>
    	<sub><%=time %></sub>
    </p>
    
    <a class="link" href=<%=moviePage%>><%=movieName%></a><br>
    <p>
    	<a href=<%=moviePage%><img src=<%=movieUrl%> class="movie"></img></a>
    </p>


</body>
</html>