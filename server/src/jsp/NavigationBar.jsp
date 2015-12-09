<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Search.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans</title>
<link rel="shortcut icon" href="/images/1.png">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("#movieS").click(function(){
    	$("#movieSearch").show();
    	$("#userSearch").hide();
    	$("#groupSearch").hide();
    	$("#hashtagSearch").hide();
    });
    $("#userS").click(function(){
        $("#userSearch").show();
        $("#movieSearch").hide();
        $("#groupSearch").hide();
        $("#hashtagSearch").hide();
    });
    $("#groupS").click(function(){
    	$("#groupSearch").show();
    	$("#userSearch").hide();
        $("#movieSearch").hide();
        $("#hashtagSearch").hide();
    });
    $("#hashtagS").click(function(){
        $("#hashtagSearch").show();
    	$("#groupSearch").hide();
    	$("#userSearch").hide();
        $("#movieSearch").hide();
    });
});
</script>

</head>
<%@ page import="java.util.Date" %>
<%@ page import="com.myapp.servlet.*" %>
<body>
<%
	String username = ServletCommon.getSessionUsername(request);
	String myUrl="/userpage?user="+username;
	int unread = ServletCommon.getUnReadMailNum(username);
%>
<ul class="nav">
    <li>
        <a href="/">Home</a>
    </li>
    <li>
         <a href=<%=myUrl%>>My Page</a>
    </li>
    <li id="search">    
      <form id ="movieSearch" action= "/search_movie_result" method="post">
         <input type="text" name="search_movie" value="" id="search_text" placeholder="Search Movies"/>
         <input type="submit" id="search_button" id="search_text" value = "Search"> 
      </form>
      <form id ="userSearch" action= "/search_user" method="post" style="display:none">
         <input type="text" name="USER" value="" id="search_text" placeholder="Search Users"/>
         <input type="submit" id="search_button" value = "Search"> 
      </form>
      <form id ="groupSearch" action= "/search_group_result" method="post" style="display:none">
         <input type="text" name="search_group" value="" id="search_text" placeholder="Search Groups"/>
         <input type="submit" id="search_button" value = "Search"> 
      </form>
      
      <form id ="hashtagSearch" action= "/search_hashtag_result" method="post" style="display:none">
         <input type="text" name="search_hashtag" value="" id="search_text" placeholder="Search Hashtag"/>
         <input type="submit" id="search_button" value = "Search"> 
      </form>
    <li id="options">
        <a href="#">Options</a>
        <ul class="subnav">
            <li><a id = "movieS" href="#">Movie</a></li>
            <li><a id = "userS"  href="#">User</a></li>
            <li><a id = "groupS" href="#">Group</a></li>
            <li><a id = "hashtagS" href="#">HashTag</a></li> 			
        </ul>
    </li>
    <li>
    <a href="/usersettings">Settings</a>
    </li> 
    <li>
    <%if (unread != 0 ) { %>
      <a href="/mailbox">Messages (<%=unread%>)</a>
    <% } 
    else { %>
      <a href="/mailbox">Messages (0)</a>
    <% } %>
    </li>  
    <li>
    <a href="/logoff">Log Out</a>
    </li>  
    <li> 
    <form action= "/search_movie_result_advanced" method="post">
    <select name="formDoor[]">
	<option value="Action">Action </option>
	<option value="Adventure" >Adventure </option>
	<option value="Horror" >Horror</option>
	<option value="Romance" >Romance </option>
	<option value="War" >War </option>
	<option value="Documentary" >Documentary </option>
	<option value="Drama" >Drama</option>
	<option value="Thriller" >Thriller</option>
	<option value="Crime" >Crime </option>
	<option value="Mystery" >Mystery </option>
	<option value="Animation" >Animation </option>
	<option value="Fantasy" >Fantasy </option>
	<option value="Comedy" >Comedy </option>
	<option value="Children" >Children</option>
	<option value="Sci-Fi" >Sci-fi </option>
	<option value="Musical">Musical <br>
	</select> 
	<select name="MovieLength">
	<option  selected = "true"  style="display:none;" value="0">30 mins</option>
	<option  value="1">31-60 mins</option>
	<option  value="2">61-90 mins</option>
	<option  value="3">91-120 mins</option>
	<option  value="4">120 mins</option>
	</select>
	<select name ="OrderBy">
  	<option selected="true" style="display:none;"></option>
  	<option selected = "selected" value="USERRATING">Rating</option>
  	<option value="RELEASEDATE">Year</option>
  	<option value="VOTE">Votes</option>
	</select>

	<input type="submit" /> 
	</form>
</li> 

    
</ul>

</body>
</html>

