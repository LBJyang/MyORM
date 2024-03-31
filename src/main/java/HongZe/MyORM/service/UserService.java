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
		return myTemplate.from(User.class).where("email = ", email).fisrt();
	}

	public User getUserByEmail(String email) {
		return myTemplate.from(User.class).where("email = ", email).unique();
	}

	public String getNameByEmail(String email) {
	}

	public List<User> getUsers(int pageIndex) {
	}

	public User login(String email, String password) {
	}

	public User register(String email, String password, String name) {
	}

	public void updateUser(Long id, String name) {
	}

	public void deleteUser(Long id) {
	}
}
