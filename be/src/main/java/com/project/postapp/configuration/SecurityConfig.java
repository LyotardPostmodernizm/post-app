package com.project.postapp.configuration;

import com.project.postapp.security.JwtAuthenticationEntryPoint;
import com.project.postapp.security.JwtAuthenticationFilter;
import com.project.postapp.services.UserDetailsServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final JwtAuthenticationEntryPoint handler;

    public SecurityConfig(UserDetailsServiceImplementation userDetailsServiceImplementation, JwtAuthenticationEntryPoint handler) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.handler = handler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);


        config.addAllowedOrigin("http://localhost:3000");  // React frontend için izin verilen origin

        // İzin verilen header'lar
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        // İzin verilen HTTP metodları
        config.setAllowedMethods(List.of("OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));

        // Tüm endpointlere bu CORS ayarını uyguluyoruz
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS yapılandırması
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/posts", "/comments").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts", "/comments").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/posts/**", "/comments/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/posts/**", "/comments/**").authenticated()
                        .requestMatchers("/auth/**").permitAll() // Auth ile ilgili endpointlere herkese erişim izni veriyoruz
                        .anyRequest().authenticated() // Diğer tüm istekler için kimlik doğrulama gereksinimi gerekiyor
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Temel kimlik doğrulamayı kapatıyoruz
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless yapılandırma

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT doğrulama filtresi

        return http.build();
    }
}
