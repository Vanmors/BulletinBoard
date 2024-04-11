-- Создание таблицы "TOPIC"
CREATE TABLE TOPIC (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);
-- Создание таблицы "MESSAGE"
CREATE TABLE MESSAGE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author_Name VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    created_At TIMESTAMP NOT NULL,
    topic_id INT NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES TOPIC(id)
);

-- Заполнение таблицы TOPIC
INSERT INTO TOPIC (title) VALUES ('First topic');
INSERT INTO TOPIC (title) VALUES ('Second topic');
INSERT INTO TOPIC (title) VALUES ('Third topic');

-- Заполнение таблицы Message
INSERT INTO Message (author_Name, text, created_At, topic_id)
VALUES ('John Doe', 'Message 1 for First topic', CURRENT_TIMESTAMP, 1),
       ('Alice Smith', 'Message 2 for First topic', CURRENT_TIMESTAMP, 1),
       ('Bob Johnson', 'Message 1 for Second topic', CURRENT_TIMESTAMP, 2),
       ('Emma Brown', 'Message 2 for Second topic', CURRENT_TIMESTAMP, 2),
       ('Charlie Wilson', 'Message 1 for Third topic', CURRENT_TIMESTAMP, 3),
       ('Ella Davis', 'Message 2 for Third topic', CURRENT_TIMESTAMP, 3);




