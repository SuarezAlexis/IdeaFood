package jainjo.ideafood.config;

import jainjo.ideafood.services.IdeaFoodUserDetailsService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;
    
    private final String REMEMBER_ME_KEY = "ideaFoodKey";
    private final String REMEMBER_ME_COOKIE_NAME = "IdeaFood";
    private final String REMEMBER_ME_PARAMETER = "recordar";
    
    public SecurityConfig() {
        super();
    }
    
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests().anyRequest().permitAll().and()
            .formLogin()
                .loginPage("/Ingresar")
                .loginProcessingUrl("Ingresar")
                .and()
            .rememberMe()
                .key(REMEMBER_ME_KEY)
                .userDetailsService(userDetailsService)
                .rememberMeCookieName(REMEMBER_ME_COOKIE_NAME)
                .rememberMeParameter(REMEMBER_ME_PARAMETER);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
    
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new IdeaFoodUserDetailsService();
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean 
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public RememberMeServices rememberMeServices() {
        RememberMeServices rememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        ((TokenBasedRememberMeServices)rememberMeServices).setCookieName(REMEMBER_ME_COOKIE_NAME);
        ((TokenBasedRememberMeServices)rememberMeServices).setParameter(REMEMBER_ME_PARAMETER);
        return rememberMeServices;
    }
    
    @Bean
    public CookieClearingLogoutHandler cookieClearingHandler() {
        return new CookieClearingLogoutHandler("JSESSIONID",REMEMBER_ME_COOKIE_NAME);
    }
}
