package ui.customComponet;


import com.mao.jf.beans.annotation.Caption;

public class BeanTableModelHeader implements Comparable<BeanTableModelHeader> {

	private Caption caption;
	private String field;
	private Class<?> fldClass;


	public BeanTableModelHeader(Caption caption, String field,Class<?> fldClass) {
		super();
		this.caption = caption;
		this.field = field;
		this.fldClass=fldClass;
	}
	public Caption getCaption() {
		return caption;
	}
	public String getField() {
		return field;
	}
	public void setCaption(Caption caption) {
		this.caption = caption;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Class<?> getFldClass() {
		return fldClass;
	}
	public void setFldClass(Class<?> fldClass) {
		this.fldClass = fldClass;
	}
	@Override
	public int compareTo(BeanTableModelHeader arg0) {
		return getCaption().order()>=arg0.getCaption().order()?1:-1;
	}

}
