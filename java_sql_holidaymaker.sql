-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for holidaymaker
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `holidaymaker`;

-- Dumping structure for table holidaymaker.additional_choices
CREATE TABLE IF NOT EXISTS `additional_choices` (
  `choice_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_id` int(11) unsigned NOT NULL,
  `booked_dates_id` int(11) unsigned NOT NULL,
  `meal_choice` enum('half board','full board','none') DEFAULT NULL,
  `additional_bed` enum('yes','no') DEFAULT NULL,
  PRIMARY KEY (`choice_ID`),
  KEY `FK_additional_choices_booked_dates` (`booked_dates_id`),
  KEY `FK_additional_choices_rooms` (`room_id`),
  CONSTRAINT `FK_additional_choices_booked_dates` FOREIGN KEY (`booked_dates_id`) REFERENCES `booked_dates` (`booked_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_additional_choices_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.additional_choices: ~33 rows (approximately)
/*!40000 ALTER TABLE `additional_choices` DISABLE KEYS */;
INSERT IGNORE INTO `additional_choices` (`choice_ID`, `room_id`, `booked_dates_id`, `meal_choice`, `additional_bed`) VALUES
	(1, 47, 1, 'half board', 'yes'),
	(2, 22, 2, 'none', 'no'),
	(3, 46, 3, 'full board', 'yes'),
	(4, 37, 5, 'half board', 'no'),
	(5, 45, 6, 'none', 'no'),
	(6, 44, 7, 'full board', 'yes'),
	(7, 41, 8, 'half board', 'no'),
	(8, 43, 9, 'none', 'yes'),
	(9, 34, 10, 'full board', 'yes'),
	(10, 30, 11, 'half board', 'yes'),
	(11, 11, 12, 'none', 'yes'),
	(12, 7, 13, 'none', 'no'),
	(13, 3, 14, 'half board', 'yes'),
	(14, 12, 15, 'full board', 'no'),
	(15, 37, 16, 'full board', 'no'),
	(16, 40, 17, 'none', 'yes'),
	(17, 47, 18, 'none', 'no'),
	(18, 32, 19, 'none', 'yes'),
	(19, 18, 20, 'half board', 'yes'),
	(20, 36, 21, 'none', 'yes'),
	(21, 10, 22, 'half board', 'yes'),
	(22, 17, 23, 'none', 'no'),
	(23, 16, 24, 'none', 'yes'),
	(24, 15, 25, 'half board', 'yes'),
	(25, 17, 26, 'none', 'no'),
	(26, 27, 27, 'half board', 'no'),
	(27, 36, 28, 'full board', 'yes'),
	(28, 20, 29, 'none', 'yes'),
	(29, 28, 30, 'half board', 'no'),
	(30, 21, 31, 'none', 'no'),
	(31, 20, 32, 'none', 'yes'),
	(32, 22, 33, 'none', 'yes'),
	(33, 44, 35, 'full board', 'yes');
/*!40000 ALTER TABLE `additional_choices` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.additional_prices
CREATE TABLE IF NOT EXISTS `additional_prices` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `destination_id` int(11) unsigned NOT NULL,
  `half_board` int(11) DEFAULT NULL,
  `full_board` int(11) DEFAULT NULL,
  `additional_bed` int(11) DEFAULT NULL,
  `none` int(11) DEFAULT '0',
  `no` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_prices_destinations` (`destination_id`),
  CONSTRAINT `FK_prices_destinations` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.additional_prices: ~8 rows (approximately)
/*!40000 ALTER TABLE `additional_prices` DISABLE KEYS */;
INSERT IGNORE INTO `additional_prices` (`id`, `destination_id`, `half_board`, `full_board`, `additional_bed`, `none`, `no`) VALUES
	(1, 1, 50, 100, 20, 0, 0),
	(2, 2, 500, 800, 400, 0, 0),
	(3, 3, 800, 1000, 500, 0, 0),
	(4, 4, 400, 600, 300, 0, 0),
	(5, 5, 1000, 1500, 800, 0, 0),
	(6, 6, 1000, 2000, 800, 0, 0),
	(7, 7, 100, 150, 50, 0, 0),
	(8, 8, 450, 900, 200, 0, 0);
/*!40000 ALTER TABLE `additional_prices` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.all_booked_guests
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `all_booked_guests` (
	`id` INT(11) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`email` VARCHAR(255) NULL COLLATE 'latin1_swedish_ci',
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`total_guests` INT(11) NOT NULL,
	`maximum_guests` INT(11) NOT NULL,
	`room_id` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.booked_dates
CREATE TABLE IF NOT EXISTS `booked_dates` (
  `booked_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_id` int(11) unsigned NOT NULL,
  `checkin_date` date DEFAULT NULL,
  `checkout_date` date DEFAULT NULL,
  PRIMARY KEY (`booked_ID`),
  KEY `FK_booked_dates_rooms` (`room_id`),
  CONSTRAINT `FK_booked_dates_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.booked_dates: ~33 rows (approximately)
/*!40000 ALTER TABLE `booked_dates` DISABLE KEYS */;
INSERT IGNORE INTO `booked_dates` (`booked_ID`, `room_id`, `checkin_date`, `checkout_date`) VALUES
	(1, 47, '2020-06-04', '2020-06-07'),
	(2, 22, '2020-06-02', '2020-06-05'),
	(3, 46, '2020-06-02', '2020-06-08'),
	(5, 37, '2020-06-07', '2020-06-09'),
	(6, 45, '2020-07-15', '2020-07-20'),
	(7, 44, '2020-07-01', '2020-07-06'),
	(8, 41, '2020-06-20', '2020-06-28'),
	(9, 43, '2020-07-10', '2020-07-20'),
	(10, 34, '2020-06-10', '2020-06-12'),
	(11, 30, '2020-06-08', '2020-06-12'),
	(12, 11, '2020-07-29', '2020-07-30'),
	(13, 7, '2020-07-01', '2020-07-15'),
	(14, 3, '2020-07-05', '2020-07-09'),
	(15, 12, '2020-06-20', '2020-06-23'),
	(16, 37, '2020-07-19', '2020-07-29'),
	(17, 40, '2020-06-23', '2020-06-24'),
	(18, 47, '2020-06-26', '2020-07-15'),
	(19, 32, '2020-06-17', '2020-06-30'),
	(20, 18, '2020-07-02', '2020-07-06'),
	(21, 36, '2020-07-26', '2020-07-30'),
	(22, 10, '2020-06-23', '2020-06-28'),
	(23, 17, '2020-06-13', '2020-06-19'),
	(24, 16, '2020-07-13', '2020-07-15'),
	(25, 15, '2020-06-23', '2020-06-25'),
	(26, 17, '2020-07-06', '2020-07-08'),
	(27, 27, '2020-06-23', '2020-06-28'),
	(28, 36, '2020-06-11', '2020-06-15'),
	(29, 20, '2020-07-16', '2020-07-18'),
	(30, 28, '2020-07-09', '2020-07-29'),
	(31, 21, '2020-07-01', '2020-07-30'),
	(32, 20, '2020-06-03', '2020-06-09'),
	(33, 22, '2020-06-22', '2020-06-24'),
	(35, 44, '2020-06-01', '2020-06-02');
/*!40000 ALTER TABLE `booked_dates` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.bookings
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `bookings` (
	`id` INT(11) UNSIGNED NOT NULL,
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`rating` ENUM('1','2','3','4','5') NULL COLLATE 'latin1_swedish_ci',
	`distance_centre` INT(11) NULL,
	`distance_beach` INT(11) NULL,
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`price_per_night` INT(11) UNSIGNED NOT NULL,
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`maximum_guests` INT(11) NOT NULL,
	`restaurant` BIT(1) NULL,
	`kids_club` BIT(1) NULL,
	`pool` BIT(1) NULL,
	`entertainment` BIT(1) NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.check_booking_dates
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `check_booking_dates` (
	`id` INT(11) UNSIGNED NOT NULL,
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`rating` ENUM('1','2','3','4','5') NULL COLLATE 'latin1_swedish_ci',
	`distance_centre` INT(11) NULL,
	`distance_beach` INT(11) NULL,
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`price_per_night` INT(11) UNSIGNED NOT NULL,
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`maximum_guests` INT(11) NOT NULL,
	`restaurant` BIT(1) NULL,
	`kids_club` BIT(1) NULL,
	`pool` BIT(1) NULL,
	`entertainment` BIT(1) NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.delete_booking
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `delete_booking` (
	`id` INT(11) UNSIGNED NOT NULL,
	`choice_ID` INT(11) UNSIGNED NOT NULL,
	`booked_ID` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.destinations
CREATE TABLE IF NOT EXISTS `destinations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `hotel_name` varchar(255) NOT NULL,
  `restaurant` bit(1) DEFAULT NULL,
  `kids_club` bit(1) DEFAULT NULL,
  `pool` bit(1) DEFAULT NULL,
  `entertainment` bit(1) DEFAULT NULL,
  `rating` enum('1','2','3','4','5') DEFAULT NULL,
  `distance_centre` int(11) DEFAULT NULL,
  `distance_beach` int(11) DEFAULT NULL,
  `number_of_rooms` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.destinations: ~8 rows (approximately)
/*!40000 ALTER TABLE `destinations` DISABLE KEYS */;
INSERT IGNORE INTO `destinations` (`id`, `city`, `hotel_name`, `restaurant`, `kids_club`, `pool`, `entertainment`, `rating`, `distance_centre`, `distance_beach`, `number_of_rooms`) VALUES
	(1, 'Barentsburg', 'Arktikugol', b'1', b'0', b'1', b'0', '3', 300, 50, 7),
	(2, 'Stavanger', 'Akkurat', b'0', b'1', b'1', b'0', '2', 25, 200, 5),
	(3, 'Trondheim', 'Ha De', b'1', b'1', b'0', b'0', '2', 1000, 780, 6),
	(4, 'Mo I Rana', 'Gutten', b'0', b'0', b'1', b'1', '1', 30, 500, 4),
	(5, 'Longyearbyen', 'Polar Bear', b'1', b'1', b'0', b'1', '4', 0, 400, 10),
	(6, 'Bergen', 'Hytten', b'1', b'1', b'1', b'1', '5', 0, 0, 5),
	(7, 'Grimsta', 'Moro Hotel', b'0', b'0', b'0', b'1', '1', 25, 800, 4),
	(8, 'Vega', 'The Island Hotel', b'0', b'1', b'0', b'1', '4', 0, 0, 6);
/*!40000 ALTER TABLE `destinations` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.filter_rooms
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `filter_rooms` (
	`id` INT(11) UNSIGNED NOT NULL,
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`rating` ENUM('1','2','3','4','5') NULL COLLATE 'latin1_swedish_ci',
	`distance_beach` INT(11) NULL,
	`distance_centre` INT(11) NULL,
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`price_per_night` INT(11) UNSIGNED NOT NULL,
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`restaurant` BIT(1) NULL,
	`kids_club` BIT(1) NULL,
	`pool` BIT(1) NULL,
	`entertainment` BIT(1) NULL,
	`maximum_guests` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.guest_bookings
CREATE TABLE IF NOT EXISTS `guest_bookings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guests_id` int(11) unsigned NOT NULL,
  `room_id` int(11) unsigned NOT NULL,
  `additional_choices_id` int(11) unsigned NOT NULL,
  `booked_dates_id` int(11) unsigned NOT NULL,
  `total_guests` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_guest_bookings_rooms` (`room_id`),
  KEY `FK_guests_bookings_guests_information` (`guests_id`),
  KEY `FK_guest_bookings_additional_choices` (`additional_choices_id`),
  KEY `FK_guest_bookings_booked_dates` (`booked_dates_id`),
  CONSTRAINT `FK_guest_bookings_additional_choices` FOREIGN KEY (`additional_choices_id`) REFERENCES `additional_choices` (`choice_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_guest_bookings_booked_dates` FOREIGN KEY (`booked_dates_id`) REFERENCES `booked_dates` (`booked_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_guest_bookings_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_guests_bookings_guests_information` FOREIGN KEY (`guests_id`) REFERENCES `guest_information` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.guest_bookings: ~32 rows (approximately)
/*!40000 ALTER TABLE `guest_bookings` DISABLE KEYS */;
INSERT IGNORE INTO `guest_bookings` (`id`, `guests_id`, `room_id`, `additional_choices_id`, `booked_dates_id`, `total_guests`) VALUES
	(1, 3, 47, 1, 1, 3),
	(2, 12, 22, 2, 2, 6),
	(3, 13, 46, 3, 3, 8),
	(4, 14, 37, 4, 5, 5),
	(5, 15, 45, 5, 6, 2),
	(6, 16, 44, 6, 7, 8),
	(7, 17, 41, 7, 8, 1),
	(8, 18, 43, 8, 9, 6),
	(9, 19, 34, 9, 10, 9),
	(10, 20, 30, 10, 11, 4),
	(11, 21, 11, 11, 12, 5),
	(12, 22, 7, 12, 13, 4),
	(13, 23, 3, 13, 14, 3),
	(14, 6, 12, 14, 15, 1),
	(16, 25, 40, 16, 17, 1),
	(17, 26, 47, 17, 18, 2),
	(18, 27, 32, 18, 19, 5),
	(19, 28, 18, 19, 20, 4),
	(20, 29, 36, 20, 21, 9),
	(21, 30, 10, 21, 22, 4),
	(22, 31, 17, 22, 23, 1),
	(23, 32, 16, 23, 24, 1),
	(24, 33, 15, 24, 25, 1),
	(25, 34, 17, 25, 26, 3),
	(26, 10, 27, 26, 27, 7),
	(27, 35, 36, 27, 28, 1),
	(28, 36, 20, 28, 29, 2),
	(29, 37, 28, 29, 30, 1),
	(30, 38, 21, 30, 31, 2),
	(31, 39, 20, 31, 32, 2),
	(32, 40, 22, 32, 33, 2),
	(33, 41, 44, 33, 35, 1);
/*!40000 ALTER TABLE `guest_bookings` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.guest_information
CREATE TABLE IF NOT EXISTS `guest_information` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.guest_information: ~41 rows (approximately)
/*!40000 ALTER TABLE `guest_information` DISABLE KEYS */;
INSERT IGNORE INTO `guest_information` (`id`, `first_name`, `last_name`, `email`, `phonenumber`) VALUES
	(1, 'Marion', 'Hood', 'marion@sherwood.com', '030-676767'),
	(2, 'Marion', 'Hood', 'quack@disney.com', '08-121212'),
	(3, 'Mamma', 'Mia', 'dance@greece.com', '003012121212'),
	(4, 'Moula', 'Mantra', 'chakra@yoga.se', '070123453'),
	(5, 'Chandler', 'Bing', 'friends@forever.com', '040-111111'),
	(6, 'Drone', 'Fly', 'wings@sky.com', '08-111111'),
	(7, 'Java', 'Coffee', 'java@forlife.com', '070-121212'),
	(8, 'Elvis', 'Presley', 'king@graceland.com', '030-121212'),
	(9, 'Raven', 'Nevermore', 'edgar@allanpoe.com', '+19078244'),
	(10, 'Mister', 'Bean', 'bean@hollywood.com', '08-6726876837'),
	(11, 'Marilyn', 'Monroe', 'grace@hollywood.com', '040-5683828'),
	(12, 'Janis', 'Joplin', 'sing@pieceofmyheart.com', '+1907825643'),
	(13, 'James', 'Brown', 'james@getup.com', '08-344343434'),
	(14, 'James', 'Dean', 'dean@borntobewilde.com', '042-43434343'),
	(15, 'Maitre', 'Gims', 'gims@miamivice.com', '003046474747'),
	(16, 'Roger ', 'Waters', 'roger@pinkfloyd.com', '050-543232442'),
	(17, 'Roy', 'Orbison', 'roy@droveallnight.com', '010-424232342'),
	(18, 'Sweet', 'Jane', 'jane@cowboyjunkies.com', '040-38763894689'),
	(19, 'Yoko', 'Ono', 'yoko@lennon.com', '090-36363636'),
	(20, 'John', 'Lennon', 'john@yoko.com', '+19833737373'),
	(21, 'David', 'Gilmour', 'david@pinkfloyd.com', '042-124353'),
	(22, 'Gene', 'Simmons', 'yay@simmons.se', '040-121344'),
	(23, 'Gary', 'Oldman', 'old@foreveryoung.com', '090-8763278632'),
	(24, 'Edgar', 'Poe', 'raven@poe.com', '08-34434334'),
	(25, 'Mister', 'Robot', 'build@robotics.se', '090-34343443'),
	(26, 'Bot', 'Dot', 'bee@boop.com', '090-23313131'),
	(27, 'Pop', 'Corn', 'pop@up.com', '+198237373'),
	(28, 'Ram', 'Dass', 'meditate@hawaii.com', '070-1244422'),
	(29, 'Wayne', 'Gretzky', 'wayne@hockey.com', '+19037823783'),
	(30, 'Leo', 'Messi', 'messi@barcelona.es', '+34892387932'),
	(31, 'Pope', 'Rome', 'pope@pray.com', '+3581838383'),
	(32, 'Sean', 'Connery', 'sean@bond.com', '+190373473'),
	(33, 'Ma', 'Donna', 'dance@madonna.com', '08-328762378'),
	(34, 'Karl', 'Pedal', 'kalle@palle.se', '040-7763478'),
	(35, 'Bart', 'Simpson', 'bart@skates.com', '08-3273278'),
	(36, 'Frank', 'Sinatra', 'sing@dance.com', '030-767678'),
	(37, 'Cookie', 'Monster', 'eat@cookies.now', '090-878879'),
	(38, 'Stephen', 'King', 'king@nightmare.com', '+19048734873487'),
	(39, 'Krishna', 'Das', 'kirtan@das.com', '08-7788787'),
	(40, 'Suzie', 'Q', 'suzz@fuzz.com', '08-677887'),
	(41, 'Woho', 'Yoho', 'yay@woho.se', '08-677778');
/*!40000 ALTER TABLE `guest_information` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guest_booking_id` int(11) unsigned NOT NULL,
  `total_price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_payments_guests_bookings` (`guest_booking_id`),
  CONSTRAINT `FK_payments_guests_bookings` FOREIGN KEY (`guest_booking_id`) REFERENCES `guest_bookings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.payments: ~0 rows (approximately)
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.pay_total
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `pay_total` (
	`room_id` INT(11) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`total_amount` BIGINT(22) UNSIGNED NULL,
	`guests_id` INT(11) UNSIGNED NOT NULL,
	`additional_choices_id` INT(11) UNSIGNED NOT NULL,
	`booked_dates_id` INT(11) UNSIGNED NOT NULL,
	`booked_ID` INT(11) UNSIGNED NOT NULL,
	`booked_room_id` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.prices_extra_choices
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `prices_extra_choices` (
	`room_id` INT(11) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`meal_choice` ENUM('half board','full board','none') NULL COLLATE 'latin1_swedish_ci',
	`additional_bed` ENUM('yes','no') NULL COLLATE 'latin1_swedish_ci',
	`half_board` INT(11) NULL,
	`full_board` INT(11) NULL,
	`bed_price` INT(11) NULL,
	`price_per_night` INT(11) UNSIGNED NOT NULL,
	`none` INT(11) NULL,
	`no` INT(11) NULL,
	`total_room_price` BIGINT(17) UNSIGNED NULL,
	`price` BIGINT(21) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `destination_id` int(11) unsigned NOT NULL,
  `room_type` enum('single','double','suite') NOT NULL,
  `price_per_night` int(11) unsigned NOT NULL,
  `maximum_guests` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rooms_destinations` (`destination_id`),
  CONSTRAINT `FK_rooms_destinations` FOREIGN KEY (`destination_id`) REFERENCES `destinations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.rooms: ~47 rows (approximately)
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT IGNORE INTO `rooms` (`id`, `destination_id`, `room_type`, `price_per_night`, `maximum_guests`) VALUES
	(1, 1, 'single', 700, 2),
	(2, 1, 'double', 900, 3),
	(3, 1, 'suite', 2000, 6),
	(4, 1, 'single', 700, 2),
	(5, 1, 'single', 700, 2),
	(6, 1, 'double', 900, 3),
	(7, 1, 'suite', 3000, 8),
	(8, 2, 'single', 500, 1),
	(9, 2, 'single', 550, 2),
	(10, 2, 'double', 660, 4),
	(11, 2, 'double', 690, 4),
	(12, 2, 'double', 650, 2),
	(13, 3, 'single', 990, 2),
	(14, 3, 'single', 990, 2),
	(15, 3, 'single', 990, 2),
	(16, 3, 'double', 1050, 3),
	(17, 3, 'double', 1050, 3),
	(18, 3, 'suite', 1990, 4),
	(19, 4, 'single', 2000, 2),
	(20, 4, 'double', 2900, 4),
	(21, 4, 'double', 3000, 4),
	(22, 4, 'suite', 5000, 5),
	(23, 5, 'single', 2500, 1),
	(24, 5, 'single', 2900, 2),
	(25, 5, 'single', 2900, 2),
	(26, 5, 'double', 3000, 4),
	(27, 5, 'double', 3000, 4),
	(28, 5, 'double', 3300, 4),
	(29, 5, 'double', 3700, 4),
	(30, 5, 'suite', 5000, 4),
	(31, 5, 'suite', 6000, 6),
	(32, 5, 'suite', 7000, 8),
	(33, 6, 'double', 5000, 4),
	(34, 6, 'double', 5500, 5),
	(35, 6, 'suite', 8000, 8),
	(36, 6, 'suite', 8900, 9),
	(37, 6, 'suite', 9000, 8),
	(38, 7, 'single', 500, 1),
	(39, 7, 'single', 550, 1),
	(40, 7, 'double', 600, 3),
	(41, 7, 'double', 650, 3),
	(42, 8, 'single', 900, 2),
	(43, 8, 'single', 990, 2),
	(44, 8, 'double', 1050, 4),
	(45, 8, 'double', 1050, 4),
	(46, 8, 'suite', 3000, 5),
	(47, 8, 'suite', 4500, 5);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.total_amount
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `total_amount` (
	`room_id` INT(11) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`total_room_price` BIGINT(17) UNSIGNED NULL,
	`price` BIGINT(21) UNSIGNED NULL,
	`total_amount` BIGINT(22) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.total_price
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `total_price` (
	`room_id` INT(11) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`hotel_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`room_type` ENUM('single','double','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`checkin_date` DATE NULL,
	`checkout_date` DATE NULL,
	`meal_choice` ENUM('half board','full board','none') NULL COLLATE 'latin1_swedish_ci',
	`additional_bed` ENUM('yes','no') NULL COLLATE 'latin1_swedish_ci',
	`half_board` INT(11) NULL,
	`full_board` INT(11) NULL,
	`bed_price` INT(11) NULL,
	`price_per_night` INT(11) UNSIGNED NOT NULL,
	`none` INT(11) NULL,
	`no` INT(11) NULL,
	`total_room_price` BIGINT(17) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.all_booked_guests
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `all_booked_guests`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `all_booked_guests` AS select `guest_bookings`.`id` AS `id`,`guest_information`.`first_name` AS `first_name`,`guest_information`.`last_name` AS `last_name`,`guest_information`.`email` AS `email`,`booked_dates`.`checkin_date` AS `checkin_date`,`booked_dates`.`checkout_date` AS `checkout_date`,`rooms`.`room_type` AS `room_type`,`destinations`.`city` AS `city`,`destinations`.`hotel_name` AS `hotel_name`,`guest_bookings`.`total_guests` AS `total_guests`,`rooms`.`maximum_guests` AS `maximum_guests`,`rooms`.`id` AS `room_id` from ((((`guest_bookings` join `guest_information` on((`guest_information`.`id` = `guest_bookings`.`guests_id`))) join `rooms` on((`rooms`.`id` = `guest_bookings`.`room_id`))) join `destinations` on((`destinations`.`id` = `rooms`.`destination_id`))) join `booked_dates` on((`rooms`.`id` = `booked_dates`.`room_id`)));

-- Dumping structure for view holidaymaker.bookings
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `bookings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `bookings` AS select `rooms`.`id` AS `id`,`destinations`.`city` AS `city`,`destinations`.`hotel_name` AS `hotel_name`,`destinations`.`rating` AS `rating`,`destinations`.`distance_centre` AS `distance_centre`,`destinations`.`distance_beach` AS `distance_beach`,`rooms`.`room_type` AS `room_type`,`rooms`.`price_per_night` AS `price_per_night`,`booked_dates`.`checkin_date` AS `checkin_date`,`booked_dates`.`checkout_date` AS `checkout_date`,`rooms`.`maximum_guests` AS `maximum_guests`,`destinations`.`restaurant` AS `restaurant`,`destinations`.`kids_club` AS `kids_club`,`destinations`.`pool` AS `pool`,`destinations`.`entertainment` AS `entertainment` from ((`destinations` join `rooms` on((`destinations`.`id` = `rooms`.`destination_id`))) left join `booked_dates` on((`rooms`.`id` = `booked_dates`.`room_id`)));

-- Dumping structure for view holidaymaker.check_booking_dates
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `check_booking_dates`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `check_booking_dates` AS select `bookings`.`id` AS `id`,`bookings`.`city` AS `city`,`bookings`.`hotel_name` AS `hotel_name`,`bookings`.`rating` AS `rating`,`bookings`.`distance_centre` AS `distance_centre`,`bookings`.`distance_beach` AS `distance_beach`,`bookings`.`room_type` AS `room_type`,`bookings`.`price_per_night` AS `price_per_night`,`bookings`.`checkin_date` AS `checkin_date`,`bookings`.`checkout_date` AS `checkout_date`,`bookings`.`maximum_guests` AS `maximum_guests`,`bookings`.`restaurant` AS `restaurant`,`bookings`.`kids_club` AS `kids_club`,`bookings`.`pool` AS `pool`,`bookings`.`entertainment` AS `entertainment` from `bookings` where (('2020-06-02' > '2020-06-01') and ('2020-06-01' between '2020-06-01' and '2020-07-30') and ('2020-06-02' between '2020-06-02' and '2020-07-31'));

-- Dumping structure for view holidaymaker.delete_booking
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `delete_booking`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `delete_booking` AS select `guest_bookings`.`id` AS `id`,`additional_choices`.`choice_ID` AS `choice_ID`,`booked_dates`.`booked_ID` AS `booked_ID` from (((`guest_bookings` join `additional_choices` on((`additional_choices`.`choice_ID` = `guest_bookings`.`additional_choices_id`))) join `booked_dates` on((`booked_dates`.`booked_ID` = `guest_bookings`.`booked_dates_id`))) join `guest_information` on((`guest_information`.`id` = `guest_bookings`.`guests_id`)));

-- Dumping structure for view holidaymaker.filter_rooms
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `filter_rooms`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `filter_rooms` AS select `bookings`.`id` AS `id`,`bookings`.`city` AS `city`,`bookings`.`hotel_name` AS `hotel_name`,`bookings`.`rating` AS `rating`,`bookings`.`distance_beach` AS `distance_beach`,`bookings`.`distance_centre` AS `distance_centre`,`bookings`.`room_type` AS `room_type`,`bookings`.`price_per_night` AS `price_per_night`,`bookings`.`checkin_date` AS `checkin_date`,`bookings`.`checkout_date` AS `checkout_date`,`bookings`.`restaurant` AS `restaurant`,`bookings`.`kids_club` AS `kids_club`,`bookings`.`pool` AS `pool`,`bookings`.`entertainment` AS `entertainment`,`bookings`.`maximum_guests` AS `maximum_guests` from `bookings` where ((('2020-06-01' not between `bookings`.`checkin_date` and `bookings`.`checkout_date`) and ('2020-06-02' not between `bookings`.`checkin_date` and `bookings`.`checkout_date`) and ('2020-06-01' between '2020-06-01' and '2020-07-30') and ('2020-06-02' between '2020-06-02' and '2020-07-31') and ('2020-06-02' > '2020-06-01')) or isnull(`bookings`.`checkin_date`) or isnull(`bookings`.`checkout_date`));

-- Dumping structure for view holidaymaker.pay_total
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `pay_total`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `pay_total` AS select `total_amount`.`room_id` AS `room_id`,`total_amount`.`first_name` AS `first_name`,`total_amount`.`last_name` AS `last_name`,`total_amount`.`city` AS `city`,`total_amount`.`hotel_name` AS `hotel_name`,`total_amount`.`room_type` AS `room_type`,`total_amount`.`checkin_date` AS `checkin_date`,`total_amount`.`checkout_date` AS `checkout_date`,`total_amount`.`total_amount` AS `total_amount`,`booked_dates`.`booked_ID` AS `booked_ID` from (`total_amount` join `booked_dates` on((`booked_dates`.`room_id` = `total_amount`.`room_id`)));

-- Dumping structure for view holidaymaker.prices_extra_choices
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `prices_extra_choices`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `prices_extra_choices` AS select `total_price`.`room_id` AS `room_id`,`total_price`.`first_name` AS `first_name`,`total_price`.`last_name` AS `last_name`,`total_price`.`city` AS `city`,`total_price`.`hotel_name` AS `hotel_name`,`total_price`.`room_type` AS `room_type`,`total_price`.`checkin_date` AS `checkin_date`,`total_price`.`checkout_date` AS `checkout_date`,`total_price`.`meal_choice` AS `meal_choice`,`total_price`.`additional_bed` AS `additional_bed`,`total_price`.`half_board` AS `half_board`,`total_price`.`full_board` AS `full_board`,`total_price`.`bed_price` AS `bed_price`,`total_price`.`price_per_night` AS `price_per_night`,`total_price`.`none` AS `none`,`total_price`.`no` AS `no`,`total_price`.`total_room_price` AS `total_room_price`,(case `total_price`.`meal_choice` when 'half board' then (`total_price`.`half_board` + `total_price`.`total_room_price`) when 'full board' then (`total_price`.`full_board` + `total_price`.`total_room_price`) else `total_price`.`total_room_price` end) AS `price` from `total_price`;

-- Dumping structure for view holidaymaker.total_amount
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `total_amount`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `total_amount` AS select `prices_extra_choices`.`room_id` AS `room_id`,`prices_extra_choices`.`first_name` AS `first_name`,`prices_extra_choices`.`last_name` AS `last_name`,`prices_extra_choices`.`city` AS `city`,`prices_extra_choices`.`hotel_name` AS `hotel_name`,`prices_extra_choices`.`room_type` AS `room_type`,`prices_extra_choices`.`checkin_date` AS `checkin_date`,`prices_extra_choices`.`checkout_date` AS `checkout_date`,`prices_extra_choices`.`total_room_price` AS `total_room_price`,`prices_extra_choices`.`price` AS `price`,(case `prices_extra_choices`.`additional_bed` when 'yes' then (`prices_extra_choices`.`price` + `prices_extra_choices`.`bed_price`) else `prices_extra_choices`.`price` end) AS `total_amount` from `prices_extra_choices`;

-- Dumping structure for view holidaymaker.total_price
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `total_price`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `total_price` AS select `rooms`.`id` AS `room_id`,`guest_information`.`first_name` AS `first_name`,`guest_information`.`last_name` AS `last_name`,`destinations`.`city` AS `city`,`destinations`.`hotel_name` AS `hotel_name`,`rooms`.`room_type` AS `room_type`,`booked_dates`.`checkin_date` AS `checkin_date`,`booked_dates`.`checkout_date` AS `checkout_date`,`additional_choices`.`meal_choice` AS `meal_choice`,`additional_choices`.`additional_bed` AS `additional_bed`,`additional_prices`.`half_board` AS `half_board`,`additional_prices`.`full_board` AS `full_board`,`additional_prices`.`additional_bed` AS `bed_price`,`rooms`.`price_per_night` AS `price_per_night`,`additional_prices`.`none` AS `none`,`additional_prices`.`no` AS `no`,((to_days(`booked_dates`.`checkout_date`) - to_days(`booked_dates`.`checkin_date`)) * `rooms`.`price_per_night`) AS `total_room_price` from ((((((`destinations` join `additional_prices` on((`destinations`.`id` = `additional_prices`.`destination_id`))) join `rooms` on((`destinations`.`id` = `rooms`.`destination_id`))) join `guest_bookings` on((`rooms`.`id` = `guest_bookings`.`room_id`))) join `additional_choices` on((`additional_choices`.`room_id` = `rooms`.`id`))) join `guest_information` on((`guest_information`.`id` = `guest_bookings`.`guests_id`))) join `booked_dates` on((`booked_dates`.`room_id` = `rooms`.`id`)));

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
