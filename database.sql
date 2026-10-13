CREATE DATABASE IF NOT EXISTS inotes_db;
USE inotes_db;

CREATE TABLE IF NOT EXISTS note_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type_id INT,
    FOREIGN KEY (type_id) REFERENCES note_type(id)
);

INSERT INTO note_type(name, description) VALUES
('Cá nhân', 'Ghi chú cá nhân'),
('Công việc', 'Ghi chú công việc'),
('Học tập', 'Ghi chú học tập');

INSERT INTO notes(title, content, type_id) VALUES
('Họp giao ban đầu tuần', 'Chuẩn bị nội dung cho cuộc họp', 2),
('Ôn tập Design Pattern Servlet', 'Ôn MVC và Strategy Pattern', 3);
