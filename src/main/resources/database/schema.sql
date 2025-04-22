-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2025 at 05:12 PM
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
-- Database: `hms_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
                            `booking_id` int(11) NOT NULL,
                            `user_id` int(11) NOT NULL,
                            `room_id` int(11) NOT NULL,
                            `check_in_date` date NOT NULL,
                            `check_out_date` date NOT NULL,
                            `total_price` decimal(10,2) NOT NULL,
                            `booking_date` timestamp NOT NULL DEFAULT current_timestamp(),
                            `status` enum('PENDING','CONFIRMED','CANCELLED','COMPLETED') NOT NULL DEFAULT 'PENDING',
                            `special_requests` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`booking_id`, `user_id`, `room_id`, `check_in_date`, `check_out_date`, `total_price`, `booking_date`, `status`, `special_requests`) VALUES
                                                                                                                                                                (1, 5, 91, '2025-04-21', '2025-04-26', 500.00, '2025-04-20 21:10:28', 'COMPLETED', ''),
                                                                                                                                                                (2, 9, 6, '2025-04-21', '2025-04-24', 749.97, '2025-04-21 05:23:32', 'CANCELLED', '');

-- --------------------------------------------------------

--
-- Table structure for table `hotels`
--

CREATE TABLE `hotels` (
                          `hotel_id` int(11) NOT NULL,
                          `name` varchar(100) NOT NULL,
                          `address` varchar(255) NOT NULL,
                          `city` varchar(50) NOT NULL,
                          `state` varchar(50) DEFAULT NULL,
                          `country` varchar(50) NOT NULL,
                          `postal_code` varchar(20) DEFAULT NULL,
                          `phone` varchar(20) DEFAULT NULL,
                          `email` varchar(100) DEFAULT NULL,
                          `star_rating` int(11) DEFAULT NULL CHECK (`star_rating` between 1 and 5),
                          `description` text DEFAULT NULL,
                          `image_url` varchar(255) DEFAULT NULL,
                          `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                          `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotels`
--

