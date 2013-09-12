drop table BackRepair;
create table BackRepair(
	ID INTEGER AUTO_INCREMENT primary key,
	billid int,
	backDate date,
	backNum long,
	getDate date,
	note varchar(1024)
);
drop table custom;
create table custom(
	id int AUTO_INCREMENT,
	name 		varchar(254) ,
	tel 		varchar(50),
	address		varchar(254),
	fax			varchar(50),
	contact 	varchar(50),
	email 	varchar(100),
	PRIMARY KEY(NAME,contact)
);
create table outcustom(
	id int AUTO_INCREMENT,
	name 		varchar(254) ,
	tel 		varchar(50),
	address		varchar(254),
	fax			varchar(50),
	contact 	varchar(50),
	email 	varchar(100),
	PRIMARY KEY(NAME)
);
drop table bill;
create table bill(
	id int IDENTITY,
	billdate 		date,
	billid 			varchar(50),
	item			varchar(50),
	picid 			varchar(254),
	requestPrice 	decimal(18,2),
	reportPrice 	decimal(18,2),
	imageUrl 		varchar(254),
	note 			varchar(254),
	custom 			varchar(254),
	outCustom	 	varchar(254),
	outComplete 	boolean,
	outPrice		decimal(18,2),
	itemComplete	boolean,
	billed			boolean,
	customMan 		varchar(50),
	outGetDate  	date,
	itemCompleteDate date,
	billedDate	 	date,	
	billNo 		 	varchar(50),	
	num 		 	long  ,
	requestDate date
);
alter table bill add column billgroup varchar(50);
drop table userman;
create table userman(
	id int AUTO_INCREMENT,
	name varchar(10),
	password varchar(10),
	level int,
	PRIMARY KEY(NAME)
)

create table operation(
	id int AUTO_INCREMENT,
	name varchar(50),
	unit varchar(10),
	out boolean,
	cost decimal(18,2),
	note varchar(100)

);

create table plan (
	id int AUTO_INCREMENT,
	bill int,
	completed boolean,
	produceDate date,
	sequenceNum int,
	num int	
);
drop table Operation;
create table Operation(id int AUTO_INCREMENT, Name varchar(50),Cost float,Num int,Note varchar(50),Out boolean );
drop table employee;
create table Employee(id int AUTO_INCREMENT, Name varchar(50),Operation int,Wage float,EmployeeType varchar(20) );
create table OperationPlan( id int AUTO_INCREMENT,Name varchar(50),MaterialNum int,MaterialCost float,Num int,Note varchar(50),Plan int,Out boolean,Cost float,UseTime float );
select * from operationwork
 alter table operationplan add column  FinishDate date;
 alter table operationplan add column  ProductNum int;
 alter table operationplan add column  Checker int;
 alter table operationplan add column  Employee int;
 alter table operationplan add column  GetNum int;
 alter table operationplan add column  workTime float;
 alter table operationplan add column  ScrapNum int;
 alter table operationplan add column  ScrapReason varchar(50);
 alter table operationplan add column  PrepareTime float;
 alter table operationplan add column  PrepareEmployee int;
 alter table operationplan add column  Preparecost float; 
 
 
 create table OperationOut( 
 id int AUTO_INCREMENT,
 Name varchar(50),
 MaterialNum int,
 MaterialCost float,
 Plan int );

alter table operationwork alter column preparecost decimal(18,2);
alter table employee alter column wage decimal(18,2);
alter table operationplan add column preparetime int;

alter table operationwork add column workCost decimal(18,2);

alter table operationwork add column planCost decimal(18,2);

update operationwork a set workcost=(select a.worktime*wage from employee b where b.id=a.employee);

update operationwork a set planCost=(select b.unitusetime*num*cost from operationplan b where b.id=a.operationplan);

