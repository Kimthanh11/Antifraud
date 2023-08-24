package antifraud.dto.request;

import antifraud.service.UserService;

public class RoleRequest {
    private String username;
    private String role;

    public String getUsername(){ return username;}

    public String getRole() {return role;}
}
