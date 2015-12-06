<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group Page</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>
<%
GroupPageView gpv = (GroupPageView) request.getAttribute("GroupPageView"); 
    Long gid = gpv.getId();
    String gname = gpv.getName(); 
    String creator = gpv.getCreator(); 
    Long gid = gpv.getId();
    ArrayList<String>members = gpv.getMembers();
    Boolean joined = gpv.isJoined();

    %>
	<div id="content"> 
	    <% if(!joined){ %>
			<form action=<%="/group/join_group" + "?" + "group=" + gid%> method="POST">
				<button class="myButton" type="submit" name="JoinThisGroup">
                    Join
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
		<% } %> 
	</div> 
</div>    
    
</body>
</html>
