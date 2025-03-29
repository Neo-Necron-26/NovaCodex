package app.novacodex.novacodex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String email;
    private Long password;
    private int number;
    private String role;

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Long getPassword() {
        return password;
    }
    public int getNumber() {
        return number;
    }
    public String getRole() {
        return role;
    }

}