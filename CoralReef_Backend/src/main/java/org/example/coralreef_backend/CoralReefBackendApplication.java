package org.example.coralreef_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("org.example.coralreef_backend.mapper")
public class CoralReefBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoralReefBackendApplication.class, args);
	}

}
