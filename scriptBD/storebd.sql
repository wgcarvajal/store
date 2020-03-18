-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 18-03-2020 a las 06:09:30
-- Versión del servidor: 5.7.28
-- Versión de PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `storebd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `brand`
--

CREATE TABLE `brand` (
  `brandId` bigint(20) NOT NULL,
  `brandName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `brand`
--

INSERT INTO `brand` (`brandId`, `brandName`) VALUES
(2, 'Fritolay'),
(3, 'Bavaria'),
(4, 'Coca cola'),
(5, 'Postobón');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `catId` bigint(20) NOT NULL,
  `catName` varchar(100) NOT NULL,
  `catDescription` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`catId`, `catName`, `catDescription`) VALUES
(3, 'Cerveza', NULL),
(4, 'Gaseosa', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `client`
--

CREATE TABLE `client` (
  `cliId` bigint(20) NOT NULL,
  `cliIdentity` varchar(50) NOT NULL,
  `cliName` varchar(100) NOT NULL,
  `cliLastName` varchar(100) NOT NULL,
  `cliPhones` varchar(200) NOT NULL,
  `cliAddress` varchar(200) NOT NULL,
  `cliCredit` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `client`
--

INSERT INTO `client` (`cliId`, `cliIdentity`, `cliName`, `cliLastName`, `cliPhones`, `cliAddress`, `cliCredit`) VALUES
(1, '1075220291', 'Wilson Geovanny', 'Carvajal Molina', '8769129', 'cr 8 este # 3-21 sur', 1),
(2, '1061705800', 'Deisy Cristina', 'Piñeros Triviño', '900945', 'crkdfkadslklka', 0),
(3, '10738748372', 'Fabian Andres', 'Carvajal Molina', 'kdsjlkflkd', 'kdjkfjksl', 0),
(4, '43536654', 'Hilda Socorro', 'Molina Gutierrez', '8769129', 'cr 5 #63-07 barrio el cortijo', 0),
(5, '8345893498', 'Diego Alejandro Fabian Alberto', 'Lucuara', 'fdf', '34345df', 0),
(6, '36182458', 'Olga Patricia', 'Triviño Perez', '8351005', 'cra8No 3-21 sur Bosques de la Rivera ', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `groupp`
--

CREATE TABLE `groupp` (
  `grouId` varchar(20) NOT NULL,
  `grouDescription` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `groupp`
--

INSERT INTO `groupp` (`grouId`, `grouDescription`) VALUES
('admin', NULL),
('cashier', NULL),
('sAdmin', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lend`
--

CREATE TABLE `lend` (
  `lendId` bigint(20) NOT NULL,
  `cliId` bigint(20) NOT NULL,
  `usId` bigint(20) NOT NULL,
  `lendDate` datetime NOT NULL,
  `lendValue` int(11) NOT NULL,
  `lendPayment` int(11) DEFAULT NULL,
  `lendState` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `lend`
--

INSERT INTO `lend` (`lendId`, `cliId`, `usId`, `lendDate`, `lendValue`, `lendPayment`, `lendState`) VALUES
(21, 1, 2, '2019-09-03 13:26:00', 2000, 2000, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `owner`
--

CREATE TABLE `owner` (
  `ownId` int(11) NOT NULL,
  `ownName` varchar(250) NOT NULL,
  `ownDescription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `owner`
--

INSERT INTO `owner` (`ownId`, `ownName`, `ownDescription`) VALUES
(1, 'Tienda de lucho', 'tienda de lucho los manguitos'),
(2, 'Tia patricia', 'miscelania de la tia patricia'),
(3, 'Tia teresa', 'venta de productos de la tia teresa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pay`
--

CREATE TABLE `pay` (
  `payId` bigint(20) NOT NULL,
  `cliId` bigint(20) NOT NULL,
  `usId` bigint(20) NOT NULL,
  `payDate` datetime NOT NULL,
  `payValue` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pay`
--

INSERT INTO `pay` (`payId`, `cliId`, `usId`, `payDate`, `payValue`) VALUES
(20, 1, 2, '2019-09-03 13:26:09', 1000),
(21, 1, 2, '2019-09-03 13:26:13', 100),
(22, 1, 2, '2019-09-03 13:31:17', 10),
(23, 1, 2, '2019-09-03 13:31:22', 890);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `price`
--

CREATE TABLE `price` (
  `priceId` bigint(20) NOT NULL,
  `priceValue` int(11) NOT NULL,
  `priceInitialDate` datetime NOT NULL,
  `priceFinalDate` datetime DEFAULT NULL,
  `prodId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `price`
--

INSERT INTO `price` (`priceId`, `priceValue`, `priceInitialDate`, `priceFinalDate`, `prodId`) VALUES
(1, 2000, '2019-08-14 04:00:00', '2019-08-18 00:31:28', 1),
(2, 1700, '2019-08-17 15:03:45', '2020-03-09 16:50:41', 2),
(3, 2000, '2019-08-17 15:07:40', NULL, 3),
(4, 600, '2019-08-17 15:15:28', NULL, 4),
(5, 700, '2019-08-17 15:16:35', NULL, 5),
(6, 1800, '2019-08-17 15:19:59', NULL, 6),
(7, 1800, '2019-08-17 15:22:29', NULL, 7),
(8, 800, '2019-08-17 17:40:57', NULL, 8),
(9, 1900, '2019-08-17 18:03:30', NULL, 9),
(10, 2200, '2019-08-17 18:06:04', NULL, 10),
(11, 20000, '2019-08-18 00:31:28', '2019-08-18 00:32:07', 1),
(12, 1500, '2019-08-18 00:32:07', NULL, 1),
(13, 2500, '2019-08-23 15:08:30', NULL, 11),
(14, 2600, '2020-03-09 11:26:56', NULL, 12),
(15, 2400, '2020-03-09 11:29:37', NULL, 13),
(16, 1800, '2020-03-09 16:50:41', '2020-03-09 16:51:11', 2),
(17, 2200, '2020-03-09 16:51:11', NULL, 2),
(18, 1200, '2020-03-17 20:51:13', NULL, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pricepurchase`
--

CREATE TABLE `pricepurchase` (
  `pricePurId` bigint(20) NOT NULL,
  `pricePurValue` int(11) NOT NULL,
  `pricePurInitialDate` datetime NOT NULL,
  `pricePurFinalDate` datetime DEFAULT NULL,
  `prodId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pricepurchase`
--

INSERT INTO `pricepurchase` (`pricePurId`, `pricePurValue`, `pricePurInitialDate`, `pricePurFinalDate`, `prodId`) VALUES
(1, 2000, '2020-03-09 11:29:37', '2020-03-09 16:52:05', 13),
(2, 2000, '2020-03-09 16:50:26', '2020-03-09 16:50:34', 2),
(3, 3000, '2020-03-09 16:50:34', '2020-03-09 16:51:03', 2),
(4, 2000, '2020-03-09 16:51:03', NULL, 2),
(5, 1900, '2020-03-09 16:52:05', NULL, 13),
(6, 500, '2020-03-09 17:18:52', NULL, 4),
(7, 1200, '2020-03-16 00:18:46', NULL, 1),
(8, 1800, '2020-03-16 00:19:08', NULL, 3),
(9, 1000, '2020-03-17 20:51:13', NULL, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE `product` (
  `prodId` bigint(20) NOT NULL,
  `prodBarCode` varchar(50) DEFAULT NULL,
  `prodName` varchar(100) NOT NULL,
  `prodStock` int(11) NOT NULL,
  `prodIva` int(11) NOT NULL DEFAULT '0',
  `prodBaseNumber` int(11) NOT NULL,
  `prodUnitValue` int(11) NOT NULL,
  `catId` bigint(20) NOT NULL,
  `brandId` bigint(20) DEFAULT NULL,
  `uniId` int(11) NOT NULL,
  `ownId` int(11) NOT NULL DEFAULT '1',
  `prodtypeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`prodId`, `prodBarCode`, `prodName`, `prodStock`, `prodIva`, `prodBaseNumber`, `prodUnitValue`, `catId`, `brandId`, `uniId`, `ownId`, `prodtypeId`) VALUES
(1, '30', 'Coca cola', 10, 5, 12, 350, 4, 4, 1, 2, 1),
(2, '31', 'Papas fritas', 0, 0, 0, 90, 3, 2, 1, 1, 1),
(3, '32', 'Papas', 4, 0, 7, 120, 3, 2, 1, 1, 1),
(4, '095489', 'Choco ramo', 0, 0, 0, 100, 3, 4, 1, 1, 1),
(5, '3434', 'Ponque ramo', 5, 0, 6, 58, 3, 2, 1, 1, 1),
(6, '45565', 'Manzana', 20, 0, 25, 350, 3, 5, 1, 1, 1),
(7, '34435', 'Uva', 7, 0, 8, 350, 3, 5, 1, 1, 1),
(8, '556567', 'Yogurt', 0, 0, 0, 20, 3, 4, 1, 1, 1),
(9, '489239', 'Sprite', 4, 0, 5, 350, 3, 4, 1, 1, 1),
(10, '859143', 'Sprite', 0, 0, 0, 600, 3, 4, 1, 1, 1),
(11, '33', 'Pollo sin viceras', 10, 0, 10, 1000, 4, 3, 2, 1, 2),
(12, '49895968', 'Prueba', 10, 5, 29, 350, 4, 5, 1, 1, 1),
(13, '545465', 'Curuba', 12, 5, 23, 34343, 4, 4, 1, 1, 1),
(14, '999999', 'Test2', 5, 5, 10, 350, 3, 2, 1, 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productimage`
--

CREATE TABLE `productimage` (
  `prodimgId` bigint(20) NOT NULL,
  `prodimgPath` varchar(100) DEFAULT NULL,
  `prodId` bigint(20) NOT NULL,
  `prodimgMain` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `productimage`
--

INSERT INTO `productimage` (`prodimgId`, `prodimgPath`, `prodId`, `prodimgMain`) VALUES
(2, '1/2.png', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producttype`
--

CREATE TABLE `producttype` (
  `prodtypeId` int(11) NOT NULL,
  `prodtypeValue` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producttype`
--

INSERT INTO `producttype` (`prodtypeId`, `prodtypeValue`) VALUES
(1, 'Empaquetado'),
(2, 'Sin empaquetar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provider`
--

CREATE TABLE `provider` (
  `provId` bigint(20) NOT NULL,
  `provName` varchar(100) NOT NULL,
  `provPhones` varchar(200) NOT NULL,
  `provAddress` varchar(200) NOT NULL,
  `provRut` varchar(50) DEFAULT NULL,
  `provNit` varchar(50) DEFAULT NULL,
  `provWeb` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provider`
--

INSERT INTO `provider` (`provId`, `provName`, `provPhones`, `provAddress`, `provRut`, `provNit`, `provWeb`) VALUES
(1, 'Coca cola', '343434,4342343,4342343', 'cre kdlksañ kdlsa', '432423', '34r5454543', 'wwww.dsakfdsa.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provides`
--

CREATE TABLE `provides` (
  `providesId` bigint(20) NOT NULL,
  `provId` bigint(20) NOT NULL,
  `prodId` bigint(20) NOT NULL,
  `providesInitialDate` datetime NOT NULL,
  `providesFinalDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provides`
--

INSERT INTO `provides` (`providesId`, `provId`, `prodId`, `providesInitialDate`, `providesFinalDate`) VALUES
(1, 1, 1, '2019-08-13 00:00:00', '2019-08-18 15:12:24'),
(2, 1, 10, '2019-08-17 18:06:04', '2019-08-18 15:14:05'),
(3, 1, 1, '2019-08-18 15:13:07', '2019-08-18 15:13:13'),
(4, 1, 10, '2019-08-18 15:14:21', NULL),
(5, 1, 1, '2019-08-18 17:33:41', NULL),
(6, 1, 11, '2019-08-23 15:08:30', NULL),
(7, 1, 13, '2020-03-09 11:29:37', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `purchase`
--

CREATE TABLE `purchase` (
  `purId` bigint(20) NOT NULL,
  `purDate` datetime NOT NULL,
  `purFinalAmount` int(11) DEFAULT NULL,
  `purDiscount` int(11) DEFAULT NULL,
  `purPayment` int(11) DEFAULT NULL,
  `cliId` bigint(20) DEFAULT NULL,
  `usId` bigint(20) NOT NULL,
  `purState` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `purchase`
--

INSERT INTO `purchase` (`purId`, `purDate`, `purFinalAmount`, `purDiscount`, `purPayment`, `cliId`, `usId`, `purState`) VALUES
(5, '2020-03-18 00:55:29', NULL, NULL, NULL, NULL, 3, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `purchaseitem`
--

CREATE TABLE `purchaseitem` (
  `purItemId` bigint(20) NOT NULL,
  `purItemQuantity` int(11) NOT NULL,
  `prodId` bigint(20) NOT NULL,
  `purId` bigint(20) NOT NULL,
  `priceValue` int(11) NOT NULL,
  `iva` int(11) NOT NULL,
  `pricePurValue` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `purchaseitem`
--

INSERT INTO `purchaseitem` (`purItemId`, `purItemQuantity`, `prodId`, `purId`, `priceValue`, `iva`, `pricePurValue`) VALUES
(13, 1, 1, 5, 1500, 5, 1200),
(14, 1, 2, 5, 2200, 0, 2000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `purchasetotal`
--

CREATE TABLE `purchasetotal` (
  `purToId` bigint(20) NOT NULL,
  `purId` bigint(20) NOT NULL,
  `ownId` int(11) NOT NULL,
  `purToTotal` int(11) NOT NULL,
  `purToGain` int(11) NOT NULL,
  `purToIva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unity`
--

CREATE TABLE `unity` (
  `uniId` int(11) NOT NULL,
  `uniName` varchar(60) NOT NULL,
  `uniAbbreviation` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `unity`
--

INSERT INTO `unity` (`uniId`, `uniName`, `uniAbbreviation`) VALUES
(1, 'Mililitros', 'ml'),
(2, 'Gramos', 'gr');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `usId` bigint(20) NOT NULL,
  `usIdentification` varchar(30) NOT NULL,
  `usName` varchar(100) NOT NULL,
  `usLastName` varchar(100) NOT NULL,
  `usUserName` varchar(75) NOT NULL,
  `usPassword` varchar(256) NOT NULL,
  `usEmail` varchar(200) NOT NULL,
  `usAddress` varchar(200) DEFAULT NULL,
  `usPhone` varchar(200) DEFAULT NULL,
  `usActive` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`usId`, `usIdentification`, `usName`, `usLastName`, `usUserName`, `usPassword`, `usEmail`, `usAddress`, `usPhone`, `usActive`) VALUES
(1, '1075220291', 'Wilson', 'Carvajal', 'wgcarvajal', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'wilnacio@hotmail.com', NULL, NULL, 1),
(2, '1061705800', 'Deisy', 'Piñeros', 'deisypt', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'deisypt@gmail.com', NULL, NULL, 1),
(3, '34545465', 'Maria Juliana', 'Carvajal Piñeros', 'maju', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'maju@hotmail.com', NULL, NULL, 1),
(4, '16190612', 'Luis Alberto', 'Carvajal Diaz', 'lucho', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'luiscarvajal@hotmail.com', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usergroup`
--

CREATE TABLE `usergroup` (
  `id` bigint(20) NOT NULL,
  `usUserName` varchar(75) NOT NULL,
  `grouId` varchar(20) NOT NULL,
  `usId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usergroup`
--

INSERT INTO `usergroup` (`id`, `usUserName`, `grouId`, `usId`) VALUES
(2, 'wgcarvajal', 'sAdmin', 1),
(3, 'deisypt', 'cashier', 2),
(4, 'maju', 'cashier', 3),
(5, 'lucho', 'admin', 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`brandId`);

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`catId`);

--
-- Indices de la tabla `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`cliId`),
  ADD UNIQUE KEY `cliIdentity` (`cliIdentity`),
  ADD UNIQUE KEY `cliIdentity_2` (`cliIdentity`);

--
-- Indices de la tabla `groupp`
--
ALTER TABLE `groupp`
  ADD PRIMARY KEY (`grouId`);

--
-- Indices de la tabla `lend`
--
ALTER TABLE `lend`
  ADD PRIMARY KEY (`lendId`),
  ADD KEY `fk_lend_client` (`cliId`),
  ADD KEY `fk_lend_user` (`usId`);

--
-- Indices de la tabla `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`ownId`);

--
-- Indices de la tabla `pay`
--
ALTER TABLE `pay`
  ADD PRIMARY KEY (`payId`),
  ADD KEY `fk_pay_client` (`cliId`),
  ADD KEY `usId` (`usId`);

--
-- Indices de la tabla `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`priceId`),
  ADD KEY `fk_price_product` (`prodId`);

--
-- Indices de la tabla `pricepurchase`
--
ALTER TABLE `pricepurchase`
  ADD PRIMARY KEY (`pricePurId`),
  ADD KEY `fk_pricepurshase_product` (`prodId`);

--
-- Indices de la tabla `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`prodId`),
  ADD KEY `fk_product_brand` (`brandId`),
  ADD KEY `fk_produt_category` (`catId`),
  ADD KEY `fk_product_unity` (`uniId`),
  ADD KEY `fk_product_producttype` (`prodtypeId`),
  ADD KEY `fk_product_owner` (`ownId`);

--
-- Indices de la tabla `productimage`
--
ALTER TABLE `productimage`
  ADD PRIMARY KEY (`prodimgId`),
  ADD KEY `fk_productimage_product` (`prodId`);

--
-- Indices de la tabla `producttype`
--
ALTER TABLE `producttype`
  ADD PRIMARY KEY (`prodtypeId`);

--
-- Indices de la tabla `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`provId`);

--
-- Indices de la tabla `provides`
--
ALTER TABLE `provides`
  ADD PRIMARY KEY (`providesId`),
  ADD KEY `fk_provides_provider` (`provId`),
  ADD KEY `fk_provides_product` (`prodId`);

--
-- Indices de la tabla `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`purId`),
  ADD KEY `fk_purchase_client` (`cliId`),
  ADD KEY `fk_purshase_user` (`usId`);

--
-- Indices de la tabla `purchaseitem`
--
ALTER TABLE `purchaseitem`
  ADD PRIMARY KEY (`purItemId`),
  ADD KEY `fk_purchaseitem_product` (`prodId`),
  ADD KEY `fk_purchaseitem_purchase` (`purId`);

--
-- Indices de la tabla `purchasetotal`
--
ALTER TABLE `purchasetotal`
  ADD PRIMARY KEY (`purToId`),
  ADD KEY `fk_purshaseTotal_purshase` (`purId`),
  ADD KEY `fk_purshaseTotal_owner` (`ownId`);

--
-- Indices de la tabla `unity`
--
ALTER TABLE `unity`
  ADD PRIMARY KEY (`uniId`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`usId`);

--
-- Indices de la tabla `usergroup`
--
ALTER TABLE `usergroup`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usergroup_user` (`usId`),
  ADD KEY `fk_usergroup_groupp` (`grouId`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `brand`
--
ALTER TABLE `brand`
  MODIFY `brandId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `catId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `client`
--
ALTER TABLE `client`
  MODIFY `cliId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `lend`
--
ALTER TABLE `lend`
  MODIFY `lendId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `owner`
--
ALTER TABLE `owner`
  MODIFY `ownId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pay`
--
ALTER TABLE `pay`
  MODIFY `payId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `price`
--
ALTER TABLE `price`
  MODIFY `priceId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `pricepurchase`
--
ALTER TABLE `pricepurchase`
  MODIFY `pricePurId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `product`
--
ALTER TABLE `product`
  MODIFY `prodId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `productimage`
--
ALTER TABLE `productimage`
  MODIFY `prodimgId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `producttype`
--
ALTER TABLE `producttype`
  MODIFY `prodtypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `provider`
--
ALTER TABLE `provider`
  MODIFY `provId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `provides`
--
ALTER TABLE `provides`
  MODIFY `providesId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `purchase`
--
ALTER TABLE `purchase`
  MODIFY `purId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `purchaseitem`
--
ALTER TABLE `purchaseitem`
  MODIFY `purItemId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `purchasetotal`
--
ALTER TABLE `purchasetotal`
  MODIFY `purToId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `unity`
--
ALTER TABLE `unity`
  MODIFY `uniId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `usId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usergroup`
--
ALTER TABLE `usergroup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `lend`
--
ALTER TABLE `lend`
  ADD CONSTRAINT `fk_lend_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `fk_lend_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Filtros para la tabla `pay`
--
ALTER TABLE `pay`
  ADD CONSTRAINT `fk_pay_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `pay_ibfk_1` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Filtros para la tabla `price`
--
ALTER TABLE `price`
  ADD CONSTRAINT `fk_price_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Filtros para la tabla `pricepurchase`
--
ALTER TABLE `pricepurchase`
  ADD CONSTRAINT `fk_pricepurshase_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Filtros para la tabla `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_brand` FOREIGN KEY (`brandId`) REFERENCES `brand` (`brandId`),
  ADD CONSTRAINT `fk_product_owner` FOREIGN KEY (`ownId`) REFERENCES `owner` (`ownId`),
  ADD CONSTRAINT `fk_product_producttype` FOREIGN KEY (`prodtypeId`) REFERENCES `producttype` (`prodtypeId`),
  ADD CONSTRAINT `fk_product_unity` FOREIGN KEY (`uniId`) REFERENCES `unity` (`uniId`),
  ADD CONSTRAINT `fk_produt_category` FOREIGN KEY (`catId`) REFERENCES `category` (`catId`);

--
-- Filtros para la tabla `productimage`
--
ALTER TABLE `productimage`
  ADD CONSTRAINT `fk_productimage_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Filtros para la tabla `provides`
--
ALTER TABLE `provides`
  ADD CONSTRAINT `fk_provides_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`),
  ADD CONSTRAINT `fk_provides_provider` FOREIGN KEY (`provId`) REFERENCES `provider` (`provId`);

--
-- Filtros para la tabla `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_purchase_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `fk_purshase_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Filtros para la tabla `purchaseitem`
--
ALTER TABLE `purchaseitem`
  ADD CONSTRAINT `fk_purchaseitem_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`),
  ADD CONSTRAINT `fk_purchaseitem_purchase` FOREIGN KEY (`purId`) REFERENCES `purchase` (`purId`);

--
-- Filtros para la tabla `purchasetotal`
--
ALTER TABLE `purchasetotal`
  ADD CONSTRAINT `fk_purshaseTotal_owner` FOREIGN KEY (`ownId`) REFERENCES `owner` (`ownId`),
  ADD CONSTRAINT `fk_purshaseTotal_purshase` FOREIGN KEY (`purId`) REFERENCES `purchase` (`purId`);

--
-- Filtros para la tabla `usergroup`
--
ALTER TABLE `usergroup`
  ADD CONSTRAINT `fk_usergroup_groupp` FOREIGN KEY (`grouId`) REFERENCES `groupp` (`grouId`),
  ADD CONSTRAINT `fk_usergroup_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
