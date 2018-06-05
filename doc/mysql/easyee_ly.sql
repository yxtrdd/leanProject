/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : 127.0.0.1:3306
Source Database       : easyee_ly

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-06-05 16:44:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `module_dept`
-- ----------------------------
DROP TABLE IF EXISTS `module_dept`;
CREATE TABLE `module_dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(30) NOT NULL,
  `loc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_dept
-- ----------------------------
INSERT INTO `module_dept` VALUES ('1', '开发部', '深圳');
INSERT INTO `module_dept` VALUES ('2', '测试部', '北京');
INSERT INTO `module_dept` VALUES ('3', '研究院', '上海');

-- ----------------------------
-- Table structure for `module_emp`
-- ----------------------------
DROP TABLE IF EXISTS `module_emp`;
CREATE TABLE `module_emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_deptno` (`deptno`) USING BTREE,
  CONSTRAINT `module_emp_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `module_dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_emp
-- ----------------------------
INSERT INTO `module_emp` VALUES ('1', '张三', '开发工程师', '1');
INSERT INTO `module_emp` VALUES ('2', '李四', '开发工程师', '1');
INSERT INTO `module_emp` VALUES ('3', '王五', '管理员', '3');
INSERT INTO `module_emp` VALUES ('4', '孙六', '测试主管', '1');
INSERT INTO `module_emp` VALUES ('5', '钱七', '测试工程师', '2');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION` text,
  `PARAMETERS` text,
  `RES` text,
  `ACCOUNT` varchar(200) DEFAULT NULL,
  `IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `SORT_NUM` int(11) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  `OPEN_MODE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_MENU_RIGHTS_ID` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `sys_menu_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '后台管理系统', '', null, '0', 'icon-home2', '', '');
INSERT INTO `sys_menu` VALUES ('2', '系统管理', null, '1', '0', 'icon-application_view_tile', '系统维护管理，系统管理员拥有', null);
INSERT INTO `sys_menu` VALUES ('3', '用户管理', 'SysUser/page', '2', '0', 'icon-user', null, null);
INSERT INTO `sys_menu` VALUES ('4', '角色管理', 'SysRole/page', '2', '1', 'icon-grade', null, null);
INSERT INTO `sys_menu` VALUES ('5', '菜单权限管理', 'SysMenuPermission/page', '2', '2', 'icon-menu', null, null);
INSERT INTO `sys_menu` VALUES ('6', '操作权限管理', 'SysOperationPermission/page', '2', '3', 'icon-rights', null, null);
INSERT INTO `sys_menu` VALUES ('7', '员工管理', null, '1', '1', 'icon-report', '人事部操作', null);
INSERT INTO `sys_menu` VALUES ('8', '部门信息管理', 'Dept/page', '7', '1', 'icon-group', '', null);
INSERT INTO `sys_menu` VALUES ('9', '员工信息管理', 'Emp/page', '7', '2', 'icon-id', '', null);
INSERT INTO `sys_menu` VALUES ('10', '报表管理', null, '1', '8', 'icon-chart_bar', '经理查看', null);
INSERT INTO `sys_menu` VALUES ('11', '统计报表', 'toReports', '10', '0', 'icon-chart_curve', '', null);
INSERT INTO `sys_menu` VALUES ('12', '系统日志', 'SysLog/page', '2', '5', 'icon-book', '系统日志查看', null);
INSERT INTO `sys_menu` VALUES ('16', '商品管理', '', '1', '4', 'icon-rights', '商品货物管理', '');
INSERT INTO `sys_menu` VALUES ('17', '订单管理', '', '1', '3', 'icon-book', '订单管理', '');
INSERT INTO `sys_menu` VALUES ('18', '白酒管理', 'Liquor/page', '16', '1', 'icon-application', '', '');
INSERT INTO `sys_menu` VALUES ('23', '客户管理', '', '1', '5', 'icon-grade', '客户资源管理', '');
INSERT INTO `sys_menu` VALUES ('24', '库存管理', '', '1', '7', 'icon-reduce-computer', '商品库存盘点', '');
INSERT INTO `sys_menu` VALUES ('25', '采购管理', '', '1', '6', 'icon-monitor_go', '进货管理', '');
INSERT INTO `sys_menu` VALUES ('26', '小程序管理', '', '1', '9', 'icon-house', '', '');
INSERT INTO `sys_menu` VALUES ('27', '广告管理', '', '26', '1', 'icon-role', '', '');
INSERT INTO `sys_menu` VALUES ('28', '支付管理', '', '26', '2', 'icon-application', '', '');
INSERT INTO `sys_menu` VALUES ('29', '分类管理', 'AdvertisSort/advertisSort', '27', '1', 'icon-application_view_tile', '', '');
INSERT INTO `sys_menu` VALUES ('30', '广告列表', 'AdvertisSort/advertisList', '27', '2', 'icon-folder', '', '');

-- ----------------------------
-- Table structure for `sys_operation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `OPERATION_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_PERMISSION_ID` int(11) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_OPERATION_MENU_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_operation_ibfk_1` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_operation
-- ----------------------------
INSERT INTO `sys_operation` VALUES ('1', '5', '查询菜单列表', 'SysMenuPermission/list', '');
INSERT INTO `sys_operation` VALUES ('2', '5', '修改菜单', 'SysMenuPermission/update', '');
INSERT INTO `sys_operation` VALUES ('3', '5', '删除菜单', 'SysMenuPermission/delete', '');
INSERT INTO `sys_operation` VALUES ('4', '5', '移动菜单次序', 'SysMenuPermission/move', '');
INSERT INTO `sys_operation` VALUES ('5', '5', '添加菜单', 'SysMenuPermission/save', '');
INSERT INTO `sys_operation` VALUES ('6', '6', '菜单列表查询', 'SysMenuPermission/list', '');
INSERT INTO `sys_operation` VALUES ('7', '6', '查询菜单对应的操作权限列表', 'SysOperationPermission/list', '');
INSERT INTO `sys_operation` VALUES ('8', '6', '新增操作权限', 'SysOperationPermission/save', '');
INSERT INTO `sys_operation` VALUES ('9', '6', '修改操作权限', 'SysOperationPermission/update', '');
INSERT INTO `sys_operation` VALUES ('10', '6', '删除操作权限', 'SysOperationPermission/delete', '');
INSERT INTO `sys_operation` VALUES ('11', '4', '查询所有角色', 'SysRole/list', '');
INSERT INTO `sys_operation` VALUES ('12', '4', '添加角色', 'SysRole/save,SysMenuPermission/listAllForSysRole', '');
INSERT INTO `sys_operation` VALUES ('13', '4', '修改角色', 'SysRole/update#SysRole/getAllPermissionsId,SysMenuPermission/listAllForSysRole', '修改角色需要获得用户的相关角色权限');
INSERT INTO `sys_operation` VALUES ('14', '4', '删除角色', 'SysRole/delete', '');
INSERT INTO `sys_operation` VALUES ('15', '3', '查询用户列表', 'SysUser/list', '');
INSERT INTO `sys_operation` VALUES ('16', '3', '添加用户', 'SysUser/save,SysRole/all', '');
INSERT INTO `sys_operation` VALUES ('17', '3', '修改用户', 'SysUser/update,SysRole/all', '');
INSERT INTO `sys_operation` VALUES ('18', '3', '删除用户', 'SysUser/delete', '');
INSERT INTO `sys_operation` VALUES ('19', '3', '显示添加用户按钮', 'SysUserAddBtn', '显示权限');
INSERT INTO `sys_operation` VALUES ('20', '3', '显示删除用户按钮', 'SysUserDelBtn', '显示权限');
INSERT INTO `sys_operation` VALUES ('21', '3', '显示修改用户按钮', 'SysUserUpdateBtn', '显示权限');
INSERT INTO `sys_operation` VALUES ('22', '3', '显示真实姓名列信息', 'showRealNameColumn', '显示权限');
INSERT INTO `sys_operation` VALUES ('23', '8', '添加新部门', 'Dept/save', '');
INSERT INTO `sys_operation` VALUES ('24', '8', '修改部门', 'Dept/update', '');
INSERT INTO `sys_operation` VALUES ('25', '8', '删除部门', 'Dept/delete', '');
INSERT INTO `sys_operation` VALUES ('26', '8', '查询部门列表', 'Dept/list', '');
INSERT INTO `sys_operation` VALUES ('27', '8', '显示动作-删除部门', 'deptDeleteShow', '');
INSERT INTO `sys_operation` VALUES ('28', '9', '添加员工', 'Emp/save', '');
INSERT INTO `sys_operation` VALUES ('29', '9', '修改员工', 'Emp/update,Emp/allDept', '');
INSERT INTO `sys_operation` VALUES ('30', '9', '删除员工', 'Emp/delete', '');
INSERT INTO `sys_operation` VALUES ('31', '9', '查看员工列表', 'Emp/list,Emp/allDept', '');
INSERT INTO `sys_operation` VALUES ('32', '12', '查询日志', 'SysLog/list', '');
INSERT INTO `sys_operation` VALUES ('33', '1', '后台管理中心权限', 'toMain', '登录到后台必须授予');
INSERT INTO `sys_operation` VALUES ('34', '3', '查询角色列表', 'SysRole/all', '');
INSERT INTO `sys_operation` VALUES ('35', '4', '查询菜单权限和操作权限', 'SysRole/getAllPermissionsId,sysMenuPermission/listAll,sysMenuPermission/listAllForSysRole', '');
INSERT INTO `sys_operation` VALUES ('36', '1', '修改个人密码', 'SysUser/changePwd', '可以执行Change Password功能');
INSERT INTO `sys_operation` VALUES ('75', '17', '产品上架', '产品上传', '');
INSERT INTO `sys_operation` VALUES ('76', '18', '查询', 'Liquor/list', '');
INSERT INTO `sys_operation` VALUES ('77', '18', '新增', 'Liquor/save', '');
INSERT INTO `sys_operation` VALUES ('78', '18', 'get查询', 'Liquor/get', '');
INSERT INTO `sys_operation` VALUES ('79', '18', '修改', 'Liquor/update', '');
INSERT INTO `sys_operation` VALUES ('80', '18', '删除', 'Liquor/delete', '');
INSERT INTO `sys_operation` VALUES ('81', '18', '批量删除', 'Liquor/deleteByIds', '');
INSERT INTO `sys_operation` VALUES ('82', '18', '删除1', 'Liquor/deleteBatch', '');
INSERT INTO `sys_operation` VALUES ('83', '29', '添加分类管理', 'AdvertisSort/addAdvertisSort', '');
INSERT INTO `sys_operation` VALUES ('84', '29', '加载广告分类详情', 'AdvertisSort/initAdvertisSortList', '');
INSERT INTO `sys_operation` VALUES ('85', '29', '添加广告信息', 'AdvertisInfo/addAdvertisInfo', '');
INSERT INTO `sys_operation` VALUES ('86', '29', '查询广告详情', 'AdvertisInfo/queryAdvertisInfo', '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '0', '拥有所有管理权限');
INSERT INTO `sys_role` VALUES ('2', '系统管理员', '0', '管理系统用户和权限分配。\r\n不能删除用户，不显示删除按钮；不显示真实姓名');
INSERT INTO `sys_role` VALUES ('3', 'HR', '0', '员工管理模块');
INSERT INTO `sys_role` VALUES ('4', '经理', '0', '报表查看');
INSERT INTO `sys_role` VALUES ('5', '演示用户', '0', '展示系统功能');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_RIGHTS_RIGHTS_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('4', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('5', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('5', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('2', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('2', '5');
INSERT INTO `sys_role_menu` VALUES ('5', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('2', '6');
INSERT INTO `sys_role_menu` VALUES ('5', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('3', '7');
INSERT INTO `sys_role_menu` VALUES ('5', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('3', '8');
INSERT INTO `sys_role_menu` VALUES ('5', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('3', '9');
INSERT INTO `sys_role_menu` VALUES ('5', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('4', '10');
INSERT INTO `sys_role_menu` VALUES ('5', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('4', '11');
INSERT INTO `sys_role_menu` VALUES ('5', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('2', '12');
INSERT INTO `sys_role_menu` VALUES ('5', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '23');
INSERT INTO `sys_role_menu` VALUES ('1', '24');
INSERT INTO `sys_role_menu` VALUES ('1', '25');
INSERT INTO `sys_role_menu` VALUES ('1', '26');
INSERT INTO `sys_role_menu` VALUES ('2', '26');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('2', '30');

-- ----------------------------
-- Table structure for `sys_role_operation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operation`;
CREATE TABLE `sys_role_operation` (
  `ROLE_ID` int(11) NOT NULL,
  `OPERATION_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_OPERATION_RIGHTS_OPERATION_RIGHTS_ID` (`OPERATION_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_operation_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_operation_ibfk_2` FOREIGN KEY (`OPERATION_PERMISSION_ID`) REFERENCES `sys_operation` (`OPERATION_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_operation
