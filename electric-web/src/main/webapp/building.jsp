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
<!-- map -->
<script src="http://map.qq.com/api/js?v=2.exp&libraries=drawing,geometry,autocomplete,convertor" type="text/javascript"></script>

<script type="text/javascript">
$(document).ready(function() {
  $('#myTab a:first').tab('show');
  var center = new qq.maps.LatLng(30.507902, 114.413659);
  var map = new qq.maps.Map(document.getElementById('bar-chart'), {
    // 地图的中心地理坐标。
    center: center,
    zoom:16,
    mapTypeControl:false,
    keyboardShortcuts:false
  });
  var latlngBounds = new qq.maps.LatLngBounds();
  var searchService = new qq.maps.SearchService({
    complete : function(results){
      var pois = results.detail.pois;
      var poi = pois[0];
      latlngBounds.extend(poi.latLng);  
      var marker = new qq.maps.Marker({
          map:map,
          position: poi.latLng
      });
      marker.setTitle('华中科技大学 ${building.name}');
      map.fitBounds(latlngBounds);
    }
  });
  setTimeout(function() {
    var keyword = '华中科技大学 ${building.name}';
    var region = '武汉';
    searchService.setLocation(region);
    searchService.search(keyword);
  }, 1000);
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
  <section class="col-lg-6 connectedSortable">
    <!-- Box (with bar chart) -->
    <div class="box box-danger" id="loading-example">
      <div class="box-header">
        <!-- tools box -->
        <div class="pull-right box-tools">
          <button class="btn btn-danger btn-sm refresh-btn" data-toggle="tooltip" title="Reload">
            <i class="fa fa-refresh"></i>
          </button>
          <button class="btn btn-danger btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse">
            <i class="fa fa-minus"></i>
          </button>
          <button class="btn btn-danger btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove">
            <i class="fa fa-times"></i>
          </button>
        </div>
        <!-- /. tools -->
        <i class="fa fa-cloud"></i>
        <h3 class="box-title">Server Load</h3>
      </div>
      <!-- /.box-header -->
      <div class="box-body no-padding">
        <div class="row">
          <div class="col-sm-12">
            <!-- bar chart -->
            <div class="chart" id="bar-chart" style="height: 300px;">
            </div>
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row - inside box -->
      </div>
      <!-- /.box-body -->
      <div class="box-footer">
        <div class="row">
          <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
            <input type="text" class="knob" data-readonly="true" value="80" data-width="60" data-height="60" data-fgcolor="#f56954">
            <div class="knob-label">CPU</div>
          </div>
          <!-- ./col -->
          <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
            <input type="text" class="knob" data-readonly="true" value="50" data-width="60" data-height="60" data-fgcolor="#00a65a">
            <div class="knob-label">Disk</div>
          </div>
          <!-- ./col -->
          <div class="col-xs-4 text-center">
            <input type="text" class="knob" data-readonly="true" value="30" data-width="60" data-height="60" data-fgcolor="#3c8dbc">
            <div class="knob-label">RAM</div>
          </div>
          <!-- ./col -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.box-footer -->
    </div>
    <!-- /.box -->
  </section>
  <section class="col-lg-6 connectedSortable">
    <div class="panel panel-default" style="border-top: 0px; margin-left: 5px;">
      <ul id="myTab" class="nav nav-tabs">
        <c:forEach var="item" items="${floorMap}">
          <li><a href="#floor_${item.key}" data-toggle="tab">${item.key}层</a></li>
        </c:forEach>
      </ul>
      <div id="myTabContent" class="tab-content">
        <c:forEach var="item" items="${floorMap}">
          <div class="tab-pane fade " id="floor_${item.key}">
            <table data-toggle="table" class="table table-striped">
              <thead>
                <tr>
                  <th>房间号</th>
                  <th>电量</th>
                  <th>抄表时间</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${item.value}" var="it">
                  <tr>
                    <td><a href="${pageContext.request.contextPath}/${building.name}/${it.roomName}">${it.roomName}</a></td>
                    <td>${it.remain}</td>
                    <td><fmt:formatDate value="${it.dateTime}" type="both" /></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:forEach>
      </div>
    </div>
  </section>

</body>
</html>