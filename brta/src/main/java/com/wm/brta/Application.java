package com.wm.brta;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.wm.brta.security.DriverAuthenticationFilter;
import com.wm.brta.security.SupplierAuthenticationFilter;
import com.wm.brta.security.XSSFilter;

@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer{
    
	private @Autowired
	AutowireCapableBeanFactory beanFactory;

	
	public Application(){
		super();
		setRegisterErrorPageFilter(false);
	}
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    protected SpringApplicationBuilder configure(

            SpringApplicationBuilder application) {

        return application.sources(Application.class);
} 
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
    @Bean
    public FilterRegistrationBean driverAuthenticationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter myFilter = new DriverAuthenticationFilter();
        beanFactory.autowireBean(myFilter);
        registration.setFilter(myFilter);
        registration.addUrlPatterns("/app/service/*");
        return registration;
    }
    
//    @Bean
//    public FilterRegistrationBean supplierAuthenticationFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        Filter myFilter = new SupplierAuthenticationFilter();
//        beanFactory.autowireBean(myFilter);
//        registration.setFilter(myFilter);
//        registration.addUrlPatterns("/app/supplier/*");
//        return registration;
//    }
    
    @Bean
    public FilterRegistrationBean supplierUiAuthenticationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter myFilter = new SupplierAuthenticationFilter();
        beanFactory.autowireBean(myFilter);
        registration.setFilter(myFilter);
        registration.addUrlPatterns("/app/ui/*");
        return registration;
    }
    
    @Bean
    public FilterRegistrationBean xssValidationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter myFilter = new XSSFilter();
        beanFactory.autowireBean(myFilter);
        registration.setFilter(myFilter);
        registration.addUrlPatterns("/app/*");
        return registration;
    }
    
}