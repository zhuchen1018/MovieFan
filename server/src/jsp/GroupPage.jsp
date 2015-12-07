<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<link rel = "stylesheet" type ="text/css" href = "../css/GroupPage.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group Page</title>

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
<body>
<%@ page import="com.myapp.view.*" %>
<jsp:include page="NavigationBar.jsp"/>
<%
GroupPageView gpv = (GroupPageView) request.getAttribute("GroupPageView"); 
    Long gid = gpv.getId();
    String gname = gpv.getName(); 
    String creator = gpv.getCreator(); 
    Boolean joined = gpv.isJoined();
%>

<%String curGroup = "?"; 
curGroup+=request.getQueryString();
%>

<div width=100% height=100% id="Outer">
	<div class="leftColumn" id="BasicDiv">
		<%-- <div class="left_top" style="background-image: url(<%=profileURL%>); background-size:cover;">
    		<div class="avatar">
	   			<%if(headURL==null||headURL.isEmpty()) {%>
   					<img src="../images/nobackground.jpg" class="avatar" />
    			<%}else{ %>
    				<img src=<%=headURL %> class="avatar" />
				<%} %>
			</div>
		</div> --%>
		<div class="left_bottom">
			<div class="left_bottom_left">
				<div>
					<h2 id="title">About This Group</h2>
					<%-- <p>
						<%=description%>
					</p> --%>
				</div>
				<br>
			</div>
			<div class="left_bottom_right">
					<form action=<%="/tweet_group"+curGroup%> method="POST">
						<div align="center">
							<textarea cols="65" rows="5" name="TWEET" placeholder="Share Something"></textarea>
							<INPUT TYPE=SUBMIT VALUE="submit">
						</div>
  					</form>
				<jsp:include page="NewsList.jsp"/>
			</div>
		</div>
	</div>
	<div class="centerColumn" id="BasicDiv">
	</div> 
	<div class="rightColumn" id="BasicDiv">
	</div>
</div>

	<div id="content"> 
	    <% if(!joined){ %>
			<form action=<%="/group/join_group" + "?" + "group=" + gid%> method="POST">
				<button class="myButton" type="submit" name="JoinThisGroup">
                    Join
				</button>
			</form>
			<form action="#" method="POST">
				<button class="myButton" type="submit" name="Members">
                    Members
				</button>
			</form>
		<%
		}
		else { %>		
			<form action=<%="/group/leave_group" + "?" + "group=" + gid%> method="POST">
			<button class="myButton" type="submit" name="LeaveThisGroup">
                Leave
			</button>
			</form>
			<form action="#" method="POST">
			<button class="myButton" type="submit" name="Members">
                Members
			</button>
			</form>
		<% } %> 
	</div> 
    
</body>
</html>
