package tivoli.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that is used on authenticating a user successfully.
 */
public class RoleBasedAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Value("/apllist")
    private String recruitTargetUrl;

    @Value("recruit")
    private String recruitAuthority;

    /**
     * Initialises this authenticator. It is used to determine which page to redirect the user to based on their role.
     * @param defUrl the default url to redirect to.
     * @param recruitTargetUrl the url to redirect to if authentication matched.
     * @param recruitAuthority the authority value that this matching is performed against.
     */
    public RoleBasedAuthSuccessHandler(String defUrl, String recruitTargetUrl, String recruitAuthority) {
        super(defUrl);
        this.recruitTargetUrl = recruitTargetUrl;
        this.recruitAuthority = recruitAuthority;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if(isRecruiter(authentication)){
            this.getRedirectStrategy().sendRedirect(request, response, this.getRecruitTargetUrl());
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private boolean isRecruiter(Authentication authentication) {
        for(GrantedAuthority authority : authentication.getAuthorities()){
            if(authority.getAuthority().equals(this.getRecruitAuthority())){
                return true;
            }
        }
        return false;
    }

    public String getRecruitAuthority() {
        return recruitAuthority;
    }

    public String getRecruitTargetUrl() {
        return recruitTargetUrl;
    }
}
