/*
Data Store: AUT DS
Created: 2017-10-26T16:06:06.871Z
By: Administrator
Appian Version: 17.1.0.0
Target Database: MySQL 5.7.19-log
Database Driver: MySQL Connector Java mysql-connector-java-5.1.44 ( Revision: b3cda4f864902ffdde495b9df93937c3e20009be )
*/

-- DROP AND CREATE DDL
drop table if exists `auttestdata`;

create table `auttestdata` (
    `testid` integer not null auto_increment,
    `testtext` varchar(255),
    `testint` integer,
    `testdecimal` double precision,
    `testdatetime` datetime,
    primary key (`testid`)
) ENGINE=InnoDB;
