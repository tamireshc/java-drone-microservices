package com.example.ms_notificador.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AmazonSnsConfiguracao {
    // Configuração do Amazon SNS

    // Carregar o Dotenv em um bloco estático
    private static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    private final String awsAccessKey = dotenv.get("AWS_ACCESS_KEY");
    private final String awsSecretKey = dotenv.get("AWS_SECRET_KEY");


    @Bean
    public AWSCredentials awsCredentials() {
        System.out.println("AWS_ACCESS_KEYI: " + awsAccessKey);
        System.out.println("AWS_SECRET_KEYI: " + awsSecretKey);
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(Regions.US_EAST_1).build();
    }
}