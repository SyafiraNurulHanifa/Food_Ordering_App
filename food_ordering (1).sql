-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2025 at 05:06 PM
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
-- Database: `food_ordering`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL,
  `nama_menu` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `stok` int(11) DEFAULT 0,
  `deskripsi` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menu_id`, `nama_menu`, `kategori`, `harga`, `stok`, `deskripsi`, `created_at`, `updated_at`) VALUES
(1, 'nasi rawin kecap', 'makanan kuah', 12000.00, 29, 'rawon enak', '2025-06-09 06:43:19', '2025-06-17 07:30:26'),
(2, 'Soto Ayam', 'Makanan Utama', 12000.00, 22, 'Soto ayam dengan kuah bening dan pelengkap', '2025-06-09 06:43:19', '2025-06-10 08:35:04'),
(4, 'Es Teh Manis', 'Minuman', 3000.00, 100, 'Es teh manis segar', '2025-06-09 06:43:19', '2025-06-09 06:43:19'),
(5, 'Es Jeruk', 'Minuman', 5000.00, 76, 'Es jeruk segar dengan potongan jeruk asli', '2025-06-09 06:43:19', '2025-06-09 16:58:46'),
(6, 'Kerupuk', 'Snack', 2000.00, 198, 'Kerupuk renyah sebagai pelengkap', '2025-06-09 06:43:19', '2025-06-10 08:51:22'),
(7, 'Nasi Pecel', 'Makanan Utama', 8000.00, 38, 'Nasi pecel dengan sayuran dan sambal pecel', '2025-06-09 06:43:19', '2025-06-09 16:58:46'),
(8, 'Bakso', 'Makanan Utama', 13000.00, 29, 'Bakso dengan kuah hangat dan mie', '2025-06-09 06:43:19', '2025-06-10 08:35:04'),
(9, 'Tahu Tek', 'Snack', 7000.00, 59, 'Tahu tek dengan tauge dan lontong', '2025-06-09 06:43:19', '2025-06-09 16:51:52'),
(10, 'Kopi Hitam', 'Minuman', 4000.00, 61, 'Kopi hitam hangat tanpa gula', '2025-06-09 06:43:19', '2025-06-17 03:54:12'),
(11, 'nasi ayam rica', 'makanan', 16000.00, 9, 'adalah makanan khas yang dibuat dengan cita rasa pedas namun berempah', '2025-06-09 17:01:23', '2025-06-17 03:54:12'),
(13, 'ayam joder', 'makanan', 10000.00, 6, 'pedas membara', '2025-06-10 08:46:56', '2025-06-10 08:51:22');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total_harga` decimal(10,2) NOT NULL,
  `status` enum('pending','confirmed','preparing','ready','delivered','cancelled') DEFAULT 'pending',
  `tanggal_pesan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `menu_id`, `jumlah`, `total_harga`, `status`, `tanggal_pesan`) VALUES
(1, 2, 1, 2, 30000.00, 'delivered', '2025-06-09 06:43:19'),
(2, 2, 4, 1, 3000.00, 'delivered', '2025-06-09 06:43:19'),
(4, 3, 5, 2, 10000.00, 'preparing', '2025-06-09 06:43:19'),
(5, 5, 1, 2, 30000.00, 'ready', '2025-06-09 15:00:14'),
(6, 5, 2, 3, 36000.00, 'pending', '2025-06-09 15:00:32');

-- --------------------------------------------------------

--
-- Table structure for table `orders_master`
--

CREATE TABLE `orders_master` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_harga` decimal(10,2) NOT NULL DEFAULT 0.00,
  `status` enum('pending','confirmed','preparing','ready','delivered','cancelled') DEFAULT 'pending',
  `tanggal_pesan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders_master`
--

