package boot1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//测试读取配置
@Configuration
public class ConfigTest {
	@Value("${ds.userName}")
	private String userName;
	@Autowired
	private Environment environment;

	public void show() {
		System.out.println("datasource.userName:" + this.userName);
		System.out.println("datasource.password:" + this.environment.getProperty("ds.password"));
	}
}
