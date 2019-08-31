-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 30, 2019 alle 12:08
-- Versione del server: 10.1.28-MariaDB
-- Versione PHP: 7.1.10

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
  `description` text,
  `code` varchar(10) NOT NULL,
  `year` int(1) NOT NULL,
  `semester` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

CREATE TABLE `class_room_calendar` (
  `ID` int(11) NOT NULL,
  `classID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `day` int(1) NOT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `class_room_lesson`
--

CREATE TABLE `class_room_lesson` (
  `ID` int(11) NOT NULL,
  `calendarID` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `degreecourse`
--

CREATE TABLE `degreecourse` (
  `ID` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` text,
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
(20, 2, 20);

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
(1, 'teacher'),
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
  `passed` tinyint(1) NOT NULL DEFAULT '0',
  `vote` int(2) NOT NULL,
  `laud` tinyint(1) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `registrationDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `roleID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`ID`, `serialNumber`, `name`, `surname`, `email`, `passwordHash`, `salt`, `registrationDate`, `roleID`) VALUES
(1, '443020', 'Francesco', 'Peragine', 'francescoperagine@gmail.com', '$2y$10$wG9wq4KArvDcbwwbVlsogu6Z9IIl8uTlxfIKb4hW5th/EIiZD8W8W', 'f627667e46de19e736b1a8239b0928a4eba100e522bbc3d0a9cf2a21f9c891ff', NULL, 2),
(19, '123456', 'paolo', 'buono', 'buono@uniba.it', '$2y$10$zDnrHIsGSEgiuVzSJk7Z7u/TnIjWxfCg1xgx6v6fg3ZRvrk49JC1i', 'debd8a7b1884f93e0573fa704ffae26231b79fb5c6f97d72e6f5c5986ccd8bce', '2019-08-13 10:39:44', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_class_rating`
--

CREATE TABLE `user_class_rating` (
  `ID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `lessonID` int(11) NOT NULL,
  `rating` int(1) DEFAULT NULL,
  `summary` varchar(128) DEFAULT NULL,
  `review` text
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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
(3, 19, 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `class`
--
ALTER TABLE `class`
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
-- Indici per le tabelle `user_class_rating`
--
ALTER TABLE `user_class_rating`
  ADD PRIMARY KEY (`ID`);

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT per la tabella `class_room_calendar`
--
ALTER TABLE `class_room_calendar`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `class_room_lesson`
--
ALTER TABLE `class_room_lesson`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `degreecourse`
--
ALTER TABLE `degreecourse`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `degreecourse_class`
--
ALTER TABLE `degreecourse_class`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT per la tabella `user_class_rating`
--
ALTER TABLE `user_class_rating`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `user_degreecourse`
--
ALTER TABLE `user_degreecourse`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
