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
<%@ page import="com.myapp.utils.*" %>
<%NewsListView nlv = (NewsListView)request.getAttribute("NewsListView"); %>
<%

if(nlv==null)
	System.out.println("null la!!!!!!!!!");
else
for(int i=0;i<nlv.getNewsNumber();++i){
	NewsObjectView nov = nlv.getNextItem();
 	int type = nov.getType();
	if(nov != null){
		switch(type){
		case Const.NEWS_TWITTER:
			request.getSession().setAttribute("nt", nov); %>
			<div class="Tweet"><jsp:include page="NewsTweet.jsp"/></div><%
			request.getSession().setAttribute("nt", null);
			break;
		case Const.NEWS_MAKE_FRIENDS:
			request.getSession().setAttribute("nmf", nov); %>
			<div class="MakeFriend"><jsp:include page="NewsMakeFriend.jsp"/></div><%
			request.getSession().setAttribute("nmf", null);
			break;
		case Const.NEWS_LIKE_MOVIE:
			request.getSession().setAttribute("nlm", nov); %>
			<div class="LikeMovie"><jsp:include page="NewsLikeMovie.jsp"/></div><%
			request.getSession().setAttribute("nlm", null);
			break;
		case Const.NEWS_SHARE_MOVIE:
			request.getSession().setAttribute("nsm", nov); %>
			<div class="ShareMovie"><jsp:include page="NewsShareMovie.jsp"/></div><%
			request.getSession().setAttribute("nsm", null);
			break;
		case Const.NEWS_MOVIE_REVIEW:
			request.getSession().setAttribute("nmr", nov); %>
			<div class="MovieReview"><jsp:include page="NewsMovieReview.jsp"/></div><%
			request.getSession().setAttribute("nmr", null);
			break;
		case Const.NEWS_ADD_GROUP:
			request.getSession().setAttribute("nag", nov); %>
			<div class="AddGroup"><jsp:include page="NewsAddGroup.jsp"/></div><%
			request.getSession().setAttribute("nag", null);
			break;
		}
	}
} 
%>

</body>
</html>
