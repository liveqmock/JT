package com.mao.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.mao.tool.BeanManager;
import com.vaadin.server.VaadinSession;

public class BusinessMarket {
	private BusinessType business;
	private Employee employee;
	private int num;

	public BusinessType getBusiness() {
		return business;
	}
	public void setBusiness(BusinessType business) {
		this.business = business;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getNum() {
		return num;
	}
	public int getValue() {
		return num*business.getWeight();
	}
	public void setNum(int num) {
		this.num = num;
	}

	public static String getStaticHtml(Date date1,Date date2) {
		HashMap<BusinessType, ArrayList<BusinessMarket>> buHashMap=new HashMap<BusinessType, ArrayList<BusinessMarket>>();
		Query query= BeanManager.BM.getEm().createNativeQuery("Select marketEmployee as employee, business,count(1) num from datacenter.MarketInfo a join core.BBFMSTLR b on a.marketEmployee=b.STCASTAF join core.BBFMORGA c on b.STCABRNO=c.ORCABRNO where marketDate between ?1 and ?2 and c.ORCFBRNO=?3 group by marketEmployee, business ");
		query.setParameter(1, date1);
		query.setParameter(2, date2);
		query.setParameter(3, VaadinSession.getCurrent().getAttribute(User.class).getEmployee().getDepartment().getTopDepartment().getId());
		List list = query.getResultList();
		for(Object object:list){
			try{
				BusinessMarket businessMarket=new BusinessMarket();
				businessMarket.setBusiness(BeanManager.BM.find(BusinessType.class, (Integer)((Object[])object)[1]));
				businessMarket.setEmployee(BeanManager.BM.find(Employee.class, ((Object[])object)[0]));
				businessMarket.setNum( (Integer) ((Object[])object)[2]);
				ArrayList<BusinessMarket> businessMarketList = buHashMap.get(businessMarket.getBusiness());
				if(businessMarketList==null){
					businessMarketList=new ArrayList<BusinessMarket>();
					buHashMap.put(businessMarket.getBusiness(), businessMarketList);
				}
				businessMarketList.add(businessMarket);
			}catch(Exception exception){
				
			}
		}
		HashMap<Employee,  HashMap<BusinessType, BusinessMarket>> emMap=new HashMap<Employee, HashMap<BusinessType, BusinessMarket>>();
		for(ArrayList<BusinessMarket> businessMarketList:buHashMap.values()){
			for(BusinessMarket businessMarket:businessMarketList){
				HashMap<BusinessType, BusinessMarket> emlist = emMap.get(businessMarket.getEmployee());
				if(emlist==null){
					emlist=new  HashMap<BusinessType, BusinessMarket>();
					emMap.put(businessMarket.getEmployee(), emlist);
				}
				emlist.put(businessMarket.business, businessMarket);
			}
		}
		StringBuffer html=new StringBuffer("<table  border=1 cellpadding=2 cellspacing=0 style=border-collapse:collapse><th>");
		Set<BusinessType> businessSet = buHashMap.keySet();
		for(BusinessType businessType:businessSet){
			html.append("<td>").append(businessType.getName()).append("</td>");
		}
		html.append("</th>");
		for(Employee em:emMap.keySet()){
			html.append("<tr><td>").append(em.getName()).append("</td>");
			for(BusinessType businessType:businessSet){
				BusinessMarket businessMarket=emMap.get(em).get(businessType);
				html.append("<td>").append(businessMarket==null?"":businessMarket.getValue()).append("</td>");
			}
			html.append("</tr>");
		}
		
		
		
		html.append("</table>");
		return html.toString();

	}
}
