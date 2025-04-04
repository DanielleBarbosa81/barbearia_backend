package br.com.barbearia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Barbearia API", version = "1.0"))
@SpringBootApplication
public class BarbeariaDioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbeariaDioApplication.class, args);
	}
}