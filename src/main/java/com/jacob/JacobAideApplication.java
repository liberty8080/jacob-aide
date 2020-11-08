package com.jacob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.jacob.common.mapper")
public class JacobAideApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(JacobAideApplication.class, args);
	}

}
