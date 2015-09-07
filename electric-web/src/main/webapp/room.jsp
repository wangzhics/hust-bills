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
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery js -->
<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
<!-- raphael.js -->
<script src="${pageContext.request.contextPath}/static/raphael-2.1.2/raphael-min.js" type="text/javascript"></script>
<!-- prettify.js -->
<script src="${pageContext.request.contextPath}/static/prettify-r224/prettify.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/prettify-r224/prettify.min.css" rel="stylesheet">
<!-- morris.js -->
<script src="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/morris.css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/static/morris.js-0.5.1/example.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/morris.js-0.5.1/example.css" rel="stylesheet" >
</head>
<body>
<h1>Formatting Dates YYYY-MM-DD</h1>
<div id="graph"></div>
<pre id="code" class="prettyprint linenums">
/* data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type */
var day_data = [
  {"period": "2012-10-01", "licensed": 3407, "sorned": 660},
  {"period": "2012-09-30", "licensed": 3351, "sorned": 629},
  {"period": "2012-09-29", "licensed": 3269, "sorned": 618},
  {"period": "2012-09-20", "licensed": 3246, "sorned": 661},
  {"period": "2012-09-19", "licensed": 3257, "sorned": 667},
  {"period": "2012-09-18", "licensed": 3248, "sorned": 627},
  {"period": "2012-09-17", "licensed": 3171, "sorned": 660},
  {"period": "2012-09-16", "licensed": 3171, "sorned": 676},
  {"period": "2012-09-15", "licensed": 3201, "sorned": 656},
  {"period": "2012-09-10", "licensed": 3215, "sorned": 622}
];
Morris.Line({
  element: 'graph',
  data: day_data,
  xkey: 'period',
  ykeys: ['licensed', 'sorned'],
  labels: ['Licensed', 'SORN']
});
</pre>
</body>

</html> 