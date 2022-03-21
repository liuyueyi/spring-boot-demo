package com.git.hui.boot.search.es;

import lombok.Getter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

@Getter
@Configuration
public class ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.connTimeout}")
    private int connTimeout;

    @Value("${elasticsearch.socketTimeout}")
    private int socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    @Value("${elasticsearch.user}")
    private String user;

    @Value("${elasticsearch.pwd}")
    private String pwd;

    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient initRestClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(connTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout));
        return new RestHighLevelClient(builder);
    }

    @Bean
    public RequestOptionsFactoryBean requestOptions(ElasticsearchConfiguration elasticsearchConfiguration) {
        return new RequestOptionsFactoryBean(elasticsearchConfiguration.user, elasticsearchConfiguration.pwd);
    }

    public class RequestOptionsFactoryBean implements FactoryBean<RequestOptions> {
        private String user, pwd;

        public RequestOptionsFactoryBean(String user, String pwd) {
            this.user = user;
            this.pwd = pwd;
        }

        @Override
        public RequestOptions getObject() {
            String auth = "Basic " + Base64Utils.encodeToString((user + ":" + pwd).getBytes());
            RequestOptions.Builder build = RequestOptions.DEFAULT.toBuilder();
            build.addHeader("Authorization", auth);
            return build.build();
        }

        @Override
        public Class<?> getObjectType() {
            return RequestOptions.class;
        }
    }
}