<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>google英译汉简化版</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
       
 
      <!-- HTML5 Shiv 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
      <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
      <!--[if lt IE 9]>
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
      <![endif]-->
            <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
       	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"> </script>
	<script>
	function detail(){
		//$("#result").css("display","none");
		$("#forma").attr("action", "detail");
		$('#forma').submit();
	
	}
	function search(){
		if($("#name")==null||$("#name")==''){
			return;
		}
		$("#forma").attr("action", "search");
		$('#forma').submit();
	
	}
	</script>
   </head>
   <body>
	<div class="container" style="background-color: #dedef8;">
		<div class="row"></div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-4">
				<h4><a href="http://www.google.com">google</a></h4>
				<form id="forma"  action="search" method="post">
					<div class="form-group">
						<input type="text" class="form-control" id="name"
							placeholder="请输入名称" name="word" >
					</div>

					<button type="button" class="btn btn-default " onclick="search()">提交</button>
					<button type="button" class="btn btn-default " onclick="detail()">明细</button>
				</form>

			</div>
		</div>

		<div class="row" id="result">
			<div class="col-md-2"></div>
			<div class="col-md-10">
				<table class="table table-condensed">
					<tr>
						<td></td>
						<td>单词</td>
						<td>解释</td>
						<td>次数</td>
						<td>首次查询时间</td>
						<td>上次查询时间</td>
					</tr>
					<tr>
						<td>google解释</td>
						<td>${word}</td>
						<td>${r.googleExplain==null?"":r.googleExplain.explanation }</td>
						<td></td>
						<td></td><td></td>
					</tr>
					<tr>
						<td>上次查询</td>
						<td>${word}</td>
						<td>${r.lastSearchExplain==null?"":r.lastSearchExplain.explanation }</td>
						<td>${r.lastSearchExplain==null?"":r.lastSearchExplain.count }</td>
						<td>${r.lastSearchExplain==null?"":r.lastSearchExplain.searchdate }</td>
						<td>${r.lastSearchExplain==null?"":r.lastSearchExplain.newsearchdate }</td>
					</tr>
				</table>
			</div>
		</div>


	</div>



	<div class="container" style="background-color: #dedef8;">

		<div class="row">
				 <div class="form-group col-md-2"></div>
				<form id=""  action="" method="post">
					<div class="form-group col-md-2">
						<input type="text" class="form-control" id="name" placeholder="请输入名称" name="word" >
					</div>
					
					<div class="form-group col-md-2">
					<select class="form-control">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
					</div>
					
					<div class="form-group col-md-2">
						<input type="text" class="form-control" id=" " placeholder="请输入名称" name=" " >
					</div>
					
					<button type="button" class="btn btn-default " onclick="search()">提交</button>
					<button type="button" class="btn btn-default " onclick="detail()">明细</button>
				</form>

			
		</div>

		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-10">
				<table class="table table-condensed">
					 <tr>
						<td></td>
						<td>单词:${fn:length(list) }</td>
						<td>解释</td>
						<td>次数</td>
						<td>首次查询时间</td>
						<td>上次查询时间</td>
					</tr>
							<c:forEach var="wd" items="${list}" >
					    <tr>
							<td>查询历史</td>
							<td>${wd.word}</td>
							<td>${wd.explanation}</td>
							<td>${wd.count} </td>
							<td>${wd.searchdate} </td>
							<td>${wd.newsearchdate} </td>
						</tr>
				    
				    
						</c:forEach>
					
				</table>
			</div>
		</div>


	</div>

</body>
</html>