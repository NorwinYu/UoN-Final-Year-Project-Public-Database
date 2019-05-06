package propra2.leihOrDie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor  extends HandlerInterceptorAdapter {
    @Autowired
    CookieHandler cookieHandler;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
            boolean isAdmin = (cookieHandler.checkIfAdmin(request.getCookies()));
            request.setAttribute("isAdmin", isAdmin);
    }
}
