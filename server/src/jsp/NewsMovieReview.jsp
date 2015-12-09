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

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nmr");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String MovieComment = nov.getText();
	String movieid = nov.getMovieId();
	String movieName = nov.getMovieName();
	String movieUrl = nov.getUrl();
	String moviePage="moviepage?movie_id="+movieid;
	String userUrl = nov.getUserUrl();
	if(userUrl==null) userUrl="/images/noprofile.jpg";
	String userLink="userpage?user="+userName;
%>
    
    <p>
    	<a href=<%=userLink%>><img src=<%=userUrl%> class="user"></img></a>
    	<a href=<%=userLink%>><b class="name"><font size="5" color="blue"><%=userName%></font></b></a>
    	&nbsp;
    	<b class="note"><font size="4"><%=note%></font></b>
    </p>
    <br>
    <p>
    	<sub><font size="4"><%=time %></font></sub>
    </p>
    <br>
    <!--<a class="link" href=<%=moviePage%>><font size="5"><%=movieName%></font></a><br>-->
    <div class="movieImage">
    	<p>
    		<a href=<%=moviePage%>><img src=<%=movieUrl%> class="movie"></img></a>
    	</p>
    </div>
    
    <br>
    
    <p class="paragraph">
    	<%=MovieComment%>
    </p>

</body>
</html>