package com.otsuka.loe.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otsuka.loe.dao.UserDao;
import com.otsuka.loe.model.Users;
import com.otsuka.loe.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	public String checkLogin(String userName, String password) {
		// TODO Auto-generated method stub
		String uname = null;
		String pass = null;
		String role = null;
		List<Users> users = userDao.checkUserLogin(userName, password);
		for (Users userList : users) {

			uname = userList.getUname();
			pass = userList.getPassword();
			role = userList.getRole();

		}

		if (userName.equals(uname) && password.equals(pass)
				&& role.equals("Admin")) {
			return "Admin";
		}

		else if(userName.equals(uname) && password.equals(pass)
				&& role.equals("General User")){
			return "General User";
		}
		
		else 
			return "user not found";
	}

	public void saveUserData(Users user) {
		// TODO Auto-generated method stub
		userDao.saveUserData(user);
	}

	public Users getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	public Users getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.getUserByEmail(email);
	}

	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		userDao.deleteUser(id);
	}

	public String getEmail(String name) {
		// TODO Auto-generated method stub
		return userDao.getEmail(name);
	}

}
