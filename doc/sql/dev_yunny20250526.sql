/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.129
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : 192.168.1.129:3306
 Source Schema         : dev_yunny

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 26/05/2025 10:04:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company_vehicles
-- ----------------------------
DROP TABLE IF EXISTS `company_vehicles`;
CREATE TABLE `company_vehicles`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `car_owner` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车主,所属公司',
  `car_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '车牌号',
  `car_type` tinyint(4) NOT NULL COMMENT '车型:1 特三,2丰田,3 希尔,4五菱,5春星,6货,7长城货,8长城,9 红宇,10 鸿星达,11 哈弗,12 霸道,13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多',
  `brand` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车辆品牌 （暂时存储车辆 车型的中文名称）',
  `model` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车辆型号（暂时存储车辆 车型的中文名称）',
  `oil_type` tinyint(4) NULL DEFAULT NULL COMMENT '油品类型 1 是 0#柴油； 2是  89#汽油；3 是92#汽油；4 是95#汽油；5 是 98#汽油',
  `purchasing_date` datetime NULL DEFAULT NULL COMMENT '置购日期',
  `car_load` int(11) NULL DEFAULT NULL COMMENT '载重 单位: 吨',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '车辆备注',
  `active_state` int(2) NULL DEFAULT 1 COMMENT '车辆使用状态 ：1 可用，2 外出中',
  `is_manage` int(2) NULL DEFAULT 0 COMMENT '车辆是否受车队管理： 0 不受车队管理车辆，1 受车队管理',
  `state` int(2) NULL DEFAULT 0 COMMENT '车身状态： 0 报废车辆，1 正常使用，2 维修中 ，3 其他',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `car_number_index`(`car_number`) USING BTREE COMMENT '车牌号不能重复！'
) ENGINE = InnoDB AUTO_INCREMENT = 967 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司车辆表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of company_vehicles
-- ----------------------------
INSERT INTO `company_vehicles` VALUES (896, '七台河海外民爆器材专卖有限公司', '黑KA2881', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (897, '七台河海外民爆器材专卖有限公司', '黑KA7713', 9, '红宇', '红宇', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (898, '七台河海外民爆器材专卖有限公司', '黑KA8767', 9, '红宇', '红宇', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (899, '哈尔滨海外爆破工程有限公司', '黑LJG779', 21, '普拉多', '普拉多', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (900, '七台河海外民爆器材专卖有限公司', '黑KA1826', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (901, '七台河海外民爆器材专卖有限公司', '黑KJ6988', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (902, '七台河海外民爆器材专卖有限公司', '黑KA2626', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (903, '七台河海外民爆器材专卖有限公司', '黑KA6409', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (904, '七台河海外民爆器材专卖有限公司', '黑K96633', 20, '酷路泽', '酷路泽', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (905, '七台河海外民爆器材专卖有限公司', '黑KA9409', 5, '春星', '春星', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-29 08:19:42', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (906, '七台河海外民爆器材专卖有限公司', '黑KA9747', 5, '春星', '春星', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (907, '七台河海外民爆器材专卖有限公司', '黑KA8132', 5, '春星', '春星', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (908, '哈尔滨海外爆破工程有限公司', '黑A9Q1H6', 8, '长城', '长城', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (909, '哈尔滨海外爆破工程有限公司', '黑A2MX52', 8, '长城', '长城', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (910, '哈尔滨海外爆破工程有限公司', '黑A3P7P2', 8, '长城', '长城', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (911, '哈尔滨海外爆破工程有限公司', '黑A2L9J0', 8, '长城', '长城', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (912, '七台河海外民爆器材专卖有限公司', '黑KF60303', 19, '比亚迪', '比亚迪', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (913, '哈尔滨海外爆破工程有限公司', '黑AT9X71', 8, '长城', '长城', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (914, '七台河海外民爆器材专卖有限公司', '黑KA6540', 10, '鸿星达', '鸿星达', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (915, '七台河海外民爆器材专卖有限公司', '黑AH181X', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (916, '七台河海外民爆器材专卖有限公司', '黑AG2R80', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (917, '哈爆宾县项目部', '黑LCD792', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (918, '七台河海外民爆器材专卖有限公司', '黑KH0010', 18, '奥迪', '奥迪', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (919, '七台河海外民爆器材专卖有限公司', '黑AW0Y60', 11, '哈弗', '哈弗', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (920, '哈爆突泉分公司', '黑AX5E25', 11, '哈弗', '哈弗', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (921, '哈爆突泉分公司', '黑K7K887', 7, '长城货', '长城货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (922, '黑龙江海外民爆器材有限公司', '黑KA7497', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (923, '七台河海外民爆器材专卖有限公司', '黑KA1821', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:07:37', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (924, '黑龙江海外爆破工程有限公司', '黑AG0P78', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (925, '哈爆宾县项目部', '黑KA6939', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (926, '哈爆宾县项目部', '黑KV4676', 9, '红宇', '红宇', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (927, '七台河海外民爆器材专卖有限公司', '黑KA4851', 9, '红宇', '红宇', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (928, '黑龙江海外民爆器材有限公司', '黑A00499', 17, '威尔法', '威尔法', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (929, '黑龙江海外民爆器材有限公司', '黑AX6988', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (930, '黑龙江海外房地产开发集团有限公司', '黑AG3H80', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (931, '七台河海外民爆器材专卖有限公司', '黑KG8142', 16, '江特', '江特', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (932, '哈爆宾县项目部', '黑AA1F93', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (933, '七台河海外民爆器材专卖有限公司', '黑KA7817', 10, '鸿星达', '鸿星达', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (934, '七台河海外民爆器材专卖有限公司', '黑KA7393', 10, '鸿星达', '鸿星达', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (935, '黑龙江海外民爆器材有限公司', '黑K1A652', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (936, '哈尔滨海外爆破工程有限公司', '黑AC8M12', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:33');
INSERT INTO `company_vehicles` VALUES (937, '黑龙江海外民爆器材有限公司', '黑A01126', 15, '奔驰', '奔驰', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (938, '七台河海外民爆器材专卖有限公司', '黑KA9790', 5, '春星', '春星', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (939, '七台河海外民爆器材专卖有限公司', '黑KA7571', 5, '春星', '春星', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (940, '哈爆突泉分公司', '黑A5NL13', 7, '长城货', '长城货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (941, '哈爆突泉分公司', '黑A7GE12', 7, '长城货', '长城货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (942, '哈爆突泉分公司', '黑A5PG13', 7, '长城货', '长城货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (943, '七台河海外民爆器材专卖有限公司', '黑A2PW70', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (944, '哈爆突泉分公司', '黑A3G1T0', 7, '长城货', '长城货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (945, '哈尔滨海外爆破工程有限公司', '黑ABN561', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (946, '哈尔滨海外爆破工程有限公司', '黑ABB373', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (947, '哈爆宾县项目部', '黑AF0L12', 14, '雪佛兰', '雪佛兰', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (948, '七台河海外民爆器材专卖有限公司', '黑A00599', 13, '雷克萨斯', '雷克萨斯', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (949, '七台河海外民爆器材专卖有限公司', '黑K81125', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (950, '七台河海外民爆器材专卖有限公司', '黑K81109', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (951, '七台河海外民爆器材专卖有限公司', '黑KA8838', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (952, '七台河海外民爆器材专卖有限公司', '黑KA6406', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 1, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (953, '哈爆突泉分公司', '黑KZ0826', 3, '希尔', '希尔', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (954, '哈爆宾县项目部', '黑AQ8J98', 6, '货', '货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (955, '黑龙江海外民爆器材有限公司', '黑AXE707', 12, '霸道', '霸道', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (956, '哈爆突泉分公司', '黑AC5W13', 6, '货', '货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (957, '哈爆突泉分公司', '黑AZ5H03', 6, '货', '货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (958, '哈尔滨海外爆破工程有限公司', '黑AT5V12', 6, '货', '货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (959, '哈尔滨海外爆破工程有限公司', '黑AT7E13', 4, '五菱', '五菱', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (960, '七台河海外民爆器材专卖有限公司', '黑KC6555', 12, '霸道', '霸道', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (961, '哈尔滨海外爆破工程有限公司', '黑AAP088', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (962, '哈尔滨海外爆破工程有限公司', '黑AAR470', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (963, '黑龙江海外民爆器材有限公司', '黑K1K280', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (964, '黑龙江海外民爆器材有限公司', '黑K1A285', 2, '丰田', '丰田', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (965, '哈尔滨海外爆破工程有限公司', '黑A295F5', 6, '货', '货', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');
INSERT INTO `company_vehicles` VALUES (966, '哈尔滨海外爆破工程有限公司', '黑K0L260', 1, '特三', '特三', NULL, NULL, NULL, '', 1, 0, 1, NULL, '2025-04-19 09:04:33', '2025-04-19 09:04:34');

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别（比如 中文的 部门 职级 岗位）',
  `code_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '比如下拉但 高管对应的value是1，存1',
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典解释比如下拉单的中文显示部分',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '暂定',
  `serial_number` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 370 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (309, '部门', '1', '高管', '', NULL);
