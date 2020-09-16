DROP TABLE IF EXISTS poet;

CREATE TABLE poet (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL default '',
  CONSTRAINT pk_t_poet PRIMARY KEY (ID)
);
