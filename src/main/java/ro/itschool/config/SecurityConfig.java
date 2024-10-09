package ro.itschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.itschool.auditor.SpringSecurityAuditorAware;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enable method-level security with Pre/Post annotations
public class SecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder()); // Use BCrypt for password encoding
    return authProvider;
  }

  // Configuring HttpSecurity
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/auth/welcome", "/error").permitAll() // Allow public
                            .requestMatchers("/auth/user/**").hasRole("USER") // Require USER role for
                            .requestMatchers("/auth/admin/**").hasRole("ADMIN") // Require ADMIN role
                            .anyRequest().authenticated() // Require authentication for all other
                                  )
            .formLogin(formLogin -> formLogin
                               .permitAll() // Allow access to the login page
                               .defaultSuccessUrl("/auth/welcome", true) // Always redirect to welcome after login
                               .failureUrl("/auth/login?error=true") // Redirect on login failure
                      )
            .logout(logout -> logout
                    .logoutUrl("/auth/logout") // Custom logout URL
                    .logoutSuccessUrl("/auth/welcome?logout=true") // Redirect after logout
                    .invalidateHttpSession(true) // Invalidate session on logout
                    .deleteCookies("JSESSIONID") // Delete session cookies
                   )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                                       .accessDeniedPage("/error/403") // Custom access denied page
                              );

    return http.build();
  }

  // Password Encoding
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Use BCrypt for hashing passwords
  }

  @Bean
  public AuditorAware<String> auditorAware() {
    return new SpringSecurityAuditorAware(); // Use custom AuditorAware implementation
  }

}
