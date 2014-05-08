package com.mao.jf.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.mao.jf.beans.annotation.Caption;
import com.mao.jf.beans.annotation.Hidden;

public class BeanManager {

	private EntityManager em;
	private EntityManagerFactory emf;

	public BeanManager() {
		emf = Persistence.createEntityManagerFactory( "jt-win-sqlserver" );

		em = emf.createEntityManager();
	}




	public void close() {
		em.close();
		emf.close();

	}
	public List<?> queryList(String queryString,Object... objects ) {
		return queryList(queryString, null,objects);
	}
	public <T> List<T> queryList(String queryString,Class<T> class1,Object... objects ) {
		Query query =class1==null? em.createQuery( queryString ): em.createQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		return query.getResultList();
	}
	public <T> List<T> queryNativeList(String queryString,Class<T> class1,Object... objects ) {
		Query query =class1==null?em.createNativeQuery( queryString ): em.createNativeQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		return query.getResultList();
	}

	public List<?> queryNativeList(String queryString,Object... objects ) {
		return queryNativeList(queryString,null,objects);
	}
	public Object querySingle(String queryString,Object... objects ) {
		Query query = em.createQuery( queryString );
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		try{
			return query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}catch (NonUniqueResultException e) {
			return query.getResultList().get(0);
		}
	}
	public <T> T querySingle(String queryString,Class<T> class1,Object... objects ) {
		TypedQuery<T> query = em.createQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		try{
			return query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}catch (NonUniqueResultException e) {
			return query.getResultList().get(0);
		}
	}
	public Object queryNativeSingle(String queryString,Object... objects ) {
		
		return queryNativeSingle(queryString,null,objects);
	}
	public Object queryNativeSingle(String queryString,Class<?> class1,Object... objects ) {
		
		Query query =class1==null?em.createNativeQuery( queryString ): em.createNativeQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		try{
			return query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}catch (NonUniqueResultException e) {
			return query.getResultList().get(0);
		}
	}
	public void executeUpdate(String queryString,Object... objects ) {
		Query query = em.createQuery( queryString );
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		try{
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}catch(Exception e){
			try{
			em.getTransaction().rollback();
			}catch(Exception e1){

				e.printStackTrace();
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO 自动生成的方法存根
		super.finalize();
		close();
	}
	public void flush() {
		try{
			em.getTransaction().begin();
			em.flush();
			em.getTransaction().commit();
		}catch(Exception e){
			try{
			em.getTransaction().rollback();
			}catch(Exception e1){

				e.printStackTrace();
			}
		}

	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString,Object... objects) {

		TypedQuery<T> query = em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass );
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		return query.getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() ,beanClass ).getResultList();

	}
	public <T> T getBean(Class<T> beanClass,String whereString,Object... objects) {

		TypedQuery<T> query = em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass );
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		try{
			return query.getSingleResult();
		}catch(NoResultException e){
			return null;
		}catch (NonUniqueResultException e) {
			return query.getResultList().get(0);
		}

	}
	public <T> T find(Class<T> beanClass,Object id) {
		return em.find(beanClass,id);

	}

	public <T> T  getBean(Class<T> beanClass,String whereString) throws NoResultException  {
		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getSingleResult();

	}


	public void removeBean(Object object) {
		try{
			em.getTransaction().begin();
			em.remove(object);
			em.getTransaction().commit();
		}catch(Exception e){
			try{
			em.getTransaction().commit();
			e.printStackTrace();
			}catch(Exception e1){
				try{
					em.getTransaction().rollback();
					}catch(Exception e2){
						e2.printStackTrace();
						
					}
				
			}
		}
	}


	public void saveBean(Object bean) {
		try{
			em.getTransaction().begin();
			em.persist(bean);;
			em.getTransaction().commit();
		}catch(Exception e){
			try{			
				em.getTransaction().commit();
			}catch(Exception e1){try{			
				em.getTransaction().rollback();
			}catch(Exception e2){
				e2.printStackTrace();
			}
			}
		}
		
	}




	public void refresh(Object entity) {
		em.refresh(entity);
		
	}





	public static String getCaption(Class<?> class1) {
		String caption="\"";
		for(Field fld:class1.getDeclaredFields()){
			if(fld.getAnnotation(Hidden.class)==null){
				Caption captionAn =fld.getAnnotation(Caption.class);
				if(captionAn!=null){
					caption+=captionAn.value()+ "\",\"";
				}


			}
		}

		for(Method method:class1.getDeclaredMethods()){
			Caption captionAn =method.getAnnotation(Caption.class);
			String name=method.getName();
			if(captionAn!=null&&name.startsWith("get")){
				caption+=captionAn.value()+ "\",\"";
			}
		}
		return caption;
	}

	public static void main(String a[]) {
		System.err.println(getCaption(PicBean.class));
	}
	


}
