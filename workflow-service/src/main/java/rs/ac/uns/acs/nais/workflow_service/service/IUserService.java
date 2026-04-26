package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}