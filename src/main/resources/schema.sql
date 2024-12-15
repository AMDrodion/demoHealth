CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT,
                                          role_id BIGINT,
                                          PRIMARY KEY (user_id, role_id)
    );

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL
    );