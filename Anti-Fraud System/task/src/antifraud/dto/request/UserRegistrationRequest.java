package antifraud.dto.request;

public class UserRegistrationRequest {
    private String name;
    private String username;
    private String password;

    // Constructors, getters, setters...
    public UserRegistrationRequest(){}

    public UserRegistrationRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}