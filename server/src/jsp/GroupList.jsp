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


<% GroupListView glv = (GroupListView) request.getAttribute("GroupListView");
	  ArrayList<GroupObjectView> grouplist = glv.getGroupList();
%>
      
	<% for(int i=0;i<grouplist.size();i++){
			String po = grouplist.get(i).getImageUrl();
			String name = grouplist.get(i).getName();
	        if(!po.equals("null")){ %>
			<img src=<%=po%> alt="Poster" style="width:145px;height:126px;"> &nbsp; <td><%=name %></td><br>
		 <% } else { %>
			<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"> &nbsp; <td><%=name %></td><br>
		 <%}
	}%>
     <br>

</body>
</html>