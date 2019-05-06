package recruitment.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * This class handles all successful authentications after a login.
 */
public class MySimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * The framework calls this method when a user has been successfully authenticated.
     *
     * @param request The request which caused the successful authentication.
     * @param response The response.
     * @param authentication The Authentication object which was created during the authentication process.
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String targetURL;
        boolean isRecruiter = false;
        isRecruiter = isRecruiter(isRecruiter, authentication);
        if (isRecruiter) {
            targetURL = "/list-applications";
        } else if (!isRecruiter) {
            targetURL = "/apply";
        } else {
           throw new IllegalStateException();
        }
        return targetURL;
    }

    /**
     * Determines if the user is a recruiter.
     *
     * @param isRecruiter False by default.
     * @param authentication The Authentication object which was created during the authentication process.
     * @return True if user is a recruiter, otherwise false.
     */
    public static boolean isRecruiter(boolean isRecruiter, Authentication authentication){
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("recruiter")) {
                isRecruiter = true;
                break;
            }
        }
        return isRecruiter;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}