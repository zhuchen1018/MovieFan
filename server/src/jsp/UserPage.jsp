<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>

<body>

<%Boolean myPageFlag = (Boolean)request.getAttribute("isMyPage");%>
<%Boolean myfriendFlag = (Boolean)request.getAttribute("isMyFriend");%> 
<%String curUser = "?"; 
curUser+=request.getQueryString();
%>


<form action="/tweet_user" method="POST">
		<div align="center">
			<textarea cols="40" rows="5" name="TWEET" placeholder="Share Something"></textarea>
			<INPUT TYPE=SUBMIT VALUE="submit">
		</div>
</form>

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
<div id="wrapper">
  <!-- Begin Header -->
  <div id="header"><h1><a href="http://www.free-css.com/free-css-layouts.php">Free CSS Layouts</a></h1></div>
  <!-- End Header -->
  <!-- Begin Navigation -->
  <div id="navigation"> Navigation Here </div>
  <!-- End Navigation -->
  <!-- Begin Left Column -->
  <div id="leftcolumn" class = "scroll"> <jsp:include page="NewsList.jsp"/> </div>
  <!-- End Left Column -->
  <!-- Begin Left Middle Column -->
  <div id="leftmiddle" class = "scroll"> <jsp:include page="NewsList.jsp"/>  </div>
  <!-- End Left Middle Column -->
  <!-- Begin Right Middle Column -->
  <div id="rightmiddle" class = "scroll"> <jsp:include page="NewsList.jsp"/> </div>
  <!-- End Right Middle Column -->
  <!-- Begin Right Column -->
  <div id="rightcolumn" class = "scroll"> Right Column </div>
  <!-- End Right Column -->
 </div>
<!-- End Wrapper -->


</body>
</html>

