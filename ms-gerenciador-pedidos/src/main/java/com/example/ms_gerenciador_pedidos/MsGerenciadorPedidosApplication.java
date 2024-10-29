package com.example.ms_gerenciador_pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsGerenciadorPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGerenciadorPedidosApplication.class, args);
	}

}
