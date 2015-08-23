use `hust-bill`;
--
DROP TABLE IF EXISTS `e_building`;
CREATE TABLE `e_building` (
  `area`        nvarchar(20)  NOT NULL,
  `name`        nvarchar(50)  NOT NULL,
  `floor`       int(1)        NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

--
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
