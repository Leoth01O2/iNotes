iNotes

Ứng dụng Java Web MVC quản lý ghi chú, dùng JDBC MySQL và Strategy Pattern để chuyển đổi giữa lưu CSDL và file text.

CSDL:
- chạy database.sql
- database: inotes_db
- JDBC mặc định: root, password rỗng

Chạy:
1. mvn clean package
2. deploy target/iNotes.war lên Tomcat
3. mở /iNotes/notes

Các lớp chính:
- Note
- NoteManagement
- NoteStrategy
- NoteDBStrategy
- NoteFileStrategy
- NoteServlet

Định dạng file: id|title|content|typeId
