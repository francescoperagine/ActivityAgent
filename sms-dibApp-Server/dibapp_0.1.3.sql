-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Ago 14, 2019 alle 11:32
-- Versione del server: 5.7.26
-- Versione PHP: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dibapp`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `class`
--

DROP TABLE IF EXISTS `class`;
CREATE TABLE IF NOT EXISTS `class` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `description` text,
  `code` varchar(10) NOT NULL,
  `year` int(1) NOT NULL,
  `semester` int(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class`
--

INSERT INTO `class` (`ID`, `name`, `description`, `code`, `year`, `semester`) VALUES
(1, 'ANALISI MATEMATICA', NULL, '000178', 1, 2),
(2, 'ARCHITETTURA DEGLI ELABORATORI E SISTEMI OPERATIVI', NULL, '062978', 1, 2),
(3, 'LABORATORIO DI INFORMATICA', NULL, '004724', 1, 1),
(4, 'LINGUA INGLESE', NULL, '005626', 1, 1),
(5, 'LINGUAGGI DI PROGRAMMAZIONE', NULL, '010194', 1, 2),
(6, 'MATEMATICA DISCRETA', NULL, '006002', 1, 1),
(7, 'PROGRAMMAZIONE', NULL, '007398', 1, 1),
(8, 'ANALISI E PROGETTAZIONE DI SISTEMI SOFTWARE', NULL, '062985', 2, 2),
(9, 'CALCOLO NUMERICO', NULL, '014368', 2, 2),
(10, 'ECONOMIA E GESTIONE D\'IMPRESA', NULL, '062991', 2, 1),
(11, 'FISICA APPLICATA ALL\'INFORMATICA', NULL, '062984', 2, 2),
(12, 'PROGETTAZIONE DI BASI DI DATI', NULL, '062983', 2, 1),
(13, 'RETI DI CALCOLATORI', NULL, '017451', 2, 1),
(14, 'STATISTICA PER L\'INGEGNERIA DEL SOFTWARE', NULL, '062986', 2, 2),
(15, 'INTEGRAZIONE E TEST DI SISTEMI SOFTWARE', NULL, '062989', 3, 1),
(16, 'LINGUA ITALIANA: TECNICHE DI COMUNICAZIONE', NULL, '005631', 1, 1),
(17, 'MODELLI E METODI PER LA QUALITA\' DEL SOFTWARE', NULL, '062939', 3, 1),
(18, 'PROGETTAZIONE DELL\'INTERAZIONE CON L\'UTENTE + LABORATORIO', NULL, '061433', 3, 1),
(19, 'PROGRAMMAZIONE PER IL WEB + LABORATORIO', NULL, '007396', 3, 2),
(20, 'SVILUPPO DI MOBILE SOFTWARE', NULL, '062992', 2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `class_room_calendar`
--

DROP TABLE IF EXISTS `class_room_calendar`;
CREATE TABLE IF NOT EXISTS `class_room_calendar` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `classID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `day` int(1) NOT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `class_room_lesson`
--

DROP TABLE IF EXISTS `class_room_lesson`;
CREATE TABLE IF NOT EXISTS `class_room_lesson` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `calendarID` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `degreecourse`
--

DROP TABLE IF EXISTS `degreecourse`;
CREATE TABLE IF NOT EXISTS `degreecourse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `description` text,
  `ministerialDecree` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `degreecourse`
--

INSERT INTO `degreecourse` (`ID`, `name`, `description`, `ministerialDecree`) VALUES
(1, 'Informatica', NULL, '270/04'),
(2, 'Informatica e Tecnologie per la produzione del software', NULL, '270/04'),
(3, 'Informatica e Comunicazione Digitale sede di Taranto', NULL, '270/04');

-- --------------------------------------------------------

--
-- Struttura della tabella `degreecourse_class`
--

DROP TABLE IF EXISTS `degreecourse_class`;
CREATE TABLE IF NOT EXISTS `degreecourse_class` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `degreecourseID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `degreecourse_class`
--

INSERT INTO `degreecourse_class` (`ID`, `degreecourseID`, `classID`) VALUES
(1, 2, 8),
(2, 2, 1),
(3, 2, 2),
(4, 2, 9),
(5, 2, 10),
(6, 2, 11),
(7, 2, 15),
(8, 2, 3),
(9, 2, 5),
(10, 2, 4),
(11, 2, 16),
(12, 2, 6),
(13, 2, 17),
(14, 2, 18),
(15, 2, 12),
(16, 2, 7),
(17, 2, 19),
(18, 2, 13),
(19, 2, 14),
(20, 2, 20);

-- --------------------------------------------------------

--
-- Struttura della tabella `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `role`
--

INSERT INTO `role` (`ID`, `name`) VALUES
(1, 'teacher'),
(2, 'student'),
(3, 'backoffice operator');

-- --------------------------------------------------------

--
-- Struttura della tabella `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `room`
--

INSERT INTO `room` (`ID`, `name`) VALUES
(1, '2 - P.zzo aule'),
(2, '4 - P.zzo aule'),
(3, 'A - P.Terra'),
(4, 'B - P.Terra'),
(5, 'Magna - P.Terra'),
(6, 'A - I_Piano'),
(7, 'B - I_Piano'),
(8, 'Hume - II_Piano'),
(9, 'Godel - II_Piano'),
(10, 'B - II_Piano'),
(11, 'A - II_Piano'),
(12, 'Sala Consiglio');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `serialNumber` varchar(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `passwordHash` varchar(256) NOT NULL,
  `salt` varchar(256) NOT NULL,
  `registrationDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`ID`, `serialNumber`, `name`, `surname`, `email`, `passwordHash`, `salt`, `registrationDate`) VALUES
(1, '443020', 'Francesco', 'Peragine', 'francescoperagine@gmail.com', '$2y$10$wG9wq4KArvDcbwwbVlsogu6Z9IIl8uTlxfIKb4hW5th/EIiZD8W8W', 'f627667e46de19e736b1a8239b0928a4eba100e522bbc3d0a9cf2a21f9c891ff', NULL),
(19, '123456', 'paolo', 'buono', 'buono@uniba.it', '$2y$10$zDnrHIsGSEgiuVzSJk7Z7u/TnIjWxfCg1xgx6v6fg3ZRvrk49JC1i', 'debd8a7b1884f93e0573fa704ffae26231b79fb5c6f97d72e6f5c5986ccd8bce', '2019-08-13 10:39:44');

-- --------------------------------------------------------

--
-- Struttura della tabella `user_class_rating`
--

DROP TABLE IF EXISTS `user_class_rating`;
CREATE TABLE IF NOT EXISTS `user_class_rating` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `lessonID` int(11) NOT NULL,
  `rating` int(1) DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `review` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_degreecourse`
--

DROP TABLE IF EXISTS `user_degreecourse`;
CREATE TABLE IF NOT EXISTS `user_degreecourse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `degreecourseID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_degreecourse`
--

INSERT INTO `user_degreecourse` (`ID`, `userID`, `degreecourseID`) VALUES
(1, 1, 2),
(2, 19, 2),
(3, 19, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `roleID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_role`
--

INSERT INTO `user_role` (`ID`, `userID`, `roleID`) VALUES
(1, 1, 2),
(2, 19, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
