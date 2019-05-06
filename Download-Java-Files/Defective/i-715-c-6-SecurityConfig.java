package recruitment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import recruitment.application.RecruiterService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

/**
 * <p>Loads all security configurations for the recruitment application.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RecruiterService userDetailsService;

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Spring utilizes this method to set the userDetailsService and password encoder to the
     * authentication manager builder to the database.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    protected void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * A bean for successful authentications.
     *
     * @return The instance of MySimpleUrlAuthenticationSuccessHandler.
     */
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
            return new MySimpleUrlAuthenticationSuccessHandler();
    }

    /**
     * A bean for denied HTTP requests.
     *
     * @return The instance of CustomAccessDeniedHandler.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    /**
     * Configuration of rules for different HTTP requests.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/register*").permitAll()
                .antMatchers("/apply*").hasAuthority("applicant")
                .antMatchers("/list-applications*").hasAuthority("recruiter")
                .anyRequest().authenticated()
                .and()

                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()

                .formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler());
    }

    /**
     * Bean to encode passwords through BCrypt in the application.
     *
     * @return The password encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}