<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/UserPage.css">
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

<jsp:include page="NavigationBar.jsp"/>

<%Boolean myPageFlag = (Boolean)request.getAttribute("isMyPage");%>
<%Boolean myfriendFlag = (Boolean)request.getAttribute("isMyFriend");%> 
<%String curUser = "?"; 
curUser+=request.getQueryString();
%>

<%@ page import="com.myapp.view.*" %>
<% UserSettingView usv = (UserSettingView)request.getAttribute("UserSettingView");
	String headURL = usv.getHeadUrl();
	String profileURL = usv.getProfileUrl();
	String description = usv.getDescription();
%>

<div width=100% height=100% id="Outer">
	<div class="leftColumn" id="BasicDiv">
		<div class="left_top" style="background-image: url(<%=profileURL%>); background-size:cover;">
    		<div class="avatar">
	   			<%if(headURL==null||headURL.isEmpty()) {%>
   					<img src="../images/nobackground.jpg" class="avatar" />
    			<%}else{ %>
    				<img src=<%=headURL %> class="avatar" />
				<%} %>
			</div>
		</div>
		<div class="left_bottom">
			<div class="left_bottom_left">
				<div class="introduction">
				</div>
			</div>
			<div class="left_bottom_right">
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
		</div>
	</div>
	<div class="centerColumn" id="BasicDiv">
	</div> 
	<div class="rightColumn" id="BasicDiv">
		<jsp:include page="FriendList.jsp"/>
	</div>
</div>

<!--<div id="container">
	<%if(profileURL==null||profileURL.isEmpty()) {%>
		<img src="../images/noprofile.jpg" style="width:152px;height:114px;" id="profile" border="5" bordercolor="#A9A9A9"/>
	<%}else{ %>
		<img src=<%=profileURL %> style="width:152px;height:114px;" id="profile" border="5" bordercolor="#A9A9A9"/>
    <%} %>
   	<%if(headURL==null||headURL.isEmpty()) {%>
   		<img src="../images/nobackground.jpg" style="width:800px;height:228px;" id="avatar" />
    <%}else{ %>
    	<img src=<%=headURL %> style="width:800px;height:228px;" id="avatar" />
	<%} %>
</div>
<br>
<%if(description!=null){ %>
	<p><em><%=description%></em></p>
<%}else{ %>
	<p><em>No Description Available</em></p>
<%} %>

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

<div width=100% height=100%>
  <div id="BasicDiv" class = "GroupList"> <jsp:include page="GroupList.jsp"/> </div>
  
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
  
  <div id="BasicDiv" class = "RecommendationList"> remain for recommendation </div>
  
  <div id="BasicDiv" class = "FriendList"> <jsp:include page="FriendList.jsp"/> </div>
 </div>

-->

</body>
</html>

