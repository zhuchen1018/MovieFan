<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Usersetting.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MovieFans</title>
</head>
<body>

<%@ page import="com.myapp.view.*" %>

<% GroupSettingView gsv = (GroupSettingView)request.getAttribute("GroupSettingView");
	String headURL = gsv.getHeadUrl();
	String profileURL = gsv.getProfileUrl();
	String description = gsv.getDescription();
	if(headURL==null) headURL="";
	if(profileURL==null) profileURL="";
	if(description==null) description="";
	String curGroup = "?"; 
	curGroup+=request.getQueryString();
%>

<jsp:include page="NavigationBar.jsp"/>

<form action= <%="/groupsettings"+curGroup %> method="post">
<br><br>
<h1 id="text_title">Avatar URL:</h1>
<input class="depth" size="100" type="text" name="HEAD_URL" value="" placeholder=<%=headURL %>>
<br><br>
<h1 id="text_title">Profile URL:</h1>
<input class="depth" size="100"type="text" name="PROFILE_URL" value="" placeholder=<%=profileURL %>>
<br><br>
<h1 id="text_title">Personal Description:</h1>
<textarea class="depth" cols="80" rows="20" name="DESC" placeholder = <%=description%>></textarea>
<br><br>

<div align="center">
	<input type="submit" value="Submit"/>
</div>
</form>

</body>
</html>
