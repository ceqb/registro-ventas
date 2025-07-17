package com.VentaCliente;

import com.VentaCliente.model.CorreoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(CorreoProperties.class)
public class VentaClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentaClienteApplication.class, args);
	}

}
