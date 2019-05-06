package at.jugger.tracker.config;

import at.jugger.tracker.security.TokenAuthenticationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static at.jugger.tracker.dto.UserData.RoleEnum.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${enable-csrf}")
    private boolean csrfEnabled;

    private static RequestMatcher publicUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/*"),
            new AntPathRequestMatcher("/api/authentication/*")
    );
    private static RequestMatcher forbiddenUrls = new NegatedRequestMatcher(publicUrls);

    private static RequestMatcher mitgliederUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/users", GET.name()),
            new AntPathRequestMatcher("/api/users/current"),
            new AntPathRequestMatcher("/api/trainings", GET.name()),
            new AntPathRequestMatcher("/api/trainings/*", GET.name()),
            new AntPathRequestMatcher("/api/tournaments", PUT.name()),
            new AntPathRequestMatcher("/api/tournaments/*", GET.name())
    );

    private static RequestMatcher trainerUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/trainings"),
            new AntPathRequestMatcher("/api/trainings/*")
    );

    private static RequestMatcher adminUrls = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/users"),
            new AntPathRequestMatcher("/api/users/*"),
            new AntPathRequestMatcher("/api/tournaments"),
            new AntPathRequestMatcher("/api/tournaments/*")
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), forbiddenUrls)
                    .and()
                .authorizeRequests()
                    .requestMatchers(publicUrls).permitAll()
                    .requestMatchers(mitgliederUrls).hasAnyRole(MITGLIED.name(), TRAINER.name(), ADMIN.name())
                    .requestMatchers(trainerUrls).hasAnyRole(TRAINER.name(), ADMIN.name())
                    .requestMatchers(adminUrls).hasAnyRole(ADMIN.name())
                    .anyRequest().denyAll()
                    .and()
                .formLogin()
                    .loginPage("/")
                    .permitAll();

        if (!csrfEnabled) {
            http
                    .csrf().disable();
        }
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new TokenAuthenticationManager();
    }
}