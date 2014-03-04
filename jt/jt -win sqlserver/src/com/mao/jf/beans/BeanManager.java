package com.mao.jf.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class BeanManager {

	private EntityManager em;
	private EntityManagerFactory emf;

	public BeanManager() {
		emf = Persistence.createEntityManagerFactory( "jt-win-sqlserver" );

		em = emf.createEntityManager();
	}


	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void close() {
		em.close();
		emf.close();

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
			em.getTransaction().rollback();
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
		em.remove(em.merge(object));
	}


	public void saveBean(Object bean) {
		em.persist(bean);
		
	}



}
