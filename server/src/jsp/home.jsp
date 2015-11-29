<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* { padding: 0; margin: 0; }

body {
 font-family: Arial, Helvetica, sans-serif;
 font-size: 13px;
}
#wrapper { 
 margin: 0 auto;
 width: 922px;
}
#header {
 color: #333;
 width: 900px;
 float: left;
 padding: 10px;
 border: 1px solid #ccc;
 height: 100px;
 margin: 10px 0px 5px 0px;
 background:#F6F0E0;
}
#navigation {
 float: left;
 width: 900px;
 color: #333;
 padding: 10px;
 border: 1px solid #ccc;
 margin: 0px 0px 5px 0px;
 background-color:#F3F2ED;
}
#leftcolumn { 
 color: #333;
 border: 1px solid #ccc;
 background:#F6F0E0;
 margin: 0px 5px 5px 0px;
 padding: 10px;
 height: 350px;
 width: 205px;
 float: left;
}
#leftmiddle { 
 color: #333;
 border: 1px solid #ccc;
 background:#CCC8B3;
 margin: 0px 5px 5px 0px;
 padding: 10px;
 height: 350px;
 width: 205px;
 float: left;
}
#rightmiddle { 
 color: #333;
 border: 1px solid #ccc;
 background:#F6F0E0;
 margin: 0px 5px 5px 0px;
 padding: 10px;
 height: 350px;
 width: 205px;
 float: left;
}
#rightcolumn { 
 color: #333;
 border: 1px solid #ccc;
 background:#CCC8B3;
 margin: 0px 0px 5px 0px;
 padding: 10px;
 height: 350px;
 width: 204px;
 float: left;
}
#footer { 
 width: 900px;
 clear: both;
 color: #333;
 border: 1px solid #ccc;
 background-color:#F3F2ED;
 margin: 0px 0px 10px 0px;
 padding: 10px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MovieFans.com</title>
<link rel="shortcut icon" href="/images/1.png">
</head>
<%@ page import="java.util.Date" %>
<body>
<!-- Begin Wrapper -->
<div id="wrapper">
  <!-- Begin Header -->
  <div id="header"><h1><a href="http://www.free-css.com/free-css-layouts.php">Free CSS Layouts</a></h1></div>
  <!-- End Header -->
  <!-- Begin Navigation -->
  <div id="navigation"> Navigation Here </div>
  <!-- End Navigation -->
  <!-- Begin Left Column -->
  <div id="leftcolumn"> <jsp:include page="NewsList.jsp"/> </div>
  <!-- End Left Column -->
  <!-- Begin Left Middle Column -->
  <div id="leftmiddle"> <jsp:include page="NewsList.jsp"/>  </div>
  <!-- End Left Middle Column -->
  <!-- Begin Right Middle Column -->
  <div id="rightmiddle"> <jsp:include page="NewsList.jsp"/> </div>
  <!-- End Right Middle Column -->
  <!-- Begin Right Column -->
  <div id="rightcolumn"> Right Column </div>
  <!-- End Right Column -->
  <!-- Begin Footer -->
  <div id="footer"> This is the Footer </div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
</body>
</html>

