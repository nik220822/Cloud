package com.Nickode.configuration;

import com.Nickode.security.NiCloudOncePerRequestFilter;
import com.Nickode.service.NiCloudUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class NiCloudSecurityConfig {

    @Autowired
    private NiCloudOncePerRequestFilter niCloudOncePerRequestFilter;
    @Autowired
    private NiCloudUserService niCloudUserService;


    public NiCloudSecurityConfig(NiCloudOncePerRequestFilter niCloudOncePerRequestFilter, NiCloudUserService niCloudUserService) {
        this.niCloudOncePerRequestFilter = niCloudOncePerRequestFilter;
        this.niCloudUserService = niCloudUserService;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(niCloudUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityfilterChain(final HttpSecurity http) throws Exception {
        String theOnlyOneRole = "ROLE_USER";
        http
                .cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()
                        .anyRequest().hasAuthority(theOnlyOneRole))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(niCloudOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
