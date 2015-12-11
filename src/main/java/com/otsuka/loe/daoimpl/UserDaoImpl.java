package com.otsuka.loe.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.otsuka.loe.dao.UserDao;
import com.otsuka.loe.model.Users;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@SuppressWarnings("unchecked")
	public List<Users> checkUserLogin(String userName, String password) {
		// TODO Auto-generated method stub
		String SQL_QUERY = "select u   from Users u where u.uname = :uname and u.password = :password";
		Query query = getSession().createQuery(SQL_QUERY);
		query.setParameter("uname", userName);
		query.setParameter("password", password);
		List<Users> userList = query.list();
         return userList;
	}

	public void saveUserData(Users user) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(user);
	}

	public Users getUserById(int id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("id",id));
        return (Users) criteria.uniqueResult();
	}

	public Users getUserByEmail(String email) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("email",email));
        return (Users) criteria.uniqueResult();
	}

	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery("delete from Users u where u.id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
	}

	public String getEmail(String name) {
		// TODO Auto-generated method stub
		
		String SQL_QUERY = "select u.email   from Users u where u.uname = :uname";
		Query query = getSession().createQuery(SQL_QUERY);
		query.setParameter("uname", name);
		List<String> userList = query.list();
         return userList.get(0);
	}

}
