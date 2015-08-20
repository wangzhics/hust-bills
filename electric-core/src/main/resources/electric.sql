--
DROP TABLE IF EXISTS `e_building`;
CREATE TABLE `e_building` (  
  `area`    nvarchar(20)  NOT NULL,  
  `name`    nvarchar(50)  NOT NULL,  
  `floor`   int(1)        NOT NULL,    
  PRIMARY KEY (`name`)  
) ENGINE=Innodb, DEFAULT CHARSET=utf8;

--
DROP TABLE IF EXISTS `e_record_remain_temp`;
CREATE TABLE `e_record_remain_temp` (  
  `buildingName`  nvarchar(50)  NOT NULL,  
  `roomName`      varchar(5)    NOT NULL,  
  `dateTime`      datetime      NOT NULL,    
  `remain`        DECIMAL(5,1)  NOT NULL, 
  PRIMARY KEY (`buildingName`, `roomName`, `dateTime`)  
) ENGINE=Innodb, DEFAULT CHARSET=utf8; 

--
DROP TABLE IF EXISTS `e_record_charge_temp`;
CREATE TABLE `e_record_charge_temp` (  
  `buildingName`  nvarchar(50)  NOT NULL,  
  `roomName`      varchar(5)    NOT NULL,  
  `dateTime`      datetime      NOT NULL,    
  `power`         DECIMAL(5,1)  NOT NULL, 
  `money`         DECIMAL(4,2)  NOT NULL,
  PRIMARY KEY (`buildingName`, `roomName`, `dateTime`)  
) ENGINE=Innodb, DEFAULT CHARSET=utf8; 

