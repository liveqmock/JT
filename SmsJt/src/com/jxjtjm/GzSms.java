package com.jxjtjm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.jxjtjm.beans.LoginUser;
import com.jxjtjm.beans.Sms;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class GzSms extends FormLayout {
	private GzReader	gzReader=new GzReader();
	private Table table;
	public GzSms() {
		setSizeFull();
		setCaption("工资单发送");
		final ByteArrayOutputStream os=new ByteArrayOutputStream();
		FileReciver fileReciver=new FileReciver(os);
		Upload upload=new Upload("工资文件",fileReciver);
		upload.setButtonCaption("上传工资文件");
		addComponent(upload);
		table=new Table("工资短信内容");
		table.setSizeFull();
		table.setColumnHeader("content", "短信内容");
		table.setColumnHeader("receiveName", "授收人");
		table.setColumnHeader("telNo", "手机号");
		table.setSelectable(true);
		addComponent(table);

		final Button button=new Button("确定发送");
		addComponent(button);
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				button.setEnabled(false);
				gzReader.sendAll( (LoginUser) getSession().getAttribute("loginUser"));

				Notification.show("发送成功");
				button.setEnabled(true);
			}
		}
				);
		upload.addStartedListener(new StartedListener() {

			@Override
			public void uploadStarted(StartedEvent event) {
				if(!(event.getFilename().endsWith(".xls")||event.getFilename().endsWith(".xlsx"))){
					event.getUpload().interruptUpload();
					Notification.show("不是有效的EXCEL文件,请重新选择文件!");
					return;
				}

			}
		});
		upload.addSucceededListener(new SucceededListener() {


			@Override
			public void uploadSucceeded(SucceededEvent event) {
				try {
					ByteArrayInputStream is=new ByteArrayInputStream(os.toByteArray());
					os.close();
					if(event.getFilename().endsWith(".xls"))
						gzReader.readSmsFromXls(is);
					if(event.getFilename().endsWith(".xlsx"))
						gzReader.readSmsFromXlsx(is);
					is.close();
					BeanItemContainer<Sms> items=new BeanItemContainer<Sms>(gzReader.getSmses());
					table.setContainerDataSource(items);
					table.setVisibleColumns("receiveName","telNo", "content");
					
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
		});
	}
	private static class FileReciver implements Receiver {
		ByteArrayOutputStream os;
		@Override
		public OutputStream receiveUpload(final String filename,
				final String MIMEType) {
			return os;

		}
		public FileReciver(ByteArrayOutputStream os) {
			super();
			this.os = os;
		}

	}
}
