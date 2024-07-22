package com.example.Hotel.config.AwsSecretManagerConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import org.json.JSONObject;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        String secretName = "test/sql";
        Region region = Region.of("us-east-1");


        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();


        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;
        String secret = null;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            secret = getSecretValueResponse.secretString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao recuperar segredo", e);
        }

        // Recebe o segredo do json
        JSONObject secretJson = new JSONObject(secret);
        String username = secretJson.getString("username");
        String password = secretJson.getString("password");
        String host = secretJson.getString("host");
        int port = secretJson.getInt("port");
        String dbInstanceIdentifier = secretJson.getString("dbInstanceIdentifier");


        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbInstanceIdentifier);


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}

