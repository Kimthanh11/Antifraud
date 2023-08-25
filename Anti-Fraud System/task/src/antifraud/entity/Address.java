package antifraud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    public Address(){}

    public Address(String ip) {
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public String getIp(){
        return ip;
    }
}
