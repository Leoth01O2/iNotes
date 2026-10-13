<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Chi tiết ghi chú</title></head>
<body>
<h2>${note.title}</h2>
<p>Phân loại: ${note.typeName}</p>
<p>${note.content}</p>
<a href="${pageContext.request.contextPath}/notes">Quay lại</a>
</body>
</html>