INSERT INTO `dictionary` VALUES (310, '部门', '2', '顾问', '', NULL);
INSERT INTO `dictionary` VALUES (311, '部门', '3', '财务部', '', NULL);
INSERT INTO `dictionary` VALUES (312, '部门', '4', '安全技术部', '', NULL);
INSERT INTO `dictionary` VALUES (313, '部门', '5', '综合办公室', '', NULL);
INSERT INTO `dictionary` VALUES (314, '部门', '6', '生产调度部', '', NULL);
INSERT INTO `dictionary` VALUES (315, '部门', '7', '制药车间', '', NULL);
INSERT INTO `dictionary` VALUES (316, '部门', '8', '机修车间', '', NULL);
INSERT INTO `dictionary` VALUES (317, '部门', '9', '销售部', '', NULL);
INSERT INTO `dictionary` VALUES (318, '部门', '10', '车队', '', NULL);
INSERT INTO `dictionary` VALUES (319, '职级', '1', '总裁', '', NULL);
INSERT INTO `dictionary` VALUES (320, '职级', '2', '执行总裁', '', NULL);
INSERT INTO `dictionary` VALUES (321, '职级', '3', '副总裁', '', NULL);
INSERT INTO `dictionary` VALUES (322, '职级', '4', '总裁助理', '', NULL);
INSERT INTO `dictionary` VALUES (323, '职级', '5', '总经理', '', NULL);
INSERT INTO `dictionary` VALUES (324, '职级', '6', '副总经理', '', NULL);
INSERT INTO `dictionary` VALUES (325, '职级', '7', '总经理助理', '', NULL);
INSERT INTO `dictionary` VALUES (326, '职级', '8', '部门经理', '', NULL);
INSERT INTO `dictionary` VALUES (327, '职级', '9', '部门副经理', '', NULL);
INSERT INTO `dictionary` VALUES (328, '职级', '10', '业务员', '', NULL);
INSERT INTO `dictionary` VALUES (329, '职级', '11', '工人', '', NULL);
INSERT INTO `dictionary` VALUES (330, '岗位', '1', '高管', '', NULL);
INSERT INTO `dictionary` VALUES (331, '岗位', '2', '顾问', '', NULL);
INSERT INTO `dictionary` VALUES (332, '岗位', '3', '财务经理', '', NULL);
INSERT INTO `dictionary` VALUES (333, '岗位', '4', '财务副经理', '', NULL);
INSERT INTO `dictionary` VALUES (334, '岗位', '5', '出纳员', '', NULL);
INSERT INTO `dictionary` VALUES (335, '岗位', '6', '设备员', '', NULL);
INSERT INTO `dictionary` VALUES (336, '岗位', '7', '工艺员', '', NULL);
INSERT INTO `dictionary` VALUES (337, '岗位', '8', '安全员', '', NULL);
INSERT INTO `dictionary` VALUES (338, '岗位', '9', '安全技术部经理', '', NULL);
INSERT INTO `dictionary` VALUES (339, '岗位', '10', '理化员', '', NULL);
INSERT INTO `dictionary` VALUES (340, '岗位', '11', '人资兼办公室文员', '', NULL);
INSERT INTO `dictionary` VALUES (341, '岗位', '12', '办公室业务员', '', NULL);
INSERT INTO `dictionary` VALUES (342, '岗位', '13', '厨师', '', NULL);
INSERT INTO `dictionary` VALUES (343, '岗位', '14', '帮厨', '', NULL);
INSERT INTO `dictionary` VALUES (344, '岗位', '15', '保洁员', '', NULL);
INSERT INTO `dictionary` VALUES (345, '岗位', '16', '保安队长', '', NULL);
INSERT INTO `dictionary` VALUES (346, '岗位', '17', '保安', '', NULL);
INSERT INTO `dictionary` VALUES (347, '岗位', '18', '生产调度部副经理', '', NULL);
INSERT INTO `dictionary` VALUES (348, '岗位', '19', '调度', '', NULL);
INSERT INTO `dictionary` VALUES (349, '岗位', '20', '库员班长', '', NULL);
INSERT INTO `dictionary` VALUES (350, '岗位', '21', '成品库员', '', NULL);
INSERT INTO `dictionary` VALUES (351, '岗位', '22', '辅助库员', '', NULL);
INSERT INTO `dictionary` VALUES (352, '岗位', '23', '院勤', '', NULL);
INSERT INTO `dictionary` VALUES (353, '岗位', '24', '装卸队长', '', NULL);
INSERT INTO `dictionary` VALUES (354, '岗位', '25', '装卸班长', '', NULL);
INSERT INTO `dictionary` VALUES (355, '岗位', '26', '装卸工', '', NULL);
INSERT INTO `dictionary` VALUES (356, '岗位', '27', '制药车间副主任', '', NULL);
INSERT INTO `dictionary` VALUES (357, '岗位', '28', '制药车间班长', '', NULL);
INSERT INTO `dictionary` VALUES (358, '岗位', '29', '制药工人', '', NULL);
INSERT INTO `dictionary` VALUES (359, '岗位', '30', '机修车间主任', '', NULL);
INSERT INTO `dictionary` VALUES (360, '岗位', '31', '机修班长', '', NULL);
INSERT INTO `dictionary` VALUES (361, '岗位', '32', '跟班电钳工', '', NULL);
INSERT INTO `dictionary` VALUES (362, '岗位', '33', '电钳工', '', NULL);
INSERT INTO `dictionary` VALUES (363, '岗位', '34', '电钳工学徒', '', NULL);
INSERT INTO `dictionary` VALUES (364, '岗位', '35', '锅炉工', '', NULL);
INSERT INTO `dictionary` VALUES (365, '岗位', '36', '销售经理', '', NULL);
INSERT INTO `dictionary` VALUES (366, '岗位', '37', '销售业务员', '', NULL);
INSERT INTO `dictionary` VALUES (367, '岗位', '38', '大厅调度', '', NULL);
INSERT INTO `dictionary` VALUES (368, '岗位', '39', '行政司机', '', NULL);
INSERT INTO `dictionary` VALUES (369, '岗位', '40', '大车司机', '', NULL);

-- ----------------------------
-- Table structure for employee_file
-- ----------------------------
DROP TABLE IF EXISTS `employee_file`;
CREATE TABLE `employee_file`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `employee_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `department` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工部门',
  `rank` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工职级',
  `profession` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工岗位',
  `position_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存放位置编码',
  `state` int(1) NULL DEFAULT 1 COMMENT '状态 -0离职 -1在职',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pdf_file_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PDF附件地址',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `employee_code_unique`(`employee_code`) USING BTREE COMMENT '员工编码唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee_file
-- ----------------------------

-- ----------------------------
-- Table structure for file_position
-- ----------------------------
DROP TABLE IF EXISTS `file_position`;
CREATE TABLE `file_position`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `position_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置编码',
  `file_type` tinyint(2) NULL DEFAULT NULL COMMENT '档案类型 1 技术文件；2 人事文件，3行政文件，4其他文件',
  `region` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域',
  `cabinet_num` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜子编号',
  `cabinet_level` tinyint(4) NULL DEFAULT NULL COMMENT '柜子层级',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `position_code_unique`(`position_code`) USING BTREE COMMENT '档案位置编码唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 141 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '档案位置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_position
-- ----------------------------
INSERT INTO `file_position` VALUES (71, 'A-1-1', 1, 'A', '1', 1, '技术文件,在A区的1号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (72, 'A-1-2', 1, 'A', '1', 2, '技术文件,在A区的1号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (73, 'A-1-3', 1, 'A', '1', 3, '技术文件,在A区的1号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (74, 'A-1-4', 1, 'A', '1', 4, '技术文件,在A区的1号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (75, 'A-1-5', 1, 'A', '1', 5, '技术文件,在A区的1号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (76, 'A-2-1', 1, 'A', '2', 1, '技术文件,在A区的2号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (77, 'A-2-2', 1, 'A', '2', 2, '技术文件,在A区的2号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (78, 'A-2-3', 1, 'A', '2', 3, '技术文件,在A区的2号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (79, 'A-2-4', 1, 'A', '2', 4, '技术文件,在A区的2号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (80, 'A-2-5', 1, 'A', '2', 5, '技术文件,在A区的2号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (81, 'A-3-1', 1, 'A', '3', 1, '技术文件,在A区的3号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (82, 'A-3-2', 1, 'A', '3', 2, '技术文件,在A区的3号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (83, 'A-3-3', 1, 'A', '3', 3, '技术文件,在A区的3号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (84, 'A-3-4', 1, 'A', '3', 4, '技术文件,在A区的3号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (85, 'A-3-5', 1, 'A', '3', 5, '技术文件,在A区的3号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (86, 'A-4-1', 1, 'A', '4', 1, '技术文件,在A区的4号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (87, 'A-4-2', 1, 'A', '4', 2, '技术文件,在A区的4号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (88, 'A-4-3', 1, 'A', '4', 3, '技术文件,在A区的4号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (89, 'A-4-4', 1, 'A', '4', 4, '技术文件,在A区的4号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (90, 'A-4-5', 1, 'A', '4', 5, '技术文件,在A区的4号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (91, 'A-5-1', 1, 'A', '5', 1, '技术文件,在A区的5号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (92, 'A-5-2', 1, 'A', '5', 2, '技术文件,在A区的5号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (93, 'A-5-3', 1, 'A', '5', 3, '技术文件,在A区的5号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (94, 'A-5-4', 1, 'A', '5', 4, '技术文件,在A区的5号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (95, 'A-5-5', 1, 'A', '5', 5, '技术文件,在A区的5号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (96, 'B-6-1', 1, 'B', '6', 1, '技术文件,在B区的6号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (97, 'B-6-2', 1, 'B', '6', 2, '技术文件,在B区的6号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (98, 'B-6-3', 1, 'B', '6', 3, '技术文件,在B区的6号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (99, 'B-6-4', 1, 'B', '6', 4, '技术文件,在B区的6号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (100, 'B-6-5', 1, 'B', '6', 5, '技术文件,在B区的6号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (101, 'B-7-1', 1, 'B', '7', 1, '技术文件,在B区的7号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (102, 'B-7-2', 1, 'B', '7', 2, '技术文件,在B区的7号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (103, 'B-7-3', 1, 'B', '7', 3, '技术文件,在B区的7号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (104, 'B-7-4', 1, 'B', '7', 4, '技术文件,在B区的7号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (105, 'B-7-5', 1, 'B', '7', 5, '技术文件,在B区的7号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (106, 'B-8-1', 2, 'B', '8', 1, '人事文件,在B区的8号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (107, 'B-8-2', 2, 'B', '8', 2, '人事文件,在B区的8号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (108, 'B-8-3', 2, 'B', '8', 3, '人事文件,在B区的8号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (109, 'B-8-4', 2, 'B', '8', 4, '人事文件,在B区的8号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (110, 'B-8-5', 2, 'B', '8', 5, '人事文件,在B区的8号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (111, 'B-9-1', 2, 'B', '9', 1, '人事文件,在B区的9号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (112, 'B-9-2', 2, 'B', '9', 2, '人事文件,在B区的9号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (113, 'B-9-3', 2, 'B', '9', 3, '人事文件,在B区的9号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (114, 'B-9-4', 2, 'B', '9', 4, '人事文件,在B区的9号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (115, 'B-9-5', 2, 'B', '9', 5, '人事文件,在B区的9号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (116, 'B-10-1', 2, 'B', '10', 1, '人事文件,在B区的10号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (117, 'B-10-2', 2, 'B', '10', 2, '人事文件,在B区的10号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (118, 'B-10-3', 2, 'B', '10', 3, '人事文件,在B区的10号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (119, 'B-10-4', 2, 'B', '10', 4, '人事文件,在B区的10号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (120, 'B-10-5', 2, 'B', '10', 5, '人事文件,在B区的10号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (121, 'C-11-1', 3, 'C', '11', 1, '行政文件,在C区的11号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (122, 'C-11-2', 3, 'C', '11', 2, '行政文件,在C区的11号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (123, 'C-11-3', 3, 'C', '11', 3, '行政文件,在C区的11号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (124, 'C-11-4', 3, 'C', '11', 4, '行政文件,在C区的11号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (125, 'C-11-5', 3, 'C', '11', 5, '行政文件,在C区的11号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (126, 'C-12-1', 3, 'C', '12', 1, '行政文件,在C区的12号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (127, 'C-12-2', 3, 'C', '12', 2, '行政文件,在C区的12号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (128, 'C-12-3', 3, 'C', '12', 3, '行政文件,在C区的12号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (129, 'C-12-4', 3, 'C', '12', 4, '行政文件,在C区的12号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (130, 'C-12-5', 3, 'C', '12', 5, '行政文件,在C区的12号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (131, 'C-13-1', 3, 'C', '13', 1, '行政文件,在C区的13号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (132, 'C-13-2', 3, 'C', '13', 2, '行政文件,在C区的13号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (133, 'C-13-3', 3, 'C', '13', 3, '行政文件,在C区的13号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (134, 'C-13-4', 3, 'C', '13', 4, '行政文件,在C区的13号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (135, 'C-13-5', 3, 'C', '13', 5, '行政文件,在C区的13号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (136, 'C-14-1', 3, 'C', '14', 1, '行政文件,在C区的14号柜子的第1层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (137, 'C-14-2', 3, 'C', '14', 2, '行政文件,在C区的14号柜子的第2层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (138, 'C-14-3', 3, 'C', '14', 3, '行政文件,在C区的14号柜子的第3层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (139, 'C-14-4', 3, 'C', '14', 4, '行政文件,在C区的14号柜子的第4层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');
INSERT INTO `file_position` VALUES (140, 'C-14-5', 3, 'C', '14', 5, '行政文件,在C区的14号柜子的第5层', 'xiaoheige', '2025-05-23 08:17:51', '2025-05-23 08:17:51');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operation_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '操作类型 增加 1 修改 2 删除3',
  `url` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '相关接口路径',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志详情',
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES (1, 1, '/vehiclesOutwardCard/create', '{\"cardNo\":\"hw2025043009245701\",\"carNumber\":\"黑KA7713\",\"vehicleCustomersName\":\"嫩江\",\"vehicleCustomersId\":14,\"daysRemaining\":15,\"openDate\":{\"date\":{\"year\":2025,\"month\":4,\"day\":29},\"time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0}},\"warningTime\":{\"date\":{\"year\":2025,\"month\":5,\"day\":9},\"time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0}},\"expirationDate\":{\"date\":{\"year\":2025,\"month\":5,\"day\":14},\"time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0}},\"state\":1,\"remarks\":\"\",\"updateMan\":\"su20210122000001\"}', 'su20210122000001', '新增的车卡号:[hw2025043009245701]', '192.168.1.131', '2025-04-30 09:24:57', NULL);
INSERT INTO `system_log` VALUES (2, 2, '/employeeFile/employeeFileEdit', '{\"id\":4,\"employeeCode\":\"1-38\",\"name\":\"孙聪\",\"department\":\"财务部\",\"rank\":\"总裁助理\",\"profession\":\"跟班电钳工\",\"positionCode\":\"B-9-1\",\"state\":0,\"remarks\":\"测试\",\"updateMan\":\"su20210302000001\",\"updateTime\":{\"date\":{\"year\":2025,\"month\":5,\"day\":26},\"time\":{\"hour\":10,\"minute\":1,\"second\":25,\"nano\":288000000}}}', 'su20210302000001', '编辑人员信息记录:[1]条，记录ID是：[4]', '127.0.0.1', '2025-05-26 10:01:25', NULL);

-- ----------------------------
-- Table structure for system_resource
-- ----------------------------
DROP TABLE IF EXISTS `system_resource`;
CREATE TABLE `system_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限路径',
  `state` tinyint(4) NULL DEFAULT NULL COMMENT '0:无效 1:有效',
  `parent_id` int(16) NULL DEFAULT NULL COMMENT '父类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_resource
