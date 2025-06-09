CREATE TABLE `links` (
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKv5l26y1u1amnux9hhyq5n7dk` (`name`),
  UNIQUE KEY `UKpowvnxxcx5omsbw0cgwauy5ru` (`url`),
  KEY `FKd67sl1rt26vn16l5n0tpxkr0k` (`tag_id`),
  CONSTRAINT `FKd67sl1rt26vn16l5n0tpxkr0k` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;