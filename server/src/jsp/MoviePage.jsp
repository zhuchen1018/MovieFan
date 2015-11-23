<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "Rating.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>
<%MoviePageView mpv = (MoviePageView) request.getAttribute("MoviePageView"); 
    String overview, name, ratingStar, releaseYear, poster, homePage, director;
    double rating;
    int runTime, votes;
    ArrayList<String> trailers;
    
    overview = mpv.getOverview();
    rating = mpv.getRating();
    name = mpv.getName();
    ratingStar = rating*10 + "%";
    releaseYear = mpv.getReleaseDate().substring(0, 4);
    poster = mpv.getPoster();
    homePage = mpv.getHomepage();
    runTime = mpv.getLength();
    votes = mpv.getVotes();
    trailers = mpv.getYoutube_trailer();
    
%>
    <td> <%=name%> (<%=releaseYear%>)</td><br>
    <td> Runtime: <%=runTime%> (mins)</td><br>
    <td>Rating: <%=rating%> (<%=votes %> votes)</td>
    <td><div class="rating-box"> 
        <div style="width:<%=ratingStar%>" class="rating"></div> 
        </div>
    </td><br>
    <td> <%=overview%></td><br>
    <%if(!poster.equals("null")){ %>
		<a href = "/jsp/MoviePage.jsp"><img src=<%=poster%> alt="Poster" style="width:200px;height:160px;"></a><br>
	<% } else { %>
		<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"><br>
	<%} %> <br>
	<% int i = 1 ;%>
	<%for(String s : trailers) {
	   int index = s.indexOf("v=");
	   s = s.substring(index+2);
	   String url = "http://www.youtube.com/embed/" + s;
	 %>
    <iframe width ="200" height = "150"
	src = <%=url %>>
	</iframe><br>
	<td> <%=url%></td><br>
	<%
	}
	%>
		
    <td> <a href=<%=homePage%>>HomePage</a></td><br>
    
    
    
</body>
</html>
