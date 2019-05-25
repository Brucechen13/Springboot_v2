
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for 用户表
-- ----------------------------
DROP TABLE IF EXISTS `wx_users`;
CREATE TABLE `wx_users` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `openid` varchar(255) NOT NULL COMMENT 'openid',
  `sessionkey` varchar(255) DEFAULT NULL COMMENT 'sessionkey',
  `nickName` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatarUrl` varchar(255) DEFAULT NULL COMMENT '头像',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `lasttime` datetime DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信用户表';


-- ----------------------------
-- Table structure for 动态
-- ----------------------------
DROP TABLE IF EXISTS `wx_posts`;
CREATE TABLE `wx_posts` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `userId` varchar(255) NOT NULL COMMENT '用户',
  `title` varchar(255) DEFAULT NULL COMMENT 'sessionkey',
  `content` varchar(255) DEFAULT NULL COMMENT '昵称',
  `status` varchar(255) DEFAULT NULL COMMENT '头像',
  `flags` varchar(255) DEFAULT NULL COMMENT '签名',
  `beginTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发布动态表';