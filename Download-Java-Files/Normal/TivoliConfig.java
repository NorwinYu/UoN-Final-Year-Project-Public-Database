package tivoli.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableTransactionManagement

/**
 * Loads all configuration for the entire application. There are additional
 * configurations in the file application.properties.
 */
@EnableWebMvc
@Configuration
public class TivoliConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    /**
     * @param applicationContext The application context used by the running
     *                           application.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    /**
     * Create a <code>org.springframework.web.servlet .ViewResolver</code> bean
     * that delegates all views to thymeleaf's template engine. There is no need
     * to specify view name patterns since the will be the only existing view
     * resolver.
     */
    @Bean
    public ThymeleafViewResolver viewResolver()
    {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setTemplateEngine(templateEngine());

        return viewResolver;
    }


    /**
     * Create a <code>org.thymeleaf.ITemplateEngine</code> bean that manages
     * thymeleaf template integration with Spring. All template resolution will
     * be delegated to the specified template resolver.
     */
    @Bean(name = "tivoliTemplateEngine")
    public SpringTemplateEngine templateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new LayoutDialect());

        return templateEngine;
    }

    /**
     * Create a <code>org.thymeleaf.templateresolver.ITemplateResolver</code>
     * that can handle thymeleaf template integration with Spring. This will be
     * the only existing template resolver.
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver()
    {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("classpath:/web-root/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);

        return templateResolver;
    }


    /**
     * Configuration of requests for static files.
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {

        int cachePeriodForStaticFilesInSecs = 1;
        String rootDirForStaticFiles = "classpath:/web-root/";

        registry.addResourceHandler("/**")

                .addResourceLocations(rootDirForStaticFiles)
                .setCachePeriod(cachePeriodForStaticFilesInSecs)
                .resourceChain(true).addResolver(new PathResourceResolver());
    }

}
