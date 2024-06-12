package holaSpringData.web;

import holaSpringData.util.UsuarioDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfSegurity {
    private final UsuarioDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/cambiar/**", "/borrar/**").hasRole("ADMIN")
                                .requestMatchers("/anexar", "/salvar").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/ingreso")
                                .permitAll()
                )
                .logout(logout->
                        logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/ingreso?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/errores/403"))
        ;
        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
