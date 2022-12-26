<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 등록</title>
</head>
<body>
	<h3>사용자 등록</h3>
	<hr>
	<form action="/user/register" method="post">
		<table>
			<tr>
				<td>UID</td>
				<td><input type="text" name="uid"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password2" name="pwd2"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="uname"></td>
			</tr>
			<tr>
				<td>EMAIL</td>
				<td><input type="email" name="email"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="제출"></td>
			</tr>
		</table>
	</form>
</body>
</html>