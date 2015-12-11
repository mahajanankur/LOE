package com.otsuka.loe.dao;

import java.util.List;

import com.otsuka.loe.model.Users;

public interface UserDao {

	public abstract List<Users> checkUserLogin(String userName, String password);
    public abstract void saveUserData(Users user);
    public Users getUserById(int id);
    public Users getUserByEmail(String email);
    public abstract void deleteUser(int id);
    public String getEmail(String name);
		
	}

