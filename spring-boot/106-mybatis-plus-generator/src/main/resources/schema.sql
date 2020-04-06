CREATE TABLE `userT0` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `pwd` varchar(26) NOT NULL DEFAULT '' COMMENT '密码',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `created` varchar(13) NOT NULL DEFAULT '0',
  `updated` varchar(13) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `story_t0` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '作者的userID',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '作者名',
  `title` varchar(26) NOT NULL DEFAULT '' COMMENT '密码',
  `story` text COMMENT '故事内容',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_at` varchar(13) NOT NULL DEFAULT '0',
  `update_at` varchar(13) NOT NULL DEFAULT '0',
  `tag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


INSERT INTO `userT0` (`id`, `name`, `pwd`, `isDeleted`, `created`, `updated`)
VALUES
	(1, '一灰灰', 'yihuihuiblog', 0, '2020-04-06 15', '2020-04-06 15');
