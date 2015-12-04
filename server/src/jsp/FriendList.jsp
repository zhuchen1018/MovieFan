<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
			<img src=<%=po%> alt="Poster" style="width:145px;height:126px;"> &nbsp; <a href=<%=nameurl %>><%=name %></a><br>
		 <% } else { %>
			<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"> &nbsp; <td><%=name %></td><br>
		 <%}
	}%>
     <br>

</body>
</html>