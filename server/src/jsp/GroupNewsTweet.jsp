<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/News.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("ntig");
	String userName = nov.getUsername();
	String tweetContent = nov.getText();
	String tweetTime = nov.getReleaseTime();
	String note = nov.getNote();
	String userUrl = nov.getUserUrl();
	if(userUrl==null) userUrl="/images/noprofile.jpg";
	String userLink="userpage?user="+userName;
%>      

	<p>
    	<img src=<%=userUrl%> class="user"></img>
    	<a href=<%=userLink%>><b class="name"><font size="5"><%=userName%></font></b></a>
    	&nbsp;
    	<b class="note"><font size="4"><%=note%></font></b>
    </p>
    <br>
    <p>
    	<sub><font size="4"><%=tweetTime %></font></sub>
    </p>
    <br>
    <p class="paragraph">
    	<%=tweetContent %>
    </p>
</body>
</html>