-- ----------------------------
INSERT INTO `sys_role_operation` VALUES ('1', '1');
INSERT INTO `sys_role_operation` VALUES ('2', '1');
INSERT INTO `sys_role_operation` VALUES ('5', '1');
INSERT INTO `sys_role_operation` VALUES ('1', '2');
INSERT INTO `sys_role_operation` VALUES ('2', '2');
INSERT INTO `sys_role_operation` VALUES ('1', '3');
INSERT INTO `sys_role_operation` VALUES ('1', '4');
INSERT INTO `sys_role_operation` VALUES ('2', '4');
INSERT INTO `sys_role_operation` VALUES ('1', '5');
INSERT INTO `sys_role_operation` VALUES ('2', '5');
INSERT INTO `sys_role_operation` VALUES ('1', '6');
INSERT INTO `sys_role_operation` VALUES ('2', '6');
INSERT INTO `sys_role_operation` VALUES ('5', '6');
INSERT INTO `sys_role_operation` VALUES ('1', '7');
INSERT INTO `sys_role_operation` VALUES ('2', '7');
INSERT INTO `sys_role_operation` VALUES ('5', '7');
INSERT INTO `sys_role_operation` VALUES ('1', '8');
INSERT INTO `sys_role_operation` VALUES ('2', '8');
INSERT INTO `sys_role_operation` VALUES ('1', '9');
INSERT INTO `sys_role_operation` VALUES ('2', '9');
INSERT INTO `sys_role_operation` VALUES ('1', '10');
INSERT INTO `sys_role_operation` VALUES ('2', '10');
INSERT INTO `sys_role_operation` VALUES ('1', '11');
INSERT INTO `sys_role_operation` VALUES ('2', '11');
INSERT INTO `sys_role_operation` VALUES ('5', '11');
INSERT INTO `sys_role_operation` VALUES ('1', '12');
INSERT INTO `sys_role_operation` VALUES ('2', '12');
INSERT INTO `sys_role_operation` VALUES ('1', '13');
INSERT INTO `sys_role_operation` VALUES ('2', '13');
INSERT INTO `sys_role_operation` VALUES ('1', '14');
INSERT INTO `sys_role_operation` VALUES ('1', '15');
INSERT INTO `sys_role_operation` VALUES ('2', '15');
INSERT INTO `sys_role_operation` VALUES ('5', '15');
INSERT INTO `sys_role_operation` VALUES ('1', '16');
INSERT INTO `sys_role_operation` VALUES ('2', '16');
INSERT INTO `sys_role_operation` VALUES ('1', '17');
INSERT INTO `sys_role_operation` VALUES ('2', '17');
INSERT INTO `sys_role_operation` VALUES ('1', '18');
INSERT INTO `sys_role_operation` VALUES ('1', '19');
INSERT INTO `sys_role_operation` VALUES ('2', '19');
INSERT INTO `sys_role_operation` VALUES ('5', '19');
INSERT INTO `sys_role_operation` VALUES ('1', '20');
INSERT INTO `sys_role_operation` VALUES ('5', '20');
INSERT INTO `sys_role_operation` VALUES ('1', '21');
INSERT INTO `sys_role_operation` VALUES ('2', '21');
INSERT INTO `sys_role_operation` VALUES ('5', '21');
INSERT INTO `sys_role_operation` VALUES ('1', '22');
INSERT INTO `sys_role_operation` VALUES ('2', '22');
INSERT INTO `sys_role_operation` VALUES ('5', '22');
INSERT INTO `sys_role_operation` VALUES ('1', '23');
INSERT INTO `sys_role_operation` VALUES ('3', '23');
INSERT INTO `sys_role_operation` VALUES ('5', '23');
INSERT INTO `sys_role_operation` VALUES ('1', '24');
INSERT INTO `sys_role_operation` VALUES ('3', '24');
INSERT INTO `sys_role_operation` VALUES ('5', '24');
INSERT INTO `sys_role_operation` VALUES ('1', '25');
INSERT INTO `sys_role_operation` VALUES ('3', '25');
INSERT INTO `sys_role_operation` VALUES ('5', '25');
INSERT INTO `sys_role_operation` VALUES ('1', '26');
INSERT INTO `sys_role_operation` VALUES ('3', '26');
INSERT INTO `sys_role_operation` VALUES ('5', '26');
INSERT INTO `sys_role_operation` VALUES ('1', '27');
INSERT INTO `sys_role_operation` VALUES ('5', '27');
INSERT INTO `sys_role_operation` VALUES ('1', '28');
INSERT INTO `sys_role_operation` VALUES ('3', '28');
INSERT INTO `sys_role_operation` VALUES ('5', '28');
INSERT INTO `sys_role_operation` VALUES ('1', '29');
INSERT INTO `sys_role_operation` VALUES ('3', '29');
INSERT INTO `sys_role_operation` VALUES ('5', '29');
INSERT INTO `sys_role_operation` VALUES ('1', '30');
INSERT INTO `sys_role_operation` VALUES ('3', '30');
INSERT INTO `sys_role_operation` VALUES ('5', '30');
INSERT INTO `sys_role_operation` VALUES ('1', '31');
INSERT INTO `sys_role_operation` VALUES ('3', '31');
INSERT INTO `sys_role_operation` VALUES ('5', '31');
INSERT INTO `sys_role_operation` VALUES ('1', '32');
INSERT INTO `sys_role_operation` VALUES ('2', '32');
INSERT INTO `sys_role_operation` VALUES ('5', '32');
INSERT INTO `sys_role_operation` VALUES ('1', '33');
INSERT INTO `sys_role_operation` VALUES ('2', '33');
INSERT INTO `sys_role_operation` VALUES ('3', '33');
INSERT INTO `sys_role_operation` VALUES ('4', '33');
INSERT INTO `sys_role_operation` VALUES ('5', '33');
INSERT INTO `sys_role_operation` VALUES ('1', '34');
INSERT INTO `sys_role_operation` VALUES ('5', '34');
INSERT INTO `sys_role_operation` VALUES ('1', '35');
INSERT INTO `sys_role_operation` VALUES ('5', '35');
INSERT INTO `sys_role_operation` VALUES ('1', '36');
INSERT INTO `sys_role_operation` VALUES ('1', '75');
INSERT INTO `sys_role_operation` VALUES ('1', '76');
INSERT INTO `sys_role_operation` VALUES ('1', '77');
INSERT INTO `sys_role_operation` VALUES ('1', '78');
INSERT INTO `sys_role_operation` VALUES ('1', '79');
INSERT INTO `sys_role_operation` VALUES ('1', '80');
INSERT INTO `sys_role_operation` VALUES ('1', '81');
INSERT INTO `sys_role_operation` VALUES ('1', '82');
INSERT INTO `sys_role_operation` VALUES ('1', '83');
INSERT INTO `sys_role_operation` VALUES ('1', '84');
INSERT INTO `sys_role_operation` VALUES ('1', '85');
INSERT INTO `sys_role_operation` VALUES ('1', '86');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REAL_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UQ_SYS_USER_NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '172eee54aa664e9dd0536b063796e54e', '0', '晁关');
INSERT INTO `sys_user` VALUES ('2', 'user', '37cf6e1a4cd5a940ae416392ac26768d', '0', '蒲关');
INSERT INTO `sys_user` VALUES ('3', 'hr', '16f60453bf87e1625f811c295e1a34fc', '0', '任立');
INSERT INTO `sys_user` VALUES ('4', 'manager', '357d5b7e9946c6bfb8d91140c31f7074', '0', '荆力');
INSERT INTO `sys_user` VALUES ('5', 'demo', 'b4ee1f625d0fef4815a761c744a16a61', '0', '戴谋');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_FK_SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`USER_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '2');
INSERT INTO `sys_user_role` VALUES ('3', '3');
INSERT INTO `sys_user_role` VALUES ('4', '3');
INSERT INTO `sys_user_role` VALUES ('5', '3');
INSERT INTO `sys_user_role` VALUES ('5', '4');
INSERT INTO `sys_user_role` VALUES ('5', '5');

-- ----------------------------
-- Table structure for `tb_advertis_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_advertis_info`;
CREATE TABLE `tb_advertis_info` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'ID',
  `parent_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '广告类别',
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '分类广告名称',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `status` int(1) DEFAULT '0' COMMENT '1 显示 ，0 隐藏',
  `create_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建时间',
  `start_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '结束时间',
  `url` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '链接地址',
  `img_size` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片尺寸',
  `img_norms` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片规格',
  `img_path` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片路径',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描素信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_advertis_info
