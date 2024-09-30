-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql_db
-- Thời gian đã tạo: Th9 18, 2024 lúc 08:57 AM
-- Phiên bản máy phục vụ: 9.0.1
-- Phiên bản PHP: 8.2.23

DROP DATABASE IF EXISTS koi_company;
-- Nếu cơ sở dữ liệu koi_company chưa tồn tại, thực hiện câu lệnh CREATE DATABASE
CREATE DATABASE IF NOT EXISTS koi_company;

-- Sử dụng cơ sở dữ liệu koi_company
USE koi_company;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `koi_company`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `google_account_id` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `role_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','BANNED','INACTIVE','UNVERIFIED','VERIFIED') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`google_account_id`, `created_at`, `date_of_birth`, `id`, `role_id`, `updated_at`, `phone_number`, `email`, `fullname`, `address`, `password`, `avatar_url`, `status`) VALUES
(0, '2024-09-18 08:45:18.271097', '1979-10-13 00:00:00.000000', 1, 1, '2024-09-18 08:45:18.271097', NULL, 'hoangclw@gmail.com', 'Hoang Cao Luu', 'Da Nang, Viet Nam', '$2a$10$YpmuWYOz0.Xf928wWlH1FOBpgmuixflAXco3xE52TDn9HHR9GW/Yq', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', NULL),
(0, '2024-09-18 08:46:04.442333', '1979-10-13 00:00:00.000000', 2, 1, '2024-09-18 08:46:04.442333', NULL, 'duong@gmail.com', 'Duong Nguyen', 'Tam Ki, Quang Nam', '$2a$10$.sw2NJvMbrnktV0T3x/0BuhAYNQLh1Q38LX6JuPzVtjYJmrEj..Cm', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', NULL),
(0, '2024-09-18 08:46:22.720305', '1979-10-13 00:00:00.000000', 3, 1, '2024-09-18 08:46:22.720305', NULL, 'son@gmail.com', 'Son Duong Con', 'Quang Binh', '$2a$10$U125Hr5iEvBFLaAWU0DxJ.Hsgggpjf5or9xC8QOc4PjtEXzzfzYAu', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
