<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="电费查询入口">
<meta name="author" content="WangZ">
<title>电费查询</title>
<!-- jquery -->
<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
<!-- jquery.countdown -->
<script src="${pageContext.request.contextPath}/static/jquery.countdown-2.1.0/jquery.countdown.min.js" type="text/javascript"></script>
<!-- angularJS -->
<script src="${pageContext.request.contextPath}/static/angular-1.4.5/angular.min.js" type="text/javascript"></script>
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet" type='text/css' />
<!-- Full screen slider -->
<link href="${pageContext.request.contextPath}/static/other/index/full-screen-slider.css" rel="stylesheet" type='text/css' />
<script src="${pageContext.request.contextPath}/static/other/index/full-screen-slider.js"  type="text/javascript" ></script>
<!-- land -->
<link href="${pageContext.request.contextPath}/static/other/index/land.css" rel="stylesheet"  type="text/css" />
<script type="text/javascript">
var app = angular.module('buildingApp', []);
app.controller('buildingCtrl', function($scope, $http) {
  $http.get("${pageContext.request.contextPath}/api/building").success(function (response) {
    $scope.datas = response;
  });
  // process the form
  $scope.processForm = function() {
    window.location.href = "${pageContext.request.contextPath}/" + $scope.selectedBuilding + "/" + $scope.inputRoom;
  };
});
</script>
<style type="text/css">
select.ng-dirty.ng-invalid,
input.ng-dirty.ng-invalid {
    border-color: #e9322d;
    -webkit-box-shadow: 0 0 6px #f8b9b7;
    -moz-box-shadow: 0 0 6px #f8b9b7;
    box-shadow: 0 0 6px #f8b9b7;
}
</style>
</head>
<body>
  <div class="full-screen-slider">
    <!-- Put the list of images you want to slide in full screen here.
       The image which has the class 'active' will be the starting slide.
       -->
    <img src="${pageContext.request.contextPath}/static/other/index/slider/1.jpg" alt="1" class="active"> 
    <img src="${pageContext.request.contextPath}/static/other/index/slider/2.jpg" alt="2"> 
    <img src="${pageContext.request.contextPath}/static/other/index/slider/3.jpg" alt="3"> 
    <img src="${pageContext.request.contextPath}/static/other/index/slider/4.jpg" alt="4">
  </div>
  <div class="container transparent">
    <div class="row">
      <div class="col-md-12 text-center">
        <strong id="logo" style="font-style: normal;">电费查询</strong>
        <h2 style="margin-bottom: 1em;">让您的电费更直观，更详细</h2>
      </div>
      <!-- /.col-md-12 -->
    </div>
    <!-- /.row -->
    <div class="row" style="font-size: 16px;">
      <div class="col-md-12 text-center">
      <div ng-app="buildingApp">
      <div ng-controller="buildingCtrl">
        <form id="roomForm" name="roomForm"  novalidate class="form-inline" role="form" style="margin-top: 2em;" ng-submit="processForm()">
          <div class="form-group">
            <label class="sr-only" for="area">楼区</label> 
            <select id="area" required class="form-control" ng-model="selectedArea" ng-change="selectedBuilding=''" ng-options="area for (area, buildings) in datas">
              <option value="">-请选择楼区-</option>
            </select>
          </div>
          <div class="form-group">
            <label class="sr-only" for="buillding">楼栋</label> 
            <select id="buillding" name="building" required class="form-control"  ng-model="selectedBuilding"  ng-change="inputRoom=''" ng-options="name for name in selectedArea">
              <option value="">-请选择楼栋-</option>
            </select>
          </div>
          <div class="form-group">
            <label class="sr-only" for="room">房间号</label> 
            <input id="room" name="room" type="number" required ng-pattern="/^[0-9]{3}$/" class="form-control" ng-model="inputRoom" placeholder="请输入房间号">
          </div>
          <button type="submit" class="btn btn-success" ng-disabled="roomForm.$invalid">查询</button>
        </form>
      </div> 
      </div>
      </div>
    </div>
    <div class="row" style="margin-top: 3em; margin-bottom: 3em;">
      <div class="col-md-12 text-center" id="links">
        <p>
          <a href="http://coverstrap.com"> <span class="glyphicon glyphicon-home"></span> From Coverstrap
          </a> <span class="separator">&middot;</span> <a href="#"> <span class="glyphicon glyphicon-user"></span> About Us
          </a> <span class="separator">&middot;</span> <a href="#"> <span class="glyphicon glyphicon-thumbs-up"></span> Like Us
          </a> <span class="separator">&middot;</span> <a href="#"> <span class="glyphicon glyphicon-envelope"></span> Contact Us
          </a>
        </p>
      </div>
    </div>
    <!-- /.row -->
  </div>
  <!-- /.container -->
</body>
</html>
