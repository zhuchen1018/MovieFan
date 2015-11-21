<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movie Object</title>
</head>
<body>
<%@ page import="com.myapp.view.*" %>

<% MovieObjectView mov = (MovieObjectView) request.getSession().getAttribute("item");
%> 
    <%String po = mov.getPoster();
      String homepage = mov.getPageUrl();%>
      
	<td><%=mov.getName()%></td><br>
    <td>Overview: <%=mov.getOverview()%> </td><br>   
    <td>Rating: <%=mov.getRating()%></td><br>
    <%if(!po.equals("null")){ %>
		<img src=<%=po%> alt="Poster" style="width:200px;height:160px;"><br>
	<% } else { %>
		<img src="/images/not-found.png" alt="Poster" style="width:145px;height:126px;"><br>
	<%} %>
    <%if (!homepage.equals( "null")) {
       out.print("Movie HomePage :" +  homepage);  	
    }%> <br>

    
</body>
</html>