-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2024 at 12:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biblio`
--

CREATE DATABASE biblio;
USE biblio;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `bookId` int(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bookId`, `title`, `author`, `publisher`) VALUES
(1, 'livre1', 'mimi', 'frej'),
(2, 'livre2', 'mimi', 'frej'),
(3, 'livre3', 'eya', 'naoel');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `borrowId` int(20) NOT NULL,
  `borrowDate` date NOT NULL,
  `returnDate` date DEFAULT NULL,
  `studentId` int(20) NOT NULL,
  `bookId` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`borrowId`, `borrowDate`, `returnDate`, `studentId`, `bookId`) VALUES
(1, '2024-05-12', '2024-05-12', 1, 1),
(4, '2024-05-12', '2024-05-12', 1, 2),
(6, '2024-05-12', '2024-05-12', 1, 3),
(7, '2024-05-12', '2024-05-12', 1, 2),
(8, '2024-05-12', '2024-05-12', 1, 1),
(9, '2024-05-12', '2024-05-12', 1, 1),
(10, '2024-05-12', '2024-05-12', 1, 2),
(11, '2024-05-12', '2024-05-12', 1, 3),
(12, '2024-05-12', '2024-05-12', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` int(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `passwordHash` varchar(500) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `passwordHash`, `name`, `contact`, `role`) VALUES
(-1, 'admin', '$2a$12$RxLjYo.k/6io4TKsYk1UJeAH73p3P5fAp5OGYNWpVkyx2di0GpR72', NULL, NULL, 'admin'),
(1, 'adel', '$2a$12$7vf0xJK4Ca1MMrokPI.AfuZStcki7yhu4mAVjSh.4LwcpF0W7v86.', 'adel', '20802418', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`bookId`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`borrowId`),
  ADD KEY `fk_student` (`studentId`),
  ADD KEY `fk_book` (`bookId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username_idx` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `borrow`
--
ALTER TABLE `borrow`
  MODIFY `borrowId` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `fk_book` FOREIGN KEY (`bookId`) REFERENCES `books` (`bookId`),
  ADD CONSTRAINT `fk_student` FOREIGN KEY (`studentId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
