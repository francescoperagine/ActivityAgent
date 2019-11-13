-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Nov 13, 2019 alle 18:02
-- Versione del server: 10.4.8-MariaDB
-- Versione PHP: 7.3.10

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
  `description` text DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `year` int(1) NOT NULL,
  `semester` int(1) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class`
--

INSERT INTO `class` (`ID`, `name`, `description`, `code`, `year`, `semester`) VALUES
(1, 'Analisi Matematica', NULL, '000178', 1, 2),
(2, 'Architettura degli Elaboratori e Sistemi Operativi', NULL, '062978', 1, 2),
(3, 'Laboratorio di Informatica', NULL, '004724', 1, 1),
(4, 'Lingua Inglese', NULL, '005626', 1, 1),
(5, 'Linguaggi di Programmazione', NULL, '010194', 1, 2),
(6, 'Matematica Discreta', NULL, '006002', 1, 1),
(7, 'Programmazione', NULL, '007398', 1, 1),
(8, 'Ingegneria del software', NULL, '062985', 2, 2),
(9, 'Calcolo Numerico', NULL, '014368', 2, 2),
(10, 'Economia e Gestione d’impresa', NULL, '062991', 2, 1),
(11, 'Fisica Applicata all’Informatica', NULL, '062984', 2, 2),
(12, 'Progettazione di Basi di Dati', NULL, '062983', 2, 1),
(13, 'Reti di calcolatori', NULL, '017451', 2, 1),
(14, 'Statistica per l’Ingegneria del Software', NULL, '062986', 2, 2),
(15, 'Integrazione e Test di Sistemi Software', NULL, '062989', 3, 1),
(17, 'Modelli e Metodi per la Qualità del Software', NULL, '062939', 3, 1),
(18, 'Progettazione dell\'Interazione con l\'Utente + Lab.', NULL, '061433', 3, 1),
(20, 'Sviluppo di Mobile Software', NULL, '062992', 3, 1),
(21, 'Algoritmi e Strutture Dati', NULL, '', 2, 1),
(22, 'Basi di Dati', NULL, '', 2, 1),
(23, 'Fondamenti di Fisica', NULL, '', 2, 1),
(25, 'Metodi avanzati di Programmazione', NULL, '', 2, 2),
(26, 'Calcolo delle Probabilità e Statistica', NULL, '', 2, 2),
(27, 'Calcolabilità e complessità', NULL, '', 3, 2),
(28, 'Interazione Uomo-Macchina', NULL, '', 3, 1),
(29, 'Metodi per il Ritrovamento dell’ Informazione', NULL, '', 3, 1),
(30, 'Ingegneria della Conoscenza', NULL, '', 3, 1),
(42, 'Data Mining', NULL, '', 3, 2),
(41, 'Evoluzione del Software', NULL, '', 3, 2),
(36, 'Sviluppo di Videogiochi', NULL, '', 3, 2),
(37, 'Sistemi e Tecniche di Gestione Documentale', NULL, '', 3, 2),
(38, 'Modelli e metodi per la sicurezza delle applicazioni', NULL, '', 3, 2),
(39, 'Sistemi ad agenti', NULL, '', 3, 2),
(40, 'Sistemi multimediali', NULL, '', 3, 2),
(43, 'Sistemi cooperativi', NULL, '', 3, 2),
(44, 'Sistemi informativi su web', NULL, '', 3, 2),
(45, 'Progettazione e produzione di informatica per la didattica', NULL, '', 3, 2),
(46, 'Progettazione e produzione multimediale', NULL, '', 3, 2),
(47, 'Cyber security', NULL, '', 3, 2),
(55, 'Programmazione II', NULL, NULL, 2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `class_lesson_attendance_rating`
--

DROP TABLE IF EXISTS `class_lesson_attendance_rating`;
CREATE TABLE IF NOT EXISTS `class_lesson_attendance_rating` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) NOT NULL,
  `lessonID` int(11) NOT NULL,
  `rating` int(1) DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `review` text DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=224 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_lesson_attendance_rating`
--

