
CREATE TABLE users (
  id BIGINT PRIMARY KEY,
  username VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  email VARCHAR(255)
);
INSERT INTO users (id, username, password, role, email) VALUES (1, 'admin', 'password123', 'ADMIN', 'admin@vulnbank.com');
INSERT INTO users (id, username, password, role, email) VALUES (2, 'user', 'userpass', 'USER', 'user@vulnbank.com');
