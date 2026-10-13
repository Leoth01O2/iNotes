<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>iNotes Dashboard</title>
<style>
body{font-family:Arial;max-width:900px;margin:40px auto} table{width:100%;border-collapse:collapse;margin-top:20px}
th,td{padding:12px;border-bottom:1px solid #ddd;text-align:left} input{padding:8px;width:280px} button,a{margin-right:8px}
</style>
</head>
<body>
<h2>Danh Sách Ghi Chú (iNotes Dashboard)</h2>
<form action="${pageContext.request.contextPath}/notes" method="get">
<input name="keyword" value="${keyword}" placeholder="Tìm kiếm tiêu đề hoặc nội dung">
<button type="submit">Tìm kiếm</button>
<a href="${pageContext.request.contextPath}/notes?action=create">Thêm Mới</a>
</form>
<p>Chuyển lưu trữ:
<a href="${pageContext.request.contextPath}/notes?storage=db">MySQL</a> |
<a href="${pageContext.request.contextPath}/notes?storage=file">File</a>
</p>
<table>
<tr><th>#</th><th>Tiêu đề</th><th>Phân loại</th><th>Thao tác</th></tr>
<c:forEach var="note" items="${notes}">
<tr>
<td>${note.id}</td>
<td>${note.title}</td>
<td>${note.typeName}</td>
<td>
<a href="${pageContext.request.contextPath}/notes?action=view&id=${note.id}">Xem</a>
<a href="${pageContext.request.contextPath}/notes?action=edit&id=${note.id}">Sửa</a>
<a href="${pageContext.request.contextPath}/notes?action=delete&id=${note.id}" onclick="return confirm('Xóa ghi chú này?')">Xóa</a>
</td>
</tr>
</c:forEach>
</table>
</body>
</html>
