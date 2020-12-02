package br.com.researchbr.backendresearchbr.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
        http
				.cors().and()
				.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
				.antMatchers(HttpMethod.POST,"/api/users/new").permitAll()
				.antMatchers(HttpMethod.GET,"/api/users/user/{username}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/api/users/all").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.POST,"/api/projects/new").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/api/projects/all").hasAnyRole("PRINCIPAL", "ASSISTANT")
				.antMatchers(HttpMethod.POST,"/api/invoices/new").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/api/invoices/invoice/edit/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/api/invoices/all").hasAnyRole("PRINCIPAL", "ASSISTANT")
				.antMatchers(HttpMethod.POST,"/api/items/new").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/api/items/item/edit/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/api/items/all").hasAnyRole("PRINCIPAL", "ASSISTANT")
				//Descomentar as 4 linhas seguintes para realizar os testes do controller e comentar as 12 de cima
//				.antMatchers(HttpMethod.POST,"/api/**").permitAll()
//				.antMatchers(HttpMethod.GET,"/api/**").permitAll()
//				.antMatchers(HttpMethod.PUT,"/api/**").permitAll()
//				.antMatchers(HttpMethod.DELETE,"/api/**").permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}