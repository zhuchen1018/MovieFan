<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/MovieObj.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movie Object</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>

<% MovieObjectView mov = (MovieObjectView) request.getSession().getAttribute("item");

      String po = mov.getPoster();
      double rating = mov.getRating();
      int votes = mov.getVotes();
      String releaseDate = mov.getReleaseDate();
      int movieLength = mov.getLength();
      String movieID = mov.getMovieId();
      String width = rating*10 + "%";    
      String directURL = "/moviepage?movie_id=";
      directURL = directURL.concat(movieID);
%>
      
    <%if(po!=null&&!po.equals("null")){ %>
		<a href = <%=directURL %>><img src=<%=po%> alt="Poster"></a><br>
	<% } else { %>
		<a href = <%=directURL %>><img src="/images/not-found.png" alt="Poster"></a><br>
	<%}%>
	<div class="info">
		<!--<a class="link" href = <%=directURL %>><b class="name"><%=mov.getName()%></b></a>-->
		<div style="width:auto;overflow:scroll;">
			<b class="name"><%=mov.getName()%></b>
		</div>
		<br>
		<b class="rating">Rating: <%=rating%> </b>
		<b class="vote">(<%=votes%> votes)<b>
		<br>
		<b class="releaseDate">Release Date: <%=releaseDate %>
		<br>
		<b class="runtime">Runtime: <%=movieLength %></td>
	</div>	
    <br><br>
   	<b class="overview">Overview: </b>
    <div class="overview"><p class="paragraph"><%=mov.getOverview()%></p> </div>

</body>
</html>
