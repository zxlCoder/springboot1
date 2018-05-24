package boot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//针对自定义 Servlet、Filter 和 Listener 的另一种配置
@SpringBootApplication
public class SpringBootApplication1 {
	
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
        SpringApplication.run(SpringBootApplication1.class, args);
    }

}
