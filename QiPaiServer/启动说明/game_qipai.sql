/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50535
Source Host           : 127.0.0.1:3306
Source Database       : game_qipai

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2016-05-29 21:55:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(36) DEFAULT NULL,
  `admin_pwd` varchar(36) DEFAULT NULL,
  `login_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `logout_time` timestamp NULL DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `admin_ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('3', 'admin', 'DMF1ucDxtqgxw5niaXcmYQ==', '2016-04-22 10:24:23', null, '1', null);
INSERT INTO `admin` VALUES ('4', 'yqh', 'WpKkTLKgwyeUY3oQZD6rpw==', null, null, '0', null);
INSERT INTO `admin` VALUES ('5', 'h', 'DMF1ucDxtqgxw5niaXcmYQ==', null, null, '0', null);
INSERT INTO `admin` VALUES ('6', 'zyj', 'ICy5YqxZB1uWSwcVLSNLcA==', '2016-04-19 18:19:00', null, '1', null);

-- ----------------------------
-- Table structure for `charge`
-- ----------------------------
DROP TABLE IF EXISTS `charge`;
CREATE TABLE `charge` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` int(12) DEFAULT NULL,
  `money` int(8) DEFAULT NULL,
  `coin` int(12) DEFAULT NULL,
  `txn_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `from` varchar(50) CHARACTER SET utf8 COLLATE utf8_sinhala_ci DEFAULT NULL,
  `order_no` varchar(100) DEFAULT NULL,
  `ordre_state` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of charge
-- ----------------------------
INSERT INTO `charge` VALUES ('1', '20', '1', '100', '2016-05-18 01:14:59', 'PHONE', 'BLMM2016051801145920', '0');
INSERT INTO `charge` VALUES ('2', '20', '1', '100', '2016-05-18 01:15:46', 'PHONE', 'BLMM2016051801154620', '0');
INSERT INTO `charge` VALUES ('3', '20', '1', '100', '2016-05-18 01:16:17', 'PHONE', 'BLMM2016051801161720', '0');
INSERT INTO `charge` VALUES ('4', '20', '1', '100', '2016-05-18 01:39:38', 'PHONE', 'BLMM2016051801393820', '0');
INSERT INTO `charge` VALUES ('5', '20', '1', '100', '2016-05-18 01:43:11', 'PHONE', 'BLMM2016051801431120', '0');
INSERT INTO `charge` VALUES ('6', '20', '1', '100', '2016-05-18 01:44:22', 'PHONE', 'BLMM2016051801442220', '0');
INSERT INTO `charge` VALUES ('7', '20', '1', '100', '2016-05-18 01:44:54', 'PHONE', 'BLMM2016051801445420', '0');
INSERT INTO `charge` VALUES ('8', '20', '1', '100', '2016-05-18 01:45:20', 'PHONE', 'BLMM2016051801452020', '0');
INSERT INTO `charge` VALUES ('9', '20', '1', '100', '2016-05-18 01:46:21', 'PHONE', 'BLMM2016051801462120', '0');
INSERT INTO `charge` VALUES ('10', '20', '1', '100', '2016-05-18 01:47:06', 'PHONE', 'BLMM2016051801470620', '0');
INSERT INTO `charge` VALUES ('11', '20', '1', '100', '2016-05-18 01:54:23', 'PHONE', 'BLMM2016051801542320', '0');
INSERT INTO `charge` VALUES ('12', '20', '1', '100', '2016-05-18 01:54:26', 'PHONE', 'BLMM2016051801542620', '0');
INSERT INTO `charge` VALUES ('13', '20', '1', '100', '2016-05-18 01:54:31', 'PHONE', 'BLMM2016051801543120', '0');
INSERT INTO `charge` VALUES ('14', '20', '1', '100', '2016-05-18 01:58:55', 'PHONE', 'BLMM2016051801585520', '0');
INSERT INTO `charge` VALUES ('15', '20', '1', '100', '2016-05-18 02:01:16', 'PHONE', 'BLMM2016051802011620', '0');

