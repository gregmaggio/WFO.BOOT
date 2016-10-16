/**
 * 
 */
package ca.datamagic.wfo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Greg
 *
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan({"ca.datamagic.wfo.controller"})
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		registry
			.addMapping("/**")
			.allowedOrigins("*")
			.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Location")
			.allowedMethods("POST", "GET", "PUT", "DELETE")
			.maxAge(1728000L);
	}
	
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry
    		.addResourceHandler("/swagger-ui.html")
        	.addResourceLocations("classpath:/META-INF/resources/");
    	registry
    		.addResourceHandler("/webjars/**")
        	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        	.apiInfo(apiInfo())
			.select()                                 
			.apis(RequestHandlerSelectors.basePackage("ca.datamagic.wfo.controller"))
			.paths(PathSelectors.any())                          
			.build();
    }
    
    @Bean
    public UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl");
	}
    
    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("WFO Service")
			.description("Weather Forecast Office API")
			.license("Apache License Version 2.0")
			.version("1.0")
			.build();
    }
}
