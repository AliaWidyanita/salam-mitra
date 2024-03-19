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
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(Customizer.withDefaults())
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/register")).anonymous()
                            .requestMatchers(new AntPathRequestMatcher("/login")).anonymous()
                            .requestMatchers(new AntPathRequestMatcher("/ubah-sandi/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/pengguna/tambah-admin")).hasAnyAuthority("program_service", "manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengguna")).hasAnyAuthority("program_service", "manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengguna/hapus/**")).hasAnyAuthority("program_service", "manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/daftar-pengajuan-saya")).hasAuthority("mitra")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/detail-pengajuan-saya/**")).hasAuthority("mitra")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/daftar-pengajuan-admin")).hasAuthority("admin")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/review-pengajuan-admin/**")).hasAuthority("admin")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/review-pengajuan-admin/review/**")).hasAuthority("admin")

                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/daftar-pengajuan-manajemen")).hasAuthority("manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/review-pengajuan-manajemen/**")).hasAuthority("manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan/tambah")).hasAuthority("mitra")
                            .requestMatchers(new AntPathRequestMatcher("/getKabupatenKotaByProvinsi")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getKecamatanByProvinsiKabupatenKota")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                    ) 
                    .formLogin((form) -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .permitAll()
                    )
                    .logout((logout) -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/login")
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
