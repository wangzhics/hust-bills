<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="宿舍电费详情页" />
<meta name="author" content="WangZ" />
<title>宿舍电费</title>
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
<script src="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/legend.css" rel="stylesheet" />
<!-- Moment.js -->
<script src="${pageContext.request.contextPath}/static/Moment.js-2.10.6/moment-with-locales.js" type="text/javascript" /></script>
<!-- AdminLTE -->
<link href="${pageContext.request.contextPath}/static/other/room/AdminLTE.css" rel="stylesheet" />

<script type="text/javascript">
var week_data = new Array();
var week_graph = null;

function initWeekData() {
	  var week_ago = moment();
	  week_ago.add(-6, 'days');
	  for(var i = 0; i < 7; i++) {
		  var date = new Object();
		  week_ago.add(1, 'days');
		  date.period = week_ago.format("YYYY-MM-DD");
		  week_data[i] = date;
	  }
}

function showGraph() {
	week_graph = Morris.Line({
	    element: 'graphDiv',
	    data: week_data,
	    xkey: 'period',
	    ykeys: ['cousume', 'average'],
	    labels: ['${room.roomName}', '${room.buildingName}'],
	    hideHover: 'auto',
	    resize: true
	  });
}

function getWeekData() {
	var roomReady = false, readyAverage = false;
	$.ajax({
	    url: '${pageContext.request.contextPath}/api/consume/${room.buildingName}/${room.roomName}/week',
	    type: 'get',
	    dataType: 'json',
	    success: function(result) {
	      var dataMap = new Object();
	      for(index in result) {
	        var date = moment(result[index].date);
	        var periodStr = moment(result[index]).format("YYYY-MM-DD");
	        dataMap[date.format("YYYY-MM-DD")]=result[index].consume;
	      }
	      for(index in week_data) {
	        var period = week_data[index].period;
	        var consume = dataMap[period];
	        if(consume != null) {
	          week_data[index].cousume = consume;
	        }
	      }
	      roomReady = true;
	    }
	  });
	$.ajax({
	      url: '${pageContext.request.contextPath}/api/consume/${room.buildingName}/week/avg',
	      type: 'get',
	      dataType: 'json',
	      success: function(result) {
	        var dataMap = new Object();
	        for(index in result) {
	          var date = moment(result[index].date);
	          var periodStr = moment(result[index]).format("YYYY-MM-DD");
	          dataMap[date.format("YYYY-MM-DD")]=result[index].average;
	        }
	        for(index in week_data) {
	          var period = week_data[index].period;
	          var average = dataMap[period];
	          if(average != null) {
	            week_data[index].average = average;
	          }
	        }
	        readyAverage = true;
	      }
	    });
	var showGraphTimer = window.setTimeout(function() {
		if(!(roomReady && readyAverage)) {
			return;
		}
		showGraph();
		window.clearTimeout(showGraphTimer);
	},400);
}

