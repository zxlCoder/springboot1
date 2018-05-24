package boot1.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ds")
@Data
public class DataSourceProperties {
	private String url;
	private String driverClassName;
	private String userName;
	private String password;

	public void show() {
		System.out.println("ds.url=" + this.url);
		System.out.println("ds.driverClassName=" + this.driverClassName);
		System.out.println("ds.userName=" + this.userName);
		System.out.println("ds.password=" + this.password);
	}
}
