LOCK TABLES `category` WRITE;
INSERT INTO `category`
VALUES (1, 'CPU'),
       (2, 'Graphics Cards'),
       (3, 'PSU');
UNLOCK TABLES;

LOCK TABLES `product` WRITE;
INSERT INTO `product`
VALUES (1, 'Intel Core I5-10400F Box',
        '10th Gen Intel Core i5-10400F desktop processor without processor graphics. Optimized for gaming, creating, and productivity. Discrete graphics required. Cooler included in the box. ONLY compatible with 400 series chipset based motherboard. 65W. ',
        3, 100.00, 1),
       (2, 'AMD Ryzen 5 3600 Box',
        ' The world\'s most advanced processor in the desktop PC gaming segment\nCan deliver ultra-fast 100+ FPS performance in the world\'s most popular games\n6 cores and 12 processing threads bundled with the quiet AMD wraith stealth cooler max temps 95°C\n4 2 GHz max boost unlocked for overclocking 35 MB of game cache DDR4 3200 support\nFor the advanced socket AM4 platform can support PCIe 4 0 on x570 motherboards ',
        2, 181.68, 1),
       (3, 'Intel Core i3-10100 Box',
        '10th Gen Intel Core i3-10100 desktop processor optimized for everyday computing. Cooler included in the box. ONLY compatible with 400 series chipset based motherboard. 65W. ',
        0, 100.00, 1),
       (4, 'Sapphire Radeon RX 5600 XT 6GB Pulse',
        'Sapphire Radeon Pulse RX 5600 XT 6GB GDDR6 HDMI / Triple DP OC w/ backplate (UEFI) PCIe 4 0 Graphics Card. ',
        3, 288.86, 2),
       (5, 'MSI GeForce GTX 1660 Ti 6GB Gaming X',
        'TORX Fan 3.0\n    - Dispersion fan blade: Steep curved blade accelerating the airflow.\n    - Traditional fan blade: Provides steady airflow to massive heat sink below.\n    Mastery of Aerodynamics: The heatsink is optimized for efficient heat dissipation, keeping your temperatures low and performance high.\n    Zero Frozr technology: Stopping the fan in low-load situations, keeping a noise-free environment.',
        2, 277.80, 2),
       (6, 'Corsair TX-M Series TX650M 650W',
        'The CORSAIR TX-M Series™ power supplies provide industrial build quality, 80 PLUS Gold efficiency, extremely tight voltages and a semi-modular cable set.',
        4, 100.00, 3);
UNLOCK
TABLES;

LOCK
TABLES `image` WRITE;
INSERT INTO `image`
VALUES (1, 'MSI GTX 1660 Ti.png', 5),
       (2, 'MSI GTX 1660 Ti 1.png', 5),
       (3, 'SAPPHIRE RADEON RX 5600 XT.png', 4),
       (4, 'SAPPHIRE RADEON RX 5600 XT 1.png', 4),
       (5, 'AMD Ryzen.png', 2),
       (6, 'AMD Ryzen 1.png', 2),
       (7, 'Intel Core i5.png', 1),
       (8, 'Intel Core i5 1.png', 1),
       (9, 'Corsair TX-M Series TX650M.png', 6);
UNLOCK
TABLES;

LOCK
TABLES `users` WRITE;
INSERT INTO `users`
VALUES (1, 'admin', '$2a$04$QtDywltFkExfswHqKZmZtePwAIDUhIFiAOmaHvE0YYdE48pZnSlUy', 1);
UNLOCK TABLES;

LOCK TABLES `user_details` WRITE;
INSERT INTO `user_details`
VALUES (1, 'admin', 'admin', 'admin@a', 1);
UNLOCK TABLES;


LOCK TABLES `authorities` WRITE;
INSERT INTO `authorities`
VALUES (1, 'admin', 1);
UNLOCK TABLES;