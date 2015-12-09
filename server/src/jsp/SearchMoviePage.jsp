<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Search.css">
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@ page import="java.util.Date" %>
<body>

<jsp:include page="NavigationBar.jsp"/>
<br><br>
<div align="center">

    <h1 style="color:#FFA500">Advance Search</h1>
    <br></br>
	<br></br>
    <h2 style="color:#FFA500">Category</h2>
	<br>
	<form action= "/search_movie_result_advanced" method="post">	
		<input type="radio" name="formDoor[]" value="Action" /><font size="4">Action</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Adventure" /><font size="4">Adventure</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Horror" /><font size="4">Horror</font>&nbsp;
		<input type="radio" name="formDoor[]" value="Romance" /><font size="4">Romance</font>&nbsp;
		<input type="radio" name="formDoor[]" value="War" /><font size="4">War </font><br/>
		<input type="radio" name="formDoor[]" value="Documentary" /><font size="4">Documentary</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Drama" /><font size="4">Drama </font>&nbsp;
		<input type="radio" name="formDoor[]" value="Thriller" /><font size="4">Thriller</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Crime" /><font size="4">Crime</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Mystery" /><font size="4">Mystery</font>&nbsp;<br/>
		<input type="radio" name="formDoor[]" value="Animation" /><font size="4">Animation </font>&nbsp;
		<input type="radio" name="formDoor[]" value="Fantasy" /><font size="4">Fantasy</font>&nbsp;
		<input type="radio" name="formDoor[]" value="Comedy" /><font size="4">Comedy </font>&nbsp;
		<input type="radio" name="formDoor[]" value="Children" /><font size="4">Children</font> &nbsp;
		<input type="radio" name="formDoor[]" value="Sci-Fi" /><font size="4">Sci-fi </font>&nbsp;
		<input type="radio" name="formDoor[]" value="Musical" /><font size="4">Musical</font><br>
		
		<br></br>
		<br></br>
		<h2 style="color:#FFA500">Choose Length</h2>
		<br></br>
		<input type="radio" name="MovieLength" value="0" />&#60;<font size="4"> <30 mins </font>&nbsp;
		<input type="radio" name="MovieLength" value="1" /><font size="4">30 - 60 mins</font>&nbsp;
		<input type="radio" name="MovieLength" value="2" /><font size="4">60 - 90 mins</font>&nbsp;
		<input type="radio" name="MovieLength" value="3" /><font size="4">90 - 120 mins</font>&nbsp;
		<input type="radio" name="MovieLength" value="4" />&#62;<font size="4"> >120 mins</font>&nbsp;</br>
		
		<br></br>
		<br></br>
		<h2 style="color:#FFA500">Order By</h2> 
		<br></br>
		<select name ="OrderBy" id = "wgtmsr">
		  <option selected="true" style="display:none;"></option>
		  <option selected = "selected" value="USERRATING">Rating</option>
		  <option value="RELEASEDATE">Year</option>
		  <option value="VOTE">Votes</option>
		</select>	
	<input type="submit" /> 
	</form>
</div>
</body>
</html>