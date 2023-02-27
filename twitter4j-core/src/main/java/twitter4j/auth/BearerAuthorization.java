package twitter4j.auth;

import twitter4j.BASE64Encoder;
import twitter4j.HttpRequest;

/**
 * Implementação de autenticação por Bearer Token
 *
 * @author Diego Vargas
 */
public class BearerAuthorization implements Authorization, java.io.Serializable {

    private static final long serialVersionUID = -793538060259060828L;
    private final String basic;
    private final String bearerToken;

    public BearerAuthorization(String bearerToken) {
        this.bearerToken = bearerToken;
        this.basic = encodeBasicAuthenticationString();
    }

    public String getbearerToken() {
        return bearerToken;
    }

    private String encodeBasicAuthenticationString() {
        if (bearerToken != null) {
//            return "Bearer " + BASE64Encoder.encode((bearerToken).getBytes());
            return "Bearer " + bearerToken;
        }
        return null;
    }

    @Override
    public String getAuthorizationHeader(HttpRequest req) {
        System.out.println("getAuthorizationHeader - BearerAuthorization");
        return basic;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BearerAuthorization)) return false;

        BearerAuthorization that = (BearerAuthorization) o;

        return basic.equals(that.basic);

    }

    @Override
    public int hashCode() {
        return basic.hashCode();
    }

    @Override
    public String toString() {
        return "BearerAuthorization{" +
                "bearerToken='" + bearerToken + '\'' +
                '}';
    }

}
