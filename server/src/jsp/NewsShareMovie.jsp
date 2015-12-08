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
<%@ page import="java.util.*" %>
<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nsm");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String movieid = nov.getMovieId();
	String movieName = nov.getMovieName();
	String movieUrl = nov.getUrl();
	String moviePage="moviepage?movie_id="+movieid;
	ArrayList<String> ToList = nov.getToList();
	String userUrl = nov.getUserUrl();
	if(userUrl==null) userUrl="/images/not-found.png";
%>
      
      
    <p>
    	<img src=<%=userUrl%> class="user"></img>
    	<b class="name"><font size="5"><%=userName%></font></b>
    	&nbsp;
    	<b class="note"><font size="4"><%=note%></font></b>
    	<b style="float:right";><font size="5">To Friends</font></b>
    </p>
    <br>
    <p>
    	<sub><<font size="4"><%=time %></font></sub>
    </p>
    <!--<a class="link" href=<%=moviePage%>><%=movieName%></a><br>-->
    <br>
    
    <div class="movieImage">
	    <p>
    		<a href=<%=moviePage%>><img src=<%=movieUrl%> class="movie"></img></a>
    	</p>
    </div>
    
</body>
</html>