INSERT INTO `orders_master` (`order_id`, `user_id`, `total_harga`, `status`, `tanggal_pesan`) VALUES
(1, 2, 48000.00, 'cancelled', '2025-06-09 15:45:11'),
(2, 3, 25000.00, 'confirmed', '2025-06-09 15:45:11'),
(3, 5, 82000.00, 'pending', '2025-06-09 16:51:52'),
(4, 5, 73000.00, 'preparing', '2025-06-09 16:58:46'),
(5, 5, 24000.00, 'pending', '2025-06-09 17:09:00'),
(6, 5, 51000.00, 'pending', '2025-06-09 17:10:54'),
(7, 5, 20000.00, 'pending', '2025-06-09 17:11:55'),
(8, 5, 44000.00, 'pending', '2025-06-09 17:23:51'),
(9, 1, 124000.00, 'pending', '2025-06-10 08:29:40'),
(10, 8, 63000.00, 'ready', '2025-06-10 08:35:04'),
(11, 1, 24000.00, 'pending', '2025-06-10 08:51:22'),
(12, 1, 32000.00, 'pending', '2025-06-10 08:57:46'),
(13, 9, 36000.00, 'pending', '2025-06-17 03:54:12');

-- --------------------------------------------------------

--
-- Stand-in structure for view `order_details`
-- (See below for the actual view)
--
CREATE TABLE `order_details` (
`order_id` int(11)
,`nama_customer` varchar(100)
,`alamat` text
,`telepon` varchar(20)
,`nama_menu` varchar(100)
,`kategori` varchar(50)
,`harga` decimal(10,2)
,`jumlah` int(11)
,`total_harga` decimal(10,2)
,`status` enum('pending','confirmed','preparing','ready','delivered','cancelled')
,`tanggal_pesan` timestamp
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `order_details_new`
-- (See below for the actual view)
--
CREATE TABLE `order_details_new` (
`order_id` int(11)
,`nama_customer` varchar(100)
,`alamat` text
,`telepon` varchar(20)
,`total_harga` decimal(10,2)
,`status` enum('pending','confirmed','preparing','ready','delivered','cancelled')
,`tanggal_pesan` timestamp
,`item_id` int(11)
,`nama_menu` varchar(100)
,`kategori` varchar(50)
,`harga_satuan` decimal(10,2)
,`jumlah` int(11)
,`subtotal` decimal(10,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `item_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `harga_satuan` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`item_id`, `order_id`, `menu_id`, `jumlah`, `harga_satuan`, `subtotal`) VALUES
(1, 1, 1, 2, 15000.00, 30000.00),
(2, 1, 4, 1, 3000.00, 3000.00),
(3, 1, 5, 3, 5000.00, 15000.00),
(5, 2, 8, 1, 13000.00, 13000.00),
(6, 2, 6, 1, 2000.00, 2000.00),
(7, 3, 1, 3, 20000.00, 60000.00),
(8, 3, 5, 3, 5000.00, 15000.00),
(9, 3, 9, 1, 7000.00, 7000.00),
(10, 4, 1, 2, 20000.00, 40000.00),
(11, 4, 7, 2, 8000.00, 16000.00),
(12, 4, 10, 3, 4000.00, 12000.00),
(13, 4, 5, 1, 5000.00, 5000.00),
(14, 5, 10, 2, 4000.00, 8000.00),
(15, 5, 11, 1, 16000.00, 16000.00),
(16, 6, 8, 3, 13000.00, 39000.00),
(17, 6, 2, 1, 12000.00, 12000.00),
(18, 7, 1, 1, 20000.00, 20000.00),
(19, 8, 2, 2, 12000.00, 24000.00),
(20, 8, 1, 1, 20000.00, 20000.00),
(21, 9, 10, 3, 4000.00, 12000.00),
(22, 9, 11, 7, 16000.00, 112000.00),
(23, 10, 8, 3, 13000.00, 39000.00),
(24, 10, 2, 2, 12000.00, 24000.00),
(25, 11, 13, 2, 10000.00, 20000.00),
(26, 11, 6, 2, 2000.00, 4000.00),
(27, 12, 11, 2, 16000.00, 32000.00),
(28, 13, 11, 2, 16000.00, 32000.00),
(29, 13, 10, 1, 4000.00, 4000.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `telepon` varchar(20) DEFAULT NULL,
  `role` enum('admin','customer') DEFAULT 'customer',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `nama`, `alamat`, `telepon`, `role`, `created_at`) VALUES
(1, 'admin', 'admin123', 'Administrator', 'Jl. Admin No. 1', '081234567890', 'admin', '2025-06-09 06:43:19'),
(2, 'customer1', 'pass123', 'Budi Santoso', 'Jl. Merdeka No. 10, Jombang', '085678901234', 'customer', '2025-06-09 06:43:19'),
(3, 'customer2', 'pass456', 'Siti Nurhaliza', 'Jl. Diponegoro No. 25, Jombang', '087890123456', 'customer', '2025-06-09 06:43:19'),
(4, 'hani2323', '23', 'syafira', 'diwek', '0816659081', 'customer', '2025-06-09 14:35:01'),
(5, 'rura', 'rura123', 'rururara', 'jakarta', '081665870', 'customer', '2025-06-09 14:59:18'),
(6, 'tata', 'tata123', 'tatata', 'surabaya', '0816657908', 'customer', '2025-06-10 03:29:26'),
(8, 'dina', '123', 'dina', 'sby', '123', 'customer', '2025-06-10 08:31:35'),
(9, 'yu', 'yu123', 'yuyu', 'sby', '098', 'customer', '2025-06-17 03:48:30'),
(11, 'we', '123', 'werr', 'rumah', '123456', 'customer', '2025-06-17 07:16:53');

-- --------------------------------------------------------

--
-- Structure for view `order_details`
--
DROP TABLE IF EXISTS `order_details`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `order_details`  AS SELECT `o`.`order_id` AS `order_id`, `u`.`nama` AS `nama_customer`, `u`.`alamat` AS `alamat`, `u`.`telepon` AS `telepon`, `m`.`nama_menu` AS `nama_menu`, `m`.`kategori` AS `kategori`, `m`.`harga` AS `harga`, `o`.`jumlah` AS `jumlah`, `o`.`total_harga` AS `total_harga`, `o`.`status` AS `status`, `o`.`tanggal_pesan` AS `tanggal_pesan` FROM ((`orders` `o` join `users` `u` on(`o`.`user_id` = `u`.`user_id`)) join `menu` `m` on(`o`.`menu_id` = `m`.`menu_id`)) ;

-- --------------------------------------------------------

--
-- Structure for view `order_details_new`
--
DROP TABLE IF EXISTS `order_details_new`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `order_details_new`  AS SELECT `om`.`order_id` AS `order_id`, `u`.`nama` AS `nama_customer`, `u`.`alamat` AS `alamat`, `u`.`telepon` AS `telepon`, `om`.`total_harga` AS `total_harga`, `om`.`status` AS `status`, `om`.`tanggal_pesan` AS `tanggal_pesan`, `oi`.`item_id` AS `item_id`, `m`.`nama_menu` AS `nama_menu`, `m`.`kategori` AS `kategori`, `oi`.`harga_satuan` AS `harga_satuan`, `oi`.`jumlah` AS `jumlah`, `oi`.`subtotal` AS `subtotal` FROM (((`orders_master` `om` join `users` `u` on(`om`.`user_id` = `u`.`user_id`)) join `order_items` `oi` on(`om`.`order_id` = `oi`.`order_id`)) join `menu` `m` on(`oi`.`menu_id` = `m`.`menu_id`)) ORDER BY `om`.`order_id` DESC, `oi`.`item_id` ASC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`),
  ADD KEY `idx_menu_kategori` (`kategori`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `menu_id` (`menu_id`),
  ADD KEY `idx_order_user` (`user_id`),
  ADD KEY `idx_order_status` (`status`);

--
-- Indexes for table `orders_master`
--
ALTER TABLE `orders_master`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `idx_order_user` (`user_id`),
  ADD KEY `idx_order_status` (`status`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders_master`
--
ALTER TABLE `orders_master`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE;

--
-- Constraints for table `orders_master`
--
ALTER TABLE `orders_master`
  ADD CONSTRAINT `orders_master_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders_master` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
