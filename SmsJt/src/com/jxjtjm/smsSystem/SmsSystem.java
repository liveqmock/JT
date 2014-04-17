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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void init() throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException {
		// 实例化短信发送监听
		OutboundNotification outboundNotification = new OutboundNotification();
		// 实例化短信接入监听
		InboundMessageNotification inboundMessageNotification = new InboundMessageNotification();

		srv = Service.getInstance();
		// 设置相关参数（一般情况下以下默认参数不要变）
		gateway = new SerialModemGateway("modem.com1",
				"COM1", 9600, "wavecom", "");
		// 设置短信接收状态为可接收
		gateway.setInbound(true);
		// 设置短信发送状态为可发送
		gateway.setOutbound(true);

		gateway.setSimPin("0000");
		// 添加网关参数到服务
		srv.addGateway(gateway);
		// 添加短信接收监听器
		srv.setInboundMessageNotification(inboundMessageNotification);
		// 添加短信发送监听器
		srv.setOutboundMessageNotification(outboundNotification);
		System.err.println("初始化成功，准备开启服务");
		srv.startService();
		System.err.println("服务启动成功");


	}
	@Override
	protected void finalize() throws Throwable {
		// TODO 自动生成的方法存根
		gateway.stopGateway();
		srv.stopService();
		super.finalize();
	}
	public void sendSms(String telNo,String smsContent) throws TimeoutException, GatewayException, IOException, InterruptedException {
		OutboundMessage msg = new OutboundMessage(telNo, smsContent);
		msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
		srv.sendMessage(msg);
	}
	public class OutboundNotification implements IOutboundMessageNotification {
		
		@Override
		public void process(AGateway gateway, OutboundMessage msg) {
			// TODO 自动生成的方法存根
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
					// 删除当前接收到的信息
					srv.deleteMessage(message);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			
		}
	}
} 