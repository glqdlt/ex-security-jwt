package com.glqdlt.exsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


//    3








    //    @Value("${symmetric.key}")
    private String privateKey = "123";

    @Autowired
    AuthenticationManager authenticationManager;

    public OAuth2AuthorizationServerConfig() {
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).accessTokenConverter(jwtAccessTokenConverter()).authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(this.privateKey);
        return tokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


//    https://stackoverflow.com/questions/38165131/spring-security-oauth2-accept-json
//    https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html
    // 아래 방식은 Authorization 헤더에 foo : bar / id: password 를 넣어서 바로 보내는 식이다.
//     curl foo:bar@localhost:8080/oauth/token -d grant_type=password -d username=admin -d password=admin1234
//    -d 는 --date , -u 는 --user를 뜻한다.
    // curl -u foo:bar localhost:8080/oauth/token -d grant_type=password -d username=admin -d password=admin1234

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//         아래는 inMemory에 인증 Client를 직접 등록해놓은 것.
        clients.inMemory()
                .withClient("foo")
                .secret("bar")
                .authorizedGrantTypes("password")
                .scopes("read", "write", "both");
    }


}
