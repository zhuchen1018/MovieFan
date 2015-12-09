<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Friend.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search User Result</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>
<%@ page import="java.util.*" %>


<% UserListView ulv = (UserListView) request.getAttribute("UserListView");
	  ArrayList<UserObjectView> userlist = ulv.getUserList();
%>
      
	<% for(int i=0;i<userlist.size();i++){
			String po = userlist.get(i).getImageUrl();
			String name = userlist.get(i).getName();
			String nameurl = "userpage?user="+name;
	        if(po!=null&&!po.equals("null")){ %>
			<a href=<%=nameurl%>><img src=<%=po%> alt="Poster" style="width:145px;height:126px;"></img></a> 
			<a href=<%=nameurl%>><b class="friendName"><font size="3"><%=name%></font></b></a>
		 <% } else { %>
			<a href=<%=nameurl%>><img src="/images/noprofile.jpg" alt="Poster" style="width:145px;height:126px;"></img></a>
			<a href=<%=nameurl%>><b class="friendName"><font size="3"><%=name%></font></b></a>
		 <%}
	}%>
     <br>

</body>
</html>
