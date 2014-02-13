package com.mao.jf.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class BeanManager {

	private EntityManager em;
	private EntityManagerFactory emf;

	public BeanManager() {
		emf = Persistence.createEntityManagerFactory( "jt-win-sqlserver" );

		em = emf.createEntityManager();
	}

	public void saveBean(Object bean) {
		try{
			em.getTransaction().begin();
			em.persist(bean);
			em.getTransaction().commit();;
		}catch(Exception e){
			e.printStackTrace();
			MyLogger.error("保存失败",e);
		}
	}
	public void removeBean(Object bean) {
		try{
			em.getTransaction().begin();
			em.remove(bean);
			em.getTransaction().commit();
			em.detach(bean);
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
			MyLogger.error("保存失败",e);
		}
	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString,Object[] objects) {
		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() ,beanClass ).getResultList();

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

	public <T> T find(Class<T> beanClass,Object id) {
		return em.find(beanClass,id);

	}

	public <T> T  getBean(Class<T> beanClass,String whereString) throws NoResultException  {
		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getSingleResult();

	}



}
