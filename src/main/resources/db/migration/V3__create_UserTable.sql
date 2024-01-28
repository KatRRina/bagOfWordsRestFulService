CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(100) NOT NULL
);

INSERT INTO users(user_name, user_password)
VALUES ('admin', '$2a$12$iPOsrTgCLd5uEwP23KaR0.LbG4LDx0ug4zGQuPTBqGVb.TCnwdIfO');
-- user_name: admin user_password: asdf2024
ALTER TABLE information ADD FOREIGN KEY (user_login)
  REFERENCES users(user_name);