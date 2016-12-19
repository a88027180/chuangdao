package com.xiyoukeji.tools;

import java.io.Serializable;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository("baseDao")
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取当前Session对象
	 * 
	 * @return
	 */
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存对象
	 */

	public void save(T entity) {
		this.getCurrentSession().save(entity);
	}

	/**
	 * 删除对象
	 */

	public void delete(T entity) {
		this.getCurrentSession().delete(entity);
	}

	/**
	 * 更新
	 */

	public void update(T entity) {
		this.getCurrentSession().update(entity);
	}

	/**
	 * 合并
	 */

	public void merge(T entity) {
		this.getCurrentSession().merge(entity);
	}

	/**
	 * 保存或更新
	 */

	public void saveOrUpdate(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 根据ID加载对象
	 */
	@SuppressWarnings("unchecked")
	public T load(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().load(c, id);
	}

	/**
	 * 根据ID获取对象
	 */
	@SuppressWarnings("unchecked")
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	/**
	 * 根据HQL语句获取对象
	 */
	public T get(String hql) {
		return this.get(hql, null);
	}

	/**
	 * 根据HQL语句和参数获取对象
	 */
	public T get(String hql, Map<String, Object> params) {
		return this.get(hql,0,params);
	}

	public T get(String hql,int position,Map<String, Object> params){
        List<T> list = this.find(hql,position,1,params);
        if (list != null && list.size() > 0) {
            // 返回第一个对象
            return list.get(0);
        }
        return null;
    }

    public T get(String hql,int position){
        return this.get(hql,position,null);
    }

	/**
	 * 根据HQL查询结果集
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql) {
		return find(hql, null);
	}

	/**
	 * 查询对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> params) {
		// 创建Query
		Query query = this.getCurrentSession().createQuery(hql);

		// 如果参数非空
		if (params != null && params.size() > 0) {
			// 设置参数
			this.setParameters(query, params);
		}
		// 返回查询结果
		List<T> list=query.list();
		if(list==null)list=new ArrayList<T>();
		return list;
	}

	/**
	 * 分页的结果集
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, int start, int rows,
			Map<String, Object> params) {

		// 创建Query
		Query query = this.getCurrentSession().createQuery(hql);

		// 如果参数非空
		if (params != null && params.size() > 0) {
			// 设置参数
			this.setParameters(query, params);
		}

        if(rows<=0)return query.list();
        if(start<0)start=0;
		// 查询结果并返回
        List<T> list=query.setFirstResult(start).setMaxResults(rows)
                .list();
        if(list==null)list=new ArrayList<T>();
        return list;
	}

	/**
	 * 无参数的 Select count(*) from
	 */
	public Long count(String hql) {
		// 通过另一个方法实现^_^
		return this.count(hql, null);
	}

	/**
	 * 带参数的 Select count(*) from
	 */
	public Long count(String hql, Map<String, Object> params) {

		// 给传递过来的hql添加前缀内容
		hql = "select count(*) " + hql;

		// 创建Query
		Query query = this.getCurrentSession().createQuery(hql);

		// 如果参数非空
		if (params != null && params.size() > 0) {
			// 设置参数
			this.setParameters(query, params);
		}

		// 返回查询结果
		return (Long) query.uniqueResult();
	}

	/**
	 * 执行HQL
	 */
	public Integer executeHql(String hql) {
		// 通过另一个方法实现^_^
		return this.executeHql(hql, null);
	}

	/**
	 * 执行HQL
	 */
	public Integer executeHql(String hql, Map<String, Object> params) {

		// 创建Query
		Query query = this.getCurrentSession().createQuery(hql);

		// 如果参数不为null
		if (params != null && params.size() > 0) {
			// 设置参数
			this.setParameters(query, params);
		}

		// 执行HQL
		return query.executeUpdate();
	}

	/**
	 * 给Query赋值的方法
	 * 
	 * @param query
	 *            Hibernate Query对象
	 * @param params
	 *            Map集合参数
	 */
	private void setParameters(Query query, Map<String, Object> params) {

		// 获取所有Key
		Set<String> keys = params.keySet();

		// 循环取值,赋值
		for (String key : keys) {

			// 获取到参数中的对象
			Object obj = params.get(key);

			// 判断参数类型
			if (obj instanceof Collection<?>) {// 是集合
				// System.out.println("参数为集合的大小为:" + ((Collection<?>)
				// obj).size());
				query.setParameterList(key, (Collection<?>) obj);
			} else if (obj instanceof Object[]) {// 是数组
				// System.out.println("参数为数姐的大小为:" + ((Object[]) obj).length);
				query.setParameterList(key, (Object[]) obj);
			} else {// 普通对象
				// System.out.println("普通对象");
				query.setParameter(key, obj);
			}
		}
	}

	public void clear(){
		Session session=this.getCurrentSession();
		session.flush();
		session.clear();
	}
}
