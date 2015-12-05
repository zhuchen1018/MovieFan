<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ page import="com.myapp.view.*" %>

<% UserSettingView usv = (UserSettingView)request.getAttribute("UserSettingView");
	String headURL = usv.getHeadUrl();
	String profileURL = usv.getProfileUrl();
	String description = usv.getDescription();
%>

<form action= "/usersettings" method="post">

<h3>Your Avatar</h3>
<input type="text" name="HEAD_URL" value="" placeholder=<%=headURL %>/>

<h3>Your Profile</h3>
<input type="text" name="PROFILE_URL" value="" placeholder=<%=profileURL %>/>

<h3>Your Personal Description</h3>
<textarea cols="40" rows="5" name="DESC" placeholder = <%=description%>></textarea>

<h3>Genres You Like</h3>
<input type="checkbox" name="GENRES" value="Action" />Action &nbsp;
<input type="checkbox" name="GENRES" value="Adventure" />Adventure &nbsp;
<input type="checkbox" name="GENRES" value="Horror" />Horror &nbsp;
<input type="checkbox" name="GENRES" value="Romance" />Romance &nbsp;
<input type="checkbox" name="GENRES" value="War" />War <br/>
<input type="checkbox" name="GENRES" value="Documentary" />Documentary &nbsp;
<input type="checkbox" name="GENRES" value="Drama" />Drama &nbsp;
<input type="checkbox" name="GENRES" value="Thriller" />Thriller &nbsp;
<input type="checkbox" name="GENRES" value="Crime" />Crime &nbsp;
<input type="checkbox" name="GENRES" value="Mystery" />Mystery &nbsp;<br/>
<input type="checkbox" name="GENRES" value="Animation" />Animation &nbsp;
<input type="checkbox" name="GENRES" value="Fantasy" />Fantasy &nbsp;
<input type="checkbox" name="GENRES" value="Comedy" />Comedy &nbsp;
<input type="checkbox" name="GENRES" value="Children" />Children &nbsp;
<input type="checkbox" name="GENRES" value="Sci-Fi" />Sci-fi &nbsp;
<input type="checkbox" name="GENRES" value="Musical" />Musical <br>

<input type="submit" value="Submit"/>

</form>

</body>
</html>
