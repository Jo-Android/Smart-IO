-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 05, 2023 at 12:19 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_agriculture`
--

-- --------------------------------------------------------

--
-- Table structure for table `CropCategory`
--

CREATE TABLE `CropCategory` (
  `ID` int(11) NOT NULL,
  `Category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `CropCategory`
--

INSERT INTO `CropCategory` (`ID`, `Category`) VALUES
(3, 'No CAtegory'),
(5, 'Vegetable'),
(7, 'Catge'),
(8, NULL),
(9, 'Catge'),
(10, 'pLANT'),
(11, 'Veggies'),
(12, 'Veggies'),
(14, 'Fruits');

-- --------------------------------------------------------

--
-- Table structure for table `Crops`
--

CREATE TABLE `Crops` (
  `autoID` int(11) NOT NULL,
  `CropCode` varchar(20) DEFAULT NULL,
  `CropType` int(11) NOT NULL,
  `CropName` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Crops`
--

INSERT INTO `Crops` (`autoID`, `CropCode`, `CropType`, `CropName`) VALUES
(1, 'ABF12342', 5, 'Tomato'),
(2, NULL, 3, NULL),
(3, 'ABF123', 3, 'Avocado'),
(4, 'ABF123', 5, 'Eggplant'),
(5, 'ZCH', 5, 'Zuchini');

-- --------------------------------------------------------

--
-- Table structure for table `Sensor`
--

CREATE TABLE `Sensor` (
  `SensorID` int(11) NOT NULL,
  `Type` int(11) NOT NULL,
  `Name` varchar(200) DEFAULT NULL,
  `QRCode` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Sensor`
--

INSERT INTO `Sensor` (`SensorID`, `Type`, `Name`, `QRCode`) VALUES
(1, 1, 'DHT22', 'FGD123456'),
(2, 1, 'DHT23', 'FGD1265'),
(3, 2, 'TempH5432', '55412012'),
(4, 2, 'DHT25', 'FHG12345');

-- --------------------------------------------------------

--
-- Table structure for table `SensorCrop`
--

CREATE TABLE `SensorCrop` (
  `id` int(11) NOT NULL,
  `CropID` int(11) NOT NULL,
  `SensorType` int(11) NOT NULL,
  `MinValue` double(10,2) DEFAULT NULL,
  `MaximumValue` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SensorCrop`
--

INSERT INTO `SensorCrop` (`id`, `CropID`, `SensorType`, `MinValue`, `MaximumValue`) VALUES
(1, 1, 1, 48.00, 98.00),
(2, 2, 1, 58.00, 88.00),
(3, 1, 2, 20.00, 25.00),
(4, 2, 2, 58.00, 88.00);

-- --------------------------------------------------------

--
-- Table structure for table `SensorCropState`
--

CREATE TABLE `SensorCropState` (
  `StateID` int(11) NOT NULL,
  `Sensor` int(11) NOT NULL,
  `StartDate` varchar(20) DEFAULT NULL,
  `EndDate` varchar(20) DEFAULT NULL,
  `CropID` int(11) NOT NULL,
  `isActive` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SensorCropState`
--

INSERT INTO `SensorCropState` (`StateID`, `Sensor`, `StartDate`, `EndDate`, `CropID`, `isActive`) VALUES
(1, 1, '30/12/2022', NULL, 1, 1),
(2, 2, '30/12/2022', NULL, 1, 1),
(3, 2, '30/12/2022', NULL, 2, 1),
(4, 3, '30/12/2022', NULL, 3, 1),
(5, 4, '30/12/2022', '03/01/2023 20:12:53', 1, 0),
(6, 4, '30/12/2022', '03/01/2023 20:12:53', 2, 0),
(7, 4, '30/12/2022', '03/01/2023 20:12:53', 3, 0),
(8, 6, '30/12/2022', '03/01/2023 20:12:53', 1, 0),
(9, 6, '30/12/2022', '03/01/2023 20:12:53', 2, 0),
(10, 1, '3/1/2023', NULL, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `SensorType`
--

CREATE TABLE `SensorType` (
  `autoID` int(11) NOT NULL,
  `Type` varchar(30) DEFAULT NULL,
  `UnitOfMeasurement` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SensorType`
--

INSERT INTO `SensorType` (`autoID`, `Type`, `UnitOfMeasurement`) VALUES
(1, 'Humidity', '%'),
(2, 'Temperature', 'C'),
(3, 'Gyroscope', 'Dgeree');

-- --------------------------------------------------------

--
-- Table structure for table `SensorUser`
--

CREATE TABLE `SensorUser` (
  `AssignID` int(11) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `Sensor` int(11) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `SensorUser`
--

INSERT INTO `SensorUser` (`AssignID`, `userID`, `Sensor`, `isActive`) VALUES
(1, 4, 1, 1),
(2, 4, 2, 1),
(3, 4, 3, 1),
(4, 7, 1, 0),
(6, 7, 2, 0),
(7, 7, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `SensorValues`
--

CREATE TABLE `SensorValues` (
  `SensorCropState` int(11) NOT NULL,
  `EntryDateTime` varchar(20) DEFAULT NULL,
  `SensorValue` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `UserTable`
--

CREATE TABLE `UserTable` (
  `userID` int(11) NOT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `UserName` varchar(30) DEFAULT NULL,
  `UserPassword` varchar(20) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `DOB` varchar(10) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isManager` tinyint(1) DEFAULT NULL,
  `IpAddress` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `UserTable`
--

INSERT INTO `UserTable` (`userID`, `ManagerID`, `FirstName`, `LastName`, `UserName`, `UserPassword`, `PhoneNumber`, `DOB`, `isActive`, `isManager`, `IpAddress`) VALUES
(4, NULL, 'Marc', 'Harfuch', 'MCh1996', 'ABF123456', '09654120874', '13/05/1196', 1, 1, '192.168.0.12'),
(5, NULL, 'Hady', 'Harfuch', 'hady654', 'ABF3', '09654785120', '10/11/1196', 1, 0, NULL),
(6, NULL, 'Roy', 'Harfuch', 'Roy543', 'ABASWF3', '09654785120', '10/11/1196', 1, 0, NULL),
(7, NULL, 'Jad', 'Hankach', 'Jad453213', 'ABASWF3', '09654785120', '10/11/1196', 0, 0, NULL),
(10, NULL, 'Ahmad', 'Hankach', 'Ahm13421', 'ABASWF3', '09654785120', '10/11/1196', 1, 0, '192.168.1.2'),
(11, NULL, 'Karen', 'Kechichyan', 'KK1196', 'ABASWF3', '09658285120', '10/1/1186', 1, 0, '192.168.1.29');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CropCategory`
--
ALTER TABLE `CropCategory`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Crops`
--
ALTER TABLE `Crops`
  ADD PRIMARY KEY (`autoID`),
  ADD KEY `CropType` (`CropType`);

--
-- Indexes for table `Sensor`
--
ALTER TABLE `Sensor`
  ADD PRIMARY KEY (`SensorID`),
  ADD KEY `Type` (`Type`);

--
-- Indexes for table `SensorCrop`
--
ALTER TABLE `SensorCrop`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CropID` (`CropID`);

--
-- Indexes for table `SensorCropState`
--
ALTER TABLE `SensorCropState`
  ADD PRIMARY KEY (`StateID`),
  ADD KEY `CropID` (`CropID`),
  ADD KEY `Sensor` (`Sensor`);

--
-- Indexes for table `SensorType`
--
ALTER TABLE `SensorType`
  ADD PRIMARY KEY (`autoID`);

--
-- Indexes for table `SensorUser`
--
ALTER TABLE `SensorUser`
  ADD PRIMARY KEY (`AssignID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `Sensor` (`Sensor`);

--
-- Indexes for table `SensorValues`
--
ALTER TABLE `SensorValues`
  ADD KEY `SensorCropState` (`SensorCropState`);

--
-- Indexes for table `UserTable`
--
ALTER TABLE `UserTable`
  ADD PRIMARY KEY (`userID`),
  ADD KEY `ManagerID` (`ManagerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CropCategory`
--
ALTER TABLE `CropCategory`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Crops`
--
ALTER TABLE `Crops`
  MODIFY `autoID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Sensor`
--
ALTER TABLE `Sensor`
  MODIFY `SensorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `SensorCrop`
--
ALTER TABLE `SensorCrop`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `SensorCropState`
--
ALTER TABLE `SensorCropState`
  MODIFY `StateID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `SensorType`
--
ALTER TABLE `SensorType`
  MODIFY `autoID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `SensorUser`
--
ALTER TABLE `SensorUser`
  MODIFY `AssignID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `UserTable`
--
ALTER TABLE `UserTable`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Crops`
--
ALTER TABLE `Crops`
  ADD CONSTRAINT `crops_ibfk_1` FOREIGN KEY (`CropType`) REFERENCES `CropCategory` (`ID`);

--
-- Constraints for table `Sensor`
--
ALTER TABLE `Sensor`
  ADD CONSTRAINT `sensor_ibfk_1` FOREIGN KEY (`Type`) REFERENCES `SensorType` (`autoID`);

--
-- Constraints for table `SensorCrop`
--
ALTER TABLE `SensorCrop`
  ADD CONSTRAINT `sensorcrop_ibfk_1` FOREIGN KEY (`CropID`) REFERENCES `Crops` (`autoID`),
  ADD CONSTRAINT `sensorcrop_ibfk_4` FOREIGN KEY (`SensorType`) REFERENCES `SensorType` (`autoID`);

--
-- Constraints for table `SensorCropState`
--
ALTER TABLE `SensorCropState`
  ADD CONSTRAINT `sensorcropstate_ibfk_2` FOREIGN KEY (`CropID`) REFERENCES `SensorCrop` (`id`),
  ADD CONSTRAINT `sensorcropstate_ibfk_3` FOREIGN KEY (`Sensor`) REFERENCES `SensorUser` (`AssignID`);

--
-- Constraints for table `SensorUser`
--
ALTER TABLE `SensorUser`
  ADD CONSTRAINT `sensoruser_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `UserTable` (`userID`),
  ADD CONSTRAINT `sensoruser_ibfk_2` FOREIGN KEY (`Sensor`) REFERENCES `Sensor` (`SensorID`);

--
-- Constraints for table `SensorValues`
--
ALTER TABLE `SensorValues`
  ADD CONSTRAINT `sensorvalues_ibfk_1` FOREIGN KEY (`SensorCropState`) REFERENCES `SensorCropState` (`StateID`);

--
-- Constraints for table `UserTable`
--
ALTER TABLE `UserTable`
  ADD CONSTRAINT `usertable_ibfk_1` FOREIGN KEY (`ManagerID`) REFERENCES `UserTable` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
