<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User List</title>
</head>
<body>
	<h3>사용자 목록</h3>
	<a href="/user/register">사용자 등록</a>
	<hr>
	<table border="1">
		<tr>
			<th>UID</th><th>비밀번호</th><th>이름</th>
			<th>EMAIL</th><th>등록일자</th><th>탈퇴여부</th>
		</tr>
		<c:forEach var="user" items="${userList}">
		<tr>
			<td>${user.uid}</td>
			<td>${user.pwd}</td>
			<td>${user.uname}</td>
			<td>${user.email}</td>
			<td>${user.regDate}</td>
			<td>${user.isDeleted}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>