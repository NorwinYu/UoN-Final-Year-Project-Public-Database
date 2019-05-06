package recruitment.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * <p>This class makes it possible for spring to inject Beans into JPA entities.</p>
 */
@Service
class BeanFactory implements ApplicationContextAware {
    private static ApplicationContext context;

    static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * Sets the application context that this object runs in.
     *
     * @param applicationContext The context.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}