-- ----------------------------
-- Table structure for `desk`
-- ----------------------------
DROP TABLE IF EXISTS `desk`;
CREATE TABLE `desk` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `desk_name` varchar(20) DEFAULT NULL,
  `room_id` int(12) DEFAULT NULL,
  `desk_status` int(1) DEFAULT NULL,
  `game_id` int(12) DEFAULT NULL,
  `is_vip_room` bit(1) DEFAULT NULL,
  `max_player` int(10) DEFAULT NULL,
  `min_player` int(10) DEFAULT NULL,
  `game_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of desk
-- ----------------------------

-- ----------------------------
-- Table structure for `game`
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `game_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES ('1', '四人三公');
INSERT INTO `game` VALUES ('2', '百人三公');
INSERT INTO `game` VALUES ('3', '疯狂VIP');
INSERT INTO `game` VALUES ('4', '五子棋');
INSERT INTO `game` VALUES ('100', '百搭玩法');
INSERT INTO `game` VALUES ('101', '拉霸游戏');

-- ----------------------------
-- Table structure for `labaconf`
-- ----------------------------
DROP TABLE IF EXISTS `labaconf`;
CREATE TABLE `labaconf` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `axis` int(5) DEFAULT NULL COMMENT '滚轮（1,2,3,4,5）',
  `cards` varchar(255) DEFAULT NULL COMMENT '卡牌范围,逗号分隔',
  `game_id` int(12) DEFAULT NULL COMMENT '游戏编号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of labaconf
