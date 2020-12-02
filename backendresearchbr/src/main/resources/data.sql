--Postgresql
--INSERT INTO role (role_id, description, name)
--VALUES (1, 'Principal', 'PRINCIPAL'), (2, 'Assistant', 'ASSISTANT')
--ON CONFLICT (role_id) DO UPDATE
--  SET description = excluded.description,
--      name = excluded.name;
--INSERT INTO category (id, name)
--VALUES (1, "Material permanente"), (2, "Material de consumo"), (3, "Diária"), (4, "Manutenção"), (5, "Transporte"), (6, "Serviços de terceiros"), (7, "Taxa de inscrição"), (8, "Publicações")
--ON CONFLICT (id) DO UPDATE
--  SET name = excluded.name;


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
INSERT INTO category (id, name)
VALUES (1, "Material permanente")
ON DUPLICATE KEY UPDATE
  id = 1,
  name = "Material permanente";
INSERT INTO category (id, name)
VALUES (2, "Material de consumo")
ON DUPLICATE KEY UPDATE
  id = 2,
  name = "Material de consumo";
INSERT INTO category (id, name)
VALUES (3, "Diária")
ON DUPLICATE KEY UPDATE
  id = 3,
  name = "Diária";
INSERT INTO category (id, name)
VALUES (4, "Manutenção")
ON DUPLICATE KEY UPDATE
  id = 4,
  name = "Manutenção";
INSERT INTO category (id, name)
VALUES (5, "Transporte")
ON DUPLICATE KEY UPDATE
  id = 5,
  name = "Transporte";
INSERT INTO category (id, name)
VALUES (6, "Serviços de terceiros")
ON DUPLICATE KEY UPDATE
  id = 6,
  name = "Serviços de terceiros";
INSERT INTO category (id, name)
VALUES (7, "Taxa de inscrição")
ON DUPLICATE KEY UPDATE
  id = 7,
  name = "Taxa de inscrição";
INSERT INTO category (id, name)
VALUES (8, "Publicações")
ON DUPLICATE KEY UPDATE
  id = 8,
  name = "Publicações";