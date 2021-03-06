use `hust-bill`;

--
DROP TABLE IF EXISTS `e_building_task`;
CREATE TABLE `e_building_task` (
  `id`              bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`            nvarchar(50)  NOT NULL UNIQUE,
  `startTime`        datetime      NOT NULL,
  `endTime`         datetime,
  `resultCount`     int,
  `status`          int(1)        NOT NULL
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_building_task_result`;
CREATE TABLE `e_building_task_result` (
  `taskId`          bigint              NOT NULL,
  `id`              bigint              NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `areaName`        nvarchar(20)        NOT NULL,
  `buildingName`    nvarchar(50)        NOT NULL,
  `buildingFloor`   int(1)              NOT NULL,
  FOREIGN KEY `fk_e_building_task_result_id` (`taskId`) REFERENCES `e_building_task`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_building_operate`;
CREATE TABLE `e_building_operate` (
  `taskId`        bigint        NOT NULL,
  `taskName`      nvarchar(50)  NOT NULL,
  `resultId`      bigint        NOT NULL,
  `areaName`      nvarchar(20)  NOT NULL,
  `buildingName`  nvarchar(50)  NOT NULL,
  `buildingFloor` int(1)        NOT NULL,
  `operate`       int(1)        NOT NULL,
  `stamp`      datetime     NOT NULL,
  FOREIGN KEY `fk_e_building_operate_taskid` (`taskId`) REFERENCES `e_building_task`(`id`),
  FOREIGN KEY `fk_e_building_operate_resultid` (`resultId`) REFERENCES `e_building_task_result`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `e_building`;
CREATE TABLE `e_building` (
  `area`        nvarchar(20)  NOT NULL,
  `name`        nvarchar(50)  NOT NULL,
  `floor`       int(1)        NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;


--
DROP TABLE IF EXISTS `e_room_task`;
CREATE TABLE `e_room_task` (
  `id`              bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`            nvarchar(50)  NOT NULL UNIQUE,
  `buildingName`    nvarchar(50)  NOT NULL,
  `startTime`       datetime      NOT NULL,
  `endTime`         datetime,
  `resultCount`     int,
  `status`          int(1)        NOT NULL
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_room_task_result`;
CREATE TABLE `e_room_task_result` (
  `taskId`          bigint            NOT NULL,
  `id`              bigint            NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `buildingName`    nvarchar(50)      NOT NULL,
  `roomName`        varchar(5)        NOT NULL,
  `roomFloor`       int(1)            NOT NULL,
  `roomNO`          int(2)            NOT NULL,
  FOREIGN KEY `fk_e_room_task_result_id` (`taskId`) REFERENCES `e_room_task`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_room_operate`;
CREATE TABLE `e_room_operate` (
  `taskId`          bigint            NOT NULL,
  `taskName`        nvarchar(50)      NOT NULL,
  `buildingName`    nvarchar(50)      NOT NULL,
  `resultId`        bigint            NOT NULL,
  `roomName`        varchar(5)        NOT NULL,
  `roomFloor`       int(1)            NOT NULL,
  `roomNO`          int(2)            NOT NULL,
  `operate`         int(1)            NOT NULL,
  `stamp`           datetime          NOT NULL,
  FOREIGN KEY `fk_e_room_operate_taskid` (`taskId`) REFERENCES `e_room_task`(`id`),
  FOREIGN KEY `fk_e_room_operate_resultid` (`resultId`) REFERENCES `e_room_task_result`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_room`;
CREATE TABLE `e_room` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `roomFloor`     int(1)        NOT NULL,
  `roomNO`        int(2)        NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`),
  FOREIGN KEY `fk_e_room_temp` (`buildingName`) REFERENCES `e_building`(`name`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;


--
DROP TABLE IF EXISTS `e_record_task`;
CREATE TABLE `e_record_task` (
  `id`              bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`            nvarchar(50)  NOT NULL UNIQUE,
  `startTime`       datetime      NOT NULL,
  `endTime`         datetime,
  `resultCount`     int,
  `status`          int(1)        NOT NULL
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_record_task_result`;
CREATE TABLE `e_record_task_result` (
  `taskId`          bigint              NOT NULL,
  `id`              bigint              NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `buildingName`    nvarchar(50)        NOT NULL,
  `remainCount`     int                 NOT NULL,
  `chargeCount`     int                 NOT NULL,
  `stamp`           datetime            NOT NULL,
  FOREIGN KEY `fk_e_record_task_result_id` (`taskId`) REFERENCES `e_record_task`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;


--
DROP TABLE IF EXISTS `e_record_remain`;
CREATE TABLE `e_record_remain` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `stamp`         datetime      NOT NULL,
  `remain`        DECIMAL(8,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `stamp`),
  FOREIGN KEY `fk_e_record_remain` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_remain` ADD INDEX `ix_e_record_remain_building` (`buildingName`);
ALTER TABLE `e_record_remain` ADD INDEX `ix_e_record_remain_room` (`roomName`);

--
DROP TABLE IF EXISTS `e_record_remain_last`;
CREATE TABLE `e_record_remain_last` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `stamp`         datetime,
  `remain`        DECIMAL(8,2),
  PRIMARY KEY (`buildingName`, `roomName`),
  FOREIGN KEY `fk_e_record_remain_last` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_remain_last` ADD INDEX `ix_e_record_remain_last_building` (`buildingName`);
ALTER TABLE `e_record_remain_last` ADD INDEX `ix_e_record_remain_last_room` (`roomName`);


--
DROP TABLE IF EXISTS `e_record_charge`;
CREATE TABLE `e_record_charge` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `stamp`         datetime      NOT NULL,    
  `power`         DECIMAL(7,2)  NOT NULL,
  `money`         DECIMAL(6,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `stamp`),
  FOREIGN KEY `fk_e_record_charge` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_charge` ADD INDEX `ix_e_record_charge_building` (`buildingName`);
ALTER TABLE `e_record_charge` ADD INDEX `ix_e_record_charge_room` (`roomName`);


--
DROP TABLE IF EXISTS `e_record_charge_last`;
CREATE TABLE `e_record_charge_last` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `stamp`         datetime,    
  `power`         DECIMAL(7,2),
  `money`         DECIMAL(6,2),
  PRIMARY KEY (`buildingName`, `roomName`),
  FOREIGN KEY `fk_e_record_charge_last` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_charge_last` ADD INDEX `ix_e_record_charge_last_building` (`buildingName`);
ALTER TABLE `e_record_charge_last` ADD INDEX `ix_e_record_charge_last_room` (`roomName`);


--
DROP TABLE IF EXISTS `e_consume_task`;
CREATE TABLE `e_consume_task` (
  `id`              bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`            nvarchar(50)  NOT NULL UNIQUE,
  `startTime`       datetime      NOT NULL,
  `endTime`         datetime,
  `resultCount`     int,
  `status`          int(1)        NOT NULL
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_consume_task_result`;
CREATE TABLE `e_consume_task_result` (
  `taskId`          bigint              NOT NULL,
  `id`              bigint              NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `buildingName`    nvarchar(50)        NOT NULL,
  `consumeCount`    int                 NOT NULL,
  `stamp`           datetime            NOT NULL,
  FOREIGN KEY `fk_e_consume_task_result_id` (`taskId`) REFERENCES `e_consume_task`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

--
DROP TABLE IF EXISTS `e_consume_last`;
CREATE TABLE `e_consume_last` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `remain`        DECIMAL(8,2)  ,
  `stamp`         datetime      ,
  PRIMARY KEY (`buildingName`, `roomName`),
  FOREIGN KEY `fk_e_consume_last` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_consume_last` ADD INDEX `ix_e_consume_last_building` (`buildingName`);
ALTER TABLE `e_consume_last` ADD INDEX `ix_e_consume_last_room` (`roomName`);

--
DROP TABLE IF EXISTS `e_consume`;
CREATE TABLE `e_consume` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `stamp`         date          NOT NULL,
  `consume`       DECIMAL(6,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `stamp`),
  FOREIGN KEY `fk_e_consume` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_consume` ADD INDEX `ix_e_consume_building` (`buildingName`);
ALTER TABLE `e_consume` ADD INDEX `ix_e_consume_room` (`roomName`);
