package rs.ac.uns.acs.nais.recommendation_service.dto;

public class UserUpdateRequest {
    private String username;
    private String email;

    public UserUpdateRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
