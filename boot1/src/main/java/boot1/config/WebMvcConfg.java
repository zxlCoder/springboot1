package boot1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import boot1.interceptor.TimeInterceptor;

//配置拦截器
@Configuration
public class WebMvcConfg implements WebMvcConfigurer{
//public class WebMvcConfg extends WebMvcConfigurerAdapter{ 废弃了
    
    @Autowired
    private TimeInterceptor timeInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

}
