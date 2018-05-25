package boot1.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.beetl.sql.core.SQLManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import boot1.beetlsql.SqlKit;

public class ActiveRecordListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ActiveRecordListener 监听器初始化...");
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		SqlKit.setDao(context.getBean(SQLManager.class));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
