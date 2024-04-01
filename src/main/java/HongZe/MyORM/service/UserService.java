package HongZe.MyORM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import HongZe.MyORM.entity.User;
import HongZe.MyORM.orm.MyTemplate;

@Component
@Transactional
public class UserService {
	@Autowired
	MyTemplate myTemplate;

	public User getUserById(long id) {
		return myTemplate.get(User.class, id);
	}

	public User fetchUserByEmail(String email) {
		return myTemplate.from(User.class).where("email = ?", email).first();
	}

	public User getUserByEmail(String email) {
		return myTemplate.from(User.class).where("email = ?", email).unique();
	}

	public String getNameByEmail(String email) {
		return myTemplate.select("name").from(User.class).where("email = ?", email).unique().getName();
	}

	public List<User> getUsers(int pageIndex) {
		int pageSize = 100;
		return myTemplate.from(User.class).limit(pageSize * (pageIndex - 1), pageSize * pageIndex).list();
	}

	public User login(String email, String password) {
		User user = getUserByEmail(email);
		if (user == null || !user.getPassword().equals(password)) {
			throw new RuntimeException("login failed!");
		}
		return user;
	}

	public User register(String email, String password, String name) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		user.setCreatedAt(System.currentTimeMillis());
		myTemplate.insert(user);
		return user;
	}

	public void updateUser(Long id, String name) {
		User user = getUserById(id);
		user.setName(name);
		user.setCreatedAt(System.currentTimeMillis());
		myTemplate.update(user);
	}

	public void deleteUser(Long id) {
		myTemplate.delete(User.class, id);
	}

	public void deleteUserByEmail(String email) {
		long id = myTemplate.from(User.class).where("email = ?", email).unique().getId();
		deleteUser(id);
	}
}
