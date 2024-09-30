-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql_db
-- Thời gian đã tạo: Th9 13, 2024 lúc 01:42 AM
-- Phiên bản máy phục vụ: 9.0.1
-- Phiên bản PHP: 8.2.23

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
-- Cấu trúc bảng cho bảng `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `script` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(2, '1', 'alter some tables', 'SQL', 'V1__alter_some_tables.sql', 670188877, 'root', '2023-12-01 10:05:58', 605, 1),
(3, '2', 'change tokens', 'SQL', 'V2__change_tokens.sql', 1468721430, 'root', '2023-12-01 10:05:58', 27, 1),
(4, '3', 'refresh tokens', 'SQL', 'V3__refresh_tokens.sql', 1847335528, 'root', '2023-12-03 04:50:25', 36, 1);

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
  `is_sold` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  `name` varchar(350) NOT NULL,
  `price` float DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `thumbnail` varchar(300) DEFAULT NULL,
  `tracking_status` tinyint DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ;

--
-- Đang đổ dữ liệu cho bảng `kois`
--

INSERT INTO `kois` (`id`, `created_at`, `updated_at`, `age`, `description`, `is_display`, `is_sold`, `length`, `name`, `price`, `sex`, `thumbnail`, `tracking_status`, `category_id`, `user_id`) VALUES
(1, '2024-09-13 01:28:07.081732', '2024-09-13 01:28:07.081732', 156, 'Magni ipsum sunt vero.', 1, 0, 15, 'Sumi Goromo', 89148900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 3),
(2, '2024-09-13 01:28:07.174674', '2024-09-13 01:28:07.174674', 124, 'Voluptate nihil a consequatur tempore dignissimos.', 1, 0, 50, 'Orenji Ogon', 62107600, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 10, 2),
(3, '2024-09-13 01:28:07.213898', '2024-09-13 01:28:07.213898', 78, 'Ea quis labore voluptates sed.', 1, 0, 78, 'Doitsu', 70503900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(4, '2024-09-13 01:28:07.247330', '2024-09-13 01:28:07.247330', 185, 'Unde architecto et molestias sit non culpa nam.', 1, 1, 77, 'Doitsu', 87877100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 3),
(5, '2024-09-13 01:28:07.291693', '2024-09-13 01:28:07.291693', 48, 'Ea assumenda nam nostrum.', 1, 1, 5, 'Sumi Goromo', 42152100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 3),
(6, '2024-09-13 01:28:07.338854', '2024-09-13 01:28:07.338854', 225, 'Repellat perspiciatis alias iure qui nisi voluptatem dicta.', 0, 0, 22, 'Hikari Utsuri', 25846700, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2, 2),
(7, '2024-09-13 01:28:07.386762', '2024-09-13 01:28:07.386762', 25, 'Voluptatum in dolorem beatae nam sit veniam expedita.', 0, 1, 82, 'Goshiki', 21980300, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(8, '2024-09-13 01:28:07.427367', '2024-09-13 01:28:07.427367', 63, 'Vero dignissimos et est perferendis dolore saepe minima.', 1, 0, 82, 'Kumonryu', 46506000, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 1),
(9, '2024-09-13 01:28:07.456703', '2024-09-13 01:28:07.456703', 169, 'Ut porro suscipit modi laboriosam quasi quisquam.', 0, 0, 67, 'Kujaku', 24035600, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 6, 2),
(10, '2024-09-13 01:28:07.488097', '2024-09-13 01:28:07.488097', 201, 'Placeat ut a voluptatem unde asperiores.', 0, 1, 72, 'Midorigoi', 79027200, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 5, 1),
(11, '2024-09-13 01:28:07.521162', '2024-09-13 01:28:07.521162', 26, 'Repellendus suscipit fuga dicta odit.', 1, 1, 43, 'Doitsu Showa', 21073600, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3, 1),
(12, '2024-09-13 01:28:07.550419', '2024-09-13 01:28:07.550419', 111, 'Porro magnam aut sit est.', 0, 0, 84, 'Utsurimono', 59289100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 3),
(13, '2024-09-13 01:28:07.581414', '2024-09-13 01:28:07.581414', 25, 'Labore occaecati provident.', 1, 0, 55, 'Doitsu Kohaku', 20585600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 1),
(14, '2024-09-13 01:28:07.610614', '2024-09-13 01:28:07.610614', 157, 'Explicabo officiis consequatur est quam quidem accusantium.', 1, 1, 5, 'Ai Goromo', 59967300, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 1),
(15, '2024-09-13 01:28:07.639088', '2024-09-13 01:28:07.639088', 10, 'Voluptatem distinctio aperiam.', 0, 1, 19, 'Kanoko Showa', 7456670, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(16, '2024-09-13 01:28:07.674230', '2024-09-13 01:28:07.674230', 172, 'Earum aut totam suscipit sed culpa minus.', 1, 1, 12, 'Kin Ki Utsuri', 35717100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 1),
(17, '2024-09-13 01:28:07.712256', '2024-09-13 01:28:07.712256', 139, 'Fugit aut voluptatem est officiis aut.', 0, 0, 67, 'Gin Shiro Utsuri', 69597900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 3),
(18, '2024-09-13 01:28:07.744270', '2024-09-13 01:28:07.744270', 161, 'Delectus ea incidunt consequatur.', 1, 0, 27, 'Ginrin Chagoi', 71643600, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 1),
(19, '2024-09-13 01:28:07.774011', '2024-09-13 01:28:07.774011', 108, 'Numquam exercitationem aut aut sequi animi labore.', 1, 0, 71, 'Kin Showa', 87039500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 10, 2),
(20, '2024-09-13 01:28:07.804737', '2024-09-13 01:28:07.804737', 72, 'In ut quos et quo.', 1, 0, 65, 'Benigoi', 47092400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 3),
(21, '2024-09-13 01:28:07.836116', '2024-09-13 01:28:07.836116', 135, 'Doloremque veniam ea velit dolor natus beatae.', 1, 0, 51, 'Hariwake', 999976, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 1),
(22, '2024-09-13 01:28:07.863696', '2024-09-13 01:28:07.863696', 35, 'Similique consequatur tenetur non.', 1, 1, 22, 'Ai Goromo', 48892800, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 1),
(23, '2024-09-13 01:28:07.891107', '2024-09-13 01:28:07.891107', 160, 'Consectetur enim libero earum porro qui.', 0, 1, 60, 'Chagoi', 4529110, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(24, '2024-09-13 01:28:07.920108', '2024-09-13 01:28:07.920108', 56, 'Corrupti nesciunt ut dolorem.', 1, 0, 48, 'Doitsu Sanke', 11490100, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(25, '2024-09-13 01:28:07.946855', '2024-09-13 01:28:07.946855', 125, 'Vitae voluptatum aut adipisci.', 0, 0, 24, 'Kanoko Kohaku', 79970300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(26, '2024-09-13 01:28:07.974249', '2024-09-13 01:28:07.974249', 2, 'Beatae rerum corporis necessitatibus eos.', 1, 1, 71, 'Koromo', 72401500, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 1),
(27, '2024-09-13 01:28:08.005689', '2024-09-13 01:28:08.005689', 208, 'Qui hic assumenda harum aut debitis.', 0, 0, 7, 'Yamabuki', 51476900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 2),
(28, '2024-09-13 01:28:08.032001', '2024-09-13 01:28:08.032001', 195, 'Officiis qui voluptate.', 0, 1, 25, 'Ai Goromo', 21483100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 2),
(29, '2024-09-13 01:28:08.058593', '2024-09-13 01:28:08.058593', 11, 'Minus iusto cum cumque esse porro asperiores omnis.', 1, 1, 1, 'Hariwake', 48697500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(30, '2024-09-13 01:28:08.088832', '2024-09-13 01:28:08.088832', 130, 'Quam quia eum qui fugit tempora deserunt.', 1, 1, 13, 'Benigoi', 25871500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 1),
(31, '2024-09-13 01:28:08.134780', '2024-09-13 01:28:08.134780', 153, 'A doloremque sunt quidem enim autem officiis ea.', 0, 0, 81, 'Doitsu Showa', 29714900, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(32, '2024-09-13 01:28:08.177175', '2024-09-13 01:28:08.177175', 15, 'Deleniti laborum officiis voluptas sed repellendus.', 0, 1, 52, 'Ginrin Showa', 64770500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 1),
(33, '2024-09-13 01:28:08.217151', '2024-09-13 01:28:08.217151', 8, 'Molestiae saepe reprehenderit.', 1, 0, 13, 'Doitsu', 44376800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 3),
(34, '2024-09-13 01:28:08.260285', '2024-09-13 01:28:08.260285', 206, 'Ut ab pariatur saepe.', 0, 1, 3, 'Hikari Utsuri', 87044900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3, 1),
(35, '2024-09-13 01:28:08.302407', '2024-09-13 01:28:08.302407', 46, 'Id aut et in eaque temporibus natus nostrum.', 1, 1, 6, 'Showa', 27258200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 2),
(36, '2024-09-13 01:28:08.328966', '2024-09-13 01:28:08.328966', 124, 'Id sint nesciunt vitae enim.', 1, 0, 35, 'Bekko', 4777450, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 3),
(37, '2024-09-13 01:28:08.360203', '2024-09-13 01:28:08.360203', 97, 'Ratione est nisi assumenda.', 1, 1, 86, 'Kanoko Kohaku', 21301700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(38, '2024-09-13 01:28:08.394843', '2024-09-13 01:28:08.394843', 68, 'Rerum et quos est nostrum iste.', 1, 0, 33, 'Kumonryu', 65248000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(39, '2024-09-13 01:28:08.426203', '2024-09-13 01:28:08.426203', 88, 'Quo consequatur eaque cumque quae.', 0, 1, 37, 'Ochiba', 11060700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(40, '2024-09-13 01:28:08.456826', '2024-09-13 01:28:08.456826', 169, 'Deserunt aut cupiditate.', 1, 1, 64, 'Kikusui', 33512300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 2),
(41, '2024-09-13 01:28:08.486259', '2024-09-13 01:28:08.486259', 191, 'Expedita recusandae quia quia laborum et.', 1, 1, 39, 'Ai Goromo', 28938900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(42, '2024-09-13 01:28:08.511932', '2024-09-13 01:28:08.511932', 4, 'Totam eveniet numquam minus debitis necessitatibus officia cumque.', 1, 0, 3, 'Doitsu Sanke', 24675800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(43, '2024-09-13 01:28:08.539891', '2024-09-13 01:28:08.539891', 141, 'Veritatis dicta laboriosam fugiat ex sed voluptas.', 1, 1, 21, 'Asagi', 80334200, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 1),
(44, '2024-09-13 01:28:08.566124', '2024-09-13 01:28:08.566148', 59, 'Aperiam qui dolorem libero at occaecati delectus dolor.', 1, 0, 73, 'Sanke', 16158900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2, 2),
(45, '2024-09-13 01:28:08.594780', '2024-09-13 01:28:08.594780', 48, 'Unde est quibusdam.', 0, 0, 17, 'Kohaku', 19549200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 2),
(46, '2024-09-13 01:28:08.626146', '2024-09-13 01:28:08.626146', 49, 'Voluptatibus porro iure beatae nisi quod illo et.', 0, 1, 20, 'Orenji Ogon', 57958700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 1),
(47, '2024-09-13 01:28:08.658541', '2024-09-13 01:28:08.658541', 38, 'Aut natus ab aut odio praesentium et.', 1, 1, 48, 'Ginrin Kohaku', 29341200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 1),
(48, '2024-09-13 01:28:08.694231', '2024-09-13 01:28:08.694231', 162, 'Dolorem dolore eveniet repudiandae tenetur laudantium tenetur.', 1, 0, 85, 'Ginrin Sanke', 52586300, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 1),
(49, '2024-09-13 01:28:08.724118', '2024-09-13 01:28:08.724118', 74, 'Fuga et quae aut vitae et facere.', 1, 1, 12, 'Tancho', 45600400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 3),
(50, '2024-09-13 01:28:08.748494', '2024-09-13 01:28:08.748494', 80, 'Aliquam illo eligendi error dolorum quidem dicta illo.', 1, 1, 68, 'Doitsu', 47105200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 1),
(51, '2024-09-13 01:28:08.780857', '2024-09-13 01:28:08.780857', 146, 'Voluptatibus quos in error.', 1, 0, 67, 'Asagi', 87135300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 1),
(52, '2024-09-13 01:28:08.827986', '2024-09-13 01:28:08.827986', 217, 'Ut quis perferendis laborum.', 0, 1, 86, 'Shusui', 1210960, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1, 1),
(53, '2024-09-13 01:28:08.863998', '2024-09-13 01:28:08.863998', 20, 'Incidunt enim nam assumenda quod fuga nostrum velit.', 1, 0, 59, 'Platinum Ogon', 22417900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 2),
(54, '2024-09-13 01:28:08.899727', '2024-09-13 01:28:08.899727', 103, 'Aspernatur in voluptatem culpa ea.', 1, 1, 31, 'Doitsu Sanke', 21755100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(55, '2024-09-13 01:28:08.930872', '2024-09-13 01:28:08.930872', 74, 'Consequuntur aut temporibus et.', 1, 0, 65, 'Doitsu', 26413400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3, 1),
(56, '2024-09-13 01:28:08.961945', '2024-09-13 01:28:08.961945', 211, 'Debitis vitae non.', 0, 1, 51, 'Hariwake', 11185100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2, 1),
(57, '2024-09-13 01:28:09.006182', '2024-09-13 01:28:09.006182', 208, 'Amet atque magni adipisci fuga eum quia.', 1, 1, 33, 'Doitsu', 76914100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 2),
(58, '2024-09-13 01:28:09.042011', '2024-09-13 01:28:09.042011', 223, 'Asperiores minus laudantium mollitia.', 0, 0, 8, 'Kohaku', 78518300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1, 2),
(59, '2024-09-13 01:28:09.075479', '2024-09-13 01:28:09.075479', 137, 'Omnis qui odit neque accusantium nihil dolore adipisci.', 0, 0, 81, 'Midorigoi', 88473600, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 1),
(60, '2024-09-13 01:28:09.273854', '2024-09-13 01:28:09.273854', 150, 'Quae sunt omnis debitis earum.', 1, 1, 83, 'Orenji Ogon', 9942380, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 1),
(61, '2024-09-13 01:28:09.299477', '2024-09-13 01:28:09.299477', 129, 'Non eveniet ratione quasi.', 1, 1, 23, 'Ginrin Sanke', 66439800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 2),
(62, '2024-09-13 01:28:09.325287', '2024-09-13 01:28:09.325287', 74, 'Sit ea recusandae voluptate enim ipsa architecto et.', 0, 1, 68, 'Kanoko Kohaku', 69554200, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 3),
(63, '2024-09-13 01:28:09.350419', '2024-09-13 01:28:09.350419', 222, 'Qui qui fugit neque dolorem quibusdam officiis.', 1, 1, 69, 'Orenji Ogon', 16726300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 2),
(64, '2024-09-13 01:28:09.379679', '2024-09-13 01:28:09.379679', 216, 'Quidem aut aliquam.', 1, 1, 74, 'Platinum Ogon', 49295500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(65, '2024-09-13 01:28:09.408074', '2024-09-13 01:28:09.408074', 127, 'Vel nesciunt ut.', 1, 1, 60, 'Sumi Goromo', 46105000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 1),
(66, '2024-09-13 01:28:09.430892', '2024-09-13 01:28:09.430892', 24, 'Sed tenetur et quisquam facere rem.', 1, 0, 66, 'Yamatonishiki', 25710300, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 3),
(67, '2024-09-13 01:28:09.463836', '2024-09-13 01:28:09.463836', 93, 'Maiores et quia natus.', 1, 1, 69, 'Matsuba', 47186400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 1),
(68, '2024-09-13 01:28:09.488776', '2024-09-13 01:28:09.488776', 121, 'Voluptatibus consectetur ut ad iusto.', 1, 1, 12, 'Ochiba', 11531200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 2),
(69, '2024-09-13 01:28:09.515459', '2024-09-13 01:28:09.515459', 22, 'Natus corrupti sunt voluptas iusto ullam.', 1, 1, 83, 'Yamatonishiki', 6060190, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(70, '2024-09-13 01:28:09.551527', '2024-09-13 01:28:09.551527', 40, 'Itaque eius libero culpa alias fuga in qui.', 0, 1, 89, 'Matsuba', 52536800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 2),
(71, '2024-09-13 01:28:09.608205', '2024-09-13 01:28:09.608205', 208, 'Laudantium quia exercitationem deleniti accusantium quos.', 0, 1, 39, 'Ginrin Kohaku', 76752200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(72, '2024-09-13 01:28:09.640404', '2024-09-13 01:28:09.640404', 87, 'Distinctio cum quia neque sit.', 1, 0, 22, 'Utsurimono', 55588300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 5, 2),
(73, '2024-09-13 01:28:09.672248', '2024-09-13 01:28:09.672248', 122, 'Et unde nihil tempora beatae totam.', 1, 0, 90, 'Doitsu Sanke', 84799900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 1),
(74, '2024-09-13 01:28:09.710246', '2024-09-13 01:28:09.710246', 21, 'Omnis dolor ducimus ipsa corporis.', 1, 0, 4, 'Yamabuki', 72661000, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 2),
(75, '2024-09-13 01:28:09.763762', '2024-09-13 01:28:09.763762', 61, 'Qui perspiciatis unde reiciendis autem ad rerum.', 0, 1, 90, 'Bekko', 45180100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 3),
(76, '2024-09-13 01:28:09.798036', '2024-09-13 01:28:09.798036', 35, 'Voluptate at autem dolor deserunt.', 0, 1, 27, 'Kumonryu', 67805900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 1),
(77, '2024-09-13 01:28:09.831264', '2024-09-13 01:28:09.831264', 199, 'In sint incidunt tempora rem vel.', 1, 1, 69, 'Kin Ki Utsuri', 31022000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 2),
(78, '2024-09-13 01:28:09.858963', '2024-09-13 01:28:09.858963', 61, 'Voluptas exercitationem enim sapiente.', 1, 0, 5, 'Kikokuryu', 28417700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(79, '2024-09-13 01:28:09.886439', '2024-09-13 01:28:09.886439', 17, 'Soluta ut enim quo minus dicta natus.', 0, 0, 59, 'Ai Goromo', 30782200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(80, '2024-09-13 01:28:09.917891', '2024-09-13 01:28:09.917891', 181, 'Aut accusantium veritatis ut.', 1, 1, 41, 'Bekko', 35468400, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 6, 1),
(81, '2024-09-13 01:28:09.949688', '2024-09-13 01:28:09.949688', 215, 'Dolor autem ratione explicabo mollitia ut.', 0, 1, 27, 'Kanoko Sanke', 35099100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 3),
(82, '2024-09-13 01:28:09.984182', '2024-09-13 01:28:09.984182', 133, 'Vel molestiae esse modi voluptas quibusdam dignissimos.', 0, 1, 65, 'Kikusui', 31109000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(83, '2024-09-13 01:28:10.014076', '2024-09-13 01:28:10.014076', 149, 'Eligendi et deserunt magni est quod.', 1, 0, 65, 'Budo Sanke', 25532000, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(84, '2024-09-13 01:28:10.038015', '2024-09-13 01:28:10.038015', 177, 'Voluptas corporis aut ut.', 0, 1, 58, 'Doitsu Kohaku', 55233400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(85, '2024-09-13 01:28:10.071662', '2024-09-13 01:28:10.071705', 73, 'Qui sint soluta.', 0, 0, 27, 'Gin Shiro Utsuri', 20110200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 2),
(86, '2024-09-13 01:28:10.109488', '2024-09-13 01:28:10.109488', 49, 'Non placeat aliquam consequatur minus non et fugiat.', 1, 0, 15, 'Benigoi', 43690300, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 1),
(87, '2024-09-13 01:28:10.139298', '2024-09-13 01:28:10.139298', 179, 'Et qui fugit reprehenderit occaecati.', 0, 0, 75, 'Shusui', 86869900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 1),
(88, '2024-09-13 01:28:10.167924', '2024-09-13 01:28:10.167924', 51, 'Omnis veniam recusandae dignissimos consequatur repudiandae hic reiciendis.', 0, 1, 8, 'Kikusui', 66737800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(89, '2024-09-13 01:28:10.195400', '2024-09-13 01:28:10.195400', 95, 'Sunt distinctio porro in vel iusto sed.', 1, 1, 36, 'Tancho', 77206200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(90, '2024-09-13 01:28:10.222449', '2024-09-13 01:28:10.222449', 44, 'Fugiat eos voluptatum.', 1, 1, 86, 'Matsuba', 73992200, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 2),
(91, '2024-09-13 01:28:10.248865', '2024-09-13 01:28:10.248865', 58, 'Et ipsam distinctio.', 1, 0, 39, 'Yamatonishiki', 32155600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 2),
(92, '2024-09-13 01:28:10.273000', '2024-09-13 01:28:10.273000', 83, 'Quam ut nostrum ad magni ipsa voluptate reiciendis.', 1, 1, 20, 'Matsuba', 72336600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2, 3),
(93, '2024-09-13 01:28:10.299788', '2024-09-13 01:28:10.299788', 46, 'Recusandae ut et architecto molestiae explicabo quasi molestiae.', 1, 1, 70, 'Soragoi', 73094800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(94, '2024-09-13 01:28:10.326770', '2024-09-13 01:28:10.326770', 30, 'Et laborum incidunt amet tenetur reprehenderit.', 0, 0, 10, 'Bekko', 33942300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 1),
(95, '2024-09-13 01:28:10.353465', '2024-09-13 01:28:10.353465', 123, 'Quaerat sunt magni odio cupiditate.', 1, 1, 14, 'Aka Matsuba', 15364700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 2),
(96, '2024-09-13 01:28:10.377197', '2024-09-13 01:28:10.377197', 162, 'Nisi dolor eum rerum.', 0, 0, 47, 'Tancho', 62076100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(97, '2024-09-13 01:28:10.402521', '2024-09-13 01:28:10.402521', 119, 'Et et quisquam.', 0, 0, 51, 'Ochiba', 51129500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(98, '2024-09-13 01:28:10.440009', '2024-09-13 01:28:10.440009', 43, 'Veritatis repellendus assumenda voluptatem illo aut minima.', 1, 0, 62, 'Beni Kikokuryu', 39688400, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(99, '2024-09-13 01:28:10.471219', '2024-09-13 01:28:10.471219', 155, 'Debitis dolor nisi.', 1, 0, 27, 'Ai Goromo', 14448800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 6, 2),
(100, '2024-09-13 01:28:10.506822', '2024-09-13 01:28:10.506822', 50, 'Officia quam corrupti sed optio voluptatem nam est.', 1, 0, 5, 'Ochiba', 63446500, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3, 2),
(101, '2024-09-13 01:28:10.558282', '2024-09-13 01:28:10.558282', 35, 'Itaque pariatur eaque unde.', 1, 1, 90, 'Soragoi', 40206400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3, 3),
(102, '2024-09-13 01:28:10.597096', '2024-09-13 01:28:10.597096', 179, 'Voluptatem architecto velit.', 1, 0, 70, 'Aka Matsuba', 12401500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(103, '2024-09-13 01:28:10.628158', '2024-09-13 01:28:10.628158', 30, 'Nihil sapiente laboriosam fugiat magni repudiandae.', 1, 1, 10, 'Benigoi', 54653000, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 1),
(104, '2024-09-13 01:28:10.657286', '2024-09-13 01:28:10.657286', 166, 'Libero ea est consectetur atque.', 1, 1, 33, 'Doitsu Kohaku', 67325800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(105, '2024-09-13 01:28:10.686083', '2024-09-13 01:28:10.686083', 145, 'Est sit mollitia accusantium.', 0, 1, 52, 'Asagi', 19614000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(106, '2024-09-13 01:28:10.716949', '2024-09-13 01:28:10.716949', 89, 'Cupiditate exercitationem repudiandae necessitatibus aut.', 1, 0, 46, 'Ai Goromo', 29841200, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 2),
(107, '2024-09-13 01:28:10.752087', '2024-09-13 01:28:10.752087', 223, 'Tempore et dolor a.', 1, 1, 85, 'Kin Ki Utsuri', 56178300, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 1),
(108, '2024-09-13 01:28:10.781855', '2024-09-13 01:28:10.781855', 82, 'Sequi libero nesciunt velit dolor sequi culpa culpa.', 0, 1, 22, 'Yamatonishiki', 38115900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 3),
(109, '2024-09-13 01:28:10.807169', '2024-09-13 01:28:10.807169', 105, 'Velit minima natus voluptate dignissimos rerum quas maiores.', 0, 1, 39, 'Doitsu Sanke', 55829500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 2),
(110, '2024-09-13 01:28:10.832681', '2024-09-13 01:28:10.832681', 31, 'Aut odit deserunt ut harum ab nesciunt ut.', 0, 1, 24, 'Kanoko Sanke', 68839900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 3),
(111, '2024-09-13 01:28:10.859942', '2024-09-13 01:28:10.859942', 210, 'Enim quibusdam ratione repellat facilis et sit ratione.', 1, 1, 65, 'Hikari Utsuri', 5042300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(112, '2024-09-13 01:28:10.890139', '2024-09-13 01:28:10.890139', 51, 'Laboriosam suscipit est.', 0, 1, 59, 'Tancho', 31013100, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(113, '2024-09-13 01:28:10.915831', '2024-09-13 01:28:10.915831', 15, 'Consequatur voluptatem consequatur sit voluptas eum corrupti.', 1, 0, 73, 'Sumi Goromo', 42322900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(114, '2024-09-13 01:28:10.939893', '2024-09-13 01:28:10.939893', 109, 'Numquam nulla dicta consectetur ex.', 1, 1, 49, 'Yamabuki', 86330900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(115, '2024-09-13 01:28:10.967618', '2024-09-13 01:28:10.967618', 110, 'Numquam fugiat id.', 0, 0, 89, 'Hikari Utsuri', 21808900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 1),
(116, '2024-09-13 01:28:10.991709', '2024-09-13 01:28:10.991709', 186, 'Alias autem et illo.', 0, 0, 41, 'Ai Goromo', 44876000, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(117, '2024-09-13 01:28:11.015928', '2024-09-13 01:28:11.015928', 68, 'Amet vitae eaque natus rerum eligendi aut nam.', 1, 0, 66, 'Matsuba', 87764900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 2),
(118, '2024-09-13 01:28:11.038428', '2024-09-13 01:28:11.038428', 134, 'Molestias corrupti corrupti doloremque aperiam.', 0, 1, 75, 'Ki Utsuri', 49535200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3, 2),
(119, '2024-09-13 01:28:11.060029', '2024-09-13 01:28:11.060029', 225, 'Animi suscipit accusantium earum.', 1, 1, 67, 'Hariwake', 73842100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 2),
(120, '2024-09-13 01:28:11.083748', '2024-09-13 01:28:11.083748', 71, 'Officia rerum officia eligendi quia reiciendis.', 1, 1, 48, 'Matsuba', 4885610, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 3),
(121, '2024-09-13 01:28:11.106905', '2024-09-13 01:28:11.106905', 203, 'Voluptas modi est sint illum odio tempora vel.', 1, 1, 2, 'Beni Kikokuryu', 12256700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(122, '2024-09-13 01:28:11.135662', '2024-09-13 01:28:11.135662', 209, 'A aut deserunt.', 1, 1, 44, 'Matsuba', 64666400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(123, '2024-09-13 01:28:11.162303', '2024-09-13 01:28:11.162303', 101, 'Ipsam voluptates est suscipit aperiam.', 0, 1, 44, 'Ki Bekko', 3677990, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(124, '2024-09-13 01:28:11.191027', '2024-09-13 01:28:11.191027', 31, 'Optio cum dolor incidunt ea.', 1, 1, 44, 'Aka Matsuba', 28284000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 2),
(125, '2024-09-13 01:28:11.219297', '2024-09-13 01:28:11.219297', 136, 'Odio repudiandae quas laudantium omnis inventore.', 1, 1, 33, 'Ginrin Showa', 27480500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 2),
(126, '2024-09-13 01:28:11.243309', '2024-09-13 01:28:11.243309', 164, 'Libero consectetur hic optio vero.', 0, 1, 16, 'Shusui', 77669900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 2),
(127, '2024-09-13 01:28:11.267746', '2024-09-13 01:28:11.267746', 105, 'Vero et quas molestias iste quam quis.', 0, 1, 3, 'Sanke', 10545100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 1),
(128, '2024-09-13 01:28:11.295801', '2024-09-13 01:28:11.295801', 205, 'Ut mollitia iste dolorum tenetur.', 1, 1, 29, 'Shusui', 33105200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(129, '2024-09-13 01:28:11.324556', '2024-09-13 01:28:11.324556', 221, 'Repudiandae ut tenetur consequatur aperiam omnis a quia.', 1, 0, 21, 'Aka Matsuba', 41254000, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(130, '2024-09-13 01:28:11.350289', '2024-09-13 01:28:11.350289', 129, 'Veritatis id sed.', 0, 1, 23, 'Ginrin Chagoi', 35187700, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 1),
(131, '2024-09-13 01:28:11.377868', '2024-09-13 01:28:11.377868', 227, 'Et ut delectus reprehenderit minima.', 1, 0, 6, 'Ki Bekko', 73271900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 1),
(132, '2024-09-13 01:28:11.403168', '2024-09-13 01:28:11.403168', 133, 'Optio sint quos in voluptas.', 0, 1, 72, 'Midorigoi', 58087800, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 3),
(133, '2024-09-13 01:28:11.427108', '2024-09-13 01:28:11.427108', 172, 'Ipsam officiis eos.', 1, 1, 12, 'Kujaku', 22091900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(134, '2024-09-13 01:28:11.449733', '2024-09-13 01:28:11.449733', 84, 'Quod et placeat omnis aut.', 1, 0, 43, 'Ginrin Kohaku', 26887900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 1),
(135, '2024-09-13 01:28:11.472325', '2024-09-13 01:28:11.472325', 169, 'Excepturi autem error atque optio veniam error.', 1, 0, 52, 'Yamabuki', 49458600, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(136, '2024-09-13 01:28:11.496438', '2024-09-13 01:28:11.496438', 93, 'Labore rem blanditiis animi quia.', 0, 1, 35, 'Doitsu Kohaku', 84559200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 2),
(137, '2024-09-13 01:28:11.519381', '2024-09-13 01:28:11.519381', 173, 'Et voluptate provident voluptate non maxime neque ex.', 1, 1, 86, 'Ginrin Sanke', 86067700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 3),
(138, '2024-09-13 01:28:11.542340', '2024-09-13 01:28:11.542340', 202, 'Voluptas est enim fugit debitis.', 0, 0, 35, 'Budo Sanke', 23222300, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2, 1),
(139, '2024-09-13 01:28:11.566998', '2024-09-13 01:28:11.566998', 50, 'Perspiciatis voluptatibus aut illo.', 0, 1, 87, 'Kanoko Showa', 69030500, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 1),
(140, '2024-09-13 01:28:11.592000', '2024-09-13 01:28:11.592000', 4, 'Nobis aut nulla.', 1, 0, 51, 'Doitsu Sanke', 71829900, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 2),
(141, '2024-09-13 01:28:11.622313', '2024-09-13 01:28:11.622313', 149, 'Sit possimus ipsam cupiditate quia aut.', 1, 0, 79, 'Yamabuki', 9459010, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 5, 1),
(142, '2024-09-13 01:28:11.648038', '2024-09-13 01:28:11.648038', 84, 'Cum earum explicabo repudiandae.', 1, 1, 51, 'Kanoko Kohaku', 42235800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 1),
(143, '2024-09-13 01:28:11.673025', '2024-09-13 01:28:11.673025', 100, 'Iusto voluptatibus corporis qui.', 1, 1, 9, 'Midorigoi', 47974500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(144, '2024-09-13 01:28:11.693974', '2024-09-13 01:28:11.693974', 142, 'Sint perspiciatis unde officiis.', 1, 0, 44, 'Doitsu Kohaku', 44673800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(145, '2024-09-13 01:28:11.719655', '2024-09-13 01:28:11.719655', 150, 'Possimus aliquid quia sunt.', 1, 1, 46, 'Bekko', 72612800, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 3),
(146, '2024-09-13 01:28:11.743596', '2024-09-13 01:28:11.743596', 193, 'Aut asperiores iusto accusamus quos ut consectetur.', 1, 0, 19, 'Ai Goromo', 8289720, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(147, '2024-09-13 01:28:11.770626', '2024-09-13 01:28:11.770626', 179, 'Voluptate voluptatum corrupti atque.', 1, 0, 60, 'Utsurimono', 16600100, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 1),
(148, '2024-09-13 01:28:11.794845', '2024-09-13 01:28:11.794845', 107, 'Cupiditate architecto possimus ea molestiae.', 0, 0, 31, 'Kage Shiro Utsuri', 8956350, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(149, '2024-09-13 01:28:11.816736', '2024-09-13 01:28:11.816736', 78, 'Ut unde nulla ratione qui ut tempora.', 1, 1, 75, 'Tancho', 83738900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(150, '2024-09-13 01:28:11.838140', '2024-09-13 01:28:11.838160', 47, 'Tenetur aut qui natus repudiandae ullam facere.', 1, 0, 58, 'Doitsu Kohaku', 15130700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 2),
(151, '2024-09-13 01:28:11.860018', '2024-09-13 01:28:11.860018', 114, 'Nisi ratione perspiciatis et pariatur sed qui aliquam.', 1, 0, 41, 'Hikari Utsuri', 17780500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(152, '2024-09-13 01:28:11.882983', '2024-09-13 01:28:11.882983', 29, 'Enim earum ea quisquam.', 1, 1, 90, 'Sanke', 89673300, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 1),
(153, '2024-09-13 01:28:11.905870', '2024-09-13 01:28:11.905870', 88, 'Impedit repellat eos dignissimos laudantium veniam voluptas.', 1, 1, 4, 'Kujaku', 20354100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(154, '2024-09-13 01:28:11.929557', '2024-09-13 01:28:11.929557', 46, 'Aut qui corporis ducimus laborum possimus.', 0, 0, 18, 'Kumonryu', 2951690, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(155, '2024-09-13 01:28:11.954866', '2024-09-13 01:28:11.954866', 195, 'Qui omnis asperiores consequatur.', 0, 1, 45, 'Shusui', 29673400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(156, '2024-09-13 01:28:11.981063', '2024-09-13 01:28:11.981063', 60, 'Perspiciatis architecto nam in.', 0, 1, 7, 'Doitsu', 79432800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 1),
(157, '2024-09-13 01:28:12.005096', '2024-09-13 01:28:12.005096', 98, 'Magni libero nulla ipsa saepe.', 0, 1, 65, 'Aka Matsuba', 89069200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 1, 2),
(158, '2024-09-13 01:28:12.029043', '2024-09-13 01:28:12.029043', 37, 'Iure quae iure officia dolorem cupiditate recusandae odit.', 1, 1, 48, 'Kikusui', 84547500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1, 2),
(159, '2024-09-13 01:28:12.052013', '2024-09-13 01:28:12.052013', 199, 'Et qui animi quam quia praesentium excepturi.', 0, 1, 48, 'Doitsu', 35902600, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 2),
(160, '2024-09-13 01:28:12.073101', '2024-09-13 01:28:12.073101', 150, 'Voluptatem est iusto.', 1, 0, 64, 'Tancho', 76093600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 3),
(161, '2024-09-13 01:28:12.094540', '2024-09-13 01:28:12.094540', 31, 'Id fuga et nam explicabo.', 1, 0, 67, 'Matsuba', 18108700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 1),
(162, '2024-09-13 01:28:12.117034', '2024-09-13 01:28:12.117034', 61, 'Id laudantium minus vel quia sed.', 0, 1, 85, 'Showa', 32293700, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 3),
(163, '2024-09-13 01:28:12.139555', '2024-09-13 01:28:12.139555', 38, 'Facere quae voluptatem qui.', 1, 1, 55, 'Kage Shiro Utsuri', 21048700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 1),
(164, '2024-09-13 01:28:12.164664', '2024-09-13 01:28:12.164664', 190, 'Eum dolorem vel perferendis voluptas dolorum saepe.', 1, 0, 60, 'Kanoko Showa', 63892900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(165, '2024-09-13 01:28:12.194272', '2024-09-13 01:28:12.194272', 64, 'Totam sed veritatis veritatis sit.', 1, 0, 8, 'Benigoi', 6878850, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 1),
(166, '2024-09-13 01:28:12.220413', '2024-09-13 01:28:12.220413', 52, 'Fugit ut itaque dolores blanditiis ducimus.', 0, 1, 37, 'Kin Ki Utsuri', 10601400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 1),
(167, '2024-09-13 01:28:12.242843', '2024-09-13 01:28:12.242843', 72, 'Qui dolor dolores vel quis dolores ipsa impedit.', 1, 1, 65, 'Koromo', 8768490, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 3),
(168, '2024-09-13 01:28:12.265176', '2024-09-13 01:28:12.265176', 226, 'Quia consequatur sit esse asperiores animi et.', 1, 1, 47, 'Asagi', 65273000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 1),
(169, '2024-09-13 01:28:12.289284', '2024-09-13 01:28:12.289284', 107, 'Deserunt ducimus quidem inventore voluptatem recusandae.', 0, 1, 56, 'Shusui', 21840500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 2),
(170, '2024-09-13 01:28:12.314067', '2024-09-13 01:28:12.314067', 155, 'Repellat officia et.', 0, 0, 37, 'Ginrin Showa', 81424500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2, 3),
(171, '2024-09-13 01:28:12.339938', '2024-09-13 01:28:12.339938', 60, 'Ab architecto officiis voluptate nisi.', 0, 1, 6, 'Kujaku', 53486300, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 3),
(172, '2024-09-13 01:28:12.365019', '2024-09-13 01:28:12.365019', 129, 'Quia distinctio illum omnis eius ut quo consequatur.', 1, 0, 24, 'Utsurimono', 20497900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 1),
(173, '2024-09-13 01:28:12.390198', '2024-09-13 01:28:12.390198', 102, 'Recusandae expedita sit.', 0, 1, 31, 'Hikari Muji', 1129040, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 2),
(174, '2024-09-13 01:28:12.412505', '2024-09-13 01:28:12.412505', 90, 'Laudantium sit quasi quos nihil ut quia.', 0, 1, 44, 'Utsurimono', 4299500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(175, '2024-09-13 01:28:12.435567', '2024-09-13 01:28:12.435567', 144, 'Explicabo a culpa delectus cum quo aut voluptatem.', 1, 1, 12, 'Kanoko Kohaku', 70530400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(176, '2024-09-13 01:28:12.458655', '2024-09-13 01:28:12.458655', 223, 'Perferendis optio maxime quis deserunt repellat est molestias.', 1, 1, 87, 'Sanke', 1147470, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 1),
(177, '2024-09-13 01:28:12.480427', '2024-09-13 01:28:12.480427', 187, 'Aut similique aliquam adipisci necessitatibus veniam voluptas enim.', 0, 1, 73, 'Hikari Utsuri', 86894500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 1, 1),
(178, '2024-09-13 01:28:12.503257', '2024-09-13 01:28:12.503257', 218, 'Voluptas occaecati quaerat dolorem soluta quibusdam nam eos.', 0, 1, 5, 'Hikari Utsuri', 2676960, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 2),
(179, '2024-09-13 01:28:12.524824', '2024-09-13 01:28:12.524824', 84, 'Porro repellendus incidunt est dicta aut quod.', 1, 1, 6, 'Matsuba', 76115800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 2),
(180, '2024-09-13 01:28:12.548036', '2024-09-13 01:28:12.548036', 220, 'Commodi quasi repellat.', 1, 0, 47, 'Yamabuki', 43564200, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(181, '2024-09-13 01:28:12.571793', '2024-09-13 01:28:12.571793', 220, 'Modi culpa voluptas harum reiciendis.', 1, 1, 87, 'Doitsu Kujaku', 22023100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(182, '2024-09-13 01:28:12.595998', '2024-09-13 01:28:12.595998', 203, 'Veniam sapiente voluptatibus.', 0, 1, 58, 'Asagi', 81351000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 3),
(183, '2024-09-13 01:28:12.618220', '2024-09-13 01:28:12.618220', 226, 'Quo fugit quia ut explicabo eum eum error.', 1, 0, 79, 'Kin Ki Utsuri', 51027500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(184, '2024-09-13 01:28:12.640746', '2024-09-13 01:28:12.640746', 177, 'Id temporibus dolorem est.', 1, 1, 55, 'Goshiki', 20105900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 2),
(185, '2024-09-13 01:28:12.661361', '2024-09-13 01:28:12.661361', 118, 'Atque sit ut porro consectetur id.', 0, 1, 31, 'Shusui', 22140100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 6, 1),
(186, '2024-09-13 01:28:12.683230', '2024-09-13 01:28:12.683230', 6, 'Dolorem natus eius modi soluta eos ut est.', 0, 1, 43, 'Kumonryu', 57261700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 1),
(187, '2024-09-13 01:28:12.703564', '2024-09-13 01:28:12.704070', 156, 'Vero et id a eaque.', 1, 1, 68, 'Kikokuryu', 18909900, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 3),
(188, '2024-09-13 01:28:12.726387', '2024-09-13 01:28:12.726387', 17, 'Odit quisquam aut quae consequatur voluptatum tempore enim.', 0, 0, 3, 'Kage Shiro Utsuri', 6968540, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 6, 2),
(189, '2024-09-13 01:28:12.749760', '2024-09-13 01:28:12.749760', 227, 'Exercitationem cumque possimus odio.', 1, 1, 63, 'Utsurimono', 68368400, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 2);
INSERT INTO `kois` (`id`, `created_at`, `updated_at`, `age`, `description`, `is_display`, `is_sold`, `length`, `name`, `price`, `sex`, `thumbnail`, `tracking_status`, `category_id`, `user_id`) VALUES
(190, '2024-09-13 01:28:12.776908', '2024-09-13 01:28:12.776908', 197, 'Qui reprehenderit ea vero rerum quae omnis.', 1, 1, 47, 'Benigoi', 67558400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 2),
(191, '2024-09-13 01:28:12.800445', '2024-09-13 01:28:12.800445', 4, 'Sunt dolor ut amet incidunt consectetur omnis reiciendis.', 1, 1, 54, 'Hikari Muji', 19614700, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(192, '2024-09-13 01:28:12.827756', '2024-09-13 01:28:12.827756', 194, 'Nostrum ad a est consequatur vitae.', 1, 0, 10, 'Bekko', 65677600, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(193, '2024-09-13 01:28:12.851741', '2024-09-13 01:28:12.851741', 136, 'Ea et provident velit velit.', 1, 1, 37, 'Yamabuki', 8353600, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 9, 2),
(194, '2024-09-13 01:28:12.872946', '2024-09-13 01:28:12.872946', 34, 'Dolor alias unde.', 1, 0, 3, 'Kumonryu', 57621500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(195, '2024-09-13 01:28:12.893692', '2024-09-13 01:28:12.893692', 227, 'Voluptatem sequi et et quo aut autem.', 1, 1, 30, 'Ginrin Showa', 28907800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 1),
(196, '2024-09-13 01:28:12.916615', '2024-09-13 01:28:12.916615', 70, 'Aut ut illo.', 1, 0, 9, 'Kanoko Kohaku', 62431700, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(197, '2024-09-13 01:28:12.940948', '2024-09-13 01:28:12.940977', 63, 'Voluptas similique consequatur explicabo totam sequi fugit est.', 1, 1, 49, 'Matsuba', 87078900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 3),
(198, '2024-09-13 01:28:12.964477', '2024-09-13 01:28:12.964477', 121, 'Veniam hic temporibus ratione nobis cumque.', 1, 1, 67, 'Sumi Goromo', 15739100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 2),
(199, '2024-09-13 01:28:12.987603', '2024-09-13 01:28:12.987603', 150, 'Nihil esse quia aut quis similique odio doloremque.', 0, 0, 2, 'Benigoi', 76554500, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(200, '2024-09-13 01:28:13.009537', '2024-09-13 01:28:13.009537', 90, 'Autem error sed sed est quae.', 1, 1, 63, 'Kanoko Kohaku', 6698750, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 2),
(201, '2024-09-13 01:28:13.032679', '2024-09-13 01:28:13.032679', 131, 'Laborum necessitatibus rerum rerum et autem placeat fugit.', 0, 1, 25, 'Hikari Muji', 55787100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 2),
(202, '2024-09-13 01:28:13.054324', '2024-09-13 01:28:13.054324', 190, 'Itaque ut quos.', 1, 0, 56, 'Midorigoi', 8328320, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 1),
(203, '2024-09-13 01:28:13.078550', '2024-09-13 01:28:13.078550', 9, 'Quae qui exercitationem ut omnis corrupti.', 0, 1, 2, 'Utsurimono', 17706000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(204, '2024-09-13 01:28:13.101814', '2024-09-13 01:28:13.101814', 217, 'Quidem cum cum placeat.', 1, 1, 60, 'Ki Utsuri', 28556800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 1, 1),
(205, '2024-09-13 01:28:13.123903', '2024-09-13 01:28:13.123903', 180, 'Voluptas veniam qui odit aut.', 1, 1, 83, 'Kin Showa', 34666000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 2),
(206, '2024-09-13 01:28:13.147257', '2024-09-13 01:28:13.147257', 74, 'Vero fuga in qui veritatis molestiae.', 0, 1, 44, 'Beni Kikokuryu', 10671600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(207, '2024-09-13 01:28:13.170052', '2024-09-13 01:28:13.170559', 204, 'Enim impedit quod ad asperiores quia vel.', 1, 1, 63, 'Doitsu Sanke', 5369450, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(208, '2024-09-13 01:28:13.194455', '2024-09-13 01:28:13.194455', 26, 'Saepe atque dolor amet temporibus vitae cum ut.', 1, 0, 69, 'Utsurimono', 38817100, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 1),
(209, '2024-09-13 01:28:13.216797', '2024-09-13 01:28:13.216797', 202, 'Eum laboriosam est perferendis.', 1, 0, 42, 'Ginrin Chagoi', 45990600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 2),
(210, '2024-09-13 01:28:13.240087', '2024-09-13 01:28:13.240087', 104, 'Omnis consequatur et qui deserunt.', 1, 1, 31, 'Ki Bekko', 27401400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 2),
(211, '2024-09-13 01:28:13.264639', '2024-09-13 01:28:13.264639', 64, 'Natus molestiae nostrum quo cumque laudantium earum autem.', 0, 0, 29, 'Matsuba', 71881800, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 1),
(212, '2024-09-13 01:28:13.287000', '2024-09-13 01:28:13.287000', 165, 'Ratione qui maxime vitae.', 1, 1, 49, 'Goshiki', 32493400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 1),
(213, '2024-09-13 01:28:13.311926', '2024-09-13 01:28:13.311926', 30, 'Est qui non voluptas nam vel in.', 0, 1, 23, 'Ginrin Kohaku', 66100800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 1),
(214, '2024-09-13 01:28:13.337678', '2024-09-13 01:28:13.337678', 26, 'Est repellendus quod labore voluptatum voluptates.', 1, 1, 74, 'Doitsu', 22996800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 2),
(215, '2024-09-13 01:28:13.364119', '2024-09-13 01:28:13.364119', 179, 'Sit libero deleniti commodi blanditiis et laborum dolorem.', 1, 0, 76, 'Bekko', 14449600, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 1),
(216, '2024-09-13 01:28:13.388314', '2024-09-13 01:28:13.388314', 47, 'Voluptas eligendi iste qui dicta explicabo.', 1, 0, 51, 'Hikari Utsuri', 63016400, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 1, 2),
(217, '2024-09-13 01:28:13.413062', '2024-09-13 01:28:13.413062', 73, 'Ipsa minima assumenda quia enim.', 1, 1, 14, 'Yamatonishiki', 5201030, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(218, '2024-09-13 01:28:13.435686', '2024-09-13 01:28:13.435686', 37, 'Est magni et odio.', 1, 0, 17, 'Hariwake', 2711910, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 3),
(219, '2024-09-13 01:28:13.459690', '2024-09-13 01:28:13.459690', 8, 'Nostrum vero suscipit.', 0, 1, 65, 'Hikari Muji', 81830900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 1),
(220, '2024-09-13 01:28:13.481224', '2024-09-13 01:28:13.481224', 151, 'Recusandae modi dolorem qui dolorum.', 1, 1, 19, 'Kin Showa', 79884500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 1),
(221, '2024-09-13 01:28:13.501221', '2024-09-13 01:28:13.501221', 119, 'Sunt repellat sequi magni distinctio autem.', 1, 1, 78, 'Doitsu Showa', 13054000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 2),
(222, '2024-09-13 01:28:13.528089', '2024-09-13 01:28:13.528089', 116, 'Voluptas repellendus officia molestiae aut qui dolores.', 1, 0, 88, 'Shusui', 68649800, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 9, 2),
(223, '2024-09-13 01:28:13.555409', '2024-09-13 01:28:13.555409', 111, 'Earum ex architecto iure in veniam impedit distinctio.', 0, 0, 21, 'Yamatonishiki', 59749500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 3),
(224, '2024-09-13 01:28:13.581256', '2024-09-13 01:28:13.581256', 196, 'Eos quo aut pariatur in.', 0, 1, 47, 'Aka Matsuba', 86107500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(225, '2024-09-13 01:28:13.606668', '2024-09-13 01:28:13.606668', 70, 'Voluptas delectus soluta eum rem alias.', 0, 1, 88, 'Kanoko Sanke', 78131100, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 2),
(226, '2024-09-13 01:28:13.632079', '2024-09-13 01:28:13.632079', 159, 'Qui alias velit unde quasi.', 1, 1, 9, 'Gin Shiro Utsuri', 83612300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(227, '2024-09-13 01:28:13.654866', '2024-09-13 01:28:13.654866', 59, 'Repellat dolorum perspiciatis et delectus eius eos.', 1, 0, 86, 'Orenji Ogon', 41164300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(228, '2024-09-13 01:28:13.678086', '2024-09-13 01:28:13.678086', 47, 'Quas officia tenetur quisquam.', 0, 1, 9, 'Kikusui', 85402300, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 1, 1),
(229, '2024-09-13 01:28:13.701837', '2024-09-13 01:28:13.701837', 184, 'Amet et consequatur.', 0, 1, 73, 'Benigoi', 52113800, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 1),
(230, '2024-09-13 01:28:13.726683', '2024-09-13 01:28:13.726683', 118, 'Dolor dolor a sint.', 1, 0, 33, 'Budo Sanke', 2850030, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 1),
(231, '2024-09-13 01:28:13.754399', '2024-09-13 01:28:13.754399', 11, 'Sunt eum ratione amet culpa reprehenderit.', 1, 1, 34, 'Showa', 65411800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 1),
(232, '2024-09-13 01:28:13.779060', '2024-09-13 01:28:13.779060', 66, 'Ut quis quam reiciendis iste voluptates.', 0, 0, 76, 'Doitsu', 41443800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 3),
(233, '2024-09-13 01:28:13.840309', '2024-09-13 01:28:13.840309', 174, 'Accusamus omnis iure.', 0, 0, 80, 'Matsuba', 87855000, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 2),
(234, '2024-09-13 01:28:13.863475', '2024-09-13 01:28:13.863475', 179, 'Deserunt excepturi qui esse quia veniam voluptatem.', 0, 0, 78, 'Budo Sanke', 64954600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 1),
(235, '2024-09-13 01:28:13.886284', '2024-09-13 01:28:13.886284', 51, 'Quo qui consequatur optio consequatur sit.', 1, 1, 45, 'Aka Matsuba', 44020900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 7, 1),
(236, '2024-09-13 01:28:13.911217', '2024-09-13 01:28:13.911217', 151, 'Sed non deleniti eos ipsam voluptatem consequuntur ea.', 1, 0, 8, 'Hikari Utsuri', 75687400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 2),
(237, '2024-09-13 01:28:13.934754', '2024-09-13 01:28:13.934754', 155, 'Tempore dignissimos aliquam ea.', 1, 1, 68, 'Kage Shiro Utsuri', 43734600, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 5, 1),
(238, '2024-09-13 01:28:13.958530', '2024-09-13 01:28:13.958530', 96, 'Recusandae quia ipsum itaque id nam sapiente tempore.', 0, 0, 15, 'Ginrin Sanke', 19342400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3, 1),
(239, '2024-09-13 01:28:13.980926', '2024-09-13 01:28:13.980926', 87, 'Praesentium dolore beatae.', 0, 1, 84, 'Midorigoi', 27346500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 7, 3),
(240, '2024-09-13 01:28:14.002927', '2024-09-13 01:28:14.002927', 45, 'Ex illo quasi soluta aut nesciunt dolor velit.', 1, 1, 29, 'Sanke', 22352400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 2),
(241, '2024-09-13 01:28:14.024927', '2024-09-13 01:28:14.024927', 59, 'Odit voluptatem natus ullam.', 1, 1, 56, 'Kin Showa', 56144000, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(242, '2024-09-13 01:28:14.046930', '2024-09-13 01:28:14.046930', 227, 'Eligendi optio eius voluptatem.', 1, 1, 39, 'Utsurimono', 30791200, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 1),
(243, '2024-09-13 01:28:14.067930', '2024-09-13 01:28:14.067930', 5, 'Ab occaecati optio.', 0, 1, 30, 'Tancho', 31201500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(244, '2024-09-13 01:28:14.090927', '2024-09-13 01:28:14.090927', 25, 'Debitis voluptas deleniti optio et consequatur quae.', 1, 1, 10, 'Ginrin Showa', 16512400, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 1),
(245, '2024-09-13 01:28:14.111930', '2024-09-13 01:28:14.111930', 133, 'Soluta expedita quia rerum.', 1, 1, 58, 'Ginrin Sanke', 70806100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(246, '2024-09-13 01:28:14.137519', '2024-09-13 01:28:14.137519', 210, 'Distinctio aut vero sunt.', 1, 0, 18, 'Chagoi', 24680600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 1),
(247, '2024-09-13 01:28:14.162201', '2024-09-13 01:28:14.162201', 7, 'Harum fugiat ut placeat quod rerum rerum pariatur.', 0, 1, 18, 'Beni Kikokuryu', 15970500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 2),
(248, '2024-09-13 01:28:14.187414', '2024-09-13 01:28:14.187414', 11, 'Soluta dolores aut dolores esse sunt et.', 0, 0, 51, 'Kikusui', 8892240, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 9, 3),
(249, '2024-09-13 01:28:14.210712', '2024-09-13 01:28:14.210712', 153, 'Consequatur nisi non.', 0, 1, 23, 'Kanoko Sanke', 9065050, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 4, 2),
(250, '2024-09-13 01:28:14.232762', '2024-09-13 01:28:14.232762', 187, 'Aliquid porro distinctio ducimus perspiciatis est.', 1, 0, 80, 'Kikokuryu', 67425200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2),
(251, '2024-09-13 01:28:14.256406', '2024-09-13 01:28:14.256406', 105, 'Dolores eos recusandae ut quam saepe officiis tempore.', 1, 1, 10, 'Doitsu Kujaku', 65127800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 3),
(252, '2024-09-13 01:28:14.278022', '2024-09-13 01:28:14.278022', 59, 'Ea nisi maiores sunt et doloremque.', 1, 0, 22, 'Aka Matsuba', 71253700, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2, 2),
(253, '2024-09-13 01:28:14.300281', '2024-09-13 01:28:14.300281', 46, 'Quae sit impedit sint illo aliquid.', 0, 1, 7, 'Platinum Ogon', 81168000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(254, '2024-09-13 01:28:14.322600', '2024-09-13 01:28:14.322600', 127, 'Praesentium amet soluta.', 1, 0, 28, 'Hikari Muji', 83525800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 2),
(255, '2024-09-13 01:28:14.345813', '2024-09-13 01:28:14.345813', 41, 'Voluptatem cumque omnis.', 0, 0, 69, 'Shusui', 50027100, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 1),
(256, '2024-09-13 01:28:14.370733', '2024-09-13 01:28:14.370733', 147, 'Excepturi animi itaque qui ullam ut.', 1, 1, 60, 'Kikusui', 7354690, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 3),
(257, '2024-09-13 01:28:14.397819', '2024-09-13 01:28:14.397819', 122, 'Veritatis ab nisi voluptatem eos quis.', 1, 1, 64, 'Kage Shiro Utsuri', 80192600, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(258, '2024-09-13 01:28:14.421996', '2024-09-13 01:28:14.421996', 179, 'Hic aut dicta ut.', 1, 1, 67, 'Sumi Goromo', 4292690, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 6, 2),
(259, '2024-09-13 01:28:14.444125', '2024-09-13 01:28:14.444125', 10, 'Facere nobis repudiandae atque voluptas aut suscipit.', 1, 1, 88, 'Ai Goromo', 5849530, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(260, '2024-09-13 01:28:14.466288', '2024-09-13 01:28:14.466288', 50, 'Dolores quis tempore voluptatem qui ut facilis quasi.', 1, 0, 78, 'Kumonryu', 83336300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 10, 1),
(261, '2024-09-13 01:28:14.492801', '2024-09-13 01:28:14.492801', 43, 'Ut voluptas saepe perspiciatis voluptatum.', 0, 1, 67, 'Kikusui', 60380500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 5, 2),
(262, '2024-09-13 01:28:14.515535', '2024-09-13 01:28:14.515535', 42, 'Dignissimos vero delectus aliquam.', 0, 1, 22, 'Ki Utsuri', 40068500, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 3, 2),
(263, '2024-09-13 01:28:14.544812', '2024-09-13 01:28:14.544812', 68, 'Sit laudantium totam neque ab commodi.', 1, 1, 90, 'Ginrin Showa', 21406600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 3, 2),
(264, '2024-09-13 01:28:14.771300', '2024-09-13 01:28:14.771300', 124, 'Voluptas omnis et voluptatibus.', 1, 0, 67, 'Ochiba', 60897100, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 2),
(265, '2024-09-13 01:28:14.809418', '2024-09-13 01:28:14.809418', 12, 'Aut in corporis porro dicta doloremque eum.', 1, 1, 15, 'Ki Utsuri', 16764300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 8, 2),
(266, '2024-09-13 01:28:14.830277', '2024-09-13 01:28:14.830277', 73, 'Maiores hic nihil qui.', 0, 1, 21, 'Doitsu Kohaku', 66596300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 2, 2),
(267, '2024-09-13 01:28:14.851854', '2024-09-13 01:28:14.851854', 156, 'Optio id eos.', 1, 1, 25, 'Bekko', 45968300, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 2, 2),
(268, '2024-09-13 01:28:14.875861', '2024-09-13 01:28:14.875861', 36, 'Nemo sed dolorem officia officiis error quis nemo.', 1, 1, 30, 'Kumonryu', 71895700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 9, 2),
(269, '2024-09-13 01:28:14.900213', '2024-09-13 01:28:14.900213', 220, 'Rerum delectus hic.', 0, 1, 11, 'Doitsu', 62463600, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 8, 2),
(270, '2024-09-13 01:28:14.925677', '2024-09-13 01:28:14.925677', 167, 'Sequi adipisci est vel.', 1, 0, 40, 'Ai Goromo', 18750000, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2, 2),
(271, '2024-09-13 01:28:14.948726', '2024-09-13 01:28:14.948726', 100, 'Distinctio accusantium perferendis sit et exercitationem itaque.', 1, 1, 78, 'Kin Ki Utsuri', 30031600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 7, 3),
(272, '2024-09-13 01:28:14.973334', '2024-09-13 01:28:14.973334', 85, 'Et esse dicta corrupti sed repellat omnis assumenda.', 1, 0, 32, 'Matsuba', 88802500, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 2, 2),
(273, '2024-09-13 01:28:14.998194', '2024-09-13 01:28:14.998194', 159, 'Laudantium non nemo sapiente magni temporibus.', 1, 1, 39, 'Bekko', 11942800, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(274, '2024-09-13 01:28:15.024129', '2024-09-13 01:28:15.024129', 172, 'Et saepe minus omnis omnis quis.', 1, 0, 2, 'Hikari Utsuri', 48434600, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 2),
(275, '2024-09-13 01:28:15.049297', '2024-09-13 01:28:15.049297', 188, 'Possimus earum et incidunt fugit odio voluptate.', 1, 1, 14, 'Ki Bekko', 32452700, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 1),
(276, '2024-09-13 01:28:15.072358', '2024-09-13 01:28:15.072358', 224, 'Et ut et dolorum optio sint.', 1, 1, 54, 'Hikari Utsuri', 65004500, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(277, '2024-09-13 01:28:15.096557', '2024-09-13 01:28:15.096557', 61, 'Dolore totam error fugit sit qui ut.', 1, 1, 34, 'Ginrin Showa', 48703900, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 5, 1),
(278, '2024-09-13 01:28:15.118570', '2024-09-13 01:28:15.118570', 202, 'Esse nesciunt ut enim et vero.', 0, 0, 24, 'Ochiba', 54496600, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 8, 1),
(279, '2024-09-13 01:28:15.142955', '2024-09-13 01:28:15.142955', 142, 'Veniam quos similique voluptates.', 1, 1, 36, 'Goshiki', 16625200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 10, 1),
(280, '2024-09-13 01:28:15.165909', '2024-09-13 01:28:15.165909', 114, 'Aut aut est sit.', 1, 0, 3, 'Ochiba', 49213700, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 4, 2),
(281, '2024-09-13 01:28:15.191578', '2024-09-13 01:28:15.191578', 75, 'Voluptate eum deserunt.', 1, 0, 8, 'Hikari Muji', 49596800, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 1, 1),
(282, '2024-09-13 01:28:15.216620', '2024-09-13 01:28:15.216620', 61, 'Dolor asperiores sed hic voluptas.', 1, 1, 37, 'Doitsu Sanke', 18048100, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(283, '2024-09-13 01:28:15.240502', '2024-09-13 01:28:15.240502', 227, 'Aperiam ea vero quasi et et tempore.', 0, 1, 33, 'Doitsu Showa', 35774700, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 10, 1),
(284, '2024-09-13 01:28:15.263957', '2024-09-13 01:28:15.263957', 217, 'Et maxime blanditiis laborum id fuga autem.', 0, 1, 51, 'Doitsu Showa', 74422200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 5, 2),
(285, '2024-09-13 01:28:15.289306', '2024-09-13 01:28:15.289306', 26, 'At officiis vero.', 1, 0, 84, 'Hariwake', 88136100, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 3, 1),
(286, '2024-09-13 01:28:15.313204', '2024-09-13 01:28:15.313204', 76, 'Rerum nihil iste et quam.', 1, 0, 88, 'Midorigoi', 61257400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 2, 3),
(287, '2024-09-13 01:28:15.335153', '2024-09-13 01:28:15.335153', 106, 'Et tenetur dolores temporibus eligendi excepturi nulla.', 1, 1, 74, 'Chagoi', 71543900, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 7, 2),
(288, '2024-09-13 01:28:15.357086', '2024-09-13 01:28:15.357086', 119, 'Veritatis soluta animi sapiente.', 0, 1, 30, 'Kujaku', 2649940, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 10, 2),
(289, '2024-09-13 01:28:15.381200', '2024-09-13 01:28:15.381200', 29, 'Occaecati pariatur dolor porro dolores natus laboriosam quam.', 1, 1, 50, 'Kin Ki Utsuri', 30473100, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 10, 1),
(290, '2024-09-13 01:28:15.405618', '2024-09-13 01:28:15.405618', 53, 'Aliquam et debitis id et voluptate.', 1, 1, 87, 'Kikusui', 8536310, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 6, 1),
(291, '2024-09-13 01:28:15.433435', '2024-09-13 01:28:15.433435', 67, 'Officiis deserunt natus unde dicta vel eveniet reiciendis.', 1, 1, 58, 'Sumi Goromo', 39399900, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 4, 1),
(292, '2024-09-13 01:28:15.462413', '2024-09-13 01:28:15.462413', 112, 'Neque nostrum porro natus.', 0, 0, 34, 'Kanoko Showa', 54060800, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 0, 5, 1),
(293, '2024-09-13 01:28:15.490176', '2024-09-13 01:28:15.490176', 121, 'Quo aut repellendus sequi eius sed.', 1, 0, 81, 'Tancho', 34567400, 'M', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 10, 1),
(294, '2024-09-13 01:28:15.513897', '2024-09-13 01:28:15.513897', 188, 'Id et ratione nulla aliquid incidunt.', 1, 1, 51, 'Doitsu Showa', 48516400, 'F', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 3, 1),
(295, '2024-09-13 01:28:15.538538', '2024-09-13 01:28:15.538538', 42, 'Temporibus velit atque nam et iste.', 1, 1, 88, 'Hikari Muji', 42976200, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 3, 6, 2),
(296, '2024-09-13 01:28:15.560633', '2024-09-13 01:28:15.560633', 73, 'Minima illo blanditiis autem illum.', 1, 1, 28, 'Orenji Ogon', 14726900, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(297, '2024-09-13 01:28:15.592876', '2024-09-13 01:28:15.592876', 114, 'Quia reiciendis nemo possimus dolores suscipit.', 0, 0, 40, 'Kujaku', 29089200, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 1, 2),
(298, '2024-09-13 01:28:15.618610', '2024-09-13 01:28:15.618610', 31, 'Nemo sunt illum ipsam.', 0, 1, 8, 'Budo Sanke', 59420300, 'O', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 2, 4, 2),
(299, '2024-09-13 01:28:15.642954', '2024-09-13 01:28:15.642954', 63, 'Rerum illum fuga similique.', 1, 1, 3, 'Kujaku', 31576000, 'U', 'https://mjjlqhnswgbzvxfujauo.supabase.co/storage/v1/object/public/auctions/48/photos/Sanke%2040cm.png', 1, 8, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `koi_images`
--

CREATE TABLE `koi_images` (
  `id` bigint NOT NULL,
  `image_url` varchar(300) DEFAULT NULL,
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
  `fullname` varchar(100) DEFAULT NULL,
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
  `color` varchar(255) DEFAULT NULL,
  `number_of_products` int NOT NULL,
  `price` float NOT NULL,
  `total_money` float NOT NULL,
  `koi_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL
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
-- Cấu trúc bảng cho bảng `shippers`
--

