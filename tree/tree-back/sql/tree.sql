/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 26/12/2021 17:19:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT 'id',
  `parent_id` bigint(20) UNSIGNED NOT NULL COMMENT '父 id',
  `lft` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '左节点',
  `rgt` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '右节点',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) NULL DEFAULT 0 COMMENT '是否已删除(0: 否 1: 是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, 1, 14, '1000', '键っ子', '2021-12-11 23:06:14', '2021-12-26 15:55:09', 0);
INSERT INTO `sys_dept` VALUES (2, 1, 2, 7, '1100', '软件部', '2021-12-12 13:55:18', '2021-12-12 13:55:20', 0);
INSERT INTO `sys_dept` VALUES (3, 2, 3, 4, '1101', '研发部', '2021-12-12 13:55:38', '2021-12-12 13:55:41', 0);
INSERT INTO `sys_dept` VALUES (4, 1, 8, 11, '1200', '市场部', '2021-12-12 13:56:22', '2021-12-12 13:56:24', 0);
INSERT INTO `sys_dept` VALUES (1475032273660760065, 2, 5, 6, '1102', '测试部', '2021-12-26 17:14:33', NULL, 0);
INSERT INTO `sys_dept` VALUES (1475032407840739329, 1, 12, 13, '1300', '行政部', '2021-12-26 17:14:59', NULL, 0);
INSERT INTO `sys_dept` VALUES (1475032956589920258, 4, 9, 10, '1201', '市场一部', '2021-12-26 17:17:16', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
