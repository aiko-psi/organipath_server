package de.aklingauf.organipath.payload;

// From https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-2/

import de.aklingauf.organipath.model.User;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String currentUsername;

    public JwtAuthenticationResponse(String accessToken, String currentUsername) {
        this.accessToken = accessToken;
        this.currentUsername = currentUsername;
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

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
}