-- ----------------------------

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `notice` varchar(255) DEFAULT NULL,
  `flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', 'andyandy', '0');

-- ----------------------------
-- Table structure for `rank`
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `game_id` int(12) DEFAULT NULL,
  `rank` int(10) DEFAULT NULL,
  `user_id` int(12) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `coin` int(10) DEFAULT NULL,
  `score` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=353 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES ('352', '100', '12', '23', '1234', '0', null);
INSERT INTO `rank` VALUES ('351', '100', '11', '22', '123', '0', null);
INSERT INTO `rank` VALUES ('350', '100', '10', '21', '12', '0', null);
INSERT INTO `rank` VALUES ('349', '100', '9', '10', '18764041111', '240', null);
INSERT INTO `rank` VALUES ('348', '100', '8', '11', '18755551235', '270', null);
INSERT INTO `rank` VALUES ('347', '100', '7', '19', '13610020363', '300', null);
INSERT INTO `rank` VALUES ('346', '100', '6', '17', '18715125123', '300', null);
INSERT INTO `rank` VALUES ('345', '100', '5', '14', '13615582656', '300', null);
INSERT INTO `rank` VALUES ('344', '100', '4', '18', '18715125124', '300', null);
INSERT INTO `rank` VALUES ('343', '100', '3', '13', '18650011401', '321', null);
INSERT INTO `rank` VALUES ('342', '100', '2', '12', '12456324563', '330', null);
INSERT INTO `rank` VALUES ('341', '100', '1', '20', '8', '22110792', null);

-- ----------------------------
-- Table structure for `reward`
-- ----------------------------
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward` (
  `reward_id` int(12) NOT NULL,
  `reward_desc` varchar(255) DEFAULT NULL,
  `money` int(12) unsigned zerofill DEFAULT NULL,
  `times` int(10) DEFAULT NULL,
  `score` int(12) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`reward_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward
-- ----------------------------
INSERT INTO `reward` VALUES ('1', '五张', '000000010000', '10000', '000000000000');
INSERT INTO `reward` VALUES ('2', '皇家同花顺', '000000001000', '1000', '000000000000');
INSERT INTO `reward` VALUES ('3', '同花顺', '000000000100', '100', '000000000000');
INSERT INTO `reward` VALUES ('4', '四张', '000000000001', '80', null);
INSERT INTO `reward` VALUES ('5', '葫芦', '000000000005', '60', null);
INSERT INTO `reward` VALUES ('6', '同花', '000000000002', '40', null);
INSERT INTO `reward` VALUES ('7', '顺子', '000000000007', '30', null);
INSERT INTO `reward` VALUES ('8', '三张', '000000000003', '20', null);
INSERT INTO `reward` VALUES ('9', '两对', '000000000004', '10', null);
INSERT INTO `reward` VALUES ('10', '8对以上', '000000000006', '5', null);
INSERT INTO `reward` VALUES ('215', '5个A', '000000000500', '500', null);
INSERT INTO `reward` VALUES ('214', null, null, '100', null);
INSERT INTO `reward` VALUES ('213', null, null, '40', null);
INSERT INTO `reward` VALUES ('212', null, null, '10', null);
INSERT INTO `reward` VALUES ('202', null, null, '4', null);
INSERT INTO `reward` VALUES ('203', null, null, '40', null);
INSERT INTO `reward` VALUES ('204', null, null, '80', null);
INSERT INTO `reward` VALUES ('205', null, null, '300', null);
INSERT INTO `reward` VALUES ('192', null, null, '4', null);
INSERT INTO `reward` VALUES ('193', null, null, '30', null);
INSERT INTO `reward` VALUES ('194', null, null, '60', null);
INSERT INTO `reward` VALUES ('195', null, null, '240', null);
INSERT INTO `reward` VALUES ('183', null, null, '20', null);
INSERT INTO `reward` VALUES ('184', null, null, '50', null);
INSERT INTO `reward` VALUES ('185', null, null, '200', null);
INSERT INTO `reward` VALUES ('173', null, null, '20', null);
INSERT INTO `reward` VALUES ('174', null, null, '40', null);
INSERT INTO `reward` VALUES ('175', null, null, '150', null);
INSERT INTO `reward` VALUES ('163', null, null, '20', null);
INSERT INTO `reward` VALUES ('164', null, null, '30', null);
INSERT INTO `reward` VALUES ('165', null, null, '100', null);
INSERT INTO `reward` VALUES ('153', null, null, '10', null);
INSERT INTO `reward` VALUES ('154', null, null, '20', null);
INSERT INTO `reward` VALUES ('155', null, null, '100', null);
INSERT INTO `reward` VALUES ('143', null, null, '10', null);
INSERT INTO `reward` VALUES ('144', null, null, '20', null);
INSERT INTO `reward` VALUES ('145', null, null, '80', null);
INSERT INTO `reward` VALUES ('133', null, null, '10', null);
INSERT INTO `reward` VALUES ('134', null, null, '20', null);
INSERT INTO `reward` VALUES ('135', null, null, '80', null);
INSERT INTO `reward` VALUES ('123', null, null, '10', null);
INSERT INTO `reward` VALUES ('124', null, null, '20', null);
INSERT INTO `reward` VALUES ('125', null, null, '60', null);
INSERT INTO `reward` VALUES ('113', null, null, '10', null);
INSERT INTO `reward` VALUES ('114', null, null, '20', null);
INSERT INTO `reward` VALUES ('115', null, null, '60', null);
INSERT INTO `reward` VALUES ('235', null, null, '12', null);
INSERT INTO `reward` VALUES ('234', null, null, '12', null);
INSERT INTO `reward` VALUES ('233', null, null, '12', null);
INSERT INTO `reward` VALUES ('501', null, '000000001000', '0', null);
INSERT INTO `reward` VALUES ('502', null, '000000000900', '0', null);
INSERT INTO `reward` VALUES ('503', null, '000000000800', '0', null);
INSERT INTO `reward` VALUES ('504', '', '000000000700', '0', null);
INSERT INTO `reward` VALUES ('505', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('506', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('507', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('508', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('509', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('510', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('511', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('512', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('513', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('514', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('515', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('516', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('517', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('518', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('519', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('520', '', '000000000600', '0', null);
INSERT INTO `reward` VALUES ('521', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('522', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('523', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('524', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('525', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('526', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('527', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('528', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('529', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('530', '', '000000000500', '0', null);
INSERT INTO `reward` VALUES ('531', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('532', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('533', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('534', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('535', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('536', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('537', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('538', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('539', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('540', '', '000000000400', '0', null);
INSERT INTO `reward` VALUES ('541', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('542', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('543', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('544', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('545', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('546', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('547', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('548', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('549', '', '000000000300', '0', null);
INSERT INTO `reward` VALUES ('550', '', '000000000300', '0', null);

-- ----------------------------
-- Table structure for `room`
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `room_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('3', '1');
INSERT INTO `room` VALUES ('4', '2');
INSERT INTO `room` VALUES ('5', '3');
INSERT INTO `room` VALUES ('6', '4');
INSERT INTO `room` VALUES ('7', '5');
INSERT INTO `room` VALUES ('8', '6');

-- ----------------------------
-- Table structure for `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` int(12) DEFAULT NULL,
  `game_id` int(12) DEFAULT NULL,
  `game_time` timestamp NULL DEFAULT NULL,
  `pay_money` int(12) DEFAULT NULL,
  `award_money` int(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('1', '2', '3', '2016-04-21 14:12:02', '6', '7');
INSERT INTO `score` VALUES ('2', '3', '5', '2016-04-22 14:14:12', '7', '5');
INSERT INTO `score` VALUES ('3', '20', '100', '2016-05-05 23:36:05', '500', '-500');
INSERT INTO `score` VALUES ('4', '20', '100', '2016-05-05 23:38:44', '500', '-500');
INSERT INTO `score` VALUES ('5', '20', '100', '2016-05-08 20:41:49', '500', '1980');
INSERT INTO `score` VALUES ('6', '20', '100', '2016-05-08 20:46:17', '500', '1980');
INSERT INTO `score` VALUES ('7', '20', '100', '2016-05-08 21:27:45', '500', '-500');
INSERT INTO `score` VALUES ('8', '20', '100', '2016-05-08 21:28:56', '500', '-500');
INSERT INTO `score` VALUES ('9', '20', '100', '2016-05-08 21:42:04', '500', '2475');
INSERT INTO `score` VALUES ('10', '20', '100', '2016-05-08 21:42:07', '500', '-2475');
INSERT INTO `score` VALUES ('11', '20', '100', '2016-05-08 22:00:48', '500', '0');
INSERT INTO `score` VALUES ('12', '20', '100', '2016-05-08 22:00:52', '500', '0');
INSERT INTO `score` VALUES ('13', '20', '100', '2016-05-08 22:00:56', '500', '0');
INSERT INTO `score` VALUES ('14', '20', '100', '2016-05-08 22:01:01', '500', '2475');
INSERT INTO `score` VALUES ('15', '20', '101', '2016-05-09 00:54:23', '1', '84');
INSERT INTO `score` VALUES ('16', '20', '101', '2016-05-09 01:01:05', '1', '59');
INSERT INTO `score` VALUES ('17', '20', '101', '2016-05-09 01:04:54', '50', '47');
INSERT INTO `score` VALUES ('18', '20', '100', '2016-05-14 20:52:23', '50', '0');
INSERT INTO `score` VALUES ('19', '20', '100', '2016-05-14 20:53:15', '50', '0');
INSERT INTO `score` VALUES ('20', '20', '100', '2016-05-14 20:53:55', '50', '0');
INSERT INTO `score` VALUES ('21', '20', '100', '2016-05-14 20:54:44', '50', '0');
INSERT INTO `score` VALUES ('22', '20', '100', '2016-05-14 20:55:10', '50', '0');
INSERT INTO `score` VALUES ('23', '20', '100', '2016-05-14 20:55:31', '50', '0');
INSERT INTO `score` VALUES ('24', '20', '100', '2016-05-14 20:56:04', '50', '0');
INSERT INTO `score` VALUES ('25', '20', '100', '2016-05-14 20:56:52', '50', '0');
INSERT INTO `score` VALUES ('26', '20', '101', '2016-05-14 20:59:06', '50', '277');
INSERT INTO `score` VALUES ('27', '20', '100', '2016-05-15 00:34:53', '50', '0');
INSERT INTO `score` VALUES ('28', '20', '100', '2016-05-15 00:35:51', '50', '0');
INSERT INTO `score` VALUES ('29', '20', '100', '2016-05-15 00:40:50', '50', '0');
INSERT INTO `score` VALUES ('30', '20', '100', '2016-05-15 00:42:03', '50', '0');
INSERT INTO `score` VALUES ('31', '20', '100', '2016-05-15 00:43:20', '50', '4950');
INSERT INTO `score` VALUES ('32', '20', '100', '2016-05-15 00:44:28', '50', '247');
INSERT INTO `score` VALUES ('33', '20', '100', '2016-05-15 00:44:56', '50', '247');
INSERT INTO `score` VALUES ('34', '20', '100', '2016-05-15 00:45:57', '50', '247');
INSERT INTO `score` VALUES ('35', '20', '100', '2016-05-15 00:46:23', '50', '247');
INSERT INTO `score` VALUES ('36', '20', '100', '2016-05-15 00:46:39', '50', '247');
INSERT INTO `score` VALUES ('37', '20', '100', '2016-05-15 00:48:00', '50', '0');
INSERT INTO `score` VALUES ('38', '20', '100', '2016-05-15 00:48:14', '50', '495');
INSERT INTO `score` VALUES ('39', '20', '100', '2016-05-15 00:48:38', '50', '247');
INSERT INTO `score` VALUES ('40', '20', '100', '2016-05-15 00:48:52', '50', '0');
INSERT INTO `score` VALUES ('41', '20', '100', '2016-05-15 00:49:12', '50', '495');
INSERT INTO `score` VALUES ('42', '20', '100', '2016-05-15 00:49:29', '50', '0');
INSERT INTO `score` VALUES ('43', '20', '100', '2016-05-15 00:52:45', '50', '990');
INSERT INTO `score` VALUES ('44', '20', '100', '2016-05-15 00:57:10', '50', '0');
INSERT INTO `score` VALUES ('45', '20', '100', '2016-05-15 00:57:22', '50', '0');
INSERT INTO `score` VALUES ('46', '20', '100', '2016-05-15 00:57:36', '50', '0');
INSERT INTO `score` VALUES ('47', '20', '100', '2016-05-15 01:05:53', '50', '990');
INSERT INTO `score` VALUES ('48', '20', '100', '2016-05-15 01:06:07', '50', '3960');
INSERT INTO `score` VALUES ('49', '20', '100', '2016-05-15 01:06:27', '50', '0');
INSERT INTO `score` VALUES ('50', '20', '100', '2016-05-15 01:06:36', '50', '247');
INSERT INTO `score` VALUES ('51', '20', '101', '2016-05-15 01:08:14', '50', '9');
INSERT INTO `score` VALUES ('52', '20', '101', '2016-05-15 01:08:36', '50', '29');
INSERT INTO `score` VALUES ('53', '20', '101', '2016-05-15 01:08:46', '50', '39');
INSERT INTO `score` VALUES ('54', '20', '101', '2016-05-15 01:09:49', '50', '9');
INSERT INTO `score` VALUES ('55', '20', '100', '2016-05-15 01:30:45', '50', '0');
INSERT INTO `score` VALUES ('56', '20', '101', '2016-05-15 01:31:30', '50', '9');
INSERT INTO `score` VALUES ('57', '20', '101', '2016-05-15 01:31:41', '50', '19');
INSERT INTO `score` VALUES ('58', '20', '101', '2016-05-15 01:31:51', '50', '11');
INSERT INTO `score` VALUES ('59', '20', '101', '2016-05-15 01:32:00', '50', '203');
INSERT INTO `score` VALUES ('60', '20', '101', '2016-05-15 01:32:13', '50', '49');
INSERT INTO `score` VALUES ('61', '20', '101', '2016-05-15 01:32:19', '50', '37');
INSERT INTO `score` VALUES ('62', '20', '101', '2016-05-15 01:32:34', '50', '19');
INSERT INTO `score` VALUES ('63', '20', '101', '2016-05-15 01:32:40', '50', '99');
INSERT INTO `score` VALUES ('64', '20', '101', '2016-05-15 01:32:46', '50', '45');
INSERT INTO `score` VALUES ('65', '20', '101', '2016-05-15 01:32:54', '50', '23');
INSERT INTO `score` VALUES ('66', '20', '101', '2016-05-15 01:33:11', '50', '23');
INSERT INTO `score` VALUES ('67', '20', '101', '2016-05-15 01:33:17', '50', '23');
INSERT INTO `score` VALUES ('68', '20', '101', '2016-05-15 01:33:23', '50', '9');
INSERT INTO `score` VALUES ('69', '20', '101', '2016-05-15 01:33:29', '50', '3');
INSERT INTO `score` VALUES ('70', '20', '101', '2016-05-15 01:33:35', '50', '29');
INSERT INTO `score` VALUES ('71', '20', '101', '2016-05-15 01:33:41', '50', '11');
INSERT INTO `score` VALUES ('72', '20', '101', '2016-05-15 01:33:47', '50', '138');
INSERT INTO `score` VALUES ('73', '20', '101', '2016-05-15 01:33:55', '50', '118');
INSERT INTO `score` VALUES ('74', '20', '101', '2016-05-15 01:34:05', '100', '63');
INSERT INTO `score` VALUES ('75', '20', '101', '2016-05-15 01:34:12', '100', '47');
INSERT INTO `score` VALUES ('76', '20', '101', '2016-05-15 01:34:18', '100', '31');
INSERT INTO `score` VALUES ('77', '20', '101', '2016-05-15 01:35:16', '5000', '1980');
INSERT INTO `score` VALUES ('78', '20', '101', '2016-05-15 01:35:29', '5000', '4752');
INSERT INTO `score` VALUES ('79', '20', '101', '2016-05-15 01:35:41', '5000', '2970');
INSERT INTO `score` VALUES ('80', '20', '101', '2016-05-15 01:35:48', '5000', '10890');
INSERT INTO `score` VALUES ('81', '20', '101', '2016-05-15 01:35:55', '5000', '11880');
INSERT INTO `score` VALUES ('82', '20', '101', '2016-05-15 01:36:10', '5000', '11286');
INSERT INTO `score` VALUES ('83', '20', '101', '2016-05-15 01:36:22', '5000', '3564');
INSERT INTO `score` VALUES ('84', '20', '101', '2016-05-15 01:36:27', '5000', '6534');
INSERT INTO `score` VALUES ('85', '20', '101', '2016-05-15 01:36:46', '5000', '3960');
INSERT INTO `score` VALUES ('86', '20', '101', '2016-05-15 01:36:51', '5000', '2376');
INSERT INTO `score` VALUES ('87', '20', '101', '2016-05-15 01:36:57', '5000', '6930');
INSERT INTO `score` VALUES ('88', '20', '101', '2016-05-15 01:37:31', '5000', '4950');
INSERT INTO `score` VALUES ('89', '20', '101', '2016-05-15 01:37:38', '5000', '4950');
INSERT INTO `score` VALUES ('90', '20', '101', '2016-05-15 01:37:43', '5000', '5940');
INSERT INTO `score` VALUES ('91', '20', '101', '2016-05-15 01:37:49', '5000', '6930');
INSERT INTO `score` VALUES ('92', '20', '101', '2016-05-15 01:37:55', '5000', '990');
INSERT INTO `score` VALUES ('93', '20', '101', '2016-05-15 01:38:01', '5000', '3960');
INSERT INTO `score` VALUES ('94', '20', '101', '2016-05-15 01:38:32', '5000', '3960');
INSERT INTO `score` VALUES ('95', '20', '101', '2016-05-18 00:27:33', '25000', '9900');
INSERT INTO `score` VALUES ('96', '20', '101', '2016-05-18 00:27:42', '25000', '7920');
INSERT INTO `score` VALUES ('97', '20', '101', '2016-05-18 00:27:45', '25000', '4950');
INSERT INTO `score` VALUES ('98', '20', '101', '2016-05-18 00:27:49', '25000', '12870');
INSERT INTO `score` VALUES ('99', '20', '101', '2016-05-18 00:27:57', '25000', '9900');
INSERT INTO `score` VALUES ('100', '20', '100', '2016-05-29 21:48:21', '500', '0');
INSERT INTO `score` VALUES ('101', '20', '100', '2016-05-29 21:48:36', '500', '2475');

-- ----------------------------
-- Table structure for `sysconf`
-- ----------------------------
DROP TABLE IF EXISTS `sysconf`;
CREATE TABLE `sysconf` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysconf
-- ----------------------------
INSERT INTO `sysconf` VALUES ('2', 'pay_merchant_code', '2010416825');
INSERT INTO `sysconf` VALUES ('3', 'pay_sign_type', 'RSA-S');
INSERT INTO `sysconf` VALUES ('4', 'pay_service_type', 'direct_pay');
INSERT INTO `sysconf` VALUES ('5', 'pay_notify_url', 'http://39.109.3.133/:8080/qps/user/charge');
INSERT INTO `sysconf` VALUES ('6', 'pay_product_name', '金币');
INSERT INTO `sysconf` VALUES ('10', 'product_desc', '棋牌游戏虚拟币');
INSERT INTO `sysconf` VALUES ('11', 'money_to_coin', '100');
INSERT INTO `sysconf` VALUES ('12', 'gm_pwd', '111111');
INSERT INTO `sysconf` VALUES ('13', 'fanpai_doub_times', '8');
INSERT INTO `sysconf` VALUES ('14', 'laba_card_percent1', '1=13|2=11|3=10|4=14|5=11|6=9|7=8|8=7|9=6|10=5|11=4|12=0|13=2');
INSERT INTO `sysconf` VALUES ('15', 'laba_card_percent2', '1=12|2=13|3=14|4=10|5=11|6=4|7=5|8=6|9=7|10=8|11=8|12=1|13=1');
INSERT INTO `sysconf` VALUES ('16', 'laba_card_percent3', '1=11|2=14|3=12|4=12|5=10|6=10|7=3|8=4|9=5|10=6|11=11|12=1|13=1');
INSERT INTO `sysconf` VALUES ('17', 'laba_card_percent4', '1=11|2=14|3=9|4=12|5=8|6=10|7=3|8=4|9=5|10=10|11=12|12=1|13=1');
INSERT INTO `sysconf` VALUES ('18', 'laba_card_percent5', '1=9|2=10|3=14|4=7|5=6|6=11|7=10|8=9|9=6|10=8|11=9|12=0|13=1');
INSERT INTO `sysconf` VALUES ('19', 'pay_product_types', '10,50,100,1000');

-- ----------------------------
-- Table structure for `tax`
-- ----------------------------
DROP TABLE IF EXISTS `tax`;
CREATE TABLE `tax` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `tax` int(3) DEFAULT NULL,
  `game_id` int(12) DEFAULT NULL,
  `stock` int(14) DEFAULT NULL,
  `activ_money` int(14) DEFAULT NULL,
  `coin_poo` int(14) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tax
-- ----------------------------
INSERT INTO `tax` VALUES ('1', '1', '100', '189', '121', '13454722');
INSERT INTO `tax` VALUES ('2', '1', '101', '189', '100', '66145');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `pwd` varchar(32) DEFAULT NULL,
  `pwd2` varchar(32) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `coin` int(8) DEFAULT NULL,
  `bank_coin` int(8) DEFAULT NULL,
  `login_time` timestamp NULL DEFAULT NULL,
  `logout_time` timestamp NULL DEFAULT NULL,
  `is_vip` char(1) DEFAULT NULL COMMENT '0-非VIP,1-VIP',
  `status` int(1) DEFAULT NULL COMMENT '0-正常，1-锁定，2-禁用，3-屏蔽',
  `comment` varchar(50) DEFAULT NULL,
  `online` char(1) DEFAULT NULL COMMENT '0-离线，1-在线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('10', '18764041111', '123456', null, null, '240', '0', null, null, '0', '0', null, null);
INSERT INTO `user` VALUES ('11', '18755551235', '123456', null, '18755551235', '270', '0', '2016-04-11 10:22:50', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('12', '12456324563', '111111', null, '12456324563', '330', '0', '2016-04-08 10:30:46', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('13', '18650011401', '0', null, '18650011401', '321', '0', '2016-04-12 14:49:39', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('14', '13615582656', '0', null, '13615582656', '300', '0', '2016-04-09 16:13:42', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('17', '18715125123', '123456', null, '18715125123', '300', '0', '2016-04-11 10:43:09', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('18', '18715125124', '123456', null, '18715125124', '300', '0', '2016-04-12 14:17:58', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('19', '13610020363', '0', null, '13610020363', '300', '0', '2016-04-12 09:41:00', null, '0', '0', null, null);
INSERT INTO `user` VALUES ('20', '8', '8', null, 'andy', '22112267', '0', '2016-05-29 21:48:05', '2016-05-29 19:14:18', '0', '0', null, '1');
INSERT INTO `user` VALUES ('21', '12', '12', null, null, '0', '0', null, null, '0', '0', null, null);
INSERT INTO `user` VALUES ('22', '123', '12', null, null, '0', '0', null, null, '0', '0', null, null);
INSERT INTO `user` VALUES ('23', '1234', '123', null, null, '0', '0', '2016-04-24 01:25:11', null, '0', '0', null, null);
