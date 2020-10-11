#Base de datos para la primera practica calificada

CREATE DATABASE `rfclientes`;
# GO
USE `rfclientes`;
# GO
SET FOREIGN_KEY_CHECKS=0;

# GO
# Dumping Table Structure for clientes

#
CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(50) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idcliente`),
  UNIQUE KEY `IDX_clientes_2` (`apellidos`,`nombres`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
# GO
#

# Dumping Data for clientes
#
BEGIN;
# GO
INSERT INTO `clientes` (idcliente, apellidos, nombres, correo, telefono) VALUES (1, 'Pérez', 'Juan', 'jperez@hotmail.com', '999-678-234');
# GO
INSERT INTO `clientes` (idcliente, apellidos, nombres, correo, telefono) VALUES (8, 'Raymondi', 'Walter', 'wraymondi@gmail.com', '678-5678');
# GO
INSERT INTO `clientes` (idcliente, apellidos, nombres, correo, telefono) VALUES (11, 'Rios', 'Karla', 'karla@yahoo.es', '456-6756');
# GO
INSERT INTO `clientes` (idcliente, apellidos, nombres, correo, telefono) VALUES (13, 'Alcántara', 'Ana', 'aalcantara@yahoo.es', '980-456-554');
# GO
COMMIT;
# GO
SET FOREIGN_KEY_CHECKS=1

# GO


