-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql_db
-- Thời gian đã tạo: Th9 19, 2024 lúc 08:24 AM
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
-- Cấu trúc bảng cho bảng `auctions`
--

CREATE TABLE `auctions` (
  `id` bigint NOT NULL,
  `status` enum('ACTIVE','ENDED','ONGOING') DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `auction_kois`
--

CREATE TABLE `auction_kois` (
  `id` bigint NOT NULL,
  `base_price` bigint DEFAULT NULL,
  `bid_method` enum('ASCENDING_BID','DESCENDING_BID','FIXED_PRICE','SEALED_BID') DEFAULT NULL,
  `current_bid` int NOT NULL DEFAULT '0',
  `current_bidder_id` bigint DEFAULT NULL,
  `is_sold` tinyint(1) NOT NULL DEFAULT '0',
  `auction_id` bigint DEFAULT NULL,
  `koi_id` bigint DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `auction_kois_details`
--

CREATE TABLE `auction_kois_details` (
  `id` bigint NOT NULL,
  `bid_step` int DEFAULT NULL,
  `bid_time` datetime(6) DEFAULT NULL,
  `auction_koi_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `auction_participants`
--

CREATE TABLE `auction_participants` (
  `id` bigint NOT NULL,
  `join_time` datetime(6) DEFAULT NULL,
  `auction_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Kohaku'),
(2, 'Taisho Sanke'),
(3, 'Showa Sanshoku'),
(4, 'Utsurimono'),
(5, 'Shiro Utsuri'),
(6, 'Hi Utsuri'),
(7, 'Ki Utsuri'),
(8, 'Asagi'),
(9, 'Shusui'),
(10, 'Bekko'),
(11, 'Tancho'),
(12, 'Ginrin Kohaku'),
(13, 'Ginrin Sanke'),
(14, 'Ginrin Showa'),
(15, 'Doitsu Kohaku'),
(16, 'Doitsu Sanke'),
(17, 'Doitsu Showa'),
(18, 'Kin Kikokuryu'),
(19, 'Kumonryu'),
(20, 'Ochiba Shigure');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kois`
--

CREATE TABLE `kois` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_display` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  `name` varchar(350) NOT NULL,
  `base_price` float DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `status` enum('PENDING','REJECTED','SOLD','UNVERIFIED','VERIFIED') DEFAULT NULL,
  `thumbnail` varchar(300) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `kois`
--

INSERT INTO `kois` (`id`, `created_at`, `updated_at`, `age`, `description`, `is_display`, `length`, `name`, `base_price`, `sex`, `status`, `thumbnail`, `category_id`, `owner_id`) VALUES
(1, '2024-09-19 08:23:30.253841', '2024-09-19 08:23:30.253841', 105, 'Rerum qui rem architecto.', 1, 26, 'Kanoko Showa', 18989400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(2, '2024-09-19 08:23:30.295634', '2024-09-19 08:23:30.295634', 204, 'Asperiores repudiandae aliquam nobis mollitia consequatur quibusdam commodi.', 0, 36, 'Kin Ki Utsuri', 36291900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(3, '2024-09-19 08:23:30.531160', '2024-09-19 08:23:30.531160', 193, 'Minus deleniti recusandae magnam ut.', 1, 4, 'Hariwake', 12720800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(4, '2024-09-19 08:23:30.584321', '2024-09-19 08:23:30.584321', 174, 'Impedit id harum consequatur nobis nisi rem qui.', 1, 15, 'Kikokuryu', 71248300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(5, '2024-09-19 08:23:30.644281', '2024-09-19 08:23:30.644281', 75, 'Id esse labore perspiciatis asperiores aliquid.', 0, 47, 'Kage Shiro Utsuri', 34312900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(6, '2024-09-19 08:23:30.709260', '2024-09-19 08:23:30.709260', 58, 'Quia maxime facere aliquid et dolores.', 0, 82, 'Matsuba', 35529400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(7, '2024-09-19 08:23:30.791433', '2024-09-19 08:23:30.791433', 39, 'Illum nostrum dolore accusantium porro distinctio.', 0, 1, 'Yamatonishiki', 50025900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(8, '2024-09-19 08:23:30.845949', '2024-09-19 08:23:30.845949', 39, 'Consectetur officiis nemo similique optio eaque.', 0, 5, 'Hikari Muji', 36265300, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(9, '2024-09-19 08:23:30.896364', '2024-09-19 08:23:30.896364', 193, 'Quia deleniti consequuntur doloribus earum.', 1, 11, 'Ginrin Sanke', 24350600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(10, '2024-09-19 08:23:30.953551', '2024-09-19 08:23:30.953551', 115, 'Placeat assumenda numquam voluptatibus omnis sapiente non laboriosam.', 1, 52, 'Kikusui', 50064800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 3),
(11, '2024-09-19 08:23:31.011209', '2024-09-19 08:23:31.011209', 126, 'Maxime quia saepe at omnis quod aut iste.', 1, 39, 'Asagi', 3272690, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(12, '2024-09-19 08:23:31.056043', '2024-09-19 08:23:31.056043', 143, 'Rem nostrum iusto ut a.', 1, 4, 'Kujaku', 1598480, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(13, '2024-09-19 08:23:31.096212', '2024-09-19 08:23:31.096212', 138, 'Consequatur culpa id et facilis omnis numquam.', 0, 10, 'Asagi', 16550200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(14, '2024-09-19 08:23:31.136118', '2024-09-19 08:23:31.136118', 136, 'Voluptatem voluptatibus praesentium.', 1, 55, 'Doitsu Sanke', 11807200, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(15, '2024-09-19 08:23:31.198635', '2024-09-19 08:23:31.198635', 221, 'Reiciendis dolores sunt eveniet dolores totam.', 0, 36, 'Asagi', 65035800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(16, '2024-09-19 08:23:31.264454', '2024-09-19 08:23:31.264454', 41, 'Officia ea explicabo officia.', 0, 73, 'Ki Utsuri', 16632600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(17, '2024-09-19 08:23:31.341560', '2024-09-19 08:23:31.341560', 121, 'Itaque iste qui doloremque asperiores autem et quis.', 0, 21, 'Gin Shiro Utsuri', 47583900, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(18, '2024-09-19 08:23:31.431578', '2024-09-19 08:23:31.431578', 62, 'Ratione ut consectetur aut.', 1, 19, 'Tancho', 28893300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(19, '2024-09-19 08:23:31.530402', '2024-09-19 08:23:31.530402', 124, 'Animi sunt est.', 1, 85, 'Goshiki', 45164800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(20, '2024-09-19 08:23:31.623852', '2024-09-19 08:23:31.623852', 149, 'A soluta aut nobis consectetur.', 0, 61, 'Soragoi', 28704400, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(21, '2024-09-19 08:23:31.696914', '2024-09-19 08:23:31.696914', 154, 'Nobis consequatur quod ut velit est voluptas veritatis.', 1, 90, 'Doitsu Sanke', 82236800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(22, '2024-09-19 08:23:31.758303', '2024-09-19 08:23:31.758303', 78, 'Id vero alias et omnis animi.', 1, 42, 'Doitsu Kohaku', 21441200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(23, '2024-09-19 08:23:31.827770', '2024-09-19 08:23:31.827770', 186, 'Explicabo et ullam sapiente dicta ut et.', 1, 4, 'Hariwake', 39089200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(24, '2024-09-19 08:23:32.549751', '2024-09-19 08:23:32.549751', 12, 'Assumenda vel voluptatibus ad quaerat sint.', 0, 37, 'Kanoko Showa', 87742800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(25, '2024-09-19 08:23:32.600149', '2024-09-19 08:23:32.600149', 65, 'Eveniet id eos eum similique qui nulla.', 1, 69, 'Kin Showa', 7755610, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(26, '2024-09-19 08:23:32.655597', '2024-09-19 08:23:32.655597', 71, 'Eum quia ut aut incidunt mollitia sunt ipsa.', 0, 20, 'Showa', 82564800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(27, '2024-09-19 08:23:32.700986', '2024-09-19 08:23:32.700986', 179, 'Sed dolorem laudantium.', 0, 41, 'Doitsu Showa', 53087100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(28, '2024-09-19 08:23:32.748145', '2024-09-19 08:23:32.748145', 140, 'Pariatur sed non harum omnis.', 0, 74, 'Kanoko Showa', 66080200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(29, '2024-09-19 08:23:32.797648', '2024-09-19 08:23:32.797648', 134, 'Eius est suscipit doloribus aliquam voluptates omnis maxime.', 1, 62, 'Aka Matsuba', 40409800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(30, '2024-09-19 08:23:32.835358', '2024-09-19 08:23:32.835358', 141, 'Aspernatur iste autem sed sint ut.', 1, 22, 'Kikusui', 61034400, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(31, '2024-09-19 08:23:32.873638', '2024-09-19 08:23:32.873638', 121, 'Nisi enim ut et.', 0, 31, 'Budo Sanke', 26906100, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(32, '2024-09-19 08:23:32.915096', '2024-09-19 08:23:32.915096', 132, 'Minima qui sed veniam.', 1, 59, 'Ginrin Showa', 35122400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(33, '2024-09-19 08:23:32.950297', '2024-09-19 08:23:32.950297', 104, 'Officia iste velit accusantium perspiciatis eos cupiditate.', 0, 76, 'Shusui', 69002600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(34, '2024-09-19 08:23:32.993808', '2024-09-19 08:23:32.993808', 21, 'Excepturi facilis qui et reprehenderit commodi tempore neque.', 1, 88, 'Kanoko Sanke', 30900900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(35, '2024-09-19 08:23:33.165518', '2024-09-19 08:23:33.165518', 228, 'Et molestias dignissimos autem veniam labore vero.', 0, 42, 'Kage Shiro Utsuri', 23870800, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(36, '2024-09-19 08:23:33.225697', '2024-09-19 08:23:33.225697', 140, 'Vero doloribus odio ab.', 1, 24, 'Sanke', 30029600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(37, '2024-09-19 08:23:33.271672', '2024-09-19 08:23:33.272228', 113, 'Quam quis consectetur est voluptate in quia vel.', 1, 1, 'Showa', 22525400, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(38, '2024-09-19 08:23:33.309798', '2024-09-19 08:23:33.309798', 139, 'Mollitia velit dolor.', 0, 46, 'Doitsu Kohaku', 9257550, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(39, '2024-09-19 08:23:33.343410', '2024-09-19 08:23:33.343410', 146, 'Et blanditiis officia molestiae nulla omnis autem.', 0, 83, 'Hikari Utsuri', 40919000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(40, '2024-09-19 08:23:33.379924', '2024-09-19 08:23:33.379924', 125, 'Consequuntur pariatur saepe.', 1, 36, 'Gin Shiro Utsuri', 7828770, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(41, '2024-09-19 08:23:33.417933', '2024-09-19 08:23:33.417933', 13, 'Blanditiis et qui totam dolor debitis.', 1, 44, 'Doitsu', 20120400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(42, '2024-09-19 08:23:33.461305', '2024-09-19 08:23:33.461305', 162, 'Totam praesentium quos enim aut velit.', 1, 31, 'Doitsu Sanke', 62433600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(43, '2024-09-19 08:23:33.510853', '2024-09-19 08:23:33.510853', 167, 'Qui et dignissimos.', 1, 84, 'Hikari Muji', 9734890, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(44, '2024-09-19 08:23:33.580489', '2024-09-19 08:23:33.580489', 112, 'Quis mollitia necessitatibus saepe enim quo ab.', 1, 64, 'Budo Sanke', 43480600, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(45, '2024-09-19 08:23:33.619486', '2024-09-19 08:23:33.619486', 174, 'Laborum voluptas qui velit animi optio.', 1, 6, 'Ginrin Sanke', 64383500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(46, '2024-09-19 08:23:33.662869', '2024-09-19 08:23:33.662869', 188, 'Odio nulla iusto eos doloremque.', 1, 4, 'Yamatonishiki', 67271700, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(47, '2024-09-19 08:23:33.701426', '2024-09-19 08:23:33.701426', 88, 'Et blanditiis consequatur id.', 1, 44, 'Soragoi', 55056700, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(48, '2024-09-19 08:23:33.733569', '2024-09-19 08:23:33.733569', 26, 'Magni doloribus numquam omnis.', 1, 20, 'Bekko', 88206700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(49, '2024-09-19 08:23:33.772485', '2024-09-19 08:23:33.772485', 112, 'Saepe et omnis eveniet enim eos itaque.', 0, 82, 'Goshiki', 60682100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(50, '2024-09-19 08:23:33.813383', '2024-09-19 08:23:33.813383', 167, 'Temporibus voluptates rem et libero odit quis.', 1, 19, 'Kanoko Kohaku', 29322800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(51, '2024-09-19 08:23:33.863909', '2024-09-19 08:23:33.863909', 44, 'Accusamus numquam aliquid.', 1, 47, 'Ginrin Showa', 66275200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(52, '2024-09-19 08:23:33.900256', '2024-09-19 08:23:33.900256', 47, 'Illo eaque et totam nemo est mollitia itaque.', 1, 30, 'Hikari Utsuri', 6537960, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(53, '2024-09-19 08:23:33.937082', '2024-09-19 08:23:33.937082', 70, 'Voluptas exercitationem nobis magnam.', 1, 63, 'Doitsu Kohaku', 36202300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(54, '2024-09-19 08:23:33.974551', '2024-09-19 08:23:33.974551', 74, 'Animi sunt est et ut ut.', 1, 51, 'Showa', 23374700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(55, '2024-09-19 08:23:34.011063', '2024-09-19 08:23:34.011063', 65, 'Minus ipsam voluptatem.', 1, 83, 'Kujaku', 64450100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(56, '2024-09-19 08:23:34.044848', '2024-09-19 08:23:34.044848', 116, 'Aliquam incidunt dolorem iure.', 1, 77, 'Doitsu', 23924400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(57, '2024-09-19 08:23:34.079251', '2024-09-19 08:23:34.079251', 67, 'Et in esse deleniti animi enim nemo sequi.', 1, 80, 'Budo Sanke', 320643, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(58, '2024-09-19 08:23:34.114783', '2024-09-19 08:23:34.114783', 5, 'Nesciunt itaque aut quia consequatur voluptate.', 0, 25, 'Kage Shiro Utsuri', 52400000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(59, '2024-09-19 08:23:34.156549', '2024-09-19 08:23:34.156549', 47, 'Nulla necessitatibus eveniet alias sit eos rem velit.', 1, 21, 'Bekko', 40172600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(60, '2024-09-19 08:23:34.203242', '2024-09-19 08:23:34.203242', 193, 'Magni quae omnis consequatur asperiores.', 0, 50, 'Yamabuki', 36005000, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(61, '2024-09-19 08:23:34.247698', '2024-09-19 08:23:34.247698', 137, 'Quo et fugit reprehenderit est ab illo.', 0, 48, 'Kujaku', 71154800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(62, '2024-09-19 08:23:34.285056', '2024-09-19 08:23:34.285056', 209, 'Quaerat ipsum excepturi quo corporis sint.', 1, 11, 'Bekko', 49581400, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(63, '2024-09-19 08:23:34.328995', '2024-09-19 08:23:34.328995', 70, 'Ullam hic et.', 1, 30, 'Ki Utsuri', 82086100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(64, '2024-09-19 08:23:34.366065', '2024-09-19 08:23:34.366065', 208, 'Inventore doloremque ut quasi placeat consequatur autem.', 0, 12, 'Doitsu Kujaku', 86221800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(65, '2024-09-19 08:23:34.407773', '2024-09-19 08:23:34.407773', 149, 'Cum exercitationem ipsa et voluptatum vitae ex.', 0, 25, 'Chagoi', 58737000, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(66, '2024-09-19 08:23:34.438582', '2024-09-19 08:23:34.438582', 225, 'Praesentium enim temporibus illo provident.', 0, 31, 'Gin Shiro Utsuri', 34478500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(67, '2024-09-19 08:23:34.474895', '2024-09-19 08:23:34.475428', 214, 'Unde quaerat repellat excepturi expedita minima.', 1, 21, 'Tancho', 74112600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(68, '2024-09-19 08:23:34.507434', '2024-09-19 08:23:34.507434', 129, 'Laudantium quod aliquid velit ex quis nihil.', 0, 64, 'Ginrin Kohaku', 20417800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(69, '2024-09-19 08:23:34.574089', '2024-09-19 08:23:34.574089', 103, 'Voluptas quam numquam earum minus.', 1, 85, 'Ginrin Chagoi', 45869200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(70, '2024-09-19 08:23:34.613301', '2024-09-19 08:23:34.613301', 121, 'Illo magni eveniet maiores iusto dolores repellendus.', 1, 24, 'Midorigoi', 15560300, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(71, '2024-09-19 08:23:34.643997', '2024-09-19 08:23:34.643997', 208, 'Quibusdam eveniet explicabo eligendi assumenda fugit accusantium.', 0, 52, 'Hikari Utsuri', 22151200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(72, '2024-09-19 08:23:34.688536', '2024-09-19 08:23:34.688536', 39, 'Consequatur ipsum tempore rerum et rem aperiam mollitia.', 0, 87, 'Beni Kikokuryu', 51183500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(73, '2024-09-19 08:23:34.732471', '2024-09-19 08:23:34.732471', 13, 'Ad occaecati sed aut iusto.', 1, 17, 'Platinum Ogon', 43010000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(74, '2024-09-19 08:23:34.773253', '2024-09-19 08:23:34.773253', 149, 'Nulla est non iusto.', 0, 38, 'Doitsu Kohaku', 86728900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(75, '2024-09-19 08:23:34.806642', '2024-09-19 08:23:34.806642', 94, 'Odit qui omnis ut sint.', 1, 20, 'Ochiba', 63990200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(76, '2024-09-19 08:23:34.839576', '2024-09-19 08:23:34.839576', 115, 'Sequi veritatis reprehenderit quas.', 1, 17, 'Kujaku', 6052730, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(77, '2024-09-19 08:23:34.873215', '2024-09-19 08:23:34.873215', 162, 'Dolorem perferendis deserunt.', 1, 81, 'Kin Ki Utsuri', 65746200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 3),
(78, '2024-09-19 08:23:34.904576', '2024-09-19 08:23:34.904576', 182, 'Eum deleniti iste pariatur.', 0, 90, 'Kage Shiro Utsuri', 46105100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(79, '2024-09-19 08:23:34.946104', '2024-09-19 08:23:34.946104', 6, 'Officia nisi minima ut quae ut molestiae.', 1, 64, 'Chagoi', 6200510, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(80, '2024-09-19 08:23:35.002031', '2024-09-19 08:23:35.002031', 215, 'Exercitationem id id.', 1, 28, 'Yamatonishiki', 84718400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2),
(81, '2024-09-19 08:23:35.030082', '2024-09-19 08:23:35.030082', 204, 'Mollitia vel sed excepturi voluptas.', 1, 5, 'Showa', 65851500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(82, '2024-09-19 08:23:35.057076', '2024-09-19 08:23:35.057076', 220, 'Magni fugiat amet est.', 0, 79, 'Gin Shiro Utsuri', 38801100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(83, '2024-09-19 08:23:35.085699', '2024-09-19 08:23:35.085699', 224, 'Excepturi aperiam error expedita accusantium eaque possimus consequatur.', 1, 24, 'Doitsu Showa', 78985900, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(84, '2024-09-19 08:23:35.119749', '2024-09-19 08:23:35.119749', 121, 'Quae molestiae accusamus voluptatem sed odio eum.', 1, 41, 'Showa', 3450220, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(85, '2024-09-19 08:23:35.148779', '2024-09-19 08:23:35.148779', 173, 'Alias eligendi explicabo expedita.', 1, 58, 'Kin Ki Utsuri', 75635100, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(86, '2024-09-19 08:23:35.185567', '2024-09-19 08:23:35.185567', 137, 'Numquam quia debitis ad eligendi.', 0, 45, 'Kohaku', 45630300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 3),
(87, '2024-09-19 08:23:35.232990', '2024-09-19 08:23:35.232990', 219, 'Laborum et quaerat rerum repudiandae est mollitia voluptatem.', 0, 23, 'Doitsu Showa', 53322600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(88, '2024-09-19 08:23:35.267048', '2024-09-19 08:23:35.267048', 99, 'Iure sit omnis amet ipsum natus praesentium.', 0, 25, 'Gin Shiro Utsuri', 52968800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(89, '2024-09-19 08:23:35.296013', '2024-09-19 08:23:35.296013', 89, 'Illum sit non.', 1, 9, 'Tancho', 66569000, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(90, '2024-09-19 08:23:35.326606', '2024-09-19 08:23:35.326606', 50, 'Qui ratione reiciendis dolorem et consequuntur aut.', 1, 48, 'Ginrin Chagoi', 949358, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(91, '2024-09-19 08:23:35.353207', '2024-09-19 08:23:35.353207', 87, 'Placeat velit voluptate corporis.', 1, 11, 'Kanoko Kohaku', 75393200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(92, '2024-09-19 08:23:35.381279', '2024-09-19 08:23:35.381279', 18, 'Repellendus molestias qui quibusdam nulla dolore.', 0, 38, 'Kikusui', 74023400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(93, '2024-09-19 08:23:35.409902', '2024-09-19 08:23:35.409902', 170, 'Quibusdam nulla asperiores libero doloribus atque laborum odio.', 1, 87, 'Gin Shiro Utsuri', 83669300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(94, '2024-09-19 08:23:35.436173', '2024-09-19 08:23:35.436173', 83, 'Consequatur eum voluptatem omnis eum ut cupiditate aut.', 1, 84, 'Platinum Ogon', 76038000, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(95, '2024-09-19 08:23:35.478393', '2024-09-19 08:23:35.478393', 153, 'Aut cum quasi error nobis atque vel aut.', 0, 82, 'Budo Sanke', 65681800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 3),
(96, '2024-09-19 08:23:35.518851', '2024-09-19 08:23:35.518851', 3, 'Et aut quisquam ut ut ipsum repellendus est.', 0, 57, 'Chagoi', 64687300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(97, '2024-09-19 08:23:35.553464', '2024-09-19 08:23:35.553464', 221, 'Non iusto quia ullam quia corporis et.', 0, 25, 'Koromo', 29898300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(98, '2024-09-19 08:23:35.603311', '2024-09-19 08:23:35.603311', 47, 'Minus non quia saepe ad laboriosam ut reprehenderit.', 0, 71, 'Budo Sanke', 33611900, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(99, '2024-09-19 08:23:35.707927', '2024-09-19 08:23:35.707927', 221, 'Quisquam velit consequuntur porro et fuga sunt.', 1, 75, 'Koromo', 49258200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(100, '2024-09-19 08:23:35.761606', '2024-09-19 08:23:35.761606', 89, 'Nisi dolores quia.', 0, 49, 'Kanoko Kohaku', 8059230, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(101, '2024-09-19 08:23:35.850641', '2024-09-19 08:23:35.850641', 16, 'Sapiente et qui quia.', 1, 72, 'Ginrin Chagoi', 68608700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(102, '2024-09-19 08:23:35.915849', '2024-09-19 08:23:35.915849', 118, 'Aut explicabo non neque veritatis autem aut qui.', 0, 40, 'Hikari Utsuri', 37903100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(103, '2024-09-19 08:23:35.964741', '2024-09-19 08:23:35.964741', 145, 'Est occaecati unde.', 0, 24, 'Ginrin Sanke', 49215400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(104, '2024-09-19 08:23:36.000138', '2024-09-19 08:23:36.000138', 162, 'Vitae consectetur quis aliquam quo.', 1, 86, 'Hariwake', 86268500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(105, '2024-09-19 08:23:36.035976', '2024-09-19 08:23:36.035976', 95, 'Et fuga et voluptas qui est sed voluptatem.', 1, 24, 'Yamabuki', 80112400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(106, '2024-09-19 08:23:36.091302', '2024-09-19 08:23:36.091302', 158, 'Est accusamus libero asperiores expedita harum impedit blanditiis.', 0, 32, 'Yamatonishiki', 79045200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(107, '2024-09-19 08:23:36.148103', '2024-09-19 08:23:36.148103', 157, 'Sint modi maiores id ipsum et.', 1, 8, 'Kumonryu', 10989200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(108, '2024-09-19 08:23:36.196260', '2024-09-19 08:23:36.196260', 161, 'Voluptas eos et reiciendis facilis a aut nam.', 1, 41, 'Asagi', 8367860, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(109, '2024-09-19 08:23:36.236247', '2024-09-19 08:23:36.236247', 128, 'Autem praesentium sint perspiciatis.', 0, 83, 'Asagi', 18332700, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(110, '2024-09-19 08:23:36.282180', '2024-09-19 08:23:36.282180', 228, 'Vitae asperiores quaerat ratione et rerum.', 1, 43, 'Hikari Muji', 47586800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(111, '2024-09-19 08:23:36.324035', '2024-09-19 08:23:36.324035', 196, 'Non dolores ex.', 1, 19, 'Kanoko Sanke', 26803400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(112, '2024-09-19 08:23:36.370147', '2024-09-19 08:23:36.370147', 110, 'Molestiae soluta eos velit repellat consequatur voluptatem.', 1, 67, 'Asagi', 76314200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3),
(113, '2024-09-19 08:23:36.419768', '2024-09-19 08:23:36.419768', 216, 'Ipsam aut sit aliquam aut.', 1, 46, 'Shusui', 4646860, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(114, '2024-09-19 08:23:36.466533', '2024-09-19 08:23:36.466533', 208, 'Cum possimus nihil occaecati ducimus explicabo minima non.', 1, 33, 'Ginrin Chagoi', 49622500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(115, '2024-09-19 08:23:36.516509', '2024-09-19 08:23:36.516509', 103, 'Ad et totam in fugiat nihil explicabo architecto.', 1, 82, 'Gin Shiro Utsuri', 62063900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(116, '2024-09-19 08:23:36.549029', '2024-09-19 08:23:36.549029', 156, 'Nobis soluta esse natus.', 0, 17, 'Kanoko Kohaku', 78545200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(117, '2024-09-19 08:23:36.592183', '2024-09-19 08:23:36.592183', 53, 'Pariatur quis dignissimos.', 1, 45, 'Aka Matsuba', 40235900, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(118, '2024-09-19 08:23:36.650867', '2024-09-19 08:23:36.650867', 107, 'Sequi dolor voluptas eos in omnis laboriosam.', 0, 38, 'Kohaku', 53133100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(119, '2024-09-19 08:23:36.717084', '2024-09-19 08:23:36.717084', 50, 'Sequi numquam temporibus.', 1, 84, 'Doitsu Kujaku', 70427900, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(120, '2024-09-19 08:23:36.775046', '2024-09-19 08:23:36.775046', 128, 'Sunt reiciendis labore vitae qui distinctio.', 0, 76, 'Ginrin Sanke', 51025800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(121, '2024-09-19 08:23:36.829214', '2024-09-19 08:23:36.829214', 138, 'Sed possimus velit vel.', 0, 63, 'Tancho', 27621100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(122, '2024-09-19 08:23:36.876786', '2024-09-19 08:23:36.876786', 129, 'Saepe architecto praesentium ut aut beatae et.', 1, 52, 'Asagi', 53473400, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(123, '2024-09-19 08:23:36.929875', '2024-09-19 08:23:36.929875', 187, 'Sed quaerat quia explicabo.', 1, 69, 'Doitsu Sanke', 63391300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(124, '2024-09-19 08:23:36.988305', '2024-09-19 08:23:36.988305', 4, 'Laborum sit quasi autem ipsam incidunt.', 1, 40, 'Soragoi', 41607800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(125, '2024-09-19 08:23:37.045333', '2024-09-19 08:23:37.045333', 194, 'Vel dolore nesciunt provident.', 0, 33, 'Platinum Ogon', 60270000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(126, '2024-09-19 08:23:37.113409', '2024-09-19 08:23:37.113409', 35, 'Sit amet ut quae aperiam expedita deleniti.', 1, 63, 'Goshiki', 76381700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(127, '2024-09-19 08:23:37.175954', '2024-09-19 08:23:37.175954', 176, 'Quaerat rerum maxime repudiandae dolores officia.', 1, 79, 'Bekko', 17867100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(128, '2024-09-19 08:23:37.227156', '2024-09-19 08:23:37.227156', 76, 'Illo blanditiis quis et et aliquam qui dignissimos.', 1, 44, 'Soragoi', 85998300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(129, '2024-09-19 08:23:37.298971', '2024-09-19 08:23:37.298971', 122, 'Molestiae explicabo et reprehenderit reprehenderit sequi repellat amet.', 1, 84, 'Midorigoi', 65822200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(130, '2024-09-19 08:23:37.343253', '2024-09-19 08:23:37.343253', 145, 'Consequuntur ipsa repudiandae rerum dolores.', 1, 30, 'Doitsu Kujaku', 74541500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2),
(131, '2024-09-19 08:23:37.390019', '2024-09-19 08:23:37.390019', 91, 'Eveniet quae maiores odio praesentium quibusdam aut.', 1, 33, 'Benigoi', 23536000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(132, '2024-09-19 08:23:37.426283', '2024-09-19 08:23:37.426283', 173, 'Est vel blanditiis.', 1, 83, 'Doitsu', 27289200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(133, '2024-09-19 08:23:37.473311', '2024-09-19 08:23:37.473311', 163, 'Ut voluptatem a quisquam ut ullam.', 0, 6, 'Ginrin Kohaku', 23896000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(134, '2024-09-19 08:23:37.517774', '2024-09-19 08:23:37.517774', 205, 'Aut ex quam optio.', 1, 67, 'Doitsu Kujaku', 44695700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 3),
(135, '2024-09-19 08:23:37.570703', '2024-09-19 08:23:37.570703', 201, 'Eum ipsum voluptatem voluptas consequatur non.', 0, 20, 'Benigoi', 28156600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(136, '2024-09-19 08:23:37.616536', '2024-09-19 08:23:37.616536', 108, 'Molestiae consequuntur in itaque voluptatem.', 1, 74, 'Budo Sanke', 3182070, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(137, '2024-09-19 08:23:37.662689', '2024-09-19 08:23:37.662689', 41, 'Voluptatum sit mollitia nihil sit quia velit.', 1, 69, 'Ai Goromo', 66942700, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(138, '2024-09-19 08:23:37.714930', '2024-09-19 08:23:37.714930', 175, 'Accusamus nihil atque dignissimos earum.', 1, 43, 'Orenji Ogon', 59278100, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(139, '2024-09-19 08:23:37.761528', '2024-09-19 08:23:37.761528', 138, 'Facilis placeat consequuntur animi quia.', 0, 51, 'Showa', 45872400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(140, '2024-09-19 08:23:37.814893', '2024-09-19 08:23:37.814893', 37, 'Maxime cumque consectetur ipsam.', 1, 57, 'Yamatonishiki', 72828900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(141, '2024-09-19 08:23:37.866963', '2024-09-19 08:23:37.866963', 47, 'Ducimus omnis vero facere enim vel.', 1, 4, 'Budo Sanke', 60609300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(142, '2024-09-19 08:23:37.922457', '2024-09-19 08:23:37.922457', 163, 'Explicabo repellat id aut vel a.', 0, 66, 'Ochiba', 25574800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(143, '2024-09-19 08:23:37.987329', '2024-09-19 08:23:37.987329', 3, 'Iste reprehenderit sit sit temporibus et velit.', 1, 86, 'Kikokuryu', 25266400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(144, '2024-09-19 08:23:38.094142', '2024-09-19 08:23:38.094142', 217, 'Ex hic quis aspernatur voluptatibus ea.', 1, 8, 'Kanoko Showa', 33625600, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(145, '2024-09-19 08:23:38.190353', '2024-09-19 08:23:38.190353', 188, 'Culpa nam dolorem dolorem tempora dolorem eveniet facere.', 0, 57, 'Doitsu Showa', 48195500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(146, '2024-09-19 08:23:38.286928', '2024-09-19 08:23:38.286928', 23, 'Sapiente officia maxime.', 0, 58, 'Hariwake', 75528900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(147, '2024-09-19 08:23:38.427439', '2024-09-19 08:23:38.427439', 34, 'Consequatur vel accusantium sint in at.', 1, 12, 'Utsurimono', 27613600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(148, '2024-09-19 08:23:38.516026', '2024-09-19 08:23:38.516026', 11, 'Officiis officiis quis ut veniam modi natus ut.', 1, 24, 'Chagoi', 66112100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(149, '2024-09-19 08:23:38.601844', '2024-09-19 08:23:38.601844', 218, 'Nesciunt nulla non quo.', 1, 52, 'Ginrin Showa', 51324400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 1),
(150, '2024-09-19 08:23:38.675807', '2024-09-19 08:23:38.675807', 70, 'Porro nobis aut ut dolorem sapiente provident.', 0, 32, 'Midorigoi', 73922800, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(151, '2024-09-19 08:23:38.728643', '2024-09-19 08:23:38.728643', 64, 'Placeat enim quo.', 0, 28, 'Kanoko Showa', 10321500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(152, '2024-09-19 08:23:38.782792', '2024-09-19 08:23:38.782792', 30, 'Sequi aperiam aut.', 1, 4, 'Goshiki', 68993900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 3),
(153, '2024-09-19 08:23:38.859809', '2024-09-19 08:23:38.859809', 222, 'Nesciunt porro quasi.', 0, 73, 'Doitsu Sanke', 22352700, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(154, '2024-09-19 08:23:38.907606', '2024-09-19 08:23:38.907606', 149, 'Ut sit vel aut est voluptate aspernatur.', 1, 48, 'Shusui', 19443300, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(155, '2024-09-19 08:23:38.954862', '2024-09-19 08:23:38.954862', 46, 'Assumenda dolorem id unde et quibusdam accusantium.', 0, 65, 'Kujaku', 50899100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(156, '2024-09-19 08:23:39.003887', '2024-09-19 08:23:39.003887', 128, 'Nemo aut id inventore quod.', 1, 50, 'Ki Bekko', 44892500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(157, '2024-09-19 08:23:39.046061', '2024-09-19 08:23:39.046061', 4, 'Nobis consectetur fuga ab.', 1, 84, 'Sanke', 16237300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(158, '2024-09-19 08:23:39.090224', '2024-09-19 08:23:39.090224', 195, 'Odit veniam voluptatem excepturi illo non.', 0, 78, 'Kanoko Kohaku', 41242900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(159, '2024-09-19 08:23:39.135979', '2024-09-19 08:23:39.135979', 3, 'Eius voluptatibus aperiam maiores quae sint.', 1, 26, 'Benigoi', 12488400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(160, '2024-09-19 08:23:39.178928', '2024-09-19 08:23:39.178928', 218, 'Et quos non qui voluptatem sapiente sunt animi.', 0, 89, 'Hariwake', 2786030, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(161, '2024-09-19 08:23:39.215400', '2024-09-19 08:23:39.215400', 49, 'Vitae nostrum fuga enim a est.', 1, 83, 'Utsurimono', 41035000, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3),
(162, '2024-09-19 08:23:39.248400', '2024-09-19 08:23:39.248400', 119, 'Excepturi ut quia soluta occaecati.', 0, 15, 'Ginrin Kohaku', 14258000, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(163, '2024-09-19 08:23:39.275576', '2024-09-19 08:23:39.275576', 70, 'Sequi voluptas accusantium deleniti eaque.', 1, 52, 'Platinum Ogon', 17127300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(164, '2024-09-19 08:23:39.304188', '2024-09-19 08:23:39.304188', 59, 'Omnis fuga id explicabo quasi ullam.', 1, 84, 'Matsuba', 24811500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(165, '2024-09-19 08:23:39.332236', '2024-09-19 08:23:39.332236', 64, 'Dolor tempora ut rerum libero qui deleniti porro.', 1, 86, 'Kanoko Sanke', 53217600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(166, '2024-09-19 08:23:39.367977', '2024-09-19 08:23:39.367977', 29, 'Quo est et eaque iste non ducimus.', 1, 52, 'Kikusui', 21411400, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(167, '2024-09-19 08:23:39.410948', '2024-09-19 08:23:39.410948', 105, 'Voluptatem perferendis deserunt quia et quod ut.', 1, 10, 'Utsurimono', 73698900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(168, '2024-09-19 08:23:39.458909', '2024-09-19 08:23:39.458909', 26, 'Corporis unde distinctio laboriosam dolorum voluptatem et laudantium.', 1, 47, 'Kanoko Kohaku', 71672100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(169, '2024-09-19 08:23:39.504248', '2024-09-19 08:23:39.504248', 143, 'Eum molestiae aut laudantium qui atque.', 1, 37, 'Ai Goromo', 70265300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(170, '2024-09-19 08:23:39.554677', '2024-09-19 08:23:39.554677', 34, 'Cum libero omnis pariatur.', 1, 45, 'Benigoi', 36937300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(171, '2024-09-19 08:23:39.591506', '2024-09-19 08:23:39.591506', 99, 'Fugiat adipisci deleniti similique doloremque voluptatem omnis.', 1, 33, 'Benigoi', 46057900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(172, '2024-09-19 08:23:39.628995', '2024-09-19 08:23:39.628995', 125, 'Quisquam explicabo repudiandae sequi.', 0, 85, 'Doitsu', 54062900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(173, '2024-09-19 08:23:39.667244', '2024-09-19 08:23:39.667244', 201, 'Facere odio veritatis temporibus.', 1, 12, 'Ginrin Showa', 35297000, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(174, '2024-09-19 08:23:39.710847', '2024-09-19 08:23:39.710847', 23, 'At rerum sequi optio dolorem consequatur.', 1, 52, 'Orenji Ogon', 54020500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(175, '2024-09-19 08:23:39.748271', '2024-09-19 08:23:39.748271', 145, 'Itaque magnam natus excepturi in doloribus animi ut.', 1, 11, 'Ochiba', 42388700, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(176, '2024-09-19 08:23:39.793505', '2024-09-19 08:23:39.793505', 43, 'Eligendi cumque est reiciendis corporis perferendis.', 0, 33, 'Hikari Utsuri', 70485100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(177, '2024-09-19 08:23:39.844390', '2024-09-19 08:23:39.844390', 170, 'Eum autem molestiae dolor est.', 0, 42, 'Ginrin Showa', 20934600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(178, '2024-09-19 08:23:39.887964', '2024-09-19 08:23:39.887964', 45, 'Maxime quia sed omnis doloribus dolore id.', 0, 81, 'Showa', 35135100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(179, '2024-09-19 08:23:39.926922', '2024-09-19 08:23:39.926922', 96, 'Vero eligendi eos aperiam voluptatem.', 1, 68, 'Kanoko Sanke', 14949200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(180, '2024-09-19 08:23:39.973619', '2024-09-19 08:23:39.973619', 68, 'Animi aut quia autem.', 1, 23, 'Doitsu Kujaku', 63844000, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(181, '2024-09-19 08:23:40.025104', '2024-09-19 08:23:40.025104', 183, 'Dicta quidem nostrum nulla.', 1, 67, 'Doitsu Kohaku', 1923960, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(182, '2024-09-19 08:23:40.067538', '2024-09-19 08:23:40.067538', 102, 'Voluptatem vel expedita officia ea sit.', 0, 37, 'Tancho', 21758600, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(183, '2024-09-19 08:23:40.126978', '2024-09-19 08:23:40.126978', 28, 'Autem praesentium dolorem quia.', 0, 67, 'Benigoi', 29703300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(184, '2024-09-19 08:23:40.164705', '2024-09-19 08:23:40.164705', 93, 'Laborum illum deleniti nesciunt accusantium iure sunt.', 0, 88, 'Benigoi', 25810800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(185, '2024-09-19 08:23:40.192450', '2024-09-19 08:23:40.192450', 27, 'Facere commodi ipsum ex.', 1, 5, 'Matsuba', 35821300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(186, '2024-09-19 08:23:40.218769', '2024-09-19 08:23:40.218769', 16, 'In saepe quo sed eveniet qui vitae ea.', 1, 36, 'Chagoi', 67725800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(187, '2024-09-19 08:23:40.253173', '2024-09-19 08:23:40.253173', 53, 'Officia libero et magnam fugiat.', 0, 50, 'Shusui', 80122700, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(188, '2024-09-19 08:23:40.293529', '2024-09-19 08:23:40.293529', 130, 'In amet et iste.', 1, 64, 'Doitsu', 89034200, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2);
INSERT INTO `kois` (`id`, `created_at`, `updated_at`, `age`, `description`, `is_display`, `length`, `name`, `base_price`, `sex`, `status`, `thumbnail`, `category_id`, `owner_id`) VALUES
(189, '2024-09-19 08:23:40.355111', '2024-09-19 08:23:40.355111', 110, 'Necessitatibus est ut.', 1, 10, 'Utsurimono', 45721500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(190, '2024-09-19 08:23:40.423556', '2024-09-19 08:23:40.424115', 55, 'Nulla sit qui eum cupiditate officia.', 0, 89, 'Ginrin Sanke', 45307900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 3),
(191, '2024-09-19 08:23:40.464090', '2024-09-19 08:23:40.464090', 69, 'Dolores dicta rem sit ipsam voluptas.', 0, 50, 'Midorigoi', 78874400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(192, '2024-09-19 08:23:40.522173', '2024-09-19 08:23:40.522173', 132, 'Ut eos voluptates eaque consectetur nam.', 1, 68, 'Hikari Muji', 65230700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(193, '2024-09-19 08:23:40.571899', '2024-09-19 08:23:40.571899', 190, 'Ut id molestias eaque iusto veritatis voluptates ducimus.', 1, 3, 'Kikokuryu', 52226800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(194, '2024-09-19 08:23:40.623895', '2024-09-19 08:23:40.623895', 217, 'Sunt doloremque sapiente explicabo sit minima voluptatibus.', 1, 26, 'Tancho', 68803800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(195, '2024-09-19 08:23:40.684664', '2024-09-19 08:23:40.684664', 149, 'Voluptas temporibus et nam non ab.', 1, 24, 'Gin Shiro Utsuri', 49210100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(196, '2024-09-19 08:23:40.734909', '2024-09-19 08:23:40.734909', 134, 'Facere consequatur facere deleniti voluptatem harum.', 1, 65, 'Kanoko Kohaku', 44808800, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(197, '2024-09-19 08:23:40.771998', '2024-09-19 08:23:40.771998', 193, 'Iusto et quo dolorum voluptas dolore dolorum.', 1, 11, 'Yamabuki', 74050500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(198, '2024-09-19 08:23:40.806755', '2024-09-19 08:23:40.806755', 41, 'Velit eum ut earum omnis.', 1, 41, 'Soragoi', 9349280, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(199, '2024-09-19 08:23:40.838770', '2024-09-19 08:23:40.838770', 171, 'Non totam est aut expedita non sit quaerat.', 1, 68, 'Shusui', 61773200, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(200, '2024-09-19 08:23:40.879762', '2024-09-19 08:23:40.879762', 212, 'Aut omnis nulla beatae nobis.', 0, 12, 'Ochiba', 88791300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(201, '2024-09-19 08:23:40.941915', '2024-09-19 08:23:40.941915', 45, 'Accusamus mollitia libero harum et.', 1, 12, 'Kanoko Showa', 67137800, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(202, '2024-09-19 08:23:40.995731', '2024-09-19 08:23:40.995731', 131, 'Doloribus molestias soluta eveniet.', 0, 59, 'Kikokuryu', 22129100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(203, '2024-09-19 08:23:41.028710', '2024-09-19 08:23:41.028710', 21, 'Vel quia atque quia dolores inventore.', 0, 19, 'Kikusui', 4692910, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(204, '2024-09-19 08:23:41.060193', '2024-09-19 08:23:41.060193', 82, 'Enim veritatis sit velit repellendus.', 0, 72, 'Platinum Ogon', 61587400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(205, '2024-09-19 08:23:41.095368', '2024-09-19 08:23:41.095368', 19, 'Placeat quidem maiores odit at laboriosam et rem.', 1, 56, 'Kumonryu', 44989700, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 3),
(206, '2024-09-19 08:23:41.151903', '2024-09-19 08:23:41.151903', 150, 'Amet laboriosam eos.', 0, 71, 'Gin Shiro Utsuri', 2265280, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(207, '2024-09-19 08:23:41.213334', '2024-09-19 08:23:41.213334', 35, 'Molestiae qui aut.', 1, 36, 'Platinum Ogon', 75928700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(208, '2024-09-19 08:23:41.267190', '2024-09-19 08:23:41.267190', 64, 'Qui eos laboriosam vitae velit omnis et.', 1, 53, 'Asagi', 80133000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(209, '2024-09-19 08:23:41.311254', '2024-09-19 08:23:41.311254', 136, 'Nemo perferendis at asperiores qui quia.', 1, 58, 'Aka Matsuba', 24408500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(210, '2024-09-19 08:23:41.346098', '2024-09-19 08:23:41.346098', 73, 'Et omnis cupiditate iure corrupti eos.', 0, 69, 'Bekko', 45904100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(211, '2024-09-19 08:23:41.393356', '2024-09-19 08:23:41.393356', 184, 'Soluta expedita placeat enim amet et.', 1, 51, 'Ginrin Showa', 20571400, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(212, '2024-09-19 08:23:41.441943', '2024-09-19 08:23:41.441943', 124, 'Officiis quia qui quam molestiae.', 1, 40, 'Kanoko Showa', 74451100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(213, '2024-09-19 08:23:41.487721', '2024-09-19 08:23:41.487721', 201, 'Quaerat id inventore modi vitae in temporibus.', 1, 78, 'Doitsu Showa', 25868400, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(214, '2024-09-19 08:23:41.539727', '2024-09-19 08:23:41.539727', 149, 'Itaque explicabo officiis quo aut dolorum.', 1, 15, 'Doitsu Kohaku', 10700000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(215, '2024-09-19 08:23:41.601721', '2024-09-19 08:23:41.601721', 82, 'Quae fugit laudantium.', 1, 30, 'Kanoko Sanke', 80829200, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(216, '2024-09-19 08:23:41.653677', '2024-09-19 08:23:41.653677', 210, 'Delectus repellendus nisi consequatur veritatis eos.', 1, 26, 'Koromo', 80598900, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(217, '2024-09-19 08:23:41.701588', '2024-09-19 08:23:41.701588', 68, 'Rerum expedita delectus et omnis fugit.', 0, 18, 'Kikokuryu', 15883800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(218, '2024-09-19 08:23:41.744651', '2024-09-19 08:23:41.744651', 209, 'Quis voluptates minus dicta velit laborum eaque doloribus.', 1, 20, 'Kin Showa', 27201400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 1),
(219, '2024-09-19 08:23:41.777256', '2024-09-19 08:23:41.777256', 163, 'Itaque eius sit magnam quas consectetur.', 1, 87, 'Kujaku', 48522600, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(220, '2024-09-19 08:23:41.808800', '2024-09-19 08:23:41.808800', 20, 'Odio debitis consequatur qui.', 1, 16, 'Kikokuryu', 77962100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 3),
(221, '2024-09-19 08:23:41.838102', '2024-09-19 08:23:41.838102', 142, 'Vero odio nobis perferendis similique.', 1, 15, 'Doitsu Kujaku', 58908700, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(222, '2024-09-19 08:23:41.867887', '2024-09-19 08:23:41.867887', 138, 'Non minima dolores incidunt libero et dolorem cupiditate.', 0, 26, 'Platinum Ogon', 72172400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(223, '2024-09-19 08:23:41.908517', '2024-09-19 08:23:41.908517', 202, 'Voluptas non odio in ex.', 0, 7, 'Utsurimono', 10062400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(224, '2024-09-19 08:23:42.008060', '2024-09-19 08:23:42.008060', 59, 'Iusto expedita nemo dolor esse sit assumenda ex.', 0, 79, 'Ginrin Kohaku', 54709400, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(225, '2024-09-19 08:23:42.063607', '2024-09-19 08:23:42.063607', 136, 'Sit alias rem deleniti harum fugit qui.', 0, 12, 'Ginrin Chagoi', 83443000, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 1),
(226, '2024-09-19 08:23:42.113278', '2024-09-19 08:23:42.113278', 133, 'Neque libero sed nostrum neque natus sapiente quod.', 1, 16, 'Ai Goromo', 62825500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(227, '2024-09-19 08:23:42.156460', '2024-09-19 08:23:42.156460', 111, 'Corporis ipsa nemo omnis temporibus ea doloribus et.', 1, 60, 'Ginrin Sanke', 13573600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(228, '2024-09-19 08:23:42.197618', '2024-09-19 08:23:42.197618', 9, 'Nemo aut necessitatibus.', 0, 40, 'Hikari Muji', 31933500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(229, '2024-09-19 08:23:42.249386', '2024-09-19 08:23:42.249386', 1, 'Sint reprehenderit voluptas ducimus libero magnam est in.', 1, 43, 'Beni Kikokuryu', 27302800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(230, '2024-09-19 08:23:42.285967', '2024-09-19 08:23:42.285967', 200, 'Officiis saepe exercitationem odio enim.', 0, 13, 'Ginrin Chagoi', 47685300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(231, '2024-09-19 08:23:42.332713', '2024-09-19 08:23:42.332713', 80, 'Illo vel ipsa recusandae rerum aut consequatur officia.', 1, 32, 'Midorigoi', 79406300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(232, '2024-09-19 08:23:42.403461', '2024-09-19 08:23:42.403461', 138, 'Velit velit saepe laudantium corporis aut reprehenderit.', 0, 58, 'Ginrin Kohaku', 80616800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(233, '2024-09-19 08:23:42.471085', '2024-09-19 08:23:42.471085', 217, 'Voluptates qui laboriosam nam dicta.', 0, 60, 'Utsurimono', 1342730, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(234, '2024-09-19 08:23:42.519923', '2024-09-19 08:23:42.519923', 194, 'Quo deleniti itaque quia ipsum laudantium aliquid explicabo.', 1, 64, 'Aka Matsuba', 6465730, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(235, '2024-09-19 08:23:42.560142', '2024-09-19 08:23:42.560142', 193, 'Laborum ut quidem eos.', 1, 27, 'Yamabuki', 35806100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(236, '2024-09-19 08:23:42.621972', '2024-09-19 08:23:42.621972', 78, 'Asperiores at aspernatur corporis odit aliquam debitis.', 0, 12, 'Hariwake', 83650700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(237, '2024-09-19 08:23:42.680914', '2024-09-19 08:23:42.680914', 139, 'Architecto eum et quos dolores.', 1, 36, 'Ginrin Chagoi', 84088200, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(238, '2024-09-19 08:23:42.747133', '2024-09-19 08:23:42.747133', 106, 'Eum reiciendis voluptatem.', 0, 48, 'Kage Shiro Utsuri', 6384670, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(239, '2024-09-19 08:23:42.801995', '2024-09-19 08:23:42.801995', 163, 'Ipsa dolor aut et architecto molestiae qui.', 1, 36, 'Ginrin Showa', 43641200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 1),
(240, '2024-09-19 08:23:42.856534', '2024-09-19 08:23:42.856534', 190, 'Dolorum rerum molestiae autem aut quam vel modi.', 0, 77, 'Ochiba', 63321100, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(241, '2024-09-19 08:23:42.899393', '2024-09-19 08:23:42.899393', 208, 'Dolore et incidunt.', 1, 31, 'Kage Shiro Utsuri', 20696200, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(242, '2024-09-19 08:23:42.943471', '2024-09-19 08:23:42.943471', 164, 'Est non et.', 1, 1, 'Doitsu Sanke', 11568500, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(243, '2024-09-19 08:23:42.988684', '2024-09-19 08:23:42.988684', 39, 'Et accusantium sit eum quas.', 0, 62, 'Asagi', 71415600, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(244, '2024-09-19 08:23:43.058026', '2024-09-19 08:23:43.058026', 102, 'Ullam rem voluptas cum aut modi.', 1, 31, 'Beni Kikokuryu', 60584100, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(245, '2024-09-19 08:23:43.112377', '2024-09-19 08:23:43.112377', 36, 'Omnis libero temporibus sed.', 0, 73, 'Hikari Utsuri', 43011300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 3),
(246, '2024-09-19 08:23:43.206815', '2024-09-19 08:23:43.206815', 147, 'Maiores voluptatem et id voluptas occaecati.', 0, 38, 'Yamabuki', 3129390, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(247, '2024-09-19 08:23:43.258835', '2024-09-19 08:23:43.258835', 111, 'Porro iure aut.', 1, 89, 'Kage Shiro Utsuri', 36373300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 1),
(248, '2024-09-19 08:23:43.316611', '2024-09-19 08:23:43.316611', 5, 'Corporis incidunt et autem.', 1, 33, 'Tancho', 74656800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(249, '2024-09-19 08:23:43.360956', '2024-09-19 08:23:43.360956', 31, 'Nihil autem nemo ut earum velit.', 1, 54, 'Orenji Ogon', 18127100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(250, '2024-09-19 08:23:43.408933', '2024-09-19 08:23:43.408933', 120, 'Quasi maiores eius modi velit quam.', 0, 38, 'Beni Kikokuryu', 13198300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(251, '2024-09-19 08:23:43.465638', '2024-09-19 08:23:43.465638', 121, 'Repudiandae eligendi earum incidunt sapiente voluptates.', 0, 39, 'Kikusui', 30899200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(252, '2024-09-19 08:23:43.511640', '2024-09-19 08:23:43.511640', 44, 'Officiis omnis molestiae veritatis sit veritatis aut sint.', 1, 79, 'Ochiba', 16674500, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(253, '2024-09-19 08:23:43.555073', '2024-09-19 08:23:43.555073', 73, 'Ut sunt et iusto quia adipisci.', 0, 29, 'Kanoko Showa', 27004000, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(254, '2024-09-19 08:23:43.604631', '2024-09-19 08:23:43.604631', 199, 'In in et saepe sunt nemo voluptatibus laboriosam.', 0, 51, 'Yamatonishiki', 6375320, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2),
(255, '2024-09-19 08:23:43.652017', '2024-09-19 08:23:43.652017', 216, 'Ducimus quibusdam consectetur debitis porro velit ut ducimus.', 1, 63, 'Aka Matsuba', 15387600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 2),
(256, '2024-09-19 08:23:43.697035', '2024-09-19 08:23:43.697035', 72, 'Dolores ipsam est et ipsa.', 1, 48, 'Platinum Ogon', 11077800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(257, '2024-09-19 08:23:43.745652', '2024-09-19 08:23:43.745652', 199, 'Reiciendis in aut.', 1, 35, 'Hikari Utsuri', 60146900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(258, '2024-09-19 08:23:43.797396', '2024-09-19 08:23:43.797396', 71, 'Accusantium quo error voluptatem dolor laboriosam voluptatem.', 1, 57, 'Chagoi', 23161400, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(259, '2024-09-19 08:23:43.851496', '2024-09-19 08:23:43.851496', 148, 'Laboriosam et assumenda suscipit.', 1, 83, 'Budo Sanke', 28790700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(260, '2024-09-19 08:23:43.902256', '2024-09-19 08:23:43.902256', 191, 'Harum sit et delectus similique.', 0, 46, 'Kage Shiro Utsuri', 77077800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(261, '2024-09-19 08:23:43.948378', '2024-09-19 08:23:43.948378', 205, 'Omnis laboriosam repellat vel dicta ut recusandae ipsum.', 1, 49, 'Kage Shiro Utsuri', 66964900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(262, '2024-09-19 08:23:43.993227', '2024-09-19 08:23:43.993227', 202, 'Non sunt iusto.', 1, 3, 'Showa', 63963800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 3),
(263, '2024-09-19 08:23:44.040071', '2024-09-19 08:23:44.040071', 121, 'Enim est dolorum sint sed similique enim maxime.', 0, 59, 'Sumi Goromo', 3273730, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(264, '2024-09-19 08:23:44.114498', '2024-09-19 08:23:44.114498', 205, 'Voluptatem aliquam iste omnis.', 1, 74, 'Ginrin Kohaku', 76265600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(265, '2024-09-19 08:23:44.174591', '2024-09-19 08:23:44.174591', 150, 'Rem sit nostrum rem optio id.', 1, 7, 'Sanke', 88221700, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(266, '2024-09-19 08:23:44.222867', '2024-09-19 08:23:44.222867', 158, 'Ut illo ea quaerat aut.', 1, 27, 'Showa', 827950, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3),
(267, '2024-09-19 08:23:44.311549', '2024-09-19 08:23:44.311549', 106, 'Reprehenderit et alias est.', 1, 90, 'Ginrin Sanke', 18564200, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(268, '2024-09-19 08:23:44.380529', '2024-09-19 08:23:44.380529', 125, 'Voluptatum velit et fugiat adipisci officiis at.', 0, 48, 'Platinum Ogon', 65733100, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(269, '2024-09-19 08:23:44.440625', '2024-09-19 08:23:44.440625', 128, 'Ipsam vero et.', 1, 17, 'Shusui', 80009800, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(270, '2024-09-19 08:23:44.526839', '2024-09-19 08:23:44.526839', 3, 'Temporibus pariatur quisquam aliquid neque a eum.', 1, 89, 'Hikari Utsuri', 75749900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(271, '2024-09-19 08:23:44.652946', '2024-09-19 08:23:44.652946', 189, 'Dignissimos praesentium officia.', 0, 3, 'Ki Utsuri', 26348400, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(272, '2024-09-19 08:23:44.729723', '2024-09-19 08:23:44.729723', 222, 'Occaecati quidem eaque rerum sit accusantium enim ab.', 0, 15, 'Kumonryu', 67525900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(273, '2024-09-19 08:23:44.797103', '2024-09-19 08:23:44.797103', 51, 'Maiores nesciunt et laboriosam laborum.', 1, 86, 'Gin Shiro Utsuri', 78088800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3),
(274, '2024-09-19 08:23:44.861948', '2024-09-19 08:23:44.861948', 132, 'Et et enim enim necessitatibus animi qui ipsum.', 1, 68, 'Utsurimono', 37808000, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(275, '2024-09-19 08:23:44.931785', '2024-09-19 08:23:44.931785', 28, 'Architecto molestias quo repudiandae porro autem exercitationem recusandae.', 1, 29, 'Ai Goromo', 54803500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 1),
(276, '2024-09-19 08:23:44.994634', '2024-09-19 08:23:44.994634', 131, 'Id magnam magnam.', 0, 21, 'Beni Kikokuryu', 87650500, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(277, '2024-09-19 08:23:45.043294', '2024-09-19 08:23:45.043294', 219, 'Esse incidunt minus et.', 0, 48, 'Hikari Muji', 9011100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 6, 2),
(278, '2024-09-19 08:23:45.093192', '2024-09-19 08:23:45.093192', 131, 'Dolorem asperiores consequatur aliquam ut consequatur fuga rerum.', 0, 17, 'Ginrin Sanke', 49613300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(279, '2024-09-19 08:23:45.142038', '2024-09-19 08:23:45.142038', 205, 'Ea eos distinctio non doloremque.', 1, 25, 'Kikokuryu', 35394800, 'M', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 1),
(280, '2024-09-19 08:23:45.253858', '2024-09-19 08:23:45.253858', 136, 'Eius esse dicta id.', 1, 30, 'Matsuba', 16325800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 5, 2),
(281, '2024-09-19 08:23:45.530783', '2024-09-19 08:23:45.530783', 25, 'Neque dolorem occaecati ut possimus voluptates.', 1, 10, 'Kumonryu', 67698800, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 4, 3),
(282, '2024-09-19 08:23:45.652202', '2024-09-19 08:23:45.652202', 30, 'Repellendus perferendis ratione facere architecto.', 1, 41, 'Doitsu Kohaku', 59449900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(283, '2024-09-19 08:23:45.720083', '2024-09-19 08:23:45.720083', 40, 'Nemo quas quia ex nulla voluptates qui cupiditate.', 1, 43, 'Ginrin Showa', 70265000, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(284, '2024-09-19 08:23:45.779244', '2024-09-19 08:23:45.779244', 158, 'Dolores et vel fugiat autem.', 1, 61, 'Ai Goromo', 37880500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1),
(285, '2024-09-19 08:23:45.850132', '2024-09-19 08:23:45.850132', 151, 'Earum nulla voluptatem.', 1, 65, 'Kujaku', 69017600, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(286, '2024-09-19 08:23:45.931974', '2024-09-19 08:23:45.931974', 76, 'Dolor aut et ipsum ratione doloremque.', 1, 61, 'Doitsu Showa', 2930100, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(287, '2024-09-19 08:23:45.978285', '2024-09-19 08:23:45.978285', 31, 'Sit provident adipisci sit autem est.', 0, 57, 'Yamabuki', 62712300, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 1),
(288, '2024-09-19 08:23:46.024423', '2024-09-19 08:23:46.024423', 79, 'Quo dignissimos iusto.', 1, 31, 'Kanoko Sanke', 53387900, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(289, '2024-09-19 08:23:46.076496', '2024-09-19 08:23:46.076496', 99, 'Sint architecto voluptas.', 0, 72, 'Hikari Utsuri', 9239950, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(290, '2024-09-19 08:23:46.120793', '2024-09-19 08:23:46.120793', 219, 'Vel qui sed provident corporis id aut.', 0, 7, 'Utsurimono', 35516200, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2),
(291, '2024-09-19 08:23:46.171953', '2024-09-19 08:23:46.171953', 191, 'Blanditiis quo quae reprehenderit ut id veritatis nam.', 0, 44, 'Utsurimono', 20466900, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1),
(292, '2024-09-19 08:23:46.222117', '2024-09-19 08:23:46.222117', 7, 'Officia minima et.', 1, 18, 'Kumonryu', 33268500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 3),
(293, '2024-09-19 08:23:46.282264', '2024-09-19 08:23:46.282264', 170, 'Sed minus delectus autem.', 1, 23, 'Koromo', 80317300, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 1),
(294, '2024-09-19 08:23:46.352242', '2024-09-19 08:23:46.352242', 120, 'Sunt ab quis porro et nam quaerat iusto.', 1, 90, 'Ai Goromo', 8055160, 'F', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 10, 2),
(295, '2024-09-19 08:23:46.417560', '2024-09-19 08:23:46.417560', 173, 'Ex sit fuga consectetur quia ad.', 1, 61, 'Kanoko Showa', 7375450, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 7, 2),
(296, '2024-09-19 08:23:46.474612', '2024-09-19 08:23:46.474612', 177, 'Accusantium eveniet dolores.', 1, 12, 'Kanoko Kohaku', 14416900, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 8, 2),
(297, '2024-09-19 08:23:46.526141', '2024-09-19 08:23:46.526141', 72, 'Id consequatur sed id porro aut impedit ut.', 1, 43, 'Ki Bekko', 20524800, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2),
(298, '2024-09-19 08:23:46.584620', '2024-09-19 08:23:46.584620', 215, 'Quasi et pariatur.', 1, 28, 'Doitsu', 29614500, 'U', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2),
(299, '2024-09-19 08:23:46.646961', '2024-09-19 08:23:46.646961', 53, 'Voluptatem voluptates labore alias id et.', 1, 90, 'Kujaku', 63944300, 'O', NULL, 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 9, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `koi_images`
--

CREATE TABLE `koi_images` (
  `id` bigint NOT NULL,
  `image_url` varchar(300) NOT NULL,
  `video_url` varchar(500) DEFAULT NULL,
  `koi_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `phone_number` varchar(100) NOT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_date` date DEFAULT NULL,
  `shipping_method` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `total_money` float DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_details`
--

CREATE TABLE `order_details` (
  `id` bigint NOT NULL,
  `number_of_products` int NOT NULL,
  `price` float NOT NULL,
  `total_money` float NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `id` bigint NOT NULL,
  `payment_amount` float NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `order_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `refunds`
--

CREATE TABLE `refunds` (
  `id` bigint NOT NULL,
  `refund_amount` float NOT NULL,
  `refund_reason` varchar(255) NOT NULL,
  `refund_status` varchar(255) NOT NULL,
  `payment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'member'),
(2, 'staff'),
(3, 'breeder'),
(4, 'manager');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `social_accounts`
--

CREATE TABLE `social_accounts` (
  `id` bigint NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider_name` enum('FACEBOOK','GOOGLE') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tokens`
--

CREATE TABLE `tokens` (
  `id` bigint NOT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  `expired` bit(1) NOT NULL,
  `is_mobile` tinyint(1) DEFAULT NULL,
  `refresh_expiration_date` datetime(6) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` varchar(50) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tracking_status`
--

CREATE TABLE `tracking_status` (
  `id` bigint NOT NULL,
  `name` tinyint DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `google_account_id` int DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` enum('ACTIVE','BANNED','INACTIVE','UNVERIFIED','VERIFIED') DEFAULT NULL,
  `role_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `created_at`, `updated_at`, `address`, `avatar_url`, `date_of_birth`, `email`, `fullname`, `google_account_id`, `password`, `phone_number`, `status`, `role_id`) VALUES
(1, '2024-09-19 08:22:17.154596', '2024-09-19 08:22:17.154596', 'Quang Binh', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'son@gmail.com', 'Son Duong Con', 0, '$2a$10$.PwzwCFU9k30premxTqsQekPkUkiay1RxHI36onQYYIIga1tpcKGa', NULL, NULL, 1),
(2, '2024-09-19 08:22:59.540506', '2024-09-19 08:22:59.540506', 'Viet Nam', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'hoangclw@gmail.com', 'Hoang Luu Cao', 0, '$2a$10$l2sKuN7a.ENBwqW7223T/O172b5dsncuRrq6wLfIQHfjU336xmnrC', NULL, NULL, 1),
(3, '2024-09-19 08:23:13.888461', '2024-09-19 08:23:13.888461', 'Viet Nam', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'duong@gmail.com', 'Duong Nguyen', 0, '$2a$10$12VlVeXdT2s01ftUFfstp.Ghs4qRkDjJp6Zu5H0iuQVrFUiBzVEsi', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `wallets`
--

CREATE TABLE `wallets` (
  `id` bigint NOT NULL,
  `balance` float NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `auctions`
--
ALTER TABLE `auctions`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `auction_kois`
--
ALTER TABLE `auction_kois`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp4gi0darfksbicctbsh6uc3ou` (`auction_id`),
  ADD KEY `FKhiwdnap3fod80aliuymaklkit` (`koi_id`);

--
-- Chỉ mục cho bảng `auction_kois_details`
--
ALTER TABLE `auction_kois_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjo0888rrp37jokibr875ftqcx` (`auction_koi_id`),
  ADD KEY `FKkaaka2inkbtxrlqno2syf1x4s` (`user_id`);

--
-- Chỉ mục cho bảng `auction_participants`
--
ALTER TABLE `auction_participants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoueiq28cuki5mhu0i7udc8q9b` (`auction_id`),
  ADD KEY `FK9jnbjd50w43dg3vtc4b1th1fw` (`user_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `kois`
--
ALTER TABLE `kois`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKabkupc6s0m4jrqoh0e0a9ri1f` (`category_id`),
  ADD KEY `FKcx45ppp71l5dlu25f9kd0qpa` (`owner_id`);

--
-- Chỉ mục cho bảng `koi_images`
--
ALTER TABLE `koi_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj6nh95hilrad5roxpf7hy6v7w` (`koi_id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Chỉ mục cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe9d6wfc3gtdvbc4aoy3lge1o0` (`product_id`),
  ADD KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK81gagumt0r8y3rmudcgpbk42l` (`order_id`);

--
-- Chỉ mục cho bảng `refunds`
--
ALTER TABLE `refunds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpt9ic0j1y6xwlej99wnynvnpy` (`payment_id`),
  ADD KEY `FKrtxief4co3k1lfklj7n05m8mo` (`user_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6rmxxiton5yuvu7ph2hcq2xn7` (`user_id`);

--
-- Chỉ mục cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`);

--
-- Chỉ mục cho bảng `tracking_status`
--
ALTER TABLE `tracking_status`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`);

--
-- Chỉ mục cho bảng `wallets`
--
ALTER TABLE `wallets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKsswfdl9fq40xlkove1y5kc7kv` (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `auctions`
--
ALTER TABLE `auctions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `auction_kois`
--
ALTER TABLE `auction_kois`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `auction_kois_details`
--
ALTER TABLE `auction_kois_details`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `auction_participants`
--
ALTER TABLE `auction_participants`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `kois`
--
ALTER TABLE `kois`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=300;

--
-- AUTO_INCREMENT cho bảng `koi_images`
--
ALTER TABLE `koi_images`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `refunds`
--
ALTER TABLE `refunds`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tracking_status`
--
ALTER TABLE `tracking_status`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `wallets`
--
ALTER TABLE `wallets`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `auction_kois`
--
ALTER TABLE `auction_kois`
  ADD CONSTRAINT `FKhiwdnap3fod80aliuymaklkit` FOREIGN KEY (`koi_id`) REFERENCES `kois` (`id`),
  ADD CONSTRAINT `FKp4gi0darfksbicctbsh6uc3ou` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`);

--
-- Các ràng buộc cho bảng `auction_kois_details`
--
ALTER TABLE `auction_kois_details`
  ADD CONSTRAINT `FKjo0888rrp37jokibr875ftqcx` FOREIGN KEY (`auction_koi_id`) REFERENCES `auction_kois` (`id`),
  ADD CONSTRAINT `FKkaaka2inkbtxrlqno2syf1x4s` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `auction_participants`
--
ALTER TABLE `auction_participants`
  ADD CONSTRAINT `FK9jnbjd50w43dg3vtc4b1th1fw` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKoueiq28cuki5mhu0i7udc8q9b` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`);

--
-- Các ràng buộc cho bảng `kois`
--
ALTER TABLE `kois`
  ADD CONSTRAINT `FKabkupc6s0m4jrqoh0e0a9ri1f` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKcx45ppp71l5dlu25f9kd0qpa` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `koi_images`
--
ALTER TABLE `koi_images`
  ADD CONSTRAINT `FKj6nh95hilrad5roxpf7hy6v7w` FOREIGN KEY (`koi_id`) REFERENCES `kois` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `FKe9d6wfc3gtdvbc4aoy3lge1o0` FOREIGN KEY (`product_id`) REFERENCES `kois` (`id`),
  ADD CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `refunds`
--
ALTER TABLE `refunds`
  ADD CONSTRAINT `FKpt9ic0j1y6xwlej99wnynvnpy` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`id`),
  ADD CONSTRAINT `FKrtxief4co3k1lfklj7n05m8mo` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD CONSTRAINT `FK6rmxxiton5yuvu7ph2hcq2xn7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Các ràng buộc cho bảng `wallets`
--
ALTER TABLE `wallets`
  ADD CONSTRAINT `FKc1foyisidw7wqqrkamafuwn4e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
