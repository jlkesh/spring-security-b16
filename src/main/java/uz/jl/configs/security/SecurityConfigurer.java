package uz.jl.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("uz.jl")
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    public static final String[] WHITE_LIST = new String[]{
            "/",
            "/auth/login",
            "/static/**"
    };

    @Value("${spring.security.rememberme.secret.key}")
    public String SECRET_KEY;
    //    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers(WHITE_LIST).permitAll()
//                                .antMatchers("/admin").hasRole("admin")
//                                .antMatchers("/mana").hasRole("manager")
//                                .antMatchers("/admin-manager").hasAnyRole("admin", "manager")
//                                .antMatchers("/user").authenticated()
//                                .antMatchers("/create").hasAuthority("create")
                                .anyRequest()
                                .authenticated())
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .usernameParameter("uname")
                                .passwordParameter("pswd")
                                .defaultSuccessUrl("/user", false))
                .rememberMe(httpSecurityRememberMeConfigurer ->
                        httpSecurityRememberMeConfigurer
                                .rememberMeParameter("rememberMe")
                                .rememberMeCookieName("remember")
                                .tokenValiditySeconds(10 * 86400)
                                .key(SECRET_KEY))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutRequestMatcher(
                                        new AntPathRequestMatcher("/auth/logout", "POST", true)
                                )
                                .logoutSuccessUrl("/auth/login")
                                .deleteCookies("JSESSIONID", "remember")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                );
        //                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("123"))
                .roles("admin")
                .and()
                .withUser("manager")
                .password(passwordEncoder.encode("123"))
                .roles("manager")
                .and()
                .withUser("user")
                .password(passwordEncoder.encode("123"))
                .authorities(new SimpleGrantedAuthority("create"))
        ;
    }



}
