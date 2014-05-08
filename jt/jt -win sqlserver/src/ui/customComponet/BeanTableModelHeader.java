package ui.customComponet;



public class BeanTableModelHeader implements Comparable<BeanTableModelHeader> {

	private int order;
	private String caption;
	private String field;
	private Class<?> fldClass;


	public BeanTableModelHeader(int order, String caption, String field,Class<?> fldClass) {
		super();
		this.setOrder(order);
		this.caption = caption;
		this.field = field;
		this.fldClass=fldClass;
	}
	public String getCaption() {
		return caption;
	}
	public String getField() {
		return field;
	}
	public void setCaption(String caption) {
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int compareTo(BeanTableModelHeader arg0) {
		int c= ((Integer)order).compareTo(arg0.getOrder());
		c=c==0?-1:c;
		return c;
	}

}
