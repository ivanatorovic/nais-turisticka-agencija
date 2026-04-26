package rs.ac.uns.acs.nais.recommendation_service.mapper;

import rs.ac.uns.acs.nais.recommendation_service.dto.UserRequest;
import rs.ac.uns.acs.nais.recommendation_service.dto.UserResponse;
import rs.ac.uns.acs.nais.recommendation_service.model.User;

public class UserMapper {

    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}