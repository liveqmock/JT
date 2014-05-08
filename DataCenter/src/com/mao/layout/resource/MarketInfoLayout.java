package com.mao.layout.resource;

import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;

import com.mao.bean.BusinessType;
import com.mao.bean.MarketInfo;
import com.mao.bean.User;
import com.mao.customLayout.BeansEditPanel;
import com.mao.tool.BeanManager;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
public class MarketInfoLayout  extends BeansEditPanel<MarketInfo>{
	public MarketInfoLayout() throws ClassNotFoundException, SQLException, NamingException, Exception {
		super(MarketInfo.class,null,new MarketInfo());
		setCaption("Ӫҵ��Ϣ¼��");
		User user = VaadinSession.getCurrent().getAttribute(User.class);
		ComboBox employeeBox= new ComboBox("",user.getEmployee().getDepartment().getTopDepartment().getAllEmployees() );
		getForm().binder(employeeBox, "marketEmployee");
		ComboBox businessBox= new ComboBox("",BeanManager.BM.getBeans(BusinessType.class));
		getForm().binder(businessBox, "business");
//		businessBox.addItem("��������");
//		businessBox.addItem("��ҵ���� ");
//		businessBox.addItem("�ֻ�����");
//		businessBox.addItem("��Ч���ǿ�");
//		businessBox.addItem("�������ʻ�");
//		businessBox.addItem("POS��");
//		businessBox.addItem("���յ绰��");
//		businessBox.addItem("��Ȼ�˴���");
//		businessBox.addItem("С΢��ҵ����");
		getForm().autoBinder();
	}

	@Override
	public void beanSave() {
		getForm().getBean().setInputDate(new Date());
		getForm().getBean().setInputUser(VaadinSession.getCurrent().getAttribute(User.class));
		super.beanSave();
	}
	
}
