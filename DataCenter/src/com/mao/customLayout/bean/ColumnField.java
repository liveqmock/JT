package com.mao.customLayout.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ColumnField<T>  implements Serializable{
	private String name;
	private String label;
	private boolean whereColumn;
	private SCOPE scopeType;
	private String valueType;
	private T value;
	private T value2;
	private ArrayList<SelectBean<T>> selectList;
	private boolean selectColumn=true;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label==null?name:label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public T getValue2() {
		return value2;
	}
	public void setValue2(T value2) {
		this.value2 = value2;
	}

	public ArrayList<SelectBean<T>> getSelectList() {
		return selectList;
	}
	public void setSelectList(ArrayList<SelectBean<T>> selectList) {
		this.selectList = selectList;
	}
	public SCOPE getScopeType() {
		return scopeType;
	}
	public void setScopeType(SCOPE scopeType) {
		this.scopeType = scopeType;
	}
	public boolean isSelectColumn() {
		return selectColumn;
	}
	public void setSelectColumn(boolean selectColumn) {
		this.selectColumn = selectColumn;
	}
	public boolean isWhereColumn() {
		return whereColumn;
	}
	public void setWhereColumn(boolean whereColumn) {
		this.whereColumn = whereColumn;
	}
	public String getSelectColStr() {
		String selectStr;
		if(selectList!=null&&selectList.size()>0){
			selectStr="case " + name;
			for(SelectBean<T> selectBean:selectList){
				if(valueType.equals("string"))
					selectStr+=" when '"+selectBean.getValue()+"' then '"+selectBean.getCaption()+"' ";
				else
					selectStr+=" when "+selectBean.getValue()+" then '"+selectBean.getCaption()+"' ";

			}
			selectStr+=" end";
		}else
			selectStr=name;
		if(label!=null)
			selectStr+= " as "+label;
		return selectStr;
	}
	public String getWhereColStr() {
		String whereSql="";
		switch (scopeType) {
		case EQUAL:
			whereSql=name +" = ?";
			break;
		case NEQUAL:
			whereSql=name +" <> ?";
			break;
		case CONTAIN:
			whereSql=name +" like ?";
			break;
		case NCONTAIN:
			whereSql=name +" not like ?";
			break;
		case BETWEEN:
			whereSql=name + " Between  ? and ?";
			break;
		case GT:
			whereSql=name + " > ?";
			break;
		case GE:
			whereSql=name + " >=  ?";
			break;
		case LT:
			whereSql=name + " < ?";
			break;
		case LE:
			whereSql=name + " <= ?";
			break;

		default:
			break;
		}
		return whereSql;
	}
	public String getWhereJpaColStr(int i) {
		String whereSql="";
		switch (scopeType) {
		case EQUAL:
			whereSql=name +" = ?"+i;
			break;
		case NEQUAL:
			whereSql=name +" <> ?"+i;
			break;
		case CONTAIN:
			whereSql=name +" like ?"+i;
			break;
		case NCONTAIN:
			whereSql=name +" not like ?"+i;
			break;
		case BETWEEN:
			whereSql=name + " Between  ?"+i+" and ?"+(i+1);
			break;
		case GT:
			whereSql=name + " > ?"+i;
			break;
		case GE:
			whereSql=name + " >=  ?"+i;
			break;
		case LT:
			whereSql=name + " < ?"+i;
			break;
		case LE:
			whereSql=name + " <= ?"+i;
			break;

		default:
			break;
		}
		return whereSql;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}


}
