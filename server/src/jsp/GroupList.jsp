<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Group.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ page import="com.myapp.view.*" %>
<%@ page import="java.util.*" %>


<% GroupListView glv = (GroupListView) request.getAttribute("GroupListView");
	  ArrayList<GroupObjectView> grouplist = glv.getGroupList();
%>

<form action="/create_group" method="GET" id="button">
</form>   
<div class="button">
	<button type="submit" form="button">Create Group!</button>
</div> 

	<% for(int i=0;i<grouplist.size();i++){
			GroupObjectView gov = grouplist.get(i);
			String po = gov.getImageUrl();
			String name = gov.getName();
			String nameurl = "grouppage?group="+ gov.getId();
			if(po!=null&&!po.equals("null")){ %>
			<div class="group">
	        	<div class="groupImage">
   			 		<a href=<%=nameurl%>><img class="group" src=<%=po%>></img></a>
   			 	</div>
   			 	<div class="groupName">
    				<a href=<%=nameurl%>><p><font size="3"><%=name%></font></p></a>
    			</div>
			</div>
			<br>
		<% } 
		 	else { %>		 
    		<div class="group">
	        	<div class="groupImage">
   			 		<a href=<%=nameurl%>><img class="group" src="/images/not-found.png"></img></a>
   			 	</div>
   			 	<div class="groupName">
    				<a href=<%=nameurl%>><p><font size="3"><%=name%></font></p></a>
    			</div>
			</div>
			<br>
		<%}
	}%>
     <br>

</body>
</html>