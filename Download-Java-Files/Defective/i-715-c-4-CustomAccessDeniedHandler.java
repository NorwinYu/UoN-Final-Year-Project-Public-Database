package recruitment.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import recruitment.domain.IllegalActionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class handles denied HTTP requests for failed authorizations.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles denied accesses by redirecting a user to previous page based
     * on what authority you have.
     *
     * @param request The request that resulted in an AccessDeniedException.
     * @param response The response so that the user agent can be advised of the failure.
     * @param exc The exception that caused the invocation.
     * @throws IOException
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {
        boolean isRecruiter = false;
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        isRecruiter = recruitment.config.MySimpleUrlAuthenticationSuccessHandler.isRecruiter(isRecruiter, authentication);
        if (isRecruiter) {
            response.sendRedirect(request.getContextPath() + "/list-applications");
        } else if (!isRecruiter) {
            response.sendRedirect(request.getContextPath() + "/apply");
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
            throw new IllegalStateException();
        }
    }
}