INSERT INTO `class_lesson_attendance_rating` (`ID`, `studentID`, `lessonID`, `rating`, `summary`, `review`, `time`) VALUES
(68, 45, 6, 3, 'Lezione mediocre', 'É stata una lezione mediocre.', '2019-09-30 10:55:38'),
(73, 44, 6, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(74, 41, 6, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(69, 46, 6, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(70, 47, 6, 3, 'Lezione mediocre', 'É stata una lezione a tratti noiosa.', '2019-09-30 10:55:38'),
(71, 48, 6, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(72, 49, 6, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(75, 42, 6, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(76, 43, 6, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(87, 44, 10, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(88, 41, 10, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(78, 44, 9, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(79, 41, 9, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(80, 46, 9, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(86, 45, 10, 3, 'Lezione mediocre', 'É stata una lezione mediocre.', '2019-09-30 10:55:38'),
(82, 48, 9, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(83, 49, 9, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(84, 42, 9, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(85, 43, 9, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(89, 46, 10, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(90, 47, 10, 3, 'Lezione mediocre', 'É stata una lezione a tratti noiosa.', '2019-09-30 10:55:38'),
(91, 48, 10, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(92, 49, 10, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(93, 42, 10, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(94, 43, 10, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(95, 48, 11, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(96, 49, 11, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(97, 42, 11, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(98, 43, 11, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(99, 46, 12, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(100, 47, 12, 3, 'Lezione mediocre', 'É stata una lezione a tratti noiosa.', '2019-09-30 10:55:38'),
(101, 48, 12, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(102, 49, 12, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(103, 42, 12, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(104, 43, 12, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(105, 41, 13, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(106, 45, 13, 3, 'Lezione mediocre', 'É stata una lezione mediocre.', '2019-09-30 10:55:38'),
(107, 46, 13, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(108, 47, 13, 3, 'Lezione mediocre', 'É stata una lezione a tratti noiosa.', '2019-09-30 10:55:38'),
(109, 48, 13, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(110, 49, 13, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(111, 42, 13, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(112, 43, 13, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(113, 49, 14, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(114, 42, 14, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(115, 43, 14, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(117, 46, 15, 5, 'Ottima lezione', 'É stata una lezione ottima.', '2019-09-30 10:55:38'),
(118, 48, 15, 4, 'Bella lezione', 'É stata una lezione gradevole.', '2019-09-30 10:55:38'),
(119, 49, 15, 1, 'Pessima lezione', 'É stata una lezione pessima.', '2019-09-30 10:55:38'),
(120, 42, 15, NULL, NULL, NULL, '2019-09-30 11:00:30'),
(121, 43, 15, NULL, NULL, NULL, '2019-09-30 11:00:30');

-- --------------------------------------------------------

--
-- Struttura della tabella `class_lesson_question`
--

DROP TABLE IF EXISTS `class_lesson_question`;
CREATE TABLE IF NOT EXISTS `class_lesson_question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `lessonID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `question` text NOT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_lesson_question`
--

INSERT INTO `class_lesson_question` (`ID`, `lessonID`, `studentID`, `question`, `time`) VALUES
(17, 6, 44, 'Può ripetere il titolo del libro di testo?', '2019-09-30 10:57:42'),
(18, 6, 44, 'Può spiegare meglio l\'ultimo argomento?', '2019-09-30 11:02:47'),
(19, 6, 44, 'Ci saranno esoneri?', '2019-09-30 11:03:09'),
(20, 6, 45, 'Quanto costa il libro di testo?', '2019-09-30 10:57:42'),
(21, 6, 46, 'Dove possiamo trovare le slide?', '2019-09-30 11:02:47'),
(22, 6, 47, 'Quale sarà la modalità d\'esame?', '2019-09-30 11:03:09'),
(23, 9, 44, 'Può ripetere il titolo del libro di testo?', '2019-09-30 10:57:42'),
(24, 9, 44, 'Può spiegare meglio l\'ultimo argomento?', '2019-09-30 11:02:47'),
(25, 10, 44, 'Ci saranno esoneri?', '2019-09-30 11:03:09'),
(26, 10, 45, 'Quanto costa il libro di testo?', '2019-09-30 10:57:42'),
(27, 10, 46, 'Dove possiamo trovare le slide?', '2019-09-30 11:02:47'),
(28, 10, 47, 'Quale sarà la modalità d\'esame?', '2019-09-30 11:03:09'),
(29, 11, 44, 'Può ripetere il titolo del libro di testo?', '2019-09-30 10:57:42'),
(30, 11, 44, 'Può spiegare meglio l\'ultimo argomento?', '2019-09-30 11:02:47'),
(31, 11, 44, 'Ci saranno esoneri?', '2019-09-30 11:03:09'),
(32, 12, 45, 'Quanto costa il libro di testo?', '2019-09-30 10:57:42'),
(33, 12, 46, 'Dove possiamo trovare le slide?', '2019-09-30 11:02:47'),
(34, 12, 47, 'Quale sarà la modalità d\'esame?', '2019-09-30 11:03:09'),
(35, 13, 44, 'Può ripetere il titolo del libro di testo?', '2019-09-30 10:57:42'),
(36, 13, 44, 'Può spiegare meglio l\'ultimo argomento?', '2019-09-30 11:02:47'),
(37, 14, 44, 'Ci saranno esoneri?', '2019-09-30 11:03:09'),
(38, 14, 45, 'Quanto costa il libro di testo?', '2019-09-30 10:57:42'),
(39, 15, 46, 'Dove possiamo trovare le slide?', '2019-09-30 11:02:47'),
(40, 15, 47, 'Quale sarà la modalità d\'esame?', '2019-09-30 11:03:09'),
(41, 15, 44, 'Può ripetere il titolo del libro di testo?', '2019-09-30 10:57:42'),
(42, 15, 44, 'Può spiegare meglio l\'ultimo argomento?', '2019-09-30 11:02:47'),
(43, 15, 44, 'Ci saranno esoneri?', '2019-09-30 11:03:09'),
(44, 15, 45, 'Quanto costa il libro di testo?', '2019-09-30 10:57:42');

-- --------------------------------------------------------

--
-- Struttura stand-in per le viste `class_lesson_question_rated`
-- (Vedi sotto per la vista effettiva)
--
DROP VIEW IF EXISTS `class_lesson_question_rated`;
CREATE TABLE IF NOT EXISTS `class_lesson_question_rated` (
`ID` int(11)
,`studentID` int(11)
,`question` text
,`time` datetime
,`rate` decimal(32,0)
,`lessonID` int(11)
);

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
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_room_calendar`
--

INSERT INTO `class_room_calendar` (`ID`, `classID`, `roomID`, `day`, `timeStart`, `timeEnd`) VALUES
(1, 20, 5, 1, '09:00:00', '12:00:00'),
(2, 5, 5, 2, '11:30:00', '14:30:00'),
(3, 5, 5, 3, '09:00:00', '11:00:00'),
(4, 1, 5, 1, '11:30:00', '14:30:00'),
(5, 1, 5, 3, '13:30:00', '16:30:00'),
(6, 1, 5, 4, '11:30:00', '14:30:00'),
(7, 3, 5, 3, '08:30:00', '12:30:00'),
(8, 3, 5, 5, '11:00:00', '14:00:00'),
(9, 3, 5, 3, '08:30:00', '12:30:00'),
(10, 3, 5, 5, '11:00:00', '14:00:00'),
(11, 4, 5, 2, '08:30:00', '11:30:00'),
(12, 4, 5, 4, '08:30:00', '11:30:00'),
(13, 8, 7, 1, '08:30:00', '11:30:00'),
(14, 8, 2, 2, '08:30:00', '11:30:00'),
(15, 8, 10, 5, '08:30:00', '12:30:00'),
(16, 11, 10, 2, '11:30:00', '14:30:00'),
(17, 11, 10, 4, '08:30:00', '11:30:00'),
(18, 14, 10, 3, '08:30:00', '11:30:00'),
(19, 14, 10, 4, '11:30:00', '14:30:00'),
(20, 10, 10, 1, '11:30:00', '14:30:00'),
(21, 10, 10, 3, '11:30:00', '14:30:00'),
(22, 42, 5, 1, '08:30:00', '11:30:00'),
(23, 42, 8, 3, '08:30:00', '11:30:00'),
(24, 44, 8, 2, '08:30:00', '11:30:00'),
(25, 44, 8, 3, '15:30:00', '18:30:00'),
(26, 45, 8, 2, '11:30:00', '14:30:00'),
(27, 45, 8, 4, '08:30:00', '11:30:00'),
(28, 6, 5, 1, '08:30:00', '11:30:00'),
(29, 6, 5, 2, '08:30:00', '10:30:00'),
(30, 6, 5, 4, '08:30:00', '11:30:00'),
(31, 2, 5, 1, '11:30:00', '14:30:00'),
(32, 2, 5, 3, '11:30:00', '14:30:00'),
(33, 2, 5, 4, '11:30:00', '13:30:00'),
(34, 7, 5, 2, '10:30:00', '13:30:00'),
(35, 7, 5, 3, '08:30:00', '11:30:00'),
(36, 7, 5, 5, '08:30:00', '12:30:00'),
(37, 55, 10, 2, '11:30:00', '14:30:00'),
(38, 55, 10, 4, '08:30:00', '11:30:00'),
(39, 55, 10, 5, '11:30:00', '13:30:00'),
(40, 12, 10, 1, '11:30:00', '13:30:00'),
(41, 12, 10, 3, '08:30:00', '10:30:00'),
(42, 12, 10, 5, '08:30:00', '11:30:00'),
(43, 13, 10, 2, '08:30:00', '11:30:00'),
(44, 13, 10, 3, '10:30:00', '13:30:00'),
(45, 9, 10, 1, '08:30:00', '11:30:00'),
(46, 9, 10, 4, '11:30:00', '14:30:00'),
(47, 18, 1, 1, '13:30:00', '15:30:00'),
(48, 18, 3, 5, '12:30:00', '14:30:00'),
(49, 20, 3, 2, '15:30:00', '18:30:00'),
(50, 20, 3, 3, '14:30:00', '18:30:00'),
(51, 17, 1, 1, '15:30:00', '18:30:00'),
(52, 17, 3, 3, '13:30:00', '15:30:00'),
(53, 15, 3, 4, '16:30:00', '18:30:00'),
(54, 15, 3, 5, '14:30:00', '18:30:00'),
(55, 17, 3, 4, '13:30:00', '16:30:00');

-- --------------------------------------------------------

--
-- Struttura della tabella `class_room_lesson`
--

DROP TABLE IF EXISTS `class_room_lesson`;
CREATE TABLE IF NOT EXISTS `class_room_lesson` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `calendarID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `timeStart` datetime DEFAULT NULL,
  `timeEnd` datetime DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_room_lesson`
--

INSERT INTO `class_room_lesson` (`ID`, `calendarID`, `roomID`, `timeStart`, `timeEnd`, `summary`, `description`) VALUES
(6, 1, 10, '2019-09-30 09:00:00', '2019-09-30 12:00:00', NULL, NULL),
(11, 1, 10, '2019-10-21 09:00:00', '2019-10-21 12:00:00', NULL, NULL),
(9, 1, 10, '2019-10-07 09:00:00', '2019-10-07 12:00:00', NULL, NULL),
(10, 1, 10, '2019-10-14 09:00:00', '2019-10-14 12:00:00', NULL, NULL),
(12, 1, 10, '2019-10-28 09:00:00', '2019-10-28 12:00:00', NULL, NULL),
(13, 1, 10, '2019-11-04 09:00:00', '2019-11-04 12:00:00', NULL, NULL),
(14, 1, 10, '2019-11-11 09:00:00', '2019-11-11 12:00:00', NULL, NULL),
(15, 1, 10, '2019-11-18 09:00:00', '2019-11-18 12:00:00', NULL, NULL),
(16, 35, 10, '2019-11-18 09:00:00', '2019-11-18 12:00:00', NULL, NULL),
(17, 35, 10, '2019-11-14 09:00:00', '2019-11-14 12:00:00', NULL, NULL),
(18, 35, 10, '2019-11-04 09:00:00', '2019-11-04 12:00:00', NULL, NULL),
(19, 2, 5, '2019-11-14 11:30:00', '2019-11-14 14:30:00', NULL, NULL),
(20, 6, 5, '2019-11-14 11:30:00', '2019-11-14 14:30:00', NULL, NULL),
(21, 17, 5, '2019-11-14 08:30:00', '2019-11-14 11:30:00', NULL, NULL),
(22, 19, 10, '2019-11-14 11:30:00', '2019-11-14 14:30:00', NULL, NULL),
(23, 27, 8, '2019-11-14 08:30:00', '2019-11-14 11:30:00', NULL, NULL),
(24, 30, 5, '2019-11-14 08:30:00', '2019-11-14 11:30:00', NULL, NULL),
(25, 33, 5, '2019-11-14 08:30:00', '2019-11-14 11:30:00', NULL, NULL),
(26, 38, 10, '2019-11-14 08:30:00', '2019-11-14 11:30:00', NULL, NULL),
(27, 46, 10, '2019-11-14 11:30:00', '2019-11-14 14:30:00', NULL, NULL),
(28, 53, 3, '2019-11-14 16:30:00', '2019-11-14 18:30:00', NULL, NULL),
(29, 56, 3, '2019-11-14 13:30:00', '2019-11-14 16:30:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `degreecourse`
--

DROP TABLE IF EXISTS `degreecourse`;
CREATE TABLE IF NOT EXISTS `degreecourse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `description` text DEFAULT NULL,
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
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4;

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
(20, 2, 20),
(21, 1, 2),
(23, 1, 7),
(25, 1, 5),
(26, 1, 3),
(56, 2, 42),
(32, 1, 6),
(34, 1, 1),
(37, 1, 4),
(38, 1, 9),
(39, 1, 13),
(40, 1, 22),
(41, 1, 27),
(42, 1, 26),
(43, 1, 23),
(44, 1, 30),
(54, 2, 55),
(55, 2, 41),
(47, 1, 25),
(48, 1, 29),
(49, 1, 38),
(50, 1, 39),
(51, 1, 37),
(52, 1, 40),
(53, 1, 36),
(57, 2, 43),
(58, 2, 44),
(59, 2, 45),
(60, 2, 46),
(61, 2, 47);

-- --------------------------------------------------------

--
-- Struttura della tabella `professor_teaching`
--

DROP TABLE IF EXISTS `professor_teaching`;
CREATE TABLE IF NOT EXISTS `professor_teaching` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `professorID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `professor_teaching`
--

INSERT INTO `professor_teaching` (`ID`, `professorID`, `classID`) VALUES
(1, 19, 20),
(2, 19, 47),
(3, 19, 7);

-- --------------------------------------------------------

--
-- Struttura della tabella `question_rate`
--

DROP TABLE IF EXISTS `question_rate`;
CREATE TABLE IF NOT EXISTS `question_rate` (
  `questionID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `questionRate` int(11) NOT NULL,
  PRIMARY KEY (`questionID`,`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `question_rate`
--

INSERT INTO `question_rate` (`questionID`, `studentID`, `questionRate`) VALUES
(17, 44, 1),
(17, 45, 1),
(17, 46, 1),
(18, 44, 1),
(18, 45, 1),
(18, 46, 1),
(19, 44, 1),
(19, 45, 1),
(20, 44, -1),
(20, 45, -1),
(21, 44, -1),
(21, 45, -1),
(21, 46, -1),
(21, 47, -1),
(24, 44, 1),
(24, 45, 1),
(24, 46, 1),
(26, 44, 1),
(26, 45, 1),
(26, 46, 1),
(29, 44, -1),
(29, 45, -1),
(30, 44, 1),
(30, 45, 1),
(32, 44, 1),
(32, 45, 1),
(34, 44, -1),
(34, 45, -1),
(36, 44, 1),
(36, 45, 1),
(36, 46, 1),
(38, 44, 1),
(38, 45, 1),
(39, 44, 1),
(39, 45, 1),
(39, 46, 1),
(40, 44, -1),
(40, 45, -1),
(40, 46, 1),
(41, 44, -1),
(41, 45, -1);

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
(1, 'professor'),
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
-- Struttura della tabella `student_career`
--

DROP TABLE IF EXISTS `student_career`;
CREATE TABLE IF NOT EXISTS `student_career` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  `passed` tinyint(1) NOT NULL DEFAULT 0,
  `vote` int(2) NOT NULL,
  `praise` tinyint(1) DEFAULT NULL,
  `passedDate` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `student_career`
--

INSERT INTO `student_career` (`ID`, `studentID`, `classID`, `passed`, `vote`, `praise`, `passedDate`) VALUES
(1, 1, 8, 0, 0, NULL, NULL),
(2, 1, 1, 0, 0, NULL, NULL),
(3, 1, 2, 0, 0, NULL, NULL),
(4, 1, 9, 0, 0, NULL, NULL),
(5, 1, 10, 0, 0, NULL, NULL),
(6, 1, 11, 0, 0, NULL, NULL),
(7, 1, 15, 0, 0, NULL, NULL),
(8, 1, 3, 0, 0, NULL, NULL),
(9, 1, 5, 0, 0, NULL, NULL),
(10, 1, 4, 0, 0, NULL, NULL),
(12, 1, 6, 0, 0, NULL, NULL),
(13, 1, 17, 0, 0, NULL, NULL),
(14, 1, 18, 0, 0, NULL, NULL),
(15, 1, 12, 0, 0, NULL, NULL),
(16, 1, 7, 0, 0, NULL, NULL),
(18, 1, 13, 0, 0, NULL, NULL),
(19, 1, 14, 0, 0, NULL, NULL),
(20, 1, 20, 0, 0, NULL, NULL),
(21, 1, 42, 0, 0, NULL, NULL),
(22, 1, 55, 0, 0, NULL, NULL),
(23, 1, 41, 0, 0, NULL, NULL),
(24, 1, 43, 0, 0, NULL, NULL),
(25, 1, 44, 0, 0, NULL, NULL),
(26, 1, 45, 0, 0, NULL, NULL),
(27, 1, 46, 0, 0, NULL, NULL),
(28, 1, 47, 0, 0, NULL, NULL),
(29, 41, 8, 0, 0, NULL, NULL),
(30, 41, 1, 0, 0, NULL, NULL),
(31, 41, 2, 0, 0, NULL, NULL),
(32, 41, 9, 0, 0, NULL, NULL),
(33, 41, 10, 0, 0, NULL, NULL),
(34, 41, 11, 0, 0, NULL, NULL),
(35, 41, 15, 0, 0, NULL, NULL),
(36, 41, 3, 0, 0, NULL, NULL),
(37, 41, 5, 0, 0, NULL, NULL),
(38, 41, 4, 0, 0, NULL, NULL),
(39, 41, 16, 0, 0, NULL, NULL),
(40, 41, 6, 0, 0, NULL, NULL),
(41, 41, 17, 0, 0, NULL, NULL),
(42, 41, 18, 0, 0, NULL, NULL),
(43, 41, 12, 0, 0, NULL, NULL),
(44, 41, 7, 0, 0, NULL, NULL),
(45, 41, 19, 0, 0, NULL, NULL),
(46, 41, 13, 0, 0, NULL, NULL),
(47, 41, 14, 0, 0, NULL, NULL),
(48, 41, 20, 0, 0, NULL, NULL),
(49, 41, 42, 0, 0, NULL, NULL),
(50, 41, 55, 0, 0, NULL, NULL),
(51, 41, 41, 0, 0, NULL, NULL),
(52, 41, 43, 0, 0, NULL, NULL),
(53, 41, 44, 0, 0, NULL, NULL),
(54, 41, 45, 0, 0, NULL, NULL),
(55, 41, 46, 0, 0, NULL, NULL),
(56, 41, 47, 0, 0, NULL, NULL),
(57, 42, 8, 0, 0, NULL, NULL),
(58, 42, 1, 0, 0, NULL, NULL),
(59, 42, 2, 0, 0, NULL, NULL),
(60, 42, 9, 0, 0, NULL, NULL),
(61, 42, 10, 0, 0, NULL, NULL),
(62, 42, 11, 0, 0, NULL, NULL),
(63, 42, 15, 0, 0, NULL, NULL),
(64, 42, 3, 0, 0, NULL, NULL),
(65, 42, 5, 0, 0, NULL, NULL),
(66, 42, 4, 0, 0, NULL, NULL),
(67, 42, 16, 0, 0, NULL, NULL),
(68, 42, 6, 0, 0, NULL, NULL),
(69, 42, 17, 0, 0, NULL, NULL),
(70, 42, 18, 0, 0, NULL, NULL),
(71, 42, 12, 0, 0, NULL, NULL),
(72, 42, 7, 0, 0, NULL, NULL),
(73, 42, 19, 0, 0, NULL, NULL),
(74, 42, 13, 0, 0, NULL, NULL),
(75, 42, 14, 0, 0, NULL, NULL),
(76, 42, 20, 0, 0, NULL, NULL),
(77, 42, 42, 0, 0, NULL, NULL),
(78, 42, 55, 0, 0, NULL, NULL),
(79, 42, 41, 0, 0, NULL, NULL),
(80, 42, 43, 0, 0, NULL, NULL),
(81, 42, 44, 0, 0, NULL, NULL),
(82, 42, 45, 0, 0, NULL, NULL),
(83, 42, 46, 0, 0, NULL, NULL),
(84, 42, 47, 0, 0, NULL, NULL),
(88, 43, 8, 0, 0, NULL, NULL),
(89, 43, 1, 0, 0, NULL, NULL),
(90, 43, 2, 0, 0, NULL, NULL),
(91, 43, 9, 0, 0, NULL, NULL),
(92, 43, 10, 0, 0, NULL, NULL),
(93, 43, 11, 0, 0, NULL, NULL),
(94, 43, 15, 0, 0, NULL, NULL),
(95, 43, 3, 0, 0, NULL, NULL),
(96, 43, 5, 0, 0, NULL, NULL),
(97, 43, 4, 0, 0, NULL, NULL),
(98, 43, 16, 0, 0, NULL, NULL),
(99, 43, 6, 0, 0, NULL, NULL),
(100, 43, 17, 0, 0, NULL, NULL),
(101, 43, 18, 0, 0, NULL, NULL),
(102, 43, 12, 0, 0, NULL, NULL),
(103, 43, 7, 0, 0, NULL, NULL),
(104, 43, 19, 0, 0, NULL, NULL),
(105, 43, 13, 0, 0, NULL, NULL),
(106, 43, 14, 0, 0, NULL, NULL),
(107, 43, 20, 0, 0, NULL, NULL),
(108, 43, 42, 0, 0, NULL, NULL),
(109, 43, 55, 0, 0, NULL, NULL),
(110, 43, 41, 0, 0, NULL, NULL),
(111, 43, 43, 0, 0, NULL, NULL),
(112, 43, 44, 0, 0, NULL, NULL),
(113, 43, 45, 0, 0, NULL, NULL),
(114, 43, 46, 0, 0, NULL, NULL),
(115, 43, 47, 0, 0, NULL, NULL),
(119, 44, 8, 0, 0, NULL, NULL),
(120, 44, 1, 0, 0, NULL, NULL),
(121, 44, 2, 0, 0, NULL, NULL),
(122, 44, 9, 0, 0, NULL, NULL),
(123, 44, 10, 0, 0, NULL, NULL),
(124, 44, 11, 0, 0, NULL, NULL),
(125, 44, 15, 0, 0, NULL, NULL),
(126, 44, 3, 0, 0, NULL, NULL),
(127, 44, 5, 0, 0, NULL, NULL),
(128, 44, 4, 0, 0, NULL, NULL),
(129, 44, 16, 0, 0, NULL, NULL),
(130, 44, 6, 0, 0, NULL, NULL),
(131, 44, 17, 0, 0, NULL, NULL),
(132, 44, 18, 0, 0, NULL, NULL),
(133, 44, 12, 0, 0, NULL, NULL),
(134, 44, 7, 0, 0, NULL, NULL),
(135, 44, 19, 0, 0, NULL, NULL),
(136, 44, 13, 0, 0, NULL, NULL),
(137, 44, 14, 0, 0, NULL, NULL),
(138, 44, 20, 0, 0, NULL, NULL),
(139, 44, 42, 0, 0, NULL, NULL),
(140, 44, 55, 0, 0, NULL, NULL),
(141, 44, 41, 0, 0, NULL, NULL),
(142, 44, 43, 0, 0, NULL, NULL),
(143, 44, 44, 0, 0, NULL, NULL),
(144, 44, 45, 0, 0, NULL, NULL),
(145, 44, 46, 0, 0, NULL, NULL),
(146, 44, 47, 0, 0, NULL, NULL),
(150, 45, 8, 0, 0, NULL, NULL),
(151, 45, 1, 0, 0, NULL, NULL),
(152, 45, 2, 0, 0, NULL, NULL),
(153, 45, 9, 0, 0, NULL, NULL),
(154, 45, 10, 0, 0, NULL, NULL),
(155, 45, 11, 0, 0, NULL, NULL),
(156, 45, 15, 0, 0, NULL, NULL),
(157, 45, 3, 0, 0, NULL, NULL),
(158, 45, 5, 0, 0, NULL, NULL),
(159, 45, 4, 0, 0, NULL, NULL),
(160, 45, 16, 0, 0, NULL, NULL),
(161, 45, 6, 0, 0, NULL, NULL),
(162, 45, 17, 0, 0, NULL, NULL),
(163, 45, 18, 0, 0, NULL, NULL),
(164, 45, 12, 0, 0, NULL, NULL),
(165, 45, 7, 0, 0, NULL, NULL),
(166, 45, 19, 0, 0, NULL, NULL),
(167, 45, 13, 0, 0, NULL, NULL),
(168, 45, 14, 0, 0, NULL, NULL),
(169, 45, 20, 0, 0, NULL, NULL),
(170, 45, 42, 0, 0, NULL, NULL),
(171, 45, 55, 0, 0, NULL, NULL),
(172, 45, 41, 0, 0, NULL, NULL),
(173, 45, 43, 0, 0, NULL, NULL),
(174, 45, 44, 0, 0, NULL, NULL),
(175, 45, 45, 0, 0, NULL, NULL),
(176, 45, 46, 0, 0, NULL, NULL),
(177, 45, 47, 0, 0, NULL, NULL),
(181, 46, 8, 0, 0, NULL, NULL),
(182, 46, 1, 0, 0, NULL, NULL),
(183, 46, 2, 0, 0, NULL, NULL),
(184, 46, 9, 0, 0, NULL, NULL),
(185, 46, 10, 0, 0, NULL, NULL),
(186, 46, 11, 0, 0, NULL, NULL),
(187, 46, 15, 0, 0, NULL, NULL),
(188, 46, 3, 0, 0, NULL, NULL),
(189, 46, 5, 0, 0, NULL, NULL),
(190, 46, 4, 0, 0, NULL, NULL),
(191, 46, 16, 0, 0, NULL, NULL),
(192, 46, 6, 0, 0, NULL, NULL),
(193, 46, 17, 0, 0, NULL, NULL),
(194, 46, 18, 0, 0, NULL, NULL),
(195, 46, 12, 0, 0, NULL, NULL),
(196, 46, 7, 0, 0, NULL, NULL),
(197, 46, 19, 0, 0, NULL, NULL),
(198, 46, 13, 0, 0, NULL, NULL),
(199, 46, 14, 0, 0, NULL, NULL),
(200, 46, 20, 0, 0, NULL, NULL),
(201, 46, 42, 0, 0, NULL, NULL),
(202, 46, 55, 0, 0, NULL, NULL),
(203, 46, 41, 0, 0, NULL, NULL),
(204, 46, 43, 0, 0, NULL, NULL),
(205, 46, 44, 0, 0, NULL, NULL),
(206, 46, 45, 0, 0, NULL, NULL),
(207, 46, 46, 0, 0, NULL, NULL),
(208, 46, 47, 0, 0, NULL, NULL),
(212, 47, 8, 0, 0, NULL, NULL),
(213, 47, 1, 0, 0, NULL, NULL),
(214, 47, 2, 0, 0, NULL, NULL),
(215, 47, 9, 0, 0, NULL, NULL),
(216, 47, 10, 0, 0, NULL, NULL),
(217, 47, 11, 0, 0, NULL, NULL),
(218, 47, 15, 0, 0, NULL, NULL),
(219, 47, 3, 0, 0, NULL, NULL),
(220, 47, 5, 0, 0, NULL, NULL),
(221, 47, 4, 0, 0, NULL, NULL),
(222, 47, 16, 0, 0, NULL, NULL),
(223, 47, 6, 0, 0, NULL, NULL),
(224, 47, 17, 0, 0, NULL, NULL),
(225, 47, 18, 0, 0, NULL, NULL),
(226, 47, 12, 0, 0, NULL, NULL),
(227, 47, 7, 0, 0, NULL, NULL),
(228, 47, 19, 0, 0, NULL, NULL),
(229, 47, 13, 0, 0, NULL, NULL),
(230, 47, 14, 0, 0, NULL, NULL),
(231, 47, 20, 0, 0, NULL, NULL),
(232, 47, 42, 0, 0, NULL, NULL),
(233, 47, 55, 0, 0, NULL, NULL),
(234, 47, 41, 0, 0, NULL, NULL),
(235, 47, 43, 0, 0, NULL, NULL),
(236, 47, 44, 0, 0, NULL, NULL),
(237, 47, 45, 0, 0, NULL, NULL),
(238, 47, 46, 0, 0, NULL, NULL),
(239, 47, 47, 0, 0, NULL, NULL),
(243, 48, 8, 0, 0, NULL, NULL),
(244, 48, 1, 0, 0, NULL, NULL),
(245, 48, 2, 0, 0, NULL, NULL),
(246, 48, 9, 0, 0, NULL, NULL),
(247, 48, 10, 0, 0, NULL, NULL),
(248, 48, 11, 0, 0, NULL, NULL),
(249, 48, 15, 0, 0, NULL, NULL),
(250, 48, 3, 0, 0, NULL, NULL),
(251, 48, 5, 0, 0, NULL, NULL),
(252, 48, 4, 0, 0, NULL, NULL),
(253, 48, 16, 0, 0, NULL, NULL),
(254, 48, 6, 0, 0, NULL, NULL),
(255, 48, 17, 0, 0, NULL, NULL),
(256, 48, 18, 0, 0, NULL, NULL),
(257, 48, 12, 0, 0, NULL, NULL),
(258, 48, 7, 0, 0, NULL, NULL),
(259, 48, 19, 0, 0, NULL, NULL),
(260, 48, 13, 0, 0, NULL, NULL),
(261, 48, 14, 0, 0, NULL, NULL),
(262, 48, 20, 0, 0, NULL, NULL),
(263, 48, 42, 0, 0, NULL, NULL),
(264, 48, 55, 0, 0, NULL, NULL),
(265, 48, 41, 0, 0, NULL, NULL),
(266, 48, 43, 0, 0, NULL, NULL),
(267, 48, 44, 0, 0, NULL, NULL),
(268, 48, 45, 0, 0, NULL, NULL),
(269, 48, 46, 0, 0, NULL, NULL),
(270, 48, 47, 0, 0, NULL, NULL),
(274, 49, 8, 0, 0, NULL, NULL),
(275, 49, 1, 0, 0, NULL, NULL),
(276, 49, 2, 0, 0, NULL, NULL),
(277, 49, 9, 0, 0, NULL, NULL),
(278, 49, 10, 0, 0, NULL, NULL),
(279, 49, 11, 0, 0, NULL, NULL),
(280, 49, 15, 0, 0, NULL, NULL),
(281, 49, 3, 0, 0, NULL, NULL),
(282, 49, 5, 0, 0, NULL, NULL),
(283, 49, 4, 0, 0, NULL, NULL),
(284, 49, 16, 0, 0, NULL, NULL),
(285, 49, 6, 0, 0, NULL, NULL),
(286, 49, 17, 0, 0, NULL, NULL),
(287, 49, 18, 0, 0, NULL, NULL),
(288, 49, 12, 0, 0, NULL, NULL),
(289, 49, 7, 0, 0, NULL, NULL),
(290, 49, 19, 0, 0, NULL, NULL),
(291, 49, 13, 0, 0, NULL, NULL),
(292, 49, 14, 0, 0, NULL, NULL),
(293, 49, 20, 0, 0, NULL, NULL),
(294, 49, 42, 0, 0, NULL, NULL),
(295, 49, 55, 0, 0, NULL, NULL),
(296, 49, 41, 0, 0, NULL, NULL),
(297, 49, 43, 0, 0, NULL, NULL),
(298, 49, 44, 0, 0, NULL, NULL),
(299, 49, 45, 0, 0, NULL, NULL),
(300, 49, 46, 0, 0, NULL, NULL),
(301, 49, 47, 0, 0, NULL, NULL);

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
  `registrationDate` datetime DEFAULT current_timestamp(),
  `roleID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`ID`, `serialNumber`, `name`, `surname`, `email`, `passwordHash`, `salt`, `registrationDate`, `roleID`) VALUES
(1, '443020', 'Francesco', 'Peragine', 'francescoperagine@gmail.com', '$2y$10$wG9wq4KArvDcbwwbVlsogu6Z9IIl8uTlxfIKb4hW5th/EIiZD8W8W', 'f627667e46de19e736b1a8239b0928a4eba100e522bbc3d0a9cf2a21f9c891ff', '2019-08-01 10:39:44', 2),
(19, '123456', 'Paolo', 'Buono', 'buono@uniba.it', '$2y$10$zDnrHIsGSEgiuVzSJk7Z7u/TnIjWxfCg1xgx6v6fg3ZRvrk49JC1i', 'debd8a7b1884f93e0573fa704ffae26231b79fb5c6f97d72e6f5c5986ccd8bce', '2019-08-13 10:39:44', 1),
(41, '123444', 'Francesco', 'Laghetti', 'francesco.laghetti@libero.it', '$2y$10$o2bHbye/pBqrjXC4MxKYteNhaCJo/bG1CXp.K12hQIw0HLJaYRv4.', 'cf1b79f0f1cf791138aff07248274504a5d86cc63ec66fb30e080cd7d4ee1792', '2019-10-03 19:53:12', 2),
(42, '999888', 'Emilio', 'Solfrizzi', 'solfrizzi@gmail.com', '$2y$10$hec8Rg6UGpfQbN1JYF1ol.0m7/VEM20.DSt5CRP1VAGSttIGrHBgu', 'ad377c0711b3cbb23f6f9187992678b436f46d3d64f9ac7da12654fd276b36c8', '2019-11-05 22:25:15', 2),
(43, '888777', 'Diego Armando', 'Maradona', 'maradona@gmail.com', '$2y$10$ZyZL8U1iWQFqs/aBiU3O9eYimbOnJp3fNposnzR2KPuuK0M.4QnlW', '058a375f8e46847f1fb1825ab99ba9a007ddc048b07ef39b892ea8a8a0dd7911', '2019-11-05 22:26:07', 2),
(44, '777666', 'Marco', 'Bellinelli', 'bellinelli@gmail.com', '$2y$10$AzqOx3Wl7wZjyW8XyGYfSOmZx16kaeYD/Y2AoeTVChC.FsfwjO9sK', '8ddae61e25463ecfbbcd68ddf5f943ce542bf8a8b3e072e5769d04aebff1204d', '2019-11-05 22:27:09', 2),
(45, '666555', 'Margherita', 'Hack', 'hack@gmail.com', '$2y$10$9YMMdDH5Y0YUn9VkItDmy.rdZBMVOKyQYH/v2S3ZjXIqt/d6H7VRW', '39efda91470634410cf105ddfe1e1cdae663c56a7ee3d5f19a5d0f4812390abc', '2019-11-05 22:29:04', 2),
(46, '555444', 'Pinco', 'Pallino', 'pallino@gmail.com', '$2y$10$9TAFRKESonGJpx/vFNJxou5XkboTZuFEm/DVW39mtv93hRS0mnBWW', 'e4506f27761add5c69224e6914e3bbb67c276ec1a1f75397e1805a42a6dc3060', '2019-11-05 22:30:16', 2),
(47, '444333', 'Maria', 'Maddalena', 'maddalena@gmail.com', '$2y$10$vphdPxMLVvNfrru58DvtJ.WPh0y3/qEhbbzhnbfxyulX/bXy5sUdy', 'db6c041511c82db21af891eab8239ea9f216f50279a1534a17891454fb5db8a8', '2019-11-05 22:31:12', 2),
(48, '333222', 'Marco', 'Togni', 'togni@gmail.com', '$2y$10$zGfTyINbvUeUmNUb8tiA7ONV/Zynyto0qdIVRu27UtcntO.GTGyle', 'aa1a36402cb40d8f824bd91d0de6c56df6bf984397ec30c326a130647e9bfc72', '2019-11-05 22:40:30', 2),
(49, '222111', 'Donato', 'Cavallo', 'cavallo@gmail.com', '$2y$10$IG8jSwyRw9zRQXMz7lYbku2bdUFMKPbhk8Pnu7c5JewagTHvYAcgW', '4749c5b4a010c70e117455b5df83b851760cea46eb52da9e3dc34d166853ff56', '2019-11-05 22:41:14', 2);

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
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_degreecourse`
--

INSERT INTO `user_degreecourse` (`ID`, `userID`, `degreecourseID`) VALUES
(1, 1, 2),
(2, 19, 2),
(3, 19, 1),
(17, 41, 2),
(18, 42, 2),
(19, 43, 2),
(20, 44, 2),
(21, 45, 2),
(22, 46, 2),
(23, 47, 2),
(24, 48, 2),
(25, 49, 2);

-- --------------------------------------------------------

--
-- Struttura per vista `class_lesson_question_rated`
--
DROP TABLE IF EXISTS `class_lesson_question_rated`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `class_lesson_question_rated`  AS  select `clq`.`ID` AS `ID`,`clq`.`studentID` AS `studentID`,`clq`.`question` AS `question`,`clq`.`time` AS `time`,sum(`qr`.`questionRate`) AS `rate`,`clq`.`lessonID` AS `lessonID` from (`class_lesson_question` `clq` left join `question_rate` `qr` on(`clq`.`ID` = `qr`.`questionID`)) group by `clq`.`ID` ;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `question_rate`
--
ALTER TABLE `question_rate`
  ADD CONSTRAINT `question_rate_ibfk_1` FOREIGN KEY (`questionID`) REFERENCES `class_lesson_question` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
