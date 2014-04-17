package com.mao.customLayout;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import com.mao.tool.BeanManager;
import com.mao.tool.MaoLogger;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class BeansEditPanel<T> extends VerticalLayout implements Handler {

	private BeansTablePanel<T> tableLayout;
	private HorizontalLayout btBar;
	private BeanForm<T> form;
	private Collection<T> beans;
	private Class<T> class1;
	private T bean;
	private Button newBt;
	private Button removeBt;
	private Button findBt;
	private Button saveBt;
	public BeansEditPanel(Class<T> class1,Collection<T> beans,T bean) {
		super();
		this.class1=class1;
		if(beans==null)
			beans=new ArrayList<T>();
		this.beans=beans;
		this.bean=bean;
		//		setMargin(true);
		createContent();
	}

	public void initForm() {
		try {
			form=new BeanForm<T>(bean);
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}			

	}
	public void setAddnewable(boolean isNewable) {
		newBt.setVisible(isNewable);
	}
	public void createContent(){

		initForm();
		initBar();
		initTable();

		VerticalLayout formLayout=new VerticalLayout();
		formLayout.setStyleName("form");
		formLayout.addComponent(form);
		formLayout.addComponent(btBar);
		formLayout.setSizeUndefined();
		formLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		formLayout.setComponentAlignment(btBar, Alignment.MIDDLE_CENTER);

		addComponent(formLayout);
		addComponent(tableLayout);
		setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		setExpandRatio(tableLayout, 1.0f);
		setSizeFull();
		btBar.setWidth(form.getWidth(),form.getWidthUnits());

	}
	private void initTable() {
		tableLayout=new BeansTablePanel<T>(class1 ,beans);
		tableLayout.setSizeFull();
		tableLayout.setCaption(getCaption());
		tableLayout.getTable().addActionHandler(this);
		tableLayout.getTable().addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				changeBean();

			}
		});
	}
	private void initBar() {
		btBar=new HorizontalLayout();
		btBar.setSpacing(true);
		btBar.setMargin(true);
		newBt=new Button("新增(N)");
		saveBt=new Button("保存(S)");
		findBt=new Button("查找(F)");
		removeBt=new Button("删除(R)");
		//功能未实现
//		findBt.setVisible(false);
		
		Button cancelBt=new Button("重置(C)");
		newBt.setClickShortcut('n',ModifierKey.ALT);
		saveBt.setClickShortcut(KeyCode.ENTER);
		saveBt.addStyleName(Reindeer.BUTTON_DEFAULT);
		findBt.setClickShortcut('f',ModifierKey.ALT);
		removeBt.setClickShortcut('r',ModifierKey.ALT);
		cancelBt.setClickShortcut('c',ModifierKey.ALT);
		saveBt.setIcon(new ThemeResource("img/save.png"));
		newBt.setIcon(new ThemeResource("img/new.png"));
		removeBt.setIcon(new ThemeResource("img/remove.png"));
		findBt.setIcon(new ThemeResource("img/search.png"));
		cancelBt.setIcon(new ThemeResource("img/reset.png"));
		btBar.setWidth("100%");
		btBar.addComponent(newBt);
		btBar.addComponent(saveBt);
		btBar.addComponent(findBt);
		btBar.addComponent(removeBt);
		btBar.addComponent(cancelBt);
		btBar.setComponentAlignment(newBt , Alignment.MIDDLE_CENTER);
		btBar.setComponentAlignment(saveBt, Alignment.MIDDLE_CENTER);
		btBar.setComponentAlignment(findBt, Alignment.MIDDLE_CENTER);
		btBar.setComponentAlignment(removeBt, Alignment.MIDDLE_CENTER);
		btBar.setComponentAlignment(cancelBt, Alignment.MIDDLE_CENTER);
		newBt.addClickListener(new Button.ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					form.setBean((T) form.getBean().getClass().newInstance());
				} catch (IllegalAccessException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		saveBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				beanSave();

			}
		});
		findBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				beanFind();
			}
		});
		removeBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				removeBean(form.getBean());

			}
		});
		cancelBt.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				form.cancel();
			}
		});

	}

	@Override
	public Action[] getActions(Object target, Object sender) {
		return new Action[]{new Action("修改"),new Action("删除")};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if(target==null)return;
		
		if(action.getCaption().equals("修改")){
			if(tableLayout.getTable().getValue()!=null)
				changeBean();
		}else if(action.getCaption().equals("删除")){			
			removeBean((T) tableLayout.getTable().getValue());
		}

	}

	@SuppressWarnings("unchecked")
	public void removeBean(final T removeBean ) {
		ConfirmDialog.show(UI.getCurrent(), "删除确认", "确定删除？", "确定", "取消", new ConfirmDialog.Listener() {

			@Override
			public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					try {

						BeanManager.BM.removeBean(removeBean);
						form.setBean((T) removeBean.getClass().newInstance());
						tableLayout.getTable().removeItem(removeBean);
					} catch (Exception e) {
						Notification.show("删除失败",Notification.Type.ERROR_MESSAGE);
						MaoLogger.error("删除失败",e);
					}
				} 
			}
		});


	}
	public void  changeBean() {
		try {
			@SuppressWarnings("unchecked")
			T bean = (T) tableLayout.getTable().getValue();
			if(bean!=null){
				form.setBean(bean);
			}
		} catch (Exception e) {
			Notification.show("不能修改",Notification.Type.ERROR_MESSAGE);
			MaoLogger.error("不能修改",e);
		}
	}
	@SuppressWarnings("unchecked")
	public void beanSave() {
		try{
			form.saveBean();
			if(!tableLayout.getTable().getContainerDataSource().containsId(form.getBean())){
				tableLayout.getTable().addItem(form.getBean());
			}
			tableLayout.getTable().refreshRowCache();

			form.setBean((T) form.getBean().getClass().newInstance());
		}catch(Exception e){
			if(e instanceof CommitException) return;
			Notification.show("保存失败",Notification.Type.ERROR_MESSAGE);
			MaoLogger.error("保存失败",e);
		}

	}
	public void beanFind() {
		
	}
	public BeanForm<T> getForm() {
		return form;
	}

	public BeansTablePanel<T> getTableLayout() {
		return tableLayout;
	}

	public void setForm(BeanForm<T> form) {
		this.form = form;
	}

	public Collection<T> getBeans() {
		return beans;
	}

	public void setBeans(Collection<T> beans) {
		this.beans = beans;
		tableLayout.setBeans(beans);
	}

}
