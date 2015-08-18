DROP TABLE IF EXISTS `electric_building`;
CREATE TABLE `electric_building` (  
  `area` nvarchar(20) NOT NULL,  
  `name` nvarchar(50) NOT NULL,  
  `floor` int(1)      NOT NULL,    
  PRIMARY KEY (`name`)  
) ENGINE=Innodb, DEFAULT CHARSET=utf8; 
