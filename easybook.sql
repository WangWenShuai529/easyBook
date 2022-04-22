/*
 Navicat Premium Data Transfer

 Source Server         : Mysql5.5
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : easybook

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 21/04/2022 19:44:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book`  (
  `id` int(10) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES (1133326, '三国演义', '2022-04-21 19:38:11');
INSERT INTO `tb_book` VALUES (2132611, '道德经', '2022-04-21 19:38:08');
INSERT INTO `tb_book` VALUES (5439065, '易经', '2022-04-21 19:40:34');
INSERT INTO `tb_book` VALUES (5529135, '红与黑', '2022-04-21 19:38:07');
INSERT INTO `tb_book` VALUES (5640824, '水浒传', '2022-04-21 19:38:15');
INSERT INTO `tb_book` VALUES (6310862, '红楼梦', '2022-04-21 19:37:59');
INSERT INTO `tb_book` VALUES (6945308, '诸子语录', '2022-04-21 19:37:55');

SET FOREIGN_KEY_CHECKS = 1;
