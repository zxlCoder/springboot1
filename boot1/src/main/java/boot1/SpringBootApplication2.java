package boot1;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import boot1.config.ConfigTest;
import boot1.model.DataSourceProperties;

//@SpringBootApplication
public class SpringBootApplication2 {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication2.class, args);
		context.getBean(ConfigTest.class).show();
		context.getBean(DataSourceProperties.class).show();
	}
}
