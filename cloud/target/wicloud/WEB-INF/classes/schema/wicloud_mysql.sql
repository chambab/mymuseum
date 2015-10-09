CREATE DATABASE wicloud;
GRANT ALL PRIVILEGES ON wicloud.* TO 'wicloud'@'localhost'    IDENTIFIED BY 'dlatl00';
GRANT ALL PRIVILEGES ON wicloud.* TO 'wicloud'@'%'    IDENTIFIED BY 'dlatl00';

-- User's own images, which cab be shared with others
CREATE TABLE TB_IMAGES
(
	IMG_ID				  MEDIUMINT   NOT NULL AUTO_INCREMENT,  
    USER_ID               VARCHAR(100)   NOT NULL,  
	IMAGE_UUID            VARCHAR(200)  NOT NULL ,
	PUBLIC                VARCHAR(10)  NOT NULL ,
	OS_TYPE               VARCHAR(100)  NULL,
	BLOG_NAME             VARCHAR(50)  NULL,
	DESCRIPTION           TEXT NULL,
	USE_CNT               INT(10) ZEROFILL,
	KEEP_CNT              INT(10) ZEROFILL,
	REG_DATE			  DATETIME,
	PRIMARY KEY(IMG_ID)
);