<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core" %>
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
<!-- timeline -->
<link href="${pageContext.request.contextPath}/static/other/building/timeline.css" rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function() {
  $('#myTab a:first').tab('show');
});
</script>
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
    <li><a href="${pageContext.request.contextPath}/"><i class="fa fa-home"></i></a></li>
    <li><i class="fa fa-building"></i>${building.name}</li> 
  </ol>
</section>
  <section class="content" style="padding: 0px;">
    <div class="row">
     <div class="col-lg-6" >
      <div class="panel panel-default" style="border-top: 0px; margin-left: 5px;">
      <ul id="myTab" class="nav nav-tabs" >
        <c:forEach var="item" items="${floorMap}">
          <li><a href="#floor_${item.key}" data-toggle="tab">${item.key}层</a></li>
        </c:forEach>
      </ul>
      <div id="myTabContent" class="tab-content">
        <c:forEach var="item" items="${floorMap}">
          <div class="tab-pane fade " id="floor_${item.key}">
          <table data-toggle="table" class="table table-striped">
            <thead><tr><th>房间号</th><th>电量</th><th>抄表时间</th></tr></thead>
            <tbody>
              <c:forEach items="${item.value}" var="it">
                <tr><td><a href="${pageContext.request.contextPath}/${building.name}/${it.roomName}">${it.roomName}</a>
                </td><td>${it.remain}</td><td><fmt:formatDate value="${it.dateTime}" type="both"/></td></tr>
              </c:forEach>
            </tbody>
            </table>
            </div>
        </c:forEach>
      </div>
      </div>
     </div>
      <div class="col-lg-6">
        <div class="panel panel-default">
          <div class="panel-heading">
            <i class="fa fa-clock-o fa-fw"></i> Timeline
          </div>
          <!-- /.panel-heading -->
          <div class="panel-body">
            <ul class="timeline">
              <li>
                <div class="timeline-badge">
                  <i class="fa fa-check"></i>
                </div>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                    <p>
                      <small class="text-muted"><i class="fa fa-time"></i> 11 hours ago via Twitter</small>
                    </p>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
              <li class="timeline-inverted">
                <div class="timeline-badge warning">
                  <i class="fa fa-credit-card"></i>
                </div>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
              <li>
                <div class="timeline-badge danger">
                  <i class="fa fa-credit-card"></i>
                </div>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
              <li class="timeline-inverted">
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
              <li>
                <div class="timeline-badge info">
                  <i class="fa fa-save"></i>
                </div>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                    <hr>
                    <div class="btn-group">
                      <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-cog"></i> <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                      </ul>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
              <li class="timeline-inverted">
                <div class="timeline-badge success">
                  <i class="fa fa-thumbs-up"></i>
                </div>
                <div class="timeline-panel">
                  <div class="timeline-heading">
                    <h4 class="timeline-title">Timeline Event</h4>
                  </div>
                  <div class="timeline-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel justo eu mi scelerisque vulputate. Aliquam in metus eu
                      lectus aliquet egestas.</p>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <!-- /.panel-body -->
        </div>
      </div>
    </div>
  </section>
</body>
</html>