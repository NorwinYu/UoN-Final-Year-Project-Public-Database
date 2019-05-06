package gov.usgs.wma.mlrlegacy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.resource.id}")
	private String resourceId;
	
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**").permitAll()
				.antMatchers("/health/**", "/info/**").permitAll()
				.anyRequest().fullyAuthenticated()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.cors()
			.and()
				.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(standardAuthEntryPoint())
		;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId);
	}

	@Bean
	public AuthenticationEntryPoint standardAuthEntryPoint() {
		return new AuthenticationEntryPoint(){
		
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
						final Map<String, Object> mapBodyException = new HashMap<>() ;

						mapBodyException.put("error_message", authException.getMessage()) ;
						response.setContentType("application/json") ;
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;
						mapper.writeValue(response.getOutputStream(), mapBodyException) ;
			}
		};
	}

}
