DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS confirmation_tokens;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS user_details;
DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `data` mediumblob,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PRODUCT_idx` (`product_id`),
  CONSTRAINT `FK_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS `user_details` (
                                              `id` int NOT NULL AUTO_INCREMENT,
                                              `first_name` varchar(45) DEFAULT NULL,
                                              `last_name` varchar(45) DEFAULT NULL,
                                              `email` varchar(45) DEFAULT NULL,
                                              `user_id` int DEFAULT NULL,
                                              PRIMARY KEY (`id`),
                                              UNIQUE KEY `UK_f4pdcamta635qqbhgcyqvrg7f` (`user_id`),
                                              KEY `id_idx` (`user_id`),
                                              CONSTRAINT `fk_userdetails_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS `authorities`
(
    `id`        int         NOT NULL AUTO_INCREMENT,
    `authority` varchar(50) NOT NULL,
    `user_id`   int DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_authorities_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS `confirmation_tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(45) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `confirmation_tokens_users_id_fk` (`user_id`),
  CONSTRAINT `confirmation_tokens_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`user_id`),
  CONSTRAINT `id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`order_id`),
  KEY `id_idx1` (`product_id`),
  CONSTRAINT `ido` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idp` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;