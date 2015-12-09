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

<form action="/create_group" method="POST" id="button">
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
   			<h2> <a href=<%=nameurl%>><img src=<%=po%> class="group"></img></a></h2>
    			<h3><a href=<%=nameurl%>><b class="groupName"><font size="3"><%=name%></font></b></a></h3>
	
		<% } 
		 	else { %>		 
   			<h2><a href=<%=nameurl%>><img src="/images/not-found.png" class="group"></img></a></h2>
    		<h3><a href=<%=nameurl%>><b class="groupName"><font size="3"><%=name%></font></b></a> </h3>	
			
		<%}
	}%>
     <br>

</body>
</html>