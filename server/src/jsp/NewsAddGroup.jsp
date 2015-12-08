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

<% NewsObjectView nov = (NewsObjectView) request.getSession().getAttribute("nag");
	String userName = nov.getUsername();
	String time = nov.getReleaseTime();
	String note = nov.getNote();
	String group = nov.getToList().get(0);
	String groupurl = "grouppage?group="+group;
	String userUrl = nov.getUserUrl();
	if(userUrl==null) userUrl="/images/not-found.png";
%>
    
    <p>
    	<img src=<%=userUrl%> class="user"></img>
    	<b class="name"><font size="5"><%=userName%><font></b>
    	&nbsp;
    	<b class="note"><font size="4"><%=note%></font></b>
    	<a class="link" href=<%=groupurl%>><%=group %></a><br>
    </p>
    <br>
    <p>
    	<sub><font size="4"><%=time %></font></sub>
    </p>

</body>
</html>