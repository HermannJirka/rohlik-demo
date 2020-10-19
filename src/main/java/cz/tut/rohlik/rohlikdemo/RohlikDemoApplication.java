package cz.tut.rohlik.rohlikdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RohlikDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RohlikDemoApplication.class, args);
	}

}
