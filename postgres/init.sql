CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO users (id, name, email) VALUES
(0, 'John Doe', 'john.doe@example.com'),
(1, 'Jane Smith', 'jane.smith@example.com');