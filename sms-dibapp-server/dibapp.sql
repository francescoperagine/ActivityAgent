-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ott 11, 2019 alle 20:03
-- Versione del server: 10.4.6-MariaDB
-- Versione PHP: 7.3.9

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

CREATE TABLE `class` (
  `ID` int(11) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `year` int(1) NOT NULL,
  `semester` int(1) DEFAULT 0
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `class_lesson_attendance_rating` (
  `ID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `lessonID` int(11) NOT NULL,
  `rating` int(1) DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `review` text DEFAULT NULL,
  `time` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_lesson_attendance_rating`
--

INSERT INTO `class_lesson_attendance_rating` (`ID`, `studentID`, `lessonID`, `rating`, `summary`, `review`, `time`) VALUES
(1, 1, 5, 5, 'test', 'test', '2019-10-07 13:01:18'),
(63, 1, 6, 5, 'test', '', '2019-10-07 09:53:49'),
(64, 1, 8, 4, '123', '4444', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `class_lesson_question`
--

CREATE TABLE `class_lesson_question` (
  `ID` int(11) NOT NULL,
  `lessonID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `question` text NOT NULL,
  `time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_lesson_question`
--

INSERT INTO `class_lesson_question` (`ID`, `lessonID`, `studentID`, `question`, `time`) VALUES
(15, 5, 1, 'question test', '2019-10-07 13:01:37'),
(16, 6, 1, 'text', '2019-10-07 09:54:15');

-- --------------------------------------------------------

--
-- Struttura della tabella `class_room_calendar`
--

CREATE TABLE `class_room_calendar` (
  `ID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `day` int(1) NOT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `class_room_lesson` (
  `ID` int(11) NOT NULL,
  `calendarID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `timeStart` datetime DEFAULT NULL,
  `timeEnd` datetime DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `class_room_lesson`
--

INSERT INTO `class_room_lesson` (`ID`, `calendarID`, `roomID`, `timeStart`, `timeEnd`, `summary`, `description`) VALUES
(6, 1, 10, '2019-10-10 09:30:00', '2019-10-10 22:33:00', NULL, NULL),
(5, 35, 5, '2019-10-04 08:00:00', '2019-10-04 23:49:00', NULL, NULL),
(7, 1, 1, '2019-10-11 07:00:00', '2019-10-11 21:00:00', 'sommario', 'descrizione'),
(8, 49, 6, '2019-10-10 06:00:00', '2019-10-10 13:00:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `degreecourse`
--

CREATE TABLE `degreecourse` (
  `ID` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` text DEFAULT NULL,
  `ministerialDecree` varchar(128) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `degreecourse_class` (
  `ID` int(11) NOT NULL,
  `degreecourseID` int(11) NOT NULL,
  `classID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `professor_teaching` (
  `ID` int(11) NOT NULL,
  `professorID` int(11) NOT NULL,
  `classID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `professor_teaching`
--

INSERT INTO `professor_teaching` (`ID`, `professorID`, `classID`) VALUES
(1, 19, 20),
(2, 19, 47),
(3, 19, 7);

-- --------------------------------------------------------

--
-- Struttura della tabella `role`
--

CREATE TABLE `role` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `room` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `student_career` (
  `ID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  `passed` tinyint(1) NOT NULL DEFAULT 0,
  `vote` int(2) NOT NULL,
  `praise` tinyint(1) DEFAULT NULL,
  `passedDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(56, 41, 47, 0, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `serialNumber` varchar(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `passwordHash` varchar(256) NOT NULL,
  `salt` varchar(256) NOT NULL,
  `registrationDate` datetime DEFAULT current_timestamp(),
  `roleID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`ID`, `serialNumber`, `name`, `surname`, `email`, `passwordHash`, `salt`, `registrationDate`, `roleID`) VALUES
(1, '443020', 'Francesco', 'Peragine', 'francescoperagine@gmail.com', '$2y$10$wG9wq4KArvDcbwwbVlsogu6Z9IIl8uTlxfIKb4hW5th/EIiZD8W8W', 'f627667e46de19e736b1a8239b0928a4eba100e522bbc3d0a9cf2a21f9c891ff', '2019-08-01 10:39:44', 2),
(19, '123456', 'Paolo', 'Buono', 'buono@uniba.it', '$2y$10$zDnrHIsGSEgiuVzSJk7Z7u/TnIjWxfCg1xgx6v6fg3ZRvrk49JC1i', 'debd8a7b1884f93e0573fa704ffae26231b79fb5c6f97d72e6f5c5986ccd8bce', '2019-08-13 10:39:44', 1),
(41, '123444', 'Francesco', 'Laghetti', 'francesco.laghetti@libero.it', '$2y$10$o2bHbye/pBqrjXC4MxKYteNhaCJo/bG1CXp.K12hQIw0HLJaYRv4.', 'cf1b79f0f1cf791138aff07248274504a5d86cc63ec66fb30e080cd7d4ee1792', '2019-10-03 19:53:12', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_degreecourse`
--

CREATE TABLE `user_degreecourse` (
  `ID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `degreecourseID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_degreecourse`
--

INSERT INTO `user_degreecourse` (`ID`, `userID`, `degreecourseID`) VALUES
(1, 1, 2),
(2, 19, 2),
(3, 19, 1),
(17, 41, 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `class_lesson_attendance_rating`
--
ALTER TABLE `class_lesson_attendance_rating`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `class_lesson_question`
--
ALTER TABLE `class_lesson_question`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `class_room_calendar`
--
ALTER TABLE `class_room_calendar`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `class_room_lesson`
--
ALTER TABLE `class_room_lesson`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `degreecourse`
--
ALTER TABLE `degreecourse`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `degreecourse_class`
--
ALTER TABLE `degreecourse_class`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `professor_teaching`
--
ALTER TABLE `professor_teaching`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `student_career`
--
ALTER TABLE `student_career`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indici per le tabelle `user_degreecourse`
--
ALTER TABLE `user_degreecourse`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `class`
--
ALTER TABLE `class`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT per la tabella `class_lesson_attendance_rating`
--
ALTER TABLE `class_lesson_attendance_rating`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT per la tabella `class_lesson_question`
--
ALTER TABLE `class_lesson_question`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT per la tabella `class_room_calendar`
--
ALTER TABLE `class_room_calendar`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT per la tabella `class_room_lesson`
--
ALTER TABLE `class_room_lesson`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `degreecourse`
--
ALTER TABLE `degreecourse`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `degreecourse_class`
--
ALTER TABLE `degreecourse_class`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT per la tabella `professor_teaching`
--
ALTER TABLE `professor_teaching`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `role`
--
ALTER TABLE `role`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `room`
--
ALTER TABLE `room`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la tabella `student_career`
--
ALTER TABLE `student_career`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT per la tabella `user_degreecourse`
--
ALTER TABLE `user_degreecourse`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
