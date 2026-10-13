<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm/Sửa ghi chú</title>
<style>
body{font-family:Arial;max-width:650px;margin:40px auto} label{display:block;margin-top:15px}
input,select,textarea{width:100%;padding:8px;box-sizing:border-box} textarea{height:150px}
button{margin-top:15px;padding:8px 14px}
</style>
</head>
<body>
<h2>${note == null ? 'Thêm Mới Ghi Chú' : 'Sửa Ghi Chú'}</h2>
<form action="${pageContext.request.contextPath}/notes" method="post">
<input type="hidden" name="action" value="${note == null ? 'create' : 'update'}">
<c:if test="${note != null}">
<input type="hidden" name="id" value="${note.id}">
</c:if>
<label>Tiêu đề:</label>
<input name="title" value="${note.title}" required>
<label>Phân loại:</label>
<select name="typeId">
<c:forEach var="type" items="${types}">
<option value="${type.id}" ${note != null && note.typeId == type.id ? 'selected' : ''}>${type.name}</option>
</c:forEach>
</select>
<label>Nội dung:</label>
<textarea name="content">${note.content}</textarea>
<button type="submit">Lưu Ghi Chú</button>
<a href="${pageContext.request.contextPath}/notes">Hủy bỏ</a>
</form>
</body>
</html>
