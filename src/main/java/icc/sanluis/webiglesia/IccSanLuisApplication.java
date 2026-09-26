package icc.sanluis.webiglesia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IccSanLuisApplication {

	public static void main(String[] args) {
		SpringApplication.run(IccSanLuisApplication.class, args);
	}
}
