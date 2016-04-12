package wang.laic.chaos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ImportResource (value={"spring-mybatis.xml","shiro-bean.xml" })
//@ImportResource({
//    "classpath:/config/spring-web-servlet.xml", 
//    "classpath:/config/database-config.xml"
//})
@EnableTransactionManagement
public class Application {

	@Bean
	public ApplicationListener<ContextRefreshedEvent> onContextRefeshed() {
		return new ApplicationListener<ContextRefreshedEvent>() {
			@Override
			public void onApplicationEvent(ContextRefreshedEvent event) {
				log.debug("Initializating application...");
			}
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
