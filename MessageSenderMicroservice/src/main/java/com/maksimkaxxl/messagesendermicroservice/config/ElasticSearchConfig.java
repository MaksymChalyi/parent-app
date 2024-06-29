package com.maksimkaxxl.messagesendermicroservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@Slf4j
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.address}")
    private String esAddress;

    @Override
    public ClientConfiguration clientConfiguration() {
        log.info("Configuring Elasticsearch client to connect to {}", esAddress);

        ClientConfiguration clientConfig = ClientConfiguration.builder()
                .connectedTo(esAddress)
                .build();

        log.info("Elasticsearch client configuration completed successfully");

        return clientConfig;
    }
}




