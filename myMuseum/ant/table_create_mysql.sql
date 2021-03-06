

create database myMuseum;
GRANT ALL PRIVILEGES ON mymuseum.* TO 'mymuseum'@'localhost' identified by 'msmuseum00';
GRANT ALL PRIVILEGES ON mymuseum.* TO 'mymuseum'@'%' identified by 'msmuseum00';


CREATE TABLE CDCTCINFO
(
	INFO_ID               CHAR(18)  NOT NULL ,
	INFO_NM               CHAR(18)  NULL
);



ALTER TABLE CDCTCINFO
	ADD CONSTRAINT  CDCTCINFO_PK PRIMARY KEY (INFO_ID);



CREATE TABLE CDCTCREGN
(
	REGN_ID               VARCHAR(15)  NOT NULL ,
	REGN_NM               VARCHAR(30)  NULL
);



ALTER TABLE CDCTCREGN
	ADD CONSTRAINT  CDCTCREGN_PK PRIMARY KEY (REGN_ID);



CREATE TABLE CDCTCREGNINFO
(
	REGN_INF_ID           VARCHAR(15)  NOT NULL ,
	LATITUDE              VARCHAR(30)  NULL ,
	LONGITUDE             VARCHAR(30)  NULL ,
	COMPASS               VARCHAR(10)  NULL
);

# cannot
CREATE SEQUENCE CDCTCREGNINFO_SEQ_01
								INCREMENT BY 1
								START WITH 1
								MAXVALUE 9999999
								NOCYCLE
								NOCACHE;

ALTER TABLE CDCTCREGNINFO
	ADD CONSTRAINT  CDCTCREGNINFO_PK PRIMARY KEY (REGN_INF_ID);



CREATE TABLE CDCTCTAG
(
	TAG_ID                MEDIUMINT   NOT NULL AUTO_INCREMENT ,
	MSG_ID                VARCHAR(15)  NOT NULL,
	TAG_NM                VARCHAR(30)  NULL,
	PRIMARY KEY(TAG_ID)
);
								
CREATE INDEX CDCTCTAG_IX01 ON CDCTCTAG(MSG_ID);								
CREATE INDEX CDCTCTAG_IX02 ON CDCTCTAG(TAG_NM);

CREATE TABLE UWFDTB0101
(
	MST_USER_ID           VARCHAR(50)  NOT NULL ,
	SUB_USER_ID           VARCHAR(50)  NOT NULL ,
	REG_DT                VARCHAR(14)  NULL
);



ALTER TABLE UWFDTB0101
	ADD CONSTRAINT  UWFDTB0101_PK PRIMARY KEY (MST_USER_ID,SUB_USER_ID);
	
CREATE INDEX UWFDTB0101_IX01 ON UWFDTB0101(SUB_USER_ID);



CREATE TABLE UWFDTB0201
(
	USER_ID               VARCHAR(50)  NOT NULL ,
	USER_PW               VARCHAR(200)  NOT NULL ,
	USER_NM               VARCHAR(100)  NULL ,
	USER_SR_CODE          VARCHAR(3)  NULL ,
	CN                    VARCHAR(1000)  NULL ,
	WEB_URL               VARCHAR(500)  NULL ,
	OPEN_YN               VARCHAR(1)  NULL ,
	REG_DT                VARCHAR(14)  NULL ,
	REG_INFO              VARCHAR(30)  NULL,
	USER_IMG              VARCHAR(200)  NULL,
	REGN_INF_ID           VARCHAR(15)  NULL
);



ALTER TABLE UWFDTB0201
	ADD CONSTRAINT  UWFDTB0201_PK PRIMARY KEY (USER_ID);

CREATE INDEX UWFDTB0201_IX01 ON UWFDTB0201 (USER_SR_CODE, OPEN_YN);


CREATE TABLE UWFDTB0301
(
	MSG_ID                VARCHAR(15)  NOT NULL ,
	USER_ID               VARCHAR(15)  NOT NULL ,
	REGN_INF_ID           VARCHAR(15)  NOT NULL ,
	MSG_CN                VARCHAR(4000)  NULL ,
	REG_DT                VARCHAR(14)  NULL ,
	INFO_ID               VARCHAR(15)  NULL,
	WRITER_ID             VARCHAR(15) NULL,
	VIEW_CNT              VARCHAR(5)
);



