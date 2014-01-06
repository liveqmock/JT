
select a.id,a.name,wktime,wkcost,productValue,scrapnum,sc,preparetime,pre from (select b.id id,b.name name,sum(worktime)wktime,sum(worktime*b.wage) wkcost,sum(worktime*c.cost) productValue,sum(scrapnum) scrapnum,SUM(reportprice*scrapnum) sc from operationwork a join employee b on a.employee=b.id join operationplan c on a.operationplan = c.id join plan d on c.plan=d.id join bill e on d.bill =e.id where finishdate>'2013-4-1' group by b.name          
) as a join (select  b.id id, b.name, sum(preparetime) preparetime,sum(preparetime*wage) pre from operationwork a join employee b on a.employee=b.id group by b.name
) as b on a.id=b.id


select * from operationwork 
select * from bill

实际成本：材料成本+工序成本（工序+调机）+