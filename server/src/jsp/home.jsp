<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Homepage.css">
<link rel = "stylesheet" type ="text/css" href = "../css/Search.css">
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<%@ page import="java.util.Date" %>
<body>

<jsp:include page="NavigationBar.jsp"/>


<!-- Begin Wrapper -->
<div id="Outer">
  <div id="BasicDiv" class = "GroupList"> <jsp:include page="GroupList.jsp"/> </div>
  <!-- End Left Column -->
  <!-- Begin Left Middle Column -->
  <div id="BasicDiv" class = "NewsList"> 
	<form action="/tweet_home" method="POST">
		<div align="center">
			<textarea cols="28" rows="5" name="TWEET" placeholder="Share Something"></textarea>
			<br>
			<INPUT TYPE=SUBMIT VALUE="submit">
		</div>
  	</form>
  <jsp:include page="NewsList.jsp"/>
  </div>
  <!-- End Left Middle Column -->
  <!-- Begin Right Middle Column -->
  <div id="BasicDiv" class = "RecommendationList"> <jsp:include page="RecommendMovieList.jsp"/> </div>
  <!-- End Right Middle Column -->
  <!-- Begin Right Column -->
  <div id="BasicDiv" class = "FriendList"> <jsp:include page="FriendList.jsp"/> </div>
  <!-- End Right Column -->
 </div>
<!-- End Wrapper -->


</body>
</html>

