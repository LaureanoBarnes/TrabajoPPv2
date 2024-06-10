package holaSpringData.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfSegurity {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests

                                .requestMatchers("/cambiar/**", "/borrar/**").hasRole("ADMIN")
                                .requestMatchers("/anexar","/salvar").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/ingreso")
                                .permitAll()

                )
                .exceptionHandling(exceptionHandLing ->
                        exceptionHandLing.accessDeniedPage("/errores/403"))
        ;
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("159")
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("753")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

}


