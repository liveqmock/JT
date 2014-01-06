select a.* ,b.* from operationPlan a left join (
	select operationplan,sum(usetime) usetime,sum(prepareTime) prepareTime,sum(getNum) getNum,sum(productNum) product,sum(scrapnum) scrapnum from operationwork group by operationplan
) b on a.id=b.operationplan