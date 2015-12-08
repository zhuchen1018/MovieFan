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

<% UserSettingView usv = (UserSettingView)request.getAttribute("UserSettingView");
	String headURL = usv.getHeadUrl();
	String profileURL = usv.getProfileUrl();
	String description = usv.getDescription();
	if(headURL==null) headURL=" ";
	if(profileURL==null) profileURL=" ";
	if(description==null) description=" ";
%>

<jsp:include page="NavigationBar.jsp"/>

<form action= "/usersettings" method="post">
<br><br>
<h1 id="text_title">Avatar URL:</h1>
<input class="depth" size="100" type="text" name="HEAD_URL" value="" placeholder=<%=headURL %> value=<%=headURL %>>
<br><br>
<h1 id="text_title">Profile URL:</h1>
<input class="depth" size="100"type="text" name="PROFILE_URL" value="" placeholder=<%=profileURL %> value=<%=profileURL %>>
<br><br>
<h1 id="text_title">Personal Description:</h1>
<textarea class="depth" cols="80" rows="20" name="DESC" placeholder = <%=description%> value=<%=description%>></textarea>
<br><br>
<h1 id="text_title">Movie Genres You Like:</h1>
<div align="center">
	<input type="checkbox" name="GENRES" value="Action" /><b id="text_genre">Action</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Adventure" /><b id="text_genre">Adventure</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Horror" /><b id="text_genre">Horror</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Romance" /><b id="text_genre">Romance</b> &nbsp;
	<input type="checkbox" name="GENRES" value="War" /><b id="text_genre">War</b> <br/>
	<input type="checkbox" name="GENRES" value="Documentary" /><b id="text_genre">Documentary</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Drama" /><b id="text_genre">Drama</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Thriller" /><b id="text_genre">Thriller</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Crime" /><b id="text_genre">Crime</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Mystery" /><b id="text_genre">Mystery</b> &nbsp;<br/>
	<input type="checkbox" name="GENRES" value="Animation" /><b id="text_genre">Animation</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Fantasy" /><b id="text_genre">Fantasy</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Comedy" /><b id="text_genre">Comedy</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Children" /><b id="text_genre">Children</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Sci-Fi" /><b id="text_genre">Sci-fi</b> &nbsp;
	<input type="checkbox" name="GENRES" value="Musical" /><b id="text_genre">Musical</b> <br>

	<input type="submit" value="Submit"/>
</div>
</form>

</body>
</html>
