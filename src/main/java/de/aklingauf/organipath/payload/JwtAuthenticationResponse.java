package de.aklingauf.organipath.payload;

// From https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-2/

import de.aklingauf.organipath.model.User;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User currentUser;

    public JwtAuthenticationResponse(String accessToken, User currentUser) {
        this.accessToken = accessToken;
        this.currentUser = currentUser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