ALTER TABLE UWFDTB0301
	ADD CONSTRAINT  UWFDTB0301_PK PRIMARY KEY (MSG_ID,USER_ID);
	
CREATE INDEX UWFDTB0301_IX01 ON UWFDTB0301 (USER_ID ASC);
CREATE INDEX UWFDTB0301_IX02 ON UWFDTB0301 (MSG_ID DESC);
CREATE INDEX UWFDTB0301_IX03 ON UWFDTB0301 (WRITER_ID);



CREATE SEQUENCE UWFDTB0301_SEQ_01
								INCREMENT BY 1
								START WITH 1
								MAXVALUE 9999999
								NOCYCLE
								NOCACHE;

-- 
-- 메시지의 리플								
CREATE TABLE UWFDTB0311
(
	RPL_ID          VARCHAR(15) NOT NULL,
	MSG_ID          VARCHAR(15) NOT NULL,
	USER_ID         VARCHAR(50) NOT NULL,
	RPL_MSG_CN      VARCHAR(1000) NULL,
	REG_DT          VARCHAR(14) NULL	
)	
;
			
ALTER TABLE UWFDTB0311
	ADD CONSTRAINT  UWFDTB0311_PK PRIMARY KEY (RPL_ID);
					
CREATE INDEX UWFDTB0311_IX01 ON UWFDTB0311 (MSG_ID ASC);

CREATE SEQUENCE UWFDTB0311_SEQ_01
								INCREMENT BY 1
								START WITH 1
								MAXVALUE 9999999
								NOCYCLE
								NOCACHE;
								
					

CREATE TABLE UWFDTB0401
(
	IMG_ID                VARCHAR(15)  NOT NULL ,
	USER_ID               VARCHAR(50)  NULL ,
	MSG_ID                VARCHAR(15)  NULL,
	IMG_NM                VARCHAR(100)  NULL ,
	IMG_CN                VARCHAR(1000)  NULL ,
	IMG_URL               VARCHAR(1000)  NULL ,
	REG_DT                VARCHAR(14)  NULL
);



ALTER TABLE UWFDTB0401
	ADD CONSTRAINT  UWFDTB0401_PK PRIMARY KEY (IMG_ID);

CREATE SEQUENCE UWFDTB0401_SEQ_01
								INCREMENT BY 1
								START WITH 1
								MAXVALUE 9999999
								NOCYCLE
								NOCACHE;


CREATE INDEX UWFDTB0401_IX01 ON UWFDTB0401(MSG_ID);


CREATE TABLE UWFDTB0405
(
	IMG_ID                VARCHAR(15)  NOT NULL ,
	USER_ID               VARCHAR(50)  NOT NULL ,
	MSG_ID                VARCHAR(15)  NOT NULL ,
	REGN_INF_ID           VARCHAR(15)  NOT NULL ,
	RESOLUTION            VARCHAR(10)  NULL ,
	BRIGHTNESS            VARCHAR(10)  NULL ,
	DARKNESS              VARCHAR(10)  NULL
);



ALTER TABLE UWFDTB0405
	ADD CONSTRAINT  UWFDTB0405_PK PRIMARY KEY (IMG_ID,USER_ID,MSG_ID);



CREATE TABLE UWFDTB0501
(
    TAG_ID                MEDIUMINT   NOT NULL AUTO_INCREMENT,  
	MSG_ID                VARCHAR(15)  NOT NULL ,
	TAG_NM                VARCHAR(50)  NOT NULL ,
	REG_DT                VARCHAR(14)  NULL,
	PRIMARY KEY(TAG_ID)
);
	 

CREATE INDEX UWFDTB0501_IX01 ON UWFDTB0501(TAG_NM);							

CREATE TABLE UWFDTB0601
(
	MSG_ID                VARCHAR(15)  NOT NULL ,
	USER_ID               VARCHAR(50)  NOT NULL ,
	REGN_ID               VARCHAR(15)  NOT NULL ,
	REG_DT                VARCHAR(14)  NULL
);



ALTER TABLE UWFDTB0601
	ADD CONSTRAINT  UWFDTB0601_PK PRIMARY KEY (MSG_ID,USER_ID,REGN_ID);

