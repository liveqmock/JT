package com.mao.customLayout;

import java.sql.Date;
import java.util.ArrayList;

import com.mao.customLayout.bean.ColumnField;
import com.mao.customLayout.bean.DbSearch;
import com.mao.customLayout.bean.SCOPE;
import com.mao.customLayout.bean.SelectBean;
import com.mao.layout.system.JgSelect;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class DbSearchForm extends GridLayout{
	private DbSearch dbSearch;
	private FieldGroup fieldGroup;
	public DbSearchForm(DbSearch dbSearch,int columnNum) {
		super();
		this.dbSearch =dbSearch;
		fieldGroup=new FieldGroup(new PropertysetItem());
		setColumns(3*columnNum);
		setSpacing(true);
		setMargin(true);

		createFields();
	}




	public void formCommit() throws CommitException {
		fieldGroup.commit();
		for(ColumnField columnField:dbSearch.getColumnFields()){

			if(columnField.isWhereColumn()){
				columnField.setScopeType((SCOPE) fieldGroup.getItemDataSource().getItemProperty(columnField.getName()+"_scope").getValue());
				if(columnField.getScopeType()!=null){
					columnField.setValue( fieldGroup.getField(columnField.getName()).getValue());
					if(columnField.getScopeType()==SCOPE.BETWEEN)
						columnField.setValue2( fieldGroup.getField(columnField.getName()+"_2").getValue());

				}
			}
		}
		dbSearch.createSql();
	}
	public void commit() throws CommitException {

		formCommit();

	}	

	public void addComponent(String caption,Component scopeField,Component field)
			throws OverlapsException, OutOfBoundsException {
		// TODO 自动生成的方法存根
		Label captionLbl = new Label(caption+":");
		addComponent(captionLbl);
		addComponent(scopeField);
		addComponent(field);
		setComponentAlignment(captionLbl, Alignment.TOP_RIGHT);

	}
	public ComboBox createScopeField(final ColumnField columnField) {
		ComboBox listSelect=new ComboBox();

		listSelect.setWidth("60px");
		listSelect.setImmediate(true);
		listSelect.setRequiredError("不能为空");
		
		
		fieldGroup.getItemDataSource().addItemProperty(columnField.getName()+"_scope",  new ObjectProperty<SCOPE>(null,SCOPE.class));
		fieldGroup.bind(listSelect, columnField.getName()+"_scope");
		listSelect.setValue(null);
		
		if(columnField.getSelectList()==null){
			if(columnField.getValueType().equals("string")||columnField.getValueType().equals("password")){
				listSelect.addItem(SCOPE.EQUAL);
				listSelect.addItem(SCOPE.NEQUAL);
				listSelect.addItem(SCOPE.CONTAIN);
				listSelect.addItem(SCOPE.NCONTAIN);
			}else if(columnField.getValueType().equals("jg")){
				listSelect.addItem(SCOPE.CONTAIN);				
			} else{
				listSelect.addItem(SCOPE.EQUAL);
				listSelect.addItem(SCOPE.NEQUAL);
				listSelect.addItem(SCOPE.GT);
				listSelect.addItem(SCOPE.GE);
				listSelect.addItem(SCOPE.LT);
				listSelect.addItem(SCOPE.LE);
				listSelect.addItem(SCOPE.BETWEEN);
			}
		}

		listSelect.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object fieldValue = event.getProperty().getValue();
				Field<?> firstField = fieldGroup.getField(columnField.getName());

				if(fieldValue==SCOPE.BETWEEN){
					showBetweenLayout(columnField);
				}else if(firstField.getParent() instanceof FormLayout){
					firstField.setCaption(null);
					FormLayout layout = (FormLayout)firstField.getParent();
					Area area = getComponentArea(layout);
					removeComponent(layout);
					addComponent(firstField,area.getColumn1(),area.getRow1(),area.getColumn2(),area.getRow2());
					fieldGroup.getField(columnField.getName()+"_2").setRequired(false);
					firstField.setRequired(false);

				}
			}


		});

		return listSelect;
	}
	private void showBetweenLayout(ColumnField columnField) {
		Field<?> firstField = fieldGroup.getField(columnField.getName());
		Area area = getComponentArea(firstField);
		firstField.setCaption("从");
		firstField.setRequired(true);
		FormLayout layout=null;
		if(fieldGroup.getItemDataSource().getItemProperty(columnField.getName()+"_2")!=null){
			Field<?> secondField = fieldGroup.getField(columnField.getName()+"_2");
			secondField.setRequired(true);
			layout=( (FormLayout)secondField.getParent());
			layout.addComponentAsFirst(firstField);
		}else{
			Field<?> secondField=createField(columnField, true);	
			secondField.setCaption("至");	
			secondField.setRequired(true);
			layout=new FormLayout();	
			layout.setSpacing(false);
			layout.setMargin(false);
			layout.addComponent(firstField);
			layout.addComponent(secondField);
		}
		addComponent(layout,area.getColumn1(),area.getRow1(),area.getColumn2(),area.getRow2());

	}

	public void createFields() {
		for(ColumnField columnField:dbSearch.getColumnFields()){

			if(columnField.isWhereColumn())
				addComponent(columnField.getLabel(),createScopeField(columnField),createField(columnField,false));
		}

	}
	public Field<?> createField(final ColumnField columnField,boolean second) {
		Field<?> field=null;
		if(columnField.getSelectList()!=null){
			field=new ComboBox();
			ArrayList<SelectBean<?>> aa = columnField.getSelectList();
			for(SelectBean<?> selectBean:aa){
				((ComboBox)field).addItem(selectBean.getValue());

				((ComboBox)field).setItemCaption(selectBean.getValue(), selectBean.getCaption());
			}
			((ComboBox)field).setImmediate(true);
		}else{
			if(columnField.getValueType().equals("date")){
				field=new DateField();
				((DateField)field).setImmediate(true);
				((DateField)field).setDateFormat("yyyy-MM-dd");
			}else if(columnField.getValueType().equals("time")){
				field=new DateField();
				((DateField)field).setImmediate(true);
				((DateField)field).setDateFormat("hh:mm:ss");
			}else if(columnField.getValueType().equals("timestamp")){
				field=new DateField();
				((DateField)field).setImmediate(true);
				((DateField)field).setDateFormat("yyyy-MM-dd hh:mm:ss");
			}else if(columnField.getValueType().equals("jg")){
				field=new JgSelect();
			}else if(columnField.getValueType().equals("password")){
				field=new PasswordField();
			}else{
				field=new TextField();
				((TextField)field).setImmediate(true);
				((TextField)field).setNullRepresentation("");
			}

		}
		field.setRequiredError("不能为空");
		fieldGroup.getItemDataSource().addItemProperty(columnField.getName()+(second?("_2"):""), getObjectProperty(columnField));
		fieldGroup.bind(field, columnField.getName()+(second?("_2"):""));
		field.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object fieldValue = event.getProperty().getValue();
				Field<?> scopeField = fieldGroup.getField(columnField.getName()+"_scope");
				if(fieldValue!=null&&!fieldValue.equals("")){
					scopeField.setRequired(true);
				}else {
					scopeField.setRequired(false);
				}

			}
		});
		setValue(field, columnField);
		return field;
	}

	@SuppressWarnings("unchecked")
	private void setValue(Field<?> field, ColumnField columnField) {

		if(columnField.getValue()!=null&&columnField.getValueType().equals("int")){			
			((Field<Integer>)field).setValue(((ColumnField<Integer>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("long")){			
			((Field<Long>)field).setValue(((ColumnField<Long>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("float")){			
			((Field<Float>)field).setValue(((ColumnField<Float>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("double")){			
			((Field<Double>)field).setValue(((ColumnField<Double>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("number")){			
			((Field<Number>)field).setValue(((ColumnField<Number>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("password")){	
			((Field<String>)field).setValue(((ColumnField<String>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("string")){	
			((Field<String>)field).setValue(((ColumnField<String>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("short")){			
			((Field<Short>)field).setValue(((ColumnField<Short>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("time")){
			((Field<Date>)field).setValue(((ColumnField<Date>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("date")){
			((Field<Date>)field).setValue(((ColumnField<Date>)columnField).getValue());
		}else if(columnField.getValue()!=null&&columnField.getValueType().equals("timestamp")){
			((Field<Date>)field).setValue(((ColumnField<Date>)columnField).getValue());
		}else{
			((Field<Object>)field).setValue((Object)columnField.getValue());
		}
	}




	private Property<?> getObjectProperty(ColumnField columnField) {
		if(columnField.getValueType().equals("int")){
			return  new ObjectProperty<Integer>(null,int.class);
		}else if(columnField.getValueType().equals("short")){
			return  new ObjectProperty<Short>(null,short.class);
		}else if(columnField.getValueType().equals("float")){
			return  new ObjectProperty<Float>(null,float.class);
		}else if(columnField.getValueType().equals("double")){
			return  new ObjectProperty<Double>(null,double.class);
		}else if(columnField.getValueType().equals("number")){
			return  new ObjectProperty<Number>(null,Number.class);
		}else if(columnField.getValueType().equals("string")){
			return  new ObjectProperty<String>(null,String.class);
		}else if(columnField.getValueType().equals("date")){
			return  new ObjectProperty<Date>(null,Date.class);
		}else if(columnField.getValueType().equals("long")){
			return  new ObjectProperty<Long>(null,long.class);
		}else if(columnField.getValueType().equals("time")){
			return  new ObjectProperty<Date>(null,Date.class);
		}else 
			return new ObjectProperty<Object>("") ;
	}




	public DbSearch getDbSearch() {
		return dbSearch;
	}




	public void setDbSearch(DbSearch columnList) {
		this.dbSearch = columnList;
	}




	public void cancel() {
		fieldGroup.discard();

	}



	//	public class PopupField implements PopupView.Content  {
	//
	//		private Field<?> firstField ;
	//		private Field<?> secondField ;
	//
	//		private FormLayout root = new FormLayout();
	//
	//		public PopupField(Field<?> firField) {
	//			this.firstField=firField;
	//			this.firstField.setCaption("从");
	//			root.setSizeUndefined();
	//			root.setSpacing(true);
	//			root.setMargin(true);           
	//			root.addComponent(this.firstField);
	//			if(this.firstField instanceof TextField){
	//				secondField=new TextField("至");
	//			}else if(this.firstField instanceof DateField){
	//				secondField=new DateField("至",new Date());
	//			}
	//			root.addComponent(secondField);
	//			secondField.setRequired(true);
	//		}
	//
	//		public String getMinimizedValueAsHTML() {
	//			if(firstField instanceof DateField){
	//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//				System.out.println(firstField.getValue());
	//				System.out.println(secondField.getValue());
	//				return df.format(firstField.getValue())+"至"+df.format(secondField.getValue());
	//			}
	//
	//			return firstField.getValue().toString()+"至"+secondField.getValue().toString();
	//		}
	//
	//		public Component getPopupComponent() {
	//			return root;
	//		}
	//		public Field getFirstField() {
	//			return firstField;
	//		}
	//
	//		public void setFirstField(Field firstField) {
	//			this.firstField = firstField;
	//		}
	//
	//		public Field getSecondField() {
	//			return secondField;
	//		}
	//
	//		public void setSecondField(Field secondField) {
	//			this.secondField = secondField;
	//		}
	//	}
}
