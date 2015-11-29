<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<h1>News</h1><br>
<body>

<%@ page import="com.myapp.view.*" %>
<%@ page import="com.myapp.utils.*" %>
<%NewsListView nlv = (NewsListView)request.getAttribute("NewsListView"); %>
<%

if(nlv==null)
	return;
for(int i=0;i<nlv.getNewsNumber();++i){%>
 <% NewsObjectView nov = nlv.getNextItem();
 	int type = nov.getType();
	if(nov != null){
		switch(type){
		case Const.NEWS_TWITTER:
			request.getSession().setAttribute("nt", nov); %>
			<jsp:include page="NewsTweet.jsp"/><%
			request.getSession().setAttribute("nt", null);
			break;
		case Const.NEWS_MAKE_FRIENDS:
			request.getSession().setAttribute("nmf", nov); %>
			<jsp:include page="NewsMakeFriend.jsp"/><%
			request.getSession().setAttribute("nmf", null);
			break;
		case Const.NEWS_LIKE_MOVIE:
			request.getSession().setAttribute("nlm", nov); %>
			<jsp:include page="NewsLikeMovie.jsp"/><%
			request.getSession().setAttribute("nlm", null);
			break;
		case Const.NEWS_SHARE_MOVIE:
			request.getSession().setAttribute("nsm", nov); %>
			<jsp:include page="NewsShareMovie.jsp"/><%
			request.getSession().setAttribute("nsm", null);
			break;
		case Const.NEWS_MOVIE_REVIEW:
			request.getSession().setAttribute("nmr", nov); %>
			<jsp:include page="NewsMovieReview.jsp"/><%
			request.getSession().setAttribute("nmr", null);
			break;
		case Const.NEWS_ADD_GROUP:
			request.getSession().setAttribute("nag", nov); %>
			<jsp:include page="NewsAddGroup.jsp"/><%
			request.getSession().setAttribute("nag", null);
			break;
		}
	}
} 
%>

</body>
</html>
