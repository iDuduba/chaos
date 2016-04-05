package wang.laic.chaos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ImportResource (value={"spring-mybatis.xml","shiro-bean.xml" })
//@ImportResource({
//    "classpath:/config/spring-web-servlet.xml", 
//    "classpath:/config/database-config.xml"
//})
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
