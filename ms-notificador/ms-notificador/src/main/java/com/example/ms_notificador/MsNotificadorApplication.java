package com.example.ms_notificador;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsNotificadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificadorApplication.class, args);
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		final String awsAccessKey = dotenv.get("AWS_ACCESS_KEY");
		final String awsSecretKey = dotenv.get("AWS_SECRET_KEY");

		System.out.println("AWS_ACCESS_KEY: " + awsAccessKey);
		System.out.println("AWS_SECRET_KEY: " + awsSecretKey);
	}

}
