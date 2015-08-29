use `hust-bill`;

--
DROP TABLE IF EXISTS `e_building_task`;
CREATE TABLE `e_building_task` (
  `id`              bigint        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`            nvarchar(50)  NOT NULL UNIQUE,
  `starTime`        datetime      NOT NULL,
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
  `timestmp`      datetime      NOT NULL,
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
  `starTime`        datetime      NOT NULL,
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
  `floor`           int(1)            NOT NULL,
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
  `floor`           int(1)            NOT NULL,
  `roomNO`          int(2)            NOT NULL,
  `operate`         int(1)            NOT NULL,
  `timestmp`      datetime            NOT NULL,
  FOREIGN KEY `fk_e_room_operate_taskid` (`taskId`) REFERENCES `e_room_task`(`id`),
  FOREIGN KEY `fk_e_room_operate_resultid` (`resultId`) REFERENCES `e_room_task_result`(`id`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `e_room`;
CREATE TABLE `e_room` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `floor`         int(1)        NOT NULL,
  `roomNO`        int(2)        NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`),
  FOREIGN KEY `fk_e_room_temp` (`buildingName`) REFERENCES `e_building`(`name`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;



--
DROP TABLE IF EXISTS `e_record_remain`;
CREATE TABLE `e_record_remain` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `dateTime`      datetime      NOT NULL,
  `remain`        DECIMAL(8,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `dateTime`),
  FOREIGN KEY `fk_e_record_remain` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_remain` ADD INDEX `ix_e_record_remain_building` (`buildingName`);
ALTER TABLE `e_record_remain` ADD INDEX `ix_e_record_remain_room` (`roomName`);

--
DROP TABLE IF EXISTS `e_record_charge`;
CREATE TABLE `e_record_charge` (
  `buildingName`  nvarchar(50)  NOT NULL,
  `roomName`      varchar(5)    NOT NULL,
  `dateTime`      datetime      NOT NULL,    
  `power`         DECIMAL(7,2)  NOT NULL,
  `money`         DECIMAL(6,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `dateTime`),
  FOREIGN KEY `fk_e_record_charge` (`buildingName`, `roomName`) REFERENCES `e_room`(`buildingName`, `roomName`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;
ALTER TABLE `e_record_charge` ADD INDEX `ix_e_record_charge_building` (`buildingName`);
ALTER TABLE `e_record_charge` ADD INDEX `ix_e_record_charge_room` (`roomName`);
