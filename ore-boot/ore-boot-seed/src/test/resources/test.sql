CREATE TABLE user_info (
  id int(11) NOT NULL,
  name varchar(255) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 16384
CHARACTER SET latin1
COLLATE latin1_swedish_ci;