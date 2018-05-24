package boot1.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	/*@Bean(name = "datasource")
	  public DataSource datasource(Environment env) {
	    HikariDataSource ds = new HikariDataSource();
	    ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
	    ds.setUsername(env.getProperty("spring.datasource.username"));
	    ds.setPassword(env.getProperty("spring.datasource.password"));
	    ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	    return ds;
	}*/

}
