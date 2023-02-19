/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : casbin

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 16/10/2022 21:50:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for casbin_rule
-- ----------------------------
DROP TABLE IF EXISTS `casbin_rule`;
CREATE TABLE `casbin_rule`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ptype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `v0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `v1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `v2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `v3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `v4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `v5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of casbin_rule
-- ----------------------------
INSERT INTO `casbin_rule` VALUES (55, 'p', 'user_1', 'domain_1', '/ask', 'POST', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (56, 'p', 'user_4', 'domain_1', '/ask', 'POST', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (57, 'p', 'user_7', 'domain_3', '/test', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (58, 'p', 'user_1', 'domain_1', '/go', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (59, 'p', 'user_3', 'domain_2', '/ask', 'POST', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (60, 'p', 'user_6', 'domain_2', '/ask', 'POST', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (61, 'g2', '/user/allocate/role/*', 'role_1', NULL, NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (62, 'p', 'user_4', 'domain_1', '/user/allocate/role/*', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (63, 'g2', '/ask', 'role_1', NULL, NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (64, 'g', 'user_7', 'role_2', 'domain_3', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (65, 'p', 'user_1', 'domain_1', '/user/allocate/role/*', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (66, 'g', 'user_7', 'role_3', 'domain_3', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (67, 'g', 'user_2', 'role_2', 'domain_2', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (68, 'g2', '/go', 'role_1', NULL, NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (69, 'g2', '/test', 'role_2', NULL, NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (70, 'g', 'user_3', 'role_1', 'domain_2', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (71, 'g', 'user_6', 'role_1', 'domain_2', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (72, 'p', 'user_7', 'domain_3', '/love', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (73, 'g', 'user_4', 'role_1', 'domain_1', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (74, 'p', 'user_3', 'domain_2', '/user/allocate/role/*', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (75, 'g', 'user_1', 'role_1', 'domain_1', NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (76, 'p', 'user_3', 'domain_2', '/go', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (77, 'p', 'user_6', 'domain_2', '/go', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (78, 'p', 'user_6', 'domain_2', '/user/allocate/role/*', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (79, 'g2', '/love', 'role_3', NULL, NULL, NULL, NULL);
INSERT INTO `casbin_rule` VALUES (80, 'p', 'user_4', 'domain_1', '/go', 'POST||GET||PUT', NULL, NULL);
INSERT INTO `casbin_rule` VALUES (81, 'p', 'user_2', 'domain_2', '/test', 'POST||GET||PUT', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
