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
      
	<% for(int i=0;i<grouplist.size();i++){
			String po = grouplist.get(i).getImageUrl();
			String name = grouplist.get(i).getName();
			String nameurl = "grouppage?group="+name;
			if(po!=null&&!po.equals("null")){ %>
	        <p>
   			 	<a href=<%=nameurl%>><img src=<%=po%> class="group"></img></a>
    			<a href=<%=nameurl%>><b class="groupName"><%=name%></b></a>
    		</p>
		<% } 
		 	else { %>
		 	<p>
   			 	<a href=<%=nameurl%><img src="/images/not-found.png" class="group"></img></a>
    			<a href=<%=nameurl%><b class="groupName"><%=name%></b></a>
    		</p>
		<%}
	}%>
     <br>

</body>
</html>