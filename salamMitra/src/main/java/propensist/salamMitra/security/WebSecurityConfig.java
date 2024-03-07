package propensist.salamMitra.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import apap.tutorial.bacabaca.security.jwt.JwtTokenFilter;
import jakarta.websocket.OnError;
//import propensist.salamMitra.security.jwt.JwtTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
//     @Autowired
//     private UserDetailsService userDetailsService;

//     // @Autowired
//     // private JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/assets/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/bootstrap/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/img/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/assets/webfonts/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .anyRequest().authenticated()
                ) 
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );
                // .logout((logout) -> logout
                //         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //         .logoutSuccessUrl("/")
                // );
        return http.build();
    }

//     // @Bean
//     // @Order(1)
//     // public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

//     //     http.securityMatcher("/api/**")
//     //             .csrf(AbstractHttpConfigurer::disable)
//     //             .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//     //                     .requestMatchers("/api/auth/**").permitAll()
//     //                     .anyRequest().authenticated()
//     //             )
//     //             .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//     //             .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

//     //     return http.build();
//     // }

//     @Bean
//     public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception{
//         http
//                 .csrf(Customizer.withDefaults())
//                 .authorizeHttpRequests(requests -> requests
//                         .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .formLogin((form) -> form
//                         .loginPage("/login")
//                         .permitAll()
//                         .defaultSuccessUrl("/")
//                 )
//         ;
//         return http.build();
//     }
//     @Bean
//     public BCryptPasswordEncoder encoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Autowired
//     public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//         auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//     }

//     @Bean
//     public AuthenticationFailureHandler handleAuthenticationFailure() {
//         return new SimpleUrlAuthenticationFailureHandler() {

//             @Override
//             public void onAuthenticationFailure(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException authenticationException) throws IOException, ServletException {
                
//                 if (authenticationException instanceof BadCredentialsException) {
//                     setDefaultFailureUrl("/login?error");
//                 }
//                 else if (authenticationException instanceof DisabledException) {
//                     setDefaultFailureUrl("/login?accessDenied");
//                 }
                
//                 super.onAuthenticationFailure(httpRequest, httpResponse, authenticationException);
//             }
//         };
//     }
}
