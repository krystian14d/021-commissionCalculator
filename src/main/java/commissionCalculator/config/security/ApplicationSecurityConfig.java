package commissionCalculator.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/api/").authenticated()
                .mvcMatchers("/").permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();

        UserDetails bankEmployee = User
                .withUsername("bankemployee")
                .password("{noop}bankpass")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, bankEmployee);
    }
}
