<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/UserPage.css">
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
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
<%@ page import="com.myapp.servlet.*" %>

<body>

<jsp:include page="NavigationBar.jsp"/>

<%String curUser = "?"; 
curUser+=request.getQueryString();
%>

<%@ page import="com.myapp.view.*" %>
<% UserInfoView uiv = (UserInfoView)request.getAttribute("UserInfoView");
	String headURL = uiv.getHeadUrl();
	String profileURL = uiv.getProfileUrl();
	if(profileURL==null) profileURL="../images/nobackground.jpg";
	String description = uiv.getDescription();
	String[] genres = uiv.getString_Genres();
    Boolean myPageFlag = uiv.isMyPage(); 
    Boolean myfriendFlag = uiv.isMyFriend(); 
    int fansNum = uiv.getFansNum();
    int followingNum = uiv.getFollowingNum();
    int newsNum = uiv.getNewsNum(); 
    String userName = uiv.getName(); 
%>

<div width=100% height=100% id="Outer">
	<div class="leftColumn" id="BasicDiv">
		<div class="left_top" style="background-image: url(<%=profileURL%>); background-size:cover;">
    		<div class="avatar">
	   			<%if(headURL==null||headURL.isEmpty()) {%>
   					<img src="../images/noprofile.jpg" class="avatar" />
    			<%}else{ %>
    				<img src=<%=headURL %> class="avatar" />
				<%} %>
				
				<font color=#FFFFFF><%=userName%></font>
				<%if(!myPageFlag) {%>
					<%if(!myfriendFlag){ %>
						<form action=<%="/follow"+curUser %> method="POST">
							<button type="submit" name="UserFollow">
    							+Follow
							</button>
						</form>
					<%}
					else { %>
						<form action=<%="/unfollow"+curUser %> method="POST">
							<button type="submit" name="UserUnFollow">
	    						-Unfollow
							</button>
						</form>
					<%} %>		
				<%} %>
				<br>
				Fans: <%=fansNum%>
				<br>
				Following: <%=followingNum%>
				<br>
				News: <%=newsNum%>
				
			</div>
		</div>
		<div class="left_bottom">
			<div class="left_bottom_left">
				<div class="description">
					<h2 id="title">About Me</h2>
					<p>
						<%if(description!=null){ %>
							<em><%=description%></em>
						<%}else{ %>
							<em>No Description Available</em>
						<%} %>
					</p>
				</div>
				<br>
				<div class="genres">
					<h3 id="title">Favorite Genres</h3>
					<%for(int i=0;i<genres.length;++i){%>
						<h2 class="genre"><em><%=genres[i]%></em></h2>
					<%
					}
					%>
				</div>
			</div>
			<div class="left_bottom_right">
				<% if(myPageFlag) {%>
					<form action="/tweet_user" method="POST">
						<div align="center">
							<textarea cols="25" rows="5" name="TWEET" placeholder="Share Something"></textarea>
							<br>
							<INPUT TYPE=SUBMIT VALUE="submit">
						</div>
  					</form>
  				<%} %>
				<jsp:include page="NewsList.jsp"/>
			</div>
		</div>
	</div>
	<div class="centerColumn" id="BasicDiv">
		<jsp:include page="SimpleMovieList.jsp"/>
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
    			+Follow
			</button>
		</form>
	<%}else { %>
		<form action=<%="/unfollow"+curUser %> method="POST">
			<button type="submit" name="UserUnFollow">
	    		-Unfollow
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
			<textarea cols="65" rows="5" name="TWEET" placeholder="What's on your mind?"></textarea>
			<INPUT TYPE=SUBMIT VALUE="Post">
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

