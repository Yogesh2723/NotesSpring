package cat.tecnocampus.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Created by josep on 6/2/17.
 */
@Configuration
public class GitHubConfiguration {

    @Bean
    @ConfigurationProperties("gitHub.client")
    public AuthorizationCodeResourceDetails gitHub() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("gitHub.resource")
    public ResourceServerProperties gitHubResource() {
        return new ResourceServerProperties();
    }

}
