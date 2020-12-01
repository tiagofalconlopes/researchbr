--Postgresql for Heroku
--INSERT INTO role (role_id, description, name)
--VALUES (1, 'Principal', 'PRINCIPAL'), (2, 'Assistant', 'ASSISTANT')
--ON CONFLICT (role_id) DO UPDATE
--  SET description = excluded.description,
--      name = excluded.name;


--Mysql
INSERT INTO role (role_id, description, name)
VALUES (1, "Principal", "PRINCIPAL")
ON DUPLICATE KEY UPDATE
  role_id = 1,
  description = "Principal",
  name = "PRINCIPAL";
INSERT INTO role (role_id, description, name)
VALUES (2, "Assistant", "ASSISTANT")
ON DUPLICATE KEY UPDATE
  role_id = 2,
  description = "Assistant",
  name = "ASSISTANT";