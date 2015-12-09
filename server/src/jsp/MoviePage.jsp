<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MoviePage</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>
<jsp:include page="NavigationBar.jsp"/>
<%
MoviePageView mpv = (MoviePageView) request.getAttribute("MoviePageView"); 
    String overview, name, releaseYear, poster, homePage;
    double rating, ratingStar;
    int runTime, votes;
    ArrayList<String> trailers;
    PersonListView casts;
    PersonObjectView director;
    Boolean liked;
    
    overview = mpv.getOverview();
    rating = mpv.getRating();
    name = mpv.getName();
    ratingStar = (rating * 60)/10;
    releaseYear = mpv.getReleaseDate().substring(0, 4);
    poster = mpv.getPoster();
    homePage = mpv.getHomepage();
    runTime = mpv.getLength();
    votes = mpv.getVotes();
    trailers = mpv.getYoutube_trailer();
    director = mpv.getDirector();
    casts = mpv.getCast();
    liked= (Boolean) request.getAttribute("isLiked");

    %>
<h1><%=name%> (<%=releaseYear%>) </h1><br>    
<div id="container">
    <%if(!poster.equals("null")){ %>
		<div id="photo"><img src=<%=poster%> alt="Poster" 
		style="width:200px;height:300px"></div>
	<%
	} else { %>
		<img src="/images/not-found.png" alt="Poster" style="width:145px;height:300px;">
	<%} %> 
    <div id="content"><h3>Runtime: <%=runTime%> (mins)</h3></div><br>
    <div id="content"><h3>Rating: <%=rating%> (<%=votes %> votes)</h3></div>    
	    <div class="rating"> 
			<div class="stars">
				<div class="stars-in" style = "width : <%=ratingStar%>px" >	</div>
			</div>
		</div>	
    <br><br>
    <br><br>   
    <div id ="content"><h4>OverView </h4><td><%=overview%></td></div> <br>
    <div id = "content">
    <% if (!homePage.equals("null")) {%>
    <h4><a href=<%=homePage %> >HomePage</a></h4>
    <% 
    }
    %>   
    </div>

	<div id="content"> 
	    <% if(!liked){ %>
			<form action=<%="/likemovie" + "?" + request.getQueryString()%> method="POST">
				<button class="myButton" type="submit" name="LikeThisMovie">
				    Like
				</button>
			</form>
		<%
		}
		else { %>
			<form action=<%="/unlikemovie" + "?" + request.getQueryString()%> method="POST">
			<button class="myButton" type="submit" name="UnlikeThisMovie">
			    Unlike
			</button>
			</form>
		<% } %> 
	</div>
	
	<div id="content"> 	
 		<form action=<%="/sharemovie" + "?" + request.getQueryString()%> method="POST">
				<button class="myButton" type="submit" name="ShareThisMovie">
				    Share
				</button>
		</form>
	</div>
	
	<div id="content">
	<form action=<%="/moviereview"  + "?" + request.getQueryString()%> method="POST">
		<div align="left">
			<textarea cols="40" rows="5" name="Review" placeholder=""></textarea>
			<INPUT TYPE=SUBMIT VALUE="submit review">
		</div>
	</form>
	</div>

	<div id="content">
	    <h4>director:</h4><br>
	    <%request.getSession().setAttribute("person",director);%>
	    <jsp:include page="Person.jsp"/>

	
    <h4>cast:</h4><br>
    <%for(int i=0;i<casts.getPersonNumber();++i){
		request.getSession().setAttribute("person",casts.getPersons().get(i));%>
		<jsp:include page="Person.jsp"/>
	<% }
    %>
    <br>

	<% int i = 1 ;%>
	<%for(String s : trailers) {
	   int index = s.indexOf("v=");
	   s = s.substring(index+2);
	   String url = "http://www.youtube.com/embed/" + s;
	 %>
    <iframe width ="500" height = "400"
	src = <%=url %>>
	</iframe>
	<%
	}
	%>
	<br>
	</div>
 
</div>    
    
</body>
</html>
