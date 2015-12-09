<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel = "stylesheet" type ="text/css" href = "../css/News.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MovieFans.com</title>
</head>
<body>

<%@ page import="com.myapp.view.*" %>
<%@ page import="com.myapp.utils.*" %>
<%NewsListView nlv = (NewsListView)request.getAttribute("NewsListView"); %>
<%

if(nlv==null)
{
	System.out.println("NewsList.jsp NewsListView is null!!!!");
}
else
{
    HttpSession s= request.getSession();
	System.out.println("print NewsList: size: " + nlv.size());
    while(!nlv.isEmpty())
    {
        NewsObjectView nov = nlv.pop();
        if(nov != null){
            int type = nov.getType();
	        System.out.println("print NewsList: type: " + type);
            switch(type){
            case Const.NEWS_TWITTER:
                s.setAttribute("nt", nov); %>
                <div class="Tweet"><jsp:include page="NewsTweet.jsp"/></div><%
                s.setAttribute("nt", null);
                break;
            case Const.NEWS_MAKE_FRIENDS:
                s.setAttribute("nmf", nov); %>
                <div class="MakeFriend"><jsp:include page="NewsMakeFriend.jsp"/></div><%
                s.setAttribute("nmf", null);
                break;
            case Const.NEWS_LIKE_MOVIE:
                s.setAttribute("nlm", nov); %>
                <div class="LikeMovie"><jsp:include page="NewsLikeMovie.jsp"/></div><%
                s.setAttribute("nlm", null);
                break;
            case Const.NEWS_SHARE_MOVIE:
                s.setAttribute("nsm", nov); %>
                <div class="ShareMovie"><jsp:include page="NewsShareMovie.jsp"/></div><%
                s.setAttribute("nsm", null);
                break;
            case Const.NEWS_MOVIE_REVIEW:
                s.setAttribute("nmr", nov); %>
                <div class="MovieReview"><jsp:include page="NewsMovieReview.jsp"/></div><%
                s.setAttribute("nmr", null);
                break;
            case Const.NEWS_ADD_GROUP:
                s.setAttribute("nag", nov); %>
                <div class="AddGroup"><jsp:include page="NewsAddGroup.jsp"/></div><%
                s.setAttribute("nag", null);
                break;
            case Const.NEWS_TWEET_IN_GROUP:
                s.setAttribute("ntig", nov); %>
                <div class="Tweet"><jsp:include page="GroupNewsTweet.jsp"/></div><%
                s.setAttribute("ntig", null);
                break;
            }
        }%>
        <br>
    <%
    } 
    %>
<%
} 
%>


</body>
</html>
