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
		setCaption("���ʵ�����");
		final ByteArrayOutputStream os=new ByteArrayOutputStream();
		FileReciver fileReciver=new FileReciver(os);
		Upload upload=new Upload("�����ļ�",fileReciver);
		upload.setButtonCaption("�ϴ������ļ�");
		addComponent(upload);
		table=new Table("���ʶ�������");
		table.setSizeFull();
		table.setColumnHeader("content", "��������");
		table.setColumnHeader("receiveName", "������");
		table.setColumnHeader("telNo", "�ֻ���");
		table.setSelectable(true);
		addComponent(table);

		final Button button=new Button("ȷ������");
		addComponent(button);
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				button.setEnabled(false);
				gzReader.sendAll( (LoginUser) getSession().getAttribute("loginUser"));

				Notification.show("���ͳɹ�");
				button.setEnabled(true);
			}
		}
				);
		upload.addStartedListener(new StartedListener() {

			@Override
			public void uploadStarted(StartedEvent event) {
				if(!(event.getFilename().endsWith(".xls")||event.getFilename().endsWith(".xlsx"))){
					event.getUpload().interruptUpload();
					Notification.show("������Ч��EXCEL�ļ�,������ѡ���ļ�!");
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
					// TODO �Զ����ɵ� catch ��
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
