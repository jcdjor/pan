-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1:3306
-- 生成日期： 2019-06-27 03:06:02
-- 服务器版本： 5.7.26
-- PHP 版本： 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `pan`
--

-- --------------------------------------------------------

--
-- 表的结构 `filelist`
--

DROP TABLE IF EXISTS `filelist`;
CREATE TABLE IF NOT EXISTS `filelist` (
  `file_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '文件信息id、唯一主键、中的增长',
  `time` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '文件上传的时间',
  `fileName` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '文件上传的文件名称',
  `size` int(8) NOT NULL COMMENT '文件上传的文件大小',
  `path` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '文件上传保存的路径',
  `type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '文件上传的文件类型',
  `user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '文件上传的用户名',
  PRIMARY KEY (`file_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `sharefile`
--

DROP TABLE IF EXISTS `sharefile`;
CREATE TABLE IF NOT EXISTS `sharefile` (
  `share_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '分享文件的id、唯一主键、自动增长',
  `time` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '分享的文件的上传时间',
  `fileName` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '分享的文件的文件名称',
  `size` int(8) NOT NULL COMMENT '分享的文件的文件大小',
  `path` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '分享的文件的文件保存路径',
  `type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '分享的文件的文件类型',
  `user` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '分享的文件所属的用户名',
  PRIMARY KEY (`share_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '用户id，唯一的主键，自动增长',
  `user_name` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '用户名，用户注册或登录的名称',
  `pwd` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '用户密码、用户登录的密码',
  `user_path` varchar(13) COLLATE utf8_bin NOT NULL COMMENT '用户网盘目录、用户网盘存储根目录',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
