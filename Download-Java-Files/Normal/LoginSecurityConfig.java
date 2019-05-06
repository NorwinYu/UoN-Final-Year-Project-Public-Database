package tivoli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tivoli.application.TivoliService;
import tivoli.presentation.RoleBasedAuthSuccessHandler;

/**
 * Contains configuration for spring security.
 */
@Configuration
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TivoliService userDetailsService;

    /**
     * Sets the password encoder a no password one.
     * @return the password encoder
     */
    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    /**
     * Configure the AuthenticationManagerBuilder of spring security.
     * @param builder the builder used.
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures HTTP requests handling and access.
     * @param http HTTP security.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // Login not required for these pages
        http.authorizeRequests().antMatchers("/", "/login", "/signup", "/403", "/error").permitAll();

        // /profile page requires access as an applicant, will redirect to /login if not logged in
        http.authorizeRequests().antMatchers("/profile").access("hasRole('ROLE_APPLICANT')");

        // /appllist page requires access as a recruiter, will redirect to /login if not logged in
        http.authorizeRequests().antMatchers("/appllist").access("hasRole('ROLE_RECRUIT')");

        // if a user tries to access a page that they dont have access to as defined by their role
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Form login config
        http.authorizeRequests().and().formLogin()
                .loginPage("/login") // URL of login page
                .loginProcessingUrl("/do-login") // URL of login form action
                .successHandler(this.getSuccessHandler())  //Role based handler for successful login
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password");

        // Log out stuff
        http.authorizeRequests().and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccessful");
    }

    /**
     * Control access to files.
     * @param web the Websecurity object.
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/fragments/**", "/stylesheets/**");
    }

    private AuthenticationSuccessHandler getSuccessHandler() {
        return new RoleBasedAuthSuccessHandler(
                "/profile",
                "/appllist",
                "ROLE_RECRUIT"
        );

    }
}
