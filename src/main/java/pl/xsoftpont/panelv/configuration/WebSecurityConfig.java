package pl.xsoftpont.panelv.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.xsoftpont.panelv.repository.UserRepository;



@ComponentScan("pl")
@Configuration
@EnableWebMvc

public class WebSecurityConfig {


    @Autowired
    UserRepository userService;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/view/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(new PlaintextPasswordEncoder());
        return authProvider;
    }


    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                pl.xsoftpont.panelv.model.User user = userService.findByLoginEquals(login);
                if (user != null) {

                    return new User(user.getLogin(), user.getPassword(), true, true, true, true,
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
                } else {
                    throw new UsernameNotFoundException("could not find the user '"
                            + login + "'");
                }
            }

        };
    }


    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.
                    authorizeRequests()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/login*").anonymous()
                    //.antMatchers("/quotes/active").anonymous()
                    .anyRequest().authenticated()
                    .and()

                    .formLogin()
                    .loginPage("/login")
                    .failureForwardUrl("/login?error")
                    .defaultSuccessUrl("/home")
                    .and()
                    .httpBasic()
                    .and().csrf().disable();

            http
                    .headers()
                    .frameOptions()
                    .sameOrigin();

        }
    }


}
