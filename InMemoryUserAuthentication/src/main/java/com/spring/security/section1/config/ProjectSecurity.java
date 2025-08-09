package com.spring.security.section1.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurity {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
 //       http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
 //       http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll()cd);
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/cards","/balance","/accounts","/loans").authenticated()
                .requestMatchers("/contact","/notices","/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin=User.withUsername("root")
                .password("{bcrypt}$2a$12$2K8van3dQXa8p9GhLgj4B.PSvpiJaYO4oMzhUsF78Oql5jYRfKCMG").authorities("admin").build();
        UserDetails user=User.withUsername("user").password("{noop}user").authorities("read").build();
    return  new InMemoryUserDetailsManager(admin,user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
