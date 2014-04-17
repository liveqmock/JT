package com.jxjtjm.smsSystem;

import java.io.IOException;

import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageEncodings;
import org.smslib.Message.MessageTypes;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

import com.jxjtjm.beans.BeanManager;
import com.jxjtjm.beans.Contact;
import com.jxjtjm.beans.Sms;

public class SmsSystem {
	public static SmsSystem system=new SmsSystem();
	private Service srv;
	private SerialModemGateway gateway;

	public SmsSystem() {
		super();
		try {
			init();
		} catch (SMSLibException| IOException | InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void init() throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException {
		// ʵ�������ŷ��ͼ���
		OutboundNotification outboundNotification = new OutboundNotification();
		// ʵ�������Ž������
		InboundMessageNotification inboundMessageNotification = new InboundMessageNotification();

		srv = Service.getInstance();
		// ������ز�����һ�����������Ĭ�ϲ�����Ҫ�䣩
		gateway = new SerialModemGateway("modem.com1",
				"COM1", 9600, "wavecom", "");
		// ���ö��Ž���״̬Ϊ�ɽ���
		gateway.setInbound(true);
		// ���ö��ŷ���״̬Ϊ�ɷ���
		gateway.setOutbound(true);

		gateway.setSimPin("0000");
		// ������ز���������
		srv.addGateway(gateway);
		// ��Ӷ��Ž��ռ�����
		srv.setInboundMessageNotification(inboundMessageNotification);
		// ��Ӷ��ŷ��ͼ�����
		srv.setOutboundMessageNotification(outboundNotification);
		System.err.println("��ʼ���ɹ���׼����������");
		srv.startService();
		System.err.println("���������ɹ�");


	}
	@Override
	protected void finalize() throws Throwable {
		// TODO �Զ����ɵķ������
		gateway.stopGateway();
		srv.stopService();
		super.finalize();
	}
	public void sendSms(String telNo,String smsContent) throws TimeoutException, GatewayException, IOException, InterruptedException {
		OutboundMessage msg = new OutboundMessage(telNo, smsContent);
		msg.setEncoding(MessageEncodings.ENCUCS2); // ����
		srv.sendMessage(msg);
	}
	public class OutboundNotification implements IOutboundMessageNotification {
		
		@Override
		public void process(AGateway gateway, OutboundMessage msg) {
			// TODO �Զ����ɵķ������
			System.err.println("Outbound handler called from Gateway: "
					+ gateway.getGatewayId());
			System.err.println(msg);
		}
	}
	private class InboundMessageNotification implements	IInboundMessageNotification	{

		private String telNo;

		@Override
		public void process(AGateway gateway, MessageTypes msgType,
				InboundMessage message) {
			if (msgType == MessageTypes.INBOUND)
			{
				Sms sms=new Sms();
				sms.setContent(message.getText());
				telNo=message.getOriginator();
				telNo=telNo.startsWith("86")?telNo.substring(2, telNo.length()):telNo;
				sms.setTelNo(telNo);
				sms.setSendDate(message.getDate());
				
				Contact contact= BeanManager.BM.getBean(Contact.class, " telNo=?1",sms.getTelNo());
				sms.setReceiveName(contact==null?sms.getTelNo():contact.getName());
				sms.setIsSend(1);
				BeanManager.BM.saveBean(sms);
				try{
					// ɾ����ǰ���յ�����Ϣ
					srv.deleteMessage(message);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			
		}
	}
} 