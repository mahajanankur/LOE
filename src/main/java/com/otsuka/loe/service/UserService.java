package com.otsuka.loe.service;

import com.otsuka.loe.model.Users;

public interface UserService {

	public String checkLogin(String userName, String password);

	public void saveUserData(Users user);

	public Users getUserById(int id);

	public Users getUserByEmail(String email);

	public void deleteUser(int id);

	public String getEmail(String name);
}
