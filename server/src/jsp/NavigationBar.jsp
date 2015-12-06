<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Search.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<%@ page import="java.util.Date" %>
<%@ page import="com.myapp.servlet.*" %>
<body>
<%
	String username=ServletCommon.getSessionUsername(request);
	String myUrl="/userpage?user="+username;
%>
<ul class="nav">
    <li>
        <a href="/">Home</a>
    </li>
    <li>
         <a href=<%=myUrl%>>My Page</a>
    </li>
    <li id="search">    
      <form action= "/search_movie_result" method="post">
         <input type="text" name="search_movie" value="" id="search_text" placeholder="Find Movies"/>
         <input type="submit" id="search_button" value = "Search"> 
      </form>
    <li id="options">
        <a href="#">Options</a>
        <ul class="subnav">
            <li><a href="#">Movie</a></li>
            <li><a href="#">User</a></li>
            <li><a href="#">Group</a></li> 			
        </ul>
    </li>
    <li>
    <a href="/usersettings">Setting</a>
    </li> 
    <li>
    <a href="/logoff">Logout</a>
    </li>   
    
</ul>

</body>
</html>

