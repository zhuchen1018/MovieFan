<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Homepage.css">
<link rel = "stylesheet" type ="text/css" href = "../css/Search.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<%@ page import="java.util.Date" %>
<body>

<ul class="nav">
    <li>
        <a href="#">Home</a>
    </li>
    <li>
        <a href="#">My Page</a>
    </li>
    <li id="search">    
      <form action= "/search_movie_result" method="post">
         <input type="text" name="search_movie" value="" id="search_text" placeholder="Search"/>
         <input type="submit" id="search_button"> 
      </form>
    </li>
    <li id="options">
        <a href="#">Options</a>
        <ul class="subnav">
            <li><a href="#">Settings</a></li>
            <li><a href="#">Application</a></li>
            <li><a href="#">Board</a></li>
            <li><a href="#">Options</a></li>
        </ul>
    </li>
</ul>

<jsp:include page="NavigationBar.jsp"/>


<!-- Begin Wrapper -->
<div width=100% height=100% id="Outer">
  <div id="BasicDiv" class = "GroupList"> <jsp:include page="GroupList.jsp"/> </div>
  <!-- End Left Column -->
  <!-- Begin Left Middle Column -->
  <div id="BasicDiv" class = "NewsList"> 
	<form action="/tweet_home" method="POST">
		<div align="center">
			<textarea cols="64" rows="5" name="TWEET" placeholder="Share Something"></textarea>
			<INPUT TYPE=SUBMIT VALUE="submit">
		</div>
  	</form>
  <jsp:include page="NewsList.jsp"/>
  </div>
  <!-- End Left Middle Column -->
  <!-- Begin Right Middle Column -->
  <div id="BasicDiv" class = "RecommendationList"> remain for recommendation </div>
  <!-- End Right Middle Column -->
  <!-- Begin Right Column -->
  <div id="BasicDiv" class = "FriendList"> <jsp:include page="FriendList.jsp"/> </div>
  <!-- End Right Column -->
 </div>
<!-- End Wrapper -->


</body>
</html>

