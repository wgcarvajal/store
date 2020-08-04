-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 04, 2020 at 03:13 PM
-- Server version: 5.7.28
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `storebd`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `brandId` bigint(20) NOT NULL,
  `brandName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`brandId`, `brandName`) VALUES
(2, 'Fritolay'),
(3, 'Bavaria'),
(4, 'Coca cola'),
(5, 'Postobón');

-- --------------------------------------------------------

--
-- Table structure for table `cash`
--

CREATE TABLE `cash` (
  `cashId` int(11) NOT NULL,
  `cashName` varchar(100) NOT NULL,
  `cashIP` varchar(250) NOT NULL,
  `cashPaperSize` int(11) NOT NULL DEFAULT '80',
  `cashPrintName` varchar(250) NOT NULL,
  `cashScalePortSerialName` varchar(20) NOT NULL,
  `cashPrintCommandOpenCashDrawer` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cash`
--

INSERT INTO `cash` (`cashId`, `cashName`, `cashIP`, `cashPaperSize`, `cashPrintName`, `cashScalePortSerialName`, `cashPrintCommandOpenCashDrawer`) VALUES
(1, 'Caja 1', '192.168.1.5', 80, 'SAT 22TUS', 'COM4', '{27,112,0,100,250}');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `catId` bigint(20) NOT NULL,
  `catName` varchar(100) NOT NULL,
  `catDescription` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`catId`, `catName`, `catDescription`) VALUES
