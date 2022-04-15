package com.bootcamp.reactive.pago_de_servicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PagoDeServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagoDeServiciosApplication.class, args);
	}

}
