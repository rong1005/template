-- MySQL dump 10.13  Distrib 5.4.3-beta, for Win32 (ia32)
--
-- Host: localhost    Database: sq_ggecwx
-- ------------------------------------------------------
-- Server version	5.4.3-beta-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_department`
--

DROP TABLE IF EXISTS `tb_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `higher_department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_id9eubd0ikv9whim66pe7380b` (`higher_department_id`) USING BTREE,
  CONSTRAINT `tb_department_ibfk_1` FOREIGN KEY (`higher_department_id`) REFERENCES `tb_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_department`
--

LOCK TABLES `tb_department` WRITE;
/*!40000 ALTER TABLE `tb_department` DISABLE KEYS */;
INSERT INTO `tb_department` VALUES (1,NULL,NULL,'公司总部','羽科技有限公司',NULL),(2,NULL,NULL,'搞软件开发的','软件中心',1),(3,NULL,NULL,'负责发钱的！','财务中心',1),(4,NULL,NULL,'JAVA开发','JAVA开发一组',2),(5,NULL,NULL,'测试','测试组',2),(6,NULL,NULL,'JAVA开发','JAVA开发二组',2);
/*!40000 ALTER TABLE `tb_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_employee`
--

DROP TABLE IF EXISTS `tb_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `whether` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_employee`
--

LOCK TABLES `tb_employee` WRITE;
/*!40000 ALTER TABLE `tb_employee` DISABLE KEYS */;
INSERT INTO `tb_employee` VALUES (1,NULL,NULL,'152217','248858868@qq.com','Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1),(2,NULL,NULL,'0012','ggecit_line@163.com','test','',NULL);
/*!40000 ALTER TABLE `tb_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_menu`
--

DROP TABLE IF EXISTS `tb_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` text,
  `is_using` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_menu`
--

LOCK TABLES `tb_menu` WRITE;
/*!40000 ALTER TABLE `tb_menu` DISABLE KEYS */;
INSERT INTO `tb_menu` VALUES (1,'2014-05-21 08:52:23','2014-05-21 08:52:23','{\r\n     \"button\":[\r\n     {	\r\n          \"type\":\"click\",\r\n          \"name\":\"今日歌曲\",\r\n          \"key\":\"V1001_TODAY_MUSIC\"\r\n      },\r\n      {\r\n           \"type\":\"click\",\r\n           \"name\":\"歌手简介\",\r\n           \"key\":\"V1001_TODAY_SINGER\"\r\n      },\r\n      {\r\n           \"name\":\"菜单\",\r\n           \"sub_button\":[\r\n           {	\r\n               \"type\":\"view\",\r\n               \"name\":\"搜索\",\r\n               \"url\":\"http://www.soso.com/\"\r\n            },\r\n            {\r\n               \"type\":\"view\",\r\n               \"name\":\"视频\",\r\n               \"url\":\"http://v.qq.com/\"\r\n            },\r\n            {\r\n               \"type\":\"click\",\r\n               \"name\":\"赞一下我们\",\r\n               \"key\":\"V1001_GOOD\"\r\n            }]\r\n       }]\r\n }',1,'自定义菜单');
/*!40000 ALTER TABLE `tb_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_news_category`
--

DROP TABLE IF EXISTS `tb_news_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_news_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `message_category` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_news_category`
--

LOCK TABLES `tb_news_category` WRITE;
/*!40000 ALTER TABLE `tb_news_category` DISABLE KEYS */;
INSERT INTO `tb_news_category` VALUES (4,NULL,NULL,'没有描述',1,'2014年04期刊','201404',1),(5,NULL,NULL,'没有描述',1,'2014年03期刊','201403',0),(6,NULL,NULL,'没有描述',0,'公司简介','gsjj',1),(7,NULL,NULL,'无描述',3,'主要产品','ZYCP',1),(8,NULL,NULL,'新闻咨询的描述',4,'新闻资讯','XWZX',1);
/*!40000 ALTER TABLE `tb_news_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_news_message`
--

DROP TABLE IF EXISTS `tb_news_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_news_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `content` text,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `big_picurl` varchar(255) DEFAULT NULL,
  `small_picurl` varchar(255) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `news_category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7fwduitiyc9odqpy3ukq1pdhp` (`news_category_id`) USING BTREE,
  CONSTRAINT `tb_news_message_ibfk_1` FOREIGN KEY (`news_category_id`) REFERENCES `tb_news_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_news_message`
--

LOCK TABLES `tb_news_message` WRITE;
/*!40000 ALTER TABLE `tb_news_message` DISABLE KEYS */;
INSERT INTO `tb_news_message` VALUES (3,NULL,NULL,'<p style=\"text-align: center;\"><img width=\"100%\" alt=\"1401096587411077698.jpg\" src=\"/template/static/uploads/image/20140526/1401096587411077698.jpg\"/></p><p><span style=\"color: rgb(13, 13, 13);line-height: 150%;text-indent: 28px;font-family: 宋体\">&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color: rgb(13, 13, 13); line-height: 150%; text-indent: 28px; font-family: 宋体; font-size: 18px;\">国光电器股份有限公司前身为广州国光电声总厂，创建时间可追溯至1951年，是全球知名的电声制造厂商，成立至今一直从事电声、电子产品的设计、生产、销售，目前产品覆盖了电声配件、扬声器单元、音响系统、数字功放、聚合物锂电池等。国光电器于2005年5月23日在中国深圳证券交易所挂牌上市（股票代码：002045），截至2013年末，公司股本为416,904,000元，总资产超过26亿元，2013年实现销售收入超过20亿元，拥有在册员工及劳务工近6,000人。</span></span></p><p><span style=\"color: rgb(13, 13, 13); font-family: 宋体; line-height: 150%; text-indent: 32px; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;公司具有从扬声器关键零部件，到扬声器单元、音箱系统及电子功放一体化的综合配套能力，六十多年的历史、六十多年的专注、六十多年的积淀凝聚了一批优秀的电声行业管理及研发人才，形成了自己独特、有效的科研攻关体制，建立了一支技术专业齐全、综合实力雄厚的研发队伍。260余名研发人员，264项专利设计，每年开发200多项新产品，亚洲一流的消音室、声学测试中心，125&nbsp;条各类音响产品生产线，扬声器日生产能力35万只，电子功放日生产能力3万套，音箱日生产能力12万套，锂离子电池日生产能力12万只的生产能力让国光能够满足市场不同领域和不同客户的研发和生产需求。</span></p><p><span style=\"color: rgb(13, 13, 13); font-family: 宋体; line-height: 150%; text-indent: 32px; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;在质量管理系统方面公司采用国际标准，从1994年开始实施ISO9001质量管理体系，之后相继通过了ISO/TS16949质量体系及ISO14001环境管理体系认证，公司检测中心还通过了CNAS的ISO/IEC17025认可，在生产中采用与国际同步的工艺、设备及控制流程，产品各项质量指标得到可靠保证，公司从设计到采购、进料检验、仓储物流、生产、成品检验、销售和售后服务形成了全过程质量控制系统。</span></p><p><span style=\"color: rgb(13, 13, 13); font-family: 宋体; line-height: 150%; text-indent: 32px; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;独有的研发设计和生产管控团队，日臻完善的质量管理体系，持续稳健的经营策略，为公司赢得了来自各个方面的荣誉，包括“信用企业”、“重点企业”、“创新型企业”、“诚信企业”等，随着公司综合实力的不断提升以及公司多类型的产品线，包括多媒体音响、专业音响、消费类音响、通讯类音响等也使得公司拥有长期稳定的优质客户群，并不断给公司带来新的市场机会和发展空间。</span></p><p><span style=\"color: rgb(13, 13, 13); font-family: 宋体; line-height: 150%; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;放眼未来，公司将充分利用专业设计和生产扬声器的传统优势，逐步加大对自主知识产权技术的研发力度，紧紧围绕“让生活更美好”的企业愿景，以产生良好的社会效益和经济效益为目标，国光人一直都秉承“以人为本，为员工创造机会，为股东创造效益，为社会承担责任”的经营宗旨，牢记“精细高效，诚信负责，创新友爱”价值观，并把价值观的精髓和“踏实、勤奋、精细、果断、革新、快速、负责、公正、自强、友爱”的国光作风贯穿到工作始终，把电声产业逐步做大做强，力争打造成为世界一流的电声专业生产厂商！</span></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587552076490.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587584042062.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587660038569.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587698020301.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587723049541.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587756010604.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587779025737.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587824052573.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587848077709.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587874046280.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587897057599.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587921057270.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587948006834.jpg\"/></p><p style=\"text-align: center;\"><img width=\"100%\" src=\"/template/static/uploads/image/20140526/1401096587976073697.jpg\"/></p><p><br/></p>','国光电器股份有限公司前身为广州国光电声总厂，创建时间可追溯至1951年，是全球知名的电声制造厂商，成立至今一直从事电声、电子产品的设计、生产、销售，目前产品覆盖了电声配件、扬声器单元、音响系统、数字功放、聚合物锂电池等。','国光电器股份有限公司','/html/weixin/news-message/1401096905441.html','/static/uploads/news-message/big/ggec_life_better.jpg','/static/uploads/news-message/small/64x64图标.jpg',NULL,6),(4,NULL,NULL,'<p><span style=\"font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;扬声器又称喇叭是一种十分常用的电声&gt;换能器件，在发声的电子电气设备中都能见到它。</span></p><p><span style=\"font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;扬声器在音响设备中是一个最薄弱的器件，而对于音响效果而言，它又是一个最重要的部件。扬声器的种类繁多，而且价格相差很大。音频电能通过电磁，压电或静电效应，使其纸盆或膜片振动并与周围的空气产生共振（共鸣）而发出声音。按换能机理和结构分动圈式（电动式）、电容式（静电式）、压电式（晶体或陶瓷）、电磁式（压簧式）、电离子式和气动式扬声器等，电动式扬声器按纸盆形状分圆形、椭圆形、双纸盆和橡皮折环；按工作频率分低音、中音、高音，有的还分成录音机专用、电视机专用、普通和高保真扬声器等；按音圈阻抗分低阻抗和高阻抗；按效果分直辐和环境声等。</span></p><table><tbody><tr class=\"firstRow\"><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\"><img src=\"/template/static/uploads/image/20140528/1401247303522063889.png\" title=\"1401247303522063889.png\" alt=\"1401157275282043446.png\"/></td></tr><tr><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"text-align: center;\">扬声器单元</span></td></tr></tbody></table><p>&nbsp;</p><table><tbody><tr class=\"firstRow\"><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\"><img src=\"/template/static/uploads/image/20140527/1401157275410068153.jpg\" alt=\"1401157275410068153.jpg\"/></td></tr><tr><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\">微型扬声器&nbsp;</td></tr></tbody></table><table><tbody><tr class=\"firstRow\"><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\"><img src=\"/template/static/uploads/image/20140527/1401157275454046053.png\" alt=\"1401157275454046053.png\"/></td></tr><tr><td width=\"1281\" valign=\"top\" style=\"word-break: break-all;\"><span style=\"text-align: center;\">喇叭&nbsp;</span></td></tr></tbody></table><p><br/></p>','扬声器又称喇叭是一种十分常用的电声>换能器件，在发声的电子电气设备中都能见到它','扬声器单元','/html/weixin/news-message/1401157600385.html','/static/uploads/news-message/big/ggec_logo2.jpg','/static/uploads/news-message/small/1401157275282043446.png',NULL,7),(5,NULL,NULL,'<p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">HI-FI音箱</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;是英语High-Fidelity的缩写，直译为高保真，其定义是：与原来的声音高度相似的重发设备。音箱是将电信号还原成声音信号的一种装置，还原真实性将作为评价音箱性能的重要标准。HIFI音箱的声音是清晰的，注重音乐的细节，注重背景宁静而突出音乐，因此感觉是清水流过心田的感觉。HIFI音箱多为2.0，注重失真率，速度感，音乐感，声场以及定位，让人闭上眼睛感觉不到音箱的存在而只是有演奏者。</span></p><p><br/></p><p><br/></p><p style=\"text-align: center;\"><img src=\"/template/static/uploads/image/20140527/1401157845322079386.png\" title=\"FA11白 小.png\" style=\"border: none; margin: 0px; padding: 0px; float: none;\"/><img src=\"/template/static/uploads/image/20140527/1401157846235094785.png\" title=\"R1 小.png\" style=\"border: none; margin: 0px; padding: 0px; float: none;\"/><img src=\"/template/static/uploads/image/20140527/1401157846485059758.png\" title=\"F6小.png\" style=\"border: none; margin: 0px; padding: 0px; float: none;\"/></p><p><br/></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">一体化音响</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;随着社会的发展，科技水平的不断创新，让人们的生活条件也在迅速攀升，家庭中必不可少的一部分就是影音娱乐系统，传统的家庭影院播放系统的比较重要的一个组成部分为音箱系统。最基本的音箱系统为5.1声道，一对主音箱、一对环绕音箱、 一只中置音箱和一只超低音音箱。但是现在科技的创新已经让人们对这些繁琐的配置说NO！一体化音箱很合理的把这些大大小小的箱体整合在了一起，这样的设计理念也是未来市场上最为流行的产品。<br/></span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">Hi-Fi音响系统从结构上可分为一体式、套装式及组合式。一体式的音响系统将各种功能的器材和扬声器组装在一个机箱内，不可以随意拆开。套装式音响系统是由生产商设计，将各种器材单搭配成套，各个单元之间可以拆开。音响组合则是根据个人的爱好选择各种型号的器材，进行自由组合。</span></p><p style=\"text-align: center;\"><img src=\"/template/static/uploads/image/20140527/1401157846833077447.png\" title=\"AT-1 小.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">多媒体音箱</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;多媒体音箱就是“电脑音箱”，它的主要音源就是电脑，这就注定了它的听音环境是一个近声场，随着人们听音方式的改变，人们对音箱的需求特点也随之改变。多媒体音箱主要包括三个部分；1.接口部分&nbsp;，常用的包括立体声3.5输入接口,USB接口，IPOD&nbsp;DOCKING接口等等。&nbsp;2.放大器&nbsp;，主要有2.0双声道立体声式及2.1双声道另加―超重低音声道式。3.喇叭，&nbsp;根据功率的大小，箱体的大小选择合适的喇叭组成一个多媒体音响。</span></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157847170052050.png\" title=\"HF4小.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157847466019276.png\" title=\"JF5 小.png\" width=\"249\" height=\"300\" border=\"0\" hspace=\"0\" vspace=\"0\" style=\"border: none; margin: 0px; padding: 0px; width: 249px; height: 300px;\"/></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">家庭影院</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;家庭影院音响又叫AV音响，它的主要作用是配合电视机和影碟机欣赏电影、唱卡拉OK以及听音乐，它与HI-FI高度保真的设计思路截然不同。家庭影院音响Audio（音频）更好地配合Video（视频），用多个声道来营造动感和包围感，因为大家在观看影片时，大部分注意力被吸引到影像上。系统主要包括蓝光碟机，多通道功放机，音箱及高清电视机或投影机。功放作为音频播放的中心，它除了放大多通道音频功率之外，还具有环绕声解码功能。目前家庭影院最常见的解码技术有AC-3、DTS、Dobly-HD、DTS-HD、前两种具有5.1声道解码功能，能将影片还原为5.1声道效果；后两种在原来的基础上增加了两个环绕声道，形成7.1声道，影院效果的包围感更强。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157847707007295.png\" title=\"MT－6小.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157847835077077.png\" title=\"C360III小.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">蓝牙音箱</span></strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\"><br/></span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;蓝牙音箱就是将蓝牙技术应用在传统数码和多媒体音箱上，让使用者可以免除恼人电线的牵绊，自在地以各种方式聆听音乐。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">蓝牙音箱指的是内置蓝牙芯片，以蓝牙连接取代传统线材连接的音响设备，通过与手机、平板电脑和笔记本等蓝牙播放设备连接，达到方便快捷的目的。目前，蓝牙音箱以便携音箱为主，外形一般较为小巧便携，蓝牙音箱技术也凭借其方便人性的特点逐渐被消费者重视和接纳，市面上常见蓝牙音箱多为单声道音箱（单扬声单元）。</span></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157847916093770.png\" title=\"M2小.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">WIFI音箱</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;WIFI和蓝牙都是即时连接， 与普通的线材是一样的传输速度。WIFI是更高的无线连接，可以得到网络内分配的地址做终端使用，网络内的电脑都可连接并使用音响来播放音乐。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">WIFI音箱具有蓝牙无线音响接收器的全部功能，同时传输距离更远，具备穿墙能力、音质损耗更低、功耗更小。一款基于WIFI传输协议的无线音频产品，支持PC、手机、平板等设备将音乐信号，无损远距传输给音响系统，同时还支持WIFI信号无线ap中继功能。高达150mbps的传输带宽，传送高保真无损音乐绰绰有余;远达100米的直线传输距离，即便是豪宅也毫无压力。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">wifi音频接收器在组网状态，除了手机、平板、笔记本之外，有线连接路由器的台式机，也支持无线音频传输。网络中的任意音频播放设备，都可以联接主路由器或者SSID为WIFIplayer的热点无线传输音乐和上网冲浪。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><strong><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">声霸</span></strong></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;&nbsp;&nbsp;&nbsp;Soundbar音响是从西方引进的一种新型音响产品，国外市场比较流行。Soundbar的国内叫法并不统一：有声霸、条形音箱、回音壁、一体化数字投音机等。Soundbar音响主要特点为主机呈条状结构，可以横放在平板电视机前面，也可以挂在墙上。它主要有两种类型，一种带有独立的低音炮，一种为内置低音炮；其中，带有独立低音炮的声霸也分为有线连接和无线连接两种类型。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">Soundbar音响的魅力在于即使低音炮和主机部分需要电线连接，也仅仅需要一条电源线和一条音频线。传统的音柱产品，一般需要5-10条线连接，才能保证正常使用，Soundbar音响却可以尽可能地减少电线的使用，从而更好地实现影音娱乐功能。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">Soundbar音响的应用</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">随着平板电视的普及，技术的飞跃发展，电视机的厚度也越来越薄。平板电视带给人们非同寻常的视觉感受，但音质方面却显得不够出色，反不如CRT电视的音质饱满浑厚，消费者在听觉方面的需求无法得到较好的满足。SOUNDBAR，能够弥补平板电视音质方面的不足。</span></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">作为一种新兴的音响产品，SOUNDBAR音箱除了保留传统音响的特性之外，其数码味道也越来越浓重，是音响家族中将传统AV音响和数码技术结合的最彻底的一个成员，在兼顾音质的情况下，让你体会触摸到数码技术的魅力。</span></p><p style=\"text-align:center\"><img src=\"/template/static/uploads/image/20140527/1401157848077099164.png\" title=\"S5000.png\" style=\"border: none; margin: 0px; padding: 0px;\"/></p><p><span style=\"font-size: 18px; font-family: 宋体, SimSun;\">&nbsp;</span><br/></p><p><br/></p>','音响系统的描述','音响系统','/html/weixin/news-message/1401157981751.html',NULL,'/static/uploads/news-message/small/1401157847466019276.png',NULL,7),(6,NULL,NULL,'<p><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">&nbsp;&nbsp;&nbsp;&nbsp;为在集团各生产车间建立一种良性的PK文化，倡导各车间做好5S工作，营造争当第一的风气。4月23日，改善办组织集团各生产车间主管在大培训室召开2014年国光集团第一季度5S总结会，总裁办主任谢爱和、制造中心总经理胡洪、制造中心生产二部经理曹震凡、电池事业部生产经理周志泉、改善办主任邓霞玉等出席本次总结会。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">&nbsp;&nbsp;&nbsp;&nbsp;会议主要对第一季度5S工作及各车间5SPK情况进行汇报。2014年年初，改善办与制造中心、电池事业部、精密电子事业部一同修订和制定《制造中心5S管理标准1.0》、《电池事业部5S管理标准1.0》、《仰诚生产现场5S管理标准1.0》。在第一季度中，集团5S检查小组成员分别对24个生产车间进行2次5S检查，从5S检查效果及整改情况来看，表现突出的车间有生产一部J14W车间、生产二部J14E车间、生产三部B6车间、电池事业部二车间。</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">总裁办主任谢爱和为以上四个车间颁发</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">羚羊旗和</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">500</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">元奖金鼓励。同时，和姐对开展</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5SPK</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">活动表示肯定，她强调：“开展</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5SPK</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">活动是很有意义的，总结上阶段的</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S&nbsp;</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">工作，分享别人的经验，设置</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5SPK</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">奖励，目的是表彰做得好的，鞭策暂时落后的”，并为所有车间主管加油打气。制造中心总经理胡洪对</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">管理现状用</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">9</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">个字形容：</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">&nbsp;</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">一紧，二松，三垮，四重来，他表示：恶性循环的原因在于管理者的心没有放在</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">上，只要管理者带头做好</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">，各个车间都能得羚羊旗。电池事业部生产经理周志泉认为电池事业部“起步晚，进步快”的原因是电池事业部借鉴了前辈的好方法，同时第一季度应客户审核也做了很多准备，而</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">5S</span><span style=\"font-family: 宋体, SimSun; font-size: 20px;\">的难点是在于维持，对此，他表示压力大，但有信心做好。</span></p><p><br/></p>','','做好5S工作 争获羚羊旗','/html/weixin/news-message/1401158341402.html',NULL,NULL,NULL,8),(7,NULL,NULL,'<p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;为充分发挥生产员工在企业生产工作中的主力军作用，尊重和肯定员工的劳动价值，激发他们学技能、比操作、争优秀的工作积极性，达到提升员工的劳动技能，提升公司生产质量水平的目的，国光集团工会和制造中心今年继续在生产车间举行劳动竞赛。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;本次劳动竞赛的项目比去年的比赛项目增多了微型扬声器装配和塑胶部生产。竞赛评委人员由生产一部经理刘锡芳、生产二部经理马信、木箱部经理肖健鸿、塑胶部经理李淮等集团主任工程师以上职务人员担任。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;比赛采取当场评分形式，以竞赛项目得分高低确定比赛成绩,每个竞赛岗位评出一等奖1名、二等奖2名、三等奖3名，分别奖励300元，200元，100元和荣誉证书一本。除了个人奖项外，还设有3名车间团队组织奖和1名评委团队组织奖。在不影响正常生产的条件下，车间能积极组织员工参赛，通过组织员工参赛，帮助员工熟悉、复习操作技能，参赛团队有组织纪律性，不迟到，不无故缺席，总体获奖人员数多，总分高的参赛车间均有机会获得车间团队组织奖，奖励500元和奖牌一面。能有效组织各车间参赛，比赛场次布置合理，打分公平、公正，有效激励车间团队参与技能比赛的积极性的评委团队均有机会获得评委团队组织奖，奖励500元和奖牌一面。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;现诚邀集团内生产车间员工积极报名参加此次的劳动竞赛。车间的兄弟姐妹，你们还等什么？赶紧报名吧！</span></p><p><br/></p>','','生产车间劳动竞赛来啦','/html/weixin/news-message/1401158422562.html',NULL,NULL,NULL,8),(8,NULL,NULL,'<p><img src=\"/template/static/uploads/image/20140527/1401159397148001989.png\"/></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;除了</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/208071.html\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">帮Amazon送货</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">以外，无人机还有什么用途呢？在互联网和大数据时代，“数据采集”其实也可以交给无人机去完成，尤其是位于空中的、一般人无法采集的数据。</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"https://www.skycatch.com/\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">无人机公司Skycatch</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">就是做这个的，他们近期完成了新一轮 1320 万美元的融资，资方为 Avalon Venture。</span></p><p><img src=\"/template/static/uploads/image/20140527/1401159353844099465.png\" alt=\"\" style=\"box-sizing: border-box; margin: 0px auto; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: middle; background-color: transparent; display: block; max-width: 100%; height: auto;\"/></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">Skycatch 可以在高空中采集高清的图像和视频信息。用户只要到 Skycatch 的软件平台上指定自己需要采集的数据，Skycatch 就可以自主规划如何完成任务并将数据传回给用户，这些过程全部由 Skycatch 机器本身自主操控，包括起飞、降落到切换电池等环节都不需要人力参与进来。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">所以什么人会需要 Skycatch 呢？它们列出了四个应用场景——建造业、矿业、太阳能行业以及农业。比如对于建造业来说，Skycatch 可以捕获俯视航拍图，让用户很快地了解到工程的进展，而农业上航拍的图片数据则可用于分析作物的生长情况。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">能获得新一轮融资，Skycatch 的幕后团队背景似乎也有加分，这家公司的创始人都是科技领域里待了十几二十年的老兵，出身 Intel、Google 还有 Facebook，对这个团队感兴趣的人可以</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/new?1401153756#\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">点击这里</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">看看他们的详细信息。</span></p><p><br/></p>','除了帮Amazon送货以外，无人机还有什么用途呢？在互联网和大数据时代，“数据采集”其实也可以交给无人机去完成，尤其是位于空中的、一般人无法采集的数据。','空中的数据采集平台：无人机公司Skycatch完成1320万美元融资','/html/weixin/news-message/1401159434138.html','/static/uploads/news-message/big/1401159397148001989.png','/static/uploads/news-message/small/64x64图标.jpg',NULL,4),(9,NULL,NULL,'<p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\"><img src=\"/template/static/uploads/image/20140527/1401159507075042249.png\"/></span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;从手机，手表到汽车，冰箱，Google 的 Andorid 智能设备正在从各个方面的进入我们的日常生活，如果有天 Google 说，“这些设备上都要有广告！” 你能够接受么？</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;在最近公布出来的 Google 去年 12 月写给美国证券和交易委员会（SEC）的一封信中，Google 说：未来 Google 会在更多的地方展示广告，比如“冰箱，汽车仪表盘，恒温器，眼镜，手表”等。事实上，除了冰箱，目前 Google 在其他四个领域都已经有了公开布局。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;Google 在今年一月与奥迪，通用，本田，现代等汽车制造商</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/208902.html\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">组成</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">了开放汽车联盟（Open Automotive Alliance），从而将 Android 系统装进汽车的仪表盘里。Google 在今年以 32 亿美元</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/209633.html\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">收购</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">的恒温器 Nest 亦有可能成为广告投放点。而已经在全美公开</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/211947.html\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">售卖</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">的 Google Glass 和为可穿戴设备打造的</span><a target=\"_blank\" data-no-turbolink=\"true\" href=\"http://www.36kr.com/p/210490.html\" style=\"font-family: 宋体, SimSun; text-decoration: underline; font-size: 18px;\"><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">Andorid wear</span></a><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">也为基于虚拟现实的广告投放和在可穿戴设备上的广告做好了准备。是的，如果有一天这个世界的每个角落都是 Google 的广告我也丝毫不会感到惊讶。</span></p><p><span style=\"font-family: 宋体, SimSun; font-size: 18px;\">&nbsp;&nbsp;&nbsp;&nbsp;当然，Google 在信中提到这个论点所想要证明的是移动设备的广告业务发展十分迅速，用户会逐渐在更多的设备上看到 Google 的广告，因而不希望公开 Google 在移动设备上的收入。因此，这更像是 Google 指出的的一个事实，而非 Google 在声明未来的战略布局。</span></p><p><br/></p>','从手机，手表到汽车，冰箱，Google 的 Andorid 智能设备正在从各个方面的进入我们的日常生活，如果有天 Google 说，“这些设备上都要有广告！” 你能够接受么？','Google说，“要有广告”，于是未来所有智能设备上都将有广告','/html/weixin/news-message/1401159557377.html','/static/uploads/news-message/big/1401159507075042249.png','/static/uploads/news-message/small/1401157847466019276.png',NULL,4);
/*!40000 ALTER TABLE `tb_news_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_project`
--

DROP TABLE IF EXISTS `tb_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finish_task` int(11) DEFAULT NULL,
  `is_place_on_file` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent` double DEFAULT NULL,
  `total_task` int(11) DEFAULT NULL,
  `director` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_muw3a8w31hw00a06fhp91xy52` (`director`) USING BTREE,
  CONSTRAINT `tb_project_ibfk_1` FOREIGN KEY (`director`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_project`
--

LOCK TABLES `tb_project` WRITE;
/*!40000 ALTER TABLE `tb_project` DISABLE KEYS */;
INSERT INTO `tb_project` VALUES (1,'2014-04-27 16:29:10','2014-04-27 16:29:10','自己搭建一个符合自己使用习惯的项目管理系统。\r\n1、第一步先完成项目管理\r\n2、第二步是项目成员管理\r\n3、第三步是任务列表',0,0,'项目管理系统',0,0,1),(2,'2014-04-27 16:33:41','2014-04-27 16:33:41','学习使用\r\n目标是：请假流程',0,0,'工作流引擎',0,0,1),(3,'2014-04-27 16:36:12','2014-04-27 16:36:12','学习Spring的相关内容',0,0,'加强学习-Spring的正式使用',0,0,1),(4,'2014-04-27 16:36:39','2014-04-27 16:36:39','培养自己的工作习惯',0,0,'习惯养成',0,0,1);
/*!40000 ALTER TABLE `tb_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `default_role` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES (1,NULL,NULL,'admin',0,'系统管理员',0),(2,NULL,NULL,'user',1,'员工',1);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_sidebar`
--

DROP TABLE IF EXISTS `tb_role_sidebar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_sidebar` (
  `role_id` bigint(20) NOT NULL,
  `sidebar_id` bigint(20) NOT NULL,
  KEY `FK_59rp0u2aph88kd4045y00mf1p` (`sidebar_id`) USING BTREE,
  KEY `FK_3eah9odem243gcd52tuj3q292` (`role_id`) USING BTREE,
  CONSTRAINT `tb_role_sidebar_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`),
  CONSTRAINT `tb_role_sidebar_ibfk_2` FOREIGN KEY (`sidebar_id`) REFERENCES `tb_sidebar` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_sidebar`
--

LOCK TABLES `tb_role_sidebar` WRITE;
/*!40000 ALTER TABLE `tb_role_sidebar` DISABLE KEYS */;
INSERT INTO `tb_role_sidebar` VALUES (2,1),(2,2),(2,5),(2,3),(1,1),(1,2),(1,4),(1,5),(1,3);
/*!40000 ALTER TABLE `tb_role_sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sidebar`
--

DROP TABLE IF EXISTS `tb_sidebar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sidebar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pre_sidebar_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e603qcttght9illi2t1yvkeer` (`pre_sidebar_id`) USING BTREE,
  CONSTRAINT `tb_sidebar_ibfk_1` FOREIGN KEY (`pre_sidebar_id`) REFERENCES `tb_sidebar` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sidebar`
--

LOCK TABLES `tb_sidebar` WRITE;
/*!40000 ALTER TABLE `tb_sidebar` DISABLE KEYS */;
INSERT INTO `tb_sidebar` VALUES (1,NULL,NULL,'workbench','/workbench','我的工作',NULL),(2,NULL,NULL,'struct-manager','','架构管理',NULL),(3,NULL,NULL,'projects-manager','','项目管理',NULL),(4,NULL,NULL,'users-list','/user','用户管理',2),(5,NULL,NULL,'departments-list','/department','部门信息',2);
/*!40000 ALTER TABLE `tb_sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_task`
--

DROP TABLE IF EXISTS `tb_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_elk6mlelf430j9ip1dka77avv` (`user_id`) USING BTREE,
  CONSTRAINT `tb_task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_task`
--

LOCK TABLES `tb_task` WRITE;
/*!40000 ALTER TABLE `tb_task` DISABLE KEYS */;
INSERT INTO `tb_task` VALUES (1,'http://www.playframework.org/','Study PlayFramework 2.0',2,NULL,NULL),(2,'http://www.grails.org/','Study Grails 2.0',2,NULL,NULL),(3,'http://www.springfuse.com/','Try SpringFuse',2,NULL,NULL),(4,'http://www.springsource.org/spring-roo','Try Spring Roo',2,NULL,NULL),(5,'As soon as posibble.','Release SpringSide 4.0',2,NULL,NULL);
/*!40000 ALTER TABLE `tb_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'admin@163.com','admin','梁仲荣','691b14d79bf0fa2215f155235df5e670b64394cc','2012-06-04 01:00:00','admin','7efbd59d9741d34f','2014-04-27 16:45:46','2014-04-27 16:45:49','libra.jpg'),(2,'user@163.com','user','成员一','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','2012-06-04 02:00:00','user','6d65d24122c30500','2014-04-27 16:45:52','2014-04-27 16:45:54','avatar1.jpg'),(3,'249603229@qq.com','krinson','ling','ce84f8cf387bfd4aad7f305b985bcd2f52211cf6','2014-05-28 17:00:57',NULL,'e18fd7673d41ec55','2014-05-28 17:00:57','2014-05-28 17:00:57',NULL);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_role`
--

DROP TABLE IF EXISTS `tb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK_1txpcisco2l99tl5qqshr6ptp` (`role_id`) USING BTREE,
  KEY `FK_qrc2efy4dx7j5okwcg0hit512` (`user_id`) USING BTREE,
  CONSTRAINT `tb_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`),
  CONSTRAINT `tb_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_role`
--

LOCK TABLES `tb_user_role` WRITE;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
INSERT INTO `tb_user_role` VALUES (2,2),(1,1);
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_weixin_auth_log`
--

DROP TABLE IF EXISTS `tb_weixin_auth_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_weixin_auth_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `expire_seconds` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `scene_id` bigint(20) DEFAULT NULL,
  `ticket` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_weixin_auth_log`
--

LOCK TABLES `tb_weixin_auth_log` WRITE;
/*!40000 ALTER TABLE `tb_weixin_auth_log` DISABLE KEYS */;
INSERT INTO `tb_weixin_auth_log` VALUES (1,'2014-05-27 18:00:50',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401184849975,'gQGC7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2ZFd0JoZnZsTXZWTHJOZm9BbUlvAAIET2KEUwMECAcAAA=='),(2,'2014-05-27 18:20:27',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401186026740,'gQEI8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2kwd0RPOERsTGZWVTNpQkxBR0lvAAIE52aEUwMECAcAAA=='),(3,'2014-05-28 07:53:00',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401234780272,'gQFQ8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzVreHY3VmpsV3ZVai1VMkdiR0lvAAIEWCWFUwMECAcAAA=='),(4,'2014-05-28 08:37:14',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401237434115,'gQEP8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1FVd3FSMWZsSy1WUy1fb1lLV0lvAAIEti_FUwMECAcAAA=='),(5,'2014-05-28 09:05:05',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401239104350,'gQEM8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3RFd3dzYVhsTXZWTERoLXRNMklvAAIEPDaFUwMECAcAAA=='),(6,'2014-05-28 09:12:18',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',1401239537975,'gQFL8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzNFdzBXc3ZsRGZWMDQzYzlOMklvAAIE7jeFUwMECAcAAA=='),(7,'2014-05-28 09:24:30',NULL,'152217','认证#Libra#152217',1800,'Libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU',70037,'gQHD8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0wweHJyUUxsWC1VbUxvVEhhR0lvAAIEyjqFUwMECAcAAA==');
/*!40000 ALTER TABLE `tb_weixin_auth_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_weixin_menu`
--

DROP TABLE IF EXISTS `tb_weixin_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_weixin_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` text,
  `is_using` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_weixin_menu`
--

LOCK TABLES `tb_weixin_menu` WRITE;
/*!40000 ALTER TABLE `tb_weixin_menu` DISABLE KEYS */;
INSERT INTO `tb_weixin_menu` VALUES (1,NULL,NULL,'{\r\n     \"button\":[\r\n     {	\r\n          \"name\":\"关于国光\",\r\n          \"sub_button\":[\r\n          {\r\n               \"type\":\"view\",\r\n               \"name\":\"微官网\",\r\n               \"url\":\"http://ggecwx.jsp.jspee.cn/template/static/js/slideby/index.html\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"公司简介\",\r\n               \"key\":\"COMPANY_PROFILE\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"主要产品\",\r\n               \"key\":\"MAJOR_PRODUCT\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"新闻资讯\",\r\n               \"key\":\"NEWS_INFORMATION\"\r\n            },\r\n                    {\r\n               \"type\":\"view\",\r\n               \"name\":\"邮件页面调试\",\r\n               \"url\":\"http://10.0.1.34:8080/template/wxmail/1\"\r\n            }\r\n		  ]\r\n      },\r\n      {\r\n           \"name\":\"个人中心\",\r\n			\"sub_button\":[\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"个人信息\",\r\n               \"key\":\"PERSONAL_INFORMATION\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"宿舍水电\",\r\n               \"key\":\"DEVELOPING\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"员工认证\",\r\n               \"key\":\"AUTHENTIFICATION_OF_USER\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"通讯录\",\r\n               \"key\":\"DEVELOPING\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"课程报名\",\r\n               \"key\":\"DEVELOPING\"\r\n            }\r\n		  ]\r\n      },\r\n      {\r\n           \"name\":\"更多资讯\",\r\n           \"sub_button\":[\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"国光大事记\",\r\n               \"key\":\"DEVELOPING\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"国光电子报\",\r\n               \"key\":\"EPAPER\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"技术专刊\",\r\n               \"key\":\"TECHNICAL_MONOGRAPH\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"饭堂美食\",\r\n               \"key\":\"DEVELOPING\"\r\n            },\r\n			{\r\n               \"type\":\"click\",\r\n               \"name\":\"班车信息\",\r\n               \"key\":\"DEVELOPING\"\r\n            }\r\n		  ]\r\n       }]\r\n }',1,'自定义菜单一');
/*!40000 ALTER TABLE `tb_weixin_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_weixin_user`
--

DROP TABLE IF EXISTS `tb_weixin_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_weixin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `errcode` int(11) DEFAULT NULL,
  `errmsg` varchar(255) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `subscribe` int(11) DEFAULT NULL,
  `subscribe_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_weixin_user`
--

LOCK TABLES `tb_weixin_user` WRITE;
/*!40000 ALTER TABLE `tb_weixin_user` DISABLE KEYS */;
INSERT INTO `tb_weixin_user` VALUES (5,NULL,'2014-05-30 11:51:11','广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/Q3auHgzwzM4hicarmC7WoDlZ87V6nrr1kj3ISRfCdiaKic3iaMICeiaMpqcfDwWl5AkacTDzDcqOJITRl9ne3RSDlJw/0','zh_CN','羽libra','o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU','广东',1,1,'1401246734'),(6,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/UUdBhxwvoUibNm78MhN2WZ3dr5EXFUfbbhMdFe9BKs0rEkygNNj9O5OA0WopkicDG5k4iak155sW5YTk70xiaZsAAUShTvHQuJNb/0','zh_CN','洁威','o7Chyt7G6o7gl-P2GRSitIL3Nbz0','广东',1,1,'1401249913'),(7,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/DBCABlsXAyKtdia9dFYAn2IicXGqibKQoC7CpGT5Ffl3r0icTDQHmomqGEC88SCjCBVcFpmz4PH4Pd7Tde6TfkPag3108RRsmEQX/0','zh_CN','Zero','o7ChytzGzIdds0MY-sB7KPQKr18E','广东',1,1,'1401249986'),(8,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/UUdBhxwvoUicdibC2KT5o6icicfh61xF7YVtXO6Viarxlge5ic9doArKb0mbNTpRsPyicKpUTKEaQiakjh9Zu47pf4UNMDzU5aoaQEj4/0','zh_CN','曾路','o7Chyt_ZDfef9YZ8P4uozDktEdoY','广东',1,1,'1401256868'),(9,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/UUdBhxwvoUibNm78MhN2WZ6mRFnZgyjp8Xper5Qfe7zx6CfIk1BZlHua1tQMf2GaiaUkOKdabL6RkEMLibk48m4kgkiapsBrNMsY/0','zh_CN','Yy','o7Chyt0Og_JjwoRqiYEMcOvRtwVA','广东',1,1,'1401263790'),(10,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/fx8hLVwDiaBzIAzY5UmVZmy4j4zaWxn8SBoQwia5bek0JFMuXiauRH7JUQLtANqdzPF0sEGXpoXsNNlDXP7M0nJ4OfeEmXTOQSz/0','zh_CN','老彭','o7Chyt1UduuofZLQLdUyC5QI43mM','广东',1,1,'1401265223'),(11,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/90Mf9xJAmSnbZibyUHvOusO6qmYicnrTpyBdtC6IeHGPpgVnZQUUojoWm13Y2VXyryB8KwQRbMsXWpMjkfRdSNkictialJvFhkNm/0','zh_CN','cccan','o7Chytw74xe1IOeDkaSX34eJdi_M','广东',1,1,'1401265677'),(12,NULL,NULL,'','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/fx8hLVwDiaBzIAzY5UmVZmibMChIFiaTHRdFYraOGicZCN9mtCQ8B0icYCzubrKcfmDRGhjKflK4H3KwTPRaXSa4UySSVph67f0zM/0','zh_CN','LA','o7Chyt1FL53wrKYmRj8QCFY8M7ao','广东',1,1,'1401265844'),(13,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/TA57WHRibQ0w8oNyvtib0V4DlrH11DmmJmgaPwZvZHeAV6vU7gETTHaZibxPUa2Xgph5KrI2HFPKT6HxwJsiabMIU2SPP6icphSjia/0','zh_CN','-','o7ChytxV0eVTd8WOKc7tBtFd-7FA','广东',1,1,'1401267527'),(14,NULL,NULL,'广州','中国',NULL,NULL,'http://wx.qlogo.cn/mmopen/X6Ucic5kYIBNszib38qyxLP5sU7IXricNz1YCRHKFteUDNWibib78BQAIj1DTg1icmnO5D0jNbIjlxuXibOzeHUic3BdqQ/0','zh_CN','婷婷','o7Chytyli6DTN-9cfz0B4yOvnWdY','广东',2,1,'1401439281');
/*!40000 ALTER TABLE `tb_weixin_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-03  0:38:00
