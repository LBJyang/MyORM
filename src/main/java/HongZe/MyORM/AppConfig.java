package HongZe.MyORM;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import HongZe.MyORM.entity.User;
import HongZe.MyORM.orm.MyTemplate;
import HongZe.MyORM.service.UserService;

/**
 * Main Class,do the config job,give the transactional job to Hibernate. Config
 * the MyTemplate,a wrapped JdbcTemplate.
 * 
 * @author Yang
 */
@ComponentScan
@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class AppConfig {

	/**
	 * the main block,also test block.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService userService = context.getBean(UserService.class);
		// test the register
		System.out.println("==========REGISTER TEST BEGIN==========");
		if (userService.fetchUserByEmail("yangjiaze@dmail.com") == null) {
			User jiaze = userService.register("yangjiaze@dmail.com", "jiaze", "jiaze");
			System.out.println("Registered OK:" + jiaze);
		}
		if (userService.fetchUserByEmail("yangjiahong@dmail.com") == null) {
			User jiahong = userService.register("yangjiahong@dmail.com", "jiahong", "jiahong");
			System.out.println("Registered OK:" + jiahong);
		}
		if (userService.fetchUserByEmail("yangjiali@dmail.com") == null) {
			User jiali = userService.register("yangjiali@dmail.com", "jiali", "jiali");
			System.out.println("Registered OK:" + jiali);
		}
		System.out.println("==========REGISTER TEST END==========");
		// test query
		System.out.println("==========QUERY TEST BEGIN==========");
		for (User user : userService.getUsers(1)) {
			System.out.println(user);
		}
		System.out.println("==========QUERY TEST END==========");
		// update test
		System.out.println("==========UPDATE TEST BEGIN==========");
		User jiali = userService.fetchUserByEmail("yangjiali@dmail.com");
		userService.updateUser(jiali.getId(), "zhuxixiaoxia");
		for (User user : userService.getUsers(1)) {
			System.out.println(user);
		}
		System.out.println("==========UPDATE TEST END==========");
		// test login
		System.out.println("==========LOGIN TEST BEGIN==========");
		User jiaze = userService.login("yangjiaze@dmail.com", "jiaze");
		System.out.println("LOGIN USER:" + jiaze);
		System.out.println("==========LOGIN TEST END==========");

		// test delete
		System.out.println("==========DELETE TEST BEGIN==========");
		userService.deleteUserByEmail("yangjiali@dmail.com");
		System.out.println("yangjiali@dmail.com has been deleted!");
		for (User user : userService.getUsers(1)) {
			System.out.println(user);
		}
		System.out.println("==========DELETE TEST END==========");
		((AbstractApplicationContext) context).close();
	}

	@Bean
	DataSource dataSource(@Value("${jdbc.url}") String jdbcUrl, @Value("${jdbc.username}") String username,
			@Value("${jdbc.password}") String password) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.addDataSourceProperty("autoCommit", "false");
		config.addDataSourceProperty("connectionTimeout", "5");
		config.addDataSourceProperty("idleTimeout", "60");
		return new HikariDataSource(config);
	}

	@Bean
	JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	MyTemplate createMyTemplate(@Autowired JdbcTemplate jdbcTemplate) {
		return new MyTemplate(jdbcTemplate, "HongZe.MyORM.entity");
	}

	@Bean
	PlatformTransactionManager ctreateTransactionManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
