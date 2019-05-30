
SET FOREIGN_KEY_CHECKS=0;

SET character_set_client='utf8';
SET character_set_connection='utf8';
SET character_set_results='utf8';

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
  `lasttime` varchar(255) DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信用户表';

INSERT INTO `wx_users` VALUES ('577272523196989440', '3', '3', '用户001', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJcs0O4Ssia44FMeW34y8j4Mse52PhwB2Rjbg5KOBN1ClEF3AWTMv6Zgv3YUkzGgCB92QMsqRQ6ialQ/132', '听说这个是签名', '2019-06-01');

INSERT INTO `wx_users` VALUES ('577272523196989441', '3', '3', '用户002', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJcs0O4Ssia44FMeW34y8j4Mse52PhwB2Rjbg5KOBN1ClEF3AWTMv6Zgv3YUkzGgCB92QMsqRQ6ialQ/132', '我没有签名', '2019-06-01');

INSERT INTO `wx_users` VALUES ('577272523196989441', '3', '3', '用户003', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJcs0O4Ssia44FMeW34y8j4Mse52PhwB2Rjbg5KOBN1ClEF3AWTMv6Zgv3YUkzGgCB92QMsqRQ6ialQ/132', '该用户未设置签名', '2019-06-01');
-- ----------------------------
-- Table structure for 动态
-- ----------------------------
DROP TABLE IF EXISTS `wx_posts`;
CREATE TABLE `wx_posts` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `userid` varchar(255) NOT NULL COMMENT '用户',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(62535) DEFAULT NULL COMMENT '内容',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `flagstr` varchar(255) DEFAULT NULL COMMENT '标签',
  `classes` varchar(255) DEFAULT NULL COMMENT '类别',
  `begintime` varchar(255) DEFAULT NULL COMMENT '开始时间',
  `endtime` varchar(255) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发布动态表';

ALTER TABLE wx_posts ADD CONSTRAINT fk_wx_posts_id FOREIGN KEY (userid) REFERENCES wx_users(id);

INSERT INTO `wx_posts` VALUES ('1241415', '577272523196989440', '发个动态吧', '今天天气很好，嘻嘻嘻', '进行中', '运动', '发布动态', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241416', '577272523196989440', '这是一个很长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的标题', '这是一个很长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的内容', '进行中', '运动#游戏#美食', '发布动态', '2019-06-01', '2019-06-01');

INSERT INTO `wx_posts` VALUES ('1241417', '577272523196989440', '我来发布任务了', '快递柜帮我取个快递，我在学十楼，多谢啦', '进行中', '运动#有米', '任务发布', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241418', '577272523196989441', '来约打球吧', '今天在篮球场，要不要来打球，人数不限', '进行中', '运动', '活动组织', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241419', '577272523196989441', '标题长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长', '这是一个很长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的内容', '进行中', '运动', '发布动态', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241421', '577272523196989440', 'I want to speak english', '有没有人英语好的，求辅导呀', '进行中', '有米#学习', '任务发布', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241422', '577272523196989441', '卖个旧手机', '有个手机要卖了，用了一年，八成新，一切正常', '进行中', '有米', '发布动态', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241423', '577272523196989441', '求带点到', '今天上午有课，身体不舒服不想去了，有没有人能帮我去点个到，教三二楼', '进行中', '学习', '发布动态', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241424', '577272523196989440', '看电影', '有没有人看电影呀', '进行中', '运动', '发布动态', '2019-06-01', '2019-06-01');
INSERT INTO `wx_posts` VALUES ('1241425', '577272523196989440', '外包项目，一起来做', '接了个外包项目，后台加前端的，找一个会前端的一起来搞', '进行中', '运动', '任务发布', '2019-06-01', '2019-09-01');
INSERT INTO `wx_posts` VALUES ('1241426', '577272523196989440', '去做志愿者，有没有人一起的', '周末想去做志愿者了，有没有人一起啊', '进行中', '运动', '活动组织', '2019-06-01', '2019-06-01');

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
  `posttime` varchar(255) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发布评论表';

ALTER TABLE wx_comments ADD CONSTRAINT fk_wx_comments_id1 FOREIGN KEY (userid) REFERENCES wx_users(id);
ALTER TABLE wx_comments ADD CONSTRAINT fk_wx_comments_id2 FOREIGN KEY (postid) REFERENCES wx_posts(id);

INSERT INTO `wx_comments` VALUES ('1245', '577272523196989440', '1241416', '这是一个很长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的评论', '', '2019-06-01');

INSERT INTO `wx_comments` VALUES ('1246', '577272523196989440', '1241416', '为啥没法加好友', '', '2019-06-01');

INSERT INTO `wx_comments` VALUES ('1247', '577272523196989440', '1241416', '插旗，能不能加个私聊', '', '2019-06-01');

INSERT INTO `wx_comments` VALUES ('1248', '577272523196989440', '1241416', '你说的很对', '', '2019-06-01');

INSERT INTO `wx_comments` VALUES ('1249', '577272523196989440', '1241417', '点赞', '', '2019-06-01');

-- ----------------------------
-- Table structure for 收藏
-- ----------------------------
DROP TABLE IF EXISTS `wx_collects`;
CREATE TABLE `wx_collects` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `userid` varchar(255) NOT NULL COMMENT '用户',
  `postid` varchar(255) DEFAULT NULL COMMENT '动态',
  `collecttime` varchar(255) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态收藏表';


INSERT INTO `wx_collects` VALUES ('1249', '577272523196989440', '1241417', '2019-06-01');