-- ----------------------------
INSERT INTO `system_resource` VALUES (1, '系统管理首页', '/viewJump/homepage', 1, 0);
INSERT INTO `system_resource` VALUES (5, '跳转临期车辆列表', '/viewJump/vehicleInsurance/query', 1, 1);
INSERT INTO `system_resource` VALUES (6, '跳转新增车辆', '/viewJump/vehicleInsurance/add', 1, 1);
INSERT INTO `system_resource` VALUES (7, '跳转车辆续保', '/viewJump/vehicleInsurance/renewInsurance', 1, 1);
INSERT INTO `system_resource` VALUES (8, '跳转修改密码', '/viewJump/vehicleInsurance/changePassword', 1, 1);
INSERT INTO `system_resource` VALUES (9, '跳转车辆保险信息分页列表', '/viewJump/vehicleInsurance/carInsuranceListPage', 1, 1);
INSERT INTO `system_resource` VALUES (10, '查询到期车辆', '/vehicleInsurance/listByExpirationReminder', 1, 1);
INSERT INTO `system_resource` VALUES (11, '查询所有车辆', '/vehicleInsurance/listAll', 1, 1);
INSERT INTO `system_resource` VALUES (12, '批量插入车数据', '/vehicleInsurance/batchInsert', 1, 1);
INSERT INTO `system_resource` VALUES (13, '车辆续保', '/vehicleInsurance/renewInsurance', 1, 1);
INSERT INTO `system_resource` VALUES (14, '查询所有车辆承保信息分页', '/vehicleInsurance/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (15, '跳转Excel导入车辆信息', '/viewJump/vehicleInsurance/importVehicleInsuranceExcel', 1, 1);
INSERT INTO `system_resource` VALUES (16, 'Excel导入车辆信息', '/vehicleInsurance/importVehicleInsuranceExcel', 1, 1);
INSERT INTO `system_resource` VALUES (17, '跳转到车辆信息页面', '/viewJump/companyVehicles/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (18, '查询所有车辆信息分页', '/companyVehicles/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (19, '跳转给车开卡页面', '/viewJump/companyVehicles/renewInsurance', 1, 1);
INSERT INTO `system_resource` VALUES (20, '模糊查询所有客户信息', '/vehicleCustomers/listAll', 1, 1);
INSERT INTO `system_resource` VALUES (21, '车辆开卡提交', '/vehiclesOutwardCard/create', 1, 1);
INSERT INTO `system_resource` VALUES (22, '跳转到车辆开卡列表界面', '/viewJump/vehiclesOutwardCard/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (23, '车辆开卡分页列表', '/vehiclesOutwardCard/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (24, '车辆发车', '/vehiclesOutwardCard/cardDepart', 1, 1);
INSERT INTO `system_resource` VALUES (25, '查询可用且车队管理的车辆', '/companyVehicles/canDriveList', 1, 1);
INSERT INTO `system_resource` VALUES (26, '车辆还车', '/companyVehicles/returnCompanyVehicles', 1, 1);
INSERT INTO `system_resource` VALUES (27, '跳转人员信息档案上传页面', '/renShiViewJump/employeeFile/handleFileUpload', 1, 1);
INSERT INTO `system_resource` VALUES (28, '人员信息档案上传', '/employeeFile/handleFileUpload', 1, 1);
INSERT INTO `system_resource` VALUES (29, '人员信息档案下载', '/employeeFile/downloadFile', 1, 1);
INSERT INTO `system_resource` VALUES (30, '人员信息档案下载(含水印)', '/employeeFile/addWatermarkAndDownload', 1, 1);
INSERT INTO `system_resource` VALUES (31, '查询档案室位置结合', '/filePosition/listByQuery', 1, 1);
INSERT INTO `system_resource` VALUES (32, '查询字典', '/dictionary/listByQuery', 1, 1);
INSERT INTO `system_resource` VALUES (33, '跳转人员信息档案编辑界面', '/renShiViewJump/employeeFile/employeeFileEdit', 1, 1);
INSERT INTO `system_resource` VALUES (34, '跳转人员信息档案列表界面', '/renShiViewJump/employeeFile/employeeFileListPage', 1, 1);
INSERT INTO `system_resource` VALUES (35, '人员档案信息列表', '/employeeFile/listByPage', 1, 1);
INSERT INTO `system_resource` VALUES (36, '人员信息档案更新', '/employeeFile/employeeFileEdit', 1, 1);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_man` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_man` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0:管理员 1:普通',
  `state` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0:无效 1:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, 'admin', 'admin', '2020-05-12 10:53:39', '2020-05-12 10:53:39', '1', '1', 0, 1);
INSERT INTO `system_role` VALUES (8, '员工', '普通\n', '2021-01-27 17:16:26', '2021-01-27 17:16:26', 'wait', 'wait', 1, 1);
INSERT INTO `system_role` VALUES (9, '游客', '只能看不能改', '2025-04-07 14:02:30', '2025-04-07 14:02:30', 'wait', 'wait', 1, 1);
INSERT INTO `system_role` VALUES (10, '客服', '客服操作', '2021-09-13 10:31:04', '2021-09-13 10:31:04', 'wait', 'wait', 1, 1);
INSERT INTO `system_role` VALUES (11, '人事部', 'PDF有水印', '2025-05-26 00:47:03', '2025-05-26 00:47:03', 'wait', 'wait', 1, 1);
INSERT INTO `system_role` VALUES (12, '档案室管理员', '档案室所有权限', '2025-05-26 01:57:33', '2025-05-26 01:57:33', '', '', 0, 1);

-- ----------------------------
-- Table structure for system_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `system_role_resource`;
CREATE TABLE `system_role_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `resource_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 287 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role_resource
-- ----------------------------
INSERT INTO `system_role_resource` VALUES (131, 1, 1);
INSERT INTO `system_role_resource` VALUES (132, 1, 2);
INSERT INTO `system_role_resource` VALUES (133, 1, 3);
INSERT INTO `system_role_resource` VALUES (134, 1, 4);
INSERT INTO `system_role_resource` VALUES (135, 1, 5);
INSERT INTO `system_role_resource` VALUES (136, 1, 6);
INSERT INTO `system_role_resource` VALUES (137, 1, 7);
INSERT INTO `system_role_resource` VALUES (138, 1, 8);
INSERT INTO `system_role_resource` VALUES (139, 1, 9);
INSERT INTO `system_role_resource` VALUES (140, 1, 10);
INSERT INTO `system_role_resource` VALUES (141, 1, 11);
INSERT INTO `system_role_resource` VALUES (142, 1, 12);
INSERT INTO `system_role_resource` VALUES (143, 1, 13);
INSERT INTO `system_role_resource` VALUES (144, 1, 14);
INSERT INTO `system_role_resource` VALUES (194, 8, 1);
INSERT INTO `system_role_resource` VALUES (195, 8, 2);
INSERT INTO `system_role_resource` VALUES (196, 8, 3);
INSERT INTO `system_role_resource` VALUES (197, 8, 4);
INSERT INTO `system_role_resource` VALUES (198, 8, 5);
INSERT INTO `system_role_resource` VALUES (199, 8, 6);
INSERT INTO `system_role_resource` VALUES (200, 8, 7);
INSERT INTO `system_role_resource` VALUES (201, 8, 8);
INSERT INTO `system_role_resource` VALUES (202, 8, 9);
INSERT INTO `system_role_resource` VALUES (203, 8, 10);
INSERT INTO `system_role_resource` VALUES (204, 8, 11);
INSERT INTO `system_role_resource` VALUES (205, 8, 12);
INSERT INTO `system_role_resource` VALUES (206, 8, 13);
INSERT INTO `system_role_resource` VALUES (207, 8, 14);
INSERT INTO `system_role_resource` VALUES (208, 9, 1);
INSERT INTO `system_role_resource` VALUES (209, 9, 2);
INSERT INTO `system_role_resource` VALUES (210, 9, 3);
INSERT INTO `system_role_resource` VALUES (211, 9, 4);
INSERT INTO `system_role_resource` VALUES (212, 9, 5);
INSERT INTO `system_role_resource` VALUES (213, 9, 8);
INSERT INTO `system_role_resource` VALUES (214, 9, 9);
INSERT INTO `system_role_resource` VALUES (215, 9, 10);
INSERT INTO `system_role_resource` VALUES (216, 9, 11);
INSERT INTO `system_role_resource` VALUES (217, 9, 14);
INSERT INTO `system_role_resource` VALUES (218, 1, 15);
INSERT INTO `system_role_resource` VALUES (219, 1, 16);
INSERT INTO `system_role_resource` VALUES (220, 1, 17);
INSERT INTO `system_role_resource` VALUES (221, 1, 18);
INSERT INTO `system_role_resource` VALUES (222, 8, 17);
INSERT INTO `system_role_resource` VALUES (223, 8, 18);
INSERT INTO `system_role_resource` VALUES (224, 9, 17);
INSERT INTO `system_role_resource` VALUES (225, 9, 18);
INSERT INTO `system_role_resource` VALUES (226, 1, 19);
INSERT INTO `system_role_resource` VALUES (227, 1, 20);
INSERT INTO `system_role_resource` VALUES (228, 1, 21);
INSERT INTO `system_role_resource` VALUES (229, 8, 19);
INSERT INTO `system_role_resource` VALUES (230, 8, 20);
INSERT INTO `system_role_resource` VALUES (231, 8, 21);
INSERT INTO `system_role_resource` VALUES (233, 9, 20);
INSERT INTO `system_role_resource` VALUES (235, 1, 22);
INSERT INTO `system_role_resource` VALUES (236, 1, 23);
INSERT INTO `system_role_resource` VALUES (237, 8, 22);
INSERT INTO `system_role_resource` VALUES (238, 8, 23);
INSERT INTO `system_role_resource` VALUES (239, 9, 23);
INSERT INTO `system_role_resource` VALUES (241, 1, 24);
INSERT INTO `system_role_resource` VALUES (242, 8, 24);
INSERT INTO `system_role_resource` VALUES (244, 1, 25);
INSERT INTO `system_role_resource` VALUES (245, 8, 25);
INSERT INTO `system_role_resource` VALUES (246, 9, 25);
INSERT INTO `system_role_resource` VALUES (247, 1, 26);
INSERT INTO `system_role_resource` VALUES (248, 8, 26);
INSERT INTO `system_role_resource` VALUES (250, 9, 22);
INSERT INTO `system_role_resource` VALUES (251, 1, 27);
INSERT INTO `system_role_resource` VALUES (252, 1, 28);
INSERT INTO `system_role_resource` VALUES (253, 1, 29);
INSERT INTO `system_role_resource` VALUES (260, 1, 30);
INSERT INTO `system_role_resource` VALUES (263, 1, 31);
INSERT INTO `system_role_resource` VALUES (264, 1, 32);
INSERT INTO `system_role_resource` VALUES (265, 1, 33);
INSERT INTO `system_role_resource` VALUES (266, 1, 34);
INSERT INTO `system_role_resource` VALUES (267, 1, 35);
INSERT INTO `system_role_resource` VALUES (268, 1, 36);
INSERT INTO `system_role_resource` VALUES (269, 11, 30);
INSERT INTO `system_role_resource` VALUES (270, 11, 32);
INSERT INTO `system_role_resource` VALUES (271, 11, 34);
INSERT INTO `system_role_resource` VALUES (272, 11, 35);
INSERT INTO `system_role_resource` VALUES (273, 11, 1);
INSERT INTO `system_role_resource` VALUES (274, 11, 8);
INSERT INTO `system_role_resource` VALUES (275, 12, 27);
INSERT INTO `system_role_resource` VALUES (276, 12, 28);
INSERT INTO `system_role_resource` VALUES (277, 12, 29);
INSERT INTO `system_role_resource` VALUES (278, 12, 30);
INSERT INTO `system_role_resource` VALUES (279, 12, 31);
INSERT INTO `system_role_resource` VALUES (280, 12, 32);
INSERT INTO `system_role_resource` VALUES (281, 12, 33);
INSERT INTO `system_role_resource` VALUES (282, 12, 34);
INSERT INTO `system_role_resource` VALUES (283, 12, 35);
INSERT INTO `system_role_resource` VALUES (284, 12, 36);
INSERT INTO `system_role_resource` VALUES (286, 12, 1);

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆名称',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `state` int(1) NULL DEFAULT 1 COMMENT '状态 -0无效 -1有效',
  `age` int(5) NULL DEFAULT NULL,
  `city` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_cardno`(`user_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'su20200409000001', 'admin', NULL, 'c1615766e7a739add78dcdfcb694756b', NULL, '2020-04-09 22:31:15', '2020-04-09 22:31:15', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (21, 'su20210122000001', 'kongdepeng', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-01-22 16:43:30', '2021-07-22 14:06:17', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (22, 'su20210125000001', 'leihongfang', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-01-25 10:30:27', '2021-07-22 14:06:14', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (23, 'su20210125000002', 'daiyan', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-01-25 10:30:48', '2021-07-22 14:06:11', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (24, 'su20210126000001', 'liqi', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-01-26 09:26:06', '2021-07-22 14:06:08', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (25, 'su20210127000001', 'xuzitong', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-01-27 17:15:47', '2021-07-22 14:06:06', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (26, 'su20210222000001', 'chenjie', NULL, 'd93e9eff5044baf851c4314e41353a9f', NULL, '2021-02-22 10:24:41', '2021-02-22 10:24:41', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (27, 'su20210302000001', 'sfw', NULL, 'c1615766e7a739add78dcdfcb694756b', NULL, '2021-03-02 17:32:02', '2021-03-02 17:32:02', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (28, 'su20210315000001', 'sunxuhao', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-03-15 11:28:34', '2021-07-22 14:06:00', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (29, 'su20210323000001', 'songruiran', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-03-23 09:55:06', '2021-03-24 09:30:36', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (30, 'su20210329000001', 'caikeke', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-03-29 11:21:25', '2021-07-22 14:05:53', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (31, 'su20210406000001', 'taobaoquan', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-04-06 15:56:39', '2021-07-22 14:05:52', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (32, 'su20210407000001', 'shixianwu', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-04-07 10:12:14', '2021-09-13 10:29:39', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (33, 'su20210510000001', 'zzp', NULL, '2922e8b627363c1633e8ca912e7c8e89', NULL, '2021-05-10 10:20:01', '2021-07-22 14:05:37', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (34, 'su20210511000001', 'zhangchunman', '张春漫', 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-05-11 09:26:45', '2021-07-22 14:05:35', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (35, 'su20210524000001', 'wangjiyan', '汪吉彦', 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-05-24 10:03:09', '2021-07-22 14:05:33', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (36, 'su20210528000001', 'chedui', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-05-28 09:53:05', '2021-07-22 14:05:31', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (37, 'su20210615000001', 'haiwai', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-06-15 09:54:37', '2021-07-22 14:05:29', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (38, 'su20210618000001', 'szw', NULL, '84c618abf1331aab1fd1e72810f6601f', NULL, '2021-06-18 16:08:10', '2021-07-22 14:05:27', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (39, 'su20210619000001', 'sunfuwei', NULL, '89f6ac178bf9e995f9f037eb596690ac', NULL, '2021-06-19 17:12:35', '2021-06-19 17:12:35', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (40, 'su20210722000001', 'jiangQiaoWei', NULL, '71f16f036981234be8e222db36f4353a', NULL, '2021-07-22 14:06:45', '2021-07-22 14:06:45', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (41, 'su20210913000001', 'qimengni', NULL, '0d544b82f893a772f10ba8ccb874f815', NULL, '2021-09-13 10:30:24', '2021-09-13 10:30:24', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (42, 'su20210913000002', 'zhengyuqi', NULL, '6ac0cb786927943c4b4bae7ff6c8a323', NULL, '2021-09-13 10:34:23', '2021-10-18 16:33:00', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (43, 'su20210913000003', 'zhengxinmiao', NULL, 'ad7ec87e6772f60441e427915793e86b', NULL, '2021-09-13 10:35:25', '2021-09-13 10:35:25', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (44, 'su20211207000001', 'linhaojie', NULL, 'c50448c92e100d43d108f1c020f6421d', NULL, '2021-12-07 10:35:12', '2022-03-29 09:24:48', 0, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (45, 'su20220329000001', 'yangjie', NULL, 'aed8280514f3fadef46049dfe9f5d21d', NULL, '2022-03-29 09:28:10', '2022-03-29 09:28:10', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES (46, 'su20250426000001', '海外车队', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, '2021-05-28 09:53:05', '2021-07-22 14:05:31', 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 'su20200409000001', 1);
INSERT INTO `system_user_role` VALUES (40, 'su20210302000001', 1);
INSERT INTO `system_user_role` VALUES (41, 'su20210615000001', 9);
INSERT INTO `system_user_role` VALUES (44, 'su20210528000001', 8);
INSERT INTO `system_user_role` VALUES (45, 'su20210122000001', 8);
INSERT INTO `system_user_role` VALUES (46, 'su20250426000001', 8);
INSERT INTO `system_user_role` VALUES (47, 'su20210511000001', 11);
INSERT INTO `system_user_role` VALUES (48, 'su20210524000001', 12);

-- ----------------------------
-- Table structure for user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `user_base_info`;
CREATE TABLE `user_base_info`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编号，内部通信',
  `user_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称，唯一性',
  `user_nick_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `user_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '2020-40-24/15876924365901809.jpeg' COMMENT '用户图像',
  `user_email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_source` tinyint(4) NOT NULL DEFAULT 0 COMMENT '用户来源 0 - yunny 系统注册的，1 - 钉钉，2 - 微信',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `state` int(2) NULL DEFAULT 1 COMMENT '用户状态 1有效 0无效',
  `limit_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '用户限制类型  0-默认 1-白名单 2-黑名单',
  `sec_pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_base_info
-- ----------------------------
INSERT INTO `user_base_info` VALUES (1, 'u20200424000001', '周', 'yunny-2162', 'fc9209b522137e4909d9a11a7393e1dc', '15838492162', '2020-44-24/15877106563294416.png', '', 1, '2020-04-24 08:59:58', '2020-07-16 18:18:04', 1, 1, '');
INSERT INTO `user_base_info` VALUES (2, 'u20200424000002', '赵利东', '赵利东', 'f9d4eef946bfeb74c9f016e469eeeb37', '18838028219', '2020-44-24/15877106563294416.png', '', 1, '2020-06-14 09:49:17', '2023-11-09 13:08:13', 1, 0, '');
INSERT INTO `user_base_info` VALUES (3, 'u20200424000003', '', 'yunny-2998', 'f9d4eef946bfeb74c9f016e469eeeb37', '4567892998123', '2020-44-24/15877106563294416.png', '', 1, '2020-04-24 09:54:25', '2021-03-10 16:28:29', 1, 0, '');
INSERT INTO `user_base_info` VALUES (5, 'u20200424000005', '', 'yunny-7431', '1e9366dceeecf56e8c0dbd7ce51aaea6', '23819487431', '2020-44-24/15877106563294416.png', '', 1, '2020-04-24 15:11:04', '2020-04-28 09:08:23', 1, 0, '');
INSERT INTO `user_base_info` VALUES (6, 'u20200424000006', '库', '库从勇', '607838ce5241af8e60db337c7f9ca6f5', '12340803526', '2020-44-24/15877106563294416.png', '', 1, '2020-04-24 15:55:55', '2023-11-09 13:08:08', 1, 0, 'fc9209b522137e4909d9a11a7393e1dc');
INSERT INTO `user_base_info` VALUES (7, 'u20200424000007', '代old', 'yunny-0203', '465501468c276aed6875521dae71b85b', '23695650203', '2020-44-24/15877106563294416.png', '', 1, '2020-04-24 16:30:03', '2020-04-28 09:35:00', 1, 0, '');
INSERT INTO `user_base_info` VALUES (8, 'u20200426000001', '伟', '陈', '465501468c276aed6875521dae71b85b', '18368073918', '2020-44-24/15877106563294416.png', '', 1, '2020-04-26 16:15:12', '2020-09-07 14:20:18', 1, 0, '');
INSERT INTO `user_base_info` VALUES (11, 'u20200428000001', '的', 'yunny-5907', '31ce8c1413944c9037bd3c513addafa4', '15061235907', '2020-44-24/15877106563294416.png', '', 1, '2020-04-28 10:59:12', '2025-03-25 00:16:41', 1, 0, '');
INSERT INTO `user_base_info` VALUES (12, 'u20200429000001', '', 'yunny-4498', '3ecdb04b06c2b858e0ecd23d3451d5c8', '23858104498', '2020-44-24/15877106563294416.png', '', 1, '2020-04-29 09:54:46', '2020-06-04 15:49:32', 1, 0, '');
INSERT INTO `user_base_info` VALUES (13, 'u20200429000002', '', '高峰', 'ac76dd8471cbc54ae8153fddc2f985d4', '18367178973', '2020-44-24/15877106563294416.png', '', 1, '2020-04-29 10:01:23', '2023-11-09 13:10:16', 1, 0, '');
INSERT INTO `user_base_info` VALUES (14, 'u20200429000003', '', '你是福建基地数据你是福建基地数', '3ecdb04b06c2b858e0ecd23d3451d5c8', '33819487431', '2020-44-24/15877106563294416.png', '', 1, '2020-04-29 10:31:51', '2020-04-30 10:57:40', 1, 0, '');
INSERT INTO `user_base_info` VALUES (15, 'u20200429000004', '', 'yunny-3253', 'ac76dd8471cbc54ae8153fddc2f985d4', '18505813253', '2020-44-24/15877106563294416.png', '', 1, '2020-04-29 11:08:34', '2020-04-29 11:08:34', 1, 0, '');
INSERT INTO `user_base_info` VALUES (16, 'u20200429000005', '', '徐俊杰', '9d51b170c11becc43f505ed561b2223d', '15738669005', '2020-44-24/15877106563294416.png', '', 1, '2020-04-29 11:26:39', '2023-11-09 13:09:59', 1, 0, '');
INSERT INTO `user_base_info` VALUES (17, 'u20200430000001', '', 'yunny-6102', 'a53ce0cfa0edbd3fa0fbfaf9833059ce', '17610606108', '2020-44-24/15877106563294416.png', '', 1, '2020-04-30 09:26:24', '2021-03-09 16:40:34', 1, 0, '');
INSERT INTO `user_base_info` VALUES (18, 'u20200430000002', '', '小黑哥', '8181528d525333c7a2cf6c125dd0cf38', '18158516825', '2020-44-24/15877106563294416.png', '', 1, '2020-04-30 10:28:53', '2021-03-05 11:12:40', 1, 0, '');
INSERT INTO `user_base_info` VALUES (20, 'u20200430000004', '', 'yunny-1185', '9d51b170c11becc43f505ed561b2223d', '15268161185', '2020-44-24/15877106563294416.png', '', 1, '2020-04-30 16:49:01', '2020-04-30 16:49:01', 1, 0, '');
INSERT INTO `user_base_info` VALUES (21, 'u20200506000001', '', '李化喻', '12a8c84c2eb4b3643da0f4ab1f721be4', '15110628871', '2020-44-24/15877106563294416.png', '', 1, '2020-05-06 14:06:22', '2023-11-09 13:16:14', 1, 0, '');
INSERT INTO `user_base_info` VALUES (22, 'u20200506000002', '', 'yunny-7431', '3ecdb04b06c2b858e0ecd23d3451d5c8', '33819487431', '2020-44-24/15877106563294416.png', '', 1, '2020-05-06 19:49:45', '2020-05-15 18:09:18', 1, 0, '');
INSERT INTO `user_base_info` VALUES (23, 'u20200515000001', '', 'yunny-7431', '3ecdb04b06c2b858e0ecd23d3451d5c8', '13819487431', '2020-44-24/15877106563294416.png', '', 1, '2020-05-15 18:10:10', '2020-05-15 18:10:10', 1, 0, '');
INSERT INTO `user_base_info` VALUES (24, 'u20200529000001', '', 'yunny-0011', '3ecdb04b06c2b858e0ecd23d3451d5c8', '10000000011', '2020-44-24/15877106563294416.png', '', 1, '2020-05-29 17:35:18', '2020-05-29 17:35:18', 1, 0, '');
INSERT INTO `user_base_info` VALUES (25, 'u20200717000001', '', '代艳', '3ecdb04b06c2b858e0ecd23d3451d5c8', '10000000023', '2020-44-24/15877106563294416.png', '', 1, '2020-07-17 09:31:37', '2020-07-17 14:49:05', 1, 0, '');
INSERT INTO `user_base_info` VALUES (27, 'u20201217000001', '', '2998', '3ecdb04b06c2b858e0ecd23d3451d5c8', '12345672998321', '2020-44-24/15877106563294416.png', '', 1, '2020-12-17 09:41:54', '2021-03-10 16:28:22', 1, 0, 'fc9209b522137e4909d9a11a7393e1dc');
INSERT INTO `user_base_info` VALUES (29, 'u20201217000003', '', '小可爱可爱', 'ecfb75f136c44bc3f5d5913a14013754', '1239565020302000', '2020-44-24/15877106563294416.png', '', 1, '2020-12-17 09:43:44', '2021-03-10 16:58:26', 1, 0, 'ecfb75f136c44bc3f5d5913a14013754');
INSERT INTO `user_base_info` VALUES (34, 'u20210120000001', '', 'yunny-2009', 'fc9209b522137e4909d9a11a7393e1dc', '12371892009123', '2020-44-24/15877106563294416.png', '', 1, '2021-01-20 11:34:26', '2021-03-10 17:04:30', 1, 1, '90e9d1d9c7cf2e99391f0026558eff5c');
INSERT INTO `user_base_info` VALUES (36, 'u20210305000002', '', '小黑', 'e10adc3949ba59abbe56e057f20f883e', '18158516826', '2020-44-24/15877106563294416.png', '', 1, '2021-03-05 11:39:39', '2025-03-31 17:14:57', 1, 0, '');
INSERT INTO `user_base_info` VALUES (37, 'u20210309000001', '', 'yunny-6102', '883e0f752e5e59c8dbb0ad5f2910ba61', '17610606112', '2020-44-24/15877106563294416.png', '', 1, '2021-03-09 18:30:11', '2021-03-10 11:27:24', 1, 0, '');
INSERT INTO `user_base_info` VALUES (38, 'u20210310000001', '', 'yunny-6102', '883e0f752e5e59c8dbb0ad5f2910ba61', '17610606107', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 11:28:50', '2021-03-16 15:56:13', 1, 0, '');
INSERT INTO `user_base_info` VALUES (39, 'u20210310000002', '', 'yunny-2009', 'fc9209b522137e4909d9a11a7393e1dc', '12371892009', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 14:40:18', '2021-03-10 17:04:22', 1, 0, '90e9d1d9c7cf2e99391f0026558eff5c');
INSERT INTO `user_base_info` VALUES (40, 'u20210310000003', '', 'yunny-2998', 'fc9209b522137e4909d9a11a7393e1dc', '123457929982546', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 14:41:25', '2021-03-10 16:28:12', 1, 0, 'fc9209b522137e4909d9a11a7393e1dc');
INSERT INTO `user_base_info` VALUES (41, 'u20210310000004', '', '我想你们了翱游', 'ecfb75f136c44bc3f5d5913a14013754', '12345650203', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 14:43:38', '2025-03-25 00:17:55', 1, 0, 'ecfb75f136c44bc3f5d5913a14013754');
INSERT INTO `user_base_info` VALUES (43, 'u20210310000006', '', 'yunny-0203', 'ecfb75f136c44bc3f5d5913a14013754', '12345695650203', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 16:14:45', '2021-03-10 17:00:56', 1, 0, '');
INSERT INTO `user_base_info` VALUES (44, 'u20210310000007', '', 'yunny-2998', 'fc9209b522137e4909d9a11a7393e1dc', '13454792998', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 16:27:52', '2021-04-25 13:53:48', 1, 1, 'fc9209b522137e4909d9a11a7393e1dc');
INSERT INTO `user_base_info` VALUES (45, 'u20210310000008', '', 'yunny-3526', 'fc9209b522137e4909d9a11a7393e1dc', '13750803526', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 16:41:09', '2021-03-11 10:37:10', 1, 0, 'fc9209b522137e4909d9a11a7393e1dc');
INSERT INTO `user_base_info` VALUES (46, 'u20210310000009', '', '测试小可爱', 'ecfb75f136c44bc3f5d5913a14013754', '13695650203', '2020-44-24/15877106563294416.png', '', 1, '2021-03-10 17:06:03', '2021-06-16 15:58:21', 1, 0, 'ecfb75f136c44bc3f5d5913a14013754');
INSERT INTO `user_base_info` VALUES (49, 'u20210317000002', '', 'yunny-6102', '883e0f752e5e59c8dbb0ad5f2910ba61', '17610606102123', '2020-44-24/15877106563294416.png', '', 1, '2021-03-17 14:11:16', '2021-03-18 13:54:59', 1, 0, '');
INSERT INTO `user_base_info` VALUES (51, 'u20210318000001', '', 'yunny-6102', '883e0f752e5e59c8dbb0ad5f2910ba61', '17610606102111', '2020-44-24/15877106563294416.png', '', 1, '2021-03-18 13:58:53', '2021-03-29 14:02:36', 1, 0, '');
INSERT INTO `user_base_info` VALUES (52, 'u20210330000001', '', 'caikeke', 'eaf2fa0e3b872c48dda53d519f791d4b', '18435140010', '2020-44-24/15877106563294416.png', '', 1, '2021-03-30 10:20:58', '2021-04-01 15:22:08', 1, 1, '');
INSERT INTO `user_base_info` VALUES (53, 'u20210413000001', '', 'yunny-3913', 'd4fa99fcfbe9dd42011691af097fd849', '18368073913', '2020-44-24/15877106563294416.png', '', 1, '2021-04-13 14:41:12', '2021-04-13 16:45:08', 1, 0, '');
INSERT INTO `user_base_info` VALUES (54, 'u20210511000001', '', '霍羽', '1736db612d32e85db84209cf3df1de2e', '18606697819', '2020-44-24/15877106563294416.png', '', 1, '2021-05-11 17:48:56', '2023-11-09 13:14:49', 1, 0, '');
INSERT INTO `user_base_info` VALUES (55, 'u20210512000001', '', '何鑫', 'ecfb75f136c44bc3f5d5913a14013754', '13698658456', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 14:58:54', '2023-11-09 13:14:07', 1, 0, '');
INSERT INTO `user_base_info` VALUES (56, 'u20210512000002', '', '张鹏', 'ecfb75f136c44bc3f5d5913a14013754', '18345698210', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:02:03', '2023-11-09 13:14:14', 1, 0, '');
INSERT INTO `user_base_info` VALUES (57, 'u20210512000003', '', '何栋', 'ecfb75f136c44bc3f5d5913a14013754', '13412568521', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:04:35', '2023-11-09 13:13:55', 1, 0, '');
INSERT INTO `user_base_info` VALUES (58, 'u20210512000004', '', '徐文丽', 'ecfb75f136c44bc3f5d5913a14013754', '15014587536', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:06:36', '2023-11-09 13:15:37', 1, 0, '');
INSERT INTO `user_base_info` VALUES (59, 'u20210512000005', '', 'yunny-4521', 'ecfb75f136c44bc3f5d5913a14013754', '14725814521', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:08:03', '2021-05-12 15:08:03', 1, 0, '');
INSERT INTO `user_base_info` VALUES (60, 'u20210512000006', '', 'yunny-6938', 'ecfb75f136c44bc3f5d5913a14013754', '18754216938', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:09:07', '2021-05-12 15:09:07', 1, 0, '');
INSERT INTO `user_base_info` VALUES (61, 'u20210512000007', '', 'yunny-1212', 'ecfb75f136c44bc3f5d5913a14013754', '15687451212', '2020-44-24/15877106563294416.png', '', 1, '2021-05-12 15:11:50', '2021-05-12 15:11:50', 1, 0, '');
INSERT INTO `user_base_info` VALUES (62, 'u20210528000001', '', '雷红芳', 'fc9209b522137e4909d9a11a7393e1dc', '15527678623', '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-05-28 16:50:51', '2023-11-09 13:16:50', 1, 0, '');
INSERT INTO `user_base_info` VALUES (68, 'u20210607000006', '', '王兴禹', '72530f5c30eaebd1cc30cec4ad301dd7', '13998597451', '2020-44-24/15877106563294416.png', '', 1, '2021-06-07 10:47:58', '2023-11-09 13:16:00', 1, 0, '');
INSERT INTO `user_base_info` VALUES (69, 'u20210607000007', '', '周起帆', '72530f5c30eaebd1cc30cec4ad301dd7', '13385868148', '2020-44-24/15877106563294416.png', '', 1, '2021-06-07 10:51:38', '2023-11-09 13:14:40', 1, 0, '');
INSERT INTO `user_base_info` VALUES (70, 'u20210609000001', '', 'yunny-4498', 'fc9209b522137e4909d9a11a7393e1dc', '13858104498', '2020-44-24/15877106563294416.png', '', 1, '2021-06-09 10:15:39', '2021-06-09 10:15:39', 1, 0, '');
INSERT INTO `user_base_info` VALUES (71, 'u20210610000001', '', 'yunny-0203', 'ecfb75f136c44bc3f5d5913a14013754', '136956502030', '2020-44-24/15877106563294416.png', '', 1, '2021-06-10 17:22:22', '2021-06-10 17:27:51', 1, 0, '');
INSERT INTO `user_base_info` VALUES (77, 'u20210615000006', '', 'yunny-1505', '1466c0ce96c0a5f5cd9c569e34e06763', '15554051505', '2020-44-24/15877106563294416.png', '', 0, '2021-06-15 15:13:04', '2021-06-28 18:08:19', 1, 0, '');
INSERT INTO `user_base_info` VALUES (78, 'u20210616000001', '', 'yunny-4632', 'ecfb75f136c44bc3f5d5913a14013754', '18855804632', '2020-44-24/15877106563294416.png', '', 0, '2021-06-16 16:18:35', '2021-06-16 16:18:35', 1, 0, '');
INSERT INTO `user_base_info` VALUES (119, 'u20210623000001', NULL, '雷创', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 11:36:27', '2021-06-23 17:06:31', 1, 0, '');
INSERT INTO `user_base_info` VALUES (120, 'u20210623000002', NULL, '张泽平', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 11:36:27', '2021-06-23 11:36:27', 1, 0, '');
INSERT INTO `user_base_info` VALUES (121, 'u20210623000003', NULL, '代艳', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 11:36:27', '2021-06-23 11:36:27', 1, 0, '');
INSERT INTO `user_base_info` VALUES (122, NULL, NULL, NULL, NULL, NULL, '2020-40-24/15876924365901809.jpeg', NULL, 0, '2021-06-23 14:36:25', '2021-06-23 14:36:25', 1, 0, '');
INSERT INTO `user_base_info` VALUES (123, 'u20210623000004', NULL, '徐梓桐', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 16:51:21', '2021-06-23 16:51:21', 1, 0, '');
INSERT INTO `user_base_info` VALUES (124, 'u20210623000005', NULL, '贾福全', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 16:51:21', '2021-06-23 16:51:21', 1, 0, '');
INSERT INTO `user_base_info` VALUES (129, 'u2021062300000a', NULL, '蔡克克', '2b25d27a27716dfc8a6df68c4c8cf293', NULL, '2020-40-24/15876924365901809.jpeg', NULL, 1, '2021-06-23 18:30:09', '2021-06-23 18:30:09', 1, 0, '');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号',
  `user_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `user_nick_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
  `user_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户图像',
  `user_email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户邮箱',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `state` int(2) NOT NULL DEFAULT 1 COMMENT '用户状态 1有效 0无效',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0 男 1 女 2 待定',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `role_id` bigint(64) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `state` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 无效 0 有效 1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for vehicle_Insurance
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_Insurance`;
CREATE TABLE `vehicle_Insurance`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `underwriting_date` datetime NULL DEFAULT NULL COMMENT '承保日期',
  `policy_expiry_alert` datetime NULL DEFAULT NULL COMMENT '承保日期提醒时间',
  `insured` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投保人',
  `car_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `car_type` tinyint(4) NOT NULL COMMENT '车型:1 特三, 2丰田, 3 希尔, 4五菱, 5春星, 6货, 7长城货, 8长城， 9 红宇，10 鸿星达，11 哈弗，12 霸道\r\n13 雷克萨斯,14 雪佛兰,15 奔驰,16 江特,17 威尔法,18 奥迪,19 比亚迪,20 酷路泽,21 普拉多',
  `car_type_cn` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车型 中文名称',
  `compulsory_insurance` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '交强险',
  `vehicle_and_Vessel_Tax` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '车船税',
  `business_tax` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '商业税',
  `non_motor_insurance` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '非车险',
  `total` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '共计',
  `batch_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 819 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆承保日期表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vehicle_Insurance
-- ----------------------------
INSERT INTO `vehicle_Insurance` VALUES (743, '2025-01-04 00:00:00', '2025-12-04 00:00:00', '海外车队', '黑K0L260', 1, '特三', '756', '182.4', '2659.8', '3500', '7098.2', NULL, '2025-04-08 10:39:35', '2025-04-25 06:50:31', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (744, '2025-01-08 00:00:00', '2025-12-08 00:00:00', '海外车队', '黑A295F5', 6, '货', '720', '188.64', '3627.53', '0', '4536.17', NULL, '2025-04-08 10:39:35', '2025-04-25 06:58:25', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (745, '2025-01-15 00:00:00', '2025-12-15 00:00:00', '海外车队', '黑K1K280', 2, '丰田', '600', '480', '5059.18', '0', '6139.18', NULL, '2025-04-08 10:39:35', '2025-04-25 06:58:41', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (746, '2025-01-15 00:00:00', '2025-12-15 00:00:00', '海外车队', '黑K1A285', 2, '丰田', '600', '480', '5059.18', '0', '6139.18', NULL, '2025-04-08 10:39:35', '2025-04-25 06:58:54', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (748, '2025-03-04 00:00:00', '2026-02-04 00:00:00', '海外车队', '黑AAP088', 1, '特三', '1458', '1619.52', '7150.66', '3500', '13728.18', NULL, '2025-04-08 10:39:35', '2025-04-25 06:59:03', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (749, '2025-03-04 00:00:00', '2026-02-04 00:00:00', '海外车队', '黑AAR470', 1, '特三', '1458', '1619.52', '7150.66', '3500', '13728.18', NULL, '2025-04-08 10:39:35', '2025-04-25 06:59:12', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (750, '2025-03-11 00:00:00', '2026-02-11 00:00:00', '海外车队', '黑AC5W13', 6, '货', '720', '172.03', '4102.68', '0', '4994.71', NULL, '2025-04-08 10:39:35', '2025-04-25 06:59:29', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (751, '2025-03-11 00:00:00', '2026-02-11 00:00:00', '海外车队', '黑AZ5H03', 6, '货', '720', '172.03', '4102.68', '0', '4994.71', NULL, '2025-04-08 10:39:35', '2025-04-25 07:00:04', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (752, '2025-03-11 00:00:00', '2026-02-11 00:00:00', '海外车队', '黑AT5V12', 6, '货', '960', '172.03', '4102.68', '0', '5234.71', NULL, '2025-04-08 10:39:35', '2025-04-25 07:00:18', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (753, '2025-03-11 00:00:00', '2026-02-11 00:00:00', '海外车队', '黑AT7E13', 4, '五菱', '678', '420', '2709.4', '0', '3807.4', NULL, '2025-04-08 10:39:35', '2025-04-25 07:00:28', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (754, '2025-03-11 00:00:00', '2026-02-11 00:00:00', '海外车队', '黑KC6555', 12, '霸道', '678', '1800', '5685.99', '0', '8163.99', NULL, '2025-04-08 10:39:35', '2025-04-25 07:00:36', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (755, '2025-03-25 00:00:00', '2026-02-25 00:00:00', '海外车队', '黑AXE707', 12, '霸道', '678', '4500', '5159.88', '0', '10337.88', NULL, '2025-04-08 10:39:35', '2025-04-25 07:00:46', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (756, '2025-03-27 00:00:00', '2026-02-27 00:00:00', '海外车队', '黑AQ8J98', 6, '货', '720', '172.03', '3516.58', '0', '4408.61', NULL, '2025-04-08 10:39:35', '2025-04-25 07:01:01', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (757, '2025-04-11 00:00:00', '2026-03-11 00:00:00', '海外车队', '黑KZ0826', 3, '希尔', '648', '281.76', '3192.06', '3500', '7621.82', NULL, '2025-04-08 10:39:35', '2025-04-25 07:01:11', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (758, '2025-04-12 00:00:00', '2026-03-12 00:00:00', '海外车队', '黑K81125', 1, '特三', '756', '903.84', '4503.62', '0', '6163.46', NULL, '2025-04-08 10:39:35', '2025-04-25 07:01:38', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (759, '2025-04-12 00:00:00', '2026-03-12 00:00:00', '海外车队', '黑K81109', 1, '特三', '648', '903.84', '4503.62', '0', '6055.46', NULL, '2025-04-08 10:39:35', '2025-04-25 07:01:49', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (760, '2025-04-12 00:00:00', '2026-03-12 00:00:00', '海外车队', '黑KA8838', 1, '特三', '648', '912.96', '3953.6', '3500', '9014.56', NULL, '2025-04-08 10:39:35', '2025-04-25 07:01:57', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (761, '2025-04-12 00:00:00', '2026-03-12 00:00:00', '海外车队', '黑KA6406', 1, '特三', '648', '912.96', '3953.6', '3500', '9014.56', NULL, '2025-04-08 10:39:35', '2025-04-25 07:02:06', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (762, '2025-04-26 00:00:00', '2026-03-26 00:00:00', '小黑哥哈哈哈', '黑A00599', 13, '雷克萨斯', '678', '4500', '8451.6', '0', '13629.6', NULL, '2025-04-08 10:39:35', '2025-04-25 07:02:16', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (763, '2025-05-09 00:00:00', '2026-04-09 00:00:00', '海外车队', '黑AF0L12', 14, '雪佛兰', '600', '240', '2585.91', '0', '3425.91', NULL, '2025-04-08 10:39:35', '2025-04-25 07:02:27', 'su20210302000001', '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (764, '2024-05-13 00:00:00', '2025-04-13 00:00:00', '哈尔滨海外爆破工程有限公司', '黑ABN561', 1, '特三', '1944', '1389.12', '8415.47', '3500', '15248.59', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (765, '2024-05-13 00:00:00', '2025-04-13 00:00:00', '哈尔滨海外爆破工程有限公司', '黑ABB373', 1, '特三', '1944', '1619.52', '8415.47', '3500', '15478.99', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (766, '2024-05-21 00:00:00', '2025-04-21 00:00:00', '七台河海外民爆器材专卖有限公司', '黑A2PW70', 4, '五菱', '678', '420', '2573.1', '0', '3671.1', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (767, '2024-05-21 00:00:00', '2025-04-21 00:00:00', '哈爆突泉分公司', '黑A3G1T0', 7, '长城货', '720', '172.03', '3021.88', '0', '3913.91', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (768, '2024-05-21 00:00:00', '2025-04-21 00:00:00', '哈爆突泉分公司', '黑A5NL13', 7, '长城货', '840', '187.68', '4956.26', '0', '5983.94', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (769, '2024-05-21 00:00:00', '2025-04-21 00:00:00', '哈爆突泉分公司', '黑A7GE12', 7, '长城货', '840', '187.68', '4336.73', '0', '5364.41', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (770, '2024-05-21 00:00:00', '2025-04-21 00:00:00', '哈爆突泉分公司', '黑A5PG13', 7, '长城货', '840', '187.68', '4336.73', '0', '5364.41', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (771, '2024-05-26 00:00:00', '2025-04-26 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA9790', 5, '春星', '1080', '480.32', '5038.49', '3500', '10098.81', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (772, '2024-05-26 00:00:00', '2025-04-26 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA7571', 5, '春星', '1080', '480.32', '5038.49', '3500', '10098.81', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (773, '2024-05-27 00:00:00', '2025-04-27 00:00:00', '黑龙江海外民爆器材有限公司', '黑A01126', 15, '奔驰', '600', '480', '4892.66', '0', '5972.66', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (774, '2024-05-28 00:00:00', '2025-04-28 00:00:00', '哈尔滨海外爆破工程有限公司', '黑AC8M12', 4, '五菱', '678', '420', '2244.93', '0', '3342.93', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (775, '2024-06-07 00:00:00', '2025-05-07 00:00:00', '黑龙江海外民爆器材有限公司', '黑K1A652', 4, '五菱', '678', '420', '1491.23', '0', '2589.23', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (776, '2024-06-13 00:00:00', '2025-05-13 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA7817', 10, '鸿星达', '864', '710.88', '7641.51', '3500', '12716.39', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (777, '2024-06-13 00:00:00', '2025-05-13 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA7393', 10, '鸿星达', '1080', '710.88', '7641.51', '3500', '12932.39', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (778, '2024-06-29 00:00:00', '2025-05-29 00:00:00', '哈爆宾县项目部', '黑AA1F93', 4, '五菱', '678', '420', '1725.86', '0', '2823.86', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (779, '2024-06-30 00:00:00', '2025-05-30 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KG8142', 16, '江特', '648', '201.6', '1469.41', '3500', '5819.01', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (780, '2024-07-11 00:00:00', '2025-06-11 00:00:00', '黑龙江海外民爆器材有限公司', '黑A00499', 17, '威尔法', '678', '900', '6075.64', '0', '7653.64', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (781, '2024-07-11 00:00:00', '2025-06-11 00:00:00', '黑龙江海外民爆器材有限公司', '黑AX6988', 2, '丰田', '600', '480', '3890.31', '0', '4970.31', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (782, '2024-07-11 00:00:00', '2025-06-11 00:00:00', '黑龙江海外房地产开发集团有限公司', '黑AG3H80', 2, '丰田', '600', '210', '2559.74', '0', '3369.74', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (783, '2024-07-15 00:00:00', '2025-06-15 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA4851', 9, '红宇', '648', '432', '2816.69', '3500', '7396.69', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (784, '2024-07-29 00:00:00', '2025-06-29 00:00:00', '哈爆宾县项目部', '黑KA6939', 3, '希尔', '648', '724.8', '2521.92', '3500', '7394.72', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (785, '2024-07-29 00:00:00', '2025-06-29 00:00:00', '哈爆宾县项目部', '黑KV4676', 9, '红宇', '648', '180.48', '2656.2', '3500', '6984.68', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (786, '2024-08-03 00:00:00', '2025-07-03 00:00:00', '黑龙江海外爆破工程有限公司', '黑AG0P78', 2, '丰田', '600', '480', '3639.04', '0', '4719.04', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (787, '2024-08-05 00:00:00', '2025-07-05 00:00:00', '七台河海外民爆器材有限公司', '黑KA1821', 3, '希尔', '864', '724.8', '6052.57', '3500', '11141.37', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (788, '2024-08-16 00:00:00', '2025-07-16 00:00:00', '哈爆突泉分公司', '黑AX5E25', 11, '哈弗', '600', '480', '2325.08', '0', '3405.08', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (789, '2024-08-16 00:00:00', '2025-07-16 00:00:00', '哈爆突泉分公司', '黑K7K887', 7, '长城货', '720', '179.04', '2080.06', '0', '2979.1', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (790, '2024-08-16 00:00:00', '2025-07-16 00:00:00', '黑龙江海外民爆器材有限公司', '黑KA7497', 2, '丰田', '678', '1800', '3858.87', '0', '6336.87', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (791, '2024-08-22 00:00:00', '2025-07-22 00:00:00', '七台河海外民爆器材专卖有限公司', '黑AW0Y60', 11, '哈弗', '800', '480', '4648.26', '0', '5928.26', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (792, '2024-08-23 00:00:00', '2025-07-23 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KH0010', 18, '奥迪', '600', '1800', '8314.58', '0', '10714.58', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (793, '2024-08-28 00:00:00', '2025-07-28 00:00:00', '哈爆宾县项目部', '黑LCD792', 4, '五菱', '1130', '420', '2573', '0', '4123', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (794, '2024-08-29 00:00:00', '2025-07-29 00:00:00', '七台河海外民爆器材专卖有限公司', '黑AH181X', 2, '丰田', '600', '420', '1953.29', '0', '2973.29', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (795, '2024-08-29 00:00:00', '2025-07-29 00:00:00', '七台河海外民爆器材专卖有限公司', '黑AG2R80', 2, '丰田', '678', '480', '4425.28', '0', '5583.28', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (796, '2024-09-11 00:00:00', '2025-08-11 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA6540', 10, '鸿星达', '1080', '150.4', '5812.05', '3500', '10542.45', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (797, '2024-10-02 00:00:00', '2025-09-02 00:00:00', '哈尔滨海外爆破工程有限公司', '黑AT9X71', 8, '长城', '720', '172.03', '4416.9', '0', '5308.93', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (798, '2024-10-27 00:00:00', '2025-09-27 00:00:00', '哈尔滨海外爆破工程有限公司', '黑A2L9J0', 8, '长城', '700', '420', '3787.99', '0', '4907.99', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (799, '2024-10-23 00:00:00', '2025-09-23 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KF60303', 19, '比亚迪', '1000', '300', '4009.19', '0', '5309.19', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (800, '2024-10-31 00:00:00', '2025-09-30 00:00:00', '哈尔滨海外爆破工程有限公司', '黑A9Q1H6', 8, '长城', '1200', '210', '4645.8', '0', '6055.8', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (801, '2024-10-31 00:00:00', '2025-09-30 00:00:00', '哈尔滨海外爆破工程有限公司', '黑A2MX52', 8, '长城', '1200', '210', '4645.8', '0', '6055.8', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (802, '2024-10-31 00:00:00', '2025-09-30 00:00:00', '哈尔滨海外爆破工程有限公司', '黑A3P7P2', 8, '长城', '1000', '480', '3070.51', '0', '4550.51', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (803, '2024-11-07 00:00:00', '2025-10-07 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA9409', 5, '春星', '648', '720.48', '2417.57', '3500', '7286.05', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (804, '2024-11-07 00:00:00', '2025-10-07 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA9747', 5, '春星', '648', '720.48', '2417.57', '3500', '7286.05', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (805, '2024-11-07 00:00:00', '2025-10-07 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA8132', 5, '春星', '1080', '720.48', '3384.61', '3500', '8685.09', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (806, '2024-11-20 00:00:00', '2025-10-20 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA1826', 3, '希尔', '648', '724.8', '2417.57', '3500', '7290.37', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (807, '2024-11-20 00:00:00', '2025-10-20 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KJ6988', 3, '希尔', '864', '192.96', '5298.96', '3500', '9855.92', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (808, '2024-11-20 00:00:00', '2025-10-20 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA2626', 3, '希尔', '648', '724.8', '2417.57', '3500', '7290.37', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (809, '2024-11-20 00:00:00', '2025-10-20 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA6409', 3, '希尔', '648', '724.8', '2417.57', '3500', '7290.37', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (810, '2024-11-20 00:00:00', '2025-10-20 00:00:00', '七台河海外民爆器材专卖有限公司', '黑K96633', 20, '酷路泽', '678', '4500', '3384.13', '0', '8562.13', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (811, '2024-11-22 00:00:00', '2025-10-22 00:00:00', '哈尔滨海外爆破工程有限公司', '黑LJG779', 21, '普拉多', '678', '1800', '2992.1', '0', '5470.1', NULL, '2025-04-08 10:39:35', '2025-04-19 08:19:46', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (812, '2024-12-09 00:00:00', '2025-11-09 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA7713', 9, '红宇', '1080', '58.44', '5375.97', '3500', '10014.41', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (813, '2024-12-09 00:00:00', '2025-11-09 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA8767', 9, '红宇', '1080', '58.44', '5375.97', '3500', '10014.41', NULL, '2025-04-08 10:39:35', '2025-04-11 10:55:16', NULL, '承保日期是统一的');
INSERT INTO `vehicle_Insurance` VALUES (816, '2024-06-14 00:00:00', '2025-05-14 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA2881', 1, '特三', '648', '432', '', '3500', '4580.00', NULL, '2025-04-17 10:28:48', '2025-04-19 08:19:46', 'su20210302000001', '补充：交强');
INSERT INTO `vehicle_Insurance` VALUES (818, '2025-02-03 00:00:00', '2026-01-03 00:00:00', '七台河海外民爆器材专卖有限公司', '黑KA2881', 1, '特三', '', '', '4234.76', '', '4234.76', NULL, '2025-04-17 10:34:49', '2025-04-19 08:19:46', 'su20210302000001', '补充:商业险');

-- ----------------------------
-- Table structure for vehicle_customers
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_customers`;
CREATE TABLE `vehicle_customers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `specs` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格 Ф',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `phone_number` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户电话号',
  `serial_number` int(11) NULL DEFAULT NULL COMMENT '序号',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '车辆客户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vehicle_customers
-- ----------------------------
INSERT INTO `vehicle_customers` VALUES (3, '北方德隆', '北方德隆', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:10:47');
INSERT INTO `vehicle_customers` VALUES (4, '尚志', '尚志', 'Φ32袋装(250公斤)、Φ90', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:12:59');
INSERT INTO `vehicle_customers` VALUES (5, '翔远', '翔远', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:14:18');
INSERT INTO `vehicle_customers` VALUES (6, '拓峰', '拓峰', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:17:42');
INSERT INTO `vehicle_customers` VALUES (7, '勃利九龙', '勃利九龙', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (8, '林口', '林口', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (9, '勃利蓄能', '勃利蓄能', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (10, '春安龙井', '春安龙井', 'Φ32袋装(250公斤)、Φ70', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', 5, NULL, '2025-05-05 08:40:59', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (11, '黑河银泰洛克', '黑河银泰洛克', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (12, '红兴隆农垦', '红兴隆农垦', 'Φ32袋装(250公斤)', '北方德隆、尚志、翔远、拓峰、勃利九龙、林口、勃利蓄能、春安龙井、黑河银泰洛克、红兴隆农垦、继盛拓', '3', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:25:07');
INSERT INTO `vehicle_customers` VALUES (14, '嫩江', '嫩江', 'Φ32加长(0.3公斤)、Φ70、Φ100箱(24公斤)、Φ110袋(20公斤)', '嫩江、春安龙井、金诺安图、尚志安生、内蒙古瀚石鸡西、黑河宏安、德明二建', '1', 2, NULL, '2025-05-05 08:39:57', '2025-04-21 09:32:56');
INSERT INTO `vehicle_customers` VALUES (16, '金诺安图', '金诺安图', 'Φ70', '嫩江、春安龙井、金诺安图、尚志安生、内蒙古瀚石鸡西、黑河宏安、德明二建', '3', 8, NULL, '2025-05-05 08:42:47', '2025-04-21 09:32:56');
INSERT INTO `vehicle_customers` VALUES (18, '内蒙古瀚石鸡西', '内蒙古瀚石鸡西', 'Φ70、Φ80', '嫩江、春安龙井、金诺安图、尚志安生、内蒙古瀚石鸡西、黑河宏安、德明二建', '3', NULL, NULL, '2025-04-25 00:25:58', '2025-04-21 09:32:56');
INSERT INTO `vehicle_customers` VALUES (19, '黑河宏安', '黑河宏安', 'Φ70、Φ120袋(20公斤)、Φ120袋(20公斤)', '嫩江、春安龙井、金诺安图、尚志安生、内蒙古瀚石鸡西、黑河宏安、德明二建', '3', 6, NULL, '2025-05-05 08:42:16', '2025-04-21 09:32:56');
INSERT INTO `vehicle_customers` VALUES (20, '德明二建', '德明二建', 'Φ70', '嫩江、春安龙井、金诺安图、尚志安生、内蒙古瀚石鸡西、黑河宏安、德明二建', '3', NULL, NULL, '2025-04-21 09:32:56', '2025-04-21 09:32:56');
INSERT INTO `vehicle_customers` VALUES (21, '阿城', '阿城', 'Φ80', '阿城、平山、内蒙古瀚石方正、内蒙古瀚石鸡西、际兴爆破、德明广盛石场', '4', NULL, NULL, '2025-04-21 09:39:55', '2025-04-21 09:39:55');
INSERT INTO `vehicle_customers` VALUES (22, '平山', '平山', 'Φ80', '阿城、平山、内蒙古瀚石方正、内蒙古瀚石鸡西、际兴爆破、德明广盛石场', '4', NULL, NULL, '2025-04-21 09:39:55', '2025-04-21 09:39:55');
INSERT INTO `vehicle_customers` VALUES (25, '德明广盛石场', '德明广盛石场', 'Φ80', '阿城、平山、内蒙古瀚石方正、内蒙古瀚石鸡西、际兴爆破、德明广盛石场', '4', NULL, NULL, '2025-04-21 09:39:55', '2025-04-21 09:39:55');
INSERT INTO `vehicle_customers` VALUES (26, '突泉', '突泉', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', 1, NULL, '2025-05-05 08:39:40', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (27, '安庆平安', '安庆平安', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:46', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (28, '春安九台', '春安九台', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', 4, NULL, '2025-05-05 08:40:21', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (29, '春安双阳', '春安双阳', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:47', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (30, '滨达', '滨达', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', 3, NULL, '2025-05-05 08:41:51', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (31, '乾谊铁力', '乾谊铁力', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:49', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (32, '鹤岗顺隆', '鹤岗顺隆', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:50', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (34, '龙煤矿山', '龙煤矿山', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:51', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (35, '万腾', '万腾', 'Φ90', '突泉、安庆平安、春安九台、春安双阳、滨达、乾谊铁力、鹤岗顺隆、吉阳、龙煤矿山、尚志、万腾、凤才石场', '5', NULL, NULL, '2025-04-21 09:46:52', '2025-04-21 09:45:27');
INSERT INTO `vehicle_customers` VALUES (37, '保利', '保利', 'Φ90编织袋', '保利', '6', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:48:18');
INSERT INTO `vehicle_customers` VALUES (38, '德明林口', '德明林口', 'Φ100箱(24公斤)', '嫩江、德明林口、五常', '7', NULL, NULL, '2025-04-25 00:43:40', '2025-04-21 09:51:14');
INSERT INTO `vehicle_customers` VALUES (39, '五常', '五常', 'Φ100箱(24公斤)、Φ100袋(20公斤)', '嫩江、德明林口、五常', '7', NULL, NULL, '2025-04-25 00:46:06', '2025-04-21 09:51:14');
INSERT INTO `vehicle_customers` VALUES (40, '大兴安岭', '大兴安岭', 'Φ100袋(20公斤)', '大兴安岭、五常', '临时号码', NULL, NULL, '2025-04-25 03:13:52', '2025-04-25 00:40:02');
INSERT INTO `vehicle_customers` VALUES (41, '金诺', '金诺', 'Φ110袋(20公斤)', '金诺、宾县、木兰、嫩江', '临时号码', 7, NULL, '2025-05-05 08:42:45', '2025-04-25 00:53:30');
INSERT INTO `vehicle_customers` VALUES (42, '宾县', '宾县', 'Φ110袋(20公斤)、Ф130袋(21公斤)', '金诺、宾县、木兰、嫩江', '临时号码', NULL, NULL, '2025-04-25 03:55:35', '2025-04-25 00:53:30');
INSERT INTO `vehicle_customers` VALUES (43, '木兰', '木兰', 'Φ110袋(20公斤)', '金诺、宾县、木兰、嫩江', '临时号码', NULL, NULL, '2025-04-25 00:53:30', '2025-04-25 00:53:30');
INSERT INTO `vehicle_customers` VALUES (44, '阿城恒达', '阿城恒达', 'Φ120袋(20公斤)、Φ110袋(24公斤)、Φ120袋(24公斤)', '阿城恒达', '临时号码', NULL, NULL, '2025-04-25 03:55:27', '2025-04-25 03:19:18');
INSERT INTO `vehicle_customers` VALUES (45, '吉林吉辉', '吉林吉辉', 'Φ120袋(20公斤)、Φ120袋(20公斤)', '吉林吉辉、大兴安岭金马、黑河宏安、集贤', '临时号码', NULL, NULL, '2025-04-25 03:51:47', '2025-04-25 03:19:37');
INSERT INTO `vehicle_customers` VALUES (46, '大兴安岭金马', '大兴安岭金马', 'Φ120袋(20公斤)、Φ120袋(20公斤)', '吉林吉辉、大兴安岭金马、黑河宏安、集贤', '临时号码', NULL, NULL, '2025-04-25 03:51:47', '2025-04-25 03:19:37');
INSERT INTO `vehicle_customers` VALUES (47, '集贤', '集贤', 'Φ120袋(20公斤)、Φ120袋(20公斤)', '吉林吉辉、大兴安岭金马、黑河宏安、集贤', '临时号码', NULL, NULL, '2025-04-25 03:51:47', '2025-04-25 03:19:37');
INSERT INTO `vehicle_customers` VALUES (49, '福泰来一井', '福泰来一井', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (50, '庆安平安飞达', '庆安平安飞达', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (51, '无烟三井', '无烟三井', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (52, '金龙', '金龙', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (53, '德明慧泉五井', '德明慧泉五井', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (54, '双鸭山双辉', '双鸭山双辉', '2MΦ29', '福泰来一井、庆安平安飞达、无烟三井、金龙、德明慧泉五井、双鸭山双辉', '未知', NULL, NULL, '2025-04-25 04:28:29', '2025-04-25 04:28:29');
INSERT INTO `vehicle_customers` VALUES (55, '宝忠', '宝忠', '2MФ32', '宝忠、鸡西恒大安腾', '未知', NULL, NULL, '2025-04-25 04:28:48', '2025-04-25 04:28:48');
INSERT INTO `vehicle_customers` VALUES (56, '鸡西恒大安腾', '鸡西恒大安腾', '2MФ32', '宝忠、鸡西恒大安腾', '未知', NULL, NULL, '2025-04-25 04:28:48', '2025-04-25 04:28:48');
INSERT INTO `vehicle_customers` VALUES (57, '鹿山', '鹿山', '3MФ29、3MФ32', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:15:41', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (58, '向阳', '向阳', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (59, '七峰', '七峰', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (60, '铁麒', '铁麒', '3MФ29、3MФ35', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:15:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (61, '岚峰二井', '岚峰二井', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (62, '胜利一', '胜利一', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (63, '胜利六', '胜利六', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (64, '双鸭山隆中', '双鸭山隆中', '3MФ29、3MФ35', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:16:28', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (65, '无烟一井', '无烟一井', '3MФ29', '鹿山、向阳、七峰、铁麒、岚峰二井、胜利一、胜利六、双鸭山隆中、无烟一井', '未知', NULL, NULL, '2025-04-25 05:11:58', '2025-04-25 05:11:58');
INSERT INTO `vehicle_customers` VALUES (67, '瀚石依兰三矿', '瀚石依兰三矿', '3MФ35', '鹿山、铁麒、瀚石依兰三矿、双鸭山隆中', '未知', NULL, NULL, '2025-04-25 05:16:09', '2025-04-25 05:16:09');

-- ----------------------------
-- Table structure for vehicles_outward_card
-- ----------------------------
DROP TABLE IF EXISTS `vehicles_outward_card`;
CREATE TABLE `vehicles_outward_card`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `card_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车卡 卡号',
  `car_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '车牌号',
  `vehicle_customers_id` int(11) NULL DEFAULT NULL COMMENT '车辆客户表ID',
  `days_remaining` int(11) NULL DEFAULT 15 COMMENT '卡剩余天数，默认开卡都是15天',
  `open_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开卡时间',
  `warning_time` datetime NULL DEFAULT NULL COMMENT '提醒用卡时间（一般都是开卡时间+剩余天数-5天）',
  `expiration_date` datetime NULL DEFAULT NULL COMMENT '卡到期时间',
  `state` int(2) NULL DEFAULT 0 COMMENT '卡状态: 0 失效卡 已使用的卡，1 可用卡 可发车 ，3，到期未使用',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `update_man` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司车卡表（车队发车用的）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vehicles_outward_card
-- ----------------------------
INSERT INTO `vehicles_outward_card` VALUES (1, 'hw2025043009245701', '黑KA7713', 14, 15, '2025-04-29 00:00:00', '2025-05-09 00:00:00', '2025-05-14 00:00:00', 1, '', 'su20210122000001', '2025-04-30 01:24:57', '2025-04-30 01:24:57');

SET FOREIGN_KEY_CHECKS = 1;
