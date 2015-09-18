<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="宿舍电费详情页" />
<meta name="author" content="WangZ" />
<title>${room.buildingName}-${room.roomName}</title>
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

<script type="text/javascript">

var graphMorris = null;
var isSettingGraph = false;

function initGraph() {
  graphMorris = Morris.Line({
    element : 'graphDiv',
    xkey : 'period',
    ykeys : [ 'cousume', 'average' ],
    labels : [ '${room.roomName}', '${room.buildingName}' ],
    hideHover : 'auto',
    resize : true
  });
  $('#graphDiv svg').height(260);
}

function resetGraph(periodType, gap) {
  isSettingGraph = true;
  var roomReady = false, readyAverage = false;
  var graph_data = new Array();
  var ago = moment();
  ago.subtract(gap + 1, 'days');
  for (var i = 0; i < gap; i++) {
    var data = new Object();
    ago.add(1, 'days');
    data.period = ago.format("YYYY-MM-DD");
    graph_data[i] = data;
  }
  $.ajax({
    url : '${pageContext.request.contextPath}/api/consume/${room.buildingName}/${room.roomName}/'
        + periodType,
    type : 'get',
    dataType : 'json',
    success : function(result) {
      var dataMap = new Object();
      for (index in result) {
        var date = moment(result[index].date);
        var periodStr = moment(result[index]).format("YYYY-MM-DD");
        dataMap[date.format("YYYY-MM-DD")] = result[index].consume;
      }
      for (index in graph_data) {
        var period = graph_data[index].period;
        var consume = dataMap[period];
        if (consume != null) {
          graph_data[index].cousume = consume;
        }
      }
      roomReady = true;
    }
  });
  $.ajax({
    url : '${pageContext.request.contextPath}/api/consume/${room.buildingName}/'
        + periodType + '/avg',
    type : 'get',
    dataType : 'json',
    success : function(result) {
      var dataMap = new Object();
      for (index in result) {
        var date = moment(result[index].date);
        var periodStr = moment(result[index]).format("YYYY-MM-DD");
        dataMap[date.format("YYYY-MM-DD")] = result[index].average;
      }
      for (index in graph_data) {
        var period = graph_data[index].period;
        var average = dataMap[period];
        if (average != null) {
          graph_data[index].average = average.toFixed(2);
        }
      }
      readyAverage = true;
    }
  });
  var showGraphTimer = window.setTimeout(function() {
    if (!(roomReady && readyAverage)) {
      return;
    }
    window.clearTimeout(showGraphTimer);
    graphMorris.setData(graph_data, true);
    isSettingGraph = false;
  }, 400);
}

function resetPeriodGraph(periodType) {
  if (isSettingGraph) {
    return;
  }
  var currentPerid = $("#graphTitleLabel").val();
  if (periodType == 'week') {
    if (currentPerid == '周') {
      return;
    }
    resetGraph('week', 7);
    $("#graphTitleLabel").text('周');
    return;
  }
  if (periodType == 'month') {
    if (currentPerid == '月') {
      return;
    }
    resetGraph('month', 28);
    $("#graphTitleLabel").text('月');
    return;
  }
}

function remainAjaxRequest(params) {
  $.ajax({
    url : '${pageContext.request.contextPath}/api/remain/${room.buildingName}/${room.roomName}/'
      + params.data.limit + '/'+ params.data.offset,
    type : 'get',
    dataType : 'json',
    success : function(result) {
      params.success(result);
      params.complete();
    },
    error : function(result) {
      params.error(result);
      params.complete();
    }
  });
}

function chargeAjaxRequest(params) {
  $.ajax({
    url : '${pageContext.request.contextPath}/api/charge/${room.buildingName}/${room.roomName}/'
      + params.data.limit + '/'+ params.data.offset,
    type : 'get',
    dataType : 'json',
    success : function(result) {
      params.success(result);
      params.complete();
    },
    error : function(result) {
      params.error(result);
      params.complete();
    }
  });
}

