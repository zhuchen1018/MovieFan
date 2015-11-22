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

      String poster = mov.getPoster();
      double rating = mov.getRating();
      String ratingStar = rating*10 + "%";
      String releaseDate = mov.getReleaseDate().substring(0,4);
%> 
	<a href = "/jsp/MoviePage.jsp"><td><%=mov.getName()%></td></a> (<%=releaseDate%>)<br>
	<td>Overview: <%=mov.getOverview()%> </td><br> 	 
    <td>Rating: <%=rating%> (<%=mov.getVotes()%> voted)</td>
    <td><div class="rating-box"> 
        <div style="width:<%=ratingStar%>" class="rating"></div> 
        </div></td><br> 

    <%if(!poster.equals("null")){ %>
		<a href = "/jsp/MoviePage.jsp"><img src=<%=poster%> alt="Poster" style="width:200px;height:160px;"></a><br>
	<% 
	} 
    else { %>
		<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"><br>
	<% 
	} 
	%> <br>
	
</body>
</html>