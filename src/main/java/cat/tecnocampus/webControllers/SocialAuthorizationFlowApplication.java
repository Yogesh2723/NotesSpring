package cat.tecnocampus.webControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;

@RestController
@EnableOAuth2Client
public class SocialAuthorizationFlowApplication {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Autowired
	private AuthorizationCodeResourceDetails gitHub;

	private String url_GET_repositories = "https://api.github.com/user/repos";
	private HashMap<String,OAuth2AccessToken> tokens = new HashMap<>();

	@GetMapping("/repositories/{name}")
	public ResponseEntity<String> getRepositories(@PathVariable String name) { //note that "name" is only used to store tokens

		ResponseEntity<String> response;

		if (!tokens.containsKey(name)) {
			OAuth2RestTemplate gitHubTemplate = gitHubRestTemplate();
			response = queryWithOauth2RestTemplate(gitHubTemplate);
			System.out.println("New name token: " + gitHubTemplate.getAccessToken().getValue());
			storeToken(name, gitHubTemplate);
		}
		else {
			response = queryWithPlainRestTEmplate(name);
			System.out.println("Known name token: " + tokens.get(name).getValue());
		}

		return response;
	}

	private ResponseEntity<String> queryWithOauth2RestTemplate(OAuth2RestTemplate gitHubTemplate) {
		//Asks for user authorization only if it hasn't got the token (user authorized gitHub)
		ResponseEntity<String> response =
				gitHubTemplate.exchange(url_GET_repositories, HttpMethod.GET, null, String.class);

		return response;
	}

	private void storeToken(String name, OAuth2RestTemplate gitHubTemplate) {
		tokens.put(name,gitHubTemplate.getAccessToken());
	}

	private ResponseEntity<String> queryWithPlainRestTEmplate(String name) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", String.format("%s %s", tokens.get(name).getTokenType(), tokens.get(name).getValue()));
		headers.add("Accept", "application/vnd.github.v3+json");

		RestTemplate restTemplate = new RestTemplate();

		String body = "";
		HttpEntity<String> request = new HttpEntity<String>(body, headers);

		ResponseEntity<String> result = restTemplate.exchange(url_GET_repositories,
				HttpMethod.GET, request, String.class);

		return result;
	}

	@Bean
	public OAuth2RestTemplate gitHubRestTemplate() {
		return new OAuth2RestTemplate(gitHub, oauth2ClientContext);
	}

}


