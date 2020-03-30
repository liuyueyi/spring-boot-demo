package com.git.hui.boot.solr.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.*;
import org.apache.http.client.protocol.RequestExpectContinue;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.protocol.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by @author yihui in 19:49 19/5/10.
 */
@Configuration
public class SearchAutoConfig {

    @Value("${spring.data.solr.host}")
    private String url;

    @Data
    public static class UrlDo {
        private String url;

        private String user;
        private String pwd;

        private String host;
        private int port;

        public static UrlDo parse(String url) throws MalformedURLException {
            // http://root:123@127.0.0.1:8983/solr
            URL u = new URL(url);
            UrlDo out = new UrlDo();
            out.setHost(u.getHost());
            out.setPort(u.getPort());

            String userInfo = u.getUserInfo();
            if (!StringUtils.isEmpty(userInfo)) {
                String[] users = org.apache.commons.lang3.StringUtils.split(userInfo, ":");
                out.setUser(users[0]);
                out.setPwd(users[1]);
            }
            out.setUrl(url);
            return out;
        }
    }

    public class SolrAuthInterceptor implements HttpRequestInterceptor {
        @Override
        public void process(final HttpRequest request, final HttpContext context) {
            AuthState authState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
            if (authState.getAuthScheme() == null) {
                CredentialsProvider credsProvider =
                        (CredentialsProvider) context.getAttribute(HttpClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
                AuthScope authScope = new AuthScope(targetHost.getHostName(), targetHost.getPort());
                Credentials creds = credsProvider.getCredentials(authScope);
                authState.update(new BasicScheme(), creds);
            }
        }
    }

//    @Bean
//    public HttpSolrClient solrClient() throws MalformedURLException {
//        UrlDo urlDo = UrlDo.parse(url);
//        CredentialsProvider provider = new BasicCredentialsProvider();
//        provider.setCredentials(new AuthScope(urlDo.getHost(), urlDo.getPort()),
//                new UsernamePasswordCredentials(urlDo.getUser(), urlDo.getPwd()));
//
//        HttpClientBuilder builder = HttpClientBuilder.create();
//        builder.addInterceptorFirst(new SolrAuthInterceptor());
//        builder.setDefaultCredentialsProvider(provider);
//        CloseableHttpClient httpClient = builder.build();
//        return new HttpSolrClient.Builder(url).withHttpClient(httpClient).build();
//    }


    @Bean
    public HttpSolrClient solrClient() {
        HttpClient httpClient = new SystemDefaultHttpClient();
        return new HttpSolrClient.Builder(url).withHttpClient(httpClient).build();
    }

    @Bean
    @ConditionalOnMissingBean(SolrTemplate.class)
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }

}
