
------------------------------------------------
-- ±íµÄ DDL Óï¾ä "DATACENTER"."USER"
------------------------------------------------
 

CREATE TABLE "DATACENTER"."USER"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +1  
		    INCREMENT BY +1  
		    MINVALUE +1  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    CACHE 20  
		    NO ORDER ) , 
		  "GYH" VARCHAR(7) NOT NULL , 
		  "LEVEL" SMALLINT , 
		  "PASSWORD" VARCHAR(50) , 
		  "LOGINDATE" DATE )   
		 IN "USERSPACE1" ; 


CREATE TABLE "DATACENTER"."BUSINESSTYPE"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +0  
		    INCREMENT BY +1  
		    MINVALUE +0  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    NO CACHE  
		    NO ORDER ) , 
		  "NAME" VARCHAR(50) NOT NULL , 
		  "WEIGHT" INTEGER NOT NULL )   
		 IN "USERSPACE1" ; 

CREATE TABLE "DATACENTER"."MARKETINFO"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +1  
		    INCREMENT BY +1  
		    MINVALUE +1  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    CACHE 20  
		    NO ORDER ) , 
		  "MARKETEMPLOYEE" CHAR(7) , 
		  "INPUTUSER" INTEGER , 
		  "INPUTDATE" DATE , 
		  "MARKETDATE" DATE , 
		  "BUSINESS" INTEGER , 
		  "ACCTNO" VARCHAR(50) , 
		  "CUSTNAME" VARCHAR(50) )   
		 IN "USERSPACE1" ; 
		 
CREATE TABLE "DATACENTER".role  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +1  
		    INCREMENT BY +1  
		    MINVALUE +1  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    CACHE 20  
		    NO ORDER ) , 
		  role_Name varCHAR(50) , 
		  remark  varCHAR(50) , 
		 status smallint  )   ;
	CREATE TABLE "DATACENTER".resource  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +1  
		    INCREMENT BY +1  
		    MINVALUE +1  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    CACHE 20  
		    NO ORDER ) , 
		  resource_Name varCHAR(50) , 
		  parent int,
		  resourcePnl  varCHAR(50) ,  
		 status smallint ,
		 iorder int,
		  remark  varCHAR(50) )   ;		 
 Create table DATACENTER.USER_ROLE (
    user_id                        int                 NOT NULL    ,
    role_id                        int                 NOT NULL    ) 
 Create table DATACENTER.ROLE_RESOURCE (
    resource_id                        int                 NOT NULL    ,
    role_id                        int                 NOT NULL    ) ;
alter table DATACENTER.USER add status smallint not null default 1;