(3, 'Cerveza', NULL),
(4, 'Gaseosa', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `client`
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
-- Dumping data for table `client`
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
-- Table structure for table `groupp`
--

CREATE TABLE `groupp` (
  `grouId` varchar(20) NOT NULL,
  `grouDescription` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupp`
--

INSERT INTO `groupp` (`grouId`, `grouDescription`) VALUES
('admin', NULL),
('cashier', NULL),
('sAdmin', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `lend`
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

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `ownId` int(11) NOT NULL,
  `ownName` varchar(250) NOT NULL,
  `ownDescription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`ownId`, `ownName`, `ownDescription`) VALUES
(1, 'Tienda de lucho', 'tienda de lucho los manguitos'),
(2, 'Tia patricia', 'miscelania de la tia patricia'),
(3, 'Tia teresa', 'venta de productos de la tia teresa');

-- --------------------------------------------------------

--
-- Table structure for table `pay`
--

CREATE TABLE `pay` (
  `payId` bigint(20) NOT NULL,
  `cliId` bigint(20) NOT NULL,
  `usId` bigint(20) NOT NULL,
  `payDate` datetime NOT NULL,
  `payValue` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pay`
--

INSERT INTO `pay` (`payId`, `cliId`, `usId`, `payDate`, `payValue`) VALUES
(20, 1, 2, '2019-09-03 13:26:09', 1000),
(21, 1, 2, '2019-09-03 13:26:13', 100),
(22, 1, 2, '2019-09-03 13:31:17', 10),
(23, 1, 2, '2019-09-03 13:31:22', 890);

-- --------------------------------------------------------

--
-- Table structure for table `price`
--

CREATE TABLE `price` (
  `priceId` bigint(20) NOT NULL,
  `priceValue` int(11) NOT NULL,
  `priceInitialDate` datetime NOT NULL,
  `priceFinalDate` datetime DEFAULT NULL,
  `prodId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `price`
--

INSERT INTO `price` (`priceId`, `priceValue`, `priceInitialDate`, `priceFinalDate`, `prodId`) VALUES
(1, 2000, '2019-08-14 04:00:00', '2019-08-18 00:31:28', 1),
(2, 1700, '2019-08-17 15:03:45', '2020-03-09 16:50:41', 2),
(3, 2000, '2019-08-17 15:07:40', '2020-07-09 17:54:53', 3),
(4, 600, '2019-08-17 15:15:28', '2020-07-09 16:56:56', 4),
(5, 700, '2019-08-17 15:16:35', NULL, 5),
(6, 1800, '2019-08-17 15:19:59', NULL, 6),
(7, 1800, '2019-08-17 15:22:29', NULL, 7),
(8, 800, '2019-08-17 17:40:57', NULL, 8),
(9, 1900, '2019-08-17 18:03:30', NULL, 9),
(10, 2200, '2019-08-17 18:06:04', NULL, 10),
(11, 20000, '2019-08-18 00:31:28', '2019-08-18 00:32:07', 1),
(12, 1500, '2019-08-18 00:32:07', '2020-07-08 11:23:00', 1),
(13, 2500, '2019-08-23 15:08:30', NULL, 11),
(14, 2600, '2020-03-09 11:26:56', NULL, 12),
(15, 2400, '2020-03-09 11:29:37', NULL, 13),
(16, 1800, '2020-03-09 16:50:41', '2020-03-09 16:51:11', 2),
(17, 2200, '2020-03-09 16:51:11', '2020-07-09 17:43:37', 2),
(18, 1200, '2020-03-17 20:51:13', NULL, 14),
(19, 1600, '2020-07-08 11:23:00', '2020-07-27 11:22:12', 1),
(20, 700, '2020-07-09 16:56:56', '2020-07-09 16:57:42', 4),
(21, 800, '2020-07-09 16:57:42', '2020-07-09 17:00:13', 4),
(22, 600, '2020-07-09 17:00:13', '2020-07-09 17:04:58', 4),
(23, 700, '2020-07-09 17:04:58', '2020-07-09 23:05:44', 4),
(24, 2203, '2020-07-09 17:43:37', NULL, 2),
(25, 2200, '2020-07-09 17:54:53', NULL, 3),
(26, 800, '2020-07-09 23:05:44', NULL, 4),
(27, 567, '2020-07-26 15:40:56', NULL, 15),
(28, 10000000, '2020-07-27 11:22:12', '2020-07-27 11:27:52', 1),
(29, 100000000, '2020-07-27 11:27:52', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pricepurchase`
--

CREATE TABLE `pricepurchase` (
  `pricePurId` bigint(20) NOT NULL,
  `pricePurValue` int(11) NOT NULL,
  `pricePurInitialDate` datetime NOT NULL,
  `pricePurFinalDate` datetime DEFAULT NULL,
  `prodId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pricepurchase`
--

INSERT INTO `pricepurchase` (`pricePurId`, `pricePurValue`, `pricePurInitialDate`, `pricePurFinalDate`, `prodId`) VALUES
(1, 2000, '2020-03-09 11:29:37', '2020-03-09 16:52:05', 13),
(2, 2000, '2020-03-09 16:50:26', '2020-03-09 16:50:34', 2),
(3, 3000, '2020-03-09 16:50:34', '2020-03-09 16:51:03', 2),
(4, 2000, '2020-03-09 16:51:03', NULL, 2),
(5, 1900, '2020-03-09 16:52:05', NULL, 13),
(6, 500, '2020-03-09 17:18:52', '2020-07-09 16:56:56', 4),
(7, 1200, '2020-03-16 00:18:46', '2020-07-27 11:22:06', 1),
(8, 1800, '2020-03-16 00:19:08', NULL, 3),
(9, 1000, '2020-03-17 20:51:13', NULL, 14),
(10, 2300, '2020-03-20 19:21:35', NULL, 11),
(11, 600, '2020-07-09 16:56:57', '2020-07-09 17:00:22', 4),
(12, 500, '2020-07-09 17:00:22', '2020-07-09 17:06:36', 4),
(13, 600, '2020-07-09 17:06:36', NULL, 4),
(14, 454, '2020-07-26 15:40:56', NULL, 15),
(15, 10000000, '2020-07-27 11:22:06', '2020-07-27 11:27:47', 1),
(16, 100000000, '2020-07-27 11:27:47', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `prodId` bigint(20) NOT NULL,
  `prodBarCode` varchar(50) DEFAULT NULL,
  `prodName` varchar(100) NOT NULL,
  `prodNameBill` varchar(18) DEFAULT NULL,
  `prodStock` int(11) NOT NULL,
  `prodIva` int(11) NOT NULL DEFAULT '0',
  `prodBaseNumber` int(11) NOT NULL,
  `prodUnitValue` int(11) NOT NULL,
  `catId` bigint(20) NOT NULL,
  `brandId` bigint(20) DEFAULT NULL,
  `uniId` int(11) NOT NULL,
  `ownId` int(11) NOT NULL DEFAULT '1',
  `prodtypeId` int(11) NOT NULL,
  `prodComposition` bigint(20) DEFAULT NULL,
  `prodCompositionValue` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`prodId`, `prodBarCode`, `prodName`, `prodNameBill`, `prodStock`, `prodIva`, `prodBaseNumber`, `prodUnitValue`, `catId`, `brandId`, `uniId`, `ownId`, `prodtypeId`, `prodComposition`, `prodCompositionValue`) VALUES
(1, '304050608040', 'Coca cola', 'coca col 350ml', 90, 5, 50, 350, 4, 4, 1, 2, 1, NULL, NULL),
(2, '31', 'Papas margarita limon', 'pap marg limo 90g', 0, 0, 4, 90, 3, 2, 2, 1, 1, NULL, NULL),
(3, '32', 'Papas margarita pollo', 'pap marg pollo 90g', 3, 0, 8, 90, 3, 2, 2, 1, 1, NULL, NULL),
(4, '095489', 'Choco ramo', NULL, 9, 0, 14, 100, 3, 4, 1, 1, 1, NULL, NULL),
(5, '3434', 'Ponque ramo', NULL, 2, 0, 6, 58, 3, 2, 1, 1, 1, NULL, NULL),
(6, '45565', 'Manzana', NULL, 18, 0, 25, 350, 3, 5, 1, 1, 1, NULL, NULL),
(7, '34435', 'Uva', NULL, 3, 0, 8, 350, 3, 5, 1, 1, 1, NULL, NULL),
(8, '556567', 'Yogurt', NULL, 0, 0, 0, 20, 3, 4, 1, 1, 1, NULL, NULL),
(9, '489239', 'Sprite', NULL, 4, 0, 5, 350, 3, 4, 1, 1, 1, NULL, NULL),
(10, '859143', 'Sprite', NULL, -1, 0, 0, 600, 3, 4, 1, 1, 1, NULL, NULL),
(11, '33', 'Pollo sin viceras', 'pollo sin vice', 35000, 0, 40000, 1000, 4, 3, 2, 1, 2, NULL, NULL),
(12, '49895968', 'Prueba', NULL, 9, 5, 29, 350, 4, 5, 1, 1, 1, NULL, NULL),
(13, '545465', 'Curuba', NULL, 11, 5, 23, 34343, 4, 4, 1, 1, 1, NULL, NULL),
(14, '999999', 'Test2', NULL, 5, 5, 10, 350, 3, 2, 1, 3, 1, NULL, NULL),
(15, '797979', 'cocacola promo', 'test composite', 0, 0, 0, 475, 4, 4, 2, 1, 1, 4, 5);

-- --------------------------------------------------------

--
-- Table structure for table `productimage`
--

CREATE TABLE `productimage` (
  `prodimgId` bigint(20) NOT NULL,
  `prodimgPath` varchar(100) DEFAULT NULL,
  `prodId` bigint(20) NOT NULL,
  `prodimgMain` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productimage`
--

INSERT INTO `productimage` (`prodimgId`, `prodimgPath`, `prodId`, `prodimgMain`) VALUES
(2, '1/2.png', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `producttype`
--

CREATE TABLE `producttype` (
  `prodtypeId` int(11) NOT NULL,
  `prodtypeValue` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producttype`
--

INSERT INTO `producttype` (`prodtypeId`, `prodtypeValue`) VALUES
(1, 'Empaquetado'),
(2, 'Sin empaquetar');

-- --------------------------------------------------------

--
-- Table structure for table `provider`
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
-- Dumping data for table `provider`
--

INSERT INTO `provider` (`provId`, `provName`, `provPhones`, `provAddress`, `provRut`, `provNit`, `provWeb`) VALUES
(1, 'Coca cola', '343434,4342343,4342343', 'cre kdlksañ kdlsa', '432423', '34r5454543', 'wwww.dsakfdsa.com');

-- --------------------------------------------------------

--
-- Table structure for table `provides`
--

CREATE TABLE `provides` (
  `providesId` bigint(20) NOT NULL,
  `provId` bigint(20) NOT NULL,
  `prodId` bigint(20) NOT NULL,
  `providesInitialDate` datetime NOT NULL,
  `providesFinalDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `provides`
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
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `purId` bigint(20) NOT NULL,
  `purDate` datetime NOT NULL,
  `purFinalAmount` int(11) DEFAULT NULL,
  `purReceivedAmount` int(11) DEFAULT NULL,
  `purDiscount` int(11) DEFAULT NULL,
  `purPayment` int(11) DEFAULT NULL,
  `cliId` bigint(20) DEFAULT NULL,
  `usId` bigint(20) NOT NULL,
  `purState` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`purId`, `purDate`, `purFinalAmount`, `purReceivedAmount`, `purDiscount`, `purPayment`, `cliId`, `usId`, `purState`) VALUES
(1, '2020-07-18 22:59:42', 1600, 2000, NULL, NULL, NULL, 3, 1),
(2, '2020-07-18 23:02:59', 1600, 2000, NULL, NULL, NULL, 3, 1),
(3, '2020-07-18 23:07:23', 1600, 2000, NULL, NULL, NULL, 3, 1),
(4, '2020-07-21 23:24:11', 1600, 2000, NULL, NULL, NULL, 3, 1),
(5, '2020-07-21 23:33:21', 2203, 3000, NULL, NULL, NULL, 3, 1),
(6, '2020-07-21 23:36:32', 1600, 2000, NULL, NULL, NULL, 3, 1),
(7, '2020-07-21 23:39:54', 1600, 2000, NULL, NULL, NULL, 3, 1),
(8, '2020-07-21 23:40:18', 2200, 10000, NULL, NULL, NULL, 3, 1),
(9, '2020-07-21 23:41:51', 800, 1000, NULL, NULL, NULL, 3, 1),
(10, '2020-07-21 23:43:28', 1600, 4000, NULL, NULL, NULL, 3, 1),
(11, '2020-07-21 23:44:06', 700, 4000, NULL, NULL, NULL, 3, 1),
(12, '2020-07-21 23:45:17', 1800, 2000, NULL, NULL, NULL, 3, 1),
(13, '2020-07-21 23:46:09', 700, 5000, NULL, NULL, NULL, 3, 1),
(14, '2020-07-21 23:47:25', 1600, 3000, NULL, NULL, NULL, 3, 1),
(15, '2020-07-21 23:49:27', 1800, 20000, NULL, NULL, NULL, 3, 1),
(17, '2020-07-21 23:57:57', 800, 1000, NULL, NULL, NULL, 3, 1),
(18, '2020-07-21 23:58:54', 700, 1000, NULL, NULL, NULL, 3, 1),
(25, '2020-07-27 14:30:14', 200002600, 220000000, NULL, NULL, NULL, 3, 1),
(27, '2020-07-27 14:38:27', 3003, 4000, NULL, NULL, NULL, 3, 1),
(29, '2020-07-27 14:44:40', 2203, 30000, NULL, NULL, NULL, 3, 1),
(31, '2020-07-27 14:50:37', 2200, 3000, NULL, NULL, NULL, 3, 1),
(32, '2020-07-27 14:52:45', 800, 1000, NULL, NULL, NULL, 3, 1),
(33, '2020-07-27 14:54:38', 2200, 2200, NULL, NULL, NULL, 3, 1),
(34, '2020-07-27 14:57:17', 1800, 2000, NULL, NULL, NULL, 3, 1),
(35, '2020-07-27 14:58:18', 2400, 3000, NULL, NULL, NULL, 3, 1),
(36, '2020-07-27 14:59:52', 10000, 20000, NULL, NULL, NULL, 3, 1),
(37, '2020-07-27 15:00:23', 1800, 2000, NULL, NULL, NULL, 3, 1),
(38, '2020-07-27 15:02:40', 1800, 2000, NULL, NULL, NULL, 3, 1),
(40, '2020-07-27 15:09:05', 6600, 7000, NULL, NULL, NULL, 3, 1),
(41, '2020-07-27 15:14:51', NULL, NULL, NULL, NULL, NULL, 3, 0),
(42, '2020-08-02 12:08:35', 800, 800, NULL, NULL, 4, 3, 1),
(43, '2020-08-02 12:18:44', 1800, 2000, NULL, NULL, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchaseitem`
--

CREATE TABLE `purchaseitem` (
  `purItemId` bigint(20) NOT NULL,
  `purItemQuantity` int(11) NOT NULL,
  `prodId` bigint(20) NOT NULL,
  `purId` bigint(20) NOT NULL,
  `priceValue` int(11) NOT NULL,
  `iva` int(11) NOT NULL,
  `pricePurValue` int(11) NOT NULL,
  `ownId` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchaseitem`
--

INSERT INTO `purchaseitem` (`purItemId`, `purItemQuantity`, `prodId`, `purId`, `priceValue`, `iva`, `pricePurValue`, `ownId`) VALUES
(1, 1, 1, 1, 1600, 19, 1200, 2),
(2, 1, 1, 2, 1600, 19, 1200, 2),
(3, 1, 1, 3, 1600, 5, 1200, 2),
(4, 1, 1, 4, 1600, 5, 1200, 2),
(5, 1, 2, 5, 2203, 0, 2000, 1),
(6, 1, 1, 6, 1600, 5, 1200, 2),
(7, 1, 1, 7, 1600, 5, 1200, 2),
(8, 1, 10, 8, 2200, 0, 2200, 1),
(9, 1, 4, 9, 800, 0, 600, 1),
(10, 1, 1, 10, 1600, 5, 1200, 2),
(11, 1, 5, 11, 700, 0, 700, 1),
(12, 1, 7, 12, 1800, 0, 1800, 1),
(13, 1, 5, 13, 700, 0, 700, 1),
(14, 1, 1, 14, 1600, 5, 1200, 2),
(15, 1, 6, 15, 1800, 0, 1800, 1),
(17, 1, 4, 17, 800, 0, 600, 1),
(18, 1, 5, 18, 700, 0, 700, 1),
(39, 1, 12, 25, 2600, 5, 2600, 1),
(40, 2, 1, 25, 100000000, 5, 100000000, 2),
(42, 1, 4, 27, 800, 0, 600, 1),
(43, 1, 2, 27, 2203, 0, 2000, 1),
(45, 1, 2, 29, 2203, 0, 2000, 1),
(47, 1, 3, 31, 2200, 0, 1800, 1),
(48, 1, 4, 32, 800, 0, 600, 1),
(49, 1, 3, 33, 2200, 0, 1800, 1),
(50, 1, 7, 34, 1800, 0, 1800, 1),
(51, 1, 13, 35, 2400, 5, 1900, 1),
(52, 4000, 11, 36, 2500, 0, 2300, 1),
(53, 1, 6, 37, 1800, 0, 1800, 1),
(54, 1, 7, 38, 1800, 0, 1800, 1),
(56, 3, 3, 40, 2200, 0, 1800, 1),
(57, 1, 2, 41, 2203, 0, 2000, 1),
(58, 1, 4, 42, 800, 0, 600, 1),
(59, 1, 7, 43, 1800, 0, 1800, 1);

--
-- Triggers `purchaseitem`
--
DELIMITER $$
CREATE TRIGGER `delete_purchaseitem` AFTER DELETE ON `purchaseitem` FOR EACH ROW BEGIN
DECLARE PROID bigint(20);
DECLARE COMPOSITEVALUE int;
SET PROID =(SELECT product.prodComposition FROM product WHERE 						product.prodId = OLD.prodId);

SET COMPOSITEVALUE =(SELECT product.prodCompositionValue FROM product WHERE product.prodId = OLD.prodId);

IF(PROID IS NULL) THEN
	UPDATE product SET product.prodStock = product.prodStock + 				OLD.purItemQuantity WHERE product.prodId = OLD.prodId;
ELSE
	UPDATE product SET product.prodStock = (product.prodStock + 		     OLD.purItemQuantity * COMPOSITEVALUE) WHERE product.prodId = PROID;
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insert_purchaseitem` AFTER INSERT ON `purchaseitem` FOR EACH ROW BEGIN
DECLARE PROID bigint(20);
DECLARE COMPOSITEVALUE int;
SET PROID =(SELECT product.prodComposition FROM product WHERE 						product.prodId = NEW.prodId);

SET COMPOSITEVALUE =(SELECT product.prodCompositionValue FROM product WHERE product.prodId = NEW.prodId);

IF(PROID IS NULL) THEN
	UPDATE product SET product.prodStock = (product.prodStock - 		     NEW.purItemQuantity) WHERE product.prodId = NEW.prodId;
ELSE
	UPDATE product SET product.prodStock = (product.prodStock - 		     NEW.purItemQuantity * COMPOSITEVALUE) WHERE product.prodId = PROID;
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_purshaseitem` AFTER UPDATE ON `purchaseitem` FOR EACH ROW BEGIN
DECLARE PROID bigint(20);
DECLARE COMPOSITEVALUE int;
SET PROID =(SELECT product.prodComposition FROM product WHERE 						product.prodId = NEW.prodId);

SET COMPOSITEVALUE =(SELECT product.prodCompositionValue FROM product WHERE product.prodId = NEW.prodId);

IF(PROID IS NULL) THEN
	UPDATE product SET product.prodStock = (product.prodStock - 			(NEW.purItemQuantity - OLD.purItemQuantity)) WHERE product.prodId = 	NEW.prodId;
ELSE
	UPDATE product SET product.prodStock = (product.prodStock -(( 		     NEW.purItemQuantity * COMPOSITEVALUE) - (OLD.purItemQuantity * COMPOSITEVALUE))) WHERE product.prodId = PROID;
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `purchasetotal`
--

CREATE TABLE `purchasetotal` (
  `purToId` bigint(20) NOT NULL,
  `purId` bigint(20) NOT NULL,
  `ownId` int(11) NOT NULL,
  `purToTotal` int(11) NOT NULL,
  `purToGain` int(11) NOT NULL,
  `purToIva` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchasetotal`
--

INSERT INTO `purchasetotal` (`purToId`, `purId`, `ownId`, `purToTotal`, `purToGain`, `purToIva`) VALUES
(1, 1, 2, 1600, 400, 63.8655),
(2, 2, 2, 1600, 400, 63.8655),
(3, 3, 2, 1600, 400, 19.0476),
(4, 4, 2, 1600, 400, 19.0476),
(5, 5, 1, 2203, 203, 0),
(6, 6, 2, 1600, 400, 19.0476),
(7, 7, 2, 1600, 400, 19.0476),
(8, 8, 1, 2200, 0, 0),
(9, 9, 1, 800, 200, 0),
(10, 10, 2, 1600, 400, 19.0476),
(11, 11, 1, 700, 0, 0),
(12, 12, 1, 1800, 0, 0),
(13, 13, 1, 700, 0, 0),
(14, 14, 2, 1600, 400, 19.0476),
(15, 15, 1, 1800, 0, 0),
(16, 17, 1, 800, 200, 0),
(17, 18, 1, 700, 0, 0),
(18, 25, 1, 2600, 0, 0),
(19, 25, 2, 200000000, 0, 0),
(20, 27, 1, 3003, 403, 0),
(21, 29, 1, 2203, 203, 0),
(22, 31, 1, 2200, 400, 0),
(23, 32, 1, 800, 200, 0),
(24, 33, 1, 2200, 400, 0),
(25, 34, 1, 1800, 0, 0),
(26, 35, 1, 2400, 500, 23.8095),
(27, 36, 1, 10000, 800, 0),
(28, 37, 1, 1800, 0, 0),
(29, 38, 1, 1800, 0, 0),
(30, 40, 1, 6600, 1200, 0),
(31, 42, 1, 800, 200, 0),
(32, 43, 1, 1800, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `unity`
--

CREATE TABLE `unity` (
  `uniId` int(11) NOT NULL,
  `uniName` varchar(60) NOT NULL,
  `uniAbbreviation` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `unity`
--

INSERT INTO `unity` (`uniId`, `uniName`, `uniAbbreviation`) VALUES
(1, 'Mililitros', 'ml'),
(2, 'Gramos', 'gr');

-- --------------------------------------------------------

--
-- Table structure for table `user`
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
-- Dumping data for table `user`
--

INSERT INTO `user` (`usId`, `usIdentification`, `usName`, `usLastName`, `usUserName`, `usPassword`, `usEmail`, `usAddress`, `usPhone`, `usActive`) VALUES
(1, '1075220291', 'Wilson', 'Carvajal', 'wgcarvajal', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'wilnacio@hotmail.com', NULL, NULL, 1),
(2, '1061705800', 'Deisy', 'Piñeros', 'deisypt', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'deisypt@gmail.com', NULL, NULL, 1),
(3, '34545465', 'Maria Juliana', 'Carvajal Piñeros', 'maju', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'maju@hotmail.com', NULL, NULL, 1),
(4, '16190612', 'Luis Alberto', 'Carvajal Diaz', 'lucho', '8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414', 'luiscarvajal@hotmail.com', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `usergroup`
--

CREATE TABLE `usergroup` (
  `id` bigint(20) NOT NULL,
  `usUserName` varchar(75) NOT NULL,
  `grouId` varchar(20) NOT NULL,
  `usId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usergroup`
--

INSERT INTO `usergroup` (`id`, `usUserName`, `grouId`, `usId`) VALUES
(2, 'wgcarvajal', 'sAdmin', 1),
(3, 'deisypt', 'cashier', 2),
(4, 'maju', 'cashier', 3),
(5, 'lucho', 'admin', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`brandId`);

--
-- Indexes for table `cash`
--
ALTER TABLE `cash`
  ADD PRIMARY KEY (`cashId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`catId`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`cliId`),
  ADD UNIQUE KEY `cliIdentity` (`cliIdentity`),
  ADD UNIQUE KEY `cliIdentity_2` (`cliIdentity`);

--
-- Indexes for table `groupp`
--
ALTER TABLE `groupp`
  ADD PRIMARY KEY (`grouId`);

--
-- Indexes for table `lend`
--
ALTER TABLE `lend`
  ADD PRIMARY KEY (`lendId`),
  ADD KEY `fk_lend_client` (`cliId`),
  ADD KEY `fk_lend_user` (`usId`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`ownId`);

--
-- Indexes for table `pay`
--
ALTER TABLE `pay`
  ADD PRIMARY KEY (`payId`),
  ADD KEY `fk_pay_client` (`cliId`),
  ADD KEY `usId` (`usId`);

--
-- Indexes for table `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`priceId`),
  ADD KEY `fk_price_product` (`prodId`);

--
-- Indexes for table `pricepurchase`
--
ALTER TABLE `pricepurchase`
  ADD PRIMARY KEY (`pricePurId`),
  ADD KEY `fk_pricepurshase_product` (`prodId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`prodId`),
  ADD KEY `fk_product_brand` (`brandId`),
  ADD KEY `fk_produt_category` (`catId`),
  ADD KEY `fk_product_unity` (`uniId`),
  ADD KEY `fk_product_producttype` (`prodtypeId`),
  ADD KEY `fk_product_owner` (`ownId`),
  ADD KEY `fk_product_product` (`prodComposition`);

--
-- Indexes for table `productimage`
--
ALTER TABLE `productimage`
  ADD PRIMARY KEY (`prodimgId`),
  ADD KEY `fk_productimage_product` (`prodId`);

--
-- Indexes for table `producttype`
--
ALTER TABLE `producttype`
  ADD PRIMARY KEY (`prodtypeId`);

--
-- Indexes for table `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`provId`);

--
-- Indexes for table `provides`
--
ALTER TABLE `provides`
  ADD PRIMARY KEY (`providesId`),
  ADD KEY `fk_provides_provider` (`provId`),
  ADD KEY `fk_provides_product` (`prodId`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`purId`),
  ADD KEY `fk_purchase_client` (`cliId`),
  ADD KEY `fk_purshase_user` (`usId`);

--
-- Indexes for table `purchaseitem`
--
ALTER TABLE `purchaseitem`
  ADD PRIMARY KEY (`purItemId`),
  ADD KEY `fk_purchaseitem_product` (`prodId`),
  ADD KEY `fk_purchaseitem_purchase` (`purId`),
  ADD KEY `fk_purchaseitem_owner` (`ownId`);

--
-- Indexes for table `purchasetotal`
--
ALTER TABLE `purchasetotal`
  ADD PRIMARY KEY (`purToId`),
  ADD KEY `fk_purshaseTotal_purshase` (`purId`),
  ADD KEY `fk_purshaseTotal_owner` (`ownId`);

--
-- Indexes for table `unity`
--
ALTER TABLE `unity`
  ADD PRIMARY KEY (`uniId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`usId`);

--
-- Indexes for table `usergroup`
--
ALTER TABLE `usergroup`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usergroup_user` (`usId`),
  ADD KEY `fk_usergroup_groupp` (`grouId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `brandId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cash`
--
ALTER TABLE `cash`
  MODIFY `cashId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `catId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `cliId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `lend`
--
ALTER TABLE `lend`
  MODIFY `lendId` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `owner`
--
ALTER TABLE `owner`
  MODIFY `ownId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pay`
--
ALTER TABLE `pay`
  MODIFY `payId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `price`
--
ALTER TABLE `price`
  MODIFY `priceId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `pricepurchase`
--
ALTER TABLE `pricepurchase`
  MODIFY `pricePurId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `prodId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `productimage`
--
ALTER TABLE `productimage`
  MODIFY `prodimgId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `producttype`
--
ALTER TABLE `producttype`
  MODIFY `prodtypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `provider`
--
ALTER TABLE `provider`
  MODIFY `provId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `provides`
--
ALTER TABLE `provides`
  MODIFY `providesId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `purId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `purchaseitem`
--
ALTER TABLE `purchaseitem`
  MODIFY `purItemId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `purchasetotal`
--
ALTER TABLE `purchasetotal`
  MODIFY `purToId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `unity`
--
ALTER TABLE `unity`
  MODIFY `uniId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `usId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `usergroup`
--
ALTER TABLE `usergroup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `lend`
--
ALTER TABLE `lend`
  ADD CONSTRAINT `fk_lend_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `fk_lend_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Constraints for table `pay`
--
ALTER TABLE `pay`
  ADD CONSTRAINT `fk_pay_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `pay_ibfk_1` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Constraints for table `price`
--
ALTER TABLE `price`
  ADD CONSTRAINT `fk_price_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Constraints for table `pricepurchase`
--
ALTER TABLE `pricepurchase`
  ADD CONSTRAINT `fk_pricepurshase_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_brand` FOREIGN KEY (`brandId`) REFERENCES `brand` (`brandId`),
  ADD CONSTRAINT `fk_product_owner` FOREIGN KEY (`ownId`) REFERENCES `owner` (`ownId`),
  ADD CONSTRAINT `fk_product_product` FOREIGN KEY (`prodComposition`) REFERENCES `product` (`prodId`),
  ADD CONSTRAINT `fk_product_producttype` FOREIGN KEY (`prodtypeId`) REFERENCES `producttype` (`prodtypeId`),
  ADD CONSTRAINT `fk_product_unity` FOREIGN KEY (`uniId`) REFERENCES `unity` (`uniId`),
  ADD CONSTRAINT `fk_produt_category` FOREIGN KEY (`catId`) REFERENCES `category` (`catId`);

--
-- Constraints for table `productimage`
--
ALTER TABLE `productimage`
  ADD CONSTRAINT `fk_productimage_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`);

--
-- Constraints for table `provides`
--
ALTER TABLE `provides`
  ADD CONSTRAINT `fk_provides_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`),
  ADD CONSTRAINT `fk_provides_provider` FOREIGN KEY (`provId`) REFERENCES `provider` (`provId`);

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_purchase_client` FOREIGN KEY (`cliId`) REFERENCES `client` (`cliId`),
  ADD CONSTRAINT `fk_purshase_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);

--
-- Constraints for table `purchaseitem`
--
ALTER TABLE `purchaseitem`
  ADD CONSTRAINT `fk_purchaseitem_owner` FOREIGN KEY (`ownId`) REFERENCES `owner` (`ownId`),
  ADD CONSTRAINT `fk_purchaseitem_product` FOREIGN KEY (`prodId`) REFERENCES `product` (`prodId`),
  ADD CONSTRAINT `fk_purchaseitem_purchase` FOREIGN KEY (`purId`) REFERENCES `purchase` (`purId`);

--
-- Constraints for table `purchasetotal`
--
ALTER TABLE `purchasetotal`
  ADD CONSTRAINT `fk_purshaseTotal_owner` FOREIGN KEY (`ownId`) REFERENCES `owner` (`ownId`),
  ADD CONSTRAINT `fk_purshaseTotal_purshase` FOREIGN KEY (`purId`) REFERENCES `purchase` (`purId`);

--
-- Constraints for table `usergroup`
--
ALTER TABLE `usergroup`
  ADD CONSTRAINT `fk_usergroup_groupp` FOREIGN KEY (`grouId`) REFERENCES `groupp` (`grouId`),
  ADD CONSTRAINT `fk_usergroup_user` FOREIGN KEY (`usId`) REFERENCES `user` (`usId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
