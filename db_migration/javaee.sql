-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.22-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table javaee.admins
CREATE TABLE IF NOT EXISTS `ADMINS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table javaee.admins: ~1 rows (approximately)
/*!40000 ALTER TABLE `ADMINS` DISABLE KEYS */;
INSERT INTO `ADMINS` (`id`, `username`, `password`) VALUES
	(1, 'admin', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203');
/*!40000 ALTER TABLE `ADMINS` ENABLE KEYS */;


-- Dumping structure for table javaee.employees
CREATE TABLE IF NOT EXISTS `EMPLOYEES` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT '0',
  `password` varchar(255) DEFAULT '0',
  `email` varchar(255) DEFAULT '0',
  `full_name` varchar(255) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table javaee.employees: ~1 rows (approximately)
/*!40000 ALTER TABLE `EMPLOYEES` DISABLE KEYS */;
INSERT INTO `EMPLOYEES` (`id`, `username`, `password`, `email`, `full_name`) VALUES
	(1, 'empl', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203', '', '');
/*!40000 ALTER TABLE `EMPLOYEES` ENABLE KEYS */;


-- Dumping structure for table javaee.lecturers
CREATE TABLE IF NOT EXISTS `LECTURERS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT '0',
  `password` varchar(255) DEFAULT '0',
  `email` varchar(255) DEFAULT '0',
  `biography` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table javaee.lecturers: ~3 rows (approximately)
/*!40000 ALTER TABLE `LECTURERS` DISABLE KEYS */;
INSERT INTO `LECTURERS` (`id`, `username`, `password`, `email`, `biography`, `full_name`) VALUES
	(1, 'nii4eto', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203', 'nia@abv.bg', NULL, NULL),
	(2, 'test', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '', NULL, NULL),
	(3, 'ivan', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203', 'ivan@abv.bg', 'Lecturer', 'Ivan Ivanov');
/*!40000 ALTER TABLE `LECTURERS` ENABLE KEYS */;


-- Dumping structure for table javaee.users
CREATE TABLE IF NOT EXISTS `USERS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `lecturer_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_users_admins` (`admin_id`),
  KEY `FK_users_lecturers` (`lecturer_id`),
  KEY `FK_users_employees` (`employee_id`),
  CONSTRAINT `FK_users_admins` FOREIGN KEY (`admin_id`) REFERENCES `ADMINS` (`id`),
  CONSTRAINT `FK_users_employees` FOREIGN KEY (`employee_id`) REFERENCES `EMPLOYEES` (`id`),
  CONSTRAINT `FK_users_lecturers` FOREIGN KEY (`lecturer_id`) REFERENCES `LECTURERS` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table javaee.users: ~5 rows (approximately)
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` (`id`, `admin_id`, `lecturer_id`, `employee_id`, `type`, `username`, `password`) VALUES
	(1, 1, NULL, NULL, 'ADMIN', 'admin', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203'),
	(2, NULL, 3, NULL, 'LECTURER', 'ivan', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203'),
	(3, NULL, 1, NULL, 'LECTURER', 'nii4eto', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203'),
	(4, NULL, 2, NULL, 'LECTURER', 'test', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203'),
	(5, NULL, NULL, 1, 'EMPLOYEE', 'empl', '91b4d142823f7d20c5f08df69122de43f35f057a988d9619f6d3138485c9a203');
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
