package boot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

//针对自定义 Servlet、Filter 和 Listener 的另一种配置
@EnableCaching  //开启缓存功能
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
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplication1.class, args);
		// SqlKit.setDao(context.getBean(SQLManager.class)); 使用监听器代替
		System.out.println("----------启动成功----------");
    }

}
