--<ScriptOptions statementTerminator=";"/>
drop table BackRepair;
create table BackRepair(
	ID INTEGER identity(1,1) primary key,
	billid int,
	backDate SmallDatetime,
	backNum bigint
);
drop table BILL;
CREATE TABLE BILL (
		ID INTEGER identity(1,1) primary key,
		BILLDATE SmallDatetime,
		BILLID VARCHAR(50),
		ITEM VARCHAR(50),
		PICID VARCHAR(254),
		REQUESTPRICE DECIMAL(18 , 2),
		REPORTPRICE DECIMAL(18 , 2),
		IMAGEURL VARCHAR(254),
		NOTE VARCHAR(254),
		CUSTOM VARCHAR(254),
		OUTCUSTOM VARCHAR(254),
		OUTCOMPLETE  bit,
		OUTPRICE DECIMAL(18 , 2),
		ITEMCOMPLETE  bit,
		BILLED  bit,
		CUSTOMMAN VARCHAR(50),
		OUTGETDATE SmallDatetime,
		ITEMCOMPLETEDATE SmallDatetime,
		BILLEDDATE SmallDatetime,
		BILLNO VARCHAR(50),
		NUM BIGINT,
		REQUESTDATE SmallDatetime,
		OUTNUM BIGINT DEFAULT 0,
		COLOR VARCHAR(10),
		WAREHOUSED bit default 0,
		OUTBILLNO VARCHAR(50),
		OUTBILLDATE SmallDatetime
	);

CREATE TABLE OUTCUSTOM (
		ID INTEGER identity(1,1) ,
		NAME VARCHAR(254) NOT NULL,
		TEL VARCHAR(50),
		ADDRESS VARCHAR(254),
		FAX VARCHAR(50),
		CONTACT VARCHAR(50),
		EMAIL VARCHAR(100),
		primary key(NAME,CONTACT)
	);

CREATE TABLE CUSTOM (
		ID INTEGER identity(1,1) ,
		NAME VARCHAR(254) NOT NULL,
		TEL VARCHAR(50),
		ADDRESS VARCHAR(254),
		FAX VARCHAR(50),
		CONTACT VARCHAR(50) NOT NULL,
		EMAIL VARCHAR(100),
		primary key(NAME,CONTACT)
	);

CREATE TABLE loginUser (
		ID INTEGER identity(1,1),
		NAME VARCHAR(10) NOT NULL,
		PASSWORD VARCHAR(10),
		LEVEL INTEGER,
		primary key(NAME)
	);

