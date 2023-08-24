package antifraud.dto.response;

import antifraud.service.UserService;

public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String role;
    
    public UserDTO(){}

    public UserDTO(Long id, String name, String username, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
    }


    // Getters, setters...
    public Long getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getUsername(){
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}