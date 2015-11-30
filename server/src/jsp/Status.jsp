<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
 <title>Status</title>
 <link href="/css/bootstrap.css" rel="stylesheet">
</head>
 
  <body>
 
    <div class="container">
 <div class="row">
 <div class="col-md-6 col-md-offset-3">
 <hr>
 <form action="#" method="post" role="form" enctype="multipart/form-data" class="facebook-share-box">
 <ul class="post-types">
 <li class="post-type">
 <a class="status" title="" href="#"><i class="icon icon-file"></i> Share an Update</a>
 </li>
 <li class="post-type">
 <a class="photos" href="#"><i class="icon icon-camera"></i> Add photos</a>
 </li>
 </ul>
 <div class="share">
 <div class="arrow"></div>
 <div class="panel panel-default">
                      <div class="panel-heading"><i class="fa fa-file"></i> Update Status</div>
                      <div class="panel-body">
                        <div class="">
                            <textarea name="message" cols="40" rows="10" id="status_message" class="form-control message" style="height: 62px; overflow: hidden;" placeholder="What's on your mind ?"></textarea> 
 </div>
                      </div>
 <div class="panel-footer">
 <div class="row">
 <div class="col-md-7">
 <div class="form-group">
 <div class="btn-group">
   <button type="button" class="btn btn-default"><i class="icon icon-map-marker"></i> Location</button>
   <button type="button" class="btn btn-default"><i class="icon icon-picture"></i> Photo</button>
 </div>
 </div>
 </div>
 <div class="col-md-5">
 <div class="form-group">
 <select name="privacy" class="form-control privacy-dropdown pull-left input-sm">
 <option value="1" selected="selected">Public</option>
 <option value="2">Only my friends</option>
 <option value="3">Only me</option>
 </select>                                    
 <input type="submit" name="submit" value="Post" class="btn btn-primary">                               
 </div>
 </div>
 </div>
 </div>
                    </div>
 </div>
 </div>
 </form>
 </div>
 </div>
 </div> 
  </body>
</html>