-- ----------------------------
INSERT INTO `tb_advertis_info` VALUES ('158ffbad-1d36-11e8-aa39-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', null, '2', '1', '2018-03-01 17:51:16', '', '', 'http://www.baidu.com', null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('21321', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', '11', null, '0', null, null, null, null, null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('3aaac16a-1d37-11e8-aa39-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', '天天', '3', '1', '2018-03-01 17:59:27', '', '', 'www.baidu.com', null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('6d14c5f5-1dbb-11e8-9a78-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', '213221', '11', '1', '2018-03-02 09:45:46', '', '', '1111', null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('86e7be80-1d35-11e8-aa39-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', null, '1', '1', '2018-03-01 17:47:16', '', '', 'AdvertisInfo/addAdvertisInfo', null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('8831bcf0-1dba-11e8-9a78-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', '22222222222222222222', '22', '1', '2018-03-02 09:39:22', '', '', '1111', null, null, null, null);
INSERT INTO `tb_advertis_info` VALUES ('b430de1e-2057-11e8-b1ca-3c970e92e04c', '14ced7d8-0d60-11e8-88d5-3c970e92e04c', '测试22222', '1', '1', '2018-03-05 17:29:29', '', '', 'www.baidu.com', null, null, null, null);

-- ----------------------------
-- Table structure for `tb_advertis_sort`
-- ----------------------------
DROP TABLE IF EXISTS `tb_advertis_sort`;
CREATE TABLE `tb_advertis_sort` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'ID',
  `code` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '分类广告名称',
  `status` int(1) DEFAULT '0' COMMENT '1 显示 ，0 隐藏',
  `create_time` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描素信息',
  PRIMARY KEY (`id`),
  KEY `idx_advertis_sort` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='分类广告信息表';

-- ----------------------------
-- Records of tb_advertis_sort
-- ----------------------------
INSERT INTO `tb_advertis_sort` VALUES ('14ced7d8-0d60-11e8-88d5-3c970e92e04c', '2018020900007', '产品3', '1', '2018-02-09 14:11:35', '产品3				');
INSERT INTO `tb_advertis_sort` VALUES ('1f9c08b3-20ec-11e8-90ef-3c970e92e04c', '2018030600031', '43w4242', '1', '2018-03-06 11:11:54', '12313123213				');
INSERT INTO `tb_advertis_sort` VALUES ('2fbcd347-0d60-11e8-88d5-3c970e92e04c', '2018020900008', '2131321', '1', '2018-02-09 14:12:20', '	31231231312			');
INSERT INTO `tb_advertis_sort` VALUES ('508d9009-210f-11e8-90ef-3c970e92e04c', '2018030600032', '43w424211111111111111111111111', '1', '2018-03-06 15:23:49', '	21421421412');
INSERT INTO `tb_advertis_sort` VALUES ('5330ebc7-0d5e-11e8-88d5-3c970e92e04c', '2018020900002', '21312313', '1', '2018-02-09 13:59:00', '213213213				');
INSERT INTO `tb_advertis_sort` VALUES ('54221ac7-210f-11e8-90ef-3c970e92e04c', '2018030600033', '43w424211111111111111111111111', '1', '2018-03-06 15:23:55', '	21421421412');
INSERT INTO `tb_advertis_sort` VALUES ('5480544f-0d5e-11e8-88d5-3c970e92e04c', '2018020900003', '21312313', '1', '2018-02-09 13:59:03', '213213213				');
INSERT INTO `tb_advertis_sort` VALUES ('59029778-1aa2-11e8-8c1b-3c970e92e04c', '2018022600022', 'ewrwer', '1', '2018-02-26 11:08:41', 'werwerwerwe				');
INSERT INTO `tb_advertis_sort` VALUES ('5d7f1100-0d47-11e8-88d5-3c970e92e04c', '2018020900001', '213123', '1', '2018-02-09 11:14:39', '	1312312			');
INSERT INTO `tb_advertis_sort` VALUES ('63e8e191-0d60-11e8-88d5-3c970e92e04c', '2018020900009', '(⇀‸↼‶)412', '1', '2018-02-09 14:13:47', '	2131232131			');
INSERT INTO `tb_advertis_sort` VALUES ('65271f0a-1aa2-11e8-8c1b-3c970e92e04c', '2018022600023', '213213213', '1', '2018-02-26 11:09:01', '1231231312				');
INSERT INTO `tb_advertis_sort` VALUES ('6542fcac-1d2a-11e8-aa39-3c970e92e04c', '2018030100029', 'ceshi111', '1', '2018-03-01 16:27:35', '	1111			');
INSERT INTO `tb_advertis_sort` VALUES ('6c66c658-1d1c-11e8-aa39-3c970e92e04c', '2018030100027', '测试分类', '1', '2018-03-01 14:47:34', '	测试分类			');
INSERT INTO `tb_advertis_sort` VALUES ('7549e9e6-0d60-11e8-88d5-3c970e92e04c', '2018020900010', 'asdadas', '1', '2018-02-09 14:14:17', '	wdeqwewqeq			');
INSERT INTO `tb_advertis_sort` VALUES ('8abd473c-1aa3-11e8-8c1b-3c970e92e04c', '2018022600025', '214312312', '1', '2018-02-26 11:17:14', '312312312				');
INSERT INTO `tb_advertis_sort` VALUES ('8ebfa491-1aa3-11e8-8c1b-3c970e92e04c', '2018022600026', '214312312qqq', '1', '2018-02-26 11:17:21', '312312312				');
INSERT INTO `tb_advertis_sort` VALUES ('9dbd896f-0d60-11e8-88d5-3c970e92e04c', '2018020900011', '21312', '1', '2018-02-09 14:15:24', '		21321		');
INSERT INTO `tb_advertis_sort` VALUES ('b0b415f6-0d61-11e8-88d5-3c970e92e04c', '2018020900012', '21312312', '1', '2018-02-09 14:23:06', '	123213123			');
INSERT INTO `tb_advertis_sort` VALUES ('b75209f5-0fa0-11e8-bcc2-3c970e92e04c', '2018021200014', '2222222222qqqq', '1', '2018-02-12 10:59:17', 'qqqqq				');
INSERT INTO `tb_advertis_sort` VALUES ('bcb99da7-1aa2-11e8-8c1b-3c970e92e04c', '2018022600024', '323123213', '1', '2018-02-26 11:11:28', '213123123				');
INSERT INTO `tb_advertis_sort` VALUES ('cdfc56d1-1906-11e8-9880-3c970e92e04c', '2018022400015', '测试', '1', '2018-02-24 10:02:45', '123123213				');
INSERT INTO `tb_advertis_sort` VALUES ('d81b040b-0d61-11e8-88d5-3c970e92e04c', '2018020900013', 'cdsfsf', '1', '2018-02-09 14:24:12', 'dsfdsfsdf				');
INSERT INTO `tb_advertis_sort` VALUES ('d897f188-1906-11e8-9880-3c970e92e04c', '2018022400016', '2134234', '1', '2018-02-24 10:03:02', '234234				');
INSERT INTO `tb_advertis_sort` VALUES ('d994f16b-1d20-11e8-aa39-3c970e92e04c', '2018030100028', '首页轮播图', '1', '2018-03-01 15:19:16', '首页轮播图				');
INSERT INTO `tb_advertis_sort` VALUES ('dcb6d37b-1906-11e8-9880-3c970e92e04c', '2018022400017', '213423411', '1', '2018-02-24 10:03:09', '234234				');
INSERT INTO `tb_advertis_sort` VALUES ('e03b898d-1906-11e8-9880-3c970e92e04c', '2018022400018', '213423411', '1', '2018-02-24 10:03:15', '234234				11');
INSERT INTO `tb_advertis_sort` VALUES ('e2fa1ef1-1d2f-11e8-aa39-3c970e92e04c', '2018030100030', 'wqreqweqeq', '1', '2018-03-01 17:06:54', '	eqweqwewq			');
INSERT INTO `tb_advertis_sort` VALUES ('e35613af-1906-11e8-9880-3c970e92e04c', '2018022400019', '213423411111', '1', '2018-02-24 10:03:20', '234234				11');
INSERT INTO `tb_advertis_sort` VALUES ('e64ab254-1906-11e8-9880-3c970e92e04c', '2018022400020', '213423411111', '1', '2018-02-24 10:03:25', '234234				11');
INSERT INTO `tb_advertis_sort` VALUES ('ead5044c-0d5e-11e8-88d5-3c970e92e04c', '2018020900004', '产品1', '1', '2018-02-09 14:03:15', '213213213				');
INSERT INTO `tb_advertis_sort` VALUES ('ec44cc22-0d5e-11e8-88d5-3c970e92e04c', '2018020900005', '产品1', '1', '2018-02-09 14:03:17', '213213213				');
INSERT INTO `tb_advertis_sort` VALUES ('f1649768-1906-11e8-9880-3c970e92e04c', '2018022400021', '324234', '1', '2018-02-24 10:03:44', '42342342				');
INSERT INTO `tb_advertis_sort` VALUES ('fa323450-0d5e-11e8-88d5-3c970e92e04c', '2018020900006', '产品2', '1', '2018-02-09 14:03:41', '产品2			');

-- ----------------------------
-- Table structure for `tb_file_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_file_info`;
CREATE TABLE `tb_file_info` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '文件原始名称',
  `file_name` varchar(64) DEFAULT NULL COMMENT '转换后的文件名称',
  `path` varchar(100) DEFAULT NULL COMMENT '文件存储路径',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_file_info
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_liquor_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_liquor_info`;
CREATE TABLE `tb_liquor_info` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL COMMENT '酒产品名称',
  `vender_price` varchar(10) DEFAULT NULL COMMENT '供应商价格',
  `capacity` varchar(10) DEFAULT NULL COMMENT '容量，即单位为多少毫升',
  `type` varchar(10) DEFAULT NULL COMMENT '酱香，浓香，醇香',
  `ingredients` varchar(255) DEFAULT NULL COMMENT '成份（组成成份）',
  `inventory` int(11) DEFAULT NULL COMMENT '库存量，即该产品存在的数量',
  `file_id` varchar(255) DEFAULT NULL COMMENT ' 附件编号，多个附件用逗号隔开,一般为图片信息',
  `vender` varchar(100) DEFAULT NULL COMMENT '生产厂商(供应商)',
  `vender_telephone` varchar(50) DEFAULT NULL COMMENT '生产厂商(供应商)联系电话',
  `vender_address` varchar(100) DEFAULT NULL COMMENT '生产厂商(供应商)地址',
  `sales_company` varchar(100) DEFAULT NULL COMMENT '销售产品的公司，即代理产品的公司',
  `sales_address` varchar(100) DEFAULT NULL COMMENT '销售产品的公司地址',
  `sales_price` varchar(10) DEFAULT NULL COMMENT '销售价格',
  `sales_telephone` varchar(50) DEFAULT NULL COMMENT '销售公司联系电话',
  `sales_desc` varchar(500) DEFAULT NULL COMMENT '销售信息描素',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `concentration` varchar(10) DEFAULT NULL COMMENT '浓度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_liquor_info
-- ----------------------------
INSERT INTO `tb_liquor_info` VALUES ('04ab8318-f07f-11e7-b99a-3c970e92e04c', '北京二锅头', '58.8元', '500ML', '', '', null, '', '', '', '', '', '', '', '', '', '', '');
