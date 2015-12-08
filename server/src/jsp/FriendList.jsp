<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/Friend.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>
<%@ page import="java.util.*" %>


<% FriendListView flv = (FriendListView) request.getAttribute("FriendListView");
	  ArrayList<FriendObjectView> friendlist = flv.getFriendList();
%>  
	<% for(int i=0;i<friendlist.size();i++){
			String po = friendlist.get(i).getImageUrl();
			String name = friendlist.get(i).getName();
			String nameurl = "userpage?user="+name;
	        if(po!=null&&!po.equals("null")){ %>
	        <div class="friend">
	        	<div class="friendImage">
   			 		<a href=<%=nameurl%>><img class="friend" src=<%=po%> class="friend"></img></a>
   			 	</div>
   			 	<div class="name">
    				<a href=<%=nameurl%>><p><font size="3"><%=name%></font></p></a>
    			</div>
			</div>
			<br>
		<% } 
		 	else { %>
		 	<div>
		 		<div class="friendImage">
   			 		<a href=<%=nameurl%>><img class="friend" src="/images/not-found.png" class="friend"></img> </a>
   			 	</div>
   			 	<div class="name">
	    			<a href=<%=nameurl%>><p><font size="3"><%=name%></font></p></a>
	    		</div>
    		</div>
    		<br>
		<%}
	}%>
     <br>

</body>
</html>