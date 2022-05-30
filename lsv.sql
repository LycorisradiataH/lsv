/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 120.79.113.230:3306
 Source Schema         : lsv

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 30/05/2022 17:17:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '测试分类', '2022-02-21 18:14:18', '2022-03-13 19:51:16');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '评论用户id',
  `video_id` int NULL DEFAULT NULL COMMENT '评论视频id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `reply_user_id` int NULL DEFAULT NULL COMMENT '回复用户id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论id',
  `type` tinyint NULL DEFAULT NULL COMMENT '评论类型 1.视频 2.贴子',
  `is_review` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否审核通过 0否 1是',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id`) USING BTREE,
  INDEX `fk_comment_video`(`video_id`) USING BTREE,
  INDEX `fk_comment_parent`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单路径',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单icon',
  `order_num` tinyint(1) NOT NULL COMMENT '排序',
  `parent_id` int NULL DEFAULT NULL COMMENT '父id',
  `is_hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏  0否1是',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', 1, NULL, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (2, '视频管理', '/video-submenu', 'Layout', 'el-icon-mywenzhang-copy', 2, NULL, 0, '2022-03-12 11:36:09', '2022-03-17 16:27:29');
INSERT INTO `menu` VALUES (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', 3, NULL, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (4, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', 5, NULL, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (5, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', 7, NULL, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (7, '修改视频', '/video/*', '/video/Video.vue', 'el-icon-myfabiaowenzhang', 2, 2, 1, '2022-03-12 11:36:09', '2022-03-14 10:57:55');
INSERT INTO `menu` VALUES (8, '视频列表', '/video-list', '/video/VideoList.vue', 'el-icon-mywenzhangliebiao', 3, 2, 0, '2022-03-12 11:36:09', '2022-03-14 10:58:30');
INSERT INTO `menu` VALUES (9, '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', 4, 2, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (10, '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', 5, 2, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (11, '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', 1, 3, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (13, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', 1, 22, 0, '2022-03-12 11:36:09', '2022-05-02 09:40:49');
INSERT INTO `menu` VALUES (14, '角色管理', '/roles', '/role/Role.vue', 'el-icon-myjiaoseliebiao', 2, 26, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:16');
INSERT INTO `menu` VALUES (15, '接口管理', '/resources', '/resource/Resource.vue', 'el-icon-myjiekouguanli', 2, 26, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:33');
INSERT INTO `menu` VALUES (16, '菜单管理', '/menus', '/menu/Menu.vue', 'el-icon-mycaidan', 2, 26, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:34');
INSERT INTO `menu` VALUES (19, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', 6, NULL, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (20, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', 1, 19, 0, '2022-03-12 11:36:09', '2022-03-12 11:36:09');
INSERT INTO `menu` VALUES (21, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', 7, 22, 0, '2022-03-12 11:36:09', '2022-05-02 09:40:57');
INSERT INTO `menu` VALUES (22, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', 4, NULL, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:00');
INSERT INTO `menu` VALUES (23, '相册管理', '/album-submenu', 'Layout', 'el-icon-myimage-fill', 5, NULL, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:07');
INSERT INTO `menu` VALUES (24, '相册列表', '/albums', '/album/Album.vue', 'el-icon-myzhaopian', 1, 23, 0, '2022-03-12 11:36:09', '2022-05-02 09:41:08');
INSERT INTO `menu` VALUES (25, '照片管理', '/albums/:albumId', '/album/Photo.vue', 'el-icon-myzhaopian', 1, 23, 1, '2022-03-12 11:36:09', '2022-05-02 09:41:10');
INSERT INTO `menu` VALUES (26, '权限管理', '/permission-submenu', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli', 4, NULL, 0, '2022-03-12 11:38:29', '2022-05-02 09:41:30');
INSERT INTO `menu` VALUES (27, '网站管理', '/website', '/website/Website.vue', 'el-icon-myxitong', 1, 4, 0, '2022-03-12 11:38:29', '2022-05-02 09:41:37');
INSERT INTO `menu` VALUES (28, '照片回收站', '/photos/delete', '/album/Delete.vue', 'el-icon-myhuishouzhan', 3, 23, 1, '2022-03-18 19:29:22', '2022-05-07 15:54:11');

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返回数据',
  `user_id` int NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_id` int NOT NULL COMMENT '相册id',
  `photo_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片名',
  `photo_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片描述',
  `photo_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片地址',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '照片' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for photo_album
-- ----------------------------
DROP TABLE IF EXISTS `photo_album`;
CREATE TABLE `photo_album`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `album_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册名',
  `album_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册描述',
  `album_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册封面',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1公开 2私密',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '相册' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` int NULL DEFAULT NULL COMMENT '父权限id',
  `is_anonymous` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否匿名访问 0否 1是',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 377 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (286, '分类模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (287, '日志模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (288, '标签模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (289, '照片模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (290, '用户信息模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (291, '用户账号模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (292, '相册模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (293, '菜单模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (294, '视频信息模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (295, '视频模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (296, '角色模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (297, '评论模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (298, '资源模块', NULL, NULL, NULL, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (299, '查看主页信息', '/', 'GET', 294, 1, '2022-05-02 17:42:52', '2022-05-02 17:53:10');
INSERT INTO `resource` VALUES (300, '查看关于作者信息', '/about', 'GET', 294, 1, '2022-05-02 17:42:52', '2022-05-02 17:53:12');
INSERT INTO `resource` VALUES (301, '查看后台信息', '/admin', 'GET', 294, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (302, '修改关于作者信息', '/admin/about', 'PUT', 294, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (303, '查看后台分类列表', '/admin/categories', 'GET', 286, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (304, '添加或修改分类', '/admin/categories', 'POST', 286, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (305, '删除分类', '/admin/categories', 'DELETE', 286, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (306, '搜索视频分类', '/admin/categories/search', 'GET', 286, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (307, '查询后台评论', '/admin/comments', 'GET', 297, 0, '2022-05-02 17:42:52', '2022-05-02 17:53:55');
INSERT INTO `resource` VALUES (308, '删除评论', '/admin/comments', 'DELETE', 297, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (309, '审核评论', '/admin/comments/review', 'PUT', 297, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (310, '查看菜单列表', '/admin/menus', 'GET', 293, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (311, '新增或修改菜单', '/admin/menus', 'POST', 293, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (312, '删除菜单', '/admin/menus/*', 'DELETE', 293, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (313, '查看操作日志', '/admin/operation/logs', 'GET', 287, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (314, '删除操作日志', '/admin/operation/logs', 'DELETE', 287, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (315, '根据相册id获取照片列表', '/admin/photos', 'GET', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (316, '保存照片', '/admin/photos', 'POST', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (317, '更新照片信息', '/admin/photos', 'PUT', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (318, '删除照片', '/admin/photos', 'DELETE', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (319, '移动照片相册', '/admin/photos/album', 'PUT', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (320, '查看后台相册列表', '/admin/photos/albums', 'GET', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (321, '保存或更新相册', '/admin/photos/albums', 'POST', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (322, '上传相册封面', '/admin/photos/albums/cover', 'POST', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (323, '获取后台相册列表信息', '/admin/photos/albums/info', 'GET', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (324, '根据id删除相册', '/admin/photos/albums/*', 'DELETE', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (325, '根据id获取后台相册信息', '/admin/photos/albums/*/info', 'GET', 292, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (326, '更新照片删除状态', '/admin/photos/delete', 'PUT', 289, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (327, '查看资源列表', '/admin/resources', 'GET', 298, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (328, '新增或修改资源', '/admin/resources', 'POST', 298, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (329, '导入swagger接口', '/admin/resources/import/swagger', 'GET', 298, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (330, '删除资源', '/admin/resources/*', 'DELETE', 298, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (331, '保存或更新角色', '/admin/role', 'POST', 296, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (332, '查看角色菜单选项', '/admin/role/menus', 'GET', 293, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (333, '查看角色资源选项', '/admin/role/resources', 'GET', 298, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (334, '查询角色列表', '/admin/roles', 'GET', 296, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (335, '删除角色', '/admin/roles', 'DELETE', 296, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (336, '修改角色禁用状态', '/admin/roles/disable', 'PUT', 296, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (337, '查询后台标签列表', '/admin/tags', 'GET', 288, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (338, '添加或修改标签', '/admin/tags', 'POST', 288, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (339, '删除标签', '/admin/tags', 'DELETE', 288, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (340, '搜索视频标签', '/admin/tags/search', 'GET', 288, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (341, '查看当前用户菜单', '/admin/user/menus', 'GET', 293, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (342, '查询后台用户列表', '/admin/users', 'GET', 291, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (343, '获取用户区域分布', '/admin/users/area', 'GET', 291, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (344, '修改用户禁用状态', '/admin/users/disable', 'PUT', 290, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (345, '查看在线用户', '/admin/users/online', 'GET', 290, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (346, '修改管理员密码', '/admin/users/password', 'PUT', 291, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (347, '查询用户角色选项', '/admin/users/role', 'GET', 296, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (348, '修改用户角色', '/admin/users/role', 'PUT', 290, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (349, '下线用户', '/admin/users/*/online', 'DELETE', 290, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (350, '查看后台视频', '/admin/videos', 'GET', 295, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (351, '恢复或删除视频', '/admin/videos', 'PUT', 295, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (352, '物理删除视频', '/admin/videos', 'DELETE', 295, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (353, '获取网站配置', '/admin/website/config', 'GET', 294, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (354, '更新网站配置', '/admin/website/config', 'PUT', 294, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (355, '查看分类列表', '/categories', 'GET', 286, 1, '2022-05-02 17:42:52', '2022-05-02 17:52:43');
INSERT INTO `resource` VALUES (356, '查询评论', '/comments', 'GET', 297, 1, '2022-05-02 17:42:52', '2022-05-02 17:53:55');
INSERT INTO `resource` VALUES (357, '添加评论', '/comments', 'POST', 297, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (358, '评论点赞', '/comments/*/like', 'POST', 297, 0, '2022-05-02 17:42:52', '2022-05-02 17:42:52');
INSERT INTO `resource` VALUES (359, '查询评论下的回复', '/comments/*/replies', 'GET', 297, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:50');
INSERT INTO `resource` VALUES (360, '用户注册', '/register', 'POST', 291, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (361, 'report', '/report', 'POST', 294, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:14');
INSERT INTO `resource` VALUES (362, '更新用户头像', '/users/avatar', 'POST', 290, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (363, '发送邮箱验证码', '/users/code', 'GET', 291, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (364, '绑定用户邮箱', '/users/email', 'POST', 290, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (365, '更新用户信息', '/users/info', 'PUT', 290, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (366, '修改密码', '/users/password', 'PUT', 291, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (367, '获取用户昵称', '/users/username/*', 'GET', 290, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (368, '查看首页视频', '/videos', 'GET', 295, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:21');
INSERT INTO `resource` VALUES (369, '添加或修改视频', '/videos', 'POST', 295, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (370, '根据条件查询视频', '/videos/condition', 'GET', 295, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:26');
INSERT INTO `resource` VALUES (371, '根据用户id查询视频', '/videos/condition/userId', 'GET', 295, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:27');
INSERT INTO `resource` VALUES (372, '上传视频缩略图', '/videos/images', 'POST', 295, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (373, '搜索视频', '/videos/search', 'GET', 295, 1, '2022-05-02 17:42:53', '2022-05-24 15:16:59');
INSERT INTO `resource` VALUES (374, '上传视频', '/videos/video', 'POST', 295, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');
INSERT INTO `resource` VALUES (375, '根据id查看视频', '/videos/*', 'GET', 295, 1, '2022-05-02 17:42:53', '2022-05-02 17:53:39');
INSERT INTO `resource` VALUES (376, '点赞视频', '/videos/*/like', 'POST', 295, 0, '2022-05-02 17:42:53', '2022-05-02 17:42:53');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  `role_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色描述',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 0否 1是',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'admin', 0, '2022-03-16 17:13:02', '2022-05-05 15:58:38');
INSERT INTO `role` VALUES (4, '普通用户', 'user', 0, '2022-05-02 17:47:01', '2022-05-05 16:02:30');
INSERT INTO `role` VALUES (5, '测试', 'test', 0, '2022-05-02 17:50:24', '2022-05-02 17:52:28');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发表时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2915 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (2830, 1, 1, '2022-03-18 19:30:30', '2022-03-18 19:30:30');
INSERT INTO `role_menu` VALUES (2831, 1, 2, '2022-03-18 19:30:30', '2022-03-18 19:30:30');
INSERT INTO `role_menu` VALUES (2832, 1, 3, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2833, 1, 4, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2834, 1, 5, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2835, 1, 7, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2836, 1, 8, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2837, 1, 9, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2839, 1, 10, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2840, 1, 11, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2841, 1, 13, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2842, 1, 14, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2843, 1, 15, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2844, 1, 16, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2846, 1, 19, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2847, 1, 20, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2848, 1, 21, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2849, 1, 22, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2850, 1, 23, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2851, 1, 24, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2852, 1, 25, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2853, 1, 26, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2854, 1, 27, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2855, 1, 28, '2022-03-18 19:30:30', '2022-05-02 09:42:34');
INSERT INTO `role_menu` VALUES (2891, 5, 1, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2892, 5, 2, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2893, 5, 7, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2894, 5, 8, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2895, 5, 9, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2896, 5, 10, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2897, 5, 3, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2898, 5, 11, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2899, 5, 22, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2900, 5, 13, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2901, 5, 21, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2902, 5, 26, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2903, 5, 14, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2904, 5, 15, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2905, 5, 16, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2906, 5, 4, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2907, 5, 27, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2909, 5, 23, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2910, 5, 24, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2911, 5, 25, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2912, 5, 19, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2913, 5, 20, '2022-05-02 17:50:46', '2022-05-02 17:50:46');
INSERT INTO `role_menu` VALUES (2914, 5, 5, '2022-05-02 17:50:46', '2022-05-02 17:50:46');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int NULL DEFAULT NULL COMMENT '权限id',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 576 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES (364, 1, 286, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (365, 1, 287, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (366, 1, 288, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (367, 1, 289, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (368, 1, 290, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (369, 1, 291, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (370, 1, 292, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (371, 1, 293, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (372, 1, 294, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (373, 1, 295, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (374, 1, 296, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (375, 1, 297, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (376, 1, 298, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (377, 1, 299, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (378, 1, 300, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (379, 1, 301, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (380, 1, 302, '2022-03-31 11:14:52', '2022-05-02 09:43:46');
INSERT INTO `role_resource` VALUES (381, 1, 303, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (382, 1, 304, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (383, 1, 305, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (384, 1, 306, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (385, 1, 307, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (386, 1, 308, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (387, 1, 309, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (388, 1, 310, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (389, 1, 311, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (390, 1, 312, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (391, 1, 313, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (392, 1, 314, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (393, 1, 315, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (394, 1, 316, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (395, 1, 317, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (396, 1, 318, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (397, 1, 319, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (398, 1, 320, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (399, 1, 321, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (400, 1, 322, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (401, 1, 323, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (402, 1, 324, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (403, 1, 325, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (404, 1, 326, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (405, 1, 327, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (406, 1, 328, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (407, 1, 329, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (408, 1, 330, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (409, 1, 331, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (410, 1, 332, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (411, 1, 333, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (412, 1, 334, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (413, 1, 335, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (414, 1, 336, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (415, 1, 337, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (416, 1, 338, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (417, 1, 339, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (418, 1, 340, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (419, 1, 341, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (420, 1, 342, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (421, 1, 343, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (422, 1, 344, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (423, 1, 345, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (424, 1, 346, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (425, 1, 347, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (426, 1, 348, '2022-03-31 11:14:52', '2022-05-02 09:43:47');
INSERT INTO `role_resource` VALUES (427, 1, 349, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (428, 1, 350, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (429, 1, 351, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (430, 1, 352, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (431, 1, 353, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (432, 1, 354, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (433, 1, 355, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (434, 1, 356, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (435, 1, 357, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (436, 1, 358, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (437, 1, 359, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (438, 1, 360, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (439, 1, 361, '2022-03-31 11:14:52', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (440, 1, 362, '2022-05-02 09:43:22', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (441, 1, 363, '2022-05-02 09:43:22', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (442, 1, 364, '2022-05-02 09:43:23', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (443, 1, 365, '2022-05-02 09:43:24', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (444, 1, 366, '2022-05-02 09:43:24', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (445, 1, 367, '2022-05-02 09:43:24', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (446, 1, 368, '2022-05-02 09:43:25', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (447, 1, 369, '2022-05-02 09:43:25', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (448, 1, 370, '2022-05-02 09:43:25', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (449, 1, 371, '2022-05-02 09:43:26', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (450, 1, 372, '2022-05-02 09:43:26', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (451, 1, 373, '2022-05-02 09:43:26', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (452, 1, 374, '2022-05-02 09:43:26', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (453, 1, 375, '2022-05-02 09:43:26', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (454, 1, 376, '2022-05-02 09:43:27', '2022-05-02 09:43:48');
INSERT INTO `role_resource` VALUES (516, 4, 355, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (517, 4, 362, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (518, 4, 364, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (519, 4, 365, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (520, 4, 367, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (521, 4, 360, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (522, 4, 363, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (523, 4, 366, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (524, 4, 299, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (525, 4, 300, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (526, 4, 353, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (527, 4, 361, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (528, 4, 352, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (529, 4, 368, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (530, 4, 369, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (531, 4, 371, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (532, 4, 373, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (533, 4, 374, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (534, 4, 375, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (535, 4, 376, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (536, 4, 356, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (537, 4, 357, '2022-05-02 17:49:50', '2022-05-02 17:49:50');
INSERT INTO `role_resource` VALUES (538, 4, 358, '2022-05-02 17:49:51', '2022-05-02 17:49:51');
INSERT INTO `role_resource` VALUES (539, 4, 359, '2022-05-02 17:49:51', '2022-05-02 17:49:51');
INSERT INTO `role_resource` VALUES (540, 5, 303, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (541, 5, 306, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (542, 5, 355, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (543, 5, 313, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (544, 5, 337, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (545, 5, 340, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (546, 5, 315, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (547, 5, 345, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (548, 5, 367, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (549, 5, 342, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (550, 5, 343, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (551, 5, 320, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (552, 5, 323, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (553, 5, 325, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (554, 5, 310, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (555, 5, 332, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (556, 5, 341, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (557, 5, 299, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (558, 5, 300, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (559, 5, 301, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (560, 5, 353, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (561, 5, 361, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (562, 5, 350, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (563, 5, 368, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (564, 5, 370, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (565, 5, 371, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (566, 5, 373, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (567, 5, 375, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (568, 5, 376, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (569, 5, 334, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (570, 5, 347, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (571, 5, 307, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (572, 5, 356, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (573, 5, 359, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (574, 5, 327, '2022-05-02 17:52:28', '2022-05-02 17:52:28');
INSERT INTO `role_resource` VALUES (575, 5, 333, '2022-05-02 17:52:28', '2022-05-02 17:52:28');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for unique_view
-- ----------------------------
DROP TABLE IF EXISTS `unique_view`;
CREATE TABLE `unique_view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `view_count` int NOT NULL COMMENT '访问量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访问量' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_info_id` int NOT NULL COMMENT '用户信息id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `login_type` tinyint(1) NOT NULL COMMENT '登陆类型',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `last_login_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次登录时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户账号' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES (1, 1, 'admin@qq.com', '$2a$10$TxzLJwN4fcf0ZUO7ma5q9.GVAB.DXY62QtOqSb2efabaogwH8DZGS', 1, '119.142.149.105', '广东省中山市 电信', '2022-05-24 22:01:17', '2022-02-26 18:36:16', '2022-05-24 22:01:17');
INSERT INTO `user_auth` VALUES (3, 3, 'test@qq.com', '$2a$10$zpHT4l5fsRCcMkTjE3IpBeKm2GuJMtQbMsqMehSVOUjy1NiMozuny', 1, '119.142.145.60', '广东省中山市 电信', '2022-05-02 17:54:42', '2022-03-23 20:18:38', '2022-05-02 17:54:42');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱号',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户简介',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 0否 1是',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin@qq.com', '管理员', 'https://hua-lsv.oss-cn-guangzhou.aliyuncs.com/avatar/5368e2d83f77669913873053c428327a.jpg', '管理员', 0, '2022-02-26 18:36:04', '2022-05-05 16:02:24');
INSERT INTO `user_info` VALUES (3, 'test@qq.com', '测试', 'https://hua-lsv.oss-cn-guangzhou.aliyuncs.com/avatar/5368e2d83f77669913873053c428327a.jpg', '测试账号', 0, '2022-03-23 20:18:38', '2022-05-24 22:03:15');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, '2022-03-16 17:13:14', '2022-05-07 15:52:19');
INSERT INTO `user_role` VALUES (2, 3, 5, '2022-05-05 13:27:44', '2022-05-07 15:52:19');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '作者id',
  `category_id` int NULL DEFAULT NULL COMMENT '文章分类id',
  `video_cover` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频缩略图',
  `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频名称',
  `video_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频链接',
  `play_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '播放id',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 0否 1是',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `ft_search`(`video_name`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for video_tag
-- ----------------------------
DROP TABLE IF EXISTS `video_tag`;
CREATE TABLE `video_tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `video_id` int NOT NULL COMMENT '视频id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_video_tag_1`(`video_id`) USING BTREE,
  INDEX `fk_video_tag_2`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for website_config
-- ----------------------------
DROP TABLE IF EXISTS `website_config`;
CREATE TABLE `website_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `config` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置信息',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '网站配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of website_config
-- ----------------------------
INSERT INTO `website_config` VALUES (1, '{\"gitee\":\"https://gitee.com/lhMorri\",\"github\":\"https://github.com/LycorisradiataH\",\"isCommentReview\":0,\"isEmailNotice\":1,\"isReward\":1,\"isVideoReview\":0,\"qq\":\"819945352\",\"socialLoginList\":[\"weibo\",\"qq\"],\"socialUrlList\":[\"qq\",\"github\",\"gitee\"],\"userAvatar\":\"http://www.static.hdiata.com/config/tourist.png\",\"websiteAuthor\":\"Morri\",\"websiteAvatar\":\"http://www.static.hdiata.com/config/author.jpg\",\"websiteBeianNo\":\"粤公网安备 44020402000283号\",\"websiteCreateTime\":\"2021-10-23\",\"websiteIntro\":\"秋天的风也能遇见春天的花\",\"websiteName\":\"lsv\",\"websiteNotice\":\"在线视频项目正在加紧搭建中，若有疑问或发现bug可点击上方的源码地址留言喔！\",\"websiteRecordNo\":\"粤ICP备2021150615号\",\"websocketUrl\":\"ws://127.0.0.1:8080/websocket\"}', '2022-03-17 17:06:27', '2022-05-05 14:04:17');

SET FOREIGN_KEY_CHECKS = 1;
