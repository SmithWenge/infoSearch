<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文献列表检索</title>
	<link rel="stylesheet" href="./css/bootstrap.min.css" type="text/css">
</head>
<body>
<body>
	<br/>
	<br/>
	<form class="form-inline" action="${contextPath}/searchByForm" method="post">
		<div class="form-group">
            <label for="type">分类</label>
            <select class="form-control" id="type" name="type">
                <option value="0">全部</option>
                <option value="1">人文</option>
                <option value="2">工程</option>
                <option value="3">农业</option>
                <option value="4">社会</option>
                <option value="5">科技</option>
                <option value="6">管理</option>
            </select>
        </div>
        <div class="form-group">
            <label for="origin">来源</label>
            <select class="form-control" id="origin" name="origin">
                <option value="0">全部</option>
                <option value="1">未知</option>
                <option value="2">个人</option>
                <option value="3">学校</option>
            </select>
        </div>
        <div class="form-group">
            <label for="auther">作者（精确查询）</label>
            <input type="text" class="form-control" id="auther" name="auther">
        </div>
        <div class="form-group">
            <label for="startDate" class="control-label">发表时间</label>
            <input type="date" class="form-control" id="startDate" name="startDate" style="width:120px" value="${sessionScope.deviceSearch.startDate}">
        </div>
        <div class="form-group">
            <label for="stopDate" class="control-label">至</label>
            <input type="date" class="form-control" id="stopDate" name="stopDate" style="width:120px" value="${sessionScope.deviceSearch.stopDate}">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">检索</button>
        </div>
	</form>
	<br/>
	<form class="form-inline" action="${contextPath}/searchByName" method="post">
        <div class="form-group">
            <label for="auther">作者（模糊查询）</label>
            <input type="text" class="form-control" id="auther" name="auther">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">检索</button>
        </div>
	</form>
	<br/>
	<form class="form-inline" action="searchByResume" method="post">
        <div class="form-group">
            <label for="content">摘要（模糊查询）</label>
            <input type="text" class="form-control" id="content" name="content">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-default">检索</button>
        </div>
	</form>
	<br/>
	<h1 style="text-align: left">文献信息列表</h1>
	<table class="table table-bordered">
		<tr>
			<td> 
				<c:if test="${empty requestScope.infos}">
					沒有信息
				</c:if>
				<c:if test="${!empty requestScope.infos}">
					<table class="table table-bordered">
						<tr>
							<th>序号</th>
							<th>类别</th>
							<th>作者</th>
							<th>文献来源</th>
							<th>出版日期</th>
							<th>题目</th>
							<th>摘要</th>
						</tr>
						<c:forEach items="${requestScope.infos}" var="c" varStatus="vs">
							<tr>
								<td>${vs.index + 1}</td>
								<td>${c.typeName}</td>
								<td>${c.auther}</td>
								<td>${c.origin}</td>
								<td>${c.publishDate}</td>
								<td>${c.title}</td>
								<td>${c.resume}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>