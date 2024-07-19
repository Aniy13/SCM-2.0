package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.service.impl.SecurityUserDetailsService;



@Configuration
@EnableWebSecurity
public class MyConfig {
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	@Autowired
	private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return userDetailsService();
//	}
   @Bean
   public PasswordEncoder encoder() {
	   return new BCryptPasswordEncoder();
   }
   @Bean
   public AuthenticationProvider authenticationProvider() {
	   DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	   provider.setUserDetailsService(userDetailsService);
	   provider.setPasswordEncoder(encoder());
	   
	   return provider;
   }
   @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	   //url configure 
	   httpSecurity.authorizeHttpRequests(authorize->{
//		   authorize.requestMatchers("/test","/signup","login").permitAll();
		   authorize.requestMatchers("/user/**").authenticated();
		   authorize.anyRequest().permitAll();
	   });
	   //default login /change here
	    httpSecurity.formLogin(formLogin->{
	    	formLogin.loginPage("/login")
	    	.loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/profile", true)
            .failureUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password");
	    	
	    	
	    	
	    });
	    
	    httpSecurity.csrf(AbstractHttpConfigurer::disable);
	    
	    
	    httpSecurity.logout(logoutForm->{
	    	logoutForm.logoutUrl("/logout");
	    	logoutForm.logoutSuccessUrl("/login?logout=true");
	    });
	    
	    
	    //oauth configurations
	    httpSecurity.oauth2Login(oauth->{
	    	oauth.loginPage("/login");
	    	oauth.successHandler(oAuthAuthenticationSuccessHandler);
	    });
	return  httpSecurity.build();  
  }
   
   
}
