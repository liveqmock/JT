package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import com.mao.jf.beans.annotation.Caption;

public class BeanMao implements Serializable {
	protected int id;


	@Caption(order = 0, value= "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void remove() throws SQLException {

		removeBySql(this.getClass(), "delete \""+this.getClass().getSimpleName()+"\" where id="+this.id);
	}
	public static void removeBySql(Class cls,String sql) throws SQLException {
		try(Statement st=SessionData.getConnection().createStatement();

				){
			st.executeUpdate(sql);
		}
	}
	public void save() throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, NoSuchMethodException, SecurityException {
		Method[] methods = this.getClass().getMethods();
		String sql = null;
		ArrayList<Method> methodList=new ArrayList<>();
		if(id==0){
			sql="insert into \""+this.getClass().getSimpleName()+"\"( ";
			String endString=" )values(";
			for(Method m:methods){
				String methodName = m.getName();
				Transient isTransient = m.getAnnotation(Transient.class);
				if(methodName.startsWith("get")&&!methodName.equals("getId")&&!methodName.equals("getClass")&&isTransient==null){
					sql+="\""+methodName.substring(3,methodName.length())+"\",";
					endString+="?,";
					methodList.add(m);
				}
			}
			sql=sql.substring(0,sql.length()-1)+endString.substring(0,endString.length()-1)+")";
//			System.err.println(sql);
		}else{
			sql="update \""+this.getClass().getSimpleName()+"\" set ";
			for(Method m:methods){
				String methodName = m.getName();
				Transient isTransient = m.getAnnotation(Transient.class);
				if(methodName.startsWith("get")&&!methodName.equals("getId")&&!methodName.equals("getClass")&&isTransient==null){
					sql+="\""+methodName.substring(3,methodName.length())+"\"=?,";
					methodList.add(m);
				}

			}
			sql=sql.substring(0,sql.length()-1)+" where id=" +this.id;


		}
		try(PreparedStatement pst = SessionData.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
				){
			for(int i=1 ;i<=methodList.size();i++){
				Object object=methodList.get(i-1).invoke(this);
				if(object!=null&&object instanceof BeanMao){
					object=((BeanMao)object).getId();
				}
				if(object instanceof Boolean){
					object=((Boolean)object)?1:0;
				}
				pst.setObject(i, object);

			}
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if(rs.next())this.id=rs.getInt(1);
		}

	}


	public static <T> Vector<T> loadAll(Class<T> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		return loadAll(cls, "select * from \""+cls.getSimpleName()+"\"");
	}
	public static <T> Vector<T> loadAll(Class<T> cls,String sql) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try(Statement st=SessionData.getConnection().createStatement();
				ResultSet rs=st.executeQuery(sql);
				){
			Vector<T> objects=new Vector<>(); 
			while(rs.next()){

				objects.add(createObject(rs, cls));
			}
			return objects;

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	public static <T> T createObject(ResultSet rs, Class<T> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, NoSuchMethodException, SecurityException {
		Object object=cls.newInstance();
		Method[] methods = cls.getMethods();
		for(int c=1;c<=rs.getMetaData().getColumnCount();c++){
			Object dbObject = rs.getObject(c);
			if(dbObject!=null){
				String cName=rs.getMetaData().getColumnName(c);
				Method setMethod=null;
				for(Method m:methods)
					if(m.getName().toLowerCase().equals(("set"+cName).toLowerCase())){
						setMethod=m;
						break;	
					}
				if(setMethod!=null){

					Class<?>[] rtnClasses = setMethod.getParameterTypes();

					if(rtnClasses.length>0){
						Class<?> rtnClass = rtnClasses[0];

						if(rtnClass.getSuperclass()!=null&&rtnClass.getSuperclass().equals(BeanMao.class)){
							Method sMethod = rtnClass.getMethod("load", new Class[] {Class.class,Integer.class});
							Object sObject = sMethod.invoke(null, new Object[]{rtnClass,rs.getInt(c)});
							setMethod.invoke(object,sObject );
						}else{
							//								System.out.println(m.getName()+":"+rtnClass.getSimpleName()+":"+dbObject);
							switch (rtnClass.getSimpleName()) {
							case "byte":
								setMethod.invoke(object, rs.getByte(c));
								break;
							case "int":
								setMethod.invoke(object, rs.getInt(c));
								break;
							case "char":
								setMethod.invoke(object, (char)dbObject);
								break;
							case "long":
								setMethod.invoke(object, rs.getLong(c));
								break;
							case "float":
								setMethod.invoke(object, rs.getFloat(c));
								break;
							case "boolean":
								setMethod.invoke(object, rs.getInt(c)==1);
								break;

							case "double":
								setMethod.invoke(object,rs.getDouble(c));
								break;

							case "short":
								setMethod.invoke(object,rs.getShort(c));
								break;

							default:
								setMethod.invoke(object, dbObject);
								break;
							}
						}
					}
				}
			}
		}
		return cls.cast(object);
	}
	public static <T> T load(Class<T> cls,Integer id) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String sql="select * from \""+cls.getSimpleName()+"\" where id="+id;
		return load(cls, sql);
	}
	public static  <T> T load(Class <T> cls,String sql) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		try(Statement st=SessionData.getConnection().createStatement();
				ResultSet rs=st.executeQuery(sql);
				){
			if(rs.next()){
				return createObject(rs, cls);
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public boolean equals(Object obj) {
		if(this.getId()==0) return super.equals(obj);
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanMao other = (BeanMao) obj;
		return other.getId()==this.getId();
	}
}
