package boot1;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import boot1.filter.TimeFilter;
import boot1.listener.ListenerTest;
import boot1.servlet.ServletTest;

//针对自定义 Servlet、Filter 和 Listener 的另一种配置
@SpringBootApplication
public class SpringBootDemo1Application {
	
	/*//public class SpringBootDemo1Application implements ServletContextInitializer{
	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 配置 Servlet
        servletContext.addServlet("servletTest",new ServletTest())
                      .addMapping("/servletTest");
        // 配置过滤器
        servletContext.addFilter("timeFilter",new TimeFilter())
                      .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        // 配置监听器
        servletContext.addListener(new ListenerTest());
    }*/
	
	public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo1Application.class, args);
    }
}
