<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="宿舍楼详情页" />
<meta name="author" content="WangZ" />
<title>${buildingName}</title>
<!-- jquery js -->
<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js" type="text/javascript" /></script>
<!-- font-awesome -->
<link href="${pageContext.request.contextPath}/static/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
<!-- ionicons -->
<link href="${pageContext.request.contextPath}/static/ionicons-2.0.1/css/ionicons.css" rel="stylesheet" />
<!-- raphael.js -->
<script src="${pageContext.request.contextPath}/static/raphael-2.1.2/raphael-min.js" type="text/javascript"></script>
<!-- prettify.js -->
<!--
<script src="${pageContext.request.contextPath}/static/prettify-r224/prettify.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/prettify-r224/prettify.min.css" rel="stylesheet"/>
 -->
<!-- morris.js -->
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/legend.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.js" type="text/javascript"></script>
<!-- Moment.js -->
<script src="${pageContext.request.contextPath}/static/Moment.js-2.10.6/moment-with-locales.js" type="text/javascript" /></script>
<!-- bootstrap-table -->
<link href="${pageContext.request.contextPath}/static/bootstrap-table-1.8.1/bootstrap-table.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/bootstrap-table-1.8.1/bootstrap-table.min.js" type="text/javascript"></script>
<!-- AdminLTE -->
<link href="${pageContext.request.contextPath}/static/other/room/AdminLTE.css" rel="stylesheet" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${buildingName}</title>
</head>
<body style="padding: 10px;">
<section class="content-header">
  <h1>
    ${room.buildingName}<small>信息概览</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"><i class="fa fa-home"></i></a></li>
    <li><i class="fa fa-building"></i>${room.buildingName}</li>
  </ol>
</section>
  <section class="content" style="padding: 0px;">
    <div class="row">
      <div class="col-lg-8" >
      <div class="panel panel-default" style="border-top: 0px; margin-left: 5px;">
<ul id="myTab" class="nav nav-tabs" >
 <li class="active"><a href="#home" data-toggle="tab">Home</a></li>
 <li><a href="#profile" data-toggle="tab">Profile</a></li>
</ul>
<div id="myTabContent" class="tab-content">
 <div class="tab-pane fade in active" id="home">
  <p>Content of home</p>
 </div>
 <div class="tab-pane fade" id="profile">
  <p>Content of profile</p>
 </div> 
 </div>
      </div>
      
      </div>
    <div class="col-lg-4">
      <!-- /.panel -->
      <div class="chat-panel panel panel-default">
        <div class="panel-heading">
          <i class="fa fa-comments fa-fw"></i> Chat
          <div class="btn-group pull-right">
            <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-chevron-down"></i>
            </button>
            <!-- 
            <ul class="dropdown-menu slidedown">
              <li><a href="#"> <i class="fa fa-refresh fa-fw"></i> Refresh
              </a></li>
              <li><a href="#"> <i class="fa fa-check-circle fa-fw"></i> Available
              </a></li>
              <li><a href="#"> <i class="fa fa-times fa-fw"></i> Busy
              </a></li>
              <li><a href="#"> <i class="fa fa-clock-o fa-fw"></i> Away
              </a></li>
              <li class="divider"></li>
              <li><a href="#"> <i class="fa fa-sign-out fa-fw"></i> Sign Out
              </a></li>
            </ul>
             -->
          </div>
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">
          <ul class="chat">
            <li class="left clearfix"><span class="chat-img pull-left"> <img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
                class="img-circle">
            </span>
              <div class="chat-body clearfix">
                <div class="header">
                  <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted"> <i class="fa fa-clock-o fa-fw"></i>
                    12 mins ago
                  </small>
                </div>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.</p>
              </div></li>
            <li class="right clearfix"><span class="chat-img pull-right"> <img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
                class="img-circle">
            </span>
              <div class="chat-body clearfix">
                <div class="header">
                  <small class=" text-muted"> <i class="fa fa-clock-o fa-fw"></i> 13 mins ago
                  </small> <strong class="pull-right primary-font">Bhaumik Patel</strong>
                </div>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.</p>
              </div></li>
            <li class="left clearfix"><span class="chat-img pull-left"> <img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
                class="img-circle">
            </span>
              <div class="chat-body clearfix">
                <div class="header">
                  <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted"> <i class="fa fa-clock-o fa-fw"></i>
                    14 mins ago
                  </small>
                </div>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.</p>
              </div></li>
            <li class="right clearfix"><span class="chat-img pull-right"> <img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
                class="img-circle">
            </span>
              <div class="chat-body clearfix">
                <div class="header">
                  <small class=" text-muted"> <i class="fa fa-clock-o fa-fw"></i> 15 mins ago
                  </small> <strong class="pull-right primary-font">Bhaumik Patel</strong>
                </div>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.</p>
              </div></li>
          </ul>
        </div>
        <!-- /.panel-body -->
        <div class="panel-footer">
          <div class="input-group">
            <input id="btn-input" type="text" class="form-control input-sm" placeholder="Type your message here..."> <span
              class="input-group-btn">
              <button class="btn btn-warning btn-sm" id="btn-chat">Send</button>
            </span>
          </div>
        </div>
        <!-- /.panel-footer -->
      </div>
      <!-- /.panel .chat-panel -->
    </div>
    </div>
  </section>
</body>
</html>