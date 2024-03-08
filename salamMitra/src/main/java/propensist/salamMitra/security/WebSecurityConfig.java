package propensist.salamMitra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http
                        .csrf(Customizer.withDefaults())
                        .authorizeHttpRequests(requests -> requests
                                .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/tambah-admin")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                // .anyRequest().authenticated()
                        ) 
                        .formLogin((form) -> form
                                .loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/")
                        )
                        .logout((logout) -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                        );
                return http.build();
        }

        @Bean
        public BCryptPasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
                auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
        }
}
