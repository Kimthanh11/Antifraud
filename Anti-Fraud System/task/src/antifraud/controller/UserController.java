package antifraud.controller;

import antifraud.dto.request.RoleRequest;
import antifraud.dto.request.UserRegistrationRequest;
import antifraud.dto.response.DeleteResponseDTO;
import antifraud.dto.response.UserDTO;
import antifraud.entity.User;
import antifraud.persistence.UserRepository;
import antifraud.dto.request.ChangeUserStatusRequest;
import antifraud.dto.response.ChangeUserStatusResponse;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @PostMapping("/user")
    public ResponseEntity<?> registerUser( @RequestBody UserRegistrationRequest registrationRequest) {
            if (registrationRequest.getName() == null ||
                    registrationRequest.getUsername() == null ||
                    registrationRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            UserDTO userDTO = userService.registerUser(registrationRequest);

            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Handle validation or conflict cases
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        }


    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getUsersList() {
        List<UserDTO> userDTOs = userService.getAllUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole().name()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<DeleteResponseDTO> deleteUser(@PathVariable String username) {
        User deletedUser = userService.deleteUserByUsername(username);

        if (deletedUser != null) {
            DeleteResponseDTO responseDTO = new DeleteResponseDTO(username, "Deleted successfully!");
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DeleteResponseDTO(username, "User not found"));
        }
    }

    @PutMapping("/role")
    public ResponseEntity<UserDTO> updateRole(@RequestBody RoleRequest roleRequest) {
        try {
            UserDTO userDTO = userService.updateRole(roleRequest);

            return ResponseEntity.status(HttpStatus.OK).body(userDTO);

        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Role already assigned

        } catch (IllegalArgumentException e){
            String errorMessage = e.getMessage();
            if ("User not found".equals(errorMessage)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
            } else if ("Invalid role".equals(errorMessage)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Invalid role
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Default error response
        }
    }

    @PutMapping("/access")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeUserStatusRequest changeUserStatusRequest) {

        try {
            ChangeUserStatusResponse statusDTO = userService.changeStatus(changeUserStatusRequest);

            return ResponseEntity.status(HttpStatus.OK).body(statusDTO);

        } catch (UnsupportedOperationException e) {
            String errorMessage = e.getMessage();
            if ("User not found".equals(errorMessage)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
            } else if ("CAN NOT LOCK ADMIN".equals(errorMessage)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Invalid role
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Default error response

        }
    }


}
