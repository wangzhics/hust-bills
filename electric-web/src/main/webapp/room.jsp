<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="宿舍电费详情页">
<meta name="author" content="WangZ">
<title>宿舍电费</title>
<!-- Bootstrap css -->
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet" type='text/css'>
<!-- angular-chart js -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/angular-chart.js/angular-chart.css" rel="stylesheet" type='text/css'>
<!-- angularJS -->
<script src="${pageContext.request.contextPath}/static/angular-1.4.5/angular.min.js" type="text/javascript"></script>
<!-- angular-chart js -->
<script src="${pageContext.request.contextPath}/static/Chart.js-1.0.2/Chart.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/angular-chart.js/angular-chart.js" type="text/javascript"></script>
</head>
<body onload="init()">
<canvas id="myChart" width="400" height="400"></canvas>
</body>  

<script type="text/javascript">
var data = {
      labels : ["January","February","March","April","May","June","July"],
      datasets : [
        {
          fillColor : "rgba(220,220,220,0.5)",
          strokeColor : "rgba(220,220,220,1)",
          pointColor : "rgba(220,220,220,1)",
          pointStrokeColor : "#fff",
          data : [65,59,90,81,56,55,40]
        },
        {
          fillColor : "rgba(151,187,205,0.5)",
          strokeColor : "rgba(151,187,205,1)",
          pointColor : "rgba(151,187,205,1)",
          pointStrokeColor : "#fff",
          data : [28,48,40,19,96,27,100]
        }
      ]
    }
    
function init(){
//Get the context of the canvas element we want to select
var ctx = document.getElementById("myChart").getContext("2d");
var myNewChart = new Chart(ctx).PolarArea(data);
}
</script>

</html> 