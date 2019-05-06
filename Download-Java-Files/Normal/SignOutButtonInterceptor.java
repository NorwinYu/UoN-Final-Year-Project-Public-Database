package propra2.leihOrDie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SignOutButtonInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CookieHandler cookieHandler;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        boolean loggedIn = (cookieHandler.checkIfLoggedIn(request.getCookies()));
        request.setAttribute("isLoggedIn", loggedIn);
    }
}
