package antifraud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    // Add a constructor that initializes the 'number' field
    public Card(String number) {
        this.number = number;
    }

    // Default constructor
    public Card() {
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
}

