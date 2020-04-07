package ru.iac.ASGIHDTORIS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AsGihdTorisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsGihdTorisApplication.class, args);
	}

}
