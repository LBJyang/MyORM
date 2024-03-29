package HongZe.MyORM;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import HongZe.MyORM.orm.MyTemplate;

@ComponentScan
@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class AppConfig {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