$(document).ready(function() {
  $('.dropdown-toggle').dropdown();
  initGraph();
  resetGraph('week', 7);
});
</script>
</head>
<body>
  <section class="content-header">
    <h1>
      ${room.buildingName}-${room.roomName} <small>信息概览</small>
    </h1>
    <ol class="breadcrumb">
     <li><a href="${pageContext.request.contextPath}"><i class="fa fa-home"></i></a></li>
      <li><a href="${pageContext.request.contextPath}/${room.buildingName}"><i class="fa fa-building"></i>${room.buildingName}</a></li>
      <li class="active">${room.roomName}</li>
    </ol>
  </section>
  <section class="content">
  <div class="row">
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>${lastRemain.remain}<sup style="font-size: 20px">&nbsp;KW</sup></h3>
          <p>剩余电量，截至<fmt:formatDate value="${lastRemain.dateTime}" type="date"/></p>
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
            ${roomRank.rank}<sup style="font-size: 20px">&nbsp;of ${roomRank.total}</sup>
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
          <h3>${dayCount}<sup style="font-size: 20px">&nbsp;Day</sup></h3>
          <p>可用天数，<fmt:formatNumber value="${roomRank.average}" pattern="0.00"/>KW/Day</p>
        </div>
        <div class="icon">
          <i class="ion ion-flash-off"></i>
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
      <i class="fa fa-bar-chart-o fa-fw"></i>
      <label id="graphTitleLabel">周</label>电量使用记录
      <div class="pull-right">
        <div class="btn-group">
          <button id="graphDropdown" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            时间段 <span class="caret"></span>
          </button>
          <ul class="dropdown-menu pull-right" role="menu">
            <li><a href="javascript:void(0);" onclick="resetPeriodGraph('week')" style="font-size: 8px;">一周</a></li>
            <li><a href="javascript:void(0);"  onclick="resetPeriodGraph('month')" style="font-size: 8px;">一月</a></li>
          </ul>
        </div>
      </div>
    </div>
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body" style="clear: both;">
          <div  id="graphParentDiv">
            <div id="graphDiv" style="heigh:280px;"></div>
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
    <!-- 
      <i class="fa fa-clock-o"> </i><fmt:formatDate value="${lastRemain.dateTime}" type="both"/>更新
     -->
    </small>
    </div>
  </div>
  </div>
  <div class="row">
  <section class="col-lg-4 connectedSortable ui-sortable">
  <div class="box box-danger" style="border-top-color:#079C6C;">
    <div class="box-header">
      <!-- tools box -->
      <div class="pull-right box-tools">
        <button class="btn btn-sm refresh-btn" data-toggle="tooltip" title="Refresh" style="background-color:#079C6C;border-color:#F5E9E9">
          <i class="fa fa-refresh"></i>
        </button>
      </div>
      <!-- /. tools -->
      <i class="fa fa-plug"></i>
      <h3 class="box-title">电费剩余记录</h3>
    </div>
    <div class="box-body no-padding">
      <div class="table-responsive">
        <table class="table table-striped" data-toggle="table" data-ajax="remainAjaxRequest" data-side-pagination="server" 
          data-pagination="true" data-page-size="7" data-page-list="[]">
          <thead>
          <tr>
              <th data-field="dateTime" data-align="center">时间</th>
              <th data-field="remain" data-align="left" >余额</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    </div>
  </section>
  <section class="col-lg-6 connectedSortable ui-sortable">
  <div class="box box-primary;">
    <div class="box-header">
      <!-- tools box -->
      <div class="pull-right box-tools">
        <button class="btn btn-sm refresh-btn" data-toggle="tooltip" title="Refresh" style="background-color:#c1c1c1;border-color:#F5E9E9">
          <i class="fa fa-refresh"></i>
        </button>
      </div>
      <!-- /. tools -->
      <i class="fa fa-credit-card"></i>
      <h3 class="box-title">充值记录</h3>
    </div>
    <div class="box-body no-padding">
      <div class="table-responsive">
        <table  class="table table-striped" data-toggle="table" data-ajax="chargeAjaxRequest" data-side-pagination="server" 
          data-pagination="true" data-page-size="7" data-page-list="[]">
          <thead>
          <tr>
              <th data-field="dateTime" data-align="center" >时间</th>
              <th data-field="chargePower" data-align="left" >充值金额</th>
              <th data-field="chargeMoney" data-align="left" >充值电量</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    </div>
  </section>
  </div>
  </section>
</body>
</html>
