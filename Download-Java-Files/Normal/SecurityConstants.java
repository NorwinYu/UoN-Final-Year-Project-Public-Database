package api.iti0208.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs"; // this should stay hiddden
    public static final long EXPIRATION_TIME = 259_200_000; // 3 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/register";
}