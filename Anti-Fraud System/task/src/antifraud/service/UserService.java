package antifraud.service;

import antifraud.dto.request.ChangeUserStatusRequest;
import antifraud.dto.request.RoleRequest;
import antifraud.dto.request.UserRegistrationRequest;
import antifraud.dto.response.ChangeUserStatusResponse;
import antifraud.dto.response.UserDTO;
import antifraud.entity.User;
import antifraud.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public enum UserRole {
        ANONYMOUS,
        MERCHANT,
        ADMINISTRATOR,
        SUPPORT
    }

    public enum Status {
        LOCK,
        UNLOCK
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User deleteUserByUsername(String username) {
        User userToDelete = userRepository.findByUsername(username).orElse(null);

        if (userToDelete != null) {
            userRepository.delete(userToDelete);
        }

        return userToDelete;
    }

    public UserDTO registerUser(UserRegistrationRequest registrationRequest) {

        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return null; // Handle conflict case
        }

        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());


        User user = new User(registrationRequest.getName(), registrationRequest.getUsername(), encodedPassword);

        if (userRepository.count() == 0) {
            user.setRole(UserRole.ADMINISTRATOR);
        }
        user.setLocked(user.getRole() != UserRole.ADMINISTRATOR);

        userRepository.save(user);

        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole().name());
    }


    public UserDTO updateRole(RoleRequest roleRequest) {
        User userToChangeRole = userRepository.findByUsername(roleRequest.getUsername()).orElse(null);

        if (userToChangeRole == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (userToChangeRole.getRole().name().equals(roleRequest.getRole())) {
            throw new UnsupportedOperationException("Role already assigned"); // Role already provided to the user
        }

        if (!( "MERCHANT".equals(roleRequest.getRole()) || "SUPPORT".equals(roleRequest.getRole()) )) {
            throw new IllegalArgumentException("Invalid role");
        }
        userToChangeRole.setRole(UserRole.valueOf(roleRequest.getRole()));
        userRepository.save(userToChangeRole);

        return new UserDTO(userToChangeRole.getId(), userToChangeRole.getName(), userToChangeRole.getUsername(), userToChangeRole.getRole().name());
    }

    public ChangeUserStatusResponse changeStatus(ChangeUserStatusRequest changeUserStatusRequest) {
        User user = userRepository.findByUsername(changeUserStatusRequest.getUsername()).orElse(null);

        if (user == null) {
            throw new UnsupportedOperationException("USER NOT FOUND");
        }

        if (user.getRole() == UserRole.ADMINISTRATOR) {
            throw new UnsupportedOperationException("CAN NOT LOCK ADMIN");
        }


        Status userStatus = Status.valueOf(changeUserStatusRequest.getOperation().toUpperCase());
        user.setLocked(userStatus == Status.LOCK);

        userRepository.save(user);
        return new ChangeUserStatusResponse(user.getUsername(), user.getLocked());
    }


}