CREATE TABLE `shippers` (
  `id` int NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `social_accounts`
--

CREATE TABLE `social_accounts` (
  `id` bigint NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `provider` varchar(20) NOT NULL,
  `provider_id` varchar(50) NOT NULL
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
  `facebook_account_id` int DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `google_account_id` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `created_at`, `updated_at`, `address`, `avatar_url`, `date_of_birth`, `email`, `facebook_account_id`, `fullname`, `google_account_id`, `is_active`, `password`, `role_id`) VALUES
(1, '2024-09-13 01:09:01.086737', '2024-09-13 01:09:01.086737', 'Da Nang, Viet Nam', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'duong@gmail.com', , 'Duong Nguyen', 0, 0, '$2a$10$XVTbFSnr06K//1KxEakEieOmo1n0BksiqFJs1BF.nzYQ55xsjpszq', 1),
(2, '2024-09-13 01:09:37.928010', '2024-09-13 01:09:37.928010', 'Tam Ki, Viet Nam', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'hoangclw@gmail.com', , 'Hoang Cao Luu', 0, 0, '$2a$10$ZVU9SOhox1rpkjxXAPviceXkIuIxEUy4nVK5xzLhBopyTcoTenYJW', 1),
(3, '2024-09-13 01:09:54.556093', '2024-09-13 01:09:54.556093', 'Quang Binh, Viet Nam', 'https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg', '1979-10-13 00:00:00.000000', 'son@gmail.com', , 'Son Duong', 0, 0, '$2a$10$KgFv0aJ.TWKxX/wn1Px3f.D2T/hbwjW4quAjSY8W.VoVB8U6jZmGy', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Chỉ mục cho bảng `kois`
--
ALTER TABLE `kois`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKabkupc6s0m4jrqoh0e0a9ri1f` (`category_id`),
  ADD KEY `FKljcm9ka7f5fde25f47ju1pdmu` (`user_id`);

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
  ADD KEY `FK35ddqbqa2qgb77wkdx7qqyf8u` (`koi_id`),
  ADD KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `shippers`
--
ALTER TABLE `shippers`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`);

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
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `kois`
--
ALTER TABLE `kois`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

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
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `shippers`
--
ALTER TABLE `shippers`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

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
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `kois`
--
ALTER TABLE `kois`
  ADD CONSTRAINT `FKabkupc6s0m4jrqoh0e0a9ri1f` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKljcm9ka7f5fde25f47ju1pdmu` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

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
  ADD CONSTRAINT `FK35ddqbqa2qgb77wkdx7qqyf8u` FOREIGN KEY (`koi_id`) REFERENCES `kois` (`id`),
  ADD CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
