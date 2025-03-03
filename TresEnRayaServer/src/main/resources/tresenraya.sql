USE `tresenraya`;
START TRANSACTION;

CREATE TABLE `partidas` (
  `id` int NOT NULL,
  `estado` int DEFAULT NULL,
  `movimientos` int DEFAULT NULL,
  `tablero` varchar(9) DEFAULT NULL,
  `ganador_id` int DEFAULT NULL,
  `jugador1_id` int DEFAULT NULL,
  `jugador2_id` int DEFAULT NULL,
  `turno_id` int DEFAULT NULL
) 


INSERT INTO `partidas` (`id`, `estado`, `movimientos`, `tablero`, `ganador_id`, `jugador1_id`, `jugador2_id`, `turno_id`) VALUES
(6, 2, 1, '    X    ', 2, 2, 1, 2),
(7, 2, 5, '  X XOX O', 1, 1, 2, 1),
(8, 2, 5, 'XXX O O  ', 1, 1, 3, 1);

CREATE TABLE `personas` (
  `id` int NOT NULL,
  `correo` varchar(100) NOT NULL,
  `foto_nombre` varchar(100) DEFAULT NULL,
  `nombre` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `rol` varchar(45) NOT NULL,
  `token_verificacion` varchar(100) DEFAULT NULL,
  `verificado` bit(1) DEFAULT NULL
) 

INSERT INTO `personas` (`id`, `correo`, `foto_nombre`, `nombre`, `password`, `rol`, `token_verificacion`, `verificado`) VALUES
(1, 'google@gmail.com', 'root', 'root', '$2a$10$1BZ.Y9JI0p8dn.Lol4F.BOeRSq8pxWNa6pg4/OyPqFmZDM7YgtHKC', 'ROLE_ADMIN', '', b'1'),
(2, 'github@gmail.com', 'user', 'user', '$2a$10$5e9oNCbq5I4n1CATMsRn5.4CmRRLU8x/IfToKnIloKNzzixcpvofS', 'ROLE_USER', NULL, b'1'),
(3, 'rubalba.rag@gmail.com', 'test', 'test', '$2a$10$M7oPUdY369JWUgiS85q.DOB0FjqEtkBZUYKXPjCWIcJmf8H/WO3im', 'ROLE_USER', NULL, b'1');

ALTER TABLE `partidas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn3nxx7btxdyfvfdsi09yrbl4e` (`ganador_id`),
  ADD KEY `FKmcj1f5v05aetmd8c4mj7yytb` (`jugador1_id`),
  ADD KEY `FKfcegfkd6cx36a84k70mp1xpry` (`jugador2_id`),
  ADD KEY `FK9sxs51ar4qcgkfcx4y7o644nv` (`turno_id`);

ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK1f3wqd14lwfifrd1308leu73o` (`correo`),
  ADD UNIQUE KEY `UKk9mbg005jinfl83646w7dts6p` (`nombre`);

ALTER TABLE `partidas`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

ALTER TABLE `personas`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `partidas`
  ADD CONSTRAINT `FK9sxs51ar4qcgkfcx4y7o644nv` FOREIGN KEY (`turno_id`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKfcegfkd6cx36a84k70mp1xpry` FOREIGN KEY (`jugador2_id`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKmcj1f5v05aetmd8c4mj7yytb` FOREIGN KEY (`jugador1_id`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKn3nxx7btxdyfvfdsi09yrbl4e` FOREIGN KEY (`ganador_id`) REFERENCES `personas` (`id`);
COMMIT;
