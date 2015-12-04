<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "Rating.css">
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
      
	<a href = <%=directURL %>><td><%=mov.getName()%></td></a> &nbsp; <td>(<%=votes%> votes) &nbsp; Release Date: <%=releaseDate %> &nbsp; Runtime: <%=movieLength %></td><br>	
    <td>Overview: <%=mov.getOverview()%> </td><br>   
    <td>Rating: <%=rating%> 
      <div class="rating-box"> 
        <div style="width:<%=width%>" class="rating"></div> 
      </div>
    </td><br>
    <%if(!po.equals("null")){ %>
		<a href = <%=directURL %>><img src=<%=po%> alt="Poster" style="width:200px;height:160px;"></a><br>
	<% } else { %>
		<a href = <%=directURL %>><img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"></a><br>
	<%}%>
     <br>

</body>
</html>