INSERT INTO `hotels` (`hotel_id`, `name`, `address`, `city`, `state`, `country`, `postal_code`, `phone`, `email`, `star_rating`, `description`, `image_url`, `created_at`, `updated_at`) VALUES
                                                                                                                                                                                             (2, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:45:23', '2025-04-20 19:45:23'),
                                                                                                                                                                                             (3, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:45:23', '2025-04-20 19:45:23'),
                                                                                                                                                                                             (4, 'Grand Hotel', '123 Main Street', 'New York', 'NY', 'USA', '10001', '212-555-1234', 'info@grandhotel.com', 5, 'Experience luxury at its finest in the heart of Manhattan. The Grand Hotel offers spacious rooms, exceptional dining, and world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:52:49', '2025-04-20 19:52:49'),
                                                                                                                                                                                             (5, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:52:49', '2025-04-20 19:52:49'),
                                                                                                                                                                                             (6, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:52:49', '2025-04-20 19:52:49'),
                                                                                                                                                                                             (7, 'Grand Hotel', '123 Main Street', 'New York', 'NY', 'USA', '10001', '212-555-1234', 'info@grandhotel.com', 5, 'Experience luxury at its finest in the heart of Manhattan. The Grand Hotel offers spacious rooms, exceptional dining, and world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:54:42', '2025-04-20 19:54:42'),
                                                                                                                                                                                             (8, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:54:42', '2025-04-20 19:54:42'),
                                                                                                                                                                                             (9, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:54:42', '2025-04-20 19:54:42'),
                                                                                                                                                                                             (10, 'Grand Hotel', '123 Main Street', 'New York', 'NY', 'USA', '10001', '212-555-1234', 'info@grandhotel.com', 5, 'Experience luxury at its finest in the heart of Manhattan. The Grand Hotel offers spacious rooms, exceptional dining, and world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:55:55', '2025-04-20 19:55:55'),
                                                                                                                                                                                             (11, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:55:55', '2025-04-20 19:55:55'),
                                                                                                                                                                                             (12, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 19:55:55', '2025-04-20 19:55:55'),
                                                                                                                                                                                             (13, 'Grand Hotel', '123 Main Street', 'New York', 'NY', 'USA', '10001', '212-555-1234', 'info@grandhotel.com', 5, 'Experience luxury at its finest in the heart of Manhattan. The Grand Hotel offers spacious rooms, exceptional dining, and world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:05:44', '2025-04-20 20:05:44'),
                                                                                                                                                                                             (14, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:05:44', '2025-04-20 20:05:44'),
                                                                                                                                                                                             (15, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:05:44', '2025-04-20 20:05:44'),
                                                                                                                                                                                             (16, 'Grand Hotel', '123 Main Street', 'New York', 'NY', 'USA', '10001', '212-555-1234', 'info@grandhotel.com', 5, 'Experience luxury at its finest in the heart of Manhattan. The Grand Hotel offers spacious rooms, exceptional dining, and world-class amenities.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:09:34', '2025-04-20 20:09:34'),
                                                                                                                                                                                             (17, 'Seaside Resort', '789 Ocean Drive', 'Miami', 'FL', 'USA', '33139', '305-555-6789', 'info@seasideresort.com', 4, 'Enjoy breathtaking ocean views and direct beach access at our beautiful Miami resort. Perfect for family vacations or romantic getaways.', 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:09:34', '2025-04-20 20:09:34'),
                                                                                                                                                                                             (18, 'Mountain Lodge', '456 Pine Road', 'Aspen', 'CO', 'USA', '81611', '970-555-4321', 'info@mountainlodge.com', 4, 'Nestled in the Rocky Mountains, our lodge offers cozy accommodations with stunning mountain views and easy access to ski slopes.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80', '2025-04-20 20:09:34', '2025-04-20 20:09:34'),
                                                                                                                                                                                             (19, 'Swad', 'Biratchowk', 'Kathmandu', '1', 'Nepal', '122', '1234567890', 'sheetalkatwal97@gmail.com', 4, 'asdfghjkl', '/HMS_war_exploded/images/hotel-images/acd2cf2d-8ba3-47b6-bf08-a299d1135de6.jpg', '2025-04-20 20:33:14', '2025-04-20 20:33:14');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
                         `room_id` int(11) NOT NULL,
                         `hotel_id` int(11) NOT NULL,
                         `room_type_id` int(11) NOT NULL,
                         `room_number` varchar(20) NOT NULL,
                         `floor` varchar(10) DEFAULT NULL,
                         `status` enum('AVAILABLE','OCCUPIED','MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_id`, `hotel_id`, `room_type_id`, `room_number`, `floor`, `status`) VALUES
                                                                                                  (6, 2, 4, '101', '1', 'AVAILABLE'),
                                                                                                  (7, 2, 4, '102', '1', 'AVAILABLE'),
                                                                                                  (8, 2, 5, '201', '2', 'AVAILABLE'),
                                                                                                  (9, 2, 5, '202', '2', 'AVAILABLE'),
                                                                                                  (10, 2, 6, '301', '3', 'OCCUPIED'),
                                                                                                  (11, 3, 7, 'A1', '1', 'AVAILABLE'),
                                                                                                  (12, 3, 7, 'A2', '1', 'AVAILABLE'),
                                                                                                  (13, 3, 8, 'B1', '1', 'AVAILABLE'),
                                                                                                  (14, 3, 8, 'B2', '1', 'AVAILABLE'),
                                                                                                  (15, 3, 9, 'C1', '1', 'AVAILABLE'),
                                                                                                  (91, 19, 55, '101', '2', 'AVAILABLE'),
                                                                                                  (92, 19, 55, '102', '2', 'OCCUPIED'),
                                                                                                  (93, 5, 56, '108', '2', 'AVAILABLE'),
                                                                                                  (94, 2, 6, '555', '2', 'MAINTENANCE');

-- --------------------------------------------------------

--
-- Table structure for table `room_types`
--

CREATE TABLE `room_types` (
                              `room_type_id` int(11) NOT NULL,
                              `hotel_id` int(11) NOT NULL,
                              `name` varchar(50) NOT NULL,
                              `description` text DEFAULT NULL,
                              `capacity` int(11) NOT NULL,
                              `price_per_night` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room_types`
--

INSERT INTO `room_types` (`room_type_id`, `hotel_id`, `name`, `description`, `capacity`, `price_per_night`) VALUES
                                                                                                                (4, 2, 'Ocean View Room', 'Cozy room with a queen-size bed and beautiful ocean views.', 2, 249.99),
                                                                                                                (5, 2, 'Beach Front Suite', 'Spacious suite with direct beach access, king-size bed, and private balcony.', 3, 399.99),
                                                                                                                (6, 2, 'Family Suite', 'Large suite with two bedrooms, perfect for families or groups.', 1, 599.99),
                                                                                                                (7, 3, 'Mountain View Room', 'Cozy room with a queen-size bed and mountain views.', 2, 179.99),
                                                                                                                (8, 3, 'Deluxe Cabin', 'Private cabin with a king-size bed, fireplace, and kitchenette.', 2, 279.99),
                                                                                                                (9, 3, 'Family Cabin', 'Spacious cabin with two bedrooms, full kitchen, and private hot tub.', 6, 479.99),
                                                                                                                (55, 19, 'Deluxe', 'shdgf', 2, 100.00),
                                                                                                                (56, 5, 'Deluxe', 'sdsd', 2, 100.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `user_id` int(11) NOT NULL,
                         `username` varchar(50) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `email` varchar(100) NOT NULL,
                         `full_name` varchar(100) NOT NULL,
                         `phone` varchar(20) DEFAULT NULL,
                         `role` enum('CUSTOMER','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
                         `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                         `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `full_name`, `phone`, `role`, `created_at`, `updated_at`) VALUES
                                                                                                                               (1, 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'admin@hms.com', 'System Administrator', '123-456-7890', 'ADMIN', '2025-04-20 19:45:23', '2025-04-20 19:45:23'),
                                                                                                                               (3, 'Shital', '/3vZexp3id3Sd1Ei/WgX8xc2ctqfgCzuxX8oQyW/WJ8=', 'Shital@gmail.com', 'Shital', '', 'ADMIN', '2025-04-20 19:54:18', '2025-04-20 19:55:38'),
                                                                                                                               (5, 'test', '/3vZexp3id3Sd1Ei/WgX8xc2ctqfgCzuxX8oQyW/WJ8=', 'test@gmail.com', 'test', '', 'CUSTOMER', '2025-04-20 19:55:28', '2025-04-20 19:55:28'),
                                                                                                                               (9, 'niruta', 'jh9YxyKfxcXOVzXjbpS3tjoITMaE+KiViQxkEkiiykk=', 'niruta@gmail.com', 'niruta', '9808765432', 'CUSTOMER', '2025-04-21 05:20:28', '2025-04-21 05:20:28'),
                                                                                                                               (10, 'Sheetal', 'enzwD/Fnh9tOIVsDsAI4yTsPmno3ja7UJKsjt2o1kOM=', 'sheetal@gmail.com', 'Sheetal', '9812345678', 'CUSTOMER', '2025-04-21 05:25:25', '2025-04-21 05:25:25');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
    ADD PRIMARY KEY (`booking_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `hotels`
--
ALTER TABLE `hotels`
    ADD PRIMARY KEY (`hotel_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
    ADD PRIMARY KEY (`room_id`),
  ADD UNIQUE KEY `hotel_id` (`hotel_id`,`room_number`),
  ADD KEY `room_type_id` (`room_type_id`);

--
-- Indexes for table `room_types`
--
ALTER TABLE `room_types`
    ADD PRIMARY KEY (`room_type_id`),
  ADD UNIQUE KEY `hotel_id` (`hotel_id`,`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
    MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `hotels`
--
ALTER TABLE `hotels`
    MODIFY `hotel_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
    MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `room_types`
--
ALTER TABLE `room_types`
    MODIFY `room_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
    ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE CASCADE;

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
    ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`hotel_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rooms_ibfk_2` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`room_type_id`) ON DELETE CASCADE;

--
-- Constraints for table `room_types`
--
ALTER TABLE `room_types`
    ADD CONSTRAINT `room_types_ibfk_1` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`hotel_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
