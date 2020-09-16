DROP TABLE IF EXISTS poet;

CREATE TABLE poet (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL default '',
  CONSTRAINT pk_t_poet PRIMARY KEY (ID)
);


CREATE TABLE poetry (
  `id` int NOT NULL,
  `poet_id` int NOT NULL default '0',
  `title` varchar(128) not null default '',
  `content` varchar(128) not null default '',
  CONSTRAINT pk_t_poetry PRIMARY KEY (ID)
);