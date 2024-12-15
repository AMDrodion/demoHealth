INSERT INTO users (username, password)
SELECT '$2a$10$Sm5kEEbxfE5rRFyKol1TJekO6VSW4.zSKbu4sbdgzGcaMF/9QbRCS', '$2a$10$Sm5kEEbxfE5rRFyKol1TJekO6VSW4.zSKbu4sbdgzGcaMF/9QbRCS'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = '$2a$10$Sm5kEEbxfE5rRFyKol1TJekO6VSW4.zSKbu4sbdgzGcaMF/9QbRCS'
);

INSERT INTO users (username, password)
SELECT '$2a$10$OHrY6sYPb/0oMP35ZQcQyu.4WmZQ9hrRcaCfk4uBjkYNhubm58zru', '$2a$10$OHrY6sYPb/0oMP35ZQcQyu.4WmZQ9hrRcaCfk4uBjkYNhubm58zru'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = '$2a$10$OHrY6sYPb/0oMP35ZQcQyu.4WmZQ9hrRcaCfk4uBjkYNhubm58zru'
);

INSERT INTO roles (id, name)
SELECT 1, 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN'
);

INSERT INTO roles (id, name)
SELECT 2, 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ROLE_USER'
);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.username = '$2a$10$Sm5kEEbxfE5rRFyKol1TJekO6VSW4.zSKbu4sbdgzGcaMF/9QbRCS'
AND NOT EXISTS (SELECT 1
                FROM user_roles ur
                WHERE ur.user_id = u.id AND ur.role_id = r.id);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.username = '$2a$10$OHrY6sYPb/0oMP35ZQcQyu.4WmZQ9hrRcaCfk4uBjkYNhubm58zru'
AND NOT EXISTS (SELECT 1
                FROM user_roles ur
                WHERE ur.user_id = u.id AND ur.role_id = r.id
                );