<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Homepage.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">

<style>
#container {
    position:relative;
}

#profile {
    position: absolute;
    top: 50px;
    bottom: 0px;
}
</style>


</head>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>

<body>

<%Boolean myPageFlag = (Boolean)request.getAttribute("isMyPage");%>
<%Boolean myfriendFlag = (Boolean)request.getAttribute("isMyFriend");%> 
<%String curUser = "?"; 
curUser+=request.getQueryString();
%>

<div id="container">
    <img src="../images/1.ico" style="width:152px;height:114px;" id="profile" border="5" bordercolor="#A9A9A9"/>
    <img src="http://climate.nasa.gov/assets/intro_image.jpg" style="width:800px;height:228px;" id="avatar" />
</div>


<%if(!myPageFlag) {%>
	<%if(!myfriendFlag){ %>
		<form action=<%="/follow"+curUser %> method="POST">
			<button type="submit" name="UserFollow">
    			Follow
			</button>
		</form>
	<%}else { %>
		<form action=<%="/unfollow"+curUser %> method="POST">
			<button type="submit" name="UserUnFollow">
	    		UnFollow
			</button>
		</form>
	<%} %>		
<%} %>

<!-- Begin Wrapper -->
<div width=100% height=100%>
  <div id="BasicDiv" class = "GroupList"> <jsp:include page="GroupList.jsp"/> </div>
  <!-- End Left Column -->
  <!-- Begin Left Middle Column -->
  <div id="BasicDiv" class = "NewsList">
  	<% if(myPageFlag) {%>
	<form action="/tweet_user" method="POST">
		<div align="center">
			<textarea cols="65" rows="5" name="TWEET" placeholder="Share Something"></textarea>
			<INPUT TYPE=SUBMIT VALUE="submit">
		</div>
  	</form>
  	<%} %>
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

