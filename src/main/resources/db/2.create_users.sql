CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);


INSERT INTO users (username, password, role, enabled)
VALUES
('admin', '$2a$12$1RkSchNFqJF2BEFEccF3tuJ4R3uxHMQtmFZkOKh95V1wJv8QxQr96', 'ROLE_ADMIN', true), // password: admin123
('user1', '$2a$12$yp5Uo7JfTtEFel.D/KBgVOEwwINEIdThXFfQru1fJUMdBUtZPn7pO', 'ROLE_USER', true); // password: password
