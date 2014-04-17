package com.mao.layout.resource;

import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;

import com.mao.bean.BusinessType;
import com.mao.bean.Employee;
import com.mao.bean.MarketInfo;
import com.mao.bean.User;
import com.mao.customLayout.BeansEditPanel;
import com.mao.tool.BeanManager;
import com.sun.org.apache.xpath.internal.operations.String;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
public class MarketInfoLayout  extends BeansEditPanel<MarketInfo>{
	public MarketInfoLayout() throws ClassNotFoundException, SQLException, NamingException, Exception {
		super(MarketInfo.class,null,new MarketInfo());
		setCaption("营业信息录入");
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		ComboBox employeeBox= new ComboBox("",user.getEmployee().getDepartment().getTopDepartment().getAllEmployees() );
		getForm().binder(employeeBox, "marketEmployee");
		ComboBox businessBox= new ComboBox("",BeanManager.BM.getBeans(BusinessType.class));
		getForm().binder(businessBox, "business");
//		businessBox.addItem("个人网银");
//		businessBox.addItem("企业网银 ");
//		businessBox.addItem("手机银行");
//		businessBox.addItem("有效贷记卡");
//		businessBox.addItem("代发工资户");
//		businessBox.addItem("POS机");
//		businessBox.addItem("丰收电话宝");
//		businessBox.addItem("自然人贷款");
//		businessBox.addItem("小微企业贷款");
		getForm().autoBinder();
	}

	@Override
	public void beanSave() {
		getForm().getBean().setInputDate(new Date());
		getForm().getBean().setInputUser(VaadinSession.getCurrent().getAttribute(User.class));
		super.beanSave();
	}
	
}
