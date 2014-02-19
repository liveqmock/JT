package com.mao.jf.beans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
			MyLogger.error("����ʧ��",e);
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
			
			MyLogger.error("����ʧ��",e);
		}
	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass ).getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass,String whereString,Object[] objects) {
		
		 TypedQuery<T> query = em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass );
		 if(objects!=null){
			 for(int i=0;i<objects.length;i++)
				 query.setParameter(i, objects[i]);
		 }
		return query.getResultList();

	}
	public <T> List<T> getBeans(Class<T> beanClass) {

		return em.createQuery( "FROM "+beanClass.getSimpleName() ,beanClass ).getResultList();

	}
	public <T> T getBean(Class<T> beanClass,String whereString,Object[] objects) {
		
		 TypedQuery<T> query = em.createQuery( "FROM "+beanClass.getSimpleName() +" as a WHERE "+whereString,beanClass );
		 if(objects!=null){
			 for(int i=0;i<objects.length;i++)
				 query.setParameter(i, objects[i]);
		 }
		return query.getSingleResult();

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
		// TODO �Զ����ɵķ������
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