$(document).ready(function(){
	$('.dropdown-toggle').dropdown();
	initWeekData();
	getWeekData();
});
</script> 
</head>
<body>
  <section class="content-header">
    <h1>
      ${room.buildingName}-${room.roomName} <small>信息概览</small>
    </h1>
    <ol class="breadcrumb">
     <li><a href="#"><i class="fa fa-home"></i></a></li>
      <li><a href="#"><i class="fa fa-building"></i>${room.buildingName}</a></li>
      <li class="active">${room.roomName}</li>
    </ol>
  </section>
  <section class="content">
  <div class="row">
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>150</h3>
          <p>剩余电量</p>
        </div>
        <div class="icon">
          <i class="ion ion-pie-graph"></i>
        </div>
        <a href="#" class="small-box-footer"> More info <i class="fa fa-arrow-circle-right"></i>
        </a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-aqua">
        <div class="inner">
          <h3>
            53<sup style="font-size: 20px"> of 100</sup>
          </h3>
          <p>一周排名</p>
        </div>
        <div class="icon">
          <i class="ion ion-stats-bars"></i>
        </div>
        <a href="#" class="small-box-footer"> More info <i class="fa fa-arrow-circle-right"></i>
        </a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <div class="small-box bg-teal">
        <div class="inner">
          <h3>0</h3>
          <p>关注人数</p>
        </div>
        <div class="icon">
          <i class="ion ion-ios-people-outline"></i>
        </div>
        <a href="#" class="small-box-footer"> More info <i class="fa fa-arrow-circle-right"></i>
        </a>
      </div>
      </div>
      <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-red">
        <div class="inner">
          <h3>--</h3>
          <p>可用天数</p>
        </div>
        <div class="icon">
          <i class="ion ion-pie-graph"></i>
        </div>
        <a href="#" class="small-box-footer"> More info <i class="fa fa-arrow-circle-right"></i>
        </a>
      </div>
    </div>
    <!-- ./col -->
  </div>
  <div class="row">
  <div class="col-lg-12"> 
  <div class="panel panel-default">
    <div class="panel-heading">
      <i class="fa fa-bar-chart-o fa-fw"></i> Area Chart Example
      <div class="pull-right">
        <div class="btn-group">
          <button id="graphDropdown" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
            Actions <span class="caret"></span>
          </button>
          <ul class="dropdown-menu pull-right" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </div>
      </div>
    </div>
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body" style="clear: both;">
          <div>
            <div id="graphDiv" style="position: relative;" ></div>
            <div>
              <div class="legend-content"></div>
              <table class="legend-table">
                <tbody>
                  <tr>
                    <td><div class="legendColorBox-border"><div class="legendColorBox1"></div></div></td>
                    <td class="legendLabel">${room.roomName}</td>
                  </tr>
                  <tr>
                    <td><div class="legendColorBox-border"><div class="legendColorBox2"></div></div></td>
                    <td class="legendLabel">${room.buildingName}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
    <div class="panel-footer">
    <small class="pull-right">
      <i class="fa fa-clock-o"> </i>2015.02.30更新
    </small>
    </div>
  </div>
  </div>
  <div class="row">
  <div class="col-xs-6 connectedSortable">
  <div class="box box-danger" style="border-top-color:#079C6C;">
    <div class="box-header">
      <!-- tools box -->
      <div class="pull-right box-tools">
        <button class="btn btn-danger btn-sm refresh-btn" data-toggle="tooltip" title="Refresh" style="background-color:#079C6C;border-color:#3c8dbc">
          <i class="fa fa-refresh"></i>
        </button>
      </div>
      <!-- /. tools -->
      <i class="fa fa-plug"></i>
      <h3 class="box-title">电费剩余记录</h3>
    </div>
    <div class="box-body no-padding">
      <div class="table-responsive">
        <table class="table table-striped">
          <tbody>
            <tr>
              <th>Country</th>
              <th>Visitors</th>
              <th>Online</th>
              <th>Page Views</th>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    </div>
  </div>
  <div class="col-xs-6 connectedSortable">
  <div class="box box-primary;">
    <div class="box-header">
      <!-- tools box -->
      <div class="pull-right box-tools">
        <button class="btn btn-primary btn-sm pull-right" data-widget="collapse" data-toggle="tooltip" title="Collapse"
          style="margin-right: 5px;">
          <i class="fa fa-close"></i>
        </button>
      </div>
      <!-- /. tools -->

      <i class="fa fa-credit-card"></i>
      <h3 class="box-title">充值记录</h3>
    </div>
    <div class="box-body no-padding">
      <div class="table-responsive">
        <table class="table table-striped">
          <tbody>
            <tr>
              <th>Country</th>
              <th>Visitors</th>
              <th>Online</th>
              <th>Page Views</th>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    </div>
  </div>
  </div>
  </section>
</body>
</html>
