
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for 用户表
-- ----------------------------
DROP TABLE IF EXISTS `wx_users`;
CREATE TABLE `wx_users` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `openid` varchar(255) NOT NULL COMMENT 'openid',
  `sessionkey` varchar(255) DEFAULT NULL COMMENT 'sessionkey',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
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
  `userid` varchar(255) NOT NULL COMMENT '用户',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `flags` varchar(255) DEFAULT NULL COMMENT '标签',
  `class` varchar(255) DEFAULT NULL COMMENT '类别',
  `begintime` datetime DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发布动态表';

ALTER TABLE wx_posts ADD CONSTRAINT fk_wx_posts_id FOREIGN KEY (userid) REFERENCES wx_users(id);

-- ----------------------------
-- Table structure for 评论
-- ----------------------------
DROP TABLE IF EXISTS `wx_comments`;
CREATE TABLE `wx_comments` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `userid` varchar(255) NOT NULL COMMENT '用户',
  `postid` varchar(255) DEFAULT NULL COMMENT '动态',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `posttime` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发布评论表';

ALTER TABLE wx_comments ADD CONSTRAINT fk_wx_comments_id1 FOREIGN KEY (userid) REFERENCES wx_users(id);
ALTER TABLE wx_comments ADD CONSTRAINT fk_wx_comments_id2 FOREIGN KEY (postid) REFERENCES wx_posts(id);