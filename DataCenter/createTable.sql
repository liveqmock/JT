
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