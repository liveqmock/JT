package com.mao.jf.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	public List<?> queryList(String queryString,Class<?> class1,Object... objects ) {
		Query query = em.createQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		return query.getResultList();
	}
	public List<?> queryNativeList(String queryString,Class<?> class1,Object... objects ) {
		Query query =class1==null?em.createNativeQuery( queryString ): em.createNativeQuery( queryString ,class1);
		if(objects!=null){
			for(int i=0;i<objects.length;i++)
				query.setParameter(i+1, objects[i]);
		}
		return query.getResultList();
	}

	public Object querySingle(String queryString,Class<?> class1,Object... objects ) {
		Query query = em.createQuery( queryString ,class1);
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
			em.getTransaction().rollback();
			}catch(Exception e1){
				e.printStackTrace();
				
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
			em.getTransaction().rollback();
			}catch(Exception e1){
				e.printStackTrace();
			}
		}
		
	}




	public void refresh(Object entity) {
		em.refresh(entity);
		
	}




	


}
